/**
 * 
 */
package com.reliance.gstn.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.dao.UserMasterDAO;
import com.reliance.gstn.model.GSTINUserMapping;
import com.reliance.gstn.model.LoginAttempt;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.OrganizationMaster;
import com.reliance.gstn.model.UserGSTINMapping;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.GSTINUserMappingService;
import com.reliance.gstn.util.GSTNConstants;
//import com.reliance.gstn.util.LoggerUtil;
import com.reliance.gstn.util.GSTNUtil;

/**
 * @author Nikesh.Bansod
 *
 */

@Repository
public class UserMasterDAOImpl implements UserMasterDAO {

	private static final Logger logger = Logger.getLogger(UserMasterDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private GSTINUserMappingService gstinUserMappingService;
	
	@Value("${login_query}")
	private String loginQuery;
	
	@Value("${validate_password_query}")
	private String validatePasswordQuery;
	
	@Value("${change_password_query}")
	private String changePasswordQuery;
	
	@Value("${update_pri_user}")
	private String upatePrimaryUser;
	
	@Value("${update_sec_user}")
	private String updateSecUser;
	
	@Value("${is_user_exist}")
	private String checkIfUserExists;
	
	@Value("${is_pan_registered}")
	private String checkIfPanRegistered;
	
	@Value("${is_gstin_registered}")
	private String checkIfGstinRegistered;
	
	@Value("${is_gstin_reg_with_org}")
	private String checkIfGstinRegWithOrg;
	
	@Value("${update_email_details}")
	private String updateEmailDetails;
	
	@Value("${primary_user_by_id}")
	private String primaryUserById;
	
	@Value("${sec_user_by_id}")
	private String secendoryUserById;
	
	@Value("${user_remove_query}")
	private String updateUserDetails;
	
	@Value("${user_remove_query_secondary}")
	private String removeUserDetailsSecondary;
	
	@Value("${sec_user_remove_query}")
	private String updateSecUserDetails;
	
	@Value("${state_by_id}")
	private String stateById;
	
	@Value("${gstin_by_gstin_no}")
	private String getGstinDetailsByGstinNo;
	
	@Value("${get_user_mobile_no}")
	private String getUserMobileNo;
	
	@Value("${recover_password_query}")
	private String recoverPasswordQuery;
	
	@Value("${is_user_mobile_exist}")
	private String checkIfUserMobileNoExists;
	
	@Value("${get_unique_sequence}")
	private String getUniqueSequenceQuery;
	
	@Value("${get_user_by_id}")
	private String getUserById;
	
	@Value("${get_user_list_by_id}")
	private String getUserListById;
	
	@Value("${is_sec_user_mapping_valid}")
	private String checkSecUserMapping;
	
	@Value("${login_attempt_query}")
	private String loginAttemptQuery;
	
	@Value("${org_logo_update}")
	private String logoUpdateQuery;
	
	@Value("${get_org_logo_path}")
	private String getlogoPath;
	
	@Value("${get_mobile_login_count}")
	private String getMobLoginCounter;
	
	@Value("${get_desktop_login_count}")
	private String getDeskLoginCounter;
	
	@Value("${add_footer_query}")
	private String addFooterQuery;
	
	@Value("${get_notificationdetails_by_contact}")
	private String getNotificationDetailsByContact;
	
	@Value("${set_invoice_sequence}")
	private String setInvoiceSequence;
	
	@Value("${check_storeid_with_orgid}")
	private String checkstoreidwithorgid;
	
	@Override
	public String  addUserMaster(UserMaster userMaster) throws Exception {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		String stateName ="";
		try {
			Session session = sessionFactory.getCurrentSession();
			userMaster.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			userMaster.setUniqueSequence("1");
			userMaster.setInvoiceSequenceType("Auto");
			session.persist(userMaster);
			userMaster=(UserMaster)session.get(UserMaster.class, userMaster.getId());
			
		
			OrganizationMaster orgMaster = userMaster.getOrganizationMaster();
			stateName=orgMaster.getState();
			Integer id=getStateId(session,stateName);
			orgMaster.setState(String.valueOf(id));
			orgMaster.setCreatedBy(userMaster.getId().toString());
			orgMaster.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			orgMaster.setUserMaster(userMaster);
			session.persist(orgMaster);
					
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
			userMaster.getOrganizationMaster().setState(stateName);
			throw e;
		}
		
		logger.info("Exit");
		return response;
	}
	
	private Integer getStateId(Session session,String stateName){
		Integer i=0;
		
		Query query = session.createQuery(stateById);
		query.setString("stateName", stateName);
		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>)query.list();
		if(null!=list && !list.isEmpty()){
			i=(Integer)list.get(0);
		}
		return i;
	}

	@Override
	public UserMaster getUserDetails(UserMaster userMaster) throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		UserMaster user = null;
		Query query = session.createQuery(loginQuery);
		query.setString("userId", userMaster.getUserId());
		query.setString("password", userMaster.getPassword());
		
		@SuppressWarnings("unchecked")
		List<UserMaster> list = (List<UserMaster>)query.list();
		
		if (!list.isEmpty()) {
			user = list.get(0);
			if(user.getUserRole().equals(GSTNConstants.SECONDARY_USER)){
				UserMaster secondaryUser = getUserMasterById(user.getReferenceId());
				user.setOrganizationMaster(secondaryUser.getOrganizationMaster());
			}
		}
		logger.info("Exit");
		return user;
	}



	@Override
	public UserMaster getUserMasterById(Integer id) throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();		
		UserMaster userMaster = (UserMaster) session.get(UserMaster.class, id);
		logger.info("Exit");
		return userMaster;
	}



	@Override
	public String updateUserMaster(UserMaster userMaster) throws Exception{
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			
			//save Organization
			
			OrganizationMaster orgMaster = userMaster.getOrganizationMaster();	
			orgMaster.setUpdatedBy(userMaster.getId().toString());
			orgMaster.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			orgMaster.setUserMaster(userMaster);
			session.saveOrUpdate(orgMaster);
			
			
			//update UserMaster
			
			Query query = session.createQuery(upatePrimaryUser);
			query.setString("secUserAadhaarNo",userMaster.getSecUserAadhaarNo());
			query.setString("userName",userMaster.getUserName());
			query.setString("emailId",userMaster.getEmailId());
			query.setString("defaultEmailId",userMaster.getDefaultEmailId());
			//query.setString("contactNo",userMaster.getContactNo());
			query.setInteger("id",userMaster.getId());
			query.setParameter("updatedOn", new java.sql.Timestamp(new Date().getTime()));
			query.setString("updatedBy",userMaster.getId().toString());
			query.executeUpdate();
			
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return response;
	}



	@Override
	public boolean validatePassword(Integer uId, String oldPassword) throws Exception{
		logger.info("Entry");
		boolean validatePassword = false;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createSQLQuery(validatePasswordQuery);
			query.setInteger("uId", uId);
			query.setString("password", oldPassword);
			
			
			@SuppressWarnings("unchecked")
			List<UserMaster> list = (List<UserMaster>)query.list();
			
			if (!list.isEmpty()) {
				validatePassword = true;
			}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return validatePassword;
	}



	@Override
	public String changePassword(Integer uId, String newPassword) {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		logger.info("Entry");
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(changePasswordQuery);
			query.setString("newPassword",newPassword);
			query.setInteger("id",uId);
			query.setParameter("updatedOn", new java.sql.Timestamp(new Date().getTime()));
			query.setString("updatedBy",uId.toString());
			query.executeUpdate();
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return response;
	}



	@Override
	public String addSecondaryUser(UserMaster userMaster) throws ConstraintViolationException, Exception{
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			userMaster.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			userMaster.setUniqueSequence(GSTNUtil.getUniqueSequence(session,getUniqueSequenceQuery,"gst_user_master", "REFERENCE_ID", userMaster.getReferenceId()));
			
			session.persist(userMaster);
			response = GSTNConstants.SUCCESS;
		}  catch(ConstraintViolationException  e){
			logger.error("Error in:",e);
			throw e;
		}  catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}


	@Override
	public String updateSecondaryUser(UserMaster userMaster) throws Exception{
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
	
			//save UserMaster
			Query query = session.createQuery(updateSecUser);
			query.setString("userName",userMaster.getUserName());
			query.setString("emailId",userMaster.getEmailId());
		//	query.setString("contactNo",userMaster.getContactNo());
			query.setString("secUserAadhaarNo",userMaster.getSecUserAadhaarNo());
			query.setInteger("id",userMaster.getId());
			query.setParameter("updatedOn", new java.sql.Timestamp(new Date().getTime()));
			query.setString("updatedBy",userMaster.getUpdatedBy());
			query.executeUpdate();
			
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return response;
	}



	@Override
	public String removeSecondaryUser(UserMaster userMaster, Map<Object, Object> mapvalues) {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		LoginMaster loginMaster = (LoginMaster)mapvalues.get("loginMaster");
		try {
			Session session = sessionFactory.getCurrentSession();
			
			//save UserMaster
		//	Query query = session.createQuery("update UserMaster set status = :status, updatedOn = :updatedOn, updatedBy = :updatedBy where id = :id");
			/*Query secUserQuery = session.createQuery(updateSecUserDetails);
			secUserQuery.setInteger("referenceUserId",userMaster.getId());
			@SuppressWarnings("unchecked")
			List<GSTINUserMapping> list = (List<GSTINUserMapping>)secUserQuery.list();
			if ((!list.isEmpty())) {
				for(GSTINUserMapping gstnUserMapping:list){
					Object persistentInstance = session.load(GSTINUserMapping.class, gstnUserMapping.getId());
					if(persistentInstance != null){
						session.delete(persistentInstance);
						response = GSTNConstants.SUCCESS;
					}
				}
				
			}*/
			
			List<UserGSTINMapping> gstinMappingList = gstinUserMappingService.getUserGSTINMappingByOrgId(loginMaster.getOrgUId());
			
			if(!gstinMappingList.isEmpty()){
				
				for(UserGSTINMapping gstinMapping : gstinMappingList){
					Object persistentInstance = session.load(UserGSTINMapping.class, gstinMapping.getId());
					if(persistentInstance != null){
						session.delete(persistentInstance);
						response = GSTNConstants.SUCCESS;
					}
					/*
					 * List<UserMaster> userMasterList = gstinMapping.getGstinUserSet();
					 * for(UserMaster master : userMasterList){ if(master.getId().intValue() ==
					 * userMaster.getId().intValue()){ response = GSTNConstants.ALREADYMAPPED; throw
					 * new Exception(); } }
					 */
				}
				
			}
			
			deletesecondaryupdateToPrimary(userMaster.getId(),loginMaster.getuId());
			
			/*
			 * Query query1 = session.createSQLQuery(
			 * "CALL deletesecondaryupdateToPrimary(:old_user_id,:new_user_id)")
			 * .setParameter("new_user_id", loginMaster.getuId())
			 * .setParameter("old_user_id", userMaster.getId());
			 * 
			 * query1.executeUpdate();
			 */
			
			/*Query query = session.createQuery(updateUserDetails);
			query.setInteger("id",userMaster.getId());
			query.setString("status",userMaster.getStatus());
			query.setParameter("updatedOn", new java.sql.Timestamp(new Date().getTime()));
			query.setString("updatedBy",userMaster.getUpdatedBy());
			query.executeUpdate();*/
			Query query = session.createQuery(removeUserDetailsSecondary);
			query.setInteger("id",userMaster.getId());
			query.executeUpdate();
			
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return response;
	}



	@Override
	public List<UserMaster> getSecondaryIdsListFromPrimaryUserId(Integer uId) throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(secendoryUserById);
		query.setInteger("uId", uId);
		
		@SuppressWarnings("unchecked")
		List<UserMaster> list = (List<UserMaster>)query.list();
		
		List<UserMaster> secUserList = getSecondaryUserList(list);
		logger.info("Exit");
		return secUserList;
	}



	private List<UserMaster> getSecondaryUserList(List<UserMaster> list) {
		List<UserMaster> SecUserList = new ArrayList<UserMaster>();
		
		
		for(UserMaster userMaster : list){
			UserMaster secUser = new UserMaster();
			secUser.setId(userMaster.getId());
			secUser.setContactNo(userMaster.getContactNo());
			secUser.setUserName(userMaster.getUserName());
			secUser.setEmailId(userMaster.getEmailId());
			SecUserList.add(secUser);
		}
		
		return SecUserList;
	}

	@Override
	public UserMaster getPrimaryUserById(Integer secondaryUserUId) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(primaryUserById);
		query.setInteger("secondaryUserUId", secondaryUserUId);
		UserMaster user = null;
		@SuppressWarnings("unchecked")
		List<UserMaster> list = (List<UserMaster>)query.list();
		
		if ((!list.isEmpty())) {
			user = list.get(0);
		}
		logger.info("Exit");
		return user;
	}

	@Override
	public boolean checkIfUserNameExists(String userName) {
		logger.info("Entry");
		boolean userNameExists = false;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(checkIfUserExists);
		query.setString("userId", userName.toLowerCase());
		
		@SuppressWarnings("unchecked")
		List<UserMaster> list = (List<UserMaster>)query.list();
		
		if (!list.isEmpty()) {
			userNameExists = true;
		}
		logger.info("Exit");
		
		return userNameExists;
	}
	
	@Override
	public String updateInvoice(Integer id, String defaultEmailId) {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
	
			//update Invoice 
			Query query = session.createQuery(updateEmailDetails);
		//	Query query = session.createQuery("update UserMaster set defaultEmailId = :defaultEmailId, secUserAadhaarNo=:secUserAadhaarNo  where id = :id");//updateSecUser
			query.setString("defaultEmailId",defaultEmailId);//shubham.upadhyay@ril.com
			query.setInteger("id",id);
			
			query.executeUpdate();
			
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return response;
	}

	@Override
	public boolean checkIfpanIsRegistered(String panNo) {
		logger.info("Entry");
		boolean panNoRegistered = false;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(checkIfPanRegistered);
		query.setString("panNumber", panNo.toLowerCase());
		
		@SuppressWarnings("unchecked")
		List<UserMaster> list = (List<UserMaster>)query.list();
		
		if (!list.isEmpty()) {
			panNoRegistered = true;
		}
		logger.info("Exit");
		
		return panNoRegistered;
	}

	@Override
	public boolean checkIfGstinIsRegistered(String gstin) {
		logger.info("Entry");
		boolean gstinRegistered = false;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(checkIfGstinRegistered);
		query.setString("gstinNumber", gstin.toLowerCase());
		
		@SuppressWarnings("unchecked")
		List<UserMaster> list = (List<UserMaster>)query.list();
		
		if (!list.isEmpty()) {
			gstinRegistered = true;
		}
		logger.info("Exit");
		
		return gstinRegistered;

	}
	
	@Override
	public boolean checkIfGstinRegisteredWithOrg(String gstin, Integer uid){
	
		logger.info("Entry");
		boolean gstinRegistered = false;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(checkIfGstinRegWithOrg);
		query.setString("gstinNumber", gstin.toLowerCase());
		query.setInteger("uid",uid);
		
		@SuppressWarnings("unchecked")
		List<UserMaster> list = (List<UserMaster>)query.list();
		
		if (!list.isEmpty()) {
			gstinRegistered = true;
		}
		logger.info("Exit");
		
		return gstinRegistered;
	}

	@Override
	public String getRegisteredUserMobileNo(String userName) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(getUserMobileNo);
		query.setString("userId", userName.toLowerCase());
		String mobileNo=null;
		
		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>)query.list();
		
		if (!list.isEmpty()) {
			mobileNo=(String)list.get(0);
		}
		logger.info("Exit");
		
		return mobileNo;
	}

	@Override
	public String recoverPassword(String userId,String newPassword) throws Exception {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		logger.info("Entry");
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(recoverPasswordQuery);
			query.setString("newPassword",newPassword);
			query.setString("userId",userId);
			query.setParameter("updatedOn", new java.sql.Timestamp(new Date().getTime()));
			query.executeUpdate();
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public boolean checkIfUserMobileNoExists(String mobileNo) throws Exception {
		logger.info("Entry");
		boolean userMobileNoExists = false;
		try {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(checkIfUserMobileNoExists);
		query.setString("contactNo", mobileNo);
		
		@SuppressWarnings("unchecked")
		List<UserMaster> list = (List<UserMaster>)query.list();
		
		if (!list.isEmpty()) {
			userMobileNoExists = true;
		}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		
		return userMobileNoExists;
	}

	@Override
	public UserMaster getUserDetailsById(Integer id) throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		UserMaster user = null;
		Query query = session.createQuery(getUserById);
		query.setInteger("id",id);
		
		@SuppressWarnings("unchecked")
		List<UserMaster> list = (List<UserMaster>)query.list();
		try {
			if (!list.isEmpty()) {
				user = list.get(0);
				if(user.getUserRole().equals(GSTNConstants.SECONDARY_USER)){
					UserMaster secondaryUser = getUserMasterById(user.getReferenceId());
					user.setOrganizationMaster(secondaryUser.getOrganizationMaster());
				}
			}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return user;
	}

	@Override
	public List<UserMaster> getUserListDetailsById(List<Integer> id) throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		List<UserMaster> userList = new ArrayList<UserMaster>();
		try {
			String ids = "";
			for(Integer i : id){
				ids= ids+i+",";
			}
			ids = ids.substring(0, ids.length()-1);
			List<Integer> ali = GSTNUtil.getListFromString(ids);
			String placeHoder = GSTNUtil.getPlaceHoderString(ali);
			String queryString =  getUserListById;
			queryString=queryString.replace("idValues", placeHoder);
			Query query = session.createQuery(queryString);
			
			for (int i = 0; i < ali.size(); i++) {
				query.setInteger(i, ali.get(i));
			}
		
		@SuppressWarnings("unchecked")
		List<UserMaster> list = (List<UserMaster>)query.list();
		userList.addAll(list);
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return userList;
	}
	
	@Override
	public boolean checkSecUserMapping(Integer orgId,Integer secId) throws Exception {
		logger.info("Entry");
		boolean isMappingValid = false;
		try {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(checkSecUserMapping);
		query.setInteger("refOrgId", orgId);
		query.setInteger("secId", secId);
		
		@SuppressWarnings("unchecked")
		List<UserMaster> list = (List<UserMaster>)query.list();
		
		if (!list.isEmpty()) {
			isMappingValid = true;
		}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		
		return isMappingValid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoginAttempt> getLoginAttemptDetails(String userId)	throws Exception {
		logger.info("Entry");
		List<LoginAttempt> loginAttemptList = new ArrayList<LoginAttempt>();
		try {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(loginAttemptQuery); 
		query.setString("userId", userId);
		
		loginAttemptList =(List<LoginAttempt>)query.list();
		
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return loginAttemptList;
	}

	@Override
	public String addLoginAttemptRecord(String userId,
			LoginAttempt loginAttempt, Integer noOfAttempts) throws Exception {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
	
			loginAttempt.setUserId(userId);
			loginAttempt.setNoOfAttempts(noOfAttempts);
			loginAttempt.setLoginAttemptTime(new java.sql.Timestamp(new Date().getTime()));
			session.saveOrUpdate(loginAttempt);
			
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return response;
	}

	@Override
	public String removeLoginAttemptRecord(String userId) throws Exception {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
	
			Query query = session.createQuery(loginAttemptQuery); 
			query.setString("userId", userId);
			@SuppressWarnings("unchecked")
			List<LoginAttempt> loginAttemptList =(List<LoginAttempt>)query.list();
			if(!loginAttemptList.isEmpty()){
				for (LoginAttempt loginAttemptObj : loginAttemptList) {
					session.delete(loginAttemptObj);
				}
			}
			
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return response;

		
	}

	@Override
	public String updateOrgLogo(String flePathWithName, Integer orgUId)
			throws Exception {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
	
			Query query = session.createQuery(logoUpdateQuery);
			query.setInteger("orgUId",orgUId);
			query.setString("flePathWithName", flePathWithName);
			query.executeUpdate();
			
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return response;

	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public String getOrgLogoPath(Integer orgUId)
			throws Exception {
		logger.info("Entry");
		String logoImagePath="";
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
	
			Query query = session.createQuery(getlogoPath);
			query.setInteger("orgUId",orgUId);
			
			List<OrganizationMaster> orgList = (List<OrganizationMaster>)query.list();
			logoImagePath = orgList.get(0).getLogoImagePath();
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return logoImagePath;
	}

	@Override
	public boolean updateTermsConditions(Integer orgUId) throws Exception {
		logger.info("Entry");
		boolean updateTermsConditions=false;
		try {
			Session session = sessionFactory.getCurrentSession();
			OrganizationMaster organizationMaster=(OrganizationMaster) session.get( OrganizationMaster.class,orgUId);
			organizationMaster.setTermsConditionsFlagHidden("Y");
			session.saveOrUpdate(organizationMaster);
			updateTermsConditions =true;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return updateTermsConditions;
	}
		

	@Override
	public boolean deleteUserAccount(Integer orgUId, String userId,
			String reasonOfDeletion, String userFeedback) throws Exception {
		logger.info("Entry");
		boolean isAccountDeleted=false;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createSQLQuery(
					"CALL DeleteUserAccount(:delete_user_org_id,:delete_user_id,:delete_user_reason,:delete_user_feedback)")
					.setParameter("delete_user_org_id", orgUId)
					.setParameter("delete_user_id", userId)
					.setParameter("delete_user_reason", reasonOfDeletion)
					.setParameter("delete_user_feedback", userFeedback);
			query.executeUpdate();
			isAccountDeleted = true;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		
		logger.info("Exit");
		return isAccountDeleted;
	}
	
	@Override
	public String countLoginAttemptRecord(String userId) throws Exception {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
	
			Query query = session.createQuery(getMobLoginCounter); 
			query.setString("userId", userId);
			query.setParameter("lastLogin", new java.sql.Timestamp(new Date().getTime()));
			query.executeUpdate();
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return response;
		
	}

	@Override
	public String countDeskLoginAttemptRecord(String userId) throws Exception {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
	
			Query query = session.createQuery(getDeskLoginCounter); 
			query.setString("userId", userId);
			query.setParameter("lastLogin", new java.sql.Timestamp(new Date().getTime()));
			query.executeUpdate();
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return response;
		
	}

	@Override
	public String addFooter(Integer orgUId, String footer, Integer uId) throws Exception {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		logger.info("Entry");
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(addFooterQuery);
			query.setString("footer",footer);
			query.setInteger("id",orgUId);
			query.setParameter("updatedOn", new java.sql.Timestamp(new Date().getTime()));
			query.setString("updatedBy",uId.toString());
			query.executeUpdate();
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public String setInvoiceSequenceType(String invoiceSequenceType,Integer uId) throws Exception {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(setInvoiceSequence); 
			query.setInteger("id", uId);
			query.setString("invoiceSequenceType", invoiceSequenceType);
			query.executeUpdate();
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		return response;
	}
	@Override
	public boolean getOrgIdCheckStatusWithStoreId(Integer locationId,Integer orgUId) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Entry");
		boolean response = false;
		logger.info("Entry");
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createSQLQuery(checkstoreidwithorgid);
			query.setInteger("locationId",locationId);
			
			Integer refOrgId = (Integer) query.uniqueResult();
	
			if(refOrgId.equals(orgUId )){
				response = true;
			}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return response;
	}
	
	
	@Override
	@Transactional
	public boolean deletesecondaryupdateToPrimary(Integer oldUid, Integer newUid) throws Exception {
		logger.info("Entry");
		boolean isAccountDeleted=false;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createSQLQuery(
					"CALL deletesecondaryupdateToPrimary(:old_user_id,:new_user_id)")
					.setParameter("new_user_id", newUid)
					.setParameter("old_user_id", oldUid);
					
			query.executeUpdate();
			isAccountDeleted = true;
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		
		
		logger.info("Exit");
		return isAccountDeleted;
	}

}
