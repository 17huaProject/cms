package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.entity.Joinus;
import com.jeeplus.modules.business.entity.JoinusExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface JoinusMapper {
    int countByExample(JoinusExample example);

    int deleteByExample(JoinusExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Joinus record);

    int insertSelective(Joinus record);

    List<Joinus> selectByExample(JoinusExample example);

    Joinus selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Joinus record, @Param("example") JoinusExample example);

    int updateByExample(@Param("record") Joinus record, @Param("example") JoinusExample example);

    int updateByPrimaryKeySelective(Joinus record);

    int updateByPrimaryKey(Joinus record);

	List<Joinus> select4List(Joinus joinus);
}