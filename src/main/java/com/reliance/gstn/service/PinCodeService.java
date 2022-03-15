/**
 * 
 */
package com.reliance.gstn.service;

import java.util.List;

import com.reliance.gstn.model.PinCode;

/**
 * @author Nikesh.Bansod
 *
 */
public interface PinCodeService {

	PinCode getPinCodeByPinCode(Integer pinCode);

	List<Integer> getPinCodeList();

	List<PinCode> getPinCodeListByPincode(String query);

	PinCode getPinCodeDetailsByPinCodeAndDistrict(Integer valueOf, String district);

}
