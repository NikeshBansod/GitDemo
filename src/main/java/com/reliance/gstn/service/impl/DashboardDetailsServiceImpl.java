package com.reliance.gstn.service.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.dao.DashboardDetailDAO;
import com.reliance.gstn.service.DashboardDetailsService;

@Service
public class DashboardDetailsServiceImpl implements DashboardDetailsService {

	private static final Logger logger = Logger.getLogger(DashboardDetailsServiceImpl.class);
	
	/*@Autowired
	public DashboardDetailDAO dashboardDetailDAO;

	@Transactional
	public String getDashboardList() throws Exception {
		return dashboardDetailDAO.getDashboardList();
	}*/
		
		
}
