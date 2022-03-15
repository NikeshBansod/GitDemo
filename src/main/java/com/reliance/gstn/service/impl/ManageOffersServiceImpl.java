/**
 * 
 */
package com.reliance.gstn.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reliance.gstn.dao.ManageOffersDAO;
import com.reliance.gstn.model.ManageOffers;
import com.reliance.gstn.service.ManageOffersService;

/**
 * @author Pradeep.Gangapuram
 *
 */
@Service
public class ManageOffersServiceImpl implements ManageOffersService {
	
	@Autowired
	public ManageOffersDAO manageOffersDAO;

	@Override
	@Transactional
	public List<ManageOffers> listManageOffers(String idsValuesToFetch,Integer orgUId) throws Exception {
		return manageOffersDAO.listManageOffers(idsValuesToFetch,orgUId);
	}

	@Override
	@Transactional
	public String addManageOffers(ManageOffers manageOffers) throws Exception {
		
		return manageOffersDAO.addManageOffers(manageOffers);
	}
	
	@Override
	@Transactional
	public ManageOffers getManageOffersById(Integer id) throws Exception {
		
		return manageOffersDAO.getManageOffersById(id);
	}
	
	@Override
	@Transactional
	public String updateManageOffers(ManageOffers manageOffers) throws Exception {
		
		return manageOffersDAO.updateManageOffers(manageOffers);
	}
	
	@Override
	@Transactional
	public String removeManageOffer(ManageOffers manageOffers)  throws Exception{
		
		return  manageOffersDAO.removeManageOffers(manageOffers);
	}

	@Override
	@Transactional
	public List<ManageOffers> getManageOffersByOfferType(String offerTypeId, String offerType) throws Exception{
		// TODO Auto-generated method stub
		return manageOffersDAO.getManageOffersByOfferType( offerTypeId, offerType);
	}

	@Override
	@Transactional
	public boolean checkIfOfferExists(String offer, Integer orgUId) throws Exception{
		// TODO Auto-generated method stub
		return manageOffersDAO.checkIfOfferExists(offer,orgUId);
	}
}
