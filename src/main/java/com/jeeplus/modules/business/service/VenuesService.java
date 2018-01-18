package com.jeeplus.modules.business.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.dao.VenuesMapper;
import com.jeeplus.modules.business.dto.ArtistDTO;
import com.jeeplus.modules.business.dto.VenueDTO;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.ArtistExample;
import com.jeeplus.modules.business.entity.Venues;
import com.jeeplus.modules.business.entity.VenuesExample;
import com.jeeplus.modules.business.entity.VenuesExample.Criteria;
import com.jeeplus.modules.business.utils.BusinessStringUtils;
import com.jeeplus.modules.business.utils.BusinessUtils;

@Service
public class VenuesService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	VenuesMapper venuesMapper ;
	
	/**
	 * 自动搜索 列表
	 * @return
	 */
	public List<VenueDTO> list4AutoComplete(String artistSearchParam){
		VenuesExample example = new VenuesExample();
		example.createCriteria().andVenueNameLike("%"+artistSearchParam+"%");
		List<VenueDTO> venues = venuesMapper.select4AutoCompleteByExample(example);
		return venues;
	}
	
	public Venues getVenue(Integer id) {
		if(id == null || id <= 0) return new Venues();
		
		Venues venues = (Venues) CacheUtils.get(BusinessUtils.CACHE_VENUES, BusinessUtils.CACHE_VENUES_BY_ID + id);
		if(venues == null){
			venues = venuesMapper.selectByPrimaryKey(id);
			CacheUtils.put(BusinessUtils.CACHE_VENUES, BusinessUtils.CACHE_VENUES_BY_ID + id, venues);
		}
		return venues;
	}
	
	
	public Page<Venues> findVenues(Page<Venues> page, Venues venue) {
		venue.setPage(page);
		
		String capacityScope = venue.getCapacityScope();
		String[] scope = null;
		if(StringUtils.isNotBlank(capacityScope)){
			scope = capacityScope.split(",");
			venue.setCapacityStart(Short.valueOf(scope[0]));
			venue.setCapacityEnd(Short.valueOf(scope[1]));
		}
		if(StringUtils.isNotBlank(venue.getVenueName())){
			venue.setVenueName(BusinessStringUtils.characterEscape(venue.getVenueName().trim()));
		}
		page.setList(venuesMapper.select4List(venue ));
		return page;
	}

	@Transactional(readOnly = false)
	public void deleteVenue(Integer id) {
		venuesMapper.deleteByPrimaryKey(id);
		CacheUtils.remove(BusinessUtils.CACHE_VENUES, BusinessUtils.CACHE_VENUES_BY_ID + id);
	}
	
	@Transactional(readOnly = false)
	public void deleteMultiVenue(List<Integer> lds) {
		VenuesExample venuesExample = new VenuesExample();
		venuesExample.createCriteria().andIdIn(lds );
		venuesMapper.deleteByExample(venuesExample);
		for(int id : lds){
			CacheUtils.remove(BusinessUtils.CACHE_VENUES, BusinessUtils.CACHE_VENUES_BY_ID + id);
		}
	}

	@Transactional(readOnly = false)
	public void saveVenue(Venues venue) {
		if(venue == null ){
			logger.error("VenuesService.saveVenue() param venue is null!");
		}
		if(venue.getId() == null || venue.getId() == 0){ 
			venue.preInsert();
			venue.setStatus(CommonConstants.STATUS_INVALID);
			venue.setIsCheck(CommonConstants.AUDIT_WAIT);
			venuesMapper.insertSelective(venue);
		}else{
			CacheUtils.remove(BusinessUtils.CACHE_VENUES, BusinessUtils.CACHE_VENUES_BY_ID + venue.getId());
			venue.preUpdate();
			venuesMapper.updateByPrimaryKeySelective(venue);
		}
	}
	
	@Transactional(readOnly = false)
	public void auditPass(Venues venue) {
		CacheUtils.remove(BusinessUtils.CACHE_VENUES, BusinessUtils.CACHE_VENUES_BY_ID + venue.getId());
		venue.preUpdate();
		venue.preAudit();
		venuesMapper.updateByPrimaryKeySelective(venue);
	}

	public List<WorkTableDTO> getWorkTableVenusInfo() {
		List<WorkTableDTO> list = venuesMapper.selectWorkTableVenusInfo();
		return list;
	}

	/**
	 * 活动关联场地，远程校验
	 * @return
	 */
	public boolean verifyArtists4Event(Integer id , String searchParam) {
		if (id == null || id <=0 || StringUtils.isBlank(searchParam)) {
			return false ;
		}
		
		VenuesExample example = new VenuesExample();
		Criteria  criteria  = example.createCriteria();
		criteria.andIdEqualTo(id) ;
		criteria.andVenueNameLike("%"+searchParam+"%");
		
		List<VenueDTO> venues = venuesMapper.select4AutoCompleteByExample(example);
		if ( venues.size() == 1){
			return true ;
		}
		return false;
	}
	
	
	

}
