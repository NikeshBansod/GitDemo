package com.reliance.gstn.master.excel.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.ManageServiceCatalogue;
import com.reliance.gstn.util.GSTNUtil;

public class ServiceDataEntityProvider implements	ExcelDataEntityProvider<ManageServiceCatalogue>  {

	@Override
	public List<ManageServiceCatalogue> getEntityList(List<Object[]> data, LoginMaster loginMaster) {
		List<ManageServiceCatalogue> serviceList = new ArrayList<ManageServiceCatalogue>();
		for(Object[] objects : data){
			ManageServiceCatalogue service = new ManageServiceCatalogue();
			
			if(!GSTNUtil.isNullOrEmpty(objects[0])){
				service.setSacDescription(String.valueOf(objects[0]));
			}
			if(!GSTNUtil.isNullOrEmpty(objects[1])){
				service.setSacCode(String.valueOf(objects[1]));
			}
			if(!GSTNUtil.isNullOrEmpty(objects[2])){
				service.setName(String.valueOf(objects[2]));
			}
			if(!GSTNUtil.isNullOrEmpty(objects[3])){
				service.setUnitOfMeasurement(String.valueOf(objects[3]).toUpperCase());
			}
			if(String.valueOf(objects[3]).equalsIgnoreCase("others") || String.valueOf(objects[3]).equalsIgnoreCase("other") && !GSTNUtil.isNullOrEmpty(objects[4])){
				service.setOtherUOM(String.valueOf(objects[4]).toUpperCase());
			}
			
			service.setAdvolCess(0d);
			service.setNonAdvolCess(0d);
			service.setServiceRate(Double.valueOf(String.valueOf(objects[5])));
			service.setServiceIgst(Double.valueOf(String.valueOf(objects[6])));
			service.setStoreName(String.valueOf(objects[7]));
			service.setGstin(String.valueOf(objects[8]));
			service.setReferenceId(loginMaster.getuId());
			service.setRefOrgId(loginMaster.getOrgUId());
			service.setCreatedBy(loginMaster.getuId().toString());
			service.setUpdatedBy(loginMaster.getuId().toString());
			service.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			service.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			service.setStatus("1");
			
			serviceList.add(service);
		}
		return serviceList;
	}

}
