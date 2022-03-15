package com.reliance.gstn.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyReader {
	private final static Logger logger = Logger.getLogger(PropertyReader.class);
	private static Properties props;

	public static String getProperty(String key) {
		if (null == props) {
			logger.info("Property File Reading Start");
			readPropertyFile();
			logger.info("Successfully read property file.");
		}
		return props.getProperty(key);
	}

	private static void readPropertyFile() {
		try {
			InputStream input = getPropertiesAsInputStream();

			props = new Properties();
			props.load(input);

		} catch (IOException e) {
			logger.info("Error in reading property file.", e);
		}
	}

	private static InputStream getPropertiesAsInputStream() throws FileNotFoundException {
		InputStream input = null;
		File file = new File(AspApiConstants.APP_CONFIG_FILE_PATH);

		if (file.exists()) {
			input = new FileInputStream(file);
		} else {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			input = classLoader.getResourceAsStream(AspApiConstants.APP_CONFIG_FILE_PATH);
		}

		return input;
	}

}
