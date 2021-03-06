package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.entity.UserCoupon;
import com.jeeplus.modules.business.entity.UserCouponExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface UserCouponMapper {
    int countByExample(UserCouponExample example);

	int deleteByExample(UserCouponExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(UserCoupon record);

	int insertSelective(UserCoupon record);

	List<UserCoupon> selectByExample(UserCouponExample example);

	UserCoupon selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") UserCoupon record,
			@Param("example") UserCouponExample example);

	int updateByExample(@Param("record") UserCoupon record,
			@Param("example") UserCouponExample example);

	int updateByPrimaryKeySelective(UserCoupon record);

	int updateByPrimaryKey(UserCoupon record);

	UserCoupon selectCouponImg(@Param("userId") String userId, @Param("couponType") String couponType);
	
}