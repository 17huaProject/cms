package com.jeeplus.modules.business.utils;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeplus.common.utils.StringUtils;

public class FileUtils {
	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
	/**
	 * 获取文件的目录路径
	 * <p>
	 * example: <br/>
	 * fileName = "/upload/image/20140814/12542456.jpg" or \\20140814\\12542456.jpg or 12542456.jpg<br/>
	 * return = /upload/image/20140814/   or \\20140814\\ or  /
	 */
	public static String getDirPath(File file){
		String fileName = file.getName();  
		return getDirPath(fileName);
	}
	/**
	 * 获取文件的目录路径
	 * <p>
	 * example: <br/>
	 * fileName = "/upload/image/20140814/12542456.jpg" or \\20140814\\12542456.jpg or 12542456.jpg<br/>
	 * return = /upload/image/20140814/   or \\20140814\\ or  /
	 */
	public static String getDirPath(String fileName) {
		int endIndex = 0 ;
		if (fileName.contains("/")) {
			endIndex = fileName.lastIndexOf("/")+1 ;
		} else if (fileName.contains("\\")) {
			endIndex = fileName.lastIndexOf("\\")+1 ;
		} else {
			return "/";
		}
		return fileName.substring(0 , endIndex); 
	}
	/**
	 * 获取文件名的后缀 
	 * <p>
	 * 如：jpg | JPEG... 
	 * @param file
	 * @return 
	 */
	public static String getFileSuffix(File file){
		String fileName = file.getName();  
		return getFileSuffix(fileName);
	}
	
	/**
	 * 获取文件名的后缀 
	 * <p>
	 * 如：jpg | JPEG... 
	 */
	public static String getFileSuffix(String fileName) {
		 String suffix = fileName.substring(fileName.lastIndexOf(".") + 1); 
	     return suffix ;
	}
	/**
	 * 获取文件名
	 * <p>
	 * example: <br/>
	 * fileName = "/upload/image/20140814/12542456.jpg" or \\20140814\\12542456.jpg or 12542456.jpg<br/>
	 * return = 12542456
	 * @param file
	 * @return 
	 */
	public static String getFileSupfix(File file){
		String fileName = file.getName();  
		return getFileName(fileName);
	}
	
	/**
	 * 获取文件名 
	 * <p>
	 * example: <br/>
	 * fileName = "/upload/image/20140814/12542456.jpg" or \\20140814\\12542456.jpg or 12542456.jpg<br/>
	 * return = 12542456
	 */
	public static String getFileName(String fileName) {
		String name = null ;
		int beginIndex = 0 ;
		if (fileName.contains("/")) {
			beginIndex = fileName.lastIndexOf("/")+1 ;
		} else if (fileName.contains("\\")) {
			beginIndex = fileName.lastIndexOf("\\")+1 ;
		}
		name = fileName.substring(beginIndex , fileName.lastIndexOf(".")); 
		return  name ;
	}
	
	/**
	 * 根据图片名，获取缩略图的文件名(绝对路径)<p>
	 * example: <br/>
	 * fileName = "/upload/image/20140814/12542456.jpg" <br/>
	 * scaleImageSize = "150x150" <br/>
	 * return = /upload/image/20140814/12542456_150x150.jpg <br/>
	 * @param fileName 			文件名，绝对路径
	 * @param scaleImageSize 	缩略图尺寸
	 * @return
	 */
	public static String  getScaleFilePath(String fileName, String scaleImageSize){
		String supfix = fileName.substring(0,fileName.lastIndexOf(".")); 
		String suffix = fileName.substring(fileName.lastIndexOf(".")); 
		return supfix  + scaleImageSize + suffix ;
	}

	/**
	 * 创建多级目录，若目录不存在，则创建
	 * @param direName
	 * @return direName is null, return null
	 */
	public static File createDirs(String direName){
		File dir = null ;
		if (StringUtils.isBlank(direName)) return dir ;

		try{
			dir = new File(direName);
			if (!dir.exists()) {
				dir.setWritable(true, false);
				dir.mkdirs();
			}
		}catch(Exception e){
			logger.error("FileUtils.createDirs() happens error : "+e.getMessage());
		}
		 return dir ;
	}
	
	/**
	 * 创建文件（完整路径）
	 * @param direName 	目录
	 * @param fileName	文件名称
	 * @return
	 */
	public static File createFile(String direName , String fileName){
		createDirs(direName);
		File file = new File(direName+fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return file ;
	}
	
	
	
	/**
	 * 创建多级目录，若目录不存在，则创建
	 */
	public static String getUPYunFilePath(String filePath){
		return filePath.substring(filePath.indexOf("/upload"));
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
}
