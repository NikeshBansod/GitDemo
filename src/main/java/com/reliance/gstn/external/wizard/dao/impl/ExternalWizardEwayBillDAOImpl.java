package com.reliance.gstn.external.wizard.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.admin.exception.EwayBillApiException;
import com.reliance.gstn.external.wizard.dao.ExternalWizardEwayBillDAO;
import com.reliance.gstn.model.EwayBillWIAuth;
import com.reliance.gstn.model.EwayBillWIMaster;
import com.reliance.gstn.model.EwayBillWIMasterRes;

@Repository
public class ExternalWizardEwayBillDAOImpl implements ExternalWizardEwayBillDAO {

	private static final Logger logger = Logger.getLogger(ExternalWizardEwayBillDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Value(value = "${get_wizard_master_query}")
	private String getWizardMasterQuery;

	@Value(value = "${get_wizard_master_children_query}")
	private String getWizardChildrenQuery;

	@Value(value = "${get_wizard_document_master_query}")
	private String getWizardDocMasterQuery;
	
	@Override
	public List<EwayBillWIMasterRes> getMasters(EwayBillWIMaster EwayBillWIMaster) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		int parentId = getParentId(session, EwayBillWIMaster);
		SQLQuery sqlQuery = session.createSQLQuery(getWizardChildrenQuery);
		sqlQuery.setInteger("parentId", parentId);
		List<EwayBillWIMasterRes> ewayBillWIMasterL = new ArrayList<EwayBillWIMasterRes>();
		@SuppressWarnings("unchecked")
		List<Object[]> result = sqlQuery.list();
		if (!result.isEmpty())
			for (Object[] objects : result) {
				EwayBillWIMasterRes ebwim = new EwayBillWIMasterRes();
				ebwim.setValue(String.valueOf(objects[0]));
				ebwim.setCode(String.valueOf(objects[1]));
				ewayBillWIMasterL.add(ebwim);
			}
		else
			throw new EwayBillApiException("Invalid MasterType");
		logger.info("Exit");
		return ewayBillWIMasterL;
	}

	private int getParentId(Session session, EwayBillWIMaster EwayBillWIMaster) {
		int parentId = 0;
		if(EwayBillWIMaster.getMasterSubType() != "" && EwayBillWIMaster.getMasterSubType() != null){
			Query query = session.createQuery(getWizardDocMasterQuery);
			query.setString("masterType", EwayBillWIMaster.getMasterType());
			query.setString("masterSubType", EwayBillWIMaster.getMasterSubType());
			query.setString("isActive", "Y");
			@SuppressWarnings("unchecked")
			List<EwayBillWIMaster> ewayBillWIMasterL = (List<EwayBillWIMaster>) query.list();
			
			if (!ewayBillWIMasterL.isEmpty()) {
				parentId = ewayBillWIMasterL.get(0).getId();
			}
		} else {
		Query query = session.createQuery(getWizardMasterQuery);
		query.setString("masterType", EwayBillWIMaster.getMasterType());
		query.setString("isActive", "Y");
		@SuppressWarnings("unchecked")
		List<EwayBillWIMaster> ewayBillWIMasterL = (List<EwayBillWIMaster>) query.list();
		
		if (!ewayBillWIMasterL.isEmpty()) {
			parentId = ewayBillWIMasterL.get(0).getId();
		}
		}
		return parentId;
	}

	@Override
	public List<EwayBillWIAuth> getAuthInfo() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from EwayBillWIAuth");
		@SuppressWarnings("unchecked")
		List<EwayBillWIAuth> ewayBillAuthL = (List<EwayBillWIAuth>) query.list();
		return ewayBillAuthL;
	}
	
	@Override
	public List<EwayBillWIMaster> getEwayBillWIMasterList() throws Exception {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from EwayBillWIMaster");
		@SuppressWarnings("unchecked")
		List<EwayBillWIMaster> ewayBillAuthL = (List<EwayBillWIMaster>) query.list();
		return ewayBillAuthL;
	}
	

}
