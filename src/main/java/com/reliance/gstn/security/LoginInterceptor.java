package com.reliance.gstn.security;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.util.EncodingDecodingUtil;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.GSTNUtil;
import com.reliance.gstn.util.InvalidSession;
//import com.reliance.gstn.util.LoggerUtil;
import com.reliance.gstn.util.LoginUtil;
import com.reliance.gstn.util.PageRedirectConstants;

/**
 * @author Vivek2.Dubey
 *
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = Logger
			.getLogger(LoginInterceptor.class);

	@Value("${SKIP_URL}")
	private String skipUrl;

	@Value("${SECONDARY_ACCESS_DENIED_URL}")
	private String secondaryADUrl;
	
	@Value("${ADMIN_URL_LIST}")
	private String adminUrlList;
	
	@Value("${NON_SECURE_URL}")
	private String nonSecureUrl;
	
	@Value("${CSRF_CHECK_URL}")
	private String csrfCheckUrl;
	
	@Value("${CSRF_NO_TOKEN_GEN_URL}")
	private String noTokenGenUrl;
	
	@Value("${UNIFICATION_URL}")
	private String unificationUrl;

	private static List<String> skipUrlL = new ArrayList<String>();
	private static List<String> secondaryADUrlL = new ArrayList<String>();
	private static List<String> adminUrlL = new ArrayList<String>();
	private static List<String> csrfUrlL = new ArrayList<String>();
	private static List<String> noTokenUrlL = new ArrayList<String>();
	private static List<String> unificationUrlL = new ArrayList<String>();

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.info("Entry");
		boolean expectedFlow = true;
		boolean isIvalidCsrfToken=false;
		boolean isNewTokenGenUrl=true;
		
		PrintWriter pw=null;
		InvalidSession invalidSession = new InvalidSession();
		
		
		if (skipUrlL.isEmpty() || secondaryADUrlL.isEmpty()||adminUrlL.isEmpty()||csrfUrlL.isEmpty() || unificationUrlL.isEmpty()) {
			intUrlList(request);
		}

		String uri = request.getRequestURI();
		boolean isNonSecure=isNonSecureUrl(uri);
		String lastURIPart = getLastURIPart(uri);
		
		String clientIpAddress = GSTNUtil.getClientIpAddress(request);
		
		logger.debug("uri=" + uri + " lastURIPart=" + lastURIPart+ " client ip address= " + clientIpAddress);
		if (skipUrlL.contains(uri) || skipUrlL.contains(lastURIPart) || isNonSecure || unificationUrlL.contains(lastURIPart)) {
			expectedFlow = true;
			
		}else if (isAdminUser(lastURIPart) && LoginUtil.isAdminUserLogin(request)) {
			expectedFlow = true;
		} else if (LoginUtil.isUserLogin(request)) {
			LoginMaster lm = LoginUtil.getLoginUser(request);
			logger.debug("login user =" + lm.getUserId() + " ,User type=" + lm.getUserRole());
			if (lm.getUserRole().equals(GSTNConstants.SECONDARY_USER) && secondaryADUrl.contains(lastURIPart)) {
				expectedFlow = false;
			} else {
				expectedFlow = true;
			}
		} else {
			expectedFlow = false;
		}
		
		if(csrfUrlL.contains(lastURIPart) && request.getMethod().equalsIgnoreCase("POST") && isInvaidCsrfToken(request)){
			isIvalidCsrfToken=true;
		}
		
		
		if(noTokenUrlL.contains(lastURIPart)){
			isNewTokenGenUrl=false;
		}

		logger.debug("expectedFlow=" + expectedFlow);
		
		 if(expectedFlow && isIvalidCsrfToken){
			 logger.debug("Invalid csrf token " + uri);
			boolean isAjaxRequest = isAjaxRequest(request);
			if (isAjaxRequest) {
				//InvalidSession invalidSession = new InvalidSession();
				invalidSession.setIsTokenValid("Invalid Request");	
				//pw.write(new Gson().toJson(invalidSession));
			} else {
				response.sendRedirect(PageRedirectConstants.INVALID_TOKEN_PAGE);
			}
			 
		 }else if (!expectedFlow) {
			logger.debug("Page access fail for " + uri);
			boolean isAjaxRequest = isAjaxRequest(request);
			if (isAjaxRequest) {
				//PrintWriter pw = response.getWriter();
				
				invalidSession.setIsSessionValid("No");	
				//pw.write(new Gson().toJson(invalidSession));
				
			} else {
				//if(request.getHeader("User-Agent").indexOf("Mobile") != -1) {
					response.sendRedirect(PageRedirectConstants.LOGIN_PAGE);
				 /* } else {
					  response.sendRedirect(PageRedirectConstants.WIZARD_LOGIN_PAGE);
				  }*/
				
			}
		}
		 String referrer = request.getHeader("referer");
		 logger.info("Request from Referrer :"+referrer);
		 
		 if(null!=invalidSession.getIsSessionValid() || null!=invalidSession.getIsTokenValid()){
			 pw = response.getWriter();
			 pw.write(new Gson().toJson(invalidSession));
			 expectedFlow=false;
		 }else if(isNewTokenGenUrl){
			 setNewCsrfToken(request,response);
		 }
		 
		logger.info("Exit");
		return expectedFlow;
	}

	private String getLastURIPart(String uri) {
		int index = uri.lastIndexOf("/");
		String lastURIPart = "";
		if (index >= 0) {
			lastURIPart = uri.substring(index);
		}

		return lastURIPart;
	}

	private void intUrlList(HttpServletRequest request) {
		skipUrlL.addAll(Arrays.asList(skipUrl.split(",")));
		skipUrlL.add(request.getContextPath() + "/");
		secondaryADUrlL.addAll(Arrays.asList(secondaryADUrl.split(",")));
		adminUrlL.addAll(Arrays.asList(adminUrlList.split(",")));
		csrfUrlL.addAll(Arrays.asList(csrfCheckUrl.split(",")));
		noTokenUrlL.addAll(Arrays.asList(noTokenGenUrl.split(",")));
		unificationUrlL.addAll(Arrays.asList(unificationUrl.split(",")));
	}

	private boolean isAjaxRequest(HttpServletRequest httpReq) {
		return "XMLHttpRequest".equals(httpReq.getHeader("X-Requested-With"));
	}
	
	private boolean isAdminUser(String lastPartUri){
		boolean isAdminUser=false;
		if(adminUrlL.contains(lastPartUri)){
			isAdminUser=true;
		}
		return isAdminUser;
	}
	
	private boolean isNonSecureUrl(String uri){
		boolean isNonSecure=false;
		if(uri.contains(nonSecureUrl)){
			isNonSecure=true;
		}
		return isNonSecure;
	}
	
	private boolean isInvaidCsrfToken(HttpServletRequest httpReq){
		boolean isInvaidCsrfToken=false;
		String storedCsrfToken=(String)httpReq.getSession().getAttribute("_csrf_token");
		String requestCsrfToken=getCsrfToken(httpReq);
		
		if(null==requestCsrfToken || !storedCsrfToken.equals(requestCsrfToken)){
			isInvaidCsrfToken=true;
		}
		return isInvaidCsrfToken;
	}
	
	
	private String getCsrfToken(HttpServletRequest httpReq){
		String requestCsrfToken=httpReq.getParameter("_csrf_token")==null?httpReq.getHeader("_csrf_token"):httpReq.getParameter("_csrf_token");
		return requestCsrfToken;
	}
	
	
	
	private void setNewCsrfToken(HttpServletRequest httpReq,HttpServletResponse response){
		StringBuffer sb=new StringBuffer(String.valueOf(System.nanoTime()));
		sb.append(UUID.randomUUID());
		LoginMaster lm = LoginUtil.getLoginUser(httpReq);
		if(null!=lm && null!=lm.getUserName()){
			sb.append(lm.getUserName());
		}
		
		String csrfToken=EncodingDecodingUtil.encodeString(sb.toString());
		System.out.println("++++++++++++++"+getLastURIPart(httpReq.getRequestURI())+"++++++++++++++++++++++++++++++++++++++++++++++");
		logger.debug("encoded csrf token="+csrfToken+", csrf token="+sb);
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		httpReq.getSession().setAttribute("_csrf_token",csrfToken);
		response.setHeader("_csrf_token",csrfToken);
	}
}
