package com.reliance.gstn.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reliance.gstn.model.InvoiceServiceDetailsHistory;

public class GSTNHistoryUtil {
	
	public static Map<String,Map<String,Double>> convertListToMap(List<InvoiceServiceDetailsHistory> list) {
		 Map<String,Map<String,Double>> result=new HashMap<String,Map<String,Double>>();
		
		 Map<String,Double> igstMap=new HashMap<String,Double>();
		 Map<String,Double> cgstMap=new HashMap<String,Double>();
		 Map<String,Double> sgstMap=new HashMap<String,Double>();
		 Map<String,Double> ugstMap=new HashMap<String,Double>();
		 Map<String,Double> igstDiffPercentMap=new HashMap<String,Double>();
		 Map<String,Double> cgstDiffPercentMap=new HashMap<String,Double>();
		 Map<String,Double> sgstDiffPercentMap=new HashMap<String,Double>();
		 Map<String,Double> ugstDiffPercentMap=new HashMap<String,Double>();
			for(InvoiceServiceDetailsHistory service : list){
				String gstPercent=null;
				if(service.getCategoryType().equals("CATEGORY_WITH_IGST")||service.getCategoryType().equals("CATEGORY_EXPORT_WITH_IGST")){
					gstPercent=String.valueOf(service.getIgstPercentage());
					if(service.getDiffPercent() != null && service.getDiffPercent().equals("Y")){
						if(!igstDiffPercentMap.containsKey(gstPercent)){
							igstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
						}else{
							igstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(igstDiffPercentMap.get(gstPercent)+service.getAmount()));
						}
					}else{
						if(!igstMap.containsKey(gstPercent)){
							igstMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
						}else{
							igstMap.put(gstPercent, GSTNUtil.convertToDouble(igstMap.get(gstPercent)+service.getAmount()));
						}
					}
					
				}
				if(service.getCategoryType().equals("CATEGORY_WITH_SGST_CSGT")|| service.getCategoryType().equals("CATEGORY_WITH_UGST_CGST")){
					gstPercent=String.valueOf(service.getCgstPercentage());
					if(service.getDiffPercent() != null && service.getDiffPercent().equals("Y")){
						if(!cgstDiffPercentMap.containsKey(gstPercent)){
							cgstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
						}else{
							cgstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(cgstDiffPercentMap.get(gstPercent)+service.getAmount()));
						}
					}else{
						if(!cgstMap.containsKey(gstPercent)){
							cgstMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
						}else{
							cgstMap.put(gstPercent, GSTNUtil.convertToDouble(cgstMap.get(gstPercent)+service.getAmount()));
						}
					}
					
				}
				if(service.getCategoryType().equals("CATEGORY_WITH_SGST_CSGT")){
					gstPercent=String.valueOf(service.getSgstPercentage());
					if(service.getDiffPercent() != null && service.getDiffPercent().equals("Y")){
						if(!sgstDiffPercentMap.containsKey(gstPercent)){
							sgstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
						}else{
							sgstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(sgstDiffPercentMap.get(gstPercent)+service.getAmount()));
						}
					}else{
						if(!sgstMap.containsKey(gstPercent)){
							sgstMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
						}else{
							sgstMap.put(gstPercent, GSTNUtil.convertToDouble(sgstMap.get(gstPercent)+service.getAmount()));
						}
					}
					
				}
				if(service.getCategoryType().equals("CATEGORY_WITH_UGST_CGST")){
					gstPercent=String.valueOf(service.getUgstPercentage());
					if(service.getDiffPercent() != null && service.getDiffPercent().equals("Y")){
						if(!ugstDiffPercentMap.containsKey(gstPercent)){
							ugstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
						}else{
							ugstDiffPercentMap.put(gstPercent, GSTNUtil.convertToDouble(ugstDiffPercentMap.get(gstPercent)+service.getAmount()));
						}
					}else{
						if(!ugstMap.containsKey(gstPercent)){
							ugstMap.put(gstPercent, GSTNUtil.convertToDouble(service.getAmount()));
						}else{
							ugstMap.put(gstPercent, GSTNUtil.convertToDouble(ugstMap.get(gstPercent)+service.getAmount()));
						}
					}
					
				}
			}	
	
			result.put("igst", igstMap);
			result.put("cgst", cgstMap);
			result.put("sgst", sgstMap);
			result.put("ugst", ugstMap);
			result.put("igstDiffPercent", igstDiffPercentMap);
			result.put("cgstDiffPercent", cgstDiffPercentMap);
			result.put("sgstDiffPercent", sgstDiffPercentMap);
			result.put("ugstDiffPercent", ugstDiffPercentMap);
			
		return result;
	}

}
