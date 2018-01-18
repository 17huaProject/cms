package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.dto.ArtDTO;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.Art;
import com.jeeplus.modules.business.entity.ArtExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface ArtMapper {
    int countByExample(ArtExample example);

    int deleteByExample(ArtExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Art record);

    int insertSelective(Art record);

    List<Art> selectByExampleWithBLOBs(ArtExample example);

    List<Art> selectByExample(ArtExample example);

    Art selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Art record, @Param("example") ArtExample example);

    int updateByExampleWithBLOBs(@Param("record") Art record, @Param("example") ArtExample example);

    int updateByExample(@Param("record") Art record, @Param("example") ArtExample example);

    int updateByPrimaryKeySelective(Art record);

    int updateByPrimaryKeyWithBLOBs(Art record);

    int updateByPrimaryKey(Art record);

	List<ArtDTO> select4AutoCompleteByExample(ArtExample example);

	List<Art> select4List(Art art);

	List<WorkTableDTO> selectWorkTableArtInfo();
}