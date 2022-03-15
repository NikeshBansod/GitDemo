package com.reliance.gstn.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.reliance.gstn.util.PageRedirectConstants;

public class HttpRequestTypeInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = Logger
			.getLogger(HttpRequestTypeInterceptor.class);
	@Value("${HTTP_SUPPORTED_METHODS}")
	private String httpSupportedMethods;
	
	private static List<String> httpSupportedMethodsL = new ArrayList<String>();
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.info("Entry");
		boolean supportedHttpMethod=true;
		if(httpSupportedMethodsL.isEmpty()){
			initRequest();
		}
		
		if (!httpSupportedMethodsL.contains(request.getMethod().toUpperCase())) {
			supportedHttpMethod = false;
		}
		
		if(!supportedHttpMethod){
			response.sendRedirect(PageRedirectConstants.ERROR_PAGE);
		}
		
		logger.info("Exit");
		return supportedHttpMethod;
	}
	
	private void initRequest(){
		httpSupportedMethodsL.addAll(Arrays.asList(httpSupportedMethods.split(",")));
	}
	

}
