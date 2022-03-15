package com.reliance.gstn.master.excel.helper;


public class ExcelDataEntityProviderFactory {

	public static ExcelDataEntityProvider<?> getExcelDataEntityProvider(String masterType){
		ExcelDataEntityProvider<?> excelDataEntityProvider=null;
		if(masterType.equalsIgnoreCase("customermastertemplate")){
			excelDataEntityProvider=new CustomerDataEntityProvider();
		}else if(masterType.equalsIgnoreCase("goodsmastertemplate")){
			excelDataEntityProvider=new GoodsDataEntityProvider();
		}else if(masterType.equalsIgnoreCase("servicesmastertemplate")){
			excelDataEntityProvider=new ServiceDataEntityProvider();
		}
		
		return excelDataEntityProvider;
	}
}
