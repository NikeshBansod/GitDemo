package com.reliance.gstn.util;

import java.util.Date;

public class Test {

	public static void main(String[] args) {
		String d="/Date(1499299200000)/";
		
		System.err.println(getStingDateToSQLDate(d));

		
		
	}
	
	public static java.sql.Date getStingDateToSQLDate(String date) {
		java.sql.Date sqlDate = null;
		if (date != null) {
			date = date.replace("/Date(", "").replace(")/", "");
			Long currentDateTime = Long.parseLong(date);
			Date currentDate = new Date(currentDateTime);
			sqlDate = new java.sql.Date(currentDate.getTime());
		}
		return sqlDate;
	}


}
