package com.jeeplus.modules.business.facade;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.ActionResponse;
import com.jeeplus.modules.business.entity.UserProfile;
import com.jeeplus.modules.business.service.CouponService;
import com.jeeplus.modules.business.service.ImageService;
import com.jeeplus.modules.business.service.UserService;
import com.jeeplus.modules.business.third.zxing.ZXingCode;
import com.jeeplus.modules.business.utils.FileUtil;
import com.jeeplus.modules.business.utils.HttpClient;

/**
 * 优惠券
 * @author afanti
 */
@Controller
public class CouponCtr {
	private Logger logger = LoggerFactory.getLogger(getClass()) ;
	private static String DIR_PATH =  Global.getConfig("upyun.localServer.dirPath"); //本地服务器和upyun的文件目录
	private static String upyunFilePrefix =  Global.getConfig("upyun.imageUrlPrefix"); //upyun文件访问的前缀
	private static String yqhLogo =  Global.getConfig("wx.fwhTwoDimensionCodeLogoPic"); //一起画官方logo
	
	@Autowired
	CouponService couponService ;
	@Autowired
	UserService userService ;
	@Autowired
	ImageService imageService;
	
	
	/**
	 * 生成优惠券分享图，自动附带改微信用户的信息
	 * @param userId 用户ID
	 */
	@RequestMapping(value = "genCouponSharedImg")
	@ResponseBody
	public ActionResponse<String> genCouponSharedImgWithWeiXinInfo(HttpServletRequest request){
		ServletContext context =  request.getSession().getServletContext() ;
		
		String userId = request.getParameter("userId");
		if (StringUtils.isBlank(userId)) {
			return new ActionResponse<String>(1101, "userId参数不可为空");
		}

		//获取用户微信信息
		UserProfile  userProfile  = userService.getUserProfileByPrimaryKey(userId);
		if (StringUtils.isBlank(userProfile.getUserId())){
			return new ActionResponse<String>(4005, "用户尚未绑定微信账号");
		}
		
		//查看优惠券是否已经存在，若存在，则直接返回，否则，生成
		String couponImg  = couponService.findCouponImg(userId, CommonConstants.CouponType.SHARECOUPON.getCode()) ;
		if (StringUtils.isNotBlank(couponImg)) {
			return new ActionResponse<String>(couponImg);
		}
		
		//String nickName = userProfile.getNickname() ; //微信名
		//String avatar  = userProfile.getAvatar() ; //微信头像地址
		
		//将用户微信头像从微信上下载下来，并圆角化，传到又拍云
		String saveTempPath = FileUtil.getTempFilePath(DIR_PATH + "image/temp/") ;
		String avatarSaveName = FileUtil.getTempFileName(".jpg") ;
		
		
		//获取微信头像地址,微信名
		String result = HttpClient.getInstance().sendGetReq("http://api.17hua.me/yqhbsp/insys/getWXUserInfo?user_id="+userId) ;
		Map<String, String> avatarAndName = parseWeiXinResult(result) ;
		FileUtil.downloadImgByUrl(avatarAndName.get("avatar"), saveTempPath , avatarSaveName );
		//更新数据库中的微信头像
		userService.updateAvatar(userId , avatarAndName.get("avatar"));
			
		//下载微信头像图片成功
		String avatarSaveNameUPYun = null ;
		//上传又拍云 ,返回 /upload......
		avatarSaveNameUPYun = couponService.genCircularAvatarAndUploadUPYun(saveTempPath + avatarSaveName, userId) ;
		logger.debug("avatarSaveNameUPYun : " + avatarSaveNameUPYun);
		//avatarSaveNameUPYun = imageService.uploadImage2UPYun( avatarSaveNamePath + avatarSaveName);
		if (null == avatarSaveNameUPYun) {
			return new ActionResponse<String>(1102, "图片处理失败:微信头像上传又拍云");
		}
		
		//:生成一起画官方服务号的二维码，附带userID，传到又拍云
		//执行生成二维码
		String qrCodeContent = Global.getConfig("wx.fwhTwoDimensionCodeContent") + userId ;
		String qrCodeName = saveTempPath + FileUtil.getTempFileName(".png") ;
		String qrCodeNameWithLogo = saveTempPath + FileUtil.getTempFileName(".png") ;
		String logoPicName = context.getRealPath("/static/common/img/20171031001.png");
		
		ZXingCode zXingCode = new ZXingCode() ;
		zXingCode.encoderQRCode(qrCodeName, qrCodeContent, logoPicName, qrCodeNameWithLogo);
		//二维码传到又拍云	
		String tdcSaveNameUPYun = imageService.uploadImage2UPYun(qrCodeNameWithLogo);
		//:~
		
		//生成优惠券分享图，返回给客户端
		String couponBackgroudImg = context.getRealPath("/upload/image/coupon/sharebj2017110901.png");
		couponImg = couponService.genCouponSharedImg(couponBackgroudImg ,userId ,"" , avatarSaveNameUPYun , tdcSaveNameUPYun);
//		couponImg = couponService.genCouponSharedImg(couponBackgroudImg ,userId ,avatarAndName.get("nickname") , avatarSaveNameUPYun , tdcSaveNameUPYun);
		if (StringUtils.isBlank(couponImg)) {
			return new ActionResponse<String>(1102 , "图片处理失败:生成优惠券分享图" );
		}
		//优惠券分享图 保存于库中
		couponService.insertCoupon(userId , upyunFilePrefix + couponImg);
		return new ActionResponse<String>(upyunFilePrefix + couponImg);
		
	}


	private Map<String, String> parseWeiXinResult(String result) {
		Map<String, String> map = new HashMap<String, String>() ;
		JSONObject jo = JSON.parseObject(result) ;
		int errcode = (int)jo.get("errcode") ;
		if (errcode == 200) {
			String recordStr = (String) jo.getString("record") ;
			JSONObject record = JSON.parseObject(recordStr) ;
			map.put("avatar", record.getString("headimgurl")) ;
			map.put("nickname", record.getString("nickname")) ;
		} else {
			logger.error("get user weixin avatar failure : " + jo.get("errmsg"));
			map.put("avatar", yqhLogo) ;
			map.put("nickname", "") ;
		}
		return map ;
	}
	
	
	
	
	
	
	
}
