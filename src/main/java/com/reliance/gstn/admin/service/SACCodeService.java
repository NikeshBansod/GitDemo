/**
 * 
 */
package com.reliance.gstn.admin.service;

import java.util.List;

import com.reliance.gstn.model.SACCode;


/**
 * @author Pradeep.N.Reddy
 *
 */
public interface SACCodeService {

	List<SACCode> getSACCodeList();

	String addSACCode(SACCode sacCode);

	SACCode getSACDetailsById(Integer id);

	String updateSACDetails(SACCode sacCode) throws Exception;

	String removeSACDetails(Integer id);

	
}
