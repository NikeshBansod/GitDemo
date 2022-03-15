package com.reliance.gstn.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.dao.GenerateUniqueSequnceDao;
import com.reliance.gstn.service.GenerateUniqueSequnceService;


@Service
public class GenerateUniqueSequnceServiceImpl implements GenerateUniqueSequnceService{
	
	@Autowired
	GenerateUniqueSequnceDao generateUniqueSequnceDao;
	
	@Override
	@Transactional
	public String getLatestDocumentNumber(String pattern, Integer orgUId) {
		return generateUniqueSequnceDao.getLatestDocumentNumber(pattern, orgUId);
	}

	@Override
	@Transactional
	public List<String> getDocSequence(String pattern, Integer orgUId,Integer size) throws Exception {
		return generateUniqueSequnceDao.getDocSequence(pattern, orgUId, size);
	}

	@Override
	@Transactional
	public List<String> getInitialDocSequence(Integer size) throws Exception {
		return generateUniqueSequnceDao.getInitialDocSequence(size);
	}

	@Override
	@Transactional
	public String getDocSequenceSession(Integer Id) throws Exception {
		return generateUniqueSequnceDao.getDocSequenceSession(Id);
	}

	@Override
	@Transactional
	public Integer getDocSequenceId(String pattern, Integer orgUId, Integer size){
		return generateUniqueSequnceDao.getDocSequenceId(pattern, orgUId, size);
	}

}
