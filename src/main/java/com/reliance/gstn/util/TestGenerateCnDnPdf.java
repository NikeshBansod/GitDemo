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

public class TestGenerateCnDnPdf {

	private static final Logger logger = Logger.getLogger(TestGenerateCnDnPdf.class);

	public static InvoiceDetails getInvoiceDetailsDummy() {
		
    	CustomerDetails customerDetails = getDummyCustomer();
    	//List<InvoiceServiceDetails> serviceList = getDummyServiceList();
    	List<InvoiceServiceDetails> serviceList = new ArrayList<>();
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
    	invoiceDetails.setInvoiceValue(5573.33);
    	invoiceDetails.setInvoiceValueAfterRoundOff(5574.0);
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

	private static CustomerDetails getDummyCustomer() {
	  	CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setId(1482);
		customerDetails.setCustName("Nikesh Bansod");
		customerDetails.setCustAddress1("Mumbai");
		customerDetails.setCustCity("THANE");
		customerDetails.setCustState("Maharashtra");
		customerDetails.setCustGstinState("27");
		customerDetails.setCustGstId("27AADCR6221Q1ZY");
		return customerDetails;
	}
	
	private static List<InvoiceAdditionalChargeDetails> getDummyInvoiceAdditionalChargeDetails(){
		List<InvoiceAdditionalChargeDetails> invoiceAddChargeDetailsList = new ArrayList<InvoiceAdditionalChargeDetails>();
		InvoiceAdditionalChargeDetails a1 = new InvoiceAdditionalChargeDetails();
		a1.setAdditionalChargeAmount(100);
		a1.setAdditionalChargeId(10);
		a1.setAdditionalChargeName("Transport Charges");
		a1.setId(10);
		invoiceAddChargeDetailsList.add(a1);
		
		InvoiceAdditionalChargeDetails a12 = new InvoiceAdditionalChargeDetails();
		a12.setAdditionalChargeAmount(100);
		a12.setAdditionalChargeId(20);
		a12.setAdditionalChargeName("Delivery Charges");
		a12.setId(20);
		invoiceAddChargeDetailsList.add(a12);
		
		return invoiceAddChargeDetailsList;
	}
	
	private static List<InvoiceCnDnDetails> getDummyCnDnList() {
		List<InvoiceCnDnDetails> cndnList = new ArrayList<InvoiceCnDnDetails>();
		
		InvoiceCnDnDetails cndnDetails = new InvoiceCnDnDetails();
    	cndnDetails.setServiceId(984);
    	cndnDetails.setUnitOfMeasurement("GRAMMS");
    	cndnDetails.setRate(400.00);
    	cndnDetails.setQuantity(10.00);
    	cndnDetails.setAmount(4190.48);
    	cndnDetails.setCalculationBasedOn("Amount");
    	cndnDetails.setTaxAmount(1173.33);
    	cndnDetails.setServiceIdInString("Non Pumps");
    	cndnDetails.setSgstAmount(0.00);
    	cndnDetails.setCgstAmount(0.00);
    	cndnDetails.setIgstAmount(1173.33);
    	cndnDetails.setUgstAmount(0.00);
    	cndnDetails.setPreviousAmount(4000.00);
    	cndnDetails.setSgstPercentage(0.0);
    	cndnDetails.setCgstPercentage(0.0);
    	cndnDetails.setIgstPercentage(28.00);
    	cndnDetails.setUgstPercentage(0.00);
    	cndnDetails.setBillingFor("Product");
    	cndnDetails.setGstnStateId(27);
    	cndnDetails.setDeliveryStateId(27);
    	cndnDetails.setCategoryType("CATEGORY_WITH_IGST");
    	cndnDetails.setCess(0.00);
    	cndnDetails.setOfferAmount(0.00);
    	cndnDetails.setHsnSacCode("84133020");
    	cndnDetails.setAdditionalAmount(190.48);
    	cndnDetails.setAmountAfterDiscount(0.00);
    	cndnDetails.setIterationNo(1);
    	cndnList.add(cndnDetails);
    	
    	InvoiceCnDnDetails s2 = new InvoiceCnDnDetails();
    	s2.setServiceId(983);
    	s2.setUnitOfMeasurement("GROSS");
    	s2.setRate(20.00);
    	s2.setQuantity(10.0);
    	s2.setAmount(209.52);
    	s2.setCalculationBasedOn("Amount");
    	s2.setTaxAmount(10.48);
    	s2.setServiceIdInString("KARAD SUGAR 23");
    	s2.setSgstAmount(0.00);
    	s2.setCgstAmount(0.00);
    	s2.setIgstAmount(10.48);
    	s2.setUgstAmount(0.00);
    	s2.setPreviousAmount(200.00);
    	s2.setSgstPercentage(0.00);
    	s2.setCgstPercentage(0.00);
    	s2.setIgstPercentage(0.00);
    	s2.setUgstPercentage(0.00);
    	s2.setBillingFor("Product");
    	s2.setGstnStateId(27);
    	s2.setDeliveryStateId(27);
    	s2.setCategoryType("CATEGORY_WITH_IGST");
    	s2.setCess(0.00);
    	s2.setHsnSacCode("04081100");
    	s2.setAdditionalAmount(9.52);
    	s2.setAmountAfterDiscount(0.00);
    	s2.setIterationNo(1);
    	cndnList.add(s2);
		
		return cndnList;
	}
	
	private static List<CnDnAdditionalDetails> getDummyCnDnAdditionalList() {
		List<CnDnAdditionalDetails> cnDnAdditionalList =  new ArrayList<CnDnAdditionalDetails>(); 
		
		CnDnAdditionalDetails cndnAdd1 = new CnDnAdditionalDetails();
		cndnAdd1.setAmountAfterDiscount(4400.00);
		cndnAdd1.setCnDnType("creditNote");
		cndnAdd1.setCreatedBy("3927");
		cndnAdd1.setCreatedOn(GSTNUtil.convertStringToTimestamp("2019-06-28"));
		cndnAdd1.setId(1);
		cndnAdd1.setInvoiceDate(GSTNUtil.convertStringToTimestamp("2019-06-28"));
		cndnAdd1.setInvoiceNumber("S1907/1/43/2");
		cndnAdd1.setInvoiceValue(5583.81);
		cndnAdd1.setInvoiceValueAfterRoundOff(5584.00);
		cndnAdd1.setIterationNo(1);
		cndnAdd1.setOrgUId(205);
		cndnAdd1.setReason("salesReturn");
		cndnAdd1.setRegime("postGst");
		cndnAdd1.setTotalTax(1183.81);
		cndnAdd1.setIsAdditionalChargePresent("Y");
		cnDnAdditionalList.add(cndnAdd1);
		
		return cnDnAdditionalList;
	}
	
	private static GSTINDetails getDummyGSTINDetails() {
    	GSTINDetails gstinDetails = new GSTINDetails();
    	
    	gstinDetails.setGstinAddressMapping(getDummyGSTINAddressMapping());
    	gstinDetails.setGstinLocationSet(getDummyGstinLocationSet());
    	gstinDetails.setGstinNo("09ASDCL1456L1ZK");
    	gstinDetails.setGstinUname("Adarsh");
    	gstinDetails.setId(2092);
    	gstinDetails.setReferenceId(3927);
    	gstinDetails.setState(27);
    	gstinDetails.setStatus("1");
    	gstinDetails.setUniqueSequence("4");
    
    	
		return gstinDetails;
	}
	
	private static GSTINAddressMapping getDummyGSTINAddressMapping() {
		GSTINAddressMapping gSTINAddressMapping = new GSTINAddressMapping();
    	gSTINAddressMapping.setAddress("Kolkota");
    	gSTINAddressMapping.setCity("KOLKATA");
    	gSTINAddressMapping.setId(1761);
    	gSTINAddressMapping.setState("West Bengal");
    	gSTINAddressMapping.setPinCode(700005);
    	
		return gSTINAddressMapping;
	}
	
	private static List<GstinLocation> getDummyGstinLocationSet() {
		List<GstinLocation> gstinLocationList = new ArrayList<GstinLocation>();
		GstinLocation g1 = new GstinLocation();
		g1.setGstinLocation("Dhanyti");
		g1.setGstinStore("Akub sf");
		g1.setId(5);
		g1.setRefGstinId(560);
		g1.setUniqueSequence("3");
		gstinLocationList.add(g1);
		
		return gstinLocationList;
	}
	
	private static UserMaster getDummyUserMaster() {
    	UserMaster user = new UserMaster();
    	OrganizationMaster organization = new OrganizationMaster();
    	organization.setOrgName("Apple INC");
    	organization.setPanNumber("ASDCL1456L");
    	user.setOrganizationMaster(organization);
		return user;
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
	 
	 public static void addMoreText(Phrase p,String content,String fontType){
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
	 
	 public static PdfPCell createCell(String content, float borderWidth, int colspan, int alignment, int rowSpan) {
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
	 
	 public static PdfPCell createCell(Phrase p, float borderWidth, int colspan, int alignment, int rowSpan) {
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
	 
	 public static PdfPCell createCellWithDynamicBorders(Phrase p, float borderWidth, int colspan, int alignment, int rowSpan,int top,int right,int bottom,int left) {
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
	    
	    public static PdfPCell createCellWithDynamicBorders(String content, float borderWidth, int colspan, int alignment, int rowSpan,int top,int right,int bottom,int left) {
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
	    
    public static Map<String, String> generateCnDnPdf(InvoiceDetails invoiceDetails, GSTINDetails gstinDetails,UserMaster user, String customerStateCode, Map<String, Map<String, Double>> gstMap, String amtInWords,String directoryPath, String logoImagePath, Integer iterationNo) throws IOException, DocumentException {
		Map<String,String> mapResponse = new HashMap<String,String>();
		Map<String,String> mapCnDnPdfFileName = GSTNUtil.generateCnDnPdfFileName(invoiceDetails.getCnDnAdditionalList(),directoryPath,iterationNo);//directoryPath + File.separator+year+File.separator+month+File.separator+invoiceDetails.getOrgUId()+File.separator+invoiceDetails.getReferenceId()+File.separator+invoiceDetails.getInvoiceNumber()+".pdf";
		String pdfFilePath = mapCnDnPdfFileName.get("pdfFilePath");
		System.out.println("Path : "+pdfFilePath);
		File file = new File(pdfFilePath);
        file.getParentFile().mkdirs();
        mapResponse = createCnDnPdf(pdfFilePath, invoiceDetails, gstinDetails,  user, customerStateCode, gstMap,amtInWords,logoImagePath,iterationNo);
        mapResponse.put(GSTNConstants.PDF_PATH, pdfFilePath);
        mapResponse.put(GSTNConstants.PDF_FILENAME, mapCnDnPdfFileName.get("pdfFileName"));
        return  mapResponse;
	}    
	
	public static void main(String[] args)  throws IOException, DocumentException{
		InvoiceDetails invoiceDetails = getInvoiceDetailsDummy();
		GSTINDetails gstinDetails = getDummyGSTINDetails();
		UserMaster user = getDummyUserMaster(); 
		Map<String,Map<String,Double>> gstMap = GSTNUtil.convertListToMapForCnDn(invoiceDetails.getCnDnList(),1);
		generateCnDnPdf(invoiceDetails,gstinDetails, user, "MH", gstMap, "Five Thousand Five Hundred Eighty-four Rupees  Only", "C:/apps/gstn/invoice/2019/July/205/3927/","C:/apps/gstn/logo/212/logo_2121.JPEG", 1);
		//createCnDnPdf("C:\\apps\\gstn\\invoice\\2019\\June\\205\\3927\\C19066-1.pdf", invoiceDetails,gstinDetails, user, "MH", gstMap, "Five Thousand Five Hundred Eighty-four Rupees  Only", "C:/apps/gstn/logo/212/logo_2121.JPEG", 1);
	}
	
	private static Map<String, String> createCnDnPdf(String dest, InvoiceDetails invoiceDetails,GSTINDetails gstinDetails, UserMaster user, String customerStateCode,Map<String, Map<String, Double>> gstMap, String amtInWords, String logoImagePath, Integer iterationNo)  throws IOException, DocumentException  {
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
			            table.addCell(createCell(GSTNUtil.convertDoubleTo2DecimalPlaces(s.getRate())+"", 1, 1, Element.ALIGN_RIGHT, 1));
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
	
private static Map<String, MapSegregate> setInMap(String key, Double kValue, Map<String, MapSegregate> mapSegregate,String type) {
		
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
}
