package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.BaseDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.bo.OrderItemsExcelBO;
import com.jeeplus.modules.business.bo.OrderSummaryBO;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.Order;
import com.jeeplus.modules.business.entity.OrderExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
@MyBatisDao
public interface OrderMapper extends BaseDao {
    int countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(String orderId);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

	List<WorkTableDTO> selectWorkTableOrderInfo();

	List<Order> select4List(Order order);

	OrderSummaryBO selectOrdersSummary(@Param("querySqlTime") String querySqlTime);

	List<OrderItemsExcelBO> selectOrderItems(@Param("querySqlTime") String querySqlTime);

	List<OrderSummaryBO> selectOrderSummayDayOfMonth(@Param("querySqlTime") String querySqlTime);
}