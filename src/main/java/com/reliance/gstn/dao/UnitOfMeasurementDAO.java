/**
 * 
 */
package com.reliance.gstn.dao;

import java.util.List;

import com.reliance.gstn.model.UnitOfMeasurement;

/**
 * @author Pradeep.Gangapuram
 *
 */
public interface UnitOfMeasurementDAO {

	List<UnitOfMeasurement> getUnitOfMeasurement()throws Exception;

}
