package com.jeeplus.modules.business.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.dao.UserMapper;
import com.jeeplus.modules.business.dao.UserProfileMapper;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.User;
import com.jeeplus.modules.business.entity.UserExample;
import com.jeeplus.modules.business.entity.UserExample.Criteria;
import com.jeeplus.modules.business.entity.UserProfile;
import com.jeeplus.modules.business.entity.UserProfileExample;


@Service
public class UserService {

	@Autowired
	UserMapper userMapper ;
	@Autowired
	UserProfileMapper userProfileMapper ;
	
	
	public Page<User> listUsers(Page<User> page, User user) {
		user.setPage(page);
		
		if (StringUtils.isNotBlank(user.getPhone())) {
			user.setPhone(user.getPhone().trim()) ;
		}
		if (StringUtils.isNotBlank(user.getRealname())) {
			user.setRealname(user.getRealname().trim());
		}
		if (StringUtils.isNotBlank(user.getIdcard())) {
			user.setIdcard(user.getIdcard().trim());
		}
		if (StringUtils.isNotBlank(user.getUserType())) {
			user.setUserType(user.getUserType().trim());
		}
		List<User> users = userMapper.selectByExample4List(user);
		page.setList(users);
		return page;
	}
	
	public User getUser(String userId) {
		User user = null ;
		if (StringUtils.isNotBlank(userId)) {
			user =  userMapper.selectByPrimaryKey(userId) ;
		}
		if (user == null) user = new User();
		return user ;
	}

	@Transactional(readOnly = false)
	public void saveUser(User user) {
		user.preUpdate();
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Transactional(readOnly = false)
	public void deleteUser(String id) {
		userMapper.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = false)
	public void deleteMultiUsers(List<String> lds) {
		UserExample example = new UserExample();
		example.createCriteria().andUserIdIn(lds);
		userMapper.deleteByExample(example );
	}

	public UserProfile getUserProfileByPrimaryKey(String id) {
		UserProfile userProfile = userProfileMapper.selectByPrimaryKey(id);
		if(userProfile == null){
			userProfile = new UserProfile();
		}
		return userProfile;
	}

	public List<WorkTableDTO> getWorkTableUserInfo() {
		List<WorkTableDTO> list = userMapper.selectWorkTableUserInfo() ;
		return list;
	}

	/**
	 * 向用户账户中追加金额
	 * @param userId
	 * @param balanceAdd
	 */
	@Transactional(readOnly = false)
	public boolean addBalance(String userId, BigDecimal balanceAdd) {
		int num = userMapper.addBalance(userId , balanceAdd);
		if (num > 0) {
			return true ;
		}
		return false ;
	}
	/**
	 * 向用户账户中充值积分
	 * @param userId
	 * @param balanceAdd
	 */
	@Transactional(readOnly = false)
	public boolean addScore(String userId, Integer scoreAdd) {
		int num = userMapper.addScore(userId , scoreAdd);
		if (num > 0) {
			return true ;
		}
		return false ;
	}

	public void updateAvatar(String userId, String avatar) {
		UserProfile record = new UserProfile();
		record.setAvatar(avatar);
		UserProfileExample example = new UserProfileExample();
		example.createCriteria().andUserIdEqualTo(userId) ;
		userProfileMapper.updateByExampleSelective(record, example) ;
	}

}
