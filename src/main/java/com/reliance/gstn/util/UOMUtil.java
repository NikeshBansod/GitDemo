package com.reliance.gstn.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reliance.gstn.model.UnitOfMeasurement;

public class UOMUtil {
		
	private static Map<String,String> uomMap=new HashMap<String,String>();
	private static boolean isMapLoaded=false;
	
	public static String getUomValue(String key){
		return uomMap.get(key);
	}
	
	public static boolean isEmptyMap(){
		boolean status = false;
		if(uomMap.isEmpty()){
			status = true;
		} 
		return status;
	}
	
		
	public static boolean isMapLoaded() {
		return isMapLoaded;
	}

	public static void setUOMMap(List<UnitOfMeasurement> unitOfMeasurementList){
		
		if(!isMapLoaded && uomMap.isEmpty()){
			synchronized (uomMap) {
			 for(UnitOfMeasurement uom : unitOfMeasurementList){
				 uomMap.put(uom.getQuantityDescription(),uom.getQuantityCode());
				}
			  }
			 isMapLoaded=true;
		}
		 
	}
}
