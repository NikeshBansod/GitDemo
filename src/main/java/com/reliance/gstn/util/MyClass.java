package com.reliance.gstn.util;

import java.text.DecimalFormat;

public class MyClass {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Double val = 3.51000976582E11;
		
		System.out.println(formatDecimal(val));
		//351000976582
		
	}

	public static Object formatDecimal(Double d) {
		DecimalFormat df = new DecimalFormat("###.##");
		String val = df.format(d);
		
		return val;
	}

}
