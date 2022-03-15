package com.reliance.gstn.service;

import java.util.List;

import com.reliance.gstn.model.PoDetails;

public interface PoDetailsService {

	List<PoDetails> viewPoDetailsList(Integer getuId) throws Exception;

	String addPoDetails(PoDetails poDetails, Integer getuId, Integer orgUId);

	PoDetails getPoDetailsById(Integer id);

	String updatePoDetails(PoDetails poDetails);

	String deletePoDetails(Integer id);

	List<PoDetails> getPoDetailsByPoCustomerName(String poCustomerName)throws Exception;
	
	List<PoDetails> getPoDetailsByProduct(Integer prdId);

	boolean checkIfPoNoExists(String poNo, Integer orgUId) throws Exception;

}
