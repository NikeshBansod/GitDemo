/**
 * 
 */
package com.reliance.gstn.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.admin.model.AdvolCessRateDetails;
import com.reliance.gstn.admin.model.NonAdvolCessRateDetails;
import com.reliance.gstn.dao.AdvolAndNonAdvolCessServiceDAO;
import com.reliance.gstn.service.AdvolAndNonAdvolCessService;


/**
 * @author Rupali.J
 *
 */
@Service
public class AdvolAndNonAdvolCessServiceImpl implements AdvolAndNonAdvolCessService {
	
	@Autowired
	public AdvolAndNonAdvolCessServiceDAO advolAndNonAdvolCessServiceDAO;

	@Transactional
	public List<AdvolCessRateDetails> getAdvolCessRateDetailList() throws Exception{
		return advolAndNonAdvolCessServiceDAO.getAdvolCessRateDetailList();
	}
	
	@Transactional
	public List<NonAdvolCessRateDetails> getNonAdvolCessRateDetailList() throws Exception{
		return advolAndNonAdvolCessServiceDAO.getNonAdvolCessRateDetailList();
	}

}
