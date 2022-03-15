package com.reliance.gstn.util;

import java.util.HashMap;
import java.util.Map;

public class CndnReasonUtil {

	private static Map<String,String> cndnReasonMap=new HashMap<String,String>();
	
	public static String getCndnReasonValue(String key){
		if(cndnReasonMap.isEmpty()){
			createCndnReasonMap();
		}
		
		return cndnReasonMap.get(key);
	}
	
	private static void createCndnReasonMap(){
		
		cndnReasonMap.put("Sales Return", "01-Sales Return");
		cndnReasonMap.put("Post Sale Discount", "02-Post Sale Discount");
		cndnReasonMap.put("Deficiency in services", "03-Deficiency in services");
		cndnReasonMap.put("Correction in Invoice", "04-Correction in Invoice");
		cndnReasonMap.put("Change in POS", "05-Change in POS");
		cndnReasonMap.put("Finalization of Provisional assessment", "06-Finalization of Provisional assessment");
		cndnReasonMap.put("Others", "07-Others");
		
	}
}
