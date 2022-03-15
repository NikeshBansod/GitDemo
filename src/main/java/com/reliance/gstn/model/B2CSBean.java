package com.reliance.gstn.model;

import java.io.Serializable;

public class B2CSBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5319803975292337091L;

	private Double iamt = 0.00 ;
	private Double camt = 0.00;
	private Double samt = 0.00;
	private Double csamt = 0.00;
	private Double txval = 0.00;
	private String pos;
	private String etin;	
	private Double rt = 0.00;
	private Double irt = 0.00;
	private Double crt = 0.00;
	private Double srt = 0.00;
	private String stid;
	private String action;
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
	public Double getTxval() {
		return txval;
	}
	public void setTxval(Double txval) {
		this.txval = txval;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getEtin() {
		return etin;
	}
	public void setEtin(String etin) {
		this.etin = etin;
	}
	public Double getRt() {
		return rt;
	}
	public void setRt(Double rt) {
		this.rt = rt;
	}
	public Double getIrt() {
		return irt;
	}
	public void setIrt(Double irt) {
		this.irt = irt;
	}
	public Double getCrt() {
		return crt;
	}
	public void setCrt(Double crt) {
		this.crt = crt;
	}
	public Double getSrt() {
		return srt;
	}
	public void setSrt(Double srt) {
		this.srt = srt;
	}
	public String getStid() {
		return stid;
	}
	public void setStid(String stid) {
		this.stid = stid;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}
