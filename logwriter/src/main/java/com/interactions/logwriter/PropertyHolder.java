package com.interactions.logwriter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyHolder {
	
	private static PropertyHolder holder;
	
	private PropertyHolder() { 	}
	private static Properties prop;
	
	public static PropertyHolder getInstance(String rootPath) {
		if(holder == null) holder = new PropertyHolder();
		prop = holder.loadProperty(rootPath);
		return holder;
	}
	
	public static String getProperty(String key) {
		return (String)prop.get(key);
	}
	
	public Properties loadProperty(String rootPath) {
		Properties prop = new Properties();
		InputStream is = null;
		try {
			is = this.getClass().getClassLoader().getResourceAsStream(rootPath);
			prop.load(is); 
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return prop;
	}

}
