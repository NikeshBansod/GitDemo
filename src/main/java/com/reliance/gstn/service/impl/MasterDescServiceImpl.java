package com.reliance.gstn.service.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.dao.MasterDescDAO;
import com.reliance.gstn.dao.UnitOfMeasurementDAO;
import com.reliance.gstn.model.FeedbackDetails;
import com.reliance.gstn.model.MasterDescDetails;
import com.reliance.gstn.model.UnitOfMeasurement;
import com.reliance.gstn.service.FeedbackDetailService;
import com.reliance.gstn.service.MasterDescService;
import com.reliance.gstn.util.GSTNConstants;

@Service
public class MasterDescServiceImpl implements MasterDescService {

	private static final Logger logger = Logger.getLogger(MasterDescServiceImpl.class);
	
	@Autowired
	public MasterDescDAO masterDescDAO;

	@Transactional
	public List<MasterDescDetails> getMasterDescList() throws Exception{
		return masterDescDAO.getMasterDescList();
	}
		
		
}
