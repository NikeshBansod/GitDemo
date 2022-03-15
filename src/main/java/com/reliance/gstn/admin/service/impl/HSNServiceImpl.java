package com.reliance.gstn.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.admin.dao.HSNDao;
import com.reliance.gstn.admin.model.HSNDetails;
import com.reliance.gstn.admin.service.HSNService;

@Service
public class HSNServiceImpl implements HSNService {

	@Autowired
	private HSNDao HSNDao;
	
	@Value("${${env}.getHsnDetails.url}")
	private String webServiceUrl;

	@Override
	@Transactional
	public String addHSNDetails(HSNDetails HSNDetails) throws Exception {
		// TODO Auto-generated method stub
		return HSNDao.addHSNDetails(HSNDetails);
	}
	
	@Override
	@Transactional
	public List<HSNDetails> listHSNDetails(Integer uId) {
		return HSNDao.listHSNDetails(uId);
	}
	

	@Override
	@Transactional
	public HSNDetails getHSNDetailsById(Integer id) {
		
		return HSNDao.getHSNDetailsById(id);
	}

	@Override
	@Transactional
	public String updateHSNDetails(HSNDetails HSNDetails) throws Exception {
		
		return HSNDao.updateHSNDetails(HSNDetails);
	}

	@Override
	@Transactional
	public String removeHSNDetails(Integer id)  throws Exception{
		
		return  HSNDao.removeHSNDetails( id);
	}

	@Override
	@Transactional
	public List<Object[]> getHSNCodeList(String parameter) {
		// TODO Auto-generated method stub
		return HSNDao.getHSNCodeList(parameter);
	}

	
	//R&D below
	
	
	/*@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> getHSNCodeList(String parameter) {
		
		logger.info("Entry");
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, String> parameterMap=new HashMap<>();
		parameterMap.put("hsnDesc", parameter);
		String body = new Gson().toJson(parameterMap);
		Map<String, String> headersMap = inventoryServiceImpl.createApiHeader();		
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		
		logger.info("Webservice call");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		Iterator<Entry<String, String>> itr=headersMap.entrySet().iterator();
		
		while(itr.hasNext()){
			Entry<String, String> entry=itr.next();
			headers.add(entry.getKey(), entry.getValue());
		}

		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		
		//String response = WebserviceCallUtil.callWebservice(webServiceUrl, headersMap, body, extraParams);
		RestTemplate restTemplate=new RestTemplate();
		responseMap=restTemplate.postForObject(webServiceUrl, entity, Map.class);
		logger.info("Payload Response :: " + responseMap); 
		Gson gson=new Gson();
		responseMap=(Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
		logger.info("Payload Response map:: " + responseMap);
		List<Map<String,Object>> resultList=(List<Map<String,Object>>)responseMap.get("result");
		
		List<Object[]> outputL=new ArrayList<>(resultList.size());
		
		for (Map<String, Object> map : resultList) {
			Object[] objA=new Object[2];
			objA[0]=map.get("hsnCode");
			objA[1]=map.get("hsnDesc");
			
			
			outputL.add(objA);
			
			
		}
		logger.info("Exit");
		return outputL;
	}*/
	
	
/*	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<String> getHSNCodeListNew(String parameter) {
		
		logger.info("Entry");
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, String> parameterMap=new HashMap<>();
		parameterMap.put("hsnDesc", parameter);
		String body = new Gson().toJson(parameterMap);
		Map<String, String> headersMap = inventoryServiceImpl.createApiHeader();		
		Map<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("methodName", "POST");
		
		logger.info("Webservice call");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		Iterator<Entry<String, String>> itr=headersMap.entrySet().iterator();
		
		while(itr.hasNext()){
			Entry<String, String> entry=itr.next();
			headers.add(entry.getKey(), entry.getValue());
		}

		HttpEntity<String> entity = new HttpEntity<String>(body, headers);
		
		//String response = WebserviceCallUtil.callWebservice(webServiceUrl, headersMap, body, extraParams);
		RestTemplate restTemplate=new RestTemplate();
		responseMap=restTemplate.postForObject(webServiceUrl, entity, Map.class);
		
		//String response = WebserviceCallUtil.callWebservice(webServiceUrl, headersMap, body, extraParams);
		logger.info("Payload Response :: " + response);
		Gson gson=new Gson();
		responseMap=(Map<String, Object>)gson.fromJson(response.toString(), responseMap.getClass());
		logger.info("Payload Response map:: " + responseMap);
		List<Map<String,Object>> resultList=(List<Map<String,Object>>)responseMap.get("result");
		
		//List<Object[]> outputL=new ArrayList<>(resultList.size());
		List<String> outputL=new ArrayList<>(resultList.size());
		
		for (Map<String, Object> map : resultList) {
			outputL.add("["+map.get("hsnCode")+"] - "+map.get("hsnDesc"));
		}
		logger.info("Exit");
		return outputL;
	}
	*/
	
	@Override
	@Transactional
	public String getHsnCodeByHsnDescription(String hsnCodeDescription) {
		// TODO Auto-generated method stub
		return HSNDao.getHsnCodeByHsnDescription(hsnCodeDescription);
	}

	@Override
	@Transactional
	public HSNDetails getHSNCodeData(String hsnCode, Integer gstnStateId) {
		// TODO Auto-generated method stub
		return HSNDao.getHSNCodeData( hsnCode,  gstnStateId);
	}

	@Override
	@Transactional
	public HSNDetails getIGSTValueByHsnCode(String hsnCode, String hsnDescription) {
		// TODO Auto-generated method stub
		return HSNDao.getIGSTValueByHsnCode(hsnCode, hsnDescription);
	}

	@Override
	@Transactional
	public List<HSNDetails> getHSNCodeList() {
		// TODO Auto-generated method stub
		return HSNDao.getHSNCodeList();
	}

}
