package com.reliance.gstn.service;
import java.util.List;

import com.reliance.gstn.model.FeedbackDetails;
public interface wizardFeedbackDetailService {
	
		public String addFeedback(FeedbackDetails feedbackDetails);
		List<FeedbackDetails> listFeedbackDetails(Integer orgUId)throws Exception;
		public List<FeedbackDetails> FeedbackDetails(Integer id, Integer userId)throws Exception;
		
	


}
