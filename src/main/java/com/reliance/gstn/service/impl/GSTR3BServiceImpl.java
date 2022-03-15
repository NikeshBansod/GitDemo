package com.reliance.gstn.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.reliance.gstn.dao.GSTR3BDao;
import com.reliance.gstn.model.GSTR3BModel;
import com.reliance.gstn.service.GSTR3BService;
import com.reliance.gstn.service.UploadAspService;
import com.reliance.gstn.util.AspApiConstants;

@Service
public class GSTR3BServiceImpl implements GSTR3BService {

	@Value("${gstr.client.user}")
	private String gstrClientUserId;

	@Value("${gstr.client.pwd}")
	private String gstrClientPwd;

	@Autowired
	private UploadAspService uploadAspService;

	@Autowired
	private GSTR3BDao gstr3bDao;

	@Override
	public Object getGstr3bJiogstData(GSTR3BModel gstr3b) throws Exception {
		gstr3b.setService(AspApiConstants.JIOGST);
		gstr3b.setServiceType(AspApiConstants.GSTR3B_JIOGST_AUTO);
		return uploadAspService.getGstr3bService(gstr3b, null);
	}

	@Override
	public Object getGstr3bJiogstL2(GSTR3BModel gstr3b) throws Exception {
		gstr3b.setService(AspApiConstants.JIOGST);
		gstr3b.setServiceType(AspApiConstants.GSTR3B_JIOGST_L2);
		return uploadAspService.getGstr3bService(gstr3b, null);
	}

	@Override
	// @Transactional
	public Object gstr3bSaveToJiogst(String input, GSTR3BModel request) throws Exception {
		Gson gson = new Gson();
		request.setService(AspApiConstants.JIOGST);
		request.setServiceType(AspApiConstants.GSTR3B_JIOGST_SAVE);
		String response = uploadAspService.getGstr3bService(request, input);
		Map<String, String> map = gson.fromJson(response, Map.class);
		if (map.containsKey("ackNo")) {
			request.setService(AspApiConstants.JIOGST);
			request.setServiceType(AspApiConstants.GSTR3B_JIOGST_RETSTATUS);
			request.setAckNo(map.get("ackNo"));
			response = uploadAspService.getGstr3bService(request, null);
		}
		return response;
	}

	public Object getGstr3bJiogstRetStatus(GSTR3BModel gstr3bModel) throws Exception {
		gstr3bModel.setService(AspApiConstants.JIOGST);
		gstr3bModel.setServiceType(AspApiConstants.GSTR3B_JIOGST_RETSTATUS);
		return uploadAspService.getGstr3bService(gstr3bModel, null);
	}

	@Override
	public Object getGstr3bJiogstList(GSTR3BModel gstr3b) throws Exception {
		return gstr3bDao.getGstr3bJiogstList(gstr3b);
	}

	@Override
	public Object getGstr3bGstnl2(GSTR3BModel gstr3b) throws Exception {
		gstr3b.setService(AspApiConstants.GSTN);
		gstr3b.setServiceType(AspApiConstants.GSTR3B_GSTN_L2);
		return uploadAspService.getGstr3bService(gstr3b, null);
	}

	@Override
	public Object getGstr3bGstnSave(GSTR3BModel gstr3b, String input) throws Exception {
		gstr3b.setService(AspApiConstants.GSTN);
		gstr3b.setServiceType(AspApiConstants.GSTR3B_GSTN_SAVE);
		return uploadAspService.getGstr3bService(gstr3b, input);
	}

}
