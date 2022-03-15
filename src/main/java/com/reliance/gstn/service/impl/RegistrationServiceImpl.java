/**
 * 
 */
package com.reliance.gstn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.dao.RegistrationDAO;
import com.reliance.gstn.model.NatureOfBusiness;
import com.reliance.gstn.model.Registration;
import com.reliance.gstn.service.RegistrationService;

/**
 * @author Nikesh.Bansod
 *
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private RegistrationDAO registrationDAO;


	@Override
	@Transactional
	public String addRegistration(Registration registration) {
		// TODO Auto-generated method stub
		return registrationDAO.addRegistration(registration);
	}

	@Override
	@Transactional
	public Registration getRegistrationById(Integer uId) {
		// TODO Auto-generated method stub
		return registrationDAO.getRegistrationById(uId);
	}

	@Override
	@Transactional
	public String updateRegistration(Registration registration) {
		// TODO Auto-generated method stub
		return registrationDAO.updateRegistration(registration);
	}

	@Override
	@Transactional
	public boolean validatePassword(Integer uId, String oldPassword) {
		// TODO Auto-generated method stub
		return registrationDAO.validatePassword(uId, oldPassword);
	}

	@Override
	@Transactional
	public List<NatureOfBusiness> getNatureOfBusinessList() throws Exception {
		// TODO Auto-generated method stub
		return registrationDAO.getNatureOfBusinessList();
	}

}
