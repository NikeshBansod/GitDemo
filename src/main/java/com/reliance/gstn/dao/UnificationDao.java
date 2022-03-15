package com.reliance.gstn.dao;

import org.hibernate.Session;

import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.model.UserMasterUnification;

public interface UnificationDao {
	
	public String saveOrUpdateUserDetails(UserMasterUnification userMasterUni);
	
	public String getEmailByContact(String contactNo);

}
