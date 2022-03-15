/**
 * 
 */
package com.reliance.gstn.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Map;

import com.reliance.gstn.dao.GenerateInvoiceDAO;
import com.reliance.gstn.model.CnDnAdditionalDetails;
import com.reliance.gstn.model.CustomerDetails;
import com.reliance.gstn.model.InvoiceCnDnDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.InvoiceServiceDetails;
import com.reliance.gstn.model.PayloadCnDnDetails;
import com.reliance.gstn.model.PoDetails;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.util.AspApiConstants;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
//import com.reliance.gstn.util.LoggerUtil;

/**
 * @author Nikesh.Bansod
 *
 */
@Repository
public class GenerateInvoiceDAOImpl implements GenerateInvoiceDAO {
	
	private static final Logger logger = Logger.getLogger(GenerateInvoiceDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${invoice_detail_list_by_po_details}")
	private String invoiceDetailByPoDetail;
	
	@Value("${invoice_detail_by_org_uid}")
	private String invoiceDetailByOrgUId;
	
	@Value("${invoice_details_by_org_uid}")
	private String invoiceDetailsByOrgUId;
	
	@Value("${only_invoices_by_org_uid}")
	private String onlyInvoicesByOrgUId;
	
	@Value("${validate_invoice_by_orgId}")
	private String validateInvoiceByOrgId;
	
	@Value("${DEBIT_NOTE_ADD_SUCESS}")
	private String debitNoteAdditionSuccessful;
	
	@Value("${CREDIT_NOTE_ADD_SUCESS}")
	private String creditNoteAdditionSuccessful;
	
	@Value(value = "${update_invoice_delete_status}")
	private String updateDeleteStatus;
	
	@Value(value = "${update_invoice_cndn_delete_status}")
	private String updateDeleteCNDNStatus;
	
	@Value("${B2CL.min.amt}")
	private String b2clMinAmt;
	
	@Value("${only_billOfSupply_by_orgUId}")
	private String onlyBillOfSupplysByOrgUId;
	
	@Value("${only_rc_invoices_by_orgUId}")
	private String onlyRCInvoicesByOrgUId;
	
	@Value("${only_ecom_invoices_by_orgUId}")
	private String onlyEComInvoicesByOrgUId;
	
	@Value("${only_ecom_billOfSupply_by_orgUId}")
	private String onlyEComBillOfSupplysByOrgUId;
	
	@Value("${remove_service_items_query}")
	private String removeServiceItemsQuery;
	
	@Value("${check_invoice_number_by_orgId}")
	private String checkInvoiceNumberQuery;
	
	@Override
	public Map<String,String> addGenerateInvoice(InvoiceDetails invoiceDetails) throws Exception{
		logger.info("Entry");
		Map<String,String> responseMap = new HashMap<String,String>();
		try {
			Session session = sessionFactory.getCurrentSession();
			/*if(invoiceDetails.getPoDetails().getId() != null){
				PoDetails poDetails = (PoDetails)session.get(PoDetails.class, invoiceDetails.getPoDetails().getId());
				invoiceDetails.setPoDetails(poDetails);
			}else{
				invoiceDetails.setPoDetails(null);
			}*/
			
			CustomerDetails customerDetails = (CustomerDetails)session.get(CustomerDetails.class, invoiceDetails.getCustomerDetails().getId());
			invoiceDetails.setCustomerDetails(customerDetails);
			
			invoiceDetails.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			
			String invCategory = setInvoiceCategory(invoiceDetails);
			
			invoiceDetails.setInvCategory(invCategory);
			
			session.saveOrUpdate(invoiceDetails);
			invoiceDetails = (InvoiceDetails)session.get(InvoiceDetails.class, invoiceDetails.getId());
			responseMap.put(GSTNConstants.RESPONSE, GSTNConstants.SUCCESS);
			responseMap.put("InvoiceNumber", invoiceDetails.getInvoiceNumber());
			responseMap.put("InvoicePkId", ""+invoiceDetails.getId());
		} catch (Exception e) {
			responseMap.put(GSTNConstants.RESPONSE, GSTNConstants.FAILURE);
			logger.error("Error in:",e);
			throw e;
		}
		
		logger.info("Exit");
		return responseMap;
	}


	private String setInvoiceCategory(InvoiceDetails invoiceDetails) {
		
		String invCategory = "";
		if(!invoiceDetails.getCustomerDetails().getCustGstId().equals("") ){
			invCategory = "B2B";	//B2B			
		}else if(invoiceDetails.getInvoiceValueAfterRoundOff()>Double.valueOf(b2clMinAmt) && isInvoiceIGSTCategory(invoiceDetails)){
			invCategory = "B2CL"; 	//B2CL
		}else if(invoiceDetails.getInvoiceValueAfterRoundOff()<=Double.valueOf(b2clMinAmt)){
			invCategory = "B2CS"; 	//B2CS
		}else if(invoiceDetails.getInvoiceValueAfterRoundOff()>Double.valueOf(b2clMinAmt) && !isInvoiceIGSTCategory(invoiceDetails)){
			invCategory = "B2CS"; 	//B2CS
		}
		return invCategory;
		
	}
	
	private boolean isInvoiceIGSTCategory(InvoiceDetails invoiceDetails){
		boolean isIgst=false;
		List<InvoiceServiceDetails> invServicedetailsList = invoiceDetails.getServiceList();
		if(invServicedetailsList.get(0).getCategoryType().equalsIgnoreCase(AspApiConstants.INV_SERVICE_IGST_CATEGORY)){
			isIgst=true;
		}
		
		return isIgst;
	}	


	@Override
	public Map<String,String> compareInvoiceDate(Timestamp inputDate, String idsValuesToFetch) {
		Map<String,String> mapReponse = new HashMap<String,String>();
		boolean allow = false;
		String lastInvoiceDate = null;
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		List<Integer> ali = GSTNUtil.getListFromString(idsValuesToFetch);
		String placeHoder = GSTNUtil.getPlaceHoderString(ali);
		String queryString = "select DATEDIFF(MAX(invoice_date), '"+inputDate+"') from gst_invoice_details where ref_user_id IN (idsValuesToFetch)";
		queryString=queryString.replace("idsValuesToFetch", placeHoder);
		Query query = session.createSQLQuery(queryString);
	
		for (int i = 0; i < ali.size(); i++) {
			query.setInteger(i, ali.get(i));
		}
		
		@SuppressWarnings("unchecked")
		List<Object> viewPoDetailsList = query.list();
		
		if (!viewPoDetailsList.isEmpty()) {
			for(int i = 0; i<viewPoDetailsList.size();i++){
				Object obj = viewPoDetailsList.get(i);
				if(obj != null){
					java.math.BigInteger testVal = (java.math.BigInteger) obj;
					System.out.println("testName : "+testVal);
					Integer poId = testVal.intValue();
					if(poId > 0 ){
						allow = false;
						Timestamp lastInvoiceDateInTimestamp = getLastInvoiceDate(idsValuesToFetch);
						if(lastInvoiceDateInTimestamp != null){
							lastInvoiceDate = GSTNUtil.convertTimestampToString1(lastInvoiceDateInTimestamp);
						}
					}else{
						allow = true; 
					}
				}else{
					allow = true; 
				}
			}
		}
		mapReponse.put("allow",allow+"");
		mapReponse.put("lastInvoiceDate",lastInvoiceDate);
		logger.info("Exit");
		return mapReponse;
	}


	private Timestamp getLastInvoiceDate(String idsValuesToFetch) {
		logger.info("Entry");
		Timestamp latestInvoiceDate = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		Session session = sessionFactory.getCurrentSession();
		List<Integer> ali = GSTNUtil.getListFromString(idsValuesToFetch);
		String placeHoder = GSTNUtil.getPlaceHoderString(ali);
		String queryString = "select max(invoice_date) from gst_invoice_details where ref_user_id in (idsValuesToFetch)";
		queryString=queryString.replace("idsValuesToFetch", placeHoder);
		Query query = session.createSQLQuery(queryString);
	
		for (int i = 0; i < ali.size(); i++) {
			query.setInteger(i, ali.get(i));
		}
		
		@SuppressWarnings("unchecked")
		List<Object> viewPoDetailsList = query.list();
		
		if (!viewPoDetailsList.isEmpty()){
				Object obj = viewPoDetailsList.get(0);
				if(obj != null){
				latestInvoiceDate = (Timestamp) obj;
				System.out.println("latestInvoiceDate : "+latestInvoiceDate);
				}
		}
		
		logger.info("Exit");
		return latestInvoiceDate;
	}
	
	@SuppressWarnings({"unchecked" })
	@Override
	public List<InvoiceDetails> getInvoiceDetailsByPoDetails(PoDetails poDetail) {
		List<InvoiceDetails> invoiceDetailsList = new ArrayList<InvoiceDetails>();
		try {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(invoiceDetailByPoDetail);
		query.setInteger("poDetailId", poDetail.getId());
		invoiceDetailsList = (List<InvoiceDetails>)query.list();
		
		logger.info("Exit");
		} catch(Exception e){
			logger.error("Error in:",e);
			e.printStackTrace();
		}
		return invoiceDetailsList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceDetails> getInvoiceDetailsByOrgUId(Integer orgUId) throws Exception{
		logger.info("Entry");
		List<InvoiceDetails> invoiceDetailsList = new ArrayList<InvoiceDetails>();
		try {
			 
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(invoiceDetailByOrgUId);
			query.setInteger("orgUId", orgUId);
			invoiceDetailsList = (List<InvoiceDetails>)query.list();
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
	
		logger.info("Exit");
		return invoiceDetailsList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getInvoiceDetailByOrgUId(Integer orgUId) throws Exception{
		logger.info("Entry");
		List<Object[]> invoiceDetailsList = new ArrayList<Object[]>();
		try {
			 
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(invoiceDetailsByOrgUId);
			query.setInteger("orgUId", orgUId);
			invoiceDetailsList = (List<Object[]>)query.list();
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
	
		logger.info("Exit");
		return invoiceDetailsList;
	}
	
	@Override
	public InvoiceDetails getInvoiceDetailsById(Integer id) throws Exception{
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		InvoiceDetails invoiceDetails = null;
		try {
		    invoiceDetails = (InvoiceDetails) session.get(InvoiceDetails.class, id);
		    invoiceDetails.getAddChargesList();
		    invoiceDetails.getServiceList();
		    invoiceDetails.getCnDnList();
		    invoiceDetails.getCnDnAdditionalList();
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");
		return invoiceDetails;
	}


	@Override
	public String getLatestInvoiceNumber(String pattern, Integer orgUId) {
		logger.info("Entry");
		String latestInvoiceNo = null;
		Session session = sessionFactory.getCurrentSession();
		String queryString = "select invoice_number from gst_invoice_details where invoice_number like :pattern and  ref_org_uId = :orgUId";
		//String queryString = "select max(invoice_number) from gst_invoice_details where invoice_number like :pattern and  ref_org_uId = :orgUId";
		//String queryString = "select max(cast(invoice_number AS SIGNED)) from gst_invoice_details where invoice_number like :pattern and  ref_org_uId = :orgUId";
		Query query = session.createSQLQuery(queryString);
		query.setString("pattern", pattern+"%");
		query.setInteger("orgUId", orgUId);
		
		@SuppressWarnings("unchecked")
		List<String> viewPoDetailsList = query.list();
		
		if (!viewPoDetailsList.isEmpty()) {
		
				//Object obj = viewPoDetailsList.get(0);
				//latestInvoiceNo = (String) obj;
				latestInvoiceNo = GSTNUtil.getMaxOfInvoiceNumber(viewPoDetailsList); 
				//System.out.println("latestInvoiceNo : "+latestInvoiceNo);
		
		}
		
		logger.info("Exit");
		return latestInvoiceNo;
	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean validateInvoiceAgainstOrgId(Integer invoiceId, Integer orgUId) throws Exception{
		logger.info("Entry");
		boolean isInvoiceAllowed = false;
		List<InvoiceDetails> invoiceDetailsList = new ArrayList<InvoiceDetails>();
		try {
			 
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(validateInvoiceByOrgId);
			query.setInteger("orgUId", orgUId);
			query.setInteger("id", invoiceId);
			invoiceDetailsList = (List<InvoiceDetails>)query.list();
			if (!invoiceDetailsList.isEmpty()){
				isInvoiceAllowed = true;
			}
			
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
	
		logger.info("Exit");
		return isInvoiceAllowed;
	}


	@Override
	public Map<String, String> addCNDN(InvoiceCnDnDetails invoiceCNDNDetails, Integer invoiceId) {
		logger.info("Entry");
		Map<String,String> responseMap = new HashMap<String,String>();
		try {
			Session session = sessionFactory.getCurrentSession();
			InvoiceDetails invoiceDetails = (InvoiceDetails) session.get(InvoiceDetails.class, invoiceId);
			 
			invoiceCNDNDetails.setId(null);
			invoiceCNDNDetails.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			List<InvoiceCnDnDetails>cnDnList = null;
			cnDnList = invoiceDetails.getCnDnList();
			if(cnDnList.size() == 0){
				cnDnList = new ArrayList<InvoiceCnDnDetails>();
			}
			cnDnList.add(invoiceCNDNDetails);
			invoiceDetails.setCnDnList(cnDnList);
			session.saveOrUpdate(invoiceDetails);
			invoiceDetails = (InvoiceDetails)session.get(InvoiceDetails.class, invoiceDetails.getId());
			responseMap.put(GSTNConstants.RESPONSE, GSTNConstants.SUCCESS);
			if(invoiceCNDNDetails.getCnDnType().equals(GSTNConstants.CREDIT_NOTE)){
				responseMap.put(GSTNConstants.MESSAGE, creditNoteAdditionSuccessful);
			}else{
				responseMap.put(GSTNConstants.MESSAGE, debitNoteAdditionSuccessful);
			}
			
		} catch (Exception e) {
			responseMap.put(GSTNConstants.RESPONSE, GSTNConstants.FAILURE);
			logger.error("Error in:",e);
			throw e;
		}
		
		logger.info("Exit");
		return responseMap;
	}


	@Override
	public Integer getLatestIterationNo(Integer invoiceId) {
		logger.info("Entry");
		Integer iterationNo = null;
		Session session = sessionFactory.getCurrentSession();
		String queryString = "select max(iteration_no) from gst_invoice_cn_dn_mapping a , gst_invoice_cn_dn_details b " 
								+"where a.invoice_id = :invoiceId and "
								+"a.invoice_cn_dn_pk_id = b.invoice_cn_dn_pk_id";
		Query query = session.createSQLQuery(queryString);
		query.setInteger("invoiceId", invoiceId);
		
		@SuppressWarnings("unchecked")
		List<Object> viewPoDetailsList = query.list();
		
		if (!viewPoDetailsList.isEmpty()) {
		
				Object obj = viewPoDetailsList.get(0);
				iterationNo = (Integer) obj;
				//System.out.println("latestInvoiceNo : "+latestInvoiceNo);
		
		}
		
		logger.info("Exit");
		return iterationNo;
	}


	@Override
	public Map<String, String> addInvoiceCnDn(InvoiceDetails invoiceDetails, Integer iterationNo, String invoiceNumber) throws Exception {
		logger.info("Entry");
		Map<String,String> responseMap = new HashMap<String,String>();
		try {
			Session session = sessionFactory.getCurrentSession();
			InvoiceDetails existingInvoiceDetails = getInvoiceDetailsById(invoiceDetails.getId());
			
			//Add for cndn services list  - Start
			List<InvoiceCnDnDetails> cnDnList = null;
			cnDnList = existingInvoiceDetails.getCnDnList();
			if(cnDnList.size() == 0){
				cnDnList = new ArrayList<InvoiceCnDnDetails>();
			}
			cnDnList.addAll(invoiceDetails.getCnDnList());
			existingInvoiceDetails.setCnDnList(cnDnList);
			//Add for cndn services list  - End
			
			//Add for cndn additional details list  - Start
			List<CnDnAdditionalDetails> cnDnAdditionalList = null;
			cnDnAdditionalList = existingInvoiceDetails.getCnDnAdditionalList();
			if(cnDnAdditionalList.size() == 0){
				cnDnAdditionalList = new ArrayList<CnDnAdditionalDetails>();
			}
			cnDnAdditionalList.addAll(invoiceDetails.getCnDnAdditionalList());
			existingInvoiceDetails.setCnDnAdditionalList(cnDnAdditionalList);
			//Add for cndn additional details list  - End
			session.saveOrUpdate(existingInvoiceDetails);
			invoiceDetails = (InvoiceDetails)session.get(InvoiceDetails.class, invoiceDetails.getId());
			responseMap.put(GSTNConstants.RESPONSE, GSTNConstants.SUCCESS);
			responseMap.put("InvoiceNumber", invoiceNumber);
			responseMap.put("InvoicePkId", ""+invoiceDetails.getId());
			responseMap.put("iterationNo", ""+iterationNo);
			responseMap.put("invoiceIterationNo", ""+invoiceDetails.getIterationNo());
		} catch (Exception e) {
			responseMap.put(GSTNConstants.RESPONSE, GSTNConstants.FAILURE);
			logger.error("Error in:",e);
			throw e;
		}
		
		logger.info("Exit");
		return responseMap;
	}


	@Override
	public String getLatestCnDnInvoiceNumber(String pattern, Integer orgUId) {
		logger.info("Entry");
		String latestInvoiceNo = null;
		Session session = sessionFactory.getCurrentSession();
		String queryString = "select invoice_number from gst_invoice_cn_dn_additional_details where invoice_number like :pattern and  ref_org_uId = :orgUId";
		//String queryString = "select max(invoice_number) from gst_invoice_cn_dn_additional_details where invoice_number like :pattern and  ref_org_uId = :orgUId";
		Query query = session.createSQLQuery(queryString);
		query.setString("pattern", pattern+"%");
		query.setInteger("orgUId", orgUId);
		
		@SuppressWarnings("unchecked")
		List<String> viewPoDetailsList = query.list();
		
		if (!viewPoDetailsList.isEmpty()) {
		
				//Object obj = viewPoDetailsList.get(0);
				//latestInvoiceNo = (String) obj;
				latestInvoiceNo = GSTNUtil.getMaxOfInvoiceNumber(viewPoDetailsList); 
		}
		
		logger.info("Exit");
		return latestInvoiceNo;
	}
	
	@Override
	public String setDeleteFlagForInvoice(List<PayloadCnDnDetails> cndnInvoiceList, InvoiceDetails invoiceDetail) throws Exception{
		logger.info("Entry");
		String response = GSTNConstants.FAILURE;
		StringBuffer sb = new StringBuffer();
		String idsInString = "";
	try{
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(updateDeleteStatus);
		query.setInteger("id",invoiceDetail.getId());
		query.executeUpdate();
		
		
		if(!cndnInvoiceList.isEmpty()){
		/*for (PayloadCnDnDetails invoiceCndnDetails : cndnInvoiceList) {
			
				sb = sb.append(invoiceCndnDetails.getId()+",");
			idsInString = sb.toString();
		} 
		if(idsInString.length() > 0){
			idsInString = idsInString.substring(0, (idsInString.length()-1));
		}
		Query cndnQuery = session.createQuery(updateDeleteCNDNStatus);
		cndnQuery.setString("ids",idsInString);
		cndnQuery.executeUpdate();*/
			for (PayloadCnDnDetails invoiceCndnDetails : cndnInvoiceList) {
				
				Query cndnQuery = session.createQuery(updateDeleteCNDNStatus);
				cndnQuery.setInteger("ids",invoiceCndnDetails.getId());
				cndnQuery.executeUpdate();
		} 
			response = GSTNConstants.SUCCESS;
			}
			response = GSTNConstants.SUCCESS;
		}catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		return response;
	}


	@Override
	public List<Object[]> getDocumentListByDocType(Integer orgUId, String documentType) throws Exception {
		logger.info("Entry");
		List<Object[]> invoiceDetailsList = new ArrayList<Object[]>();
		String queryToCall = null;
		try {
			if(documentType.equals("invoice")){
				queryToCall = onlyInvoicesByOrgUId;
			}else if(documentType.equals("billOfSupply")){
				queryToCall = onlyBillOfSupplysByOrgUId;
			}else if(documentType.equals("rcInvoice")){
				queryToCall = onlyRCInvoicesByOrgUId;
			}else if(documentType.equals("eComInvoice")){
				queryToCall = onlyEComInvoicesByOrgUId;
			}else if(documentType.equals("eComBillOfSupply")){
				queryToCall = onlyEComBillOfSupplysByOrgUId;
			} 
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(queryToCall);
			query.setInteger("orgUId", orgUId);
			invoiceDetailsList = (List<Object[]>)query.list();
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
	
		logger.info("Exit");
		return invoiceDetailsList;
	}


	@Override
	public String getCnDnPkIdByCnDnNoAndIterationNo(String cndnNumber, Integer orgUId, String iterationNo) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		String queryString = "select id from CnDnAdditionalDetails where iterationNo = :iterationNo and invoiceNumber =:invoiceNumber and orgUId =:orgUId";
		Query query = session.createQuery(queryString);
		query.setInteger("orgUId", orgUId);
		query.setInteger("iterationNo", Integer.parseInt(iterationNo));
		query.setString("invoiceNumber", cndnNumber);
	
		@SuppressWarnings("unchecked")
		List<Integer> viewPoDetailsList = query.list();
		
		logger.info("Exit");
		return viewPoDetailsList.get(0)+"";
	}


	@Override
	public String saveRR(InvoiceDetails invoiceDetails) {
		logger.info("Entry");
		String status = GSTNConstants.FAILURE;
		//Map<String,String> responseMap = new HashMap<String,String>();
		try {
			Session session = sessionFactory.getCurrentSession();
			CustomerDetails customerDetails = (CustomerDetails)session.get(CustomerDetails.class, invoiceDetails.getCustomerDetails().getId());
			invoiceDetails.setCustomerDetails(customerDetails);
			invoiceDetails.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			
			String invCategory = setInvoiceCategory(invoiceDetails);
			
			invoiceDetails.setInvCategory(invCategory);
			
			session.saveOrUpdate(invoiceDetails);
			invoiceDetails = (InvoiceDetails)session.get(InvoiceDetails.class, invoiceDetails.getId());
			status = GSTNConstants.SUCCESS;
			//responseMap.put(GSTNConstants.RESPONSE, GSTNConstants.SUCCESS);
			//responseMap.put("InvoiceNumber", invoiceDetails.getInvoiceNumber());
			//responseMap.put("InvoicePkId", ""+invoiceDetails.getId());
		} catch (Exception e) {
			//responseMap.put(GSTNConstants.RESPONSE, GSTNConstants.FAILURE);
			logger.error("Error in:",e);
			throw e;
		}
		
		logger.info("Exit");
		return status;
	}


	@Override
	public String removeServiceLineItems(String removeItemsList) {
		logger.info("Entry");
		String status = GSTNConstants.FAILURE;
		Session session = sessionFactory.getCurrentSession();
		//Transaction transaction = session.beginTransaction();
		try {
			Query query = session.createQuery(removeServiceItemsQuery + "("+removeItemsList+")");
			//query.setString("serviceIds", removeItemsList);
			query.executeUpdate();
			status = GSTNConstants.SUCCESS;
		  //transaction.commit();
		} catch (Exception e) {
		  //transaction.rollback();
		  //throw t;
		  logger.error("Error in deleting service : "+e.getMessage());
		}
		
		logger.info("Exit");
		return status;
	}


	@Override
	public String checkInvoiceNumberPresent(String invoiceNumber, Integer orgUId) throws Exception {
		logger.info("Entry");
		String isInvoiceAllowed = "TRUE";
		List<InvoiceDetails> invoiceDetailsList = new ArrayList<InvoiceDetails>();
		try {
			 
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery(checkInvoiceNumberQuery);
			query.setInteger("orgUId", orgUId);
			query.setString("invoiceNumber", invoiceNumber);
			invoiceDetailsList = (List<InvoiceDetails>)query.list();
			if (!invoiceDetailsList.isEmpty()){
				isInvoiceAllowed = "FALSE";
			}
			
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
	
		logger.info("Exit");
		return isInvoiceAllowed;
	}
	
	
	
	
	
	

}
