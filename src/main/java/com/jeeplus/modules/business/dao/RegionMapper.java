package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.BaseDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.dto.ProvinceCityDTO;
import com.jeeplus.modules.business.entity.Region;
import com.jeeplus.modules.business.entity.RegionExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface RegionMapper extends BaseDao{
    int countByExample(RegionExample example);

    int deleteByExample(RegionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Region record);

    int insertSelective(Region record);

    List<Region> selectByExample(RegionExample example);
    
    List<Region> selectDistrictsByParentId(String parentId);

    Region selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Region record, @Param("example") RegionExample example);

    int updateByExample(@Param("record") Region record, @Param("example") RegionExample example);

    int updateByPrimaryKeySelective(Region record);

    int updateByPrimaryKey(Region record);
    
    ProvinceCityDTO selectProvinceCityByCityCode(String cityCode);
}