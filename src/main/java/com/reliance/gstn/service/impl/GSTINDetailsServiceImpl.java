/**
 * 
 */
package com.reliance.gstn.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.dao.GSTINDetailsDAO;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.GstinLocation;
import com.reliance.gstn.service.GSTINDetailsService;

/**
 * @author Pradeep.Gangapuram
 *
 */

@Service
public class GSTINDetailsServiceImpl implements GSTINDetailsService {
	 
	@Autowired
	private GSTINDetailsDAO gstinDetailsDAO;

	private static final Logger logger = Logger.getLogger(GSTINDetailsServiceImpl.class);
    
	@Override
	@Transactional
	public String addGstinDetails(GSTINDetails gstinDetails) throws Exception{
		// TODO Auto-generated method stub
		return gstinDetailsDAO.addGstinDetails(gstinDetails);
	}
	
	@Override
	@Transactional
	public GSTINDetails getGstinDetailsById(Integer id) throws Exception{
		// TODO Auto-generated method stub
		return gstinDetailsDAO.getGstinDetailsById(id);
	}
	
	@Override
	@Transactional
	public List<GSTINDetails> listGstinDetails(Integer uId) throws Exception{
		return gstinDetailsDAO.listGstinDetails(uId);
	}

	@Override
	@Transactional
	public String updateGstinDetails(GSTINDetails gstinDetails) {
		// TODO Auto-generated method stub
		return gstinDetailsDAO.updateGstinDetails( gstinDetails);
	}

	@Override
	@Transactional
	public String removeGSTINDetails(GSTINDetails gstinDetails) throws Exception {
		// TODO Auto-generated method stub
		return gstinDetailsDAO.removeGSTINDetails(gstinDetails);
	}

	@Override
	@Transactional
	public List<GSTINDetails> getGstinDetailsByUniqueIds(String gstinUserMapped) throws Exception{
		// TODO Auto-generated method stub
		return gstinDetailsDAO.getGstinDetailsByReferenceId(gstinUserMapped);
	}

	@Override
	@Transactional
	public GSTINDetails getGstinDetailsFromGstinNo(String gstinNo,Integer primaryUserUid) throws Exception {
		// TODO Auto-generated method stub
		return gstinDetailsDAO.getGstinDetailsFromGstinNo(gstinNo,primaryUserUid);
	}
	
	@Override
	@Transactional
	public boolean checkIfGstinIsValidWithRegisteredPAN(Integer orgId,String panNo) throws Exception{
		// TODO Auto-generated method stub
		return gstinDetailsDAO.checkIfGstinIsValidWithRegisteredPAN(orgId,panNo);
	}

	@Override
	@Transactional
	public String removeGstinLocationById(Integer locId) throws Exception {
		// TODO Auto-generated method stub
		return gstinDetailsDAO.removeGstinLocationById(locId);
	}
	
	@Override
	@Transactional
	public List<GstinLocation> listGstinLocationDetails(Integer uId) throws Exception{
		return gstinDetailsDAO.listGstinLocationDetails(uId);
	}
	
	@Override
	@Transactional
	public GstinLocation listGstinLocationDetail(Integer uId) throws Exception{
		return gstinDetailsDAO.listGstinLocationDetail(uId);
	}

	@Override
	@Transactional
	public List<GSTINDetails> getGstinDetailsMappedForSecondaryUser(Integer uId) throws Exception{
		// TODO Auto-generated method stub
		return gstinDetailsDAO.getGstinDetailsMappedForSecondaryUser(uId) ;
	}

	@Override
	@Transactional
	public List<GstinLocation> getSecondaryMappedLocations(Integer gstinId, Integer getuId) throws Exception{
		// TODO Auto-generated method stub
		return gstinDetailsDAO.getSecondaryMappedLocations(gstinId,getuId);
	}
	
	@Override
	@Transactional
	public String updateUserGstinDetails(Map<String, Object> mapValues) throws Exception{
		return gstinDetailsDAO.updateUserGstinDetails(mapValues);
	}
	
	@Override
	@Transactional
	public GSTINDetails getGstinDetailsByIdEdit(Integer id) throws Exception{
		return gstinDetailsDAO.getGstinDetailsByIdEdit(id);
	}

	@Override
	@Transactional
	public List<GSTINDetails> listGstinDetailsAndCity(Integer getuId) throws Exception {
		// TODO Auto-generated method stub
		return gstinDetailsDAO.listGstinDetailsAndCity(getuId);
	}
}
