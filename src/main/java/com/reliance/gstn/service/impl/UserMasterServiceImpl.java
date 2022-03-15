/**
 * 
 */
package com.reliance.gstn.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.dao.UserMasterDAO;
import com.reliance.gstn.model.LoginAttempt;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;

/**
 * @author Nikesh.Bansod
 *
 */

@Service
public class UserMasterServiceImpl implements UserMasterService {

	@Autowired
	private UserMasterDAO userMasterDAO;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public String addUserMaster(UserMaster userMaster) throws Exception{
	
		return userMasterDAO.addUserMaster(userMaster);
	}

	@Override
	@Transactional
	public UserMaster getUserDetails(UserMaster userMaster) throws Exception{
		
		return userMasterDAO.getUserDetails(userMaster);
	}

	@Override
	@Transactional
	public UserMaster getUserMasterById(Integer id) throws Exception{
	
		return userMasterDAO.getUserMasterById(id);
	}

	@Override
	@Transactional
	public String updateUserMaster(UserMaster userMaster) throws Exception{
	
		return userMasterDAO.updateUserMaster(userMaster);
	}

	@Override
	@Transactional
	public boolean validatePassword(Integer uId, String oldPassword) throws Exception{
	
		return userMasterDAO.validatePassword( uId, oldPassword);
	}

	@Override
	@Transactional
	public String changePassword(Integer uId, String newPassword) {
	
		return userMasterDAO.changePassword(uId, newPassword);
	}

	@Override
	@Transactional
	public String addSecondaryUser(UserMaster userMaster) throws ConstraintViolationException, Exception{
	
		String result="";
		synchronized (this) {
			result = userMasterDAO.addSecondaryUser(userMaster);
		}
		return result;
	}

	@Override
	@Transactional
	public String updateSecondaryUser(UserMaster userMaster) throws Exception{
	
		return userMasterDAO.updateSecondaryUser(userMaster);
	}

	@Override
	@Transactional
	public String removeSecondaryUser(UserMaster userMaster, Map<Object, Object> mapValues) {
	
		return userMasterDAO.removeSecondaryUser( userMaster, mapValues);
	}

	@Override
	@Transactional
	public String getDependentIds(Integer uId,String userRole) throws Exception{
		
		String idsInString = null;
		if(userRole.equals(GSTNConstants.PRIMARY_USER)){
			//fetchOnlySecondaryIds
			List<UserMaster> secondaryList = userMasterDAO.getSecondaryIdsListFromPrimaryUserId(uId);
			if(secondaryList.size() > 0){
				idsInString = ""+uId+","+GSTNUtil.getSecondaryUsersIdsInString(secondaryList);
			}else{
				idsInString = ""+uId+"";
			}
			
		}else{
			//fetchPrimaryId and then child ids 
			UserMaster user = userMasterDAO.getPrimaryUserById(uId);
			List<UserMaster> secondaryList = userMasterDAO.getSecondaryIdsListFromPrimaryUserId(user.getReferenceId());
			if(secondaryList.size() > 0){
				idsInString = ""+user.getReferenceId()+","+GSTNUtil.getSecondaryUsersIdsInString(secondaryList);
			}
			 
			
		}
			
		
		return idsInString;
	}

	@Override
	@Transactional
	public List<UserMaster> getSecondaryUsersList(Integer uId) throws Exception{
	
		return userMasterDAO.getSecondaryIdsListFromPrimaryUserId(uId);
	}

	@Override
	@Transactional
	public String updateInvoice(Integer uId,String defaultEmailId) {
	
		return userMasterDAO.updateInvoice(uId, defaultEmailId);
	}
	@Override
	@Transactional
	public boolean checkIfUserNameExists(String userName) {
	
		return userMasterDAO.checkIfUserNameExists(userName);
	}

	@Override
	@Transactional
	public boolean checkIfpanIsRegistered(String panNo) {
	
		return userMasterDAO.checkIfpanIsRegistered(panNo);
	}

	@Override
	@Transactional
	public boolean checkIfGstinIsRegistered(String gstin) {
	
		return userMasterDAO.checkIfGstinIsRegistered(gstin);
	}

	@Override
	@Transactional
	public boolean checkIfGstinRegisteredWithOrg(String gstin, Integer uid){
		return userMasterDAO.checkIfGstinRegisteredWithOrg(gstin, uid);
	}
	
	@Override
	@Transactional
	public String getRegisteredUserMobileNo(String userName) throws Exception {
	
		return  userMasterDAO.getRegisteredUserMobileNo(userName);
	}

	@Override
	@Transactional
	public String recoverPassword(String userId,String newPassword) throws Exception {
	
		return userMasterDAO.recoverPassword(userId,newPassword);
	}

	@Override
	@Transactional
	public boolean checkIfUserMobileNoExists(String mobileNo) throws Exception {
	
		return userMasterDAO.checkIfUserMobileNoExists(mobileNo);
	}

	@Override
	@Transactional
	public UserMaster getUserDetailsById(Integer id) throws Exception{
	
		return userMasterDAO.getUserDetailsById(id);
	}

	@Override
	@Transactional
	public boolean checkSecUserMapping(Integer orgId,Integer secId) throws Exception {
	
		return userMasterDAO.checkSecUserMapping(orgId,secId);
	}

	@Override
	@Transactional
	public List<LoginAttempt> getLoginAttemptDetails(String userId) throws Exception {
	
		return userMasterDAO.getLoginAttemptDetails(userId);
	}

	@Override
	@Transactional
	public String addLoginAttemptRecord(String userId,LoginAttempt loginAttempt, Integer noOfAttempts) throws Exception {
	
		return userMasterDAO.addLoginAttemptRecord(userId,loginAttempt,noOfAttempts);
	}

	@Override
	@Transactional
	public String removeLoginAttemptRecord(String userId) throws Exception {
	
		return userMasterDAO.removeLoginAttemptRecord(userId);
	}
	
	@Override
	@Transactional
	public String updateOrgLogo(String flePathWithName, Integer orgUId)
			throws Exception {
	
		return userMasterDAO.updateOrgLogo(flePathWithName,orgUId);
	}

	@Override
	@Transactional
	public String getOrgLogoPath(Integer orgUId)
			throws Exception {
	
		return userMasterDAO.getOrgLogoPath(orgUId);
	}
	
	
	@Override
	@Transactional
	public List<UserMaster> getUserListDetailsById(List<Integer> id) throws Exception{
	
		return userMasterDAO.getUserListDetailsById(id);
	}

	@Override
	@Transactional
	public boolean updateTermsConditions(Integer orgUId) throws Exception {
	
		return userMasterDAO.updateTermsConditions(orgUId);
	}

	@Override
	@Transactional
	public boolean deleteUserAccount(Integer orgUId, String userId,
			String reasonOfDeletion, String userFeedback) throws Exception {
	
		return userMasterDAO.deleteUserAccount(orgUId,userId,reasonOfDeletion,userFeedback);
	}
	
	@Override
	@Transactional
	public String countLoginAttemptRecord(String userId) throws Exception {
	
		return userMasterDAO.countLoginAttemptRecord(userId);
	}

	@Override
	@Transactional
	public String countDeskLoginAttemptRecord(String userId) throws Exception {
	
		return userMasterDAO.countDeskLoginAttemptRecord(userId);
	}

	@Override
	@Transactional
	public String addFooter(Integer orgUId, String footer,Integer uId) throws Exception {
		
		return userMasterDAO.addFooter(orgUId, footer,uId);
	}

	@Override
	@Transactional
	public String setInvoiceSequenceType(String invoiceSequenceType,Integer uId) throws Exception {
		
		return userMasterDAO.setInvoiceSequenceType(invoiceSequenceType,uId);
	}
	@Override
	@Transactional
	public boolean getOrgIdCheckStatusWithStoreId(Integer locationId,
			Integer orgUId) throws Exception {
		// TODO Auto-generated method stub
		return userMasterDAO.getOrgIdCheckStatusWithStoreId(locationId,orgUId);
	}

}
