package com.reliance.gstn.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoicePdfDelete {

	public static void main(String[] args) {
		String storageDirectory = "C:/apps/gstn/invoice";
		String organizationId = "226";
		List<String> presentInvoiceList = new ArrayList<String>();
		
		presentInvoiceList = getPaths(storageDirectory,organizationId);
		for(String a : presentInvoiceList){
			System.out.println(a);
		}
		
		System.out.println("==============");

	}

	public static List<String> getPaths(String storageDirectory, String organizationId) {
		List<String> presentInvoiceList = new ArrayList<String>();
		int fromYear = 2017;
		int fromMonth = 7;
		String currentdate = getCurrentDateInyrMMM();
		int toYear = Integer.parseInt(currentdate.split("---")[0]);
		int toMonth = Integer.parseInt(currentdate.split("---")[1]);
		String month = null;
		String dynamicMonthAndyear = null;
		for(int year = fromYear; year <= toYear; year++){
			if(( fromYear == 2017)){
				if(toYear == 2017){
					for(int z = 7; z <= toMonth; z++){
						switch(z){  
						    case 7: month = "July";
				    				break; 
						    case 8: month = "August";
				    				break; 
						    case 9: month = "September";
				    				break; 
						    case 10: month = "October";
				    				break; 
						    case 11: month = "November";
				    				break; 
						    case 12: month = "December";
				    				break;  
						} 
				    dynamicMonthAndyear = storageDirectory+File.separator+year+File.separator+month+File.separator+organizationId;
				    presentInvoiceList.add(dynamicMonthAndyear);
					}
				}else{
					if(year == 2017){
						for(int z = 7; z <= 12; z++){
							switch(z){  
						    case 7: month = "July";
				    				break; 
						    case 8: month = "August";
				    				break; 
						    case 9: month = "September";
				    				break; 
						    case 10: month = "October";
				    				break; 
						    case 11: month = "November";
				    				break; 
						    case 12: month = "December";
				    				break;  
					    } 
					    dynamicMonthAndyear = storageDirectory+File.separator+year+File.separator+month+File.separator+organizationId;
					    presentInvoiceList.add(dynamicMonthAndyear);
						}
					}
					
					
					if(year == toYear){
						for(int z = 1; z <= toMonth; z++){
						    switch(z){  
							    case 1: month = "January";
							    	    break;  
							    case 2: month = "February";
							    		break; 
							    case 3: month = "March";
					    	    		break; 
							    case 4: month = "April";
					    				break; 
							    case 5: month = "May";
					    				break; 
							    case 6: month = "June";
					    				break; 
							    case 7: month = "July";
					    				break; 
							    case 8: month = "August";
					    				break; 
							    case 9: month = "September";
					    				break; 
							    case 10: month = "October";
					    				break; 
							    case 11: month = "November";
					    				break; 
							    case 12: month = "December";
					    				break;  
						    } 
						    dynamicMonthAndyear = storageDirectory+File.separator+year+File.separator+month+File.separator+organizationId;
						    presentInvoiceList.add(dynamicMonthAndyear);
						}
					}else{
						if(year != 2017){
							for(int z = 1; z <= 12; z++){
							    switch(z){  
								    case 1: month = "January";
								    	    break;  
								    case 2: month = "February";
								    		break; 
								    case 3: month = "March";
						    	    		break; 
								    case 4: month = "April";
						    				break; 
								    case 5: month = "May";
						    				break; 
								    case 6: month = "June";
						    				break; 
								    case 7: month = "July";
						    				break; 
								    case 8: month = "August";
						    				break; 
								    case 9: month = "September";
						    				break; 
								    case 10: month = "October";
						    				break; 
								    case 11: month = "November";
						    				break; 
								    case 12: month = "December";
						    				break;  
							    } 
							    dynamicMonthAndyear = storageDirectory+File.separator+year+File.separator+month+File.separator+organizationId;
							    presentInvoiceList.add(dynamicMonthAndyear);
							}
						}
					}
				}
				
			}
		}
	
		
		
		
		return presentInvoiceList;
	}
	
	public static String getCurrentDateInyrMMM() {
		Date dateRef = new Date();
		String formattedDate = new SimpleDateFormat("yyyy---MM").format(dateRef);
		System.out.println("formattedDate : "+formattedDate);
		return formattedDate;
		//return "2021---04";
	}

}
