package com.reliance.gstn.dao.impl;

import java.util.Date;
import java.util.List;





import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.dao.UnificationDao;
import com.reliance.gstn.model.OrganizationMasterUnification;
import com.reliance.gstn.model.Product;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.model.UserMasterUnification;
import com.reliance.gstn.util.GSTNConstants;


@Repository
public class UnificationDaoImpl implements UnificationDao{
	
	private static final Logger logger = Logger.getLogger(UnificationDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${state_by_id}")
	private String stateById; 
	
	@Value("${get_user_emailId}")
	private String emailByContact;

	@Override
    public String saveOrUpdateUserDetails(UserMasterUnification userMasterUnification) {
          logger.info("Entry");
          String response;
          String stateName ="";
          try{
          Session session = sessionFactory.getCurrentSession();
          userMasterUnification.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
          userMasterUnification.setUniqueSequence("1");
          userMasterUnification.setInvoiceSequenceType("auto");
          session.persist(userMasterUnification);
        userMasterUnification=(UserMasterUnification)session.get(UserMasterUnification.class, userMasterUnification.getId());
          
    
          OrganizationMasterUnification orgMaster = userMasterUnification.getOrganizationMasterUnification();
          stateName=orgMaster.getState();
          Integer id=getStateId(session,stateName);
          orgMaster.setState(String.valueOf(id));
          orgMaster.setCreatedBy(userMasterUnification.getId().toString());
          orgMaster.setCreatedOn(new java.sql.Timestamp(new Date().getTime()));
          orgMaster.setUserMasterUnification(userMasterUnification);
          session.persist(orgMaster);
          response = GSTNConstants.SUCCESS;
          }catch(Exception e){
                logger.error("Error in:",e);
                throw e;
          }
          logger.info("Exit");
          return response;
          
    }

	private Integer getStateId(Session session,String stateName){
        Integer i=0;
        
        Query query = session.createQuery(stateById);
        query.setString("stateName", stateName);
        @SuppressWarnings("unchecked")
        List<Object> list = (List<Object>)query.list();
        if(null!=list && !list.isEmpty()){
              i=(Integer)list.get(0);
        }
        return i;
  }
	
	@Override
	@Transactional
	public String getEmailByContact(String contactNo){
		Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(emailByContact);
        query.setString("contactNo", contactNo);
       String emailid=null;
        UserMasterUnification userMasterUnification =  (UserMasterUnification) query.uniqueResult();
        if(userMasterUnification!=null){
        	emailid = userMasterUnification.getDefaultEmailId();
        }
        return emailid;
  }



	

}
