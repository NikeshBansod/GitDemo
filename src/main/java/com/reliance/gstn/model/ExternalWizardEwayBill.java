package com.reliance.gstn.model;

import java.io.Serializable;

public class ExternalWizardEwayBill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String key;
	private String userId;
	private String ewaybillno;
	private String client_id;
	private String secret_key;
	private String app_code;
	private String ip_usr;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEwaybillno() {
		return ewaybillno;
	}

	public void setEwaybillno(String ewaybillno) {
		this.ewaybillno = ewaybillno;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getSecret_key() {
		return secret_key;
	}

	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}

	public String getApp_code() {
		return app_code;
	}

	public void setApp_code(String app_code) {
		this.app_code = app_code;
	}

	public String getIp_usr() {
		return ip_usr;
	}

	public void setIp_usr(String ip_usr) {
		this.ip_usr = ip_usr;
	}

}
