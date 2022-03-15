/**
 * 
 */
package com.reliance.gstn.service;

import java.util.List;
import java.util.Map;

import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.GstinLocation;

/**
 * @author Pradeep.Gangapuram
 *
 */
public interface GSTINDetailsService {

	String addGstinDetails(GSTINDetails gstinDetails) throws Exception;
	
	GSTINDetails getGstinDetailsById(Integer id) throws Exception;
	
	List<GSTINDetails> listGstinDetails(Integer uId)throws Exception;

	String updateGstinDetails(GSTINDetails gstinDetails);

	String removeGSTINDetails(GSTINDetails gstinDetails) throws Exception;

	List<GSTINDetails> getGstinDetailsByUniqueIds(String gstinUserMapped)throws Exception;

	GSTINDetails getGstinDetailsFromGstinNo(String gstinNo, Integer primaryUserUid) throws Exception;
	
	boolean checkIfGstinIsValidWithRegisteredPAN(Integer orgId, String panNo)throws Exception;

	String removeGstinLocationById(Integer locId)throws Exception;
	
	List<GstinLocation> listGstinLocationDetails(Integer uId)throws Exception;
	
	GstinLocation listGstinLocationDetail(Integer uId) throws Exception;

	List<GSTINDetails> getGstinDetailsMappedForSecondaryUser(Integer getuId) throws Exception;

	List<GstinLocation> getSecondaryMappedLocations(Integer id, Integer getuId) throws Exception;
	
	String updateUserGstinDetails(Map<String, Object> mapValues) throws Exception;

	GSTINDetails getGstinDetailsByIdEdit(Integer id) throws Exception;

	List<GSTINDetails> listGstinDetailsAndCity(Integer getuId) throws Exception;

	
}
