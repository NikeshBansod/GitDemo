/**
 * 
 */
package com.reliance.gstn.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.dao.GSTINAddressMappingDao;
import com.reliance.gstn.model.GSTINAddressMapping;

/**
 * @author Pradeep.n.reddy
 *
 */
@Repository
public class GSTINAddressMappingDaoImpl implements GSTINAddressMappingDao {
	
	private static final Logger logger = Logger.getLogger(GSTINAddressMappingDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Value("${gstin_address_from_gstin_no}")
	private String gstinAddressFromGstinNo;

	@Override
	public GSTINAddressMapping getGstinAddressByGstinId(Integer id) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		//GSTINAddressMapping gstinAddressMapping = (GSTINAddressMapping)session.get(GSTINAddressMapping.class, id);
		Query query = session.createSQLQuery(gstinAddressFromGstinNo);
		query.setInteger("gstinId", id);
		GSTINAddressMapping gstinAddressMapping = new GSTINAddressMapping();
		
		@SuppressWarnings("unchecked")
		List<GSTINAddressMapping> gstinAddressMappingList = (List<GSTINAddressMapping>)query.list();
		
		if (!gstinAddressMappingList.isEmpty()) {
			gstinAddressMapping = gstinAddressMappingList.get(0);
		}
		
		logger.info("Exit");
		return gstinAddressMapping;
	}

}
