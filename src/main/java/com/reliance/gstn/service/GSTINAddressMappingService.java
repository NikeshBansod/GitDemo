/**
 * 
 */
package com.reliance.gstn.service;

import com.reliance.gstn.model.GSTINAddressMapping;

/**
 * @author Pradeep.n.reddy
 *
 */
public interface GSTINAddressMappingService {

	GSTINAddressMapping getGstinAddressByGstinId(Integer id) throws Exception;

}
