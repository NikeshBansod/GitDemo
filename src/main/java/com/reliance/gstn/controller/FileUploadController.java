package com.reliance.gstn.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

@Controller
public class FileUploadController {

	private static final Logger logger = Logger.getLogger(FileUploadController.class);

	/**
	 * Upload single file using Spring Controller
	 */
	
	@Value("${${env}.PATH_FOR_LOGO_IMG}")
	private String pathForLogoImage;
	
	@Value("${LOGO_UPLOAD_MAX_SIZE}")
	private Long maxFileUploadSize;
	
	@Value("${LOGO_IMG_FORMATS_ALLOWED}")
	private String formatsAllowed;
	
	@Value("${LOGO_UPLOAD_FILE_FORMAT_EXCEPTION}")
	private String logoFormatException;
	
	@Value("${LOGO_UPLOAD_FILE_SIZE_EXCEPTION}")
	private String logoSizeException;
	
	@Value("${LOGO_UPLOAD_FAILURE}")
	private String logoUploadFailure;
	
	@Value("${LOGO_UPLOAD_SUCCESS}")
	private String logoUploadSucess;
	
	@Autowired
	private UserMasterService userMasterService;
	
	@RequestMapping(value = "/getLogoPage", method = RequestMethod.GET)
	public String getLogoPage(Model model) {
		return PageRedirectConstants.GET_LOGO_UPLOAD_PAGE;
	}
	
	@RequestMapping(value = "/uploadLogo", method = RequestMethod.POST)  //, produces = {"application/json"}
    public String uploadOrgLogo(MultipartHttpServletRequest request,HttpServletResponse httpResponse,HttpServletRequest httpRequest,Model model) throws Exception {
		logger.info("Entry");
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
		Integer orgUId = loginMaster.getOrgUId();
        MultipartFile multipartFile = request.getFile("file");
        String response = "";
        String pageRedirect="";
        try {
        	 Long size = multipartFile.getSize();
        	 String contentType = multipartFile.getContentType();
             String extArray [] = contentType.split("/");
             String ext ="";
             if(extArray.length==2){
            	 ext = extArray[1];
                 ext = ext.toLowerCase();
             }
             
             byte [] byteArr=multipartFile.getBytes();
             InputStream inputStream = new ByteArrayInputStream(byteArr);
             
             String mimeType = URLConnection.guessContentTypeFromStream(inputStream)==null?"": URLConnection.guessContentTypeFromStream(inputStream);
             String mimeArray [] = mimeType.split("/");
             String mime="NA";
             if(mimeArray.length==2){
            	 mime = mimeArray[1];
            	 mime = mime.toLowerCase();
             }
             
             if(!formatsAllowed.contains(ext) || !formatsAllowed.contains(mime) ){
        		 model.addAttribute(GSTNConstants.RESPONSE,logoFormatException);
        		 pageRedirect=PageRedirectConstants.GET_LOGO_UPLOAD_PAGE;
        	 }else if(size>maxFileUploadSize){
        		 model.addAttribute(GSTNConstants.RESPONSE,logoSizeException);
        		 pageRedirect=PageRedirectConstants.GET_LOGO_UPLOAD_PAGE;
        	 }else{
        		 String directoryName = pathForLogoImage.concat(orgUId.toString());
                 String fileName = "logo_"+orgUId+"."+ext;

                 File directory = new File(directoryName);
                 
                 if (! directory.exists()){
                     directory.mkdir();
                     //Creating the Directory of user named using ORG-Prim ID for storing logo 
                 }else{
                	 File []files=directory.listFiles();
                	 for (File file : files) {
                		 file.delete();
                		 //Deleting the previous logos if existed
					}
                 }
                 
                 File file = new File(directoryName + "/" + fileName);
                 multipartFile.transferTo(file);
                 response =GSTNConstants.SUCCESS;
                 
                 String flePathWithName =directoryName + "/" + fileName;
                 response= userMasterService.updateOrgLogo(flePathWithName,orgUId);
	                if(response.equals(GSTNConstants.SUCCESS)){
	                	 model.addAttribute(GSTNConstants.RESPONSE,logoUploadSucess);
	            		 pageRedirect=PageRedirectConstants.GET_LOGO_UPLOAD_PAGE;
	                }else{
	                	model.addAttribute(GSTNConstants.RESPONSE,logoUploadFailure);
	            		 pageRedirect=PageRedirectConstants.GET_LOGO_UPLOAD_PAGE;
	                }
        	 }
             
	    } 
        
	     catch (Exception e) {
	    	 logger.error("Error in:",e);
		}
        logger.info("Exit");
        return pageRedirect;
    }
	
	
	@RequestMapping(value = "/getOrgLogo", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImageAsResponseEntity(HttpServletRequest httpRequest) throws IOException {
		logger.info("Entry");
		ResponseEntity<byte[]> responseEntity = null;
		try {
			 HttpHeaders headers = new HttpHeaders();
			 LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			 Integer orgUId = loginMaster.getOrgUId();
			String filePath = userMasterService.getOrgLogoPath(orgUId);
			File ImageFile = new File(filePath);
			InputStream inputStream = new FileInputStream(ImageFile);
			 byte[] media = IOUtils.toByteArray(inputStream);
			 headers.setCacheControl(CacheControl.noCache().getHeaderValue());
			 responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
			 inputStream.close();
		} catch (Exception e) {
			 logger.error("Logo not found :",e);
		}
		logger.info("Exit");
	    return responseEntity;
	}
	
	}
