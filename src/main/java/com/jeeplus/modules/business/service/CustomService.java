package com.jeeplus.modules.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.dao.UserCustomMapper;
import com.jeeplus.modules.business.entity.UserCustom;
import com.jeeplus.modules.business.entity.UserCustomExample;

@Service
public class CustomService {

	@Autowired
	UserCustomMapper userCustomMapper ;
	
	public Page<UserCustom> listCustoms(Page<UserCustom> page, UserCustom custom) {
		custom.setPage(page);
		
		if(StringUtils.isNotBlank(custom.getPhone())){
			custom.setPhone(custom.getPhone().trim());
		}
		List<UserCustom> list = userCustomMapper.select4List(custom);
		page.setList(list);
		return page;
	}
	
	/**
	 * 根据userId查询定制列表
	 * @param userId
	 * @return
	 */
	public List<UserCustom> listCustoms(String userId) {
		UserCustomExample example = new UserCustomExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<UserCustom> list = userCustomMapper.selectByExample(example );
		if(list == null){
			list = new ArrayList<UserCustom>();
		}
		return list;
	}

	public UserCustom getCustom(Integer id) {
		UserCustom userCustom = userCustomMapper.selectByPrimaryKey(id);
		if (userCustom == null) {
			userCustom = new UserCustom();
		}
		return userCustom;
	}

	@Transactional(readOnly = false)
	public void deleteCustom(Integer id) {
		userCustomMapper.deleteByPrimaryKey(id);
	}

	@Transactional(readOnly = false)
	public void deleteMultiCustoms(List<Integer> ids) {
		UserCustomExample example = new UserCustomExample();
		example.createCriteria().andIdIn(ids) ;
		userCustomMapper.deleteByExample(example );
		
	}

	@Transactional(readOnly = false)
	public void saveCustom(UserCustom custom) {
		if(custom != null ){
			custom.setOperatorId(custom.getCurrentUser().getId());
			custom.setTransTime(new Date());
			userCustomMapper.updateByPrimaryKeySelective(custom);
		}
	}
	
	@Transactional(readOnly = false)
	public void updateEventId(int customId , int eventId){
		UserCustom custom = new UserCustom();
		custom.setId(customId);
		custom.setEventId(eventId);
		custom.setTransTime(new Date());
		userCustomMapper.updateByPrimaryKeySelective(custom ) ;
	}


	
	

}


