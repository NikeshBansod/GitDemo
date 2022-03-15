/**
 * 
 */
package com.reliance.gstn.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.reliance.gstn.model.ApiBean;
import com.reliance.gstn.model.CnDnAdditionalDetails;
import com.reliance.gstn.model.InvoiceCnDnDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.InvoiceServiceDetails;
import com.reliance.gstn.model.Month;
import com.reliance.gstn.model.PayloadCnDnDetails;
import com.reliance.gstn.model.PurchaseEntryServiceOrGoodDetails;
import com.reliance.gstn.model.UserMaster;


/**
 * @author Nikesh.Bansod
 *
 */
public class GSTNUtil {

	private static final Logger logger = Logger.getLogger(GSTNUtil.class);
	
	public static String getSecondaryUsersIdsInString(List<UserMaster> secondaryUserList) {
		StringBuffer sb = new StringBuffer();
		String idsInString = null;
		for(UserMaster user : secondaryUserList){
			sb = sb.append(user.getId()+",");
		}
		idsInString = sb.toString();
		return idsInString.substring(0,idsInString.length()-1);
	}
	
	public Date convertStringToDate(String dateString)
	{
	    Date date = null;
	    Date formatteddate = null;
	    DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
	    try{
	        date = df.parse(dateString);
	        formatteddate = new java.sql.Timestamp(date.getTime());
	    }
	    catch ( Exception e){
	        System.out.println(e);
	    }
	    return formatteddate;
	}
	
	public static Timestamp convertStringToTimestamp(String str_date) {
	    try {
	      DateFormat formatter;
	      formatter = new SimpleDateFormat("yyyy-MM-dd");
	       // you can change format of date
	      Date date = formatter.parse(str_date);
	      java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());

	      return timeStampDate;
	    } catch (ParseException e) {
	      System.out.println("Exception :" + e);
	      return null;
	    }
	}
	
	public static String convertTimestampToString(Timestamp timeRef) {
		Date dateRef = new Date();
		dateRef.setTime(timeRef.getTime());
		String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(dateRef);
		return formattedDate;
	}
	
	public static String convertTimestampToMonthAndYear() {
		
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		return year+"--"+theMonth(month);
	}
	
	public static String theMonth(int month){
	    String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	    return monthNames[month];
	}
		
	
	
	public static List<Integer> getListFromString(String idsValuesToFetch){
		List<Integer> ali=new ArrayList<Integer>();
		List<String> als=new ArrayList<String>();
		als=Arrays.asList(idsValuesToFetch.split(","));
		for (String val : als) {
			ali.add(Integer.valueOf(val));
		}
		return ali;
	}
	
	public static String getPlaceHoderString(List<Integer> ali) {
		StringBuffer bpIdDynamicPart = new StringBuffer();
		for (int i = 0; i < ali.size(); i++) {
			bpIdDynamicPart.append("?,");
		}

		return bpIdDynamicPart.substring(0, bpIdDynamicPart.length() - 1);
	}

	public static String convertStringDateToOtherFormat(String dateStr) {
		DateFormat srcDf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = srcDf.parse(dateStr);
			DateFormat destDf = new SimpleDateFormat("yyyyMMdd");
			dateStr = destDf.format(date);
			//System.out.println("Converted date is : " + dateStr);
		} catch (ParseException e) {
			System.out.println(e);
		}
	

		return dateStr;
	}

	public static String verifyGstinService(ApiBean api) throws Exception {
		String USER_AGENT = "Mozilla/5.0";
		/*String userName=api.getUserName();
		String location=api.getLocation();
		String stateCode=api.getStateCode();
		String sourceDevice=api.getSourceDevice();
		String sourceDeviceString=api.getSourceDeviceString();
		String userIp=api.getUserIp();
		String securityKey=api.getSecurityKey();
		String appCode=api.getAppCode();
		*/
		URL obj = new URL(api.getGstinverifyUrl());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		String urlParameters = "userName=Reliance.MH.1&securityKey=258412SDAOMH425484&location=Mumbai&appCode=01&stateCode=27&userIP=127.0.0.1&sourceDevice=Android&sourceDeviceString=Laptop";
		/*String urlParameters = "userName="+userName+"&securityKey="+securityKey+"&location="+location+
				 "&appCode="+appCode+"&stateCode="+stateCode+"&userIP="+userIp+"&sourceDevice="+sourceDevice+"&sourceDeviceString="+sourceDeviceString+"";*/
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println("Response ::::" + response.toString());
		return new String(response);

	}
	
	public static String validateGstinOtp(ApiBean api) throws Exception {
		String USER_AGENT = "Mozilla/5.0";
	/*	String userName=api.getUserName();
		String location=api.getLocation();
		String stateCode=api.getStateCode();
		String sourceDevice=api.getSourceDevice();
		String sourceDeviceString=api.getSourceDeviceString();
		String userIp=api.getUserIp();
		String securityKey=api.getSecurityKey();
		String appCode=api.getAppCode();
		String gstinOtp= api.getGstinOtp();
		*/
		URL obj = new URL(api.getGstinverifyUrl());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		String urlParameters = "userName=Reliance.MH.1&securityKey=258412SDAOMH425484&location=Mumbai&appCode=01&stateCode=27&userIP=127.0.0.1&sourceDevice=Android&sourceDeviceString=Laptop&otp=111111";
		/*String urlParameters = "userName="+userName+"&securityKey="+securityKey+"&location="+location+
				 "&appCode="+appCode+"&stateCode="+stateCode+"&userIP="+userIp+"&sourceDevice="+sourceDevice+"&sourceDeviceString="+sourceDeviceString+"";*/
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println("Response ::::" + response.toString());
		return new String(response);

	}
	
	public static String convertTimestampToString1(Timestamp timeRef) {
		//2017-05-26 00:00:00.0
		String formattedDate = null;
		 try {
			 Date dateRef = new java.util.Date(timeRef.getTime() + (timeRef.getNanos() / 1000000));;
			 //dateRef.setTime(timeRef.getTime());
			  formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(dateRef);
		 } catch (Exception e) { 
			 System.out.println("Error "+e);
			 return null;  
		 }
		return formattedDate;
	}
	
	public static boolean isValidInputPattern(String input,String regex){
		boolean isValidInputPattern=false;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			isValidInputPattern=true;
		}
		return isValidInputPattern;
	}
	
	
	public static String getClientIpAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}
	
	public static Timestamp convertStringInddMMyyyyToTimestamp(String str_date) {
	    try {
	      DateFormat formatter;
	      formatter = new SimpleDateFormat("dd-MM-yyyy");
	       // you can change format of date
	      Date date = formatter.parse(str_date);
	      java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());

	      return timeStampDate;
	    } catch (ParseException e) {
	      System.out.println("Exception :" + e);
	      return null;
	    }
	}
	
	public static String convertCurrentDateToddMMYYYY(){
		Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String today = formatter.format(date);
        System.out.println(today);
        return today;
	}
	
	public static String convertStringDateInddMMyyyyToFormatyyyyMMdd(String dateStr) {
		DateFormat srcDf = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = srcDf.parse(dateStr);
			DateFormat destDf = new SimpleDateFormat("yyyy-MM-dd");
			dateStr = destDf.format(date);
			//System.out.println("Converted date is : " + dateStr);
		} catch (ParseException e) {
			System.out.println(e);
		}
	

		return dateStr;
	}

	public static Map<String,Map<String,Double>> convertListToMap(List<InvoiceServiceDetails> serviceDetails) {
		 Map<String,Map<String,Double>> result=new HashMap<String,Map<String,Double>>();
		
		 Map<String,Double> igstMap=new HashMap<String,Double>();
		 Map<String,Double> cgstMap=new HashMap<String,Double>();
		 Map<String,Double> sgstMap=new HashMap<String,Double>();
		 Map<String,Double> ugstMap=new HashMap<String,Double>();
		 Map<String,Double> igstDiffPercentMap=new HashMap<String,Double>();
		 Map<String,Double> cgstDiffPercentMap=new HashMap<String,Double>();
		 Map<String,Double> sgstDiffPercentMap=new HashMap<String,Double>();
		 Map<String,Double> ugstDiffPercentMap=new HashMap<String,Double>();
			for(InvoiceServiceDetails service : serviceDetails){
				String gstPercent=null;
				if(service.getCategoryType().equals("CATEGORY_WITH_IGST")||service.getCategoryType().equals("CATEGORY_EXPORT_WITH_IGST")){
					gstPercent=String.valueOf(service.getIgstPercentage());
					if(service.getDiffPercent() != null && service.getDiffPercent().equals("Y")){
						if(!igstDiffPercentMap.containsKey(gstPercent)){
							igstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
						}else{
							igstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(igstDiffPercentMap.get(gstPercent)+service.getAmount()));
						}
					}else{
						if(!igstMap.containsKey(gstPercent)){
							igstMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
						}else{
							igstMap.put(gstPercent, GSTNUtil.convertToDouble(igstMap.get(gstPercent)+service.getAmount()));
						}
					}
					
				}
				if(service.getCategoryType().equals("CATEGORY_WITH_SGST_CSGT")|| service.getCategoryType().equals("CATEGORY_WITH_UGST_CGST")){
					gstPercent=String.valueOf(service.getCgstPercentage());
					if(service.getDiffPercent() != null && service.getDiffPercent().equals("Y")){
						if(!cgstDiffPercentMap.containsKey(gstPercent)){
							cgstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
						}else{
							cgstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(cgstDiffPercentMap.get(gstPercent)+service.getAmount()));
						}
					}else{
						if(!cgstMap.containsKey(gstPercent)){
							cgstMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
						}else{
							cgstMap.put(gstPercent, GSTNUtil.convertToDouble(cgstMap.get(gstPercent)+service.getAmount()));
						}
					}
					
				}
				if(service.getCategoryType().equals("CATEGORY_WITH_SGST_CSGT")){
					gstPercent=String.valueOf(service.getSgstPercentage());
					if(service.getDiffPercent() != null && service.getDiffPercent().equals("Y")){
						if(!sgstDiffPercentMap.containsKey(gstPercent)){
							sgstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
						}else{
							sgstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(sgstDiffPercentMap.get(gstPercent)+service.getAmount()));
						}
					}else{
						if(!sgstMap.containsKey(gstPercent)){
							sgstMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
						}else{
							sgstMap.put(gstPercent, GSTNUtil.convertToDouble(sgstMap.get(gstPercent)+service.getAmount()));
						}
					}
					
				}
				if(service.getCategoryType().equals("CATEGORY_WITH_UGST_CGST")){
					gstPercent=String.valueOf(service.getUgstPercentage());
					if(service.getDiffPercent() != null && service.getDiffPercent().equals("Y")){
						if(!ugstDiffPercentMap.containsKey(gstPercent)){
							ugstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
						}else{
							ugstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(ugstDiffPercentMap.get(gstPercent)+service.getAmount()));
						}
					}else{
						if(!ugstMap.containsKey(gstPercent)){
							ugstMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
						}else{
							ugstMap.put(gstPercent, GSTNUtil.convertToDouble(ugstMap.get(gstPercent)+service.getAmount()));
						}
					}
					
				}
			}	
	
			result.put("igst", igstMap);
			result.put("cgst", cgstMap);
			result.put("sgst", sgstMap);
			result.put("ugst", ugstMap);
			result.put("igstDiffPercent", igstDiffPercentMap);
			result.put("cgstDiffPercent", cgstDiffPercentMap);
			result.put("sgstDiffPercent", sgstDiffPercentMap);
			result.put("ugstDiffPercent", ugstDiffPercentMap);
			
		return result;
	}
	
	public static String getUniqueSequence(Session session,String uniqueSequenceQuery,String targetTable,String refColumn,Integer id){
		
		String uniqueSequence="";
		
		logger.info("Entry");
		try {
			uniqueSequenceQuery =	uniqueSequenceQuery.replace("targetTable", targetTable);
			uniqueSequenceQuery=uniqueSequenceQuery.replace("refColumn", refColumn);
			Query query = session.createSQLQuery(uniqueSequenceQuery);
			
			query.setInteger("id", id);
		
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>)query.list();
			if(!list.isEmpty()){
				uniqueSequence=list.get(0);
			}else{
				uniqueSequence="1";
			}
			
			} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");

		return uniqueSequence;
	}

	public static String getCurrentYear() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yy");
		String year = format.format(date);
		return year;
	}

	public static String incrementInvoiceCount(String latestInvoiceCount) {
		int count = 0;
		/*if(latestInvoiceCount.length() >= 7){*/
			 count = Integer.parseInt(latestInvoiceCount.substring(latestInvoiceCount.lastIndexOf("/") + 1));
			 
		/*}*/
		count = count + 1;
		return ""+count;
	}
	
	public static String incrementDocCount(String latestdocNo) {
		String result = null;
		String value=null;
		String first="01";
		int count =01;
		DecimalFormat formatter = new DecimalFormat("00");
		if(latestdocNo.length() >= 13){
			 //count = Integer.parseInt(latestdocNo.substring(12,13));
			  value=latestdocNo.substring(latestdocNo.length() - 2);
			  count = Integer.parseInt(value)+Integer.parseInt(first);
			  //String aFormatted = formatter.format(count);
		}
		 return result = ""+formatter.format(count);
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getLocationUniqueSequence(Session session,String uniqueSequenceQuery,String targetTable,String refColumn,Integer id,int listSize){
		
		List<String> list = null;
		logger.info("Entry");
		try {
			uniqueSequenceQuery =	uniqueSequenceQuery.replace("targetTable", targetTable);
			uniqueSequenceQuery=uniqueSequenceQuery.replace("refColumn", refColumn);
			Query query = session.createSQLQuery(uniqueSequenceQuery);
			
			query.setInteger("refId", id);
			query.setInteger("locSize", listSize);
		
			list = (List<String>)query.list();
		} catch (Exception e) {
			logger.error("Error in:",e);
			throw e;
		}
		logger.info("Exit");

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getInitialLocationUniqueSequence(Session session,String uniqueSequenceQuery,int listSize){
			
			List<String> list = null;
			logger.info("Entry");
			try {
				Query query = session.createSQLQuery(uniqueSequenceQuery);
				
				query.setInteger("listSize", (listSize-1));
			
				list = (List<String>)query.list();
				} catch (Exception e) {
				logger.error("Error in:",e);
				throw e;
			}
			logger.info("Exit");
	
			return list;
		}
	
	public static boolean checkIfFileExists(String filePathString) {
		boolean isFilePresent = false;
		if(null != filePathString){
			File f = new File(filePathString);
			if(!f.isDirectory()  && f.exists()) { 
				isFilePresent = true;
			}
		}
		
		return isFilePresent;
	}

	public static String generateInvoicePdfFileName(InvoiceDetails invoiceDetails,String directoryPath) {
		String monthAndYear = getInvoiceYearAndMonth(invoiceDetails.getCreatedOn());//convertTimestampToMonthAndYear();
		String[] parts = monthAndYear.split("---");
		String year = parts[0]; 
		String month = parts[1];
		String invoiceNumber = invoiceDetails.getInvoiceNumber();
		invoiceNumber = invoiceNumber.replaceAll("/", "-");
		String pdfFilePath = directoryPath + File.separator+year+File.separator+month+File.separator+invoiceDetails.getOrgUId()+File.separator+invoiceDetails.getReferenceId()+File.separator+invoiceNumber+"_"+invoiceDetails.getIterationNo()+".pdf";
		return pdfFilePath;
	}
	
	public static String convertTimestampToStringInyyMMM(Timestamp timeRef) {
		Date dateRef = new Date();
		dateRef.setTime(timeRef.getTime());
		String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(dateRef);
		return formattedDate;
	}
	
	private static String getInvoiceYearAndMonth(Timestamp createdOn) {
		String str = GSTNUtil.convertTimestampToStringInyrMMM(createdOn);
		return str;
	}

	public static String convertTimestampToStringInyrMMM(Timestamp timeRef) {
		Date dateRef = new Date();
		dateRef.setTime(timeRef.getTime());
		String formattedDate = new SimpleDateFormat("yyyy---MMMM").format(dateRef);
		return formattedDate;
	}
	
	public static Double convertToDouble(double amount){
		
		return Double.valueOf(new DecimalFormat("#.##").format(amount));
	}
	
	public static String convertDoubleTo2DecimalPlaces(double amount){
		 //DecimalFormat df = new DecimalFormat("#.00");
		 //String angle = df.format(amount);
		
		return String.format("%.2f", amount);
	}
	
	public static String convertDoubleTo3DecimalPlaces(double amount){
		 //DecimalFormat df = new DecimalFormat("#.00");
		 //String angle = df.format(amount);
		
		return String.format("%.3f", amount);
	}
	
	public static String genRandomNumber(long minRange,long maxRange){
		
		long random=ThreadLocalRandom.current().nextLong(minRange, maxRange);
		
		return String.valueOf(random);
	}
	
	public static String genUniqueInvoiceTxn(){
		
		String txn=GSTNConstants.GSTR1_INV_TXN_STRING+System.currentTimeMillis();
		 String uuid = UUID.randomUUID().toString().replaceAll("-","");
		 int len=txn.length();
		   if(len!=AspApiConstants.GSTR1_UNIQUE_TXN_SIZE){
			   txn=txn+uuid.substring(0,AspApiConstants.GSTR1_UNIQUE_TXN_SIZE-len).toUpperCase();
		   }
		   
		return txn;
	}
	
	public static double aggTaxRate(InvoiceServiceDetails invServiceDetails){
		
		double aggTax = (invServiceDetails.getIgstPercentage() + invServiceDetails.getCgstPercentage() + invServiceDetails.getSgstPercentage() + invServiceDetails.getUgstPercentage());
		
		return aggTax;
	}
	
	public static double aggCNDNTaxRate(PayloadCnDnDetails invServiceDetails){
		
		double aggTax = (invServiceDetails.getIgstPercentage() + invServiceDetails.getCgstPercentage() + invServiceDetails.getSgstPercentage() + invServiceDetails.getUgstPercentage());
		
		return aggTax;
	}

	public static String formatDate(Date date,String dtFormat) {
		String formattedDate = new SimpleDateFormat(dtFormat).format(date);
		return formattedDate;
	}
	
	public static String getCurrentMachineIpAddr(){
		String ipAddress="";
		
		  try {
			  InetAddress ip;
			ip = InetAddress.getLocalHost();
			ipAddress = ip.getHostAddress();

		  } catch (UnknownHostException e) {

			e.printStackTrace();

		  }
		  return ipAddress;
	}
	
	public static boolean deleteLogo(String pathForLogoImage) throws IOException {
		logger.info("Start");
		boolean isImagePresent = false;
        File directory = new File(pathForLogoImage);
		if (directory.exists()){
        	//directory.delete();
        	FileUtils.deleteDirectory(directory);
       		isImagePresent = true;
       		logger.info("Deleted the folder at path : "+pathForLogoImage);
        }
		logger.info("Exit");
		return isImagePresent;
	}
	
	public static void deletePdf(String pdfPath,String orgId) throws IOException {
		logger.info("Start");
		List<String> pathForPdfImage = InvoicePdfDelete.getPaths(pdfPath, orgId);
		for(String dirPath : pathForPdfImage){
			File directory = new File(dirPath);
			
			if (directory.exists()){
	        	logger.info("Deleted the folder at path : "+pdfPath+" for organization id : "+orgId);
	        	FileUtils.deleteDirectory(directory);
	        	//directory.delete();
	        }
		}
		logger.info("Exit");
	}
	
	public static String getLastURIPart(String uri) {
		int index = uri.lastIndexOf("/");
		String lastURIPart = "";
		if (index >= 0) {
			lastURIPart = uri.substring(index);
		}

		return lastURIPart;
	}

	public static List<InvoiceCnDnDetails> changeObjectFromServiceToCnDn(List<InvoiceServiceDetails> serviceList) {
		List<InvoiceCnDnDetails> cnDnList = new ArrayList<InvoiceCnDnDetails>();
		InvoiceCnDnDetails cnDn = null;
		for(InvoiceServiceDetails service : serviceList){
			cnDn = new InvoiceCnDnDetails();
			cnDn.setAdditionalAmount(service.getAdditionalAmount());
			cnDn.setAmount(service.getAmount());
			cnDn.setBillingFor(service.getBillingFor());
			cnDn.setCalculationBasedOn(service.getCalculationBasedOn());
			cnDn.setCategoryType(service.getCategoryType());
			cnDn.setCess(service.getCess());
			cnDn.setCgstAmount(service.getCgstAmount());
			cnDn.setCgstPercentage(service.getCgstPercentage());
			cnDn.setCnDnType(service.getCnDnType());
			/*cnDn.setCreatedBy(createdBy);
			cnDn.setCreatedOn(createdOn);*/
			cnDn.setDeliveryStateId(service.getDeliveryStateId());
			cnDn.setGstnStateId(service.getGstnStateId());
			cnDn.setHsnSacCode(service.getHsnSacCode());
			cnDn.setHsnSacDescription(service.getHsnSacDescription());
			/*cnDn.setId(id);*/
			cnDn.setIgstAmount(service.getIgstAmount());
			cnDn.setIgstPercentage(service.getIgstPercentage());
			cnDn.setIterationNo(0);
			cnDn.setOfferAmount(service.getOfferAmount());
			cnDn.setPreviousAmount(service.getPreviousAmount());
			cnDn.setQuantity(service.getQuantity());
			cnDn.setRate(service.getRate());
			cnDn.setReason(service.getReason());
			cnDn.setRegime(service.getRegime());
			cnDn.setServiceId(service.getId());
			cnDn.setServiceIdInString(service.getServiceIdInString());
			cnDn.setSgstAmount(service.getSgstAmount());
			cnDn.setSgstPercentage(service.getSgstPercentage());
			cnDn.setStatus(service.getStatus());
			cnDn.setTaxableValue(service.getTaxableValue());
			cnDn.setTaxAmount(service.getTaxAmount());
			cnDn.setUgstAmount(service.getUgstAmount());
			cnDn.setUgstPercentage(service.getUgstPercentage());
			cnDn.setUnitOfMeasurement(service.getUnitOfMeasurement());
			cnDn.setValueAfterTax(service.getValueAfterTax());
			cnDn.setAmountAfterDiscount(service.getAmountAfterDiscount());
			cnDn.setDiffPercent(service.getDiffPercent());
			cnDnList.add(cnDn);
		}
		return cnDnList;
	}

	public static String incrementCnDnCount(String latestInvoiceCount) {
		int count = Integer.parseInt(latestInvoiceCount.substring(7));
		count = count + 1;
		return String.valueOf(count);
	}

	public static Map<String, Map<String, Double>> convertListToMapForCnDn(List<InvoiceCnDnDetails> cnDnList,Integer iterationNo) {
		 Map<String,Map<String,Double>> result=new HashMap<String,Map<String,Double>>();
			
		 Map<String,Double> igstMap=new HashMap<String,Double>();
		 Map<String,Double> cgstMap=new HashMap<String,Double>();
		 Map<String,Double> sgstMap=new HashMap<String,Double>();
		 Map<String,Double> ugstMap=new HashMap<String,Double>();
		 Map<String,Double> igstDiffPercentMap=new HashMap<String,Double>();
		 Map<String,Double> cgstDiffPercentMap=new HashMap<String,Double>();
		 Map<String,Double> sgstDiffPercentMap=new HashMap<String,Double>();
		 Map<String,Double> ugstDiffPercentMap=new HashMap<String,Double>();
			for(InvoiceCnDnDetails service : cnDnList){
				if(service.getIterationNo() == iterationNo){	
				
					String gstPercent=null;
					if(service.getCategoryType().equals("CATEGORY_WITH_IGST")||service.getCategoryType().equals("CATEGORY_EXPORT_WITH_IGST")){
						gstPercent=String.valueOf(service.getIgstPercentage());
						if(service.getDiffPercent() != null && service.getDiffPercent().equals("Y")){
							if(!igstDiffPercentMap.containsKey(gstPercent)){
								igstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
							}else{
								igstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(igstDiffPercentMap.get(gstPercent)+service.getAmount()));
							}
						}else{
							if(!igstMap.containsKey(gstPercent)){
								igstMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
							}else{
								igstMap.put(gstPercent, GSTNUtil.convertToDouble(igstMap.get(gstPercent)+service.getAmount()));
							}
						}
					}
					if(service.getCategoryType().equals("CATEGORY_WITH_SGST_CSGT")|| service.getCategoryType().equals("CATEGORY_WITH_UGST_CGST")){
						gstPercent=String.valueOf(service.getCgstPercentage());
						if(service.getDiffPercent() != null && service.getDiffPercent().equals("Y")){
							if(!cgstDiffPercentMap.containsKey(gstPercent)){
								cgstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
							}else{
								cgstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(cgstDiffPercentMap.get(gstPercent)+service.getAmount()));
							}
						}else{
							if(!cgstMap.containsKey(gstPercent)){
								cgstMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
							}else{
								cgstMap.put(gstPercent, GSTNUtil.convertToDouble(cgstMap.get(gstPercent)+service.getAmount()));
							}
						}
					}
					if(service.getCategoryType().equals("CATEGORY_WITH_SGST_CSGT")){
						gstPercent=String.valueOf(service.getSgstPercentage());
						if(service.getDiffPercent() != null && service.getDiffPercent().equals("Y")){
							if(!sgstDiffPercentMap.containsKey(gstPercent)){
								sgstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
							}else{
								sgstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(sgstDiffPercentMap.get(gstPercent)+service.getAmount()));
							}
						}else{
							if(!sgstMap.containsKey(gstPercent)){
								sgstMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
							}else{
								sgstMap.put(gstPercent, GSTNUtil.convertToDouble(sgstMap.get(gstPercent)+service.getAmount()));
							}
						}
					}
					if(service.getCategoryType().equals("CATEGORY_WITH_UGST_CGST")){
						gstPercent=String.valueOf(service.getUgstPercentage());
						if(service.getDiffPercent() != null && service.getDiffPercent().equals("Y")){
							if(!ugstDiffPercentMap.containsKey(gstPercent)){
								ugstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
							}else{
								ugstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(ugstDiffPercentMap.get(gstPercent)+service.getAmount()));
							}
						}else{
							if(!ugstMap.containsKey(gstPercent)){
								ugstMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
							}else{
								ugstMap.put(gstPercent, GSTNUtil.convertToDouble(ugstMap.get(gstPercent)+service.getAmount()));
							}
						}
					}
				
				}
			}//end of for loop	
	
			result.put("igst", igstMap);
			result.put("cgst", cgstMap);
			result.put("sgst", sgstMap);
			result.put("ugst", ugstMap);
			result.put("igstDiffPercent", igstDiffPercentMap);
			result.put("cgstDiffPercent", cgstDiffPercentMap);
			result.put("sgstDiffPercent", sgstDiffPercentMap);
			result.put("ugstDiffPercent", ugstDiffPercentMap);
			
		return result;
	}

	public static Map<String,String> generateCnDnPdfFileName(List<CnDnAdditionalDetails> cndnAddList, String directoryPath,Integer iterationNo) {
		Map<String,String> map = new HashMap<String,String>();
		String monthAndYear = null;
		
		String createdBy = null;
		String invoiceNumber = null;
		String orgUid = null;
		for(CnDnAdditionalDetails cndn : cndnAddList){
			if(cndn.getIterationNo() == iterationNo){
				monthAndYear = getInvoiceYearAndMonth(cndn.getCreatedOn());//convertTimestampToMonthAndYear();
				createdBy = cndn.getCreatedBy();
				invoiceNumber = cndn.getInvoiceNumber();
				orgUid = cndn.getOrgUId().toString();
			}
		}
		if(invoiceNumber.contains("/")){
			invoiceNumber = invoiceNumber.replace("/", "-");
		}
		
		String[] parts = monthAndYear.split("---");
		String year = parts[0]; 
		String month = parts[1];
		 
		String pdfFilePath = directoryPath + File.separator+year+File.separator+month+File.separator+orgUid+File.separator+createdBy+File.separator+invoiceNumber+".pdf";
		map.put("pdfFilePath", pdfFilePath);
		map.put("pdfFileName", invoiceNumber+".pdf");
		map.put("cndnInvoiceNumber", invoiceNumber);
		return map;
	}
	
	public static String toTitleCase(String input) {
		return WordUtils.capitalizeFully(input);
	}
	
	public static double cdnrAggTaxRate(PayloadCnDnDetails invCdnrDetails){
		
		double aggTax = (invCdnrDetails.getIgstPercentage() + invCdnrDetails.getCgstPercentage() + invCdnrDetails.getSgstPercentage() + invCdnrDetails.getUgstPercentage());
		
		return aggTax;
	}

	public static Map<String,Double> getNoteTxValSum(InvoiceDetails invoiceDetails){
		
		Map<String,Double> noteTaxValues = new HashMap<String, Double>();
		noteTaxValues.put("debitNoteTxvalSum", 0.0);
		noteTaxValues.put("debitNoteIgstSum", 0.0);
		noteTaxValues.put("debitNoteCgstSum", 0.0);
		noteTaxValues.put("debitNoteSgstSum", 0.0);
		
		noteTaxValues.put("creditNoteTxvalSum", 0.0);
		noteTaxValues.put("crediNoteIgstSum", 0.0);
		noteTaxValues.put("crediNoteCgstSum", 0.0);
		noteTaxValues.put("crediNoteSgstSum", 0.0);
		
		List<InvoiceCnDnDetails> cnDnDetailsList = invoiceDetails.getCnDnList();
		
		for (InvoiceCnDnDetails invoiceCnDnDetails : cnDnDetailsList) {
			if(invoiceCnDnDetails.getCnDnType().equalsIgnoreCase(AspApiConstants.CREDIT_NOTE)){
				noteTaxValues.put("creditNoteTxvalSum",noteTaxValues.get("creditNoteTxvalSum")+invoiceCnDnDetails.getTaxableValue());
				noteTaxValues.put("crediNoteIgstSum",noteTaxValues.get("crediNoteIgstSum")+invoiceCnDnDetails.getIgstAmount());
				noteTaxValues.put("crediNoteCgstSum",noteTaxValues.get("crediNoteCgstSum")+invoiceCnDnDetails.getCgstAmount());
				noteTaxValues.put("crediNoteSgstSum",noteTaxValues.get("crediNoteSgstSum")+invoiceCnDnDetails.getSgstAmount());
			}else if(invoiceCnDnDetails.getCnDnType().equalsIgnoreCase(AspApiConstants.DEBIT_NOTE)){
				noteTaxValues.put("debitNoteTxvalSum",noteTaxValues.get("debitNoteTxvalSum")+invoiceCnDnDetails.getTaxableValue());
				noteTaxValues.put("debitNoteIgstSum",noteTaxValues.get("debitNoteIgstSum")+invoiceCnDnDetails.getIgstAmount());
				noteTaxValues.put("debitNoteCgstSum",noteTaxValues.get("debitNoteCgstSum")+invoiceCnDnDetails.getCgstAmount());
				noteTaxValues.put("debitNoteSgstSum",noteTaxValues.get("debitNoteSgstSum")+invoiceCnDnDetails.getSgstAmount());
			}
		}
		return noteTaxValues;
	}
	
	public static Map<String,Double> getNoteTxValSum(InvoiceDetails invoiceDetails, InvoiceServiceDetails invoiceServiceDetails){
		
		Map<String,Double> noteTaxValues = new HashMap<String, Double>();
		noteTaxValues.put("debitNoteTxvalSum", 0.0);
		noteTaxValues.put("debitNoteIgstSum", 0.0);
		noteTaxValues.put("debitNoteCgstSum", 0.0);
		noteTaxValues.put("debitNoteSgstSum", 0.0);
		
		noteTaxValues.put("creditNoteTxvalSum", 0.0);
		noteTaxValues.put("crediNoteIgstSum", 0.0);
		noteTaxValues.put("crediNoteCgstSum", 0.0);
		noteTaxValues.put("crediNoteSgstSum", 0.0);
		
		List<InvoiceCnDnDetails> cnDnDetailsList = invoiceDetails.getCnDnList();
		
		for (InvoiceCnDnDetails invoiceCnDnDetails : cnDnDetailsList) {
			if(invoiceCnDnDetails.getServiceId().intValue() == invoiceServiceDetails.getId().intValue()){
			if(invoiceCnDnDetails.getCnDnType().equalsIgnoreCase(AspApiConstants.CREDIT_NOTE)){
				noteTaxValues.put("creditNoteTxvalSum",noteTaxValues.get("creditNoteTxvalSum")+invoiceCnDnDetails.getTaxableValue());
				noteTaxValues.put("crediNoteIgstSum",noteTaxValues.get("crediNoteIgstSum")+invoiceCnDnDetails.getIgstAmount());
				noteTaxValues.put("crediNoteCgstSum",noteTaxValues.get("crediNoteCgstSum")+invoiceCnDnDetails.getCgstAmount());
				noteTaxValues.put("crediNoteSgstSum",noteTaxValues.get("crediNoteSgstSum")+invoiceCnDnDetails.getSgstAmount());
			}else if(invoiceCnDnDetails.getCnDnType().equalsIgnoreCase(AspApiConstants.DEBIT_NOTE)){
				noteTaxValues.put("debitNoteTxvalSum",noteTaxValues.get("debitNoteTxvalSum")+invoiceCnDnDetails.getTaxableValue());
				noteTaxValues.put("debitNoteIgstSum",noteTaxValues.get("debitNoteIgstSum")+invoiceCnDnDetails.getIgstAmount());
				noteTaxValues.put("debitNoteCgstSum",noteTaxValues.get("debitNoteCgstSum")+invoiceCnDnDetails.getCgstAmount());
				noteTaxValues.put("debitNoteSgstSum",noteTaxValues.get("debitNoteSgstSum")+invoiceCnDnDetails.getSgstAmount());
			}
			}
		}
		return noteTaxValues;
	}

	public static String getCurrentYearAndCurrentMonth() {
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyMM");
		String year = format.format(date);
		return year;
	}
	
	public static Map<String,Map<String,Double>> convertListToMapForPurchaseEntry(List<PurchaseEntryServiceOrGoodDetails> serviceOrGoodDetails) {
		 Map<String,Map<String,Double>> result=new HashMap<String,Map<String,Double>>();
		
		 Map<String,Double> igstMap=new HashMap<String,Double>();
		 Map<String,Double> cgstMap=new HashMap<String,Double>();
		 Map<String,Double> sgstMap=new HashMap<String,Double>();
		 Map<String,Double> ugstMap=new HashMap<String,Double>();
			for(PurchaseEntryServiceOrGoodDetails service : serviceOrGoodDetails){
				String gstPercent=null;
				if(service.getCategoryType().equals("CATEGORY_WITH_IGST")||service.getCategoryType().equals("CATEGORY_EXPORT_WITH_IGST")){
					gstPercent=String.valueOf(service.getIgstPercentage());
					if(!igstMap.containsKey(gstPercent)){
						igstMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
					}else{
						igstMap.put(gstPercent, GSTNUtil.convertToDouble(igstMap.get(gstPercent)+service.getAmount()));
					}
				}
				if(service.getCategoryType().equals("CATEGORY_WITH_SGST_CSGT")|| service.getCategoryType().equals("CATEGORY_WITH_UGST_CGST")){
					gstPercent=String.valueOf(service.getCgstPercentage());
					if(!cgstMap.containsKey(gstPercent)){
						cgstMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
					}else{
						cgstMap.put(gstPercent, GSTNUtil.convertToDouble(cgstMap.get(gstPercent)+service.getAmount()));
					}
				}
				if(service.getCategoryType().equals("CATEGORY_WITH_SGST_CSGT")){
					gstPercent=String.valueOf(service.getSgstPercentage());
					if(!sgstMap.containsKey(gstPercent)){
						sgstMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
					}else{
						sgstMap.put(gstPercent, GSTNUtil.convertToDouble(sgstMap.get(gstPercent)+service.getAmount()));
					}
				}
				if(service.getCategoryType().equals("CATEGORY_WITH_UGST_CGST")){
					gstPercent=String.valueOf(service.getUgstPercentage());
					if(!ugstMap.containsKey(gstPercent)){
						ugstMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
					}else{
						ugstMap.put(gstPercent, GSTNUtil.convertToDouble(ugstMap.get(gstPercent)+service.getAmount()));
					}
				}
			}	
	
			result.put("igst", igstMap);
			result.put("cgst", cgstMap);
			result.put("sgst", sgstMap);
			result.put("ugst", ugstMap);
			
		return result;
	}
	
	public static String getMaxOfInvoiceNumber(List<String> dbInvNoList) {
		int maxCount = 0;
		String maxInvNo = null;
		for(String latestInvoiceCount : dbInvNoList){
			System.out.println("NB"+Integer.parseInt(latestInvoiceCount.substring(latestInvoiceCount.lastIndexOf("/") + 1)));
			int currentCount = Integer.parseInt(latestInvoiceCount.substring(latestInvoiceCount.lastIndexOf("/") + 1));
			if(currentCount > maxCount ){
				maxCount = currentCount;
				maxInvNo = latestInvoiceCount;
			}
		}
		return maxInvNo ;
		
	}
	
	
	public static String getMaxOfDocumentNumber(List<String> dbdocumentList) {
		int maxCount = 0;
		String maxDocNo = null;
		for(String latestdbdocumentListCount : dbdocumentList){
			//System.out.println(Integer.parseInt(latestInvoiceCount.substring(6)));
			int currentCount = Integer.parseInt(latestdbdocumentListCount.substring(12));
			if(currentCount > maxCount ){
				maxCount = currentCount;
				maxDocNo = latestdbdocumentListCount;
			}
		}
		return maxDocNo ;
		
	}
	
	public static String getMaxOfCNDNInvoiceNumber(List<String> dbInvNoList) {
		int maxCount = 0;
		String maxInvNo = null;
		for(String latestInvoiceCount : dbInvNoList){
			//System.out.println(Integer.parseInt(latestInvoiceCount.substring(6)));
			int currentCount = Integer.parseInt(latestInvoiceCount.substring(7));
			if(currentCount > maxCount ){
				maxCount = currentCount;
				maxInvNo = latestInvoiceCount;
			}
		}
		return maxInvNo ;
		
	}
	
	public static String getDocumentType(String invoiceDocType){
		String documentType = null;
        if(GSTNConstants.DOCUMENT_TYPE_INVOICE.equals(invoiceDocType)){
       	 	documentType = "Invoice";
        }else if(GSTNConstants.DOCUMENT_TYPE_BILL_OF_SUPPLY.equals(invoiceDocType)){
       	 	documentType = "Bill Of Supply";
        }else if(GSTNConstants.DOCUMENT_TYPE_INVOICE_CUM_BILL_OF_SUPPLY.equals(invoiceDocType)){
       	 	documentType = "Invoice cum Bill Of Supply";
        }
		return documentType;
	}
	
	public static boolean isNullOrEmpty(Object object){
		boolean isNullOrEmpty=false;
		if(null==object){
			isNullOrEmpty=true;
		}else if(String.valueOf(object).equals("")){
			isNullOrEmpty=true;
		}
		return isNullOrEmpty;
	}

	public static String getDateAsPerTallyFormat1(Timestamp invoiceDate) {
		Date dateRef = new Date();
		dateRef.setTime(invoiceDate.getTime());
		String formattedDate = new SimpleDateFormat("yyyyMMdd").format(dateRef);
		return formattedDate;
	}

	public static String getDateAsPerTallyFormat2(Timestamp invoiceDate) {
		Date dateRef = new Date();
		dateRef.setTime(invoiceDate.getTime());
		String formattedDate = new SimpleDateFormat("dd-MMM-yyyy").format(dateRef)+" at "+new SimpleDateFormat("HH:mm").format(dateRef);
		return formattedDate;
	}

	public static Document convertStringToDocument(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        try  
        {  
            builder = factory.newDocumentBuilder();  
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
            return doc;
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        return null;
	}
	
	public static String getMonthLimitDate(String month,String year,String format,boolean isLastDate) throws Exception
	{
		Calendar cal = Calendar.getInstance();
		int date=01;
		cal.set(Integer.parseInt(year),Integer.parseInt(month)-1,date);
		
		if(isLastDate){
			date=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		cal.set(Integer.parseInt(year),Integer.parseInt(month)-1,date);
		
		
	    java.util.Date utilDate =cal.getTime();
	    String startdate= new SimpleDateFormat(format).format(utilDate);
        return startdate;
	}
	
	
	public static void main(String[] args) throws Exception {
		System.out.println(getMonthLimitDate("02", "2018", "dd-MM-yyyy", true));
		convertCurrentDateToddMMYYYY();
	}
public static List<String> getDropdownYears(int Currentyear,int dropdownyears) {
		 List<String>years = new ArrayList<String>();
		  for(int i = 0; i <= dropdownyears ; i++)
		   {
			  years.add(Integer.toString(Currentyear));
		     Currentyear=Currentyear-1; 
		 
		   } 
		return years;
	}
	
	
	
	public static List<Month> getMonths() {
		 List<Month>months = new ArrayList<Month>();
		 months.add(new Month("All","All"));
		 months.add(new Month("01","January"));
		 months.add(new Month("02","February"));
		 months.add(new Month("03","March"));
		 months.add(new Month("04","April"));
		 months.add(new Month("05","May"));
		 months.add(new Month("06","June"));
		 months.add(new Month("07","July"));
		 months.add(new Month("08","August"));
		 months.add(new Month("09","September"));
		 months.add(new Month("10","October"));
		 months.add(new Month("11","November"));
		 months.add(new Month("12","December"));
		 
		 return months;
		                      
	}

	/**
	 * This method will return file extension else it will return blank string
	 * 
	 * @param file
	 * @return File extension if file exist
	 * @throws FileNotFoundException
	 */
	public static String getFileExtension(File file)throws FileNotFoundException{
		String extension="";
		if(file.exists()){
			String filePath=file.getPath();
			extension=filePath.substring(filePath.lastIndexOf(".")+1);
		}else{
			throw new FileNotFoundException("File not found on "+file.getPath());
		}
		return extension;
	}
	
	public static BigDecimal getConversionfromobjectToBigdecimal(Object o){
		
		DecimalFormat df = new DecimalFormat("#.##");
		
		BigDecimal value=new BigDecimal(df.format(Double.parseDouble(o!=null? String.valueOf(o):"0")));
		return value;
		
	}
	

	public static Map<String, Object> showErrMsg(String statusCode, String errMsg, String status){
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put(GSTNConstants.ERRORCODE, statusCode);
		responseMap.put(GSTNConstants.ERROR, true);
		responseMap.put(GSTNConstants.STATUS, status);
		responseMap.put(GSTNConstants.MESSAGE, errMsg);
		return responseMap;
	}

	public static String getInvoiceDocumentType(String invoiceDocumentType) {
		String docType = "invoice,billOfSupply,rcInvoice,eComInvoice,eComBillOfSupply";
		 switch (invoiceDocumentType) { 
	        case "invoice": 
	        	docType = "Invoice"; 
	            break; 
	        case "billOfSupply": 
	        	docType = "Bill Of Supply"; 
	            break; 
	        case "rcInvoice": 
	        	docType = "Reverse Charge Invoice"; 
	            break; 
	        case "eComInvoice": 
	        	docType = "e-Commerce Invoice"; 
	            break; 
	        case "eComBillOfSupply": 
	        	docType = "e-Commerce Bill Of Supply"; 
	            break; 
	        
	        default: 
	        	docType = "Invoice"; 
	            break; 
	        }
		return docType;
	}
	
	public static String getServiceIdsInString(List<Integer> removeItemsList) {
		StringBuffer sb = new StringBuffer();
		String idsInString = null;
		for(Integer item : removeItemsList){
			sb = sb.append(item+",");
		}
		idsInString = sb.toString();
		return idsInString.substring(0,idsInString.length()-1);
	}
	
	public static String convertStringDateToOtherStringFormat(String dateStr,String format1,String format2) {
		DateFormat srcDf = new SimpleDateFormat(format1);
		Date date = null;
		try {
			date = srcDf.parse(dateStr);
			DateFormat destDf = new SimpleDateFormat(format2);
			dateStr = destDf.format(date);
			//System.out.println("Converted date is : " + dateStr);
		} catch (ParseException e) {
			System.out.println(e);
		}
	

		return dateStr;
	}
	
}
