package com.reliance.gstn.admin.dao;

import java.util.List;

import com.reliance.gstn.model.SACCode;

public interface SACCodeDao {

	List<SACCode> getSACCodeList();

	String addSACCode(SACCode sacCode);

	SACCode getSACDetailsById(Integer id);

	String updateSACDetails(SACCode sacCode) throws Exception;

	String removeSACDetails(Integer id);
	
}
