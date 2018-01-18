package com.jeeplus.modules.business.dao;

import com.jeeplus.modules.business.entity.UserBalanceRecord;
import com.jeeplus.modules.business.entity.UserBalanceRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserBalanceRecordMapper {
    int countByExample(UserBalanceRecordExample example);

    int deleteByExample(UserBalanceRecordExample example);

    int deleteByPrimaryKey(String recordId);

    int insert(UserBalanceRecord record);

    int insertSelective(UserBalanceRecord record);

    List<UserBalanceRecord> selectByExample(UserBalanceRecordExample example);

    UserBalanceRecord selectByPrimaryKey(String recordId);

    int updateByExampleSelective(@Param("record") UserBalanceRecord record, @Param("example") UserBalanceRecordExample example);

    int updateByExample(@Param("record") UserBalanceRecord record, @Param("example") UserBalanceRecordExample example);

    int updateByPrimaryKeySelective(UserBalanceRecord record);

    int updateByPrimaryKey(UserBalanceRecord record);
}