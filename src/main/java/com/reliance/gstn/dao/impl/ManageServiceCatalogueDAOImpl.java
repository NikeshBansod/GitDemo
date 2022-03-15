/**
 * 
 */
package com.reliance.gstn.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.dao.ManageServiceCatalogueDAO;
import com.reliance.gstn.model.ManageServiceCatalogue;
import com.reliance.gstn.model.ServiceFetch;
import com.reliance.gstn.util.GSTNConstants;

/**
 * @author Nikesh.Bansod
 *
 */
@Repository
public class ManageServiceCatalogueDAOImpl implements ManageServiceCatalogueDAO {
	
	private static final Logger logger = Logger.getLogger(ManageServiceCatalogueDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${manage_Service_Catalogue_list_query}")
	private String manageServiceCatalogueListQuery;
	
	@Value("${manage_service_catalogue_remove_query}")
	private String removeServiceCatalogueQuery;
	
	@Value("${manage_service_catalogue_list_by_refid}")
	private String manageServiceCatalogueListByrefId;

	@Value("${check_service_org_duplicacy}")
	private String checkServiceDuplicacy;
	
	@Value("${unit_of_measurement_list_query}")
	private String unitOfMeasurementListQuery;
	
	@Value("${fetch_serviceName_list_by_autoComplete_query}")
	private String fetchServiceNameListByAutoCompleteQuery;
	
	@Value("${get_service_by_service_name}")
	private String fetchManageServiceCatalogueByName;
	
	@Value("${manage_service_invoice_list_by_refid}")
	private String manageServiceInvoiceListByrefId;
	
	@Value("${manage_service_billofsupply_list_by_refid}")
	private String manageServiceBillOfSupplyListByrefId;
	
	@Value("${fetch_serviceName_list_of_invoice_by_autoComplete_query}")
	private String fetchServiceNameListOfInvoiceByAutoCompleteQuery;
	
	@Value("${fetch_serviceName_list_of_billOfSupply_by_autoComplete_query}")
	private String fetchServiceNameListOfBillOfSupplyByAutoCompleteQuery;
	
	@Value("${get_service_by_service_name_and_store_id}")
	private String fetchManageServiceCatalogueByNameAndStoreId;
	
	@Value("${manage_service_catalogue_list_by_refid_Fetch}")
	private String manageServiceCatalogueListByrefIdFetch;
	
	@Value("${update_Service_Edit_Page}")
	private String updateService;
	
	@Override
	public List<ManageServiceCatalogue> listManageServiceCatalogue(Integer uId) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(manageServiceCatalogueListQuery);
		query.setInteger("referenceId", uId);
		
		@SuppressWarnings("unchecked")
		List<ManageServiceCatalogue> manageServiceCatalogueList = (List<ManageServiceCatalogue>)query.list();
		logger.info("Exit");
		return manageServiceCatalogueList;
	}

	@Override
	public Map<String,Object> addManageServiceCatalogue(ManageServiceCatalogue manageServiceCatalogue) throws ConstraintViolationException, Exception {
		logger.info("Entry");
		Map<String,Object> mapResponse = new HashMap<String,Object>();
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			manageServiceCatalogue.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			session.save(manageServiceCatalogue);
			mapResponse.put("manageServiceCatalogue", manageServiceCatalogue);
			response = GSTNConstants.SUCCESS;
		} catch(ConstraintViolationException e){
			logger.error("Error in:",e);
			throw e;
		}catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		mapResponse.put(GSTNConstants.RESPONSE, response);
		logger.info("Exit");
		return mapResponse;
	}

	@Override
	public ManageServiceCatalogue getManageServiceCatalogueById(Integer id) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();		
		ManageServiceCatalogue msc = (ManageServiceCatalogue) session.get(ManageServiceCatalogue.class, id);
		logger.info("Exit");
		return msc;
	}

	@Override
	public String updateManageServiceCatalogue(ManageServiceCatalogue manageServiceCatalogue) throws DataIntegrityViolationException, Exception{
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			Integer OrgId=manageServiceCatalogue.getRefOrgId();
			String Newname=manageServiceCatalogue.getName();
			String Existingname= manageServiceCatalogue.getExistingName();
			Double tax=manageServiceCatalogue.getServiceIgst();
			Double sellingPrice=manageServiceCatalogue.getServiceRate();
			manageServiceCatalogue.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			Query query = session.createQuery(updateService);
			
			query.setString("Newname",Newname);
			query.setString("Existingname",Existingname);
			query.setDouble("tax",tax);
			query.setDouble("sellingPrice",sellingPrice);
			query.setInteger("updatedBY", manageServiceCatalogue.getReferenceId());
			query.setInteger("refOrgId",manageServiceCatalogue.getRefOrgId() );
			query.setParameter("updatedOn", new java.sql.Timestamp(new Date().getTime()));
			query.executeUpdate();
			response = GSTNConstants.SUCCESS;
		} catch(DataIntegrityViolationException e){
			logger.error("Error in:",e);
			throw e;
		}catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public String removeManageServiceCatalogue(ManageServiceCatalogue manageServiceCatalogue) throws Exception{
		logger.info("Entry");
	
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
	
			//remove Service
			Object persistServiceDetail = session.get(ManageServiceCatalogue.class, manageServiceCatalogue.getId());
			if(persistServiceDetail != null){
			session.delete(persistServiceDetail);
			response = GSTNConstants.SUCCESS;
			}
			
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public List<ManageServiceCatalogue> getServicesList(String idsValuesToFetch,Integer orgUId) {
		logger.info("Entry");
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(manageServiceCatalogueListByrefId);
		
		query.setInteger("orgUid", orgUId);
		@SuppressWarnings("unchecked")
		List<ManageServiceCatalogue> servicesList = query.list();
		logger.info("Exit");
		return servicesList;
	}

	@Override
	public boolean checkIfServiceExists(String service, Integer orgUId) {
		logger.info("Entry");
		boolean gstinRegistered = false;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(checkServiceDuplicacy);
		query.setString("serviceName", service);
		query.setInteger("orgRefId", orgUId);
		@SuppressWarnings("unchecked")
		List<ManageServiceCatalogue> list = (List<ManageServiceCatalogue>)query.list();
		
		if (!list.isEmpty()) {
			gstinRegistered = true;
		}
		logger.info("Exit");
		
		return gstinRegistered;
	}

	@Override
	public List<String> getServiceNameListByAutoComplete(String parameter,Integer orgUId, String docType,Integer location) {
		logger.info("Entry");
		String queryToCall = null;
		if(docType.equals("invoice")){
			queryToCall = fetchServiceNameListOfInvoiceByAutoCompleteQuery;
		}else if(docType.equals("billOfSupply")){
			queryToCall = fetchServiceNameListOfBillOfSupplyByAutoCompleteQuery;
		}
		
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(queryToCall);
		query.setString("inputData", '%'+parameter+'%');
		query.setInteger("orgRefId", orgUId);
		query.setInteger("storeId" , location);
		
		@SuppressWarnings("unchecked")
		List<String> serviceNameList = query.list();
		
		logger.info("Exit");
		return serviceNameList;
	}

	@Override
	public ManageServiceCatalogue getManageServiceCatalogueByName(String serviceName,Integer orgUId) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(fetchManageServiceCatalogueByName);
		query.setString("inputData", '%'+serviceName+'%');
		query.setInteger("orgRefId", orgUId);
		
		@SuppressWarnings("unchecked")
		List<ManageServiceCatalogue> manageServiceCatalogueList = (List<ManageServiceCatalogue>)query.list();
		logger.info("Exit");
		return manageServiceCatalogueList.get(0);
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public List<ManageServiceCatalogue> getServicesList(String idsValuesToFetch, Integer orgUId, String docType, Integer location) throws Exception {
		logger.info("Entry");
		List<ManageServiceCatalogue> servicesList = new ArrayList<ManageServiceCatalogue>();
		String queryToCall = null;
		if(docType.equals("invoice")){
			queryToCall = manageServiceInvoiceListByrefId;
		}else if(docType.equals("billOfSupply")){
			queryToCall = manageServiceBillOfSupplyListByrefId;
		}
		try{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(queryToCall);
			
			query.setInteger("orgUid", orgUId);
			query.setInteger("storeId" , location);
			servicesList = query.list();
		}catch(Exception e){
			logger.error("Error in:",e);
			throw e;
		}
		
		logger.info("Exit");
		return servicesList;
	}

	@Override
	public List<String> getServiceNameListByAutoComplete(String parameter, Integer orgUId) {
		logger.info("Entry");
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(fetchServiceNameListByAutoCompleteQuery);
		query.setString("inputData", '%'+parameter+'%');
		query.setInteger("orgRefId", orgUId);
		
		@SuppressWarnings("unchecked")
		List<String> serviceNameList = query.list();
		
		logger.info("Exit");
		return serviceNameList;
	}

	@Override
	public ManageServiceCatalogue getManageServiceCatalogueByNameAndStoreId(String serviceName, Integer orgUId,Integer storeId) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(fetchManageServiceCatalogueByNameAndStoreId);
		query.setString("inputData", serviceName+'%');
		query.setInteger("orgRefId", orgUId);
		query.setInteger("storeId",storeId);
		
		@SuppressWarnings("unchecked")
		List<ManageServiceCatalogue> manageServiceCatalogueList = (List<ManageServiceCatalogue>)query.list();
		logger.info("Exit");
		return manageServiceCatalogueList.get(0);
	}
	
		
	@Override
	public List<ServiceFetch> getServicesListJson(String idsValuesToFetch,
			Integer orgUId) {
		logger.info("Entry");
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(manageServiceCatalogueListByrefIdFetch);
		
		query.setInteger("orgUid", orgUId);
		@SuppressWarnings("unchecked")
		List<ServiceFetch> servicesList = query.list();
		logger.info("Exit");
		return servicesList;
	}

	@Override
	public ServiceFetch getManageServiceCatalogueByIdFetch(Integer id) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();		
		ServiceFetch msc = (ServiceFetch) session.get(ServiceFetch.class, id);
		logger.info("Exit");
		return msc;
	}
}
