/**
 * 
 */
package com.reliance.gstn.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Nikesh.Bansod
 *
 */
public class AmountInWords {

	/**
	 * @param args
	 */
	
	public static String convertNumberToWords(String amtBefore){
		String amt = new BigDecimal(amtBefore).setScale(2, RoundingMode.DOWN).toPlainString();
		String str2 = "", str1="";
		int rupees=0;
		int paise=0;
		NumToWords w = new NumToWords();
		String paiseInString = null;
		try {
			
			rupees = Integer.parseInt(amt.split("\\.")[0]);		
			if (rupees>0) 
				{	str1 = w.convert(rupees);
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
			str2 = w.convert(paise);
			str2 += " Paise";
		}
		//System.out.println(str1 + str2 + " Only");
		return str1 + str2 + " Only";
	}
	
	public static void main(String args[]) {
		String str2 = "", str1="";  int rupees=0;  int paise=0;
		NumToWords w = new NumToWords();		
		String amt = "345596160.00";
		String paiseInString = amt.split("\\.")[1];
		
		try {
			rupees = Integer.parseInt(amt.split("\\.")[0]);		
			if (rupees>0) 
				{	str1 = w.convert(rupees);
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
			str2 = w.convert(paise);
			str2 += " Paise";
		}
		System.out.println(str1 + str2 + " Only");
	}

}

class NumToWords {
	String string;
	String st1[] = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", };
	String st2[] = { "Hundred", "Thousand", "Lakh", "Crore" };
	String st3[] = { "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Ninteen", };
	String st4[] = { "Twenty", "Thirty", "Fourty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninty" };

	public String convert(int number) {
		int n = 1;
		int word;
		string = "";
		while (number != 0) {
			switch (n) {
			case 1:
				word = number % 100;
				pass(word);
				if (number > 100 && number % 100 != 0) {
					show("and ");
				}
				number /= 100;
				break;
			case 2:
				word = number % 10;
				if (word != 0) {
					show(" ");
					show(st2[0]);
					show(" ");
					pass(word);
				}
				number /= 10;
				break;
			case 3:
				word = number % 100;
				if (word != 0) {
					show(" ");
					show(st2[1]);
					show(" ");
					pass(word);
				}
				number /= 100;
				break;
			case 4:
				word = number % 100;
				if (word != 0) {
					show(" ");
					show(st2[2]);
					show(" ");
					pass(word);
				}
				number /= 100;
				break;
			case 5:
				word = number % 100;
				if (word != 0) {
					show(" ");
					show(st2[3]);
					show(" ");
					pass(word);
				}
				number /= 100;
				break;
			}
			n++;
		}
		return string;
	}

	public void pass(int number) {
		int word, q;
		if (number < 10) {
			show(st1[number]);
		}
		if (number > 9 && number < 20) {
			show(st3[number - 10]);
		}
		if (number > 19) {
			word = number % 10;
			if (word == 0) {
				q = number / 10;
				show(st4[q - 2]);
			} else {
				q = number / 10;
				show(st1[word]);
				show(" ");
				show(st4[q - 2]);
			}
		}
	}

	public void show(String s) {
		String st;
		st = string;
		string = s;
		string += st;
	}
	
}
