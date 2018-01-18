package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.entity.Gift;
import com.jeeplus.modules.business.entity.GiftExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface GiftMapper {
    int countByExample(GiftExample example);

    int deleteByExample(GiftExample example);

    int deleteByPrimaryKey(String giftId);

    int insert(Gift record);

    int insertSelective(Gift record);

    List<Gift> selectByExample(GiftExample example);

    Gift selectByPrimaryKey(String giftId);

    int updateByExampleSelective(@Param("record") Gift record, @Param("example") GiftExample example);

    int updateByExample(@Param("record") Gift record, @Param("example") GiftExample example);

    int updateByPrimaryKeySelective(Gift record);

    int updateByPrimaryKey(Gift record);

	int deleteLogicalByPrimaryKey(String id);

	int deleteLogicalByExample(GiftExample example);

	List<Gift> select4List(Gift gift);
}