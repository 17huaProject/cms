package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.BaseDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.entity.OrderPaid;
import com.jeeplus.modules.business.entity.OrderPaidExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface OrderPaidMapper extends BaseDao {
    int countByExample(OrderPaidExample example);

    int deleteByExample(OrderPaidExample example);

    int deleteByPrimaryKey(String paidId);

    int insert(OrderPaid record);

    int insertSelective(OrderPaid record);

    List<OrderPaid> selectByExample(OrderPaidExample example);

    OrderPaid selectByPrimaryKey(String paidId);

    int updateByExampleSelective(@Param("record") OrderPaid record, @Param("example") OrderPaidExample example);

    int updateByExample(@Param("record") OrderPaid record, @Param("example") OrderPaidExample example);

    int updateByPrimaryKeySelective(OrderPaid record);

    int updateByPrimaryKey(OrderPaid record);
}