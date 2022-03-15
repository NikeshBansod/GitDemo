/**
 * 
 */
package com.reliance.gstn.admin.service;

import java.util.List;

import com.reliance.gstn.admin.model.AdditionalChargeRateOfTaxDetails;
import com.reliance.gstn.admin.model.ProductRateOfTaxDetails;
import com.reliance.gstn.admin.model.ServiceRateOfTaxDetails;

/**
 * @author Rupali J
 *
 */
public interface RateOfTaxDetailsService {

	List<ProductRateOfTaxDetails> getTaxRatesInPercentage()throws Exception;
	
	List<ServiceRateOfTaxDetails> getServiceTaxRatesInPercentage()throws Exception;
	
	List<AdditionalChargeRateOfTaxDetails> getAdditionalChargeTaxRatesInPercentage() throws Exception;

}
