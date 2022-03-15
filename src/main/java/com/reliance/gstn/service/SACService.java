/**
 * 
 */
package com.reliance.gstn.service;

import java.util.List;

import com.reliance.gstn.model.SACCode;

/**
 * @author Nikesh.Bansod
 *
 */
public interface SACService {

	List<Object[]> getSACCodeList(String query);

	String getSacCodeBySacDescription(String sacCodeDescription);

	SACCode getSACCodeData(String sacCode, Integer deliveryStateId);

	SACCode getIGSTValueBySacCode(String sacCode, String sacDescription);

	SACCode getSACCodeById(Integer sacCodePkId);

	List<Object[]> getSACCodeListWithMs(String query);

}
