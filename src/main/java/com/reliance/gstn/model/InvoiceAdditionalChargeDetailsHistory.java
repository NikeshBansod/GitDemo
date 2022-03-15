package com.reliance.gstn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.SafeHtml;


@Entity
@Table(name = "gst_invoice_additional_charges_details_history")
public class InvoiceAdditionalChargeDetailsHistory implements Serializable{
	
	private static final long serialVersionUID = -6725489989920516993L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_additional_charge_pk_id")
	private Integer id;
	
	@Column(name = "add_charge_id")
	private Integer additionalChargeId;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "add_charge_name")
	private String additionalChargeName;
	
	@Column(name = "add_charge_amount")
	private double additionalChargeAmount;
	
	@Column(name = "iteration_no")
	private int iterationNo ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAdditionalChargeId() {
		return additionalChargeId;
	}

	public void setAdditionalChargeId(Integer additionalChargeId) {
		this.additionalChargeId = additionalChargeId;
	}

	public String getAdditionalChargeName() {
		return additionalChargeName;
	}

	public void setAdditionalChargeName(String additionalChargeName) {
		this.additionalChargeName = additionalChargeName;
	}

	public double getAdditionalChargeAmount() {
		return additionalChargeAmount;
	}

	public void setAdditionalChargeAmount(double additionalChargeAmount) {
		this.additionalChargeAmount = additionalChargeAmount;
	}
	
	

}
