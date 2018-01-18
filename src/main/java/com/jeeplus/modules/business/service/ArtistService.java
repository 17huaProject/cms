package com.jeeplus.modules.business.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.dao.ArtistMapper;
import com.jeeplus.modules.business.dto.ArtistDTO;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.Artist;
import com.jeeplus.modules.business.entity.ArtistExample;
import com.jeeplus.modules.business.entity.ArtistExample.Criteria;

@Service
public class ArtistService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	ArtistMapper artistMapper;
	
	/**
	 * 自动搜索 画家列表
	 * @return
	 */
	public ArtistDTO selectArtistBySysUserId(String sysUserId){
		ArtistDTO artist = null ;
		if (StringUtils.isNotBlank(sysUserId)) {
			artist = artistMapper.selectArtistBySysUserId(sysUserId);
		}
		if ( artist == null) artist = new ArtistDTO();
		return artist;
	}
	/**
	 * 自动搜索 画家列表
	 * @return
	 */
	public List<ArtistDTO> listArtistAutoComplete(String artistSearchParam){
		ArtistExample example = new ArtistExample();
		example.setOrderByClause("artist_name");
		if (StringUtils.isNotBlank(artistSearchParam)) {
			example.or().andRealnameLike("%"+artistSearchParam+"%");
			example.or().andIdcardLike("%"+artistSearchParam+"%");
			example.or().andPhoneLike("%"+artistSearchParam+"%");
		}
		List<ArtistDTO> artists = artistMapper.select4AutoCompleteByExample(example);
		return artists;
	}
	
	public Page<Artist> findArtists(Page<Artist> page, Artist artist) {
		artist.setPage(page);
		
		/*String artistLevelScope = artist.getArtistLevelScope();
		ArtistExample example = new ArtistExample();
		Criteria criteria = example.createCriteria();
		String[] scope = null;
		if(StringUtils.isNotBlank(artistLevelScope)){
			scope = artistLevelScope.split(",");
			if (scope != null && scope.length == 2) {
				criteria.andArtistLevelGreaterThanOrEqualTo(scope[0]);
				if (scope[0].equals(scope[1])) {
					criteria.andArtistLevelLessThanOrEqualTo(scope[1]);	
				}else{
					criteria.andArtistLevelLessThan(scope[1]);
				}
			}else{
				logger.error("ArtistService.findArtists() ==> array of artistLevelScope is error, must just 2 values. but it's " + artistLevelScope);
			}
		}
		if(StringUtils.isNotBlank(artist.getRealname())){
			criteria.andRealnameLike("%"+artist.getRealname().trim()+"%");
		}
		if(StringUtils.isNotBlank(artist.getIdcard())){
			criteria.andIdcardLike("%"+artist.getIdcard().trim()+"%");
		}
		if(artist.getGender() != null){
			criteria.andGenderEqualTo(artist.getGender());
		}
		if(StringUtils.isNotBlank(artist.getProvinceCode())){
			criteria.andProvinceCodeEqualTo(artist.getProvinceCode());
		}
		if(StringUtils.isNotBlank(artist.getCityCode())){
			criteria.andCityCodeEqualTo(artist.getCityCode());
		}
		if (artist.getIsCheck() != null) {
			criteria.andIsCheckEqualTo(artist.getIsCheck().byteValue()) ;
		}*/
		
		if(StringUtils.isNotBlank(artist.getRealname())){
			artist.setRealname(artist.getRealname().trim());
		}
		if(StringUtils.isNotBlank(artist.getIdcard())){
			artist.setIdcard(artist.getIdcard().trim());
		}
		List<Artist> list= artistMapper.select4List(artist);

		return page.setList(list);
	}

	public Artist getArtist(Integer id) {
		if(id == null || id <= 0) return new Artist();
		Artist artist = artistMapper.selectByPrimaryKey(id);
		return artist;
	}

	@Transactional(readOnly = false)
	public void saveArtist(Artist artist) {
		if(artist == null ){
			logger.error("VenuesService.saveArtist() param venue is null!");
		}
		if(artist.getId() == null || artist.getId() == 0){
			artist.preInsert();
			artistMapper.insertSelective(artist);
		}else{
			artist.preUpdate();
			artistMapper.updateByPrimaryKeySelective(artist);
		}
	}

	@Transactional(readOnly = false)
	public void auditPass(Artist artist) {
		artist.preUpdate();
		artist.preAudit();
		artistMapper.updateByPrimaryKeySelective(artist);
	}

	@Transactional(readOnly = false)
	public void deleteArtist(Integer id) {
		artistMapper.deleteByPrimaryKey(id);
	}
	
	@Transactional(readOnly = false)
	public void deleteMultiArtists(List<Integer> ids) {
		ArtistExample example = new ArtistExample();
		example.createCriteria().andIdIn(ids);
		artistMapper.deleteByExample(example );
	}
	public List<WorkTableDTO> getWorkTableArtistInfo() {
		List<WorkTableDTO> list = artistMapper.selectWorkTableArtistInfo() ;
		return list;
	}
	
	/**
	 * 活动关联画师，远程校验
	 * @return
	 */
	public boolean verifyArtists4Event(Integer id , String searchParam) {
		if (id == null || id <=0 || StringUtils.isBlank(searchParam)) {
			return false ;
		}
		
		ArtistExample example = new ArtistExample();
		Criteria  criteria  = example.createCriteria();
		criteria.andIdEqualTo(id) ;
		criteria.andRealnameLike("%"+searchParam+"%");
		
		Criteria  criteria0  = example.createCriteria();
		criteria0.andIdEqualTo(id) ;
		criteria0.andRealnameLike("%"+searchParam+"%");
		example.or(criteria0) ;
		
		Criteria  criteria1  = example.createCriteria();
		criteria1.andIdEqualTo(id) ;
		criteria1.andIdcardLike("%"+searchParam+"%");
		example.or(criteria1) ;
		
		Criteria  criteria2  = example.createCriteria();
		criteria2.andIdEqualTo(id) ;
		criteria2.andPhoneLike("%"+searchParam+"%");
		example.or(criteria2) ;
		
		List<ArtistDTO> artists = artistMapper.select4AutoCompleteByExample(example);
		if ( artists.size() == 1){
			return true ;
		}
		return false;
	}

}

















