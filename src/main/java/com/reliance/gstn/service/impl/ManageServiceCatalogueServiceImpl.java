/**
 * 
 */
package com.reliance.gstn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reliance.gstn.dao.ManageServiceCatalogueDAO;
import com.reliance.gstn.model.ManageServiceCatalogue;
import com.reliance.gstn.model.ServiceFetch;
import com.reliance.gstn.service.ManageServiceCatalogueService;
import com.reliance.gstn.util.WebserviceCallUtil;

/**
 * @author Nikesh.Bansod
 *
 */
@Service
public class ManageServiceCatalogueServiceImpl implements ManageServiceCatalogueService {
	
	private static final Logger logger = Logger.getLogger(UnitOfMeasurementServiceImpl.class);
	
	@Autowired
	public ManageServiceCatalogueDAO manageServiceCatalogueDAO;
	
	@Autowired 
	private InventoryServiceImpl inventoryServiceImpl;

	@Value("${${env}.addService.url}")
	private String addServiceUrl;
	
	@Override
	@Transactional
	public List<ManageServiceCatalogue> listManageServiceCatalogue(Integer uId) {
		return manageServiceCatalogueDAO.listManageServiceCatalogue(uId);
	}

	@Override
	@Transactional
	public Map<String,Object> addManageServiceCatalogue(ManageServiceCatalogue manageServiceCatalogue) throws ConstraintViolationException, Exception{
		
		return manageServiceCatalogueDAO.addManageServiceCatalogue(manageServiceCatalogue);
	}

	@Override
	@Transactional
	public ManageServiceCatalogue getManageServiceCatalogueById(Integer id) throws Exception{
		
		return manageServiceCatalogueDAO.getManageServiceCatalogueById(id);
	}

	@Override
	@Transactional
	public String updateManageServiceCatalogue(ManageServiceCatalogue manageServiceCatalogue) throws DataIntegrityViolationException, Exception {
		
		return manageServiceCatalogueDAO.updateManageServiceCatalogue(manageServiceCatalogue);
	}

	@Override
	@Transactional
	public String removeManageServiceCatalogue(ManageServiceCatalogue manageServiceCatalogue)  throws Exception{
		
		return  manageServiceCatalogueDAO.removeManageServiceCatalogue( manageServiceCatalogue);
	}

	@Override
	@Transactional
	public List<ManageServiceCatalogue> getServicesList(String idsValuesToFetch,Integer orgUId) throws Exception{
		// TODO Auto-generated method stub
		return manageServiceCatalogueDAO.getServicesList(idsValuesToFetch,orgUId);
	}

	@Override
	@Transactional
	public boolean checkIfServiceExists(String service, Integer orgUId) throws Exception{
		// TODO Auto-generated method stub
		return manageServiceCatalogueDAO.checkIfServiceExists(service,orgUId);
	}

	@Override
	@Transactional
	public List<String> getServiceNameListByAutoComplete(String query,Integer orgUId, String serviceTypeCall, Integer location) {
		// TODO Auto-generated method stub
		return manageServiceCatalogueDAO.getServiceNameListByAutoComplete( query, orgUId, serviceTypeCall, location);
	}

	@Override
	@Transactional
	public ManageServiceCatalogue getManageServiceCatalogueByName(String serviceName,Integer orgUId) {
		// TODO Auto-generated method stub
		return manageServiceCatalogueDAO.getManageServiceCatalogueByName( serviceName, orgUId);
	}
	
	@Override
	public String addServiceThroughWebserviceCall(List<ManageServiceCatalogue> serviceList) {
		String status="false";
		
		logger.info("Entry Webservice Calling");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
		String body = gson.toJson(serviceList);
		/*String body = new Gson().toJson(productList);*/
		System.out.println(body);
		System.out.println(addServiceUrl);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, String> headersMap = inventoryServiceImpl.createApiHeader();		
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		System.out.println(headersMap);
		System.out.println(extraParams);
		
		String response = WebserviceCallUtil.callWebservice(addServiceUrl, headersMap, body, extraParams);
		logger.info("Payload Response :: " + response);
		responseMap=(Map<String, Object>)new Gson().fromJson(response.toString(), responseMap.getClass());
		logger.info("Payload Response map:: " + responseMap);
		if(!(boolean)responseMap.get("error")){
			status="true";
		}
		return status;
		
	}

	@Override
	@Transactional
	public List<ManageServiceCatalogue> getServicesList(String idsValuesToFetch, Integer orgUId, String docType,Integer location) throws Exception{
		return manageServiceCatalogueDAO.getServicesList( idsValuesToFetch,  orgUId,  docType, location);
	}

	@Override
	@Transactional
	public ManageServiceCatalogue getManageServiceCatalogueByNameAndStoreId(String serviceName, Integer orgUId,Integer storeId) {
		return manageServiceCatalogueDAO.getManageServiceCatalogueByNameAndStoreId( serviceName,  orgUId, storeId);
	}
	
	@Override
	@Transactional
	public List<ServiceFetch> getServicesListJson(String idsValuesToFetch,
			Integer orgUId) {
		// TODO Auto-generated method stub
		return manageServiceCatalogueDAO.getServicesListJson(idsValuesToFetch,orgUId);
	}

	@Override
	@Transactional
	public ServiceFetch getManageServiceCatalogueByIdFetch(Integer id) throws Exception{
		
		return manageServiceCatalogueDAO.getManageServiceCatalogueByIdFetch(id);
	}

}
