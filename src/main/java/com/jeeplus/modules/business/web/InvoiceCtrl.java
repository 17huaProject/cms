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
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.web.ActionResponse;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.business.dto.InvoiceInfoDTO;
import com.jeeplus.modules.business.entity.Invoice;
import com.jeeplus.modules.business.entity.User;
import com.jeeplus.modules.business.service.InvoiceService;
import com.jeeplus.modules.business.service.UserService;


@Controller
@RequestMapping(value = "${adminPath}/cms/invoice")
public class InvoiceCtrl extends BaseController {

	@Autowired
	InvoiceService invoiceService ;
	@Autowired
	UserService userService;
	
	@RequiresPermissions("cms:invoice:index")
	@RequestMapping("index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model){
		return "modules/invoice/invoiceIndex";
	}
	
	@RequiresPermissions("cms:invoice:index")
	@RequestMapping(value = {"list", ""})
	public String list(@ModelAttribute(value="invoice") Invoice invoice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Invoice> page = invoiceService.findInvoice(new Page<Invoice>(request, response), invoice);
        model.addAttribute("page", page);
		return "modules/invoice/invoiceList";
	}
	
	
	@RequiresPermissions(value={"cms:invoice:add","cms:invoice:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) Integer id, Model model) {
		Invoice invoice = invoiceService.getInvoice(id);
		User user = userService.getUser(invoice.getUserId());
		model.addAttribute("invoice", invoice);
		model.addAttribute("user", user);
		return "modules/invoice/invoiceForm";
	}
	
	
	@RequiresPermissions(value={"cms:invoice:view"})
	@RequestMapping(value = "viewForm")
	public String viewForm(@RequestParam(required=false) Integer id, Model model) {
		Invoice invoice = invoiceService.getInvoice(id);
		User user = userService.getUser(invoice.getUserId());
		model.addAttribute("invoice", invoice);
		model.addAttribute("user", user);
		return "modules/invoice/invoiceFormView";
	}
	
	
	
	@RequiresPermissions(value={"cms:invoice:add","cms:invoice:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(@ModelAttribute Invoice invoice, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes) {
		invoiceService.saveInvoice(invoice);
		return "redirect:" + adminPath + "/cms/invoice/list?repage";
	}
	
	
	
	@RequiresPermissions("cms:invoice:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
		if(id != null && id > 0){
			invoiceService.deleteInvoice(id);
		}
		return "redirect:" + adminPath + "/cms/invoice/list?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequiresPermissions("cms:invoice:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(ids)){
			List<Integer> lds = new ArrayList<Integer>();
			String idArray[] =ids.split(",");
			for(String id : idArray){
				lds.add(Integer.valueOf(id));
			}
			
			invoiceService.deleteMultiInvoice(lds);
			
			addMessage(redirectAttributes, "删除成功");
		}
		
		return "redirect:" + adminPath + "/cms/invoice/list?repage";
		
	}
	
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("cms:invoice:export")
    @RequestMapping(value = "export")
	@ResponseBody
    public ActionResponse<String> exportFile(@RequestParam String ids, HttpServletRequest request, HttpServletResponse response) {
		ActionResponse<String> actionResponse  = null ;
		if (StringUtils.isNotBlank(ids)){
			List<Integer> lds = new ArrayList<Integer>();
			String idArray[] = ids.split(",");
			for(String id : idArray){
				lds.add(Integer.valueOf(id));
			}
			
			List<InvoiceInfoDTO> invoices = invoiceService.listInvoices(lds);
			
			try {
				String fileName = "开票信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
				new ExportExcel("开票信息", InvoiceInfoDTO.class).setDataList(invoices).write(response, fileName).dispose();
				return null;
			} catch (Exception e) {
				actionResponse = new ActionResponse<String>(1109, "导出开票信息失败！") ;
				logger.error(e.getMessage());
			}
		} else {
			actionResponse = new ActionResponse<String>(1101, "参数不可为空 ") ; 
		}
			
		return actionResponse ;
    }
	
	
	
	
}
