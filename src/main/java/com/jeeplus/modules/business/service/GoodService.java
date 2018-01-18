package com.jeeplus.modules.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.dao.GoodMapper;
import com.jeeplus.modules.business.dto.GoodDTO;
import com.jeeplus.modules.business.entity.Good;
import com.jeeplus.modules.business.entity.GoodExample;

@Service
public class GoodService {
	
	Logger logger = LoggerFactory.getLogger(getClass()) ;
	
	@Autowired
	GoodMapper goodMapper ;
	
	public Page<Good> findGoods(Page<Good> page, Good good) {
		good.setPage(page) ;
		
		if(StringUtils.isNotBlank(good.getName())){
			good.setName(good.getName().trim());
		}
		if(StringUtils.isNotBlank(good.getGoodsNo())){
			good.setGoodsNo(good.getGoodsNo().trim());
		}
		
		List<Good> list= goodMapper.select4List(good);
		
		return page.setList(list);
	}

	public Good getGood(Integer id) {
		if(id == null || id <= 0) {
			Good good = new Good() ;
			good.setGoodsNo(String.valueOf(new Date().getTime())); //时间戳作为商品编号
			return good;
		}
		Good category = goodMapper.selectByPrimaryKey(id);
		return category;
	}

	public void saveGood(Good good) {
		if(good == null ){
			logger.error("GoodService.saveGood() param venue is null!");
		}
		
		if(good.getId() == null || good.getId() == 0){
			good.preInsert();
			goodMapper.insertSelective(good);
		}else{
			good.preUpdate();
			goodMapper.updateByPrimaryKeySelective(good);
		}
	}

	public void deleteGood(Integer id) {
		Good good = new Good();
		good.setId(id);
		good.preDeleteLogic();
		goodMapper.updateByPrimaryKeySelective(good);
		
	}

	public void deleteMultiGoods(List<Integer> ids) {
		Good good = new Good();
		good.preDeleteLogic();
		GoodExample example = new GoodExample();
		example.createCriteria().andIdIn(ids);
		goodMapper.updateByExampleSelective(good, example);
		
	}

	/**
	 * 根据商品类型id获取商品列表
	 * @param categoryId
	 * @return
	 */
	public List<GoodDTO> listGoodsByCategoryId(Integer categoryId) {
		return goodMapper.selectByCategoryId(categoryId);
	}

	/**
	 * 列出所有为物料的商品
	 * @return
	 */
	public List<GoodDTO> listMaterialGoods() {


		return null;
	}

	public List<GoodDTO> getGoodsByMaterialModel(String goodsInfo) {
		
		if (StringUtils.isBlank(goodsInfo)) return new ArrayList<GoodDTO>() ;
		
		Map<String, String> goodsInfoMap = JSON.parseObject(goodsInfo, Map.class);
		Set<String> goodsIds = goodsInfoMap.keySet() ;
		List<Integer> goodsIdList = new ArrayList<Integer>() ;
		for (String id : goodsIds) {
			goodsIdList.add(Integer.valueOf(id)) ;
		}
		
		List<GoodDTO> goods = goodMapper.selectGoodsByMaterialModel(goodsIdList);
		
		return goods ;
	}

	
	
	
}




