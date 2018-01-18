package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.dto.EventDTO;
import com.jeeplus.modules.business.dto.EventInfoDTO;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.Event;
import com.jeeplus.modules.business.entity.EventExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface EventMapper {
    int countByExample(EventExample example);

    int deleteByExample(EventExample example);

    int deleteByPrimaryKey(Integer id);
    
    int deleteByPrimaryKeyLogic(Event record);

    Event insert(Event record);

    int insertSelective(Event record);

    List<Event> selectByExample(EventExample example);

    Event selectByPrimaryKey(Integer id);

    List<EventDTO> listEventsByExample(EventExample example);
    
    /**
     * 为物料单提供活动列表<br/>
     * 条件：活动状态为：销售中或售完，活动开始前24小时内，且未创建物料单的活动
     * @return
     */
    List<EventDTO> listEvents4MaterialDelivery();
    
    int updateByExampleSelective(@Param("record") Event record, @Param("example") EventExample example);

    int updateByExample(@Param("record") Event record, @Param("example") EventExample example);

    int updateByPrimaryKeySelective(Event record);

    int updateByPrimaryKey(Event record);

	List<WorkTableDTO> selectWorkTableEventInfo();

	/**
	 * 活动详情<br/>
	 * 并获取关联画师、场所、助教 相关信息
	 */
	EventInfoDTO getEventassociatedInfo(Integer id);

	List<EventDTO> listEvents(EventDTO eventDTO);
    
}