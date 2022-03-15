/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.SafeHtml;

/**
 * @author Nikesh.Bansod
 *
 */
@Entity
@Table(name = "gst_eway_bill")
public class EWayBill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bill_id")
	private int id;
	
	@Column(name = "kms_travelled")
	private double kmsToBeTravelled;

	@Column(name = "mode_of_transport")
	private int modeOfTransport;

	@Column(name = "mode_of_transport_desc")
	@SafeHtml(message = "HTML elements not allowed")
	private String modeOfTransportDesc;

	@Column(name = "invoice_id")
	private int invoiceId;

	@Column(name = "vehicle_no")
	@SafeHtml(message = "HTML elements not allowed")
	private String vehicleNo;

	@Column(name = "transporter_name")
	@SafeHtml(message = "HTML elements not allowed")
	private String transporterName;

	@Column(name = "transport_doc_no")
	@SafeHtml(message = "HTML elements not allowed")
	private String docNo;

	@Column(name = "transport_doc_date")
	private java.sql.Timestamp docDate;

	@Column(name = "transporter_id")
	@SafeHtml(message = "HTML elements not allowed")
	private String transporterId;

	@Column(name = "ewaybill_no")
	@SafeHtml(message = "HTML elements not allowed")
	private String ewaybillNo;

	@Column(name = "ewaybill_date")
	@SafeHtml(message = "HTML elements not allowed")
	private String ewaybill_date;

	@Column(name = "ewaybill_valid_upto")
	@SafeHtml(message = "HTML elements not allowed")
	private String ewaybill_valid_upto;

	@Column(name = "supply_type")
	@SafeHtml(message = "HTML elements not allowed")
	private String supplyType;

	@Column(name = "sub_supply_type")
	@SafeHtml(message = "HTML elements not allowed")
	private String subSupplyType;

	@Column(name = "doc_type")
	@SafeHtml(message = "HTML elements not allowed")
	private String docType;

	@Column(name = "ref_org_uId")
	private Integer ref_org_uId;

	@Column(name = "gstin")
	@SafeHtml(message = "HTML elements not allowed")
	private String gstin;

	@Column(name = "nic_id")
	@SafeHtml(message = "HTML elements not allowed")
	private String nic_id;

	@Column(name = "ewaybill_status")
	@SafeHtml(message = "HTML elements not allowed")
	private String ewaybillStatus;

	@Column(name = "cancel_remark")
	@SafeHtml(message = "HTML elements not allowed")
	private String cancelRmrk;

	@Column(name = "ewaybill_canceldate")
	@SafeHtml(message = "HTML elements not allowed")
	private String canceldate;

	@Transient
	@SafeHtml(message = "HTML elements not allowed")
	private String docDateInString;

	@Transient
	private String remarks;

	@Transient
	@SafeHtml(message = "HTML elements not allowed")
	private String nicPwd;

	@Transient
	@SafeHtml(message = "HTML elements not allowed")
	private String isCheckNicUserId;

	@Transient
	private String ewayBillClientId;

	@Transient
	private String renderData;

	@Transient
	private String ewayBillSecretKey;
	
	@Column(name = "vehicle_type")
	@SafeHtml(message = "HTML elements not allowed")
	private String vehicleType;
	
	@Column(name = "txn")
	@SafeHtml(message = "HTML elements not allowed")
	private String txn;
	
	
	@Transient
	private String authUserId;
	
	@Transient
	private String appCode;

	@Column(name = "generated_through")				//added on 20/09/2018 to track 
	private String generatedThrough;
	
	
	
	public String getGeneratedThrough() {
		return generatedThrough;
	}

	public void setGeneratedThrough(String generatedThrough) {
		this.generatedThrough = generatedThrough;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getKmsToBeTravelled() {
		return kmsToBeTravelled;
	}

	public void setKmsToBeTravelled(double kmsToBeTravelled) {
		this.kmsToBeTravelled = kmsToBeTravelled;
	}

	public int getModeOfTransport() {
		return modeOfTransport;
	}

	public void setModeOfTransport(int modeOfTransport) {
		this.modeOfTransport = modeOfTransport;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getTransporterName() {
		return transporterName;
	}

	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public java.sql.Timestamp getDocDate() {
		return docDate;
	}

	public void setDocDate(java.sql.Timestamp docDate) {
		this.docDate = docDate;
	}

	public String getTransporterId() {
		return transporterId;
	}

	public void setTransporterId(String transporterId) {
		this.transporterId = transporterId;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getDocDateInString() {
		return docDateInString;
	}

	public void setDocDateInString(String docDateInString) {
		this.docDateInString = docDateInString;
	}

	public String getNicPwd() {
		return nicPwd;
	}

	public void setNicPwd(String nicPwd) {
		this.nicPwd = nicPwd;
	}

	public String getIsCheckNicUserId() {
		return isCheckNicUserId;
	}

	public void setIsCheckNicUserId(String isCheckNicUserId) {
		this.isCheckNicUserId = isCheckNicUserId;
	}

	public String getEwaybillNo() {
		return ewaybillNo;
	}

	public void setEwaybillNo(String ewaybillNo) {
		this.ewaybillNo = ewaybillNo;
	}

	public String getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(String supplyType) {
		this.supplyType = supplyType;
	}

	public String getSubSupplyType() {
		return subSupplyType;
	}

	public void setSubSupplyType(String subSupplyType) {
		this.subSupplyType = subSupplyType;
	}

	public Integer getRef_org_uId() {
		return ref_org_uId;
	}

	public void setRef_org_uId(Integer ref_org_uId) {
		this.ref_org_uId = ref_org_uId;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getNic_id() {
		return nic_id;
	}

	public void setNic_id(String nic_id) {
		this.nic_id = nic_id;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getEwaybill_date() {
		return ewaybill_date;
	}

	public void setEwaybill_date(String ewaybill_date) {
		this.ewaybill_date = ewaybill_date;
	}

	public String getEwaybill_valid_upto() {
		return ewaybill_valid_upto;
	}

	public void setEwaybill_valid_upto(String ewaybill_valid_upto) {
		this.ewaybill_valid_upto = ewaybill_valid_upto;
	}

	public String getModeOfTransportDesc() {
		return modeOfTransportDesc;
	}

	public void setModeOfTransportDesc(String modeOfTransportDesc) {
		this.modeOfTransportDesc = modeOfTransportDesc;
	}

	public String getEwayBillClientId() {
		return ewayBillClientId;
	}

	public void setEwayBillClientId(String ewayBillClientId) {
		this.ewayBillClientId = ewayBillClientId;
	}

	public String getEwayBillSecretKey() {
		return ewayBillSecretKey;
	}

	public void setEwayBillSecretKey(String ewayBillSecretKey) {
		this.ewayBillSecretKey = ewayBillSecretKey;
	}

	public String getRenderData() {
		return renderData;
	}

	public void setRenderData(String renderData) {
		this.renderData = renderData;
	}

	public String getCancelRmrk() {
		return cancelRmrk;
	}

	public void setCancelRmrk(String cancelRmrk) {
		this.cancelRmrk = cancelRmrk;
	}

	public String getEwaybillStatus() {
		return ewaybillStatus;
	}

	public void setEwaybillStatus(String ewaybillStatus) {
		this.ewaybillStatus = ewaybillStatus;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCanceldate() {
		return canceldate;
	}

	public void setCanceldate(String canceldate) {
		this.canceldate = canceldate;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getTxn() {
		return txn;
	}

	public void setTxn(String txn) {
		this.txn = txn;
	}

	public String getAuthUserId() {
		return authUserId;
	}

	public void setAuthUserId(String authUserId) {
		this.authUserId = authUserId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	
	
	
	

}
