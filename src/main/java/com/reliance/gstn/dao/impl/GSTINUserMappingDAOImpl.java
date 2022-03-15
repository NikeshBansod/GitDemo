/**
 * 
 */
package com.reliance.gstn.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.admin.exception.DuplicateRecordExistException;
import com.reliance.gstn.dao.GSTINUserMappingDAO;
import com.reliance.gstn.model.GSTINUserMapping;
import com.reliance.gstn.model.GstinLocation;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.UserGSTINMapping;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.GSTINDetailsService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.GSTNConstants;
//import com.reliance.gstn.util.LoggerUtil;
import com.reliance.gstn.util.GSTNUtil;

/**
 * @author Pradeep.Gangapuram
 *
 */

@Repository
public class GSTINUserMappingDAOImpl implements GSTINUserMappingDAO {

	private static final Logger logger = Logger.getLogger(GSTINUserMappingDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private UserMasterService userMasterService;
	
	@Autowired
	private GSTINDetailsService gstinDetailsService;

	@Value("${gstin_user_list_query}")
	private String gstinDetailListQuery;
	
	@Value("${user_gstin_list_query}")
	private String userGstinDetailListQuery;
	
	/*@Value("${gstin_user_remove_query}")
	private String removeGstinUserQuery;*/
	
	@Value("${gstin_user_by_refuser}")
	private String GstinUserByRefUser;
	
	@Value("${check_user_mapping_duplicacy}")
	private String checkGstinUserDuplicacy;
	
	@Value("${check_gstin_user_mapping_exist}")
	private String checkGstinUserExist;
	
	@Value("${check_gstin_user_loc_mapping_exist}")
	private String checkGstinUserLocExist;
	
	
	@Value("${get_gstin_user_by_org_id}")
	private String getGstnUsrByOrgId;
	
	@Value("${get_user_gstin_mapping_by_gstin}")
	private String getMappingListByGstinId;
	
	@Value("${get_gstin_location_by_gstin}")
	private String getgstnlocationListByGstinId;
	
	
	@Override
	public String addGSTINUserMapping(GSTINUserMapping gstinUserMapping) throws Exception{
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			gstinUserMapping.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			session.save(gstinUserMapping);
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			
			logger.error("Error in:",e);
			throw e;
		}
	
		return response;
	}

	@Override
	public List<GSTINUserMapping> getGSTINUserMappingList(String idsValuesToFetch) throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		List<Integer> ali = GSTNUtil.getListFromString(idsValuesToFetch);
		String placeHoder = GSTNUtil.getPlaceHoderString(ali);
		String gstinDetailListQueryStr=gstinDetailListQuery.replace("replace_referenceId", placeHoder);
		Query query = session.createQuery(gstinDetailListQueryStr);
		for (int i = 0; i < ali.size(); i++) {
			query.setInteger(i, ali.get(i));
		}
		@SuppressWarnings("unchecked")
		List<GSTINUserMapping> gstinUserMappingList = (List<GSTINUserMapping>)query.list();
		logger.info("Exit");
		return gstinUserMappingList;
	}

	@Override
	public GSTINUserMapping getGSTINUserMappingById(Integer id) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();		
		GSTINUserMapping msc = (GSTINUserMapping) session.load(GSTINUserMapping.class, id);
		logger.info("Exit");
		return msc;
	}

	@Override
	public String updateGstinUserMapping(GSTINUserMapping gstinUserMapping) throws Exception{
		logger.info("Exit");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			gstinUserMapping.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			
			session.saveOrUpdate(gstinUserMapping);
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public String removeGstinUserMapping(GSTINUserMapping gstinUserMapping) throws Exception{
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			Object persistentInstance = session.load(GSTINUserMapping.class, gstinUserMapping.getId());
			if(persistentInstance != null){
				session.delete(persistentInstance);
				response = GSTNConstants.SUCCESS;
			}
			//remove UserMaster mapping
		//	Query query = session.createQuery("update GSTINUserMapping set status = :status, updatedOn = :updatedOn, updatedBy = :updatedBy where id = :id");
			/*Query query = session.createQuery(removeGstinUserQuery);
			query.setInteger("id",gstinUserMapping.getId());
			query.setString("status",gstinUserMapping.getStatus());
			query.setParameter("updatedOn", new java.sql.Timestamp(new Date().getTime()));
			query.setString("updatedBy",gstinUserMapping.getUpdatedBy());
			query.executeUpdate();*/
			
			
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		return response;
	}

	@Override
	public String getGSTINUserMappingByReferenceUserId(Integer getuId) throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(GstinUserByRefUser);
		query.setInteger("referenceUserId",getuId);
		
		String users = null;
		@SuppressWarnings("unchecked")
		List<GSTINUserMapping> gstinUserMappingList = (List<GSTINUserMapping>)query.list();
		if(!gstinUserMappingList.isEmpty()){
			users = gstinUserMappingList.get(0).getGstinId();
		}
		
		logger.info("Exit");
		return users;
	}
	
	
	
	@Override
	public boolean checkIfGSTINUserExists(Integer referenceUserId) {
		logger.info("Entry");
		boolean gstinUserDuplicacy = false;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(checkGstinUserDuplicacy);
		query.setInteger("referenceUserId", referenceUserId);
		@SuppressWarnings("unchecked")
		List<GSTINUserMapping> list = (List<GSTINUserMapping>)query.list();
		
		if ((!list.isEmpty())) {
			gstinUserDuplicacy = true;
		}
		logger.info("Exit");
		
		return gstinUserDuplicacy;
	}
	
	@Override
	public String addUserGSTINMapping(UserGSTINMapping gstinMapping, Map<?, ?> mapValues) throws DuplicateRecordExistException, Exception{
		String response = GSTNConstants.FAILURE;
		try {
			LoginMaster loginMaster = (LoginMaster)mapValues.get("loginMaster");
			GstinLocation gstinLocation = null;
			List<UserGSTINMapping> userGSTINMappingList = new ArrayList<UserGSTINMapping>();
			List<UserMaster> userList = userMasterService.getUserListDetailsById(gstinMapping.getGstinUserIds());
			if(gstinMapping.getGstinAddressMapping().getId() != null){
				gstinLocation =  gstinDetailsService.listGstinLocationDetail(gstinMapping.getGstinAddressMapping().getId());				
				userGSTINMappingList = getUserGSTINMappingListForUniqueRecords(gstinMapping.getGstinId(), gstinMapping.getGstinAddressMapping().getId());			
			} else {
				userGSTINMappingList = getUserGSTINMappingListForUniqueRecords(gstinMapping.getGstinId(), null);
				
			}
			if(!userGSTINMappingList.isEmpty()){
				throw new DuplicateRecordExistException();
			}
			gstinMapping.setStatus("1");
			gstinMapping.setCreatedBy(loginMaster.getuId().toString());
			gstinMapping.setRefOrgId(loginMaster.getOrgUId());
			gstinMapping.setReferenceUserId(loginMaster.getuId());
			gstinMapping.setGstinUserSet(userList);
			gstinMapping.setGstinId(gstinMapping.getGstinId()); 
			gstinMapping.setGstinAddressMapping(gstinLocation); 
			Session session = sessionFactory.getCurrentSession();
			gstinMapping.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			session.save(gstinMapping);
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			
			logger.error("Error in:",e);
			throw e;
		}
	
		return response;
	}
	
	
	@Override
	public List<UserGSTINMapping> getUserGSTINMappingList(String idsValuesToFetch) throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		List<Integer> ali = GSTNUtil.getListFromString(idsValuesToFetch);
		String placeHoder = GSTNUtil.getPlaceHoderString(ali);
		String gstinDetailListQueryStr=userGstinDetailListQuery.replace("replace_referenceId", placeHoder);
		Query query = session.createQuery(gstinDetailListQueryStr);
		for (int i = 0; i < ali.size(); i++) {
			query.setInteger(i, ali.get(i));
		}
		@SuppressWarnings("unchecked")
		List<UserGSTINMapping> gstinUserMappingList = (List<UserGSTINMapping>)query.list();
		logger.info("Exit");
		return gstinUserMappingList;
	}
	
	@Override
	public UserGSTINMapping getUserGSTINMappingById(Integer id) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();		
		UserGSTINMapping ugm = (UserGSTINMapping) session.get(UserGSTINMapping.class, id);
		logger.info("Exit");
		return ugm;
	}
	
	@Override
	public List<UserGSTINMapping> getUserGSTINMappingByGstinId(Integer gstinid){
		
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();	
		Query query = session.createQuery(getMappingListByGstinId);
		query.setInteger("gstinId", gstinid);
	
		@SuppressWarnings("unchecked")
		List<UserGSTINMapping> userGSTINMappings = (List<UserGSTINMapping>)query.list();
		return userGSTINMappings;
	}

	@Override
	public String updateUserGstinMapping(UserGSTINMapping userGSTINMapping, Map<?, ?> mapValues) throws Exception{
		logger.info("Exit");
		String response = GSTNConstants.FAILURE;
		try {
			LoginMaster loginMaster = (LoginMaster)mapValues.get("loginMaster");
			List<UserMaster> userList = userMasterService.getUserListDetailsById(userGSTINMapping.getGstinUserIds());
			GstinLocation gstinLocation =  gstinDetailsService.listGstinLocationDetail(userGSTINMapping.getGstinAddressMapping().getId());
			Session session = sessionFactory.getCurrentSession();
			userGSTINMapping.setReferenceUserId(loginMaster.getuId());
			userGSTINMapping.setUpdatedBy(loginMaster.getuId().toString());
			userGSTINMapping.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			userGSTINMapping.setStatus("1");
			userGSTINMapping.setRefOrgId(loginMaster.getOrgUId());
			userGSTINMapping.setGstinAddressMapping(gstinLocation);
			userGSTINMapping.setGstinUserSet(userList);
			session.saveOrUpdate(userGSTINMapping);
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}
	
	@Override
	public String removeUserGstinMapping(UserGSTINMapping userGSTINMapping) throws Exception{
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			Object persistentInstance = session.get(UserGSTINMapping.class, userGSTINMapping.getId());
			if(persistentInstance != null){
				session.delete(persistentInstance);
				response = GSTNConstants.SUCCESS;
			}
			
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		return response;
	}
	
	@Override
	public List<UserGSTINMapping> getUserGSTINMappingListForUniqueRecords(Integer gstinId, Integer locId) throws Exception{
		List<UserGSTINMapping> gstinMappings = new ArrayList<UserGSTINMapping>();
		Session session = sessionFactory.getCurrentSession();
		
		if(locId != null){
			Query query = session.createQuery(checkGstinUserLocExist);
			query.setInteger("gstinId", gstinId);
			query.setInteger("locId", locId);
			
			@SuppressWarnings("unchecked")
			List<UserGSTINMapping> list = (List<UserGSTINMapping>)query.list();
			gstinMappings.addAll(list);
		} else {
			Query query = session.createQuery(checkGstinUserExist);
			query.setInteger("gstinId", gstinId);
			
			@SuppressWarnings("unchecked")
			List<UserGSTINMapping> list = (List<UserGSTINMapping>)query.list();
			gstinMappings.addAll(list);
		}
		
		return gstinMappings;
	}
	
	
	@Override
	public List<UserGSTINMapping> getUserGSTINMappingByOrgId(Integer refOrgId) throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(getGstnUsrByOrgId);
		query.setInteger("refOrgId", refOrgId);
		
		@SuppressWarnings("unchecked")
		List<UserGSTINMapping> gstinUserMappingList = (List<UserGSTINMapping>)query.list();
		
		logger.info("Exit");
		return gstinUserMappingList;
	}
	
	@Override
	public List<GstinLocation> getGstinLocationByGstinId(Integer gstinid){
		
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();	
		Query query = session.createQuery(getgstnlocationListByGstinId);
		query.setInteger("refGstinId", gstinid);
	
		@SuppressWarnings("unchecked")
		List<GstinLocation> gstinLocationlist = (List<GstinLocation>)query.list();
		return gstinLocationlist;
	}

}
