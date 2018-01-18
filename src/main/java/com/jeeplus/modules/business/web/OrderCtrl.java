package com.jeeplus.modules.business.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.ActionResponse;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.business.dto.EventInfoDTO;
import com.jeeplus.modules.business.dto.Ticket4OrderDTO;
import com.jeeplus.modules.business.dto.TicketVerifyDTO;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.Event;
import com.jeeplus.modules.business.entity.Order;
import com.jeeplus.modules.business.entity.OrderPaid;
import com.jeeplus.modules.business.entity.OrderRefund;
import com.jeeplus.modules.business.entity.User;
import com.jeeplus.modules.business.service.EventService;
import com.jeeplus.modules.business.service.OrderService;
import com.jeeplus.modules.business.service.UserService;
import com.jeeplus.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/wms/order")
public class OrderCtrl   extends BaseController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserService userService ;
	@Autowired
	private EventService eventService ;
	

	@RequiresPermissions("wms:order:index")
	@RequestMapping("index")
	public String index(){
		return "modules/order/orderIndex";
	}
	
	@RequiresPermissions("wms:order:index")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute(value="order") Order order, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Order> page = orderService.findOrders(new Page<Order>(request, response), order);
		model.addAttribute("page", page);
		return "modules/order/orderList";
	}
	
	@RequiresPermissions("wms:order:index")
	@RequestMapping(value = {"listByEvent"})
	public String listByEvent(@RequestParam String eventId, HttpServletRequest request, HttpServletResponse response, Model model) {
		EventInfoDTO event = new EventInfoDTO();
		List<Order> orders = new ArrayList<Order>();
		if (StringUtils.isNotBlank(eventId)) {
			Integer eventIdTemp = Integer.parseInt(eventId) ;
			event = eventService.getEventassociatedInfo(eventIdTemp) ;
			orders = orderService.listOrders(eventIdTemp);
		}
		String orderStatus = "" ;
		model.addAttribute("orderStatus" , orderStatus);
		model.addAttribute("event" , event);
		model.addAttribute("orders" , orders);
		
		return "modules/order/orderList4Event";
	}
	
	
	@RequiresPermissions(value={"wms:order:view"})
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) String id, Model model) {
		Order order = orderService.getOrder(id);
		String orderStatus = order.getStatus() ;
		String paidId = order.getPaidId() ;
		String refundId = order.getRefundId() ;
		String userId = order.getUserId();
		
		OrderPaid orderPaid = new OrderPaid() ;
		if (!CommonConstants.OrderStatus.UNPAID.getCode().equals(orderStatus) &&
			!CommonConstants.OrderStatus.CLOSED.getCode().equals(orderStatus)	) {
			orderPaid = orderService.getOrderPaid(paidId);
		}
		
		OrderRefund orderRefund = new OrderRefund();
		if (CommonConstants.OrderStatus.REFUND.getCode().equals(orderStatus)) {
			orderRefund = orderService.getOrderRefund(refundId);
		}
		
		User user = new User() ;
		if (StringUtils.isNotBlank(userId)) {
			user = userService.getUser(userId);
		}
		
		Byte paidStatus = orderPaid.getStatus() ;
		List<Ticket4OrderDTO> tickets = new ArrayList<Ticket4OrderDTO>() ;
		if (paidStatus != null && CommonConstants.OrderPaidStatus.SUCCESS.getCode() == paidStatus) {
			tickets = orderService.getTicketsByOrder(order.getOrderId());
		}
		
		
		model.addAttribute("order", order);
		model.addAttribute("orderPaid", orderPaid);
		model.addAttribute("orderRefund", orderRefund);
		model.addAttribute("user", user);
		model.addAttribute("tickets", tickets);
		return "modules/order/orderForm";
	}
	
	@RequiresPermissions("wms:order:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam String id, RedirectAttributes redirectAttributes) {
		if(StringUtils.isNotBlank(id)){
			orderService.deleteOrder(id);
		}
		addMessage(redirectAttributes, "删除订单成功");
		return "redirect:" + adminPath + "/wms/order/list?repage";
	}
	
	/**
	 * 批量删除场所
	 */
	@RequiresPermissions("wms:order:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(ids)){
			List<String> lds = new ArrayList<String>();
			String idArray[] =ids.split(",");
			for(String id : idArray){
				lds.add(id);
			}
			
			orderService.deleteMultiOrder(lds);
			
			addMessage(redirectAttributes, "删除订单成功");
		}
		
		return "redirect:" + adminPath + "/wms/order/list?repage";
		
	}
	
	/**
	 * 订单核销
	 */
	
	@RequiresPermissions("wms:ticket:index")
	@RequestMapping("ticketIndex")
	public String ticketIndex(){
		return "modules/order/ticketIndex";
	}
	
	
	@RequiresPermissions("wms:ticket:index")
	@RequestMapping(value = {"listTickets", ""})
	public String listTickets(@ModelAttribute(value="ticket") TicketVerifyDTO ticket ,
			HttpServletRequest request, 
			HttpServletResponse response,
			Model model) {
		String message = "";
		List<TicketVerifyDTO> tickets = null;
		Map<Integer, String> eventList = new HashMap<Integer, String>();
		
		com.jeeplus.modules.sys.entity.User currentUser = UserUtils.getUser();
		String userId = currentUser.getId() ;
		
		List<Event> events = null ;
		
		if(StringUtils.isNotBlank(userId)){
			events = eventService.listEventsByArtistIdAssistantId(userId);
		}else{
			logger.info("用户id为空！无法查到活动票");
			 message = "登录超时,请重新登录！" ;
		}
		if(events == null || events.size()==0){
			if(events == null) events = new ArrayList<Event>();
			message = "您暂无可核销的活动票,您可能不是画师或助教！";
		}else{ //两级：1活动，2票
			for (Event event : events) {
				eventList.put(event.getId() , event.getEventName());
			}
			
			if (ticket != null) {
				tickets = orderService.findTickets(ticket);
				if(tickets == null || tickets.size()==0){
					if(tickets == null) tickets = new ArrayList<TicketVerifyDTO>();
					if(ticket.getEventId() == -1) message = "请选择活动并查询"; else message = "该活动暂无可核销的活动票";
				}
			}
		}
		
		model.addAttribute("eventList", eventList);
		model.addAttribute("tickets", tickets);
		model.addAttribute("message",  message);
		
		return "modules/order/ticketList";
	}
	
	@RequiresPermissions("wms:ticket:verify")
	@RequestMapping(value = "verify")
	@ResponseBody
	public ActionResponse<String> verify(@RequestParam String id, @RequestParam String orderId) {
		ActionResponse<String> actionResponse = null ;
		if(StringUtils.isNotBlank(id)){
			int num = orderService.verifyTicket(Integer.valueOf(id) , orderId);
			
			if (num > 0) {
				actionResponse = new ActionResponse<String>(id);
			}else{
				actionResponse = new ActionResponse<String>(1105,"核销失败");
			}
		}
		return actionResponse;
	}
	
	@RequiresPermissions("wms:ticket:verify")
	@RequestMapping(value = "verifyMulti")
	public String verifyMulti(@RequestParam String ids, @RequestParam String orderIds,  RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(orderIds)){
			List<Integer> lds = new ArrayList<Integer>();
			String idArray[] =ids.split(",");
			for(String id : idArray){
				lds.add(Integer.valueOf(id));
			}
			List<String> orderIdsParam = new ArrayList<String>();
			String orderIdArray[] = orderIds.split(",");
			for(String orderId : orderIdArray){
				orderIdsParam.add(orderId);
			}
			
			int num = orderService.verifyMulti(lds , orderIdsParam);
			
			if (num > 0) {
				addMessage(redirectAttributes, "核销成功");
			}else{
				addMessage(redirectAttributes, "核销失败，请重新核销");
			}
		}
		return "redirect:" + adminPath + "/wms/order/listTickets?repage";
	}
	
	
	
	@RequiresPermissions("wms:order:index")
	@RequestMapping("worktable/getOrderInfo")
	@ResponseBody
	public List<WorkTableDTO> getOrderInfo(){
		List<WorkTableDTO> list = orderService.getWorkTableOrderInfo();
		return list;
	}
	
	
	
	
	
	
	
	
	
}
