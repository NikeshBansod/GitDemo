/**
 * 
 */
package com.reliance.gstn.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;

import com.reliance.gstn.model.Dashboard;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.LoginAttempt;
import com.reliance.gstn.model.UserMaster;

/**
 * @author Nikesh.Bansod
 *
 */
public interface UserMasterDAO {

	String addUserMaster(UserMaster userMaster) throws Exception;

	UserMaster getUserDetails(UserMaster userMaster) throws Exception;

	UserMaster getUserMasterById(Integer id) throws Exception;

	String updateUserMaster(UserMaster userMaster) throws Exception;

	boolean validatePassword(Integer uId, String oldPassword) throws Exception;

	String changePassword(Integer uId, String newPassword);

	String addSecondaryUser(UserMaster userMaster) throws ConstraintViolationException, Exception;

	String updateSecondaryUser(UserMaster userMaster) throws Exception;

	String removeSecondaryUser(UserMaster userMaster, Map<Object, Object> mapvalues);

	List<UserMaster> getSecondaryIdsListFromPrimaryUserId(Integer uId) throws Exception;

	UserMaster getPrimaryUserById(Integer uId);
	
	String updateInvoice(Integer uId, String defaultEmailId);

	boolean checkIfUserNameExists(String userName);

	boolean checkIfpanIsRegistered(String panNo);

	boolean checkIfGstinIsRegistered(String gstin);
	
	boolean checkIfGstinRegisteredWithOrg(String gstin, Integer uid);
	

	String getRegisteredUserMobileNo(String userName) throws Exception;

	String recoverPassword(String userId,String newPassword) throws Exception;

	boolean checkIfUserMobileNoExists(String mobileNo) throws Exception;

	UserMaster getUserDetailsById(Integer id) throws Exception;

	boolean checkSecUserMapping(Integer orgId,Integer secId) throws Exception;

	List<LoginAttempt> getLoginAttemptDetails(String userId) throws Exception;

	String addLoginAttemptRecord(String userId, LoginAttempt loginAttempt,Integer noOfAttempts) throws Exception;

	String removeLoginAttemptRecord(String userId) throws Exception;

	String updateOrgLogo(String flePathWithName, Integer orgUId) throws Exception;

	String getOrgLogoPath(Integer orgUId) throws Exception;

	List<UserMaster> getUserListDetailsById(List<Integer> id) throws Exception;

	boolean updateTermsConditions(Integer orgUId) throws Exception;
	
	public boolean deleteUserAccount(Integer orgUId, String userId,String reasonOfDeletion, String userFeedback) throws Exception;

	public String countLoginAttemptRecord(String userId) throws Exception;
	
	public String countDeskLoginAttemptRecord(String userId) throws Exception;

	String addFooter(Integer orgUId, String footer,Integer uId) throws Exception;

	String setInvoiceSequenceType(String invoiceSequenceType,Integer uId) throws Exception;

	boolean getOrgIdCheckStatusWithStoreId(Integer locationId, Integer orgUId)throws Exception;

	boolean deletesecondaryupdateToPrimary(Integer oldUid, Integer newUid)
			throws Exception;

	//boolean updateSecondaryUserByPrimaryId(Integer orgUId) throws Exception;

}
