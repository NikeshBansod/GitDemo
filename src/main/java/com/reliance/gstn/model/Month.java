/**
 * 
 */
package com.reliance.gstn.model;

/**
 * @author Sagar.Parab
 *
 */
public class Month {
	String monthVal;
	String monthDesc;
	public String getMonthVal() {
		return monthVal;
	}
	public void setMonthVal(String monthVal) {
		this.monthVal = monthVal;
	}
	public String getMonthDesc() {
		return monthDesc;
	}
	public void setMonthDesc(String monthDesc) {
		this.monthDesc = monthDesc;
	}
	public Month(String monthVal, String monthDesc) {
		super();
		this.monthVal = monthVal;
		this.monthDesc = monthDesc;
	}
	
	
	
	

}
