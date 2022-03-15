package com.reliance.gstn.util;

import java.util.Map;

import com.reliance.gstn.admin.exception.EwayBillApiException;
import com.reliance.gstn.model.EwayBillCalculateTax;
import com.reliance.gstn.model.EwayBillItemList;
import com.reliance.gstn.model.EwayBillWITransaction;
import com.reliance.gstn.model.ExternalWizardEwayBill;

public enum BaseRequestValidation {

	EWAYBILL_CUSTOMERBONBOARDING {
		public Boolean getBaseResponse(Object object) throws EwayBillApiException {
			if (object != null) {
				Map<String, String> request = (Map<String, String>) object;
				if (isValidRequest(request.get(AspApiConstants.GSTIN), AspApiConstants.GSTIN)
						&& isValidRequest(request.get(AspApiConstants.NIC_USER_ID), AspApiConstants.NIC_USER_ID)
						&& isValidRequest(request.get(AspApiConstants.PWD), AspApiConstants.PWD)
						&& isValidRequest(request.get(AspApiConstants.USERID), AspApiConstants.USERID)
						&& isValidRequest(request.get(AspApiConstants.EMAIL_ID), AspApiConstants.EMAIL_ID)
						&& isValidRequest(request.get(AspApiConstants.MOBILE_NO), AspApiConstants.MOBILE_NO))
					return true;
				else
					return false;
			} else {
				throw new EwayBillApiException(AspApiConstants.INVALID_REQ);
			}

		}
	},
	GSTIN_DETAILS {
		public Boolean getBaseResponse(Object object) throws EwayBillApiException {
			if (object != null) {
				Map<String, String> request = (Map<String, String>) object;
				if (isValidRequest(request.get(AspApiConstants.GSTIN), AspApiConstants.GSTIN))
					return true;
				else
					return false;
			} else {
				throw new EwayBillApiException(AspApiConstants.INVALID_REQ);
			}

		}
	},
	GENERATED_EWAYBILLLIST {
		public Boolean getBaseResponse(Object object) throws EwayBillApiException {
			if (object != null) {
				Map<String, String> request = (Map<String, String>) object;
				if (isValidRequest(request.get(AspApiConstants.USERID), AspApiConstants.USERID))
					return true;
				else
					return false;
			} else {
				throw new EwayBillApiException(AspApiConstants.INVALID_REQ);
			}

		}
	},
	HSNCODE {
		public Boolean getBaseResponse(Object object) throws EwayBillApiException {
			if (object != null) {
				ExternalWizardEwayBill externalWizardEwayBill = (ExternalWizardEwayBill) object;
				if (isValidRequest(externalWizardEwayBill.getKey(), AspApiConstants.KEY))
					return true;
				else
					return false;
			} else {
				throw new EwayBillApiException(AspApiConstants.INVALID_REQ);
			}

		}
	},
	EWAYBILL_BYNUMBER {
		public Boolean getBaseResponse(Object object) throws EwayBillApiException {
			if (object != null) {
				Map<String, String> request = (Map<String, String>) object;
				if (isValidRequest(request.get(AspApiConstants.EWAYBILLNO), AspApiConstants.EWAYBILLNO)
						&& isValidRequest(request.get(AspApiConstants.USERID), AspApiConstants.USERID))
					return true;
				else
					return false;
			} else {
				throw new EwayBillApiException(AspApiConstants.INVALID_REQ);
			}

		}
	},
	PINCODE {
		public Boolean getBaseResponse(Object object) throws EwayBillApiException {
			if (object != null) {
				ExternalWizardEwayBill externalWizardEwayBill = (ExternalWizardEwayBill) object;
				if (isValidRequest(externalWizardEwayBill.getKey(), AspApiConstants.KEY))
					return true;
				else
					return false;
			} else {
				throw new EwayBillApiException(AspApiConstants.INVALID_REQ);
			}

		}
	},
	EWAYBILL_CALCULATETAXAMOUNT {
		public Boolean getBaseResponse(Object object) throws EwayBillApiException {
			if (object != null) {
				EwayBillCalculateTax ewayBillCalculateTax = (EwayBillCalculateTax) object;
				if (!ewayBillCalculateTax.getFromGstin().equals("URP")
						&& !ewayBillCalculateTax.getToGstin().equals("URP")) {
					isValidRequest(ewayBillCalculateTax.getFromGstin(), AspApiConstants.FROM_GSTIN);
					isValidRequest(ewayBillCalculateTax.getFromGstin(), AspApiConstants.TO_GSTIN);
				} else if (ewayBillCalculateTax.getToGstin().equals("URP")) {
					isValidRequest(ewayBillCalculateTax.getFromGstin(), AspApiConstants.FROM_GSTIN);
				} else if (ewayBillCalculateTax.getFromGstin().equals("URP")) {
					isValidRequest(ewayBillCalculateTax.getFromGstin(), AspApiConstants.TO_GSTIN);
				} else {
					throw new EwayBillApiException(AspApiConstants.INVALID_REQ);
				}
				if (ewayBillCalculateTax.getItemList() != null && !ewayBillCalculateTax.getItemList().isEmpty()) {
					for (EwayBillItemList ewayBillItemList : ewayBillCalculateTax.getItemList()) {
						if (isValidRequest(ewayBillItemList.getHsnId(), AspApiConstants.HSNID)) {
						}
					}
					return true;
				} else
					throw new EwayBillApiException(AspApiConstants.INVALID_REQ);

			} else {
				throw new EwayBillApiException(AspApiConstants.INVALID_REQ);
			}

		}
	},
	EWAYBILL_CALCULATETAXAMOUNTV2 {
		public Boolean getBaseResponse(Object object) throws EwayBillApiException {
			if (object != null) {
				EwayBillCalculateTax ewayBillCalculateTax = (EwayBillCalculateTax) object;
				if (!ewayBillCalculateTax.getFromGstin().equals("URP")
						&& !ewayBillCalculateTax.getToGstin().equals("URP")) {
					isValidRequest(ewayBillCalculateTax.getFromGstin(), AspApiConstants.FROM_GSTIN);
					isValidRequest(ewayBillCalculateTax.getFromGstin(), AspApiConstants.TO_GSTIN);
				} else if (ewayBillCalculateTax.getToGstin().equals("URP")) {
					isValidRequest(ewayBillCalculateTax.getFromGstin(), AspApiConstants.FROM_GSTIN);
				} else if (ewayBillCalculateTax.getFromGstin().equals("URP")) {
					isValidRequest(ewayBillCalculateTax.getFromGstin(), AspApiConstants.TO_GSTIN);
				} else {
					throw new EwayBillApiException(AspApiConstants.INVALID_REQ);
				}

				return true;
			} else {
				throw new EwayBillApiException(AspApiConstants.INVALID_REQ);
			}

		}
	},
	GENERATE_EWAYBILL {
		public Boolean getBaseResponse(Object object) throws EwayBillApiException {
			if (object != null) {
				EwayBillWITransaction ewayBillWITransaction = (EwayBillWITransaction) object;

				if (!ewayBillWITransaction.getFromGstin().equals("URP")
						&& !ewayBillWITransaction.getToGstin().equals("URP")) {
					isValidRequest(ewayBillWITransaction.getFromGstin(), AspApiConstants.FROM_GSTIN);
					isValidRequest(ewayBillWITransaction.getFromGstin(), AspApiConstants.TO_GSTIN);
				} else if (ewayBillWITransaction.getToGstin().equals("URP")) {
					isValidRequest(ewayBillWITransaction.getFromGstin(), AspApiConstants.FROM_GSTIN);
				} else if (ewayBillWITransaction.getFromGstin().equals("URP")) {
					isValidRequest(ewayBillWITransaction.getFromGstin(), AspApiConstants.TO_GSTIN);
				}

				if (isValidRequest(ewayBillWITransaction.getSupplyType(), AspApiConstants.SUPPLY_TYPE)
						&& isValidRequest(ewayBillWITransaction.getSupplyType(), AspApiConstants.SUB_SUPPLY_TYPE)
						&& isValidRequest(ewayBillWITransaction.getDocumentType(), AspApiConstants.DOC_TYPE)
						&& isValidRequest(ewayBillWITransaction.getDocumentNo(), AspApiConstants.DOC_NO)
						&& isValidRequest(ewayBillWITransaction.getDocumentDateInString(), AspApiConstants.DOC_DATE)) {
					return true;
				} else
					return false;

			} else {
				throw new EwayBillApiException(AspApiConstants.INVALID_REQ);
			}

		}
	},
	CANCEL_EWAYBILL {
		public Boolean getBaseResponse(Object object) throws EwayBillApiException {
			if (object != null) {
				Map<String, String> request = (Map<String, String>) object;
				if (isValidRequest(request.get(AspApiConstants.EWAYBILLNO), AspApiConstants.EWAYBILLNO)
						&& isValidRequest(request.get(AspApiConstants.USERID), AspApiConstants.USERID)
						&& isValidRequest(request.get(AspApiConstants.GSTIN), AspApiConstants.GSTIN)
						&& isValidRequest(request.get(AspApiConstants.NIC_USER_ID), AspApiConstants.NIC_USER_ID)
						&& isValidRequest(request.get(AspApiConstants.PWD), AspApiConstants.PWD)
						&& isValidRequest(request.get(AspApiConstants.CANCEL_RMK), AspApiConstants.CANCEL_RMK))
					return true;
				else
					return false;
			} else {
				throw new EwayBillApiException(AspApiConstants.INVALID_REQ);
			}

		}
	};
	public abstract Boolean getBaseResponse(Object object) throws EwayBillApiException;

	public static Boolean getBaseResponseObject(BaseRequestValidation base, Object object) {
		switch (base) {
		case EWAYBILL_CUSTOMERBONBOARDING:
			return EWAYBILL_CUSTOMERBONBOARDING.getBaseResponse(object);
		case GSTIN_DETAILS:
			return GSTIN_DETAILS.getBaseResponse(object);
		case GENERATED_EWAYBILLLIST:
			return GENERATED_EWAYBILLLIST.getBaseResponse(object);
		case HSNCODE:
			return HSNCODE.getBaseResponse(object);
		case EWAYBILL_BYNUMBER:
			return EWAYBILL_BYNUMBER.getBaseResponse(object);
		case PINCODE:
			return PINCODE.getBaseResponse(object);
		case EWAYBILL_CALCULATETAXAMOUNT:
			return EWAYBILL_CALCULATETAXAMOUNT.getBaseResponse(object);
		case EWAYBILL_CALCULATETAXAMOUNTV2:
			return EWAYBILL_CALCULATETAXAMOUNTV2.getBaseResponse(object);
		case GENERATE_EWAYBILL:
			return GENERATE_EWAYBILL.getBaseResponse(object);
		case CANCEL_EWAYBILL:
			return CANCEL_EWAYBILL.getBaseResponse(object);

		default:
			throw new EwayBillApiException(AspApiConstants.INVALID_REQ);
		}

	}

	private static boolean isValidRequest(String data, String key) {
		boolean flag = false;
		if (data != null && !data.trim().equals(""))
			flag = true;
		else {
			throw new EwayBillApiException(key + ": should not be empty");
		}
		return flag;
	}

	public static boolean isValidRequest(Object data, String key) {
		boolean flag = false;
		if (data != null)
			flag = true;
		else {
			throw new EwayBillApiException(key + ": should not be empty");
		}
		return flag;
	}
}
