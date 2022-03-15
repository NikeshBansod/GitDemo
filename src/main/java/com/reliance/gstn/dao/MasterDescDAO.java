/**
 * 
 */
package com.reliance.gstn.dao;

import java.util.List;

import com.reliance.gstn.model.MasterDescDetails;

/**
 * @author Rupali J
 *
 */
public interface MasterDescDAO {

	List<MasterDescDetails> getMasterDescList()throws Exception;

}
