package com.jeeplus.modules.business.third.jfreeChart;

import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeplus.common.config.Global;
import com.jeeplus.modules.business.third.upyun.UPYunUtils;
import com.jeeplus.modules.business.utils.FileUtils;

/**
 * 折线图 抽象类
 * @author afanti
 */
public abstract class LineChartAbstract {
	private Logger logger = LoggerFactory.getLogger(getClass()) ;

	private String title;
	private String xLabel;
	private String yLabel;
	private String imgName ;
	private int imgWidth;
	private int imgHeight;
	private int numberAxisUnit;
	
	/**
	 * 
	 * @param title				标题
	 * @param xLabel			目录轴显示标签
	 * @param yLabel			数值轴显示标签
	 * @param imgName			保存曲线图的图片名称
	 * @param imgWidth			图片宽
	 * @param imgHeight			图片高
	 * @param numberAxisUnit	Y轴数据间隔 
	 */
	protected void setProperties(String title, String xLabel, String yLabel,
			String imgName, int imgWidth, int imgHeight , int numberAxisUnit) {
		this.title = title;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.imgName = imgName;
		this.imgWidth = imgWidth;
		this.imgHeight = imgHeight;
		this.numberAxisUnit = numberAxisUnit ;
	}

	protected JFreeChart getJFreeChart(){
		 // JFreeChart对象 参数：标题，目录轴显示标签，数值轴显示标签，数据集，是否显示图例，是否生成工具，是否生成URL连接
		JFreeChart imgChart = ChartFactory.createLineChart(this.title, this.xLabel, this.yLabel, getDataSet(), PlotOrientation.VERTICAL, true, true, false);
		imgChart.setBackgroundPaint(Color.white);
		imgChart.setBorderVisible(true);
		// 解决曲线图片标题中文乱码问题
		TextTitle textTitle = new TextTitle(title, new Font("宋体", Font.BOLD, 20));
		imgChart.setTitle(textTitle);
		//解决图表底部中文乱码问题 
		imgChart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
		CategoryPlot categoryplot = (CategoryPlot) imgChart.getPlot();  
        categoryplot.setBackgroundPaint(Color.lightGray);  
        categoryplot.setRangeGridlinePaint(Color.white);
        // Y轴  
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();  
        setNumberAxis(numberaxis);  
          
        // x轴  
        CategoryAxis domainAxis = (CategoryAxis) categoryplot.getDomainAxis();  
        setDomainAxis(domainAxis);  
          
        LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot.getRenderer();// 数据点  
        // series 点（即数据点）可见  
        lineandshaperenderer.setBaseShapesVisible(true);  
        // 显示数据点的数据  
        lineandshaperenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());   
        // 显示折线图点上的数据  
        lineandshaperenderer.setBaseItemLabelsVisible(true);  
        return imgChart;
	}

	/** 
     * 设置X轴 
     */ 
	protected void setDomainAxis(CategoryAxis domainAxis) {
		 // 解决x轴坐标上中文乱码  
        domainAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 11));  
        // 解决x轴标题中文乱码  
        domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 14));  
        // 用于显示X轴刻度  
        domainAxis.setTickMarksVisible(true);  
        //设置X轴45度  
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
	}
	
	/** 
     * 设置Y轴 
     */ 
	protected void setNumberAxis(NumberAxis numberaxis) {
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());  
        // 是否显示零点  
        numberaxis.setAutoRangeIncludesZero(true);  
        numberaxis.setAutoTickUnitSelection(false);  
        // 解决Y轴标题中文乱码  
        numberaxis.setLabelFont(new Font("sans-serif", Font.PLAIN, 14));  
        numberaxis.setTickUnit(new NumberTickUnit(numberAxisUnit));//Y轴数据间隔  
	}

	/**
	 * 设置折线图的数据
	 */
	protected abstract CategoryDataset getDataSet();
	
    /** 
     * 输出图片 
     * @throws IOException 
     */  
	public String saveImg(){  
		try {
			FileOutputStream fos = new  FileOutputStream(this.imgName);
			ChartUtilities.writeChartAsJPEG(fos , 1 , getJFreeChart() , this.imgWidth , this.imgHeight , null);  
			fos.close();  
		} catch (Exception e) {
			logger.error("generate LineChart failed : " + e.getMessage());
			return "" ;
		}  
		//上传至又拍云
		String upyunImageUrlPrefix = Global.getConfig("upyun.imageUrlPrefix");
		UPYunUtils.writeFile(this.imgName) ;
		
		String upyunImageUrl = upyunImageUrlPrefix + FileUtils.getUPYunFilePath(this.imgName) ;
		
		logger.debug(upyunImageUrl);
		
		return upyunImageUrl ;
    }
	
	
}
