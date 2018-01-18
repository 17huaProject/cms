package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.entity.GoodCategory;
import com.jeeplus.modules.business.entity.GoodCategoryExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface GoodCategoryMapper {
    int countByExample(GoodCategoryExample example);

    int deleteByExample(GoodCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GoodCategory record);

    int insertSelective(GoodCategory record);

    List<GoodCategory> selectByExample(GoodCategoryExample example);

    GoodCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodCategory record, @Param("example") GoodCategoryExample example);

    int updateByExample(@Param("record") GoodCategory record, @Param("example") GoodCategoryExample example);

    int updateByPrimaryKeySelective(GoodCategory record);

    int updateByPrimaryKey(GoodCategory record);

	List<GoodCategory> select4List(GoodCategory goodCategory);
}