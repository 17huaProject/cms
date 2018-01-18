package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.BaseDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.entity.UserProfile;
import com.jeeplus.modules.business.entity.UserProfileExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
@MyBatisDao
public interface UserProfileMapper extends BaseDao {
    int countByExample(UserProfileExample example);

    int deleteByExample(UserProfileExample example);

    int deleteByPrimaryKey(String userId);

    int insert(UserProfile record);

    int insertSelective(UserProfile record);

    List<UserProfile> selectByExampleWithBLOBs(UserProfileExample example);

    List<UserProfile> selectByExample(UserProfileExample example);

    UserProfile selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") UserProfile record, @Param("example") UserProfileExample example);

    int updateByExampleWithBLOBs(@Param("record") UserProfile record, @Param("example") UserProfileExample example);

    int updateByExample(@Param("record") UserProfile record, @Param("example") UserProfileExample example);

    int updateByPrimaryKeySelective(UserProfile record);

    int updateByPrimaryKeyWithBLOBs(UserProfile record);

    int updateByPrimaryKey(UserProfile record);
}