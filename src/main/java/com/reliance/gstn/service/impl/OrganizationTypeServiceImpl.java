/**
 * 
 */
package com.reliance.gstn.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.dao.OrganizationTypeDAO;
import com.reliance.gstn.model.OrganizationType;
import com.reliance.gstn.service.OrganizationTypeService;


/**
 * @author Nikesh.Bansod
 *
 */
@Service
public class OrganizationTypeServiceImpl implements OrganizationTypeService {

	
	@Autowired 
	OrganizationTypeDAO organizationTypeDAO;
	
	
	@Override
	@Transactional
	public List<OrganizationType> listOrganizationType() {
		// TODO Auto-generated method stub
		return organizationTypeDAO.listOrganizationType();
	}

}
