package com.jeeplus.modules.business.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.java.com.UpYun;
import main.java.com.UpYun.FolderItem;
import main.java.com.upyun.UpException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;
import com.baidu.ueditor.PathFormat;
import com.jeeplus.common.config.Global;
import com.jeeplus.modules.business.service.ImageService;
import com.jeeplus.modules.business.third.upyun.UPYunUtils;
import com.jeeplus.modules.business.third.upyun.UeditorCallBackEntity;
import com.jeeplus.modules.business.third.upyun.UpYunCallBackEntity;
import com.jeeplus.modules.business.third.upyun.UpYunEntity;
import com.jeeplus.modules.business.third.upyun.UpYunUploader;

@Controller
@RequestMapping("/upyun/")  
public class UpYunCtrl {
	
	private static Log logger = LogFactory.getLog(UpYunCtrl.class);
	
	private String bucketName = Global.getConfig("upyun.bucketName");
	private String userName = Global.getConfig("upyun.operatorName");
	private String password = Global.getConfig("upyun.operatorPwd");
	
	@Autowired
	ImageService imageService;
	
	/**
	 * ueditor 请求
	 * @param upYunSaveFolder
	 * @return
	 * @throws UpException
	 */
	
	@RequestMapping(value = "getUpYunParam", method = RequestMethod.GET, params = { "saveFolder" })
	@ResponseBody
	public 	String getUpYunParam(String saveFolder) throws UpException {
		try {
			saveFolder = URLDecoder.decode(saveFolder, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("getUpYunParam() ==> don't surppot UTF-8");
		}
		UpYunUploader uploader = new UpYunUploader();
		UpYunEntity upYunEntity = uploader.genarateUpyunEntity(saveFolder);
		String resultJSON = JSON.toJSONString(upYunEntity) ;
		logger.debug("getUpYunParam() ==> " + resultJSON);
		return resultJSON;
	}

	/**
	 * upyun 回调函数，将上传的文件下载到本地server
	 * @param upyunImage
	 * @param request
	 * @return
	 */
	@RequestMapping("/callback")
	public @ResponseBody
	UeditorCallBackEntity callBack(UpYunCallBackEntity upyunImage, WebRequest request) {
		//todo 将上传的文件下载到本地server
		boolean flag =  UPYunUtils.readFile(upyunImage.getUrl());
		if (!flag) {
			logger.error("download upyun server image to local server error, upyun " + upyunImage.getUrl());
		}
		//:~
		return new UeditorCallBackEntity(upyunImage);
	}

	/**
	 * 在线管理：ueditor在线图片、在线附件
	 * @param upYunSaveFolder
	 * @return
	 */
	@RequestMapping(value = "onlineFileManager", params = { "upYunSaveFolder" })
	@ResponseBody
	public 	String onlineFileManager(String upYunSaveFolder) {
		UpYun upYun = new UpYun(bucketName, userName, password);
		//格式化路径
		upYunSaveFolder = PathFormat.parse(upYunSaveFolder);
		
		List<FolderItem> list = upYun.readDir(upYunSaveFolder);
		StringBuffer buffer = new StringBuffer();
		if (list != null) {
			for (FolderItem fi : list) {
				buffer.append(upYunSaveFolder);
				buffer.append(fi.name);
				buffer.append("ue_separate_ue");  
			}
		} else {
			logger.info("upyun : there is no onlineFile in direction of " + upYunSaveFolder + " on upyun server");
		}
		String imgStr = buffer.toString();
		if (!imgStr.equals("")) {
			imgStr = imgStr.substring(0, imgStr.lastIndexOf("ue_separate_ue")).replace(File.separator, "/").trim();
		}
		return imgStr;
	}
	
	/**
	 * 先上传文件至本地，再上传到又拍云。用于ueditor多图上传、video上传、附件上传<p>
	 * 注意： <br/>
	 * 1.本地与又拍云的图片名称及地址完全一致 <br/>
	 * 2.又拍云出现故障可以切换至本地
	 * 
	 * @param request
	 * @param response
	 * @param imageFile
	 * @return
	 */
	@RequestMapping(value = "uploadFileUPYun")
	@ResponseBody
	public String uploadMultiImageUPYun(HttpServletRequest request , HttpServletResponse response ,
			@RequestParam(value = "file") MultipartFile file){
		
		if(file.isEmpty()){
			logger.debug("file is empty!");
			return "{\"state\":\"Error\",\"url\":\"\"}";
		}
		String fileType = request.getParameter("fileType") ;
		return imageService.uploadImage2UPYun(file, fileType);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}