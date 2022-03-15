/**
 * 
 */
package com.reliance.gstn.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.reliance.gstn.model.AspUserDetails;
import com.reliance.gstn.model.Dashboard;
import com.reliance.gstn.model.Gstr1OtpResponseEntity;
import com.reliance.gstn.model.GstrUploadDetails;
import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.UnitOfMeasurement;
import com.reliance.gstn.service.GenerateInvoiceService;
import com.reliance.gstn.service.GenericService;
import com.reliance.gstn.service.GstinValidationService;
import com.reliance.gstn.service.UnitOfMeasurementService;
import com.reliance.gstn.service.UploadAspService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.AspApiConstants;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;
import com.reliance.gstn.util.UOMUtil;


@Controller
public class WizardUploadAspController {
	
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

	
	
	@ModelAttribute("aspUserDetail")
	public AspUserDetails construct(){
		return new AspUserDetails();
	}
	
	
	@RequestMapping(value = "/wAspMasters", method = RequestMethod.GET)
	public String getASPMasterPage(Model model) {
		return PageRedirectConstants.WIZARD_ASP_HOME_PAGE;
	}
	
	
	/*@RequestMapping(value = "/wGetJioGstLoginPage", method = RequestMethod.GET)
	public String getJioGstLoginPage(Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		String pageRedirect = PageRedirectConstants.WIZARD_LOGIN_JIO_GST_PAGE;
		AspUserDetails aspUserDetailObj = null;
		
		try {
			String response = null;
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			model.addAttribute("loginMaster", loginMaster);
			List<AspUserDetails> aspUserDetailList = uploadAspService.getAspUserListByUid(loginMaster.getuId());
			if(!aspUserDetailList.isEmpty()){
				aspUserDetailObj = aspUserDetailList.get(0); 
				aspUserDetailObj.setUserExist(true); 
				response = uploadAspService.getGstUser(aspUserDetailObj);
				if(response.equals(GSTNConstants.SUCCESS)){
					
					pageRedirect = PageRedirectConstants.WIZARD_UPLOAD_JIO_GST_PAGE;
				}  else if(response.equals(GSTNConstants.LOGIN_USER)) {
					pageRedirect = PageRedirectConstants.WIZARD_LOGIN_JIO_GST_PAGE;
				} else {
					aspUserDetailObj = new AspUserDetails();
					pageRedirect = PageRedirectConstants.WIZARD_LOGIN_JIO_GST_PAGE;
				}
				
			} else {
				aspUserDetailObj = new AspUserDetails();
				pageRedirect = PageRedirectConstants.WIZARD_LOGIN_JIO_GST_PAGE;
			}
			model.addAttribute("aspUserDetailObj", aspUserDetailObj);
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return pageRedirect;
	}*/
	
	
	@RequestMapping(value = "/wGetJioGstLoginPage", method = RequestMethod.GET)
	public String getJioGstLoginPage(Model model, HttpServletRequest httpRequest) {
		logger.info("Entry");
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		model.addAttribute("loginMaster", loginMaster);
		
		logger.info("Exit");
		return PageRedirectConstants.WIZARD_UPLOAD_JIO_GST_PAGE;
	}
	
	@RequestMapping(value="/wGetJioGstLoginPage",method=RequestMethod.POST)
	public String validateJioGstLogin(@Valid @ModelAttribute("aspUserDetail") AspUserDetails aspUserDetails, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String pageRedirect = PageRedirectConstants.WIZARD_LOGIN_JIO_GST_PAGE;
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
					pageRedirect = PageRedirectConstants.WIZARD_UPLOAD_JIO_GST_PAGE;
				}else{
					model.addAttribute(GSTNConstants.RESPONSE, gstUploadUserLoginFailure);
					pageRedirect = PageRedirectConstants.WIZARD_UPLOAD_JIO_GST_PAGE;
				}
			}else{
				model.addAttribute(GSTNConstants.RESPONSE, gstUploadUserLoginFailure);
				pageRedirect = PageRedirectConstants.WIZARD_UPLOAD_JIO_GST_PAGE;
				
			}
		} catch (Exception e) {			
			logger.error("Error in:",e);
			model.addAttribute(GSTNConstants.RESPONSE, gstUploadUserLoginFailure);
			pageRedirect = PageRedirectConstants.WIZARD_UPLOAD_JIO_GST_PAGE;
		}
		logger.info("Exit");
		return  pageRedirect;
		
	}
	
	
	@RequestMapping(value="/wGetAspUserListByUid",method=RequestMethod.POST)
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
	
	@RequestMapping(value = "/wGetUploadPage", method = RequestMethod.GET)
	public String uploadPage(Model model) {
		
		return PageRedirectConstants.WIZARD_UPLOAD_JIO_GST_PAGE;
	}
	
		
	@RequestMapping(value = "/wUploadInvoicesManual", method = RequestMethod.POST)
	public String uploadInvoicesManual(Model model,HttpServletRequest request) {
		
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		String gstinId=request.getParameter("gstinId");
		String gstntype=request.getParameter("gstrtype");
		String gstinResponse = GSTNConstants.FAILURE;
		String isError = "";
		String isActive = "";
		String goback = "goback";
		String financialPeriod=request.getParameter("financialPeriod");
		String fpMonth = financialPeriod.substring(0, 2);
		String month=GSTNUtil.theMonth(Integer.parseInt(fpMonth)-1);
		String fpYear = financialPeriod.substring(2, 6);
		String errorMessage = "Selected GSTIN is not valid. Please Update your GSTIN in GSTIN master and then try to upload the Data";
		
		
		String message = "We are uploading data for You data is getting uploaded to JioGST for "+month +" "+fpYear+" for - "+gstinId+". "
				+ " It will take few minutes, you can visit JioGST.com to view the uploaded data. ";
				
		try {
						
			//Logic for loading UOM and handling synchronization
			if (!UOMUtil.isMapLoaded()) {
				List<UnitOfMeasurement> unitOfMeasurementList = (List<UnitOfMeasurement>) unitOfMeasurementService.getUnitOfMeasurement();
				UOMUtil.setUOMMap(unitOfMeasurementList);
			}
			List<String> gstinList = new ArrayList<String>();
			gstinList.add("27AWRPJ5446K0ZS");
			gstinList.add("26AWRPJ5446K0ZA");
			gstinList.add("29AWRPJ5446K0Z0");
			gstinList.add("27AWRPJ5446K0Z6");
			gstinList.add("29AWRPJ5446K0ZZ");
			gstinList.add("07AWRPJ5446K2Z3");
			gstinList.add("27AWRPJ5446K1Z1");
			gstinList.add("27AWRPJ5446K7ZP");
			gstinList.add("27AWRPJ5446K0ZA");
			gstinList.add("29AWRPJ5446K0Z2");
			gstinList.add("27AWRPJ5446KAZ1");
			gstinList.add("27AWRPJ5446K0Z1");
			
			if(gstinList.contains(gstinId)) {
			String response = uploadAspService.uploadInvoiceForGSTR1(loginMaster,gstinId,financialPeriod);
			if(response.equalsIgnoreCase("Success") || response.equalsIgnoreCase("emptyList")){
				model.addAttribute("apiResponse",response);
				model.addAttribute("successMsg", message);
				model.addAttribute("return_gstrtype", gstntype);
				model.addAttribute("return_gstinId", gstinId);
				model.addAttribute("return_financialperiod", financialPeriod);
				model.addAttribute("goback", goback);
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
				String response = uploadAspService.uploadInvoiceForGSTR1(loginMaster,gstinId,financialPeriod);
				if(response.equalsIgnoreCase("Success") || response.equalsIgnoreCase("emptyList")){
					model.addAttribute("apiResponse",response);
					model.addAttribute("successMsg", message);
					model.addAttribute("return_gstrtype", gstntype);
					model.addAttribute("return_gstinId", gstinId);
					model.addAttribute("return_financialperiod", financialPeriod);
					model.addAttribute("goback", goback);
				} else{
					model.addAttribute("apiResponse","error");
				}
				}
				}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return PageRedirectConstants.WIZARD_UPLOAD_JIO_GST_PAGE;
	}
	
	
	
/*	@RequestMapping(value = "/wGetUploadSetting", method = RequestMethod.GET)
	public String getUploadSetting(Model model) {
		return PageRedirectConstants.WIZARD_UPLOAD_JIO_GST_SETTING;
	}
	
	@RequestMapping(value = "/wUploadInvoicesSetting", method = RequestMethod.POST)
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
		return PageRedirectConstants.WIZARD_UPLOAD_JIO_GST_SETTING;
	}
	
	@RequestMapping(value = "/wGetHistory", method = RequestMethod.GET)
	public String historyPage(Model model) {
		
		return PageRedirectConstants.WIZARD_HISTORY_JIO_GST_PAGE;
	}
	*/			
	
	@RequestMapping(value="/wLastUploadedGstinData", method= RequestMethod.POST)
	public @ResponseBody String lastUploadedGstinData(@RequestParam("gstin") String gstin,@RequestParam("financialPeriod") String financialPeriod, HttpServletRequest request){
		
		logger.info("Entry");	
		GstrUploadDetails gstrUploadDetails = new GstrUploadDetails();
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		try{
			gstrUploadDetails = uploadAspService.getLastUploadedGstinData(gstin,financialPeriod);
			
		} catch(Exception e){
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
		
		return new Gson().toJson(gstrUploadDetails);
	}
	
	
	@RequestMapping(value = "/wgetgstr1l0response", method = RequestMethod.POST)
	public @ResponseBody String getGstr1LoResponse(  Model model, HttpServletRequest request){
		logger.info("Entry");
		String response= null;
		
		 
		 
		 String gstin=request.getParameter("gstin");
		 String fp=request.getParameter("fp");
		 String fpMonth = fp.substring(0, 2);
		String fpYear = fp.substring(2, 6);
		 System.out.println(gstin);
		 System.out.println(fp+"what");
		/*String fpMonth = fp.substring(0, 2);
		String fpYear = fp.substring(2, 6);*/
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		try{
			response = uploadAspService.getGstr1LoResponse(loginMaster, gstin, fp);
			
		} catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		model.addAttribute("gstin", gstin);
		model.addAttribute("fp",fp);
		model.addAttribute("fpMonth", fpMonth);
		model.addAttribute("fpYear", fpYear);
		/*return response;*/
		return new Gson().toJson(response);
	}
	
	@RequestMapping(value = "/wgetgstr1l2response", method = RequestMethod.POST)
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
			response = uploadAspService.getGstr1L2Response(loginMaster, gstin, fp,section,recOffset,noOfRecords);
			
		} catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		model.addAttribute("gstin", gstin);
		model.addAttribute("fp",fp);
		
		return response;
	}
	
	
	
	@RequestMapping(value = "/wizardaspdraft", method = RequestMethod.GET)
	public String wizardDraftLo(Model model, HttpServletRequest httpRequest) {
		
		logger.info("Entry");
		String pageRedirect = null;
		AspUserDetails aspUserDetailObj = null;
		
		try {
			String response = null;
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			model.addAttribute("loginMaster", loginMaster);
			List<AspUserDetails> aspUserDetailList = uploadAspService.getAspUserListByUid(loginMaster.getuId());
			if(!aspUserDetailList.isEmpty()){
				aspUserDetailObj = aspUserDetailList.get(0); 
				aspUserDetailObj.setUserExist(true); 
				response = uploadAspService.getGstUser(aspUserDetailObj);
				if(response.equals(GSTNConstants.SUCCESS)){
					
					pageRedirect = PageRedirectConstants.WIZARD_DRAFT_JIO_GST_PAGE;
				}  else if(response.equals(GSTNConstants.LOGIN_USER)) {
					pageRedirect = PageRedirectConstants.WIZARD_DRAFT_JIO_GST_PAGE;
				} else {
					aspUserDetailObj = new AspUserDetails();
					pageRedirect = PageRedirectConstants.WIZARD_DRAFT_JIO_GST_PAGE;
				}
				
			} else {
				aspUserDetailObj = new AspUserDetails();
				pageRedirect = PageRedirectConstants.WIZARD_DRAFT_JIO_GST_PAGE;
			}
			model.addAttribute("aspUserDetailObj", aspUserDetailObj);
			
		} catch (Exception e) {
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return pageRedirect;
	}
	//DRAFT PAGE 
	@RequestMapping(value = "/wgetL0ResponsePage", method = RequestMethod.POST)
	public String getL0ResponsePage( Model model, HttpServletRequest request){
		logger.info("Entry");
		
		 String gstin=request.getParameter("gstinId");
		
		 String fp=request.getParameter("financialPeriod");
		 String gstntype=request.getParameter("gstrtype");
		 String goback = "goback";
		String fpMonth = fp.substring(0, 2);
		String fpYear = fp.substring(2, 6);
		String month=GSTNUtil.theMonth(Integer.parseInt(fpMonth)-1);
		
		logger.info("Exit");
		model.addAttribute("gstinId", gstin);
		model.addAttribute("financialPeriod",fp);
		model.addAttribute("return_gstrtype", gstntype);
		model.addAttribute("return_gstinId", gstin);
		model.addAttribute("return_financialperiod", fp);
		model.addAttribute("goback", goback);
		model.addAttribute("month", month);
		model.addAttribute("fpYear", fpYear);
		
		//model.addAttribute("response",response);
		return PageRedirectConstants.WIZARD_DRAFT_LO_DATA;
	}
	
	
	@RequestMapping(value="/getGstrtype", method=RequestMethod.POST )
	public @ResponseBody String getGstrtype(HttpServletRequest request){
		
		List<String> gstr = new ArrayList<String>();

		
		 gstr.add("GSTR-1");
		 
		 gstr.add("GSTR-3B");
		 
		
	//	
			
			return new Gson().toJson(gstr);
		
	}
	
	
	@PostMapping(value = "/gotoCompliance")
	public String gotoDashboard(@RequestParam("return_gstinId") String return_gstinId ,@RequestParam("return_financialperiod") String return_financialperiod,@RequestParam("return_gstrtype") String return_gstrtype,@RequestParam("goback") String goback,Model model,HttpServletRequest request) {
		logger.info("Entry");
		model.addAttribute("dashboard", new Dashboard());
		
	
		model.addAttribute("return_gstinId", return_gstinId);
		model.addAttribute("goback", goback);
		model.addAttribute("return_financialperiod", return_financialperiod);
		model.addAttribute("return_gstrtype", return_gstrtype);
	
		logger.info("Exit");
		System.out.println("hie2");
		return PageRedirectConstants.WIZARD_UPLOAD_JIO_GST_PAGE;
	}
	//draft-->save to jiogst 
	
	@RequestMapping(value = "/wgetgstr1otpresponse", method = RequestMethod.POST)
	public @ResponseBody String getgstr1otpresponse( HttpServletRequest request, Model model){
		logger.info("Entry");
        String statusofaction="firsttimeloginuser";
		String response= "";
		String tk_gen_time="";
		Date d1 = null;
		String gstin=request.getParameter("gstin");
		 String fp=request.getParameter("fp");
		/*String gstin="27GSPMH0782G1ZJ";
		String fp="072017";*/
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		String sname=loginMaster.getFirmName();
		
		String status_cd="";
		String databaseResponse="";
		String auth_token="";
		String app_key="";
		String sek="";
        String gt="200000";
        String cur_gt="0";
        String err_cd="";
        /*String fpMonth = fp.substring(0, 2);
   		String fpYear = fp.substring(2, 6);*/
   		
   		Map<String, Object> responseMap1 = new HashMap<String, Object>();
		try{
			List<Gstr1OtpResponseEntity> responseEntityMap = new ArrayList<>();
			responseEntityMap=uploadAspService.getGstr1OtpResponse(gstin);//fetch user to check weather user present in db or not 
			if(responseEntityMap.isEmpty()){
				
				     Map<String, String> responseMap = new HashMap<String, String>();
				     statusofaction="firsttimeloginuser";
			         response = uploadAspService.getGstr1OTPResponse(loginMaster,gstin,fp,statusofaction);//OTPREQUEST FOR 1ST USER
			         logger.info("OTP REQUEST"+responseMap);
			      
			         
			 }else{
				 //checking session timings from database if expired or not 
			        tk_gen_time=responseEntityMap.get(0).getTk_gen_time();
			        DateFormat  format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
			        d1 = (Date)format.parse(tk_gen_time);
			        Date date= new Date();
			        long time = date.getTime();
			        long diff = time-d1.getTime();
			        
			        long diffMinutes=TimeUnit.MILLISECONDS.toMinutes(diff);  
				        
				    if(diffMinutes > 480 ) {
				    	statusofaction="expired";//user present session expired 
				    	response=uploadAspService.getGstr1OTPResponse(loginMaster,gstin,fp,statusofaction);//[OTP REQUEST CALL if expired ]
				    	logger.info("otprequest"+response);
				        }else if(480 >= diffMinutes && diffMinutes >= 478 ){
				        		 statusofaction="invalidsession";//[OTP REQUEST REAUTH CALL if invalid ]
				        		 response=uploadAspService.getGstr1OTPResponse(loginMaster,gstin,fp,statusofaction);
				        		 logger.info("OTP REQUEST REAUTH"+response);
				        }else{
					    
							
								statusofaction = "validsession";
								
							
				        }
			   }
			
		} catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(statusofaction);
	}
	//IF USER ENTERS OTP THIS URL IS CALLED [GSTNSAVEOTP CALL]
	
	@SuppressWarnings({ "unchecked", "static-access" })
	@RequestMapping(value = "/submitotpgstr1", method = RequestMethod.POST)
	public @ResponseBody String submitotpgstr1( HttpServletRequest request, Model model){
		logger.info("Entry");
		String response= "";
		String status_cd="";
		String databaseResponse="";
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		
	    String otp=request.getParameter("otp");
        /*String gstin="27GSPMH0782G1ZJ";
		String fp="072017";*/
		String gstin=request.getParameter("gstin");
	    String fp=request.getParameter("fp");
		String statusofaction="";
		String auth_token="";
		String app_key="";
		String sek="";
	    String gt="200000";
	    String cur_gt="0";
	    String err_cd="";
	    String errorMsg="";
	    String UploadType = "SaveToGSTN";
	    Map<String,String> result1=new HashMap<>();
	    String sname=loginMaster.getFirmName();
	    String fpMonth = fp.substring(0, 2);
	    String fpYear = fp.substring(2, 6);
	    List<Gstr1OtpResponseEntity> responseEntityMap = new ArrayList<>();
   		Map<String,String> saveGstnResponse=new HashMap<String,String>();
   		String ref_id="";
   		String serviceType="";
   	
			Map<String, Object> responseMap = new HashMap<String, Object>();
			Gson gson = new Gson();
		
		try{
			
			if(otp != ""){
				result1 = uploadAspService.responsesavetogstn(loginMaster,gstin,otp,fp,gt,cur_gt,sname);
					
					responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
					
					Map<String, String> errorl0ResponseMap = new HashMap<String, String>();
					 errorl0ResponseMap=(Map<String, String>) responseMap.get("l0ResponseMap");
					 String error_desc;
					 if(errorl0ResponseMap==null)
					 {
					 String error_desc1 = (String) responseMap.get("err_cd");
					 error_desc = (String) responseMap.get("error_desc");
					 errorMsg=(String) responseMap.get("errorMsg");
					 }
					 else
					 {
						 String error_desc1 = (String) errorl0ResponseMap.get("err_cd");
						  error_desc = (String) responseMap.get("error_desc");
					 }
					if(error_desc != null){
						statusofaction=(String) responseMap.get("error_desc");
					}
					else if(errorMsg != null){
						statusofaction=(String) responseMap.get("errorMsg");
					}
			
			}else{
				responseEntityMap=uploadAspService.getGstr1OtpResponse(gstin);
				app_key=responseEntityMap.get(0).getAppkey();
			    sek=responseEntityMap.get(0).getSek();
			    auth_token=responseEntityMap.get(0).getAuthtoken();
			    result1  = uploadAspService.responsesavetogstnwithoutOtp(loginMaster, gstin,fp, gt, cur_gt, sname,app_key, sek, auth_token);
			  
			    responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
			   
				saveGstnResponse=(Map<String, String>) responseMap.get("saveGstnResponse");
				err_cd = (String) saveGstnResponse.get("err_cd");
				if(err_cd == null){
					status_cd = (String) saveGstnResponse.get("status_cd");
				}else{
					statusofaction = (String) saveGstnResponse.get("usr_msg");
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
			    
				responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
				Map<String, Object> saveGstnStatusResponse = new HashMap<String, Object>();
				saveGstnStatusResponse= (Map<String, Object>) responseMap.get("saveGstnStatusResponse");
				
				err_cd = (String) saveGstnStatusResponse.get("err_cd");
				if(err_cd != null){
					statusofaction = (String) saveGstnStatusResponse.get("usr_act");
				}
				else
				{
					
					String gstnUploadDetailsDatabaseResponse = uploadAspService.addDataTogstnUploadDetailsDatabase(loginMaster, gstin,
							fpMonth, fpYear, fp, (String) result1.get("txnid"), (String) result1.get("ref_id"), UploadType);
					List<InvoiceDetails> invoiceList = uploadAspService.getInvoiceDetailsByGstin(gstin, fp);
					uploadAspService.setUploadToJiogstStatus(gstin, fp, /* UploadDate */response,
							UploadType/* ,invoiceid */, invoiceList);
					statusofaction ="1";
				}
	        }
			
			
		   
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(statusofaction);
	}
		
		
	
	//redirecting to submit page 
	
	
	@RequestMapping(value = "/wgetsubmitpage", method = RequestMethod.POST)
	public String getsubmitpage( Model model, HttpServletRequest request){
		logger.info("Entry");
		
		 String gstin=request.getParameter("gstinId");
		
		 String fp=request.getParameter("financialPeriod");
		 String gstrtype=request.getParameter("gstrtype");
		 String goback = "goback";
		/*String fpMonth = fp.substring(0, 2);
		String fpYear = fp.substring(2, 6);*/
		 String monthofFP=fp.substring(0, 2);
		 String fpYear = fp.substring(2, 6);
		 String payload_mode="submit";
		
		
		logger.info("Exit");
		model.addAttribute("gstin", gstin);
		model.addAttribute("financialPeriod",fp);
		model.addAttribute("return_gstrtype", gstrtype);
		model.addAttribute("gstrtype", gstrtype);
		model.addAttribute("return_gstinId", gstin);
		model.addAttribute("return_financialperiod", fp);
		model.addAttribute("goback", goback);
		model.addAttribute("monthofFP",monthofFP);
		model.addAttribute("fpYear",fpYear);
		model.addAttribute("payload_mode",payload_mode);
		
		//model.addAttribute("response",response);
		return PageRedirectConstants.WIZARD_SUBMIT_LO_DATA;
	}
	
	// CONTROLLERS for view LO GSTR1 page 
	
	
	/*@RequestMapping(value = "/getgstrtype", method = RequestMethod.POST)
	public @ResponseBody String getGstrType(HttpServletRequest request)
	{
		Map<String,String>gstrType=new LinkedHashMap<String,String>();
		gstrType.put("GSTR1", "GSTR1");
		gstrType.put("GSTR3B", "GSTR3B");
		return new Gson().toJson(gstrType);
	}*/
	
	@RequestMapping(value = "/wgetgstr1responsefromGSTN", method = RequestMethod.POST)
	public @ResponseBody String getgstr1responsefromGSTN( HttpServletRequest request, Model model){
		logger.info("Entry");
        String statusofaction="firsttimeloginuser";
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
		String sname=loginMaster.getFirmName();
		try{
			List<Gstr1OtpResponseEntity> responseEntityMap = new ArrayList<>();
			responseEntityMap=uploadAspService.getGstr1OtpResponse(gstin);
			
			if(responseEntityMap.isEmpty()){
				statusofaction="expired";
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
			        /*long diffMinutes = diff / (60 * 1000) % 60;*/
			        long diffMinutes=TimeUnit.MILLISECONDS.toMinutes(diff);
				    if(diffMinutes > 480 ) {
				    	statusofaction="expired";
				    	response=uploadAspService.getGstr1OTPResponse(loginMaster,gstin,fp,statusofaction);
				    }else if(480 >= diffMinutes && diffMinutes >= 478 ){
				        		 statusofaction="invalidsession";
				        		 response=uploadAspService.getGstr1OTPResponse(loginMaster,gstin,fp,statusofaction);
				    }else{
					     statusofaction="validsession";
				    }
		     } 
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return new Gson().toJson(statusofaction);
	}
	
	//user submits otp to see gstr1 data
	@RequestMapping(value = "/wgetgstr1submittogstnl0response", method = RequestMethod.POST)
	public @ResponseBody String getgstr1submittogstnl0response(@RequestParam("gstin") String gstin,@RequestParam("fp") String fp,@RequestParam("otp") String otp,Model model, HttpServletRequest request){
		logger.info("Entry");
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
		String offset="0";
        String limit="100";
		String response= "";
		String auth_token="";
		String app_key="";
		String sek="";
		/*String gstin="27GSPMH0782G1ZJ";
		String fp="072017";*/
		String errorcatch="";
		Map<String, Object> responseMap = new HashMap<String, Object>();
		String databaseResponse="";
		try{
			if(otp != "")
			{
				response = uploadAspService.getGstnL0ResponsewithOTP(loginMaster,gstin,otp,fp,offset,limit);
				Gson gson = new Gson();
				responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
				Map<String, String> errorl0ResponseMap = new HashMap<String, String>();
				 errorl0ResponseMap=(Map<String, String>) responseMap.get("l0ResponseMap");
				
				String error_desc;
				 String error_desc1;
				 if(errorl0ResponseMap==null)
				 {
				 
				 error_desc = (String) responseMap.get("error_desc");
				 }
				 else
				 {
					  error_desc1 = (String) errorl0ResponseMap.get("err_cd");
					  error_desc = (String) responseMap.get("error_desc");
				 }
				if(error_desc != null){
					/*response=(String) responseMap.get("error_desc");*/
					response="Something went wrong. Try again after some time";
				}
				else{
				Map<String,String> authTokenResponse=new HashMap<String,String>();
				databaseResponse = uploadAspService.addGstrAuthenticationDetails(gstin,fp,authTokenResponse,loginMaster.getuId().toString(),responseMap);
				
				}
				/*if(error_desc != null ){
					response="Invalid OTP";
			    }else if(error_desc1 != null){
			    	response="Please select the preference";
			    }*/
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
			 String error_desc1 = (String) errorl0ResponseMap.get("err_cd");
			
			
			 if(error_desc1 != null){
		    	response="Something went wrong. Try again after some time";
		    }
		}
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		return new Gson().toJson(response);
	}
	// final submit to gstn gstr1 data 
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/wresponsesubmittogstn", method = RequestMethod.POST)
	public @ResponseBody String responsesumbittogstn( HttpServletRequest request, Model model){
		logger.info("Entry");
		String response= "";
		String status_cd="";
		String databaseResponse="";
		String gstnUploadDetailsDatabaseResponse="";
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(request);
        String otp=request.getParameter("otp");
        /*String gstin="27GSPMH0782G1ZJ";
		String fp="072017";*/
		String gstin=request.getParameter("gstin");
	      String fp=request.getParameter("fp");
		String auth_token="";
		String app_key="";
		String sek="";
        String offset="0";
        String limit="100";
        String err_cd="";
        Map<String,String> result1=new HashMap<>();
        String sname=loginMaster.getFirmName();
        String fpMonth = fp.substring(0, 2);
   		String fpYear = fp.substring(2, 6);
   	 String goback = "goback";
   	String UploadType = "SubmitToGSTN";
   		Map<String, Object> responseMap = new HashMap<String, Object>();
        String message="Data submitted successfully for financial period "+fpMonth+""+fpYear+" for GSTIN "+gstin+" to GSTN";
        Gson gson = new Gson();
		try{
			
			if(otp != null){
				result1 = uploadAspService.getSubmitToGstnL0OtpResponse(loginMaster,gstin,otp,fp);
					response=result1.get("response");
					responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
					String error_desc=(String) responseMap.get("error_desc");
					if(error_desc != null){
						response=(String) responseMap.get("error_desc");
					}
					else{
						Map<String,String> authTokenResponse=new HashMap<String,String>();
						
						databaseResponse = uploadAspService.addGstrAuthenticationDetails(gstin,fp,authTokenResponse,loginMaster.getuId().toString(),responseMap);
						Map<String,String> saveGstnResponse=new HashMap<String,String>();
						saveGstnResponse=(Map<String, String>) responseMap.get("saveGstnResponse");
						
						err_cd = (String) saveGstnResponse.get("err_cd");
						if(err_cd == null){
							status_cd = (String) saveGstnResponse.get("status_cd");
						}else{
							message = (String) saveGstnResponse.get("usr_msg");
							response=message;
						}
					}
			}else{
					List<Gstr1OtpResponseEntity> responseEntityMap = new ArrayList<>();
					responseEntityMap=uploadAspService.getGstr1OtpResponse(gstin);
					app_key=responseEntityMap.get(0).getAppkey();
				    sek=responseEntityMap.get(0).getSek();
				    auth_token=responseEntityMap.get(0).getAuthtoken();
				    result1 = uploadAspService.responsesubmittogstnwithoutOtp(loginMaster, gstin, fp, sname,app_key, sek, auth_token);
				    
				    response=result1.get("response");
				    //Gson gson = new Gson();
				    responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
				    Map<String,String> saveGstnResponse=new HashMap<String,String>();
					saveGstnResponse=(Map<String, String>) responseMap.get("saveGstnResponse");
					
					err_cd = (String) saveGstnResponse.get("err_cd");
					if(err_cd == null){
						status_cd = (String) saveGstnResponse.get("status_cd");
					}else{
						message = (String) saveGstnResponse.get("usr_msg");
						response=message;
					}
					
					Thread.currentThread().sleep(5000);
			        if(status_cd.equalsIgnoreCase("1")){
			        	String ref_id = (String) saveGstnResponse.get("ref_id");
			        	responseEntityMap=uploadAspService.getGstr1OtpResponse(gstin);
						app_key=responseEntityMap.get(0).getAppkey();
					    sek=responseEntityMap.get(0).getSek();
					    auth_token=responseEntityMap.get(0).getAuthtoken();
					    response = uploadAspService.getGstnSaveStatus(AspApiConstants.GSTR1_SERVICE_GSTNSUBMITRESTATUS,loginMaster,ref_id,app_key,sek,auth_token,gstin,fp);
					    //Gson gson = new Gson();
						responseMap = (Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
						Map<String, Object> saveGstnStatusResponse = new HashMap<String, Object>();
						saveGstnStatusResponse= (Map<String, Object>) responseMap.get("saveGstnStatusResponse");
						
						err_cd = (String) saveGstnStatusResponse.get("err_cd");
						if(err_cd != null){
							response = (String) saveGstnStatusResponse.get("usr_act");
						}
						else
						{
							
							gstnUploadDetailsDatabaseResponse = uploadAspService.addDataTogstnUploadDetailsDatabase(loginMaster, gstin,
									fpMonth, fpYear, fp, (String) result1.get("txnid"), (String) result1.get("ref_id"), UploadType);
							List<InvoiceDetails> invoiceList = uploadAspService.getInvoiceDetailsByGstin(gstin, fp);
							
							uploadAspService.setUploadToJiogstStatus(gstin, fp, /* UploadDate */response,
									UploadType/* ,invoiceid */, invoiceList);
							message = (String) saveGstnResponse.get("usr_msg");
							response=message;
							
						}
			        }
			}
		     
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		return  new Gson().toJson(response);
	}
	
	//controllers for filing gstr 1
	
	@RequestMapping(value = "/wgetfilepage", method = RequestMethod.POST)
	public String getfilepage( Model model, HttpServletRequest request){
		logger.info("Entry");
		
		 String gstin=request.getParameter("gstinId");
		
		 String fp=request.getParameter("financialPeriod");
		 String gstrtype=request.getParameter("gstrtype");
		 String goback = "goback";
		/*String fpMonth = fp.substring(0, 2);
		String fpYear = fp.substring(2, 6);*/
		 String monthofFP=fp.substring(0, 2);
		 String fpYear = fp.substring(2, 6);
		 String payload_mode="file";
		
		
		logger.info("Exit");
		model.addAttribute("gstin", gstin);
		model.addAttribute("financialPeriod",fp);
		model.addAttribute("return_gstrtype", gstrtype);
		model.addAttribute("gstrtype", gstrtype);
		model.addAttribute("return_gstinId", gstin);
		model.addAttribute("return_financialperiod", fp);
		model.addAttribute("goback", goback);
		model.addAttribute("monthofFP",monthofFP);
		model.addAttribute("fpYear",fpYear);
		model.addAttribute("payload_mode",payload_mode);
		
		//model.addAttribute("response",response);
		return PageRedirectConstants.WIZARD_FILE_LO_DATA;
	}
	
	//
	//wfiletogstn
	
	
	
	/*//validation for uploading, saving,submiiting,filing
	
	
	@RequestMapping(value="/wgetuploadtojiogststatus", method= RequestMethod.POST)
	public @ResponseBody String getuploadtojiogststatus(@RequestParam("gstin") String gstinId,@RequestParam("financialPeriod") String financialPeriod,@RequestParam("button") String button, HttpServletRequest request){
		
		logger.info("Entry");	
		
		 String status = "";
		 String actionType="";
		 long conditioncountUpload = 0;
		 long conditioncountSave=0;
		 long conditioncountSubmit=0;
		System.out.println(button);
		
		Map<String,Long> statusinfo= new HashMap();
		try{
			
			List<InvoiceDetails> invoiceList = uploadAspService.getInvoiceDetailsByGstinForCheck(gstinId, financialPeriod);
			
			for(int i=0;i<invoiceList.size();i++)
			{
				InvoiceDetails invoicedetails =invoiceList.get(i);
				statusinfo.put("UploadToJiogst", conditioncountUpload);
				statusinfo.put("SaveToGstn", conditioncountSave);
				statusinfo.put("SubmitToGstn", conditioncountSubmit);
				if(button.equalsIgnoreCase("draft"))
				{
					actionType="UploadToJiogst";
					
				status=invoicedetails.getUploadToJiogst();
				if(status.equalsIgnoreCase("false"))
				{
					conditioncountUpload++;
					statusinfo.put("UploadToJiogst", conditioncountUpload);
					falsecount++;
				}
				
				}
				else if(button.equalsIgnoreCase("submit"))
				{
					
					status=invoicedetails.getUploadToJiogst();
					if(status.equalsIgnoreCase("false"))
					{
						actionType="UploadToJiogst";
						falsecount++;
						
						conditioncountUpload++;
						statusinfo.put("UploadToJiogst", conditioncountUpload);
						
					}
					
					status=invoicedetails.getSaveToGstn();
					if(status.equalsIgnoreCase("false"))
					{
						actionType="SaveToGstn";
						conditioncountSave++;
						statusinfo.put("SaveToGstn", conditioncountSave);
					}
					
					}
				else if(button.equalsIgnoreCase("file"))
				{
					status=invoicedetails.getUploadToJiogst();
					if(status.equalsIgnoreCase("false"))
					{
						actionType="UploadToJiogst";
						falsecount++;
						conditioncountsave++;
						conditioncountUpload++;
						statusinfo.put("UploadToJiogst", conditioncountUpload);
						
						
					}
					
					
					status=invoicedetails.getSaveToGstn();
					if(status.equalsIgnoreCase("false"))
					{
						actionType="SaveToGstn";
						falsecount++;
						conditioncountsubmit++;
						conditioncountSave++;
						statusinfo.put("SaveToGstn", conditioncountSave);
						
					}
					
					
					
						status=invoicedetails.getSubmitToGstn();
						if(status.equalsIgnoreCase("false"))
						{
							actionType="SubmitToGstn";
							falsecount++;
							conditioncountSubmit++;
							statusinfo.put("SubmitToGstn", conditioncountSubmit);
						}
					
					}
			}
		}catch(Exception e){
			logger.error("Error in:",e);
		}
		logger.info("Exit");
		statusinfo.add(0, falsecount);
		statusinfo.add(1, actionType);
		if(statusinfo.isEmpty()==true)
		{
			statusinfo=null;
		}
		return new Gson().toJson(statusinfo);
	}
*/
}
