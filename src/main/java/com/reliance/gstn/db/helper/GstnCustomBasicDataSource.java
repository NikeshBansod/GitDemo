package com.reliance.gstn.db.helper;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.reliance.gstn.util.EncryptionUtil;

public class GstnCustomBasicDataSource extends BasicDataSource {

	final static Logger logger = Logger.getLogger(GstnCustomBasicDataSource.class);
	
	 public GstnCustomBasicDataSource() {
	        super();
	    }

	    public synchronized void setPassword(String encodedPassword){
	        this.password = decode(encodedPassword);
	    }
	    
	    public synchronized void setUsername(String encodedUserName){
	        this.username = decode(encodedUserName);
	    }
	    

	    private String decode(String encryptString) {
	    	
	    	String originalString="";
			try {
				originalString = new EncryptionUtil().decrypt(encryptString);
			} catch (InvalidKeyException e) {
				logger.error("Error in:",e);
			} catch (InvalidAlgorithmParameterException e) {
				logger.error("Error in:",e);
			} catch (IllegalBlockSizeException e) {
				logger.error("Error in:",e);
			} catch (BadPaddingException e) {
				logger.error("Error in:",e);
			} catch (NoSuchAlgorithmException e) {
				logger.error("Error in:",e);
			} catch (NoSuchPaddingException e) {
				logger.error("Error in:",e);
			} catch (UnsupportedEncodingException e) {
				logger.error("Error in:",e);
			}
	    	
	        return originalString;
	    }
	

}
