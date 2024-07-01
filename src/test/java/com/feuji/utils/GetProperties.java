package com.feuji.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.feuji.context.Constants;


public class GetProperties {
	/** The Constant props. */
	private static final Properties props = new Properties();

	/**
	 * Load all properties.
	 */
	public static void loadAllProperties() {
		try {
			FileInputStream locator;
			locator = new FileInputStream(Constants.BASE_PROPERTY_PATH);
			props.load(locator);
		} catch (IOException e) {
			LoggerUtil.getLogger().fatal("Could not load properties : " + e.getMessage());
		}
	}

	/**
	 * Gets the property.
	 * @param key the key
	 * @return the property
	 */
	public static String getProperty(String key) {
		return props.getProperty(key);
	}

	/**
	 * Put property.
	 * @param key   the key
	 * @param value the value
	 */
	public static void putProperty(String key, String value) {
		props.put(key, value);
	}
}
