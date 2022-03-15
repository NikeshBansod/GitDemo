package com.reliance.gstn.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.service.WizardBulkUploadService;
import com.reliance.gstn.util.ExcelTemplateConstant;
import com.reliance.gstn.util.ExcelUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Vivek2.Dubey
 * This class will upload excel of master data
 *
 */
@Controller
public class WizardBulkUploadController {
	
	private static final Logger logger = Logger.getLogger(WizardBulkUploadController.class);
	
	@Autowired
	WizardBulkUploadService wizardBulkUploadService;
	
	@Value("${product_master_headers}")
	String productMasterHeader;

	@Value("${service_master_headers}")
	String serviceMasterHeader;

	@Value("${customer_master_headers}")
	String customerMasterHeader;
	
	@Value("${DUPLICAT_ENTRY_IN_EXCEL_UPLOAD}")
	String duplicateEntryInExcelUpload;	
	
	@RequestMapping(value="/downloadmastertemplate",method=RequestMethod.GET)
	public void downloadExcelTemplates(@RequestParam(name="templatename")String templatename,HttpServletResponse response){
		logger.info("Entry");
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String filePath=classLoader.getResource(ExcelTemplateConstant.TEMPLATE_PATH+templatename+ExcelTemplateConstant.FILE_EXTENSION).getPath();
		//String filePath=classLoader.getResource(ApplicationConstants.Batch_Upload_Template).toString().replace("file:/", "");
		File file = new File(filePath);
		logger.debug("File exist="+file.exists());
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		String contentDescription="attachment; filename="+templatename+ExcelTemplateConstant.FILE_EXTENSION;
		response.addHeader("Content-Disposition",contentDescription);
		response.setContentLength((int) file.length());

		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			OutputStream responseOutputStream = response.getOutputStream();
			int bytes;
			while ((bytes = fileInputStream.read()) != -1) {
				responseOutputStream.write(bytes);
			}
			fileInputStream.close();
			responseOutputStream.close();

		} catch (FileNotFoundException e) {
			logger.debug("Error file not found",e);
		} catch (IOException e) {
			logger.debug("Error IO Exception ",e);
		}
		
		logger.info("Exit");
	}
	
	
	@RequestMapping(value="/uploadmasterexcel",method=RequestMethod.GET)
	public String uploadMasterExcel(){
		return PageRedirectConstants.WIZARD_BULK_MASTER_UPLOAD;
	}
	
	
	@RequestMapping(value="/uploadmasterexceldata",method=RequestMethod.POST)
	public @ResponseBody String uploadMasterExcelData(
			@RequestParam("hiddenMasterType") String masterType,
			@RequestParam("fileName") String fileName,
			@RequestParam("file") MultipartFile file,HttpServletRequest httpRequest) {
		logger.info("Entry");
		String result = "";
		try {
			LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest);
			
			if(ExcelUtil.isHeaderMapEmpty()){
				ExcelUtil.setHeaderMap("goodsmastertemplate", productMasterHeader);
				ExcelUtil.setHeaderMap("servicesmastertemplate", serviceMasterHeader);
				ExcelUtil.setHeaderMap("customermastertemplate", customerMasterHeader);
			}
			
			result=wizardBulkUploadService.uploadMasterExcelData(masterType, fileName, file,loginMaster);
		} catch (Exception e) {			
			logger.error("error in upload ",e);
			if(e instanceof ConstraintViolationException || e instanceof NonUniqueObjectException){
				result=duplicateEntryInExcelUpload;
			}else{
				result=e.getMessage();
			}
		}
		logger.info("Exit");
		return new Gson().toJson(result);
	}
	
	
}
