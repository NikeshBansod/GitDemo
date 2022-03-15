package com.reliance.gstn.dao;


import java.util.List;

import com.reliance.gstn.model.FeedbackDetails;
import com.reliance.gstn.model.MasterDescDetails;

public interface wizardFeedbackDao {

	List<FeedbackDetails> listFeedbackDetails(Integer uId);

	List<com.reliance.gstn.model.FeedbackDetails> FeedbackDetails(Integer id,
			Integer userId);

	List<MasterDescDetails> getMasterDesc(Integer masterDescDetails);

	List<MasterDescDetails> getAllMasterDesc();

}
