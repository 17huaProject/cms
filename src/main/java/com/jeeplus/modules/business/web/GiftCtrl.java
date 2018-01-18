package com.jeeplus.modules.business.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.search.expression.And;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.business.entity.Gift;
import com.jeeplus.modules.business.entity.OrderPaid;
import com.jeeplus.modules.business.entity.OrderRefund;
import com.jeeplus.modules.business.entity.User;
import com.jeeplus.modules.business.service.GiftService;
import com.jeeplus.modules.business.service.OrderService;
import com.jeeplus.modules.business.service.UserService;

@Controller
@RequestMapping(value = "${adminPath}/cms/gift")
public class GiftCtrl   extends BaseController {

	@Autowired
	GiftService giftService;
	@Autowired
	OrderService orderService ;
	@Autowired
	UserService userService;

	@RequiresPermissions("cms:gift:index")
	@RequestMapping("index")
	public String index(){
		return "modules/gift/giftIndex";
	}
	
	@RequiresPermissions("cms:gift:index")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute(value="gift") Gift gift, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Gift> page = giftService.findGifts(new Page<Gift>(request, response), gift);
        model.addAttribute("page", page);
		return "modules/gift/giftList";
	}
	
	
	@RequiresPermissions(value={"cms:gift:edit"})
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) String id, Model model) {
		logger.info(String.valueOf(id));
		Gift gift = giftService.getGift(id);
		model.addAttribute("gift", gift);
		return "modules/gift/giftForm";
	}
	
	
	@RequiresPermissions(value={"cms:gift:view"})
	@RequestMapping(value = "viewForm")
	public String viewForm(@RequestParam(required=false) String id, Model model) {
		Gift gift = giftService.getGift(id);
		OrderPaid orderPaid = null ;
		OrderRefund getOrderRefund = null ;
		
		//：购买者信息
		User user = userService.getUser(gift.getUserId());
		User receiver = userService.getUser(gift.getReceiverId());
		//：~
		
		//：支付、退款
		String giftStatus = gift.getGiftStatus() ;
		if (StringUtils.isNotBlank(giftStatus)) {
			giftStatus = giftStatus.toUpperCase() ;
			if ( giftStatus.equals(CommonConstants.GiftStatus.PAID) && StringUtils.isNotBlank(gift.getPaidId())) {
				orderPaid = orderService.getOrderPaid(gift.getPaidId()) ;
			}
			if ( giftStatus.equals(CommonConstants.GiftStatus.REFUND) && StringUtils.isNotBlank(gift.getRefundId()) ) {
				getOrderRefund = orderService.getOrderRefund(gift.getRefundId()) ;
			}
		}
		if (orderPaid == null) orderPaid = new OrderPaid() ;
		if (getOrderRefund == null)  getOrderRefund = new OrderRefund()  ;
		//：~
		
		model.addAttribute("gift", gift);
		model.addAttribute("user", user);
		model.addAttribute("receiver", receiver);
		model.addAttribute("orderPaid", orderPaid);
		model.addAttribute("getOrderRefund", getOrderRefund);
		
		return "modules/gift/giftFormView";
	}
	
	
	
	@RequiresPermissions(value={"cms:gift:edit"})
	@RequestMapping(value = "save")
	public String save(@ModelAttribute Gift gift, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes) {
		int num = giftService.saveGift(gift);
		if (num > 0) {
			addMessage(redirectAttributes, "保存成功");
		}else{
			addMessage(redirectAttributes, "保存失败");
		}
		return "redirect:" + adminPath + "/cms/gift/list?repage";
	}
	
	
	@RequiresPermissions("cms:gift:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam String id, RedirectAttributes redirectAttributes) {
		if(StringUtils.isNotBlank(id)){
			int num = giftService.deleteGift(id);
			if (num > 0) {
				addMessage(redirectAttributes, "删除成功");
			}else{
				addMessage(redirectAttributes, "删除失败");
			}
		}
		return "redirect:" + adminPath + "/cms/gift/list?repage";
	}
	
	/**
	 * 批量删除礼品卡
	 */
	@RequiresPermissions("cms:gift:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(ids)){
			List<String> lds = new ArrayList<String>();
			String idArray[] =ids.split(",");
			for(String id : idArray){
				lds.add(id);
			}
			
			int num = giftService.deleteMultiGifts(lds);
			if (num > 0) {
				addMessage(redirectAttributes, "删除成功");
			}else{
				addMessage(redirectAttributes, "删除失败");
			}
		}
		
		return "redirect:" + adminPath + "/cms/gift/list?repage";
		
	}
	
	
	
	
	
	
	
	
}
