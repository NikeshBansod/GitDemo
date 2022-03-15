/**
 * 
 */
package com.reliance.gstn.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.admin.exception.DuplicateRecordExistException;
import com.reliance.gstn.dao.GSTINUserMappingDAO;
import com.reliance.gstn.model.GSTINUserMapping;
import com.reliance.gstn.model.GstinLocation;
import com.reliance.gstn.model.UserGSTINMapping;
import com.reliance.gstn.service.GSTINUserMappingService;

/**
 * @author Pradeep.Gangapuram
 *
 */

@Service
public class GSTINUserMappingServiceImpl implements GSTINUserMappingService {

	@Autowired
	private GSTINUserMappingDAO gstinUserMappingDAO;

	@Override
	@Transactional
	public String addGSTINUserMapping(GSTINUserMapping gstinUserMapping) throws Exception{
		// TODO Auto-generated method stub
		return gstinUserMappingDAO.addGSTINUserMapping(gstinUserMapping);
	}

	@Override
	@Transactional
	public List<GSTINUserMapping> getGSTINUserMappingList(String idsValuesToFetch) throws Exception{
		// TODO Auto-generated method stub
		return gstinUserMappingDAO.getGSTINUserMappingList(idsValuesToFetch);
	}

	@Override
	@Transactional
	public GSTINUserMapping getGSTINUserMappingById(Integer id) {
		// TODO Auto-generated method stub
		return gstinUserMappingDAO.getGSTINUserMappingById(id);
	}

	@Override
	@Transactional
	public String updateGstinUserMapping(GSTINUserMapping gstinUserMapping) throws Exception{
		// TODO Auto-generated method stub
		return gstinUserMappingDAO.updateGstinUserMapping(gstinUserMapping);
	}

	@Override
	@Transactional
	public String removeGstinUserMapping(GSTINUserMapping gstinUserMapping) throws Exception{
		// TODO Auto-generated method stub
		return gstinUserMappingDAO.removeGstinUserMapping( gstinUserMapping);
	}

	@Override
	@Transactional
	public String getGSTINUserMappingByReferenceUserId(Integer getuId) throws Exception{
		// TODO Auto-generated method stub
		return gstinUserMappingDAO.getGSTINUserMappingByReferenceUserId(getuId);
	}
	
	@Override
	@Transactional
	public boolean checkIfGSTINUserExists(Integer referenceUserId) throws Exception{ 
		// TODO Auto-generated method stub
		return gstinUserMappingDAO.checkIfGSTINUserExists(referenceUserId);
	}
	
	@Override
	@Transactional
	public String addUserGSTINMapping(UserGSTINMapping userGSTINMapping, Map<?, ?> mapValues) throws DuplicateRecordExistException, Exception{
		// TODO Auto-generated method stub
		return gstinUserMappingDAO.addUserGSTINMapping(userGSTINMapping, mapValues);
	}
	
	@Override
	@Transactional
	public List<UserGSTINMapping> getUserGSTINMappingList(String idsValuesToFetch) throws Exception{
		// TODO Auto-generated method stub
		return gstinUserMappingDAO.getUserGSTINMappingList(idsValuesToFetch);
	}

	@Override
	@Transactional
	public UserGSTINMapping getUserGSTINMappingById(Integer id) {
		// TODO Auto-generated method stub
		return gstinUserMappingDAO.getUserGSTINMappingById(id);
	}
	
	@Override
	@Transactional
	public List<UserGSTINMapping> getUserGSTINMappingByGstinId(Integer gstinid){
		
		return gstinUserMappingDAO.getUserGSTINMappingByGstinId(gstinid);
	}

	@Override
	@Transactional
	public String updateUserGstinMapping(UserGSTINMapping userGstinMapping, Map<?, ?> mapValues)throws Exception{
		// TODO Auto-generated method stub
		return gstinUserMappingDAO.updateUserGstinMapping(userGstinMapping, mapValues);
	}
	
	@Override
	@Transactional
	public String removeUserGstinMapping(UserGSTINMapping userGstinMapping)throws Exception{
		// TODO Auto-generated method stub
		return gstinUserMappingDAO.removeUserGstinMapping(userGstinMapping);
	}
	
	@Override
	@Transactional
	public List<UserGSTINMapping> getUserGSTINMappingListForUniqueRecords(Integer gstinId, Integer locId) throws Exception{
		// TODO Auto-generated method stub
		return gstinUserMappingDAO.getUserGSTINMappingListForUniqueRecords(gstinId, locId);
	}
	
	@Override
	@Transactional
	public List<UserGSTINMapping> getUserGSTINMappingByOrgId(Integer refOrgId)  throws Exception{
		// TODO Auto-generated method stub 
		return  gstinUserMappingDAO.getUserGSTINMappingByOrgId(refOrgId);
	}
	
	@Override
	@Transactional
	public List<GstinLocation> getGstinLocationGstinId(Integer gstinid)throws Exception{
		
		return gstinUserMappingDAO.getGstinLocationByGstinId(gstinid);
	}

}
