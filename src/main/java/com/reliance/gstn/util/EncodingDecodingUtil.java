package com.reliance.gstn.util;

import javax.xml.bind.DatatypeConverter;

public class EncodingDecodingUtil {

	public static String encodeString(String inputStr){
		String encoded = DatatypeConverter.printBase64Binary(inputStr.getBytes());
	//	System.out.println(encoded);
		return encoded;
	}
	
	public static String decodeString(String encoded){
		String decoded = new String(DatatypeConverter.parseBase64Binary(encoded));
		System.out.println(decoded); 
		return decoded;
	}
	
	public static void main(String[] args) {
		decodeString("ykq8eJnnRb2ogTIUYIUZUw==");
	}
}
