package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.entity.GiftPhoto;
import com.jeeplus.modules.business.entity.GiftPhotoExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface GiftPhotoMapper {
    int countByExample(GiftPhotoExample example);

    int deleteByExample(GiftPhotoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(GiftPhoto record);

    int insertSelective(GiftPhoto record);

    List<GiftPhoto> selectByExample(GiftPhotoExample example);

    GiftPhoto selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GiftPhoto record, @Param("example") GiftPhotoExample example);

    int updateByExample(@Param("record") GiftPhoto record, @Param("example") GiftPhotoExample example);

    int updateByPrimaryKeySelective(GiftPhoto record);

    int updateByPrimaryKey(GiftPhoto record);
}