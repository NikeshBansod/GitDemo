/**
 * 
 */
package com.reliance.gstn.dao;

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
public interface ManageServiceCatalogueDAO {

	List<ManageServiceCatalogue> listManageServiceCatalogue(Integer uId);

	Map<String, Object> addManageServiceCatalogue(ManageServiceCatalogue manageServiceCatalogue) throws ConstraintViolationException, Exception;

	ManageServiceCatalogue getManageServiceCatalogueById(Integer id);

	String updateManageServiceCatalogue(ManageServiceCatalogue manageServiceCatalogue) throws DataIntegrityViolationException, Exception;

	String removeManageServiceCatalogue(ManageServiceCatalogue manageServiceCatalogue)  throws Exception;

	List<ManageServiceCatalogue> getServicesList(String idsValuesToFetch, Integer orgUId) throws Exception;

	boolean checkIfServiceExists(String service, Integer orgUId) throws Exception;

	List<String> getServiceNameListByAutoComplete(String query,Integer orgUId);

	ManageServiceCatalogue getManageServiceCatalogueByName(String serviceName,Integer orgUId);

	List<ManageServiceCatalogue> getServicesList(String idsValuesToFetch, Integer orgUId, String docType,Integer location) throws Exception;

	List<String> getServiceNameListByAutoComplete(String query, Integer orgUId, String serviceTypeCall,Integer location);

	ManageServiceCatalogue getManageServiceCatalogueByNameAndStoreId(String serviceName, Integer orgUId,Integer storeId);
	
	List<ServiceFetch> getServicesListJson(String idsValuesToFetch,Integer orgUId);

	ServiceFetch getManageServiceCatalogueByIdFetch(Integer id);
	
	

}
