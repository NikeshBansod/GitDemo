package com.reliance.gstn.service;

import java.util.List;

import com.reliance.gstn.model.FeedbackDetails;
import com.reliance.gstn.model.MasterDescDetails;

public interface FeedbackDetailService {

	public String addFeedback(FeedbackDetails feedbackDetails);

	public List<FeedbackDetails> listFeedbackDetails(Integer getuId);

	public List<FeedbackDetails> FeedbackDetails(Integer id, Integer uId);

	public List<MasterDescDetails> getMasterDesc(Integer masterDescDetails);

	public List<MasterDescDetails> getAllMasterDesc();

	public String getImageFile(String image1) throws Exception;
}
