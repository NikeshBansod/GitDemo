package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "ewb_wi_trasaction")
public class EwayBillWITransaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3248327695024914358L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int ewayBillWITransId;

	@Column(name = "supply_type")
	private String supplyType;

	@Column(name = "sub_supply_type")
	private String subSupplyType;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "sub_supply_desc")
	private String subSupplyDesc;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "subtype_other_desc")
	private String othersSubType;
	

	@Column(name = "document_type")
	private String documentType;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "doc_no")
	private String documentNo;

	@Column(name = "doc_date")
	private java.sql.Timestamp DocumentDate;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "from_gstin")
	private String fromGstin;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "from_trader_name")
	private String fromTraderName;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "from_address1")
	private String fromAddress1;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "from_address2")
	private String fromAddress2;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "from_place")
	private String fromPlace;

	@Column(name = "from_pincode")
	private Integer fromPincode;

	@Column(name = "act_from_state_code")
	private Integer actFromStateCode;

	@Column(name = "from_state_code")
	private Integer fromStateCode;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "to_gstin")
	private String toGstin;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "to_trader_name")
	private String toTraderName;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "to_address1")
	private String toAddress1;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "to_address2")
	private String toAddress2;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "to_place")
	private String toPlace;

	@Column(name = "to_pincode")
	private Integer toPincode;

	@Column(name = "act_to_state_code")
	private Integer actToStateCode;

	@Column(name = "to_state_code")
	private Integer toStateCode;

	@Column(name = "total_value")
	private Double totalValue;

	@Column(name = "sgst_value")
	private Double sgstValue;

	@Column(name = "cgst_value")
	private Double cgstValue;

	@Column(name = "igst_value")
	private Double igstValue;

	@Column(name = "cess_value")
	private Double cessValue;

	@Column(name = "tot_inv_value")
	private Double totInvValue;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "trans_id")
	private String transId;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "trans_name")
	private String transName;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "trans_doc_no")
	private String transDocNo;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "trans_mode")
	private String transMode;

	@Column(name = "trans_distance")
	private Double transDistance;

	@Column(name = "trans_doc_date")
	private java.sql.Timestamp transDocDate;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "vehicle_no")
	private String vehicleNo;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "vehicle_type")
	private String vehicleType;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "app_name")
	private String appName;

	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "status")
	private String status;

	@Column(name = "CREATED_ON", updatable=false)
	private java.sql.Timestamp createdOn;

	@Column(name = "CREATED_BY", updatable=false)
	private String createdBy;
	
	@Column(name = "cess_advol_value")
	private Double totalCessadvolValue;
	
	@Column(name = "cess_nonadvol_value")
	private Double totalCessnonadvolValue;
	
	@Column(name = "othervalue")
	private Double otherValue;


	@Column(name = "UPDATED_ON")
	private java.sql.Timestamp updatedOn;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Transient
	private String transDocDateInString;

	@Transient
	private String documentDateInString;

	@Transient
	private String nicId;

	@Transient
	private String nicPassword;

	@Transient
	private Map<String, Object> headersMap;

	@Column(name = "ewaybill_no")
	private String ewaybillNo;

	@Column(name = "ewaybill_date")
	private String ewaybill_date;

	@Column(name = "ewaybill_valid_upto")
	private String ewaybill_valid_upto;

	@Column(name = "ewaybill_canceldate")
	private String ewaybill_canceldate;

	@Column(name = "cancel_remark")
	private String cancelRemark;

	@Column(name = "ewaybill_status")
	private String ewaybillStatus;

	@Column(name = "userId")
	private String userId;

	@Column(name = "txnId")
	private String txnId;

	@Column(name = "supply_type_desc")
	private String supplyTypeDesc;

	@Column(name = "document_type_desc")
	private String documentTypeDesc;

	@Column(name = "trans_mode_desc")
	private String transModeDesc;

	@OneToMany(mappedBy = "ewayBillWITransaction", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<EwayBillWIItem> ewayBillWIItem;

	@Transient
	private List<Map<String, Object>> ewayBillWIItemList;

	public List<EwayBillWIItem> getEwayBillWIItem() {
		return ewayBillWIItem;
	}

	public void setEwayBillWIItem(List<EwayBillWIItem> ewayBillWIItem) {
		this.ewayBillWIItem = ewayBillWIItem;
	}

	public int getEwayBillWITransId() {
		return ewayBillWITransId;
	}

	public void setEwayBillWITransId(int ewayBillWITransId) {
		this.ewayBillWITransId = ewayBillWITransId;
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

	public String getSubSupplyDesc() {
		return subSupplyDesc;
	}

	public void setSubSupplyDesc(String subSupplyDesc) {
		this.subSupplyDesc = subSupplyDesc;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public java.sql.Timestamp getDocumentDate() {
		return DocumentDate;
	}

	public void setDocumentDate(java.sql.Timestamp documentDate) {
		DocumentDate = documentDate;
	}

	public String getFromGstin() {
		return fromGstin;
	}

	public void setFromGstin(String fromGstin) {
		this.fromGstin = fromGstin;
	}

	public String getFromTraderName() {
		return fromTraderName;
	}

	public void setFromTraderName(String fromTraderName) {
		this.fromTraderName = fromTraderName;
	}

	public String getFromAddress1() {
		return fromAddress1;
	}

	public void setFromAddress1(String fromAddress1) {
		this.fromAddress1 = fromAddress1;
	}

	public String getFromAddress2() {
		return fromAddress2;
	}

	public void setFromAddress2(String fromAddress2) {
		this.fromAddress2 = fromAddress2;
	}

	public String getFromPlace() {
		return fromPlace;
	}

	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}

	public Integer getFromPincode() {
		return fromPincode;
	}

	public void setFromPincode(Integer fromPincode) {
		this.fromPincode = fromPincode;
	}

	public Integer getActFromStateCode() {
		return actFromStateCode;
	}

	public void setActFromStateCode(Integer actFromStateCode) {
		this.actFromStateCode = actFromStateCode;
	}

	public Integer getFromStateCode() {
		return fromStateCode;
	}

	public void setFromStateCode(Integer fromStateCode) {
		this.fromStateCode = fromStateCode;
	}

	public String getToGstin() {
		return toGstin;
	}

	public void setToGstin(String toGstin) {
		this.toGstin = toGstin;
	}

	public String getToTraderName() {
		return toTraderName;
	}

	public void setToTraderName(String toTraderName) {
		this.toTraderName = toTraderName;
	}

	public String getToAddress1() {
		return toAddress1;
	}

	public void setToAddress1(String toAddress1) {
		this.toAddress1 = toAddress1;
	}

	public String getToAddress2() {
		return toAddress2;
	}

	public void setToAddress2(String toAddress2) {
		this.toAddress2 = toAddress2;
	}

	public String getToPlace() {
		return toPlace;
	}

	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}

	public Integer getToPincode() {
		return toPincode;
	}

	public void setToPincode(Integer toPincode) {
		this.toPincode = toPincode;
	}

	public Integer getActToStateCode() {
		return actToStateCode;
	}

	public void setActToStateCode(Integer actToStateCode) {
		this.actToStateCode = actToStateCode;
	}

	public Integer getToStateCode() {
		return toStateCode;
	}

	public void setToStateCode(Integer toStateCode) {
		this.toStateCode = toStateCode;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public Double getSgstValue() {
		return sgstValue;
	}

	public void setSgstValue(Double sgstValue) {
		this.sgstValue = sgstValue;
	}

	public Double getCgstValue() {
		return cgstValue;
	}

	public void setCgstValue(Double cgstValue) {
		this.cgstValue = cgstValue;
	}

	public Double getIgstValue() {
		return igstValue;
	}

	public void setIgstValue(Double igstValue) {
		this.igstValue = igstValue;
	}

	public Double getCessValue() {
		return cessValue;
	}

	public void setCessValue(Double cessValue) {
		this.cessValue = cessValue;
	}

	public Double getTotInvValue() {
		return totInvValue;
	}

	public void setTotInvValue(Double totInvValue) {
		this.totInvValue = totInvValue;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getTransName() {
		return transName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}

	public String getTransDocNo() {
		return transDocNo;
	}

	public void setTransDocNo(String transDocNo) {
		this.transDocNo = transDocNo;
	}

	public String getTransMode() {
		return transMode;
	}

	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}

	public Double getTransDistance() {
		return transDistance;
	}

	public void setTransDistance(Double transDistance) {
		this.transDistance = transDistance;
	}

	public java.sql.Timestamp getTransDocDate() {
		return transDocDate;
	}

	public void setTransDocDate(java.sql.Timestamp transDocDate) {
		this.transDocDate = transDocDate;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public java.sql.Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(java.sql.Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public java.sql.Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(java.sql.Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getTransDocDateInString() {
		return transDocDateInString;
	}

	public void setTransDocDateInString(String transDocDateInString) {
		this.transDocDateInString = transDocDateInString;
	}

	public String getDocumentDateInString() {
		return documentDateInString;
	}

	public void setDocumentDateInString(String documentDateInString) {
		this.documentDateInString = documentDateInString;
	}

	public String getNicId() {
		return nicId;
	}

	public void setNicId(String nicId) {
		this.nicId = nicId;
	}

	public String getNicPassword() {
		return nicPassword;
	}

	public void setNicPassword(String nicPassword) {
		this.nicPassword = nicPassword;
	}

	public Map<String, Object> getHeadersMap() {
		return headersMap;
	}

	public void setHeadersMap(Map<String, Object> headersMap) {
		this.headersMap = headersMap;
	}

	public String getEwaybillNo() {
		return ewaybillNo;
	}

	public void setEwaybillNo(String ewaybillNo) {
		this.ewaybillNo = ewaybillNo;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public String getEwaybillStatus() {
		return ewaybillStatus;
	}

	public void setEwaybillStatus(String ewaybillStatus) {
		this.ewaybillStatus = ewaybillStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getEwaybill_canceldate() {
		return ewaybill_canceldate;
	}

	public void setEwaybill_canceldate(String ewaybill_canceldate) {
		this.ewaybill_canceldate = ewaybill_canceldate;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public List<Map<String, Object>> getEwayBillWIItemList() {
		return ewayBillWIItemList;
	}

	public void setEwayBillWIItemList(List<Map<String, Object>> ewayBillWIItemList) {
		this.ewayBillWIItemList = ewayBillWIItemList;
	}

	public String getSupplyTypeDesc() {
		return supplyTypeDesc;
	}

	public void setSupplyTypeDesc(String supplyTypeDesc) {
		this.supplyTypeDesc = supplyTypeDesc;
	}

	public String getDocumentTypeDesc() {
		return documentTypeDesc;
	}

	public void setDocumentTypeDesc(String documentTypeDesc) {
		this.documentTypeDesc = documentTypeDesc;
	}

	public String getTransModeDesc() {
		return transModeDesc;
	}

	public void setTransModeDesc(String transModeDesc) {
		this.transModeDesc = transModeDesc;
	}

	public String getOthersSubType() {
		return othersSubType;
	}

	public void setOthersSubType(String othersSubType) {
		this.othersSubType = othersSubType;
	}

	public Double getTotalCessadvolValue() {
		return totalCessadvolValue;
	}

	public void setTotalCessadvolValue(Double totalCessadvolValue) {
		this.totalCessadvolValue = totalCessadvolValue;
	}

	public Double getTotalCessnonadvolValue() {
		return totalCessnonadvolValue;
	}

	public void setTotalCessnonadvolValue(Double totalCessnonadvolValue) {
		this.totalCessnonadvolValue = totalCessnonadvolValue;
	}

	public Double getOtherValue() {
		return otherValue;
	}

	public void setOtherValue(Double otherValue) {
		this.otherValue = otherValue;
	}
	
	
	

}
