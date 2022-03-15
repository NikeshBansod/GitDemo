package com.reliance.gstn.external.wizard.service;

import java.util.List;

import com.reliance.gstn.model.EwayBillWIAuth;
import com.reliance.gstn.model.EwayBillWIMaster;
import com.reliance.gstn.model.EwayBillWIMasterRes;

public interface ExternalWizardEwayBillService {
	public List<EwayBillWIMasterRes> getMasters(EwayBillWIMaster EwayBillWIMaster) throws Exception;
	public List<EwayBillWIAuth> getAuthInfo() throws Exception;
	public List<EwayBillWIMaster> getEwayBillWIMasterList() throws Exception ;
}
