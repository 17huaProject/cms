package com.jeeplus.modules.business.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.modules.business.dao.FeedbackMapper;
import com.jeeplus.modules.business.dto.FeedbackDTO;
import com.jeeplus.modules.business.entity.Feedback;
import com.jeeplus.modules.business.entity.FeedbackExample;

@Service("FeedbackService")
public class FeedbackService {

	@Autowired
	FeedbackMapper feedbackMapper ;
	
	public Page<Feedback> listFeedbacks(Page<Feedback> page, FeedbackDTO feedbackDTO) {
		Feedback feedback = new Feedback() ;
		feedback.setPage(page);
		List<Feedback> list = feedbackMapper.select4List(feedbackDTO);
		page.setList(list);
		return page;
	}
	

	public Feedback getFeedback(Integer id) {
		Feedback feedback = feedbackMapper.selectByPrimaryKey(id);
		if (feedback == null) {
			feedback = new Feedback();
		}
		return feedback;
	}

	public int deleteFeedback(Integer id) {
		return feedbackMapper.deleteByPrimaryKey(id);
	}

	public int deleteMultiFeedbacks(List<Integer> ids) {
		FeedbackExample example = new FeedbackExample();
		example.createCriteria().andIdIn(ids) ;
		return feedbackMapper.deleteByExample(example );
		
	}

	/**
	 * 更新 </br>
	 * 本模块无需插入功能
	 * @param feedback
	 */
	public int saveFeedback(Feedback feedback) {
		int num = 0 ;
		if(feedback != null ){
			feedback.preUpdate();
			num = feedbackMapper.updateByPrimaryKeySelective(feedback);
		}
		return num;
	}

	/**
	 * 未处理的意见反馈
	 */
	public List<FeedbackDTO> listUndealFeedbacks() {
		List<FeedbackDTO> undealFeedbacks = feedbackMapper.selectUndealFeedbacks() ;
		return undealFeedbacks;
	}

}
