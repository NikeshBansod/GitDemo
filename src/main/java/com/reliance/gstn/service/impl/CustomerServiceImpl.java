package com.reliance.gstn.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.dao.CustomerDao;
import com.reliance.gstn.model.CustomerDetails;
import com.reliance.gstn.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;

	@Override
	@Transactional
	public Map<String,Object> addCustomerDetails(CustomerDetails customerDetails, Map<?, ?> mapValues) throws ConstraintViolationException, Exception {
		// TODO Auto-generated method stub
		Map<String,Object> customer;
		synchronized (this) {
			customer = customerDao.addCustomerDetails(customerDetails,mapValues);
		}
		return customer;
	}

	@Override
	@Transactional
	public List<CustomerDetails> viewCustomerDetailsList(Integer getuId) throws Exception{
		// TODO Auto-generated method stub
		return customerDao.viewCustomerDetailsList(getuId);
	}

	@Override
	@Transactional
	public String deleteCustomerDetails(Integer id) throws ConstraintViolationException, Exception {
		// TODO Auto-generated method stub
		return customerDao.deleteCustomerDetails(id);
	}

	@Override
	@Transactional
	public String updateCustomerDetails(CustomerDetails customerDetails, Map<?, ?> mapValues)
			throws DataIntegrityViolationException, Exception {
		// TODO Auto-generated method stub
		return customerDao.updateCustomerDetails(customerDetails,mapValues);
	}

	@Override
	@Transactional
	public CustomerDetails getCustomerDetailsById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return customerDao.getCustomerDetailsById(id);
	}

	@Override
	@Transactional
	public List<CustomerDetails> getCustomersList(Integer refOrgId) throws Exception{
		// TODO Auto-generated method stub
		return customerDao.getCustomersList(refOrgId);
	}

	@Override
	@Transactional
	public List<String> getCustomersListByCustName(String idsValuesToFetch) throws Exception{
		// TODO Auto-generated method stub
		return customerDao.getCustomersListByCustName(idsValuesToFetch);
	}

	@Override
	@Transactional
	public CustomerDetails getCustomerDetailsByCustName(String custName,String idsValuesToFetch) throws Exception{
		// TODO Auto-generated method stub
		return customerDao.getCustomerDetailsByCustName(custName, idsValuesToFetch);
	}

	@Override
	@Transactional
	public boolean checkIfCustomerExists(String customer, String custName, Integer orgUId) throws Exception{
		// TODO Auto-generated method stub
		return customerDao.checkIfCustomerExists(customer,custName,orgUId);
	}

	@Override
	@Transactional
	public CustomerDetails checkIfCustomerContactNoExists(String contactNo, Integer orgUId) {
		// TODO Auto-generated method stub
		return customerDao.checkIfCustomerContactNoExists(contactNo,orgUId);
	}

	@Override
	@Transactional
	public List<Object[]> getCustomersListByCustNameAndMobileNo(Integer orgUid, String query, String documentType) {
		// TODO Auto-generated method stub
		return customerDao.getCustomersListByCustNameAndMobileNo(orgUid, query, documentType);
	}

	@Override
	@Transactional
	public CustomerDetails getCustomerDetailsByCustNameAndContactNo(String custName, String contactNo, Integer orgUid) {
		// TODO Auto-generated method stub
		return customerDao.getCustomerDetailsByCustNameAndContactNo(custName, contactNo, orgUid);
	} 

	@Override
	@Transactional
	public boolean checkUserCustomerMapping(String idsValuesToFetch,Integer primId, Integer custId)
			throws Exception {
		// TODO Auto-generated method stub
		return customerDao.checkUserCustomerMapping(idsValuesToFetch,primId,custId);
	}

	@Override
	@Transactional
	public boolean checkSecUserCustomerMapping(Integer primId, Integer custId)
			throws Exception {
		// TODO Auto-generated method stub
		return customerDao.checkSecUserCustomerMapping(primId,custId);
	}

	@Override
	@Transactional
	public List<Object[]> getSupplierListBySupplierNameAndMobileNo(
			Integer orgUid, String query, String documentType) throws Exception {
		// TODO Auto-generated method stub
		return customerDao.getSupplierListBySupplNameAndMobileNo(orgUid, query, documentType);
	}
}
