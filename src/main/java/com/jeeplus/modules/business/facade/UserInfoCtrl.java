package com.jeeplus.modules.business.facade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.sys.dao.UserDao;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.service.SystemService;

@Controller
@RequestMapping(value = "/h5/")  
public class UserInfoCtrl {

	@Autowired
	UserDao userDao ;
	
	@RequestMapping(value = "verifyUser", method = RequestMethod.POST)
	@ResponseBody
	public String verifyUserNameAndPasswd(HttpServletRequest request, HttpServletResponse response){
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		if (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
			
			User user = new User();
			user.setLoginName(userName);
			User userSearch = userDao.getByLoginName(user) ;
			
			boolean flag = false ;
			if (userSearch != null && StringUtils.isNotBlank(userSearch.getId())) {
				flag = SystemService.validatePassword(password, userSearch.getPassword());
			}
			if (flag) {
				String userType = userSearch.getUserType() ;
				String userTypeDesc = "非画师或助教" ;
				if ( CommonConstants.SysUserType.artist.getCode().equals(userType)) {
					userTypeDesc = "画师";
				} else if (CommonConstants.SysUserType.assistant.getCode().equals(userType)) {
					userTypeDesc = "助教";
				}
				return "{\"code\":0,\"message\":\"登录成功\","
						+ "\"userId\":\""+userSearch.getId()+"\","
						+ "\"userType\":\""+userType+"\","
						+ "\"userTypeDesc\":\""+userTypeDesc+"\"}" ;
			}
		} else {
			return "{\"code\":1000,\"message\":\"用户名或密码不能为空\"}" ;
		}
		
		return "{\"code\":1001,\"message\":\"用户名或密码不正确\"}" ;
	}
	
	
}
