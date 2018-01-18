package com.jeeplus.modules.business.dao;

import com.jeeplus.common.persistence.annotation.MyBatisDao;
import com.jeeplus.modules.business.dto.FeedbackDTO;
import com.jeeplus.modules.business.entity.Feedback;
import com.jeeplus.modules.business.entity.FeedbackExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface FeedbackMapper {
    int countByExample(FeedbackExample example);

    int deleteByExample(FeedbackExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Feedback record);

    int insertSelective(Feedback record);

    List<Feedback> selectByExample(FeedbackExample example);

    Feedback selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Feedback record, @Param("example") FeedbackExample example);

    int updateByExample(@Param("record") Feedback record, @Param("example") FeedbackExample example);

    int updateByPrimaryKeySelective(Feedback record);

    int updateByPrimaryKey(Feedback record);

	List<Feedback> select4List(FeedbackDTO feedbackDTO);
	/**
	 * 未处理的意见反馈
	 */
	List<FeedbackDTO> selectUndealFeedbacks();
}