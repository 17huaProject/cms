package com.jeeplus.modules.business.third.upyun;

import java.util.*;
import main.java.com.upyun.UpException;
import main.java.com.upyun.UpYunUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jeeplus.common.config.Global;

/**
 * UEditor文件上传辅助类,上传至又拍云。
 * 
 */
public class UpYunUploader {

	private static Log logger = LogFactory.getLog(UpYunUploader.class);
    //回调的方法，见又拍云api
	private String callBackWay = Global.getConfig("upyun.callBackWay");
    //回调时调用路径，注意使用绝对路径
	private String callBackUrl = Global.getConfig("upyun.callBackUrl");
    //又拍云的bucket
	private String bucket = Global.getConfig("upyun.bucketName");
	private String operatorPwd = Global.getConfig("upyun.operatorPwd");
	private String operator = Global.getConfig("upyun.operatorName");
    //又拍云bucket中的apikey
	private String apiKey = Global.getConfig("upyun.apiKey");

	/**
	 * 注意：只能使用return-url同步方式。异步方式，ueditor iframe无法跨域
	 * @param saveKey  文件保存路径，可用占位符  参见：http://docs.upyun.com/api/form_api/#save-key
	 * @return
	 * @throws UpException
	 */
	public UpYunEntity genarateUpyunEntity(String saveFolder)throws UpException {
		
		String expiration = ((Long) (System.currentTimeMillis() / 1000 + 1000 * 60 * 30)).toString(); // 过期时间：30分钟
		String saveKey = getSaveKey(saveFolder, "{random32}{.suffix}");
		
		Map<String, Object> policyParamMap = new HashMap<String, Object>();
		policyParamMap.put("bucket", bucket);
		policyParamMap.put("save-key", saveKey);
		policyParamMap.put("expiration", expiration);
		policyParamMap.put("return-url", callBackUrl);
//		policyParamMap.put("notify-url", callBackUrl);

		String policy = UpYunUtils.getPolicy(policyParamMap );
		
//		String signature = UpYunUtils.sign("POST", null, saveKey, bucket, UpYunUtils.md5(operatorPwd), null);
		String signature = UpYunUtils.getSignature(policy, apiKey);
		
		return new UpYunEntity(bucket, operator, policy, signature, callBackUrl,expiration);

	}
	
	public String getSaveKey(String saveFloder, String pattern) {
		return "/" + saveFloder + "/" + pattern;
//		return File.separator + saveFloder + File.separator + pattern;
	}


}