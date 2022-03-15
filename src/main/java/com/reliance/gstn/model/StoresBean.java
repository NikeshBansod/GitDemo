/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;

/**
 * @author Dibyendu.Mukherjee
 *
 */
public class StoresBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6911396338069450913L;

	private Integer gstnId ;
	private Integer storeId ;
	
	
	public Integer getGstnId() {
		return gstnId;
	}
	public void setGstnId(Integer gstnId) {
		this.gstnId = gstnId;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	
}
