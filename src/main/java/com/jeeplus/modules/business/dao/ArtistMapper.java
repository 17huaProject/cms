package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.dto.ArtistDTO;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.Artist;
import com.jeeplus.modules.business.entity.ArtistExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface ArtistMapper {
    int countByExample(ArtistExample example);

    int deleteByExample(ArtistExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Artist record);

    int insertSelective(Artist record);

    List<Artist> selectByExampleWithBLOBs(ArtistExample example);

    List<Artist> selectByExample(ArtistExample example);
    /**
     * 自动搜索 画家列表
     */
    List<ArtistDTO> select4AutoCompleteByExample(ArtistExample example);
    /**
     * 根据sys_user.id 搜索画家信息
     */
    ArtistDTO selectArtistBySysUserId(String sysUserId);

    Artist selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Artist record, @Param("example") ArtistExample example);

    int updateByExampleWithBLOBs(@Param("record") Artist record, @Param("example") ArtistExample example);

    int updateByExample(@Param("record") Artist record, @Param("example") ArtistExample example);

    int updateByPrimaryKeySelective(Artist record);

    int updateByPrimaryKeyWithBLOBs(Artist record);

    int updateByPrimaryKey(Artist record);

	List<WorkTableDTO> selectWorkTableArtistInfo();

	List<Artist> select4List(Artist artist);
}