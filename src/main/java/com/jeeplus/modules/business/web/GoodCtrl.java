package com.jeeplus.modules.business.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeeplus.modules.business.dto.GoodDTO;
import com.jeeplus.modules.business.entity.Good;
import com.jeeplus.modules.business.entity.GoodCategory;
import com.jeeplus.modules.business.service.GoodCategoryService;
import com.jeeplus.modules.business.service.GoodService;

@Controller
@RequestMapping(value = "${adminPath}/cms/good")
public class GoodCtrl   extends BaseController {

	@Autowired
	GoodService goodService;
	@Autowired
	GoodCategoryService goodCategoryService;
	
	private Good goodQueryParamsCache ;  //查询条参数缓存
	

	@RequiresPermissions("cms:good:index")
	@RequestMapping("index")
	public String index(){
		return "modules/good/goodIndex";
	}
	
	@RequiresPermissions("cms:good:index")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute(value="good") Good good, 
			HttpServletRequest request, 
			HttpServletResponse response, 
			Model model) {
		
		Page<Good> page = goodService.findGoods(new Page<Good>(request, response), good);
		List<GoodCategory>  goodCategorys = goodCategoryService.findGoodCategorys() ;
		
		goodQueryParamsCache = good ;
		
		model.addAttribute("good", good);
        model.addAttribute("page", page);
        model.addAttribute("goodCategorys", goodCategorys);
		return "modules/good/goodList";
	}
	
	
	@SuppressWarnings("unchecked")
	@RequiresPermissions(value={"cms:good:add","cms:good:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) Integer id, Model model) {
		Good good = goodService.getGood(id);
		
		List<GoodCategory> categorys= goodCategoryService.findGoodCategorys();
		
		Map<String , String> attrMap = null ;
		
		if(StringUtils.isNotBlank(good.getAttribute())){
			attrMap = JSON.parseObject(good.getAttribute(), Map.class) ;
		} else {
			attrMap = new HashMap<String, String>() ;
		}
		
		model.addAttribute("good", good);
		model.addAttribute("categorys", categorys);
		model.addAttribute("attrMap", attrMap);
		return "modules/good/goodForm";
	}
	
	
	@RequiresPermissions(value={"cms:good:view"})
	@RequestMapping(value = "viewForm")
	public String viewForm(@RequestParam(required=false) Integer id, Model model) {
		Good good = goodService.getGood(id);
		Map<String , String> attrMap = null ;
		
		if(StringUtils.isNotBlank(good.getAttribute())){
			attrMap = JSON.parseObject(good.getAttribute(), Map.class) ;
		} else {
			attrMap = new HashMap<String, String>() ;
		}
		
		GoodCategory  goodCategory  = goodCategoryService.getGoodCategory(good.getCategoryId());
		String categoryName = goodCategory.getName() ;
		
		model.addAttribute("attrMap", attrMap);
		model.addAttribute("good", good);
		model.addAttribute("categoryName", categoryName);
		return "modules/good/goodFormView";
	}
	
	@RequiresPermissions(value={"cms:good:add","cms:good:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(@ModelAttribute Good good, 
			HttpServletRequest request, Model model,  
			RedirectAttributes redirectAttributes) {
		
		goodService.saveGood(good);
		addMessage(redirectAttributes, "操作成功");
		redirectAttributes.addFlashAttribute("good", goodQueryParamsCache) ;
		return "redirect:" + adminPath + "/cms/good/list?repage";
	}
	
	
	@RequiresPermissions("cms:good:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
		if(id != null && id > 0){
			goodService.deleteGood(id);
		}
		addMessage(redirectAttributes, "删除成功");
		redirectAttributes.addFlashAttribute("good", goodQueryParamsCache) ;
		return "redirect:" + adminPath + "/cms/good/list?repage";
	}
	
	/**
	 * 批量删除场所
	 */
	@RequiresPermissions("cms:good:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(ids)){
			List<Integer> lds = new ArrayList<Integer>();
			String idArray[] =ids.split(",");
			for(String id : idArray){
				lds.add(Integer.valueOf(id));
			}
			
			goodService.deleteMultiGoods(lds);
			
			addMessage(redirectAttributes, "删除成功");
		}
		redirectAttributes.addFlashAttribute("good", goodQueryParamsCache) ;
		
		return "redirect:" + adminPath + "/cms/good/list?repage";
		
	}
	
	
	
	@RequiresPermissions("cms:good:index")
	@RequestMapping("listGoodsByCategoryId")
	@ResponseBody
	public ActionResponse<List<GoodDTO>> listGoodsByCategoryId(Integer categoryId ){
		if (categoryId == null || categoryId <0) return new  ActionResponse<List<GoodDTO>>() ;
		
		List<GoodDTO> goodDTOList = goodService.listGoodsByCategoryId(categoryId);
		
		if (goodDTOList == null) {
			return new ActionResponse(1108 , "查询失败");
		}
		return new ActionResponse(goodDTOList);
	}
	
	
}
