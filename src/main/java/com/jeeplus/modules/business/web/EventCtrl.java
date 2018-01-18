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
import com.google.common.collect.ImmutableMap;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.business.dto.EventDTO;
import com.jeeplus.modules.business.dto.ProvinceCityDTO;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.Art;
import com.jeeplus.modules.business.entity.Artist;
import com.jeeplus.modules.business.entity.UserCustom;
import com.jeeplus.modules.business.entity.Venues;
import com.jeeplus.modules.business.service.ArtService;
import com.jeeplus.modules.business.service.ArtistService;
import com.jeeplus.modules.business.service.CustomService;
import com.jeeplus.modules.business.service.EventService;
import com.jeeplus.modules.business.service.RegionService;
import com.jeeplus.modules.business.service.VenuesService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;


@Controller
@RequestMapping(value = "${adminPath}/wms/event")
public class EventCtrl extends BaseController {

	@Autowired
	EventService eventService ;
	@Autowired
	ArtService artService ;
	@Autowired
	ArtistService artistService ;
	@Autowired
	VenuesService venuesService ;
	@Autowired
	SystemService systemService;
	@Autowired
	RegionService regionService ;	
	@Autowired
	CustomService customService ;
	
	private String previewUrl = Global.getConfig("event.previewUrl");
	private EventDTO eventQueryParamsCache ;  //查询条参数缓存
	
	
	@RequiresPermissions("wms:event:index")
	@RequestMapping("index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model){
		String isCheck = request.getParameter("isCheck");
		model.addAttribute("isCheck", isCheck) ;
		return "modules/event/eventIndex";
	}
	
	@RequiresPermissions("wms:event:index")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute(value="event") EventDTO eventDTO, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EventDTO> page = eventService.findEvents(new Page<EventDTO>(request, response), eventDTO);
		eventQueryParamsCache = eventDTO ;
        model.addAttribute("page", page);
        model.addAttribute("previewUrl", previewUrl);
		return "modules/event/eventList";
	}
	
	
	@RequiresPermissions(value={"wms:event:add","wms:event:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) Integer id, 
			@RequestParam(required=false) Integer customId, 
			Model model) {
		
		EventDTO eventDTO = eventService.getEvent(id);
		Art  art = artService.getArt(eventDTO.getArtId());
		Artist artist = artistService.getArtist(eventDTO.getArtistId());
		Venues  venue = venuesService.getVenue(eventDTO.getVenueId());
		User user = systemService.getUser(eventDTO.getAssistantId());
		ImmutableMap<String, String> cityInfo = null ;
		
		eventDTO.setFreeServices(StringUtils.split(eventDTO.getFreeService(), ","));
		if (StringUtils.isNotBlank(eventDTO.getCityCode())){
			ProvinceCityDTO provinceCityDTO = regionService.selectProvinceCityByCityCode(eventDTO.getCityCode());
			
			if(provinceCityDTO.getCityCode() != null){  
				cityInfo = ImmutableMap.of(provinceCityDTO.getCityCode(),provinceCityDTO.getCityName());
			}
			eventDTO.setProvinceCode(provinceCityDTO.getProvinceCode());
		}
		
		//:填充定制信息
		if (customId != null && customId>0) {
			UserCustom userCustom = customService.getCustom(customId);
			
			if ( CommonConstants.CustomType.COMPANY.getCode().equals( userCustom.getCustomType() )) {
				eventDTO.setType(CommonConstants.EventType.COMPANY.getCode());
			} else if (CommonConstants.CustomType.PRIVATE.getCode().equals( userCustom.getCustomType() )) {
				eventDTO.setType(CommonConstants.EventType.PRIVATE.getCode());
			}
			
			eventDTO.setCapacity( userCustom.getEstNum() );
			eventDTO.setEventTime( userCustom.getEstDate());
			eventDTO.setClosingTime( userCustom.getEstDate());
		}
		//:~ 填充定制信息

		//: 活动分享描述语list
		List<String> shareDescList = eventService.genShareDescList();
		//:~ 活动分享描述语list
		
		eventDTO.setCustomId(customId);
		model.addAttribute("event", eventDTO);
		model.addAttribute("art", art);
		model.addAttribute("artist", artist);
		model.addAttribute("venue", venue);
		model.addAttribute("user", user);
		model.addAttribute("cityInfo", cityInfo);
		model.addAttribute("shareDescList", shareDescList);
		 
		return "modules/event/eventForm";
	}
	
	
	@RequiresPermissions(value={"wms:event:view"})
	@RequestMapping(value = "viewForm")
	public String viewForm(@RequestParam(required=false) Integer id, Model model) {
		EventDTO eventDTO = eventService.getEvent(id);
		Art  art = artService.getArt(eventDTO.getArtId());
		Artist artist = artistService.getArtist(eventDTO.getArtistId());
		Venues  venue = venuesService.getVenue(eventDTO.getVenueId());
		User user = systemService.getUser(eventDTO.getAssistantId());
		ImmutableMap<String, String> cityInfo = null ;
		
		eventDTO.setFreeServices(StringUtils.split(eventDTO.getFreeService(), ","));
		if (StringUtils.isNotBlank(eventDTO.getCityCode())){
			ProvinceCityDTO provinceCityDTO = regionService.selectProvinceCityByCityCode(eventDTO.getCityCode());
			
			if(provinceCityDTO.getCityCode() != null){  
				cityInfo = ImmutableMap.of(provinceCityDTO.getCityCode(),provinceCityDTO.getCityName());
			}
			eventDTO.setProvinceCode(provinceCityDTO.getProvinceCode());
		}
		
		model.addAttribute("event", eventDTO);
		model.addAttribute("art", art);
		model.addAttribute("artist", artist);
		model.addAttribute("venue", venue);
		model.addAttribute("user", user);
		model.addAttribute("cityInfo", cityInfo);
		
		
		return "modules/event/eventFormView";
	}
	
	
	
	@RequiresPermissions(value={"wms:event:add","wms:event:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(@ModelAttribute(value="event") EventDTO eventDTO, 
			HttpServletRequest request, 
			Model model,  
			RedirectAttributes redirectAttributes) {
		
		int eventId = eventService.saveEvent(eventDTO);
		
		if (eventDTO.getCustomId()!=null && eventId > 0) {
			customService.updateEventId(eventDTO.getCustomId() , eventId);
		}
		
		redirectAttributes.addFlashAttribute("event" , eventQueryParamsCache);
		
		return "redirect:" + adminPath + "/wms/event/list?repage";
	}
	
	@RequiresPermissions(value={"wms:event:audit"})
	@RequestMapping(value = "formAudit")
	public String formAudit(@RequestParam(required=false) Integer id, Model model) {
		EventDTO eventDTO = eventService.getEvent(id);
		Art  art = artService.getArt(eventDTO.getArtId());
		Artist artist = artistService.getArtist(eventDTO.getArtistId());
		Venues  venue = venuesService.getVenue(eventDTO.getVenueId());
		User user = systemService.getUser(eventDTO.getAssistantId());
		
		eventDTO.setFreeServices(StringUtils.split(eventDTO.getFreeService(), ","));
		
		ProvinceCityDTO provinceCityDTO = regionService.selectProvinceCityByCityCode(eventDTO.getCityCode());
		
		ImmutableMap<String, String> cityInfo = null ;
		if(provinceCityDTO.getCityCode() != null){  
			cityInfo = ImmutableMap.of(provinceCityDTO.getCityCode(),provinceCityDTO.getCityName());
		}
		eventDTO.setProvinceCode(provinceCityDTO.getProvinceCode());
		//: 活动分享描述语list
		List<String> shareDescList = eventService.genShareDescList();
		//:~ 活动分享描述语list
		
		model.addAttribute("event", eventDTO);
		model.addAttribute("art", art);
		model.addAttribute("artist", artist);
		model.addAttribute("venue", venue);
		model.addAttribute("user", user);
		model.addAttribute("cityInfo", cityInfo);
		model.addAttribute("shareDescList", shareDescList);
		
		return "modules/event/eventFormAudit";
	}
	
	
	@RequiresPermissions(value={"wms:event:audit"})
	@RequestMapping(value = "audit")
	public String audit(@ModelAttribute(value="event") EventDTO eventDTO, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes) {
		
		eventService.auditPass(eventDTO);
		
		redirectAttributes.addFlashAttribute("event" , eventQueryParamsCache);
		return "redirect:" + adminPath + "/wms/event/list?repage";
	}
	
	/**
	 * 复制活动
	 */
	@RequiresPermissions(value={"wms:event:copy"})
	@RequestMapping(value = "copy")
	public String copy(@RequestParam(required=true) Integer id, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes) {
		boolean flag = eventService.copyEvent(id);
		if (flag) {
			addMessage(redirectAttributes, "复制活动成功");
		} else {
			addMessage(redirectAttributes, "复制活动失败,请重新操作");
		}
		return "redirect:" + adminPath + "/wms/event/list?repage";
	}
	
	@RequiresPermissions("wms:event:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
		if(id != null && id > 0){
			eventService.deleteEventLogic(id);
			addMessage(redirectAttributes, "删除活动成功");
		}
		redirectAttributes.addFlashAttribute("event" , eventQueryParamsCache);
		return "redirect:" + adminPath + "/wms/event/list?repage";
	}
	
	/**
	 * 批量删除活动
	 */
	@RequiresPermissions("wms:event:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(ids)){
			List<Integer> lds = new ArrayList<Integer>();
			String idArray[] =ids.split(",");
			for(String id : idArray){
				lds.add(Integer.valueOf(id));
			}
			
			eventService.deleteMultiEventsLogic(lds);
			
			addMessage(redirectAttributes, "删除活动成功");
		}
		redirectAttributes.addFlashAttribute("event" , eventQueryParamsCache);
		
		return "redirect:" + adminPath + "/wms/event/list?repage";
		
	}
	
	@RequiresPermissions("wms:event:index")
	@RequestMapping("worktable/getEventInfo")
	@ResponseBody
	public List<WorkTableDTO> getEventInfo(){
		List<WorkTableDTO> list = eventService.getWorkTableEventInfo();
		return list;
	}
	

	
	
	
	
	
}
