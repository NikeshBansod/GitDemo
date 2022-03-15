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
import org.springframework.transaction.annotation.Transactional;

import com.reliance.gstn.admin.exception.EwayBillApiException;
import com.reliance.gstn.admin.exception.GSTR3BApiException;
import com.reliance.gstn.dao.GSTR3BDao;
import com.reliance.gstn.model.GSTR3BDetails;
import com.reliance.gstn.model.GSTR3BModel;
import com.reliance.gstn.model.Gstr3bUploadDetails;
import com.reliance.gstn.util.NICUtil;

/**
 * @author Nikesh.Bansod
 *
 */
@Repository
public class GSTR3BDaoImpl implements GSTR3BDao {
	private static final Logger logger = Logger.getLogger(GSTR3BDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Value("${get_gstr3b_by_ackno}")
	private String getGstr3bByAckno;

	@Value("${get_gstr3b_jiogst_list}")
	private String getGstr3bJiogstList;

	@Override
	public void saveToJiogst(GSTR3BModel request) throws GSTR3BApiException {
		logger.info("Entry");
		try {
			Session session = sessionFactory.openSession();
			session.getTransaction().begin();
			Gstr3bUploadDetails gstr3bUploadDetails = new Gstr3bUploadDetails();
			gstr3bUploadDetails.setUserId(request.getUserId());
			gstr3bUploadDetails.setGstin(request.getGstin());
			gstr3bUploadDetails.setFpMonth(request.getRet_period().substring(0, 2));
			gstr3bUploadDetails.setFpYear(request.getRet_period().substring(2));
			gstr3bUploadDetails.setFpPeriod(request.getRet_period());
			gstr3bUploadDetails.setGstrType("GSTR3B");
			gstr3bUploadDetails.setAckNo(request.getAckNo());
			gstr3bUploadDetails.setTransactionId(request.getTransactionId());
			gstr3bUploadDetails.setStatus(request.getStatus());
			gstr3bUploadDetails.setUploadDate(NICUtil.getSQLDate());
			gstr3bUploadDetails.setUploadType("");
			gstr3bUploadDetails.setActionTaken("UploadToJioGST");
			session.save(gstr3bUploadDetails);
			String id = gstr3bUploadDetails.getId();
			if (request.getSup_details() != null)
				for (GSTR3BDetails GSTR3BDetails : request.getSup_details()) {
					GSTR3BDetails.setGstrUploadId(id);
					GSTR3BDetails.setCreatedOn(NICUtil.getSQLDate());
					GSTR3BDetails.setSection("sup_details");
					session.save(GSTR3BDetails);
				}
			if (request.getInter_sup() != null)
				for (GSTR3BDetails GSTR3BDetails : request.getInter_sup()) {
					GSTR3BDetails.setGstrUploadId(id);
					GSTR3BDetails.setSection("inter_sup");
					GSTR3BDetails.setCreatedOn(NICUtil.getSQLDate());

					session.save(GSTR3BDetails);
				}
			if (request.getItc_elg() != null)
				for (GSTR3BDetails GSTR3BDetails : request.getItc_elg()) {
					GSTR3BDetails.setGstrUploadId(id);
					GSTR3BDetails.setCreatedOn(NICUtil.getSQLDate());
					GSTR3BDetails.setSection("itc_elg");
					session.save(GSTR3BDetails);
				}
			if (request.getInward_sup() != null)
				for (GSTR3BDetails GSTR3BDetails : request.getInward_sup()) {
					GSTR3BDetails.setGstrUploadId(id);
					GSTR3BDetails.setCreatedOn(NICUtil.getSQLDate());
					GSTR3BDetails.setSection("inward_sup");
					session.save(GSTR3BDetails);
				}
			if (request.getIntr_details() != null) {
				GSTR3BDetails GSTR3BDetails = request.getIntr_details();
				GSTR3BDetails.setGstrUploadId(id);
				GSTR3BDetails.setCreatedOn(NICUtil.getSQLDate());
				GSTR3BDetails.setSection("intr_details");
				session.save(GSTR3BDetails);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error("Error in:", e);
			throw new EwayBillApiException(e.getMessage());
		}

		logger.info("Exit");

	}

	@Override
	@Transactional
	public void updateByAckNo(String ackNo, String status) throws GSTR3BApiException {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(getGstr3bByAckno);
		query.setString("ackNo", ackNo);
		Gstr3bUploadDetails gstr3bUploadDetails = (Gstr3bUploadDetails) query.uniqueResult();
		gstr3bUploadDetails.setStatus(status);
		session.update(gstr3bUploadDetails);

	}

	@Override
	@Transactional
	public Object getGstr3bJiogstList(GSTR3BModel gstr3b) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery(getGstr3bJiogstList);
		query.setString("userId", gstr3b.getUserId());
		List<Gstr3bUploadDetails> gstr3bUploadDetails = (List<Gstr3bUploadDetails>) query.list();
		return gstr3bUploadDetails;
	}

}
