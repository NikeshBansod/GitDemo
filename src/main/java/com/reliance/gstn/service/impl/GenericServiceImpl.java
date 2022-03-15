package com.reliance.gstn.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.dao.GenericDao;
import com.reliance.gstn.service.GenericService;
@Service
public class GenericServiceImpl implements GenericService {

	@Autowired
	private GenericDao genericDao;
	
	@Override
	@Transactional
	public boolean checkUserMappingValidation(String idsValuesToFetch,String getPrimIdsListQuery, Integer primId,String targetTable,String refColumn) throws Exception {
		// TODO Auto-generated method stub
		return genericDao.checkUserMappingValidation(idsValuesToFetch,getPrimIdsListQuery,primId,targetTable,refColumn);
	}

	@Override
	@Transactional
	public boolean validateGstin(String gstinId, Integer uId)
			throws Exception {
		// TODO Auto-generated method stub
		return genericDao.validateGstin(gstinId,uId);
	}

}
