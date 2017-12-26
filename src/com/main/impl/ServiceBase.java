package com.main.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONException;

import com.main.util.PropertyFileReader;

public class ServiceBase {
	private PropertyFileReader propertyFileReader;
	public Connection dbConnection;
	
	public ServiceBase() {
		propertyFileReader = new PropertyFileReader();
		
	}
	
	public void getConnection(){
		try {
			
			
			Class.forName("com.mysql.jdbc.Driver");
			dbConnection = DriverManager.getConnection("jdbc:mysql://"+propertyFileReader.propertyInfo.getString("host")+":"+propertyFileReader.propertyInfo.getString("port")+"/"+propertyFileReader.propertyInfo.getString("db")+"",propertyFileReader.propertyInfo.getString("dbuser"),propertyFileReader.propertyInfo.getString("dbpassword"));
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
