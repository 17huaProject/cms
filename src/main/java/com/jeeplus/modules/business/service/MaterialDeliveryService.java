package com.jeeplus.modules.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.CommonConstants;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.dao.GoodMapper;
import com.jeeplus.modules.business.dao.MaterialDeliveryMapper;
import com.jeeplus.modules.business.dto.GoodDTO;
import com.jeeplus.modules.business.dto.MaterialDeliveryBaseInfoDTO;
import com.jeeplus.modules.business.dto.MaterialDeliveryDTO;
import com.jeeplus.modules.business.dto.MaterialDeliveryExportDTO;
import com.jeeplus.modules.business.entity.MaterialDelivery;
import com.jeeplus.modules.business.entity.MaterialDeliveryExample;
import com.jeeplus.modules.business.utils.BusinessStringUtils;

@Service
public class MaterialDeliveryService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	MaterialDeliveryMapper materialDeliveryMapper ;	
	@Autowired
	GoodMapper goodMapper ;
	@Autowired
	GoodService goodService;
	@Autowired
	MaterialModelService materialModelService;
	
	public Page<MaterialDelivery> findMaterialDeliverys(Page<MaterialDelivery> page, MaterialDelivery materialDelivery) {

		materialDelivery.setPage(page);
		
		String eventName = materialDelivery.getEventName() ;
		if (StringUtils.isNotBlank( eventName )) {
			materialDelivery.setEventName(eventName.trim()) ;
		}
		List<MaterialDelivery> materialDeliveries = materialDeliveryMapper.selectAllMaterialDeliverys( materialDelivery ) ;
		
		page.setList(materialDeliveries);
		return page;
		
	}

	public List<MaterialDeliveryDTO> getMaterialDelivery(Integer eventId) {
		if(eventId == null || eventId <= 0) return new ArrayList<MaterialDeliveryDTO>();
		
		List<MaterialDeliveryDTO> materialDeliveryes =  materialDeliveryMapper.selectMaterialDelivery(eventId);
		
		return materialDeliveryes;
	}

	@Transactional(readOnly = false)
	public void saveMaterialDelivery(MaterialDeliveryDTO materialDeliveryDTO) {
		if(materialDeliveryDTO == null ){
			logger.error("MaterialDeliveryService.saveMaterialDelivery() param materialDelivery is null!");
		}
		String goodsInfo = materialDeliveryDTO.getGoodsInfo() ;
		if (StringUtils.isNotBlank(goodsInfo)) {
			
			List<String[]> goodsInfoArray = BusinessStringUtils.parseJSONStr2ListArray(goodsInfo) ;
			
			MaterialDelivery materialDelivery = new MaterialDelivery() ;
			materialDelivery.setEventId(materialDeliveryDTO.getEventId());
			materialDelivery.setModelId(materialDeliveryDTO.getModelId());
			
			for (String[] goodInfo : goodsInfoArray) {
				
				materialDelivery.setGoodId(Integer.parseInt(goodInfo[1]));
				materialDelivery.setOutCount(Short.parseShort(goodInfo[2]));
				
				switch ( Integer.parseInt(goodInfo[3]) ) {
					case CommonConstants.ADD:
						materialDelivery.preInsert();
						materialDeliveryMapper.insertSelective(materialDelivery);
						break;
					case CommonConstants.UPDATE:
						materialDelivery.setId(Integer.parseInt(goodInfo[0]));
						materialDelivery.preUpdate();
						materialDeliveryMapper.updateByPrimaryKeySelective(materialDelivery);
						break;
					case CommonConstants.DELETED:
						materialDelivery.setId(Integer.parseInt(goodInfo[0]));
						materialDeliveryMapper.deleteByPrimaryKey( Integer.parseInt(goodInfo[0]) ) ;
						break;
				}
			}
		}
	}
	
	
	@Transactional(readOnly = false)
	public void saveInMaterialDelivery(MaterialDeliveryDTO materialDeliveryDTO) {
		if(materialDeliveryDTO == null ){
			logger.error("MaterialDeliveryService.saveMaterialDelivery() param materialDelivery is null!");
		}
		String goodsInfo = materialDeliveryDTO.getGoodsInfo() ;
		if (StringUtils.isNotBlank(goodsInfo)) {
			
			List<String[]> goodsInfoArray = BusinessStringUtils.parseJSONStr2ListArray2D(goodsInfo) ;
			
			MaterialDelivery materialDelivery = new MaterialDelivery() ;
			
			for (String[] goodInfo : goodsInfoArray) {
				//修改入库量
				materialDelivery.setId(Integer.parseInt(goodInfo[0]));
				materialDelivery.setInCount(Short.parseShort(goodInfo[1]));
				materialDelivery.setStatus(1);
				materialDelivery.preUpdate();
				materialDeliveryMapper.updateByPrimaryKeySelective(materialDelivery);
			}
		}
	}
	
	
	@Transactional(readOnly = false)
	public void deleteMaterialDelivery(Integer id) {
		MaterialDeliveryExample example = new MaterialDeliveryExample();
		example.createCriteria().andEventIdEqualTo(id);
		MaterialDelivery record = new MaterialDelivery();
		record.setDeleted(CommonConstants.DELETED_Y);
		//materialDeliveryMapper.deleteByExample(example);
		materialDeliveryMapper.updateByExampleSelective(record , example);
	}
	
	@Transactional(readOnly = false)
	public void deleteMultiMaterialDeliverys(List<Integer> ids) {
		MaterialDeliveryExample example = new MaterialDeliveryExample();
		example.createCriteria().andEventIdIn(ids);
		MaterialDelivery record = new MaterialDelivery();
		record.setDeleted(CommonConstants.DELETED_Y);
		materialDeliveryMapper.updateByExampleSelective(record, example) ;
		//materialDeliveryMapper.deleteByExample(example);
	}

	/**
	 * 根据活动id获取生成物料单的基本信息
	 * @param eventId   活动id
	 * @return
	 */
	public MaterialDeliveryBaseInfoDTO genBaseInfo(Integer eventId) {
		MaterialDeliveryBaseInfoDTO baseInfo = materialDeliveryMapper.genBaseInfo(eventId);
		
		if (baseInfo == null){
			baseInfo = new MaterialDeliveryBaseInfoDTO() ;
		} else {
			String goodsInfo = baseInfo.getMaterialModelGoodsInfo() ;
			if (StringUtils.isNoneBlank(goodsInfo)) {
				List<GoodDTO> goods = goodService.getGoodsByMaterialModel(goodsInfo);
				
				List<Map<String, String>> goodsInfoMaps = BusinessStringUtils.parseJSONStr(goodsInfo) ;
				
				String materialJson = materialModelService.genMaterialJson(goods, goodsInfoMaps);
				baseInfo.setMaterialJson(materialJson);
			}
		}
		return baseInfo;
	}

	/**
	 * 根据活动ID得到物料单的信息
	 * @param eventId 活动ID
	 * @return
	 */
	public MaterialDeliveryExportDTO getMaterialDeliveryExportInfo(Integer eventId) {
		MaterialDeliveryExportDTO exportDTO = null ;
		exportDTO = materialDeliveryMapper.select4ExportMaterialDelivery(eventId);
		
		return exportDTO;
	}


}














