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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.ActionResponse;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.business.dto.ArtDTO;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.Art;
import com.jeeplus.modules.business.service.ArtService;

@Controller
@RequestMapping(value = "${adminPath}/wms/art")
public class ArtCtrl   extends BaseController {

	@Autowired
	ArtService artService;
	
	private Art artQueryParamsCache ;  //查询条参数缓存

	@RequiresPermissions("wms:art:index")
	@RequestMapping("index")
	public String index(){
		return "modules/art/artIndex";
	}
	
	@RequiresPermissions("wms:art:index")
	@RequestMapping("artList4Event")
	public String artList4Event(@ModelAttribute(value="art") Art art, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<Art> page = artService.findArts(new Page<Art>(request, response), art);
        model.addAttribute("page", page);
		return "modules/art/artList4Event";
	}
	
	@RequiresPermissions("wms:art:index")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute(value="art") Art art, HttpServletRequest request, HttpServletResponse response, Model model) {
		artQueryParamsCache = art ;
		Page<Art> page = artService.findArts(new Page<Art>(request, response), art);
        model.addAttribute("page", page);
		return "modules/art/artList";
	}
	
	
	@RequiresPermissions(value={"wms:art:add","wms:art:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) Integer id, Model model) {
		logger.info(String.valueOf(id));
		Art art = artService.getArt(id);
		model.addAttribute("art", art);
		return "modules/art/artForm";
	}
	
	
	@RequiresPermissions(value={"wms:art:view"})
	@RequestMapping(value = "viewForm")
	public String viewForm(@RequestParam(required=false) Integer id, Model model) {
		logger.info(String.valueOf(id));
		Art art = artService.getArt(id);
		model.addAttribute("art", art);
		return "modules/art/artFormView";
	}
	
	
	
	@RequiresPermissions(value={"wms:art:add","wms:art:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(@ModelAttribute Art art, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes) {
		artService.saveArt(art);
		addMessage(redirectAttributes, "操作成功");
		redirectAttributes.addFlashAttribute("art", artQueryParamsCache) ;
		return "redirect:" + adminPath + "/wms/art/list?repage";
	}
	
	@RequiresPermissions(value={"wms:art:audit"})
	@RequestMapping(value = "formAudit")
	public String formAudit(@RequestParam(required=false) Integer id, Model model) {
		Art art = artService.getArt(id);
		model.addAttribute("art", art);
		return "modules/art/artFormAudit";
	}
	
	
	@RequiresPermissions(value={"wms:art:audit"})
	@RequestMapping(value = "audit")
	public String audit(@ModelAttribute Art art, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes) {
		logger.info(JSON.toJSONString(art));
		
		artService.auditPass(art);
		addMessage(redirectAttributes, "作品审核成功");
		redirectAttributes.addFlashAttribute("art", artQueryParamsCache) ;
		return "redirect:" + adminPath + "/wms/art/list?repage";
	}
	
	@RequiresPermissions("wms:art:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
		if(id != null && id > 0){
			artService.deleteArt(id);
		}
		addMessage(redirectAttributes, "删除作品成功");
		redirectAttributes.addFlashAttribute("art", artQueryParamsCache) ;
		return "redirect:" + adminPath + "/wms/art/list?repage";
	}
	
	/**
	 * 批量删除场所
	 */
	@RequiresPermissions("wms:art:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(ids)){
			List<Integer> lds = new ArrayList<Integer>();
			String idArray[] =ids.split(",");
			for(String id : idArray){
				lds.add(Integer.valueOf(id));
			}
			
			artService.deleteMultiArts(lds);
			
			addMessage(redirectAttributes, "删除作品成功");
		}
		redirectAttributes.addFlashAttribute("art", artQueryParamsCache) ;
		return "redirect:" + adminPath + "/wms/art/list?repage";
		
	}
	
	/**
	 * 自动搜索 画家列表
	 * @return json
	 */
	@RequiresPermissions(value={"wms:event:view","wms:event:add","wms:event:edit"},logical=Logical.OR)
	@RequestMapping(value = {"list4AutoComplete"})
	@ResponseBody
	public String list4AutoComplete(HttpServletRequest request, HttpServletResponse response) {
		String searchParam = request.getParameter("searchParam");
		if(StringUtils.isNotBlank(searchParam)){
			List<ArtDTO> lists = artService.listArtistAutoComplete(searchParam.trim());
			return JSON.toJSONString(new ActionResponse<List<ArtDTO>>(lists));
		}
		return JSON.toJSONString(new ActionResponse<>(1101,"参数不可为空"));
	}
	
	@RequiresPermissions(value={"wms:art:view"})
	@RequestMapping(value = "verifyRemote4EventBind")
	@ResponseBody
	public boolean verifyRemote4EventBind(@RequestParam Integer id , @RequestParam String searchParam){
		return artService.verifyArtists4Event(id , searchParam );
		
	}
		
	@RequiresPermissions("wms:event:view")
	@RequestMapping("worktable/getArtInfo")
	@ResponseBody
	public List<WorkTableDTO> getArtInfo(){
		List<WorkTableDTO> list = artService.getWorkTableArtInfo();
		return list;
	}
	
	
	
	
}
