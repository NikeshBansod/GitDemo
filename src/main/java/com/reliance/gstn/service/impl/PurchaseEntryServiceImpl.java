package com.reliance.gstn.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.dao.PurchaseEntryDAO;
import com.reliance.gstn.model.PurchaseEntryDetails;
import com.reliance.gstn.service.PurchaseEntryService;
/**
 * @author @kshay Mohite
 *
 */
@Service
public class PurchaseEntryServiceImpl implements PurchaseEntryService {

	private static final Logger logger = Logger.getLogger(PurchaseEntryServiceImpl.class);

	@Autowired
	PurchaseEntryDAO purchaseEntryDAO;

	@Override
	@Transactional
	public String getLatestInvoiceNumber(String pattern, Integer orgUId) {
		return purchaseEntryDAO.getLatestInvoiceNumber(pattern, orgUId);
	}

	@Override
	@Transactional
	public Map<String, String> addGeneratePurchaseEntryInvoice(PurchaseEntryDetails purchaseEntryDetails) throws Exception {
		return purchaseEntryDAO.addGeneratePurchaseEntryInvoice(purchaseEntryDetails);
	}

	@Override
	@Transactional
	public List<Object[]> getPurchaseEntriesDetailByOrgUId(Integer orgUId) {
		return purchaseEntryDAO.getPurchaseEntriesDetailByOrgUId(orgUId);
	}

	@Override
	@Transactional
	public boolean validateInvoiceAgainstOrgId(Integer purchaseEntryDetailsId, Integer orgUId) throws Exception {
		return purchaseEntryDAO.validateInvoiceAgainstOrgId(purchaseEntryDetailsId, orgUId);
	}

	@Override
	@Transactional
	public PurchaseEntryDetails getPurchaseEntryInvoiceDetailsById(Integer id) throws Exception {
		return purchaseEntryDAO.getPurchaseEntryInvoiceDetailsById(id);
	}

	@Override
	@Transactional
	public String deletePurchaseEntryInvoice(int purchaseEntryDetailsId) throws Exception {
		return purchaseEntryDAO.deletePurchaseEntryInvoice(purchaseEntryDetailsId);
	}



}
