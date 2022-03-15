package com.reliance.gstn.master.excel.helper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class EntityValidator {

	public static Set<Set<ConstraintViolation<Object>>> validateEntiy(List<?>entiyList){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<Set<ConstraintViolation<Object>>> resultset=new HashSet<>();
		for (Object object : entiyList) {
			Set<ConstraintViolation<Object>> result=validator.validate(object);
			if(!result.isEmpty()){
				resultset.add(result);
			}
		}
		return resultset;	
		}
}
