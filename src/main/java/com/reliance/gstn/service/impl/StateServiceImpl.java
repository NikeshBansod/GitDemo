/**
 * 
 */
package com.reliance.gstn.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.dao.StateDAO;
import com.reliance.gstn.model.State;
import com.reliance.gstn.service.StateService;

/**
 * @author Nikesh.Bansod
 *
 */

@Service
public class StateServiceImpl implements StateService {
	
	@Autowired
	StateDAO stateDAO;

	@Override
	@Transactional
	public List<State> listState() throws Exception{
		// TODO Auto-generated method stub
		return stateDAO.listState();
	}

	@Override
	@Transactional
	public String isStateOrUnionTerritory(Integer stateId) {
		// TODO Auto-generated method stub
		return stateDAO.isStateOrUnionTerritory(stateId);
	}

	@Override
	@Transactional
	public State getStateByStateName(String stateName) throws Exception{
		// TODO Auto-generated method stub
		return stateDAO.getStateByStateName(stateName);
	}

	@Override
	@Transactional
	public List<State> getStateByStateId(Integer stateId) throws Exception{
		// TODO Auto-generated method stub
		return stateDAO.getStateByStateId(stateId);
	}

	@Override
	public State getStateById(Integer stateId) throws Exception {
		// TODO Auto-generated method stub
		return stateDAO.getStateById(stateId);
	}

}
