package com.jeeplus.modules.business.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.dao.GoodCategoryMapper;
import com.jeeplus.modules.business.entity.GoodCategory;
import com.jeeplus.modules.business.entity.GoodCategoryExample;
import com.jeeplus.modules.business.entity.GoodCategoryExample.Criteria;

@Service
public class GoodCategoryService {
	
	Logger logger = LoggerFactory.getLogger(getClass()) ;
	
	@Autowired
	GoodCategoryMapper categoryMapper ;
	
	public Page<GoodCategory> findGoodCategorys(Page<GoodCategory> page, GoodCategory goodCategory) {
		goodCategory.setPage(page) ;
		
		if(StringUtils.isNotBlank(goodCategory.getName())){
			goodCategory.setName(goodCategory.getName().trim());
		}
	
		List<GoodCategory> list= categoryMapper.select4List(goodCategory);
		
		return page.setList(list);
	}
	
	public List<GoodCategory> findGoodCategorys() {
		GoodCategoryExample example = new GoodCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(CommonConstants.DELETED_N);
		List<GoodCategory> list= categoryMapper.selectByExample(example);
		return list;
	}

	public GoodCategory getGoodCategory(Integer id) {
		if(id == null || id <= 0) return new GoodCategory();
		GoodCategory category = categoryMapper.selectByPrimaryKey(id);
		return category;
	}
	
	public String[] getCateAttribsById(int id){
		String[] cateAttribs = new String[0]  ;
		GoodCategory goodCategory = getGoodCategory(id);
		String cateAttrib = goodCategory.getCateAttrib() ;
		if (StringUtils.isNotBlank(cateAttrib)) {
			cateAttribs = StringUtils.split(goodCategory.getCateAttrib(), ",") ;
		}
		return cateAttribs ;
	}

	public void saveGoodCategory(GoodCategory goodCategory) {
		if(goodCategory == null ){
			logger.error("GoodCategoryService.saveGoodCategory() param venue is null!");
		}
		goodCategory.setCateAttrib(StringUtils.join(goodCategory.getCateAttribs(), ",")) ;
		
		if(goodCategory.getId() == null || goodCategory.getId() == 0){
			goodCategory.preInsert();
			categoryMapper.insertSelective(goodCategory);
		}else{
			goodCategory.preUpdate();
			categoryMapper.updateByPrimaryKeySelective(goodCategory);
		}
	}

	public void deleteGoodCategory(Integer id) {
		GoodCategory goodCategory = new GoodCategory();
		goodCategory.setId(id);
		goodCategory.preDeleteLogic();
		categoryMapper.updateByPrimaryKeySelective(goodCategory);
		
	}

	public void deleteMultiGoodCategorys(List<Integer> ids) {
		GoodCategory goodCategory = new GoodCategory();
		goodCategory.preDeleteLogic();
		GoodCategoryExample example = new GoodCategoryExample();
		example.createCriteria().andIdIn(ids);
		categoryMapper.updateByExampleSelective(goodCategory, example);
		
	}

}
