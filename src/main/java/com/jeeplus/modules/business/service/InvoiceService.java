package com.jeeplus.modules.business.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.modules.business.bo.OrderItemsExcelBO;
import com.jeeplus.modules.business.dao.InvoiceMapper;
import com.jeeplus.modules.business.dto.InvoiceInfoDTO;
import com.jeeplus.modules.business.entity.Invoice;
import com.jeeplus.modules.business.entity.InvoiceExample;
import com.jeeplus.modules.business.utils.FileUtils;

@Service("InvoiceService")
public class InvoiceService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	InvoiceMapper invoiceMapper ;
	
	
	public Invoice getInvoice(Integer id) {
		if(id == null || id <= 0) return new Invoice();
		
		Invoice invoice = invoiceMapper.selectByPrimaryKey(id);
		return invoice;
	}
	
	
	public Page<Invoice> findInvoice(Page<Invoice> page, Invoice invoice) {
		invoice.setPage(page);
		
		if(StringUtils.isNotBlank(invoice.getUserId())){
			invoice.setUserId("%"+invoice.getUserId().trim()+"%");
		}
		if(StringUtils.isNotBlank(invoice.getInvoiceType())){
			invoice.setInvoiceType("%"+invoice.getInvoiceType().trim()+"%");
		}
		if(StringUtils.isNotBlank(invoice.getShippingMode())){
			invoice.setShippingMode("%"+invoice.getShippingMode().trim()+"%");
		}
		if(StringUtils.isNotBlank(invoice.getTitle())){
			invoice.setTitle("%"+invoice.getTitle().trim()+"%");
		}
		if(StringUtils.isNotBlank(invoice.getCompanyCode())){
			invoice.setCompanyCode("%"+invoice.getCompanyCode().trim()+"%");
		}
		if(StringUtils.isNotBlank(invoice.getInvoiceStatus())){
			invoice.setInvoiceStatus("%"+invoice.getInvoiceStatus().trim()+"%");
		}
		if(StringUtils.isNotBlank(invoice.getOrderIds())){
			invoice.setOrderIds("%"+invoice.getOrderIds().trim()+"%");
		}
		if(StringUtils.isNotBlank(invoice.getOrderType())){
			invoice.setOrderType("%"+invoice.getOrderType().trim()+"%");
		}
		if(invoice.getPattern() != null){
			invoice.setPattern(invoice.getPattern());
		}
		
		page.setList(invoiceMapper.select4List(invoice ));
		return page;
	}

	@Transactional(readOnly = false)
	public void deleteInvoice(Integer id) {
		invoiceMapper.deleteByPrimaryKey(id);
	}
	
	@Transactional(readOnly = false)
	public void deleteMultiInvoice(List<Integer> lds) {
		InvoiceExample invoiceExample = new InvoiceExample();
		invoiceExample.createCriteria().andIdIn(lds );
		invoiceMapper.deleteByExample(invoiceExample);
	}

	@Transactional(readOnly = false)
	public void saveInvoice(Invoice invoice) {
		if(invoice == null ){
			logger.error("InvoiceService.saveInvoice() param invoice is null!");
		}
		if(invoice.getId() == null || invoice.getId() == 0){ 
			invoice.preInsert();
			invoiceMapper.insertSelective(invoice);
		}else{
			invoice.preUpdate();
			invoiceMapper.updateByPrimaryKeySelective(invoice);
		}
	}

	/**
	 * 根据id查找记录
	 * @param lds
	 * @return
	 */
	public List<InvoiceInfoDTO> listInvoices(List<Integer> lds) {
		List<InvoiceInfoDTO> invoices = invoiceMapper.selectInvoices4Print(lds);
		return invoices;
	}
	
	/**
	 * 查找未处理的发票记录
	 * @return
	 */
	public List<InvoiceInfoDTO> listUndealInvoices() {
		List<InvoiceInfoDTO> invoices = invoiceMapper.selectUndealInvoices();
		return invoices;
	}
	
	/**
	 * 生成查询未处理的发票明细记录，并生成excle
	 * @return
	 */
	public String[] getInvoiceItemsExcel(String fileNameDescPrefix ,List<InvoiceInfoDTO> undealInvoices){
		String[] fileArr = new String[1] ;
		if (undealInvoices == null || undealInvoices.size() == 0) return null ;
		
		String direName = Global.getConfig("email.notify.savePath");
		String fileName = fileNameDescPrefix + "前尚未处理发票明细.xlsx";
		String absoluteFileName = direName + fileName ;
		FileUtils.createFile(direName, fileName);
		try {
			new ExportExcel(fileName, InvoiceInfoDTO.class).setDataList(undealInvoices).writeFile(absoluteFileName).dispose();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		fileArr[0] = absoluteFileName ;
		return fileArr ;
		
	}
	



}
