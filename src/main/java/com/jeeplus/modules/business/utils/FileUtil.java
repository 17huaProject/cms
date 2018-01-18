package com.jeeplus.modules.business.utils;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeeplus.common.utils.DateUtils;


public class FileUtil {
	 private static  Logger logger = LoggerFactory.getLogger(FileUtil.class);
	/**
	 * 通过get请求得到读取器响应数据的数据流
	 * @param url
	 * @return
	 */
    public static InputStream getInputStreamByGet(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url)
                    .openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();
                return inputStream;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 将服务器响应的数据流存到本地文件
    public static void saveData(InputStream is, File file) {
        try (BufferedInputStream bis = new BufferedInputStream(is);
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(file));) {
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
                bos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
    * 图片下载到本地服务器
    * @param fileUrl  文件的完整路径，如http://www.baidu.com/1010.jpg
    * @param savepath 完整的保存路径，如/upload/
    * @param name	  文件名，如/1010.jpg
    */
    public static boolean downloadImgByUrl(String fileUrl,  String savepath, String name){
    	
    	try {
			URL url  = new URL(fileUrl);
			URLConnection connection = url.openConnection() ;
			InputStream is = connection.getInputStream() ;
			byte[] bs = new byte[1024] ;
			int length ;
			File file = new File(savepath);
			if (!file.exists()) {
				file.mkdirs() ;
			}
			
			OutputStream os = new FileOutputStream(savepath + name);
			while ((length = is.read(bs)) != -1) {
				os.write(bs, 0 , length);
			}
			
			os.close();
			is.close();
			return true ;
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false ;
		}
    	
    }
    
    /**
     * 生成临时文件，常用于上传文件时临时保存文件内容
     * @param dirPath
     * @param suffix
     * @return 完整的保存路径，如/upload/201710/1010.jpg
     */
    public static String getTempFilePath(String dirPath ){
    	String year = DateUtils.getYear();
		String month = DateUtils.getMonth();
		String resourcePath = dirPath + year + month + "/" ;
		return resourcePath ;
    }
    public static String getTempFileName(String suffix){
    	String saveName = DateTimeUtils.getRandom16ByCurrentTime() + suffix ;
    	return saveName ;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
