package com.reliance.gstn.service;

import java.util.List;
import java.util.Map;

import com.reliance.gstn.model.InventoryProductSave;
import com.reliance.gstn.model.OpeningStockJSObjectSave;
import com.reliance.gstn.model.Product;

public interface InventoryService {

	Map<String, Object> getGSTINListwithms(Integer getuId);

	Map<String, Object> getOpeningStockProductList(Integer getuId,Integer locationId);
	Map<String, Object> getLocationByGstinMicroService(Integer gstinId);

	Map<String, Object> getInventoryProductByGstinIdNLocationIdMicroService(Integer locationId);

	Map<String, Object> getGstinDetailsMappedForSecondaryUserByUid(Integer getuId);

	Map<String, Object> saveInventoryDetails(InventoryProductSave inventoryProductSave, Integer getuId) throws Exception;

	Map<String, Object> getListOfProductByProductIdList(List<Product> productList);

	Map<String, Object> saveProductNInventoryDetailsStores(InventoryProductSave inventoryProductSave, Integer orguId);

	Map<String, Object> saveOpeningstockProductList(OpeningStockJSObjectSave openingStockJSObjectSave, Integer uId);

	Map<String, Object> getProductNameListByStoreId(Integer locationId);

	Map<String, Object> generateStockStatusDetailedReport(Integer locationId, Integer productId);

	Map<String, String> getReasonsForIncrease();

	Map<String, String> getReasonsForDecrease();

	Map<String, Object> saveInventoryDetailsFromIncreDecreInventry(InventoryProductSave inventoryProductSave, Integer getuId)throws Exception;

	Map<String, Object> getGoodsByStoreIdAndcurrentDate(Integer gstinNo,Integer storeId, String tDate);
	
	List<Object[]> getInventoryHistory();
}
