package com.jeeplus.modules.business.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeplus.common.utils.StringUtils;

public class BusinessStringUtils { 
	private static Logger logger = LoggerFactory.getLogger(BusinessStringUtils.class);
	
	public static String contentImgAddDomain(String content){
//		String test ="img alt=&quot;&quot; src=&quot;/yqhCMS/userfiles/58bdb9724bfb48beac4d5917df087830/images/event/content/2017/08/bench-accounting-49022.jpg&quot; style=&quot;width: 600px;&quot; /&gt;&lt;br /&gt;边吃，边喝，边画画&lt;br /&gt;"+
//						"&lt;img alt=&quot;&quot; src=&quot;http://123.206.108.152:8090/yqhCMS/userfiles/58bdb9724bfb48beac4d5917df087830/images/event/content/2017/08/%E5%B1%8F%E5%B9%95%E5%BF%AB%E7%85%A7%202017-08-01%20%E4%B8%8B%E5%8D%883_39_21.png&quot; style=&quot;width: 600px;&quot; /&gt;&lt;br /&gt;";

		return content.replaceAll("src=.*?/yqhCMS", "src=&quot;http://123.206.108.152:8090/yqhCMS");
	}
	
	public static String replaceBackslant4UPYun(String source){
		source =  source.replaceAll("\\/", "|");
		return source ;
	}
	
	/**
	 * 解析json字符串
	 * @param jsonStr
	 * @return
	 */
	public static List<Map<String, String>> parseJSONStr(String jsonStr){
		List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
		Map<String, String> map = null ;
		
		jsonStr = jsonStr.replace("\"", "");
		jsonStr = jsonStr.replace("{", "");
		jsonStr = jsonStr.replace("}", "");
		
		String[] strArr = jsonStr.split(",") ;
		
		for (String eachStr : strArr) {
			String[] each = eachStr.split(":") ;
			map = new HashMap<String, String>() ;
			map.put(each[0], each[1]) ;
			listMap.add(map) ;
		}
		return listMap ;
	}

	/**
	 * 
	 * @param jsonStr 格式：id : goodId : outCount : flag , id : goodId : outCount : flag <br/>
	 * flag: 0 删除，1 添加，2 修改
	 * @return
	 */
	public static List<String[]> parseJSONStr2ListArray(String jsonStr) {
		List<String[]> listArray = new ArrayList<String[]>() ;
		try {
			
			String[] strArr = jsonStr.split(",") ;
			
			for (String eachStr : strArr) {
				String[] each = eachStr.split(":") ;
				String[] arr = {each[0], each[1], each[2], each[3]} ;
				listArray.add(arr) ;
			}
		} catch (Exception e) {
			logger.error("BusinessStringUtils.parseJSONStr2ListArray() parameter format  error");
		}
		
		return listArray ;
	}
	
	/**
	 * 
	 * @param jsonStr 格式：id : inCount , id : inCount   <br/>
	 * @return
	 */
	public static List<String[]> parseJSONStr2ListArray2D(String jsonStr) {
		List<String[]> listArray = new ArrayList<String[]>() ;
		try {
			
			String[] strArr = jsonStr.split(",") ;
			
			for (String eachStr : strArr) {
				String[] each = eachStr.split(":") ;
				String[] arr = {each[0], each[1]} ;
				listArray.add(arr) ;
			}
		} catch (Exception e) {
			logger.error("BusinessStringUtils.parseJSONStr2ListArray2D() parameter format  error");
		}
		
		return listArray ;
	}
	
	/**
	 * 根据分隔符，将字符串进行分割
	 * @param source 源字符串
	 * @param regex  分隔符正则
	 * @return source为空，则返回长度为0的list
	 */
	public static List<Integer> parseString2ListInteger(String source , String regex){
		List<Integer> list = new ArrayList<Integer>();
		if (StringUtils.isNotBlank(source)){
			String strArray[] = source.split(regex);
			for(String cell : strArray){
				list.add(Integer.valueOf(cell));
			}
		}
		return list;
	}
	
	
	public static String characterEscape(String source){
		source = source.replaceAll("'", "\\\\'");
		return source;
	}
	
}







