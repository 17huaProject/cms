package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.BaseDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.entity.SysUserArtist;
import com.jeeplus.modules.business.entity.SysUserArtistExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
@MyBatisDao
public interface SysUserArtistMapper extends BaseDao {
    int countByExample(SysUserArtistExample example);

    int deleteByExample(SysUserArtistExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysUserArtist record);

    int insertSelective(SysUserArtist record);

    List<SysUserArtist> selectByExample(SysUserArtistExample example);

    SysUserArtist selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysUserArtist record, @Param("example") SysUserArtistExample example);

    int updateByExample(@Param("record") SysUserArtist record, @Param("example") SysUserArtistExample example);

    int updateByPrimaryKeySelective(SysUserArtist record);

    int updateByPrimaryKey(SysUserArtist record);
}