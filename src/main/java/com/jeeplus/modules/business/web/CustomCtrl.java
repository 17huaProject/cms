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
import com.jeeplus.modules.business.dto.EventDTO;
import com.jeeplus.modules.business.entity.Art;
import com.jeeplus.modules.business.entity.Artist;
import com.jeeplus.modules.business.entity.UserCustom;
import com.jeeplus.modules.business.entity.Venues;
import com.jeeplus.modules.business.service.ArtService;
import com.jeeplus.modules.business.service.ArtistService;
import com.jeeplus.modules.business.service.CustomService;
import com.jeeplus.modules.business.service.EventService;
import com.jeeplus.modules.business.service.VenuesService;


@Controller
@RequestMapping(value = "${adminPath}/wms/custom")
public class CustomCtrl extends BaseController{
	@Autowired
	CustomService customService;
	@Autowired
	EventService eventService ;
	@Autowired
	ArtService artService ;
	@Autowired
	ArtistService artistService ;
	@Autowired
	VenuesService venuesService ;

	@RequiresPermissions("wms:custom:index")
	@RequestMapping("index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model){
		String status = request.getParameter("status");
		model.addAttribute("status", status) ;
		return "modules/user/customIndex";
	}
	
	@RequiresPermissions("wms:custom:index") 
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute(value="custom") UserCustom custom, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<UserCustom> page = customService.listCustoms(new Page<UserCustom>(request, response), custom);
        model.addAttribute("page", page);
		return "modules/user/customList";
	}
	
	
	@RequiresPermissions(value={"wms:custom:edit"})
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) Integer id, Model model) {
		UserCustom custom = customService.getCustom(id);
		model.addAttribute("custom", custom);
		return "modules/user/customForm";
	}
	
	@RequiresPermissions(value={"wms:custom:view"})
	@RequestMapping(value = "viewForm")
	public String viewForm(@RequestParam Integer id, Model model) {
		UserCustom custom = customService.getCustom(id);
		EventDTO eventDTO = eventService.getEvent(custom.getEventId());
		Art  art = artService.getArt(eventDTO.getArtId());
		Artist artist = artistService.getArtist(eventDTO.getArtistId());
		Venues  venue = venuesService.getVenue(eventDTO.getVenueId());
		
		model.addAttribute("event", eventDTO);
		model.addAttribute("art", art);
		model.addAttribute("artist", artist);
		model.addAttribute("venue", venue);
		model.addAttribute("custom", custom);
		return "modules/user/customFormView";
	}
	
	@RequiresPermissions(value={"wms:custom:edit"})
	@RequestMapping(value = "save")
	public String save(@ModelAttribute(value="custom") UserCustom custom, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes) {
		customService.saveCustom(custom);
		return "redirect:" + adminPath + "/wms/custom/list?repage";
	}
	
	
	@RequiresPermissions("wms:custom:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
		if(id != null && id > 0){
			customService.deleteCustom(id);
		}
		addMessage(redirectAttributes, "删除定制信息成功");
		return "redirect:" + adminPath + "/wms/custom/list?repage";
	}
	
	/**
	 * 批量删除场所
	 */
	@RequiresPermissions("wms:custom:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(ids)){
			List<Integer> lds = new ArrayList<Integer>();
			String idArray[] =ids.split(",");
			for(String id : idArray){
				lds.add(Integer.valueOf(id));
			}
			
			customService.deleteMultiCustoms(lds);
			
			addMessage(redirectAttributes, "删除定制信息成功");
		}
		
		return "redirect:" + adminPath + "/wms/custom/list?repage";
		
	}
	
	
	
}
