package com.jeeplus.modules.business.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;


public class DateTimeUtils {	
	
	/**
	 * 得到当前时间的时间戳+3位随机码<br/>
	 * 16位
	 */
	public static String getRandom16ByCurrentTime(){
		String ramdom3 = String.valueOf(new Random().nextInt(999)+100) ;
		return getCurrentTime()+ramdom3;
	}
	
	/**
	 * 得到当前时间的时间戳<br/>
	 * 13位
	 * @return
	 */
	public static String getCurrentTime(){
		return String.valueOf(new Date().getTime());
	}
	
	/**
	 * 判断当前时间是否超过某一时间<br>
	 * example: DateTimeUtils.outOfDate("2017-07-15 10:00:00", "yyyy-MM-dd hh:mm:ss")
	 */
	public static boolean outOfDate(String distDateStr ,String distFormat){
		Date distDate = DateTimeStrToDate(distFormat,distDateStr);
		if (new Date().getTime() > distDate.getTime()) {
			return true ;
		}
		return false ;
	}
	
	
	/**
	 * 输出给定日期延长指定月后的时间
	 * @param sourceDate	原日期
	 * @param sourceFormat 	原日期格式
	 * @param amount 		延长月数
	 * @param distFormat  	目标日期格式
	 * @return
	 */
	public static String delayMonth(String sourceDate ,String sourceFormat ,int amount , String distFormat){
		Date date = DateTimeStrToDate(sourceFormat,sourceDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, amount);
		SimpleDateFormat sf=new SimpleDateFormat(distFormat);
		return sf.format(calendar.getTime());
	}
	/**
	 * 输出当前时间延长/提前指定小时后的时间
	 * @param sourceDate	原日期
	 * @param amount 		正数为延长小时数，负数为提前
	 * @param distFormat  	目标日期格式
	 * @return
	 */
	public static Date delayOrAheadHour(int amount){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, amount);
		
		return calendar.getTime();
	}
	/**
	 * 输出当前时间延长/提前指定时间（年月日时分秒）后的时间
	 * @param field			指定年月日时分秒，如 Calendar.HOUR
	 * @param amount 		正数为延长小时数，负数为提前
	 * @return
	 */
	public static Date delayOrAheadCurrentTime(int field, int amount){
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		
		return calendar.getTime();
	}
	
	/**
	 * 输出指定时间延长/提前指定小时后的时间
	 * @param amount 		正数为延长小时数，负数为提前
	 * @return
	 */
	public static Date delayOrAheadHour(Date date , int amount){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, amount);
		
		return calendar.getTime();
	}
	
	public static int getCurrentWeek(Date date){
		Calendar calendar = Calendar.getInstance();
		//本土化，因为美国是以周日为每周的第一天
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}
	
	
	/**
	 * 获取当前时间
	 * @param  format 
	 * @return
	 */
	public static String getDateTimeFormat(String format, Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	/**
	 * 将时间字符串转换为Date类型
	 * @param format
	 * @param DateTimeStr
	 * @return
	 */
	public static Date DateTimeStrToDate(String format, String DateTimeStr){
		Date date = null ;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			date = sdf.parse(DateTimeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 
	 * @param formatSrc		时间的原格式
	 * @param formatDest	时间的目标格式
	 * @param dateTimeStr	时间字符串，要与formatSrc格式一致
	 * @return
	 */
	public static String formatChange(String formatSrc, String formatDest, String dateTimeStr){
		Date date = DateTimeUtils.DateTimeStrToDate(formatSrc,dateTimeStr);
		return DateTimeUtils.getDateTimeFormat(formatDest,date);
	}
	
	/**
	 * 计算给定时间与当前时间相差的分钟数
	 */
	public static long timeDiff(String format, String DateTimeStr ){
		Date source = DateTimeUtils.DateTimeStrToDate(format, DateTimeStr);
		Date currDate = new Date();
		long milliseconds  = currDate.getTime() - source.getTime();
		return milliseconds/60000;
	}
	
	/**
	 * 获取本月开始时间
	 */
	public static String getCurrMonthStartTime(){
		//规定返回日期格式
		SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		Date theDate = calendar.getTime();
		GregorianCalendar gcLast = (GregorianCalendar)Calendar.getInstance();
		gcLast.setTime(theDate);
		//设置为第一天
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first=sf.format(gcLast.getTime());
		//打印本月第一天
		System.out.println(day_first+"000000");
		return day_first+"000000";  
	}
	/**
	 * 获取本月开始时间
	 */
	public static String getCurrMonth(String format){
		//规定返回日期格式
		SimpleDateFormat sf=new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		Date theDate = calendar.getTime();
		GregorianCalendar gcLast = (GregorianCalendar)Calendar.getInstance();
		gcLast.setTime(theDate);
		//设置为第一天
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first=sf.format(gcLast.getTime());
		//打印本月第一天
		System.out.println(day_first);
		return day_first;  
	}
	
	
}
