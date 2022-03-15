package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.List;
public class OpeningStockJSObjectSave implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<OpeningStockJsObject> productList;
	
	
	public List<OpeningStockJsObject> getProductList() {
		return productList;
	}
	public void setProductList(List<OpeningStockJsObject> productList) {
		this.productList = productList;
	}

}
