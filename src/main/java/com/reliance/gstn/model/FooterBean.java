package com.reliance.gstn.model;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

public class FooterBean {
	
	@SafeHtml(message = "HTML elements not allowed")
	private String footer;

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}
	
	

}
