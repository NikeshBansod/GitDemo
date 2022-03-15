/**
 * 
 */
package com.reliance.gstn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.model.OrganizationType;
import com.reliance.gstn.service.OrganizationTypeService;

/**
 * @author Nikesh.Bansod
 *
 */
@Controller
public class OrganizationTypeController {
	
	@Autowired
	OrganizationTypeService organizationTypeService;
	
	@RequestMapping(value="/getOrganizationTypeList",method=RequestMethod.GET)
	public @ResponseBody String getOrganizationTypeList(Model model,HttpServletRequest httpRequest) {
		List<OrganizationType> organizationTypeList = organizationTypeService.listOrganizationType();
		return new Gson().toJson(organizationTypeList);
	}

}
