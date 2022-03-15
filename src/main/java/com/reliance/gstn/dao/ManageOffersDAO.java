/**
 * 
 */
package com.reliance.gstn.dao;

import java.util.List;

import com.reliance.gstn.model.ManageOffers;

/**
 * @author Pradeep.Gangapuram
 *
 */
public interface ManageOffersDAO {

	List<ManageOffers> listManageOffers(String idsValuesToFetch, Integer orgUId)throws Exception;
	
	String addManageOffers(ManageOffers manageOffers);
	
	ManageOffers getManageOffersById(Integer id);
	
	String updateManageOffers(ManageOffers manageOffers) throws Exception;
	
	String removeManageOffers(ManageOffers manageOffers)  throws Exception;

	List<ManageOffers> getManageOffersByOfferType(String offerTypeId, String offerType)throws Exception;

	boolean checkIfOfferExists(String offer, Integer orgUId);
	
	
}
