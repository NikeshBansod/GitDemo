/**
 * 
 */
package com.reliance.gstn.dao;

import java.util.List;

import com.reliance.gstn.model.PinCode;

/**
 * @author Nikesh.Bansod
 *
 */
public interface PinCodeDAO {

	PinCode getPinCodeByPinCode(Integer pinCode);

	List<Integer> getPinCodeList();

	List<PinCode> getPinCodeListByPincode(String pincode);

	PinCode getPinCodeDetailsByPinCodeAndDistrict(Integer pincode, String district);

}
