package com.reliance.gstn.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.reliance.gstn.admin.model.HSNDetails;
import com.reliance.gstn.admin.model.ProductRateOfTaxDetails;
import com.reliance.gstn.admin.model.ServiceRateOfTaxDetails;
import com.reliance.gstn.dao.WizardBulkUploadDAO;
import com.reliance.gstn.model.CustomerDetails;
import com.reliance.gstn.model.ManageServiceCatalogue;
import com.reliance.gstn.model.PinCode;
import com.reliance.gstn.model.Product;
import com.reliance.gstn.model.SACCode;
import com.reliance.gstn.model.State;
import com.reliance.gstn.model.UnitOfMeasurement;

@Repository
public class WizardBulkUploadDAOImpl implements WizardBulkUploadDAO {

	private static final Logger logger = Logger.getLogger(WizardBulkUploadDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private static final int REC_COUNT = 1000;

	@Override
	public String uploadMasterExcelData(List<?> entityList) throws Exception {
		logger.info("Entry");
		// need to write logic of insert or update
		String msg = "Data Upload fail";
		Session session = sessionFactory.getCurrentSession();
		entityList = identifyUpdateObjects(entityList, session);
		session.clear();
		int index = 0;
		for (Object object : entityList) {
			session.saveOrUpdate(object);
			index++;
			if (index % 100 == 0) {
				session.flush();
				session.clear();
			}
		}
		msg = index + " Records Uploaded Successfully";
		logger.info("Exit");
		return msg;
	}

	private List<?> identifyUpdateObjects(List<?> entityList, Session session) {
		logger.info("Entry");
		Criteria criteria = getCriteria(session, entityList);
		criteria.setProjection(Projections.rowCount());

		@SuppressWarnings("unchecked")
		List<Long> resultList = (List<Long>) criteria.list();
		long rowCount = resultList.get(0).intValue();
		long noOfLoop = getNoOfLoop(rowCount);

		int loopCounter = 0;
		while (loopCounter < noOfLoop) {
			criteria = getCriteria(session, entityList).setFirstResult(loopCounter * WizardBulkUploadDAOImpl.REC_COUNT)
					.setMaxResults(WizardBulkUploadDAOImpl.REC_COUNT);
			@SuppressWarnings("rawtypes")
			List list = criteria.list();
			compareAndUpdateIdOfEntity(entityList, list);
			loopCounter++;
		}

		logger.info("Exit");
		return entityList;
	}

	private Criteria getCriteria(Session session, List<?> entityList) {
		logger.info("Entry");
		Criteria criteria = null;
		if (entityList.get(0).getClass() == CustomerDetails.class) {
			@SuppressWarnings("unchecked")
			List<CustomerDetails> customerDetails = (List<CustomerDetails>) entityList;
			criteria = session.createCriteria(CustomerDetails.class)
					.add(Restrictions.eq("refOrgId", customerDetails.get(0).getRefOrgId()));
		} else if (entityList.get(0).getClass() == Product.class) {
			@SuppressWarnings("unchecked")
			List<Product> productList = (List<Product>) entityList;
			criteria = session.createCriteria(Product.class)
					.add(Restrictions.eq("refOrgId", productList.get(0).getRefOrgId()));
		} else if (entityList.get(0).getClass() == ManageServiceCatalogue.class) {
			@SuppressWarnings("unchecked")
			List<ManageServiceCatalogue> serviceList = (List<ManageServiceCatalogue>) entityList;
			criteria = session.createCriteria(ManageServiceCatalogue.class)
					.add(Restrictions.eq("refOrgId", serviceList.get(0).getRefOrgId()));
		}

		logger.info("Exit");
		return criteria;
	}

	private void compareAndUpdateIdOfEntity(List<?> entityList, @SuppressWarnings("rawtypes") List list) {
		logger.info("Entry");
		if (entityList.get(0).getClass() == CustomerDetails.class) {
			@SuppressWarnings("unchecked")
			List<CustomerDetails> customerDetailsList = (List<CustomerDetails>) entityList;
			@SuppressWarnings("unchecked")
			List<CustomerDetails> dbCustomerDetailsList = (List<CustomerDetails>) list;
			compareAndUpdateIdOfCustomerEntity(customerDetailsList, dbCustomerDetailsList);
		} else if (entityList.get(0).getClass() == Product.class) {
			@SuppressWarnings("unchecked")
			List<Product> productList = (List<Product>) entityList;

			@SuppressWarnings("unchecked")
			List<Product> dbProductList = (List<Product>) list;
			compareAndUpdateIdOfProductEntity(productList, dbProductList);
		} else if (entityList.get(0).getClass() == ManageServiceCatalogue.class) {
			@SuppressWarnings("unchecked")
			List<ManageServiceCatalogue> serviceList = (List<ManageServiceCatalogue>) entityList;

			@SuppressWarnings("unchecked")
			List<ManageServiceCatalogue> dbServiceList = (List<ManageServiceCatalogue>) list;
			compareAndUpdateIdOfServiceEntity(serviceList, dbServiceList);
		}

		logger.info("Exit");
	}

	private void compareAndUpdateIdOfCustomerEntity(List<CustomerDetails> customerDetailsList,
			List<CustomerDetails> dbCustomerDetailsList) {
		logger.info("Entry");
		for (CustomerDetails customerDetails : customerDetailsList) {
			for (CustomerDetails dbcustomerDetails : dbCustomerDetailsList) {
				if (customerDetails.equals(dbcustomerDetails)) {
					customerDetails.setId(dbcustomerDetails.getId());
					break;
				}
				/*
				 * if(dbcustomerDetails.equals(customerDetails)){
				 * customerDetails.setId(dbcustomerDetails.getId()); break; }
				 */
			}
		}
		logger.info("Exit");
	}

	private long getNoOfLoop(long rowCount) {
		logger.info("Entry");
		long noOfLoop = rowCount / WizardBulkUploadDAOImpl.REC_COUNT;
		if (rowCount % WizardBulkUploadDAOImpl.REC_COUNT != 0) {
			noOfLoop++;
		}
		logger.info("Exit");
		return noOfLoop;
	}

	private void compareAndUpdateIdOfProductEntity(List<Product> productList, List<Product> dbProductList) {
		logger.info("Entry");
		for (Product product : productList) {
			for (Product dbProduct : dbProductList) {
				if (product.equals(dbProduct)) {
					product.setId(dbProduct.getId());
					break;
				}
			}
		}
		logger.info("Exit");
	}

	private void compareAndUpdateIdOfServiceEntity(List<ManageServiceCatalogue> serviceList,
			List<ManageServiceCatalogue> dbServiceList) {
		logger.info("Entry");
		for (ManageServiceCatalogue service : serviceList) {
			for (ManageServiceCatalogue dbService : dbServiceList) {
				if (service.equals(dbService)) {
					service.setId(dbService.getId());
					break;
				}
			}
		}
		logger.info("Exit");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getRequiredData(String masterType) throws Exception {
		logger.info("Entry");
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = null;
		List<Object> requiredDatalist = new ArrayList<Object>();

		if (masterType.equals("customermastertemplate")) {
			List<PinCode> pincodeList = null;
			criteria = session.createCriteria(PinCode.class);
			pincodeList = criteria.list();
			if (!pincodeList.isEmpty() && pincodeList != null) {
				requiredDatalist.add(pincodeList);
			}

			List<State> stateList = null;
			criteria = session.createCriteria(State.class);
			stateList = criteria.list();
			if (!stateList.isEmpty() && stateList != null) {
				requiredDatalist.add(stateList);
			}
		} else {
			List<HSNDetails> hsnDetailList = null;
			List<SACCode> sacDetailList = null;
			List<UnitOfMeasurement> uomList = null;
			List<ProductRateOfTaxDetails> productRateOfTaxList = null;
			List<ServiceRateOfTaxDetails> serviceRateOfTaxList = null;

			criteria = session.createCriteria(UnitOfMeasurement.class);
			uomList = criteria.list();
			if (!uomList.isEmpty() && uomList != null) {
				requiredDatalist.add(uomList);
			}

			if (masterType.equals("goodsmastertemplate")) {
				criteria = session.createCriteria(HSNDetails.class);
				hsnDetailList = criteria.list();
				if (!hsnDetailList.isEmpty() && hsnDetailList != null) {
					requiredDatalist.add(hsnDetailList);
				}

				criteria = session.createCriteria(ProductRateOfTaxDetails.class);
				productRateOfTaxList = criteria.list();
				if (!productRateOfTaxList.isEmpty() && productRateOfTaxList != null) {
					requiredDatalist.add(productRateOfTaxList);
				}
			} else {
				criteria = session.createCriteria(SACCode.class);
				sacDetailList = criteria.list();
				if (!sacDetailList.isEmpty() && sacDetailList != null) {
					requiredDatalist.add(sacDetailList);
				}

				criteria = session.createCriteria(ServiceRateOfTaxDetails.class);
				serviceRateOfTaxList = criteria.list();
				if (!serviceRateOfTaxList.isEmpty() && serviceRateOfTaxList != null) {
					requiredDatalist.add(serviceRateOfTaxList);
				}
			}
		}

		logger.info("Exit");
		return requiredDatalist;
	}

}
