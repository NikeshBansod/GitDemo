package com.reliance.gstn.dao;

import java.util.List;

import com.reliance.gstn.model.InventoryDetails;
import com.reliance.gstn.model.InventoryDocSequence;
import com.reliance.gstn.model.InventoryProductSave;
import com.reliance.gstn.model.Product;

public interface InventoryDao {

	Product getProductDetails(Integer storeId,
			Integer productId) throws Exception;
	
	public Product saveOrUpdateProductDetails(Product product) throws Exception;
	
	List<InventoryProductSave> updateProductDetails(Integer storeId,
			Integer productId) throws Exception;
	
	Product getProductDetails(Integer storeId,
			Integer refOrgId,String name) throws Exception;
	
	public InventoryDetails saveOrUpdateInventoryDetails(List<InventoryDetails> inventoryDetails) throws Exception;

	public List<Object[]> getInventoryHistory();
	
}
