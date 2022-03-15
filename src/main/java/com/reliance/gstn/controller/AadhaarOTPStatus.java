package com.reliance.gstn.controller;



import org.springframework.stereotype.Controller;

@Controller
public class AadhaarOTPStatus {


	

	/*@RequestMapping(value = "/getAadhaarOtpStatus", method = RequestMethod.POST)
	@ResponseBody
	public AadherAuthOtpResponse aadharOtpStatus(Model model,
			@ModelAttribute("userMaster") UserMaster userMaster,
			HttpServletRequest httpRequest, HttpSession httpSession) {
		logger.info("Entry");
		AadherAuthOtpResponse aadhaarOtpResponse = null;
		try {
			AadherAuthOtpRequest aadherAuthOtpRequest = new AadherAuthOtpRequest();
			aadherAuthOtpRequest.setUid("");
			aadhaarOtpResponse=(AadherAuthOtpResponse) aadhaarAuthService.getAadhaarOTP(aadherAuthOtpRequest);
			

		} catch (Exception e) {
			logger.error("Error in:",e);
			System.out.println(e);
		}
		logger.info("Exit");
		return aadhaarOtpResponse;
	}*/
}
