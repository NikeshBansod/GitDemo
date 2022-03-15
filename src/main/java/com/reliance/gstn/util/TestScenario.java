package com.reliance.gstn.util;

import java.util.ArrayList;
import java.util.List;

import com.reliance.gstn.model.InvoiceCnDnDetails;
import com.reliance.gstn.model.InvoiceServiceDetails;

public class TestScenario {
	public static void main(String[] args) {
		List<InvoiceServiceDetails> servList = dummyServiseList();
		List<InvoiceCnDnDetails> cndnList = dummyCndnList();
		boolean considerAdditionalAmount = false;
		
		for(InvoiceServiceDetails existingService : servList){
			for(InvoiceCnDnDetails cndn : cndnList){
				if(cndn.getServiceId().equals(existingService.getId())){
					if(cndn.getQuantity() == existingService.getQuantity()){
						considerAdditionalAmount = true;
						continue;
					}else{
						considerAdditionalAmount = false;
						
					}
				}
			}
			if(!considerAdditionalAmount){
				break;
			}
			
		}
		
		if(considerAdditionalAmount){
			System.out.println("Match");
			System.out.println("Consider Add Chgs");
		}else{
			System.out.println("Not-Match");
			System.out.println("Donot Consider Add Chgs");
		}
	}

	private static List<InvoiceCnDnDetails> dummyCndnList() {
		List<InvoiceCnDnDetails> cndnList = new ArrayList<InvoiceCnDnDetails>();
		InvoiceCnDnDetails ai = new InvoiceCnDnDetails();
		ai.setServiceId(1);
		ai.setQuantity(10);
		cndnList.add(ai);
		
		InvoiceCnDnDetails ai1 = new InvoiceCnDnDetails();
		ai1.setQuantity(20);
		ai1.setServiceId(2);
		cndnList.add(ai1);
		return cndnList;
	}

	private static List<InvoiceServiceDetails> dummyServiseList() {
		List<InvoiceServiceDetails> servList = new ArrayList<InvoiceServiceDetails>();
		InvoiceServiceDetails ai = new InvoiceServiceDetails();
		ai.setQuantity(10);
		ai.setId(1);
		servList.add(ai);
		
		InvoiceServiceDetails ai1 = new InvoiceServiceDetails();
		ai1.setQuantity(20);
		ai1.setId(2);
		servList.add(ai1);
		return servList;
	}
}
