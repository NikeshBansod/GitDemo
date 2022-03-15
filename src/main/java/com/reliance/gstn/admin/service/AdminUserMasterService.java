/**
 * 
 */
package com.reliance.gstn.admin.service;

import java.util.List;

import com.reliance.gstn.admin.model.AdminFeedbackDetails;
import com.reliance.gstn.admin.model.AdminUserMaster;

/**
 * @author Nikesh.Bansod
 *
 */
public interface AdminUserMasterService {

	AdminUserMaster getUserDetails(String userId, String password);
	
	List<AdminFeedbackDetails> getUploadHistory(Integer masterDesc);

}
