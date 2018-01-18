package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.entity.EventDetail;
import com.jeeplus.modules.business.entity.EventDetailExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
@MyBatisDao
public interface EventDetailMapper {
    int countByExample(EventDetailExample example);

    int deleteByExample(EventDetailExample example);

    int deleteByPrimaryKey(Integer eventId);

    int insert(EventDetail record);

    int insertSelective(EventDetail record);

    List<EventDetail> selectByExampleWithBLOBs(EventDetailExample example);

    List<EventDetail> selectByExample(EventDetailExample example);

    EventDetail selectByPrimaryKey(Integer eventId);

    int updateByExampleSelective(@Param("record") EventDetail record, @Param("example") EventDetailExample example);

    int updateByExampleWithBLOBs(@Param("record") EventDetail record, @Param("example") EventDetailExample example);

    int updateByExample(@Param("record") EventDetail record, @Param("example") EventDetailExample example);

    int updateByPrimaryKeySelective(EventDetail record);

    int updateByPrimaryKeyWithBLOBs(EventDetail record);

    int updateByPrimaryKey(EventDetail record);
}