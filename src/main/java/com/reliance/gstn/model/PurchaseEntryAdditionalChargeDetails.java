package com.reliance.gstn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.SafeHtml;
/**
 * @author @kshay Mohite
 *
 */
@Entity
@Table(name = "gst_purchase_entry_additional_charges_details")
public class PurchaseEntryAdditionalChargeDetails implements Serializable {

	private static final long serialVersionUID = -5070472579923390494L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchase_entry_additional_charge_id")
	private Integer purchaseEntryAdditionalChargeId;
	
	@Column(name = "add_charge_id")
	private Integer additionalChargeId;
	
	@SafeHtml(message = "HTML elements not allowed")
	@Column(name = "add_charge_name")
	private String additionalChargeName;
	
	@Column(name = "add_charge_amount")
	private double additionalChargeAmount;

	public Integer getId() {
		return purchaseEntryAdditionalChargeId;
	}

	public void setId(Integer purchaseEntryAdditionalChargeId) {
		this.purchaseEntryAdditionalChargeId = purchaseEntryAdditionalChargeId;
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
