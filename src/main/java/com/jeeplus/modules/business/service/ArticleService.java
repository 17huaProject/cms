package com.jeeplus.modules.business.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.dao.ArticleMapper;
import com.jeeplus.modules.business.entity.Article;
import com.jeeplus.modules.business.entity.ArticleExample;

@Service
public class ArticleService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ArticleMapper articleMapper;
	
	public Page<Article> findArticles(Page<Article> page, Article article) {
		article.setPage(page);
		if(StringUtils.isNotBlank(article.getTitle())){
			article.setTitle(article.getTitle().trim());
		}
		if(StringUtils.isNotBlank(article.getAuthor())){
			article.setAuthor(article.getAuthor().trim());
		}
	
		List<Article> list= articleMapper.select4List(article);

		return page.setList(list);
	}

	public Article getArticle(Integer id) {
		if(id == null || id <= 0) return new Article();
		Article article = articleMapper.selectByPrimaryKey(id);
		return article;
	}

	@Transactional(readOnly = false)
	public void saveArticle(Article article) {
		if(article == null ){
			logger.error("VenuesService.saveArticle() param venue is null!");
		}
		if(article.getArticleId() == null || article.getArticleId() == 0){
			article.preInsert();
			articleMapper.insertSelective(article);
		}else{
			article.preUpdate();
			articleMapper.updateByPrimaryKeySelective(article);
		}
	}

	@Transactional(readOnly = false)
	public void auditPass(Article article) {
		article.preUpdate();
		article.preAudit();
		articleMapper.updateByPrimaryKeySelective(article);
	}

	@Transactional(readOnly = false)
	public void deleteArticle(Integer id) {
		articleMapper.deleteByPrimaryKey(id);
	}
	
	@Transactional(readOnly = false)
	public void deleteMultiArticles(List<Integer> ids) {
		ArticleExample example = new ArticleExample();
		example.createCriteria().andArticleIdIn(ids);
		articleMapper.deleteByExample(example );
	}

}
