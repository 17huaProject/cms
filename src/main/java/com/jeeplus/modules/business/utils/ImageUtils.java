package com.jeeplus.modules.business.utils;
import java.awt.AlphaComposite;
import java.awt.Color;  
import java.awt.Graphics2D;  
import java.awt.Image;  
import java.awt.Rectangle;
import java.awt.RenderingHints;  
import java.awt.Transparency;
import java.awt.geom.Ellipse2D;  
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.IOException;  

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ImageUtils {
	private static Logger logger = LoggerFactory.getLogger(ImageUtils.class) ;
	
	/**
	 * 将图片缩放成正方形小图，并裁剪成圆形，背景为透明
	 * @param imageName				原图
	 * @param formatName			圆形图的格式
	 * @param circularImageName		圆形图的完整路径名称
	 */
	public  static void circularImage( String imageName,String formatName, String circularImageName){
		 try {  
		        //图片的本地地址  
		          
		        //处理图片将其压缩成正方形的小图  
		        //BufferedImage  convertImage= scaleByPercentage(url, 232,232);  
		        //裁剪成圆形 （传入的图像必须是正方形的 才会 圆形 如果是长方形的比例则会变成椭圆的）  
		        //生成的图片位置  
		        File circularImage = new File(circularImageName) ;
		        if (!circularImage.exists()) {
		        	circularImage.createNewFile() ;
	            }
		        makeRoundedCorner2(imageName, circularImageName , formatName, 720);
		        
		        /*convertImage = convertCircular(url);  
		        //生成的图片位置  
		        File circularImage = new File(circularImageName) ;
		        if (!circularImage.exists()) {
		        	circularImage.createNewFile() ;
	            }
		        ImageIO.write(convertImage, formatName , circularImage);  */ 
		    } catch (Exception e) { 
		    	logger.error(e.getMessage());
		    } 
	}
	
	public static void main(String[] args) {  
	    try {  
	        //图片的本地地址  
	        Image src = ImageIO.read(new File("C:/Users/Administrator/Desktop/Imag.png"));  
	        BufferedImage url = (BufferedImage) src;  
	        //处理图片将其压缩成正方形的小图  
	        BufferedImage  convertImage= scaleByPercentage(url, 100,100);  
	        //裁剪成圆形 （传入的图像必须是正方形的 才会 圆形 如果是长方形的比例则会变成椭圆的）  
	        convertImage = convertCircular(url);  
	        //生成的图片位置  
	        String imagePath= "C:/Users/Administrator/Desktop/Imag.png";  
	        ImageIO.write(convertImage, imagePath.substring(imagePath.lastIndexOf(".") + 1), new File(imagePath));   
	        System.out.println("ok");  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	} 
	
	 /** 
	  * 缩小Image，此方法返回源图像按给定宽度、高度限制下缩放后的图像 
	  * @param inputImage 
	  * @param maxWidth：压缩后宽度 
	  * @param maxHeight：压缩后高度 
	  * @throws java.io.IOException 
	  * return  
	  */  
	 public static BufferedImage scaleByPercentage(BufferedImage inputImage, int newWidth, int newHeight) throws Exception {  
	     //获取原始图像透明度类型  
	     int type = inputImage.getColorModel().getTransparency();  
	     int width = inputImage.getWidth();  
	     int height = inputImage.getHeight();  
	     //开启抗锯齿  
	     RenderingHints renderingHints=new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);  
	     //使用高质量压缩  
	     renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);  
	     BufferedImage img = new BufferedImage(newWidth, newHeight, type);  
	     Graphics2D graphics2d =img.createGraphics();  
	     graphics2d.setRenderingHints(renderingHints);          
	     graphics2d.drawImage(inputImage, 0, 0, newWidth, newHeight, 0, 0, width, height, null);  
	     graphics2d.dispose();  
	     return img;  
	 }
	
	 /** 
	  * 传入的图像必须是正方形的 才会 圆形  如果是长方形的比例则会变成椭圆的 
	  * @param url 用户头像地址   
	  * @return 
	  * @throws IOException 
	  */  
	 public static BufferedImage convertCircular(BufferedImage bi1) throws IOException{  
	    //透明底的图片  
	    BufferedImage bi2 = new BufferedImage(bi1.getWidth(),bi1.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);   
	    Ellipse2D.Double shape = new Ellipse2D.Double(0,0,bi1.getWidth(),bi1.getHeight());  
	    Graphics2D g2 = bi2.createGraphics();  
	    g2.setClip(shape);   
	    // 使用 setRenderingHint 设置抗锯齿  
	    g2.drawImage(bi1,0,0,null);   
	    //设置颜色  
	    g2.setBackground(Color.white);  
	    g2.dispose();  
	    return bi2;  
	 } 
	 /**图片圆角处理，背景透明化 
     * @param srcImageFile 原图片 
     * @param result  处理后图片 
     * @param type   图片格式 
     * @param cornerRadius  720为圆角 
     */  
    public  static void makeRoundedCorner(String imageName, File result, String type, int cornerRadius) {          
        try {  
        	BufferedImage bi1 = ImageIO.read(new File(imageName));
            // 根据需要是否使用 BufferedImage.TYPE_INT_ARGB  
            BufferedImage image = new BufferedImage(bi1.getWidth(), bi1.getHeight(),  BufferedImage.TYPE_INT_ARGB);  
        
            Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1.getHeight());  
               
            Graphics2D g2 = image.createGraphics();  
            image = g2.getDeviceConfiguration().createCompatibleImage(bi1.getWidth(), bi1.getHeight(), Transparency.TRANSLUCENT);  
            g2 = image.createGraphics();  
            g2.setComposite(AlphaComposite.Clear);  
            g2.fill(new Rectangle(image.getWidth(), image.getHeight()));  
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));  
            g2.setClip(shape);  
            // 使用 setRenderingHint 设置抗锯齿  
            g2 = image.createGraphics();  
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);  
            g2.fillRoundRect(0, 0,bi1.getWidth(), bi1.getHeight(), cornerRadius, cornerRadius);  
            g2.setComposite(AlphaComposite.SrcIn);  
            g2.drawImage(bi1, 0, 0, bi1.getWidth(), bi1.getHeight(), null);  
            g2.dispose();  
            ImageIO.write(image, type, result);  
        } catch (Exception e) {  
            // TODO: handle exception  
        }  
    } 
    
    
    public static String makeRoundedCorner2(String srcImageFile, String result, String type, int cornerRadius) {
    	try {
    	BufferedImage image = ImageIO.read(new File(srcImageFile));
    	int w = image.getWidth();
    	int h = image.getHeight();
    	BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g2 = output.createGraphics();
    	output = g2.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
    	g2.dispose();
    	g2 = output.createGraphics();
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	g2.fillRoundRect(0, 0,w, h, cornerRadius, cornerRadius);
    	g2.setComposite(AlphaComposite.SrcIn);
    	g2.drawImage(image, 0, 0, w, h, null);
    	g2.dispose();
    	ImageIO.write(output, type, new File(result));
    	return result;
    	} catch (IOException e) {
    	e.printStackTrace();
    	}
    	return null;
    	}
	    
	
}
