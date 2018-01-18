package com.jeeplus.modules.business.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.web.ActionResponse;
import com.jeeplus.modules.business.bo.OrderItemsExcelBO;
import com.jeeplus.modules.business.bo.OrderSummaryBO;
import com.jeeplus.modules.business.dao.OrderDetailMapper;
import com.jeeplus.modules.business.dao.OrderMapper;
import com.jeeplus.modules.business.dao.OrderPaidMapper;
import com.jeeplus.modules.business.dao.OrderRefundMapper;
import com.jeeplus.modules.business.dto.InvoiceInfoDTO;
import com.jeeplus.modules.business.dto.Ticket4OrderDTO;
import com.jeeplus.modules.business.dto.TicketVerifyDTO;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.Order;
import com.jeeplus.modules.business.entity.OrderDetail;
import com.jeeplus.modules.business.entity.OrderDetailExample;
import com.jeeplus.modules.business.entity.OrderExample;
import com.jeeplus.modules.business.entity.OrderPaid;
import com.jeeplus.modules.business.entity.OrderRefund;
import com.jeeplus.modules.business.utils.DateTimeUtils;
import com.jeeplus.modules.business.utils.FileUtils;

@Service("OrderService")
public class OrderService {
	Logger logger = LoggerFactory.getLogger(getClass()) ;
	
	@Autowired
	OrderMapper orderMapper ;
	@Autowired
	OrderPaidMapper orderPaidMapper ;
	@Autowired
	OrderRefundMapper orderRefundMapper ;
	@Autowired
	OrderDetailMapper orderDetailMapper ;
	
	public List<Order> listAllOrders() {
		List<Order> orders = new ArrayList<Order>();
		return orders;
	}

	public List<Order> listOrders(Order order) {
		List<Order> orders = new ArrayList<Order>();
		
		return orders;
	}
	public List<Order> listOrders(Integer eventId) {
		List<Order> orders = null ;
		if (eventId !=null  && eventId > 0) {
			OrderExample example = new OrderExample();
			example.setOrderByClause("status");
			example.createCriteria()
			.andEventIdEqualTo(eventId)
		//	.andStatusNotEqualTo(CommonConstants.OrderStatus.CLOSED.getCode())
			.andIsDeleteEqualTo(CommonConstants.DELETED_N);
			
			orders = orderMapper.selectByExample(example );
			
		}
		if (orders == null) {
			orders = new ArrayList<Order>();
		}
		
		return orders;
	}

	public Order getOrder(String id) {
		Order order = new Order();
		if (StringUtils.isNotBlank(id)) {
			order = orderMapper.selectByPrimaryKey(id);
		}
		
		return order;
	}

	public OrderPaid getOrderPaid(String paidId) {
		OrderPaid orderPaid = null;
		if (StringUtils.isNotBlank(paidId)) {
			orderPaid = orderPaidMapper.selectByPrimaryKey(paidId);
		}
		if (orderPaid == null) orderPaid = new OrderPaid() ;
		return orderPaid;
	}

	public OrderRefund getOrderRefund(String refundId) {
		OrderRefund orderRefund = null;
		if (StringUtils.isNotBlank(refundId)) {
			orderRefund = orderRefundMapper.selectByPrimaryKey(refundId);
		}
		if(orderRefund == null) orderRefund = new OrderRefund() ;
		return orderRefund;
	}

	public Page<Order> findOrders(Page<Order> page, Order order) {
		order.setPage(page);
		if (StringUtils.isNotBlank(order.getOrderId())) {
			order.setOrderId(order.getOrderId().trim());
		}
		if (StringUtils.isNotBlank(order.getOrderName())) {
			order.setOrderName(order.getOrderName().trim());
		}
		if (StringUtils.isNotBlank(order.getStatus())) {
			order.setStatus(order.getStatus()) ;
		}
		if (StringUtils.isNotBlank(order.getRealname())) {
			order.setRealname(order.getRealname().trim());
		}
		if (StringUtils.isNotBlank(order.getUsePhone())) {
			order.setUsePhone(order.getUsePhone().trim());
		}
		page.setList(orderMapper.select4List(order));
		
		return page;
	}

	@Transactional(readOnly = false)
	public void deleteOrder(String id) {
		Order order = new Order();
		order.setOrderId(id);
		order.preDeleteLogic();
		orderMapper.updateByPrimaryKeySelective(order);
	}

	@Transactional(readOnly = false)
	public void deleteMultiOrder(List<String> ids) {
		Order order = new Order();
		order.preDeleteLogic();
		OrderExample example = new OrderExample();
		example.createCriteria().andOrderIdIn(ids);
		orderMapper.updateByExampleSelective(order, example);
		
	}

	public List<Ticket4OrderDTO> getTicketsByOrder(String orderId) {
		
		List<Ticket4OrderDTO> tickets = orderDetailMapper.selectTicketByOrder(orderId);
		
		if (tickets == null) tickets = new ArrayList<Ticket4OrderDTO>();
		
		return tickets;
	}

	/**
	 *  订单核销 查询
	 */
	public List<TicketVerifyDTO> findTickets(TicketVerifyDTO ticket) {
		if (StringUtils.isNotBlank(ticket.getTicketCode())) ticket.setTicketCode(ticket.getTicketCode().trim());
		if (StringUtils.isNotBlank(ticket.getPhone())) ticket.setPhone(ticket.getPhone().trim());
		
		List<TicketVerifyDTO> tickets = orderDetailMapper.findTickets(ticket) ;
		return tickets;
	}

	/**
	 *  订单核销
	 */
	@Transactional(readOnly = false)
	public int verifyTicket(int id , String orderId) {
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setId(id);
		orderDetail.setUsedFlag(CommonConstants.OrderUsedStatus.USED.getCode());
		orderDetail.setUsedTime(new Date());
		orderDetail.setOperator(orderDetail.getCurrentUser().getId());
		int num = orderDetailMapper.updateByPrimaryKeySelective(orderDetail);
		if (num > 0) {
			Order order = new Order();
			order.setOrderId(orderId);
			order.setStatus(CommonConstants.OrderStatus.PENDING.getCode());
			order.preUpdate();
			num = orderMapper.updateByPrimaryKeySelective(order );
		}
		
		return num ;
	}

	@Transactional(readOnly = false)
	public int verifyMulti(List<Integer> lds , List<String> orderIds) {
		int num = 0;
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setUsedFlag(CommonConstants.OrderUsedStatus.USED.getCode());
		orderDetail.setUsedTime(new Date());
		orderDetail.setOperator(orderDetail.getCurrentUser().getId());
		
		OrderDetailExample example = new OrderDetailExample();
		example.createCriteria().andIdIn(lds);
		
		num = orderDetailMapper.updateByExampleSelective(orderDetail, example );
		if(num >0){
			Order order = new Order();
			order.setStatus(CommonConstants.OrderStatus.PENDING.getCode());
			order.preUpdate();
			OrderExample orderExample = new OrderExample();
			orderExample.createCriteria().andOrderIdIn(orderIds);
			num = orderMapper.updateByExampleSelective(order, orderExample );
		}
		
		return num ;
	}

	public List<WorkTableDTO> getWorkTableOrderInfo() {
		List<WorkTableDTO> list = orderMapper.selectWorkTableOrderInfo() ;
		return list;
	}

	/**
	 * 得到订单汇总信息
	 * @param querySqlTime	查询语句中的时间限定
	 * @return
	 */
	public OrderSummaryBO getOrderSummaryInfo(String querySqlTime){
		OrderSummaryBO orderSummaryInfo = orderMapper.selectOrdersSummary(querySqlTime) ;
		return orderSummaryInfo ;
	}
	
	/**
	 * 生成查询订单明细记录，并生成excle
	 * @param fileNameDescPrefix 文件名说明前缀
	 * @param querySqlTime		 查询语句中的时间限定
	 * @return
	 */
	public String[] getOrderItemsExcel(String fileNameDescPrefix , String querySqlTime){
		String[] fileArr = new String[1] ;
		List<OrderItemsExcelBO> orderItemsExcelBO = orderMapper.selectOrderItems(querySqlTime);
		if (orderItemsExcelBO == null || orderItemsExcelBO.size() == 0) return null ;
		
		String direName = Global.getConfig("email.notify.savePath");
		String fileName = fileNameDescPrefix + "订单明细.xlsx";
		String absoluteFileName = direName + fileName ;
		FileUtils.createFile(direName, fileName);
		try {
			new ExportExcel(fileName, OrderItemsExcelBO.class).setDataList(orderItemsExcelBO).writeFile(absoluteFileName).dispose();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		fileArr[0] = absoluteFileName ;
		return fileArr ;
	}

	/**
	 * 获取月的每一天订单统计信息
	 * @param querySqlTime	查询语句中的时间限定
	 * @return
	 */
	public List<OrderSummaryBO> selectOrderSummayDayOfMonth(String querySqlTime) {
		List<OrderSummaryBO> orderSummayDayOfMonth = orderMapper.selectOrderSummayDayOfMonth(querySqlTime) ;
		return orderSummayDayOfMonth;
	}
	
	
	
	
	
	

}
