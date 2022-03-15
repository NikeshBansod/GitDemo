package com.reliance.gstn.master.excel.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.reliance.gstn.model.CustomerDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.util.GSTNUtil;

public class CustomerDataEntityProvider implements ExcelDataEntityProvider<CustomerDetails> {

	@Override
	public List<CustomerDetails> getEntityList(List<Object[]> data,LoginMaster loginMaster) {
		// TODO Auto-generated method stub
		List<CustomerDetails> customerDetails=new ArrayList<>();
		for (Object[] objects : data) {
			CustomerDetails customerDetail=new CustomerDetails();
			customerDetail.setCustName(String.valueOf(objects[0]));
			if(!GSTNUtil.isNullOrEmpty(objects[1])){
				customerDetail.setContactNo(String.valueOf(objects[1]));
			}
			
			if(!GSTNUtil.isNullOrEmpty(objects[2])){
				customerDetail.setCustEmail(String.valueOf(objects[2]));
			}
			
			
			
			int pinCode=Double.valueOf(String.valueOf(objects[3])).intValue();
			customerDetail.setPinCode(pinCode);
			
			/*customerDetail.setCustCity(String.valueOf(objects[4]));
			customerDetail.setCustState(String.valueOf(objects[5]));*/
			customerDetail.setCustCountry(String.valueOf(objects[4]));
			
			if(!GSTNUtil.isNullOrEmpty(objects[5])){
				customerDetail.setCustAddress1(String.valueOf(objects[5]));
			}
			
			//customerDetail.setCustType("Individual");
			
			if(objects[6]!=null ||String.valueOf(objects[6]).equals("")){
				customerDetail.setCustType("Organization");
			}else{
				customerDetail.setCustType("Individual");
			}
			
			if(!GSTNUtil.isNullOrEmpty(objects[6])){
				customerDetail.setCustGstId(String.valueOf(objects[6]));
			}
			
			/*if(!GSTNUtil.isNullOrEmpty(objects[4])){
			customerDetail.setCustGstinState(String.valueOf(objects[4]));
			}*/
			
			customerDetail.setCreatedBy(loginMaster.getuId().toString());
			customerDetail.setUpdatedBy(loginMaster.getuId().toString());
			customerDetail.setRefOrgId(loginMaster.getOrgUId());
			customerDetail.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
			customerDetail.setUpdatedOn(new java.sql.Timestamp(new Date().getTime()));
			customerDetail.setRefId(loginMaster.getuId());
			customerDetail.setStatus("1");
			customerDetails.add(customerDetail);
		}
		return customerDetails;
	}

}
