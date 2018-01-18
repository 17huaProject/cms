package com.jeeplus.modules.business.web;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.business.dto.EventDTO;
import com.jeeplus.modules.business.dto.VenueSettlementDTO;
import com.jeeplus.modules.business.entity.CommissionSettlement;
import com.jeeplus.modules.business.entity.Venues;
import com.jeeplus.modules.business.service.CommissionSettleService;
import com.jeeplus.modules.business.service.EventService;
import com.jeeplus.modules.business.service.VenuesService;


/**
 * 活动  佣金结算
 * */
@Controller
@RequestMapping(value = "${adminPath}/wms/settle")
public class CommissionSettleCtrl  extends BaseController {
	@Autowired
	EventService eventService ;
	@Autowired
	VenuesService venuesService ;
	@Autowired
	CommissionSettleService commissionSettleService ;
	
	
	/**
	 * 场所结算  结算页 
	 * */
	@RequiresPermissions("wms:commissionSettle:save")
	@RequestMapping("venueSettleForm")
	public String venueSettleForm(@RequestParam(required=true) Integer id, Model model, RedirectAttributes redirectAttributes) {
		
		EventDTO event = eventService.getEvent(id) ;
		
		if (!CommonConstants.EventStatus.FINISH.getCode().equals(event.getEventStatus())) {
			addMessage(redirectAttributes, "活动结束后方可结算");
			return "redirect:" + adminPath + "/wms/event/list?repage";
		}
		
		Venues  venue = venuesService.getVenue(event.getVenueId());
		
		model.addAttribute("event", event) ;
		model.addAttribute("venue", venue) ;
		return "modules/venues/settleForm";
	}
	
	/**
	 * 场所结算  结算
	 * */
	@RequiresPermissions("wms:commissionSettle:save")
	@RequestMapping("venueSettle")
	public String venueSettle(@ModelAttribute(value="commissionSettle") CommissionSettlement commissionSettle, 
			HttpServletRequest request, 
			Model model,  
			RedirectAttributes redirectAttributes) {
		
		boolean flag = commissionSettleService.saveCommissionSettle(commissionSettle);
		if (flag) {
			addMessage(redirectAttributes, "场所结算成功");
		} else {
			addMessage(redirectAttributes, "场所结算失败");
		}
		
		return "redirect:" + adminPath + "/wms/event/list?repage";
	}
	
	/**
	 * 场所结算  结算页 
	 * */
	@RequiresPermissions("wms:commissionSettle:view")
	@RequestMapping("listSettlements4Venue") 
	public String listSettlements4Venue(@RequestParam(required=true) Integer id, Model model) {
		
		List<VenueSettlementDTO> settlements = commissionSettleService.listSettlements4Venue(id);
		
		BigDecimal feeSum = new BigDecimal(0) ;
		if (settlements.size() > 0) {
			for (VenueSettlementDTO settlement : settlements) {
				feeSum = feeSum.add(settlement.getFee()) ;
			}
		}
		
		model.addAttribute("feeSum", feeSum) ;
		model.addAttribute("settlements", settlements) ;
		return "modules/venues/settleList";
	}
	
	
}
