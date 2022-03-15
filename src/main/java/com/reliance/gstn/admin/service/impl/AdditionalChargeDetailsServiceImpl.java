package com.reliance.gstn.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.admin.dao.AdditionalChargeDetailsDao;
import com.reliance.gstn.admin.model.AdditionalChargeDetails;
import com.reliance.gstn.admin.service.AdditionalChargeDetailsService;

@Service
public class AdditionalChargeDetailsServiceImpl implements AdditionalChargeDetailsService {

	@Autowired
	private AdditionalChargeDetailsDao additionalChargeDetailsDao;

	
	@Override
	@Transactional
	public List<AdditionalChargeDetails> viewAdditionalChargeDetailsList(Integer getuId) throws Exception{
		 return additionalChargeDetailsDao.viewAdditionalChargeDetailsList(getuId);
	}
	
	@Override
	@Transactional
	public String addChargesDetails(AdditionalChargeDetails additionalChargeDetails, Map<?, ?> mapValues) throws ConstraintViolationException, Exception {
		return additionalChargeDetailsDao.addChargesDetails(additionalChargeDetails, mapValues);
	}
	
	
	@Override
	@Transactional
	public List<AdditionalChargeDetails> getAddChargesList(Integer orgId) throws Exception{
		return additionalChargeDetailsDao.getAddChargesList(orgId);
	}
		
	@Override
	@Transactional
	public String updateAdditionalChargeDetails(AdditionalChargeDetails additionalChargeDetails, Map<?, ?> mapValues)throws DataIntegrityViolationException, Exception{
		return additionalChargeDetailsDao.updateAdditionalChargeDetails(additionalChargeDetails, mapValues);
	}

	@Override
	@Transactional
	public AdditionalChargeDetails getAdditionalChargeDetailsById(Integer id) throws Exception{
		return additionalChargeDetailsDao.getAdditionalChargeDetailsById(id);
	}
	
	
	@Override
	@Transactional
	public String deleteAdditionalChargeDetails(Integer id) throws Exception{
		 return additionalChargeDetailsDao.deleteAdditionalChargeDetails(id);
	}

	@Override
	@Transactional
	public List<String> getAdditionalChargesListAutoComplete(String query, Integer orgUId) throws Exception {
		return additionalChargeDetailsDao.getAdditionalChargesListAutoComplete( query,  orgUId);
	}

	@Override
	@Transactional
	public AdditionalChargeDetails getAdditionalChargeByChargeName(String chargeName, Integer orgUId) throws Exception {
		// TODO Auto-generated method stub
		return additionalChargeDetailsDao.getAdditionalChargeByChargeName( chargeName, orgUId);
	}
	
}
