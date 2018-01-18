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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.ActionResponse;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.business.dto.EventDTO;
import com.jeeplus.modules.business.dto.MaterialDeliveryBaseInfoDTO;
import com.jeeplus.modules.business.dto.MaterialDeliveryDTO;
import com.jeeplus.modules.business.dto.MaterialDeliveryExportDTO;
import com.jeeplus.modules.business.entity.GoodCategory;
import com.jeeplus.modules.business.entity.MaterialDelivery;
import com.jeeplus.modules.business.service.EventService;
import com.jeeplus.modules.business.service.GoodCategoryService;
import com.jeeplus.modules.business.service.GoodService;
import com.jeeplus.modules.business.service.MaterialDeliveryService;
import com.jeeplus.modules.business.service.MaterialModelService;

@Controller
@RequestMapping(value = "${adminPath}/cms/materialDelivery")
public class MaterialDeliveryCtrl   extends BaseController {

	@Autowired
	MaterialDeliveryService materialDeliveryService;
	@Autowired
	GoodCategoryService goodCategoryService;
	@Autowired
	GoodService goodService;
	@Autowired
	MaterialModelService materialModelService;
	@Autowired
	EventService eventService ;

	@RequiresPermissions("cms:materialDelivery:index")
	@RequestMapping("index")
	public String index(){
		
		return "modules/good/materialDeliveryIndex";
	}
	
	@RequiresPermissions("cms:materialDelivery:index")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute(value="materialDelivery") MaterialDelivery materialDelivery, 
			HttpServletRequest request, 
			HttpServletResponse response, 
			Model model) {
		
		Page<MaterialDelivery> page = materialDeliveryService.findMaterialDeliverys(new Page<MaterialDelivery>(request, response), materialDelivery);
		
        model.addAttribute("page", page);
		return "modules/good/materialDeliveryList";
	}
	
	
	@RequiresPermissions(value={"cms:materialDelivery:add","cms:materialDelivery:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) Integer eventId, Model model) {
		
		//List<MaterialModel> materialModels = materialModelService.listAllMaterialModels();
		
		List<GoodCategory> goodCategorys = goodCategoryService.findGoodCategorys() ;
		
		EventDTO event = null ;
		List<EventDTO> events = null ;
		
		if (eventId != null && eventId >0) { //修改模块
			event = eventService.getEvent(eventId) ;
		} else {	//添加模块
			events = eventService.listEvents4MaterialDelivery();
		}
		
		MaterialDeliveryDTO materialDeliveryDTO = new MaterialDeliveryDTO();
		materialDeliveryDTO.setModelId(0);
		List<MaterialDeliveryDTO> materialDeliverys  = null ;
		if (eventId != null && eventId >0) {
			materialDeliverys  = materialDeliveryService.getMaterialDelivery(eventId);
			if (materialDeliverys.size()>1) {
				materialDeliveryDTO.setModelId(materialDeliverys.get(0).getModelId());
			}
			materialDeliveryDTO.setEventId(eventId);
		}
		if (materialDeliverys == null) materialDeliverys = new ArrayList<MaterialDeliveryDTO>();
		
		
		model.addAttribute("materialDeliveryDTO", materialDeliveryDTO );
		model.addAttribute("materialDeliverys", materialDeliverys);
		//model.addAttribute("materialModels", materialModels);
		model.addAttribute("goodCategorys", goodCategorys);
		model.addAttribute("events", events);
		model.addAttribute("event", event);
		return "modules/good/materialDeliveryForm";
	}
	
	
	@RequiresPermissions(value={"cms:materialDelivery:view"})
	@RequestMapping(value = "viewForm")
	public String viewForm(@RequestParam(required=false) Integer eventId, Model model) {
		List<MaterialDeliveryDTO> materialDeliverys  = materialDeliveryService.getMaterialDelivery(eventId);
		EventDTO event  = eventService.getEvent(eventId) ;
		
		MaterialDeliveryDTO materialDeliveryDTO = new MaterialDeliveryDTO();
		if (eventId != null && eventId >0) {
			if (materialDeliverys.size()>1) {
				materialDeliveryDTO.setModelId(materialDeliverys.get(0).getModelId());
			}
		}
		
		model.addAttribute("materialDeliveryDTO", materialDeliveryDTO );
		model.addAttribute("event", event);
		model.addAttribute("materialDeliverys", materialDeliverys);
		return "modules/good/materialDeliveryFormView";
	}
	
	/**
	 * 入库
	 */
	@RequiresPermissions(value={"cms:materialDelivery:view"})
	@RequestMapping(value = "formIn")
	public String FormIn(@RequestParam(required=false) Integer eventId, Model model) {
		List<MaterialDeliveryDTO> materialDeliverys  = materialDeliveryService.getMaterialDelivery(eventId);
		EventDTO event  = eventService.getEvent(eventId) ;
		
		MaterialDeliveryDTO materialDeliveryDTO = new MaterialDeliveryDTO();
		if (eventId != null && eventId >0) {
			if (materialDeliverys.size()>1) {
				materialDeliveryDTO.setModelId(materialDeliverys.get(0).getModelId());
			}
		}
		
		model.addAttribute("materialDeliveryDTO", materialDeliveryDTO );
		model.addAttribute("event", event);
		model.addAttribute("materialDeliverys", materialDeliverys);
		return "modules/good/materialDeliveryInForm";
	}
	
	/**
	 * 入库
	 */	
	@RequiresPermissions(value={"cms:materialDelivery:add","cms:materialDelivery:edit"},logical=Logical.OR)
	@RequestMapping(value = "saveIn")
	public String saveIn(@ModelAttribute MaterialDeliveryDTO materialDeliveryDTO, 
			HttpServletRequest request, Model model,  
			RedirectAttributes redirectAttributes) {
		
		materialDeliveryService.saveInMaterialDelivery(materialDeliveryDTO);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:" + adminPath + "/cms/materialDelivery/list?repage";
	}
	
	@RequiresPermissions(value={"cms:materialDelivery:add","cms:materialDelivery:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(@ModelAttribute MaterialDeliveryDTO materialDeliveryDTO, 
			HttpServletRequest request, Model model,  
			RedirectAttributes redirectAttributes) {
		
		materialDeliveryService.saveMaterialDelivery(materialDeliveryDTO);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:" + adminPath + "/cms/materialDelivery/list?repage";
	}
	
	
	@RequiresPermissions("cms:materialDelivery:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
		if(id != null && id > 0){
			materialDeliveryService.deleteMaterialDelivery(id);
		}
		addMessage(redirectAttributes, "删除成功");
		return "redirect:" + adminPath + "/cms/materialDelivery/list?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequiresPermissions("cms:materialDelivery:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(ids)){
			List<Integer> lds = new ArrayList<Integer>();
			String idArray[] =ids.split(",");
			for(String id : idArray){
				lds.add(Integer.valueOf(id));
			}
			
			materialDeliveryService.deleteMultiMaterialDeliverys(lds);
			
			addMessage(redirectAttributes, "删除成功");
		}
		
		return "redirect:" + adminPath + "/cms/materialDelivery/list?repage";
		
	}
	
	/**
	 * 根据活动id获取生成物料单的基本信息
	 * @return
	 */
	@RequiresPermissions("cms:materialDelivery:index")
	@RequestMapping("genBaseInfo")
	@ResponseBody
	public ActionResponse<MaterialDeliveryBaseInfoDTO> genBaseInfo(@RequestParam Integer eventId ){
		if (eventId == null || eventId <= 0) 
			return new ActionResponse<MaterialDeliveryBaseInfoDTO>(1101 , "活动ID不可为空") ;

		MaterialDeliveryBaseInfoDTO baseInfo = materialDeliveryService.genBaseInfo(eventId);
		if (baseInfo.getMaterialModelId() == null) {
			return new ActionResponse<MaterialDeliveryBaseInfoDTO>(3001, baseInfo.getArtName()+"作品尚未配置物料") ;
		}
		
		return new ActionResponse<MaterialDeliveryBaseInfoDTO>(baseInfo) ;
	}
	
	/**
	 * 导出物料单数据
	 */
	@RequiresPermissions("cms:materialDelivery:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(@RequestParam Integer eventId, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		
		if (eventId != null && eventId >0){
//			List<Integer> idArr = BusinessStringUtils.parseString2ListInteger(ids , ",");
			
			MaterialDeliveryExportDTO exportInfo = materialDeliveryService.getMaterialDeliveryExportInfo(eventId) ;
			
			try {
				String fileName = "开票信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
				//new ExportExcel("开票信息", InvoiceInfoDTO.class).setDataList(invoices).write(response, fileName).dispose();
				return null;
			} catch (Exception e) {
				addMessage(redirectAttributes, "导出物料信息失败！失败信息："+e.getMessage());
			}
			
		} else {
			addMessage(redirectAttributes, "错误：至少选择一条记录");
		}
			
		return "redirect:" + adminPath + "/cms/invoice/list?repage";
    }
	
	
	
}
