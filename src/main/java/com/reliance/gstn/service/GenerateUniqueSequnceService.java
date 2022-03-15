package com.reliance.gstn.service;

import java.util.List;

public interface GenerateUniqueSequnceService {

	String getLatestDocumentNumber(String pattern, Integer orgUId);
	
	List<String> getDocSequence(String pattern, Integer orgUId,Integer size) throws Exception;
	
	List<String> getInitialDocSequence(Integer size) throws Exception;
	
	public String getDocSequenceSession(Integer Id) throws Exception;
	
	Integer getDocSequenceId(String pattern, Integer orgUId, Integer size);
}
