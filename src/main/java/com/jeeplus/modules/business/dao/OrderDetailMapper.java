package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.dto.Ticket4OrderDTO;
import com.jeeplus.modules.business.dto.TicketVerifyDTO;
import com.jeeplus.modules.business.entity.OrderDetail;
import com.jeeplus.modules.business.entity.OrderDetailExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface OrderDetailMapper {
    int countByExample(OrderDetailExample example);

    int deleteByExample(OrderDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    List<OrderDetail> selectByExample(OrderDetailExample example);
    
    List<Ticket4OrderDTO> selectTicketByOrder(String orderId);
    
    OrderDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderDetail record, @Param("example") OrderDetailExample example);

    int updateByExample(@Param("record") OrderDetail record, @Param("example") OrderDetailExample example);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);
    
    /**
     * 订单核销 查询
     */
    List<TicketVerifyDTO> findTickets(TicketVerifyDTO ticket);

    
    
    
    
    
    
    
    
    
    
    
    
}