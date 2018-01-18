package com.jeeplus.modules.business.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.ActionResponse;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.business.entity.GoodCategory;
import com.jeeplus.modules.business.service.GoodCategoryService;
import com.jeeplus.modules.sys.utils.DictUtils;

@Controller
@RequestMapping(value = "${adminPath}/cms/goodCategory")
public class GoodCategoryCtrl   extends BaseController {

	@Autowired
	GoodCategoryService goodCategoryService;
	

	@RequiresPermissions("cms:goodCategory:index")
	@RequestMapping("index")
	public String index(){
		return "modules/good/goodCategoryIndex";
	}
	
	@RequiresPermissions("cms:goodCategory:index")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute(value="goodCategory") GoodCategory goodCategory, 
			HttpServletRequest request, 
			HttpServletResponse response, 
			Model model) {
		
		Page<GoodCategory> page = goodCategoryService.findGoodCategorys(new Page<GoodCategory>(request, response), goodCategory);
        model.addAttribute("page", page);
		return "modules/good/goodCategoryList";
	}
	
	
	@RequiresPermissions(value={"cms:goodCategory:add","cms:goodCategory:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) Integer id, Model model) {
		GoodCategory goodCategory = goodCategoryService.getGoodCategory(id);
		goodCategory.setCateAttribs(StringUtils.split(goodCategory.getCateAttrib(), ","));
		model.addAttribute("goodCategory", goodCategory);
		return "modules/good/goodCategoryForm";
	}
	
	
	@RequiresPermissions(value={"cms:goodCategory:view"})
	@RequestMapping(value = "viewForm")
	public String viewForm(@RequestParam(required=false) Integer id, Model model) {
		GoodCategory goodCategory = goodCategoryService.getGoodCategory(id);
		
		goodCategory.setCateAttribs(StringUtils.split(goodCategory.getCateAttrib(), ","));
		model.addAttribute("goodCategory", goodCategory);
		return "modules/good/goodCategoryFormView";
	}
	
	@RequiresPermissions(value={"cms:goodCategory:add","cms:goodCategory:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(@ModelAttribute GoodCategory goodCategory, 
			HttpServletRequest request, Model model,  
			RedirectAttributes redirectAttributes) {
		
		goodCategoryService.saveGoodCategory(goodCategory);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:" + adminPath + "/cms/goodCategory/list?repage";
	}
	
	
	@RequiresPermissions("cms:goodCategory:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
		if(id != null && id > 0){
			goodCategoryService.deleteGoodCategory(id);
		}
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + adminPath + "/cms/goodCategory/list?repage";
	}
	
	/**
	 * 批量删除场所
	 */
	@RequiresPermissions("cms:goodCategory:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(ids)){
			List<Integer> lds = new ArrayList<Integer>();
			String idArray[] =ids.split(",");
			for(String id : idArray){
				lds.add(Integer.valueOf(id));
			}
			
			goodCategoryService.deleteMultiGoodCategorys(lds);
			
			addMessage(redirectAttributes, "删除成功");
		}
		
		return "redirect:" + adminPath + "/cms/goodCategory/list?repage";
		
	}
	
	
	@RequiresPermissions("cms:goodCategory:view")
	@RequestMapping(value = "getAttr")
	@ResponseBody
	public ActionResponse<String> getAttr(@RequestParam Integer id) {
		ActionResponse<String> response = null ;
		if(id != null && id > 0){
			GoodCategory goodCategory = goodCategoryService.getGoodCategory(id);
			String cateAttrib = goodCategory.getCateAttrib() ;
			if (StringUtils.isNotBlank(cateAttrib)) {
				String[] attrArray = cateAttrib.split(",") ;
				HashedMap map = new HashedMap();
				for (String attr : attrArray) {
					map.put(attr, DictUtils.getDictLabel(attr, "goodCategory_attr", "")) ;
				}
				response = new ActionResponse<String>(JSON.toJSONString(map)) ;
			} else {  
				response = new ActionResponse<String>(1108,"查询失败");
			}
		}
		return response;
	}
	
	
	
	
}
