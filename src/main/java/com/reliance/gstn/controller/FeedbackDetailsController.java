/**
 * 
 */
package com.reliance.gstn.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.reliance.gstn.model.FeedbackDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.MasterDescDetails;
import com.reliance.gstn.service.FeedbackDetailService;
import com.reliance.gstn.service.MasterDescService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Rupali J
 *
 */

@Controller
public class FeedbackDetailsController {
	
	private static final Logger logger = Logger.getLogger(FeedbackDetailsController.class);
	
	@Autowired
	public FeedbackDetailService feedbackDetailsService;
	
	@Autowired
	public MasterDescService masterDescService;
	
	
	@Value("${FEEDBACK_DETAILS_SUCESS}")
	private String feedbackDetailsSuccessful;
	
	@Value("${FEEDBACK_DETAILS_FAILURE}")
	private String feedbackDetailsFailure;
	
	@Value("${FEEDBACK_UPLOAD_MAX_SIZE}")
	private Long maxFileUploadSize;
	
	@Value("${FEEDBACK_UPLOAD_MIN_SIZE}")
	private Long minFileUploadSize;
	
	@Value("${FEEDBACK_IMG_FORMATS_ALLOWED}")
	private String formatsAllowed;
	
	@Value("${FEEDBACK_UPLOAD_FILE_FORMAT_EXCEPTION}")
	private String feedbackFormatException;
	
	
	
	@Value("${FEEDBACK_UPLOAD_FILE_SIZE_EXCEPTION}")
	private String feedbackSizeException;
	

	
	@Value("${${env}.PATH_FOR_FEEDBACK_IMG}")
	private String pathForFeedbackImage;
	
	
		
	@ModelAttribute("feedbackDetails")
	public FeedbackDetails construct(){
		return new FeedbackDetails();
	}
	
	@RequestMapping(value = "/addFeedbackDetails", method = RequestMethod.GET)
	public String addFeedbackDetails(Model model) {
		return PageRedirectConstants.MANAGE_FEEDBACK_DETAILS_LIST_PAGE;
	}
	
	@RequestMapping(value="/addFeedbackDetails",method=RequestMethod.POST)
	public String addFeedbackDetailsPost(MultipartHttpServletRequest request ,@Valid @ModelAttribute("feedbackDetails") FeedbackDetails feedbackDetails, BindingResult result,Model model,HttpServletRequest httpRequest) {
		logger.info("Entry");	
		String response = null;
		int i=0;
		
		String status = null;
		String pageRedirect = PageRedirectConstants.MANAGE_FEEDBACK_DETAILS_LIST_PAGE;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		if (!result.hasErrors()){
			Integer orgUId = loginMaster.getOrgUId();
			List<MultipartFile> multipartFile = request.getFiles("fields[]");
			int count=multipartFile.size();
			try {
	        	long size1 = 0;
	        	long size = 0;
	        	for (MultipartFile file : multipartFile){
	        		 size1 =file.getSize();
	        		 size=size+size1;
	        		 if(size1 !=0){
		        		 String contentType = file.getContentType();
	                     String extArray [] = contentType.split("/");
	                     String ext ="";
	                     if(extArray.length==2){
	            	         ext = extArray[1];
	                         ext = ext.toLowerCase();
	                      }
				         byte [] byteArr=file.getBytes();
				         InputStream inputStream = new ByteArrayInputStream(byteArr);
				         String mimeType = URLConnection.guessContentTypeFromStream(inputStream)==null?"": URLConnection.guessContentTypeFromStream(inputStream);
				         String mimeArray [] = mimeType.split("/");
				         String mime="NA";
				         if(mimeArray.length==2){
				        	 mime = mimeArray[1];
				        	 mime = mime.toLowerCase();
					      }
				         if((!formatsAllowed.contains(ext) || !formatsAllowed.contains(mime)) ){
				        	 status="fail";
				    		 model.addAttribute(GSTNConstants.RESPONSE,feedbackFormatException);
				    		  pageRedirect=PageRedirectConstants.MANAGE_FEEDBACK_DETAILS_LIST_PAGE;
				    	 }
				         else if(size1>maxFileUploadSize)
				        		 {
				        			 status="fail";
					        		 model.addAttribute(GSTNConstants.RESPONSE,feedbackSizeException);
					        		 pageRedirect=PageRedirectConstants.MANAGE_FEEDBACK_DETAILS_LIST_PAGE;
				        		 }
	        		 	}
	        	  }
	        	  feedbackDetails.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
				  feedbackDetails.setOrgId(loginMaster.getOrgUId());
				  feedbackDetails.setUserId(loginMaster.getuId());
	        	   if(size == minFileUploadSize){
					  response = feedbackDetailsService.addFeedback(feedbackDetails);
					  if(response.equals(GSTNConstants.SUCCESS)){
							model.addAttribute(GSTNConstants.RESPONSE, feedbackDetailsSuccessful);
							pageRedirect = PageRedirectConstants.MANAGE_FEEDBACK_DETAILS_LIST_PAGE;
					  }else{
							model.addAttribute(GSTNConstants.RESPONSE, feedbackDetailsFailure);
					   }
	        	   }
	        	 else{
	        		if(status != "fail")
	        		{
	        			for (MultipartFile file : multipartFile) {
		        			 long size2 = 0;
		        			 size2 =file.getSize();
		        			 if(size2 !=0){
			                     i++;
		                         String contentType = file.getContentType();
		        			     String extArray [] = contentType.split("/");
					             String ext ="";
					             if(extArray.length==2){
					            	 ext = extArray[1];
					                 ext = ext.toLowerCase();
				                 }
				        		 String directoryName = pathForFeedbackImage.concat(orgUId.toString())+File.separator+(loginMaster.getuId().toString())/*+File.separator+(feedbackDetails.getId().toString())*/;
				                 String fileName = "feedback_"+loginMaster.getuId()+"_"+System.currentTimeMillis()+"."+ext; 
				                 File directory = new File(directoryName);
				                 if (! directory.exists()){
				                     directory.mkdirs();
				                     //Creating the Directory of user named using user ID for storing feedback images 
				                 }else{
				                	File []files=directory.listFiles();
				                     /*for (File file1 : files) { }*/
				                 }
				                 File file1 = new File(directoryName + "/" + fileName);
				                 file.transferTo(file1);
				                 response =GSTNConstants.SUCCESS;
				                 String filePathWithName =directoryName + "/" + fileName;
				                 if(i==1){
				                  feedbackDetails.setFilePathWithName1(filePathWithName);
				                 }
				                 else if(i==2){
				                	 feedbackDetails.setFilePathWithName2(filePathWithName);
				                 }
				                 else{
				                	 feedbackDetails.setFilePathWithName3(filePathWithName); 
				                 }
	        		          }
	        		       }
	        		  }
	        			 if(status != "fail"){
	        				  response = feedbackDetailsService.addFeedback(feedbackDetails);
	        				  if(response.equals(GSTNConstants.SUCCESS)){
									model.addAttribute(GSTNConstants.RESPONSE, feedbackDetailsSuccessful);
									pageRedirect = PageRedirectConstants.MANAGE_FEEDBACK_DETAILS_LIST_PAGE;
								}else{
									model.addAttribute(GSTNConstants.RESPONSE, feedbackDetailsFailure);
								}
	        			 }
	        	      }
		}catch (Exception e) {
				model.addAttribute(GSTNConstants.RESPONSE, feedbackDetailsFailure);
				logger.error("Error in:",e);
			}
		}
		
		model.addAttribute("feedbackDetails", new FeedbackDetails());
		logger.info("Exit");
		return  pageRedirect;
	}
	
	 @RequestMapping(value={"/getMasterDescList","/idt/getAdminMasterDescList"},method=RequestMethod.POST)
	  public @ResponseBody String getUnitOfMeasurement(Model model,HttpServletRequest httpRequest) {
		  logger.info("Entry");
		  List<MasterDescDetails> masterList = new ArrayList<MasterDescDetails>();
		  try {
			  masterList =  masterDescService.getMasterDescList();
		  } catch (Exception e) {
			  logger.error("Error in:",e);
		  }
		  logger.info("Exit");
		  return new Gson().toJson(masterList);
	  }
	 
	 @RequestMapping(value = "/showFeedbackDetails", method = RequestMethod.GET)
	 public String showFeedbackDetails(Model model) {
	 		return PageRedirectConstants.SHOW_FEEDBACK_DETAILS_LIST_PAGE;
	 }
	 
	 @RequestMapping(value="/getFeedbackDetails",method=RequestMethod.POST)
		public @ResponseBody String getFeedbackDetails(Model model,HttpServletRequest httpRequest) {
			logger.info("Entry");
			
			
			List<FeedbackDetails> feedbackDetails = new ArrayList<FeedbackDetails>();
			try {
				LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
				feedbackDetails = feedbackDetailsService.listFeedbackDetails(loginMaster.getuId());
			} catch (Exception e) {
				logger.error("Error in:",e);
			}
			logger.info("Exit");
			return new Gson().toJson(feedbackDetails);
		}
	 
	 @RequestMapping(value="/getMasterDescription",method=RequestMethod.POST)
		public @ResponseBody String getMasterDescription(Model model,HttpServletRequest httpRequest) {
			logger.info("Entry");
			
			List<MasterDescDetails> masterDesc = null;
			
			try {
				masterDesc=feedbackDetailsService.getAllMasterDesc();
				
			} catch (Exception e) {
				logger.error("Error in:",e);
			}
			logger.info("Exit");
			return new Gson().toJson(masterDesc);
		}
	 
	 @RequestMapping(value="/showDetailedFeedback",method=RequestMethod.POST)
		public String showDetailedFeedback(@RequestParam Integer masterDescDetails,@RequestParam Integer id,@RequestParam Integer userId , Model model,HttpServletRequest httpRequest) {
			logger.info("Entry");
			List<FeedbackDetails> feedbackDetails = new ArrayList<FeedbackDetails>();
			String b641 = null;
			String b642 = null;
			String b643 = null;
			
			List<MasterDescDetails> masterDesc = null;
			try {
				
				feedbackDetails = feedbackDetailsService.FeedbackDetails(id,userId);
				masterDesc=feedbackDetailsService.getMasterDesc(masterDescDetails);
				
				String image1=feedbackDetails.get(0).getFilePathWithName1();
				String image2=feedbackDetails.get(0).getFilePathWithName2();
				String image3=feedbackDetails.get(0).getFilePathWithName3();
				
				
		        b641 = feedbackDetailsService.getImageFile(image1);
		        b642 = feedbackDetailsService.getImageFile(image2);
		        b643 = feedbackDetailsService.getImageFile(image3);
		        
			} catch (Exception e) {
				logger.error("Error in:",e);
			}
			logger.info("Exit");
			model.addAttribute("masterDesc", masterDesc);
			model.addAttribute("feedbackDetails", feedbackDetails);
			model.addAttribute("b641", b641);
			model.addAttribute("b642", b642);
			model.addAttribute("b643", b643);
			return PageRedirectConstants.VIEW_FEEDBACK_DETAILS_PAGE;
		}
	 

		
	 
}
