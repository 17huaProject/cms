package com.jeeplus.modules.business.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.business.dto.FeedbackDTO;
import com.jeeplus.modules.business.entity.Feedback;
import com.jeeplus.modules.business.entity.User;
import com.jeeplus.modules.business.service.FeedbackService;
import com.jeeplus.modules.business.service.UserService;


@Controller
@RequestMapping(value = "${adminPath}/wms/user/feedback")
public class FeedbackCtrl extends BaseController{
	@Autowired
	FeedbackService feedbackService;
	@Autowired
	UserService userService ;	

	@RequiresPermissions("wms:user:feedback:index")
	@RequestMapping("index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model){
		String status = request.getParameter("status");
		model.addAttribute("status", status) ;
		return "modules/user/feedbackIndex";
	}
	
	@RequiresPermissions("wms:user:feedback:index") 
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute(value="feedback") FeedbackDTO feedback, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Feedback> page = feedbackService.listFeedbacks(new Page<Feedback>(request, response), feedback);
        model.addAttribute("page", page);
		return "modules/user/feedbackList";
	}
	
	/**
	 * 查看
	 */
	@RequiresPermissions(value={"wms:user:feedback:view"})
	@RequestMapping(value = "formView")
	public String formView(@RequestParam(required=false) Integer id, Model model) {
		Feedback feedback = feedbackService.getFeedback(id);
		User user = userService.getUser(feedback.getIssuerId());
		
		model.addAttribute("feedback", feedback);
		model.addAttribute("user", user);
		return "modules/user/feedbackViewForm";
	}
	
	/**
	 * 编辑
	 */
	@RequiresPermissions(value={"wms:user:feedback:edit"})
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) Integer id, Model model) {
		Feedback feedback = feedbackService.getFeedback(id);
		User user = userService.getUser(feedback.getIssuerId());
		
		model.addAttribute("feedback", feedback);
		model.addAttribute("user", user);
		return "modules/user/feedbackForm";
	}
	
	@RequiresPermissions(value={"wms:user:feedback:edit"})
	@RequestMapping(value = "save")
	public String save(@ModelAttribute(value="feedback") Feedback feedback, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes) {
		int num = feedbackService.saveFeedback(feedback);
		if (num >0) {
			addMessage(redirectAttributes, "保存成功");
		}else{
			addMessage(redirectAttributes, "保存失败");
		}
		return "redirect:" + adminPath + "/wms/user/feedback/list?repage";
	}
	
	
	@RequiresPermissions("wms:user:feedback:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
		int num = 0 ;
		if(id != null && id > 0){
			 num = feedbackService.deleteFeedback(id);
		}
		
		if (num >0) addMessage(redirectAttributes, "删除成功");
		else addMessage(redirectAttributes, "删除失败");

		return "redirect:" + adminPath + "/wms/user/feedback/list?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequiresPermissions("wms:user:feedback:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		int num = 0 ;
		if (StringUtils.isNotBlank(ids)){
			List<Integer> lds = new ArrayList<Integer>();
			String idArray[] =ids.split(",");
			for(String id : idArray){
				lds.add(Integer.valueOf(id));
			}
			
			num = feedbackService.deleteMultiFeedbacks(lds);
			
			if (num >0) addMessage(redirectAttributes, "删除成功");
			else addMessage(redirectAttributes, "删除失败");
		}
		
		return "redirect:" + adminPath + "/wms/user/feedback/list?repage";
		
	}
	
	
	
}
