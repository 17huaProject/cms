package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.dto.VenueSettlementDTO;
import com.jeeplus.modules.business.entity.CommissionSettlement;
import com.jeeplus.modules.business.entity.CommissionSettlementExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface CommissionSettlementMapper {
    int countByExample(CommissionSettlementExample example);

    int deleteByExample(CommissionSettlementExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommissionSettlement record);

    int insertSelective(CommissionSettlement record);

    List<CommissionSettlement> selectByExample(CommissionSettlementExample example);

    CommissionSettlement selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommissionSettlement record, @Param("example") CommissionSettlementExample example);

    int updateByExample(@Param("record") CommissionSettlement record, @Param("example") CommissionSettlementExample example);

    int updateByPrimaryKeySelective(CommissionSettlement record);

    int updateByPrimaryKey(CommissionSettlement record);

	List<VenueSettlementDTO> selectSettlements4Venue(Integer id);
}