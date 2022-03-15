/**
 * 
 */
package com.reliance.gstn.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reliance.gstn.model.InvoiceDetails;
import com.reliance.gstn.model.LoginMaster;
import com.reliance.gstn.model.UserMaster;
import com.reliance.gstn.service.GenerateInvoiceService;
import com.reliance.gstn.service.UserMasterService;
import com.reliance.gstn.util.GSTNConstants;
import com.reliance.gstn.util.LoginUtil;
/**
 * @author Nikesh.Bansod
 *
 */
@RestController
public class TallyInvoiceController {
	
	private static final Logger logger = Logger.getLogger(TallyInvoiceController.class);
	
	@Autowired
	GenerateInvoiceService generateInvoiceService;
	
	@Autowired
	private UserMasterService userMasterService; 
	
	@RequestMapping(value="/xmlInvDetail",method=RequestMethod.POST,produces=MediaType.APPLICATION_XML_VALUE)
	public String xmlInvDetail(@RequestParam("id") String invoiceId,HttpServletRequest httpRequest) throws Exception {
		logger.info("Entry");
		InvoiceDetails invoiceDetails = null;
		LoginMaster loginMaster = (LoginMaster)LoginUtil.getLoginUser(httpRequest); 
		UserMaster user = null;
		String xmlInvoiceResponse = null;
		try {
			boolean isInvoiceAllowed = generateInvoiceService.validateInvoiceAgainstOrgId(Integer.parseInt(invoiceId),loginMaster.getOrgUId());
			if(isInvoiceAllowed){
				user = userMasterService.getUserMasterById( loginMaster.getuId());
				if(!loginMaster.getUserRole().equals(GSTNConstants.PRIMARY_USER)){
					user = userMasterService.getUserMasterById(user.getReferenceId());
				}
				invoiceDetails = generateInvoiceService.getInvoiceDetailsById(Integer.parseInt(invoiceId));
				xmlInvoiceResponse = generateInvoiceService.fetchInvoiceinXml(invoiceDetails,user);
			    
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error in:",e);
		}
		
		logger.info("Exit");
	 //  return new XStream().toXML(xmlInvoiceResponse);
	//	return new String(xmlInvoiceResponse);
		return xmlInvoiceResponse;
	}
}
