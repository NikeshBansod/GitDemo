package com.reliance.gstn.master.excel.helper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ExcelUploadHelperFactory {

	public static ExcelUploadHelper getExcelUploadHelper(String masterType){
		ExcelUploadHelper excelUploadHelper=null;
		Map<Integer,Map<Pattern,String>> validationRules;
		if(masterType.equalsIgnoreCase("customermastertemplate")){
			//column index will be start form 0
			validationRules=new HashMap<Integer,Map<Pattern,String>>();
			
			//rule for pin code
			Map<Pattern,String> ruleMap=new HashMap<>();			
			ruleMap.put(Pattern.compile("[^\\s\\-][\\w \\.-]+[^\\s\\-]"), "Only alphanumerics are allowed.");
			validationRules.put(0,ruleMap);
			
			ruleMap=new HashMap<>();
			ruleMap.put(Pattern.compile("^null|\\d+$"), "Only 10 digit numbers are allowed.");
			validationRules.put(1,ruleMap);		
			
			ruleMap=new HashMap<>();
			ruleMap.put(Pattern.compile("^null|[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE),"Invalid Email Id.");
			validationRules.put(2,ruleMap);		
			
			ruleMap=new HashMap<>();
			ruleMap.put(Pattern.compile("\\D*\\d{6}\\D*"), "Only 6 digit numbers are allowed.");
			validationRules.put(3,ruleMap);

			ruleMap=new HashMap<>();
			ruleMap.put(Pattern.compile("^[a-zA-Z][a-zA-Z ]+$"), "Only characters are allowed.");
			validationRules.put(4,ruleMap);		

			ruleMap=new HashMap<>();
			ruleMap.put(Pattern.compile("[^\\s\\-][\\w \\.,-]+[^\\s\\-]"), "Only alphanumerics are allowed.");
			validationRules.put(5,ruleMap);				

			excelUploadHelper=new ExcelUploadHelper(7,Arrays.asList(0,3,4),validationRules);
		}
		else if(masterType.equalsIgnoreCase("goodsmastertemplate"))
		{			
			validationRules=new HashMap<Integer,Map<Pattern,String>>();
			Map<Pattern,String> ruleMap=new HashMap<>();			
			ruleMap.put(Pattern.compile("^\\d+$"), "Only numbers are allowed.");
			validationRules.put(1,ruleMap);		
			
			ruleMap=new HashMap<>();
			ruleMap.put(Pattern.compile("[^\\s\\-][\\w \\./-]+[^\\s\\-]"), "Only characters are allowed and can accept special characters “-“ “/” “.”.");		//^[a-zA-Z][a-zA-Z ]+$
			validationRules.put(2,ruleMap);
			
			ruleMap=new HashMap<>();
			ruleMap.put(Pattern.compile("^[a-zA-Z][a-zA-Z ]+$"), "Only characters are allowed.");
			validationRules.put(3,ruleMap);	
			
			ruleMap=new HashMap<>();
			ruleMap.put(Pattern.compile("^[a-zA-Z][a-zA-Z ]+$"), "Only characters are allowed.");
			validationRules.put(4,ruleMap);	
			
			ruleMap=new HashMap<>();
			//ruleMap.put(Pattern.compile("^\\d+$"), "Only numbers are allowed.");
			ruleMap.put(Pattern.compile("^(?!\\.?$)\\d{0,6}(\\.\\d{0,2})?$"), "Decimal with optional 2 digit precisions are allowed.");
			validationRules.put(5,ruleMap);		
			
			ruleMap=new HashMap<>();		
			ruleMap.put(Pattern.compile("^\\d+(\\.\\d{1,2})?$"), "Invalid value.");
			validationRules.put(6,ruleMap);	
			
			
			
			
			ruleMap=new HashMap<>();
			//ruleMap.put(Pattern.compile("^\\d+$"), "Only numbers are allowed.");
			ruleMap.put(Pattern.compile("^(?!\\.?$)\\d{0,6}(\\.\\d{0,2})?$"), "Decimal with optional 2 digit precisions are allowed.");
			validationRules.put(7,ruleMap);	
			
			
			ruleMap=new HashMap<>();
			//ruleMap.put(Pattern.compile("^\\d+$"), "Only numbers are allowed.");
			ruleMap.put(Pattern.compile("^(?!\\.?$)\\d{0,6}(\\.\\d{0,2})?$"), "Decimal with optional 2 digit precisions are allowed.");
			validationRules.put(8,ruleMap);	
			
			ruleMap=new HashMap<>();
			//ruleMap.put(Pattern.compile("^\\d+$"), "Only numbers are allowed.");
			ruleMap.put(Pattern.compile("^(?!\\.?$)\\d{0,6}(\\.\\d{0,2})?$"), "Decimal with optional 2 digit precisions are allowed.");
			validationRules.put(9,ruleMap);	
			
			
			
			excelUploadHelper=new ExcelUploadHelper(12,Arrays.asList(0,1,2,3,5,6,7,8,9,10,11),validationRules);
		}
		else if(masterType.equalsIgnoreCase("servicesmastertemplate"))
		{			
			validationRules=new HashMap<Integer,Map<Pattern,String>>();
			Map<Pattern,String> ruleMap=new HashMap<>();			
			ruleMap.put(Pattern.compile("^\\d+$"), "Only numbers are allowed.");
			validationRules.put(1,ruleMap);		
			
			ruleMap=new HashMap<>();
			ruleMap.put(Pattern.compile("[^\\s\\-][\\w \\./-]+[^\\s\\-]"), "Only characters are allowed and can accept special characters “-“ “/” “.”.");//^[a-zA-Z][a-zA-Z ]+$
			validationRules.put(2,ruleMap);
			
			ruleMap=new HashMap<>();
			ruleMap.put(Pattern.compile("^[a-zA-Z][a-zA-Z ]+$"), "Only characters are allowed.");
			validationRules.put(3,ruleMap);	
			
			ruleMap=new HashMap<>();
			ruleMap.put(Pattern.compile("^[a-zA-Z][a-zA-Z ]+$"), "Only characters are allowed.");
			validationRules.put(4,ruleMap);	
			
			ruleMap=new HashMap<>();
			//ruleMap.put(Pattern.compile("^\\d+$"), "Only numbers are allowed.");
			ruleMap.put(Pattern.compile("^(?!\\.?$)\\d{0,6}(\\.\\d{0,2})?$"), "Decimal with optional 2 digit precisions are allowed.");
			validationRules.put(5,ruleMap);		
			
			ruleMap=new HashMap<>();		
			ruleMap.put(Pattern.compile("^\\d+(\\.\\d{1,2})?$"), "Invalid value.");
			validationRules.put(6,ruleMap);			
			
			excelUploadHelper=new ExcelUploadHelper(9,Arrays.asList(0,1,2,3,5,6,7,8),validationRules);
		}
		
		return excelUploadHelper;
	}
}
