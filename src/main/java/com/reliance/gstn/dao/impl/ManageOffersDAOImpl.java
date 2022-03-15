/**
 * 
 */
package com.reliance.gstn.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.dao.ManageOffersDAO;
import com.reliance.gstn.model.ManageOffers;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
//import com.reliance.gstn.util.LoggerUtil;

/**
 * @author Pradeep.Gangapuram
 *
 */
@Repository
public class ManageOffersDAOImpl implements ManageOffersDAO {
	
	private static final Logger logger = Logger.getLogger(ManageOffersDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${manage_offers_list_query}")
	private String manageOffersListQuery;

	@Value("${manage_offers_remove_query}")
	private String removemanageOfferQuery;

	@Value("${manage_offers_list_by_offertype}")
	private String manageOffersListByOffertype;

	@Value("${check_offer_org_duplicacy}")
	private String checkOfferDuplicacy;
	
	@Override
	public List<ManageOffers> listManageOffers(String idsValuesToFetch,Integer orgUId) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		List<Integer> ali=GSTNUtil.getListFromString(idsValuesToFetch);
		String placeHoder=GSTNUtil.getPlaceHoderString(ali);
		String manageOffersListQueryStr =manageOffersListQuery.replace("replace_referenceId", placeHoder);
		Query query = session.createQuery(manageOffersListQueryStr);
		int counter =0;
		for (int i = 0; i < ali.size(); i++) {
			query.setInteger(i, ali.get(i));
			counter=i;
		}
		query.setInteger(++counter, orgUId);
		
		@SuppressWarnings("unchecked")
		List<ManageOffers> manageoffersList = query.list();
		logger.info("Exit");
		return manageoffersList;
	}
	
	@Override
	public String addManageOffers(ManageOffers manageOffers) {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			manageOffers.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			session.save(manageOffers);
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return response;
	}
	
	@Override
	public ManageOffers getManageOffersById(Integer id) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();		
		ManageOffers offers = (ManageOffers) session.get(ManageOffers.class, id);
		logger.info("Exit");
		return offers;
	}

	@Override
	public String updateManageOffers(ManageOffers manageOffers) throws Exception{
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			manageOffers.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			
			session.saveOrUpdate(manageOffers);
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}
	
	@Override
	public String removeManageOffers(ManageOffers manageOffers) throws Exception{
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
	
			Query query = session.createQuery(removemanageOfferQuery);
			query.setInteger("id",manageOffers.getId());
			query.setString("status",manageOffers.getStatus());
			query.setParameter("updatedOn", new java.sql.Timestamp(new Date().getTime()));
			query.setString("updatedBy",manageOffers.getUpdatedBy());
			query.executeUpdate();
			
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public List<ManageOffers> getManageOffersByOfferType(String offerTypeId, String offerType) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(manageOffersListByOffertype);
		query.setString("offerType", offerType);
		query.setString("offerTypeId", offerTypeId);
		
		@SuppressWarnings("unchecked")
		List<ManageOffers> manageoffersList = (List<ManageOffers>)query.list();
		logger.info("Exit");
		return manageoffersList;
	}

	@Override
	public boolean checkIfOfferExists(String offer, Integer orgUId) {
		logger.info("Entry");
		boolean gstinRegistered = false;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(checkOfferDuplicacy);
		query.setString("offerName", offer);
		query.setInteger("orgRefId", orgUId);
		@SuppressWarnings("unchecked")
		List<ManageOffers> list = (List<ManageOffers>)query.list();
		
		if ((!list.isEmpty())) {
			gstinRegistered = true;
		}
		logger.info("Exit");
		
		return gstinRegistered;
	}

}
