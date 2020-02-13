package com.qa.FW.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

	Properties pro;

	public ConfigReader() {
		File src = new File("./Configuration/Appconfig.properties");
		try {
			FileInputStream fis = new FileInputStream(src);
			pro = new Properties();
			pro.load(fis);
		} catch (Exception e) {
			System.out.println("Config file is not loading " + e.getMessage());
		}
	}

	public String getvalue(String value) {
		// String url = pro.getProperty("url");
		return pro.getProperty(value);

	}

	public String getbrowser() {

		return pro.getProperty("Browser");
	}

	public String getSitURL() {

		return pro.getProperty("Sturl");

	}

	public String getpageloadtimeout() {

		return pro.getProperty("PageLodTimeOut");

	}

}
