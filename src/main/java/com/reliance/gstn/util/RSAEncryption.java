package com.reliance.gstn.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

public class RSAEncryption {

	public static void main(String[] args) throws Exception {
		String plainText = "Hello World!";

		// Generate public and private keys using RSA
		Map<String, Object> keys = getRSAKeys();

		PrivateKey privateKey = (PrivateKey) keys.get("private");
		System.out.println("privateKey"+privateKey);
		PublicKey publicKey = (PublicKey) keys.get("public");
		System.out.println("publicKey"+publicKey);
		String encryptedText = encryptMessage(plainText, privateKey);
		String descryptedText = decryptMessage(encryptedText, publicKey);

		System.out.println("input:" + plainText);
		System.out.println("encrypted:" + encryptedText);
		System.out.println("decrypted:" + descryptedText);

	}

	// Get RSA keys. Uses key size of 2048.
	private static Map<String, Object> getRSAKeys() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("private", privateKey);
		keys.put("public", publicKey);
		return keys;
	}

	// Decrypt using RSA public key
	private static String decryptMessage(String encryptedText, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return new String(cipher.doFinal(DatatypeConverter.parseBase64Binary(encryptedText)));
	}

	// Encrypt using RSA private key
	private static String encryptMessage(String plainText, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return DatatypeConverter.printBase64Binary(cipher.doFinal(plainText.getBytes()));
	}
}