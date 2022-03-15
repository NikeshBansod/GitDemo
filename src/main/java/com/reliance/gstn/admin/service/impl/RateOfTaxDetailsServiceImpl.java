/**
 * 
 */
package com.reliance.gstn.admin.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.admin.dao.RateOfTaxDetailsDAO;
import com.reliance.gstn.admin.model.AdditionalChargeRateOfTaxDetails;
import com.reliance.gstn.admin.model.ProductRateOfTaxDetails;
import com.reliance.gstn.admin.model.ServiceRateOfTaxDetails;
import com.reliance.gstn.admin.service.RateOfTaxDetailsService;

/**
 * @author Rupali J
 *
 */
@Service
public class RateOfTaxDetailsServiceImpl implements RateOfTaxDetailsService {
	
	@Autowired
	public RateOfTaxDetailsDAO rateOfTaxDetailsDAO;

	@Transactional
	public List<ProductRateOfTaxDetails> getTaxRatesInPercentage() throws Exception{
		return rateOfTaxDetailsDAO.getProductTaxRatesInPercentage();
	}

	@Transactional
	public List<ServiceRateOfTaxDetails> getServiceTaxRatesInPercentage() throws Exception{
		return rateOfTaxDetailsDAO.getServiceTaxRatesInPercentage();
	}
	
	@Transactional
	public List<AdditionalChargeRateOfTaxDetails> getAdditionalChargeTaxRatesInPercentage() throws Exception{
		return rateOfTaxDetailsDAO.getAdditionalChargeTaxRatesInPercentage();
	}
}
