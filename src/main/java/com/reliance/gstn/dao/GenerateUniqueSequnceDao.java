package com.reliance.gstn.dao;

import java.util.List;

public interface GenerateUniqueSequnceDao {
	
	String getLatestDocumentNumber(String pattern, Integer orgUId);
	
	List<String> getDocSequence(String pattern, Integer orgUId,Integer size) throws Exception;

	List<String> getInitialDocSequence(Integer size) throws Exception;
	

	Integer getDocSequenceId(String pattern, Integer orgUId, Integer size);
	
	public String getDocSequenceSession(Integer Id) throws Exception;

}
