package com.jeeplus.modules.business.web;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.ActionResponse;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.User;
import com.jeeplus.modules.business.entity.UserProfile;
import com.jeeplus.modules.business.service.CustomService;
import com.jeeplus.modules.business.service.UserService;

@Controller
@RequestMapping(value = "${adminPath}/wms/user")
public class UserCtrl  extends BaseController{

	@Autowired
	UserService userService;
	@Autowired
	CustomService customService ;
	

	@RequiresPermissions("wms:user:index")
	@RequestMapping("index")
	public String index(){
		return "modules/user/userIndex";
	}
	
	@RequiresPermissions("wms:user:index") 
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute(value="user") User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = userService.listUsers(new Page<User>(request, response), user);
        model.addAttribute("page", page);
		return "modules/user/userList";
	}
	
	
	@RequiresPermissions(value={"wms:user:view"})
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) String id, Model model) {
		User user = userService.getUser(id);
		UserProfile userProfile = userService.getUserProfileByPrimaryKey(id);
		
		model.addAttribute("user", user);
		model.addAttribute("userProfile", userProfile);
		return "modules/user/userForm";
	}
	
	@RequiresPermissions(value={"wms:user:edit"})
	@RequestMapping(value = "editForm")
	public String editForm(@RequestParam(required=false) String id, Model model) {
		User user = userService.getUser(id);
		UserProfile userProfile = userService.getUserProfileByPrimaryKey(id);
		
		model.addAttribute("user", user);
		model.addAttribute("userProfile", userProfile);
		return "modules/user/userEditForm"; 
	}
	
	@RequiresPermissions(value={"wms:user:edit"})
	@RequestMapping(value = "save")
	public String save(@ModelAttribute(value="user") User user, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes) {
		userService.saveUser(user);
		return "redirect:" + adminPath + "/wms/user/list?repage";
	}
	
	
	@RequiresPermissions("wms:user:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam String id, RedirectAttributes redirectAttributes) {
		if(StringUtils.isNotBlank(id)){
			userService.deleteUser(id);
		}
		addMessage(redirectAttributes, "删除顾客信息成功");
		return "redirect:" + adminPath + "/wms/user/list?repage";
	}
	
	/**
	 * 批量删除场所
	 */
	@RequiresPermissions("wms:user:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(ids)){
			List<String> lds = new ArrayList<String>();
			String idArray[] =ids.split(",");
			for(String id : idArray){
				lds.add(id);
			}
			
			userService.deleteMultiUsers(lds);
			
			addMessage(redirectAttributes, "删除顾客信息成功");
		}
		
		return "redirect:" + adminPath + "/wms/user/list?repage";
		
	}
	
	@RequiresPermissions("wms:user:index")
	@RequestMapping("worktable/getUserInfo")
	@ResponseBody
	public List<WorkTableDTO> getUserInfo(){
		List<WorkTableDTO> list = userService.getWorkTableUserInfo();
		return list;
	}
	
	/**
	 * 充值
	 */
	@RequiresPermissions(value={"wms:user:addBalance"})
	@RequestMapping("addBalance") 
	@ResponseBody
	public ActionResponse<BigDecimal> addBalance(@RequestParam(required=true) String userId,
			@RequestParam(required=true) BigDecimal balanceAdd ) {
		User user = null ;
		BigDecimal balance = null ;
		//追加金额为负数
		if (balanceAdd.compareTo(BigDecimal.ZERO) == -1) {
			user = userService.getUser(userId);
			balance = user.getBalance() ;
			//追加金额的正数>=余额
			if (balanceAdd.add(balance).compareTo(BigDecimal.ZERO) == -1) {
				return new ActionResponse<BigDecimal>(4001,"输入金额("+balanceAdd+")的绝对值须小于或等于余额("+balance+")");
			}
		}
		//追加金额为负数，且其正数<=余额；或追加金额为正数
		boolean flag = userService.addBalance(userId , balanceAdd);
		if (!flag) {
			return new ActionResponse<BigDecimal>(4002 , "添加金额失败");
		}
		user = userService.getUser(userId);
		balance = user.getBalance() ;
		return new ActionResponse<BigDecimal>(balance);
	}
	
	/**
	 * 添加积分
	 */
	@RequiresPermissions(value={"wms:user:addScore"})
	@RequestMapping("addScore") 
	@ResponseBody
	public ActionResponse<Integer> addScore(@RequestParam(required=true) String userId,
			@RequestParam(required=true) Integer scoreAdd ) {
		User user = null ;
		Integer score = null ;
		//追加积分为负数
		if (scoreAdd < 0) {
			user = userService.getUser(userId);
			score = user.getScore() ;
			//追加积分的正数>=积分
			if ((score + scoreAdd)<0) { 
				return new ActionResponse<Integer>(4003,"输入积分("+scoreAdd+")的绝对值须小于或等于现有积分("+score+")");
			}
		}
		//追加积分为负数，且其正数<=余额；或追加积分为正数
		boolean flag = userService.addScore(userId , scoreAdd);
		if (!flag) {
			return new ActionResponse<Integer>(4004 , "添加积分失败");
		}
		user = userService.getUser(userId);
		score = user.getScore() ;
		return new ActionResponse<Integer>(score);
	}
	
	
	
	
}
