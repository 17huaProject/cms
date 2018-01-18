package com.jeeplus.modules.business.web;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.business.entity.Article;
import com.jeeplus.modules.business.entity.ArticleCat;
import com.jeeplus.modules.business.service.ArticleCatService;
import com.jeeplus.modules.business.service.ArticleService;

/**
 * ArticleCtrl class
 * 
 * @author afanti
 * @date 2017/10/05
 */
@Controller
@RequestMapping(value = "${adminPath}/wms/article")
public class ArticleCtrl   extends BaseController {

	@Autowired
	ArticleService articleService;
	
	@Autowired
	ArticleCatService articleCatService ;
	

	@RequiresPermissions("wms:article:index")
	@RequestMapping("index")
	public String index(){
		return "modules/article/articleIndex";
	}
	
	@RequiresPermissions("wms:article:index")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute(value="article") Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Article> page = articleService.findArticles(new Page<Article>(request, response), article);
		List<ArticleCat> articleCats = articleCatService.findAllList();
        model.addAttribute("page", page);
        model.addAttribute("articleCats", articleCats);
		return "modules/article/articleList";
	}
	
	
	@RequiresPermissions(value={"wms:article:view","wms:article:add","wms:article:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) Integer id, Model model) {
		Article article = articleService.getArticle(id);
		List<ArticleCat> articleCats = articleCatService.findAllList();
		model.addAttribute("article", article);
		model.addAttribute("articleCats", articleCats);
		return "modules/article/articleForm";
	}
	
	@RequiresPermissions(value={"wms:article:view"})
	@RequestMapping(value = "viewForm")
	public String viewForm(@RequestParam(required=false) Integer id, Model model) {
		Article article = articleService.getArticle(id);
		ArticleCat articleCat = articleCatService.getArticleCat(article.getCatId());
		model.addAttribute("article", article);
		model.addAttribute("articleCat", articleCat);
		return "modules/article/artistFormView";
	}
	
	
	@RequiresPermissions(value={"wms:article:add","wms:article:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(@ModelAttribute Article article, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes) {
		articleService.saveArticle(article);
		addMessage(redirectAttributes, "操作成功");
		return "redirect:" + adminPath + "/wms/article/list?repage";
	}
	
	@RequiresPermissions(value={"wms:article:audit"})
	@RequestMapping(value = "formAudit")
	public String formAudit(@RequestParam(required=false) Integer id, Model model) {
		Article article = articleService.getArticle(id);
		model.addAttribute("article", article);
		return "modules/article/articleFormAudit";
	}
	
	
	@RequiresPermissions(value={"wms:article:audit"})
	@RequestMapping(value = "audit")
	public String audit(@ModelAttribute Article article, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes) {
		logger.info(JSON.toJSONString(article));
		
		articleService.auditPass(article);
		addMessage(redirectAttributes, "文章审核成功");
		return "redirect:" + adminPath + "/wms/article/list?repage";
	}
	
	@RequiresPermissions("wms:article:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
		if(id != null && id > 0){
			articleService.deleteArticle(id);
		}
		addMessage(redirectAttributes, "删除文章成功");
		return "redirect:" + adminPath + "/wms/article/list?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequiresPermissions("wms:article:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(ids)){
			List<Integer> lds = new ArrayList<Integer>();
			String[] idArray =ids.split(",");
			for(String id : idArray){
				lds.add(Integer.valueOf(id));
			}
			
			articleService.deleteMultiArticles(lds);
			
			addMessage(redirectAttributes, "删除文章成功");
		}
		
		return "redirect:" + adminPath + "/wms/article/list?repage";
		
	}
}
