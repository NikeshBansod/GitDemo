package com.reliance.gstn.service;

import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import com.reliance.gstn.model.CustomerDetails;

public interface CustomerService {

	Map<String,Object> addCustomerDetails(CustomerDetails customerDetails, Map<?, ?> mapValues) throws ConstraintViolationException, Exception;

	List<CustomerDetails> viewCustomerDetailsList(Integer getuId) throws Exception;

	String deleteCustomerDetails(Integer id) throws ConstraintViolationException, Exception;

	String updateCustomerDetails(CustomerDetails customerDetails, Map<?, ?> mapValues)throws DataIntegrityViolationException, Exception;

	CustomerDetails getCustomerDetailsById(Integer id) throws Exception;

	List<CustomerDetails> getCustomersList(Integer refOrgId) throws Exception;

	List<String> getCustomersListByCustName(String idsValuesToFetch) throws Exception;

	CustomerDetails getCustomerDetailsByCustName(String custName, String idsValuesToFetch) throws Exception;

	boolean checkIfCustomerExists(String customer, String custName, Integer orgUId) throws Exception;

	CustomerDetails checkIfCustomerContactNoExists(String contactNo, Integer orgUId);

	List<Object[]> getCustomersListByCustNameAndMobileNo(Integer orgUid, String query, String documentType);

	CustomerDetails getCustomerDetailsByCustNameAndContactNo(String custName, String contactNo, Integer orgUid);

	boolean checkUserCustomerMapping(String idsValuesToFetch,Integer primId, Integer custId) throws Exception;

	boolean checkSecUserCustomerMapping(Integer primId, Integer custId) throws Exception;

	List<Object[]> getSupplierListBySupplierNameAndMobileNo(Integer orgUid,String query, String documentType)throws Exception;

}
