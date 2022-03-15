/**
 * 
 */
package com.reliance.gstn.service;

import com.reliance.gstn.model.GSTR3BModel;

/**
 * @author sandeep jalagam
 *
 */
public interface GSTR3BService {

	Object getGstr3bJiogstData(GSTR3BModel gstr3b) throws Exception;

	Object getGstr3bJiogstL2(GSTR3BModel gstr3b) throws Exception;

	Object gstr3bSaveToJiogst(String input, GSTR3BModel gstr3bModel) throws Exception;

	Object getGstr3bJiogstRetStatus(GSTR3BModel gstr3bModel) throws Exception;

	Object getGstr3bJiogstList(GSTR3BModel gstr3b) throws Exception;
	
	Object getGstr3bGstnl2(GSTR3BModel gstr3b) throws Exception;
	
	Object getGstr3bGstnSave(GSTR3BModel gstr3b,String input) throws Exception;
	

}
