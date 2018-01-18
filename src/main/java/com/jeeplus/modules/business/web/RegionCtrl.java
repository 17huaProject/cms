package com.jeeplus.modules.business.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.modules.business.utils.BusinessUtils;
@Controller
@RequestMapping(value = "${adminPath}/wms/region") 
public class RegionCtrl {

	/**
	 * 根据省份编号获取城市列表 或  直辖市的区列表
	 */
	@RequestMapping(value = "cityAndDistrict/list")
	@ResponseBody
	public String getCityAndDistrictList(HttpServletRequest request,String provinceCode, HttpServletResponse response) {
		 
		 String cityes = "" ;
		 //直辖市
		 if (CommonConstants.MUNICIPALITY.contains(provinceCode)) {
			 cityes = JSON.toJSONString( BusinessUtils.getDistrictList(provinceCode) );
		 }else{
			 cityes = JSON.toJSONString( BusinessUtils.getCityList(provinceCode) );
		 }
		 return "{\"code\":0,\"data\":"+cityes+"}" ;
	}
	
	/**
	 * 根据省份编号获取城市列表
	 */
	@RequestMapping(value = "city/list")
	@ResponseBody
	public String getCityList(HttpServletRequest request,String provinceCode, HttpServletResponse response) {
		
		String	cityes = JSON.toJSONString( BusinessUtils.getCityList(provinceCode) );
		return "{\"code\":0,\"data\":"+cityes+"}" ;
	}
	
	
	
	
	
	
}
