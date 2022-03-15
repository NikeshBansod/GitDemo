/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

/**
 * @author Dibyendu.Mukherjee
 *
 */
public class ProductBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1908573611723984150L;

	@NotEmpty(message = "Product Name cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	private String name;

	@NotEmpty(message = "HSN Code cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	private String hsnCode;

	@NotEmpty(message = "HSN Description cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	private String hsnDescription;

	@NotEmpty(message = "Unit Of Measurement cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	private String unitOfMeasurement;

	@SafeHtml(message = "HTML elements not allowed")
	private String otherUOM;

	private Double productRate;

	private Double productIgst;

	private Integer hsnCodePkId;

	private String tempUom;

	private Double advolCess;

	private Double nonAdvolCess;

	private Double purchaseRate;

	@NotEmpty(message = "Unit Of Measurement cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	private String purchaseUOM;
	
	private String purchaseOtherUOM;

	
	@NotEmpty(message = "Opening Stock cannot be empty")
	List<OpeningStockBean> openingStockBean = new ArrayList<OpeningStockBean>();

	/*
	 * "referenceId": 3927, "createdOn": "2019-01-21T09:57:19.000+0000",
	 * "createdBy": "3927", "updatedOn": null, "updatedBy": null, "status": "1",
	 * "refOrgId": 205,
	 */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}

	public String getHsnDescription() {
		return hsnDescription;
	}

	public void setHsnDescription(String hsnDescription) {
		this.hsnDescription = hsnDescription;
	}

	public String getUnitOfMeasurement() {
		return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(String unitOfMeasurement) {
		this.unitOfMeasurement = unitOfMeasurement;
	}

	public String getOtherUOM() {
		return otherUOM;
	}

	public void setOtherUOM(String otherUOM) {
		this.otherUOM = otherUOM;
	}

	public Double getProductRate() {
		return productRate;
	}

	public void setProductRate(Double productRate) {
		this.productRate = productRate;
	}

	public Double getProductIgst() {
		return productIgst;
	}

	public void setProductIgst(Double productIgst) {
		this.productIgst = productIgst;
	}

	public Integer getHsnCodePkId() {
		return hsnCodePkId;
	}

	public void setHsnCodePkId(Integer hsnCodePkId) {
		this.hsnCodePkId = hsnCodePkId;
	}

	public String getTempUom() {
		return tempUom;
	}

	public void setTempUom(String tempUom) {
		this.tempUom = tempUom;
	}

	public Double getAdvolCess() {
		return advolCess;
	}

	public void setAdvolCess(Double advolCess) {
		this.advolCess = advolCess;
	}

	public Double getNonAdvolCess() {
		return nonAdvolCess;
	}

	public void setNonAdvolCess(Double nonAdvolCess) {
		this.nonAdvolCess = nonAdvolCess;
	}

	public Double getPurchaseRate() {
		return purchaseRate;
	}

	public void setPurchaseRate(Double purchaseRate) {
		this.purchaseRate = purchaseRate;
	}

	public String getPurchaseUOM() {
		return purchaseUOM;
	}

	public void setPurchaseUOM(String purchaseUOM) {
		this.purchaseUOM = purchaseUOM;
	}

	public String getPurchaseOtherUOM() {
		return purchaseOtherUOM;
	}

	public void setPurchaseOtherUOM(String purchaseOtherUOM) {
		this.purchaseOtherUOM = purchaseOtherUOM;
	}

	public List<OpeningStockBean> getOpeningStockBean() {
		return openingStockBean;
	}

	public void setOpeningStockBean(List<OpeningStockBean> openingStockBean) {
		this.openingStockBean = openingStockBean;
	}





}
