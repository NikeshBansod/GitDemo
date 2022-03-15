package com.reliance.gstn.external.wizard.dao.impl;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.external.wizard.dao.WithoutInvoiceEwayBillDao;
import com.reliance.gstn.model.EwayBillWIItem;
import com.reliance.gstn.model.EwayBillWITransaction;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.util.GSTNConstants;

@Repository
public class WithoutInvoiceEwayBillDaoImpl implements WithoutInvoiceEwayBillDao {

	private static final Logger logger = Logger.getLogger(WithoutInvoiceEwayBillDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public String saveWIEWayBillItem(EwayBillWITransaction ewayBillWITransaction, LoginMaster loginMaster) throws ConstraintViolationException, Exception {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try
		{			
			ewayBillWITransaction.setSupplyType("I");
			ewayBillWITransaction.setSubSupplyType("1");
			ewayBillWITransaction.setSubSupplyDesc("IJKL");
			ewayBillWITransaction.setDocumentType("INV");
			ewayBillWITransaction.setDocumentNo("12345");
			ewayBillWITransaction.setDocumentDate(new java.sql.Timestamp(new Date().getTime()));
			ewayBillWITransaction.setFromGstin("27AAACA9696D1ZS");
			ewayBillWITransaction.setFromTraderName("Apollo");
			ewayBillWITransaction.setFromAddress1("Goregaon");
			ewayBillWITransaction.setFromAddress2("Mumbai");
			ewayBillWITransaction.setFromPlace("AAA");
			ewayBillWITransaction.setFromPincode(400001);
			ewayBillWITransaction.setActFromStateCode(1);
			ewayBillWITransaction.setFromStateCode(27);
			ewayBillWITransaction.setToGstin("27AAACA9999D1ZS");
			ewayBillWITransaction.setToTraderName("MRF");
			ewayBillWITransaction.setToAddress1("Airoli");
			ewayBillWITransaction.setToAddress2("Navi Mumbai");
			ewayBillWITransaction.setToPlace("BBB");
			ewayBillWITransaction.setToPincode(400708);
			ewayBillWITransaction.setActToStateCode(1);
			ewayBillWITransaction.setToStateCode(27);
			ewayBillWITransaction.setTotalValue(1000d);
			ewayBillWITransaction.setSgstValue(50d);
			ewayBillWITransaction.setCgstValue(50d);
			ewayBillWITransaction.setIgstValue(0d);
			ewayBillWITransaction.setCessValue(100d);
			ewayBillWITransaction.setTotInvValue(1000d);
			ewayBillWITransaction.setTransId("123457890");
			ewayBillWITransaction.setTransName("AAAABBB");
			ewayBillWITransaction.setTransMode("1");
			ewayBillWITransaction.setTransDocNo("1212");
			ewayBillWITransaction.setTransDistance(100d);
			ewayBillWITransaction.setTransDocDate(new java.sql.Timestamp(new Date().getTime()));
			ewayBillWITransaction.setVehicleNo("MH01CB0101");
			ewayBillWITransaction.setVehicleType("R");
			ewayBillWITransaction.setAppName("SXSX");
			ewayBillWITransaction.setStatus("Success");
			ewayBillWITransaction.setCreatedBy(String.valueOf(loginMaster.getuId()));
			ewayBillWITransaction.setUpdatedBy(String.valueOf(loginMaster.getuId()));
			ewayBillWITransaction.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			ewayBillWITransaction.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			
			
			EwayBillWIItem ewayBillWIItem = new EwayBillWIItem();			
			ewayBillWIItem.setProductName("Cookies");
			ewayBillWIItem.setProductDesc("Biscuit, cookie etc");
			ewayBillWIItem.setHsnCode("19053100");
			ewayBillWIItem.setQuantity(2d);
			ewayBillWIItem.setQuantityUnit("PACKS");
			ewayBillWIItem.setSgstRate(5d);
			ewayBillWIItem.setCgstRate(5d);
			ewayBillWIItem.setIgstRate(0d);
			ewayBillWIItem.setCessRate(100d);
			ewayBillWIItem.setCessAdVol(2);
			ewayBillWIItem.setTaxableAmount(1000d);
			ewayBillWIItem.setCreatedBy(String.valueOf(loginMaster.getuId()));
			ewayBillWIItem.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			ewayBillWIItem.setUpdatedBy(String.valueOf(loginMaster.getuId()));
			ewayBillWIItem.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			
			ewayBillWIItem.setEwayBillWITransaction(ewayBillWITransaction);
			
			java.util.List<EwayBillWIItem> itemList = new ArrayList<EwayBillWIItem>(); 
			itemList.add(ewayBillWIItem);

			ewayBillWITransaction.setEwayBillWIItem(itemList);
			
			Session session = sessionFactory.getCurrentSession();				 		
			session.save(ewayBillWITransaction);
			
			response = GSTNConstants.SUCCESS;
		}catch(ConstraintViolationException e){
			logger.error("Error in:",e);
			throw e;
		}catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}

}
