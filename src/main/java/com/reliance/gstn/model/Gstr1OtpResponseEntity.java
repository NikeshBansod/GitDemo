package com.reliance.gstn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "gst_authentication_details")
public class Gstr1OtpResponseEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "user_id")
	private String userid;
	
	@Column(name = "gstin")
	private String gstin;
	
	@Column(name = "financial_period")
	private String fp;
	
	@Column(name = "appkey")
	private String appkey;
	
	@Column(name = "sek")
	private String sek;
	
	@Column(name = "auth_token")
	private String authtoken;
	
	
	@Column(name = "ref_exp")
	private String ref_exp;
	
	@Column(name = "ses_exp")
	private String ses_exp;
	
	@Column(name = "action")
	private String action;
	
	@Column(name = "ses_gen_time")
	private String ses_gen_time;
	
	@Column(name = "tk_gen_time")
	private String tk_gen_time;
	
	@Column(name = "username")
	private String username;



	public String getRef_exp() {
		return ref_exp;
	}



	public void setRef_exp(String ref_exp) {
		this.ref_exp = ref_exp;
	}



	public String getSes_exp() {
		return ses_exp;
	}



	public void setSes_exp(String ses_exp) {
		this.ses_exp = ses_exp;
	}



	public String getAction() {
		return action;
	}



	public void setAction(String action) {
		this.action = action;
	}



	public String getSes_gen_time() {
		return ses_gen_time;
	}



	public void setSes_gen_time(String ses_gen_time) {
		this.ses_gen_time = ses_gen_time;
	}



	public String getTk_gen_time() {
		return tk_gen_time;
	}



	public void setTk_gen_time(String tk_gen_time) {
		this.tk_gen_time = tk_gen_time;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getUserid() {
		return userid;
	}



	public void setUserid(String userid) {
		this.userid = userid;
	}



	public String getGstin() {
		return gstin;
	}



	public void setGstin(String gstin) {
		this.gstin = gstin;
	}



	public String getFp() {
		return fp;
	}



	public void setFp(String fp) {
		this.fp = fp;
	}



	public String getAppkey() {
		return appkey;
	}



	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}



	public String getSek() {
		return sek;
	}



	public void setSek(String sek) {
		this.sek = sek;
	}



	public String getAuthtoken() {
		return authtoken;
	}



	public void setAuthtoken(String authtoken) {
		this.authtoken = authtoken;
	
	}}
	



	
	

	