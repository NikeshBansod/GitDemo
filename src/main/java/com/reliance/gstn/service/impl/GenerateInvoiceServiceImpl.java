/**
 * 
 */
package com.reliance.gstn.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.reliance.gstn.dao.GenerateInvoiceDAO;
import com.reliance.gstn.model.CnDnAdditionalDetails;
import com.reliance.gstn.model.InvoiceAdditionalChargeDetails;
import com.reliance.gstn.model.InvoiceCnDnDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.InvoiceServiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.PayloadCnDnDetails;
import com.reliance.gstn.model.PoDetails;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.GenerateInvoiceService;
import com.reliance.gstn.service.UploadAspService;
import com.reliance.gstn.util.AspApiConstants;
import com.reliance.gstn.util.EncodingDecodingUtil;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.WebserviceCallUtil;

/**
 * @author Nikesh.Bansod
 *
 */
@Service
public class GenerateInvoiceServiceImpl implements GenerateInvoiceService {
	
	private static final Logger logger = Logger.getLogger(GenerateInvoiceServiceImpl.class);
	
	@Value("${${env}.tiny.api.url}")
	private String tinyApiUrl;
	
	@Value("${${env}.tiny.api.user}")
	private String tinyApiUser;
	
	@Value("${${env}.tiny.api.pwd}")
	private String tinyApiPwd;
	
	@Value("${${env}.tiny.getPdf.url}")
	private String tinygetPdfUrl;
	
	@Autowired
	GenerateInvoiceDAO generateInvoiceDAO;

	@Autowired
	UploadAspService uploadAspService;
	
	
	@Value("${${env}.gstr1.url}")
	private String gstr1Url;

	@Value("${${env}.asp.client.secret.key}")
	private String aspClientSecretKey;
	
	@Value("${${env}.aspclient.id}")
	private String aspClientId;
	
	@Value("${source.device.string}")
	private String sourceDeviceString;
	
	@Value("${source.device}")
	private String sourceDevice;
	
	@Value("${${env}.app.code}")
	private String appCode;
	
	@Value("${device.string}")
	private String deviceString;
	
	@Value("${B2CL.min.amt}")
	private String b2clMinAmt;
	
	@Value("${gstr.client.user}")
	private String gstrClientUserId;
	
	@Value("${gstr.client.pwd}")
	private String gstrClientPwd;
	
	@Override
	@Transactional
	public Map<String,String> addGenerateInvoice(InvoiceDetails invoiceDetails) throws Exception{
		return generateInvoiceDAO.addGenerateInvoice( invoiceDetails);
	}

	@Override
	@Transactional
	public Map<String,String> compareInvoiceDate(Timestamp inputDate, String uIdToString) {
		return generateInvoiceDAO.compareInvoiceDate( inputDate, uIdToString);
	}

	@Override
	@Transactional
	public  List<InvoiceDetails> getInvoiceDetailsByPoDetails(PoDetails poDetail) {
		return generateInvoiceDAO.getInvoiceDetailsByPoDetails(poDetail);
	}

	@Override
	@Transactional
	public List<InvoiceDetails> getInvoiceDetailsByOrgUId(Integer orgUId) throws Exception{
		return generateInvoiceDAO.getInvoiceDetailsByOrgUId(orgUId);
	}

	@Override
	@Transactional
	public List<Object[]> getInvoiceDetailByOrgUId(Integer orgUId) throws Exception{
		return generateInvoiceDAO.getInvoiceDetailByOrgUId(orgUId);
	}
	
	@Override
	@Transactional
	public InvoiceDetails getInvoiceDetailsById(Integer id)  throws Exception{
		return generateInvoiceDAO.getInvoiceDetailsById(id);
	}

	@Override
	@Transactional
	public String getLatestInvoiceNumber(String pattern, Integer orgUId) {
		
		return generateInvoiceDAO.getLatestInvoiceNumber(pattern, orgUId);
	}

	@Override
	@Transactional
	public boolean validateInvoiceAgainstOrgId(Integer invoiceId, Integer orgUId) throws Exception{
		
		return generateInvoiceDAO.validateInvoiceAgainstOrgId( invoiceId,  orgUId);
	}

	@Override
	@Transactional
	public Map<String, String> addCNDN(InvoiceCnDnDetails invoiceCNDNDetails, Integer invoiceId) {
		
		return  generateInvoiceDAO.addCNDN(invoiceCNDNDetails,invoiceId);
	}

	@Override
	@Transactional
	public Map<String, String> addInvoiceCnDn(InvoiceDetails invoiceDetails, LoginMaster loginMaster,String invoiceNumber, String cnDnFooter) throws Exception{
		Integer iterationNo = generateInvoiceDAO.getLatestIterationNo(invoiceDetails.getId());
		if(iterationNo == null){
			iterationNo = 1;
		}else{
			iterationNo = iterationNo +1;
		}
		invoiceDetails.setCnDnAdditionalList(setCnDnAdditionalDetailsObject(iterationNo, loginMaster,invoiceDetails,invoiceNumber, cnDnFooter));
		
		invoiceDetails.setCnDnList(getInvoiceCnDnDataObject(invoiceDetails.getCnDnList(), iterationNo, loginMaster,invoiceNumber));
		
		
		return generateInvoiceDAO.addInvoiceCnDn(invoiceDetails,iterationNo,invoiceNumber);
	}

	private List<CnDnAdditionalDetails> setCnDnAdditionalDetailsObject(Integer iterationNo, LoginMaster loginMaster,InvoiceDetails invoiceDetails,String invoiceNumber, String cnDnFooter) {
		List<CnDnAdditionalDetails> cndnAddList = new ArrayList<CnDnAdditionalDetails>();
		CnDnAdditionalDetails cndnAddDetails = new CnDnAdditionalDetails();
		for(InvoiceCnDnDetails a : invoiceDetails.getCnDnList()){
			cndnAddDetails.setCnDnType(a.getCnDnType());
			cndnAddDetails.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			cndnAddDetails.setCreatedBy(loginMaster.getuId().toString());
			cndnAddDetails.setIterationNo(iterationNo);
			cndnAddDetails.setReason(a.getReason());
			cndnAddDetails.setRegime(a.getRegime());
			cndnAddDetails.setInvoiceValue(invoiceDetails.getInvoiceValue());
			cndnAddDetails.setInvoiceDate(GSTNUtil.convertStringInddMMyyyyToTimestamp(invoiceDetails.getInvoiceDateInString()));
			cndnAddDetails.setInvoiceNumber(invoiceNumber);
			cndnAddDetails.setInvoiceValueAfterRoundOff(invoiceDetails.getInvoiceValueAfterRoundOff());
			cndnAddDetails.setAmountAfterDiscount(invoiceDetails.getAmountAfterDiscount());
			cndnAddDetails.setTotalTax(invoiceDetails.getTotalTax());
			cndnAddDetails.setOrgUId(loginMaster.getOrgUId());
			cndnAddDetails.setGeneratedThrough(loginMaster.getLoggedInThrough());
			cndnAddDetails.setFooter(cnDnFooter);
			cndnAddDetails.setIsAdditionalChargePresent(invoiceDetails.getCndnIsAdditionalChargePresent());
			cndnAddList.add(cndnAddDetails);
			break;
		}
		return cndnAddList;
	}

	private List<InvoiceCnDnDetails> getInvoiceCnDnDataObject(List<InvoiceCnDnDetails> cnDnList,Integer iterationNo, LoginMaster loginMaster,String invoiceNumber) {
		for(InvoiceCnDnDetails a : cnDnList){
			a.setCreatedBy(loginMaster.getuId().toString());
			a.setIterationNo(iterationNo);
			a.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			a.setOrgUId(loginMaster.getOrgUId());
			a.setCndnNumber(invoiceNumber);
		}
		return cnDnList;
	}

	@Override
	@Transactional
	public Map<String, String> verifyInvoiceCnDn(InvoiceDetails invoiceDetails) throws Exception {
		logger.info("Entry");
		Map<String,String> responseMap = new HashMap<String,String>();
		try {
			
			InvoiceDetails existingInvoiceDetails = generateInvoiceDAO.getInvoiceDetailsById(invoiceDetails.getId());

			responseMap = validateInvoiceCnDn(existingInvoiceDetails,invoiceDetails.getCnDnList(),invoiceDetails.getCnDnType());
			responseMap.put(GSTNConstants.RESPONSE, GSTNConstants.SUCCESS);
		} catch (Exception e) {
			responseMap.put(GSTNConstants.RESPONSE, GSTNConstants.FAILURE);
			logger.error("Error in:",e);
			throw e;
		}
		
		logger.info("Exit");
		return responseMap;
	}

	private Map<String, String> validateInvoiceCnDn(InvoiceDetails existingInvoiceDetails, List<InvoiceCnDnDetails> cnDnList,String cndnType) {
		Map<String,String> responseMap = new HashMap<String,String>();
		responseMap.put(GSTNConstants.STATUS, GSTNConstants.SUCCESS);	
		 Map<Integer,Double> cnDnMap = new HashMap<Integer,Double>();
		 double valueAfterTax = 0;
		 double taxRate = 0;
		 double cndnValuePreviouslyAdded = 0;
		 double totalPreviousCnDnValue = 0d;
		 
		 if(cndnType.equals(GSTNConstants.CREDIT_NOTE)){
			 if((existingInvoiceDetails.getCnDnList() != null) && (existingInvoiceDetails.getCnDnList().size() > 0)){
					for(InvoiceCnDnDetails a : existingInvoiceDetails.getCnDnList()){
						if(a.getCnDnType().equals(GSTNConstants.CREDIT_NOTE)){
							if (!cnDnMap.containsKey(a.getServiceId())) {
								cnDnMap.put(a.getServiceId(), a.getValueAfterTax());
							} else {
								cnDnMap.put(a.getServiceId(), cnDnMap.get(a.getServiceId()) + a.getValueAfterTax());
							}
							
						}
						
					}		
					
				}
				
				for(InvoiceServiceDetails service : existingInvoiceDetails.getServiceList()){
					//taxRate = (service.getSgstPercentage() + service.getUgstPercentage() + service.getCgstPercentage() + service.getIgstPercentage());
					//valueAfterTax = (service.getPreviousAmount() + (service.getPreviousAmount() * taxRate) / 100);
					valueAfterTax = service.getAmount() + service.getTaxAmount();
					service.setValueAfterTax(valueAfterTax);
					if((existingInvoiceDetails.getCnDnList() != null) && (existingInvoiceDetails.getCnDnList().size() > 0)){
						cndnValuePreviouslyAdded = 0;
						for (Map.Entry<Integer, Double> pair : cnDnMap.entrySet()) {
						    if(pair.getKey().equals(service.getId()) ){
						    	cndnValuePreviouslyAdded = pair.getValue();
						    	totalPreviousCnDnValue = totalPreviousCnDnValue + cndnValuePreviouslyAdded;
							}
						}
					}
					service.setCndnValuePreviouslyAdded(cndnValuePreviouslyAdded);
					
				}
				
				for(InvoiceCnDnDetails cnDn : cnDnList){
					for(InvoiceServiceDetails service : existingInvoiceDetails.getServiceList()){
						if(cnDn.getServiceId().equals(service.getId())){
							totalPreviousCnDnValue = totalPreviousCnDnValue + cnDn.getValueAfterTax();
							if(((service.getCndnValuePreviouslyAdded()+(cnDn.getValueAfterTax()))-(service.getValueAfterTax())) > 0 ){
								responseMap.put(GSTNConstants.STATUS, GSTNConstants.FAILURE);
								responseMap.put(GSTNConstants.MESSAGE, "Total of previous cn values plus current cn invoice value exceeds total invoice value for selected item.");
								logger.info("Error in Invoice Id : "+existingInvoiceDetails.getId()+" : Total of previous cn values( "+service.getCndnValuePreviouslyAdded()+" ) plus current cn invoice value( "+cnDn.getValueAfterTax()+" ) exceeds total invoice value ( "+service.getValueAfterTax()+" )for service Id : "+service.getId());
								break;
							}
						}
					}
				}
				
				//check if all total of all cn invoice values exceeds original invoice value - Start 
				
				double originalInvoiceValue = existingInvoiceDetails.getInvoiceValue();
				if(originalInvoiceValue < totalPreviousCnDnValue){
					responseMap.put(GSTNConstants.STATUS, GSTNConstants.FAILURE);
					responseMap.put(GSTNConstants.MESSAGE, "Total of all cn values exceeds original invoice value.");
					logger.info("Error in Invoice Id : "+existingInvoiceDetails.getId()+" : Total of all cn values ( "+totalPreviousCnDnValue+" )exceeds original invoice value( "+originalInvoiceValue+" ).");
					
				}
				//check if all total of all cn invoice values exceeds original invoice value - End
		 }
		
		
		
		
		
		return responseMap;
	}

	@Override
	@Transactional
	public String getLatestCnDnInvoiceNumber(String pattern, Integer orgUId) {
		
		return generateInvoiceDAO.getLatestCnDnInvoiceNumber( pattern,  orgUId);
	}

	@Override
	@Transactional
	public String deleteInvoice(LoginMaster loginMaster, InvoiceDetails invoiceDetail) throws Exception {
		logger.info("Entry");
		 String response="";
		 //1.Check whether Invoice is already uploaded to GSTN
		 // if uploaded show the error msg We need to ensure that we handle this case.
		 //if not uploaded ask GSTN to set flag "D" against invoice
		 try{
			 List<PayloadCnDnDetails> cndnInvoiceList = uploadAspService.getCndnDetailsByInvoiceId(invoiceDetail.getId());
		 String orgName = uploadAspService.getOrgNameById(loginMaster.getOrgUId());
		 Map<Object, Object> mapValues = new HashMap<Object, Object>(); 
		 response = uploadAspService.getResponseForDeleteInvoice(invoiceDetail, orgName, mapValues, cndnInvoiceList, loginMaster);
		 if(response.equalsIgnoreCase(AspApiConstants.GSTR1_STATUS_SERVICE_RESPONSE)){
			response = generateInvoiceDAO.setDeleteFlagForInvoice(cndnInvoiceList, invoiceDetail); 
		 }
		 } catch(Exception e){
			 logger.error("Error in:",e);
				throw e;
		 }
		logger.info("Exit");
		return response;
	}


	@Override
	public String getTinyUrl(String invId) throws Exception {
		
		logger.info("Entry:");
		String response ="";
		Map<String, String> headersMap = new HashMap<String, String>();
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		 String encryptedInvId=EncodingDecodingUtil.encodeString(invId);
		String inputData="username="+tinyApiUser+"&password="+tinyApiPwd+"&url="+tinygetPdfUrl+encryptedInvId+"&action=shorturl&format=json&keyword="+System.currentTimeMillis()+"";
		response=WebserviceCallUtil.callHttpsWebservice(tinyApiUrl, headersMap, inputData, extraParams);
		logger.debug("response="+response);
		logger.info("Exit:");
		return response;
	}

	@Override
	public String fetchInvoiceinXml(InvoiceDetails invoiceDetails, UserMaster user) throws Exception {
		double cessAmt = 0;
		double taxableAmt = 0;
		StringBuffer outputStr1 = new StringBuffer();
		StringBuffer outputStr = new StringBuffer(
				"<ENVELOPE>"
					+ "<HEADER>"
						+ "<TALLYREQUEST>Import Data</TALLYREQUEST>"
					+ "</HEADER>"
					+ "<BODY>"
						+ "<IMPORTDATA>"
							+ "<REQUESTDESC>"
								+ "<REPORTNAME>Voucher</REPORTNAME>"
								+ "<STATICVARIABLES>"
									+ "<SVCURRENTCOMPANY>Import</SVCURRENTCOMPANY>"
								+ "</STATICVARIABLES>"
							+ "</REQUESTDESC>"
							+ "<REQUESTDATA>"
								+ "<TALLYMESSAGE xmlns:UDF=\"TallyUDF\">"
									+ "<VOUCHER VCHTYPE=\"Sales\" ACTION=\"Create\" OBJVIEW=\"Invoice Voucher View\">"
										+ "<VOUCHERTYPENAME>Invoice</VOUCHERTYPENAME>" 
										+ "<VOUCHERNUMBER>"+invoiceDetails.getInvoiceNumber()+"</VOUCHERNUMBER>"
										+ "<DATE>"+GSTNUtil.getDateAsPerTallyFormat1(invoiceDetails.getInvoiceDate())+"</DATE>"
										+ "<REFERENCE>Reference No of sales</REFERENCE>"
										+ "<PERSISTEDVIEW>Invoice Voucher View</PERSISTEDVIEW>"
										+ "<PARTYNAME>"+user.getOrganizationMaster().getOrgName()+"</PARTYNAME>"
										/*+ "<BASICSHIPDOCUMENTNO>"+invoiceDetails.getInvoiceNumber()+"</BASICSHIPDOCUMENTNO>"
										+ "<BASICSHIPPEDBY>Desptached Thru</BASICSHIPPEDBY>"
										+ "<BASICFINALDESTINATION>"+invoiceDetails.getServiceCountry()+"</BASICFINALDESTINATION>"
										+ "<BILLOFLADINGNO>BillOfLanding</BILLOFLADINGNO>"
										+ "<BILLOFLADINGDATE>"+GSTNUtil.getDateAsPerTallyFormat1(invoiceDetails.getInvoiceDate())+"</BILLOFLADINGDATE>"
										+ "<BASICSHIPVESSELNO>MotorVehicleNo</BASICSHIPVESSELNO>"
										+ "<BASICDUEDATEOFPYMT>ModeofPayment</BASICDUEDATEOFPYMT>"
										+ "<BASICORDERREF>OtherReferences</BASICORDERREF>"
										+ "<BASICORDERTERMS.LIST TYPE=\"String\">"
									       + "<BASICORDERTERMS>TermsOfDelivery in OrderDetails Line 1</BASICORDERTERMS>"
									       + "<BASICORDERTERMS>TermsOfDelivery in OrderDetails Line 2</BASICORDERTERMS>"
									    + "</BASICORDERTERMS.LIST>"		*/
									    + "<BASICBUYERNAME>"+invoiceDetails.getCustomerDetails().getCustName()+"</BASICBUYERNAME>"   
									    + "<BASICBUYERADDRESS.LIST TYPE=\"String\">"
								           + "<BASICBUYERADDRESS>"+invoiceDetails.getCustomerDetails().getCustAddress1()+"</BASICBUYERADDRESS>"
								        + "</BASICBUYERADDRESS.LIST>"
								        + "<CONSIGNEESTATENAME>"+invoiceDetails.getServiceCountry()+"</CONSIGNEESTATENAME>"    
								        + "<CONSIGNEEGSTIN>"+invoiceDetails.getCustomerDetails().getCustGstId()+"</CONSIGNEEGSTIN>"
								        + "<PARTYLEDGERNAME>"+user.getOrganizationMaster().getOrgName()+"</PARTYLEDGERNAME>"
								        + "<BASICBASEPARTYNAME>"+user.getOrganizationMaster().getOrgName()+"</BASICBASEPARTYNAME>"
								        + "<ADDRESS.LIST TYPE=\"String\">"
								        	+ "<ADDRESS>"+invoiceDetails.getCustomerDetails().getCustAddress1()+"</ADDRESS>"
								        	+ "<ADDRESS>"+invoiceDetails.getCustomerDetails().getCustCity()+","+invoiceDetails.getCustomerDetails().getCustState()+"</ADDRESS>"
								        + "</ADDRESS.LIST>"
								        + "<COUNTRYOFRESIDENCE>India</COUNTRYOFRESIDENCE>");
					if(StringUtils.isNotBlank(user.getOrganizationMaster().getState())){
						outputStr.append("<STATENAME>"+user.getOrganizationMaster().getState()+"</STATENAME>");
					}else{
						outputStr.append("<STATENAME></STATENAME>");
					}
						outputStr.append("<PLACEOFSUPPLY>"+invoiceDetails.getServiceCountry()+"</PLACEOFSUPPLY>");
					if(StringUtils.isNotBlank(invoiceDetails.getCustomerDetails().getCustGstId())){
						outputStr.append("<PARTYGSTIN>"+invoiceDetails.getGstnStateIdInString()+"</PARTYGSTIN>");	
					}else{
						outputStr.append("<PARTYGSTIN></PARTYGSTIN>");
					}
						outputStr.append("<BASICDATETIMEOFINVOICE>"+GSTNUtil.getDateAsPerTallyFormat2(invoiceDetails.getInvoiceDate())+"</BASICDATETIMEOFINVOICE>"
								        + "<BASICDATETIMEOFREMOVAL>"+GSTNUtil.getDateAsPerTallyFormat2(invoiceDetails.getInvoiceDate())+"</BASICDATETIMEOFREMOVAL>");
					if(StringUtils.isNotBlank(invoiceDetails.getFooterNote())){
						outputStr.append("<NARRATION>"+invoiceDetails.getFooterNote()+"</NARRATION>");
					}else{
						outputStr.append("<NARRATION></NARRATION>");
					}				    
						
					    outputStr.append( "<ISINVOICE>Yes</ISINVOICE>"
									    /*
									    <EWAYBILLDETAILS.LIST>
									       <CONSIGNORADDRESS.LIST TYPE="String">
									        <CONSIGNORADDRESS>Huavdvaygsfv]</CONSIGNORADDRESS>
									        <CONSIGNORADDRESS>Kefiwhiuewhu</CONSIGNORADDRESS>
									       </CONSIGNORADDRESS.LIST>
									       <CONSIGNEEADDRESS.LIST TYPE="String">
									        <CONSIGNEEADDRESS>Consignee Address Line 1, Consignee Address Line 2</CONSIGNEEADDRESS>
									        <CONSIGNEEADDRESS>Address Line 2 of Eway Bill Shipped to</CONSIGNEEADDRESS>
									       </CONSIGNEEADDRESS.LIST>
									       <BILLDATE>20180602</BILLDATE>
									       <DOCUMENTTYPE>Tax Invoice</DOCUMENTTYPE>
									       <CONSIGNEEGSTIN>27ASDCL1456D1Z4</CONSIGNEEGSTIN>
									       <CONSIGNEESTATENAME>Maharashtra</CONSIGNEESTATENAME>
									       <CONSIGNEEPINCODE>412309</CONSIGNEEPINCODE>
									       <BILLNUMBER>EWay Bill N1</BILLNUMBER>
									       <SUBTYPE>Supply</SUBTYPE>
									       <BILLSTATUS>Generated by me</BILLSTATUS>
									       <CONSIGNORNAME>Import</CONSIGNORNAME>
									       <CONSIGNORPLACE>Mumbai</CONSIGNORPLACE>
									       <CONSIGNORPINCODE>400054</CONSIGNORPINCODE>
									       <CONSIGNORGSTIN>27AAACP7879D1Z4</CONSIGNORGSTIN>
									       <CONSIGNORSTATENAME>Maharashtra</CONSIGNORSTATENAME>
									       <CONSIGNEENAME>Apple INC</CONSIGNEENAME>
									       <CONSIGNEEPLACE>Pune</CONSIGNEEPLACE>
									       <TRANSPORTDETAILS.LIST>
									        <DOCUMENTDATE>20180605</DOCUMENTDATE>
									        <TRANSPORTERID>TransporterID12</TRANSPORTERID>
									        <REMARKS>Remarks at Eway</REMARKS>
									        <TRANSPORTERNAME>RATNA ROADLINES</TRANSPORTERNAME>
									        <TRANSPORTMODE>Road</TRANSPORTMODE>
									        <VEHICLENUMBER>MotorVehicleNo</VEHICLENUMBER>
									        <DOCUMENTNUMBER>BillOfLanding</DOCUMENTNUMBER>
									        <PLACEOFCHANGE>PlaceOfChange</PLACEOFCHANGE>
									        <STATEOFCHANGE>Maharashtra</STATEOFCHANGE>
									        <REASONOFCHANGE>Due to Transhipment</REASONOFCHANGE>
									        <VEHICLETYPE>Regular</VEHICLETYPE>
									        <DISTANCE> 300</DISTANCE>
									       </TRANSPORTDETAILS.LIST>
									      </EWAYBILLDETAILS.LIST>
									     * */
									    + "<LEDGERENTRIES.LIST>"
											+ "<LEDGERNAME>"+user.getOrganizationMaster().getOrgName()+"</LEDGERNAME>"
											+ "<ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>"
											+ "<AMOUNT>-"+invoiceDetails.getInvoiceValueAfterRoundOff()+"</AMOUNT>"
											+ "<BILLALLOCATIONS.LIST>"
												+ "<NAME>"+invoiceDetails.getInvoiceNumber()+"</NAME>"
												+ "<BILLTYPE>New Ref</BILLTYPE>"
												+ "<AMOUNT>-"+invoiceDetails.getInvoiceValueAfterRoundOff()+"</AMOUNT>"
											+ "</BILLALLOCATIONS.LIST>"
										+ "</LEDGERENTRIES.LIST>");
					    for(InvoiceAdditionalChargeDetails addChgItem : invoiceDetails.getAddChargesList()){
					     outputStr.append("<LEDGERENTRIES.LIST>"
								         + "<LEDGERNAME>"+addChgItem.getAdditionalChargeName()+"</LEDGERNAME>"
								         + "<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
								         + "<AMOUNT>"+addChgItem.getAdditionalChargeAmount()+"</AMOUNT>"
								        + "</LEDGERENTRIES.LIST>"
					     );
					    }
					    
					    for(InvoiceServiceDetails item : invoiceDetails.getServiceList()){
							cessAmt = cessAmt + item.getCess();
							taxableAmt = taxableAmt + (item.getAmount() - item.getAdditionalAmount());
						}
					    
						if(!invoiceDetails.getGstnStateId().equals(invoiceDetails.getDeliveryPlace())){
						outputStr.append( "<LEDGERENTRIES.LIST>"
										     + "<LEDGERNAME>IGST</LEDGERNAME>"
										     + "<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
										     + "<AMOUNT>"+(invoiceDetails.getTotalTax() - cessAmt)+"</AMOUNT>"
										     + "<VATEXPAMOUNT>0</VATEXPAMOUNT>"
										+ "</LEDGERENTRIES.LIST>");
						}else{
							String taxAmt = GSTNUtil.convertDoubleTo2DecimalPlaces((invoiceDetails.getTotalTax() - cessAmt)/2);
						outputStr.append( "<LEDGERENTRIES.LIST>"
										     + "<LEDGERNAME>CGST</LEDGERNAME>"
										     + "<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
										     + "<AMOUNT>"+taxAmt+"</AMOUNT>"
										     + "<VATEXPAMOUNT>0</VATEXPAMOUNT>"
										+ "</LEDGERENTRIES.LIST>"
										+ "<LEDGERENTRIES.LIST>"
										     + "<LEDGERNAME>SGST</LEDGERNAME>"
										     + "<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
										     + "<AMOUNT>"+taxAmt+"</AMOUNT>"
										     + "<VATEXPAMOUNT>0</VATEXPAMOUNT>"
										+ "</LEDGERENTRIES.LIST>");	
						}
						
										
										
							for(InvoiceServiceDetails item : invoiceDetails.getServiceList()){
								outputStr1.append(
										"<ALLINVENTORYENTRIES.LIST>"
											   + "<STOCKITEMNAME>"+item.getServiceIdInString()+"</STOCKITEMNAME>"
											   + "<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
											   + "<RATE>"+item.getRate()+"/"+item.getUnitOfMeasurement()+"</RATE>"
											   + "<AMOUNT>"+(item.getRate() * item.getQuantity())+"</AMOUNT>"
											   + "<ACTUALQTY>"+item.getQuantity()+" "+item.getUnitOfMeasurement()+"</ACTUALQTY>"
											   + "<BILLEDQTY>"+item.getQuantity()+" "+item.getUnitOfMeasurement()+"</BILLEDQTY>"
											   /*+ "<BATCHALLOCATIONS.LIST>"
												  + "<GODOWNNAME>Main Location</GODOWNNAME>"
												  + "<BATCHNAME>Primary Batch</BATCHNAME>"
												  + "<AMOUNT>1000.00</AMOUNT>"
												  + "<ACTUALQTY>50 PC</ACTUALQTY>"
												  + "<BILLEDQTY>50 PC</BILLEDQTY>"
											   + "</BATCHALLOCATIONS.LIST>"*/
								);	
								if(!invoiceDetails.getGstnStateId().equals(invoiceDetails.getDeliveryPlace())){
									outputStr1.append(
												"<ACCOUNTINGALLOCATIONS.LIST>"
											       		+ "<LEDGERNAME>Sales</LEDGERNAME>"
											       		+ "<GSTOVRDNNATURE>Inter Sales Taxable</GSTOVRDNNATURE>"
											       		+ "<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
											       		+ "<AMOUNT>"+taxableAmt+"</AMOUNT>"
											       		/*+ "<RATEDETAILS.LIST>"
											       			+ "<GSTRATEDUTYHEAD>Integrated Tax</GSTRATEDUTYHEAD>"
												        + "</RATEDETAILS.LIST>"
												        + "<RATEDETAILS.LIST>"
												         	+ "<GSTRATEDUTYHEAD>Central Tax</GSTRATEDUTYHEAD>"
												        + "</RATEDETAILS.LIST>"
												        + "<RATEDETAILS.LIST>"
												         	+ "<GSTRATEDUTYHEAD>State Tax</GSTRATEDUTYHEAD>"
												        + "</RATEDETAILS.LIST>"
												        + "<RATEDETAILS.LIST>"
												        	+ "<GSTRATEDUTYHEAD>Cess</GSTRATEDUTYHEAD>"
												        + "</RATEDETAILS.LIST>"*/
										           + "</ACCOUNTINGALLOCATIONS.LIST>"
									         + "</ALLINVENTORYENTRIES.LIST>"	
											
									);
								}else{
									outputStr1.append(
												"<ACCOUNTINGALLOCATIONS.LIST>"
											       		+ "<LEDGERNAME>Sales</LEDGERNAME>"
											       		+ "<GSTOVRDNNATURE>Sales Taxable</GSTOVRDNNATURE>"
											       		+ "<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
											       		+ "<AMOUNT>"+taxableAmt+"</AMOUNT>"
											       		/*+ "<RATEDETAILS.LIST>"
											       			+ "<GSTRATEDUTYHEAD>Integrated Tax</GSTRATEDUTYHEAD>"
												        + "</RATEDETAILS.LIST>"
												        + "<RATEDETAILS.LIST>"
												         	+ "<GSTRATEDUTYHEAD>Central Tax</GSTRATEDUTYHEAD>"
												        + "</RATEDETAILS.LIST>"
												        + "<RATEDETAILS.LIST>"
												         	+ "<GSTRATEDUTYHEAD>State Tax</GSTRATEDUTYHEAD>"
												        + "</RATEDETAILS.LIST>"
												        + "<RATEDETAILS.LIST>"
												        	+ "<GSTRATEDUTYHEAD>Cess</GSTRATEDUTYHEAD>"
												        + "</RATEDETAILS.LIST>"*/
										           + "</ACCOUNTINGALLOCATIONS.LIST>"
									         + "</ALLINVENTORYENTRIES.LIST>"
											
											
									);
								}
										       
										
								
							}
							
							outputStr.append( 
									"<LEDGERENTRIES.LIST>"
									     + "<LEDGERNAME>Cess</LEDGERNAME>"
									     + "<ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>"
									     + "<AMOUNT>"+cessAmt+"</AMOUNT>"
									     + "<VATEXPAMOUNT>0</VATEXPAMOUNT>"
									+ "</LEDGERENTRIES.LIST>");
							if(invoiceDetails.getInvoiceValue() != invoiceDetails.getInvoiceValueAfterRoundOff()){
								double roundOff = (double)invoiceDetails.getInvoiceValue() - invoiceDetails.getInvoiceValueAfterRoundOff();
								String roundingOffPositiveOrNegative = "Yes";
								if(roundOff < 0 ){
									roundingOffPositiveOrNegative = "No";
									roundOff = roundOff * (-1);
								}
					outputStr.append("<LEDGERENTRIES.LIST>"
								      + "<LEDGERNAME>Rounding Off</LEDGERNAME>"
								      + "<ISDEEMEDPOSITIVE>"+roundingOffPositiveOrNegative+"</ISDEEMEDPOSITIVE>"
								      + "<ROUNDTYPE>Normal Rounding</ROUNDTYPE>"
								      + "<ROUNDLIMIT> 1</ROUNDLIMIT>"
								      + "<AMOUNT>"+String.format("%.02f", roundOff)+"</AMOUNT>"
								      + "</LEDGERENTRIES.LIST>"
							   );	      
							}
							outputStr.append(outputStr1);
									outputStr.append(
											 
									 "</VOUCHER>"
								+ "</TALLYMESSAGE>"
							+ "</REQUESTDATA>"
						+ "</IMPORTDATA>"
					+ "</BODY>"
				+ "</ENVELOPE>");
		return outputStr.toString();
	}

	@Override
	@Transactional
	public List<Object[]> getDocumentListByDocType(Integer orgUId, String documentType) throws Exception{
		return generateInvoiceDAO.getDocumentListByDocType(orgUId, documentType);
	}

	@Override
	@Transactional
	public String getCnDnPkIdByCnDnNoAndIterationNo(String cndnNumber, Integer orgUId, String iterationNo) {
		// TODO Auto-generated method stub
		return generateInvoiceDAO.getCnDnPkIdByCnDnNoAndIterationNo(cndnNumber, orgUId, iterationNo);
	}

	@Override
	@Transactional
	public String saveRR(InvoiceDetails invoiceDetails) {
		// TODO Auto-generated method stub
		return generateInvoiceDAO.saveRR(invoiceDetails);
	}

	@Override
	@Transactional
	public String removeServiceLineItems(String removeItemsList) {
		// TODO Auto-generated method stub
		return generateInvoiceDAO.removeServiceLineItems(removeItemsList) ;
	}

	@Override
	@Transactional
	public String checkInvoiceNumberPresent(String invoiceNumber, Integer orgUId) throws Exception {
		return generateInvoiceDAO.checkInvoiceNumberPresent( invoiceNumber, orgUId);
	}
}
