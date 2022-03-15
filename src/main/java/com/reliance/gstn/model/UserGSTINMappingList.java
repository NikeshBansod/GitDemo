/**
 * 
 */
package com.reliance.gstn.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author pradeep.n.reddy
 *
 */
public class UserGSTINMappingList implements Serializable {

	private static final long serialVersionUID = -5125397875612256732L;


	private List<GSTINUserMapping> userMasterList;
	
	private List<GSTINUserMapping> gstinUserMappingList;

	public List<GSTINUserMapping> getUserMasterList() {
		return userMasterList;
	}

	public void setUserMasterList(List<GSTINUserMapping> secondaryUserNames) {
		this.userMasterList = secondaryUserNames;
	}

	public List<GSTINUserMapping> getGstinUserMappingList() {
		return gstinUserMappingList;
	}

	public void setGstinUserMappingList(List<GSTINUserMapping> gstinUserMappingList) {
		this.gstinUserMappingList = gstinUserMappingList;
	}

	
	

}
