package com.reliance.gstn.util;

public class TestFromLast {

	public static void main(String[] args) {
		String example = "INVOICE_DEBIT";
		System.out.println(""+example.substring(example.lastIndexOf("_") + 1));
		
		double a = -100;
		double b = -200;
		
		double c = a+b;
		System.out.println(Math.abs(c));

	}

}
