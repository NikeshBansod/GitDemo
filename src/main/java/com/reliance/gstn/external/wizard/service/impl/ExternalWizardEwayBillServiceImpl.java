package com.reliance.gstn.external.wizard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.external.wizard.dao.ExternalWizardEwayBillDAO;
import com.reliance.gstn.external.wizard.service.ExternalWizardEwayBillService;
import com.reliance.gstn.model.EwayBillWIAuth;
import com.reliance.gstn.model.EwayBillWIMaster;
import com.reliance.gstn.model.EwayBillWIMasterRes;

@Service
public class ExternalWizardEwayBillServiceImpl implements ExternalWizardEwayBillService {
	@Autowired
	ExternalWizardEwayBillDAO externalWizardEwayBillDAO;

	@Override
	@Transactional
	public List<EwayBillWIMasterRes> getMasters(EwayBillWIMaster EwayBillWIMaster) throws Exception {
		return externalWizardEwayBillDAO.getMasters(EwayBillWIMaster);
	}

	@Override
	@Transactional
	public List<EwayBillWIAuth> getAuthInfo() throws Exception {
		// TODO Auto-generated method stub
		return externalWizardEwayBillDAO.getAuthInfo();
	}

	@Override
	@Transactional
	public List<EwayBillWIMaster> getEwayBillWIMasterList() throws Exception {
		// TODO Auto-generated method stub
		return externalWizardEwayBillDAO.getEwayBillWIMasterList();
	}

}
