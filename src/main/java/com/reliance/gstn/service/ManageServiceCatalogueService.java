/**
 * 
 */
package com.reliance.gstn.service;

import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import com.reliance.gstn.model.ManageServiceCatalogue;
import com.reliance.gstn.model.ServiceFetch;

/**
 * @author Nikesh.Bansod
 *
 */
public interface ManageServiceCatalogueService {

	List<ManageServiceCatalogue> listManageServiceCatalogue(Integer uId);

	Map<String, Object> addManageServiceCatalogue(ManageServiceCatalogue manageServiceCatalogue) throws ConstraintViolationException, Exception;

	ManageServiceCatalogue getManageServiceCatalogueById(Integer id)throws Exception;

	String updateManageServiceCatalogue(ManageServiceCatalogue manageServiceCatalogue) throws DataIntegrityViolationException,Exception;

	String removeManageServiceCatalogue(ManageServiceCatalogue manageServiceCatalogue) throws Exception;

	List<ManageServiceCatalogue> getServicesList(String idsValuesToFetch, Integer orgUId) throws Exception;

	boolean checkIfServiceExists(String service, Integer orgUId) throws Exception;

	List<String> getServiceNameListByAutoComplete(String query,Integer orgUId, String serviceTypeCall, Integer location);

	ManageServiceCatalogue getManageServiceCatalogueByName(String serviceName,Integer orgUId);
	
	String addServiceThroughWebserviceCall(List<ManageServiceCatalogue> serviceList);

	List<ManageServiceCatalogue> getServicesList(String idsValuesToFetch, Integer orgUId, String docType,Integer location) throws Exception;

	ManageServiceCatalogue getManageServiceCatalogueByNameAndStoreId(String serviceName, Integer orgUId, Integer parseInt);
	
	List<ServiceFetch> getServicesListJson(String idsValuesToFetch,Integer orgUId);

	ServiceFetch getManageServiceCatalogueByIdFetch(Integer id)throws Exception;
	
}
