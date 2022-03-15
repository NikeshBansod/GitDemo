/**
 * 
 */
package com.reliance.gstn.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.dao.GSTINAddressMappingDao;
import com.reliance.gstn.model.GSTINAddressMapping;
import com.reliance.gstn.service.GSTINAddressMappingService;

/**
 * @author Pradeep.n.reddy
 *
 */
@Service
public class GSTINAddressMappingServiceImpl implements GSTINAddressMappingService {
	
	@Autowired
	private GSTINAddressMappingDao gstinAddressMappingDao;

	@Override
	@Transactional
	public GSTINAddressMapping getGstinAddressByGstinId(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return gstinAddressMappingDao.getGstinAddressByGstinId( id);
	}
}
