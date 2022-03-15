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
import com.reliance.gstn.dao.ProductDAO;
import com.reliance.gstn.model.Product;
import com.reliance.gstn.model.ProductFetch;
import com.reliance.gstn.service.ProductService;
import com.reliance.gstn.util.WebserviceCallUtil;

/**
 * @author Nikesh.Bansod
 *
 */
@Service
public class ProductServiceImpl implements ProductService {
	
	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);
	
	 
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired 
	private InventoryServiceImpl inventoryServiceImpl;
	
	@Value("${${env}.addProducts.url}")
	private String addProductsUrl;
	
	@Override
	@Transactional
	public List<Product> getProductsList(String idsValuesToFetch,Integer orgUId) throws Exception {
		
		return productDAO.getProductsList(idsValuesToFetch,orgUId) ;
	}

	@Override
	@Transactional
	public Map<String, Object> addProduct(Product product) throws ConstraintViolationException, Exception {
		// TODO Auto-generated method stub
		return productDAO.addProduct(product);
	}

	@Override
	@Transactional
	public Product getProductById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return productDAO.getProductById(id);
	}

	@Override
	@Transactional
	public String updateProduct(Product product)  throws DataIntegrityViolationException, Exception{
		// TODO Auto-generated method stub
		return productDAO.updateProduct(product);
	}

	@Override
	@Transactional
	public String removeProduct(Product product) throws Exception {
		// TODO Auto-generated method stub
		return productDAO.removeProduct(product);
	}

	@Override
	@Transactional
	public boolean checkIfProductExists(String product, Integer orgUId) throws Exception {
		// TODO Auto-generated method stub
		return productDAO.checkIfProductExists(product,  orgUId);
	}

	@Override
	@Transactional
	public List<String> getProductNameListByAutoComplete(String query, Integer orgUId, String productTypeCall, Integer location) {
		// TODO Auto-generated method stub
		return productDAO.getProductNameListByAutoComplete( query,  orgUId, productTypeCall, location);
	}

	@Override
	@Transactional
	public Product getProductByName(String productName, Integer orgUId) {
		// TODO Auto-generated method stub
		return productDAO.getProductByName( productName,  orgUId);
	}

	@Override
	@Transactional
	public List<Product> getProductsList(String idsValuesToFetch, Integer orgUId, String docType, Integer location) throws Exception {
		// TODO Auto-generated method stub
		return productDAO.getProductsList(idsValuesToFetch,orgUId, docType,location);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public Map<String, Object> addProductThroughWebserviceCall(List<Product> productList) {
		String status="false";
		
		logger.info("Entry Webservice Calling");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
		String body = gson.toJson(productList);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, String> headersMap = inventoryServiceImpl.createApiHeader();		
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		String response = WebserviceCallUtil.callWebservice(addProductsUrl, headersMap, body, extraParams);
		logger.info("Payload Response :: " + response);
		responseMap=(Map<String, Object>)new Gson().fromJson(response.toString(), responseMap.getClass());
		logger.info("Payload Response map:: " + responseMap);
		if(!(boolean)responseMap.get("error")){
			status="true";
		}
		return responseMap;
	
	}

	@Override
	@Transactional
	public Product getProductByNameAndStoreId(String productName, Integer orgUId, Integer storeId) {
		// TODO Auto-generated method stub
		return productDAO.getProductByNameAndStoreId( productName,  orgUId,  storeId);
	}
	
	@Override
	@Transactional
	public List<ProductFetch> getProductsListJson(String idsValuesToFetch,Integer orgUId) throws Exception {
		
		return productDAO.getProductsListJson(idsValuesToFetch,orgUId) ;
	}

	@Override
	@Transactional
	public ProductFetch getProductByIdJson(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return productDAO.getProductByIdJson(id);
	}

	@Override
	@Transactional
	public List<ProductFetch> getProductByIdJsonEditPage(Integer refOrgID,
			String name) throws Exception {
		// TODO Auto-generated method stub
		return productDAO.getProductByIdJson(refOrgID,name);
	}

	@Override

	@Transactional
	public String updateProductThroughPurchaseEntry(Product product)throws Exception {
		// TODO Auto-generated method stub
		return productDAO.updateProductThroughPurchaseEntry(product);
	}
}
