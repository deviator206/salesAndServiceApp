package com.main.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.main.models.LoginResponse;

public class LoginServiceImpl extends ServiceBase{
	
	private String userName ;
	private String userPassword ;
	private String LOGIN_TABLE = "validemporiumuser";
	private String COL_USER_NAME ="empName";
	private String COL_USER_PASSWORD="empPassword";
	
	private LoginResponse loginResponse;
	
	public LoginServiceImpl(){
		
	}

	public void setUserName(String str) {
		this.userName = str; 
	}

	public void setUserPassword(String str) {
		this.userPassword= str;
		
	}

	public void execute() {
		this.getConnection();
		Statement stmt;
		loginResponse = new LoginResponse();
		try {
			loginResponse.setStatus(false);
			stmt = this.dbConnection.createStatement();
			ResultSet rs=stmt.executeQuery("select * from "+LOGIN_TABLE+" where "+this.COL_USER_NAME+"='"+this.userName+"' AND "+this.COL_USER_PASSWORD+"='"+this.userPassword+"'");  
			while(rs.next()) {
				loginResponse.setStatus(true);
				loginResponse.setId(rs.getInt(1));
				loginResponse.setUserName(rs.getString(2));
				loginResponse.setRole(rs.getString(4));
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
			}
			this.dbConnection.close();  
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}

	public LoginResponse getLoginResponse() {
		return this.loginResponse;
	}

	
}
