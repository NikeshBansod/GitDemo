package com.reliance.gstn.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.reliance.gstn.admin.model.HSNDetails;
import com.reliance.gstn.admin.model.ProductRateOfTaxDetails;
import com.reliance.gstn.admin.model.ServiceRateOfTaxDetails;
import com.reliance.gstn.admin.service.HSNService;
import com.reliance.gstn.admin.service.SACCodeService;
import com.reliance.gstn.dao.GSTINDetailsDAO;
import com.reliance.gstn.dao.WizardBulkUploadDAO;
import com.reliance.gstn.master.excel.helper.EntityValidator;
import com.reliance.gstn.master.excel.helper.ExcelDataEntityProvider;
import com.reliance.gstn.master.excel.helper.ExcelDataEntityProviderFactory;
import com.reliance.gstn.master.excel.helper.ExcelUploadHelper;
import com.reliance.gstn.master.excel.helper.ExcelUploadHelperFactory;
import com.reliance.gstn.model.CustomerDetails;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.GstinLocation;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.ManageServiceCatalogue;
import com.reliance.gstn.model.PinCode;
import com.reliance.gstn.model.Product;
import com.reliance.gstn.model.SACCode;
import com.reliance.gstn.model.State;
import com.reliance.gstn.model.UnitOfMeasurement;
import com.reliance.gstn.service.WizardBulkUploadService;
import com.reliance.gstn.util.ExcelUtil;

@Service
public class WizardBulkUploadServiceImpl implements WizardBulkUploadService {

	private static final Logger logger = Logger.getLogger(WizardBulkUploadServiceImpl.class);

	@Autowired
	WizardBulkUploadDAO wizardBulkUploadDAO;

	@Autowired
	public HSNService HSNService;

	@Autowired
	SACCodeService SACCodeService;

	@Autowired
	GSTINDetailsDAO gstinDetailsDAO;

	@Override
	@Transactional
	public String uploadMasterExcelData(String masterType, String fileName, MultipartFile file, LoginMaster loginMaster)
			throws Exception {
		logger.info("Entry");
		// need to write logic of insert or update
		String msg = "";
		// read Excel data
		ExcelUploadHelper excelUploadHelper = ExcelUploadHelperFactory.getExcelUploadHelper(masterType);
		List<Object[]> excelData = ExcelUtil.readExcel(masterType, excelUploadHelper, file);

		// create entity from excel data
		ExcelDataEntityProvider<?> excelDataEntityProvider = ExcelDataEntityProviderFactory
				.getExcelDataEntityProvider(masterType);
		List<?> entiyList = excelDataEntityProvider.getEntityList(excelData, loginMaster);

		List<Object> requiredObjectList = getRequiredDataToValidate(masterType); // retieve records of UOM, rate of tax
																					// and HSN/SAC master
		List<Object> entityList = setRemainingEntityData(entiyList, requiredObjectList);


		if (masterType.equalsIgnoreCase("goodsmastertemplate")
				|| masterType.equalsIgnoreCase("servicesmastertemplate"))
			entityList = validateStoreIds(entityList);


		
		// validate entity
		Set<Set<ConstraintViolation<Object>>> validationResult = EntityValidator.validateEntiy(entiyList);

		if (!validationResult.isEmpty()) {
			for (Set<ConstraintViolation<Object>> set : validationResult) {
				for (ConstraintViolation<Object> constraintViolation : set) {
					msg = "Business validation fail:";
					msg = msg + " for " + constraintViolation.getPropertyPath() + " value: "
							+ constraintViolation.getInvalidValue() + " " + constraintViolation.getMessage() + ",";
				}
			}
		} else {
			// upload data
			validateBusinessRules(entityList, requiredObjectList);
			msg = wizardBulkUploadDAO.uploadMasterExcelData(entiyList);
		}
		logger.info("Exit");
		return msg;
	}

	private List<Object> getRequiredDataToValidate(String masterType) throws Exception {
		List<Object> mainObjectList = new ArrayList<Object>();
		mainObjectList = wizardBulkUploadDAO.getRequiredData(masterType);
		return mainObjectList;
	}

	@SuppressWarnings("unchecked")
	private List<Object> validateStoreIds(List<?> entityList) throws Exception {
		Map<String, Integer> gstinStoreIds = new LinkedHashMap<>();
		List<String> gstinOrgList = new ArrayList<>();
		List<Object> list = new ArrayList<>();
		if (entityList.get(0) instanceof Product) {
			for (Product product : (List<Product>) entityList) {
				String gstinUserId = product.getGstin() + "#" + product.getReferenceId();
				if (!gstinOrgList.contains(gstinUserId)) {
					GSTINDetails GSTINDetails = gstinDetailsDAO.getGstinDetailsFromGstinNo(product.getGstin(),
							product.getReferenceId());
					if (GSTINDetails != null) {
						String key = null;
						for (GstinLocation gstinLocation : GSTINDetails.getGstinLocationSet()) {
							key = GSTINDetails.getGstinNo() + "#" + gstinLocation.getGstinLocation();
							if (!gstinStoreIds.containsKey(key))
								gstinStoreIds.put(key, gstinLocation.getId());
						}
					} else {
						throw new Exception("Invalid GSTIN :" + product.getGstin());
					}
				}
				String key = product.getGstin() + "#" + product.getStoreName();
				if (gstinStoreIds.containsKey(key)) {
					product.setStoreId(gstinStoreIds.get(key));
				} else
					throw new Exception(
							"Invalid Store :" + product.getStoreName() + " for GSTIN :" + product.getGstin());

				HSNDetails hsnDetails = HSNService.getIGSTValueByHsnCode(product.getHsnCode(),
						product.getHsnDescription());
				if (hsnDetails != null) {
					product.setHsnCodePkId(hsnDetails.getId());
				}
				list.add(product);

			}
		} else if (entityList.get(0) instanceof ManageServiceCatalogue) {
			for (ManageServiceCatalogue service : (List<ManageServiceCatalogue>) entityList) {
				String gstinUserId = service.getGstin() + "#" + service.getReferenceId();
				if (!gstinOrgList.contains(gstinUserId)) {
					GSTINDetails GSTINDetails = gstinDetailsDAO.getGstinDetailsFromGstinNo(service.getGstin(),
							service.getReferenceId());
					if (GSTINDetails != null) {
						String key = null;
						for (GstinLocation gstinLocation : GSTINDetails.getGstinLocationSet()) {
							key = GSTINDetails.getGstinNo() + "#" + gstinLocation.getGstinLocation();
							if (!gstinStoreIds.containsKey(key))
								gstinStoreIds.put(key, gstinLocation.getId());
						}
					} else {
						throw new Exception("Invalid GSTIN :" + service.getGstin());
					}
				}
				String key = service.getGstin() + "#" + service.getStoreName();
				if (gstinStoreIds.containsKey(key)) {
					service.setStoreId(gstinStoreIds.get(key));
				} else
					throw new Exception(
							"Invalid Store :" + service.getStoreName() + " for GSTIN :" + service.getGstin());
				list.add(service);
			}

		} else
			list = (List<Object>) entityList;

		return list;
	}

	@SuppressWarnings("unchecked")
	private List<Object> setRemainingEntityData(List<?> entiyList, List<Object> requiredObjectList) {
		logger.info("Entry");
		List<Object> list = new ArrayList<Object>();
		if (entiyList.get(0) instanceof CustomerDetails) {
			List<CustomerDetails> custDetailList = new ArrayList<CustomerDetails>();
			for (CustomerDetails customerDetails : (List<CustomerDetails>) entiyList) {
				for (Object listofObject : requiredObjectList) {
					List<Object> unidentifiedList = (List<Object>) listofObject;
					if (unidentifiedList.get(0) instanceof PinCode) {
						for (Object obj : unidentifiedList) {
							PinCode pincode = (PinCode) obj;
							if (pincode.getPinCode().equals(customerDetails.getPinCode())) {
								customerDetails.setCustCity(pincode.getDistrict());
								customerDetails.setCustState(pincode.getStateId().toString());
								// customerDetails.setCustGstId("");
								break;
							}
						}
					}
					if (customerDetails.getCustState() != null) {
						if (unidentifiedList.get(0) instanceof State) {
							for (Object object : unidentifiedList) {
								State state = (State) object;
								if (state.getId() == Integer.parseInt(customerDetails.getCustState())) {
									customerDetails.setCustState(state.getStateName());
									break;
								}
							}
						}
					}
					if (customerDetails.getCustGstId() != null) {
						if (unidentifiedList.get(0) instanceof State) {
							for (Object object : unidentifiedList) {
								State state = (State) object;
								String gstinCode = (String) customerDetails.getCustGstId().subSequence(0, 2);
								if (state.getId() == Integer.parseInt(gstinCode)) {
									customerDetails.setCustGstinState(state.getStateName());
									break;
								}
							}
						}
					}
				}
				custDetailList.add(customerDetails);
			}
			list.addAll(custDetailList);
		} else if (entiyList.get(0) instanceof Product) {
			List<Product> productList = new ArrayList<Product>();
			for (Product product : (List<Product>) entiyList) {
				for (Object listofObject : requiredObjectList) {
					List<Object> unidentifiedList = (List<Object>) listofObject;
					if (unidentifiedList.get(0) instanceof HSNDetails) {
						for (Object obj : unidentifiedList) {
							HSNDetails hsn = (HSNDetails) obj;
							if (product.getHsnCode().equals(hsn.getHsnCode()) && product.getHsnDescription()
									.toUpperCase().contains(hsn.getHsnDesc().toUpperCase())) {
								if (product.getHsnCodePkId() == null || product.getHsnCodePkId() == 0) {
									product.setHsnCodePkId(hsn.getId());
									break;
								}
							}
						}
					}
				}
				productList.add(product);
			}
			list.addAll(productList);
		} else if (entiyList.get(0) instanceof ManageServiceCatalogue) {
			List<ManageServiceCatalogue> serviceList = new ArrayList<ManageServiceCatalogue>();
			for (ManageServiceCatalogue service : (List<ManageServiceCatalogue>) entiyList) {
				for (Object listofObject : requiredObjectList) {
					List<Object> unidentifiedList = (List<Object>) listofObject;
					if (unidentifiedList.get(0) instanceof SACCode) {
						for (Object obj : unidentifiedList) {
							SACCode sac = (SACCode) obj;
							if (service.getSacCode().equals(sac.getSacCode()) && sac.getSacDescription().toUpperCase()
									.contains(service.getSacDescription().toUpperCase())) {
								if (service.getSacCodePkId() == null || service.getSacCodePkId() == 0) {
									service.setSacCodePkId(sac.getId());
									break;
								}
							}
						}
					}
				}
				serviceList.add(service);
			}
			list.addAll(serviceList);
		}
		logger.info("Exit");
		return list;
	}

	private void validateBusinessRules(List<Object> entiyList, List<Object> requiredObjectList) throws Exception {
		if (entiyList.get(0) instanceof CustomerDetails) {
			validateCustomerDetailsData(entiyList, requiredObjectList);
		} else if (entiyList.get(0) instanceof Product) {
			validateProductData(entiyList, requiredObjectList);
		} else if (entiyList.get(0) instanceof ManageServiceCatalogue) {
			validateServiceData(entiyList, requiredObjectList);
		}

	}

	@SuppressWarnings("unchecked")
	private void validateCustomerDetailsData(List<?> entiyList, List<Object> requiredObjectList) throws Exception {
		logger.info("Entry");
		// need to write logic of insert or update
		final String invalidErrorMsg = "Invalid value for column: ";
		final String forCustomerMastertype = "of Customer Name: ";
		if (entiyList.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (CustomerDetails customerDetails : (List<CustomerDetails>) entiyList) {
				for (Object listofObject : requiredObjectList) {
					int doesNotExistCounter = 0;
					List<Object> unidentifiedList = (List<Object>) listofObject;
					if (unidentifiedList.get(0) instanceof PinCode) {
						int sizeOfList = unidentifiedList.size();
						for (Object obj : unidentifiedList) {
							PinCode pincode = (PinCode) obj;
							if (!pincode.getPinCode().equals(customerDetails.getPinCode())) {
								doesNotExistCounter++;
							} else {
								break;
							}
						}

						if (doesNotExistCounter >= sizeOfList) {
							sb.append(invalidErrorMsg + "Pin Code* " + forCustomerMastertype + ""
									+ customerDetails.getCustName());
						}
					}
				}
			}
			if (!sb.toString().isEmpty()) {
				throw new Exception(sb.toString());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void validateProductData(List<?> entiyList, List<Object> requiredObjectList) throws Exception {
		logger.info("Entry");
		// need to write logic of insert or update
		final String invalidErrorMsg = "Invalid value for column: ";
		final String forGoodsMastertype = "of Goods Name: ";
		if (entiyList.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (Product product : (List<Product>) entiyList) {
				for (Object listofObject : requiredObjectList) {
					int doesNotExistCounter = 0;
					List<Object> unidentifiedList = (List<Object>) listofObject;
					if (unidentifiedList.get(0) instanceof HSNDetails) {
						int sizeOfList = unidentifiedList.size();
						for (Object obj : unidentifiedList) {
							HSNDetails hsn = (HSNDetails) obj;
							if (!product.getHsnCode().equals(hsn.getHsnCode())) {
								doesNotExistCounter++;
							} else {
								break;
							}
						}

						if (doesNotExistCounter >= sizeOfList) {
							sb.append(invalidErrorMsg + "HSN Code* " + forGoodsMastertype + "" + product.getName());
						}
					} else if (unidentifiedList.get(0) instanceof UnitOfMeasurement) {
						int sizeOfList = unidentifiedList.size();
						for (Object obj : unidentifiedList) {
							UnitOfMeasurement uom = (UnitOfMeasurement) obj;
							if (!product.getUnitOfMeasurement().equalsIgnoreCase(uom.getQuantityDescription())) {
								doesNotExistCounter++;
							} else {
								break;
							}
						}

						if (doesNotExistCounter >= sizeOfList) {
							sb.append(invalidErrorMsg + "Unit Of Measurement* " + forGoodsMastertype + ""
									+ product.getName());
						}

						if (product.getUnitOfMeasurement().equalsIgnoreCase("others")) {
							if (product.getOtherUOM().isEmpty() || product.getOtherUOM() == "null"
									|| product.getOtherUOM().equalsIgnoreCase("others")
									|| product.getOtherUOM().equalsIgnoreCase("other")) {
								sb.append(invalidErrorMsg + "Unit Of Measurement if Others Please Specify "
										+ forGoodsMastertype + "" + product.getName());
							}
						}
					} else if (unidentifiedList.get(0) instanceof ProductRateOfTaxDetails) {
						int sizeOfList = unidentifiedList.size();
						for (Object obj : unidentifiedList) {
							ProductRateOfTaxDetails rot = (ProductRateOfTaxDetails) obj;
							if (!product.getProductIgst().equals(rot.getTaxRate())) {
								doesNotExistCounter++;
							} else {
								break;
							}
						}

						if (doesNotExistCounter >= sizeOfList) {
							sb.append(
									invalidErrorMsg + "Rate of tax(%)* " + forGoodsMastertype + "" + product.getName());
						}
					}
				}
			}
			if (!sb.toString().isEmpty()) {
				throw new Exception(sb.toString());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void validateServiceData(List<?> entiyList, List<Object> requiredObjectList) throws Exception {
		logger.info("Entry");
		// final String enterErrorMsg = "Please enter appropriate value for column: ";
		final String invalidErrorMsg = "Invalid value for column: ";
		final String forServiceMastertype = "of Service Name: ";
		if (entiyList.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (ManageServiceCatalogue service : (List<ManageServiceCatalogue>) entiyList) {
				for (Object listofObject : requiredObjectList) {
					int doesNotExistCounter = 0;
					List<Object> unidentifiedList = (List<Object>) listofObject;
					if (unidentifiedList.get(0) instanceof SACCode) {
						int sizeOfList = unidentifiedList.size();
						for (Object obj : unidentifiedList) {
							SACCode sac = (SACCode) obj;
							if (!service.getSacCode().equals(sac.getSacCode())) {
								doesNotExistCounter++;
							} else {
								break;
							}
						}

						if (doesNotExistCounter >= sizeOfList) {
							sb.append(invalidErrorMsg + "SAC Code* " + forServiceMastertype + "" + service.getName());
						}
					} else if (unidentifiedList.get(0) instanceof UnitOfMeasurement) {
						int sizeOfList = unidentifiedList.size();
						for (Object obj : unidentifiedList) {
							UnitOfMeasurement uom = (UnitOfMeasurement) obj;
							if (!service.getUnitOfMeasurement().equalsIgnoreCase(uom.getQuantityDescription())) {
								doesNotExistCounter++;
							} else {
								break;
							}
						}

						if (doesNotExistCounter >= sizeOfList) {
							sb.append(invalidErrorMsg + "Unit Of Measurement* " + forServiceMastertype + ""
									+ service.getName());
						}

						if (service.getUnitOfMeasurement().equalsIgnoreCase("others")) {
							if (service.getOtherUOM().isEmpty() || service.getOtherUOM() == "null"
									|| service.getOtherUOM().equalsIgnoreCase("others")
									|| service.getOtherUOM().equalsIgnoreCase("other")) {
								sb.append(invalidErrorMsg + "Unit Of Measurement if Others Please Specify* "
										+ forServiceMastertype + "" + service.getName());
							}
						}
					} else if (unidentifiedList.get(0) instanceof ServiceRateOfTaxDetails) {
						int sizeOfList = unidentifiedList.size();
						for (Object obj : unidentifiedList) {
							ServiceRateOfTaxDetails rot = (ServiceRateOfTaxDetails) obj;
							if (!service.getServiceIgst().equals(rot.getTaxRate())) {
								doesNotExistCounter++;
							} else {
								break;
							}
						}

						if (doesNotExistCounter >= sizeOfList) {
							sb.append(invalidErrorMsg + "Rate of tax(%)* " + forServiceMastertype + ""
									+ service.getName());
						}
					}
				}
			}
			if (!sb.toString().isEmpty()) {
				throw new Exception(sb.toString());
			}
		}
		logger.info("Exit");
	}

}
