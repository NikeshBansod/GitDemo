/**
 * 
 */
package com.reliance.gstn.dao;

import java.util.List;

import com.reliance.gstn.admin.model.AdvolCessRateDetails;
import com.reliance.gstn.admin.model.NonAdvolCessRateDetails;
import com.reliance.gstn.model.UnitOfMeasurement;

/**
 * @author Pradeep.Gangapuram
 *
 */
public interface AdvolAndNonAdvolCessServiceDAO {

	List<AdvolCessRateDetails> getAdvolCessRateDetailList()throws Exception;
	
	List<NonAdvolCessRateDetails> getNonAdvolCessRateDetailList()throws Exception;

}
