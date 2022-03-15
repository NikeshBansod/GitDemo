package com.reliance.gstn.filter;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.safety.Whitelist;

public class RequestValidator {

	private boolean isXSSExist = false;

	HttpServletRequest request;

	public RequestValidator(HttpServletRequest request) {
		this.request = request;
	}

	public boolean isXSSExist() {
		return isXSSExist;
	}

	public void setXSSExist(boolean isXSSExist) {
		this.isXSSExist = isXSSExist;
	}
	
	
	static class EncodedRequest extends HttpServletRequestWrapper {


        public EncodedRequest(ServletRequest request) {
            super((HttpServletRequest)request);
        }


        public String getParameter(String paramName) {
            String value = super.getParameter(paramName);
            value = getSanitizeValue(value);
            return value;
        }

        public String[] getParameterValues(String paramName) {
            String values[] = super.getParameterValues(paramName);
            
                for (int index = 0; index < values.length; index++) {
                    values[index] = getSanitizeValue(values[index]);
                }
           
            return values;
        }
        
        private String getSanitizeValue(String value) {
    		if (value != null) {
    			//Document.OutputSettings outputSettings = new Document.OutputSettings();
    			//outputSettings.escapeMode(EscapeMode.xhtml);
    			//outputSettings.prettyPrint(false);
    			//value = Jsoup.parse(value).text();
    			//value = Jsoup.clean(value, "", Whitelist.none(), outputSettings);
    			
    			/*for addAtribute method*/
    			value = value.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
    			  Whitelist customwhitelist1 = new Whitelist();
    			  customwhitelist1.addTags("b","br", 
    		                "div", "em", "h1", "h2", "h3", "h4", "h5", "h6",
    		                "i", "li", "ol", "p",  "small", "strike", "strong",
    		                "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u",
    		                "ul");
    			  customwhitelist1.addAttributes("ul", "type");
    			  customwhitelist1.addAttributes("li", "type");
    			  customwhitelist1.addAttributes("ol", "type");
    			  value = Jsoup.clean(value, customwhitelist1);
    			  
    		}

    		return value;
    	}
    }

	public void validateRequestParameter() {
		@SuppressWarnings("unchecked")
		Map<String, String[]> parameterMap = request.getParameterMap();
		Iterator<String> keyparams = parameterMap.keySet().iterator();
		outer: while (keyparams.hasNext()) {
			String[] params = parameterMap.get(keyparams.next());
			for (String param : params) {
				//System.out.println("param==" + param);
				if (checkXSS(param)) {
					isXSSExist = true;
					break outer;
				}
			}
		}

	}


	private boolean checkXSS(String value) {
		boolean isXSS = false;
		if (value != null) {
			Document.OutputSettings outputSettings = new Document.OutputSettings();
			outputSettings.escapeMode(EscapeMode.xhtml);
			outputSettings.prettyPrint(false);
			String encodedValue = Jsoup.clean(value, "", Whitelist.none(),
					outputSettings);
			isXSS = value.equals(encodedValue);
		}
		return !isXSS;

	}

	public static void main(String[] args) {
		// String
		// value="&lt;img src=&quot;&quot; onmouseover=&quot;alert(&quot;87&quot;)&quot;&gt;";
		String value = "<script>alert('123')  abbcdd";
		Document.OutputSettings outputSettings = new Document.OutputSettings();
		outputSettings.escapeMode(EscapeMode.xhtml);
		outputSettings.prettyPrint(false);
		value = Jsoup.parse(value).text();
		String encodedValue = Jsoup.clean(value, "", Whitelist.none(),
				outputSettings);

		boolean isXSS = value.equals(encodedValue);
		System.out.println("value==" + value + " encoded value ="
				+ encodedValue + " isXSS=" + isXSS);
		// System.out.println();
	}

}
