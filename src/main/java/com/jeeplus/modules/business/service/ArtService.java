package com.jeeplus.modules.business.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.dao.ArtMapper;
import com.jeeplus.modules.business.dto.ArtDTO;
import com.jeeplus.modules.business.dto.ArtistDTO;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.Art;
import com.jeeplus.modules.business.entity.ArtExample;
import com.jeeplus.modules.business.entity.ArtistExample;
import com.jeeplus.modules.business.entity.ArtExample.Criteria;
import com.jeeplus.modules.business.utils.BusinessStringUtils;

@Service
public class ArtService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ArtMapper artMapper;
	
	
	/**
	 * 自动搜索 画家列表
	 * @return
	 */
	public List<ArtDTO> listArtistAutoComplete(String searchParam){
		ArtExample example = new ArtExample();
		example.createCriteria().andArtNameLike("%"+searchParam+"%");
		List<ArtDTO> arts = artMapper.select4AutoCompleteByExample(example);
		return arts;
	}
	
	public Page<Art> findArts(Page<Art> page, Art art) {
		art.setPage(page);
		String easyLevelScope = art.getEasyLevelScope();
		String[] scope = null;
		if(StringUtils.isNotBlank(easyLevelScope)){
			scope = easyLevelScope.split(",");
			if(scope.length == 2){
				art.setEasyLevelStart(Float.valueOf(scope[0]));
				art.setEasyLevelEnd(Float.valueOf(scope[1]));
			}else{
				logger.error("easyLevelScope 传值有误！");
			}
		}
		if(StringUtils.isNotBlank(art.getArtName())){
			art.setArtName(BusinessStringUtils.characterEscape(art.getArtName().trim()));
		}
		List<Art> list= artMapper.select4List(art);

		return page.setList(list);
	}

	public Art getArt(Integer id) {
		if(id == null || id <= 0) return new Art();
		Art art = artMapper.selectByPrimaryKey(id);
		return art;
	}

	@Transactional(readOnly = false)
	public void saveArt(Art art) {
		if(art == null ){
			logger.error("VenuesService.saveArt() param venue is null!");
		}
		if(art.getId() == null || art.getId() == 0){
			art.preInsert();
			artMapper.insertSelective(art);
		}else{
			art.preUpdate();
			artMapper.updateByPrimaryKeySelective(art);
		}
	}

	@Transactional(readOnly = false)
	public void auditPass(Art art) {
		art.preUpdate();
		art.preAudit();
		artMapper.updateByPrimaryKeySelective(art);
	}

	@Transactional(readOnly = false)
	public void deleteArt(Integer id) {
		artMapper.deleteByPrimaryKey(id);
	}
	
	@Transactional(readOnly = false)
	public void deleteMultiArts(List<Integer> ids) {
		ArtExample example = new ArtExample();
		example.createCriteria().andIdIn(ids);
		artMapper.deleteByExample(example );
	}

	/**
	 * 活动关联画师，远程校验
	 * @return
	 */
	public boolean verifyArtists4Event(Integer id , String searchParam) {
		if (id == null || id <=0 || StringUtils.isBlank(searchParam)) {
			return false ;
		}
		
		ArtExample example = new ArtExample();
		Criteria  criteria  = example.createCriteria();
		criteria.andIdEqualTo(id) ;
		criteria.andArtNameLike("%"+searchParam+"%");
		
		List<ArtDTO> arts = artMapper.select4AutoCompleteByExample(example);
		if ( arts.size() == 1){
			return true ;
		}
		return false;
	}

	public List<WorkTableDTO> getWorkTableArtInfo() {
		List<WorkTableDTO> list = artMapper.selectWorkTableArtInfo() ;
		return list;
	}

}
