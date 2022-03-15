/**
 * 
 */
package com.reliance.gstn.admin.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.admin.dao.RateOfTaxDetailsDAO;
import com.reliance.gstn.admin.model.AdditionalChargeRateOfTaxDetails;
import com.reliance.gstn.admin.model.ProductRateOfTaxDetails;
import com.reliance.gstn.admin.model.ServiceRateOfTaxDetails;

/**
 * @author Rupali J
 *
 */
@Repository
public class RateOfTaxDetailsDAOImpl implements RateOfTaxDetailsDAO {
	
	private static final Logger logger = Logger.getLogger(RateOfTaxDetailsDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${product_rate_of_tax_list_query}")
	private String productTaxRateDetailsListQuery;
	
	
	@Value("${service_rate_of_tax_list_query}")
	private String serviceTaxRateDetailsListQuery;
	
	@Value("${additional_rate_of_tax_list_query}")
	private String additionalTaxRateDetailsListQuery;
	
	@Override
	public List<ProductRateOfTaxDetails> getProductTaxRatesInPercentage() throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(productTaxRateDetailsListQuery);
		@SuppressWarnings("unchecked")
		List<ProductRateOfTaxDetails> taxRateDetailsList  =(List<ProductRateOfTaxDetails>)query.list();
		
		logger.info("Exit");
		return taxRateDetailsList;
	}
	
	
	@Override
	public List<ServiceRateOfTaxDetails> getServiceTaxRatesInPercentage() throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(serviceTaxRateDetailsListQuery);
		@SuppressWarnings("unchecked")
		List<ServiceRateOfTaxDetails> serviceTaxRateDetailsList  =(List<ServiceRateOfTaxDetails>)query.list();
		
		logger.info("Exit");
		return serviceTaxRateDetailsList;
	}
	

	@Override
	public List<AdditionalChargeRateOfTaxDetails> getAdditionalChargeTaxRatesInPercentage() throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(additionalTaxRateDetailsListQuery);
		@SuppressWarnings("unchecked")
		List<AdditionalChargeRateOfTaxDetails> additionalTaxRateDetailsList = (List<AdditionalChargeRateOfTaxDetails>)query.list();
		
		logger.info("Exit");
		return additionalTaxRateDetailsList;
	}
	
}
