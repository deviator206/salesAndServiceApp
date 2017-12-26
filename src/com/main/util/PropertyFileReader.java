package com.main.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class PropertyFileReader {
	public JSONObject propertyInfo;  
	
	public PropertyFileReader(){
		this.simulatedInfo();
		//this.populateJSONProperties();
	}
	
	private void simulatedInfo() {
		// TODO Auto-generated method stub
		propertyInfo = new JSONObject();
		try {
			//propertyFileReader.propertyInfo.getString("host")
			//propertyFileReader.propertyInfo.getString("port")
			//propertyFileReader.propertyInfo.getString("db")
			//propertyFileReader.propertyInfo.getString("dbuser")
			//propertyFileReader.propertyInfo.getString("dbpassword")
			propertyInfo.put("host", "localhost");

			propertyInfo.put("port", "3306");
			propertyInfo.put("db", "nce_db");
			propertyInfo.put("dbuser", "root");
			propertyInfo.put("dbpassword", "root");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void populateJSONProperties(){
		FileInputStream fileInput;
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			fileInput = (FileInputStream) classLoader.getResourceAsStream("com/main/resoures/config.properties");  
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration enuKeys = properties.keys();
			propertyInfo = new JSONObject();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);
				System.out.println(key + ": " + value);
				propertyInfo.put(key, value);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
