package com.sdi.util;

import java.io.IOException;
import java.util.Properties;

public class Conf {

	private static final String CONF_FILE_JDBC = "jdbc.properties";
	private static final String CONF_FILE_SQL = "sql.properties";
	private static Conf instance;
	private Properties propertiesJDBC;
	private Properties propertiesSQL;

	private Conf() {
		propertiesJDBC = new Properties();
		propertiesSQL = new Properties();
		try {
			propertiesJDBC.load(Conf.class.getClassLoader()
					.getResourceAsStream(CONF_FILE_JDBC));
			propertiesSQL.load(Conf.class.getClassLoader().getResourceAsStream(
					CONF_FILE_SQL));
		} catch (IOException e) {
			throw new RuntimeException("Properties file can not be loaded", e);
		}
	}

	public static String get(String key) {
		return getInstance().getProperty(key);
	}

	private String getProperty(String key) {
		String valueJDBC = propertiesJDBC.getProperty(key);
		if (valueJDBC == null) {
			String valueSQL = propertiesSQL.getProperty(key);
			if (valueSQL == null) {
				throw new RuntimeException("Property not found in config file");
			}
			return valueSQL;
		}
		return valueJDBC;
	}

	private static Conf getInstance() {
		if (instance == null) {
			instance = new Conf();
		}
		return instance;
	}

}
