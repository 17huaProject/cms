package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.BaseDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.dto.VenueDTO;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.ArtistExample;
import com.jeeplus.modules.business.entity.Venues;
import com.jeeplus.modules.business.entity.VenuesExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface VenuesMapper extends BaseDao{
    int countByExample(VenuesExample example);

    int deleteByExample(VenuesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Venues record);

    int insertSelective(Venues record);

    List<Venues> selectByExampleWithBLOBs(VenuesExample example);

    List<Venues> selectByExample(VenuesExample example);

    Venues selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Venues record, @Param("example") VenuesExample example);

    int updateByExampleWithBLOBs(@Param("record") Venues record, @Param("example") VenuesExample example);

    int updateByExample(@Param("record") Venues record, @Param("example") VenuesExample example);

    int updateByPrimaryKeySelective(Venues record);

    int updateByPrimaryKeyWithBLOBs(Venues record);

    int updateByPrimaryKey(Venues record);

	List<VenueDTO> select4AutoCompleteByExample(VenuesExample example);

	List<WorkTableDTO> selectWorkTableVenusInfo();

	List<Venues> select4List(Venues venue);
}