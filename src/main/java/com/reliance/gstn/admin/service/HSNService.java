package com.reliance.gstn.admin.service;

import java.util.List;

import com.reliance.gstn.admin.model.HSNDetails;

public interface HSNService {

	String addHSNDetails(HSNDetails HSNDetails) throws Exception;

	List<HSNDetails> listHSNDetails(Integer uId);

	HSNDetails getHSNDetailsById(Integer id);

	String updateHSNDetails(HSNDetails HSNDetails) throws Exception;

	String removeHSNDetails(Integer id) throws Exception;

	List<Object[]> getHSNCodeList(String parameter);

	String getHsnCodeByHsnDescription(String hsnCodeDescription);

	HSNDetails getHSNCodeData(String hsnCode, Integer gstnStateId);

	HSNDetails getIGSTValueByHsnCode(String hsnCode, String hsnDescription);

	List<HSNDetails> getHSNCodeList();

	/*List<String> getHSNCodeListNew(String parameter);*/
}
