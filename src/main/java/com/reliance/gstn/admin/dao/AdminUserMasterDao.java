/**
 * 
 */
package com.reliance.gstn.admin.dao;

import java.util.List;

import com.reliance.gstn.admin.model.AdminFeedbackDetails;
import com.reliance.gstn.admin.model.AdminUserMaster;

/**
 * @author Nikesh.Bansod
 *
 */
public interface AdminUserMasterDao {

	AdminUserMaster getUserDetails(String userId, String password);
	
	List<AdminFeedbackDetails> getUploadHistory(Integer masterDesc);

}
