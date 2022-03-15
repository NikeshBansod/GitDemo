/**
 * 
 */
package com.reliance.gstn.model;

/**
 * @author Nikesh.Bansod
 *
 */
public class LoginMaster {

	
	
	private Integer uId;
	private String userId;
	private String userName;
	private String userRole;
//	private String billingFor;
	private Integer primaryUserUId;
	private Integer orgUId;
	private String termsConditionsFlag;
	private String uniqueSequence;
	private String logoImagePath;
	private String panNo;
	private String firmName;
	private boolean loggedInByWizard;
	private String loggedInThrough;
	private String footer;
	private String imeiNo;
	private String dataSend;
	private String invoiceSequenceType;

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	/*public String getBillingFor() {
		return billingFor;
	}

	public void setBillingFor(String billingFor) {
		this.billingFor = billingFor;
	}
*/
	public Integer getPrimaryUserUId() {
		return primaryUserUId;
	}

	public void setPrimaryUserUId(Integer primaryUserUId) {
		this.primaryUserUId = primaryUserUId;
	}

	public Integer getOrgUId() {
		return orgUId;
	}

	public void setOrgUId(Integer orgUId) {
		this.orgUId = orgUId;
	}

	public String getTermsConditionsFlag() {
		return termsConditionsFlag;
	}

	public void setTermsConditionsFlag(String termsConditionsFlag) {
		this.termsConditionsFlag = termsConditionsFlag;
	}

	public String getUniqueSequence() {
		return uniqueSequence;
	}

	public void setUniqueSequence(String uniqueSequence) {
		this.uniqueSequence = uniqueSequence;
	}

	public String getLogoImagePath() {
		return logoImagePath;
	}

	public void setLogoImagePath(String logoImagePath) {
		this.logoImagePath = logoImagePath;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getFirmName() {
		return firmName;
	}

	public void setFirmName(String firmName) {
		this.firmName = firmName;
	}

	public boolean getLoggedInByWizard() {
		return loggedInByWizard;
	}

	public void setLoggedInByWizard(boolean loggedInByWizard) {
		this.loggedInByWizard = loggedInByWizard;
	}

	public String getLoggedInThrough() {
		return loggedInThrough;
	}

	public void setLoggedInThrough(String loggedInThrough) {
		this.loggedInThrough = loggedInThrough;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getDataSend() {
		return dataSend;
	}

	public void setDataSend(String dataSend) {
		this.dataSend = dataSend;
	}

	public String getInvoiceSequenceType() {
		return invoiceSequenceType;
	}

	public void setInvoiceSequenceType(String invoiceSequenceType) {
		this.invoiceSequenceType = invoiceSequenceType;
	}

	

}
