package com.main.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.main.models.CustomerServiceResponse;

public class CreateNewSaleEntryServiceImpl extends ServiceBase {

	private String customerId;
	private List<String> newList;
	private boolean executionForList = false;
	private String invoiceId = "";
	private String vatTin = "";
	private String productId = "";
	private float grandTotal;
	private String taxType;
	private float taxValue;
	private float taxAmmount;
	private String taxRate;
	private float quantity;
	private float price;
	private float totalPrice;
	
	private String SALE_TABLE ="SALES_ORDER_TABLE";
	private String invoice_id ="invoice_id";
	private String customer_id ="customer_id";
	private String product_id ="product_id";
	private String product_unit_price ="product_unit_price";
	private String product_qty ="product_qty";
	private String product_price_with_qty ="product_price_with_qty";
	private String tax_type ="tax_type";
	private String tax_value ="tax_value";
	
	
	private String tax_rate ="tax_rate";
	private String order_date ="order_date";
	
	private String tax_amount ="tax_amount";
	private String total_amount ="total_amount";
	private Timestamp orderDate;
	
	

	// select * from EMP_PRODUCT_TABLE where id in (1,4,6);

	public void setCustomerId(int id) {
		this.customerId = Integer.toString(id);

	}

	public void setProductList(List<String> newList) {
		this.newList = newList;

	}

	public void isProductList(boolean b) {
		this.executionForList = b;

	}

	public void setInvoiceInformation(String str, String str2) {
		// TODO Auto-generated method stub
		this.vatTin = str2;
		this.invoiceId = str;
	}

	public void execute() {
		if (this.executionForList) {
			/*
			 * String[] finalProductList = this.newList.toArray( new
			 * String[this.newList.size()] );
			 * 
			 * this.getConnection(); Statement stmt; try {
			 * 
			 * stmt = this.dbConnection.createStatement(); ResultSet
			 * rs=stmt.executeQuery("select * from "+this.
			 * CUSTOMER_TABLE+" where "+this.COL_USER_NAME+" LIKE '%"+this.
			 * queryText+"' OR "+this.COL_USER_NAME+" LIKE '"+this.
			 * queryText+"%' OR "+this.COL_USER_NAME+" LIKE '%"+this.
			 * queryText+"%'   "); List<CustomerServiceResponse>
			 * customerServiceResponses = new ArrayList<>(); while(rs.next()) {
			 * CustomerServiceResponse customerServiceResponse = new
			 * CustomerServiceResponse();
			 * customerServiceResponse.setId(rs.getInt(1));
			 * customerServiceResponse.setName(rs.getString(2));
			 * customerServiceResponse.setAddress(rs.getString(3));
			 * customerServiceResponse.setPhone(rs.getString(4));
			 * customerServiceResponses.add(customerServiceResponse); }
			 * this.dbConnection.close();
			 * 
			 * } catch (SQLException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */

		}

	}

	public void setProductId(String string) {
		this.productId = string;

	}

	public void setGrandTotal(float str1) {
		this.grandTotal = str1;

	}

	public void setTaxInformation(String taxType, float taxValue, float taxAmmount, String taxRate) {
		// TODO Auto-generated method stub

		this.taxType = taxType;
		this.taxValue = taxValue;
		this.taxAmmount = taxAmmount;
		this.taxRate = taxRate;

	}

	public void setQuantityInfo(float quantity, float price, float totalPrice) {
		this.quantity = quantity;
		this.price = price;
		this.totalPrice = totalPrice;

	}
	
	/**
	 * 
	 * 
	 * //Sales Table with order information create table SALES_ORDER_TABLE (
	 * sale_id int(30) not null auto_increment, invoice_id varchar(60) not null,
	 * customer_id varchar(60) not null, product_id varchar(60) not null,
	 * 
	 * product_unit_price float(10,2) not null, product_qty float(10,2) not
	 * null, product_price_with_qty float(10,2) not null, tax_type varchar(60) ,
	 * tax_rate varchar(60), order_date TIMESTAMP default CURRENT_TIMESTAMP ,
	 * tax_amount float(10,2) , total_amount float(10,2) not null, primary
	 * key(sale_id))
	 * 
	 * 
	 * alter table SALES_ORDER_TABLE add column tax_value float(10,2)
	 * 
	 * 
	 */

	/**
	private String SALE_TABLE ="SALES_ORDER_TABLE";
	private String invoice_id ="invoice_id";
	private String customer_id ="customer_id";
	private String product_id ="product_id";
	private String product_unit_price ="product_unit_price";
	private String product_qty ="product_qty";
	
	private String product_price_with_qty ="product_price_with_qty";
	private String tax_type ="tax_type";
	
	private String tax_rate ="tax_rate";
	private String order_date ="order_date";
	
	private String tax_amount ="tax_amount";
	private String total_amount ="total_amount";
	
	**/
	
	public void executeExistingProduct() {
		this.getConnection();
		String query = " insert into "+SALE_TABLE+" ("+this.invoice_id+", "+this.customer_id+","+this.product_id+","+this.product_unit_price+","+this.product_qty+","+this.product_price_with_qty+","+this.tax_type+","+this.tax_rate+","+this.order_date+","+this.tax_amount+","+this.total_amount+","+this.tax_value+")  values (?, ?, ?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = this.dbConnection.prepareStatement(query);
			preparedStmt.setString(1, this.invoiceId);
			preparedStmt.setString(2, this.customerId);
			preparedStmt.setString(3, this.productId);
			
			preparedStmt.setFloat(4, this.price);
			preparedStmt.setFloat(5, this.quantity);
			preparedStmt.setFloat(6, this.totalPrice);
			
			preparedStmt.setString(7, this.taxType);
			preparedStmt.setString(8, this.taxRate);
			preparedStmt.setTimestamp(9, this.orderDate);
			
			preparedStmt.setFloat(10, this.taxAmmount);
			preparedStmt.setFloat(11, this.grandTotal);
			preparedStmt.setFloat(12, this.taxValue);
			
			int count = preparedStmt.executeUpdate();
			    if (count > 0) {
			     System.out.println(" VALUE INSERTED");
			    }
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
	}

	public void setOrderDate(Timestamp long1) {
		// TODO Auto-generated method stub
		this.orderDate = long1;
	}

}
