package com.reliance.gstn.service.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.reliance.gstn.dao.InventoryDao;
import com.reliance.gstn.model.InventoryDetails;
import com.reliance.gstn.model.InventoryProductSave;
import com.reliance.gstn.model.InventoryProductTable;
import com.reliance.gstn.model.OpeningStockJSObjectSave;
import com.reliance.gstn.model.OpeningStockJsObject;
import com.reliance.gstn.model.Product;
import com.reliance.gstn.service.GenerateUniqueSequnceService;
import com.reliance.gstn.service.InventoryService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.WebserviceCallUtil;
import org.springframework.transaction.annotation.Transactional;
/*
 * Author @kshay Mohite
 */

@Service
public class InventoryServiceImpl implements InventoryService {

	private static final Logger logger = Logger.getLogger(InventoryServiceImpl.class);

	@Value("${client_id}")
	private String clientid;

	@Value("${secret_key}")
	private String secretkey;

	@Value("${app_code}")
	private String appcode;

	@Value("${ip_usr}")
	private String ipaddress;

	@Value("${${env}.gstinDetailsByUid.url}")
	private String gstinDetailsByUidUrl;

	@Value("${${env}.locationDetailsByGstnId.url}")
	private String locationDetailsByGstnId;

	@Value("${${env}.showInventoryProductByGstnIdNlocationId.url}")
	private String showInventoryProductByGstnIdNlocationId;

	@Value("${${env}.getGstinDetailsMappedForSecondaryUserByUid.url}")
	private String getGstinDetailsMappedForSecondaryUserByUid;

	@Value("${${env}.saveOpeningStocks.url}")
	private String saveOpeningStocksOrProductDetails;

	@Value("${${env}.saveInventoryData.url}")
	private String saveInventoryData;

	@Value("${${env}.getListOfProductByProductIds.url}")
	private String getListOfProductByProductIds;

	@Value("${PLEASE_SELECT_ATLEAST_1_PRODUCT}")
	private String selectValidation;

	@Value("${SERVICE_UNAVAILABLE}")
	private String serviceUnavailable;

	@Value("${${env}.getopeningstock.url}")
	private String getOpeningStock;

	@Value("${${env}.getProductsNameByStoreId.url}")
	private String getProductsNameByStoreId;

	@Value("${${env}.getProductNInventoryDetailByProductIdAndStoreId.url}")
	private String getProductNInventoryDetailByProductIdAndStoreId;
	
	@Value("${${env}.getGoodsByStoreIdAndcurrentDate.url}")
	private String getGoodsByStoreIdAndcurrentDate;

	@Autowired
	private InventoryDao inventoryDao;

	@Autowired
	GenerateUniqueSequnceService generateUniqueSequnceService;

	@Override
	public Map<String, Object> getGSTINListwithms(Integer uId) {
		logger.info("Entry:");

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("referenceId", uId);
		String body = new Gson().toJson(map);
		logger.info("Body : " + body);

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = webServiceCall(gstinDetailsByUidUrl, body);
		if (responseMap == null) {
			responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_503,serviceUnavailable, GSTNConstants.FAILURE);
		}
		logger.info("Exit");
		return responseMap;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> webServiceCall(String apiUrl, String body) {
		logger.info("Entry:");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, String> headersMap = createApiHeader();
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");

		String response = WebserviceCallUtil.callWebservice(apiUrl, headersMap,body, extraParams);
		logger.info("Payload Response :: " + response);

		Gson gson = new Gson();
		responseMap = (Map<String, Object>) gson.fromJson(response.toString(),responseMap.getClass());

		logger.info("Exit");
		return responseMap;
	}

	public Map<String, String> createApiHeader() {
		Map<String, String> headersMap = new HashMap<>();
		headersMap.put("client-id", clientid);
		headersMap.put("secret-key", secretkey);
		headersMap.put("Content-Type", "application/json");
		headersMap.put("app-code", appcode);
		headersMap.put("ip-address", ipaddress);
		return headersMap;
	}

	@Override
	public Map<String, Object> getGstinDetailsMappedForSecondaryUserByUid(Integer uId) {
		logger.info("Entry:");

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("referenceId", uId);
		String body = new Gson().toJson(map);
		logger.info("Body : " + body);

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = webServiceCall(getGstinDetailsMappedForSecondaryUserByUid, body);
		if (responseMap == null) {
			responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_503,serviceUnavailable, GSTNConstants.FAILURE);
		}
		logger.info("Exit");
		return responseMap;
	}

	@Override
	public Map<String, Object> getLocationByGstinMicroService(Integer gstinId) {
		logger.info("Entry:");

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("refGstinId", gstinId);
		String body = new Gson().toJson(map);
		logger.info("Body : " + body);

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = webServiceCall(locationDetailsByGstnId, body);
		if (responseMap == null) {
			responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_503,serviceUnavailable, GSTNConstants.FAILURE);
		}
		logger.info("Exit");
		return responseMap;
	}

	@Override
	public Map<String, Object> getInventoryProductByGstinIdNLocationIdMicroService(
			Integer locationId) {
		logger.info("Entry:");

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("storeId", locationId);
		String body = new Gson().toJson(map);
		logger.info("Body : " + body);

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = webServiceCall(showInventoryProductByGstnIdNlocationId,body);
		if (responseMap == null) {
			responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_503,serviceUnavailable, GSTNConstants.FAILURE);
		}
		logger.info("Exit");
		return responseMap;
	}

	@Override
	public Map<String, Object> saveInventoryDetails(InventoryProductSave inventoryProductSave, Integer getuId) throws Exception {
		logger.info("Entry:");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String transactionType = null;

		if (inventoryProductSave.getInventoryType().equals(GSTNConstants.INVENTORY_TYPE_INCREASE)|| inventoryProductSave.getInventoryType().equals(GSTNConstants.INVENTORY_TYPE_DECREASE)) {
			transactionType = inventoryProductSave.getInventoryType().equals(GSTNConstants.INVENTORY_TYPE_INCREASE) ? GSTNConstants.CREDIT : GSTNConstants.DEBIT;
		} else {
			transactionType = inventoryProductSave.getInventoryType();
		}

		responseMap = saveProductNInventoryDetails(inventoryProductSave,getuId, transactionType);

		logger.info("Exit");
		return responseMap;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> saveProductNInventoryDetails(InventoryProductSave inventoryProductSave, Integer getuId,String transactionType) throws Exception {
		logger.info("Entry:");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<InventoryDetails> inventoryList = new ArrayList<InventoryDetails>();
		Integer storeId = null;
		String inventoryType = inventoryProductSave.getInventoryType();
		DateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
		if (inventoryProductSave.getProductList() != null && !inventoryProductSave.getProductList().isEmpty()) {
			if (inventoryProductSave.getProductList().get(0).getStoreId() != null) {
				storeId = inventoryProductSave.getProductList().get(0).getStoreId();
			}

			String redirectedFrom = null;

			if (inventoryProductSave.getInventoryType().equals(GSTNConstants.INCREASE)) {
				redirectedFrom = "Increase Inventory";
			} else if (inventoryProductSave.getInventoryType().equals(GSTNConstants.DECREASE)) {
				redirectedFrom = "Decrease Inventory";
			} else {
				String ttType = transactionType.substring(transactionType.lastIndexOf("-") + 1);
				transactionType = ttType;
				redirectedFrom = inventoryProductSave.getInventoryType();
			}

			Map<String, Object> finalResponseMapSaveProduct = new HashMap<>();
			if (storeId != null) {
				List<Product> saveProductList = new ArrayList<Product>();
				Map<String, Object> responseMapProduct = getInventoryProductByGstinIdNLocationIdMicroService(storeId);
				if (responseMapProduct.get("status").equals(GSTNConstants.SUCCESS_LOWER_CASE)) {
					List<Map<String, String>> resultMap = (List<Map<String, String>>) responseMapProduct.get("result");

					Gson gson = new Gson();
					List<Product> responseListProduct = new ArrayList<Product>();
					for (Map<String, String> map : resultMap) {
						Product product = gson.fromJson(gson.toJson(map),Product.class);
						responseListProduct.add(product);
					}

					if (responseListProduct != null && !responseListProduct.isEmpty()) {
						for (Product responseProduct : responseListProduct){ // api response list
							for (InventoryProductTable invproductTableList : inventoryProductSave.getProductList()){ // user updated list
								if (responseProduct.getId().equals(invproductTableList.getId())) {
									if (inventoryType.equals(GSTNConstants.INVENTORY_TYPE_INCREASE)|| inventoryType.equals(GSTNConstants.INVENTORY_TYPE_DECREASE)) {
										Double dbActualQty = responseProduct.getCurrentStock();
										Double dbActualStockValue = Double.valueOf(new DecimalFormat("#.##").format(responseProduct.getCurrentStockValue()));
										Double uiNewCalculatedQty = invproductTableList.getCurrentStock();
										Double uiNewCalculatedStockValue = Double.valueOf(new DecimalFormat("#.##").format(invproductTableList.getCurrentStockValue()));
										Double uiModifiedQty = invproductTableList.getModifiedQty();
										Double uiModifiedStockValue = Double.valueOf(new DecimalFormat("#.##").format(invproductTableList.getModifiedStockValue()));
										if (transactionType.equals(GSTNConstants.CREDIT)) {
											if (uiNewCalculatedQty != (dbActualQty + uiModifiedQty)) {
												responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_409,"Data has been manipulated",GSTNConstants.NOT_ALLOWED_ACCESS);
												break;
											}
											if (uiNewCalculatedStockValue != (dbActualStockValue + uiModifiedStockValue)) {
												responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_409,"Data has been manipulated",GSTNConstants.NOT_ALLOWED_ACCESS);
												break;
											}
										} else {
											if (uiNewCalculatedQty != (dbActualQty - uiModifiedQty)) {
												responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_409,"Data has been manipulated",GSTNConstants.NOT_ALLOWED_ACCESS);
												break;
											}
											if (uiNewCalculatedStockValue != (dbActualStockValue - uiModifiedStockValue)) {
												responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_409,"Data has been manipulated",GSTNConstants.NOT_ALLOWED_ACCESS);
												break;
											}
										}
									}

									responseProduct.setCurrentStock(invproductTableList.getCurrentStock());
									responseProduct.setCurrentStockValue(invproductTableList.getCurrentStockValue());
									responseProduct.setInventoryUpdateFlag(GSTNConstants.Y);
									responseProduct.setUpdatedBy(String.valueOf(getuId));
									responseProduct.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
									
									saveProductList.add(responseProduct);
									
									//for (InventoryProductTable invproductTable : inventoryProductSave.getProductList()) {
										InventoryDetails inventoryobj = new InventoryDetails();
										inventoryobj.setProductId(invproductTableList.getId());
										//inventoryobj.setCreditDebitValues(Double.valueOf(String.valueOf(invproductTable.getModifiedStockValue())));
										inventoryobj.setCreditDebitValues(responseProduct.getCurrentStockValue());
										inventoryobj.setUpdateType(transactionType);
										inventoryobj.setNarration(inventoryProductSave.getNarration());
										inventoryobj.setSelectedReason(inventoryProductSave.getReason());
										inventoryobj.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
										inventoryobj.setQuantity(invproductTableList.getModifiedQty());				
										inventoryobj.setTransactionDate(GSTNUtil.convertStringInddMMyyyyToTimestamp(invproductTableList.getTransactionDate()));
										inventoryobj.setActionFrom(redirectedFrom);
										inventoryList.add(inventoryobj);
									//}
								}	
							}
						}
						
						String pattern = f.format(new java.sql.Timestamp(new Date().getTime()));
						Integer id = inventoryProductSave.getUniqueSequenceid();
						for (int i = 0; i < inventoryList.size(); i++) {
							++id;
							String sequeneceNumber = generateUniqueSequnceService.getDocSequenceSession(id);
							InventoryDetails item = (InventoryDetails) inventoryList.get(i);
							item.setDocumentNo(pattern + sequeneceNumber);
						}
						
						if (!saveProductList.isEmpty()) {
							finalResponseMapSaveProduct = saveProductListCall(saveProductList);

							if (!finalResponseMapSaveProduct.isEmpty() && finalResponseMapSaveProduct.get("status").equals(GSTNConstants.SUCCESS_LOWER_CASE)) {
								if (!inventoryList.isEmpty() && inventoryList.size() > 0) {
									Map<String, Object> finalResponseMapSaveInventory = saveInventoryListCall(inventoryList);
									responseMap = finalResponseMapSaveInventory;
									responseMap.remove("uniqueSequenceid"); 
								}
							} else {
								responseMap = finalResponseMapSaveProduct;
							}
						}
					}
				} else {
					responseMap = responseMapProduct;
				}
			} else {
				responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_500, selectValidation,GSTNConstants.FAILURE);
			}
		} else {
			responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_500,selectValidation, GSTNConstants.FAILURE);
		}
		logger.info("Exit");
		return responseMap;
	}

	@Override
	public Map<String, Object> getListOfProductByProductIdList(List<Product> productList) {
		logger.info("Entry:");

		String body = new Gson().toJson(productList);
		logger.info("Body : " + body);

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = webServiceCall(getListOfProductByProductIds, body);
		if (responseMap == null) {
			responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_503,serviceUnavailable, GSTNConstants.FAILURE);
		}
		logger.info("Exit");
		return responseMap;
	}

	private Map<String, Object> saveProductListCall(List<Product> productList) {
		logger.info("Entry:");

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
		String body = gson.toJson(productList);
		logger.info("Body : " + body);

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = webServiceCall(saveOpeningStocksOrProductDetails, body);
		if (responseMap == null) {
			responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_503,serviceUnavailable, GSTNConstants.FAILURE);
		}
		logger.info("Exit");
		return responseMap;
	}

	private Map<String, Object> saveInventoryListCall(List<InventoryDetails> inventoryDetailList) {
		logger.info("Entry:");

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
		String body = gson.toJson(inventoryDetailList);
		logger.info("Body : " + body);

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = webServiceCall(saveInventoryData, body);
		if (responseMap == null) {
			responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_503,serviceUnavailable, GSTNConstants.FAILURE);
		}
		logger.info("Exit");
		return responseMap;
	}

	@Override
	public Map<String, Object> getOpeningStockProductList(Integer getuId,Integer locationId) {
		logger.info("Entry:");
		Map<String, String> headersMap = createApiHeader();
		Map<Object, Object> map = new HashMap<Object, Object>();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		logger.info("GSTR1 Header : " + headersMap);

		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");

		map.put("storeId", locationId);
		String body = new Gson().toJson(map);
		logger.info("Body : " + body);
		responseMap = webServiceCall(getOpeningStock, body);
		logger.info("Payload Response :: " + responseMap);
		if (responseMap == null) {
			responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_503,serviceUnavailable, GSTNConstants.FAILURE);
		}
		logger.info("Exit");

		return responseMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> saveOpeningstockProductList(OpeningStockJSObjectSave openingStockJSObjectSave, Integer uId) {
		logger.info("Entry:");

		Map<String, Object> finalResponseMapSaveProduct = new HashMap<>();
		if (!openingStockJSObjectSave.getProductList().isEmpty()) {
			List<Product> productIdList = new ArrayList<Product>();

			for (OpeningStockJsObject OpeningStockjsTable : openingStockJSObjectSave.getProductList()) {
				Product prodObj = new Product();
				prodObj.setId(OpeningStockjsTable.getId());
				productIdList.add(prodObj);
			}

			if (!productIdList.isEmpty()) {
				List<Product> saveProductList = new ArrayList<Product>();
				Map<String, Object> responseMapProduct = getListOfProductByProductIdList(productIdList);
				if (responseMapProduct.get("status").equals(GSTNConstants.SUCCESS_LOWER_CASE)) {
					List<Map<String, String>> responseMap1 = (List<Map<String, String>>) responseMapProduct.get("result");

					Gson gson = new Gson();
					List<Product> responseListProduct = new ArrayList<Product>();
					for (Map<String, String> map : responseMap1) {
						Product product = gson.fromJson(gson.toJson(map),Product.class);
						responseListProduct.add(product);
					}

					for (Product responseProduct : responseListProduct){ // api response list
						for(OpeningStockJsObject OpeningStockjsTable : openingStockJSObjectSave.getProductList()){ // user updated list
							if (responseProduct.getId().equals(OpeningStockjsTable.getId())&& responseProduct.getStoreId().equals(OpeningStockjsTable.getStoreId())) {
								responseProduct.setUnitOfMeasurement(OpeningStockjsTable.getUnitOfMeasurement());
								responseProduct.setId(OpeningStockjsTable.getId());
								responseProduct.setName(OpeningStockjsTable.getName());
								responseProduct.setCurrentStock(OpeningStockjsTable.getCurrentStock());
								responseProduct.setCurrentStockValue(OpeningStockjsTable.getCurrentStockValue());
								responseProduct.setOpeningStock(OpeningStockjsTable.getOpeningStock());
								responseProduct.setOpeningStockValue(OpeningStockjsTable.getOpeningStockValue());
								responseProduct.setOtherUOM(OpeningStockjsTable.getOtherUOM());
								responseProduct.setUpdatedBy(String.valueOf(uId));
								responseProduct.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
								saveProductList.add(responseProduct);
							}
						}
					}

					if (!saveProductList.isEmpty() && saveProductList.size() > 0) {
						finalResponseMapSaveProduct = saveProductListCall(saveProductList);
						logger.info("Body : " + finalResponseMapSaveProduct);
					}
				}
			}
		}

		logger.info("Exit");
		return finalResponseMapSaveProduct;
	}

	@Override
	public Map<String, Object> getProductNameListByStoreId(Integer locationId) {
		logger.info("Entry:");
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("storeId", locationId);
		String body = new Gson().toJson(map);
		logger.info("Body : " + body);

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = webServiceCall(getProductsNameByStoreId, body);
		if (responseMap == null) {
			responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_503,serviceUnavailable, GSTNConstants.FAILURE);
		}
		logger.info("Exit");
		return responseMap;
	}

	@Override
	public Map<String, Object> generateStockStatusDetailedReport(Integer locationId, Integer productId) {
		logger.info("Entry:");
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("id", productId);
		map.put("storeId", locationId);
		String body = new Gson().toJson(map);
		logger.info("Body : " + body);

		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap = webServiceCall(getProductNInventoryDetailByProductIdAndStoreId, body);
		if (responseMap == null) {
			responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_503,serviceUnavailable, GSTNConstants.FAILURE);
		}
		logger.info("Exit");
		return responseMap;
	}

	@Override
	public Map<String, Object> saveProductNInventoryDetailsStores(InventoryProductSave inventoryProductSave, Integer orguId) {
		logger.info("Entry:");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		try {
			responseMap = saveProductNInventoryDetails(inventoryProductSave,orguId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Exit");
		return responseMap;
	}

	private Map<String, Object> saveProductNInventoryDetails(InventoryProductSave inventoryProductSave, Integer orguId)throws Exception {
		logger.info("Entry:");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<InventoryDetails> inventoryList = new ArrayList<InventoryDetails>();
		Integer fromStoreId = null;
		Integer refOrgId = orguId;
		String name = "";
		Integer toStoreId = null;
		Double modifiedStockValue = null;
		Product productUpdtaed = null;
		Product productUpdtaedToStore = null;
		Double currentStock = null;
		Double currentStockValue = null;
		// int productListSize = inventoryProductSave.getProductList().size();
		int productListSize = 1;
		DateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
		List<String> docSequenceListfromStore = null;
		List<String> docSequenceListtoStore = null;
		List<InventoryDetails> inventoryListFromStore = new ArrayList<InventoryDetails>();
		List<InventoryDetails> inventoryListToStore = new ArrayList<InventoryDetails>();

		if (inventoryProductSave.getProductList() != null && !inventoryProductSave.getProductList().isEmpty()) {
			for(InventoryProductTable invproductTable : inventoryProductSave.getProductList()) {
				fromStoreId = invproductTable.getFromStoreId();
				toStoreId = invproductTable.getToStoreId();
				name = invproductTable.getName();
				modifiedStockValue = invproductTable.getModifiedStockValue();
				currentStock = invproductTable.getCurrentStock();
				currentStockValue = invproductTable.getCurrentStockValue();
				Product fromStoreProductObject = inventoryDao.getProductDetails(fromStoreId, refOrgId, name);

				if (fromStoreProductObject != null) {
					  double fromStorecurrentStockQty = fromStoreProductObject.getCurrentStock();
					  if (fromStorecurrentStockQty >=invproductTable.getModifiedQty()) { 
					  Double CurrentStockValue = fromStoreProductObject.getCurrentStockValue();
					  fromStorecurrentStockQty = fromStorecurrentStockQty -invproductTable.getModifiedQty();
					 
						if (fromStorecurrentStockQty <= 0) {
							fromStoreProductObject.setCurrentStockValue(0.00);
							fromStoreProductObject.setCurrentStock(fromStorecurrentStockQty);
							fromStoreProductObject.setPurchaseRate(fromStoreProductObject.getPurchaseRate());
							fromStoreProductObject.setProductRate(fromStoreProductObject.getProductRate());
							fromStoreProductObject.setProductIgst(fromStoreProductObject.getProductIgst());
							fromStoreProductObject.setUnitOfMeasurement(fromStoreProductObject.getUnitOfMeasurement());
						} else {
							Double totalStockValue = CurrentStockValue - modifiedStockValue;
							fromStoreProductObject.setCurrentStockValue(totalStockValue);
							fromStoreProductObject.setCurrentStock(fromStorecurrentStockQty);
							fromStoreProductObject.setProductRate(fromStoreProductObject.getPurchaseRate());
							fromStoreProductObject.setProductRate(fromStoreProductObject.getProductRate());
							fromStoreProductObject.setProductRate(fromStoreProductObject.getProductIgst());
							fromStoreProductObject.setUnitOfMeasurement(fromStoreProductObject.getUnitOfMeasurement());
							fromStoreProductObject.setInventoryUpdateFlag("Y");
						}

						productUpdtaed = inventoryDao.saveOrUpdateProductDetails(fromStoreProductObject);

						Product productObject = inventoryDao.getProductDetails(toStoreId, refOrgId, name);
						if (productObject != null) {
							double currentStockQty = productObject.getCurrentStock() != null ? productObject.getCurrentStock() : 0;
							Double toCurrentStockValue = productObject.getCurrentStockValue();

							productObject.setCurrentStockValue(toCurrentStockValue + modifiedStockValue);
							currentStockQty = currentStockQty + invproductTable.getModifiedQty();
							productObject.setCurrentStock(currentStockQty);
							productObject.setProductIgst(productObject.getProductIgst());
							productObject.setProductRate(productObject.getProductRate());
							productObject.setPurchaseRate(productObject.getPurchaseRate());
							productObject.setUnitOfMeasurement(productObject.getUnitOfMeasurement());
							productObject.setInventoryUpdateFlag("Y");

							productUpdtaedToStore = inventoryDao.saveOrUpdateProductDetails(productObject);
							responseMap.put(GSTNConstants.MESSAGE,GSTNConstants.STOCK_MOVEMENT_SUCCESS);

						} else {
							Product product = new Product();
							product.setName(fromStoreProductObject.getName());
							product.setUnitOfMeasurement(fromStoreProductObject.getUnitOfMeasurement());
							product.setProductRate(fromStoreProductObject.getProductRate());
							product.setHsnCode(fromStoreProductObject.getHsnCode());
							product.setHsnDescription(fromStoreProductObject.getHsnDescription());
							product.setProductIgst(fromStoreProductObject.getProductIgst());
							product.setPurchaseRate(fromStoreProductObject.getPurchaseRate());
							product.setAdvolCess(fromStoreProductObject.getAdvolCess());
							product.setNonAdvolCess(fromStoreProductObject.getAdvolCess());
							product.setStatus(fromStoreProductObject.getStatus());
							product.setReferenceId(fromStoreProductObject.getReferenceId());
							product.setRefOrgId(refOrgId);
							product.setStoreId(toStoreId);
							product.setOpeningStock(0.00);
							product.setOpeningStockValue(0.00);
							product.setCurrentStock(invproductTable.getModifiedQty());
							product.setCurrentStockValue(modifiedStockValue);
							product.setInventoryUpdateFlag("Y");

							productUpdtaedToStore = inventoryDao.saveOrUpdateProductDetails(product);

							responseMap.put(GSTNConstants.MESSAGE,GSTNConstants.STOCK_MOVEMENT_SUCCESS);
						}
					}

				}

				InventoryDetails inventoryobj = new InventoryDetails();
				inventoryobj.setProductId(productUpdtaed.getId());
				inventoryobj.setCreditDebitValues(productUpdtaed.getCurrentStockValue());
				inventoryobj.setUpdateType("DEBIT");
				inventoryobj.setActionFrom("Movement Between Stores");
				inventoryobj.setSelectedReason("Movement Between Stores");
				inventoryobj.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
				inventoryobj.setQuantity(invproductTable.getModifiedQty());
				inventoryobj.setTransactionDate(GSTNUtil.convertStringInddMMyyyyToTimestamp(invproductTable.getTransactionDate()));
				inventoryList.add(inventoryobj);

				InventoryDetails inventoryobjToStore = new InventoryDetails();
				inventoryobjToStore.setProductId(productUpdtaedToStore.getId());
				inventoryobjToStore.setCreditDebitValues(productUpdtaedToStore.getCurrentStockValue());
				inventoryobjToStore.setUpdateType("CREDIT");
				inventoryobjToStore.setActionFrom("Movement Between Stores");
				inventoryobjToStore.setSelectedReason("Movement Between Stores");
				inventoryobjToStore.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
				inventoryobjToStore.setQuantity(invproductTable.getModifiedQty());
				inventoryobjToStore.setTransactionDate(GSTNUtil.convertStringInddMMyyyyToTimestamp(invproductTable.getTransactionDate()));
				inventoryList.add(inventoryobjToStore);
			}
			String pattern = f.format(new java.sql.Timestamp(new Date().getTime()));
			Integer id = inventoryProductSave.getUniqueSequenceid();
			for (int i = 0; i < inventoryList.size(); i++) {
				++id;
				String sequeneceNumber = generateUniqueSequnceService.getDocSequenceSession(id);
				InventoryDetails item = (InventoryDetails) inventoryList.get(i);
				item.setDocumentNo(pattern + sequeneceNumber);
			}
			Map<String, Object> finalResponseMapSave = saveInventoryListCall(inventoryList);
			responseMap.put("uniqueSequenceid", id);
			responseMap.remove("uniqueSequenceid");
		} else
			responseMap.put(GSTNConstants.MESSAGE,GSTNConstants.STOCK_MOVEMENT_FAILURE);
		    return responseMap;
	}

	public Map<String, String> getReasonsForIncrease() {
		Map<String, String> reasonMap = new HashMap<>();
		reasonMap.put("excessstock", "Excess Stock");
		reasonMap.put("receivedassample", "Received as Sample");
		reasonMap.put("openingstockadjustment", "Opening stock adjustment");
		reasonMap.put("others", "Others");
		return reasonMap;
	}

	public Map<String, String> getReasonsForDecrease() {
		Map<String, String> reasonMap = new HashMap<>();
		reasonMap.put("damaged", "Damaged");
		reasonMap.put("selfconsumption", "Self Consumption");
		reasonMap.put("givenassample", "Given as Sample");
		reasonMap.put("openingstockadjustment", "Opening stock adjustment");
		reasonMap.put("others", "Others");
		return reasonMap;
	}

	@Override
	public Map<String, Object> saveInventoryDetailsFromIncreDecreInventry(InventoryProductSave inventoryProductSave, Integer getuId)throws Exception {
		logger.info("Entry:");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String transactionType = null;

		if (inventoryProductSave.getInventoryType().equals(GSTNConstants.INVENTORY_TYPE_INCREASE)|| inventoryProductSave.getInventoryType().equals(GSTNConstants.INVENTORY_TYPE_DECREASE)) {
			transactionType = inventoryProductSave.getInventoryType().equals(GSTNConstants.INVENTORY_TYPE_INCREASE) ? GSTNConstants.CREDIT : GSTNConstants.DEBIT;
		} else {
			transactionType = inventoryProductSave.getInventoryType();
		}

		responseMap = saveProductNInventoryDetailsFromIncreDecreInventory(inventoryProductSave, getuId, transactionType);

		logger.info("Exit");
		return responseMap;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private Map<String, Object> saveProductNInventoryDetailsFromIncreDecreInventory(InventoryProductSave inventoryProductSave, Integer getuId,String transactionType) throws Exception {

		logger.info("Entry:");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<InventoryDetails> inventoryList = new ArrayList<InventoryDetails>();
		Integer storeId = null;
		int productListSize = 1;
		DateFormat f = new SimpleDateFormat("yyyyMMddHHmm");
		List<String> docSequenceList = null;
		String inventoryType = inventoryProductSave.getInventoryType();
		if (inventoryProductSave.getProductList() != null && !inventoryProductSave.getProductList().isEmpty()) {
			if (inventoryProductSave.getProductList().get(0).getStoreId() != null) {
				storeId = inventoryProductSave.getProductList().get(0).getStoreId();
			}

			String redirectedFrom = null;

			if (inventoryProductSave.getInventoryType().equals(GSTNConstants.INCREASE)) {redirectedFrom = "Increase Inventory";
			} else if (inventoryProductSave.getInventoryType().equals(GSTNConstants.DECREASE)) {
				redirectedFrom = "Decrease Inventory";
			} else {
				String ttType = transactionType.substring(transactionType.lastIndexOf("-") + 1);
				transactionType = ttType;
				redirectedFrom = inventoryProductSave.getInventoryType();
			}

			Map<String, Object> finalResponseMapSaveProduct = new HashMap<>();
			if (storeId != null) {
				List<Product> saveProductList = new ArrayList<Product>();
				Map<String, Object> responseMapProduct = getInventoryProductByGstinIdNLocationIdMicroService(storeId);
				if (responseMapProduct.get("status").equals(GSTNConstants.SUCCESS_LOWER_CASE)) {
					List<Map<String, String>> resultMap = (List<Map<String, String>>) responseMapProduct.get("result");

					Gson gson = new Gson();
					List<Product> responseListProduct = new ArrayList<Product>();
					for (Map<String, String> map : resultMap) {
						Product product = gson.fromJson(gson.toJson(map),Product.class);
						responseListProduct.add(product);
					}

					if (responseListProduct != null && !responseListProduct.isEmpty()) {
						for (Product responseProduct : responseListProduct){ // api response list
							for (InventoryProductTable invproductTableList : inventoryProductSave.getProductList()){ // user updated list
								if (responseProduct.getId().equals(invproductTableList.getId())) {
									if (inventoryType.equals(GSTNConstants.INVENTORY_TYPE_INCREASE) || inventoryType.equals(GSTNConstants.INVENTORY_TYPE_DECREASE)) {
										Double dbActualQty = responseProduct.getCurrentStock();
										Double dbActualStockValue = Double.valueOf(new DecimalFormat("#.##").format(responseProduct.getCurrentStockValue()));

										Double uiNewCalculatedQty = invproductTableList.getCurrentStock();
										Double uiNewCalculatedStockValue = Double.valueOf(new DecimalFormat("#.##").format(invproductTableList.getCurrentStockValue()));
										Double uiModifiedQty = invproductTableList.getModifiedQty();
										Double uiModifiedStockValue = Double.valueOf(new DecimalFormat("#.##").format(invproductTableList.getModifiedStockValue()));

										if (transactionType.equals(GSTNConstants.CREDIT)) {
											responseProduct.setCurrentStock(dbActualQty+ uiModifiedQty); //NEW CODE FOR CREDIT/DEBIT THE CURRENT STOCK FROM PRODUCT MASTER
											responseProduct.setCurrentStockValue(dbActualStockValue+ uiModifiedStockValue);

										} else {
											responseProduct.setCurrentStock(dbActualQty - uiModifiedQty);
											responseProduct.setCurrentStockValue(dbActualStockValue- uiModifiedStockValue);
										}
									}
									responseProduct.setInventoryUpdateFlag(GSTNConstants.Y);
									responseProduct.setUpdatedBy(String.valueOf(getuId));
									responseProduct.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
									saveProductList.add(responseProduct);
									
									//for (InventoryProductTable invproductTable : inventoryProductSave.getProductList()) {
										InventoryDetails inventoryobj = new InventoryDetails();
										inventoryobj.setProductId(invproductTableList.getId());
										//inventoryobj.setCreditDebitValues(Double.valueOf(String.valueOf(invproductTable.getModifiedStockValue())));
										inventoryobj.setCreditDebitValues(responseProduct.getCurrentStockValue());
										inventoryobj.setUpdateType(transactionType);
										inventoryobj.setNarration(inventoryProductSave.getNarration());
										inventoryobj.setSelectedReason(inventoryProductSave.getReason());
										inventoryobj.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
										inventoryobj.setQuantity(invproductTableList.getModifiedQty());				
										inventoryobj.setTransactionDate(GSTNUtil.convertStringInddMMyyyyToTimestamp(invproductTableList.getTransactionDate()));
										inventoryobj.setActionFrom(redirectedFrom);
										inventoryList.add(inventoryobj);
									//}
										String pattern = f.format(new java.sql.Timestamp(new Date().getTime()));
										Integer id = inventoryProductSave.getUniqueSequenceid();
										for (int i = 0; i < inventoryList.size(); i++) {
											++id;
											String sequeneceNumber = generateUniqueSequnceService.getDocSequenceSession(id);
											InventoryDetails item = (InventoryDetails) inventoryList.get(i);
											item.setDocumentNo(pattern + sequeneceNumber);
										}
								}
							}
						}
						if (!saveProductList.isEmpty()) {
							finalResponseMapSaveProduct = saveProductListCall(saveProductList);

							if (!finalResponseMapSaveProduct.isEmpty() && finalResponseMapSaveProduct.get("status").equals(GSTNConstants.SUCCESS_LOWER_CASE)) {
								if (!inventoryList.isEmpty() && inventoryList.size() > 0) {
									Map<String, Object> finalResponseMapSaveInventory = saveInventoryListCall(inventoryList);
									responseMap = finalResponseMapSaveInventory;
									responseMap.remove("uniqueSequenceid");	
								}
							} else {
								responseMap = finalResponseMapSaveProduct;
							}
						}
					}
				} else {
					responseMap = responseMapProduct;
				}
			} else {
				responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_500, selectValidation,GSTNConstants.FAILURE);
			}
		} else {
			responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_500,selectValidation, GSTNConstants.FAILURE);
		}
		logger.info("Exit");
		return responseMap;
	}

	
	@Override
	public Map<String, Object> getGoodsByStoreIdAndcurrentDate(Integer gstinNo,Integer storeId,String tDate) {
		logger.info("Entry:");
		
		String toDate= GSTNUtil.convertStringDateInddMMyyyyToFormatyyyyMMdd(tDate);
		toDate = toDate.concat(GSTNConstants.END_TIME_FORMAT);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("gstinNo", gstinNo);
		map.put("storeId", storeId);
		map.put("tdate", toDate);
		
		String body = new Gson().toJson(map);
		logger.info("Body : " + body);
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap = webServiceCall(getGoodsByStoreIdAndcurrentDate,body);
		if (responseMap == null) {
			responseMap = GSTNUtil.showErrMsg(GSTNConstants.STATUS_CODE_503, serviceUnavailable, GSTNConstants.FAILURE);
		}
		logger.info("Exit");		
		return responseMap;
	}

	//@Transactional
	@Override
	public List<Object[]> getInventoryHistory() {
		
		return inventoryDao.getInventoryHistory();
	}

}
