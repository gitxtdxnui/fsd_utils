package com.feishidai.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class PropertiesUtils {
	private static Map<String, String> properties = null;

	private static void iniProperties(String filepath){
		properties = new ConcurrentHashMap<String, String>();
		Properties p = new Properties();
		InputStream fis = null;
		try {
			fis = PropertiesUtils.class.getClassLoader().getResourceAsStream(filepath + ".properties");
			p.load(fis);
			Enumeration<Object> keys = p.keys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement().toString();
				properties.put(key, p.getProperty(key));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String get(String filepath, String key) {
		if(properties == null || !properties.containsKey(key)){
			iniProperties(filepath);
		}
		return properties.get(key);
	}
	
	public static int getInt(String filepath, String key) {
		return Integer.parseInt(get(filepath, key));
	}
	
	public static void reset() {
		properties = null;
	}
}
