package com.reliance.gstn.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.reliance.gstn.admin.exception.EwayBillApiException;

public class AESEncryptionV31 {

	public static final String AES_TRANSFORMATION = "AES/ECB/PKCS5Padding";
	public static final String AES_ALGORITHM = "AES";
	public static final int ENC_BITS = 256;
	public static final String CHARACTER_ENCODING = "UTF-8";

	private static Cipher ENCRYPT_CIPHER;
	private static Cipher DECRYPT_CIPHER;
	private static KeyGenerator KEYGEN;

	private static final Logger log = Logger.getLogger(AESEncryptionV31.class);

	static {
		try {
			ENCRYPT_CIPHER = Cipher.getInstance(AES_TRANSFORMATION);
			DECRYPT_CIPHER = Cipher.getInstance(AES_TRANSFORMATION);
			KEYGEN = KeyGenerator.getInstance(AES_ALGORITHM);
			KEYGEN.init(ENC_BITS);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			log.error("exceptionHandler method : AESEncryptionV31 error occurred.{}", e);
		}
	}

	/**
	 * This method is used to encode bytes[] to base64 string.
	 * 
	 * @param bytes
	 *            : Bytes to encode
	 * @return : Encoded Base64 String
	 */
	public static String encodeBase64String(byte[] bytes) {
		return new String(DatatypeConverter.printBase64Binary(bytes));
	}

	/**
	 * This method is used to decode the base64 encoded string to byte[]
	 * 
	 * @param stringData
	 *            : String to decode
	 * @return : decoded String
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] decodeBase64StringTOByte(String stringData) throws Exception {
		return DatatypeConverter.parseBase64Binary(stringData);
	}

	/**
	 * This method is used to generate the base64 encoded secure AES 256 key *
	 * 
	 * @return : base64 encoded secure Key
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 *//*
		 * public static String generateSecureKey() throws Exception{ return
		 * GenerateRandomKey.generateRandomString(); }
		 */
	/**
	 * This method is used to encrypt the string which is passed to it as byte[] and
	 * return base64 encoded encrypted String
	 * 
	 * @param plainText
	 *            : byte[]
	 * @param secret
	 *            : Key using for encrypt
	 * @return : base64 encoded of encrypted string.
	 * 
	 */

	public static String encryptEK(byte[] plainText, byte[] secret) {
		try {
			log.error("encryptEK method : start");
			SecretKeySpec sk = new SecretKeySpec(secret, AES_ALGORITHM);
			ENCRYPT_CIPHER.init(Cipher.ENCRYPT_MODE, sk);
			return DatatypeConverter.printBase64Binary(ENCRYPT_CIPHER.doFinal(plainText));

		} catch (Exception e) {
			log.error("exceptionHandler method : AESEncryptionV31 error occurred.{ encryptEK }", e);
			return "";
		}
	}

	/**
	 * This method is used to encrypt the string , passed to it using a public key
	 * provided
	 * 
	 * @param planTextToEncrypt
	 *            : Text to encrypt
	 * @return :encrypted string
	 */
	public static String encrypt(byte[] plaintext, String publiccerturl) throws EwayBillApiException {

		try {
			log.info("encrypt ::ewaybillKeyPath " + publiccerturl);
			PublicKey key = loadPublicKey(publiccerturl);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptedByte = cipher.doFinal(plaintext);
			String encodedString = new String(DatatypeConverter.printBase64Binary(encryptedByte));
			return encodedString;
		} catch (Exception e) {
			log.error("exceptionHandler method : AESEncryptionV31 error occurred.{ encryptEK }", e);
			throw new EwayBillApiException(e.getMessage());
		}

	}

	/**
	 * This method is used to decrypt base64 encoded string using an AES 256 bit
	 * key.
	 * 
	 * @param plainText
	 *            : plain text to decrypt
	 * @param secret
	 *            : key to decrypt
	 * @return : Decrypted String
	 * 
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static byte[] decrypt(String plainText, byte[] secret)
			throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, Exception {
		log.error("decrypt method : start");
		SecretKeySpec sk = new SecretKeySpec(secret, AES_ALGORITHM);
		DECRYPT_CIPHER.init(Cipher.DECRYPT_MODE, sk);
		return DECRYPT_CIPHER.doFinal(DatatypeConverter.parseBase64Binary(plainText));
	}

	public static PublicKey loadPublicKey(String publiccerturl) throws Exception {
		File dir = new File(publiccerturl);
		if (!dir.exists()) {
			log.info("loadPublicKey certificate_publickey  :: /certificate_publickey.pem");
			PdfGenUtil p = new PdfGenUtil();
			publiccerturl = p.loadResource("/certificate_publickey.pem");
		}

		log.info("loadPublicKey certificate_publickey  :: " + publiccerturl);
		String publicKeyPEM = FileUtils.readFileToString(new File(publiccerturl));
		// strip of header, footer, newlines, whitespaces
		publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----\n", "").replace("-----END PUBLIC KEY-----", "")
				.replaceAll("\\s", "").trim();
		// decode to get the binary DER representation
		byte[] publicKeyDER = DatatypeConverter.parseBase64Binary(publicKeyPEM);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		// PublicKey publicKey = keyFactory.generatePublic(new
		// X509EncodedKeySpec(publicKeyDER));
		RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyDER));
		return publicKey;
	}

	private static String encryptMessage(String plainText, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return DatatypeConverter.printBase64Binary(cipher.doFinal(plainText.getBytes()));

	}

	private static String decryptMessage(String encryptedText, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return new String(cipher.doFinal(DatatypeConverter.parseBase64Binary(encryptedText)));

	}

}
