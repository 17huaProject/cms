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
import com.jeeplus.modules.business.entity.Art;
import com.jeeplus.modules.business.entity.GoodCategory;
import com.jeeplus.modules.business.entity.MaterialModel;
import com.jeeplus.modules.business.service.ArtService;
import com.jeeplus.modules.business.service.GoodCategoryService;
import com.jeeplus.modules.business.service.GoodService;
import com.jeeplus.modules.business.service.MaterialModelService;
import com.jeeplus.modules.business.utils.BusinessStringUtils;

@Controller
@RequestMapping(value = "${adminPath}/cms/materialModel")
public class MaterialModelCtrl   extends BaseController {

	@Autowired
	MaterialModelService materialModelService;
	@Autowired
	GoodCategoryService goodCategoryService;
	@Autowired
	GoodService goodService;
	@Autowired
	ArtService artService;

	@RequiresPermissions("cms:materialModel:index")
	@RequestMapping("index") 
	public String index(){
		return "modules/good/materialModelIndex";
	}
	
	@RequiresPermissions("cms:materialModel:index")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute(value="materialModel") MaterialModel materialModel, 
			HttpServletRequest request, 
			HttpServletResponse response, 
			Model model) {
		
		Page<MaterialModel> page = materialModelService.findMaterialModels(new Page<MaterialModel>(request, response), materialModel);
		
        model.addAttribute("page", page);
		return "modules/good/materialModelList";
	}
	
	
	@RequiresPermissions(value={"cms:materialModel:add","cms:materialModel:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) Integer id,
			@RequestParam(required=false) Integer artId , 
			Model model) {
		
		MaterialModel materialModel = materialModelService.getMaterialModel(id);
		
		String goodsInfo = materialModel.getGoodsInfo() ;
		List<GoodDTO> goods = goodService.getGoodsByMaterialModel(goodsInfo);
		
		List<GoodCategory> goodCategorys = goodCategoryService.findGoodCategorys() ;
		
		Map<String, String> goodsInfoMap = null ;
		if (StringUtils.isNoneBlank(goodsInfo)) {
			goodsInfoMap = JSON.parseObject(goodsInfo, Map.class);
		}else{
			goodsInfoMap = new HashMap<String, String>() ;
		}
		
		Art art = new Art();
		if (id != null && id >0) {  //模板模块
			art = artService.getArt(materialModel.getArtId());
		} else if (artId != null && artId >0) {  //作品模块
			materialModel.setArtId(artId);
			art = artService.getArt(artId);
		}
		
		model.addAttribute("art", art);
		model.addAttribute("goods", goods);
		model.addAttribute("goodsInfoMap", goodsInfoMap);
		model.addAttribute("materialModel", materialModel);
		model.addAttribute("goodCategorys", goodCategorys);
		return "modules/good/materialModelForm";
	}
	
	
	@RequiresPermissions(value={"cms:materialModel:view"})
	@RequestMapping(value = "viewForm")
	public String viewForm(@RequestParam(required=false) Integer id, Model model) {
		MaterialModel materialModel = materialModelService.getMaterialModel(id);
		String goodsInfo = materialModel.getGoodsInfo() ;
		List<GoodDTO> goods = goodService.getGoodsByMaterialModel(goodsInfo);
		
		Map<String, String> goodsInfoMap = null ;
		if (StringUtils.isNoneBlank(goodsInfo)) {
			goodsInfoMap = JSON.parseObject(goodsInfo, Map.class);
		}else{
			goodsInfoMap = new HashMap<String, String>() ;
		}
		
		
		model.addAttribute("goods", goods);
		model.addAttribute("goodsInfoMap", goodsInfoMap);
		model.addAttribute("materialModel", materialModel);
		return "modules/good/materialModelFormView";
	}
	
	@RequiresPermissions(value={"cms:materialModel:add","cms:materialModel:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(@ModelAttribute MaterialModel materialModel, 
			HttpServletRequest request, Model model,  
			RedirectAttributes redirectAttributes) {
		
		materialModelService.saveMaterialModel(materialModel);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:" + adminPath + "/cms/materialModel/list?repage";
	}
	
	
	@RequiresPermissions("cms:materialModel:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
		if(id != null && id > 0){
			materialModelService.deleteMaterialModel(id);
		}
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + adminPath + "/cms/materialModel/list?repage";
	}
	
	/**
	 * 批量删除模板
	 */
	@RequiresPermissions("cms:materialModel:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(ids)){
			List<Integer> lds = new ArrayList<Integer>();
			String idArray[] =ids.split(",");
			for(String id : idArray){
				lds.add(Integer.valueOf(id));
			}
			
			materialModelService.deleteMultiMaterialModels(lds);
			
			addMessage(redirectAttributes, "删除成功");
		}
		
		return "redirect:" + adminPath + "/cms/materialModel/list?repage";
		
	}
	
	
	@RequiresPermissions(value={"cms:materialModel:index"})
	@RequestMapping(value = "getMaterialModelInfo")
	@ResponseBody
	public ActionResponse<String> getMaterialModelInfo(@RequestParam Integer id) {
		MaterialModel materialModel = materialModelService.getMaterialModel(id);
		String goodsInfo = materialModel.getGoodsInfo() ;
		List<GoodDTO> goods = goodService.getGoodsByMaterialModel(goodsInfo);
		
		List<Map<String, String>> goodsInfoMaps = BusinessStringUtils.parseJSONStr(goodsInfo) ;
		
		String materialJson = materialModelService.genMaterialJson(goods, goodsInfoMaps);
		
		ActionResponse<String> response = null  ;
		
		if (StringUtils.isNotBlank(materialJson)) {
			response = new ActionResponse<String>(materialJson) ;
		} else {
			response = new ActionResponse<String>(1108 , "查询失败") ;
		}

		return response ;
	}
	
	
	
}
