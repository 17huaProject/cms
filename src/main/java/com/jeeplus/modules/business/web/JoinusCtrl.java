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
import com.jeeplus.modules.business.entity.Joinus;
import com.jeeplus.modules.business.service.JoinusService;


@Controller
@RequestMapping(value = "${adminPath}/cms/joinus")
public class JoinusCtrl extends BaseController{
	@Autowired
	JoinusService joinusService;

	@RequiresPermissions("cms:joinus:index")
	@RequestMapping("index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model){
		String status = request.getParameter("status");
		model.addAttribute("status", status) ;
		return "modules/joinus/joinusIndex";
	}
	
	@RequiresPermissions("cms:joinus:index") 
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute(value="joinus") Joinus joinus, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Joinus> page = joinusService.listJoinuses(new Page<Joinus>(request, response), joinus);
        model.addAttribute("page", page);
		return "modules/joinus/joinusList";
	}
	
	/**
	 * 查看
	 */
	@RequiresPermissions(value={"cms:joinus:view"})
	@RequestMapping(value = "formView")
	public String formView(@RequestParam(required=false) Integer id, Model model) {
		Joinus joinus = joinusService.getJoinus(id);
		
		model.addAttribute("joinus", joinus);
		return "modules/joinus/joinusViewForm";
	}
	
	/**
	 * 编辑
	 */
	@RequiresPermissions(value={"cms:joinus:edit"})
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) Integer id, Model model) {
		Joinus joinus = joinusService.getJoinus(id);
		
		model.addAttribute("joinus", joinus);
		return "modules/joinus/joinusForm";
	}
	
	@RequiresPermissions(value={"cms:joinus:edit"})
	@RequestMapping(value = "save")
	public String save(@ModelAttribute(value="joinus") Joinus joinus, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes) {
		int num = joinusService.saveJoinus(joinus);
		if (num >0) {
			addMessage(redirectAttributes, "保存成功");
		}else{
			addMessage(redirectAttributes, "保存失败");
		}
		return "redirect:" + adminPath + "/cms/joinus/list?repage";
	}
	
	
	@RequiresPermissions("cms:joinus:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
		int num = 0 ;
		if(id != null && id > 0){
			 num = joinusService.deleteJoinus(id);
		}
		
		if (num >0) addMessage(redirectAttributes, "删除成功");
		else addMessage(redirectAttributes, "删除失败");

		return "redirect:" + adminPath + "/cms/joinus/list?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequiresPermissions("cms:joinus:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		int num = 0 ;
		if (StringUtils.isNotBlank(ids)){
			List<Integer> lds = new ArrayList<Integer>();
			String[] idArray =ids.split(",");
			for(String id : idArray){
				lds.add(Integer.valueOf(id));
			}
			
			num = joinusService.deleteMultiJoinuses(lds);
			
			if (num >0) addMessage(redirectAttributes, "删除成功");
			else addMessage(redirectAttributes, "删除失败");
		}
		
		return "redirect:" + adminPath + "/cms/joinus/list?repage";
		
	}
	
	
	
}
