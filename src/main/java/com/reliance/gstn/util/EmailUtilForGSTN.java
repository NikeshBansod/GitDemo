package com.reliance.gstn.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;


public class EmailUtilForGSTN {
	private final static Logger logger = Logger.getLogger(EmailUtilForGSTN.class);
	
	public static void sendEmail(String to,String from,String subject,String msg){
		logger.info("Entry:");
		  String env=getPropertyGSTR1("env");
	      String host = getPropertyGSTR1(env+".EMAIL_SMTP_HOSTNAME");
	  
	     //Get the session object  
	      Properties properties = System.getProperties();  
	      properties.setProperty("mail.smtp.host", host);  
	      Session session = Session.getDefaultInstance(properties);  
	  
	     //compose the message  
	      try{  
	         MimeMessage message = new MimeMessage(session);  
	         message.setFrom(new InternetAddress(from));  
	         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
	         message.setSubject(subject);  
	         message.setText(msg);  
	  
	         // Send message  
	         Transport.send(message);  
	         logger.info("Mail sent successfully....");  
	  
	      }catch (MessagingException mex) {
	    	  logger.error("Error in:",mex);
	      }
	      
	      logger.info("Exit:");
	}

	private static Properties props1;

	public static String getPropertyGSTR1(String key) {
		if (null == props1) {
			logger.info("Property File Reading Start");
			readPropertyFileGSTR1();
			logger.info("Successfully read property file.");
		}
		return props1.getProperty(key);
	}

	private static void readPropertyFileGSTR1() {
		try {
			InputStream input = getPropertiesAsInputStreamGSTR1();

			props1 = new Properties();
			props1.load(input);

		} catch (IOException e) {
			logger.info("Error in reading property file.", e);
		}
	}

	private static InputStream getPropertiesAsInputStreamGSTR1() throws FileNotFoundException {
		InputStream input = null;
		File file = new File(AspApiConstants.APP_CONFIG_FILE_PATH_FOR_GSTR1);

		if (file.exists()) {
			input = new FileInputStream(file);
		} else {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			input = classLoader.getResourceAsStream(AspApiConstants.APP_CONFIG_FILE_PATH_FOR_GSTR1);
		}

		return input;
	}

	
	
}
