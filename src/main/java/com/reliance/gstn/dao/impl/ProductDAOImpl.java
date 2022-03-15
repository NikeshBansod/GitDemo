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

import com.reliance.gstn.dao.ProductDAO;
import com.reliance.gstn.model.Product;
import com.reliance.gstn.model.ProductFetch;
import com.reliance.gstn.service.GenerateInvoiceService;
import com.reliance.gstn.service.ManageOffersService;
import com.reliance.gstn.service.PoDetailsService;
import com.reliance.gstn.util.GSTNConstants;
/**
 * @author Nikesh.Bansod
 *
 */
@Repository
public class ProductDAOImpl<productsList> implements ProductDAO {
	
	private static final Logger logger = Logger.getLogger(ProductDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private PoDetailsService poDetails;
	
	@Autowired
	private GenerateInvoiceService invoiceService;
	
	@Autowired
	private ManageOffersService offerService;

	@Value("${product_remove_query}")
	private String removeProduct;
	
	@Value("${check_product_org_duplicacy}")
	private String checkProductDuplicacy;
	
	@Value("${get_product_list}")
	private String getProductList;
	
	@Value("${fetch_productName_list_by_autoComplete_query}")
	private String fetchProductNameListByAutoCompleteQuery;
	
	@Value("${get_product_by_product_name}")
	private String fetchProductByProductName;
	
	/*@Value("${update_product_by_product_id}")
	private String updateProduct;*/
	
	@Value("${get_invoice_product_list}")
	private String manageProductInvoiceListByrefId;

	@Value("${get_billofsupply_product_list}")
	private String manageProductBillOfSupplyListByrefId;
	
	@Value("${get_invoice_and_billofsupply_product_list}")
	private String manageProductPurchaseEntryInvoiceAndBillOfSupplyListByrefId;
	
	@Value("${fetch_productName_list_of_invoice_by_autoComplete_query}")
	private String fetchProductNameListOfInvoiceByAutoCompleteQuery;
	
	@Value("${fetch_productName_list_of_billOfSupply_by_autoComplete_query}")
	private String fetchProductNameListOfBillOfSupplyByAutoCompleteQuery;
	
	@Value("${fetch_productName_list_of_purchase_entry_invoice_and_billOfSupply_by_autoComplete_query}")
	private String fetchProductNameListOfPurEntInvAndBOSByAutoCompleteQuery;
	
	
	@Value("${get_product_by_product_name_and_store_id}")
	private String fetchProductByProductNameAndStoreId;
	
	@Value("${get_product_list_fetch}")
	private String getProductListFetch;
	
	@Value("${get_product_list_edit_page}")
	private String getProductListEditPage;
	
	@Value("${update_product_edit_page}")
	private String updateProduct;
	
	@Value("${delete_product}")
	private String deleteProduct;
	
	
	@Value("${fetch_productName_list_Of_inventory_by_autoComplete_query}")
	private String fetchProductNameListOfInventory;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getProductsList(String idsValuesToFetch,Integer orgUId) throws Exception{
		logger.info("Entry");
		List<Product> productsList = new ArrayList<Product>();
		try {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(getProductList);
		
		query.setInteger("refOrgId" , orgUId);
		
		productsList = (List<Product>)query.list();
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		
		return productsList;
	}
	
	
	@Override
	public Map<String, Object> addProduct(Product product) throws ConstraintViolationException, Exception {
		logger.info("Entry");
		Map<String,Object> mapResponse = new HashMap<String,Object>();
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			
			product.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			session.save(product);
			mapResponse.put("product", product);
			response = GSTNConstants.SUCCESS;
		} catch(ConstraintViolationException e){
			logger.error("Error in:",e);
			throw e;
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		mapResponse.put(GSTNConstants.RESPONSE, response);
		logger.info("Exit");
		return mapResponse;
	}

	@Override
	public Product getProductById(Integer id)throws Exception{
		logger.info("Entry");
		Product msc;
		try {
		Session session = sessionFactory.getCurrentSession();		
		msc = (Product) session.get(Product.class, id);
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		
		return msc;
	}

	@Override
	public String updateProduct(Product product) throws DataIntegrityViolationException, Exception {
		logger.info("Exit");
		String response = GSTNConstants.FAILURE;
		Session session = sessionFactory.getCurrentSession();	
		
		try {
			
			Integer OrgId=product.getRefOrgId();
			String Newname=product.getName();
			String Existingname= product.getExistingName();
			String SellingUom=product.getUnitOfMeasurement();
			String purchaseUom=product.getUnitOfMeasurement();
			String OtherPurchaseUom=product.getOtherUOM();
			String SellingOtherUom=product.getOtherUOM();
			Double tax=product.getProductIgst();
			Double purchasePrice=product.getPurchaseRate();
			Double sellingPrice=product.getProductRate();
			
			
			
			/*product.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));*/
			Query query = session.createQuery(updateProduct);
			query.setString("Newname",Newname);
			query.setString("Existingname",Existingname);
			query.setString("SellingUom",SellingUom);
			query.setString("purchaseUom",purchaseUom); 
			query.setString("OtherPurchaseUom",OtherPurchaseUom);
			query.setString("SellingOtherUom",SellingOtherUom);
			query.setDouble("tax",tax);
			query.setDouble("purchasePrice",purchasePrice);
			query.setDouble("sellingPrice",sellingPrice);
			query.setInteger("updatedBY", product.getReferenceId());
			query.setInteger("refOrgId",product.getRefOrgId() );
			query.setParameter("updatedOn", new java.sql.Timestamp(new Date().getTime()));
			
			query.executeUpdate();
			response = GSTNConstants.SUCCESS; 
		} catch(DataIntegrityViolationException e){
			logger.error("Error in:",e);
			throw e;
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public String removeProduct(Product product) throws Exception{
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
		
				Product persistProductDetail = (Product) session.get(Product.class, product.getId());
				
				if(persistProductDetail.getInventoryUpdateFlag().equals("Y"))
				{
					response="Product Cannot be Deleted as Inventory Already Updated";
				}
				else
				{
					Query query = session.createQuery(deleteProduct);
					query.setString("productName",persistProductDetail.getName());
					query.setInteger("refOrgId",persistProductDetail.getRefOrgId() );
					/*session.delete(persistProductDetail);*/
					query.executeUpdate();
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

	public boolean checkIfProductExists(String productName, Integer orgUId) throws Exception{


		logger.info("Entry");
		boolean gstinRegistered = false;
		try {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(checkProductDuplicacy);

		query.setString("productName", productName);


		query.setInteger("orgRefId", orgUId);
		@SuppressWarnings("unchecked")
		List<Product> list = (List<Product>)query.list();
		if ((!list.isEmpty())) {
			gstinRegistered = true;
		}
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		
		return gstinRegistered;
	}


	@Override
	public List<String> getProductNameListByAutoComplete(String parameter, Integer orgUId, String docType, Integer location) {
		logger.info("Entry");
		String queryToCall = null;
		if(docType.equals("invoice")){
			queryToCall = fetchProductNameListOfInvoiceByAutoCompleteQuery;
		}else if(docType.equals("billOfSupply")){
			queryToCall = fetchProductNameListOfBillOfSupplyByAutoCompleteQuery;
		}else if(docType.equals("purchaseEntryInvoiceAndBillOfSupply")){
			queryToCall = fetchProductNameListOfPurEntInvAndBOSByAutoCompleteQuery;
		}
		else{
			queryToCall=fetchProductNameListOfInventory;
		}
		
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(queryToCall);
		query.setString("inputData", '%'+parameter+'%');
		query.setInteger("orgRefId", orgUId);
		query.setInteger("storeId" , location);
		
		@SuppressWarnings("unchecked")
		List<String> productNameList = query.list();
		
		logger.info("Exit");
		return productNameList;
	}


	@Override
	public Product getProductByName(String productName, Integer orgUId) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(fetchProductByProductName);
		query.setString("inputData", productName+'%');
		query.setInteger("orgRefId", orgUId);
		
		@SuppressWarnings("unchecked")
		List<Product> productList = (List<Product>)query.list();
		logger.info("Exit");
		return productList.get(0);
	}


	@Override
	public List<Product> getProductsList(String idsValuesToFetch, Integer orgUId, String docType, Integer location) {
		logger.info("Entry");
		List<Product> productsList = new ArrayList<Product>();
		String queryToCall = null;
		if(docType.equals("invoice")){
			queryToCall = manageProductInvoiceListByrefId;
		}else if(docType.equals("billOfSupply")){
			queryToCall = manageProductBillOfSupplyListByrefId;
		}else if(docType.equals("purchaseEntryInvoiceAndBillOfSupply")){
			queryToCall = manageProductPurchaseEntryInvoiceAndBillOfSupplyListByrefId;
		}
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(queryToCall);
			
			query.setInteger("refOrgId" , orgUId);
			query.setInteger("storeId" , location);
			productsList = (List<Product>)query.list();
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		
		return productsList;
	}


	@Override
	public Product getProductByNameAndStoreId(String productName, Integer orgUId, Integer storeId) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(fetchProductByProductNameAndStoreId);
		query.setString("inputData", productName+'%');
		query.setInteger("orgRefId", orgUId);
		query.setInteger("storeId",storeId);
		
		@SuppressWarnings("unchecked")
		List<Product> productList = (List<Product>)query.list();
		logger.info("Exit");
		return productList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductFetch> getProductsListJson(String idsValuesToFetch,Integer orgUId) throws Exception{
		logger.info("Entry");
		List<ProductFetch> productsList = new ArrayList<>();
		try {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(getProductListFetch);
		
		query.setInteger("refOrgId" , orgUId);
		
		productsList = (List<ProductFetch>)query.list();
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		
		return productsList;
	}


	@Override
	public ProductFetch getProductByIdJson(Integer id) throws Exception {
		logger.info("Entry");
		ProductFetch msc = null;
		try {
		Session session = sessionFactory.getCurrentSession();		
		msc = (ProductFetch) session.get(ProductFetch.class, id);
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit"); 
		return msc;
	}


	@Override
	public List<ProductFetch> getProductByIdJson(Integer refOrgID, String name)
			throws Exception {
		logger.info("Entry");
		
		List<ProductFetch>productsList= new ArrayList<>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(getProductListEditPage);
			query.setInteger("refOrgId" , refOrgID);
			query.setString("name" , name);
			productsList = (List<ProductFetch>)query.list();
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit"); 
		return productsList;
	}
	
	@Override
	public String updateProductThroughPurchaseEntry(Product product) throws ConstraintViolationException, Exception {
		logger.info("Entry");
		Map<String,Object> mapResponse = new HashMap<String,Object>();
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			
			product.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			session.saveOrUpdate(product);
			response = GSTNConstants.SUCCESS;
		} catch(ConstraintViolationException e){
			logger.error("Error in:",e);
			throw e;
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}
}
