/**
 * 
 */
package com.reliance.gstn.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.model.AspUserDetails;
import com.reliance.gstn.model.Gstr1OtpResponseEntity;
import com.reliance.gstn.model.GstrUploadDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.PayloadCnDnDetails;
import com.reliance.gstn.model.UnitOfMeasurement;
import com.reliance.gstn.service.GenerateInvoiceService;
import com.reliance.gstn.service.GenericService;
import com.reliance.gstn.service.GstinValidationService;
import com.reliance.gstn.service.UnitOfMeasurementService;
import com.reliance.gstn.service.UploadAspService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.service.impl.UploadAspServiceImpl;
import com.reliance.gstn.util.AspApiConstants;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;
import com.reliance.gstn.util.UOMUtil;
import com.reliance.gstn.util.UploadJIOGSTConstant;

/**
 * @author Nikesh.Bansod
 *
 */
@Controller
public class UploadAspController {
	
	private static final Logger logger = Logger.getLogger(UploadAspController.class);
	
	@Autowired
	private UploadAspService uploadAspService;
	
	@Autowired
	private UserMasterService userMasterService;
	
	@Autowired
	GenerateInvoiceService generateInvoiceService;
	
	@Autowired
	private GenericService genericService;
	
	@Autowired
	GstinValidationService gstinValidationService;

	@Autowired
	public UnitOfMeasurementService unitOfMeasurementService;

	@Value("${GST_USER_LOGIN_SUCESS}")
	private String gstUserLoginSuccessful;
	
	
	@Value("${GST_UPLOAD_USER_LOGIN_FAILURE}")
	private String gstUploadUserLoginFailure;

	@Value("${invalid_setting_time_hour}")
	private int timeInHour;
	
	@Value("${invalid_setting_time_minute}")
	private int timeInMinute;
	
	@Value("${sessionvalidationtime}")
	private int sessionvalidationtime;
	
	@Value("${reauthforsessionvalidationtime}")
	private int reauthforsessionvalidationtime;
	
	
	@ModelAttribute("aspUserDetail")
	public AspUserDetails construct(){
		return new AspUserDetails();
	}
	
	
	@RequestMapping(value = "/aspMasters", method = RequestMethod.GET)
	public String getASPMasterPage(Model model) {
		return PageRedirectConstants.ASP_HOME_PAGE;
	}
	

	
	@RequestMapping(value = "/getJioGstLoginPage", method = RequestMethod.GET)
	public String getJioGstLoginPage(Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");


		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		model.addAttribute("loginMaster", loginMaster);
		
		logger.info("Exit");
		return PageRedirectConstants.UPLOAD_JIO_GST_PAGE;
	}
	
	@RequestMapping(value="/getJioGstLoginPage",method=RequestMethod.POST)
	public String validateJioGstLogin(@Valid @ModelAttribute("aspUserDetail") AspUserDetails aspUserDetails, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.LOGIN_JIO_GST_PAGE;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		String response = null;
		try {
				model.addAttribute("loginMaster", loginMaster);
				model.addAttribute("aspUserDetailObj", aspUserDetails);
				if (!result.hasErrors()){
					aspUserDetails.setReferenceId(loginMaster.getuId());
					
					response = uploadAspService.getGstUser(aspUserDetails);
					if(response.equals(GSTNConstants.SUCCESS)){
					model.addAttribute(GSTNConstants.RESPONSE, gstUserLoginSuccessful);
					pageRedirect = PageRedirectConstants.UPLOAD_JIO_GST_PAGE;
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, gstUploadUserLoginFailure);
					pageRedirect = PageRedirectConstants.LOGIN_JIO_GST_PAGE;
				}
			}else{
				model.addAttribute(GSTNConstants.RESPONSE, gstUploadUserLoginFailure);
				pageRedirect = PageRedirectConstants.LOGIN_JIO_GST_PAGE;
				
			}
		} catch (Exception e) {			
			logger.error("Error in:",e);
			model.addAttribute(GSTNConstants.RESPONSE, gstUploadUserLoginFailure);
			pageRedirect = PageRedirectConstants.LOGIN_JIO_GST_PAGE;
		}
		logger.info("Exit");
		return  pageRedirect;
		
	}
	
	
	@RequestMapping(value="/getAspUserListByUid",method=RequestMethod.POST)
	public @ResponseBody String getAspUserListByUid(@RequestParam("panNo") Integer panNo, HttpServletRequest request) {
		logger.info("Entry");
		List<AspUserDetails> aspUserDetailList = new ArrayList<AspUserDetails>();
		boolean aspUserExist = false;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		try {
			aspUserDetailList = uploadAspService.getAspUserListByUid(loginMaster.getuId());
			if(!aspUserDetailList.isEmpty() ){
				aspUserExist = true;
			}
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(aspUserExist);
	}
	
	@RequestMapping(value = "/getUploadPage", method = RequestMethod.GET)
	public String uploadPage(Model model) {
		
		return PageRedirectConstants.UPLOAD_JIO_GST_PAGE;
	}
	
		
	@RequestMapping(value = "/uploadInvoicesManual", method = RequestMethod.POST)
	public String uploadInvoicesManual(Model model,HttpServletRequest request) {
		
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		String gstinResponse = GSTNConstants.FAILURE;
		String gstinId=request.getParameter("gstinId");
		
		String isError = "";
		String isActive = "";
		String financialPeriod=request.getParameter("financialPeriod");
		String fpMonth = financialPeriod.substring(0, 2);
		String fpYear = financialPeriod.substring(2, 6);
		String fpMonth2=GSTNUtil.theMonth(Integer.parseInt(fpMonth)-1);
		/*String message = "We are uploading data for "+fpMonth +" "+fpYear+" for "+gstinId+" into JioGST. It will take few minutes. ";
				+ " Please visit the History tab to check the status of this upload. "
				+ " After the status becomes ‘Success’ for this upload, you can visit JioGST.com to view the data"*/
		String message = "Data uploaded successfully for the period "+fpMonth2 +" "+fpYear+" for "+gstinId+" to JioGST. ";
		String errorMessage = "Selected GSTIN is not valid. Please Update your GSTIN in GSTIN master and then try to upload the Data";
		
		try {
						
			//Logic for loading UOM and handling synchronization
			if (!UOMUtil.isMapLoaded()) {
				List<UnitOfMeasurement> unitOfMeasurementList = (List<UnitOfMeasurement>) unitOfMeasurementService.getUnitOfMeasurement();
				UOMUtil.setUOMMap(unitOfMeasurementList);
			}
			List<String> gstinList = new ArrayList<String>();
		
			gstinList.add("09ASDCL1456L1ZK");
			gstinList.add("15ASDCL1456L1ZA");
			gstinList.add("27ASDCL1456L2ZS");
			gstinList.add("27ASDCL1456LZZZ");
			gstinList.add("27GSPMH6171G1ZG");
			
			if(gstinList.contains(gstinId)) { 
				String response = uploadAspService.uploadInvoiceForGSTR1NEW(loginMaster,gstinId,financialPeriod);
				if(response.equalsIgnoreCase("Success") || response.equalsIgnoreCase("emptyList")){
					model.addAttribute("apiResponse",response);
					model.addAttribute("successMsg", message);
				} else{
					model.addAttribute("apiResponse","error");
				}
			}else {
			gstinResponse = gstinValidationService.isValidGstin(gstinId);
			Gson gson = new Gson();
			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap = (Map<String, Object>)gson.fromJson(gstinResponse.toString(), responseMap.getClass());
			isError = (String) responseMap.get("err_cd");
			isActive = (String) responseMap.get("sts");
			
			if(isError != null && !isError.isEmpty()){
				String status = "SUCCESS";
				model.addAttribute("apiResponse",status);
				model.addAttribute("successMsg", errorMessage);
				
			} else if (!isActive.equalsIgnoreCase("Active")){
				
				String status = "SUCCESS";
				model.addAttribute("apiResponse",status);
				model.addAttribute("successMsg", errorMessage);
				
			} else {
			String response = uploadAspService.uploadInvoiceForGSTR1NEW(loginMaster,gstinId,financialPeriod);
			if(response.equalsIgnoreCase("Success") || response.equalsIgnoreCase("emptyList")){
				model.addAttribute("apiResponse",response);
				model.addAttribute("successMsg", message);
			} else if(response.equalsIgnoreCase("alreadysubmitted")){
				model.addAttribute("apiResponse",response);
			}else{
				model.addAttribute("apiResponse","error");
			}
			}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("L0_gstin",gstinId);
		model.addAttribute("L0_fp",financialPeriod);
		model.addAttribute("GSTR_type","GSTR1");
		model.addAttribute("gobacktoasp", "gobacktoasp");
		return PageRedirectConstants.UPLOAD_JIO_GST_PAGE;
	}
	
	
	/*@RequestMapping(value="/getUploadFP",method=RequestMethod.POST)
	public @ResponseBody String getUploadFP(HttpServletRequest request) {
		logger.info("Entry");
		String response = "";
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		try {
			List<String> fp = new ArrayList<String>();
			fp = uploadAspService.getUploadFP();
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(response);
	}*/
	
	@RequestMapping(value="/getFpMonthsArray", method=RequestMethod.POST )
	public @ResponseBody String getFPeriod(HttpServletRequest request){
		Calendar c = new GregorianCalendar();
		Map<String, String> monthArray = new LinkedHashMap<String, String>();
	//	Map<String, String> monthArray = new HashMap<String, String>();
		boolean var = false;
		c.setTime(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("MMYYYY");
		SimpleDateFormat sdfmy = new SimpleDateFormat("MMMM YYYY");
		SimpleDateFormat sdfm = new SimpleDateFormat("MM");
		SimpleDateFormat sdfy = new SimpleDateFormat("YYYY");
		monthArray.put(sdfmy.format(c.getTime()), sdf.format(c.getTime()));
		
		while(var == false) {
			if((sdfm.format(c.getTime()).equalsIgnoreCase("08")) && (sdfy.format(c.getTime()).equalsIgnoreCase("2017"))){
				var = true;
			}
				c.add(Calendar.MONTH, -1);
				monthArray.put(sdfmy.format(c.getTime()), sdf.format(c.getTime()));
			};
			
			return new Gson().toJson(monthArray);
		
	}
	
	@RequestMapping(value = "/getUploadSetting", method = RequestMethod.GET)
	public String getUploadSetting(Model model) {
		return PageRedirectConstants.UPLOAD_JIO_GST_SETTING;
	}
	
	@RequestMapping(value = "/uploadInvoicesSetting", method = RequestMethod.POST)
	public String uploadInvoicesSetting(Model model,HttpServletRequest request) {
		
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		try {
			String response="";
			Date time = new Date();
			int hours = time.getHours();
			int minutes = time.getMinutes();
		
			if(hours == timeInHour && minutes >= timeInMinute){
				model.addAttribute("apiResponse","INVALID");
			} else { 
				String gstinId=request.getParameter("gstinId");
				String uploadType=request.getParameter("uploadType");
				boolean isMappingValid = false;
				isMappingValid = genericService.validateGstin(gstinId,loginMaster.getuId());
				if(isMappingValid){
					
					response = uploadAspService.updateUploadSetting(loginMaster,gstinId,uploadType);
					
				}else{
					response= GSTNConstants.ACCESSVIOLATION;
				}
				if(response.equalsIgnoreCase(GSTNConstants.SUCCESS)){
					model.addAttribute("apiResponse",response);
				}else if(response.equalsIgnoreCase(GSTNConstants.FAILURE)){
					model.addAttribute("apiResponse",response);
				}else if(response.equalsIgnoreCase(GSTNConstants.ACCESSVIOLATION)){
					model.addAttribute("apiResponse",response);
				}
			} 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return PageRedirectConstants.UPLOAD_JIO_GST_SETTING;
	}
	
	@RequestMapping(value = "/getHistory", method = RequestMethod.GET)
	public String historyPage(Model model) {
		
		return PageRedirectConstants.HISTORY_JIO_GST_PAGE;
	}
	
	
	@RequestMapping(value="/uploadHistory", method=RequestMethod.POST)
	public @ResponseBody String uploadHistory(@RequestParam("gstin") String gstin, HttpServletRequest request ){
		logger.info("Entry");	
		List<GstrUploadDetails> lst = new ArrayList<GstrUploadDetails>();
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		try{
			lst = uploadAspService.getUploadHistory(loginMaster.getuId().toString(), gstin);
			
		} catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(lst);
	}			
	
	@RequestMapping(value="/lastUploadedGstinData", method= RequestMethod.POST)
	public @ResponseBody String lastUploadedGstinData(@RequestParam("gstin") String gstin,@RequestParam("financialPeriod") String financialPeriod, HttpServletRequest request){
		
		logger.info("Entry");	
		GstrUploadDetails gstrUploadDetails = new GstrUploadDetails();
		try{
			gstrUploadDetails = uploadAspService.getLastUploadedGstinData(gstin,financialPeriod);
			
		} catch(Exception e){
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		
		return new Gson().toJson(gstrUploadDetails);
	}
	
	@RequestMapping(value = "/getL0ResponsePage", method = RequestMethod.POST)
	public String getL0ResponsePage( Model model, HttpServletRequest request){
		logger.info("Entry");
		
		 String gstin=request.getParameter("gstinId");
		
		 String fp=request.getParameter("financialPeriod");
		 String gstrtype=request.getParameter("gstrtype");
		 String monthofFP=fp.substring(0, 2);
		 String fpYear = fp.substring(2, 6);
		 String payload_mode="Draft";
		 System.out.println("month=="+monthofFP);
		
	
		model.addAttribute("gstin", gstin);
		model.addAttribute("fp",fp);
		model.addAttribute("gstrtype",gstrtype);
		model.addAttribute("monthofFP",monthofFP);
		model.addAttribute("fpYear",fpYear);
		model.addAttribute("payload_mode",payload_mode);
		logger.info("Exit");
		return PageRedirectConstants.VIEW_LO_PAYLOAD_DATA;
	}
	
	@RequestMapping(value = "/gobacktoasphome", method = RequestMethod.POST)
	public String goBackToAspHome(@RequestParam("L0_gstin") String L0_gstin,@RequestParam("L0_fp") String L0_fp,@RequestParam("GSTR_type") String GSTR_type,HttpServletRequest request,Model model)
	{
		logger.info("Entry");
	       
	       model.addAttribute("L0_gstin", L0_gstin);
	       model.addAttribute("L0_fp", L0_fp);
	       model.addAttribute("GSTR_type", GSTR_type);
	       model.addAttribute("gobacktoasp", "gobacktoasp");
	logger.info("Exit");
		return PageRedirectConstants.UPLOAD_JIO_GST_PAGE;
		
	}
	
	@RequestMapping(value = "/gobacktoasphomeforotperror", method = RequestMethod.POST)
	public String goBackToAspHome(@RequestParam("L0_gstin") String L0_gstin,@RequestParam("L0_fp") String L0_fp,@RequestParam("GSTR_type") String GSTR_type,@RequestParam("message") String message,HttpServletRequest request,Model model)
	{
		logger.info("Entry");
		 String status = "SUCCESS";
		   model.addAttribute("apiResponse",status);
		   model.addAttribute("successMsg",message);
	       model.addAttribute("L0_gstin", L0_gstin);
	       model.addAttribute("L0_fp", L0_fp);
	       model.addAttribute("GSTR_type", GSTR_type);
	       model.addAttribute("gobacktoasp", "gobacktoasp");
	logger.info("Exit");
		return PageRedirectConstants.UPLOAD_JIO_GST_PAGE;
		
	}
	
	@RequestMapping(value = "/getgstr1l0response", method = RequestMethod.POST)
	public @ResponseBody String getGstr1LoResponse(@RequestParam("gstin") String gstin,@RequestParam("fp") String fp,Model model, HttpServletRequest request){
		logger.info("Entry");
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		
		Map<String,Object>responseMap=new HashMap<String, Object>();
		String response= null;
		try{
			response = uploadAspService.getGstr1LoResponse(loginMaster, gstin, fp);
			Gson gson = new Gson();
			responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
			String errorMsg=(String) responseMap.get("errorMsg");
			/*if(errorMsg.equalsIgnoreCase("System Error Occured"))*/
		
			if(errorMsg != null && errorMsg.equalsIgnoreCase("System Error Occured"))
			{
				response="";
			}
			
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		return new Gson().toJson(response);
	}
	
	@RequestMapping(value = "/getgstr1l2response", method = RequestMethod.POST)
	public @ResponseBody String getGstr1L2Response( HttpServletRequest request, Model model){
		logger.info("Entry");
		String response= null;
		String gstin=request.getParameter("gstinId");
		String section="b2b";	
		String fp=request.getParameter("financialPeriod");
		String recOffset="0";
		String noOfRecords="20";
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		try{
			response = uploadAspService.getGstr1L2Response(loginMaster,gstin,fp,section,recOffset,noOfRecords);
			
		} catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		model.addAttribute("gstin", gstin);
		model.addAttribute("fp",fp);
		
		return response;
	}
	
	//CHECK SESSION AFTER CLICK ON SAVE TO GSTN
	@RequestMapping(value = "/getgstr1otpresponse", method = RequestMethod.POST)
	public @ResponseBody String getgstr1otpresponse( HttpServletRequest request, Model model){
		logger.info("Entry");
        String statusofaction=AspApiConstants.GSTR1_FIRSTTIMELOGINUSER;
		String response= "";
		String tk_gen_time="";
		Date d1 = null;
		String gstin=request.getParameter("gstin");
		String fp=request.getParameter("fp");
		/*String gstin="27GSPMH0782G1ZJ";
		String fp="072017";*/
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		try{
			List<Gstr1OtpResponseEntity> responseEntityMap = new ArrayList<>();
			responseEntityMap=uploadAspService.getGstr1OtpResponse(gstin);
			if(responseEntityMap.isEmpty()){
				     statusofaction=AspApiConstants.GSTR1_FIRSTTIMELOGINUSER;
			         response = uploadAspService.getGstr1OTPResponse(loginMaster,gstin,fp,statusofaction);
			 }else{
			        tk_gen_time=responseEntityMap.get(0).getTk_gen_time();
			        DateFormat  format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
			        d1 = (Date)format.parse(tk_gen_time);
			        Date date= new Date();
			        long time = date.getTime();
			        long diff = time-d1.getTime();
			        long diffMinutes=TimeUnit.MILLISECONDS.toMinutes(diff);  
				        
				    if(diffMinutes >= reauthforsessionvalidationtime &&  diffMinutes<=sessionvalidationtime ) {
				    	statusofaction=AspApiConstants.GSTR1_INVALIDSESSION;
				        response=uploadAspService.getGstr1OTPResponse(loginMaster,gstin,fp,statusofaction);
				     }else if(diffMinutes > sessionvalidationtime){
				    	 statusofaction=AspApiConstants.GSTR1_EXPIRED;
					    	response=uploadAspService.getGstr1OTPResponse(loginMaster,gstin,fp,statusofaction);
				     }else{
					     statusofaction=AspApiConstants.GSTR1_VALIDSESSION;
				      }
			   }
			
		} catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(statusofaction);
	}

	//SAVE TO GSTN
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value = "/responsesavetogstn", method = RequestMethod.POST)
	public String responsesavetogstn( HttpServletRequest request, Model model){
		logger.info("Entry");
		String response= "";
		String status_cd="";
		String databaseResponse="";
		String status_cdforError="";
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		String pageRedirect = PageRedirectConstants.UPLOAD_JIO_GST_PAGE;
        String otp=request.getParameter("otp");
       /* String gstin="27GSPMH0782G1ZJ";
		String fp="072017";*/
		String gstin=request.getParameter("L0_gstin");
	    String fp=request.getParameter("L0_fp");
		String auth_token="";
		String app_key="";
		String sek="";
        String gt="200000";
        String cur_gt="0";
        String err_cd="";
        String sname=loginMaster.getFirmName();
        String fpMonth = fp.substring(0, 2);
   		String fpYear = fp.substring(2, 6);
   		Map<String, Object> responseMap = new HashMap<String, Object>();
   		Map<String,String> saveGstnResponse=new HashMap<String,String>();
   		String ref_id="";
   		String serviceType="";
   		String UploadType = "SaveToGSTN";
   		String fpMonth2=GSTNUtil.theMonth(Integer.parseInt(fpMonth)-1);
   	    Map<String,String> result1=new HashMap<>();
   	    String gstnUploadDetailsDatabaseResponse = "";
   		List<Gstr1OtpResponseEntity> responseEntityMap = new ArrayList<>();
        String message=  "Your data is getting Saved to GSTN for "+fpMonth2+" "+fpYear+ "-" +gstin+" You can view the saved data in Step 3- Submit data to GSTN";

		
		try{
			if(otp != null){
				result1 = uploadAspService.responsesavetogstn(loginMaster,gstin,otp,fp,gt,cur_gt,sname);
				response=result1.get("response");
					
					Gson gson = new Gson();
					responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
					String errorMsg=(String) responseMap.get("errorMsg");
					String error_code=(String) responseMap.get("error_code");
					if(error_code != null && error_code.equalsIgnoreCase("AUTH403")){
						/*message=(String) responseMap.get("error_desc");*/
						message="Maximum sessions expired.";
					}else if(error_code != null && error_code.equalsIgnoreCase("AUTH4033")){
						message="OTP is incorrect.";
					}else if(error_code != null){
						message=(String) responseMap.get("error_desc");
					}else if(errorMsg !=null){
						message="Something wrong in API response";
					}else{
						Map<String,String> authTokenResponse=new HashMap<String,String>();
						databaseResponse = uploadAspService.addGstrAuthenticationDetails(gstin,fp,authTokenResponse,loginMaster.getuId().toString(),responseMap);
						
						saveGstnResponse=(Map<String, String>) responseMap.get("saveGstnResponse");
						err_cd = (String) saveGstnResponse.get("err_cd");
						if(err_cd == null){
							status_cd = (String) saveGstnResponse.get("status_cd");
						}else{
							message = (String) saveGstnResponse.get("usr_msg");
						}
			        }
			   }else{ 
					responseEntityMap=uploadAspService.getGstr1OtpResponse(gstin);
					app_key=responseEntityMap.get(0).getAppkey();
				    sek=responseEntityMap.get(0).getSek();
				    auth_token=responseEntityMap.get(0).getAuthtoken();
				    result1 = uploadAspService.responsesavetogstnwithoutOtp(loginMaster, gstin,fp, gt, cur_gt, sname,app_key, sek, auth_token);
				    response=result1.get("response");
				    Gson gson = new Gson();
				    responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
				   
					saveGstnResponse=(Map<String, String>) responseMap.get("saveGstnResponse");
					err_cd = (String) saveGstnResponse.get("err_cd");
					if(err_cd == null){
						status_cd = (String) saveGstnResponse.get("status_cd");
					}else{
						message = (String) saveGstnResponse.get("usr_msg");
					}
			}
			        Thread.currentThread().sleep(5000);
			        if(status_cd.equalsIgnoreCase("1")){
			        	ref_id = (String) saveGstnResponse.get("ref_id");
			        	responseEntityMap=uploadAspService.getGstr1OtpResponse(gstin);
						app_key=responseEntityMap.get(0).getAppkey();
					    sek=responseEntityMap.get(0).getSek();
					    auth_token=responseEntityMap.get(0).getAuthtoken();
					    response = uploadAspService.getGstnSaveStatus(AspApiConstants.GSTR1_SERVICE_GSTNSAVERESTATUS,loginMaster,ref_id,app_key,sek,auth_token,gstin,fp);
					    Gson gson = new Gson();
						responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
						Map<String, Object> saveGstnStatusResponse = new HashMap<String, Object>();
						saveGstnStatusResponse= (Map<String, Object>) responseMap.get("saveGstnStatusResponse");
						
						err_cd = (String) saveGstnStatusResponse.get("err_cd");
						/*status_cdforError = (String) saveGstnStatusResponse.get("status_cd");*/
						
						/*if(err_cd.equalsIgnoreCase("010200") && status_cdforError.equalsIgnoreCase("ER")){
							message = "GSTR1 is already submitted for current period";
						}else*/ if(err_cd != null){
							message = (String) saveGstnStatusResponse.get("usr_act");
						}else{
							
							gstnUploadDetailsDatabaseResponse = uploadAspService.addDataTogstnUploadDetailsDatabase(loginMaster, gstin,
									fpMonth, fpYear, fp, (String) result1.get("txnid"), (String) result1.get("ref_id"), UploadType);
							
							
							
							List<InvoiceDetails> invoiceList = uploadAspService.getInvoiceDetailsByGstinForCheck(gstin, fp);
							uploadAspService.setUploadToJiogstStatus(gstin, fp, /* UploadDate */response,
									UploadType/* ,invoiceid */, invoiceList);
							
							
							List<PayloadCnDnDetails> cndnList= uploadAspService.getCndnDetailsByGstinForCheck(gstin, fp);
							uploadAspService.setUploadToJiogstStatusCndn(gstin, fp,response, UploadType, cndnList);
						}
			        }
				   String status = "SUCCESS";
				    model.addAttribute("apiResponse",status);
				    model.addAttribute("successMsg",message);
				    model.addAttribute("L0_gstin", gstin);
			        model.addAttribute("L0_fp", fp);
			        model.addAttribute("GSTR_type", "GSTR1");
			        model.addAttribute("gobacktoasp", "gobacktoasp");
			        pageRedirect=PageRedirectConstants.UPLOAD_JIO_GST_PAGE;
			
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return pageRedirect;
	}
	
	
	
	@RequestMapping(value = "/getgstrtype", method = RequestMethod.POST)
	public @ResponseBody String getGstrType(HttpServletRequest request)
	{
		Map<String,String>gstrType=new LinkedHashMap<String,String>();
		gstrType.put("GSTR1", "GSTR1");
		gstrType.put("GSTR3B", "GSTR3B");
		return new Gson().toJson(gstrType);
	}
	//GETTING LO RESPONSE PAGE AFTER CLICK ON SUBMIT
	@RequestMapping(value = "/getSubmitToGSTNResponsePage", method = RequestMethod.POST)
	public String getSubmitToGSTNResponsePage( Model model, HttpServletRequest request){
		logger.info("Entry");
		 String gstin=request.getParameter("gstinId");
		 String fp=request.getParameter("financialPeriod");
		/* String gstin="27GSPMH0782G1ZJ";
		 String fp="072017";*/
		 String gstrtype=request.getParameter("gstrtype");
		 String monthofFP=fp.substring(0, 2);
		 String fpYear = fp.substring(2, 6);
		 String payload_mode="submit";
		 System.out.println("month=="+monthofFP);
		
		 logger.info("Exit");
		 model.addAttribute("gstin", gstin);
		 model.addAttribute("fp",fp);
		 model.addAttribute("gstrtype",gstrtype);
		 model.addAttribute("monthofFP",monthofFP);
		 model.addAttribute("fpYear",fpYear);
		 model.addAttribute("payload_mode",payload_mode);
		//model.addAttribute("response",response);
		 return PageRedirectConstants.VIEW_GSTN_L0_PAYLOAD;
	}
	
	//CHECK SESSION AT TIME OF SUBMIT
	@SuppressWarnings("unused")
	@RequestMapping(value = "/getgstr1responsefromGSTN", method = RequestMethod.POST)
	public @ResponseBody String getgstr1responsefromGSTN( HttpServletRequest request, Model model){
		logger.info("Entry");
        String statusofaction=AspApiConstants.GSTR1_FIRSTTIMELOGINUSER;
		String auth_token="";
		String app_key="";
		String sek="";
		String response= "";
		String tk_gen_time="";
		Date d1 = null;
		String gstin=request.getParameter("gstin");
		String fp=request.getParameter("fp");
		/*String gstin="27GSPMH0782G1ZJ";
		String fp="072017";*/
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		
		try{
			List<Gstr1OtpResponseEntity> responseEntityMap = new ArrayList<>();
			responseEntityMap=uploadAspService.getGstr1OtpResponse(gstin);
			
			if(responseEntityMap.isEmpty()){
				statusofaction=AspApiConstants.GSTR1_FIRSTTIMELOGINUSER;
				 
		         response = uploadAspService.getGstr1OTPResponse(loginMaster,gstin,fp,statusofaction);
			}else{
		        app_key=responseEntityMap.get(0).getAppkey();
		        sek=responseEntityMap.get(0).getSek();
		        auth_token=responseEntityMap.get(0).getAuthtoken();
		        tk_gen_time=responseEntityMap.get(0).getTk_gen_time();
		        DateFormat  format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		        d1 = (Date)format.parse(tk_gen_time);
		        Date date= new Date();
		        long time = date.getTime();
		        long diff = time-d1.getTime();
		        
		        long diffMinutes=TimeUnit.MILLISECONDS.toMinutes(diff);
			    if(diffMinutes >= reauthforsessionvalidationtime &&  diffMinutes<=sessionvalidationtime) {
			    	statusofaction=AspApiConstants.GSTR1_INVALIDSESSION;
	        		 response=uploadAspService.getGstr1OTPResponse(loginMaster,gstin,fp,statusofaction);
			    }else if(diffMinutes > sessionvalidationtime ){
			    	statusofaction=AspApiConstants.GSTR1_EXPIRED;
			    	response=uploadAspService.getGstr1OTPResponse(loginMaster,gstin,fp,statusofaction);
			    }else{
				     statusofaction=AspApiConstants.GSTR1_VALIDSESSION;
			    }
		     } 
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(statusofaction);
	}
	// SUBMIT DATA TO GSTN
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value = "/responsesubmittogstn", method = RequestMethod.POST)
	public String responsesumbittogstn( HttpServletRequest request, Model model){
		logger.info("Entry");
		String response= "";
		String status_cd="";
		@SuppressWarnings("unused")
		String databaseResponse="";
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		String pageRedirect = PageRedirectConstants.UPLOAD_JIO_GST_PAGE;
        String otp=request.getParameter("otp");
        /*String gstin="27GSPMH0782G1ZJ";
		String fp="072017";*/
		String gstin=request.getParameter("L0_gstin");
	    String fp=request.getParameter("L0_fp");
		String auth_token="";
		String app_key="";
		String sek="";
        String err_cd="";
        String sname=loginMaster.getFirmName();
        String fpMonth = fp.substring(0, 2);
   		String fpYear = fp.substring(2, 6);
   		String UploadType = "SubmitToGSTN";
   	 Map<String,String> result1=new HashMap<>();
   		Map<String, Object> responseMap = new HashMap<String, Object>();
   	 Map<String,String> saveGstnResponse=new HashMap<String,String>();
   	List<Gstr1OtpResponseEntity> responseEntityMap = new ArrayList<>();
   	
		String fpMonth2=GSTNUtil.theMonth(Integer.parseInt(fpMonth)-1);
        String message= "Your data is getting Submitted to GSTN for "+fpMonth2+" "+fpYear+ "-" +gstin+".";
		
		try{
			
			if(otp != null){
				result1 = uploadAspService.getSubmitToGstnL0OtpResponse(loginMaster,gstin,otp,fp);
					response=result1.get("response");
					
					Gson gson = new Gson();
					responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
					String error_desc=(String) responseMap.get("error_desc");
					if(error_desc != null){
						message=(String) responseMap.get("error_desc");
					}
					else{
						Map<String,String> authTokenResponse=new HashMap<String,String>();
						
						databaseResponse = uploadAspService.addGstrAuthenticationDetails(gstin,fp,authTokenResponse,loginMaster.getuId().toString(),responseMap);
						
						saveGstnResponse=(Map<String, String>) responseMap.get("saveGstnResponse");
						err_cd = (String) saveGstnResponse.get("err_cd");
						if(err_cd == null){
							status_cd = (String) saveGstnResponse.get("status_cd");
						}else{
							message = (String) saveGstnResponse.get("usr_msg");
						}
					}
			}else{
					
					responseEntityMap=uploadAspService.getGstr1OtpResponse(gstin);
					app_key=responseEntityMap.get(0).getAppkey();
				    sek=responseEntityMap.get(0).getSek();
				    auth_token=responseEntityMap.get(0).getAuthtoken();
				    result1 = uploadAspService.responsesubmittogstnwithoutOtp(loginMaster, gstin, fp, sname,app_key, sek, auth_token);
				    response=result1.get("response");
				    
				    Gson gson = new Gson();
				    responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
				   
					saveGstnResponse=(Map<String, String>) responseMap.get("saveGstnResponse");
					
					err_cd = (String) saveGstnResponse.get("err_cd");
					if(err_cd == null){
						status_cd = (String) saveGstnResponse.get("status_cd");
					}else{
						message = (String) saveGstnResponse.get("usr_msg");
					}
			}
			
			
			Thread.currentThread().sleep(5000);
	        if(status_cd.equalsIgnoreCase("1")){
	        	String ref_id = (String) saveGstnResponse.get("ref_id");
	        	responseEntityMap=uploadAspService.getGstr1OtpResponse(gstin);
				app_key=responseEntityMap.get(0).getAppkey();
			    sek=responseEntityMap.get(0).getSek();
			    auth_token=responseEntityMap.get(0).getAuthtoken();
			    response = uploadAspService.getGstnSaveStatus(AspApiConstants.GSTR1_SERVICE_GSTNSUBMITRESTATUS,loginMaster,ref_id,app_key,sek,auth_token,gstin,fp);
			    Gson gson = new Gson();
				responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
				Map<String, Object> saveGstnStatusResponse = new HashMap<String, Object>();
				saveGstnStatusResponse= (Map<String, Object>) responseMap.get("saveGstnStatusResponse");
				
				err_cd = (String) saveGstnStatusResponse.get("err_cd");
				if(err_cd != null){
					message = (String) saveGstnStatusResponse.get("usr_act");
				}
				
				else
				{
					String gstnUploadDetailsDatabaseResponse = uploadAspService.addDataTogstnUploadDetailsDatabase(loginMaster, gstin,
							fpMonth, fpYear, fp, (String) result1.get("txnid"), (String) result1.get("ref_id"), UploadType);
					List<InvoiceDetails> invoiceList = uploadAspService.getInvoiceDetailsByGstinForCheck(gstin, fp);
					uploadAspService.setUploadToJiogstStatus(gstin, fp, /* UploadDate */response,
							UploadType/* ,invoiceid */, invoiceList);
					
					List<PayloadCnDnDetails> cndnList= uploadAspService.getCndnDetailsByGstinForCheck(gstin, fp);
					uploadAspService.setUploadToJiogstStatusCndn(gstin, fp,response, UploadType, cndnList);
					
				}
	        }
				    String status = "SUCCESS";
				    model.addAttribute("apiResponse",status);
				    model.addAttribute("successMsg",message);
				    model.addAttribute("L0_gstin", gstin);
				    model.addAttribute("L0_fp", fp);
				    model.addAttribute("GSTR_type", "GSTR1");
				    model.addAttribute("gobacktoasp", "gobacktoasp");
					pageRedirect=PageRedirectConstants.UPLOAD_JIO_GST_PAGE;
			
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return pageRedirect;
	}
	// FOR GETTING L0 RESPONSE FROM THE GSTN 
	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping(value = "/getgstr1submittogstnl0response", method = RequestMethod.POST)
	public @ResponseBody String getgstr1submittogstnl0response(@RequestParam("gstin") String gstin,@RequestParam("fp") String fp,@RequestParam("otp") String otp,Model model, HttpServletRequest request){
		logger.info("Entry");
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		 String offset="0";
         String limit="100";
		 String response= "";
		 String auth_token="";
		 String app_key="";
		 String sek="";
		 String error_desc1 = null;
		 String error_desc=null;
		 String error_code=null;
		 String errorMsg=null;
		/* 
		String gstin="27GSPMH0782G1ZJ";
		String fp="072017";*/
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String databaseResponse="";
		try{
			if(otp != "")
			{
				response = uploadAspService.getGstnL0ResponsewithOTP(loginMaster,gstin,otp,fp,offset,limit);
				Gson gson = new Gson();
				responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
				errorMsg = (String) responseMap.get("errorMsg");
				Map<String, String> errorl0ResponseMap = new HashMap<String, String>();
				 errorl0ResponseMap=(Map<String, String>) responseMap.get("l0ResponseMap");
				 if(errorl0ResponseMap!= null){
				 error_desc1 = (String) errorl0ResponseMap.get("err_cd");
				 }
				 error_code = (String) responseMap.get("error_code");
				 error_desc = (String) responseMap.get("error_desc");
				Map<String,String> authTokenResponse=new HashMap<String,String>();
				Map<String,String> authTokenResponse1=(Map<String, String>) responseMap.get("authTokenResponse");
				
				
				if(authTokenResponse1 != null){
					databaseResponse = uploadAspService.addGstrAuthenticationDetails(gstin,fp,authTokenResponse,loginMaster.getuId().toString(),responseMap);
				}
				if(error_desc != null && error_code.equalsIgnoreCase("AUTH4033")){
					response="OTP is incorrect.";
			    }else if(error_desc != null && error_code.equalsIgnoreCase("30782")){
			    	response="Validation Failed : otp should be 6 digit integer.";
			    }else if(error_desc != null && error_code.equalsIgnoreCase("AUTH403")){
			    	response="Maximum session allowed for user with this GSP account exceeded.";
			    }/*else if(error_desc1 ==null && error_desc == null && error_code == null ){
			    }*/else if(error_desc1 != null ){
			    	response="Please select the preference or Unauthorized User!";
			    	/*response=(String) errorl0ResponseMap.get("usr_msg");*/
			    }else if(errorMsg != null){
			    	response="Something went wrong in API response";
			    }else if(error_code != null || error_desc != null){
			    	response="something went wrong in data";
			    }
			}
			else{
			
			List<Gstr1OtpResponseEntity> responseEntityMap = new ArrayList<>();
			responseEntityMap=uploadAspService.getGstr1OtpResponse(gstin);
			
	        app_key=responseEntityMap.get(0).getAppkey();
	        sek=responseEntityMap.get(0).getSek();
	        auth_token=responseEntityMap.get(0).getAuthtoken();
			
			response = uploadAspService.getGstnL0Response(loginMaster,gstin,otp,fp,offset,limit,app_key,sek,auth_token);
			Gson gson = new Gson();
			responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
			Map<String, String> errorl0ResponseMap = new HashMap<String, String>();
			 errorl0ResponseMap=(Map<String, String>) responseMap.get("l0ResponseMap");
			 if(errorl0ResponseMap != null){
			error_desc1 = (String) errorl0ResponseMap.get("err_cd");
			
			 }
			 if(error_desc1 != null){
		    	response="Please select the preference Or Unauthorized User!";
		    }
		}
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		return new Gson().toJson(response);
	}
	
	@RequestMapping(value="/getuploadtojiogststatus", method= RequestMethod.POST)
	public @ResponseBody String getuploadtojiogststatus(@RequestParam("gstin") String gstin,@RequestParam("financialPeriod") String financialPeriod, HttpServletRequest request){
		
		logger.info("Entry");	
		GstrUploadDetails gstrUploadDetails = new GstrUploadDetails();
		try{
			gstrUploadDetails = uploadAspService.getLastUploadedGstinData(gstin,financialPeriod);
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		
		return new Gson().toJson(gstrUploadDetails);
	}
	
	
	//1st PAGE FOR FILING TO GSTN 
	@RequestMapping(value = "/getfiletogstnresponsepage", method = RequestMethod.POST)
	public String getFileToGSTNResponsePage( Model model, HttpServletRequest request){
		logger.info("Entry");
		 String gstin=request.getParameter("gstinId");
		 String fp=request.getParameter("financialPeriod");
	/*	 String gstin="27GSPMH0782G1ZJ";
		 String fp="072017";*/
		 String gstrtype=request.getParameter("gstrtype");
		 String monthofFP=fp.substring(0, 2);
		 String fpYear = fp.substring(2, 6);
		 String payload_mode="file";
		 System.out.println("month=="+monthofFP);
		
		 logger.info("Exit");
		 model.addAttribute("gstin", gstin);
		 model.addAttribute("fp",fp);
		 model.addAttribute("gstrtype",gstrtype);
		 model.addAttribute("monthofFP",monthofFP);
		 model.addAttribute("fpYear",fpYear);
		 model.addAttribute("payload_mode",payload_mode);
		//model.addAttribute("response",response);
		 return PageRedirectConstants.VIEW_FILE_TO_GSTN_PAYLOAD;
	}
	
	
	//REDIRECT TO ASP HOME PAGE IF USER ENTERS WRONG OTP 
	
	@RequestMapping(value = "/gobacktoasphomeforotperrorfromfile", method = RequestMethod.POST)
	public String goBackToAspHomeFromFile(@RequestParam("L0_gstin") String L0_gstin,@RequestParam("L0_fp") String L0_fp,@RequestParam("GSTR_type") String GSTR_type,@RequestParam("message") String message,HttpServletRequest request,Model model)
	{
		logger.info("Entry");
		 String status = "SUCCESS";
		   model.addAttribute("apiResponse",status);
		   model.addAttribute("successMsg",message);
	       model.addAttribute("L0_gstin", L0_gstin);
	       model.addAttribute("L0_fp", L0_fp);
	       model.addAttribute("GSTR_type", GSTR_type);
	       model.addAttribute("gobacktoasp", "gobacktoasp");
	logger.info("Exit");
		return PageRedirectConstants.UPLOAD_JIO_GST_PAGE;
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/otpforresponsefiletogstn", method = RequestMethod.POST)
	public @ResponseBody String OtpForResponseFileToGstn( HttpServletRequest request, Model model){
		logger.info("Entry");
		String response= "";
		String status_cd="";
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
        /*String gstin="27GSPMH6151G1ZK";
		String fp="072017";*/
		String gstin=request.getParameter("gstin");
	    String fp=request.getParameter("fp");
   		Map<String, String> responseMap = new HashMap<String, String>();
		try{
					response = uploadAspService.otpResponseForFileToGstn(loginMaster,gstin,fp);
					Gson gson = new Gson();
					responseMap = (Map<String, String>)gson.fromJson(response.toString(), responseMap.getClass());
					 status_cd= String.valueOf(responseMap.get("status_cd"));
					 
					if(status_cd.equalsIgnoreCase("1")){
						response="success";
					}else{
						response="fail";
					}
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(response);
	}
	
	
	
	// FILE DATA TO GSTN
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/responsefiletogstn", method = RequestMethod.POST)
		public String responseFiletoGstn( HttpServletRequest request, Model model){
			logger.info("Entry");
			String response= "";
			@SuppressWarnings("unused")
			String status_cd="";
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
			String pageRedirect = PageRedirectConstants.UPLOAD_JIO_GST_PAGE;
	        String otp=request.getParameter("otp");
	        /*String gstin="27GSPMH6151G1ZK";
			String fp="072017";*/
			String gstin=request.getParameter("L0_gstin");
		    String fp=request.getParameter("L0_fp");
	        String err_cd="";
	        String code="";
	        String UploadType="fileToGstn";
	        String fpMonth = fp.substring(0, 2);
	   		String fpYear = fp.substring(2, 6);
	   		Map<String, Object> responseMap = new HashMap<String, Object>();
	   		
	   		
			String fpMonth2=GSTNUtil.theMonth(Integer.parseInt(fpMonth)-1);
	        String message="Data filed successfully for financial period "+fpMonth2+""+fpYear+" for GSTIN "+gstin+" to GSTN";
			try{
				if(otp != null){
						response = uploadAspService.responseFileToGstn(loginMaster,gstin,otp,fp);
						Gson gson = new Gson();
						responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
						err_cd=(String) responseMap.get("err_cd");
						code=(String) responseMap.get("code");
						if(err_cd != null){
							message=(String) responseMap.get("usr_msg");
						}else if(code != null){
							message=(String) responseMap.get("messages");
						}else{
							status_cd = (String) responseMap.get("status_cd");
							
							if(status_cd.equalsIgnoreCase("1")){
							List<InvoiceDetails> invoiceList = uploadAspService.getInvoiceDetailsByGstinForCheck(gstin, fp);
							uploadAspService.setUploadToJiogstStatus(gstin, fp, /* UploadDate */response,
									UploadType/* ,invoiceid */, invoiceList);
							
							List<PayloadCnDnDetails> cndnList= uploadAspService.getCndnDetailsByGstinForCheck(gstin, fp);
							uploadAspService.setUploadToJiogstStatusCndn(gstin, fp,response, UploadType, cndnList);
							
							}
							else
							{
								message="Error while filing data to Gstn";
							}
							
						}
				}
					    String status = "SUCCESS";
					    model.addAttribute("apiResponse",status);
					    model.addAttribute("successMsg",message);
					    model.addAttribute("L0_gstin", gstin);
					    model.addAttribute("L0_fp", fp);
					    model.addAttribute("GSTR_type", "GSTR1");
					    model.addAttribute("gobacktoasp", "gobacktoasp");
						pageRedirect=PageRedirectConstants.UPLOAD_JIO_GST_PAGE;
				
			}catch(Exception e){
				logger.error("Error in:",e);
			}
			logger.info("Exit");
			return pageRedirect;
		}
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/getresponseforauthtokenindatabase", method=RequestMethod.POST)
		public @ResponseBody String getResponseforAuthtokeninDatabase(Model model,HttpServletRequest request){
			logger.info("Entry");
			String response= "";
			String status_cd="";
			String databaseResponse=GSTNConstants.FAILURE;
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
	       /* String gstin="27GSPMH6151G1ZK";
			String fp="072017";*/
			String gstin=request.getParameter("gstin");
		    String fp=request.getParameter("fp");
		    String otp=request.getParameter("otp");
			String error_desc="";
	   		
	   		Map<String, String> responseMap = new HashMap<String, String>();
			try{
				if(otp != null){
						response = uploadAspService.getValideSession(loginMaster,gstin,otp,fp);
						Gson gson = new Gson();
						responseMap = (Map<String, String>)gson.fromJson(response.toString(), responseMap.getClass());
						error_desc=(String) responseMap.get("error_desc");
						if(error_desc == null){
						     databaseResponse = uploadAspService.addGstrAuthenticationDetailsFilingTime(gstin,fp,loginMaster.getuId().toString(),responseMap);
						}
						if(databaseResponse.equalsIgnoreCase(GSTNConstants.SUCCESS)){
							response = uploadAspService.otpResponseForFileToGstn(loginMaster,gstin,fp);
							
							responseMap = (Map<String, String>)gson.fromJson(response.toString(), responseMap.getClass());
							error_desc=(String) responseMap.get("error_desc");
							if(error_desc == null){
							    status_cd=(String) responseMap.get("status_cd");
							}else{
								error_desc=(String) responseMap.get("error_desc");
							}
							if(status_cd.equalsIgnoreCase("1")){
								response="success";
							}else{
								response=error_desc;
							}
						}
						
						if(error_desc != null){
						     response=error_desc;
						}else{
							response="success";
						}
				}
			}catch(Exception e){
				logger.error("Error in:",e);
			}
			logger.info("Exit");
			return new Gson().toJson(response);
			
		}
		//validation for uploading, saving,submiiting,filing
		
		
		@RequestMapping(value="/wgetuploadtojiogststatus", method= RequestMethod.POST)
		public @ResponseBody String getuploadtojiogststatus(@RequestParam("gstin") String gstinId,@RequestParam("financialPeriod") String financialPeriod,@RequestParam("button") String button, HttpServletRequest request){
			
			logger.info("Entry");	
			
			 String status = "";
			 String actionType="";
			 long conditioncountUpload = 0;
			 long conditioncountSave=0;
			 long conditioncountSubmit=0;
			 long conditioncountUploadCndn = 0;
			 long conditioncountSaveCndn=0;
			 long conditioncountSubmitCndn=0;
			System.out.println(button);
			
			Map<String,Long> statusinfo= new HashMap();
			// map to store values of status 
			try{
				
				List<InvoiceDetails> invoiceList = uploadAspService.getInvoiceDetailsByGstinForCheck(gstinId, financialPeriod);
				List<PayloadCnDnDetails> cndnList= uploadAspService.getCndnDetailsByGstinForCheck(gstinId, financialPeriod);
				
				
				// statusinfo will store values of upload save submit and file of invoice and cndn 
				
				
				statusinfo.put("UploadToJiogst", conditioncountUpload);
				statusinfo.put("SaveToGstn", conditioncountSave);
				statusinfo.put("SubmitToGstn", conditioncountSubmit);
				statusinfo.put("UploadToJiogstCndn", conditioncountUploadCndn);
				statusinfo.put("SaveToGstnCndn", conditioncountSaveCndn);
				statusinfo.put("SubmitToGstnCndn", conditioncountSubmitCndn);
				
			
				
				//// iterating through the CNDN and checking upload save submit status 
				for(int i=0;i<cndnList.size();i++)
				{
					
					
					PayloadCnDnDetails cndndetails=cndnList.get(i);
					
					if(button.equalsIgnoreCase("draft"))
					{
						/*actionType="UploadToJiogst";*/
						
					status=cndndetails.getUploadToJiogst();
					if(status.equalsIgnoreCase("false"))
					{
						conditioncountUploadCndn++;
						statusinfo.put("UploadToJiogstCndn", conditioncountUploadCndn);
						/*falsecount++;*/
					}
					
					}
					else if(button.equalsIgnoreCase("submit"))
					{
						
						status=cndndetails.getUploadToJiogst();
						if(status.equalsIgnoreCase("false"))
						{
							/*actionType="UploadToJiogst";*/
							/*falsecount++;*/
							
							conditioncountUploadCndn++;
							statusinfo.put("UploadToJiogstCndn", conditioncountUploadCndn);
							
						}
						
						status=cndndetails.getSaveToGstn();
						if(status.equalsIgnoreCase("false"))
						{
							/*actionType="SaveToGstn";*/
							conditioncountSaveCndn++;
							statusinfo.put("SaveToGstnCndn", conditioncountSaveCndn);
						}
						
						}
					else if(button.equalsIgnoreCase("file"))
					{
						status=cndndetails.getUploadToJiogst();
						if(status.equalsIgnoreCase("false"))
						{
							/*actionType="UploadToJiogst";*/
							/*falsecount++;
							conditioncountsave++;*/
							conditioncountUploadCndn++;
							statusinfo.put("UploadToJiogstCndn", conditioncountUploadCndn);
							
							
						}
						
						
						status=cndndetails.getSaveToGstn();
						if(status.equalsIgnoreCase("false"))
						{
							/*actionType="SaveToGstn";
							falsecount++;
							conditioncountsubmit++;*/
							conditioncountSaveCndn++;
							statusinfo.put("SaveToGstnCndn", conditioncountSaveCndn);
							
						}
						
						
						
							status=cndndetails.getSubmitToGstn();
							if(status.equalsIgnoreCase("false"))
							{
								/*actionType="SubmitToGstn";
								falsecount++;*/
								conditioncountSubmitCndn++;
								statusinfo.put("SubmitToGstnCndn", conditioncountSubmitCndn);
							}
						
						}
				}
				
				
				
				// iterating through the INVOICE and checking upload save subit status 
				for(int i=0;i<invoiceList.size();i++)
				{
					
					InvoiceDetails invoicedetails =invoiceList.get(i);
					
					
					if(button.equalsIgnoreCase("draft"))
					{
						/*actionType="UploadToJiogst";*/
						
					status=invoicedetails.getUploadToJiogst();
					if(status.equalsIgnoreCase("false"))
					{
						conditioncountUpload++;
						statusinfo.put("UploadToJiogst", conditioncountUpload);
						/*falsecount++;*/
					}
					
					}
					else if(button.equalsIgnoreCase("submit"))
					{
						
						status=invoicedetails.getUploadToJiogst();
						if(status.equalsIgnoreCase("false"))
						{
							/*actionType="UploadToJiogst";*/
							/*falsecount++;*/
							
							conditioncountUpload++;
							statusinfo.put("UploadToJiogst", conditioncountUpload);
							
						}
						
						status=invoicedetails.getSaveToGstn();
						if(status.equalsIgnoreCase("false"))
						{
							/*actionType="SaveToGstn";*/
							conditioncountSave++;
							statusinfo.put("SaveToGstn", conditioncountSave);
						}
						
						}
					else if(button.equalsIgnoreCase("file"))
					{
						status=invoicedetails.getUploadToJiogst();
						if(status.equalsIgnoreCase("false"))
						{
							/*actionType="UploadToJiogst";*/
							/*falsecount++;
							conditioncountsave++;*/
							conditioncountUpload++;
							statusinfo.put("UploadToJiogst", conditioncountUpload);
							
							
						}
						
						
						status=invoicedetails.getSaveToGstn();
						if(status.equalsIgnoreCase("false"))
						{
							/*actionType="SaveToGstn";
							falsecount++;
							conditioncountsubmit++;*/
							conditioncountSave++;
							statusinfo.put("SaveToGstn", conditioncountSave);
							
						}
						
						
						
							status=invoicedetails.getSubmitToGstn();
							if(status.equalsIgnoreCase("false"))
							{
								/*actionType="SubmitToGstn";
								falsecount++;*/
								conditioncountSubmit++;
								statusinfo.put("SubmitToGstn", conditioncountSubmit);
							}
						
						}
				}
				
			}catch(Exception e){
				logger.error("Error in:",e);
			}
			logger.info("Exit");
			/*statusinfo.add(0, falsecount);
			statusinfo.add(1, actionType);*/
			if(statusinfo.isEmpty()==true)
			{
				statusinfo=null;
			}
			return new Gson().toJson(statusinfo);
		}

		
	
}
