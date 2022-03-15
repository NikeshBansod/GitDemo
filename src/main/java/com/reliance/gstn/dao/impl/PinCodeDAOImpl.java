/**
 * 
 */
package com.reliance.gstn.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.dao.PinCodeDAO;
import com.reliance.gstn.model.PinCode;
//import com.reliance.gstn.util.LoggerUtil;

/**
 * @author Nikesh.Bansod
 *
 */
@Repository
public class PinCodeDAOImpl implements PinCodeDAO {
	
	private static final Logger logger = Logger.getLogger(PinCodeDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Value("${get_pincode_details_by_pinno}")
	private String getPincodeDetailsByPincodeNo;
	
	@Value("${pincode_list_query}")
	private String getPincodeListQuery;
	
	@Value("${pincode_list_by_pincode_query}")
	private String getPincodeListByPincodeQuery;
	
	@Value("${get_pincode_details_by_pinno_and_district}")
	private String getPincodeDetailsByPincodeNoAndDistrict;
	

	@Override
	public PinCode getPinCodeByPinCode(Integer pinCode) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(getPincodeDetailsByPincodeNo);
		query.setInteger("pinCode", pinCode);
		
		@SuppressWarnings("unchecked")
		List<Object[]> pinCodeList = (List<Object[]>)query.list();
		PinCode pc = null;
		if (!pinCodeList.isEmpty()) {
			pc = new PinCode();
			pc.setPinCode(Integer.parseInt(pinCodeList.get(0)[0].toString()));
			pc.setDistrict(pinCodeList.get(0)[1].toString());
			pc.setStateId(Integer.parseInt(pinCodeList.get(0)[2].toString()));
			pc.setStateInString(pinCodeList.get(0)[3].toString());
		}
		
		logger.info("Exit");
		return pc;
	}


	@Override
	public List<Integer> getPinCodeList() {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(getPincodeListQuery);
	
		@SuppressWarnings("unchecked")
		List<Integer> pinCodeList = (List<Integer>)query.list();
		
		logger.info("Exit");
		return pinCodeList;
	}


	@Override
	public List<PinCode> getPinCodeListByPincode(String pincode) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(getPincodeListByPincodeQuery);
		query.setString("pincode", pincode+"%");
		
		@SuppressWarnings("unchecked")
		List<PinCode> pinCodeList = (List<PinCode>)query.list();
		
		logger.info("Exit");
		return pinCodeList;
	}


	@Override
	public PinCode getPinCodeDetailsByPinCodeAndDistrict(Integer pincode, String district) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(getPincodeDetailsByPincodeNoAndDistrict);
		
		query.setInteger("pinCode", pincode);
		query.setString("district", district);
		
		@SuppressWarnings("unchecked")
		List<Object[]> pinCodeList = (List<Object[]>)query.list();
		PinCode pc = null;
		if (!pinCodeList.isEmpty()) {
			pc = new PinCode();
			pc.setPinCode(Integer.parseInt(pinCodeList.get(0)[0].toString()));
			pc.setDistrict(pinCodeList.get(0)[1].toString());
			pc.setStateId(Integer.parseInt(pinCodeList.get(0)[2].toString()));
			pc.setStateInString(pinCodeList.get(0)[3].toString());
		}
		
		logger.info("Exit");
		return pc;
	}

}
