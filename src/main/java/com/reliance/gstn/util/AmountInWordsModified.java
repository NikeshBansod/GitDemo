package com.reliance.gstn.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountInWordsModified {
	
	public static String convertNumberToWords(String amtBefore){
		String amt = new BigDecimal(amtBefore).setScale(2, RoundingMode.DOWN).toPlainString();
		String str2 = "", str1="";
		long rupees=0;
		int paise=0;
		NumToWordsModified w = new NumToWordsModified();
		String paiseInString = null;
		try {
			
			rupees = Long.parseLong(amt.split("\\.")[0]);		
			if (rupees>0) 
				{	str1 = w.NumberToWords(rupees);
					str1 += " Rupees ";
				}
			paiseInString = amt.split("\\.")[1];
			if(paiseInString.length() < 2){
				if(paiseInString.length() == 0){
					paiseInString = "00";
				}
				if(paiseInString.length() == 1){
					paiseInString = paiseInString + "0";
				}
			}
			paise = Integer.parseInt(paiseInString);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		if (paise != 0) {
			str2 += " and";
			str2 = w.NumberToWords(paise);
			str2 += " Paise";
		}
		//System.out.println(str1 + str2 + " Only");
		return toCamelCase(str1 + str2 + " Only");
	}
	
	public static void main(String[] args) {
		
		String str2 = "", str1="";  long rupees=0;  int paise=0;
		NumToWordsModified w = new NumToWordsModified();		
		String amt = "211111345596160.00";
		String paiseInString = amt.split("\\.")[1];
		
		try {
			rupees = Long.parseLong(amt.split("\\.")[0]);		
			if (rupees>0) 
				{	str1 = w.NumberToWords(rupees);
					str1 += " Rupees ";
				}
			if(paiseInString.length() < 2){
				if(paiseInString.length() == 0){
					paiseInString = "00";
				}
				if(paiseInString.length() == 1){
					paiseInString = paiseInString + "0";
				}
			}
			paise = Integer.parseInt(paiseInString);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		if (paise != 0) {
			str2 += " and";
			str2 = w.NumberToWords(paise);
			str2 += " Paise";
		}
		System.out.println(toCamelCase(str1 + str2 + "Only"));
	}
	
	public static String toCamelCase(final String init) {
	    if (init==null)
	        return null;

	    final StringBuilder ret = new StringBuilder(init.length());

	    for (final String word : init.split(" ")) {
	        if (!word.isEmpty()) {
	            ret.append(word.substring(0, 1).toUpperCase());
	            ret.append(word.substring(1).toLowerCase());
	        }
	        if (!(ret.length()==init.length()))
	            ret.append(" ");
	    }

	    return ret.toString();
	}

}

class NumToWordsModified{
	static String unitsMap[] = { "zero", "one", "two", "three", "four", "five","six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };
	static String tensMap[] = { "zero", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety" };

	public String NumberToWords(long number){
		
		if (number == 0)
		    return "zero";
	
		if (number < 0)
		    return "minus " + NumberToWords(Math.abs(number));
	
		String words = "";
		
		if ((number / 1000000000000000L) > 0)
		{
		    words += NumberToWords(number / 1000000000000000L) + " padm ";
		    number %= 1000000000000000L;
		}
		
		if ((number / 10000000000000L) > 0)
		{
		    words += NumberToWords(number / 10000000000000L) + " neel ";
		    number %= 10000000000000L;
		}
		
		if ((number / 100000000000L) > 0)
		{
		    words += NumberToWords(number / 100000000000L) + " kharab ";
		    number %= 100000000000L;
		}
		
		if ((number / 1000000000) > 0)
		{
		    words += NumberToWords(number / 1000000000) + " arab ";
		    number %= 1000000000;
		}
	
		if ((number / 10000000) > 0)
		{
		    words += NumberToWords(number / 10000000) + " crore ";
		    number %= 10000000;
		}
	
		if ((number / 100000) > 0)
		{
		    words += NumberToWords(number / 100000) + " lakh ";
		    number %= 100000;
		}
	
		if ((number / 1000) > 0)
		{
		    words += NumberToWords(number / 1000) + " thousand ";
		    number %= 1000;
		}
	
		if ((number / 100) > 0)
		{
		    words += NumberToWords(number / 100) + " hundred ";
		    number %= 100;
		}
	
		if (number > 0)
		{
		    if (number < 20)
		        words += unitsMap[(int)number];
		    else
		    {
		        words += tensMap[(int)number / 10];
		        if ((number % 10) > 0)
		            words += "-" + unitsMap[(int)number % 10];
		    }
		}
	
		return words;
    }
}
