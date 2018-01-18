package com.jeeplus.modules.business.service;

import java.util.ArrayList;
import java.util.List;









import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.utils.CacheUtils;
import com.jeeplus.modules.business.dao.ArticleCatMapper;
import com.jeeplus.modules.business.entity.ArticleCat;
import com.jeeplus.modules.business.entity.ArticleCatExample;
import com.jeeplus.modules.business.utils.BusinessUtils;

@Service
public class ArticleCatService {

	@Autowired
	ArticleCatMapper articleCatMapper ;
	
	public List<ArticleCat> findAllList() {
		@SuppressWarnings("unchecked")
		List<ArticleCat> articleCats = (List<ArticleCat>) CacheUtils.get(BusinessUtils.CACHE_ARTICLECAT, BusinessUtils.CACHE_ARTICLECAT_LIST);
		if(articleCats == null ){
			articleCats = articleCatMapper.selectByExample(null);
			CacheUtils.put(BusinessUtils.CACHE_ARTICLECAT, BusinessUtils.CACHE_ARTICLECAT_LIST, articleCats);
		}
		return articleCats;
	}

	@SuppressWarnings("unchecked")
	public List<ArticleCat> findList(ArticleCat articleCat) {
		int catId = articleCat.getCatId();
		List<ArticleCat> articleCatsRet = new ArrayList<ArticleCat>(); 
		List<ArticleCat> articleCats = (List<ArticleCat>) CacheUtils.get(BusinessUtils.CACHE_ARTICLECAT, BusinessUtils.CACHE_ARTICLECAT_LIST);
		if(articleCats == null ){
			ArticleCatExample example = new ArticleCatExample();
			example.setOrderByClause("catId");
			example.createCriteria().andParentIdEqualTo(catId);
			articleCatsRet = articleCatMapper.selectByExample(example);
		}else{
			for (ArticleCat ac : articleCats) {
				if(catId == ac.getParentId()){
					articleCatsRet.add(ac);
				}
			}
		}
		return articleCatsRet;
	}

	@Transactional(readOnly = true)
	public void save(ArticleCat articleCat) {
		articleCatMapper.insertSelective(articleCat);
		CacheUtils.remove(BusinessUtils.CACHE_ARTICLECAT, BusinessUtils.CACHE_ARTICLECAT_LIST);
	}

	@Transactional(readOnly = true)
	public void delete(Integer catId) {
		articleCatMapper.deleteByPrimaryKey(catId);
		CacheUtils.remove(BusinessUtils.CACHE_ARTICLECAT, BusinessUtils.CACHE_ARTICLECAT_LIST);
	}

	public ArticleCat getArticleCat(Short catId) {
		ArticleCat articleCatRet = new ArticleCat() ;
		List<ArticleCat> articleCats = findAllList() ;
		for (ArticleCat articleCat : articleCats) {
			if (articleCat.getCatId() == Integer.valueOf(catId)) {
				articleCatRet =  articleCat ;
				break ;
			}
		}
		return articleCatRet ;
	}

}
