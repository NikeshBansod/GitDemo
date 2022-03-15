/**
 * 
 */
package com.reliance.gstn.dao;

import java.util.List;

import com.reliance.gstn.model.SACCode;

/**
 * @author Nikesh.Bansod
 *
 */
public interface SACDAO {

	List<Object[]> getSACCodeList(String query);

	String getSacCodeBySacDescription(String sacCodeDescription);

	SACCode getSACCodeData(String sacCode, Integer deliveryStateId);

	SACCode getIGSTValueBySacCode(String sacCode, String sacDescription);

	SACCode getSACCodeById(Integer sacCodePkId);

}
