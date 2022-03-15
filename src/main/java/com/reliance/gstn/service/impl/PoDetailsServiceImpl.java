package com.reliance.gstn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.dao.PoDetailsDao;
import com.reliance.gstn.model.PoDetails;
import com.reliance.gstn.service.PoDetailsService;

@Service
public class PoDetailsServiceImpl implements PoDetailsService {

	@Autowired
	private PoDetailsDao poDetailsDao;

	@Override
	@Transactional
	public List<PoDetails> viewPoDetailsList(Integer getuId) throws Exception{
		// TODO Auto-generated method stub
		return poDetailsDao.viewPoDetailsList(getuId);
	}

	@Override
	@Transactional
	public String addPoDetails(PoDetails poDetails, Integer getuId,Integer orgUId) {
		// TODO Auto-generated method stub
		return poDetailsDao.addPoDetails(poDetails,getuId,orgUId);
	}

	@Override
	@Transactional
	public PoDetails getPoDetailsById(Integer id) {
		// TODO Auto-generated method stub
		return poDetailsDao.getPoDetailsById(id);
	}

	@Override
	@Transactional
	public String updatePoDetails(PoDetails poDetails) {
		// TODO Auto-generated method stub
		return poDetailsDao.updatePoDetails(poDetails);
	}

	@Override
	@Transactional
	public String deletePoDetails(Integer id) {
		// TODO Auto-generated method stub
		return poDetailsDao.deletePoDetails(id);
	}

	@Override
	@Transactional
	public List<PoDetails> getPoDetailsByPoCustomerName(String poCustomerName) throws Exception{
		// TODO Auto-generated method stub
		return poDetailsDao.getPoDetailsByPoCustomerName(poCustomerName);
	}
	
	@Override
	@Transactional
	public  List<PoDetails> getPoDetailsByProduct(Integer prdId){
		// TODO Auto-generated method stub
		return poDetailsDao.getPoDetailsByProduct(prdId);
	}

	@Override
	@Transactional
	public boolean checkIfPoNoExists(String poNo, Integer orgUId) throws Exception{
		// TODO Auto-generated method stub
		return poDetailsDao.checkIfPoNoExists(poNo,orgUId);
	}
}
