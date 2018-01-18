package com.jeeplus.modules.business.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.dao.SysUserArtistMapper;
import com.jeeplus.modules.business.entity.SysUserArtist;
import com.jeeplus.modules.business.entity.SysUserArtistExample;

/**
 * 系统用户（员工）与画师关联 
 */
@Service
public class SysUserArtistService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	SysUserArtistMapper userArtistMapper ;
	
	@Transactional(readOnly = false)
	public void save(SysUserArtist userArtist) {
		if(userArtist == null ){
			logger.error("SysUserArtistService.saveSysUserArtist() param venue is null!");
		}
		userArtistMapper.insertSelective(userArtist);
	}
	
	@Transactional(readOnly = false)
	public void update(SysUserArtist userArtist) {
		if(userArtist == null ){
			logger.error("SysUserArtistService.saveSysUserArtist() param venue is null!");
		}
		SysUserArtistExample example = new SysUserArtistExample();
		example.createCriteria().andSysUserIdEqualTo(userArtist.getSysUserId());
		userArtistMapper.updateByExample(userArtist, example) ;
	}
	
	
	
	
}
