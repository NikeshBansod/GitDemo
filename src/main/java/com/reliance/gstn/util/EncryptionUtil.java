package com.reliance.gstn.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

public class EncryptionUtil {

	@Value("${ciphertype}")
	private String cipherType = "AES/CBC/PKCS5PADDING";

	@Value("${algorithm}")
	private String algorithm = "AES";

	@Value("${secretkey}")
	private String secretKey = "l7xx2c4dc804b9b14a3abb3c36e8e1c17f6d";

	final static Logger logger = Logger.getLogger(EncryptionUtil.class);
	private Cipher cipher;
	private IvParameterSpec ivParam;
	private SecretKeySpec sKeySpec;
	private byte[] key;
	String customKey;

	public EncryptionUtil() throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
		this.key = getKeyBytes();
		this.cipher = Cipher.getInstance(cipherType);
		this.ivParam = new IvParameterSpec(this.key);
		this.sKeySpec = new SecretKeySpec(this.key, algorithm);
	}

	public EncryptionUtil(String customKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
		this.customKey = customKey;
		this.key = getKeyBytes();
		this.cipher = Cipher.getInstance(cipherType);
		this.ivParam = new IvParameterSpec(this.key);
		this.sKeySpec = new SecretKeySpec(this.key, algorithm);
	}

	public String encrypt(String inputString) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		String encryptedString = null;
		// Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		this.cipher.init(Cipher.ENCRYPT_MODE, this.sKeySpec, this.ivParam);

		byte[] encrypted = cipher.doFinal(inputString.getBytes());
		encryptedString = DatatypeConverter.printBase64Binary(encrypted);
		
		/*logger.debug("Enrypted value: " + encryptedString);*/
		logger.info("Exit: ");
		return encryptedString;
	}

	private byte[] getKeyBytes() throws UnsupportedEncodingException {
		byte[] keyBytes = new byte[16];
		byte[] parameterKeyBytes;
		if (null == this.customKey) {
			parameterKeyBytes = secretKey.getBytes("UTF-8");
		} else {
			parameterKeyBytes = this.customKey.getBytes("UTF-8");
		}

		System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));
		return keyBytes;
	}

	public String decrypt(String encryptedString) throws InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException {
		logger.info("Entry: ");
		byte[] decodedValue = DatatypeConverter.parseBase64Binary(encryptedString);
		this.cipher.init(Cipher.DECRYPT_MODE, this.sKeySpec, this.ivParam);

		byte[] plainByte = this.cipher.doFinal(decodedValue);

		String plainText = new String(plainByte);

		/*logger.debug("Decrypted value: " + plainText);*/

		logger.info("Exit: ");
		return plainText;

	}

	public static void main(String[] args)
			throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
		String ekkey = "AD5652A44F0A472799021E934C51A4B15054998E955161A4";
		// decoding then decription
		String decrypt = "root";
		// System.out.println("decrypted gui id==: " + decrypt);
		String encryptString = new EncryptionUtil(ekkey).encrypt(decrypt);
		System.out.println("encryptString==: " + encryptString);
		//EncryptionUtil en = new EncryptionUtil();
		//en.decrypt("4ljtbKqPH09JEQ0YwVWmHg==");
		//en.encrypt("gspmysql@123");
		
	    System.out.println("decrypted ::: " + new EncryptionUtil(ekkey).decrypt("4ljtbKqPH09JEQ0YwVWmHg=="));

	}

}
