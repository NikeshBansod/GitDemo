/**
 * 
 */
package com.reliance.gstn.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.dao.SACDAO;
import com.reliance.gstn.model.SACCode;
import com.reliance.gstn.service.SACService;

/**
 * @author Nikesh.Bansod
 *
 */
@Service
public class SACServiceImpl implements SACService {
	
	@Autowired
	SACDAO sacDAO;

	@Override
	@Transactional
	public List<Object[]> getSACCodeList(String query) {
		// TODO Auto-generated method stub
		return sacDAO.getSACCodeList( query);
	}

	@Override
	@Transactional
	public String getSacCodeBySacDescription(String sacCodeDescription) {
		// TODO Auto-generated method stub
		return sacDAO.getSacCodeBySacDescription(sacCodeDescription);
	}

	@Override
	@Transactional
	public SACCode getSACCodeData(String sacCode, Integer deliveryStateId) {
		// TODO Auto-generated method stub
		return sacDAO. getSACCodeData(sacCode, deliveryStateId);
	}

	@Override
	@Transactional
	public SACCode getIGSTValueBySacCode(String sacCode, String sacDescription) {
		// TODO Auto-generated method stub
		return sacDAO.getIGSTValueBySacCode(sacCode,sacDescription);
	}

	@Override
	@Transactional
	public SACCode getSACCodeById(Integer sacCodePkId) {
		// TODO Auto-generated method stub
		return sacDAO.getSACCodeById(sacCodePkId);
	}

	@Override
	public List<Object[]> getSACCodeListWithMs(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
