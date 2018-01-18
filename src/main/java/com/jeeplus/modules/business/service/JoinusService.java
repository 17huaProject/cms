package com.jeeplus.modules.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.modules.business.dao.JoinusMapper;
import com.jeeplus.modules.business.entity.Joinus;
import com.jeeplus.modules.business.entity.JoinusExample;

@Service("JoinusService")
public class JoinusService {

	@Autowired
	JoinusMapper joinusMapper ;
	
	public Page<Joinus> listJoinuses(Page<Joinus> page, Joinus joinus) {
		joinus.setPage(page);
		
		List<Joinus> list = joinusMapper.select4List(joinus);
		page.setList(list);
		return page;
	}
	

	public Joinus getJoinus(Integer id) {
		Joinus joinus = joinusMapper.selectByPrimaryKey(id);
		if (joinus == null) {
			joinus = new Joinus();
		}
		return joinus;
	}

	public int deleteJoinus(Integer id) {
		return joinusMapper.deleteByPrimaryKey(id);
	}

	public int deleteMultiJoinuses(List<Integer> ids) {
		JoinusExample example = new JoinusExample();
		example.createCriteria().andIdIn(ids) ;
		return joinusMapper.deleteByExample(example );
		
	}

	/**
	 * 更新 </br>
	 * 本模块无需插入功能
	 * @param feedback
	 */
	public int saveJoinus(Joinus joinus) {
		int num = 0 ;
		if(joinus != null ){
			joinus.preUpdate();
			num = joinusMapper.updateByPrimaryKeySelective(joinus);
		}
		return num;
	}

	/**
	 * 未处理的加盟合作通知
	 */
	public List<Joinus> listUndealJoinus() {
		Joinus joinus = new Joinus();
		joinus.setStatus((byte) 0);
		List<Joinus> list = joinusMapper.select4List(joinus );
		return list;
	}

}
