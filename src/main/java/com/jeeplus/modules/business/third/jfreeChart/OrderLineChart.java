package com.jeeplus.modules.business.third.jfreeChart;

import java.util.List;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.business.bo.OrderSummaryBO;
import com.jeeplus.modules.business.service.OrderService;
import com.jeeplus.modules.business.utils.DateTimeUtils;

public class OrderLineChart extends LineChartAbstract {
	String direName = Global.getConfig("order.excel.savePath");
	private OrderService orderService = (OrderService) SpringContextHolder.getApplicationContext().getBean("OrderService") ;
	
	public OrderLineChart(String datetimeDesc) {
		String title = datetimeDesc + "订单曲线图";
		String xLabel = "日期(无票则不显示)";
		String yLabel = "已售票数";
		String imgName = direName + title + ".jpg"; 
		int imgWidth = 800 ;
		int imgHeight = 600 ;
		setProperties(title, xLabel, yLabel, imgName, imgWidth, imgHeight, 10);
	}

	@Override
	protected CategoryDataset getDataSet() {
		String format = "MM-dd" ;
		String querySqlTime = "DATE_FORMAT( pay_time, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )" ;
		List<OrderSummaryBO> orderSummary = orderService.selectOrderSummayDayOfMonth(querySqlTime);
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset() ;
		for (OrderSummaryBO each : orderSummary) {
			dataset.addValue(each.getSumNumber(), "订单量", DateTimeUtils.getDateTimeFormat(format, each.getPayDate())); 
		}
		
		return dataset;
	}

}
