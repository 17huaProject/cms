package com.jeeplus.modules.business.service;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONArray;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.ImageUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.third.upyun.UPYunUtils;
import com.jeeplus.modules.business.utils.DateTimeUtils;
import com.jeeplus.modules.business.utils.FileUtils;



@Service
public class ImageService {
	/**
	 * 上传、裁剪、压缩图片
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param rotate
	 * @param realPath
	 * @param resourcePath
	 * @param thumbnailSize
	 * @param imageFile
	 * @return 成功 ：返回裁剪后的图片绝对路径 ， 失败：1102
	 */
	private static String imageUrlPrefix = Global.getConfig("upyun.imageUrlPrefix") ;
    private static String DIR_PATH =  Global.getConfig("upyun.localServer.dirPath"); //本地服务器和upyun的文件目录
	
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Deprecated
	public String uploadCutScaleImage(
			Integer x, Integer y, Integer width, Integer height,Integer rotate,
			String realPath, String resourcePath , JSONArray thumbnailSize ,
			MultipartFile imageFile){
		
	    System.out.println("x:"+x+", y:"+y+", width:"+width+", height:"+height+", rotate:"+rotate);
		System.out.println(realPath);
		
		String imagePrefix = String.valueOf(new Date().getTime());
		String imageSuffix = ".jpg" ;
	
		 if (imageFile!=null) {
			 try{
				 
				 String year = DateUtils.getYear();
				 String month = DateUtils.getMonth();
				 resourcePath += "/"+year+"/"+month+"/";
				 
				 File dir = new File( realPath + resourcePath );
				 if(!dir.exists()){
					 dir.mkdirs();
				 }
				 
				 //先把用户上传到原图保存到服务器上
				 File file = new File(dir,imagePrefix+"_original"+imageSuffix);
				 imageFile.transferTo(file);
				 
				 if(file.exists()){
					 String src = realPath + resourcePath + imagePrefix;
					 int thumbnailLength = thumbnailSize.size() ;
					 boolean[] flag = new boolean[thumbnailLength];
					 //旋转后剪裁图片
					 flag[0] = ImageUtils.cutAndRotateImage(src+"_original"+imageSuffix, src+imageSuffix, x, y, width, height, rotate);
					 
					 JSONArray each = null;
					 
					 for (int i=0; i<thumbnailLength; i++) {
						 each = thumbnailSize.getJSONArray(i) ;
						 //缩放图片,生成不同大小的图片，应用于不同的大小显示
						 flag[i] = ImageUtils.scale2(src+imageSuffix, 
								 src + "_" + each.getIntValue(0) + "*" + each.getIntValue(1) + imageSuffix , 
								 each.getIntValue(0), each.getIntValue(1), true);
					 }
					 
					 if (!ArrayUtils.contains(flag, false)) {
						 return src + imageSuffix;
					 }else{
						 return "1102";
					 }
				 }
			 }catch (Exception e) {
				 return "1102";
			}

		 }
		return "1102";
	}

	/**
	 * 使用又拍云 上传、裁剪、压缩图片
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param rotate
	 * @param thumbnailSize 缩略图的大小数组 [[50,50]],在properties中配置, <br/>
	 * 						若使用UPyun压缩版本(exp:!5050)，请设置为null ,请求：http://xxx.com/1502889056134620.jpg!5050
	 * @param imageFile
	 * @return 成功 ：返回裁剪后的图片绝对路径 ， 失败：1102
	 */
	public String uploadCutScaleImage(Integer x, Integer y, Integer width, Integer height,Integer rotate,
			JSONArray thumbnailSize , MultipartFile imageFile) {
		
		if (imageFile!=null && !imageFile.isEmpty()) {
			 try{
				 String year = DateUtils.getYear();
				 String month = DateUtils.getMonth();
				 String resourcePath = DIR_PATH + "image/" + year + month + "/" ;
//				 String resourcePath = DIR_PATH + "image" + File.separator + year + month + File.separator ;
				 
				 File dir = FileUtils.createDirs(resourcePath);
		
				 String imageName = DateTimeUtils.getRandom16ByCurrentTime() + "." + FileUtils.getFileSuffix(imageFile.getOriginalFilename()) ;
				 
				 //先把用户上传到原图保存到服务器上,上传upyun后，删除
				 File file = new File(dir, imageName);
				 file.setWritable(true, false);
				 
				 imageFile.transferTo(file);
				 
				 if (file.exists()) {
					 String localFilePath = resourcePath + imageName ;
					 
					 //旋转后剪裁图片
					 String upyunFilePath = UPYunUtils.crop(localFilePath, x, y, width, height) ;
					 boolean cropFlag = false ;
					 //upyun剪裁图片成功,保存至本地服务器
					 if (StringUtils.isNotBlank(upyunFilePath)) { 
						 file.delete();
						 cropFlag =  UPYunUtils.readFile(localFilePath);
					 }
					 
					 //裁剪上传成功，且已配置了压缩尺寸
					 if ( thumbnailSize != null && cropFlag ) {
						 int thumbnailLength = thumbnailSize.size() ;
						 boolean[] flag = new boolean[thumbnailLength];
						 Arrays.fill(flag, false);
						 flag[0] = cropFlag ;
						 
						 JSONArray each = null;
						 String scaleImageSize = null ;
						 String scaleImagePath = null ; 
						 for (int i = 0; i < thumbnailLength; i++) {
							 each = thumbnailSize.getJSONArray(i) ;
							 //缩放图片,生成不同大小的图片，应用于不同的大小显示
							 scaleImageSize = each.getIntValue(0) + "" + each.getIntValue(1) ;
							 
							 //待解决 http  400
							 scaleImagePath =  UPYunUtils.gmkerl(localFilePath, scaleImageSize);
							 //upyun缩略图生成成功,保存至本地服务器
							 if (StringUtils.isNotBlank(scaleImagePath)) { 
								 flag[i+1] = UPYunUtils.readFile(scaleImagePath);
							 }else{
								 flag[i+1] = false ;
							 }
						 }
						 //图片处理成功
						 if (!ArrayUtils.contains(flag, false)) {
							 return imageUrlPrefix + FileUtils.getUPYunFilePath(localFilePath);
						 } else {
							 return "1102";
						 }
					 }
					//图片处理成功
					 if (cropFlag) {
						 return imageUrlPrefix + FileUtils.getUPYunFilePath(localFilePath);
					 }
					 
				 }
			 }catch (Exception e) {
				 return "1102";
			 }
		 }
		return "1102";
	}
	
	
	
	/**
	 * 使用又拍云 上传图片
	 * @return 成功 ：返回裁剪后的图片绝对路径 ， 失败：1102
	 */
	public String uploadImage2UPYun(MultipartFile uploadFile, String fileType) {
		
		if (uploadFile!=null && !uploadFile.isEmpty()) {
			try{
				String year = DateUtils.getYear();
				String month = DateUtils.getMonth();
				String resourcePath = DIR_PATH + fileType + "/" + year + month + "/" ;
//				 String resourcePath = DIR_PATH + "image" + File.separator + year + month + File.separator ;
				
				File dir = FileUtils.createDirs(resourcePath);
				
				String imageName = DateTimeUtils.getRandom16ByCurrentTime() + "." + FileUtils.getFileSuffix(uploadFile.getOriginalFilename()) ;
				
				//先把用户上传到原图保存到服务器上
				File file = new File(dir, imageName);
				file.setWritable(true, false);
				
				uploadFile.transferTo(file);
				
				if (file.exists()) {
					String localFilePath = resourcePath + imageName ;
					
					String upyunFilePath = UPYunUtils.writeFile(localFilePath);
					//upyun图片成功
					if (StringUtils.isNotBlank(upyunFilePath)) { 
						String url = FileUtils.getUPYunFilePath(localFilePath) ;
						return "{\"state\":\"SUCCESS\",\"url\":\""+url+"\"}"; 
					}
				}
			}catch (Exception e) {
				return "{\"state\":\"Error\",\"url\":\"\"}";
			}
		}
		return "{\"state\":\"Error\",\"url\":\"\"}";
	}
	
	/**
	 * 使用又拍云 上传图片
	 * @param fileName  完整的文件路径，如/upload/1010.jpg
	 * @return 成功 ：返回图片绝对路径 ，如/upload/1010.jpg， 失败：null
	 */
	public String uploadImage2UPYun(String fileName) {
		File file = new File(fileName);
		file.setWritable(true, false);
		if (file.exists()) {
			
			String upyunFilePath = UPYunUtils.writeFile(fileName);
			//upyun图片成功
			if (StringUtils.isNotBlank(upyunFilePath)) { 
				return FileUtils.getUPYunFilePath(fileName) ;
			}
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
