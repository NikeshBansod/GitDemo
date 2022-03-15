/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

/**
 * @author Dibyendu.Mukherjee
 *
 */
public class ProductGstinLocationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3954556488408014504L;
	
	
	private Integer id;

	private Integer openingStock;
	
	private Double openingStockValue;

	private Integer currentStock;

	@Column(name = "CURRENT_STOCK_VALUE")
	private Double currentStockValue;

	@Column(name = "INVENTORY_UPDATE")
	private String inventoryUpdateFlag;

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
	
	private Integer refUserId;

	private List <GstinLocation> gstinlocationbean= new ArrayList <>();
	public Integer getRefUserId() {
		return refUserId;
	}

	public void setRefUserId(Integer refUserId) {
		this.refUserId = refUserId;
	}

	@NotEmpty(message = "Unit Of Measurement cannot be empty")
	@SafeHtml(message = "HTML elements not allowed")
	private String purchaseUOM;
	
	private String purchaseOtherUOM;

	
	@SafeHtml(message = "HTML elements not allowed")
	private String gstinLocation;
	
	@SafeHtml(message = "HTML elements not allowed")
	private String uniqueSequence;
	

	
	@SafeHtml(message = "HTML elements not allowed")
	private String gstinStore;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOpeningStock() {
		return openingStock;
	}

	public void setOpeningStock(Integer openingStock) {
		this.openingStock = openingStock;
	}

	public Double getOpeningStockValue() {
		return openingStockValue;
	}

	public void setOpeningStockValue(Double openingStockValue) {
		this.openingStockValue = openingStockValue;
	}

	public Integer getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(Integer currentStock) {
		this.currentStock = currentStock;
	}

	public Double getCurrentStockValue() {
		return currentStockValue;
	}

	public void setCurrentStockValue(Double currentStockValue) {
		this.currentStockValue = currentStockValue;
	}

	public String getInventoryUpdateFlag() {
		return inventoryUpdateFlag;
	}

	public void setInventoryUpdateFlag(String inventoryUpdateFlag) {
		this.inventoryUpdateFlag = inventoryUpdateFlag;
	}

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

	public String getGstinLocation() {
		return gstinLocation;
	}

	public void setGstinLocation(String gstinLocation) {
		this.gstinLocation = gstinLocation;
	}

	public String getUniqueSequence() {
		return uniqueSequence;
	}

	public void setUniqueSequence(String uniqueSequence) {
		this.uniqueSequence = uniqueSequence;
	}

	public String getGstinStore() {
		return gstinStore;
	}

	public void setGstinStore(String gstinStore) {
		this.gstinStore = gstinStore;
	}

	public List<GstinLocation> getGstinlocationbean() {
		return gstinlocationbean;
	}

	public void setGstinlocationbean(List<GstinLocation> gstinlocationbean) {
		this.gstinlocationbean = gstinlocationbean;
	}
	
}
