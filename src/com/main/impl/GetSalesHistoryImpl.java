package com.main.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.main.models.SalesHistoryResponse;
import com.main.models.sales.CustomerInfo;
import com.main.models.sales.PaymentInfo;
import com.main.models.sales.ProductInfo;
import com.main.models.sales.SingleSaleInfo;

public class GetSalesHistoryImpl extends ServiceBase{
	private String queryText="";
	private String queryOnColumn="";
	private String byType="";
	private String startFrom="";
	private String startTo="";
	private SalesHistoryResponse salesHistoryResponse = new SalesHistoryResponse();
	

	public void setQueryText(String queryText) {
		// TODO Auto-generated method stub
		this.queryText = queryText;
		
	}

	public void setQueryOnColumn(String queryOnColumn) {
		// TODO Auto-generated method stub
		this.queryOnColumn = queryOnColumn;
	}

	public void setByType(String byType) {
		this.byType = byType;
		
	}

	public void setStartFrom(String startFrom) {
		this.startFrom = startFrom;
		
	}

	public void setStartTo(String startTo) {
		this.startTo = startTo;
		
	}

	public void execute() throws SQLException, JSONException{
		
		Statement stmt;
		this.getConnection();
		stmt = this.dbConnection.createStatement();
		String query = "";
		
		
		if(!this.byType.isEmpty() && this.byType.equalsIgnoreCase("BY_QUERY")) {
			switch(this.queryOnColumn.toUpperCase()) {
			case "CUSTOMER_NAME" :
				query = "select * from emp_customer_table t1 inner join sales_order_table t2 on t1.id = t2.customer_id inner join emp_product_table t3 on t2.product_id = t3.id inner join payment_details_table t4 on t2.invoice_id = t4.invoice_id  where t1.name like'%"+this.queryText +"%' order by t2.sale_id DESC ";
				break;
			case "CUSTOMER_PHONE" :
				query = "select * from emp_customer_table t1 inner join sales_order_table t2 on t1.id = t2.customer_id inner join emp_product_table t3 on t2.product_id = t3.id inner join payment_details_table t4 on t2.invoice_id = t4.invoice_id  where  t1.phone like'%"+this.queryText +"%' or t1.alternate_number like '%"+this.queryText +"%' order by t2.sale_id DESC ";
				break;
/*			case "SERIAL_NUMBER" :
				break;
			case "PRODUCT_NAME" :
				break;
	*/
				case "INVOICE_ID":
				query = "select * from emp_customer_table t1 inner join sales_order_table t2 on t1.id = t2.customer_id inner join emp_product_table t3 on t2.product_id = t3.id inner join payment_details_table t4 on t2.invoice_id = t4.invoice_id  where   t2.invoice_id like'%"+this.queryText +"%' order by t2.sale_id DESC ";
				break;
			default :
				break;
			}
			
		} else if(!this.byType.isEmpty() && this.byType.equalsIgnoreCase("BY_DATE")) {
			
		}
		
		// execute
		ResultSet resultSet = stmt.executeQuery(query);
		List<SingleSaleInfo> finalResponseList = new ArrayList<>();
		while (resultSet.next()) {
			
			SingleSaleInfo finalResponse = new SingleSaleInfo();
			CustomerInfo customerInfo = new CustomerInfo();
			customerInfo.setName(resultSet.getString("name"));
			customerInfo.setAddress(resultSet.getString("address"));
			customerInfo.setPhone(resultSet.getString("phone"));
			customerInfo.setAlternateNo(resultSet.getString("alternate_number"));
			customerInfo.setEmail(resultSet.getString("email_id"));
			
			finalResponse.setCustomerInfo(customerInfo);
			
			PaymentInfo paymentInfo = new PaymentInfo();
			paymentInfo.setAmount(resultSet.getString("amount"));
			if( resultSet.getString("cardNetwork") != null && resultSet.getString("cardNo") != null  ) {
				paymentInfo.setType("card");
				paymentInfo.setCardNetwork(resultSet.getString("cardNetwork"));
				paymentInfo.setCardNumber(resultSet.getString("cardNo"));
				paymentInfo.setBankName(resultSet.getString("final_bankName"));
				paymentInfo.setCardBank( resultSet.getString("final_bankName"));
				paymentInfo.setExpDate(resultSet.getString("cardExpiryDate"));
				if( resultSet.getString("cash") != null) {
					paymentInfo.setAdditional_cash( resultSet.getString("cash"));
				}
			} else if (resultSet.getString("cheqNo") != null || resultSet.getString("cheqDate") != null) {
				paymentInfo.setType("cheq");
				paymentInfo.setCheqDate(resultSet.getString("cheqDate"));
				paymentInfo.setCheqNo( resultSet.getString("cheqNo"));
				paymentInfo.setBankName(resultSet.getString("bankName"));
				if( resultSet.getString("cash") != null) {
					paymentInfo.setAdditional_cash( resultSet.getString("cash"));
				}
			} else if (resultSet.getString("onlinePaymentMode") != null || resultSet.getString("onlineTransactionId") != null) {
				paymentInfo.setType("online");
				paymentInfo.setPayMode(resultSet.getString("onlinePaymentMode"));
				paymentInfo.setTransactionId(resultSet.getString("onlineTransactionId"));
				paymentInfo.setRemark(resultSet.getString("onlineRemark"));
				if( resultSet.getString("cash") != null) {
					paymentInfo.setAdditional_cash( resultSet.getString("cash"));
				}
			} else {
				paymentInfo.setType("cash");
			}
			
			
			finalResponse.setPaymentInfo(paymentInfo);
			ProductInfo productInfo = new ProductInfo();
			productInfo.setName(resultSet.getString("brandName"));
			productInfo.setModel(resultSet.getString("brandModel"));
			productInfo.setSn(resultSet.getString("serialNumber"));
			productInfo.setQuantity(resultSet.getString("product_qty"));
			productInfo.setPrice(resultSet.getString("product_unit_price"));
			productInfo.setTaxAmmount(resultSet.getString("tax_amount"));
			productInfo.setTaxType(resultSet.getString("tax_type"));
			productInfo.setTaxValue(resultSet.getString("tax_value"));
			productInfo.setGrandTotal(resultSet.getString("total_amount"));
			productInfo.setTaxRate(resultSet.getString("tax_rate"));
			productInfo.setOrderDate(resultSet.getString("order_date"));
			
			List<ProductInfo> productInfoList = new ArrayList<>();
			productInfoList.add(productInfo);
			
			finalResponse.setProductInfo(productInfoList);
			
			finalResponse.setInvoiceId(resultSet.getString("invoice_id"));
			finalResponse.setInvoiceTin(resultSet.getString("invoice_tin"));
			
			finalResponseList.add(finalResponse);
			
		}
				
		this.dbConnection.close();
		salesHistoryResponse.setSalesList(finalResponseList);
		
		
	}

	public SalesHistoryResponse getSearchResult() {
		// TODO Auto-generated method stub
		return this.salesHistoryResponse;
	}

	

	
}
