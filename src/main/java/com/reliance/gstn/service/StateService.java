/**
 * 
 */
package com.reliance.gstn.service;

import java.util.List;

import com.reliance.gstn.model.State;

/**
 * @author Nikesh.Bansod
 *
 */
public interface StateService {

	List<State> listState() throws Exception;

	String isStateOrUnionTerritory(Integer stateId);

	State getStateByStateName(String stateName) throws Exception;

	List<State> getStateByStateId(Integer stateId) throws Exception;

	State getStateById(Integer stateId) throws Exception;

}
