package com.jeeplus.modules.business.web;

import java.util.List;
import java.util.Map;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.business.entity.ArticleCat;
import com.jeeplus.modules.business.service.ArticleCatService;

@Controller
@RequestMapping(value = "${adminPath}/wms/articleCat")
public class ArticleCatCtrl extends BaseController {

	@Autowired
	private ArticleCatService articleCatService;
	
	@RequiresPermissions("wms:articleCat:index")
	@RequestMapping(value = {"index"})
	public String index(ArticleCat articleCat, Model model) {
		return "modules/article/articleCatIndex";
	}

	@RequiresPermissions("wms:articleCat:index")
	@RequestMapping(value = {"list"})
	public String list(@ModelAttribute ArticleCat articleCat, Model model) {
		if(articleCat==null){
			 model.addAttribute("list", articleCatService.findAllList());
		}else{
			 model.addAttribute("list", articleCatService.findList(articleCat));
		}
		return "modules/article/articleCatList";
	}
	
	@RequiresPermissions(value={"wms:articleCat:view","wms:articleCat:add","wms:articleCat:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(@ModelAttribute ArticleCat articleCat, Model model) {
		model.addAttribute("articleCat", articleCat);
		return "modules/article/articleCatForm";
	}
	
	@RequiresPermissions(value={"wms:articleCat:add","wms:articleCat:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(@ModelAttribute ArticleCat articleCat, Model model, RedirectAttributes redirectAttributes) {
		articleCatService.save(articleCat);
		
		
		addMessage(redirectAttributes, "保存成功");
		String id = (String) ("0".equals(articleCat.getParentId()) ? "" : articleCat.getParentId());
		return "redirect:" + adminPath + "/modules/article/list?id="+id;
	}
	
	@RequiresPermissions("wms:articleCat:del")
	@RequestMapping(value = "delete")
	public String delete(Integer catId, RedirectAttributes redirectAttributes) {
		if(catId!=null && catId!=0){
			articleCatService.delete(catId);
			addMessage(redirectAttributes, "删除成功");
		}else{
			addMessage(redirectAttributes, "编号未传，删除失败");
		}
		return "redirect:" + adminPath + "/modules/article/list?id="+catId;
	}

	/**
	 * 获取文章类别JSON数据。
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(
			@RequestParam(required=false) String extId, 
			@RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, 
			HttpServletResponse response) {
		
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ArticleCat> list = articleCatService.findAllList();
		for (int i=0; i<list.size(); i++){
			ArticleCat e = list.get(i);
			if (true){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getCatId());
				map.put("pId", e.getParentId());
				map.put("name", e.getCatName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
}
