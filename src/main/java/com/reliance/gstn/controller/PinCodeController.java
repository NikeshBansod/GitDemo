/**
 * 
 */
package com.reliance.gstn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.reliance.gstn.model.PinCode;
import com.reliance.gstn.service.PinCodeService;

/**
 * @author Nikesh.Bansod
 *
 */

@Controller
public class PinCodeController {
	
	@Autowired
	PinCodeService pinCodeService;
	
	@RequestMapping(value="/getPincodeById",method=RequestMethod.POST)
	public @ResponseBody String postPincodeById(@RequestParam("id") String pinCode, Model model) {
		
		System.out.println("getPincodeById:"+pinCode);
		PinCode pC = pinCodeService.getPinCodeByPinCode(Integer.valueOf(pinCode));
		
		return new Gson().toJson(pC);
		
	}
	
	@RequestMapping(value = "/getPinCodeList", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody List<String> getPinCode(@RequestParam("term") String query) {
		List<String> sacCodeList = getPinCodeList(query);

		return sacCodeList;
	}
	
	private List<String> getPinCodeList(String query) {
	        String pinCode = null;
	        query = query.toLowerCase();
	        List<String> matched = new ArrayList<String>();
	        List<PinCode> distinctPinCodeList = pinCodeService.getPinCodeListByPincode(query);
	        for(int i=0; i < distinctPinCodeList.size(); i++) {
	        	Integer pinCodeInInt = distinctPinCodeList.get(i).getPinCode();
	        	String district = distinctPinCodeList.get(i).getDistrict();
	        	pinCode = String.valueOf(pinCodeInInt);
	            if(pinCode.startsWith(query)) {
	                matched.add(pinCode + " [ "+district+" ] ");
	            }
	        }
	        return matched;   
	}
	

	@RequestMapping(value="/getPincodeByIdAndDistrict",method=RequestMethod.POST)
	public @ResponseBody String postPincodeById(@RequestParam("id") String pinCode, @RequestParam("district") String district, Model model) {
		
		System.out.println("getPincodeById:"+pinCode);
		PinCode pC = pinCodeService.getPinCodeDetailsByPinCodeAndDistrict(Integer.valueOf(pinCode),district);
		
		return new Gson().toJson(pC);
		
	}
	
	

}
