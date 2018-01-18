package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.BaseDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.entity.City;
import com.jeeplus.modules.business.entity.CityExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface CityMapper extends BaseDao{
    int countByExample(CityExample example);

    int deleteByExample(CityExample example);

    int deleteByPrimaryKey(String code);

    int insert(City record);

    int insertSelective(City record);

    List<City> selectByExample(CityExample example);

    City selectByPrimaryKey(String code);

    int updateByExampleSelective(@Param("record") City record, @Param("example") CityExample example);

    int updateByExample(@Param("record") City record, @Param("example") CityExample example);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
}