/**
 * 
 */
package com.reliance.gstn.dao;

import java.util.List;
import java.util.Map;

import com.reliance.gstn.admin.exception.DuplicateRecordExistException;
import com.reliance.gstn.model.GSTINUserMapping;
import com.reliance.gstn.model.GstinLocation;
import com.reliance.gstn.model.UserGSTINMapping;

/**
 * @author Pradeep.Gangapuram
 *
 */
public interface GSTINUserMappingDAO {

	String addGSTINUserMapping(GSTINUserMapping gstinUserMapping)throws Exception;

	List<GSTINUserMapping> getGSTINUserMappingList(String idsValuesToFetch)throws Exception;

	GSTINUserMapping getGSTINUserMappingById(Integer id);

	String updateGstinUserMapping(GSTINUserMapping gstinUserMapping)throws Exception;

	String removeGstinUserMapping(GSTINUserMapping gstinUserMapping)throws Exception;

	String getGSTINUserMappingByReferenceUserId(Integer getuId)throws Exception;
	
	boolean checkIfGSTINUserExists(Integer referenceUserId);
	
	String addUserGSTINMapping(UserGSTINMapping userGSTINMapping, Map<?, ?> mapValues) throws DuplicateRecordExistException, Exception;
	
	List<UserGSTINMapping> getUserGSTINMappingList(String idsValuesToFetch) throws Exception;
	
	UserGSTINMapping getUserGSTINMappingById(Integer id);
	
	List<UserGSTINMapping> getUserGSTINMappingByGstinId(Integer gstinid);
	
	String updateUserGstinMapping(UserGSTINMapping userGstinMapping, Map<?, ?> mapValues)throws Exception;
	
	String removeUserGstinMapping(UserGSTINMapping userGstinMapping)throws Exception;
	
	List<UserGSTINMapping> getUserGSTINMappingListForUniqueRecords(Integer gstinId, Integer locId) throws Exception;

	List<UserGSTINMapping> getUserGSTINMappingByOrgId(Integer refOrgId) throws Exception;

	List<GstinLocation> getGstinLocationByGstinId(Integer gstinid)throws Exception;
}
