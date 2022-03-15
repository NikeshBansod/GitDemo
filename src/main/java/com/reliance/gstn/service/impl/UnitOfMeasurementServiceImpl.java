/**
 * 
 */
package com.reliance.gstn.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.reliance.gstn.dao.UnitOfMeasurementDAO;
import com.reliance.gstn.model.UnitOfMeasurement;
import com.reliance.gstn.service.UnitOfMeasurementService;
import com.reliance.gstn.util.WebserviceCallUtil;



/**
 * @author Dibyendu.Mukherjee
 *
 */
@Service
public class UnitOfMeasurementServiceImpl implements UnitOfMeasurementService {
	private static final Logger logger = Logger.getLogger(UnitOfMeasurementServiceImpl.class);
	
	@Autowired 
	InventoryServiceImpl inventoryServiceImpl;
	
	@Value("${${env}.getUnitOfMeasurement.url}")
	private String webServiceUrl;
	
	@Autowired
	public UnitOfMeasurementDAO unitOfMeasurementDAO;

	/*	@Override
	public List<UnitOfMeasurement> getUnitOfMeasurement() throws Exception {
		logger.info("Entry Webservice Calling");
		//String body = new Gson().toJson(new HashMap<>());
		String body = "";
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, String> headersMap = inventoryServiceImpl.createApiHeader();		
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "GET");
		
		
		String response = WebserviceCallUtil.callWebservice(webServiceUrl, headersMap, body, extraParams);
		logger.info("Payload Response :: " + response);
		
		Gson gson=new Gson();		
		responseMap=(Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
		logger.info("Payload Response map:: " + responseMap);
		
		List<UnitOfMeasurement> uomlist = (List<UnitOfMeasurement>) responseMap.get("result");
		//List<UnitOfMeasurement> uomlist = new ArrayList<>(responseMap.values());
		
		return uomlist;
	}*/

	@Override
	@Transactional
	public List<UnitOfMeasurement> getUnitOfMeasurement() throws Exception{
		return unitOfMeasurementDAO.getUnitOfMeasurement();
	}

	
	
	
}
