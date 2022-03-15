/**
 * 
 */
package com.reliance.gstn.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.admin.exception.EwayBillApiException;
import com.reliance.gstn.dao.EWayBillDao;
import com.reliance.gstn.model.EWayBill;
import com.reliance.gstn.model.EwayBillRateMaster;
import com.reliance.gstn.model.EwayBillWITransaction;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.NicUserDetails;
import com.reliance.gstn.util.AspApiConstants;
import com.reliance.gstn.util.NICUtil;

/**
 * @author Nikesh.Bansod
 *
 */
@Repository
public class EWayBillDaoImpl implements EWayBillDao {
	private static final Logger logger = Logger.getLogger(EWayBillDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${get_nic_user_details}")
	private String nic_UserDetails;

	@Value("${get_nic_user_details2}")
	private String nic_UserDetails2;

	@Value("${get_eway_bill_details}")
	private String getEWayBillList;

	@Value("${get_eway_bill_by_org_id}")
	private String getEWayBillByOrgId;

	@Value("${get_ewaybill_details}")
	private String eWayBillDetails;

	@Value("${nic_details_gstin_and_orgid}")
	private String nicDetailsGstinAndOrgid;

	@Value("${get_eway_bills}")
	private String getEwayBills;

	@Value("${get_ewaybill_bynumber}")
	private String getEwayBillByNumber;

	@Value("${get_ewb_customerbonboardedList}")
	private String customerbonboardedList;

	@Value("${get_ewaybill_rateList}")
	private String ewaybillRateList;
	
	@Value("${get_ewaybill_listCustomer}")
	private String getEWayBillListCustomer;

	@Override
	public void addEwayBill(EWayBill eWayBill) throws EwayBillApiException {
		logger.info("Entry");
		try {
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(eWayBill);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw new EwayBillApiException(e.getMessage());
		}

		logger.info("Exit");

	}

	public NicUserDetails getNICUserDetails(EWayBill eWayBill) throws EwayBillApiException {
		logger.info("Entry");
		NicUserDetails nicUserDetails = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(nic_UserDetails);
			query.setString(AspApiConstants.GSTIN, eWayBill.getGstin());// eWayBill.getGstin()
			query.setString(AspApiConstants.NIC_USER_ID, eWayBill.getNic_id());
			query.setInteger(AspApiConstants.REF_ORG_UID, eWayBill.getRef_org_uId());// eWayBill.getRef_org_uId()
			nicUserDetails = (NicUserDetails) query.uniqueResult();
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw new EwayBillApiException(e.getMessage());
		}
		logger.info("Exit");
		return nicUserDetails;
	}

	public NicUserDetails getWizardNICUserDetails(EWayBill eWayBill) throws EwayBillApiException {
		logger.info("getWizardNICUserDetails Entry");
		NicUserDetails nicUserDetails = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(nic_UserDetails2);
			query.setString(AspApiConstants.GSTIN, eWayBill.getGstin());// eWayBill.getGstin()
			query.setString(AspApiConstants.NIC_USER_ID, eWayBill.getNic_id());
			query.setString(AspApiConstants.AUTH_USER_ID, eWayBill.getAuthUserId());// eWayBill.getRef_org_uId()
			// query.setString(AspApiConstants.APP_CODE, eWayBill.getAppCode());
			nicUserDetails = (NicUserDetails) query.uniqueResult();
		} catch (Exception e) {
			logger.error("getWizardNICUserDetails Error in:", e);
			throw new EwayBillApiException(e.getMessage());
		}
		logger.info("getWizardNICUserDetails Exit");
		return nicUserDetails;
	}

	@Override
	public void save(NicUserDetails nicUserDetails) throws EwayBillApiException {
		try {
			Session session = sessionFactory.getCurrentSession();
			// session.getTransaction().begin();
			session.saveOrUpdate(nicUserDetails);
			// session.getTransaction().commit();
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw new EwayBillApiException(e.getMessage());
		}
		logger.info("Exit");

	}

	@Override
	public List<EWayBill> getEwayBillsByInvoiceId(int id) throws Exception {

		logger.info("Entry");
		List<EWayBill> ewayBills = new ArrayList<EWayBill>();
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(getEWayBillList);
			query.setInteger("invoiceId", id);
			ewayBills = (List<EWayBill>) query.list();
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw new EwayBillApiException(e.getMessage());
		}
		logger.info("Exit");
		return ewayBills;

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean validateEWayBillAgainstOrgId(Integer invoiceId, Integer orgUId) throws Exception {
		logger.info("Entry");
		boolean isInvoiceAllowed = false;
		List<InvoiceDetails> invoiceDetailsList = new ArrayList<InvoiceDetails>();
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(getEWayBillByOrgId);
			query.setInteger("orgUId", orgUId);
			query.setInteger("id", invoiceId);
			invoiceDetailsList = (List<InvoiceDetails>) query.list();
			if (!invoiceDetailsList.isEmpty()) {
				isInvoiceAllowed = true;
			}

		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}

		logger.info("Exit");
		return isInvoiceAllowed;
	}

	@Override
	public EWayBill getEWayBillDetailsById(Integer id) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		EWayBill eWayBillDetails = null;
		try {
			eWayBillDetails = (EWayBill) session.get(EWayBill.class, id);

		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return eWayBillDetails;
	}

	@Override
	public EWayBill getEwayDetails(EWayBill gstin) throws EwayBillApiException {
		logger.info("Entry");
		EWayBill eWayBill = null;
		try {
			Session session = sessionFactory.openSession();
			Query query = session.createQuery(eWayBillDetails);
			query.setString(AspApiConstants.GSTIN, gstin.getGstin());
			query.setString(AspApiConstants.EWAYBILL_NO, gstin.getEwaybillNo());
			query.setInteger(AspApiConstants.REF_ORG_UID, gstin.getRef_org_uId());
			eWayBill = (EWayBill) query.uniqueResult();
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw new EwayBillApiException(e.getMessage());
		}
		logger.info("Exit");
		return eWayBill;
	}

	@Override
	public NicUserDetails getNicDetailsFromGstinAndOrgId(String gstnStateIdInString, Integer orgUId) throws Exception {
		logger.info("Entry");
		NicUserDetails nicUserDetails = null;
		try {
			Session session = sessionFactory.openSession();
			Query query = session.createQuery(nicDetailsGstinAndOrgid);
			query.setString(AspApiConstants.GSTIN, gstnStateIdInString);
			query.setInteger(AspApiConstants.AUTH_USER_ID, orgUId);
			nicUserDetails = (NicUserDetails) query.uniqueResult();
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw new EwayBillApiException(e.getMessage());
		}
		logger.info("Exit");
		return nicUserDetails;
	}

	@Override
	public void updateEwayBill(EWayBill eWayBill) throws EwayBillApiException {
		logger.info("updateEwayBill Entry ");
		try {
			Session session = sessionFactory.openSession();
			EWayBill eWayBillDetails = (EWayBill) session.get(EWayBill.class, eWayBill.getId());
			eWayBillDetails.setCancelRmrk(eWayBill.getCancelRmrk());
			eWayBillDetails.setEwaybillStatus(eWayBill.getEwaybillStatus());
			session.update(eWayBillDetails);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw new EwayBillApiException(e.getMessage());
		}

		logger.info("updateEwayBill Exit");

	}

	@Override
	public void addEwayBill(EwayBillWITransaction eWayBill) throws EwayBillApiException {
		logger.info(" addEwayBill Entry");
		try {
			Session session = sessionFactory.getCurrentSession();
			// session.getTransaction().begin();
			session.saveOrUpdate(eWayBill);
			// session.getTransaction().commit();
		} catch (Exception e) {
			logger.error("addEwayBill Error in:", e);
			throw new EwayBillApiException(e.getMessage());
		}

		logger.info("Exit");

	}

	@Override
	public Object getGeneratedEwayBillList(Map<String, String> request) throws EwayBillApiException {
		logger.info(" addEwayBill Entry");
		Object object = null;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(getEwayBills);
		query.setString(AspApiConstants.USERID, request.get(AspApiConstants.USERID));
		// query.setString(AspApiConstants.APPCODE,
		// request.get(AspApiConstants.APP_CODE));
		List<Object[]> ewayBillList = query.list();
		List<Map<String, Object>> ewayBillMapList = new ArrayList<>();
		if (ewayBillList != null && !ewayBillList.isEmpty()) {
			for (Object[] objectArray : ewayBillList) {
				Map<String, Object> map = new LinkedHashMap<>();
				map.put("ewaybillNo", objectArray[0]);
				map.put("ewaybill_date", objectArray[1]);
				map.put("ewaybill_valid_upto", objectArray[2]);
				map.put("ewaybillStatus", objectArray[3]);
				map.put("toTraderName", objectArray[4]);
				map.put("transModeDesc", objectArray[5]);
				ewayBillMapList.add(map);
			}
			object = ewayBillMapList;
		} else
			object = NICUtil.getExcObjDesc("no record found");
		logger.info(" addEwayBill object " + object);
		logger.info(" addEwayBill exit ");
		return object;
	}

	public Object getEwayBillByNumber(Map<String, String> request) throws EwayBillApiException {
		logger.info(" getEwayBillByNumber Entry");
		Object object = null;
		try

		{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(getEwayBillByNumber);
			query.setString(AspApiConstants.USERID, request.get(AspApiConstants.USERID));
			// query.setString(AspApiConstants.APPCODE,
			// request.get(AspApiConstants.APP_CODE));
			query.setString("ewaybillNo", request.get("ewaybillno"));
			EwayBillWITransaction ewayBillList = (EwayBillWITransaction) query.uniqueResult();
			if (ewayBillList != null) {
				object = ewayBillList;
			} else {
				NICUtil.getExceptionMsg("no record found");
			}
			logger.info(" getEwayBillByNumber object " + object);
			logger.info(" getEwayBillByNumber exit ");
		} catch (Exception e) {
			logger.info(" getEwayBillByNumber exit " + e);
		}

		return object;
	}

	@Override
	public Object getEwayBillCustomerOnboardedList(Map<String, String> request) throws EwayBillApiException {
		logger.info("getEwayBillCustomerOnboardedList Entry");
		NicUserDetails nicUserDetails = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(customerbonboardedList);
			query.setString(AspApiConstants.GSTIN, request.get(AspApiConstants.GSTIN));// eWayBill.getGstin()
			query.setString(AspApiConstants.AUTH_USER_ID, request.get(AspApiConstants.USERID));
			// query.setString(AspApiConstants.APPCODE,
			// request.get(AspApiConstants.APP_CODE));
			nicUserDetails = (NicUserDetails) query.uniqueResult();
			if (nicUserDetails != null) {
				Map<String, Object> map = new LinkedHashMap<>();
				map.put(AspApiConstants.STATUS_CD, AspApiConstants.SU_CD);
				map.put(AspApiConstants.STATUS, AspApiConstants.SUCCESS);
				map.put(AspApiConstants.GSTIN, nicUserDetails.getGstin());
				map.put("nicId", nicUserDetails.getUserId());
				map.put("userId", nicUserDetails.getAuthUserId());
				return map;

			} else {
				Map<String, Object> exObject = new LinkedHashMap<>();
				exObject.put(AspApiConstants.STATUS_CD, AspApiConstants.ER_CD);
				exObject.put(AspApiConstants.STATUS, AspApiConstants.FAILURE);
				exObject.put(AspApiConstants.ERROR_CODE, AspApiConstants.EWAYBILL_CONNECTION_ERROR);
				exObject.put(AspApiConstants.ERROR_DESC, "No Customer Onboarded");
				exObject.put(AspApiConstants.ERROR_GRP, AspApiConstants.EWAYBILL_CONNECTION_ERROR);
				return exObject;

			}

		} catch (Exception e) {
			logger.error("getEwayBillCustomerOnboardedList Error in:", e);
			throw new EwayBillApiException(e.getMessage());
		}

	}

	@Override
	public List<EwayBillRateMaster> getEwayBillRateList() throws EwayBillApiException {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(ewaybillRateList);
			List<EwayBillRateMaster> ewayBillList = query.list();
			return ewayBillList;
		} catch (Exception e) {

		}

		return null;
	}

	@Override
	public Object operation(Map<String, String> inputMap) throws EwayBillApiException {
		Object object = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			String inputQuery = inputMap.get("query");
			String type = inputMap.get("type");
			Query query = session.createSQLQuery(inputQuery);
			if (type.equals("S")) {
				if (inputMap.get("start") != null && inputMap.get("maxRows") != null) {
					query.setFirstResult(Integer.parseInt(inputMap.get("start")));
					query.setMaxResults(Integer.parseInt(inputMap.get("maxRows")));
				}
				return query.list();
			} else if (type.equals("U") || type.equals("D")) {
				return query.executeUpdate();
			}
		} catch (Exception e) {

		}
		return object;
	}

	@Override
	public List<Object[]> getEwayBillsAndCustomerByInvoiceId( Integer id,Integer orgid) throws EwayBillApiException {
		logger.info("Entry");
		List<Object[]> ewayBills = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createSQLQuery(getEWayBillListCustomer);
			query.setInteger("id", id);
			query.setInteger("orgUId", orgid);
			ewayBills = (List<Object[]>) query.list();
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw new EwayBillApiException(e.getMessage());
		}
		logger.info("Exit");
		return ewayBills;
	}

}
