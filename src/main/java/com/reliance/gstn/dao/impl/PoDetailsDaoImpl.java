package com.reliance.gstn.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.dao.PoDetailsDao;
import com.reliance.gstn.model.PoDetails;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
//import com.reliance.gstn.util.LoggerUtil;

@Repository
public class PoDetailsDaoImpl implements PoDetailsDao {

	private static final Logger logger = Logger.getLogger(PoDetailsDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value(value = "${manage_po_details_list_query}")
	private String managePODetailsListQuery;
	
	@Value(value = "${po_details_by_customer_id}")
	private String pODetailsByCustomerAndValidDate;
	
	@Value(value = "${po_details_by_Product_id}")
	private String pODetailsByProductId;
	
	@Value(value = "${check_po_no_duplicacy}")
	private String checkPoNoDuplicacy;


	@Override
	public List<PoDetails> viewPoDetailsList(Integer getuId) throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(managePODetailsListQuery);
		query.setInteger("referenceId", getuId);
		query.setString("status", "1");

		@SuppressWarnings("unchecked")
		List<PoDetails> viewPoDetailsList = query.list();
		logger.info("Exit");
		return viewPoDetailsList;
	}

	@Override
	public String addPoDetails(PoDetails poDetails, Integer getuId,Integer orgUId) {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.openSession();
			poDetails.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			poDetails.setRefUserId(getuId);
			poDetails.setOrgRefId(orgUId);
			poDetails.setStatus("1");
			session.save(poDetails);
			session.close();
			response = GSTNConstants.SUCCESS;
		}catch(ConstraintViolationException  e){
			logger.error("Error in:",e);
			throw e;
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public PoDetails getPoDetailsById(Integer id) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();	
		PoDetails poDetails = (PoDetails) session.get(PoDetails.class, id);
		logger.info("Exit");
		return poDetails;
	}

	@Override
	public String updatePoDetails(PoDetails poDetails)  {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			
			Session session = sessionFactory.getCurrentSession();
			if(poDetails.getSelType().equalsIgnoreCase("Service")){
			poDetails.setPoAssocProductName(null);
			} else {
			poDetails.setPoAssocServiceName(null);
			}
			poDetails.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			session.saveOrUpdate(poDetails);
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public String deletePoDetails(Integer id) {
		String response = GSTNConstants.FAILURE;
		logger.info("Entry");
		try {
			Session session = sessionFactory.getCurrentSession();	
			PoDetails poDetails = (PoDetails) session.load(PoDetails.class, id);
			poDetails.setStatus("0");
			if(null != poDetails){
				session.saveOrUpdate(poDetails);
			}
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public List<PoDetails> getPoDetailsByPoCustomerName(String poCustomerName) throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(pODetailsByCustomerAndValidDate);
		query.setInteger("poCustomerName", Integer.parseInt(poCustomerName));
		
		List<PoDetails> poDetailsList = new ArrayList<PoDetails>();
		@SuppressWarnings("unchecked")
		List<Object[]> viewPoDetailsList = query.list();
		
		if (!viewPoDetailsList.isEmpty()) {
			for(int i = 0; i<viewPoDetailsList.size();i++){
				Object[] obj = viewPoDetailsList.get(i);
				PoDetails poDetails = new PoDetails();
				Integer poId = (Integer)obj[0];
				poDetails.setId(poId);
				String poNo = (String)obj[1];
				poDetails.setPoNo(poNo);
				java.sql.Timestamp poValidToDate = (java.sql.Timestamp)obj[3];
				poDetails.setPoValidToDateTemp(GSTNUtil.convertTimestampToString(poValidToDate));
				poDetailsList.add(poDetails);
			}
			
		}
		
		logger.info("Exit");
		return poDetailsList;
	}
	
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<PoDetails> getPoDetailsByProduct(Integer prdId) {
		logger.info("Entry");
		List<PoDetails>  poDetailsList = new ArrayList<PoDetails>();
		try {
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(pODetailsByProductId);
		query.setInteger("prdId", prdId);
		poDetailsList = (List<PoDetails>)query.list();
		
		
		} catch(Exception e){
			logger.error("Error in:",e);
			e.printStackTrace();
		}
		logger.info("Exit");
		return poDetailsList;
	}

	@Override
	public boolean checkIfPoNoExists(String poNo, Integer orgUId) throws Exception{
		
		logger.info("Entry");
		boolean poNoExists = false;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(checkPoNoDuplicacy);
		query.setString("poNo", poNo);
		query.setInteger("orgRefId", orgUId);
		@SuppressWarnings("unchecked")
		List<PoDetails> list = (List<PoDetails>)query.list();
		
		if ((!list.isEmpty())) {
			poNoExists = true;
		}
		logger.info("Exit");
		
		return poNoExists;
	}
	
}
