package com.jeeplus.modules.business.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.modules.business.dao.GiftPhotoMapper;
import com.jeeplus.modules.business.entity.GiftPhoto;
import com.jeeplus.modules.business.entity.GiftPhotoExample;

@Service
public class GiftPhotoService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	GiftPhotoMapper giftPhotoMapper ;
	
	public List<GiftPhoto> findGiftPhotos(){

		List<GiftPhoto> giftPhotos = giftPhotoMapper.selectByExample(null ) ;
		if (giftPhotos == null) {
			giftPhotos = new  ArrayList<GiftPhoto>() ;
		}
		return giftPhotos ;
	}

	public GiftPhoto getGiftPhoto(Integer id){
		if(id == null || id <= 0) return new GiftPhoto();
		
		GiftPhoto giftPhoto = giftPhotoMapper.selectByPrimaryKey(id) ;
		if (giftPhoto == null) giftPhoto =  new GiftPhoto() ;
		
		return giftPhoto ;
	}

	@Transactional(readOnly=false)
	public int saveGiftPhoto(GiftPhoto giftPhoto){
		int num = 0 ;
		if(giftPhoto == null ){
			logger.error("GiftService.saveGift() param giftPhoto is null!");
		}
		if(giftPhoto.getId() != null && giftPhoto.getId() > 0){
			num = giftPhotoMapper.updateByPrimaryKeySelective(giftPhoto);
		}else{
			num = giftPhotoMapper.insertSelective(giftPhoto);
		}
		return num ;
	}

	@Transactional(readOnly=false)
	public int deleteGiftPhoto(Integer id){
		int num = 0 ;
		if (id != null && id > 0) {
			num = giftPhotoMapper.deleteByPrimaryKey(id);
		}
		return num ;
	}

	@Transactional(readOnly=false)
	public int deleteMultiGiftPhotos(List<Integer> ids){
		int num = 0 ;
		if (ids.size()==0)  return num;
		
		GiftPhotoExample example = new GiftPhotoExample() ;
		example .createCriteria().andIdIn(ids) ;
		num = giftPhotoMapper.deleteByExample(example );
		return num;
	}

}
