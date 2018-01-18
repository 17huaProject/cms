package com.jeeplus.modules.business.third.upyun;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import main.java.com.UpYun;
import main.java.com.UpYun.PARAMS;
import main.java.com.upyun.*;
import com.jeeplus.common.config.Global;
import com.jeeplus.modules.business.utils.FileUtils;

/**
 * 又拍云图片类空间 
 * <p>
 * 注意：直接使用部分图片处理功能后，将会丢弃原图保存处理后的图片
 * 
 */
public class UPYunUtils {
	
    private static String BUCKET_NAME ;   //云存储服务名称
    private static String OPERATOR_NAME ; //操作员
    private static String OPERATOR_NWD ;  //密码
    
    private static UpYun upyun ;
    
	
    private static Logger logger = LoggerFactory.getLogger(UPYunUtils.class);
    
	static{
		BUCKET_NAME = Global.getConfig("upyun.bucketName");
		OPERATOR_NAME =  Global.getConfig("upyun.operatorName");
		OPERATOR_NWD =  Global.getConfig("upyun.operatorPwd");
		
		// 初始化空间
		upyun = new UpYun(BUCKET_NAME, OPERATOR_NAME, OPERATOR_NWD);

		// 切换 API 接口的域名接入点，默认为自动识别接入点
		// upyun.setApiDomain(UpYun.ED_AUTO);

		// 设置连接超时时间，默认为30秒
		// upyun.setTimeout(60);

		// 设置是否开启debug模式，默认不开启
		upyun.setDebug(true);
	}
	
    /**
     * 表单上传文件
     */
    public static Result formUpload(String filepath){
        final Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put(Params.SAVE_KEY, filepath);  //保存路径 必须设置该参数

        FormUploader uploader = new FormUploader(BUCKET_NAME, OPERATOR_NAME, OPERATOR_NWD);
        File file = new File(filepath);
        Result result = null ;
		try {
			result = uploader.upload(paramsMap, file);
		} catch (Exception e) {
			logger.error("UPYunUtils.writeFile() happen error : " + e.getMessage());
		}
		return result ;
    }
	
    /**
	 * 上传文件
	 * @param localFilePath 本地文件路径
	 * @return remoteFilePath 传到upyun后的文件路径 , 出现异常，返回null
	 */
	public static String writeFile(String localFilePath) {

		boolean result = false;
		// 要传到upyun后的文件路径
		String filePath = FileUtils.getUPYunFilePath(localFilePath);

		// 本地待上传的图片文件
		File file = new File(localFilePath);

		try {
			// 设置待上传文件的 Content-MD5 值
			// 如果又拍云服务端收到的文件MD5值与用户设置的不一致，将回报 406 NotAcceptable 错误
			upyun.setContentMD5(UpYun.md5(file));
	
			// 设置待上传文件的"访问密钥"
			// 注意：
			// 仅支持图片空！，设置密钥后，无法根据原文件URL直接访问，需带URL后面加上（缩略图间隔标志符+密钥）进行访问
			// 举例：
			// 如果缩略图间隔标志符为"!"，密钥为"bac"，上传文件路径为"/folder/test.jpg"，
			// 那么该图片的对外访问地址为：http://空间域名 /folder/test.jpg!bac
			// 代码示例：upyun.setFileSecret("bac");
	
			// 上传文件，并自动创建父级目录（最多10级）
			result = upyun.writeFile(filePath, file, true);
		} catch (IOException e) {
			logger.error("UPYunUtils.writeFile() IOException : " + e.getMessage());
		}
		
		if (result) {
			return filePath ;
		} else {
			logger.error(filePath + " upload " + isSuccess(result));
			return null;
		}
	}
	
	/**
	 * 上传图片，并进行处理
	 * @param localFilePath 本地文件路径
	 * @return remoteFilePath 传到upyun后的文件路径 , 出现异常，返回null
	 */
	public static String uploadImgAndDeal(String localFilePath , String userId, Map<String, String> params) {
		
		boolean result = false;
		// 要传到upyun后的文件路径
		String filePrefix = localFilePath.substring(localFilePath.indexOf("/upload"), localFilePath.lastIndexOf("/")+1) ;
		String fileSuffix = localFilePath.substring(localFilePath.lastIndexOf(".")) ;
		String filePath = filePrefix + userId + fileSuffix ;
		
		// 本地待上传的图片文件
		File file = new File(localFilePath);
		
		try {
			// 设置待上传文件的 Content-MD5 值
			// 如果又拍云服务端收到的文件MD5值与用户设置的不一致，将回报 406 NotAcceptable 错误
			upyun.setContentMD5(UpYun.md5(file));
			
			// 上传文件，并自动创建父级目录（最多10级）
			result = upyun.writeFile(filePath, file, true, params);
		} catch (IOException e) {
			logger.error("UPYunUtils.writeFile() IOException : " + e.getMessage());
		}
		
		if (result) {
			return filePath ;
		} else {
			logger.error(filePath + " upload image and deal " + isSuccess(result));
			return null;
		}
	}

	/**
	 * 图片做缩略图
	 * <p>
	 * 注意：若使用了缩略图功能，则会丢弃原图
	 * 
	 * @param localFilePath 本地文件绝对路径
	 * @param scaleImageSize 缩略图大小  ， example："150x150"
	 * @return remoteFilePath 传到upyun后的文件路径 , 出现异常，返回null
	 */
	public static String gmkerl(String localFilePath, String scaleImageSize){

		// 要传到upyun后的文件路径
		String filePath = FileUtils.getScaleFilePath(FileUtils.getUPYunFilePath(localFilePath), scaleImageSize);
		

		// 本地待上传的图片文件
		File file = new File(localFilePath);

		// 设置缩略图的参数
		Map<String, String> params = new HashMap<String, String>();

		// 设置缩略图类型，必须搭配缩略图参数值（KEY_VALUE）使用，否则无效
		params.put(PARAMS.KEY_X_GMKERL_TYPE.getValue(), PARAMS.VALUE_FIX_BOTH.getValue());

		// 设置缩略图参数值，必须搭配缩略图类型（KEY_TYPE）使用，否则无效
		params.put(PARAMS.KEY_X_GMKERL_VALUE.getValue(), scaleImageSize);

		// 设置缩略图的质量，默认 95
		params.put(PARAMS.KEY_X_GMKERL_QUALITY.getValue(), "95");

		// 设置缩略图的锐化，默认锐化（true）
		params.put(PARAMS.KEY_X_GMKERL_UNSHARP.getValue(), "true");

		// 若在 upyun 后台配置过缩略图版本号，则可以设置缩略图的版本名称
		// 注意：只有存在缩略图版本名称，才会按照配置参数制作缩略图，否则无效
		//params.put(PARAMS.KEY_X_GMKERL_THUMBNAIL.getValue(), "small");

		// 上传文件，并自动创建父级目录（最多10级）
		boolean result = false;
		try {
			result = upyun.writeFile(filePath, file, true, params);
		} catch (Exception e) {
			logger.error("UPYunUtils.gmkerl() Exception : " + e.getMessage());
		}

		if (!result) {
			logger.error("UPYunUtils.gmkerl() : " + filePath + " make gmkerl " + isSuccess(result));
			return null;
		} else {
			return filePath ;
		}
		
	}

	/**
	 * 图片旋转
	 * @return remoteFilePath 传到upyun后的文件路径 , 出现异常，返回null
	 */
	public static String rotate(String localFilePath){

		// 要传到upyun后的文件路径
		String filePath = FileUtils.getUPYunFilePath(localFilePath) ;

		// 本地待上传的图片文件
		File file = new File(localFilePath);

		// 图片旋转功能具体可参考：http://wiki.upyun.com/index.php?title=图片旋转
		Map<String, String> params = new HashMap<String, String>();

		// 设置图片旋转：只接受"auto"，"90"，"180"，"270"四种参数
		params.put(	PARAMS.KEY_X_GMKERL_ROTATE.getValue(),
					PARAMS.VALUE_ROTATE_90.getValue());

		// 上传文件，并自动创建父级目录（最多10级）
		boolean result = false;
		try {
			result = upyun.writeFile(filePath, file, true, params);
		} catch (IOException e) {
			logger.error("UPYunUtils.rotate() IOException : " + e.getMessage());
		}

		if (!result) {
			logger.error("UPYunUtils.rotate() : " + filePath + " image rotate " + isSuccess(result));
			return null;
		} else {
			return filePath ;
		}
		
	}

	/**
	 * 图片裁剪
	 * @return remoteFilePath 传到upyun后的文件路径 , 出现异常，返回null
	 */
	public static String crop(String localFilePath,int x, int y, int width, int height){

		// 要传到upyun后的文件路径
		String filePath = FileUtils.getUPYunFilePath(localFilePath) ;

		// 本地待上传的图片文件
		File file = new File(localFilePath); 

		// 图片裁剪功能具体可参考：http://wiki.upyun.com/index.php?title=图片裁剪
		Map<String, String> params = new HashMap<String, String>();

		// 设置图片裁剪，参数格式：x,y,width,height
		String imgSize = x + "," + y + "," + width + "," + height ;
		params.put(PARAMS.KEY_X_GMKERL_CROP.getValue(), imgSize);

		// 上传文件，并自动创建父级目录（最多10级）
		boolean result = false ;
		try {
			result = upyun.writeFile(filePath, file, true, params);
		} catch (IOException e) {
			logger.error("UPYunUtils.crop() IOException : " + e.getMessage());
		}

		if (!result) {
			logger.error("UPYunUtils.crop() : " + filePath + " image crop " + isSuccess(result));
			return null;
		} else {
			return filePath ;
		}
	}
	
	/**
	 * 读取文件/下载文件
	 */
	public static boolean readFile(String localFilePath){
		// upyun空间下存在的文件的路径
		String filePath = FileUtils.getUPYunFilePath(localFilePath) ;
		//下载文件，采用数据流模式下载文件（节省内存）
		// 要写入的本地临时文件
		File file = null ;
		boolean result = false;
		try {
			String fileSupfix = FileUtils.getFileName(localFilePath) ;
			String fileSuffix = FileUtils.getFileSuffix(localFilePath) ;
			String dirPath = FileUtils.getDirPath(localFilePath) ;
			File dir = FileUtils.createDirs(dirPath);
			file = File.createTempFile(fileSupfix, "."+fileSuffix , dir);
			
			// 把upyun空间下的文件下载到本地的临时文件
			result = upyun.readFile(filePath, file);
			
			if (result) {
				file.renameTo(new File(dirPath + fileSupfix + "."+ fileSuffix));
				logger.info("UPYunUtils.readFile() : " + filePath + " download" + isSuccess(result) + ", save to " + file.getAbsolutePath());
			}

		} catch (Exception e) {
			logger.error("UPYunUtils.readFile() Exception: " + e.getMessage());
		}
		return result ;

	}
	
	
	

	private static String isSuccess(boolean result) {
		return result ? " success" : " failure";
	}
    
}
