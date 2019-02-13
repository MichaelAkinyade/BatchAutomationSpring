package com.globalcapital.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertyFileUtils {
	
	Properties prop = new Properties();
	InputStream input = null;
	String propertyFileName;
	
	
	
	public PropertyFileUtils(String propertyFileName) {
		super();
		this.propertyFileName = propertyFileName;
		
		
		// TODO Auto-generated constructor stub
	}
	public Properties loadProperties() {

		try {
			 Path path = Paths.get(getClass().getClassLoader()
				     .getResource(propertyFileName).toURI()); 
			//System.out.println("********URL is "+path);
			input = new FileInputStream(path.toString());
			// load a properties file
			prop.load(input);
			
			//value = prop.getProperty(key);

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
	public String getPropetyByStringkey(String key) {
		
		String value = "";
		InputStream input = null;

		try {

			 Path path = Paths.get(getClass().getClassLoader()
				     .getResource("cronValues.properties").toURI()); 
			//System.out.println("********URL is "+path);
			input = new FileInputStream(path.toString());
			// load a properties file
			prop.load(input);
			
			value = prop.getProperty(key);

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		
		return value;
	}
	

	public List<String> getPropertyValueList() {

		InputStream input = null;
		List<String> result = new ArrayList<>();

		try {

			 Path path = Paths.get(getClass().getClassLoader()
				     .getResource("cronValues.properties").toURI()); 
			//System.out.println("********URL is "+path);
			input = new FileInputStream(path.toString());
			// load a properties file
			prop.load(input);
			
			// iterate properties file to get key-value pairs
			for (String key : prop.stringPropertyNames()) {
				result.add(key);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;

	}
	
}
