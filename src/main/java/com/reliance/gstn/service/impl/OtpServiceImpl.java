package com.reliance.gstn.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.reliance.gstn.model.OtpBean;
import com.reliance.gstn.model.OtpVerificationDetails;
import com.reliance.gstn.service.OtpService;
import com.reliance.gstn.util.TimeUtil;

@Service
public class OtpServiceImpl implements OtpService {

	private static final Logger logger = Logger.getLogger(OtpServiceImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${${env}.SEND_OTP_URL}")
	private String sendOtpUrl;
	
	@Value(value = "${get_otp_rec}")
	private String getOtpRecords;
	
	@Value(value = "${add_otp_rec}")
	private String addOtpDetails;
	
	@Value("${no_of_otp}")
	private String otpNos;

	@Value("${otp_idle_duration_min}")
	private String otpIdleTimeMin;
	
	@Value("${otp_idle_duration_hour}")
	private String otpIdleTimeHr;
	
	
	@Override
	@Transactional
	public Map<Boolean, String> sendOtpToRegisteredUser(String userId,String userMobNo,HttpServletRequest request) throws IOException {
		boolean otpStatus=false;
		Integer noOfAttempt = 1;
		Map<Boolean, String> otpData = new HashMap<Boolean, String>();
		Session session = sessionFactory.getCurrentSession();
		try{
			Query query = session.createQuery(getOtpRecords);
			query.setString("mobileNo", userMobNo);
			
			@SuppressWarnings("unchecked")
			List<OtpVerificationDetails> otpRecordsList = (List<OtpVerificationDetails>)query.list();
				
			if(otpRecordsList.isEmpty()){
				OtpVerificationDetails otpVerificationDetails = new OtpVerificationDetails();
				addOtpRecord(userMobNo, noOfAttempt, session, request, otpVerificationDetails);
				otpStatus=true;
				otpData.put(otpStatus, "OTP has been sent to Mobile No : "+userMobNo+". Kindly Verify");
				
				request.getSession().setAttribute("userId", userId);
			} else {
				for(OtpVerificationDetails details : otpRecordsList){
					
					Integer counter = details.getNoOfAttempts();
					Integer otpCount = Integer.parseInt(otpNos);
					if(counter < otpCount){
						OtpBean otpBean = sendOtpToVerifiedNo(userMobNo);
						String otpNo = details.getOtp();
						counter ++;
						otpNo = otpNo+" , "+otpBean.getOtpNo();
						details.setOtpTime(new java.sql.Timestamp(new Date().getTime()));
						details.setNoOfAttempts(counter);
						details.setOtp(otpNo);
						session.saveOrUpdate(details);
						request.getSession().setAttribute("otpBean", otpBean);
						otpStatus=true;
						otpData.put(otpStatus, "OTP has been sent to Mobile No : "+userMobNo+". Kindly Verify to complete Registration");
					} else {
						long diffMinutes = TimeUtil.getTimeDiffFromCurrentTimeInLong(details.getOtpTime().getTime(), "minutes");
						Integer blockTimeinMin = Integer.parseInt(otpIdleTimeMin);
						if(diffMinutes >= blockTimeinMin){
							addOtpRecord(userMobNo, noOfAttempt, session, request, details);
							otpStatus=true;
							otpData.put(otpStatus, "OTP has been sent to Mobile No : "+userMobNo+". Kindly Verify to complete Registration");
						} else {
							otpStatus=false;
							String alertMsg = "You will not receive any OTP. Please try after "+(blockTimeinMin - diffMinutes)+" minutes";
							otpData.put(otpStatus, alertMsg);
						}
					}
				}
				request.getSession().setAttribute("userId", userId);
			}
			
		} catch (Exception e) {
			logger.error("Error in", e);
		}
		
		return otpData;
	}

	@Override
	@Transactional
	public Map<Boolean, String> sendOtpOnRegistration(String userMobileNo, HttpServletRequest request) throws Exception {
		Map<Boolean, String> otpData = new HashMap<Boolean, String>();
		boolean otpStatus=false;
		Integer noOfAttempt = 1;
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(getOtpRecords);
			query.setString("mobileNo", userMobileNo);

			@SuppressWarnings("unchecked")
			List<OtpVerificationDetails> otpRecordsList = (List<OtpVerificationDetails>)query.list();
						
			if(otpRecordsList.isEmpty()){
				OtpVerificationDetails otpVerificationDetails = new OtpVerificationDetails();
				addOtpRecord(userMobileNo, noOfAttempt, session, request, otpVerificationDetails);
				otpStatus=true;
				otpData.put(otpStatus, "OTP has been sent to Mobile No : "+userMobileNo+". Kindly Verify to complete Registration");
			} else {
					for(OtpVerificationDetails details : otpRecordsList){
						
						Integer counter = details.getNoOfAttempts();
						Integer otpCount = Integer.parseInt(otpNos);
						if(counter < otpCount){
							OtpBean otpBean = sendOtpToVerifiedNo(userMobileNo);
							String otpNo = details.getOtp();
							counter ++;
							otpNo = otpNo+" , "+otpBean.getOtpNo();
							details.setOtpTime(new java.sql.Timestamp(new Date().getTime()));
							details.setNoOfAttempts(counter);
							details.setOtp(otpNo);
							session.saveOrUpdate(details);
							request.getSession().setAttribute("otpBean", otpBean);
							otpStatus=true;
							otpData.put(otpStatus, "OTP has been sent to Mobile No : "+userMobileNo+". Kindly Verify to complete Registration");
						} else {
							long diffMinutes = TimeUtil.getTimeDiffFromCurrentTimeInLong(details.getOtpTime().getTime(), "minutes");
							Integer blockTimeinMin = Integer.parseInt(otpIdleTimeMin);
							if(diffMinutes >= blockTimeinMin){
								addOtpRecord(userMobileNo, noOfAttempt, session, request, details);
								otpStatus=true;
								otpData.put(otpStatus, "OTP has been sent to Mobile No : "+userMobileNo+". Kindly Verify to complete Registration");
							} else {
								otpStatus=false;
								String alertMsg = "You will not receive any OTP. Please try after "+(blockTimeinMin - diffMinutes)+" minutes";
								otpData.put(otpStatus, alertMsg);
							}
						}
					}
				}
			
		}catch (Exception e) {
			logger.error("Error in", e);
		}
		return otpData;
	}
	
	@Override
	@Transactional
	public void removeOtpRecord(String userMobileNo, HttpServletRequest request) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(getOtpRecords);
			query.setString("mobileNo", userMobileNo);

			@SuppressWarnings("unchecked")
			List<OtpVerificationDetails> otpRecordsList = (List<OtpVerificationDetails>)query.list();
						
			if(!otpRecordsList.isEmpty()){
				for(OtpVerificationDetails details : otpRecordsList){
					session.delete(details); 
				}
			}
		} catch (Exception e) {
			logger.error("Error in", e);
		}
	}
	
	
	@Override
	@Transactional
	public Integer countVerificationOtp(String userMobileNo, HttpServletRequest request) throws Exception {

		Integer otpCount=0;
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery(getOtpRecords);
			query.setString("mobileNo", userMobileNo);

			@SuppressWarnings("unchecked")
			List<OtpVerificationDetails> otpRecordsList = (List<OtpVerificationDetails>)query.list();
						
			if(otpRecordsList.isEmpty()){
				otpCount=0;
			} else {
					for(OtpVerificationDetails details : otpRecordsList){
						Integer counter = details.getOtpCounter();
						Integer otpNumbar = Integer.parseInt(otpNos);
						if(counter < otpNumbar){
							counter ++;
							details.setOtpCounter(counter);
							session.saveOrUpdate(details);
						} else {
							
							long diffMinutes = TimeUtil.getTimeDiffFromCurrentTimeInLong(details.getOtpTime().getTime(), "minutes");
							Integer blockTimeinMin = Integer.parseInt(otpIdleTimeMin);
							if(diffMinutes >= blockTimeinMin){
								details.setOtpCounter(1);
								session.saveOrUpdate(details);
							} else {
								otpCount = details.getOtpCounter();
							}
						}
					}
				}
			
		}catch (Exception e) {
			logger.error("Error in", e);
		}
		return otpCount;
	}
	
	
	public void addOtpRecord(String userMobileNo, Integer noOfAttempt, Session session, HttpServletRequest request, OtpVerificationDetails otpVerificationDetails) throws Exception{
		OtpBean otpBean = sendOtpToVerifiedNo(userMobileNo);
		if(otpBean.getOtpNo()!=null){
			
			otpVerificationDetails.setMobileNo(otpBean.getMobileNo());
			otpVerificationDetails.setOtp(otpBean.getOtpNo());
			otpVerificationDetails.setOtpTime(new java.sql.Timestamp(new Date().getTime()));
			otpVerificationDetails.setNoOfAttempts(noOfAttempt);
			otpVerificationDetails.setOtpCounter(0);
			session.saveOrUpdate(otpVerificationDetails);
			request.getSession().setAttribute("otpBean", otpBean);
			logger.info("MobileNo : "+otpBean.getMobileNo() + "- OTP : "+otpBean.getOtpNo());
		
		}
	}
		
		
	public OtpBean sendOtpToVerifiedNo(String userMobileNo) throws Exception{
		BufferedReader in = null;
		StringBuffer response;
		OtpBean otpBean = null;
		try{
		String USER_AGENT = "Mozilla/5.0";
		URL obj = new URL(sendOtpUrl+userMobileNo);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setDoOutput(true);

		in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		response = new StringBuffer();
	
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		Gson gson = new Gson();
		otpBean = gson.fromJson(response.toString(), OtpBean.class);
		} catch (Exception e) {
			logger.error("Error in", e);
			throw e;
		} finally{
			if(in!=null){
				in.close();
			}
		}
		return otpBean;
	}
	
}
