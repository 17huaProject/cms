package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.dto.MaterialDeliveryBaseInfoDTO;
import com.jeeplus.modules.business.dto.MaterialDeliveryDTO;
import com.jeeplus.modules.business.dto.MaterialDeliveryExportDTO;
import com.jeeplus.modules.business.entity.MaterialDelivery;
import com.jeeplus.modules.business.entity.MaterialDeliveryExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;


@MyBatisDao
public interface MaterialDeliveryMapper {
    int countByExample(MaterialDeliveryExample example);

    int deleteByExample(MaterialDeliveryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MaterialDelivery record);

    int insertSelective(MaterialDelivery record);

    List<MaterialDelivery> selectByExample(MaterialDeliveryExample example);

    MaterialDelivery selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MaterialDelivery record, @Param("example") MaterialDeliveryExample example);

    int updateByExample(@Param("record") MaterialDelivery record, @Param("example") MaterialDeliveryExample example);

    int updateByPrimaryKeySelective(MaterialDelivery record);

    int updateByPrimaryKey(MaterialDelivery record);

	List<MaterialDeliveryDTO> selectMaterialDelivery(Integer eventId);

	List<MaterialDelivery> selectAllMaterialDeliverys(MaterialDelivery materialDelivery);

	/**
	 * 根据活动id获取生成物料单的基本信息
	 * @param eventId   活动id
	 * @return
	 */
	MaterialDeliveryBaseInfoDTO genBaseInfo(Integer eventId);
	/**
	 * 根据活动ID得到物料单的信息
	 * @param eventId 活动ID
	 * @return
	 */
	MaterialDeliveryExportDTO select4ExportMaterialDelivery(Integer eventId);
}