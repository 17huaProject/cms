package com.jeeplus.modules.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.nutz.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.dao.MaterialModelMapper;
import com.jeeplus.modules.business.dto.GoodDTO;
import com.jeeplus.modules.business.entity.MaterialModel;
import com.jeeplus.modules.business.entity.MaterialModelExample;
import com.jeeplus.modules.business.entity.MaterialModelExample.Criteria;

@Service
public class MaterialModelService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	MaterialModelMapper materialModelMapper ;
	
	/**
	 * 获取所有模板
	 * @return
	 */
	public List<MaterialModel> listAllMaterialModels(){
		List<MaterialModel> materialModels = null ;
		MaterialModelExample example = new MaterialModelExample();
		Criteria  criteria = example.createCriteria();
		criteria.andDeletedEqualTo(CommonConstants.DELETED_N);
		
		materialModels = materialModelMapper.selectByExample(example ) ;
		if (materialModels == null ) return new ArrayList<MaterialModel>();
		return materialModels ;
	}
	
	public Page<MaterialModel> findMaterialModels(Page<MaterialModel> page,
			MaterialModel materialModel) {
		
		materialModel.setPage(page);
		
		if(StringUtils.isNotBlank(materialModel.getName())){
			materialModel.setName(materialModel.getName().trim());
		}
		page.setList(materialModelMapper.select4List(materialModel ));
		return page;
		
	}

	public MaterialModel getMaterialModel(Integer id) {
		if(id == null || id <= 0) return new MaterialModel();
		MaterialModel	materialModel = materialModelMapper.selectByPrimaryKey(id);
		return materialModel;
	}

	@Transactional(readOnly = false)
	public void saveMaterialModel(MaterialModel materialModel) {
		if(materialModel == null ){
			logger.error("MaterialModelService.saveMaterialModel() param materialModel is null!");
		}
		if(materialModel.getId() == null || materialModel.getId() == 0){ 
			materialModel.preInsert();
			materialModelMapper.insertSelective(materialModel);
		}else{
			materialModel.preUpdate();
			materialModelMapper.updateByPrimaryKeySelective(materialModel);
		}
		
	}
	
	@Transactional(readOnly = false)
	public void deleteMaterialModel(Integer id) {
		materialModelMapper.deleteByPrimaryKey(id);
	}
	
	@Transactional(readOnly = false)
	public void deleteMultiMaterialModels(List<Integer> lds) {
		MaterialModelExample example = new MaterialModelExample();
		example.createCriteria().andIdIn(lds );
		materialModelMapper.deleteByExample(example);
	}

	
	public String genMaterialJson(List<GoodDTO> goods, List<Map<String, String>> goodsInfo) {
		String json = "" ;
		String unitNum = "" ;
		String goodId = "" ;
		for (GoodDTO good : goods) {
			goodId = good.getId()+"" ;
			for (Map<String, String> map : goodsInfo) {
				if(map.containsKey(goodId)){
					unitNum = map.get(goodId);
					good.setUnitNum(unitNum);  
					map.remove(goodId) ;
				}
			}
		}
		json = Json.toJson(goods) ;
		return json;
	}


}











