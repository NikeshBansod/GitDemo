package com.reliance.gstn.util;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * @author Vivek2.Dubey
 *
 */
public class BindingErrorUtil {
	public static void getErrorResult(BindingResult result,Logger logger){
		if(result.hasErrors()){
		
			List<FieldError> errors = result.getFieldErrors();
		    for (FieldError error : errors ) {
		    	logger.debug(error.getObjectName() + " - " + error.getDefaultMessage());
		    }
			
		}
		
	}

}
