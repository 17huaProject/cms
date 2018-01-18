package com.jeeplus.common.utils.excel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.Reflections;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.business.dto.MaterialDeliveryDTO;
import com.jeeplus.modules.business.dto.MaterialDeliveryExportDTO;


/**
 * 导出物料单Excel文件（导出“XLSX”格式，支持大数据量导出   @see org.apache.poi.ss.SpreadsheetVersion）
 */
public class ExportExcel4MaterialDelivery extends ExportExcelBase {
	
	private static Logger log = LoggerFactory.getLogger(ExportExcel4MaterialDelivery.class);
	
	public ExportExcel4MaterialDelivery(String title, MaterialDeliveryExportDTO exportInfo) {
		
		genAnnotationList(exportInfo);
		
		this.wb = new SXSSFWorkbook(500);
		this.sheet = wb.createSheet("Export");
		this.styles = createStyles(wb);
		
		List<MaterialDeliveryDTO> materials =  exportInfo.getMaterials() ;
		//设置标题
		createTitle(title, materials.size());
		
		// Initialize
		int colunm = 0;
		for (Object[] os : annotationList){
			String filedTitle = ((ExcelField)os[0]).title();
			Row row = addRow();
			addCell(row, colunm++, filedTitle) ;
		}
		
	}

	/**
	 * @param exportInfo
	 * @throws SecurityException
	 */
	private void genAnnotationList(MaterialDeliveryExportDTO exportInfo){
		Class cls = exportInfo.getClass() ;
		//获取所有注解的字段
		Field[]  fields = cls.getDeclaredFields();
		for (Field field : fields) {
			ExcelField eField = field.getAnnotation(ExcelField.class) ;
			if (eField != null) {
				annotationList.add(new Object[]{eField, field}) ;
			}
		}
		
		//获取所有注解的方法
		Method[] methods = cls.getDeclaredMethods() ;
		for (Method method : methods) {
			ExcelField eField = method.getAnnotation(ExcelField.class) ;
			if (eField != null) {
				annotationList.add(new Object[]{eField, method}) ;
			}
		}
		// Field sorting
		Collections.sort(annotationList, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				return new Integer(((ExcelField)o1[0]).sort()).compareTo(
						new Integer(((ExcelField)o2[0]).sort()));
			};
		});
	}


	/**
	 * @param title
	 * @param i
	 */
	private void createTitle(String title, int i) {
		if (StringUtils.isNotBlank(title)){
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
					titleRow.getRowNum(), titleRow.getRowNum(), i ));
		}
	}












	
	
	
	
	
	
	
	
	
	
	
	
	

}
