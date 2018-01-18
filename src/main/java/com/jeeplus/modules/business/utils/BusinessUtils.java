package com.jeeplus.modules.business.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.business.dao.ArticleCatMapper;
import com.jeeplus.modules.business.dao.CityMapper;
import com.jeeplus.modules.business.dao.RegionMapper;
import com.jeeplus.modules.business.entity.ArticleCat;
import com.jeeplus.modules.business.entity.ArticleCatExample;
import com.jeeplus.modules.business.entity.City;
import com.jeeplus.modules.business.entity.CityExample;
import com.jeeplus.modules.business.entity.RegionExample;
import com.jeeplus.modules.business.entity.Region;


public class BusinessUtils {
	
	private static Logger logger = LoggerFactory.getLogger(BusinessUtils.class);
	
	private static RegionMapper regionMapper = SpringContextHolder.getBean(RegionMapper.class);
	private static CityMapper cityMapper = SpringContextHolder.getBean(CityMapper.class);
	private static ArticleCatMapper articleCatMaper = SpringContextHolder.getBean(ArticleCatMapper.class);

	public static final String BUSINESS_CACHE = "businessCache";
	public static final String CACHE_CITY_LIST = "cityListByProvinceCode_";
	public static final String CACHE_DISTRICT_LIST = "districtListByProvinceCode_";	
	
	public static final String CACHE_PROVINCE_LIST = "provinceList";
	
	public static final String CACHE_VENUES = "venueCache";
	public static final String CACHE_VENUES_LIST = "venueList";
	public static final String CACHE_VENUES_BY_ID = "venue_";
	
	public static final String CACHE_ARTICLECAT = "articleCatCache";
	public static final String CACHE_ARTICLECAT_LIST = "articleCatList";
	
	public static List<ArticleCat> getArticleCatList(){
		@SuppressWarnings("unchecked")
		List<ArticleCat> articleCats = (List<ArticleCat>) CacheUtils.get(CACHE_ARTICLECAT,CACHE_ARTICLECAT_LIST);
		if(articleCats == null){
			ArticleCatExample example = new ArticleCatExample();
			example.setOrderByClause("catId");
			articleCats = articleCatMaper.selectByExample(example);
			CacheUtils.put(CACHE_ARTICLECAT_LIST, articleCats);
		}
		return articleCats ;
	}
	
	/**
	 * 获取省份列表
	 * @return
	 */
	public static List<Region> getProvinceList(){
		@SuppressWarnings("unchecked")
		List<Region> provinces = (List<Region>)CacheUtils.get(BUSINESS_CACHE, CACHE_PROVINCE_LIST);
		if(provinces == null){
			RegionExample example = new RegionExample();
			example.setOrderByClause("id");
			example.createCriteria().andLevelEqualTo(CommonConstants.RegionStatus.PROVINCE.getCode());
			provinces = (List<Region>) regionMapper.selectByExample(example );
			CacheUtils.put(BUSINESS_CACHE, CACHE_PROVINCE_LIST ,provinces);
		}
		return provinces;
	}
	
	/**
	 * 根据省份码获取直辖市的区列表
	 * @param ProvinceCode  省份编码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Region> getDistrictList(String provinceCode){
		List<Region> districts = null;
		List<City> citys = getCityList(provinceCode);
		
		if (citys.size() > 0 ) {
			districts = (List<Region>)CacheUtils.get(BUSINESS_CACHE, CACHE_DISTRICT_LIST+provinceCode);
			if(districts == null){
				districts = (List<Region>) regionMapper.selectDistrictsByParentId(provinceCode);
			}
		}
			
		CacheUtils.put(BUSINESS_CACHE, CACHE_DISTRICT_LIST+provinceCode, districts);
		return districts;
	}
	
	/**
	 * 根据省份码获取城市列表
	 * @param ProvinceCode  省份编码
	 * @return
	 */
	public static List<City> getCityList(String provinceCode){
		@SuppressWarnings("unchecked")
		List<City> citys = (List<City>)CacheUtils.get(BUSINESS_CACHE, CACHE_CITY_LIST+provinceCode);
		if(citys == null){
			CityExample example = new CityExample();
			example.createCriteria().andParentIdEqualTo(provinceCode).andIsShowEqualTo(CommonConstants.CITY_SHOW);
			citys = (List<City>) cityMapper.selectByExample(example);
			
			CacheUtils.put(BUSINESS_CACHE, CACHE_CITY_LIST+provinceCode, citys);
		}
		return citys;
	}

}
