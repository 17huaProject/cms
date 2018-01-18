package com.jeeplus.modules.business.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.ActionResponse;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.business.service.ImageService;

@Controller
@RequestMapping(value = "${adminPath}/wms/image")
public class ImageCtrl extends BaseController{

	
	@Autowired
	ImageService imageService;
	
	@RequestMapping(value = "index")
	public String index(HttpServletRequest request , HttpServletResponse response){
		return "modules/image/imageCropper";
	}


	/**
	 * 先上传原图至本地，再上传到又拍云，进行裁剪、缩略图、存储，并将裁剪图、缩略图下载到本地存储。<p>
	 * 注意： <br/>
	 * 1.本地与又拍云的图片名称及地址完全一致 <br/>
	 * 2.又拍云出现故障可以切换至本地
	 * 
	 * @param request
	 * @param response
	 * @param imageFile
	 * @return
	 */
	@RequestMapping(value = "uploadImageUPYun")
	@ResponseBody
	public String uploadImageUPYun(HttpServletRequest request , HttpServletResponse response ,
			@RequestParam(value = "file") MultipartFile imageFile){
		
		if(imageFile.isEmpty())	logger.debug("image is empty!");
		
		/*
		String type = request.getParameter("type");
		
		if (StringUtils.isBlank(type)) {
			logger.error("type参数不可为空");
			return JSON.toJSONString(new ActionResponse(1101,type+"参数不可为空"));
		}
		 */
		//指定不同模块的图片缩略图的个数及对应尺寸
		//JSONArray thumbnailSize = JSON.parseArray(Global.getConfig("scaleImageSize."+type));
		
		String result = imageService.uploadCutScaleImage(
				Double.valueOf(request.getParameter("x")).intValue() ,
				Double.valueOf(request.getParameter("y")).intValue() ,
				Double.valueOf(request.getParameter("width")).intValue() ,
				Double.valueOf(request.getParameter("height")).intValue() ,
				0,
				null, imageFile);
		
		if ("1102".equals(result)) {
			return JSON.toJSONString(new ActionResponse(1102,"图片处理失败"));
		}
		
		return JSON.toJSONString(new ActionResponse(result));
		
		
	}
	
	@RequestMapping(value = "uploadImage")
	@ResponseBody
	public String uploadImage(HttpServletRequest request , HttpServletResponse response ,
			@RequestParam(value = "file") MultipartFile imageFile){
		
		if(!imageFile.isEmpty()){
			logger.debug(String.valueOf(imageFile.getSize()));
		}
		String type = request.getParameter("type");
		logger.debug("type : "+type);
		
		if (StringUtils.isBlank(type)) {
			logger.error("type参数不可为空");
			return JSON.toJSONString(new ActionResponse(1101,type+"参数不可为空"));
		}
		
		/*String imageData = request.getParameter("imageData");
		logger.debug("imageData : "+imageData);
		ImageDataDTO imageDataDTO = JSON.parseObject(imageData , ImageDataDTO.class);
		if (imageDataDTO == null) {
			logger.error("json解析为ImageDataDTO对象失败");
			return JSON.toJSONString(new ActionResponse(1103,"json解析为ImageDataDTO对象失败"));
		}*/
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String resourcePath =  Global.USERFILES_BASE_URL + "image/" + type.trim() ;
		
		//指定不同模块的图片缩略图的个数及对应尺寸
		JSONArray thumbnailSize = JSON.parseArray(Global.getConfig("scaleImageSize."+type));
		
		String result = imageService.uploadCutScaleImage(
				Double.valueOf(request.getParameter("x")).intValue() ,
				Double.valueOf(request.getParameter("y")).intValue() ,
				Double.valueOf(request.getParameter("width")).intValue() ,
				Double.valueOf(request.getParameter("height")).intValue() ,
				0,
				realPath, resourcePath, thumbnailSize, imageFile);
		
		if ("1102".equals(result)) {
			return JSON.toJSONString(new ActionResponse(1102,"图片处理失败"));
		}

		return JSON.toJSONString(new ActionResponse(result));
		
	}
	
/*	
	@RequestMapping(value = "uploadImage")
	@ResponseBody
	public String uploadImage(HttpServletRequest request , HttpServletResponse response ,
			@RequestParam(value = "file") MultipartFile imageFile){
		logger.debug("imageFile : "+imageFile);
		
		String type = request.getParameter("type");
		logger.debug("type : "+type);
		
		if (StringUtils.isBlank(type)) {
			logger.error("type参数不可为空");
			return JSON.toJSONString(new ActionResponse(1101,type+"参数不可为空"));
		}
		
		String imageData = request.getParameter("imageData");
		logger.debug("imageData : "+imageData);
		ImageDataDTO imageDataDTO = JSON.parseObject(imageData , ImageDataDTO.class);
		if (imageDataDTO == null) {
			logger.error("json解析为ImageDataDTO对象失败");
			return JSON.toJSONString(new ActionResponse(1103,"json解析为ImageDataDTO对象失败"));
		}
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String resourcePath =  Global.USERFILES_BASE_URL + "image/" + type.trim() ;
		
		
		JSONArray thumbnailSize = JSON.parseArray(Global.getConfig("scaleImageSize."+type));
		
		String result = imageService.uploadCutScaleImage(
				imageDataDTO.getX(), 
				imageDataDTO.getY(), 
				imageDataDTO.getWidth(), 
				imageDataDTO.getHeight(), 
				imageDataDTO.getRotate(),
				realPath, resourcePath, thumbnailSize, imageFile);
		
		if ("1102".equals(result)) {
			return JSON.toJSONString(new ActionResponse(1102,"图片处理失败"));
		}
		
		return JSON.toJSONString(new ActionResponse(result));
		
	}
	
*/	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
