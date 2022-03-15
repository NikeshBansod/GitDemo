package com.reliance.gstn.master.excel.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ExcelUploadHelper {

	private int colCount;
	//column index will be start form 0
	private List<Integer> mandatoryColumns;
	
	//column index will be start form 0
	private Map<Integer,Map<Pattern,String>> validationRules;
	
	public ExcelUploadHelper(int colCount, List<Integer> mandatoryColumns,Map<Integer,Map<Pattern,String>> validationRules) {
		if(null==mandatoryColumns){
			mandatoryColumns=new ArrayList<Integer>();
		}
		if(null==validationRules){
			validationRules=new HashMap<Integer,Map<Pattern,String>>();
		}
		this.colCount = colCount;
		this.mandatoryColumns = mandatoryColumns;
		this.validationRules=validationRules;
	}
	

	public int getColCount() {
		return colCount;
	}

	public List<Integer> getMandatoryColumns() {
		return mandatoryColumns;
	}


	public Map<Integer, Map<Pattern, String>> getValidationRules() {
		return validationRules;
	}
	
}
