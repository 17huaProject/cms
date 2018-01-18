package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.BaseDao;
import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.dto.WorkTableDTO;
import com.jeeplus.modules.business.entity.User;
import com.jeeplus.modules.business.entity.UserExample;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface UserMapper extends BaseDao {
    int countByExample(UserExample example);

	int deleteByExample(UserExample example);

	int deleteByPrimaryKey(String userId);

	int insert(User record);

	int insertSelective(User record);

	List<User> selectByExample(UserExample example);

	User selectByPrimaryKey(String userId);

	int updateByExampleSelective(@Param("record") User record,
			@Param("example") UserExample example);

	int updateByExample(@Param("record") User record,
			@Param("example") UserExample example);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);


	List<WorkTableDTO> selectWorkTableUserInfo();
	/**
	 * 向用户账户中追加金额
	 * @param userId
	 * @param balanceAdd 追加金额
	 * @return 
	 */
	int addBalance(@Param("userId") String userId, @Param("balanceAdd") BigDecimal balanceAdd);
	/**
	 * 向用户账户中充�?积分
	 * @param userId
	 * @param scoreAdd  充�?积分
	 */
	int addScore(@Param("userId") String userId, @Param("scoreAdd")Integer scoreAdd);

	List<User> selectByExample4List(User record);
}