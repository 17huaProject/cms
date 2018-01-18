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
import com.jeeplus.modules.business.dto.VenueDTO;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.City;
import com.jeeplus.modules.business.entity.Venues;
import com.jeeplus.modules.business.service.VenuesService;
import com.jeeplus.modules.business.utils.BusinessUtils;


@Controller
@RequestMapping(value = "${adminPath}/wms/venue")
public class VenuesCtrl extends BaseController {

	@Autowired
	VenuesService venuesService ;
	
	private Venues venueQueryParamsCache ;  //查询条参数缓存
	
	@RequiresPermissions("wms:venues:index")
	@RequestMapping("index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model){
		String isCheck = request.getParameter("isCheck");
		model.addAttribute("isCheck", isCheck) ;
		return "modules/venues/venuesIndex";
	}
	
	@RequiresPermissions("wms:venues:index")
	@RequestMapping(value = "venueList4Event")
	public String venueList4Event(@ModelAttribute(value="venue") Venues venue, HttpServletRequest request, HttpServletResponse response, Model model) {
		venue.setIsCheck(CommonConstants.CHECKPASSED);
		Page<Venues> page = venuesService.findVenues(new Page<Venues>(request, response), venue);
        model.addAttribute("page", page);
		return "modules/venues/venueList4Event";
	}
	
	@RequiresPermissions("wms:venues:index")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute(value="venue") Venues venue, HttpServletRequest request, HttpServletResponse response, Model model) {
		venueQueryParamsCache = venue ;
		Page<Venues> page = venuesService.findVenues(new Page<Venues>(request, response), venue);
		model.addAttribute("page", page);
		return "modules/venues/venuesList";
	}
	
	
	@RequiresPermissions(value={"wms:venues:add","wms:venues:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) Integer id, Model model) {
		logger.info(String.valueOf(id));
		Venues venue = venuesService.getVenue(id);
		ImmutableMap<String, String> cityInfo = null ;
		if(venue.getId() != null){
			cityInfo = ImmutableMap.of(venue.getCityCode(),venue.getCity());
		}
		model.addAttribute("venue", venue);
		model.addAttribute("cityInfo", cityInfo);
		return "modules/venues/venuesForm";
	}
	
	
	@RequiresPermissions(value={"wms:venues:view"})
	@RequestMapping(value = "viewForm")
	public String viewForm(@RequestParam(required=false) Integer id, Model model) {
		logger.info(String.valueOf(id));
		Venues venue = venuesService.getVenue(id);
		ImmutableMap<String, String> cityInfo = null ;
		if(venue.getId() != null){
			cityInfo = ImmutableMap.of(venue.getCityCode(),venue.getCity());
		}
		model.addAttribute("venue", venue);
		model.addAttribute("cityInfo", cityInfo);
		return "modules/venues/venuesFormView";
	}
	
	
	
	@RequiresPermissions(value={"wms:venues:add","wms:venues:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(@ModelAttribute Venues venue, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes) {
		logger.info(JSON.toJSONString(venue));
		String provinceCode = venue.getProvinceCode() ;
		if (provinceCode !=null && CommonConstants.MUNICIPALITY.contains(provinceCode)) {
			List<City> citys = BusinessUtils.getCityList(provinceCode);
			if(citys.size() == 1){
				venue.setCity(citys.get(0).getName());
				venue.setCityCode(citys.get(0).getCode());
			}
		}
		venuesService.saveVenue(venue);
		redirectAttributes.addFlashAttribute("venue", venueQueryParamsCache) ;
		return "redirect:" + adminPath + "/wms/venue/list?repage";
	}
	
	@RequiresPermissions(value={"wms:venues:audit"})
	@RequestMapping(value = "formAudit")
	public String formAudit(@RequestParam(required=false) Integer id, Model model) {
		Venues venue = venuesService.getVenue(id);
		ImmutableMap<String, String> cityInfo = ImmutableMap.of(venue.getCityCode(),venue.getCity());
		model.addAttribute("venue", venue);
		model.addAttribute("cityInfo", cityInfo);
		return "modules/venues/venuesFormAudit";
	}
	
	
	@RequiresPermissions(value={"wms:venues:audit"})
	@RequestMapping(value = "audit")
	public String audit(@ModelAttribute Venues venue, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes) {
		venuesService.auditPass(venue);
		redirectAttributes.addFlashAttribute("venue", venueQueryParamsCache) ;
		return "redirect:" + adminPath + "/wms/venue/list?repage";
	}
	
	@RequiresPermissions("wms:venues:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
		if(id != null && id > 0){
			venuesService.deleteVenue(id);
		}
		redirectAttributes.addFlashAttribute("venue", venueQueryParamsCache) ;
		return "redirect:" + adminPath + "/wms/venue/list?repage";
	}
	
	/**
	 * 批量删除场所
	 */
	@RequiresPermissions("wms:venues:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(ids)){
			List<Integer> lds = new ArrayList<Integer>();
			String idArray[] =ids.split(",");
			for(String id : idArray){
				lds.add(Integer.valueOf(id));
			}
			
			venuesService.deleteMultiVenue(lds);
			
			addMessage(redirectAttributes, "删除场所成功");
		}
		redirectAttributes.addFlashAttribute("venue", venueQueryParamsCache) ;
		return "redirect:" + adminPath + "/wms/venue/list?repage";
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
			List<VenueDTO> lists = venuesService.list4AutoComplete(searchParam.trim());
			return JSON.toJSONString(new ActionResponse<List<VenueDTO>>(lists));
		}
		return JSON.toJSONString(new ActionResponse<>(1101,"参数不可为空"));
	}
	
	
	@RequiresPermissions(value={"wms:venues:audit"})
	@RequestMapping("worktable/getVenusInfo")
	@ResponseBody
	public List<WorkTableDTO> getVenusInfo(){
		List<WorkTableDTO> list = venuesService.getWorkTableVenusInfo();
		return list;
	}
	
	@RequiresPermissions(value={"wms:venues:view"})
	@RequestMapping(value = "verifyRemote4EventBind")
	@ResponseBody
	public boolean verifyRemote4EventBind(@RequestParam Integer id , @RequestParam String searchParam){
		return venuesService.verifyArtists4Event(id , searchParam );
		
	}
	
	
	
	
}
