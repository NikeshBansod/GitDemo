package com.reliance.gstn.dao;

import java.util.List;

public interface WizardBulkUploadDAO {
	public String uploadMasterExcelData(List<?> entityList) throws Exception;
	
	public List<Object> getRequiredData(String masterType) throws Exception;
}
