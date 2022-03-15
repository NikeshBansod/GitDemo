/**
 * 
 */
package com.reliance.gstn.service;

import java.util.List;

import com.reliance.gstn.admin.model.AdvolCessRateDetails;
import com.reliance.gstn.admin.model.NonAdvolCessRateDetails;
import com.reliance.gstn.model.UnitOfMeasurement;

/**
 * @author Rupali.J
 *
 */
public interface AdvolAndNonAdvolCessService {

	List<AdvolCessRateDetails> getAdvolCessRateDetailList()throws Exception;
	
	List<NonAdvolCessRateDetails> getNonAdvolCessRateDetailList()throws Exception;

}
