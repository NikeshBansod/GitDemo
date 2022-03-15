/**
 * 
 */
package com.reliance.gstn.admin.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.admin.dao.AdminUserMasterDao;
import com.reliance.gstn.admin.model.AdminFeedbackDetails;
import com.reliance.gstn.admin.model.AdminUserMaster;
import com.reliance.gstn.admin.service.AdminUserMasterService;

/**
 * @author Nikesh.Bansod
 *
 */
@Service
public class AdminUserMasterServiceImpl implements AdminUserMasterService {
	
	@Autowired
	private AdminUserMasterDao  adminUserMasterDao;

	@Override
	@Transactional
	public AdminUserMaster getUserDetails(String userId, String password) {
		// TODO Auto-generated method stub
		return adminUserMasterDao.getUserDetails(userId, password);
	}
	
	@Override
	@Transactional
	public List<AdminFeedbackDetails> getUploadHistory(Integer masterDesc){
		
		return adminUserMasterDao.getUploadHistory(masterDesc);
	};

}
