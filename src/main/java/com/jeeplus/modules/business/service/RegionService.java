package com.jeeplus.modules.business.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeeplus.modules.business.dao.RegionMapper;
import com.jeeplus.modules.business.dto.ProvinceCityDTO;

@Service
public class RegionService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	RegionMapper regionMapper;
	
	public ProvinceCityDTO selectProvinceCityByCityCode(String cityCode){
		ProvinceCityDTO provinceCityDTO = regionMapper.selectProvinceCityByCityCode(cityCode);
		if(provinceCityDTO == null) provinceCityDTO = new ProvinceCityDTO();
		return provinceCityDTO ;
		
	}
	
	

}
