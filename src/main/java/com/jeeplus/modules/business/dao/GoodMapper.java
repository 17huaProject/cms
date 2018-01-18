package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.dto.GoodDTO;
import com.jeeplus.modules.business.entity.Good;
import com.jeeplus.modules.business.entity.GoodExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface GoodMapper {
    int countByExample(GoodExample example);

    int deleteByExample(GoodExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Good record);

    int insertSelective(Good record);

    List<Good> selectByExampleWithBLOBs(GoodExample example);

    List<Good> selectByExample(GoodExample example);
    
    List<GoodDTO> selectByCategoryId(Integer categoryId);

    Good selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Good record, @Param("example") GoodExample example);

    int updateByExampleWithBLOBs(@Param("record") Good record, @Param("example") GoodExample example);

    int updateByExample(@Param("record") Good record, @Param("example") GoodExample example);

    int updateByPrimaryKeySelective(Good record);

    int updateByPrimaryKeyWithBLOBs(Good record);

    int updateByPrimaryKey(Good record);

	List<GoodDTO> selectGoodsByMaterialModel(List<Integer> goodsIdList);

	List<Good> select4List(Good good);
}