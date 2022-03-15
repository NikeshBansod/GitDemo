package com.reliance.gstn.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.safety.Whitelist;

public class RequestWrapper extends HttpServletRequestWrapper {
	
	private boolean isXSSExist=false;
	
	public RequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}
	
	public boolean isXSSExist() {
		return isXSSExist;
	}

	public void setXSSExist(boolean isXSSExist) {
		this.isXSSExist = isXSSExist;
	}

    private String checkXSS( String value )
    {
    	String encodedValue ="";
        if( value != null ){
            Document.OutputSettings outputSettings = new Document.OutputSettings();
            outputSettings.escapeMode( EscapeMode.xhtml );
            outputSettings.prettyPrint( false );
            encodedValue = Jsoup.clean( value, "", Whitelist.none(), outputSettings );
            isXSSExist=value.equals(encodedValue);
        }
        return encodedValue;
           
   }



	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);

		if (values == null) {
			return null;
		}

		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = checkXSS(values[i]);
		}

		return encodedValues;
	}

	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);

		return checkXSS(value);
	}

	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		return checkXSS(value);
	}
}
