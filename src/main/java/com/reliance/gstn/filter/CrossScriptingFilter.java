/**
 * 
 */
package com.reliance.gstn.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.reliance.gstn.filter.RequestValidator.EncodedRequest;
import com.reliance.gstn.util.InvalidSession;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Nikesh.Bansod
 *
 */
public class CrossScriptingFilter implements Filter {

	private static Logger logger = Logger.getLogger(CrossScriptingFilter.class);

	List<String> excludeUrls = null;

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		// TODO Auto-generated method stub
		excludeUrls = Arrays.asList(cfg.getInitParameter("excludeurls").split(","));
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		logger.info("Entry");
		boolean expectedFlow = false;
		boolean isExcludeURL = false;
		InvalidSession invalidSession = new InvalidSession();

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		String lastPartUri = getLastURIPart(uri);

		
		RequestValidator requestValidator = new RequestValidator(req);
		
		if(excludeUrls.contains(lastPartUri)){
			logger.debug("Excluded URI ");
			isExcludeURL=true;
			expectedFlow = true;
		}else{
			requestValidator.validateRequestParameter();
			logger.debug("Request wrapped ");
			if (!requestValidator.isXSSExist()) {
				logger.debug("XSS not exist ");
				expectedFlow = true;
			}
		}
		
		
		
		
		
		if (expectedFlow) {
			
			if(isExcludeURL){
				RequestValidator.EncodedRequest encodedRequest=new EncodedRequest(request);
				request=encodedRequest;
				logger.debug("Encoded Request Processing");
			}
			
			chain.doFilter(request, response);
		} else {
			logger.debug("Page access fail for " + req.getRequestURI());
			boolean isAjaxRequest = isAjaxRequest(req);
			if (isAjaxRequest) {
				req.getSession().invalidate();
				PrintWriter pw = resp.getWriter();
				invalidSession.setIsXSSValid("No");
				pw.write(new Gson().toJson(invalidSession));

			} else {
				req.getSession().invalidate();
				//if (req.getHeader("User-Agent").indexOf("Mobile") != -1) {
					resp.sendRedirect(PageRedirectConstants.LOGIN_PAGE);
				/*} else {
					resp.sendRedirect(PageRedirectConstants.WIZARD_LOGIN_PAGE);
				}*/

			}
		}
		logger.info("Exit");
	}

	private boolean isAjaxRequest(HttpServletRequest httpReq) {
		return "XMLHttpRequest".equals(httpReq.getHeader("X-Requested-With"));
	}

	private String getLastURIPart(String uri) {
		int index = uri.lastIndexOf("/");
		String lastURIPart = "";
		if (index >= 0) {
			lastURIPart = uri.substring(index);
		}

		return lastURIPart;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
