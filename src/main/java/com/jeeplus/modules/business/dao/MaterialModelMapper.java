package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.entity.MaterialModel;
import com.jeeplus.modules.business.entity.MaterialModelExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface MaterialModelMapper {
    int countByExample(MaterialModelExample example);

    int deleteByExample(MaterialModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MaterialModel record);

    int insertSelective(MaterialModel record);

    List<MaterialModel> selectByExample(MaterialModelExample example);

    MaterialModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MaterialModel record, @Param("example") MaterialModelExample example);

    int updateByExample(@Param("record") MaterialModel record, @Param("example") MaterialModelExample example);

    int updateByPrimaryKeySelective(MaterialModel record);

    int updateByPrimaryKey(MaterialModel record);

	List<MaterialModel> select4List(MaterialModel materialModel);
}