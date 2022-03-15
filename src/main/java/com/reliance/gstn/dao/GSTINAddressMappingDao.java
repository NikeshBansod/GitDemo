/**
 * 
 */
package com.reliance.gstn.dao;

import com.reliance.gstn.model.GSTINAddressMapping;

/**
 * @author Pradeep.n.reddy
 *
 */
public interface GSTINAddressMappingDao {

	GSTINAddressMapping getGstinAddressByGstinId(Integer id) throws Exception;

}
