package com.reliance.gstn.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.reliance.gstn.admin.model.AdminLoginMaster;
import com.reliance.gstn.admin.model.AdminUserMaster;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.UserMaster;

public class LoginUtil {

	public static boolean addUser(HttpServletRequest httpRequest,LoginMaster lb){
		boolean isUserAdded=false;
		HttpSession session = httpRequest.getSession();
		session.setAttribute("loginUser", lb);
		isUserAdded=true;
		return isUserAdded;
	}
	
	public static LoginMaster getLoginUser(HttpServletRequest httpRequest){
		HttpSession session = httpRequest.getSession();
		LoginMaster loginMaster = (LoginMaster)session.getAttribute(GSTNConstants.LOGIN_USER);
		return loginMaster;
	}
	
	public static boolean isUserLogin(HttpServletRequest httpRequest){
		boolean isUserLogin=false;
		
		HttpSession session = httpRequest.getSession();
		LoginMaster loginMaster = (LoginMaster)session.getAttribute(GSTNConstants.LOGIN_USER);
		if(null!=loginMaster && !loginMaster.getUserId().trim().equals("")){
			isUserLogin=true;
		}
		return isUserLogin;
	}
	
	public static void setLoginUser(HttpServletRequest httpRequest, UserMaster user,String calledFrom){
		if("LOGIN".equals(calledFrom)){
			createNewSessionAfterLogin(httpRequest);
		}
		
		HttpSession session = httpRequest.getSession(true);
		LoginMaster loginMaster = new LoginMaster();
		loginMaster.setuId(user.getId());
		loginMaster.setUserId(user.getUserId());
		loginMaster.setUserRole(user.getUserRole());
		if(user.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
			loginMaster.setPrimaryUserUId(user.getId());
		}else{
			loginMaster.setPrimaryUserUId(user.getReferenceId());
		}
	//	loginMaster.setBillingFor(user.getOrganizationMaster().getBillingFor());
		loginMaster.setOrgUId(user.getOrganizationMaster().getId());
		loginMaster.setTermsConditionsFlag(user.getOrganizationMaster().getTermsConditionsFlagHidden());
		loginMaster.setUniqueSequence(user.getUniqueSequence());
		loginMaster.setLogoImagePath(user.getOrganizationMaster().getLogoImagePath());
		loginMaster.setPanNo(user.getOrganizationMaster().getPanNumber()); 
		loginMaster.setFirmName(user.getOrganizationMaster().getOrgName());
		loginMaster.setImeiNo(user.getIMEINo());
		loginMaster.setDataSend(user.getDataSend());
		//loginMaster.setLoggedInThrough("MOBILE");
		loginMaster.setLoggedInThrough(user.getLoggedInDevice());
		if(StringUtils.isNotEmpty(user.getOrganizationMaster().getFooter())){
			loginMaster.setFooter(user.getOrganizationMaster().getFooter());
		}
		loginMaster.setInvoiceSequenceType(user.getInvoiceSequenceType());
		session.setAttribute(GSTNConstants.LOGIN_USER, loginMaster);
	}

	public static String getIdsToFetchData(LoginMaster loginMaster) {
		String idsValuesToFetch = null;
		if(loginMaster != null){
			if(loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
				//add value of primary users id and also secondary users id
			}else{
				//fetch id of primary user and then fetch the secondary ids assigned to them
			}
		}
		return idsValuesToFetch;
	}
	
	public static void createNewSessionAfterLogin(HttpServletRequest request){
		
		HttpSession session = request.getSession(false);
		if (session!=null && !session.isNew()) {
		    session.invalidate();
		}
		
	}

	public static void setAdminLoginUser(HttpServletRequest httpRequest, AdminUserMaster user) {
		createNewSessionAfterLogin(httpRequest);
		HttpSession session = httpRequest.getSession(true);
		AdminLoginMaster loginMaster = new AdminLoginMaster();
		loginMaster.setuId(user.getId());
		loginMaster.setUserId(user.getUserId());
		loginMaster.setUserRole(user.getUserRole());
		session.setAttribute(GSTNConstants.ADMIN_LOGIN_USER, loginMaster);
		
	}
	
	
	public static boolean isAdminUserLogin(HttpServletRequest httpRequest){
		boolean isUserLogin=false;
		
		HttpSession session = httpRequest.getSession();
		AdminLoginMaster loginMaster = (AdminLoginMaster)session.getAttribute(GSTNConstants.ADMIN_LOGIN_USER);
		if(null!=loginMaster && !loginMaster.getUserId().trim().equals("")){
			isUserLogin=true;
		}
		return isUserLogin;
	}
	
	public static AdminLoginMaster getAdminLoginUser(HttpServletRequest httpRequest){
		HttpSession session = httpRequest.getSession();
		AdminLoginMaster loginMaster = (AdminLoginMaster)session.getAttribute(GSTNConstants.ADMIN_LOGIN_USER);
		return loginMaster;
	}
	
	public static void destroyUserSession(HttpServletRequest httpRequest){
		HttpSession session = httpRequest.getSession(false);
		if (httpRequest.isRequestedSessionIdValid() && session != null) {
			session.removeAttribute(GSTNConstants.LOGIN_USER);
			session.invalidate();
			session=null;
		}
	}
	

	public static void setLoginUserFromWizard(HttpServletRequest httpRequest, UserMaster user){
		createNewSessionAfterLogin(httpRequest);
		HttpSession session = httpRequest.getSession(true);
		LoginMaster loginMaster = new LoginMaster();
		loginMaster.setuId(user.getId());
		loginMaster.setUserId(user.getUserId());
		loginMaster.setUserRole(user.getUserRole());
		if(user.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
			loginMaster.setPrimaryUserUId(user.getId());
		}else{
			loginMaster.setPrimaryUserUId(user.getReferenceId());
		}
	//	loginMaster.setBillingFor(user.getOrganizationMaster().getBillingFor());
		loginMaster.setOrgUId(user.getOrganizationMaster().getId());
		loginMaster.setTermsConditionsFlag(user.getOrganizationMaster().getTermsConditionsFlagHidden());
		loginMaster.setUniqueSequence(user.getUniqueSequence());
		loginMaster.setLogoImagePath(user.getOrganizationMaster().getLogoImagePath());
		loginMaster.setPanNo(user.getOrganizationMaster().getPanNumber()); 
		loginMaster.setFirmName(user.getOrganizationMaster().getOrgName());
		loginMaster.setLoggedInByWizard(true);
		loginMaster.setLoggedInThrough("WIZARD");
		if(StringUtils.isNotEmpty(user.getOrganizationMaster().getFooter())){
			loginMaster.setFooter(user.getOrganizationMaster().getFooter());
		}
		session.setAttribute(GSTNConstants.LOGIN_USER, loginMaster);
	}

}
