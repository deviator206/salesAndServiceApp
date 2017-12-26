package com.main.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.main.models.CustomerServiceResponse;

public class CustomerServiceImpl extends ServiceBase {
	private CustomerServiceResponse customerServiceResponse;
	private int userID;
	private String userName;
	private String userAddress;
	private String userPhone;
	private String userAlternatePhone;
	private String userEmail;

	public String CUSTOMER_TABLE = "EMP_CUSTOMER_TABLE";
	public String COL_USER_NAME = "name";
	private String COL_USER_ADDRESS = "address";
	private String COL_USER_PHONE = "phone";
	private String COL_USER_ID = "id";
	

	public void setUserName(String str) {
		this.userName = str;

	}

	public void setUserAddress(String str) {
		this.userAddress = str;

	}

	public void setUserPhone(String str) {
		this.userPhone = str;

	}
	public void setUserID(int str) {
		this.userID = str;

	}

	public void execute() {

		this.getConnection();
		customerServiceResponse = new CustomerServiceResponse();
		try {
			customerServiceResponse.setStatus(false);
			String query = " insert into " + CUSTOMER_TABLE + " (" + this.COL_USER_NAME + ", " + this.COL_USER_ADDRESS
					+ "," + this.COL_USER_PHONE + ")" + " values (?, ?, ?)";

			PreparedStatement preparedStmt = this.dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setString(1, this.userName);
			preparedStmt.setString(2, this.userAddress);
			preparedStmt.setString(3, this.userPhone);

			int count = preparedStmt.executeUpdate();
			if (count > 0) {
				customerServiceResponse.setStatus(true);
			}

			customerServiceResponse.setName(this.userName);
			customerServiceResponse.setAddress(this.userAddress);
			customerServiceResponse.setPhone(this.userPhone);
			ResultSet rs = preparedStmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				customerServiceResponse.setId(rs.getInt(1));

			}
			System.out.println(count);
			this.dbConnection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public CustomerServiceResponse getCustomerCreationResponse() {
		// TODO Auto-generated method stub
		return customerServiceResponse;
	}

	public void executeUpdateCustomer() {

		this.getConnection();
		customerServiceResponse = new CustomerServiceResponse();
		try {
			customerServiceResponse.setStatus(false);
			String query = " update  " + CUSTOMER_TABLE + " set " + this.COL_USER_NAME + " =? ," + this.COL_USER_ADDRESS
					+ "=?, " + this.COL_USER_PHONE + "=? , alternate_number = ? , email_id= ? where " + this.COL_USER_ID + " = ?";

			PreparedStatement preparedStmt = this.dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStmt.setString(1, this.userName);
			preparedStmt.setString(2, this.userAddress);
			preparedStmt.setString(3, this.userPhone);
			preparedStmt.setString(4, this.userAlternatePhone);
			preparedStmt.setString(5, this.userEmail);
			preparedStmt.setInt(6, this.userID);

			int count = preparedStmt.executeUpdate();
			if (count > 0) {
				customerServiceResponse.setStatus(true);
			}

			customerServiceResponse.setName(this.userName);
			customerServiceResponse.setAddress(this.userAddress);
			customerServiceResponse.setPhone(this.userPhone);
			customerServiceResponse.setAlternateNo(this.userAlternatePhone);
			customerServiceResponse.setEmail(this.userEmail);
			
			ResultSet rs = preparedStmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				customerServiceResponse.setId(rs.getInt(1));

			}
			System.out.println(count);
			this.dbConnection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @return the userAlternatePhone
	 */
	public String getUserAlternatePhone() {
		return userAlternatePhone;
	}

	/**
	 * @param userAlternatePhone the userAlternatePhone to set
	 */
	public void setUserAlternatePhone(String userAlternatePhone) {
		this.userAlternatePhone = userAlternatePhone;
	}

	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}
