/**
 * 
 */
package com.reliance.gstn.dao;

import com.reliance.gstn.admin.exception.GSTR3BApiException;
import com.reliance.gstn.model.GSTR3BModel;

/**
 * @author Nikesh.Bansod
 *
 */
public interface GSTR3BDao {

	public void saveToJiogst(GSTR3BModel request) throws GSTR3BApiException;

	public void updateByAckNo(String ackNo,String status) throws GSTR3BApiException;
	
	public Object getGstr3bJiogstList(GSTR3BModel gstr3b) throws Exception;

}
