package com.jeeplus.modules.business.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.web.ActionResponse;
import com.jeeplus.modules.business.utils.SecurityCode;
import com.jeeplus.modules.business.utils.SecurityCode.SecurityCodeLevel;
import com.jeeplus.modules.business.utils.SecurityImage;


/**
 * 提供图片验证码
 * @version 1.0
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/securityCode")
public class SecurityCodeImageCtrl {
	private Logger  logger = LoggerFactory.getLogger(getClass()) ;
	
	@RequestMapping("generate")
	public void genSecurityCode(HttpServletRequest request, HttpServletResponse response){
		//如果开启Hard模式，不区分大小写
        String securityCode = SecurityCode.getSecurityCode(4,SecurityCodeLevel.Hard, false).toLowerCase();
        //放入session中
        request.getSession().getServletContext().setAttribute("SESSION_SECURITY_CODE", securityCode);
        //图片流
        BufferedImage imageStream = SecurityImage.createImage(securityCode);
        try {
			response.setContentType("image/png");
        	OutputStream os = response.getOutputStream() ;
			ImageIO.write(imageStream, "png", os) ;
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	@RequestMapping("verify")
	@ResponseBody
	public ActionResponse<String> verifySecurityCode(HttpServletRequest request, HttpServletResponse response){
		String securityCode = (String) request.getParameter("securityCode") ; 
		String securityCodeSession = (String) request.getSession().getServletContext().getAttribute("SESSION_SECURITY_CODE");
		if(securityCode != null && securityCodeSession != null && securityCode.equals(securityCodeSession)) {
			return new ActionResponse<String>("验证成功");
		}
		return new ActionResponse<String>(1110 , "验证码错误");
	}
	
	
	
}
