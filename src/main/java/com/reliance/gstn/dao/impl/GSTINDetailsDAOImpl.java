/**
 * 
 */
package com.reliance.gstn.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.reliance.gstn.dao.GSTINDetailsDAO;
import com.reliance.gstn.dao.StateDAO;
import com.reliance.gstn.model.GSTINAddressMapping;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.GstinLocation;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.PurchaseEntryDetails;
import com.reliance.gstn.model.State;
import com.reliance.gstn.model.UserGSTINMapping;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.GSTINUserMappingService;
import com.reliance.gstn.service.GstinValidationService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;

//import com.reliance.gstn.util.LoggerUtil;

/**
 * @author Pradeep.Gangapuram
 *
 */

@Repository
public class GSTINDetailsDAOImpl implements GSTINDetailsDAO {

	private static final Logger logger = Logger
			.getLogger(GSTINDetailsDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	StateDAO stateDAO;

	@Autowired
	GSTINUserMappingService gstinUserMappingService;

	@Autowired
	GstinValidationService gstinValidationService;

	@Value("${gstin_details_list_query}")
	private String gstinDetailsListQuery;

	@Value("${gstin_detail_remove_query}")
	private String removeGstinDetailQuery;

	@Value("${gstin_details_by_ref_id}")
	private String gstinDetailsByRefid;

	@Value("${gstin_by_gstin_no}")
	private String getGstinDetailsByGstinNo;

	@Value("${gstin_validation_with_pan}")
	private String checkIfGstinIsValidWithRegisteredPAN;

	@Value("${get_unique_sequence}")
	private String uniqueSequenceQuery;

	@Value("${get_location_unique_sequence}")
	private String locationUniqueSequenceQuery;

	@Value("${get_initial_location_sequence}")
	private String initialLocationUniqueSequenceQuery;

	@Value("${gstin_location_details_list_query}")
	private String gstinLocationDetailsListQuery;

	@Value("${gstin_location_details_query}")
	private String gstinLocationDetailQuery;

	@Value("${get_gstin_mapped_for_secondary_user}")
	private String getGstinMappedForSecondaryUser;

	@Value("${get_user_gstin_mapping}")
	private String getUserGstinMapping;

	@Value("${get_location_by_gstin_and_user_id}")
	private String getLocationByGstinAndUserId;

	@Value("${get_invoice_details_for_gstin_no}")
	private String getInvoiceDetailsForGstinNo;

	@Value("${get_purchase_entry_details_for_gstin_no}")
	private String getPurchaseEntryDetailsForGstinNo;

	@Value("${get_gstn_details_by_id}")
	private String getGstnDetailsBygstinId;
	
	@Value("${gstin_details_list_city_query}")
	private String gstinDetailsListQueryAndCity;

	@Override
	public List<GSTINDetails> listGstinDetails(Integer uId) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(gstinDetailsListQuery);
		query.setInteger("referenceId", uId);

		@SuppressWarnings("unchecked")
		List<GSTINDetails> gstinDetailsList = (List<GSTINDetails>) query.list();

		if (!gstinDetailsList.isEmpty()) {
			gstinDetailsList = mapStateName(gstinDetailsList);
		}
		logger.info("Exit");
		return gstinDetailsList;
	}

	private List<GSTINDetails> mapStateName(List<GSTINDetails> gstinDetailsList)
			throws Exception {
		List<State> stateList = (List<State>) stateDAO.listState();

		for (GSTINDetails a : gstinDetailsList) {
			for (State s : stateList) {
				if (a.getState() == s.getStateId()) {
					a.setStateInString(s.getStateName());
					break;
				}
			}
		}
		return gstinDetailsList;
	}

	@Override
	public String addGstinDetails(GSTINDetails gstinDetails) throws Exception {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		String isError = "";
		String isActive = "";

		try {
			Session session = sessionFactory.getCurrentSession();
			gstinDetails.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			Integer id = (int) (long) gstinDetails.getReferenceId();
			response = gstinValidationService.isValidGstin(gstinDetails.getGstinNo());
			Gson gson = new Gson();
			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap = (Map<String, Object>) gson.fromJson(response.toString(), responseMap.getClass());
			isError = (String) responseMap.get("err_cd");
			isActive = (String) responseMap.get("sts");

			if (isError != null && !isError.isEmpty()) {

				response = GSTNConstants.INVALID_GSTIN;

			} else if (!isActive.equalsIgnoreCase("Active")) {

				response = GSTNConstants.INVALID_GSTIN;

			} else {

				gstinDetails.setUniqueSequence(GSTNUtil.getUniqueSequence(session, uniqueSequenceQuery, "gst_gstin_master","REF_USER_ID", id));
				session.saveOrUpdate(gstinDetails.getGstinAddressMapping());
				session.persist(gstinDetails);

				List<String> locUniqueSeqList = GSTNUtil.getLocationUniqueSequence(session,locationUniqueSequenceQuery,"gst_gstin_location_master", "ref_gstin_id",gstinDetails.getId(), gstinDetails.getGstinLocationSet().size());
				if (locUniqueSeqList.isEmpty()) {
					locUniqueSeqList = GSTNUtil.getInitialLocationUniqueSequence(session,initialLocationUniqueSequenceQuery,gstinDetails.getGstinLocationSet().size());
				}
				int i = 0;
				for (GstinLocation gstinLocation : gstinDetails.getGstinLocationSet()) {
					gstinLocation.setRefGstinId(gstinDetails.getId());
					if (locUniqueSeqList.size() > i) {
						gstinLocation.setUniqueSequence(locUniqueSeqList.get(i));
					}
					i++;
				}
				session.saveOrUpdate(gstinDetails);

				response = GSTNConstants.SUCCESS;
			}
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public GSTINDetails getGstinDetailsById(Integer id) throws Exception {
		logger.info("Entry");
		GSTINDetails gstinDetails = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			gstinDetails = (GSTINDetails) session.get(GSTINDetails.class, id);
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}

		logger.info("Exit");
		return gstinDetails;

	}

	@Override
	public GSTINDetails getGstinDetailsByIdEdit(Integer id) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		logger.info("Entry");
		Query query = session.createQuery(getGstnDetailsBygstinId);
		query.setInteger("id", id);
		GSTINDetails gstinDetails = (GSTINDetails) query.uniqueResult();
		if (gstinDetails != null)
			return gstinDetails;
		else {
			logger.info("gstn not found for this store");
		}
		return gstinDetails;
	}

	@Override
	public String updateGstinDetails(GSTINDetails gstinDetails) {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		String isError = "";
		String isActive = "";
		GSTINDetails gstinDetail = new GSTINDetails();
		GstinLocation gstinLocation = new GstinLocation();
		try {

			Session session = sessionFactory.getCurrentSession();

			response = gstinValidationService.isValidGstin(gstinDetails
					.getGstinNo());
			Gson gson = new Gson();
			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap = (Map<String, Object>) gson.fromJson(
					response.toString(), responseMap.getClass());
			isError = (String) responseMap.get("err_cd");
			isActive = (String) responseMap.get("sts");

			if (isError != null && !isError.isEmpty()) {

				response = GSTNConstants.INVALID_GSTIN;

			} else if (!isActive.equalsIgnoreCase("Active")) {

				response = GSTNConstants.INVALID_GSTIN;

			} else {

				gstinDetail = getGstinDetailsById(gstinDetails.getId());

				if (!gstinDetail.getGstinNo().equalsIgnoreCase(
						gstinDetails.getGstinNo())) {

					Query invQuery = session
							.createQuery(getInvoiceDetailsForGstinNo);
					invQuery.setString("gstinNo", gstinDetail.getGstinNo());

					@SuppressWarnings("unchecked")
					List<InvoiceDetails> invoiceDetailsList = (List<InvoiceDetails>) invQuery
							.list();

					if (!invoiceDetailsList.isEmpty()) {
						for (InvoiceDetails invoiceDetail : invoiceDetailsList) {
							invoiceDetail.setGstnStateIdInString(gstinDetails
									.getGstinNo());
							session.merge(invoiceDetail);
						}
					}

					Query purDetquery = session
							.createQuery(getInvoiceDetailsForGstinNo);
					purDetquery.setString("gstinNo", gstinDetail.getGstinNo());

					@SuppressWarnings("unchecked")
					List<PurchaseEntryDetails> puchaseEntryDetailsList = (List<PurchaseEntryDetails>) purDetquery
							.list();

					if (!puchaseEntryDetailsList.isEmpty()) {
						for (PurchaseEntryDetails puchaseEntryDetail : puchaseEntryDetailsList) {
							puchaseEntryDetail
									.setGstnStateIdInString(gstinDetails
											.getGstinNo());
							session.merge(puchaseEntryDetail);
						}
					}
				}
                
                List<String> locUniqueSeqList = GSTNUtil.getLocationUniqueSequence(session,locationUniqueSequenceQuery,"gst_gstin_location_master", "ref_gstin_id",gstinDetails.getId(), gstinDetails.getGstinLocationSet().size());
                List<GstinLocation> list = new ArrayList<GstinLocation>();
				int i=0;
				
				for (GstinLocation gstinLocation2 : gstinDetails.getGstinLocationSet()) {
					gstinLocation2.setRefGstinId(gstinDetail.getId());
					if(gstinLocation2.getUniqueSequence()==null){
						if (locUniqueSeqList.size() > i) {
							gstinLocation2.setUniqueSequence(locUniqueSeqList.get(i));
						}
						i++;
					}
					list.add(gstinLocation2);
				}
				gstinDetails.setGstinLocationSet(list);
				gstinDetails.setUpdatedOn(new java.sql.Timestamp(new Date()
						.getTime()));
				//gstinLocation.setRefGstinId(gstinDetails.getId());
				session.merge(gstinDetails.getGstinAddressMapping());
				session.merge(gstinDetails);
				response = GSTNConstants.SUCCESS;
			}
		} catch (Exception e) {
			logger.error("Error in:", e);
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public String removeGSTINDetails(GSTINDetails gstinDetails) throws Exception {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();

			List<UserGSTINMapping> userGSTINMappings = new ArrayList<UserGSTINMapping>();
			List<GstinLocation> gstinLocationList = new ArrayList<GstinLocation>();
			userGSTINMappings = gstinUserMappingService
					.getUserGSTINMappingByGstinId(gstinDetails.getId());
			
			gstinLocationList = gstinUserMappingService.getGstinLocationGstinId(gstinDetails.getId());
					
			
			for (GstinLocation gstinLocation : gstinLocationList) {
				session.delete(gstinLocation);
			}

			for (UserGSTINMapping gstinMapping : userGSTINMappings) {
				session.delete(gstinMapping);
			}

			Object persistentInstance = session.get(GSTINDetails.class,
					gstinDetails.getId());
			if (persistentInstance != null) {
				session.delete(persistentInstance);
				response = GSTNConstants.SUCCESS;
			}

		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public List<GSTINDetails> getGstinDetailsByReferenceId(
			String idsValuesToFetch) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		List<Integer> ali = GSTNUtil.getListFromString(idsValuesToFetch);
		String placeHoder = GSTNUtil.getPlaceHoderString(ali);
		String queryString = gstinDetailsByRefid;
		queryString = queryString.replace("idsValuesToFetch", placeHoder);
		Query query = session.createQuery(queryString);

		for (int i = 0; i < ali.size(); i++) {
			query.setInteger(i, ali.get(i));
		}

		@SuppressWarnings("unchecked")
		List<GSTINDetails> gstinList = (List<GSTINDetails>) query.list();
		if (!gstinList.isEmpty()) {
			gstinList = mapStateName(gstinList);
		}
		logger.info("Exit");
		return gstinList;
	}

	@Override
	public boolean checkIfGstinIsValidWithRegisteredPAN(Integer orgId,
			String panNo) throws Exception {
		logger.info("Entry");
		boolean gstinRegistered = false;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(checkIfGstinIsValidWithRegisteredPAN);
		query.setString("panNumber", panNo.toLowerCase());
		query.setInteger("orgId", orgId);

		@SuppressWarnings("unchecked")
		List<UserMaster> list = (List<UserMaster>) query.list();

		if (!list.isEmpty()) {
			gstinRegistered = true;
		}
		logger.info("Exit");

		return gstinRegistered;
	}

	@Override
	public GSTINDetails getGstinDetailsFromGstinNo(String gstinNo,
			Integer primaryUserUid) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(getGstinDetailsByGstinNo);
		query.setString("gstinNo", gstinNo);
		query.setInteger("referenceId", primaryUserUid);

		@SuppressWarnings("unchecked")
		List<GSTINDetails> gstinDetailsList = (List<GSTINDetails>) query.list();

		GSTINDetails gstinDetails = null;

		if (!gstinDetailsList.isEmpty()) {
			gstinDetails = (GSTINDetails) gstinDetailsList.get(0);
		}

		logger.info("Exit");
		return gstinDetails;
	}

	@Override
	public String removeGstinLocationById(Integer locId) throws Exception {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		try {
			Session session = sessionFactory.getCurrentSession();
			GstinLocation gstinLocation = (GstinLocation) session.get(
					GstinLocation.class, locId);
			if (null != gstinLocation) {
				session.delete(gstinLocation);
			}
			response = GSTNConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}
		logger.info("Exit");
		return response;
	}

	@Override
	public List<GstinLocation> listGstinLocationDetails(Integer uId)
			throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(gstinLocationDetailsListQuery);
		query.setInteger("refGstinId", uId);

		@SuppressWarnings("unchecked")
		List<GstinLocation> gstinLocationDetailsList = (List<GstinLocation>) query
				.list();

		logger.info("Exit");
		return gstinLocationDetailsList;
	}

	@Override
	public GstinLocation listGstinLocationDetail(Integer uId) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		GstinLocation gstinLocation = null;
		Query query = session.createQuery(gstinLocationDetailQuery);
		if (uId != null) {
			query.setInteger("refGstinId", uId);

			@SuppressWarnings("unchecked")
			List<GstinLocation> gstinLocationDetailList = (List<GstinLocation>) query
					.list();
			gstinLocation = gstinLocationDetailList.get(0);

		}
		logger.info("Exit");
		return gstinLocation;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GSTINDetails> getGstinDetailsMappedForSecondaryUser(Integer uId)
			throws Exception {
		logger.info("Entry");
		List<GSTINDetails> gstinDetails = new ArrayList<GSTINDetails>();
		Session session = sessionFactory.getCurrentSession();
		// "from gst_gstin_master where id in (
		// select ref_gstin_id from gst_user_gstin_mapping where id in (
		// select gstin_id from gst_gstin_user_map where user_id = 3969))
		List<Integer> gstinIds = getGstinIds(uId);
		if (gstinIds.size() > 0) {
			try {

				String placeHoder = GSTNUtil.getPlaceHoderString(gstinIds);
				String queryString = getGstinMappedForSecondaryUser;
				queryString = queryString.replace("idsValuesToFetch",
						placeHoder);
				Query query2 = session.createQuery(queryString);
				for (int i = 0; i < gstinIds.size(); i++) {
					query2.setInteger(i, gstinIds.get(i));
				}

				gstinDetails = (List<GSTINDetails>) query2.list();
				if (!gstinDetails.isEmpty()) {
					gstinDetails = mapStateName(gstinDetails);
				}
			} catch (Exception e) {
				logger.error("Error in:", e);
				throw e;
			}
		}

		logger.info("Exit");

		return gstinDetails;
	}

	@SuppressWarnings("unchecked")
	private List<Integer> getGstinIds(Integer uId) {
		logger.info("Entry");

		Session session = sessionFactory.getCurrentSession();
		List<Integer> intGstinIdList = new ArrayList<Integer>();
		Query query1 = session.createSQLQuery(getUserGstinMapping);
		query1.setInteger("uId", uId);
		intGstinIdList = (List<Integer>) query1.list();

		logger.info("Exit");
		return intGstinIdList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GstinLocation> getSecondaryMappedLocations(Integer gstinId,
			Integer uId) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		List<GstinLocation> gstinLocationDetailsList = new ArrayList<GstinLocation>();
		// select * from gst_gstin_location_master where id in (
		// select ref_address_id from gst_user_gstin_mapping where
		// ref_gstin_id = 592 and id in (select gstin_id from gst_gstin_user_map
		// where user_id = 3969))
		try {
			Query query = session.createSQLQuery(getLocationByGstinAndUserId)
					.addEntity(GstinLocation.class);
			query.setInteger("gstinId", gstinId);
			query.setInteger("uId", uId);

			gstinLocationDetailsList = query.list();

		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}

		logger.info("Exit");
		return gstinLocationDetailsList;
	}

	@Override
	public String updateUserGstinDetails(Map<String, Object> mapValues)
			throws Exception {
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		GSTINDetails gstinDetail = new GSTINDetails();
		String GstnUserId = (String) mapValues.get("GstnUserId");
		String GrossTurnover = (String) mapValues.get("GrossTurnover");
		String CurrentTurnover = (String) mapValues.get("CurrentTurnover");
		String gstinId = (String) mapValues.get("gstinId");
		LoginMaster loginMaster = (LoginMaster) mapValues.get("loginMaster");
		try {
			Session session = sessionFactory.getCurrentSession();

			gstinDetail = getGstinDetailsFromGstinNo(gstinId,
					loginMaster.getPrimaryUserUId());
			gstinDetail.setGstnUserId(GstnUserId);
			gstinDetail.setGrossTurnover(GrossTurnover);
			gstinDetail.setCurrentTurnover(CurrentTurnover);

			session.merge(gstinDetail);
			response = GSTNConstants.SUCCESS;

		} catch (Exception e) {
			logger.error("Error in:", e);
			throw e;
		}

		logger.info("Exit");
		return response;

	}

	@Override
	public List<GSTINDetails> listGstinDetailsAndCity(Integer getuId) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		List<GSTINDetails> gstnlist = new ArrayList<GSTINDetails>();
		GSTINDetails gSTINDetails = new GSTINDetails();
		GSTINAddressMapping gSTINAddressMapping = new GSTINAddressMapping();
		
		Query query = session.createSQLQuery(gstinDetailsListQueryAndCity);
		query.setInteger("referenceId", getuId);

		@SuppressWarnings("unchecked")
		List<Object[]> gstinDetailsList =  (List<Object[]>)query.list();
	        for(int i=0; i < gstinDetailsList.size(); i++) {
	         	Object[] obj = gstinDetailsList.get(i);
	         	int id = (Integer)obj[0];
	         	String gstnNo = (String)obj[1];
	         	String nickName=(String)obj[2];
	         	int state=(Integer)obj[3];
	         	String city=(String)obj[4];
	         	gSTINDetails.setId(id);
	         	gSTINDetails.setGstinNo(gstnNo);
	         	gSTINDetails.setGstnnickname(nickName);
	         	gSTINDetails.setState(state);
	         	gSTINAddressMapping.setCity(city);
	         	gSTINDetails.setGstinAddressMapping(gSTINAddressMapping);
	        }
	        gstnlist.add(gSTINDetails);
	        
		if (!gstnlist.isEmpty()) {
			try {
				gstnlist = mapStateName(gstnlist);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("Exit");
		return gstnlist;

}
}
