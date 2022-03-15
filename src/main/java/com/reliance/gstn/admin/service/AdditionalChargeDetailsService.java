package com.reliance.gstn.admin.service;

import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import com.reliance.gstn.admin.model.AdditionalChargeDetails;

public interface AdditionalChargeDetailsService {

	List<AdditionalChargeDetails> viewAdditionalChargeDetailsList(Integer getuId) throws Exception;
	
	String addChargesDetails(AdditionalChargeDetails additionalChargeDetails, Map<?, ?> mapValues) throws ConstraintViolationException, Exception;
	
	List<AdditionalChargeDetails> getAddChargesList(Integer orgId) throws Exception;
	
	String updateAdditionalChargeDetails(AdditionalChargeDetails additionalChargeDetails, Map<?, ?> mapValues)throws DataIntegrityViolationException, Exception;

	AdditionalChargeDetails getAdditionalChargeDetailsById(Integer id) throws Exception;
	
	String deleteAdditionalChargeDetails(Integer id) throws Exception;

	List<String> getAdditionalChargesListAutoComplete(String query, Integer orgUId) throws Exception;

	AdditionalChargeDetails getAdditionalChargeByChargeName(String chargeName, Integer orgUId) throws Exception;
	
}
