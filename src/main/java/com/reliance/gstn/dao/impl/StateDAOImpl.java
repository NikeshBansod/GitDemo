/**
 * 
 */
package com.reliance.gstn.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.dao.StateDAO;
import com.reliance.gstn.model.State;
//import com.reliance.gstn.util.LoggerUtil;

/**
 * @author Nikesh.Bansod
 *
 */
@Repository
public class StateDAOImpl implements StateDAO {

	private static final Logger logger = Logger.getLogger(StateDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${state_list_query}")
	private String stateListQuery;

	@Value("${state_list_by_id}")
	private String stateListById;

	@Value("${state_details_by_name}")
	private String stateDetailsByNameQuery;

	@Value("${state_details_by_id}")
	private String stateDetailsByIdQuery;

	@Value("${get_state_details_by_id}")
	private String stateCodeByIdQuery;

	@Override
	public List<State> listState() throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(stateListQuery);

		@SuppressWarnings("unchecked")
		List<State> stateList = (List<State>) query.list();

		logger.info("Exit");
		return stateList;
	}

	@Override
	public String isStateOrUnionTerritory(Integer stateId) {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(stateListById);
		query.setInteger("stateId", stateId);
		String stateOrUnionTerritory = null;
		@SuppressWarnings("unchecked")
		List<State> stateList = (List<State>) query.list();

		if (!stateList.isEmpty()) {
			stateOrUnionTerritory = stateList.get(0).getStateType();
		}

		logger.info("Exit");
		return stateOrUnionTerritory;
	}

	@Override
	public State getStateByStateName(String stateName) throws Exception {
		// TODO Auto-generated method stub stateDetailsByNameQuery stateName
		logger.info("Entry");
		State state = null;
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(stateDetailsByNameQuery);
		query.setString("stateName", stateName);

		@SuppressWarnings("unchecked")
		List<State> stateList = (List<State>) query.list();

		if (!stateList.isEmpty()) {
			state = stateList.get(0);
		}

		logger.info("Exit");
		return state;
	}

	@Override
	public List<State> getStateByStateId(Integer stateId) throws Exception {

		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(stateDetailsByIdQuery);
		query.setInteger("stateId", stateId);

		@SuppressWarnings("unchecked")
		List<State> stateList = (List<State>) query.list();

		logger.info("Exit");
		return stateList;
	}

	@Override
	public State getStateById(Integer stateId) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(stateCodeByIdQuery);
		query.setInteger("id", stateId);
		State stateList = (State) query.uniqueResult();
		logger.info("Exit");
		return stateList;
	}

}
