/**
 * 
 */
package com.reliance.gstn.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.dao.PinCodeDAO;
import com.reliance.gstn.model.PinCode;
import com.reliance.gstn.service.PinCodeService;

/**
 * @author Nikesh.Bansod
 *
 */
@Service
public class PinCodeServiceImpl implements PinCodeService {
	
	@Autowired
	PinCodeDAO pinCodeDAO;

	@Override
	@Transactional
	public PinCode getPinCodeByPinCode(Integer pinCode) {
		// TODO Auto-generated method stub
		return pinCodeDAO.getPinCodeByPinCode( pinCode);
	}

	@Override
	@Transactional
	public List<Integer> getPinCodeList() {
		// TODO Auto-generated method stub
		return pinCodeDAO.getPinCodeList();
	}

	@Override
	@Transactional
	public List<PinCode> getPinCodeListByPincode(String pincode) {
		// TODO Auto-generated method stub
		return pinCodeDAO.getPinCodeListByPincode(pincode);
	}

	@Override
	@Transactional
	public PinCode getPinCodeDetailsByPinCodeAndDistrict(Integer pincode, String district) {
		// TODO Auto-generated method stub
		return pinCodeDAO.getPinCodeDetailsByPinCodeAndDistrict(pincode, district) ;
	}

}
