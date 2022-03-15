/**
 * 
 */
package com.reliance.gstn.service;

import java.util.List;

import com.reliance.gstn.model.ManageOffers;

/**
 * @author Pradeep.Gangapuram
 *
 */
public interface ManageOffersService {

	List<ManageOffers> listManageOffers(String idsValuesToFetch, Integer orgUId)throws Exception;
	
	String addManageOffers(ManageOffers manageOffers)throws Exception;
	
	ManageOffers getManageOffersById(Integer id)throws Exception;
	
	String updateManageOffers(ManageOffers manageOffers) throws Exception;
	
	String removeManageOffer(ManageOffers manageOffers) throws Exception;

	List<ManageOffers> getManageOffersByOfferType(String offerTypeId, String offerType)throws Exception;

	boolean checkIfOfferExists(String offer, Integer orgUId)throws Exception;
	
	


}
