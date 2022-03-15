package com.reliance.gstn.admin.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.admin.dao.AdditionalChargeDetailsDao;
import com.reliance.gstn.admin.model.AdditionalChargeDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.Product;
import com.reliance.gstn.util.GSTNConstants;

	@Repository
	public class AdditionalChargeDetailsDaoImpl implements AdditionalChargeDetailsDao {

		private static final Logger logger = Logger.getLogger(HSNDaoImpl.class);
		
		@Autowired
		private SessionFactory sessionFactory;
		
		@Value(value = "${add_charges_details_list_by_refid}")
		private String addChargeDetailsListByRefId;
		
		@Value(value = "${manage_add_charges_details_list_query}")
		private String manageAddChargeDetailsListQuery;
		
		@Value(value = "${fetch_additional_charges_list_autoComplete_query}")
		private String fetchAddChargeDetailsListAutoCompleteQuery;
		
		@Value(value = "${fetch_additional_charges_list_by_charge_name}")
		private String fetchAdditionalChargeByChargeName;
		
		@Override
		public List<AdditionalChargeDetails> viewAdditionalChargeDetailsList(Integer uId) throws Exception{
			logger.info("Entry");
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(manageAddChargeDetailsListQuery);
			query.setInteger("referenceId", uId);
			query.setString("status", "1");

			@SuppressWarnings("unchecked")
			List<AdditionalChargeDetails> viewAdditionalChargeDetailsList =(List<AdditionalChargeDetails>)query.list();
			logger.info("Exit");
			return viewAdditionalChargeDetailsList;
		}
		
		/*@Override
		public List<AdditionalChargeDetails> getAddChargesList(String idsValuesToFetch) throws Exception{
			logger.info("Entry");
			Session session = sessionFactory.getCurrentSession();
			List<Integer> ali = GSTNUtil.getListFromString(idsValuesToFetch);
			String placeHoder = GSTNUtil.getPlaceHoderString(ali);
			String queryString =  addChargeDetailsListByRefId;
			queryString=queryString.replace("refId", placeHoder);
			Query query = session.createQuery(queryString);
		//	query.setString("referenceId", idsValuesToFetch);
			
			for (int i = 0; i < ali.size(); i++) {
				query.setInteger(i, ali.get(i));
			}
			
			@SuppressWarnings("unchecked")
			List<AdditionalChargeDetails> viewAddChargeDetailsList = (List<AdditionalChargeDetails>)query.list();
			
			logger.info("Exit");
			return viewAddChargeDetailsList;
		}*/
		
		@Override
		public List<AdditionalChargeDetails> getAddChargesList(Integer orgId) throws Exception{
			logger.info("Entry");
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(addChargeDetailsListByRefId);
			query.setInteger("refOrgId", orgId);
						
			@SuppressWarnings("unchecked")
			List<AdditionalChargeDetails> viewAddChargeDetailsList = (List<AdditionalChargeDetails>)query.list();
			
			logger.info("Exit");
			return viewAddChargeDetailsList;
		}
		
		@Override
		public String addChargesDetails(AdditionalChargeDetails additionalChargeDetails,
				Map<?, ?> mapValues) throws ConstraintViolationException, Exception {
			logger.info("Entry");
			String response = GSTNConstants.FAILURE;
			try {
				Session session = sessionFactory.openSession();
				LoginMaster loginMaster = (LoginMaster)mapValues.get("loginMaster");
				additionalChargeDetails.setStatus("1");
				additionalChargeDetails.setCreatedBy(loginMaster.getuId().toString());
				additionalChargeDetails.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
				additionalChargeDetails.setReferenceId(loginMaster.getuId());
				additionalChargeDetails.setRefOrgId(loginMaster.getOrgUId());
				session.save(additionalChargeDetails);
				response = GSTNConstants.SUCCESS;
			} catch(ConstraintViolationException  e){
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
		 public String updateAdditionalChargeDetails(AdditionalChargeDetails additionalChargeDetails, Map<?, ?> mapValues)throws ConstraintViolationException, Exception{
			 
			 logger.info("Entry");
				String response = GSTNConstants.FAILURE;
				try {
					Session session = sessionFactory.getCurrentSession();
					LoginMaster loginMaster = (LoginMaster)mapValues.get("loginMaster");
					additionalChargeDetails.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
					additionalChargeDetails.setReferenceId(loginMaster.getuId());
					additionalChargeDetails.setUpdatedBy(loginMaster.getuId().toString());
					additionalChargeDetails.setRefOrgId(loginMaster.getOrgUId());
					session.saveOrUpdate(additionalChargeDetails);
					response = GSTNConstants.SUCCESS;
				} catch(DataIntegrityViolationException e){
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
	  public AdditionalChargeDetails getAdditionalChargeDetailsById(Integer id) throws Exception{
		 
		 logger.info("Entry");
		 AdditionalChargeDetails addChargeDetails = null;
		 try {
			 Session session = sessionFactory.getCurrentSession();	
			 addChargeDetails = (AdditionalChargeDetails) session.get(AdditionalChargeDetails.class, id);
		 }catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		 }
		 logger.info("Exit");
		return addChargeDetails;
		 
	   }
		 
		@Override
		public String deleteAdditionalChargeDetails(Integer id) throws Exception {
			String response = GSTNConstants.FAILURE;
			logger.info("Entry");
			try {
				Session session = sessionFactory.getCurrentSession();	
				Object persistAddChargeDetail = session.get(AdditionalChargeDetails.class, id);
				
				if(persistAddChargeDetail != null){
				session.delete(persistAddChargeDetail);
				response = GSTNConstants.SUCCESS;
				}
				
			} catch (Exception e) {
				logger.error("Error in:",e);
				throw e;
			}
			logger.info("Exit");
			return response;
		}

		@Override
		public List<String> getAdditionalChargesListAutoComplete(String parameter, Integer orgUId) throws Exception {
			logger.info("Entry");
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(fetchAddChargeDetailsListAutoCompleteQuery);
			query.setString("inputData", '%'+parameter+'%');
			query.setInteger("orgRefId", orgUId);
			
			@SuppressWarnings("unchecked")
			List<String> addChgNameList = query.list();
			
			logger.info("Exit");
			return addChgNameList;
		}

		@Override
		public AdditionalChargeDetails getAdditionalChargeByChargeName(String chargeName, Integer orgUId) throws Exception {
			logger.info("Entry");
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(fetchAdditionalChargeByChargeName);
			query.setString("inputData", chargeName+'%');
			query.setInteger("orgRefId", orgUId);
			
			@SuppressWarnings("unchecked")
			List<AdditionalChargeDetails> addChgList = (List<AdditionalChargeDetails>)query.list();
			logger.info("Exit");
			return addChgList.get(0);
		}
}
