/**
 * 
 */
package com.reliance.gstn.service;

import java.util.List;

import com.reliance.gstn.model.NatureOfBusiness;
import com.reliance.gstn.model.Registration;

/**
 * @author Nikesh.Bansod
 *
 */
public interface RegistrationService {

	public String addRegistration(Registration registration);

	public Registration getRegistrationById(Integer uId);

	public String updateRegistration(Registration registration);

	public boolean validatePassword(Integer uId, String oldPassword);

	public List<NatureOfBusiness> getNatureOfBusinessList() throws Exception;
	
}
