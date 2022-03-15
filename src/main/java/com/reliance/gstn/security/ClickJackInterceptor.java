package com.reliance.gstn.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ClickJackInterceptor  extends HandlerInterceptorAdapter{

	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String mode="DENY";
		response.addHeader("X-FRAME-OPTIONS", mode );
		return true;
	}
}
