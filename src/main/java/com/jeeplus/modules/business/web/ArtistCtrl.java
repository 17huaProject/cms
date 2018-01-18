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
import com.google.common.collect.ImmutableMap;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.ActionResponse;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.business.dto.ArtistDTO;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.Artist;
import com.jeeplus.modules.business.entity.City;
import com.jeeplus.modules.business.service.ArtistService;
import com.jeeplus.modules.business.service.ImageService;
import com.jeeplus.modules.business.utils.BusinessUtils;

@Controller
@RequestMapping(value = "${adminPath}/wms/artist")
public class ArtistCtrl  extends BaseController {

	@Autowired
	ArtistService artistService;
	@Autowired
	ImageService imageService;
	
	private Artist artistQueryParamsCache ;  //查询条参数缓存

	@RequiresPermissions("wms:artist:index")
	@RequestMapping("index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model){
		String isCheck = request.getParameter("isCheck");
		model.addAttribute("isCheck", isCheck) ;
		return "modules/artist/artistIndex";
	}
	
	@RequiresPermissions("wms:artist:index")
	@RequestMapping(value = "artistList4Event")
	public String artistList4Event(@ModelAttribute(value="artist") Artist artist, HttpServletRequest request, HttpServletResponse response, Model model) {
		artist.setIsCheck(CommonConstants.CHECKPASSED);
		Page<Artist> page = artistService.findArtists(new Page<Artist>(request, response), artist);
        model.addAttribute("page", page);
		return "modules/artist/artistList4Event";
	}
	
	@RequiresPermissions("wms:artist:index")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute(value="artist") Artist artist, HttpServletRequest request, HttpServletResponse response, Model model) {
		artistQueryParamsCache = artist ;
		
		Page<Artist> page = artistService.findArtists(new Page<Artist>(request, response), artist);
		model.addAttribute("page", page);
		return "modules/artist/artistList";
	}
	
	@RequiresPermissions(value={"wms:artist:add","wms:artist:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) Integer id, Model model) {
		logger.info(String.valueOf(id));
		Artist artist = artistService.getArtist(id);
		ImmutableMap<String, String> cityInfo = null ;
		if(artist.getId() != null){
			cityInfo = ImmutableMap.of(artist.getCityCode(),artist.getCity());
		}
		model.addAttribute("artist", artist);
		model.addAttribute("cityInfo", cityInfo);
		return "modules/artist/artistForm";
	}
	
	@RequiresPermissions(value={"wms:artist:view"})
	@RequestMapping(value = "viewForm")
	public String viewForm(@RequestParam(required=false) Integer id, Model model) {
		logger.info(String.valueOf(id));
		Artist artist = artistService.getArtist(id);
		ImmutableMap<String, String> cityInfo = null ;
		if(artist.getId() != null){
			cityInfo = ImmutableMap.of(artist.getCityCode(),artist.getCity());
		}
		model.addAttribute("artist", artist);
		model.addAttribute("cityInfo", cityInfo);
		return "modules/artist/artistFormView";
	}
	
	
	
	@RequiresPermissions(value={"wms:artist:add","wms:artist:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(@ModelAttribute Artist artist, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes) {
		String provinceCode = artist.getProvinceCode() ;
		if (provinceCode !=null && CommonConstants.MUNICIPALITY.contains(provinceCode)) {
			List<City> citys = BusinessUtils.getCityList(provinceCode);
			if(citys.size() == 1){
				artist.setCity(citys.get(0).getName());
				artist.setCityCode(citys.get(0).getCode());
			}
		}
		artistService.saveArtist(artist);
		redirectAttributes.addFlashAttribute("artist", artistQueryParamsCache) ;
		return "redirect:" + adminPath + "/wms/artist/list?repage";
	}
	
	@RequiresPermissions(value={"wms:artist:audit"})
	@RequestMapping(value = "formAudit")
	public String formAudit(@RequestParam(required=false) Integer id, Model model) {
		Artist artist = artistService.getArtist(id);
		ImmutableMap<String, String> cityInfo = ImmutableMap.of(artist.getCityCode(),artist.getCity());
		model.addAttribute("artist", artist);
		model.addAttribute("cityInfo", cityInfo);
		return "modules/artist/artistFormAudit";
	}
	
	
	@RequiresPermissions(value={"wms:artist:audit"})
	@RequestMapping(value = "audit")
	public String audit(@ModelAttribute Artist artist, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes) {
		artistService.auditPass(artist);
		redirectAttributes.addFlashAttribute("artist", artistQueryParamsCache) ;
		return "redirect:" + adminPath + "/wms/artist/list?repage";
	}
	
	@RequiresPermissions("wms:artist:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
		if(id != null && id > 0){
			artistService.deleteArtist(id);
		}
		redirectAttributes.addFlashAttribute("artist", artistQueryParamsCache) ;
		return "redirect:" + adminPath + "/wms/artist/list?repage";
	}
	
	/**
	 * 批量删除场所
	 */
	@RequiresPermissions("wms:artist:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(ids)){
			List<Integer> lds = new ArrayList<Integer>();
			String idArray[] =ids.split(",");
			for(String id : idArray){
				lds.add(Integer.valueOf(id));
			}
			
			artistService.deleteMultiArtists(lds);
			
			addMessage(redirectAttributes, "删除场所成功");
		}
		redirectAttributes.addFlashAttribute("artist", artistQueryParamsCache) ;
		return "redirect:" + adminPath + "/wms/artist/list?repage";
		
	}
	
	/**
	 * 自动搜索 列表
	 * @return json
	 */
	@RequiresPermissions(value={"wms:event:view","wms:event:add","wms:event:edit"},logical=Logical.OR)
	@RequestMapping(value = {"list4AutoComplete"})
	@ResponseBody
	public String list4AutoComplete(HttpServletRequest request, HttpServletResponse response) {
		String searchParam = request.getParameter("searchParam");
		if(StringUtils.isNotBlank(searchParam)){
			List<ArtistDTO> lists = artistService.listArtistAutoComplete(searchParam.trim());
			return JSON.toJSONString(new ActionResponse<List<ArtistDTO>>(lists));
		}
		return JSON.toJSONString(new ActionResponse<>(1101,"参数不可为空"));
	}
	
	/**
	 * 自动搜索 所有画师 列表
	 * 
	 */
	@RequiresPermissions(value={"sys:user:view","sys:user:add","sys:user:edit"},logical=Logical.OR)
	@RequestMapping(value = {"listAllArtists"})
	@ResponseBody
	public ActionResponse<List<ArtistDTO>> listAllArtists(HttpServletRequest request, HttpServletResponse response) {
		List<ArtistDTO> lists = artistService.listArtistAutoComplete(null);
		if(lists == null) {
			return new ActionResponse<List<ArtistDTO>>(1108,"查询失败");
		}
		return new ActionResponse<List<ArtistDTO>>(lists);
		
	}
	
	
	@RequiresPermissions("wms:artist:audit")
	@RequestMapping("worktable/getArtistInfo")
	@ResponseBody
	public List<WorkTableDTO> getArtistInfo(){
		List<WorkTableDTO> list = artistService.getWorkTableArtistInfo();
		return list;
	}
	
	
	@RequiresPermissions(value={"wms:artist:view"})
	@RequestMapping(value = "verifyRemote4EventBind")
	@ResponseBody
	public boolean verifyRemote4EventBind(@RequestParam Integer id , @RequestParam String searchParam){
		return artistService.verifyArtists4Event(id , searchParam );
		
	}
	
	
	
	
	
	
	
}
