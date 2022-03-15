package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.List;

public class GSTR3BModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String gstin;
	private String fp;
	private String serviceType;
	private String service;
	private String ret_period;
	private String ackNo;
	private String transactionId;
	private String userId;
	private String orgid;
	private String status;
	private String otp;
	private String username;

	private List<GSTR3BDetails> sup_details;
	private List<GSTR3BDetails> inter_sup;
	private List<GSTR3BDetails> itc_elg;
	private List<GSTR3BDetails> inward_sup;
	private GSTR3BDetails intr_details;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public List<GSTR3BDetails> getSup_details() {
		return sup_details;
	}

	public void setSup_details(List<GSTR3BDetails> sup_details) {
		this.sup_details = sup_details;
	}

	public List<GSTR3BDetails> getInter_sup() {
		return inter_sup;
	}

	public void setInter_sup(List<GSTR3BDetails> inter_sup) {
		this.inter_sup = inter_sup;
	}

	public List<GSTR3BDetails> getItc_elg() {
		return itc_elg;
	}

	public void setItc_elg(List<GSTR3BDetails> itc_elg) {
		this.itc_elg = itc_elg;
	}

	public List<GSTR3BDetails> getInward_sup() {
		return inward_sup;
	}

	public void setInward_sup(List<GSTR3BDetails> inward_sup) {
		this.inward_sup = inward_sup;
	}

	public GSTR3BDetails getIntr_details() {
		return intr_details;
	}

	public void setIntr_details(GSTR3BDetails intr_details) {
		this.intr_details = intr_details;
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

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getRet_period() {
		return ret_period;
	}

	public void setRet_period(String ret_period) {
		this.ret_period = ret_period;
	}

	public String getAckNo() {
		return ackNo;
	}

	public void setAckNo(String ackNo) {
		this.ackNo = ackNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
