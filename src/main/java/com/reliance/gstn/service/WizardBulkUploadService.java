package com.reliance.gstn.service;

import org.springframework.web.multipart.MultipartFile;

import com.reliance.gstn.model.LoginMaster;

public interface WizardBulkUploadService {
	public String uploadMasterExcelData(String masterType,String fileName,MultipartFile file,LoginMaster loginMaster)  throws Exception;
}
