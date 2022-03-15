/**
 * 
 */
package com.reliance.gstn.admin.model;

/**
 * @author Nikesh.Bansod
 *
 */
public class AdminLoginMaster {

	private Integer uId;
	private String userId;
	private String userRole;

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

}
