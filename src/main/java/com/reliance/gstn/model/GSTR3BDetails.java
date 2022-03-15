package com.reliance.gstn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gstn_gstr3b_savejiogst_gstn")
public class GSTR3BDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(name = "gstr_upload_id")
	private String gstrUploadId;

	@Column(name = "action")
	private String action;

	@Column(name = "sup_ty")
	private String sup_ty;

	@Column(name = "txval")
	private Double txval;

	@Column(name = "iamt")
	private Double iamt;

	@Column(name = "camt")
	private Double camt;

	@Column(name = "samt")
	private Double samt;

	@Column(name = "csamt")
	private Double csamt;

	@Column(name = "pos")
	private String pos;

	@Column(name = "itc_ty")
	private String itc_ty;

	@Column(name = "ty")
	private String ty;

	@Column(name = "inter")
	private String inter;

	@Column(name = "intra")
	private String intra;

	@Column(name = "section")
	private String section;

	@Column(name = "created_on", updatable=false)
	private java.sql.Timestamp createdOn;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Double getTxval() {
		return txval;
	}

	public void setTxval(Double txval) {
		this.txval = txval;
	}

	public Double getIamt() {
		return iamt;
	}

	public void setIamt(Double iamt) {
		this.iamt = iamt;
	}

	public Double getCamt() {
		return camt;
	}

	public void setCamt(Double camt) {
		this.camt = camt;
	}

	public Double getSamt() {
		return samt;
	}

	public void setSamt(Double samt) {
		this.samt = samt;
	}

	public Double getCsamt() {
		return csamt;
	}

	public void setCsamt(Double csamt) {
		this.csamt = csamt;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public String getTy() {
		return ty;
	}

	public void setTy(String ty) {
		this.ty = ty;
	}

	public String getInter() {
		return inter;
	}

	public void setInter(String inter) {
		this.inter = inter;
	}

	public String getIntra() {
		return intra;
	}

	public void setIntra(String intra) {
		this.intra = intra;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSup_ty() {
		return sup_ty;
	}

	public void setSup_ty(String sup_ty) {
		this.sup_ty = sup_ty;
	}

	public String getItc_ty() {
		return itc_ty;
	}

	public void setItc_ty(String itc_ty) {
		this.itc_ty = itc_ty;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public java.sql.Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(java.sql.Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getGstrUploadId() {
		return gstrUploadId;
	}

	public void setGstrUploadId(String gstrUploadId) {
		this.gstrUploadId = gstrUploadId;
	}

}
