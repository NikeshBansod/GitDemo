
package com.reliance.gstn.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.reliance.gstn.model.CnDnAdditionalDetails;
import com.reliance.gstn.model.CustomerDetails;
import com.reliance.gstn.model.GSTINAddressMapping;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.GstinLocation;
import com.reliance.gstn.model.InvoiceAdditionalChargeDetails;
import com.reliance.gstn.model.InvoiceCnDnDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.InvoiceServiceDetails;
import com.reliance.gstn.model.MapSegregate;
import com.reliance.gstn.model.OrganizationMaster;
import com.reliance.gstn.model.UserMaster;
 
public class GenerateInvoicePdf {
	
	private static final Logger logger = Logger.getLogger(GenerateInvoicePdf.class);
	
	@Value("${${env}.PATH_FOR_INVOICE_PDF}")
	private static String directoryPath;
	
    public static final String DEST = "C:/apps/gstn/invoice/";
    
    public static final String IMAGE_PATH = "C:/apps/gstn/logo/212/logo_212.JPEG";
 
    /*public static void main(String[] args) throws IOException, DocumentException {
    	
    	InvoiceDetails invoiceDetails = getInvoiceDetailsDummy();
    	GSTINDetails gstinDetails = getDummyGSTINDetails();
    	UserMaster user = getDummyUserMaster(); 
    	String customerStateCode = "MH";
    	Map<String,Map<String,Double>> gstMap = GSTNUtil.convertListToMap(invoiceDetails.getServiceList());
    	String amtInWords = "Twenty Seven Thousand Nine Hundred and Twenty Rupees  Only";
    	generatePdf(invoiceDetails,gstinDetails,user,customerStateCode,gstMap,amtInWords,DEST,IMAGE_PATH);
    	
    }*/
    
  public static void main(String[] args) throws IOException, DocumentException {
    	
    	InvoiceDetails invoiceDetails = getInvoiceDetailsDummy();
    	GSTINDetails gstinDetails = getDummyGSTINDetails();
    	UserMaster user = getDummyUserMaster(); 
    	String customerStateCode = "MH";
    	Map<String,Map<String,Double>> gstMap = GSTNUtil.convertListToMapForCnDn(invoiceDetails.getCnDnList(),3);
    	String amtInWords = "Twenty Seven Thousand Nine Hundred and Twenty Rupees  Only";
    	generateCnDnPdf(invoiceDetails,gstinDetails,user,customerStateCode,gstMap,amtInWords,DEST,IMAGE_PATH,3);
    }

	public static Map<String,Map<String,Double>> convertListToMap(List<InvoiceServiceDetails> serviceDetails) {
		 Map<String,Map<String,Double>> result=new HashMap<String,Map<String,Double>>();
		
		 Map<String,Double> igstMap=new HashMap<String,Double>();
		 Map<String,Double> cgstMap=new HashMap<String,Double>();
		 Map<String,Double> sgstMap=new HashMap<String,Double>();
		 Map<String,Double> ugstMap=new HashMap<String,Double>();
			for(InvoiceServiceDetails service : serviceDetails){
				String gstPercent=null;
				if(service.getCategoryType().equals("CATEGORY_WITH_IGST")||service.getCategoryType().equals("CATEGORY_EXPORT_WITH_IGST")){
					gstPercent=String.valueOf(service.getIgstPercentage());
					if(!igstMap.containsKey(gstPercent)){
						igstMap.put(gstPercent, service.getAmount());
					}else{
						igstMap.put(gstPercent, igstMap.get(gstPercent)+service.getAmount());
					}
				}
				if(service.getCategoryType().equals("CATEGORY_WITH_SGST_CSGT")|| service.getCategoryType().equals("CATEGORY_WITH_UGST_CGST")){
					gstPercent=String.valueOf(service.getCgstPercentage());
					if(!cgstMap.containsKey(gstPercent)){
						cgstMap.put(gstPercent, service.getAmount());
					}else{
						cgstMap.put(gstPercent, cgstMap.get(gstPercent)+service.getAmount());
					}
				}
				if(service.getCategoryType().equals("CATEGORY_WITH_SGST_CSGT")){
					gstPercent=String.valueOf(service.getSgstPercentage());
					if(!sgstMap.containsKey(gstPercent)){
						sgstMap.put(gstPercent, service.getAmount());
					}else{
						sgstMap.put(gstPercent, sgstMap.get(gstPercent)+service.getAmount());
					}
				}
				if(service.getCategoryType().equals("CATEGORY_WITH_UGST_CGST")){
					gstPercent=String.valueOf(service.getUgstPercentage());
					if(!ugstMap.containsKey(gstPercent)){
						ugstMap.put(gstPercent, service.getAmount());
					}else{
						ugstMap.put(gstPercent, ugstMap.get(gstPercent)+service.getAmount());
					}
				}
			}	
	
			result.put("igst", igstMap);
			result.put("cgst", cgstMap);
			result.put("sgst", sgstMap);
			result.put("ugst", ugstMap);
			
		return result;
	}

	private static UserMaster getDummyUserMaster() {
    	UserMaster user = new UserMaster();
    	OrganizationMaster organization = new OrganizationMaster();
    	organization.setOrgName("Abfd syv pvt Ltd");
    	organization.setPanNumber("GYCTF1234F");
    	user.setOrganizationMaster(organization);
		return user;
	}

	private static GSTINDetails getDummyGSTINDetails() {
    	GSTINDetails gstinDetails = new GSTINDetails();
    	
    	gstinDetails.setGstinAddressMapping(getDummyGSTINAddressMapping());
    	gstinDetails.setGstinLocationSet(getDummyGstinLocationSet());
    	gstinDetails.setGstinNo("27GYCTF1234FVZ5");
    	gstinDetails.setGstinUname("prashant");
    	gstinDetails.setId(560);
    	gstinDetails.setReferenceId(3935);
    	gstinDetails.setState(27);
    	gstinDetails.setStatus("1");
    	gstinDetails.setUniqueSequence("A0");
    
    	
		return gstinDetails;
	}

	private static List<GstinLocation> getDummyGstinLocationSet() {
		List<GstinLocation> gstinLocationList = new ArrayList<GstinLocation>();
		GstinLocation g1 = new GstinLocation();
		g1.setGstinLocation("MUMBAI");
		g1.setId(5);
		g1.setRefGstinId(560);
		g1.setUniqueSequence("A1");
		gstinLocationList.add(g1);
		
		GstinLocation g12 = new GstinLocation();
		g12.setGstinLocation("KAlyan");
		g12.setId(25);
		g12.setRefGstinId(560);
		g12.setUniqueSequence("A2");
		gstinLocationList.add(g12);
		return gstinLocationList;
	}

	private static GSTINAddressMapping getDummyGSTINAddressMapping() {
		GSTINAddressMapping gSTINAddressMapping = new GSTINAddressMapping();
    	gSTINAddressMapping.setAddress("Mumbai");
    	gSTINAddressMapping.setCity("MUMBAI");
    	gSTINAddressMapping.setState("Maharashtra");
    	gSTINAddressMapping.setPinCode(400080);
    	
		return gSTINAddressMapping;
	}
	
	private static List<InvoiceAdditionalChargeDetails> getDummyInvoiceAdditionalChargeDetails(){
		List<InvoiceAdditionalChargeDetails> invoiceAddChargeDetailsList = new ArrayList<InvoiceAdditionalChargeDetails>();
		InvoiceAdditionalChargeDetails a1 = new InvoiceAdditionalChargeDetails();
		a1.setAdditionalChargeAmount(1000);
		a1.setAdditionalChargeId(10);
		a1.setAdditionalChargeName("Transport Charges");
		a1.setId(10);
		invoiceAddChargeDetailsList.add(a1);
		
		InvoiceAdditionalChargeDetails a12 = new InvoiceAdditionalChargeDetails();
		a12.setAdditionalChargeAmount(1000);
		a12.setAdditionalChargeId(20);
		a12.setAdditionalChargeName("Delivery Charges");
		a12.setId(20);
		invoiceAddChargeDetailsList.add(a12);
		
		return invoiceAddChargeDetailsList;
	}

	public static InvoiceDetails getInvoiceDetailsDummy() {
		
    	CustomerDetails customerDetails = getDummyCustomer();
    	List<InvoiceServiceDetails> serviceList = getDummyServiceList();
    	List<InvoiceAdditionalChargeDetails> addChargesList =  getDummyInvoiceAdditionalChargeDetails();
    	
    	List<InvoiceCnDnDetails> cnDnList = getDummyCnDnList();
    	List<CnDnAdditionalDetails> cnDnAdditionalList = getDummyCnDnAdditionalList();
    	
    	InvoiceDetails invoiceDetails = new InvoiceDetails();
    	invoiceDetails.setServicePlace("Mumbai,Thane");
    	invoiceDetails.setServiceCountry("Maharashtra");
    	invoiceDetails.setDeliveryPlace(27);
    	invoiceDetails.setDeliveryCountry("India");
    	invoiceDetails.setPoDetails("GP143PB");
    	invoiceDetails.setTypeOfExport("");
    	invoiceDetails.setDiscountType("Percentage");
    	invoiceDetails.setDiscountValue(0);
    	invoiceDetails.setDiscountAmount(1434.00);
    	invoiceDetails.setDiscountRemarks(" ");
    	invoiceDetails.setAdditionalChargesType("Percentage");
    	invoiceDetails.setAdditionalChargesValue(0);
    	invoiceDetails.setAdditonalAmount(0);
    	invoiceDetails.setAdditionalChargesRemark("");
    	invoiceDetails.setAmountAfterDiscount(1434.00);
    	invoiceDetails.setTotalTax(4047.85);
    	invoiceDetails.setInvoiceValue(27547.85);
    	invoiceDetails.setInvoiceValueAfterRoundOff(27548.0);
    	invoiceDetails.setGstnStateId(27);
    	invoiceDetails.setInvoiceDateInString("24-07-2017");
    	invoiceDetails.setGstnStateIdInString("27QWERT1234HXZX");
    	invoiceDetails.setInvoicePeriodFromDateInString("21-07-2017");
    	invoiceDetails.setInvoicePeriodToDateInString("24-07-2017");
    	invoiceDetails.setBillToShipIsChecked("No");
    	invoiceDetails.setShipToCustomerName("Pradeep");
    	invoiceDetails.setShipToAddress("Gachiboli");
    	invoiceDetails.setShipToCity("Hyderabad");
    	invoiceDetails.setShipToState("Telangana");
    	invoiceDetails.setShipToStateCode("TS");
    	invoiceDetails.setShipToStateCodeId("36");
    	invoiceDetails.setLocation("A2");
    	invoiceDetails.setInvoiceNumber("1704A190001");
    	invoiceDetails.setReferenceId(123);
    	invoiceDetails.setOrgUId(225);
    	invoiceDetails.setInvoiceFor("Product");
    	invoiceDetails.setInvoiceDate(GSTNUtil.convertStringToTimestamp("2017-07-24"));
    	invoiceDetails.setCreatedOn(new Timestamp(System.currentTimeMillis()));
    	invoiceDetails.setServiceList(serviceList);
    	invoiceDetails.setCustomerDetails(customerDetails);
    	invoiceDetails.setAddChargesList(addChargesList);
    	invoiceDetails.setReverseCharge("No");
    	invoiceDetails.setCnDnList(cnDnList);
    	invoiceDetails.setCnDnAdditionalList(cnDnAdditionalList);
    	invoiceDetails.setFooterNote("From Ajit To Kumar in accordance to Vds.");
    	invoiceDetails.setDocumentType("invoiceCumBillOfSupply");
    	invoiceDetails.setPlaceOfSupply("Maharashtra");
    	invoiceDetails.setEcommerce("Yes");
    	return invoiceDetails;
	}

	private static List<CnDnAdditionalDetails> getDummyCnDnAdditionalList() {
		List<CnDnAdditionalDetails> cnDnAdditionalList =  new ArrayList<CnDnAdditionalDetails>(); 
		
		CnDnAdditionalDetails cndnAdd1 = new CnDnAdditionalDetails();
		cndnAdd1.setAmountAfterDiscount(800);
		cndnAdd1.setCnDnType("creditNote");
		cndnAdd1.setCreatedBy("3927");
		cndnAdd1.setCreatedOn(GSTNUtil.convertStringToTimestamp("2017-10-08"));
		cndnAdd1.setId(3);
		cndnAdd1.setInvoiceDate(GSTNUtil.convertStringToTimestamp("2017-10-09"));
		cndnAdd1.setInvoiceNumber("C17A3A0A00000001");
		cndnAdd1.setInvoiceValue(840);
		cndnAdd1.setInvoiceValueAfterRoundOff(840);
		cndnAdd1.setIterationNo(3);
		cndnAdd1.setOrgUId(205);
		cndnAdd1.setReason("Other");
		cndnAdd1.setRegime("preGst");
		cndnAdd1.setTotalTax(40);
		cnDnAdditionalList.add(cndnAdd1);
		
		CnDnAdditionalDetails cndnAdd2 = new CnDnAdditionalDetails();
		cndnAdd2.setAmountAfterDiscount(800);
		cndnAdd2.setCnDnType("creditNote");
		cndnAdd2.setCreatedBy("3927");
		cndnAdd2.setCreatedOn(GSTNUtil.convertStringToTimestamp("2017-10-08"));
		cndnAdd2.setId(3);
		cndnAdd2.setInvoiceDate(GSTNUtil.convertStringToTimestamp("2017-10-09"));
		cndnAdd2.setInvoiceNumber("C17A3A0A00000002");
		cndnAdd2.setInvoiceValue(840);
		cndnAdd2.setInvoiceValueAfterRoundOff(840);
		cndnAdd2.setIterationNo(2);
		cndnAdd2.setOrgUId(205);
		cndnAdd2.setReason("Other");
		cndnAdd2.setRegime("preGst");
		cndnAdd2.setTotalTax(40);
		cnDnAdditionalList.add(cndnAdd2);
		
		return cnDnAdditionalList;
	}

	private static List<InvoiceCnDnDetails> getDummyCnDnList() {
		List<InvoiceCnDnDetails> cndnList = new ArrayList<InvoiceCnDnDetails>();
		
		InvoiceCnDnDetails cndnDetails = new InvoiceCnDnDetails();
    	cndnDetails.setServiceId(984);
    	cndnDetails.setUnitOfMeasurement("DOZEN");
    	cndnDetails.setRate(2000.00);
    	cndnDetails.setQuantity(10.00);
    	cndnDetails.setAmount(19957.75);
    	cndnDetails.setCalculationBasedOn("Amount");
    	cndnDetails.setTaxAmount(5688.17);
    	cndnDetails.setServiceIdInString("Shoe Box");
    	cndnDetails.setSgstAmount(2794.08);
    	cndnDetails.setCgstAmount(2794.08);
    	cndnDetails.setIgstAmount(0.00);
    	cndnDetails.setUgstAmount(0.00);
    	cndnDetails.setPreviousAmount(20000.00);
    	cndnDetails.setSgstPercentage(14.0);
    	cndnDetails.setCgstPercentage(14.0);
    	cndnDetails.setIgstPercentage(0.00);
    	cndnDetails.setUgstPercentage(0.00);
    	cndnDetails.setBillingFor("Product");
    	cndnDetails.setGstnStateId(27);
    	cndnDetails.setDeliveryStateId(27);
    	cndnDetails.setCategoryType("CATEGORY_WITH_SGST_CSGT");
    	cndnDetails.setCess(100.00);
    	cndnDetails.setOfferAmount(500.00);
    	cndnDetails.setHsnSacCode("84145120");
    	cndnDetails.setAdditionalAmount(457.75);
    	cndnDetails.setAmountAfterDiscount(19957.75);
    	cndnDetails.setIterationNo(3);
    	cndnList.add(cndnDetails);
    	
    	InvoiceCnDnDetails s2 = new InvoiceCnDnDetails();
    	s2.setServiceId(983);
    	s2.setUnitOfMeasurement("GREAT GROSS");
    	s2.setRate(100.00);
    	s2.setQuantity(20.0);
    	s2.setAmount(1842.25);
    	s2.setCalculationBasedOn("Amount");
    	s2.setTaxAmount(431.6);
    	s2.setServiceIdInString("Nikesh Hair Oil");
    	s2.setSgstAmount(165.8);
    	s2.setCgstAmount(165.8);
    	s2.setIgstAmount(0.00);
    	s2.setUgstAmount(0.00);
    	s2.setPreviousAmount(2000.00);
    	s2.setSgstPercentage(9.00);
    	s2.setCgstPercentage(9.00);
    	s2.setIgstPercentage(0.00);
    	s2.setUgstPercentage(0.00);
    	s2.setBillingFor("Product");
    	s2.setGstnStateId(27);
    	s2.setDeliveryStateId(27);
    	s2.setCategoryType("CATEGORY_WITH_SGST_CSGT");
    	s2.setCess(100.00);
    	s2.setHsnSacCode("76074000");
    	s2.setAdditionalAmount(42.25);
    	s2.setAmountAfterDiscount(1842.25);
    	s2.setIterationNo(2);
    	cndnList.add(s2);
		
		return cndnList;
	}

	private static List<InvoiceServiceDetails> getDummyServiceList() {
		List<InvoiceServiceDetails> serviceList = new ArrayList<InvoiceServiceDetails>();
    	//serviceList.set(index, element);
    	InvoiceServiceDetails invoiceServiceDetails = new InvoiceServiceDetails();
    	invoiceServiceDetails.setServiceId(984);
    	invoiceServiceDetails.setUnitOfMeasurement("DOZEN");
    	invoiceServiceDetails.setRate(2000.00);
    	invoiceServiceDetails.setQuantity(10.00);
    	invoiceServiceDetails.setAmount(19957.75);
    	invoiceServiceDetails.setCalculationBasedOn("Amount");
    	invoiceServiceDetails.setTaxAmount(5688.17);
    	invoiceServiceDetails.setServiceIdInString("Shoe Box");
    	invoiceServiceDetails.setSgstAmount(2794.08);
    	invoiceServiceDetails.setCgstAmount(2794.08);
    	invoiceServiceDetails.setIgstAmount(0.00);
    	invoiceServiceDetails.setUgstAmount(0.00);
    	invoiceServiceDetails.setPreviousAmount(20000.00);
    	invoiceServiceDetails.setSgstPercentage(14.0);
    	invoiceServiceDetails.setCgstPercentage(14.0);
    	invoiceServiceDetails.setIgstPercentage(0.00);
    	invoiceServiceDetails.setUgstPercentage(0.00);
    	invoiceServiceDetails.setBillingFor("Product");
    	invoiceServiceDetails.setGstnStateId(27);
    	invoiceServiceDetails.setDeliveryStateId(27);
    	invoiceServiceDetails.setCategoryType("CATEGORY_WITH_SGST_CSGT");
    	invoiceServiceDetails.setCess(100.00);
    	invoiceServiceDetails.setOfferAmount(500.00);
    	invoiceServiceDetails.setHsnSacCode("84145120");
    	invoiceServiceDetails.setAdditionalAmount(457.75);
    	invoiceServiceDetails.setAmountAfterDiscount(19957.75);
    	invoiceServiceDetails.setDiffPercent("Y");
    	serviceList.add(invoiceServiceDetails);
    	
    	InvoiceServiceDetails s2 = new InvoiceServiceDetails();
    	s2.setServiceId(983);
    	s2.setUnitOfMeasurement("GREAT GROSS");
    	s2.setRate(100.00);
    	s2.setQuantity(20.0);
    	s2.setAmount(1842.25);
    	s2.setCalculationBasedOn("Amount");
    	s2.setTaxAmount(431.6);
    	s2.setServiceIdInString("Nikesh Hair Oil");
    	s2.setSgstAmount(165.8);
    	s2.setCgstAmount(165.8);
    	s2.setIgstAmount(0.00);
    	s2.setUgstAmount(0.00);
    	s2.setPreviousAmount(2000.00);
    	s2.setSgstPercentage(9.00);
    	s2.setCgstPercentage(9.00);
    	s2.setIgstPercentage(0.00);
    	s2.setUgstPercentage(0.00);
    	s2.setBillingFor("Product");
    	s2.setGstnStateId(27);
    	s2.setDeliveryStateId(27);
    	s2.setCategoryType("CATEGORY_WITH_SGST_CSGT");
    	s2.setCess(100.00);
    	s2.setHsnSacCode("76074000");
    	s2.setAdditionalAmount(42.25);
    	s2.setAmountAfterDiscount(1842.25);
    	s2.setDiffPercent("Y");
    	serviceList.add(s2);
		return serviceList;
	}

	private static CustomerDetails getDummyCustomer() {
	  	CustomerDetails customerDetails = new CustomerDetails();
    	customerDetails.setId(1482);
    	customerDetails.setCustName("Nikesh");
    	customerDetails.setCustAddress1("Mumbai");
    	customerDetails.setCustCity("THANE");
    	customerDetails.setCustState("Maharashtra");
    	customerDetails.setCustGstinState("27");
    	customerDetails.setCustGstId("27AAAAA6543A1Z1");
		return customerDetails;
	}

	public static Map<String,String> generatePdf(InvoiceDetails invoiceDetails,
			GSTINDetails gstinDetails, UserMaster user,
			String customerStateCode, Map<String, Map<String, Double>> gstMap,
			String amtInWords,String directoryPath,String logoImagePath) throws IOException, DocumentException{
		Map<String,String> mapResponse = new HashMap<String,String>();
		String pdfFilePath = GSTNUtil.generateInvoicePdfFileName(invoiceDetails,directoryPath);//directoryPath + File.separator+year+File.separator+month+File.separator+invoiceDetails.getOrgUId()+File.separator+invoiceDetails.getReferenceId()+File.separator+invoiceDetails.getInvoiceNumber()+".pdf";
		System.out.println("Path : "+pdfFilePath);
		File file = new File(pdfFilePath);
        file.getParentFile().mkdirs();
        mapResponse = new GenerateInvoicePdf().createPdf(pdfFilePath, invoiceDetails, gstinDetails,  user, customerStateCode, gstMap,amtInWords,logoImagePath);
        mapResponse.put(GSTNConstants.PDF_PATH, pdfFilePath);
        mapResponse.put(GSTNConstants.PDF_FILENAME, invoiceDetails.getInvoiceNumber()+"_"+invoiceDetails.getIterationNo()+".pdf");
        return  mapResponse;
    }
 
    public Map<String,String> createPdf1(String dest,InvoiceDetails invoiceDetails,
			GSTINDetails gstinDetails, UserMaster user,
			String customerStateCode, Map<String, Map<String, Double>> gstMap,
			String amtInWords,String logoImagePath) throws IOException, DocumentException {
    	logger.info("Entry");
    	Map<String,String> mapResponse = new HashMap<String,String>();
    	String status = GSTNConstants.FAILURE;
        Document document = new Document();
        try {
        	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        	if(null != invoiceDetails.getFooterNote()){
        		writer.setPageEvent(new MyFooter(invoiceDetails.getFooterNote()));
        	}
        	
	        document.open();
	        PdfPTable table = new PdfPTable(8);
	        
	        //table.setWidths(new int[]{ 17, 42, 10, 9, 6, 16, 9, 12});
	        table.setWidths(new int[]{ 20, 36, 12, 10, 10, 13, 11, 12});
	   
	      //UserInfo LHS - Start -------------------------------------------------------------------------
	        
	         String regularFont="regular";  
	         String boldFont="Bold";
	         
	         Phrase p = new Phrase();
	         //String filePath = "C:/apps/gstn/logo/212/logo_212.JPEG";
	         boolean isImagePresent = false;
	         
	         PdfPCell pv = createImageCell(logoImagePath, 1, 1, Element.ALIGN_LEFT,6);
	         if(pv.getImage() != null){
	        	isImagePresent = true;
	        	table.addCell(pv);
	         }
	          	
	         String content=user.getOrganizationMaster().getOrgName();
	         addMoreText(p, content, regularFont);
	         content="\n"+gstinDetails.getGstinAddressMapping().getAddress()+" , "+gstinDetails.getGstinAddressMapping().getCity()+" , "+gstinDetails.getGstinAddressMapping().getState()+" [ "+gstinDetails.getState()+" ]  , "+gstinDetails.getGstinAddressMapping().getPinCode();
	         addMoreText(p, content, regularFont);
	         
	         content=" \n GSTIN No. : ";
	         addMoreText(p, content, boldFont);
	         content=invoiceDetails.getGstnStateIdInString();
	         addMoreText(p, content, regularFont);
	         
	         content=" \n PAN No. : ";
	         addMoreText(p, content, boldFont);
	         content=user.getOrganizationMaster().getPanNumber();
	         addMoreText(p, content, regularFont);
	 		
	 		if(invoiceDetails.getInvoiceFor().equals("Service")){
	 			if(null != invoiceDetails.getInvoicePeriodFromDateInString()){
					 content=" \n Service Period : ";
				     addMoreText(p, content, boldFont);
				     
				     content=invoiceDetails.getInvoicePeriodFromDateInString()+" TO "+invoiceDetails.getInvoicePeriodToDateInString();
				     addMoreText(p, content, regularFont);
				}
	 			
	 		}
	 		
	 		 content=" \n Whether tax is payable on reverse charge? ";
		     addMoreText(p, content, boldFont);
		     content = invoiceDetails.getReverseCharge();
		     addMoreText(p, content, regularFont);
		     
	 		if(isImagePresent){
	 			table.addCell(createCell(p, 1, 3, Element.ALIGN_LEFT,6));
	 		}else{
	 			table.addCell(createCell(p, 1, 4, Element.ALIGN_LEFT,6));
	 		}
	      //UserInfo LHS - End ---------------------------------------------------------------------------
	 		
	 	//Document Type - Start -------------------------------------------------------------------------
	 		 String documentType = "";
	         String documentTypeToDisplay = "";
	         if(GSTNConstants.DOCUMENT_TYPE_INVOICE.equals(invoiceDetails.getDocumentType())){
	        	 documentType = "TAX INVOICE";
	        	 documentTypeToDisplay = "Tax Invoice";
	         }else if(GSTNConstants.DOCUMENT_TYPE_BILL_OF_SUPPLY.equals(invoiceDetails.getDocumentType())){
	        	 documentType = "BILL OF SUPPLY";
	        	 documentTypeToDisplay = "Bill Of Supply";
	         }else if(GSTNConstants.DOCUMENT_TYPE_INVOICE_CUM_BILL_OF_SUPPLY.equals(invoiceDetails.getDocumentType())){
	        	 documentType = "INVOICE CUM BILL OF SUPPLY";
	        	 documentTypeToDisplay = "Invoice cum Bill Of Supply";
	         }else if(GSTNConstants.DOCUMENT_TYPE_REVERSE_CHARGE.equals(invoiceDetails.getDocumentType())){
	        	 documentType = "REVERSE CHARGE TAX INVOICE";
	        	 documentTypeToDisplay = "Reverse Charge Tax Invoice";
	         }else if(GSTNConstants.DOCUMENT_TYPE_E_COMMERCE_TAX_INVOICE.equals(invoiceDetails.getDocumentType())){
	        	 documentType = "E-COMMERCE TAX INVOICE";
	        	 documentTypeToDisplay = "E-Commerce Tax Invoice";
	         }else if(GSTNConstants.DOCUMENT_TYPE_E_COMMERCE_BILL_OF_SUPPLY.equals(invoiceDetails.getDocumentType())){
	        	 documentType = "E-COMMERCE BILL OF SUPPLY";
	        	 documentTypeToDisplay = "E-Commerce Bill Of Supply";
	         }else{
	        	 documentType = "TAX INVOICE";
	        	 documentTypeToDisplay = "Tax Invoice";
	         }
	       //Document Type - End ------------------------------------------------------------------------- 
	         
	         //Invoice INFO RHS - Start  --------------------------------------------------------------------  
	         p = new Phrase();
	         content="\n \n \n "+documentTypeToDisplay+" No. ";
	         addMoreText(p, content, boldFont);
	         content=invoiceDetails.getInvoiceNumber();
	         addMoreText(p, content, regularFont);
	       
	         content=" \n "+documentTypeToDisplay+" Date: ";
	         addMoreText(p, content, boldFont);
	         content=GSTNUtil.convertTimestampToString1(invoiceDetails.getInvoiceDate());
	         addMoreText(p, content, regularFont);
	         table.addCell(createCell(p, 1, 4, Element.ALIGN_LEFT,6));
	         
	      //Invoice INFO RHS - End -----------------------------------------------------------------------
	        
	         p = new Phrase();
	         content = documentType+"\n";
	         addMoreText(p, content, boldFont);
	         table.addCell(createCell(p, 1, 8, Element.ALIGN_CENTER, 1));
	        
	         //Bill To - LHS - Start ------------------------------------------------------------------------
	         p = new Phrase();
	         content="\nBill To ";
	         addMoreText(p, content, boldFont);
	         
	         content="\nName : ";
	         addMoreText(p, content, boldFont);
	         content=invoiceDetails.getCustomerDetails().getCustName();
	         addMoreText(p, content, regularFont);
	         
	         content="\nAddress : ";
	         addMoreText(p, content, boldFont);
	         content=invoiceDetails.getCustomerDetails().getCustAddress1();
	         addMoreText(p, content, regularFont);
	         
	         content="\nCity : ";
	         addMoreText(p, content, boldFont);
	         content=invoiceDetails.getCustomerDetails().getCustCity();
	         addMoreText(p, content, regularFont);
	         
	         content="\nState : ";
	         addMoreText(p, content, boldFont);
	         content=invoiceDetails.getCustomerDetails().getCustState();
	         addMoreText(p, content, regularFont);
	         
	 	        if(invoiceDetails.getCustomerDetails().getCustGstinState() != null){
	 	        	 content=" [ "+invoiceDetails.getCustomerDetails().getCustGstinState()+" ] ";
	 	             addMoreText(p, content, regularFont);
	 	        }
	 	        
	 	        content="\nState Code : ";
	 	        addMoreText(p, content, boldFont);
	 	        content=customerStateCode;
	 	        addMoreText(p, content, regularFont);
	 	        
	 	        content="\nGSTIN/Unique Code : ";
	 	        addMoreText(p, content, boldFont);
	 	        content=invoiceDetails.getCustomerDetails().getCustGstId();
	 	        addMoreText(p, content, regularFont);
	 	        
	         table.addCell(createCell(p, 1, 2, Element.ALIGN_LEFT, 6));
	      //Bill To - LHS - End  -------------------------------------------------------------------------
	        
	     //Ship To - RHS - Start ------------------------------------------------------------------------
	        if(invoiceDetails.getInvoiceFor().equals("Product")){
	        	if(invoiceDetails.getBillToShipIsChecked().equals("No")){
	        		p = new Phrase();
	                content="\nShip To ";
	                addMoreText(p, content, boldFont);
	                
	                content="\nName : ";
	    	        addMoreText(p, content, boldFont);
	    	        content=invoiceDetails.getShipToCustomerName();
	    	        addMoreText(p, content, regularFont);
	    	        
	    	        content="\nAddress : ";
	    	        addMoreText(p, content, boldFont);
	    	        content=invoiceDetails.getShipToAddress();
	    	        addMoreText(p, content, regularFont);
	    	        
	    	        content="\nState : ";
	    	        addMoreText(p, content, boldFont);
	    	        content=invoiceDetails.getShipToState()+" [ "+invoiceDetails.getShipToStateCodeId()+" ] ";
	    	        addMoreText(p, content, regularFont);
	    	        
	    	        content="\nState Code : ";
	    	        addMoreText(p, content, boldFont);
	    	        content=invoiceDetails.getShipToStateCode();
	    	        addMoreText(p, content, regularFont);
	        		
	        	}
	        	if(invoiceDetails.getBillToShipIsChecked().equals("Yes")){
	       		 p = new Phrase();
	       	        content="\nShip To ";
	       	        addMoreText(p, content, boldFont);
	       	        
	       	        content="\nName : ";
	       	        addMoreText(p, content, boldFont);
	       	        content=invoiceDetails.getCustomerDetails().getCustName();
	       	        addMoreText(p, content, regularFont);
	       	        
	       	        content="\nAddress : ";
	       	        addMoreText(p, content, boldFont);
	       	        content=invoiceDetails.getCustomerDetails().getCustAddress1();
	       	        addMoreText(p, content, regularFont);
	       	        
	       	        content="\nCity : ";
	       	        addMoreText(p, content, boldFont);
	       	        content=invoiceDetails.getCustomerDetails().getCustCity();
	       	        addMoreText(p, content, regularFont);
	       	        
	       	        content="\nState : ";
	       	        addMoreText(p, content, boldFont);
	       	        content=invoiceDetails.getCustomerDetails().getCustState();
	       	        addMoreText(p, content, regularFont);
	       
	       		        if(invoiceDetails.getCustomerDetails().getCustGstinState() != null){
	       		        	 content=" [ "+invoiceDetails.getCustomerDetails().getCustGstinState()+" ] ";
	       	        	        addMoreText(p, content, regularFont);
	       		        	
	       		        }
	       		        
	       		        content="\nState Code : ";
	           	        addMoreText(p, content, boldFont);
	           	        content=customerStateCode;
	           	        addMoreText(p, content, regularFont);
	           	        
	           	        content="\nGSTIN/Unique Code : ";
	           	        addMoreText(p, content, boldFont);
	           	        content=invoiceDetails.getCustomerDetails().getCustGstId();
	           	        addMoreText(p, content, regularFont);
	       	}
	        	table.addCell(createCell(p, 1, 6, Element.ALIGN_LEFT, 6));
	        }else{
	        	p = new Phrase();
	        	/*sbShipTo.append("\n  ");
	        	sbShipTo.append("\n  ");
	        	sbShipTo.append("\n  ");
	        	sbShipTo.append("\n  ");
	        	sbShipTo.append("\n  ");
	        	sbShipTo.append("\n  ");*/
	        	//table.addCell(createCell(""+"  \n \n \n \n \n \n ", 1, 6, Element.ALIGN_LEFT, 6));
	        	table.addCell(createCell(""+"  \n \n \n \n \n \n ", 1, 6, Element.ALIGN_LEFT, 6));
	        }
	        //table.addCell(createCell(p, 1, 6, Element.ALIGN_LEFT, 6));
	     //Ship To - RHS - End  ------------------------------------------------------------------------- 
	        
	        //Place Of Supply - Start ----------------------------------------------------------------------
	        /*if(invoiceDetails.getBillToShipIsChecked().equals("Yes")){*/
	        	
	        	p = new Phrase();
		        content="Place of supply : ";
		        addMoreText(p, content, boldFont);
		        content=invoiceDetails.getPlaceOfSupply();//invoiceDetails.getCustomerDetails().getCustState()+" [ "+ customerStateCode+" ] ";
		        addMoreText(p, content, regularFont);
	
	        	
	        	 table.addCell(createCell(p, 1, 8, Element.ALIGN_LEFT, 1));
	        /*}else{
	        	p = new Phrase();
		        content="Place of supply : ";
		        addMoreText(p, content, boldFont);
		        content=invoiceDetails.getShipToState()+" [ "+ invoiceDetails.getShipToStateCodeId()+" ] ";
		        addMoreText(p, content, regularFont);
	
	        	 table.addCell(createCell(p, 1, 8, Element.ALIGN_LEFT, 1));
	        }*/
	       
	     //Place Of Supply - End ------------------------------------------------------------------------
	        
	        //Invoice table - Start ------------------------------------------------------------------------
	        //Table heading containing five columns - Start
	        p = new Phrase();
	        content="Sr.No";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
	        p = new Phrase();
	        if(invoiceDetails.getInvoiceFor().equals("Product")){
	        	content="Description of Goods";
	    	}else{
	    		content="Description of Services";
	        }
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
	        p = new Phrase();
	        if(invoiceDetails.getInvoiceFor().equals("Product")){
	   		   	content="HSN Code";
	    	}else{
	    		content="SAC Code";
	        }
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
	        p = new Phrase();
	        content="Quantity";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
	        p = new Phrase();
	        content="UOM";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
	        p = new Phrase();
	        content="Rate (Rs.)/UOM";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
	        p = new Phrase();
	        content="Discount (Rs.)";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
	        p = new Phrase();
	        content="Total (Rs.) after discount";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
	      //Table heading containing five columns - End
	      //Looping through Service List - Start  
	        double totalAmount = 0;
	        double cessTotalTax = 0;
	       
	        String categoryType = "";
	        int i = 1;
	        
	        int isDiffPercentPresent = 0;
	        for(InvoiceServiceDetails s : invoiceDetails.getServiceList()){
	        	String descriptionTxt = "";
	        	String containsDiffPercentage = "";
	        	if(s.getDiffPercent() != null && s.getDiffPercent().equals("Y")){
	        		containsDiffPercentage = "(*)";
	        		isDiffPercentPresent++;
	        	}
	        	if(StringUtils.isNotEmpty(s.getIsDescriptionChecked()) && (s.getIsDescriptionChecked().equals("Yes"))){
	        		descriptionTxt = " - "+s.getDescription();
	        	}
	        	table.addCell(createCell(Integer.toString(i), 1, 1, Element.ALIGN_LEFT, 1));
	            table.addCell(createCell(s.getServiceIdInString()+containsDiffPercentage+descriptionTxt, 1, 1, Element.ALIGN_LEFT, 1));
	            table.addCell(createCell(s.getHsnSacCode(), 1, 1, Element.ALIGN_LEFT, 1));
	            table.addCell(createCell(s.getQuantity()+"", 1, 1, Element.ALIGN_LEFT, 1));
	            table.addCell(createCell(s.getUnitOfMeasurement(), 1, 1, Element.ALIGN_LEFT, 1));
	            table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(s.getRate())+"", 1, 1, Element.ALIGN_LEFT, 1));
	            table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(s.getOfferAmount())+"", 1, 1, Element.ALIGN_LEFT, 1));
	            table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(s.getPreviousAmount() - s.getOfferAmount())+"", 1, 2, Element.ALIGN_CENTER, 1));
	            totalAmount = totalAmount + (s.getPreviousAmount() - s.getOfferAmount());
	            cessTotalTax = cessTotalTax + s.getCess();
	            categoryType = s.getCategoryType();
	            i++;
	        }
	      //Looping through Service List - End   
	        
	     //SubTotal - Start-----------------------------------------------------------------------------------
	        p = new Phrase();
	        content="Total Value (A)";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        table.addCell(createCell(p, 1, 6, Element.ALIGN_LEFT, 1));
	        table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(totalAmount) + "", 1, 1, Element.ALIGN_CENTER, 1));
	     //SubTotal - End ------------------------------------------------------------------------------------
	        //table.addCell(createCell(" ", 1, 4, Element.ALIGN_CENTER, 1));
	        
	     //leave a blank line -Start---------------------------------------------------------------------------    
	        table.addCell(createCell(" ", 1, 8, Element.ALIGN_CENTER, 1));
	     //leave a blank line -End--------------------------------------------------------------------------- 
	        
	        
	     //Loop through Additional Charges - Start
	     String containsAdditionalCharges = "";
	     double addChargeAmount = 0;
	     if(invoiceDetails.getAddChargesList() != null && invoiceDetails.getAddChargesList().size() > 0){
	    	 containsAdditionalCharges = "YES";
	    	 
	    	 //Show 1st row of Add : Additional Charges
	    	 p = new Phrase();
		     content = "Add : Additional Charges";
		     addMoreText(p, content, boldFont);
		        
	    	 table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
	    	 table.addCell(createCell("", 1, 6, Element.ALIGN_LEFT, 1));
	    	 
	    	//Show 2nd row of Additional Charges
	    	 for(InvoiceAdditionalChargeDetails addChgDet : invoiceDetails.getAddChargesList()){
	    		 table.addCell(createCell(addChgDet.getAdditionalChargeName()+"", 1, 2, Element.ALIGN_CENTER, 1));
	    		 table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(addChgDet.getAdditionalChargeAmount())+"", 1, 5, Element.ALIGN_LEFT, 1));
		         table.addCell(createCell("", 1, 1, Element.ALIGN_CENTER, 1));
		         
		         addChargeAmount = addChargeAmount + addChgDet.getAdditionalChargeAmount();
	    	 }
	    	 
	    	//Show 3rd row of Additional Charges
	    	 p = new Phrase();
		     content="Total Additional Charges (B)";
		     addMoreText(p, content, boldFont);
	    	 table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
    		 table.addCell(createCell("", 1, 5, Element.ALIGN_LEFT, 1));
	         table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(addChargeAmount)+"", 1, 1, Element.ALIGN_CENTER, 1));
	         
	        //Show 4th row of Additional Charges
	         p = new Phrase();
		     content = "Total Value (A+B)";
		     addMoreText(p, content, boldFont);
	         table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
    		 table.addCell(createCell("", 1, 5, Element.ALIGN_LEFT, 1));
	         table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(totalAmount + addChargeAmount)+"", 1, 1, Element.ALIGN_CENTER, 1));
	    	 
	         //leave a blank line -Start---------------------------------------------------------------------------    
		        table.addCell(createCell(" ", 1, 8, Element.ALIGN_CENTER, 1));
		     //leave a blank line -End--------------------------------------------------------------------------- 
	     }
	        
	     //Loop through Additional Charges - End
	     
	  
	        
	        
	     //Loop through GST TAX - Start 
	        
	        p = new Phrase();
		    content = "Add : Taxes";
		    addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
	    	table.addCell(createCell("", 1, 6, Element.ALIGN_LEFT, 1));
	        
	        if(categoryType.equals("CATEGORY_WITH_SGST_CSGT") || categoryType.equals("CATEGORY_WITH_UGST_CGST")){
	        	for (Map.Entry<String,Map<String,Double>> entry : gstMap.entrySet()) {
	        	    String key = entry.getKey();
	        	    Map<String,Double> value = entry.getValue();
	        	   if(key.equals("cgst")){
	        		   int cgstLoopCount = 1;
	        		   for(Map.Entry<String,Double> insideEntry : value.entrySet()){
	        			   if(cgstLoopCount == 1){
	        				    table.addCell(createCell("Central Goods & Services Tax", 1, 2, Element.ALIGN_CENTER, 1));
	        		            table.addCell(createCell(insideEntry.getKey()+"% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }else{
	        				    table.addCell(createCell("", 1, 2, Element.ALIGN_CENTER, 1));
	        				    table.addCell(createCell(insideEntry.getKey()+"% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }
	        			   cgstLoopCount = cgstLoopCount + 1;
	        		   }
	        	   }
	        	   
	        	   if(key.equals("cgstDiffPercent")){
	        		   int cgstLoopCount = 1;
	        		   for(Map.Entry<String,Double> insideEntry : value.entrySet()){
	        			   if(cgstLoopCount == 1){
	        				    table.addCell(createCell("Central Goods & Services Tax (*)", 1, 2, Element.ALIGN_CENTER, 1));
	        		            table.addCell(createCell(insideEntry.getKey()+"% of 65% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces(((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }else{
	        				    table.addCell(createCell("", 1, 2, Element.ALIGN_CENTER, 1));
	        				    table.addCell(createCell(insideEntry.getKey()+"% of 65% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces(((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }
	        			   cgstLoopCount = cgstLoopCount + 1;
	        		   }
	        	   }
	        	}
	        }
	        
	        if(categoryType.equals("CATEGORY_WITH_SGST_CSGT")){
	        	for (Map.Entry<String,Map<String,Double>> entry : gstMap.entrySet()) {
	        	    String key = entry.getKey();
	        	    Map<String,Double> value = entry.getValue();
	        	   if(key.equals("sgst")){
	        		   int sgstLoopCount = 1;
	        		   for(Map.Entry<String,Double> insideEntry : value.entrySet()){
	        			   if(sgstLoopCount == 1){
	        				    table.addCell(createCell("State Goods & Services Tax", 1, 2, Element.ALIGN_CENTER, 1));
	        		            table.addCell(createCell(insideEntry.getKey()+"% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }else{
	        				    table.addCell(createCell("", 1, 2, Element.ALIGN_CENTER, 1));
	        				    table.addCell(createCell(insideEntry.getKey()+"% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }
	        			   sgstLoopCount = sgstLoopCount + 1;
	        		   }
	        	   }
	        	   
	        	   if(key.equals("sgstDiffPercent")){
	        		   int sgstLoopCount = 1;
	        		   for(Map.Entry<String,Double> insideEntry : value.entrySet()){
	        			   if(sgstLoopCount == 1){
	        				    table.addCell(createCell("State Goods & Services Tax (*)", 1, 2, Element.ALIGN_CENTER, 1));
	        		            table.addCell(createCell(insideEntry.getKey()+"% of 65% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces(((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }else{
	        				    table.addCell(createCell("", 1, 2, Element.ALIGN_CENTER, 1));
	        				    table.addCell(createCell(insideEntry.getKey()+"% of 65% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces(((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }
	        			   sgstLoopCount = sgstLoopCount + 1;
	        		   }
	        	   }
	        	}
	        }
	        
	        if(categoryType.equals("CATEGORY_WITH_IGST") || categoryType.equals("CATEGORY_EXPORT_WITH_IGST")){
	        	for (Map.Entry<String,Map<String,Double>> entry : gstMap.entrySet()) {
	        	    String key = entry.getKey();
	        	    Map<String,Double> value = entry.getValue();
	        	   if(key.equals("igst")){
	        		   int igstLoopCount = 1;
	        		   for(Map.Entry<String,Double> insideEntry : value.entrySet()){
	        			   if(igstLoopCount == 1){
	        				    table.addCell(createCell("Integrated Goods & Services Tax", 1, 2, Element.ALIGN_CENTER, 1));
	        		            table.addCell(createCell(insideEntry.getKey()+"% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }else{
	        				    table.addCell(createCell("", 1, 2, Element.ALIGN_CENTER, 1));
	        				    table.addCell(createCell(insideEntry.getKey()+"% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }
	        			   igstLoopCount = igstLoopCount + 1;
	        		   }
	        	   }
	        	   
	        	   if(key.equals("igstDiffPercent")){
	        		   int igstLoopCount = 1;
	        		   for(Map.Entry<String,Double> insideEntry : value.entrySet()){
	        			   if(igstLoopCount == 1){
	        				    table.addCell(createCell("Integrated Goods & Services Tax (*)", 1, 2, Element.ALIGN_CENTER, 1));
	        		            table.addCell(createCell(insideEntry.getKey()+"% of 65% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces(((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }else{
	        				    table.addCell(createCell("", 1, 2, Element.ALIGN_CENTER, 1));
	        				    table.addCell(createCell(insideEntry.getKey()+"% of 65% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces(((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }
	        			   igstLoopCount = igstLoopCount + 1;
	        		   }
	        	   }
	        	}
	        }
	        
	        if(categoryType.equals("CATEGORY_WITH_UGST_CGST")){
	        	for (Map.Entry<String,Map<String,Double>> entry : gstMap.entrySet()) {
	        	    String key = entry.getKey();
	        	    Map<String,Double> value = entry.getValue();
	        	   if(key.equals("ugst")){
	        		   int ugstLoopCount = 1;
	        		   for(Map.Entry<String,Double> insideEntry : value.entrySet()){
	        			   if(ugstLoopCount == 1){
	        				    table.addCell(createCell("Union Territory Goods & Services Tax", 1, 2, Element.ALIGN_CENTER, 1));
	        		            table.addCell(createCell(insideEntry.getKey()+"% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }else{
	        				    table.addCell(createCell("", 1, 2, Element.ALIGN_CENTER, 1));
	        				    table.addCell(createCell(insideEntry.getKey()+"% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }
	        			   ugstLoopCount = ugstLoopCount + 1;
	        		   }
	        	   }
	        	   
	        	   if(key.equals("ugstDiffPercent")){
	        		   int ugstLoopCount = 1;
	        		   for(Map.Entry<String,Double> insideEntry : value.entrySet()){
	        			   if(ugstLoopCount == 1){
	        				    table.addCell(createCell("Union Territory Goods & Services Tax (*)", 1, 2, Element.ALIGN_CENTER, 1));
	        		            table.addCell(createCell(insideEntry.getKey()+"% of 65% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces(((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }else{
	        				    table.addCell(createCell("", 1, 2, Element.ALIGN_CENTER, 1));
	        				    table.addCell(createCell(insideEntry.getKey()+"% of 65% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces(((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }
	        			   ugstLoopCount = ugstLoopCount + 1;
	        		   }
	        	   }
	        	}
	        } 
	        
	      //Loop through GST TAX - End  
	      
	      //Cess Amount - Start  
	        table.addCell(createCell("Cess", 1, 2, Element.ALIGN_CENTER, 1));
	        //table.addCell(createCell("", 1, 3, Element.ALIGN_LEFT, 1));
	        table.addCell(createCell("", 1, 5, Element.ALIGN_LEFT, 1));
	        table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(cessTotalTax) + "", 1, 1, Element.ALIGN_CENTER, 1));
	      //Cess Amount - End  
	        
	      //Total Tax - Start 
	        if(containsAdditionalCharges.equals("YES")){
	        	table.addCell(createCell("Total Tax (C)", 1, 2, Element.ALIGN_CENTER, 1));
		        table.addCell(createCell("", 1, 5, Element.ALIGN_LEFT, 1));
		        table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(invoiceDetails.getTotalTax()) + "", 1, 1, Element.ALIGN_CENTER, 1));
	        }else{
	        	table.addCell(createCell("Total Tax (B)", 1, 2, Element.ALIGN_CENTER, 1));
		        table.addCell(createCell("", 1, 5, Element.ALIGN_LEFT, 1));
		        table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(invoiceDetails.getTotalTax()) + "", 1, 1, Element.ALIGN_CENTER, 1));
	        }
	       
	      //leave a blank line - Start --------------------------------------------------------------------------- 
	        table.addCell(createCell(" ", 1, 8, Element.ALIGN_CENTER, 1));
	      //leave a blank line - End ---------------------------------------------------------------------------     
	        
	      //Total Invoice Value  - Start --------------------------------------------------------------------------- 
	        p = new Phrase();
	        content = "";
	        if(containsAdditionalCharges.equals("YES")){
	        	content = "Total "+documentTypeToDisplay+" Value (A+B+C)";
	        }else{
	        	content = "Total "+documentTypeToDisplay+" Value (A+B)";
	        }
	        
		    addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
		    table.addCell(createCell("", 1, 5, Element.ALIGN_LEFT, 1));
		    table.addCell(createCell(invoiceDetails.getInvoiceValue() + "", 1, 1, Element.ALIGN_CENTER, 1));
	       
	        
	        //Round-Off
	        table.addCell(createCell("Round off", 1, 2, Element.ALIGN_CENTER, 1));
	        table.addCell(createCell("", 1, 5, Element.ALIGN_LEFT, 1));
	        table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(invoiceDetails.getInvoiceValueAfterRoundOff() - invoiceDetails.getInvoiceValue()) + "", 1, 1, Element.ALIGN_CENTER, 1));
	        
	        //Amount After Round-Off
	        p = new Phrase();
	        content = "Total "+documentTypeToDisplay+" Value (After Round off)";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
	        table.addCell(createCell("", 1, 5, Element.ALIGN_LEFT, 1));
	        table.addCell(createCell(invoiceDetails.getInvoiceValueAfterRoundOff()+ "", 1, 1, Element.ALIGN_CENTER, 1));
	        
	        
	     //Total Invoice Value  - End ---------------------------------------------------------------------------  
	        
	     //Total Invoice Value In Words  - Start ----------------------------------------------------------------    
	        p = new Phrase();
	        content="Total "+documentTypeToDisplay+" Value in Rs. (In Words) : ";
	        addMoreText(p, content, boldFont);
	        content=amtInWords;
	        addMoreText(p, content, regularFont);
	        table.addCell(createCell(p, 1, 8, Element.ALIGN_LEFT, 1));
	     //Total Invoice Value In Words - End  -------------------------------------------------------------------------------  
	        
	     //* Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government. - Start    
	        if(isDiffPercentPresent > 0){
	        	p = new Phrase();
		        content="(*) Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government.";
		        addMoreText(p, content, boldFont);
		        content="";
		        addMoreText(p, content, regularFont);
		        table.addCell(createCell(p, 1, 8, Element.ALIGN_LEFT, 1));
	        }
	     //* Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government. - End
	        
	     //Customer PO/WO  - Start    
	        p = new Phrase();
	        content="Customer PO/ WO No. ";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 3, Element.ALIGN_LEFT, 2));
	        table.addCell(createCell(""+invoiceDetails.getPoDetails(), 1, 5, Element.ALIGN_LEFT, 2));
	     //Customer PO/WO  - End     
	        table.addCell(createCell("Recipient Name \n \nRecipient Signature \n ", 1, 3, Element.ALIGN_LEFT, 4));
	        table.addCell(createCell("For Supplier Name \n \nAuthorised Signatory \n", 1, 5, Element.ALIGN_LEFT, 4));
	        
	        //*Cess : For Goods, this value is the sum total of 'Advol Cess Amount' and 'Non Advol Cess Amount'. - Start 
	    	p = new Phrase();
	        content="*Cess : For Goods, this value is the sum total of 'Advol Cess Amount' and 'Non Advol Cess Amount'.";
	        addMoreText(p, content, boldFont);
	        content="";
	        addMoreText(p, content, regularFont);
	        table.addCell(createCell(p, 1, 8, Element.ALIGN_LEFT, 4));
	        //*Cess : For Goods, this value is the sum total of 'Advol Cess Amount' and 'Non Advol Cess Amount'. - end 
	        
	        p = new Phrase();
	        content="Declaration :";
	        addMoreText(p, content, boldFont);
	        content="\nI) Certified that all the particulars given above are true and correct. The amounts indicated represents the price actually charged and there is no flow of additional consideration directly or indirectly from the Buyer.\nII) The normal terms governing the above sale are printed overleaf. ";
	        addMoreText(p, content, regularFont);
	        
	        table.addCell(createCell(p, 1, 8, Element.ALIGN_LEFT, 4));
	        
	        table.setTotalWidth(540);
	        table.setLockedWidth(true);
	        table.setWidthPercentage(100);
	        document.add(table);
	        
	       /* PdfContentByte canvas = writer.getDirectContent();
	        PdfTemplate template = canvas.createTemplate(table.getTotalWidth(), table.getTotalHeight());
	        table.writeSelectedRows(0, -1, 0, table.getTotalHeight(), template);
	        Image img = Image.getInstance(template);
	        img.scaleToFit(PageSize.A4.getWidth()- 20f, PageSize.A4.getHeight());
	        img.setAbsolutePosition(10, (PageSize.A4.getHeight() - table.getTotalHeight()) / 2);
	        
	        document.add(img);*/
	        
	        document.close();
	    	status = GSTNConstants.SUCCESS;
		} catch (NumberFormatException e) {
			logger.error("Error in:",e);
		}
        mapResponse.put(GSTNConstants.STATUS, status);
        logger.info("Exit");
        return mapResponse;
    }
    
    public PdfPCell createCell(String content, float borderWidth, int colspan, int alignment, int rowSpan) {
    	FontSelector selector1 = new FontSelector();
        Font font2 = FontFactory.getFont("/fonts/calibri.ttf");//f1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);
        BaseFont baseFont = font2.getBaseFont();
        Font f1 = new Font(baseFont, 9, Font.NORMAL);
        f1.setColor(BaseColor.BLACK);
        selector1.addFont(f1);
        Phrase ph = selector1.process(content);
    	PdfPCell cell = new PdfPCell(ph);
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(alignment);
        cell.setRowspan(rowSpan);
      
        return cell;
    }
    
    
    public PdfPCell createCell(Phrase p, float borderWidth, int colspan, int alignment, int rowSpan) {
    	FontSelector selector1 = new FontSelector();
        /*Font f1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);
        */
    	Font font2 = FontFactory.getFont("/fonts/calibri.ttf");//f1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);
        BaseFont baseFont = font2.getBaseFont();
        Font f1 = new Font(baseFont, 9, Font.NORMAL);
        f1.setColor(BaseColor.BLACK);
        selector1.addFont(f1);
       // Phrase ph = selector1.process(content);
    	PdfPCell cell = new PdfPCell(p);
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(alignment);
        cell.setRowspan(rowSpan);
        
        return cell;
    }
    
    public PdfPCell createCellWithDynamicBorders(Phrase p, float borderWidth, int colspan, int alignment, int rowSpan,int top,int right,int bottom,int left) {
    	FontSelector selector1 = new FontSelector();
        /*Font f1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);*/
    	Font font2 = FontFactory.getFont("/fonts/calibri.ttf");//f1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);
        BaseFont baseFont = font2.getBaseFont();
        Font f1 = new Font(baseFont, 9, Font.NORMAL);
        f1.setColor(BaseColor.BLACK);
        selector1.addFont(f1);
       // Phrase ph = selector1.process(content);
    	PdfPCell cell = new PdfPCell(p);
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(alignment);
        cell.setRowspan(rowSpan);
        cell.setBorder(PdfPCell.NO_BORDER);
        if(top == 1){
        	cell.enableBorderSide(Rectangle.TOP); 
        }
        if(bottom == 1){
        	cell.enableBorderSide(Rectangle.BOTTOM); 
        }
        if(right == 1){
        	cell.enableBorderSide(Rectangle.RIGHT); 
        }
        if(left == 1){
        	cell.enableBorderSide(Rectangle.LEFT); 
        }
        return cell;
    }
    
    public PdfPCell createCellWithDynamicBorders(String content, float borderWidth, int colspan, int alignment, int rowSpan,int top,int right,int bottom,int left) {
    	FontSelector selector1 = new FontSelector();
        /*Font f1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);*/
    	Font font2 = FontFactory.getFont("/fonts/calibri.ttf");//f1 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);
        BaseFont baseFont = font2.getBaseFont();
        Font f1 = new Font(baseFont, 9, Font.NORMAL);
        f1.setColor(BaseColor.BLACK);
        selector1.addFont(f1);
        Phrase ph = selector1.process(content);
    	PdfPCell cell = new PdfPCell(ph);
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(alignment);
        cell.setRowspan(rowSpan);
        cell.setBorder(PdfPCell.NO_BORDER);
        if(top == 1){
        	cell.enableBorderSide(Rectangle.TOP); 
        }
        if(bottom == 1){
        	cell.enableBorderSide(Rectangle.BOTTOM); 
        }
        if(right == 1){
        	cell.enableBorderSide(Rectangle.RIGHT); 
        }
        if(left == 1){
        	cell.enableBorderSide(Rectangle.LEFT); 
        }
        return cell;
    }
    
    public void addMoreText(Phrase p,String content,String fontType){
    	Font font=null;
    	Font font2 = null;
    	BaseFont baseFont = null;
        if(fontType.equals("regular")){
        	font2 = FontFactory.getFont("/fonts/calibri.ttf");
        	baseFont = font2.getBaseFont();
        	font = new Font(baseFont, 9, Font.NORMAL);
        }else if(fontType.equals("h2")){
        	font2 = FontFactory.getFont("/fonts/calibri.ttf");
        	baseFont = font2.getBaseFont();
        	font = new Font(baseFont, 14, Font.BOLD);
        }else if(fontType.equals("rupee")){
        	font = FontFactory.getFont("/fonts/PlayfairDisplay-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12f,Font.BOLD);
        	content = "(\u20B9)";
        }else if(fontType.equals("invoiceBoldFont")){
        	font2 = FontFactory.getFont("/fonts/calibri.ttf");
        	baseFont = font2.getBaseFont();
        	font = new Font(baseFont, 11, Font.BOLD);
        }else{
        	font2 = FontFactory.getFont("/fonts/calibri.ttf");
        	baseFont = font2.getBaseFont();
        	font = new Font(baseFont, 9, Font.BOLD);
        }
        
        p.add(new Chunk(content, font));
    	
    }
    
    
    public static PdfPCell createImageCell(String logoImagePath,float borderWidth, int colspan, int alignment, int rowSpan) throws DocumentException, IOException {
   	 	PdfPCell cell = new PdfPCell();
   	 	boolean isImageExist = GSTNUtil.checkIfFileExists(logoImagePath);
   	 
	   	if(isImageExist){
	   		Image img = Image.getInstance(logoImagePath);
	   		cell = new PdfPCell(img, true);
	   		cell.setColspan(colspan);
	        cell.setHorizontalAlignment(alignment);
	        cell.setRowspan(rowSpan);
	   	}else{
	   		cell.setColspan(colspan);
	        cell.setHorizontalAlignment(alignment);
	        cell.setRowspan(rowSpan);
	   		
	   	}
       return cell;
   }

	public static Map<String, String> generateCnDnPdf(InvoiceDetails invoiceDetails, GSTINDetails gstinDetails,UserMaster user, String customerStateCode, Map<String, Map<String, Double>> gstMap, String amtInWords,String directoryPath, String logoImagePath, Integer iterationNo) throws IOException, DocumentException {
		Map<String,String> mapResponse = new HashMap<String,String>();
		Map<String,String> mapCnDnPdfFileName = GSTNUtil.generateCnDnPdfFileName(invoiceDetails.getCnDnAdditionalList(),directoryPath,iterationNo);//directoryPath + File.separator+year+File.separator+month+File.separator+invoiceDetails.getOrgUId()+File.separator+invoiceDetails.getReferenceId()+File.separator+invoiceDetails.getInvoiceNumber()+".pdf";
		String pdfFilePath = mapCnDnPdfFileName.get("pdfFilePath");
		System.out.println("Path : "+pdfFilePath);
		File file = new File(pdfFilePath);
        file.getParentFile().mkdirs();
        mapResponse = new GenerateInvoicePdf().createCnDnPdf(pdfFilePath, invoiceDetails, gstinDetails,  user, customerStateCode, gstMap,amtInWords,logoImagePath,iterationNo);
        mapResponse.put(GSTNConstants.PDF_PATH, pdfFilePath);
        mapResponse.put(GSTNConstants.PDF_FILENAME, mapCnDnPdfFileName.get("pdfFileName"));
        return  mapResponse;
	}

	private Map<String, String> createCnDnPdf1(String dest, InvoiceDetails invoiceDetails,GSTINDetails gstinDetails, UserMaster user, String customerStateCode,Map<String, Map<String, Double>> gstMap, String amtInWords, String logoImagePath, Integer iterationNo)  throws IOException, DocumentException  {

    	logger.info("Entry");
    	Map<String,String> mapResponse = new HashMap<String,String>();
    	String status = GSTNConstants.FAILURE;
        Document document = new Document();
        String cnDnType = null;
        String currentInvoiceNumber = null;
        String currentInvoiceDate = null;
        double totalTaxes = 0d;
        double invoiceValue = 0d;
        double invoiceValueAfterRoundOff = 0d;
        String cndnFooterNote = null;
        String cndnCreationType = null;
        String isAdditionalChargePresent = null;
        
        
        try {
        	
        	//Fetch the CnDntype - Start ------------------------------------------------------------------- 
	         for(CnDnAdditionalDetails cnDnAddDetail : invoiceDetails.getCnDnAdditionalList()){
	        	 if(cnDnAddDetail.getIterationNo() == iterationNo){
	        		 cnDnType = cnDnAddDetail.getCnDnType();
	        		 currentInvoiceNumber = cnDnAddDetail.getInvoiceNumber();
	        		 currentInvoiceDate = GSTNUtil.convertTimestampToString1(cnDnAddDetail.getInvoiceDate());
	        		 totalTaxes = cnDnAddDetail.getTotalTax();
	        		 invoiceValue = cnDnAddDetail.getInvoiceValue();
	        		 invoiceValueAfterRoundOff = cnDnAddDetail.getInvoiceValueAfterRoundOff();
	        		 cndnFooterNote = cnDnAddDetail.getFooter();
	        		 cndnCreationType = cnDnAddDetail.getReason();
	        		 isAdditionalChargePresent = cnDnAddDetail.getIsAdditionalChargePresent();
	        	 } 
	         }
	         
	      //Fetch the CnDntype - End ------------------------------------------------------------------- 
	         
        	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        	if(null != cndnFooterNote){
        		writer.setPageEvent(new MyFooter(cndnFooterNote));
        	}
	        document.open();
	        PdfPTable table = new PdfPTable(8);
	        
	        //table.setWidths(new int[]{ 17, 42, 10, 9, 6, 16, 9, 12});
	        table.setWidths(new int[]{ 20, 36, 12, 10, 10, 13, 11, 12});
	        
	        
	   
	      //UserInfo LHS - Start -------------------------------------------------------------------------
	        
	         String regularFont="regular";  
	         String boldFont="Bold";
	         
	         Phrase p = new Phrase();
	         //String filePath = "C:/apps/gstn/logo/212/logo_212.JPEG";
	         boolean isImagePresent = false;
	         
	         PdfPCell pv = createImageCell(logoImagePath, 1, 1, Element.ALIGN_LEFT,6);
	         if(pv.getImage() != null){
	        	isImagePresent = true;
	        	table.addCell(pv);
	         }
	          	
	         String content=" "+user.getOrganizationMaster().getOrgName();
	         addMoreText(p, content, regularFont);
	         content="\n "+gstinDetails.getGstinAddressMapping().getAddress()+" , "+gstinDetails.getGstinAddressMapping().getCity()+" , "+gstinDetails.getGstinAddressMapping().getState()+" [ "+gstinDetails.getState()+" ]  , "+gstinDetails.getGstinAddressMapping().getPinCode();
	         addMoreText(p, content, regularFont);
	         
	         content=" \n GSTIN No. : ";
	         addMoreText(p, content, boldFont);
	         content=invoiceDetails.getGstnStateIdInString();
	         addMoreText(p, content, regularFont);
	         
	         content=" \n PAN No. : ";
	         addMoreText(p, content, boldFont);
	         content=user.getOrganizationMaster().getPanNumber();
	         addMoreText(p, content, regularFont);
	         
	         content="\n \n Original Tax Document No.: ";
	         addMoreText(p, content, boldFont);
	         content = invoiceDetails.getInvoiceNumber();
	         addMoreText(p, content, regularFont);
	         
	         content=" \n Original Tax Document Date: ";
	         addMoreText(p, content, boldFont);
	         content = GSTNUtil.convertTimestampToString1(invoiceDetails.getInvoiceDate());
	         addMoreText(p, content, regularFont);
	 		
	         
	         if(isImagePresent){
		 		table.addCell(createCell(p, 1, 3, Element.ALIGN_LEFT,6));
		 	 }else{
		 		table.addCell(createCell(p, 1, 4, Element.ALIGN_LEFT,6));
		 	 }
	         
	        // table.addCell(createCell(p, 1, 4, Element.ALIGN_LEFT,6));
	         
	      //UserInfo LHS - End ---------------------------------------------------------------------------
	        
	         //Invoice INFO RHS - Start  --------------------------------------------------------------------  
	         p = new Phrase();
	         if(cnDnType.equals(GSTNConstants.DEBIT_NOTE)){
	        	 content="\n \n \n Debit Note No. ";
	         }else{
	        	 content="\n \n \n Credit Note No. ";
	         }
	         
	         addMoreText(p, content, boldFont);
	         content = currentInvoiceNumber;
	         addMoreText(p, content, regularFont);
	       
	         if(cnDnType.equals(GSTNConstants.DEBIT_NOTE)){
	        	 content="\n \n \n Debit Note Date : ";
	         }else{
	        	 content="\n \n \n Credit Note Date : ";
	         }
	         addMoreText(p, content, boldFont);
	         content = currentInvoiceDate;
	         addMoreText(p, content, regularFont);
	         table.addCell(createCell(p, 1, 4, Element.ALIGN_LEFT,6));
	         
	      //Invoice INFO RHS - End -----------------------------------------------------------------------
	         
	  
	        
	         p = new Phrase();
	         if(cnDnType.equals(GSTNConstants.DEBIT_NOTE)){
	        	 content="\n DEBIT NOTE\n \n";
	         }else{
	        	 content="\n CREDIT NOTE\n \n";
	         }
	         
	         addMoreText(p, content, boldFont);
	         table.addCell(createCell(p, 1, 8, Element.ALIGN_CENTER, 1));
	         
	        
	         //Bill To - LHS - Start ------------------------------------------------------------------------
	         p = new Phrase();
	         content="\nBill To ";
	         addMoreText(p, content, boldFont);
	         
	         content="\nName : ";
	         addMoreText(p, content, boldFont);
	         content=invoiceDetails.getCustomerDetails().getCustName();
	         addMoreText(p, content, regularFont);
	         
	         content="\nAddress : ";
	         addMoreText(p, content, boldFont);
	         content=invoiceDetails.getCustomerDetails().getCustAddress1();
	         addMoreText(p, content, regularFont);
	         
	         content="\nCity : ";
	         addMoreText(p, content, boldFont);
	         content=invoiceDetails.getCustomerDetails().getCustCity();
	         addMoreText(p, content, regularFont);
	         
	         content="\nState : ";
	         addMoreText(p, content, boldFont);
	         content=invoiceDetails.getCustomerDetails().getCustState();
	         addMoreText(p, content, regularFont);
	         
	 	        if(invoiceDetails.getCustomerDetails().getCustGstinState() != null){
	 	        	 content=" [ "+invoiceDetails.getCustomerDetails().getCustGstinState()+" ] ";
	 	             addMoreText(p, content, regularFont);
	 	        }
	 	        
	 	        content="\nState Code : ";
	 	        addMoreText(p, content, boldFont);
	 	        content=customerStateCode;
	 	        addMoreText(p, content, regularFont);
	 	        
	 	        content="\nGSTIN/Unique Code : ";
	 	        addMoreText(p, content, boldFont);
	 	        content=invoiceDetails.getCustomerDetails().getCustGstId();
	 	        addMoreText(p, content, regularFont);
	 	        
	 	        content="\n\n";
	 	        addMoreText(p, content, regularFont);
	         table.addCell(createCell(p, 1, 2, Element.ALIGN_LEFT, 7));
	      //Bill To - LHS - End  -------------------------------------------------------------------------
	        
	     //Ship To - RHS - Start ------------------------------------------------------------------------
	      
	        	p = new Phrase();
	        	table.addCell(createCell(""+"  \n \n \n \n \n \n ", 1, 6, Element.ALIGN_LEFT, 7));
	        
	     //Ship To - RHS - End  ------------------------------------------------------------------------- 
	        
	        //Invoice table - Start ------------------------------------------------------------------------
	        //Table heading containing five columns - Start
	        p = new Phrase();
	        content="Sr.No";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
	        p = new Phrase();
	        if(invoiceDetails.getInvoiceFor().equals("Product")){
	        	content="Description of Goods";
	    	}else{
	    		content="Description of Services";
	        }
	        
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
	        p = new Phrase();
	        if(invoiceDetails.getInvoiceFor().equals("Product")){
	   		   	content="HSN Code";
	    	}else{
	    		content="SAC Code";
	        }
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
	        p = new Phrase();
	        content="Quantity";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
	        p = new Phrase();
	        content="UOM";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
	        p = new Phrase();
	        content="Rate (Rs.)/UOM";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
	       /* p = new Phrase();
	        content="Discount (Rs.)";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));*/
	        p = new Phrase();
	        content="Total (Rs.)";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
	      //Table heading containing five columns - End
	      //Looping through Service List - Start  
	        double totalAmount = 0;
	        double cessTotalTax = 0;
	       
	        String categoryType = "";
	        int i = 1;
	        int isDiffPercentPresent = 0;
	        for(InvoiceCnDnDetails s : invoiceDetails.getCnDnList()){
	        	if(s.getIterationNo() == iterationNo){
	        		String containsDiffPercentage = "";
		        	if(s.getDiffPercent() != null && s.getDiffPercent().equals("Y")){
		        		containsDiffPercentage = "(*)";
		        		isDiffPercentPresent++;
		        	}
		        	table.addCell(createCell(Integer.toString(i), 1, 1, Element.ALIGN_CENTER, 1));
		            table.addCell(createCell(s.getServiceIdInString()+containsDiffPercentage, 1, 1, Element.ALIGN_LEFT, 1));
		            table.addCell(createCell(s.getHsnSacCode(), 1, 1, Element.ALIGN_LEFT, 1));
		            table.addCell(createCell(s.getQuantity()+"", 1, 1, Element.ALIGN_LEFT, 1));
		            table.addCell(createCell(GSTNUtil.toTitleCase(s.getUnitOfMeasurement()), 1, 1, Element.ALIGN_LEFT, 1));
		            table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(s.getRate())+"", 1, 2, Element.ALIGN_LEFT, 1));
		            /*table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(s.getOfferAmount())+"", 1, 1, Element.ALIGN_LEFT, 1));*/
		            table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(s.getPreviousAmount() - s.getOfferAmount())+"", 1, 2, Element.ALIGN_CENTER, 1));
		            totalAmount = totalAmount + (s.getPreviousAmount() - s.getOfferAmount());
		            cessTotalTax = cessTotalTax + s.getCess();
		            categoryType = s.getCategoryType();
		            i++;
	        	}
	        }
	      //Looping through Service List - End   
	        
	     //SubTotal - Start-----------------------------------------------------------------------------------
	        p = new Phrase();
	        content="Taxable Value";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        table.addCell(createCell(p, 1, 6, Element.ALIGN_LEFT, 1));
	        table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(totalAmount) + "", 1, 1, Element.ALIGN_CENTER, 1));
	     //SubTotal - End ------------------------------------------------------------------------------------
	        //table.addCell(createCell(" ", 1, 4, Element.ALIGN_CENTER, 1));
	        
	     //leave a blank line -Start---------------------------------------------------------------------------    
	        table.addCell(createCell(" ", 1, 8, Element.ALIGN_CENTER, 1));
	     //leave a blank line -End--------------------------------------------------------------------------- 
	        
	        
	     //Loop through Additional Charges - Start
	   /*  String containsAdditionalCharges = "";
	     double addChargeAmount = 0;
	     if(invoiceDetails.getAddChargesList() != null && invoiceDetails.getAddChargesList().size() > 0){
	    	 containsAdditionalCharges = "YES";
	    	 
	    	 //Show 1st row of Add : Additional Charges
	    	 p = new Phrase();
		     content = "Add : Additional Charges";
		     addMoreText(p, content, boldFont);
		        
	    	 table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
	    	 table.addCell(createCell("", 1, 6, Element.ALIGN_LEFT, 1));
	    	 
	    	//Show 2nd row of Additional Charges
	    	 for(InvoiceAdditionalChargeDetails addChgDet : invoiceDetails.getAddChargesList()){
	    		 table.addCell(createCell(addChgDet.getAdditionalChargeName()+"", 1, 2, Element.ALIGN_CENTER, 1));
	    		 table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(addChgDet.getAdditionalChargeAmount())+"", 1, 5, Element.ALIGN_LEFT, 1));
		         table.addCell(createCell("", 1, 1, Element.ALIGN_CENTER, 1));
		         
		         addChargeAmount = addChargeAmount + addChgDet.getAdditionalChargeAmount();
	    	 }
	    	 
	    	//Show 3rd row of Additional Charges
	    	 p = new Phrase();
		     content="Total Additional Charges (B)";
		     addMoreText(p, content, boldFont);
	    	 table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
    		 table.addCell(createCell("", 1, 5, Element.ALIGN_LEFT, 1));
	         table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(addChargeAmount)+"", 1, 1, Element.ALIGN_CENTER, 1));
	         
	        //Show 4th row of Additional Charges
	         p = new Phrase();
		     content = "Total Value (A+B)";
		     addMoreText(p, content, boldFont);
	         table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
    		 table.addCell(createCell("", 1, 5, Element.ALIGN_LEFT, 1));
	         table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(totalAmount + addChargeAmount)+"", 1, 1, Element.ALIGN_CENTER, 1));
	    	 
	         //leave a blank line -Start---------------------------------------------------------------------------    
		        table.addCell(createCell(" ", 1, 8, Element.ALIGN_CENTER, 1));
		     //leave a blank line -End--------------------------------------------------------------------------- 
	     }*/
	        
	     //Loop through Additional Charges - End
	     
	  
	        
	        
	     //Loop through GST TAX - Start 
	        
	        p = new Phrase();
		    content = "Add : Taxes";
		    addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
	    	table.addCell(createCell("", 1, 6, Element.ALIGN_LEFT, 1));
	        
	        if(categoryType.equals("CATEGORY_WITH_SGST_CSGT") || categoryType.equals("CATEGORY_WITH_UGST_CGST")){
	        	for (Map.Entry<String,Map<String,Double>> entry : gstMap.entrySet()) {
	        	    String key = entry.getKey();
	        	    Map<String,Double> value = entry.getValue();
	        	   if(key.equals("cgst")){
	        		   int cgstLoopCount = 1;
	        		   for(Map.Entry<String,Double> insideEntry : value.entrySet()){
	        			   if(cgstLoopCount == 1){
	        				    table.addCell(createCell("Central Goods & Services Tax", 1, 2, Element.ALIGN_CENTER, 1));
	        		            table.addCell(createCell(insideEntry.getKey()+"% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }else{
	        				    table.addCell(createCell("", 1, 2, Element.ALIGN_CENTER, 1));
	        				    table.addCell(createCell(insideEntry.getKey()+"% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }
	        			   cgstLoopCount = cgstLoopCount + 1;
	        		   }
	        	   }
	        	   
	        	   if(key.equals("cgstDiffPercent")){
	        		   int cgstLoopCount = 1;
	        		   for(Map.Entry<String,Double> insideEntry : value.entrySet()){
	        			   if(cgstLoopCount == 1){
	        				    table.addCell(createCell("Central Goods & Services Tax (*)", 1, 2, Element.ALIGN_CENTER, 1));
	        		            table.addCell(createCell(insideEntry.getKey()+"% of 65% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces(((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }else{
	        				    table.addCell(createCell("", 1, 2, Element.ALIGN_CENTER, 1));
	        				    table.addCell(createCell(insideEntry.getKey()+"% of 65% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces(((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }
	        			   cgstLoopCount = cgstLoopCount + 1;
	        		   }
	        	   }
	        	   
	        	}
	        }
	        
	        if(categoryType.equals("CATEGORY_WITH_SGST_CSGT")){
	        	for (Map.Entry<String,Map<String,Double>> entry : gstMap.entrySet()) {
	        	    String key = entry.getKey();
	        	    Map<String,Double> value = entry.getValue();
	        	   if(key.equals("sgst")){
	        		   int sgstLoopCount = 1;
	        		   for(Map.Entry<String,Double> insideEntry : value.entrySet()){
	        			   if(sgstLoopCount == 1){
	        				    table.addCell(createCell("State Goods & Services Tax", 1, 2, Element.ALIGN_CENTER, 1));
	        		            table.addCell(createCell(insideEntry.getKey()+"% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }else{
	        				    table.addCell(createCell("", 1, 2, Element.ALIGN_CENTER, 1));
	        				    table.addCell(createCell(insideEntry.getKey()+"% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }
	        			   sgstLoopCount = sgstLoopCount + 1;
	        		   }
	        	   }
	        	   
	        	   if(key.equals("sgstDiffPercent")){
	        		   int sgstLoopCount = 1;
	        		   for(Map.Entry<String,Double> insideEntry : value.entrySet()){
	        			   if(sgstLoopCount == 1){
	        				    table.addCell(createCell("State Goods & Services Tax (*)", 1, 2, Element.ALIGN_CENTER, 1));
	        		            table.addCell(createCell(insideEntry.getKey()+"% of 65% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces(((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }else{
	        				    table.addCell(createCell("", 1, 2, Element.ALIGN_CENTER, 1));
	        				    table.addCell(createCell(insideEntry.getKey()+"% of 65% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces(((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }
	        			   sgstLoopCount = sgstLoopCount + 1;
	        		   }
	        	   }
	        	}
	        }
	        
	        if(categoryType.equals("CATEGORY_WITH_IGST") || categoryType.equals("CATEGORY_EXPORT_WITH_IGST")){
	        	for (Map.Entry<String,Map<String,Double>> entry : gstMap.entrySet()) {
	        	    String key = entry.getKey();
	        	    Map<String,Double> value = entry.getValue();
	        	   if(key.equals("igst")){
	        		   int igstLoopCount = 1;
	        		   for(Map.Entry<String,Double> insideEntry : value.entrySet()){
	        			   if(igstLoopCount == 1){
	        				    table.addCell(createCell("Integrated Goods & Services Tax", 1, 2, Element.ALIGN_CENTER, 1));
	        		            table.addCell(createCell(insideEntry.getKey()+"% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }else{
	        				    table.addCell(createCell("", 1, 2, Element.ALIGN_CENTER, 1));
	        				    table.addCell(createCell(insideEntry.getKey()+"% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }
	        			   igstLoopCount = igstLoopCount + 1;
	        		   }
	        	   }
	        	   
	        	   if(key.equals("igstDiffPercent")){
	        		   int igstLoopCount = 1;
	        		   for(Map.Entry<String,Double> insideEntry : value.entrySet()){
	        			   if(igstLoopCount == 1){
	        				    table.addCell(createCell("Integrated Goods & Services Tax (*)", 1, 2, Element.ALIGN_CENTER, 1));
	        		            table.addCell(createCell(insideEntry.getKey()+"% of 65% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces(((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }else{
	        				    table.addCell(createCell("", 1, 2, Element.ALIGN_CENTER, 1));
	        				    table.addCell(createCell(insideEntry.getKey()+"% of 65% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces(((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }
	        			   igstLoopCount = igstLoopCount + 1;
	        		   }
	        	   }
	        	}
	        }
	        
	        if(categoryType.equals("CATEGORY_WITH_UGST_CGST")){
	        	for (Map.Entry<String,Map<String,Double>> entry : gstMap.entrySet()) {
	        	    String key = entry.getKey();
	        	    Map<String,Double> value = entry.getValue();
	        	   if(key.equals("ugst")){
	        		   int ugstLoopCount = 1;
	        		   for(Map.Entry<String,Double> insideEntry : value.entrySet()){
	        			   if(ugstLoopCount == 1){
	        				    table.addCell(createCell("Union Territory Goods & Services Tax", 1, 2, Element.ALIGN_CENTER, 1));
	        		            table.addCell(createCell(insideEntry.getKey()+"% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }else{
	        				    table.addCell(createCell("", 1, 2, Element.ALIGN_CENTER, 1));
	        				    table.addCell(createCell(insideEntry.getKey()+"% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }
	        			   ugstLoopCount = ugstLoopCount + 1;
	        		   }
	        	   }
	        	   
	        	   if(key.equals("ugstDiffPercent")){
	        		   int ugstLoopCount = 1;
	        		   for(Map.Entry<String,Double> insideEntry : value.entrySet()){
	        			   if(ugstLoopCount == 1){
	        				    table.addCell(createCell("Union Territory Goods & Services Tax (*)", 1, 2, Element.ALIGN_CENTER, 1));
	        		            table.addCell(createCell(insideEntry.getKey()+"% of 65% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces(((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }else{
	        				    table.addCell(createCell("", 1, 2, Element.ALIGN_CENTER, 1));
	        				    table.addCell(createCell(insideEntry.getKey()+"% of 65% on "+insideEntry.getValue()+"", 1, 5, Element.ALIGN_LEFT, 1));
	        		            //table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
	        		            table.addCell(createCell(GSTNUtil.convertDoubleTo3DecimalPlaces(((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65))+"", 1, 1, Element.ALIGN_CENTER, 1));
	        			   }
	        			   ugstLoopCount = ugstLoopCount + 1;
	        		   }
	        	   }
	        	}
	        } 
	        
	      //Loop through GST TAX - End  
	      
	      //Cess Amount - Start  
	        table.addCell(createCell("Cess", 1, 2, Element.ALIGN_CENTER, 1));
	        //table.addCell(createCell("", 1, 3, Element.ALIGN_LEFT, 1));
	        table.addCell(createCell("", 1, 5, Element.ALIGN_LEFT, 1));
	        table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(cessTotalTax) + "", 1, 1, Element.ALIGN_CENTER, 1));
	      //Cess Amount - End  
	        
	      //Total Tax - Start 
	       
        	table.addCell(createCell("Total Taxes", 1, 2, Element.ALIGN_CENTER, 1));
	        table.addCell(createCell("", 1, 5, Element.ALIGN_LEFT, 1));
	        table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(totalTaxes) + "", 1, 1, Element.ALIGN_CENTER, 1));
	        
	       
	      //leave a blank line - Start --------------------------------------------------------------------------- 
	        table.addCell(createCell(" ", 1, 8, Element.ALIGN_CENTER, 1));
	      //leave a blank line - End ---------------------------------------------------------------------------     
	        
	      //Total Invoice Value  - Start --------------------------------------------------------------------------- 
	        p = new Phrase();
	        content = "";
	        if(cnDnType.equals(GSTNConstants.DEBIT_NOTE)){
	        	content = "Total Debit Note Value";
	        }else{
	        	content = "Total Credit Note Value";
	        }
	        
		    addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
		    table.addCell(createCell("", 1, 5, Element.ALIGN_LEFT, 1));
		    table.addCell(createCell(invoiceValue + "", 1, 1, Element.ALIGN_CENTER, 1));
	       
	        
	        //Round-Off
	        table.addCell(createCell("Round off", 1, 2, Element.ALIGN_CENTER, 1));
	        table.addCell(createCell("", 1, 5, Element.ALIGN_LEFT, 1));
	        table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(invoiceValueAfterRoundOff - invoiceValue) + "", 1, 1, Element.ALIGN_CENTER, 1));
	        
	        //Amount After Round-Off
	        p = new Phrase();
	        if(cnDnType.equals(GSTNConstants.DEBIT_NOTE)){
	        	content = "Total Debit Note Value (After Round off)";
	        }else{
	        	content = "Total Credit Note Value (After Round off)";
	        }
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
	        table.addCell(createCell("", 1, 5, Element.ALIGN_LEFT, 1));
	        table.addCell(createCell(invoiceValueAfterRoundOff+ "", 1, 1, Element.ALIGN_CENTER, 1));
	        
	        
	     //Total Invoice Value  - End ---------------------------------------------------------------------------  
	        
	     //Total Invoice Value In Words  - Start ----------------------------------------------------------------    
	        p = new Phrase();
	        if(cnDnType.equals(GSTNConstants.DEBIT_NOTE)){
	        	content="Total Debit Note Value in Rs. (In Words) : ";
	        }else{
	        	content="Total Credit Note Value in Rs. (In Words) : ";
	        }
	        addMoreText(p, content, boldFont);
	        content=amtInWords;
	        addMoreText(p, content, regularFont);
	        table.addCell(createCell(p, 1, 8, Element.ALIGN_LEFT, 1));
	     //Total Invoice Value In Words - End  -------------------------------------------------------------------------------  
	        
	      //* Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government. - Start    
	        if(isDiffPercentPresent > 0){
	        	p = new Phrase();
		        content="(*) Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government.";
		        addMoreText(p, content, boldFont);
		        content="";
		        addMoreText(p, content, regularFont);
		        table.addCell(createCell(p, 1, 8, Element.ALIGN_LEFT, 1));
	        }
	     //* Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government. - End
	        
	     //Customer PO/WO  - Start    
	        p = new Phrase();
	        content="Customer PO/ WO No. ";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 3, Element.ALIGN_LEFT, 2));
	        table.addCell(createCell(""+invoiceDetails.getPoDetails(), 1, 5, Element.ALIGN_LEFT, 2));
	     //Customer PO/WO  - End     
	        table.addCell(createCell("Recipient Name \n \nRecipient Signature \n ", 1, 3, Element.ALIGN_LEFT, 4));
	        table.addCell(createCell("For Supplier Name \n \nAuthorised Signatory \n", 1, 5, Element.ALIGN_LEFT, 4));
	        
	      
	        p = new Phrase();
	        content="Declaration :";
	        addMoreText(p, content, boldFont);
	        content="\nI) Certified that all the particulars given above are true and correct. The amounts indicated represents the price actually charged and there is no flow of additional consideration directly or indirectly from the Buyer.\nII) The normal terms governing the above sale are printed overleaf. ";
	        addMoreText(p, content, regularFont);
	        
	        table.addCell(createCell(p, 1, 8, Element.ALIGN_LEFT, 4));
	        
	        table.setTotalWidth(540);
	        table.setLockedWidth(true);
	        table.setWidthPercentage(100);
	        
	        document.add(table);
	        document.close();
	    	status = GSTNConstants.SUCCESS;
		} catch (NumberFormatException e) {
			logger.error("Error in:",e);
		}
        mapResponse.put(GSTNConstants.STATUS, status);
        logger.info("Exit");
        return mapResponse;
	}
	
	public Map<String,String> createPdf(String dest,InvoiceDetails invoiceDetails,
			GSTINDetails gstinDetails, UserMaster user,
			String customerStateCode, Map<String, Map<String, Double>> gstMap,
			String amtInWords,String logoImagePath) throws IOException, DocumentException {
    	logger.info("Entry");
    	Map<String,String> mapResponse = new HashMap<String,String>();
    	String status = GSTNConstants.FAILURE;
        Document document = new Document();
        try {
        	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
	        document.open();
	        PdfPTable table = new PdfPTable(10);
	       
	        //table.setWidths(new int[]{ 20, 36, 12, 10, 10, 13, 11, 12});
	        table.setWidths(new int[]{ 10, 5, 15, 10, 10, 10,10, 10, 10, 10});
	   
	     //1st row LHS - Start -------------------------------------------------------------------------
	        
	         String regularFont="regular";  
	         String boldFont="Bold";
	         
	         Phrase p = new Phrase();
	         boolean isImagePresent = false;
	         
	         PdfPCell pv = createImageCell(logoImagePath, 1, 2, Element.ALIGN_LEFT,4);
	         if(pv.getImage() != null){
	        	isImagePresent = true;
	        	table.addCell(pv);
	         }
	          	
	         String content=" "+user.getOrganizationMaster().getOrgName();
	         addMoreText(p, content, "h2");
	         content="\n\n "+gstinDetails.getGstinAddressMapping().getAddress()+" , "+gstinDetails.getGstinAddressMapping().getCity()+" , "+gstinDetails.getGstinAddressMapping().getPinCode()+".";
	         addMoreText(p, content, regularFont);
	         
	         content="\n "+gstinDetails.getGstinAddressMapping().getState()+" - "+gstinDetails.getState()+"   ";
	         addMoreText(p, content, regularFont);
	         
	         content=" \n Phone. : ";
	         addMoreText(p, content, regularFont);
	         content="";
	         addMoreText(p, content, regularFont);
		     
	 		if(isImagePresent){
	 			table.addCell(createCellWithDynamicBorders(p, 1, 5, Element.ALIGN_LEFT,4,1,0,0,0));
	 		}else{
	 			table.addCell(createCellWithDynamicBorders(p, 1, 7, Element.ALIGN_LEFT,4,1,0,0,1));
	 		}
	    //1st row LHS - End ---------------------------------------------------------------------------
	 		
	 	//1st row RHS - Start
	 		p = new Phrase();
	 		content=" \n\n GSTIN No. : ";
	        addMoreText(p, content, boldFont);
	        content=invoiceDetails.getGstnStateIdInString(); 
	        addMoreText(p, content, regularFont);
	         
	        content=" \n PAN No.    : ";
	        addMoreText(p, content, boldFont);
	        content=user.getOrganizationMaster().getPanNumber();
	        addMoreText(p, content, regularFont);
	      
	        table.addCell(createCellWithDynamicBorders(p, 1, 3, Element.ALIGN_LEFT,4,1,1,1,0));
	 	//1st row RHS - End
	        
	   //2nd row - Start
	        String documentType = "";
	        String documentTypeToDisplay = "Invoice";
	        if((GSTNConstants.DOCUMENT_TYPE_INVOICE.equals(invoiceDetails.getDocumentType())) || (GSTNConstants.DOCUMENT_TYPE_REVERSE_CHARGE.equals(invoiceDetails.getDocumentType())) || (GSTNConstants.DOCUMENT_TYPE_E_COMMERCE_TAX_INVOICE.equals(invoiceDetails.getDocumentType()))){
	        	documentType = "Tax Invoice";
	        }else if((GSTNConstants.DOCUMENT_TYPE_BILL_OF_SUPPLY.equals(invoiceDetails.getDocumentType())) || (GSTNConstants.DOCUMENT_TYPE_E_COMMERCE_BILL_OF_SUPPLY.equals(invoiceDetails.getDocumentType()))){
	        	documentType = "Bill Of Supply";
	        	documentTypeToDisplay = "Bill";
	        }else if(GSTNConstants.DOCUMENT_TYPE_INVOICE_CUM_BILL_OF_SUPPLY.equals(invoiceDetails.getDocumentType())){
	        	documentType = "Invoice cum Bill Of Supply";
	        }else{
	        	documentType = "Tax Invoice";
	        }
	        
	        p = new Phrase();
	        content = documentType+" \n";
	        addMoreText(p, content, "invoiceBoldFont");
	        table.addCell(createCell(p, 1, 10, Element.ALIGN_CENTER, 2));
	        
	   //2nd row - End
	   //3rd row - Start
	        //Part 1 - Start
	        p = new Phrase();
            content=" "+documentTypeToDisplay+" No : ";
            addMoreText(p, content, boldFont);
            content=invoiceDetails.getInvoiceNumber();
            addMoreText(p, content, regularFont);
       
            content=" \n "+documentTypeToDisplay+" Date : ";
            addMoreText(p, content, boldFont);
            content=GSTNUtil.convertTimestampToString1(invoiceDetails.getInvoiceDate());
            addMoreText(p, content, regularFont);
            
            if(invoiceDetails.getPoDetails() != null){
            	content=" \n PO/WO Number : ";
                addMoreText(p, content, boldFont);
                content=invoiceDetails.getPoDetails();
                addMoreText(p, content, regularFont);
            }
            
            if(invoiceDetails.getInvoiceFor().equals("Product")){
            	content=" \n Material Location : ";
                addMoreText(p, content, boldFont);
                content="";
                addMoreText(p, content, regularFont);
            }
            
            table.addCell(createCell(p, 1, 4, Element.ALIGN_LEFT,6));
            //Part 1 - End 
            
          //Part 2 - Bill To - Start ------------------------------------------------------------------------
	         p = new Phrase();
	         content="Bill To : ";
	         addMoreText(p, content, boldFont);
	         
	         content="\n"+invoiceDetails.getCustomerDetails().getCustName();
	         addMoreText(p, content, regularFont);
	         
	         content="\n"+invoiceDetails.getCustomerDetails().getCustAddress1();
	         addMoreText(p, content, regularFont);
	         
	         content="\n"+invoiceDetails.getCustomerDetails().getCustCity();
	         addMoreText(p, content, regularFont);
	        
	         content="\n"+invoiceDetails.getCustomerDetails().getCustState();
	         addMoreText(p, content, regularFont);
	         
	 	        if(invoiceDetails.getCustomerDetails().getCustGstinState() != null){
	 	        	 content=" - "+invoiceDetails.getCustomerDetails().getCustGstinState();
	 	             addMoreText(p, content, regularFont);
	 	        }
	 	        
	 	        content="( "+customerStateCode+" ) ";
	 	        addMoreText(p, content, regularFont);
	 	        
	 	        content="\nGSTIN : ";
	 	        addMoreText(p, content, regularFont);
	 	        content=invoiceDetails.getCustomerDetails().getCustGstId();
	 	        addMoreText(p, content, regularFont);
	 	        
	         table.addCell(createCell(p, 1, 3, Element.ALIGN_LEFT, 6));
	      //Part 2 - Bill To  - End  -------------------------------------------------------------------------
	        
	      //Part 3  - Start ------------------------------------------------------------------------
	        if(invoiceDetails.getInvoiceFor().equals("Product")){
	        	if(invoiceDetails.getBillToShipIsChecked().equals("No")){
	        		p = new Phrase();
	                content="Ship To :";
	                addMoreText(p, content, boldFont);
	                
	    	        content="\n"+invoiceDetails.getShipToCustomerName();
	    	        addMoreText(p, content, regularFont);
	    	        
	    	        content="\n"+invoiceDetails.getShipToAddress();
	    	        addMoreText(p, content, regularFont);
	    	        
	    	        content="\n"+invoiceDetails.getShipToState()+" - "+invoiceDetails.getShipToStateCodeId();
	    	        addMoreText(p, content, regularFont);
	    	        
	    	        content="( "+invoiceDetails.getShipToStateCode()+" )";
	    	        addMoreText(p, content, regularFont);
	    	        
	    	        content="\n\nPlace of supply : ";
	 		        addMoreText(p, content, regularFont);
	 		        content=invoiceDetails.getPlaceOfSupply();
	 		        addMoreText(p, content, regularFont);
	        		
	        	}
	        	if(invoiceDetails.getBillToShipIsChecked().equals("Yes")){
	       		 p = new Phrase();
	       	        content="Ship To : ";
	       	        addMoreText(p, content, boldFont);
	       	        
	       	        content="\n"+invoiceDetails.getCustomerDetails().getCustName();
	       	        addMoreText(p, content, regularFont);
	       	        
	       	        content="\n"+invoiceDetails.getCustomerDetails().getCustAddress1();
	       	        addMoreText(p, content, regularFont);
	       	        
	       	        content="\n"+invoiceDetails.getCustomerDetails().getCustCity();
	       	        addMoreText(p, content, regularFont);
	       	        
	       	        content="\n"+invoiceDetails.getCustomerDetails().getCustState();
	       	        addMoreText(p, content, regularFont);
	       
       		        if(invoiceDetails.getCustomerDetails().getCustGstinState() != null){
       		        	 content=" - "+invoiceDetails.getCustomerDetails().getCustGstinState();
       	        	     addMoreText(p, content, regularFont);
       		        }
	       		        
           	        content="( "+customerStateCode+" )";
           	        addMoreText(p, content, regularFont);
	           	        
	           	     
	 		        content="\nPlace of supply : ";
	 		        addMoreText(p, content, regularFont);
	 		        content = ((invoiceDetails.getPlaceOfSupply() != null) ? invoiceDetails.getPlaceOfSupply() : " ");
	 		        addMoreText(p, content, regularFont);
	       	}
	        	table.addCell(createCell(p, 1, 3, Element.ALIGN_LEFT, 6));
	        }else{
	        	p = new Phrase();
	        	
	        	//table.addCell(createCell(""+"  \n \n \n \n \n \n ", 1, 6, Element.ALIGN_LEFT, 6));
	        	table.addCell(createCell(""+"  \n \n \n \n \n \n ", 1, 3, Element.ALIGN_LEFT, 6));
	        }
	        //table.addCell(createCell(p, 1, 6, Element.ALIGN_LEFT, 6));
	     //Part3 3 - Ship To  - End  ------------------------------------------------------------------------- 
	        
	   //3rd row - End
	        
	   //4th row - Start 
	        if(invoiceDetails.getInvoiceFor().equals("Product")){
	        	//Table heading containing eight columns - Start
		        p = new Phrase();
		        content="Sr.No";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_RIGHT, 1));
		        
		        p = new Phrase();
		        content="Item Name";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 2, Element.ALIGN_LEFT, 1));
		        
		        p = new Phrase();
		        content="HSN Code";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content="Quantity";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content="UOM";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content="Rate ";
		        addMoreText(p, content, boldFont);
		        addMoreText(p, "a", "rupee");
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content="Total ";
		        addMoreText(p, content, boldFont);
		        addMoreText(p, "a", "rupee");
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content="Discount ";
		        addMoreText(p, content, boldFont);
		        addMoreText(p, "a", "rupee");
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content="Amount ";
		        addMoreText(p, content, boldFont);
		        addMoreText(p, "a", "rupee");
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		      //Table heading containing eight columns - End
	        	
	        }else{
	        	//Table heading containing six columns - Start
		        p = new Phrase();
		        content="Sr.No";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_RIGHT, 1));
		        
		        p = new Phrase();
		        content="Service Name";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 5, Element.ALIGN_LEFT, 1));
		        
		        p = new Phrase();
		        content="SAC Code";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		        
		        
		        p = new Phrase();
		        content="Charges ";
		        addMoreText(p, content, boldFont);
		        addMoreText(p, "a", "rupee");
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content="Discount ";
		        addMoreText(p, content, boldFont);
		        addMoreText(p, "a", "rupee");
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content="Amount ";
		        addMoreText(p, content, boldFont);
		        addMoreText(p, "a", "rupee");
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		      //Table heading containing eight columns - End
	        } 
	   //4th row - End
	   //5th row - Start
	        double totalAmount = 0;
	        double cessTotalTax = 0;
	        String categoryType = "";
	        double totalQty = 0;
	        double totalPreviousAmount = 0;
	        double totalDiscount = 0;
	        int i = 1;
        	int itemsCount = invoiceDetails.getServiceList().size();
        	int blankLoopCount = 0;
	        
	        int isDiffPercentPresent = 0;
	        if(invoiceDetails.getInvoiceFor().equals("Product")){
	        	//Looping through Product List - Start  
		      
		        for(InvoiceServiceDetails s : invoiceDetails.getServiceList()){
		        	String descriptionTxt = "";
		        	String containsDiffPercentage = "";
		        	if(s.getDiffPercent() != null && s.getDiffPercent().equals("Y")){
		        		containsDiffPercentage = "(*)";
		        		isDiffPercentPresent++;
		        	}
		        	if(StringUtils.isNotEmpty(s.getIsDescriptionChecked()) && (s.getIsDescriptionChecked().equals("Yes"))){
		        		descriptionTxt = " - "+s.getDescription();
		        	}
		        	table.addCell(createCellWithDynamicBorders(Integer.toString(i), 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
		            table.addCell(createCellWithDynamicBorders(s.getServiceIdInString()+containsDiffPercentage+descriptionTxt, 1, 2, Element.ALIGN_LEFT, 1,0,0,0,1));
		            table.addCell(createCellWithDynamicBorders(s.getHsnSacCode(), 1, 1, Element.ALIGN_CENTER, 1,0,0,0,1));
		            table.addCell(createCellWithDynamicBorders(s.getQuantity()+"", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
		            table.addCell(createCellWithDynamicBorders(s.getUnitOfMeasurement(), 1, 1, Element.ALIGN_LEFT, 1,0,0,0,1));
		            table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(s.getRate())+"", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
		            table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(s.getPreviousAmount())+"", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
		            table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(s.getOfferAmount())+"", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
		            table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(s.getPreviousAmount() - s.getOfferAmount())+"", 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,1));
		            totalAmount = totalAmount + (s.getPreviousAmount() - s.getOfferAmount());
		            cessTotalTax = cessTotalTax + s.getCess();
		            categoryType = s.getCategoryType();
		            totalQty = totalQty + s.getQuantity();
		            totalPreviousAmount = totalPreviousAmount + s.getPreviousAmount();
		            totalDiscount = totalDiscount + s.getOfferAmount();
		            i++;
		        }
		        
		        if(itemsCount < 10){
		        	blankLoopCount = 10 - itemsCount;
		        	for(int u = 0; u < blankLoopCount; u++){
			        	table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
			            table.addCell(createCellWithDynamicBorders(" ", 1, 2, Element.ALIGN_LEFT, 1,0,0,0,1));
			            table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_CENTER, 1,0,0,0,1));
			            table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
			            table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_LEFT, 1,0,0,0,1));
			            table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
			            table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
			            table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
			            table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,1)); 
			        }
		        }
		      //Looping through Product List - End 
	        	
	        }else{
	        	//Looping through Service List - Start  
		       
		        for(InvoiceServiceDetails s : invoiceDetails.getServiceList()){
		        	String descriptionTxt = "";
		        	String containsDiffPercentage = "";
		        	if(s.getDiffPercent() != null && s.getDiffPercent().equals("Y")){
		        		containsDiffPercentage = "(*)";
		        		isDiffPercentPresent++;
		        	}
		        	if(StringUtils.isNotEmpty(s.getIsDescriptionChecked()) && (s.getIsDescriptionChecked().equals("Yes"))){
		        		descriptionTxt = " - "+s.getDescription();
		        	}
		        	table.addCell(createCellWithDynamicBorders(Integer.toString(i), 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
		            table.addCell(createCellWithDynamicBorders(s.getServiceIdInString()+containsDiffPercentage+descriptionTxt, 1, 5, Element.ALIGN_LEFT, 1,0,0,0,1));
		            table.addCell(createCellWithDynamicBorders(s.getHsnSacCode(), 1, 1, Element.ALIGN_CENTER, 1,0,0,0,1));
		            table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(s.getPreviousAmount())+"", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
		            table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(s.getOfferAmount())+"", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
		            table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(s.getPreviousAmount() - s.getOfferAmount())+"", 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,1));
		            totalAmount = totalAmount + (s.getPreviousAmount() - s.getOfferAmount());
		            cessTotalTax = cessTotalTax + s.getCess();
		            categoryType = s.getCategoryType();
		            totalPreviousAmount = totalPreviousAmount + s.getPreviousAmount();
		            totalDiscount = totalDiscount + s.getOfferAmount();
		            i++;
		        }
		        
		        if(itemsCount < 10){
		        	blankLoopCount = 10 - itemsCount;
		        	for(int u = 0; u < blankLoopCount; u++){
			        	table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
			            table.addCell(createCellWithDynamicBorders(" ", 1, 5, Element.ALIGN_LEFT, 1,0,0,0,1));
			            table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_CENTER, 1,0,0,0,1));
			            table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
			            table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_LEFT, 1,0,0,0,1));
			            table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,1)); 
			        }
		        }
		      //Looping through Service List - End 
	        }  
	   //5th row - End
	     
	   //6th row - Start
	        p = new Phrase();
	        content="    Total";
	        addMoreText(p, content, boldFont);
	        
	        if(invoiceDetails.getInvoiceFor().equals("Product")){
	        	
		        table.addCell(createCell(p, 1, 4, Element.ALIGN_LEFT, 1));
		        table.addCell(createCell(""+totalQty, 1, 1, Element.ALIGN_RIGHT, 1));
		        table.addCell(createCell("", 1, 2, Element.ALIGN_LEFT, 1));
		        table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(totalPreviousAmount) + "", 1, 1, Element.ALIGN_RIGHT, 1));
		        table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(totalDiscount) + "", 1, 1, Element.ALIGN_RIGHT, 1));
		        table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(totalAmount) + "", 1, 1, Element.ALIGN_RIGHT, 1));
	        }else{
	        	
		        table.addCell(createCell(p, 1, 7, Element.ALIGN_LEFT, 1));
		        table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(totalPreviousAmount) + "", 1, 1, Element.ALIGN_RIGHT, 1));
		        table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(totalDiscount) + "", 1, 1, Element.ALIGN_RIGHT, 1));
		        table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(totalAmount) + "", 1, 1, Element.ALIGN_RIGHT, 1));
	        }
	        
	   //6th row - End
	   //7th row - Start
	        //Loop through Additional Charges - Start
		     String containsAdditionalCharges = "";
		     double addChargeAmount = 0;
		     if(invoiceDetails.getAddChargesList() != null && invoiceDetails.getAddChargesList().size() > 0){
		    	 containsAdditionalCharges = "YES";
		    	 
		    	 for(InvoiceAdditionalChargeDetails addChgDet : invoiceDetails.getAddChargesList()){
			         addChargeAmount = addChargeAmount + addChgDet.getAdditionalChargeAmount();
		    	 }
		    	 
		    	 p = new Phrase();
			     content = "Additional Charges";
			     addMoreText(p, content, regularFont);
		    	 table.addCell(createCellWithDynamicBorders(p, 1, 9, Element.ALIGN_LEFT, 1,0,0,0,1));
		    	 
		    	 p = new Phrase();
			     content = GSTNUtil.convertDoubleTo2DecimalPlaces(addChargeAmount)+"";
			     addMoreText(p, content, boldFont);
		    	 table.addCell(createCellWithDynamicBorders(p, 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,0));
		    	 
		    	 for(InvoiceAdditionalChargeDetails addChgDet : invoiceDetails.getAddChargesList()){
		    		 table.addCell(createCellWithDynamicBorders("", 1, 6, Element.ALIGN_CENTER, 1,0,0,0,1));
		    		 table.addCell(createCellWithDynamicBorders(addChgDet.getAdditionalChargeName()+"", 1, 2, Element.ALIGN_LEFT, 1,0,0,0,0));
		    		 table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(addChgDet.getAdditionalChargeAmount())+"", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,0));
			         table.addCell(createCellWithDynamicBorders("", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
		    	 }
		    	 
		         p = new Phrase();
			     content = "Total Amount";
			     addMoreText(p, content, boldFont);
		         table.addCell(createCellWithDynamicBorders(p, 1, 9, Element.ALIGN_LEFT, 1,0,0,1,1));
		         
		         p = new Phrase();
			     content = GSTNUtil.convertDoubleTo2DecimalPlaces(totalAmount + addChargeAmount)+"";
			     addMoreText(p, content, boldFont);
		         table.addCell(createCellWithDynamicBorders(p, 1, 1, Element.ALIGN_RIGHT, 1,0,1,1,0));
		     }

		     //Loop through Additional Charges - End
	        
	   //7th row - End
	   //8th row - Start
		   //Loop through Taxes - Start
		     double totalCgstAmount = 0;
		     double totalSgstAmount = 0;
		     double totalIgstAmount = 0;
		     double totalTaxAmount = 0 ;
		     Map<String,MapSegregate> mapSegregate = new HashMap<String,MapSegregate>();
		     if(categoryType.equals("CATEGORY_WITH_SGST_CSGT") || categoryType.equals("CATEGORY_WITH_UGST_CGST")){
		    	 for (Map.Entry<String,Map<String,Double>> entry : gstMap.entrySet()) {
		    		 String key = entry.getKey();
		    		 Map<String,Double> value = entry.getValue();
		    		 if(key.equals("cgst")){
		    			 for(Map.Entry<String,Double> insideEntry : value.entrySet()){
		    				 totalCgstAmount = totalCgstAmount + ((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)); 
		    				 mapSegregate = setInMap(insideEntry.getKey(),insideEntry.getValue(),mapSegregate,"c");
		    			 }
		    		 }
		    		 if(key.equals("cgstDiffPercent")){
		    			 for(Map.Entry<String,Double> insideEntry : value.entrySet()){
		    				 totalCgstAmount = totalCgstAmount + (((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65));
		    				 mapSegregate = setInMap(insideEntry.getKey(),insideEntry.getValue(),mapSegregate,"c*");
		    			 }
		    		 }
		    	 }
		     } 
		     if(categoryType.equals("CATEGORY_WITH_SGST_CSGT")){
		    	 for (Map.Entry<String,Map<String,Double>> entry : gstMap.entrySet()) {
		    		 String key = entry.getKey();
		    		 Map<String,Double> value = entry.getValue();
		    		 if(key.equals("sgst")){
		    			 for(Map.Entry<String,Double> insideEntry : value.entrySet()){
		    				 totalSgstAmount = totalSgstAmount + ((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)); 
		    				 mapSegregate = setInMap(insideEntry.getKey(),insideEntry.getValue(),mapSegregate,"s");
		    			 }
		    		 }
		    		 if(key.equals("sgstDiffPercent")){
		    			 for(Map.Entry<String,Double> insideEntry : value.entrySet()){
		    				 totalSgstAmount = totalSgstAmount + (((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65));
		    				 mapSegregate = setInMap(insideEntry.getKey(),insideEntry.getValue(),mapSegregate,"s*");
		    			 }
		    		 }
		    	 }
		    	 
		     }
		     if(categoryType.equals("CATEGORY_WITH_IGST") || categoryType.equals("CATEGORY_EXPORT_WITH_IGST")){
		    	 for (Map.Entry<String,Map<String,Double>> entry : gstMap.entrySet()) {
		    		 String key = entry.getKey();
		    		 Map<String,Double> value = entry.getValue();
		    		 if(key.equals("igst")){
		    			 for(Map.Entry<String,Double> insideEntry : value.entrySet()){
		    				 totalIgstAmount = totalIgstAmount + ((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)); 
		    				 mapSegregate = setInMap(insideEntry.getKey(),insideEntry.getValue(),mapSegregate,"i");
		    			 }
		    		 }
		    		 if(key.equals("igstDiffPercent")){
		    			 for(Map.Entry<String,Double> insideEntry : value.entrySet()){
		    				 totalIgstAmount = totalIgstAmount + (((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65));
		    				 mapSegregate = setInMap(insideEntry.getKey(),insideEntry.getValue(),mapSegregate,"i*");
		    			 }
		    		 }
		    	 }
		     }
		     if(categoryType.equals("CATEGORY_WITH_UGST_CGST")){
		    	 for (Map.Entry<String,Map<String,Double>> entry : gstMap.entrySet()) {
		    		 String key = entry.getKey();
		    		 Map<String,Double> value = entry.getValue();
		    		 if(key.equals("ugst")){
		    			 for(Map.Entry<String,Double> insideEntry : value.entrySet()){
		    				 totalSgstAmount = totalSgstAmount + ((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)); 
		    				 mapSegregate = setInMap(insideEntry.getKey(),insideEntry.getValue(),mapSegregate,"s");
		    			 }
		    		 }
		    		 if(key.equals("ugstDiffPercent")){
		    			 for(Map.Entry<String,Double> insideEntry : value.entrySet()){
		    				 totalSgstAmount = totalSgstAmount + (((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65));
		    				 mapSegregate = setInMap(insideEntry.getKey(),insideEntry.getValue(),mapSegregate,"s*");
		    			 }
		    		 }
		    	 }
		    	 
		     }
		     totalTaxAmount = totalCgstAmount + totalSgstAmount + totalIgstAmount + cessTotalTax;
		     
		     if(!documentTypeToDisplay.equals("Bill")){
		    	 p = new Phrase();
			     content = "Taxes";
			     addMoreText(p, content, regularFont);
		    	 table.addCell(createCellWithDynamicBorders(p, 1, 9, Element.ALIGN_LEFT, 1,0,0,0,1));
		    	 
		    	 p = new Phrase();
			     content = GSTNUtil.convertDoubleTo2DecimalPlaces(totalTaxAmount)+"";
			     addMoreText(p, content, boldFont);
		    	 table.addCell(createCellWithDynamicBorders(p, 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,0));
		    	 
		    	 //CGST - Start
		    	 table.addCell(createCellWithDynamicBorders("", 1, 6, Element.ALIGN_CENTER, 1,0,0,0,1));
	    		 table.addCell(createCellWithDynamicBorders("CGST", 1, 2, Element.ALIGN_LEFT, 1,0,0,0,0));
	    		 if(totalCgstAmount == 0){
	    			 table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,0,0,0,0));
	    		 }else{
	    			 table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo3DecimalPlaces(totalCgstAmount)+"", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,0));
	    		 }
		         table.addCell(createCellWithDynamicBorders("", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
		    	 //CGST - End
		         
		         //SGST - Start
		    	 table.addCell(createCellWithDynamicBorders("", 1, 6, Element.ALIGN_CENTER, 1,0,0,0,1));
	    		 table.addCell(createCellWithDynamicBorders("SGST", 1, 2, Element.ALIGN_LEFT, 1,0,0,0,0));
	    		 if(totalSgstAmount == 0){
	    			 table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,0,0,0,0));
	    		 }else{
	    			 table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo3DecimalPlaces(totalSgstAmount)+"", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,0));
	    		 }
		         table.addCell(createCellWithDynamicBorders("", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
		    	 //SGST - End
		         
		         //IGST - Start
		    	 table.addCell(createCellWithDynamicBorders("", 1, 6, Element.ALIGN_CENTER, 1,0,0,0,1));
	    		 table.addCell(createCellWithDynamicBorders("IGST", 1, 2, Element.ALIGN_LEFT, 1,0,0,0,0));
	    		 if(totalIgstAmount == 0){
	    			 table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,0,0,0,0));
	    		 }else{
	    			 table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo3DecimalPlaces(totalIgstAmount)+"", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,0));
	    		 }
		         table.addCell(createCellWithDynamicBorders("", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
		    	 //IGST - End
		         
		         //Cess - Start
		    	 table.addCell(createCellWithDynamicBorders("", 1, 6, Element.ALIGN_CENTER, 1,0,0,0,1));
	    		 table.addCell(createCellWithDynamicBorders("Cess", 1, 2, Element.ALIGN_LEFT, 1,0,0,0,0));
	    		 if(cessTotalTax == 0){
	    			 table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,0,0,0,0));
	    		 }else{
	    			 table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo3DecimalPlaces(cessTotalTax)+"", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,0));
	    		 }
		         table.addCell(createCellWithDynamicBorders("", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
		    	 //Cess - End
		         
		         p = new Phrase();
			     content = "Grand Total ";
			     addMoreText(p, content, boldFont);
		         table.addCell(createCellWithDynamicBorders(p, 1, 9, Element.ALIGN_LEFT, 1,0,0,1,1));
		         
		         p = new Phrase();
			     content = GSTNUtil.convertDoubleTo2DecimalPlaces(totalAmount + addChargeAmount + totalTaxAmount)+"";
			     addMoreText(p, content, boldFont);
		         table.addCell(createCellWithDynamicBorders(p, 1, 1, Element.ALIGN_RIGHT, 1,0,1,1,0));
		    	 
		     }
		   //Loop through Taxes - End  
	   //8th row - End
		     
	   //9th row - Start
		     p = new Phrase();
		     content = "Round Off (+/-)";
		     addMoreText(p, content, regularFont);
	    	 table.addCell(createCellWithDynamicBorders(p, 1, 9, Element.ALIGN_LEFT, 1,0,0,0,1));
	    	 
	    	 p = new Phrase();
		     content = GSTNUtil.convertDoubleTo2DecimalPlaces(invoiceDetails.getInvoiceValueAfterRoundOff() - invoiceDetails.getInvoiceValue());
		     addMoreText(p, content, regularFont);
	    	 table.addCell(createCellWithDynamicBorders(p, 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,0));
	    	 
	    	 p = new Phrase();
		     content = "Total Invoice Value";
		     addMoreText(p, content, boldFont);
	         table.addCell(createCellWithDynamicBorders(p, 1, 7, Element.ALIGN_LEFT, 1,0,0,1,1));
	         
	         p = new Phrase();
		     content = GSTNUtil.convertDoubleTo2DecimalPlaces(invoiceDetails.getInvoiceValueAfterRoundOff())+"";
		     addMoreText(p, content, "invoiceBoldFont");
	         table.addCell(createCellWithDynamicBorders(p, 1, 3, Element.ALIGN_RIGHT, 1,0,1,1,0));
		     
		     
	   //9th row - End
	   //10th row - Start
	        p = new Phrase();
	        content="Total Invoice Value in words : ";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 2, Element.ALIGN_LEFT, 1));
	        
	        p = new Phrase();
	        content = amtInWords;
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 8, Element.ALIGN_LEFT, 1));   
	         
	   //10th row - End
	  
	 //11th row - Start
	        //Part A - Start
	        int mapSize = mapSegregate.size();
	        int rhsRowSize = 0;//mapSize + 2;
	        int lhsEmptyRowSize = 0;
	        if(mapSize < 5){
	        	lhsEmptyRowSize = 5 - mapSize;
	        }
	        if(mapSize >= 7){
	        	rhsRowSize = mapSize;
	        }else if(mapSize < 7){
	        	rhsRowSize = 7;
	        }
	        p = new Phrase();
	        content="Tax Rate ";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1)); 
	        
	        p = new Phrase();
	        content="Taxable Amount ";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
	        
	        p = new Phrase();
	        content="CGST ";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
	        
	        p = new Phrase();
	        content="SGST ";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
	        
	        p = new Phrase();
	        content="IGST ";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
	        
	        p = new Phrase();
	        content="Total Tax";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
	        
	        p = new Phrase();
	        content = "Whether tax is payable \non reverse charge? : ";
	        addMoreText(p, content, boldFont);
	        content = "          "+invoiceDetails.getReverseCharge()+"\n\n";
	        addMoreText(p, content, regularFont);
	        content = "Whether Supply is \nthrough E-Commerce? : ";
	        addMoreText(p, content, boldFont);
	        content = "\t   "+invoiceDetails.getEcommerce()+"\n\n";
	        addMoreText(p, content, regularFont);
	        content = "GSTIN of ECO : ";
	        addMoreText(p, content, boldFont);
	        if(StringUtils.isNotBlank(invoiceDetails.getEcommerceGstin())){
	        	content = "\t   "+invoiceDetails.getEcommerceGstin()+"\n\n";
	        }else{
	        	content = " - \n";
	        }
	        addMoreText(p, content, regularFont);
	        table.addCell(createCell(p, 1, 3, Element.ALIGN_LEFT, rhsRowSize));
	        
	        double k_taxableAmount = 0;
	        double k_cgst = 0;
	        double k_sgst = 0;
	        double k_igst = 0;
	        double k_totaltax = 0;
	        for (Map.Entry<String,MapSegregate> entry : mapSegregate.entrySet()) {
	        	MapSegregate segObj = entry.getValue();
	        	table.addCell(createCellWithDynamicBorders(segObj.getTaxRate()+"%", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,1));
	        	k_taxableAmount = k_taxableAmount + segObj.getTaxableAmount();
	    		table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(segObj.getTaxableAmount())+"", 1, 2, Element.ALIGN_RIGHT, 1,0,1,0,0));
	    		if(segObj.getCgstAmount() == 0){
	    			table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));	
	    		}else{
	    			k_cgst = k_cgst + segObj.getCgstAmount();
	    			table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(segObj.getCgstAmount())+"", 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,0));
	    		}
	    		if(segObj.getSgstAmount() == 0){
	    			table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));	
	    		}else{
	    			k_sgst = k_sgst + segObj.getSgstAmount();
	    			table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(segObj.getSgstAmount())+"", 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,0));
	    		}
	    		if(segObj.getIgstAmount() == 0){
	    			table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));	
	    		}else{
	    			k_igst = k_igst + segObj.getIgstAmount();
	    			table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(segObj.getIgstAmount())+"", 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,0));
	    		}
	    		if(segObj.getTaxRateTotaltax() == 0){
	    			table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));	
	    		}else{
	    			k_totaltax = k_totaltax + segObj.getTaxRateTotaltax();
	    			table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(segObj.getTaxRateTotaltax())+"", 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,0));
	    		}
	        	
	        }
	        if(lhsEmptyRowSize > 0){
	        	for(int ij =0; ij < lhsEmptyRowSize; ij++){
	        		table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,1));
	        		table.addCell(createCellWithDynamicBorders(" ", 1, 2, Element.ALIGN_RIGHT, 1,0,1,0,0));
	        		table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
	        		table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
	        		table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
	        		table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
	        	}
	        	
	        }
	        //Total Row - Start
	        table.addCell(createCellWithDynamicBorders("Total ", 1, 1, Element.ALIGN_CENTER, 1,1,1,0,1));
    		table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(k_taxableAmount)+"", 1, 2, Element.ALIGN_RIGHT, 1,1,1,0,0));
    		if(k_cgst == 0){
    			table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,1,1,0,0));	
    		}else{
    			table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(k_cgst)+"", 1, 1, Element.ALIGN_RIGHT, 1,1,1,0,0));
    		}
    		if(k_sgst == 0){
    			table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,1,1,0,0));	
    		}else{
    			table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(k_sgst)+"", 1, 1, Element.ALIGN_RIGHT, 1,1,1,0,0));
    		}
    		if(k_igst == 0){
    			table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,1,1,0,0));	
    		}else{
    			table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(k_igst)+"", 1, 1, Element.ALIGN_RIGHT, 1,1,1,0,0));
    		}
    		if(k_totaltax == 0){
    			table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,1,1,0,0));	
    		}else{
    			table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(k_totaltax)+"", 1, 1, Element.ALIGN_RIGHT, 1,1,1,0,0));
    		}
	        
	        //Total Row - End
	      //Part A - End
	        
	      //Part B - Start
	       // table.addCell(createCellWithDynamicBorders("-", 1, 3, Element.ALIGN_CENTER, mapSize,0,0,0,0));
	      //Part B - End
	        
	 //11th row - End
	 //12th row - Start 
	        p = new Phrase();
	        content="Footer Note : ";
	        addMoreText(p, content, boldFont);
	        table.addCell(createCellWithDynamicBorders(p, 1, 2, Element.ALIGN_LEFT, 1,1,0,1,1));
	        
	        p = new Phrase();
	        if(StringUtils.isNotEmpty(invoiceDetails.getFooterNote())){
	        	content = invoiceDetails.getFooterNote();
	        }else{
	        	content = "";
	        }
	        addMoreText(p, content, regularFont);
	        table.addCell(createCellWithDynamicBorders(p, 1, 8, Element.ALIGN_LEFT, 1,1,1,1,0));     
	 //12th row - End
/*    
	        
	     //* Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government. - Start    
	        if(isDiffPercentPresent > 0){
	        	p = new Phrase();
		        content="(*) Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government.";
		        addMoreText(p, content, boldFont);
		        content="";
		        addMoreText(p, content, regularFont);
		        table.addCell(createCell(p, 1, 8, Element.ALIGN_LEFT, 1));
	        }
	     //* Subjected to differential percentage(%) of the existing tax-rate as notified by the Indian Government. - End   
	     
	        //*Cess : For Goods, this value is the sum total of 'Advol Cess Amount' and 'Non Advol Cess Amount'. - Start 
	    	p = new Phrase();
	        content="*Cess : For Goods, this value is the sum total of 'Advol Cess Amount' and 'Non Advol Cess Amount'.";
	        addMoreText(p, content, boldFont);
	        content="";
	        addMoreText(p, content, regularFont);
	        table.addCell(createCell(p, 1, 8, Element.ALIGN_LEFT, 4));
	        //*Cess : For Goods, this value is the sum total of 'Advol Cess Amount' and 'Non Advol Cess Amount'. - end 
*/	        
	        p = new Phrase();
	        content="Declaration :\nCertified that all the particulars given above are true and correct. The amounts indicated represents the price actually charged and there is no flow of additional consideration directly or indirectly from the Buyer.\n";
	        addMoreText(p, content, regularFont);
	        table.addCell(createCell(p, 1, 10, Element.ALIGN_LEFT, 4));
	        
	        p = new Phrase();
	        content="Recipients Signature: \n ";
	        addMoreText(p, content, regularFont);
	        table.addCell(createCell(p, 1, 4, Element.ALIGN_LEFT, 5));
	        
	        p = new Phrase();
	        content="For Company Name \n \nAuthorised Signatory \n";
	        addMoreText(p, content, regularFont);
	        table.addCell(createCell(p, 1, 6, Element.ALIGN_LEFT, 5));
	        
	        table.setTotalWidth(540);
	        table.setLockedWidth(true);
	        table.setWidthPercentage(100);
	        document.add(table);
	     
	        document.close();
	    	status = GSTNConstants.SUCCESS;
		} catch (NumberFormatException e) {
			logger.error("Error in:",e);
		}
        mapResponse.put(GSTNConstants.STATUS, status);
        logger.info("Exit");
        return mapResponse;
    }

	private Map<String, MapSegregate> setInMap(String key, Double kValue, Map<String, MapSegregate> mapSegregate,String type) {
		
		if(!mapSegregate.containsKey(key)){
			double totalTaxableAmount = 0;
			MapSegregate segregateObject = new MapSegregate();
			segregateObject.setTaxRate(key);
			segregateObject.setTaxableAmount(kValue);
			
			if(type.equals("c")){
				double cgstAmount = 0;
				cgstAmount = ((kValue * Double.parseDouble(key))/100);
				segregateObject.setCgstAmount(cgstAmount);
				totalTaxableAmount = cgstAmount;
			}else if(type.equals("c*")){
				double cgstAmount = 0;
				cgstAmount = ((kValue * Double.parseDouble(key))/100) * (0.65);
				segregateObject.setCgstAmount(cgstAmount);
				totalTaxableAmount = cgstAmount;
				segregateObject.setIsDiff("Y");
			}
			
			if(type.equals("s")){
				double sgstAmount = 0;
				sgstAmount = ((kValue * Double.parseDouble(key))/100);
				segregateObject.setSgstAmount(sgstAmount);
				totalTaxableAmount = sgstAmount;
			}else if(type.equals("s*")){
				double sgstAmount = 0;
				sgstAmount = ((kValue * Double.parseDouble(key))/100) * (0.65);
				segregateObject.setSgstAmount(sgstAmount);
				totalTaxableAmount = sgstAmount;
				segregateObject.setIsDiff("Y");
			}
			
			if(type.equals("i")){
				double igstAmount = 0;
				igstAmount = ((kValue * Double.parseDouble(key))/100);
				segregateObject.setIgstAmount(igstAmount);
				totalTaxableAmount = igstAmount;
			}else if(type.equals("i*")){
				double igstAmount = 0;
				igstAmount = ((kValue * Double.parseDouble(key))/100) * (0.65);
				segregateObject.setIgstAmount(igstAmount);
				totalTaxableAmount = igstAmount;
				segregateObject.setIsDiff("Y");
			}
			segregateObject.setTaxRateTotaltax(totalTaxableAmount);
			mapSegregate.put(key, segregateObject);
		}else{

			MapSegregate existingObject = mapSegregate.get(key);
			double totalTaxableAmount = existingObject.getTaxRateTotaltax();
			if(type.equals("c")){
				double cgstAmount = 0;
				cgstAmount = ((kValue * Double.parseDouble(key))/100);
				existingObject.setCgstAmount(cgstAmount);
				totalTaxableAmount = totalTaxableAmount + cgstAmount;
			}else if(type.equals("c*")){
				double cgstAmount = 0;
				cgstAmount = ((kValue * Double.parseDouble(key))/100) * (0.65);
				existingObject.setCgstAmount(cgstAmount);
				totalTaxableAmount = totalTaxableAmount + cgstAmount;
				existingObject.setIsDiff("Y");
			}
			
			if(type.equals("s")){
				double sgstAmount = 0;
				sgstAmount = ((kValue * Double.parseDouble(key))/100);
				existingObject.setSgstAmount(sgstAmount);
				totalTaxableAmount = totalTaxableAmount + sgstAmount;
			}else if(type.equals("s*")){
				double sgstAmount = 0;
				sgstAmount = ((kValue * Double.parseDouble(key))/100) * (0.65);
				existingObject.setSgstAmount(sgstAmount);
				totalTaxableAmount = totalTaxableAmount + sgstAmount;
				existingObject.setIsDiff("Y");
			}
			
			if(type.equals("i")){
				double igstAmount = 0;
				igstAmount = ((kValue * Double.parseDouble(key))/100);
				existingObject.setIgstAmount(igstAmount);
				totalTaxableAmount = totalTaxableAmount + igstAmount;
			}else if(type.equals("i*")){
				double igstAmount = 0;
				igstAmount = ((kValue * Double.parseDouble(key))/100) * (0.65);
				existingObject.setIgstAmount(igstAmount);
				totalTaxableAmount = totalTaxableAmount + igstAmount;
				existingObject.setIsDiff("Y");
			}
			existingObject.setTaxRateTotaltax(totalTaxableAmount);
			mapSegregate.put(key, existingObject);
			
		}
		return mapSegregate;
	}
	
	private Map<String, String> createCnDnPdf(String dest, InvoiceDetails invoiceDetails,GSTINDetails gstinDetails, UserMaster user, String customerStateCode,Map<String, Map<String, Double>> gstMap, String amtInWords, String logoImagePath, Integer iterationNo)  throws IOException, DocumentException  {
		logger.info("Entry");
    	Map<String,String> mapResponse = new HashMap<String,String>();
    	String status = GSTNConstants.FAILURE;
        Document document = new Document();
        String cnDnType = null;
        String currentInvoiceNumber = null;
        String currentInvoiceDate = null;
        double totalTaxes = 0d;
        double invoiceValue = 0d;
        double invoiceValueAfterRoundOff = 0d;
        String cndnFooterNote = null;
        String cndnCreationType = null;
        String isAdditionalChargePresent = null;
        
        try {
        	//Fetch the CnDntype - Start ------------------------------------------------------------------- 
	         for(CnDnAdditionalDetails cnDnAddDetail : invoiceDetails.getCnDnAdditionalList()){
	        	 if(cnDnAddDetail.getIterationNo() == iterationNo){
	        		 cnDnType = cnDnAddDetail.getCnDnType();
	        		 currentInvoiceNumber = cnDnAddDetail.getInvoiceNumber();
	        		 currentInvoiceDate = GSTNUtil.convertTimestampToString1(cnDnAddDetail.getInvoiceDate());
	        		 totalTaxes = cnDnAddDetail.getTotalTax();
	        		 invoiceValue = cnDnAddDetail.getInvoiceValue();
	        		 invoiceValueAfterRoundOff = cnDnAddDetail.getInvoiceValueAfterRoundOff();
	        		 cndnFooterNote = cnDnAddDetail.getFooter();
	        		 cndnCreationType = cnDnAddDetail.getReason();
	        		 isAdditionalChargePresent = cnDnAddDetail.getIsAdditionalChargePresent();
	        	 } 
	         }
	         
	      //Fetch the CnDntype - End ------------------------------------------------------------------- 
	         
        	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
	        document.open();
	        PdfPTable table = new PdfPTable(10);
	        table.setWidths(new int[]{ 10, 5, 15, 10, 10, 10,10, 10, 10, 10});
	        
	     //1st row LHS - Start -------------------------------------------------------------------------
	        
	         String regularFont="regular";  
	         String boldFont="Bold";
	         
	         Phrase p = new Phrase();
	         boolean isImagePresent = false;
	         
	         PdfPCell pv = createImageCell(logoImagePath, 1, 2, Element.ALIGN_LEFT,4);
	         if(pv.getImage() != null){
	        	isImagePresent = true;
	        	table.addCell(pv);
	         }
	          	
	         String content=" "+user.getOrganizationMaster().getOrgName();
	         addMoreText(p, content, "h2");
	         content="\n\n "+gstinDetails.getGstinAddressMapping().getAddress()+" , "+gstinDetails.getGstinAddressMapping().getCity()+" , "+gstinDetails.getGstinAddressMapping().getPinCode()+".";
	         addMoreText(p, content, regularFont);
	         
	         content="\n "+gstinDetails.getGstinAddressMapping().getState()+" - "+gstinDetails.getState()+"   ";
	         addMoreText(p, content, regularFont);
	         
	         content=" \n Phone : ";
	         addMoreText(p, content, regularFont);
	         content="";
	         addMoreText(p, content, regularFont);
		     
	 		if(isImagePresent){
	 			table.addCell(createCellWithDynamicBorders(p, 1, 5, Element.ALIGN_LEFT,4,1,0,0,0));
	 		}else{
	 			table.addCell(createCellWithDynamicBorders(p, 1, 7, Element.ALIGN_LEFT,4,1,0,0,1));
	 		}
	    //1st row LHS - End ---------------------------------------------------------------------------
	 		
	 	//1st row RHS - Start
	 		p = new Phrase();
	 		content=" \n\n GSTIN No. : ";
	        addMoreText(p, content, boldFont);
	        content=invoiceDetails.getGstnStateIdInString(); 
	        addMoreText(p, content, regularFont);
	         
	        content=" \n PAN No.    : ";
	        addMoreText(p, content, boldFont);
	        content=user.getOrganizationMaster().getPanNumber();
	        addMoreText(p, content, regularFont);
	      
	        table.addCell(createCellWithDynamicBorders(p, 1, 3, Element.ALIGN_LEFT,4,1,1,1,0));
	 	//1st row RHS - End
	        
	    //2nd row RHS - Start
	        p = new Phrase();
	        if(cnDnType.equals(GSTNConstants.DEBIT_NOTE)){
	        	 content="Debit Note"+" \n";
	        }else{
	        	 content="Credit Note"+" \n";
	        }
	        addMoreText(p, content, "invoiceBoldFont");
	        table.addCell(createCell(p, 1, 10, Element.ALIGN_CENTER, 2));
	        
	    //2nd row - End
	    //3rd row - Start
	      //Part 1 - Start
	        p = new Phrase();
            if(cnDnType.equals(GSTNConstants.DEBIT_NOTE)){
	        	 content=" Debit Note No : ";
	        }else{
	        	 content=" Credit Note No : ";
	        }
            addMoreText(p, content, boldFont);
            content = currentInvoiceNumber;
            addMoreText(p, content, regularFont);
       
            if(cnDnType.equals(GSTNConstants.DEBIT_NOTE)){
	        	 content="\n Debit Note Date : ";
	        }else{
	        	 content="\n Credit Note Date : ";
	        }
            addMoreText(p, content, boldFont);
            content = currentInvoiceDate;
            addMoreText(p, content, regularFont);
            
            if(invoiceDetails.getPoDetails() != null){
            	content=" \n PO/WO Number : ";
                addMoreText(p, content, boldFont);
                content=invoiceDetails.getPoDetails();
                addMoreText(p, content, regularFont);
            }
            
            if(invoiceDetails.getInvoiceFor().equals("Product")){
            	content=" \n Material Location : ";
                addMoreText(p, content, boldFont);
                content="";
                addMoreText(p, content, regularFont);
            }
            
            content="\n Original Document No : ";
	        addMoreText(p, content, boldFont);
	        content = invoiceDetails.getInvoiceNumber();
	        addMoreText(p, content, regularFont);
	         
	        content="\n Original Document Date : ";
	        addMoreText(p, content, boldFont);
	        content = GSTNUtil.convertTimestampToString1(invoiceDetails.getInvoiceDate());
	        addMoreText(p, content, regularFont);
            
            table.addCell(createCell(p, 1, 4, Element.ALIGN_LEFT,6));
            //Part 1 - End 
            
            //Part 2 - Bill To - Start ------------------------------------------------------------------------
	         p = new Phrase();
	         content="Bill To : ";
	         addMoreText(p, content, boldFont);
	         
	         content="\n"+invoiceDetails.getCustomerDetails().getCustName();
	         addMoreText(p, content, regularFont);
	         
	         content="\n"+invoiceDetails.getCustomerDetails().getCustAddress1();
	         addMoreText(p, content, regularFont);
	         
	         content="\n"+invoiceDetails.getCustomerDetails().getCustCity();
	         addMoreText(p, content, regularFont);
	        
	         content="\n"+invoiceDetails.getCustomerDetails().getCustState();
	         addMoreText(p, content, regularFont);
	         
	 	        if(invoiceDetails.getCustomerDetails().getCustGstinState() != null){
	 	        	 content=" - "+invoiceDetails.getCustomerDetails().getCustGstinState();
	 	             addMoreText(p, content, regularFont);
	 	        }
	 	        
	 	        content="( "+customerStateCode+" ) ";
	 	        addMoreText(p, content, regularFont);
	 	        
	 	        content="\nGSTIN : ";
	 	        addMoreText(p, content, regularFont);
	 	        content=invoiceDetails.getCustomerDetails().getCustGstId();
	 	        addMoreText(p, content, regularFont);
	 	        
	         table.addCell(createCell(p, 1, 3, Element.ALIGN_LEFT, 6));
	      //Part 2 - Bill To  - End  -------------------------------------------------------------------------
	         
	      //Part 3  - Start ------------------------------------------------------------------------
		        if(invoiceDetails.getInvoiceFor().equals("Product")){
		        	if(invoiceDetails.getBillToShipIsChecked().equals("No")){
		        		p = new Phrase();
		                content="Ship To :";
		                addMoreText(p, content, boldFont);
		                
		    	        content="\n"+invoiceDetails.getShipToCustomerName();
		    	        addMoreText(p, content, regularFont);
		    	        
		    	        content="\n"+invoiceDetails.getShipToAddress();
		    	        addMoreText(p, content, regularFont);
		    	        
		    	        content="\n"+invoiceDetails.getShipToState()+" - "+invoiceDetails.getShipToStateCodeId();
		    	        addMoreText(p, content, regularFont);
		    	        
		    	        content="( "+invoiceDetails.getShipToStateCode()+" )";
		    	        addMoreText(p, content, regularFont);
		    	        
		    	        content="\n\nPlace of supply : ";
		 		        addMoreText(p, content, regularFont);
		 		        content=invoiceDetails.getPlaceOfSupply();
		 		        addMoreText(p, content, regularFont);
		        		
		        	}
		        	if(invoiceDetails.getBillToShipIsChecked().equals("Yes")){
		       		 p = new Phrase();
		       	        content="Ship To : ";
		       	        addMoreText(p, content, boldFont);
		       	        
		       	        content="\n"+invoiceDetails.getCustomerDetails().getCustName();
		       	        addMoreText(p, content, regularFont);
		       	        
		       	        content="\n"+invoiceDetails.getCustomerDetails().getCustAddress1();
		       	        addMoreText(p, content, regularFont);
		       	        
		       	        content="\n"+invoiceDetails.getCustomerDetails().getCustCity();
		       	        addMoreText(p, content, regularFont);
		       	        
		       	        content="\n"+invoiceDetails.getCustomerDetails().getCustState();
		       	        addMoreText(p, content, regularFont);
		       
	       		        if(invoiceDetails.getCustomerDetails().getCustGstinState() != null){
	       		        	 content=" - "+invoiceDetails.getCustomerDetails().getCustGstinState();
	       	        	     addMoreText(p, content, regularFont);
	       		        }
		       		        
	           	        content="( "+customerStateCode+" )";
	           	        addMoreText(p, content, regularFont);
		           	        
		           	     
		 		        content="\nPlace of supply : ";
		 		        addMoreText(p, content, regularFont);
		 		        content = ((invoiceDetails.getPlaceOfSupply() != null) ? invoiceDetails.getPlaceOfSupply() : " ");
		 		        addMoreText(p, content, regularFont);
		       	}
		        	table.addCell(createCell(p, 1, 3, Element.ALIGN_LEFT, 6));
		        }else{
		        	p = new Phrase();
		        	
		        	//table.addCell(createCell(""+"  \n \n \n \n \n \n ", 1, 6, Element.ALIGN_LEFT, 6));
		        	table.addCell(createCell(""+"  \n \n \n \n \n \n ", 1, 3, Element.ALIGN_LEFT, 6));
		        }
		        //table.addCell(createCell(p, 1, 6, Element.ALIGN_LEFT, 6));
		     //Part3 3 - Ship To  - End  ------------------------------------------------------------------------- 
	        
	    //3rd row - End
		
		//4th row - Start 
	        if(invoiceDetails.getInvoiceFor().equals("Product")){
	        	//Table heading containing seven columns - Start
		        p = new Phrase();
		        content="Sr.No";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_RIGHT, 1));
		        
		        p = new Phrase();
		        content="Item Name";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 4, Element.ALIGN_LEFT, 1));
		        
		        p = new Phrase();
		        content="HSN Code";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content="Quantity";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content="UOM";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content="Rate ";
		        addMoreText(p, content, boldFont);
		        addMoreText(p, "a", "rupee");
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content="Amount ";
		        addMoreText(p, content, boldFont);
		        addMoreText(p, "a", "rupee");
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		      //Table heading containing seven columns - End
	        	
	        }else{
	        	//Table heading containing four columns - Start
		        p = new Phrase();
		        content="Sr.No";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_RIGHT, 1));
		        
		        p = new Phrase();
		        content="Service Name";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 6, Element.ALIGN_LEFT, 1));
		        
		        p = new Phrase();
		        content="SAC Code";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content="Amount ";
		        addMoreText(p, content, boldFont);
		        addMoreText(p, "a", "rupee");
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		      //Table heading containing four columns - End
	        } 
		//4th row - End
	        
	    //5th row - Start   
	        double totalAmount = 0;
	        double cessTotalTax = 0;
	        double totalQty = 0;
	        String categoryType = "";
	        int itemsCount = invoiceDetails.getCnDnList().size();
	        int blankLoopCount = 0;
	        int i = 1;
	        int isDiffPercentPresent = 0;
	        if(invoiceDetails.getInvoiceFor().equals("Product")){
	        	for(InvoiceCnDnDetails s : invoiceDetails.getCnDnList()){
		        	if(s.getIterationNo() == iterationNo){
		        		String containsDiffPercentage = "";
			        	if(s.getDiffPercent() != null && s.getDiffPercent().equals("Y")){
			        		containsDiffPercentage = "(*)";
			        		isDiffPercentPresent++;
			        	}
			        	table.addCell(createCell(Integer.toString(i), 1, 1, Element.ALIGN_RIGHT, 1));
			            table.addCell(createCell(s.getServiceIdInString()+containsDiffPercentage, 1, 4, Element.ALIGN_LEFT, 1));
			            table.addCell(createCell(s.getHsnSacCode(), 1, 1, Element.ALIGN_CENTER, 1));
			            table.addCell(createCell(s.getQuantity()+"", 1, 1, Element.ALIGN_RIGHT, 1));
			            table.addCell(createCell(GSTNUtil.toTitleCase(s.getUnitOfMeasurement()), 1, 1, Element.ALIGN_LEFT, 1));
			            if(s.getReason().equals("discountChange")){
			            	double modRate = ((s.getPreviousAmount() - s.getOfferAmount())/(s.getQuantity()));
			            	table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(modRate)+"", 1, 1, Element.ALIGN_RIGHT, 1));
			            }else{
			            	table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(s.getRate())+"", 1, 1, Element.ALIGN_RIGHT, 1));
			            }
			            
			            table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(s.getPreviousAmount() - s.getOfferAmount())+"", 1, 1, Element.ALIGN_RIGHT, 1));
			            totalAmount = totalAmount + (s.getPreviousAmount() - s.getOfferAmount());
			            cessTotalTax = cessTotalTax + s.getCess();
			            categoryType = s.getCategoryType();
			            totalQty = totalQty + s.getQuantity();
			            i++;
		        	}
		        }
	        	if(itemsCount < 10){
			        	blankLoopCount = 10 - itemsCount;
			        	for(int u = 0; u < blankLoopCount; u++){
				        	table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
				            table.addCell(createCellWithDynamicBorders(" ", 1, 4, Element.ALIGN_LEFT, 1,0,0,0,1));
				            table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_CENTER, 1,0,0,0,1));
				            table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
				            table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_LEFT, 1,0,0,0,1));
				            table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
				            table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,1));
				        }
			    }
	        }else{
	        	for(InvoiceCnDnDetails s : invoiceDetails.getCnDnList()){
		        	if(s.getIterationNo() == iterationNo){
		        		String containsDiffPercentage = "";
			        	if(s.getDiffPercent() != null && s.getDiffPercent().equals("Y")){
			        		containsDiffPercentage = "(*)";
			        		isDiffPercentPresent++;
			        	}
			        	table.addCell(createCell(Integer.toString(i), 1, 1, Element.ALIGN_RIGHT, 1));
			            table.addCell(createCell(s.getServiceIdInString()+containsDiffPercentage, 1, 6, Element.ALIGN_LEFT, 1));
			            table.addCell(createCell(s.getHsnSacCode(), 1, 2, Element.ALIGN_CENTER, 1));
			            table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(s.getPreviousAmount() - s.getOfferAmount())+"", 1, 1, Element.ALIGN_RIGHT, 1));
			            totalAmount = totalAmount + (s.getPreviousAmount() - s.getOfferAmount());
			            cessTotalTax = cessTotalTax + s.getCess();
			            categoryType = s.getCategoryType();
			            i++;
		        	}
		        }
	        	if(itemsCount < 10){
		        	blankLoopCount = 10 - itemsCount;
		        	for(int u = 0; u < blankLoopCount; u++){
			        	table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,1));
			            table.addCell(createCellWithDynamicBorders(" ", 1, 6, Element.ALIGN_LEFT, 1,0,0,0,1));
			            table.addCell(createCellWithDynamicBorders(" ", 1, 2, Element.ALIGN_CENTER, 1,0,0,0,1));
			            table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,1));
			        }
	        	}
	        }
	    //5th row - End  
	        
	    //6th row - Start
	        p = new Phrase();
	        content="    Total";
	        addMoreText(p, content, boldFont);
	        
	        if(invoiceDetails.getInvoiceFor().equals("Product")){
	        	
		        table.addCell(createCell(p, 1, 6, Element.ALIGN_LEFT, 1));
		        table.addCell(createCell(""+totalQty, 1, 1, Element.ALIGN_RIGHT, 1));
		        table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
		        table.addCell(createCell("", 1, 1, Element.ALIGN_LEFT, 1));
		        table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(totalAmount) + "", 1, 1, Element.ALIGN_RIGHT, 1));
	        }else{
		        table.addCell(createCell(p, 1, 9, Element.ALIGN_LEFT, 1));
		        table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(totalAmount) + "", 1, 1, Element.ALIGN_RIGHT, 1));
	        }
	        
	   //6th row - End
	   //7th row - Start
	        //Loop through Additional Charges - Start
		     String containsAdditionalCharges = "";
		     double addChargeAmount = 0;
		     if(invoiceDetails.getCnDnAdditionalList() != null && invoiceDetails.getAddChargesList().size() > 0){
		    	 for(CnDnAdditionalDetails cndnAddChgs : invoiceDetails.getCnDnAdditionalList()){
		    		 if((cndnAddChgs.getIterationNo() == iterationNo) && (cndnAddChgs.getIsAdditionalChargePresent().equals("Y"))){
		    			 containsAdditionalCharges = "YES";
				    	 
				    	 for(InvoiceAdditionalChargeDetails addChgDet : invoiceDetails.getAddChargesList()){
					         addChargeAmount = addChargeAmount + addChgDet.getAdditionalChargeAmount();
				    	 }
				    	 
				    	 p = new Phrase();
					     content = "Additional Charges";
					     addMoreText(p, content, regularFont);
				    	 table.addCell(createCellWithDynamicBorders(p, 1, 9, Element.ALIGN_LEFT, 1,0,0,0,1));
				    	 
				    	 p = new Phrase();
					     content = GSTNUtil.convertDoubleTo2DecimalPlaces(addChargeAmount)+"";
					     addMoreText(p, content, boldFont);
				    	 table.addCell(createCellWithDynamicBorders(p, 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,0));
				    	 
				    	 for(InvoiceAdditionalChargeDetails addChgDet : invoiceDetails.getAddChargesList()){
				    		 table.addCell(createCellWithDynamicBorders("", 1, 6, Element.ALIGN_CENTER, 1,0,0,0,1));
				    		 table.addCell(createCellWithDynamicBorders(addChgDet.getAdditionalChargeName()+"", 1, 2, Element.ALIGN_LEFT, 1,0,0,0,0));
				    		 table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(addChgDet.getAdditionalChargeAmount())+"", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,0));
					         table.addCell(createCellWithDynamicBorders("", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
				    	 }
				    	 
				         p = new Phrase();
					     content = "Total Amount";
					     addMoreText(p, content, boldFont);
				         table.addCell(createCellWithDynamicBorders(p, 1, 9, Element.ALIGN_LEFT, 1,0,0,1,1));
				         
				         p = new Phrase();
					     content = GSTNUtil.convertDoubleTo2DecimalPlaces(totalAmount + addChargeAmount)+"";
					     addMoreText(p, content, boldFont);
				         table.addCell(createCellWithDynamicBorders(p, 1, 1, Element.ALIGN_RIGHT, 1,0,1,1,0));
				         break;
		    		 }
		    	 }
		    	 
		     }

		     //Loop through Additional Charges - End 
	   //7th row - End
	   //8th row - Start
			   //Loop through Taxes - Start
			     double totalCgstAmount = 0;
			     double totalSgstAmount = 0;
			     double totalIgstAmount = 0;
			     double totalTaxAmount = 0 ;
			     Map<String,MapSegregate> mapSegregate = new HashMap<String,MapSegregate>();
			     if(categoryType.equals("CATEGORY_WITH_SGST_CSGT") || categoryType.equals("CATEGORY_WITH_UGST_CGST")){
			    	 for (Map.Entry<String,Map<String,Double>> entry : gstMap.entrySet()) {
			    		 String key = entry.getKey();
			    		 Map<String,Double> value = entry.getValue();
			    		 if(key.equals("cgst")){
			    			 for(Map.Entry<String,Double> insideEntry : value.entrySet()){
			    				 totalCgstAmount = totalCgstAmount + ((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)); 
			    				 mapSegregate = setInMap(insideEntry.getKey(),insideEntry.getValue(),mapSegregate,"c");
			    			 }
			    		 }
			    		 if(key.equals("cgstDiffPercent")){
			    			 for(Map.Entry<String,Double> insideEntry : value.entrySet()){
			    				 totalCgstAmount = totalCgstAmount + (((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65));
			    				 mapSegregate = setInMap(insideEntry.getKey(),insideEntry.getValue(),mapSegregate,"c*");
			    			 }
			    		 }
			    	 }
			     } 
			     if(categoryType.equals("CATEGORY_WITH_SGST_CSGT")){
			    	 for (Map.Entry<String,Map<String,Double>> entry : gstMap.entrySet()) {
			    		 String key = entry.getKey();
			    		 Map<String,Double> value = entry.getValue();
			    		 if(key.equals("sgst")){
			    			 for(Map.Entry<String,Double> insideEntry : value.entrySet()){
			    				 totalSgstAmount = totalSgstAmount + ((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)); 
			    				 mapSegregate = setInMap(insideEntry.getKey(),insideEntry.getValue(),mapSegregate,"s");
			    			 }
			    		 }
			    		 if(key.equals("sgstDiffPercent")){
			    			 for(Map.Entry<String,Double> insideEntry : value.entrySet()){
			    				 totalSgstAmount = totalSgstAmount + (((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65));
			    				 mapSegregate = setInMap(insideEntry.getKey(),insideEntry.getValue(),mapSegregate,"s*");
			    			 }
			    		 }
			    	 }
			    	 
			     }
			     if(categoryType.equals("CATEGORY_WITH_IGST") || categoryType.equals("CATEGORY_EXPORT_WITH_IGST")){
			    	 for (Map.Entry<String,Map<String,Double>> entry : gstMap.entrySet()) {
			    		 String key = entry.getKey();
			    		 Map<String,Double> value = entry.getValue();
			    		 if(key.equals("igst")){
			    			 for(Map.Entry<String,Double> insideEntry : value.entrySet()){
			    				 totalIgstAmount = totalIgstAmount + ((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)); 
			    				 mapSegregate = setInMap(insideEntry.getKey(),insideEntry.getValue(),mapSegregate,"i");
			    			 }
			    		 }
			    		 if(key.equals("igstDiffPercent")){
			    			 for(Map.Entry<String,Double> insideEntry : value.entrySet()){
			    				 totalIgstAmount = totalIgstAmount + (((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65));
			    				 mapSegregate = setInMap(insideEntry.getKey(),insideEntry.getValue(),mapSegregate,"i*");
			    			 }
			    		 }
			    	 }
			     }
			     if(categoryType.equals("CATEGORY_WITH_UGST_CGST")){
			    	 for (Map.Entry<String,Map<String,Double>> entry : gstMap.entrySet()) {
			    		 String key = entry.getKey();
			    		 Map<String,Double> value = entry.getValue();
			    		 if(key.equals("ugst")){
			    			 for(Map.Entry<String,Double> insideEntry : value.entrySet()){
			    				 totalSgstAmount = totalSgstAmount + ((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)); 
			    				 mapSegregate = setInMap(insideEntry.getKey(),insideEntry.getValue(),mapSegregate,"s");
			    			 }
			    		 }
			    		 if(key.equals("ugstDiffPercent")){
			    			 for(Map.Entry<String,Double> insideEntry : value.entrySet()){
			    				 totalSgstAmount = totalSgstAmount + (((insideEntry.getValue() * Double.parseDouble(insideEntry.getKey()))/(100)) * (0.65));
			    				 mapSegregate = setInMap(insideEntry.getKey(),insideEntry.getValue(),mapSegregate,"s*");
			    			 }
			    		 }
			    	 }
			    	 
			     }
			     totalTaxAmount = totalCgstAmount + totalSgstAmount + totalIgstAmount + cessTotalTax;
			     
		    	 p = new Phrase();
			     content = "Taxes";
			     addMoreText(p, content, regularFont);
		    	 table.addCell(createCellWithDynamicBorders(p, 1, 9, Element.ALIGN_LEFT, 1,0,0,0,1));
		    	 
		    	 p = new Phrase();
			     content = GSTNUtil.convertDoubleTo2DecimalPlaces(totalTaxAmount)+"";
			     addMoreText(p, content, boldFont);
		    	 table.addCell(createCellWithDynamicBorders(p, 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,0));
		    	 
		    	 //CGST - Start
		    	 table.addCell(createCellWithDynamicBorders("", 1, 6, Element.ALIGN_CENTER, 1,0,0,0,1));
	    		 table.addCell(createCellWithDynamicBorders("CGST", 1, 2, Element.ALIGN_LEFT, 1,0,0,0,0));
	    		 if(totalCgstAmount == 0){
	    			 table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,0,0,0,0));
	    		 }else{
	    			 table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo3DecimalPlaces(totalCgstAmount)+"", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,0));
	    		 }
		         table.addCell(createCellWithDynamicBorders("", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
		    	 //CGST - End
		         
		         //SGST - Start
		    	 table.addCell(createCellWithDynamicBorders("", 1, 6, Element.ALIGN_CENTER, 1,0,0,0,1));
	    		 table.addCell(createCellWithDynamicBorders("SGST", 1, 2, Element.ALIGN_LEFT, 1,0,0,0,0));
	    		 if(totalSgstAmount == 0){
	    			 table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,0,0,0,0));
	    		 }else{
	    			 table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo3DecimalPlaces(totalSgstAmount)+"", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,0));
	    		 }
		         table.addCell(createCellWithDynamicBorders("", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
		    	 //SGST - End
		         
		         //IGST - Start
		    	 table.addCell(createCellWithDynamicBorders("", 1, 6, Element.ALIGN_CENTER, 1,0,0,0,1));
	    		 table.addCell(createCellWithDynamicBorders("IGST", 1, 2, Element.ALIGN_LEFT, 1,0,0,0,0));
	    		 if(totalIgstAmount == 0){
	    			 table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,0,0,0,0));
	    		 }else{
	    			 table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo3DecimalPlaces(totalIgstAmount)+"", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,0));
	    		 }
		         table.addCell(createCellWithDynamicBorders("", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
		    	 //IGST - End
		         
		         //Cess - Start
		    	 table.addCell(createCellWithDynamicBorders("", 1, 6, Element.ALIGN_CENTER, 1,0,0,0,1));
	    		 table.addCell(createCellWithDynamicBorders("Cess", 1, 2, Element.ALIGN_LEFT, 1,0,0,0,0));
	    		 if(cessTotalTax == 0){
	    			 table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,0,0,0,0));
	    		 }else{
	    			 table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo3DecimalPlaces(cessTotalTax)+"", 1, 1, Element.ALIGN_RIGHT, 1,0,0,0,0));
	    		 }
		         table.addCell(createCellWithDynamicBorders("", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
		    	 //Cess - End
		         
		         p = new Phrase();
			     content = "Grand Total ";
			     addMoreText(p, content, boldFont);
		         table.addCell(createCellWithDynamicBorders(p, 1, 9, Element.ALIGN_LEFT, 1,0,0,1,1));
		         
		         p = new Phrase();
			     content = GSTNUtil.convertDoubleTo2DecimalPlaces(totalAmount + addChargeAmount + totalTaxAmount)+"";
			     addMoreText(p, content, boldFont);
		         table.addCell(createCellWithDynamicBorders(p, 1, 1, Element.ALIGN_RIGHT, 1,0,1,1,0));
			    	 
			     
			   //Loop through Taxes - End  
		//8th row - End 
		//9th row - Start
			     p = new Phrase();
			     content = "Round Off (+/-)";
			     addMoreText(p, content, regularFont);
		    	 table.addCell(createCellWithDynamicBorders(p, 1, 9, Element.ALIGN_LEFT, 1,0,0,0,1));
		    	 
		    	 p = new Phrase();
			     content = GSTNUtil.convertDoubleTo2DecimalPlaces(invoiceValueAfterRoundOff - invoiceValue);
			     addMoreText(p, content, regularFont);
		    	 table.addCell(createCellWithDynamicBorders(p, 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,0));
		    	 
		    	 p = new Phrase();
			     content = "Total Invoice Value";
			     addMoreText(p, content, boldFont);
		         table.addCell(createCellWithDynamicBorders(p, 1, 7, Element.ALIGN_LEFT, 1,0,0,1,1));
		         
		         p = new Phrase();
			     content = GSTNUtil.convertDoubleTo2DecimalPlaces(invoiceValueAfterRoundOff)+"";
			     addMoreText(p, content, "invoiceBoldFont");
		         table.addCell(createCellWithDynamicBorders(p, 1, 3, Element.ALIGN_RIGHT, 1,0,1,1,0));
			     
			     
		//9th row - End
		//10th row - Start
		        p = new Phrase();
		        content="Total Invoice Value in words : ";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 2, Element.ALIGN_LEFT, 1));
		        
		        p = new Phrase();
		        content = amtInWords;
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 8, Element.ALIGN_LEFT, 1));   
		         
		//10th row - End 
		//11th row - Start
		        //Part A - Start
		        int mapSize = mapSegregate.size();
		        int rhsRowSize = 0;//mapSize + 2;
		        int lhsEmptyRowSize = 0;
		        if(mapSize < 5){
		        	lhsEmptyRowSize = 5 - mapSize;
		        }
		        if(mapSize >= 7){
		        	rhsRowSize = mapSize;
		        }else if(mapSize < 7){
		        	rhsRowSize = 7;
		        }
		        p = new Phrase();
		        content="Tax Rate ";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1)); 
		        
		        p = new Phrase();
		        content="Taxable Amount ";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 2, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content="CGST ";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content="SGST ";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content="IGST ";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content="Total Tax";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCell(p, 1, 1, Element.ALIGN_CENTER, 1));
		        
		        p = new Phrase();
		        content = "Whether tax is payable \non reverse charge? : ";
		        addMoreText(p, content, boldFont);
		        content = "          "+invoiceDetails.getReverseCharge()+"\n\n";
		        addMoreText(p, content, regularFont);
		        content = "Whether Supply is \nthrough E-Commerce? : ";
		        addMoreText(p, content, boldFont);
		        content = "\t   "+invoiceDetails.getEcommerce()+"\n\n";
		        addMoreText(p, content, regularFont);
		        content = "GSTIN of ECO : ";
		        addMoreText(p, content, boldFont);
		        if(StringUtils.isNotBlank(invoiceDetails.getEcommerceGstin())){
		        	content = "\t   "+invoiceDetails.getEcommerceGstin()+"\n\n";
		        }else{
		        	content = " - \n";
		        }
		        addMoreText(p, content, regularFont);
		        table.addCell(createCell(p, 1, 3, Element.ALIGN_LEFT, rhsRowSize));
		        
		        double k_taxableAmount = 0;
		        double k_cgst = 0;
		        double k_sgst = 0;
		        double k_igst = 0;
		        double k_totaltax = 0;
		        for (Map.Entry<String,MapSegregate> entry : mapSegregate.entrySet()) {
		        	MapSegregate segObj = entry.getValue();
		        	table.addCell(createCellWithDynamicBorders(segObj.getTaxRate()+"%", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,1));
		        	k_taxableAmount = k_taxableAmount + segObj.getTaxableAmount();
		    		table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(segObj.getTaxableAmount())+"", 1, 2, Element.ALIGN_RIGHT, 1,0,1,0,0));
		    		if(segObj.getCgstAmount() == 0){
		    			table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));	
		    		}else{
		    			k_cgst = k_cgst + segObj.getCgstAmount();
		    			table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(segObj.getCgstAmount())+"", 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,0));
		    		}
		    		if(segObj.getSgstAmount() == 0){
		    			table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));	
		    		}else{
		    			k_sgst = k_sgst + segObj.getSgstAmount();
		    			table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(segObj.getSgstAmount())+"", 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,0));
		    		}
		    		if(segObj.getIgstAmount() == 0){
		    			table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));	
		    		}else{
		    			k_igst = k_igst + segObj.getIgstAmount();
		    			table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(segObj.getIgstAmount())+"", 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,0));
		    		}
		    		if(segObj.getTaxRateTotaltax() == 0){
		    			table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));	
		    		}else{
		    			k_totaltax = k_totaltax + segObj.getTaxRateTotaltax();
		    			table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(segObj.getTaxRateTotaltax())+"", 1, 1, Element.ALIGN_RIGHT, 1,0,1,0,0));
		    		}
		        	
		        }
		        if(lhsEmptyRowSize > 0){
		        	for(int ij =0; ij < lhsEmptyRowSize; ij++){
		        		table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,1));
		        		table.addCell(createCellWithDynamicBorders(" ", 1, 2, Element.ALIGN_RIGHT, 1,0,1,0,0));
		        		table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
		        		table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
		        		table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
		        		table.addCell(createCellWithDynamicBorders(" ", 1, 1, Element.ALIGN_CENTER, 1,0,1,0,0));
		        	}
		        	
		        }
		        //Total Row - Start
		        table.addCell(createCellWithDynamicBorders("Total ", 1, 1, Element.ALIGN_CENTER, 1,1,1,0,1));
	    		table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(k_taxableAmount)+"", 1, 2, Element.ALIGN_RIGHT, 1,1,1,0,0));
	    		if(k_cgst == 0){
	    			table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,1,1,0,0));	
	    		}else{
	    			table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(k_cgst)+"", 1, 1, Element.ALIGN_RIGHT, 1,1,1,0,0));
	    		}
	    		if(k_sgst == 0){
	    			table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,1,1,0,0));	
	    		}else{
	    			table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(k_sgst)+"", 1, 1, Element.ALIGN_RIGHT, 1,1,1,0,0));
	    		}
	    		if(k_igst == 0){
	    			table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,1,1,0,0));	
	    		}else{
	    			table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(k_igst)+"", 1, 1, Element.ALIGN_RIGHT, 1,1,1,0,0));
	    		}
	    		if(k_totaltax == 0){
	    			table.addCell(createCellWithDynamicBorders("-", 1, 1, Element.ALIGN_CENTER, 1,1,1,0,0));	
	    		}else{
	    			table.addCell(createCellWithDynamicBorders(GSTNUtil.convertDoubleTo2DecimalPlaces(k_totaltax)+"", 1, 1, Element.ALIGN_RIGHT, 1,1,1,0,0));
	    		}
		        
		        //Total Row - End
		      //Part A - End
		        
		      //Part B - Start
		       // table.addCell(createCellWithDynamicBorders("-", 1, 3, Element.ALIGN_CENTER, mapSize,0,0,0,0));
		      //Part B - End
		        
		//11th row - End
		//12th row - Start 
		        p = new Phrase();
		        content="Footer Note : ";
		        addMoreText(p, content, boldFont);
		        table.addCell(createCellWithDynamicBorders(p, 1, 2, Element.ALIGN_LEFT, 1,1,0,1,1));
		        
		        p = new Phrase();
		        if(StringUtils.isNotEmpty(invoiceDetails.getFooterNote())){
		        	content = invoiceDetails.getFooterNote();
		        }else{
		        	content = "";
		        }
		        addMoreText(p, content, regularFont);
		        table.addCell(createCellWithDynamicBorders(p, 1, 8, Element.ALIGN_LEFT, 1,1,1,1,0));     
		//12th row - End
		        
		        p = new Phrase();
		        content="Declaration :\nCertified that all the particulars given above are true and correct. The amounts indicated represents the price actually charged and there is no flow of additional consideration directly or indirectly from the Buyer.\n";
		        addMoreText(p, content, regularFont);
		        table.addCell(createCell(p, 1, 10, Element.ALIGN_LEFT, 4));
		        
		        p = new Phrase();
		        content="Recipients Signature: \n ";
		        addMoreText(p, content, regularFont);
		        table.addCell(createCell(p, 1, 4, Element.ALIGN_LEFT, 5));
		        
		        p = new Phrase();
		        content="For Company Name \n \nAuthorised Signatory \n";
		        addMoreText(p, content, regularFont);
		        table.addCell(createCell(p, 1, 6, Element.ALIGN_LEFT, 5));
	        
	        table.setTotalWidth(540);
	        table.setLockedWidth(true);
	        table.setWidthPercentage(100);
	        
	        document.add(table);
	        document.close();
	    	status = GSTNConstants.SUCCESS;
		} catch (NumberFormatException e) {
			logger.error("Error in:",e);
		}
        mapResponse.put(GSTNConstants.STATUS, status);
        logger.info("Exit");
        return mapResponse;
	
	}

	

    
}

