/**
 * 
 */
package com.reliance.gstn.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
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
import com.reliance.gstn.model.CnDnAdditionalDetails;
import com.reliance.gstn.model.FeedbackDetails;
import com.reliance.gstn.model.GSTINDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.MasterDescDetails;
import com.reliance.gstn.model.UnitOfMeasurement;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.FeedbackDetailService;
import com.reliance.gstn.service.MasterDescService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Rupali J
 *
 */

@Controller
public class WizardFeedbackDetailsController {
	
	private static final Logger logger = Logger.getLogger(FeedbackDetailsController.class);
	
	@Autowired
	public FeedbackDetailService feedbackDetailsService;
	
	@Autowired
	public MasterDescService masterDescService;
	
	@Autowired
	private UserMasterService userMasterService;
	
	
	@Value("${FEEDBACK_DETAILS_SUCESS}")
	private String feedbackDetailsSuccessful;
	
	@Value("${FEEDBACK_DETAILS_FAILURE}")
	private String feedbackDetailsFailure;
	
	@Value("${${env}.PATH_FOR_LOGO_IMG}")
	private String pathForLogoImage;
	
	@Value("${${env}.PATH_FOR_FEEDBACK_IMG}")
	private String pathForFeedbackScreenshotImage;
	
	@Value("${FEEDBACK_UPLOAD_MAX_SIZE}")
	private Long maxFileUploadSize;
	
	@Value("${FEEDBACK_IMG_FORMATS_ALLOWED}")
	private String formatsAllowed;
	
	@Value("${FEEDBACK_UPLOAD_FILE_FORMAT_EXCEPTION}")
	private String logoFormatException;
	
	@Value("${FEEDBACK_UPLOAD_FILE_SIZE_EXCEPTION}")
	private String logoSizeException;
	
	@Value("${LOGO_UPLOAD_FAILURE}")
	private String logoUploadFailure;
	
	@Value("${LOGO_UPLOAD_SUCCESS}")
	private String logoUploadSucess;
	

		
	@ModelAttribute("feedbackDetails")
	public FeedbackDetails construct(){
		return new FeedbackDetails();
	}
	
	@RequestMapping(value = "/wAddFeedbackDetails", method = RequestMethod.GET)
	public String addFeedbackDetails(Model model) {
		return PageRedirectConstants.WIZARD_MANAGE_FEEDBACK_DETAILS_LIST_PAGE;
	}

	@RequestMapping(value="/wAddFeedbackDetails",method=RequestMethod.POST, produces = {"application/json"})
	public String addFeedbackDetailsPost(@Valid @ModelAttribute("feedbackDetails") FeedbackDetails feedbackDetails, BindingResult result,Model model,HttpServletRequest httpRequest,MultipartHttpServletRequest request,HttpServletResponse httpResponse) {
		logger.info("Entry");	
		
		
        /*MultipartFile multipartFile = request.getFile("file");*/
        List<MultipartFile> multipartFile = request.getFiles("file");
        List<String> fileNames = new ArrayList<String>();
        String response = "";
        String pageRedirect="";
	/*	String pageRedirect = PageRedirectConstants.WIZARD_MANAGE_FEEDBACK_DETAILS_LIST_PAGE;*/
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		Integer orgUId = loginMaster.getOrgUId();
		Integer userId=loginMaster.getuId();
		if (!result.hasErrors()){
			
			try {
				

				feedbackDetails.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
				feedbackDetails.setOrgId(loginMaster.getOrgUId());
				feedbackDetails.setUserId(loginMaster.getuId());
				
				int flag=0;//used to check if all the files are jpeg/png and less than 500 kb.if all dosent meets criteria none of the files will be saved in hard disk and exception is thrown 
				int count1=0;
				for (MultipartFile checkfile : multipartFile) {
					 
	                String fileName1 = checkfile.getOriginalFilename();
	                fileNames.add(fileName1);
	                Long size =checkfile.getSize();
	                count1++;
	                
	                
	                String contentType = checkfile.getContentType();
	                String extArray [] = contentType.split("/");
		             String ext ="";
		             
		             if(extArray.length==2){
		            	 ext = extArray[1];
		                 ext = ext.toLowerCase();
		             }
		             
		             byte [] byteArr=checkfile.getBytes();
		             InputStream inputStream = new ByteArrayInputStream(byteArr);
		             
		             String mimeType = URLConnection.guessContentTypeFromStream(inputStream)==null?"": URLConnection.guessContentTypeFromStream(inputStream);
		             String mimeArray [] = mimeType.split("/");
		             String mime="NA";
		             
		             if(mimeArray.length==2){
		            	 mime = mimeArray[1];
		            	 mime = mime.toLowerCase();
		             }
		            
		             if(size<1){
		            	
		            	 
		              
		                 
		                 
		                 if(count1==1)
		                 {
		                	 String filePathWithName1 = null;
		                  feedbackDetails.setFilePathWithName1(filePathWithName1);
		                 }
		                 else if(count1==2)
		                 {
		                	   String filePathWithName2 = null;
		                 feedbackDetails.setFilePathWithName2(filePathWithName2);
		                	 
		                 }
		                 else
		                 {
		                	 String filePathWithName3 = null;
		                 feedbackDetails.setFilePathWithName3(filePathWithName3);
		                 }
		                 
		              
		                 
		                 
		            }
		             
		            
		             
		             else{
		             if(!formatsAllowed.contains(ext) || !formatsAllowed.contains(mime) ){
		        		 model.addAttribute(GSTNConstants.RESPONSE,logoFormatException);
		        		 flag++;
		        		 feedbackDetails.setFeedbackDesc("");
		        		 pageRedirect=PageRedirectConstants.WIZARD_MANAGE_FEEDBACK_DETAILS_LIST_PAGE;
		        		 return  pageRedirect;
		        	 }else if(size>maxFileUploadSize){
		        		 model.addAttribute(GSTNConstants.RESPONSE,logoSizeException);
		        		 flag++;
		        		 feedbackDetails.setFeedbackDesc("");
		        		 pageRedirect=PageRedirectConstants.WIZARD_MANAGE_FEEDBACK_DETAILS_LIST_PAGE;
		        		 return  pageRedirect;
		        	 }}
		             
				}
				
				
				if(flag==0)
				{
					
				int count=0;
				 if (null != multipartFile&& multipartFile.size() < 4 )
			        {
			            for (MultipartFile file : multipartFile) {
			 
			                String fileName1 = file.getOriginalFilename();
			                fileNames.add(fileName1);
			                Long size =file.getSize();
			                
			                
			                count++;
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
				            
				         
			                 if(size<1){
			                	  if(count==1)
					                 {
					                	 String filePathWithName1 = null;
					                  feedbackDetails.setFilePathWithName1(filePathWithName1);
					                 }
					                 else if(count==2)
					                 {
					                	   String filePathWithName2 = null;
					                 feedbackDetails.setFilePathWithName2(filePathWithName2);
					                	 
					                 }
					                 else
					                 {
					                	 String filePathWithName3 = null;
					                 feedbackDetails.setFilePathWithName3(filePathWithName3);
					                 }
					                 
					              
				            }
			                 
				            
				             
				           
				             else{
				             if(!formatsAllowed.contains(ext) || !formatsAllowed.contains(mime) ){
				        		 model.addAttribute(GSTNConstants.RESPONSE,logoFormatException);
				        		 
				        		 feedbackDetails.setFeedbackDesc("");
				        		 pageRedirect=PageRedirectConstants.WIZARD_MANAGE_FEEDBACK_DETAILS_LIST_PAGE;
				        		 
				        	 }else if(size>maxFileUploadSize){
				        		 model.addAttribute(GSTNConstants.RESPONSE,logoSizeException);
				        		 flag++;
				        		 feedbackDetails.setFeedbackDesc("");
				        		 pageRedirect=PageRedirectConstants.WIZARD_MANAGE_FEEDBACK_DETAILS_LIST_PAGE;
				        		 
				        	 }
				        	 else{
				      
				        		 String directoryName = pathForFeedbackScreenshotImage.concat(orgUId.toString())+File.separator+(userId.toString());
				        		 
				                 String fileName = "feedback_"+orgUId+"_"+System.currentTimeMillis()+"_"+fileName1;
				                 
				                 
				                
				                 
				                 File directory = new File(directoryName);
				                 
				                 if (! directory.exists()){
				                     directory.mkdirs();
				                     //Creating the Directory of user named using ORG-Prim ID for storing logo 
				                 }else{
				                	 File []files=directory.listFiles();
				                	/* for (File file1 : files) {
				                		 file1.delete();
				                		 //Deleting the previous logos if existed
									}*/
				                 }
				                 
				                 File file1 = new File(directoryName + "/" + fileName);
				                 file.transferTo(file1);
				                 response =GSTNConstants.SUCCESS;
				                 String filePathWithName1 = null;
				                 String filePathWithName2 = null;
				                 String filePathWithName3 = null;
				                 if(count==1)
				                 {
				                  filePathWithName1 =directoryName + "/" + fileName;
				                  feedbackDetails.setFilePathWithName1(filePathWithName1);
				                 }
				                 else if(count==2)
				                 {
				                 filePathWithName2 =directoryName + "/" + fileName;
				                 feedbackDetails.setFilePathWithName2(filePathWithName2);
				                	 
				                 }
				                 else
				                 {
				                 filePathWithName3 =directoryName + "/" + fileName;
				                 feedbackDetails.setFilePathWithName3(filePathWithName3);
				                 }
				                
				                 
				                
				                
				                	 
				                 
				                
				        	 } 
				             }
			            }/*response= userMasterService.updateOrgLogo(flePathWithName,orgUId);*/
							response = feedbackDetailsService.addFeedback(feedbackDetails);
							if(response.equals(GSTNConstants.SUCCESS)){
								model.addAttribute(GSTNConstants.RESPONSE, feedbackDetailsSuccessful);
								pageRedirect = PageRedirectConstants.WIZARD_MANAGE_FEEDBACK_DETAILS_LIST_PAGE;
							}else{
								model.addAttribute(GSTNConstants.RESPONSE, feedbackDetailsFailure);
							}
						}
				}
			            
				 
				 
			
			
			
			
			}
				             
				             catch(ConstraintViolationException e){
							model.addAttribute(GSTNConstants.RESPONSE, feedbackDetailsFailure);
							logger.error("Error in:",e);
						} catch (Exception e) {
							model.addAttribute(GSTNConstants.RESPONSE, feedbackDetailsFailure);
							logger.error("Error in:",e);
						}
						
					}
					
					model.addAttribute("feedbackDetails", new FeedbackDetails());
					logger.info("Exit");
					return  pageRedirect;
				}
	


	@RequestMapping(value = "/wizardFeedbackHistory", method = RequestMethod.GET)
	public String feedbackhistory(HttpServletRequest httpRequest, Model model) {
		
		
		 
             logger.info("Entry");
             
            
             

             LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
             String response = GSTNConstants.NOT_ALLOWED_ACCESS;
             
            
             List<FeedbackDetails> feedbackhistory =  new ArrayList<FeedbackDetails>();
     		try {
     			
     			
     			feedbackhistory= feedbackDetailsService.listFeedbackDetails(loginMaster.getuId());
     			
     			model.addAttribute("feedbackhistory", feedbackhistory);

                logger.info("Exit");
        		
     			
     		} 
                             
             catch (Exception e) {
                             e.printStackTrace();
                             logger.error("Error in:",e);
             }
     		return PageRedirectConstants.WIZARD_FEEDBACK_HISTORY;
             
     	}
	
	@RequestMapping(value = "/getFeedbackHistoryDetails", method = RequestMethod.POST) 
	public String feedbackhistoryDetails(@RequestParam Integer masterDescDetails,Integer id,Integer userId, HttpServletRequest httpRequest, Model model){
		
        logger.info("Entry");
        LoginMaster loginMaster = (LoginMaster) LoginUtil.getLoginUser(httpRequest);
        String response = GSTNConstants.NOT_ALLOWED_ACCESS;
        List<FeedbackDetails> feedbackDetails = new ArrayList<FeedbackDetails>();
        String b641 = null;
		String b642 = null;
		String b643 = null;
		List<MasterDescDetails> masterDesc = null;
        try
        
        {
        	
        	System.out.println("BRO");
        	feedbackDetails = feedbackDetailsService.FeedbackDetails(id,userId);
			masterDesc=feedbackDetailsService.getMasterDesc(masterDescDetails);
			
			String image1=feedbackDetails.get(0).getFilePathWithName1();
			String image2=feedbackDetails.get(0).getFilePathWithName2();
			String image3=feedbackDetails.get(0).getFilePathWithName3();
			
			
			
			
			BufferedImage bImage1 = ImageIO.read(new File(image1));//give the path of an image for 1st image.
	        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
	        String extension=GSTNUtil.getFileExtension(new File(image1));
	        ImageIO.write( bImage1, extension, baos1 );
	        baos1.flush();
	        byte[] imageInByteArray1 = baos1.toByteArray();
	        baos1.close();                                   
	        b641 = DatatypeConverter.printBase64Binary(imageInByteArray1);
			
	         
	        BufferedImage bImage2 = ImageIO.read(new File(image2));//give the path of an image for 2nd image.
	        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
	        extension=GSTNUtil.getFileExtension(new File(image2));
	        ImageIO.write( bImage2, extension, baos2 );
	        baos2.flush();
	        byte[] imageInByteArray2 = baos2.toByteArray();
	        baos2.close();                                   
	        b642 = DatatypeConverter.printBase64Binary(imageInByteArray2);
		         
	         BufferedImage bImage3 = ImageIO.read(new File(image3));//give the path of an image for 3rd image.
		     ByteArrayOutputStream baos3 = new ByteArrayOutputStream();
		     extension=GSTNUtil.getFileExtension(new File(image3));
		     ImageIO.write( bImage3, extension, baos3 );
		     baos3.flush();
		     byte[] imageInByteArray3 = baos3.toByteArray();
		     baos3.close();                                   
		     b643 = DatatypeConverter.printBase64Binary(imageInByteArray3);
        	
        	
        	
        	
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in:",e);
            
}
        logger.info("Exit");
		model.addAttribute("masterDesc", masterDesc);
		model.addAttribute("feedbackDetails", feedbackDetails);
		model.addAttribute("b641", b641);
		model.addAttribute("b642", b642);
		model.addAttribute("b643", b643);
		
        
        
        return PageRedirectConstants.WIZARD_FEEDBACK_HISTORY_DETAILS;
        
        
        
        
        
        
        
        
        
        
		
		
	
	
	}
	
	






}
				                 
				                 
				                 
			 
			             

