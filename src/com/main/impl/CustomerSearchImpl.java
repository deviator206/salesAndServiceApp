package com.main.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.main.models.CustomerSearchResponse;
import com.main.models.CustomerServiceResponse;

public class CustomerSearchImpl extends CustomerServiceImpl{
	private String queryText;
	private CustomerSearchResponse customerSearchResponse;

	public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	@Override
	public void execute() {
		this.getConnection();
		Statement stmt;
		customerSearchResponse = new CustomerSearchResponse();
		try {
			
			customerSearchResponse.setStatus(false);
			stmt = this.dbConnection.createStatement();
			ResultSet rs=stmt.executeQuery("select * from "+this.CUSTOMER_TABLE+" where "+this.COL_USER_NAME+" LIKE '%"+this.queryText+"' OR "+this.COL_USER_NAME+" LIKE '"+this.queryText+"%' OR "+this.COL_USER_NAME+" LIKE '%"+this.queryText+"%'   ");
			List<CustomerServiceResponse> customerServiceResponses = new ArrayList<>();
			while(rs.next()) {
				customerSearchResponse.setStatus(true);
				CustomerServiceResponse customerServiceResponse = new CustomerServiceResponse();
				customerServiceResponse.setId(rs.getInt(1));
				customerServiceResponse.setName(rs.getString(2));
				customerServiceResponse.setAddress(rs.getString(3));
				customerServiceResponse.setPhone(rs.getString(4));
				customerServiceResponses.add(customerServiceResponse);
			}
			customerSearchResponse.setCustomerServiceResponseList(customerServiceResponses);
			this.dbConnection.close();  
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
	}

	public CustomerSearchResponse getCustomerSearchResponse() {
		// TODO Auto-generated method stub
		return customerSearchResponse;
	}
}
