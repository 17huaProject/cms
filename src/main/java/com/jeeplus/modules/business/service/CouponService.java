package com.jeeplus.modules.business.service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import main.java.com.upyun.Base64Coder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.modules.business.dao.UserCouponMapper;
import com.jeeplus.modules.business.dao.UserCustomMapper;
import com.jeeplus.modules.business.entity.UserCoupon;
import com.jeeplus.modules.business.entity.UserCustom;
import com.jeeplus.modules.business.entity.UserCustomExample;
import com.jeeplus.modules.business.entity.UserCustomExample.Criteria;
import com.jeeplus.modules.business.third.upyun.UPYunUtils;
import com.jeeplus.modules.business.utils.BusinessStringUtils;

/**
 * 优惠券
 * @author afanti
 *
 */
@Service
public class CouponService {
	private static Log logger = LogFactory.getLog(CouponService.class);
	
	private static String DIR_PATH =  Global.getConfig("upyun.imageUrlPrefix");
	
	@Autowired
	private UserCustomMapper userCustomMapper;
	
	@Autowired
	private UserCouponMapper userCouponMapper ;
	
	public String findCouponImg(String userId , String couponType){
		UserCoupon userCoupon = userCouponMapper.selectCouponImg(userId , couponType);
		if (userCoupon == null) return null ;
		return userCoupon.getCouponName() ;
	}
	
	/**
	 * 将用户微信头像从微信上下载下来，并圆角化，传到又拍云
	 * @return
	 */
	public String genCircularAvatarAndUploadUPYun(String avatar, String userId){
		
		String param = "/sq/84/border/2x2/brdcolor/FFFFFF00"; 
//		String param = "/sq/232/clip/232x232a0a0/gravity/center/roundrect/100/canvas/232x232/cvscolor/FFD20000"; 
		Map<String, String> extParams = new HashMap<String, String>() ;
		extParams.put("x-gmkerl-thumb", param);
		return UPYunUtils.uploadImgAndDeal(avatar , userId , extParams ) ;
		
	}
	
	/**
	 * 生成优惠券分享图，返回给客户端  FORM API<br/>
	 * 参考：https://docs.upyun.com/cloud/image/#url (URL作图)
	 * @param nickName			微信昵称
	 * @param avatarPathOfUPYun	头像地址	以/option/....开头，不可加http
	 * @param tdcPathOfUPYun	二维码地址	以/option/....开头，不可加http
	 */
	public String genCouponSharedImg(String couponBackgroudImg , String userId, String nickName, String avatarPathOfUPYun, String tdcPathOfUPYun) {
		
		logger.debug("avatarPathOfUPYun == > " + avatarPathOfUPYun);
		
		String param = genCouponDealParam(nickName, avatarPathOfUPYun, tdcPathOfUPYun) ;
		
		Map<String, String> extParams = new HashMap<String, String>() ;
		extParams.put("x-gmkerl-thumb", param);
		
		return UPYunUtils.uploadImgAndDeal(couponBackgroudImg , userId , extParams ) ;
		
	}
	

	/**
	 * 获取优惠券 水印参数
	 * @param nickName
	 * @param avatarPathOfUPYun	头像地址	以/option/....开头，不可加http
	 * @param tdcPathOfUPYun	二维码地址	以/option/....开头，不可加http
	 */
	private String genCouponDealParam(String nickName, String avatarPathOfUPYun, String tdcPathOfUPYun){
		
		String nickNameBase64 = BusinessStringUtils.replaceBackslant4UPYun(Base64Coder.encodeString(nickName)) ;
		String avatarPathBase64 = BusinessStringUtils.replaceBackslant4UPYun(Base64Coder.encodeString(avatarPathOfUPYun)) ;
		String tdcPathBase64 = BusinessStringUtils.replaceBackslant4UPYun(Base64Coder.encodeString(tdcPathOfUPYun)) ;

		String param = 
				//"/watermark/text/"+nickNameBase64+"/align/northwest/margin/430x200/size/48/font/simhei/color/232323"+
				"/watermark/url/"+tdcPathBase64+"/align/northwest/margin/104x944"+
				"/watermark/url/"+avatarPathBase64+"/align/northwest/margin/60x108";
		
		logger.debug("genCouponDealParam() ==> "+param);
		
		return param ;
	}
	
	/**
	 * 参考：https://docs.upyun.com/cloud/image/#url (URL作图)
	 * @param nickName
	 * @param avatarPathOfUPYun	头像地址	以/option/....开头，不可加http
	 * @param tdcPathOfUPYun	二维码地址	以/option/....开头，不可加http
	 */
	private String genCouponByURLWay(String nickName, String avatarPathOfUPYun, String tdcPathOfUPYun){
		String backgroudImg = DIR_PATH + "/upload/image/201710/201710271733.png" ;
		
		String nickNameBase64 = BusinessStringUtils.replaceBackslant4UPYun(Base64Coder.encodeString(nickName)) ;
		String avatarPathBase64 = BusinessStringUtils.replaceBackslant4UPYun(Base64Coder.encodeString(avatarPathOfUPYun+"!/fwfh/232x232")) ;
		String tdcPathBase64 = BusinessStringUtils.replaceBackslant4UPYun(Base64Coder.encodeString(tdcPathOfUPYun)) ;
		
		String requestUrl = backgroudImg + 
				"!/watermark/text/"+nickNameBase64+"/align/northwest/margin/430x200/size/48/font/simhei/color/232323"+
				"/watermark/url/"+tdcPathBase64+"/align/northwest/margin/245x1870"+
				"/watermark/url/"+avatarPathBase64+"/align/northwest/margin/150x275";
		
		return requestUrl ;
	}


	/*
	 *更新优惠券分享图
	 */
	public void insertCoupon(String userId, String couponImg) {
		UserCoupon record = new UserCoupon();
		record.setUserId(userId);
		record.setCouponType(CommonConstants.CouponType.SHARECOUPON.getCode());
		record.setCouponName(couponImg);
		record.setCreateTime(new Date());
		userCouponMapper.insert(record );
	}
	
	
	
	
	
	
	
	
}
