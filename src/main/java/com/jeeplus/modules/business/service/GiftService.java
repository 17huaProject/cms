package com.jeeplus.modules.business.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.dao.GiftMapper;
import com.jeeplus.modules.business.entity.Gift;
import com.jeeplus.modules.business.entity.GiftExample;
import com.jeeplus.modules.business.entity.GiftExample.Criteria;

@Service
public class GiftService {
	private Logger logger = LoggerFactory.getLogger(getClass()) ;
	
	@Autowired
	private GiftMapper giftMapper ;

	public Page<Gift> findGifts(Page<Gift> page, Gift gift){
		gift.setPage(page);
		
		List<Gift> list = giftMapper.select4List(gift ) ;
		return page.setList(list);
	}

	public Gift getGift(String id){
		if (StringUtils.isBlank(id)) {
			return new Gift() ;
		}
		
		return giftMapper.selectByPrimaryKey(id) ;
	}

	@Transactional(readOnly = false)
	public int saveGift(Gift gift){
		int num = 0 ;
		if(gift == null ){
			logger.error("GiftService.saveGift() param gift is null!");
		}
		if(StringUtils.isBlank(gift.getGiftId())){
			gift.preInsert();
			num = giftMapper.insertSelective(gift);
		}else{
			gift.preUpdate();
			num = giftMapper.updateByPrimaryKeySelective(gift);
		}
		return num ;
	}

	@Transactional(readOnly = false)
	public int deleteGift(String id){
		int num = 0 ;
		if (StringUtils.isBlank(id))  return num;
		
		num = giftMapper.deleteLogicalByPrimaryKey(id) ;
		return num;
	}

	@Transactional(readOnly = false)
	public int deleteMultiGifts(List<String> ids){
		int num = 0 ;
		if (ids.size()==0)  return num;
		
		GiftExample example = new GiftExample();
		example.createCriteria().andGiftIdIn(ids) ;
		num = giftMapper.deleteLogicalByExample(example );
		return num;
	}

	
	
	
	
	
}
