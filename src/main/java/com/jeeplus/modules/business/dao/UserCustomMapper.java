package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.entity.UserCustom;
import com.jeeplus.modules.business.entity.UserCustomExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface UserCustomMapper {
    int countByExample(UserCustomExample example);

    int deleteByExample(UserCustomExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserCustom record);

    int insertSelective(UserCustom record);

    List<UserCustom> selectByExample(UserCustomExample example);

    UserCustom selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserCustom record, @Param("example") UserCustomExample example);

    int updateByExample(@Param("record") UserCustom record, @Param("example") UserCustomExample example);

    int updateByPrimaryKeySelective(UserCustom record);

    int updateByPrimaryKey(UserCustom record);

	List<UserCustom> select4List(UserCustom custom);

	
}