package com.jeeplus.modules.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import scala.noinline;

import com.alibaba.fastjson.JSON;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.dao.EventMapper;
import com.jeeplus.modules.business.dao.EventDetailMapper;
import com.jeeplus.modules.business.dao.SysUserArtistMapper;
import com.jeeplus.modules.business.dto.Event4VerifyDTO;
import com.jeeplus.modules.business.dto.EventDTO;
import com.jeeplus.modules.business.dto.EventInfoDTO;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.Event;
import com.jeeplus.modules.business.entity.EventDetail;
import com.jeeplus.modules.business.entity.EventDetailExample;
import com.jeeplus.modules.business.entity.EventExample;
import com.jeeplus.modules.business.entity.SysUserArtist;
import com.jeeplus.modules.business.entity.SysUserArtistExample;
import com.jeeplus.modules.business.entity.EventExample.Criteria;
import com.jeeplus.modules.business.utils.BusinessStringUtils;
import com.jeeplus.modules.business.utils.DateTimeUtils;

@Service
public class EventService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	EventMapper eventMapper;
	@Autowired
	EventDetailMapper eventDetailMapper;
	@Autowired
	SysUserArtistMapper userArtistMapper ;
	
	public Page<EventDTO> findEvents(Page<EventDTO> page, EventDTO eventDTO) {
		eventDTO.setPage(page);
		
		String capacityScope = eventDTO.getCapacityScope();
		String[] scope = null;
		if(StringUtils.isNotBlank(capacityScope)){
			scope = capacityScope.split(",");
			if(scope.length == 2){
				eventDTO.setCapacityStart(Short.valueOf(scope[0]));
				eventDTO.setCapacityEnd(Short.valueOf(scope[1]));
			}else{
				logger.error("param error : capacityScope");
			}
		}
		if(StringUtils.isNotBlank(eventDTO.getEventName())){
			eventDTO.setEventName(BusinessStringUtils.characterEscape(eventDTO.getEventName().trim()));
		}
		if(StringUtils.isNotBlank(eventDTO.getEventStatus())){
			eventDTO.setEventStatus(eventDTO.getEventStatus().trim());
		}
		List<EventDTO> list = eventMapper.listEvents(eventDTO);
		
		return page.setList(list);
	}

	public EventDTO getEvent(Integer id) {
		EventDTO eventDTO = new EventDTO();
		if(id == null || id <= 0) return eventDTO ;
		
		Event event = eventMapper.selectByPrimaryKey(id);
		if (event != null && event.getId() != null && event.getId() >0) {
			EventDetail eventDetail = eventDetailMapper.selectByPrimaryKey(id);
			BeanUtils.copyProperties(event, eventDTO);
			BeanUtils.copyProperties(eventDetail , eventDTO);
		}
		
		return eventDTO ;
	}

//	@Transactional(readOnly = false)
	public int saveEvent(EventDTO eventDTO) {
		if(eventDTO == null ){
			logger.error("VenueService.saveEvent() param eventDTO is null!");
		}
		
		eventDTO.setFreeService(StringUtils.join(eventDTO.getFreeServices(), ",")) ;
		
		Event event = new Event();
		EventDetail eventDetail = new EventDetail() ;
		
		eventDTO.synchroId();
		BeanUtils.copyProperties(eventDTO, event);
		BeanUtils.copyProperties(eventDTO , eventDetail );

		if(eventDTO.getId() == null || eventDTO.getId() == 0){
			event.preInsert();
			eventMapper.insertSelective(event);
			
			int eventId = event.getId() ;
			
			logger.debug("newId ==> "+ eventId);
			
			eventDetail.setEventId(eventId);
			eventDetailMapper.insertSelective(eventDetail);
			return eventId ;
		}else{
			event.preUpdate();
			eventMapper.updateByPrimaryKeySelective(event);
			eventDetailMapper.updateByPrimaryKeySelective(eventDetail);
			return eventDTO.getId() ;
		}
	}

	@Transactional(readOnly = false)
	public void auditPass(EventDTO eventDTO) {
		Event event = new Event();
		BeanUtils.copyProperties(eventDTO, event);
		event.preUpdate();
		event.preAudit();
		eventMapper.updateByPrimaryKeySelective(event);
	}

	/**
	 * 逻辑删除
	 */
	@Transactional(readOnly = false)
	public void deleteEventLogic(Integer id) {
		Event event = new Event();
		event.setId(id);
		event.preDeleteLogic();
		eventMapper.updateByPrimaryKeySelective(event);
	}
	
	/**
	 * 逻辑删除
	 */
	@Transactional(readOnly = false)
	public void deleteMultiEventsLogic(List<Integer> ids) {
		Event event = new Event();
		event.preDeleteLogic();
		
		EventExample example = new EventExample();
		example.createCriteria()
			.andIdIn(ids)
			.andUpdateTimeEqualTo(event.getUpdateTime())
			.andOperatorIdEqualTo(event.getOperatorId())
			.andIsDeleteEqualTo(event.getIsDelete());
		
		eventMapper.updateByExampleSelective(event,example);
	}

	
	public List<Event> listEventsByArtistIdAssistantId(String userId) {
		//( or ) and 
		Integer artistId = 0 ;
		Date currentDateAhead2Hours = DateTimeUtils.delayOrAheadHour(-2) ; //当前时间提前2小时
		List<Event> events = null ;
		EventExample example = new EventExample();
		Criteria criteria = example.createCriteria();
		criteria.andAssistantIdEqualTo(userId); //sys_user
		criteria.andEventTimeGreaterThanOrEqualTo(currentDateAhead2Hours);
		
		try{
			SysUserArtistExample userArtistExample = new SysUserArtistExample();
			userArtistExample.createCriteria().andSysUserIdEqualTo(userId) ;
			List<SysUserArtist> userArtists = userArtistMapper.selectByExample(userArtistExample);
			if (userArtists != null && userArtists.size()==1) {
				artistId = userArtists.get(0).getArtistId() ;
			}else{
				logger.info("correlation(sys_user and t_artist) is failure ");
			}
		}catch(NumberFormatException e){
			logger.info("userId is String which can not be changed to number");
		}
		Criteria criteria1 = example.createCriteria();
		criteria1.andEventTimeGreaterThanOrEqualTo(currentDateAhead2Hours);
		criteria1.andArtistIdEqualTo(artistId); //t_artists
		
		example.or(criteria1);
		
		events = eventMapper.selectByExample(example);
		return events;
	}

	public List<WorkTableDTO> getWorkTableEventInfo() {
		List<WorkTableDTO> list = eventMapper.selectWorkTableEventInfo() ;
		return list;
	}

	/**
	 * 为物料单提供活动列表<br/>
	 * 条件：活动状态为：销售中或售完，活动开始前24小时内，且未创建物料单的活动
	 */
	public List<EventDTO> listEvents4MaterialDelivery(){
		List<EventDTO> events = eventMapper.listEvents4MaterialDelivery();
		if (events == null) return new ArrayList<EventDTO>() ;
		return events ;
	}
	
	/**
	 * 活动详情<br/>
	 * 并获取关联画师、场所、助教 相关信息
	 *
	 */
	public EventInfoDTO getEventassociatedInfo(Integer id) {
		EventInfoDTO event = new EventInfoDTO();
		if(id == null || id <= 0) return event ;
		
		event = eventMapper.getEventassociatedInfo(id);
		return event;
	}

	/**
	 * 产生活动分享描述语list
	 * @return
	 */
	public List<String> genShareDescList() {
		List<String> list = new ArrayList<String>();
		String[] shareDescModels = CommonConstants.shareDescModels ;
		for (String shareDescModel : shareDescModels) {
			//todo 活动分享描述语模板  占位符替换
			list.add(shareDescModel) ;
		}
		return list;
	}
	
	/**
	 * 复制活动<br/>
	 * 注：不能使用事务，须手动同步
	 */
	//@Transactional(readOnly = false)
	public boolean copyEvent(Integer id) {
		int num = 0 ;
		
		Event event  = eventMapper.selectByPrimaryKey(id);
		event.setId(null);
		event.setEventTime(new Date());
		event.setClosingTime(null);
		event.setSold(null);
		event.setEventStatus(CommonConstants.EventStatus.ONSALE.getCode());
		event.setViewNum(1);
		event.preInsert();
		event.setIsCheck(CommonConstants.AUDIT_WAIT);
		num = eventMapper.insertSelective(event);
		if (num>0) {
			int eventId = event.getId() ;
			
			EventDetail eventDetail = eventDetailMapper.selectByPrimaryKey(id);
			eventDetail.setEventId(eventId);
			num = eventDetailMapper.insertSelective(eventDetail) ;
			
			if (num>0) {
				return true ;
			} else {
				eventMapper.deleteByPrimaryKey(id);
			}
		}
		
		return false;
	}
	
	
	
	
	
	
	
	
}
