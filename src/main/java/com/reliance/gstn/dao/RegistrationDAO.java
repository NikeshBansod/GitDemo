/**
 * 
 */
package com.reliance.gstn.dao;

import java.util.List;

import com.reliance.gstn.model.NatureOfBusiness;
import com.reliance.gstn.model.Registration;

/**
 * @author Nikesh.Bansod
 *
 */
public interface RegistrationDAO {

	String addRegistration(Registration registration);

	Registration getRegistrationById(Integer uId);

	String updateRegistration(Registration registration);

	boolean validatePassword(Integer uId, String oldPassword);

	List<NatureOfBusiness> getNatureOfBusinessList() throws Exception;

}
