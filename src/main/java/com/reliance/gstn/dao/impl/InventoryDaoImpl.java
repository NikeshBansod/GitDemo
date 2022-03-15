package com.reliance.gstn.dao.impl;

import java.util.List;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.reliance.gstn.dao.InventoryDao;
import com.reliance.gstn.model.InventoryDetails;
import com.reliance.gstn.model.InventoryDocSequence;
import com.reliance.gstn.model.InventoryProductSave;
import com.reliance.gstn.model.InventoryProductTable;
import com.reliance.gstn.model.Product;
import com.reliance.gstn.util.AspApiConstants;
import com.reliance.gstn.util.GSTNUtil;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
public class InventoryDaoImpl implements InventoryDao {

	private static final Logger logger = Logger
			.getLogger(InventoryDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${product_details_by_storeid_and_productid}")
	private String getProductDetailsByStoreIdandProductId;

	@Value("${update_product_details_by_storeid_product_id}")
	private String updateProductDetailsByStoreIdandProductId;
	
	@Value("${product_details_by_storeid_and_productid_orgId}")
	private String getProductDetailsByProductidandStoreIdandOrgid;
	
	@Value("${get_product_details_org_id}")
	private String getProductDetailsByOrgid;
	
	@Value("${fetch_history_of_inventory}")
	private String fetchHistoryOfInventory;
	

	public Product getProductDetailsByStoreIdAndProductId(Integer productId,Integer storeId)
			throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery(getProductDetailsByStoreIdandProductId);
		query.setInteger("productId", productId);
		query.setInteger("storeId", storeId);
		Product product =  (Product) query.uniqueResult();
		if (product != null)
			return product;
		else {
			logger.info("product not found for this store");
		}
		logger.info("Exit");
		return product;
	}
	@Transactional
	public Product getProductDetails(Integer storeId, Integer refOrgId,String name)
			throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery(getProductDetailsByProductidandStoreIdandOrgid);
		query.setInteger("storeId", storeId);
		query.setInteger("refOrgId", refOrgId);
		query.setString("name", name);
		Product product =  (Product) query.uniqueResult();
		if (product != null)
			return product;
		else {
			logger.info("product not found for this store");
		}
		logger.info("Exit");
		return product;
	}
	
	/*@Transactional
	public List<InventoryDocSequence> getProductDetailsForDocSequence(Integer refOrgId)throws Exception {
		logger.info("Entry");
		List<Object[]> transactions=null;
		List<InventoryDocSequence> inventoryDocSequenceList=null;
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery(getProductDetailsByOrgid);
		query.setInteger("refOrgId", refOrgId);
		
		if(query.uniqueResult()!=null){
			for(Object[] transaction : transactions){
				InventoryDocSequence inventoryDocSequence = new InventoryDocSequence();
				inventoryDocSequence.setDocNo(String.valueOf(transaction[0]));
				inventoryDocSequence.setId(Integer.valueOf((String) transaction[1]));
				inventoryDocSequence.setName(String.valueOf(transaction[2]));
				inventoryDocSequence.setRefOrgId(Integer.valueOf((String) transaction[3]));
				inventoryDocSequenceList.add(inventoryDocSequence);
			}
		}
		return inventoryDocSequenceList;
	}*/
	
	@Transactional
	public Product saveOrUpdateProductDetails(Product product) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(product);
		logger.info("Exit");
		return product;
	}
	
	@Transactional
	public InventoryDetails saveOrUpdateInventoryDetails(List<InventoryDetails> inventoryDetails) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(inventoryDetails);
		logger.info("Exit");
		return (InventoryDetails) inventoryDetails;
	}
	
	public Product insertProductDetails(Product product) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		session.save(product);
		logger.info("Exit");
		return product;
	}


	public List<InventoryProductSave> updateProductDetails(Integer storeId,
			Integer productId) throws Exception {
		logger.info("Entry");
		InventoryProductTable inventoryProductTable = null;
		Session session = sessionFactory.getCurrentSession();
		// Query query =
		// session.createSQLQuery(updateProductDetailsByStoreIdandProductId);

		Query query = session
				.createQuery(updateProductDetailsByStoreIdandProductId);
		query.setDouble("currentStockValue",
				inventoryProductTable.getCurrentStockValue());
		query.setDouble("currentStock",
				inventoryProductTable.getCurrentStock());

		// cndnQuery.executeUpdate();

		@SuppressWarnings("unchecked")
		List<InventoryProductSave> inventoryProductSaveList = query.list();
		if (!inventoryProductSaveList.isEmpty()) {
			return inventoryProductSaveList;
		} else {
			logger.info("product not found for this store");
		}
		logger.info("Exit");
		return inventoryProductSaveList;
	}

	@Override
	public Product getProductDetails(Integer storeId, Integer productId)
			throws Exception {
		return null;
	}
	
	@Transactional
	@Override
	public List<Object[]> getInventoryHistory(){
		logger.info("Entry");
		List<Object[]> inventoryHistory  = new ArrayList<Object[]>();  
		Session session = sessionFactory.getCurrentSession();
		try{
		Query query = session.createSQLQuery(fetchHistoryOfInventory);
		@SuppressWarnings("unchecked")
		List<Object[]> list =(List<Object[]>)query.list();
		inventoryHistory.addAll(list);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
	    logger.info("Exit");
	    
		return inventoryHistory;
	}
	
}
