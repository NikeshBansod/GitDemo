package com.reliance.gstn.model;

import java.util.HashMap;
import java.util.Map;

public class EwayBillWIMasterRes {

	private String value;
	private String code;
	boolean isError=false;
	Map<String,String> errorMsg=new HashMap<>();
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	public Map<String, String> getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(Map<String, String> errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
