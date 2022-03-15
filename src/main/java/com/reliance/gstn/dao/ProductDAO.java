/**
 * 
 */
package com.reliance.gstn.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import com.reliance.gstn.model.Product;
import com.reliance.gstn.model.ProductFetch;

/**
 * @author Nikesh.Bansod
 *
 */
public interface ProductDAO {

	List<Product> getProductsList(String idsValuesToFetch,Integer orgUId)throws Exception;

	Map<String, Object> addProduct(Product product) throws ConstraintViolationException, Exception;

	Product getProductById(Integer id)throws Exception;

	String updateProduct(Product product) throws DataIntegrityViolationException, Exception;

	String removeProduct(Product product)throws Exception;

	boolean checkIfProductExists(String product, Integer orgUId)throws Exception;

	List<String> getProductNameListByAutoComplete(String query, Integer orgUId, String productTypeCall, Integer location);

	Product getProductByName(String productName, Integer orgUId);

	List<Product> getProductsList(String idsValuesToFetch, Integer orgUId, String docType, Integer location);

	Product getProductByNameAndStoreId(String productName, Integer orgUId, Integer storeId);
	
	List<ProductFetch> getProductsListJson(String idsValuesToFetch,Integer orgUId) throws Exception;
	
	ProductFetch getProductByIdJson(Integer id)throws Exception;

	List<ProductFetch> getProductByIdJson(Integer refOrgID, String name)throws Exception;

	String updateProductThroughPurchaseEntry(Product product)throws Exception;


}
