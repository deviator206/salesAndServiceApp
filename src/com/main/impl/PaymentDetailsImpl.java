package com.main.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.main.models.ProductServiceResponse;
import com.main.models.SalesServiceResponse;

public class PaymentDetailsImpl extends ServiceBase{
	
	private JSONObject paymentInfo;
	private JSONArray paymentInfoList;
	private HashMap<String, String> invoiceInformation;
	
	private String COL_CASH = "cash";
	private String COL_INVOICE = "invoice_id";
	private String COL_TIN ="invoice_tin";
	private String PAYMENT_TABLE="PAYMENT_DETAILS_TABLE";
	private String COL_CARD_NO ="cardNo";
	private String COL_BANK_NAME="bankName";
	private String COL_CHEQ_NO ="cheqNo";
	private String COL_CHEQ_DATE= "cheqDate";
	private String COL_AMOUNT= "amount";
	
	public void setPaymentInfo(JSONArray jsonObject) {
		this.paymentInfoList = jsonObject;
		
	}

	public void setInvoiceInfo(HashMap<String, String> invoiceInformation2) {
		this.invoiceInformation = invoiceInformation2;
		
	}

	public SalesServiceResponse updatePaymentDetails() {
		SalesServiceResponse ps= new SalesServiceResponse();
		boolean success =false;
		int count = 0;
		this.getConnection();
		try {
			
			for (int i=0;i<this.paymentInfoList.length();i++){
				this.paymentInfo = this.paymentInfoList.getJSONObject(i);
				PreparedStatement preparedStmt =  getQueryBasedOnInput();
			    int UpdatedCount = preparedStmt.executeUpdate();
			    if (UpdatedCount > 0){
			    	count++;
			    }
			}
			System.out.println("COMPARING");
			System.out.print(count);
			System.out.print("-------");
			System.out.print(this.paymentInfoList.length());
			int len = this.paymentInfoList.length();
			if (count == len){
				success = true;
			}
			
		    this.dbConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ps.setStatus(success);
		ps.setCounter(count);
		return ps;
	}

	private PreparedStatement getQueryBasedOnInput() throws JSONException, SQLException {
		
		String query = "" ;
		PreparedStatement preparedStmt = null ;
	    	if(this.paymentInfo.getString("type").equalsIgnoreCase("CASH")) {
				//insert into PAYMENT_DETAILS_TABLE(cash,invoice_id,invoice_tin) values ('5000','CE/2017-18/6','2763039355V');
				query = "insert into "+this.PAYMENT_TABLE+"("+this.COL_INVOICE+","+this.COL_TIN+","+this.COL_AMOUNT+") values (?,?,?)";
				//("+this.paymentInfo.getString("cash")+","+this.invoiceInformation.get("invoice")+","+this.invoiceInformation.get("vatTinNumber")+")
				preparedStmt =  this.dbConnection.prepareStatement(query);
				
				preparedStmt.setFloat(3, this.paymentInfo.getInt("amount"));
				preparedStmt.setString(1, this.invoiceInformation.get("invoice"));
				preparedStmt.setString(2, this.invoiceInformation.get("vatTinNumber"));
				
			} else if(this.paymentInfo.getString("type").equalsIgnoreCase("CARD")) {
				//insert into PAYMENT_DETAILS_TABLE(cardNo,bankName,invoice_id,invoice_tin) values ('345678093234324823418','Standarad Chartered Bank','CE/2017-18/6','2763039355V');
				query = "insert into "+this.PAYMENT_TABLE+"("+this.COL_CARD_NO+","+this.COL_BANK_NAME+","+this.COL_INVOICE+","+this.COL_TIN+","+this.COL_AMOUNT+") values (?,?,?,?,?)";
				//("+this.paymentInfo.getString("cardNumber")+","+this.paymentInfo.getString("bankName")+","+this.invoiceInformation.get("invoice")+","+this.invoiceInformation.get("vatTinNumber")+")"
				preparedStmt =  this.dbConnection.prepareStatement(query);
				
				preparedStmt.setString(1, this.paymentInfo.getString("cardNumber"));
				preparedStmt.setString(2, this.paymentInfo.getString("bankName"));
				preparedStmt.setFloat(5, this.paymentInfo.getInt("amount"));
				preparedStmt.setString(3, this.invoiceInformation.get("invoice"));
				preparedStmt.setString(4, this.invoiceInformation.get("vatTinNumber"));
				

			} else if(this.paymentInfo.getString("type").equalsIgnoreCase("CHEQ")) {
				//insert into PAYMENT_DETAILS_TABLE(cheqNo,cheqDate,bankName,invoice_id,invoice_tin) values ('345678093218','12/6/2017','SBI','CE/2017-18/6','2763039355V');
				query = "insert into "+this.PAYMENT_TABLE+"("+this.COL_CHEQ_NO+","+this.COL_CHEQ_DATE+","+this.COL_BANK_NAME+","+this.COL_INVOICE+","+this.COL_TIN+","+this.COL_AMOUNT+") values (?,?,?,?,?,?)";
				// ("+this.paymentInfo.getString("cheqNo")+","+this.paymentInfo.getString("cheqDate")+","+this.paymentInfo.getString("bankName")+","+this.invoiceInformation.get("invoice")+","+this.invoiceInformation.get("vatTinNumber")+")
				preparedStmt =  this.dbConnection.prepareStatement(query);
				preparedStmt.setString(1, this.paymentInfo.getString("cheqNo"));
				preparedStmt.setString(2, this.paymentInfo.getString("cheqDate"));
				preparedStmt.setString(3, this.paymentInfo.getString("bankName"));
				preparedStmt.setFloat(6, this.paymentInfo.getInt("amount"));
				preparedStmt.setString(4, this.invoiceInformation.get("invoice"));
				preparedStmt.setString(5, this.invoiceInformation.get("vatTinNumber"));
				
				
			}else if(this.paymentInfo.getString("type").equalsIgnoreCase("ONLINE")) {
				//insert into PAYMENT_DETAILS_TABLE(cheqNo,cheqDate,bankName,invoice_id,invoice_tin) values ('345678093218','12/6/2017','SBI','CE/2017-18/6','2763039355V');
				query = "insert into "+this.PAYMENT_TABLE+"("+this.COL_CHEQ_NO+",onlinePaymentMode,onlineTransactionId,onlineRemark,"+this.COL_TIN+","+this.COL_AMOUNT+") values (?,?,?,?,?,?)";
				// ("+this.paymentInfo.getString("cheqNo")+","+this.paymentInfo.getString("cheqDate")+","+this.paymentInfo.getString("bankName")+","+this.invoiceInformation.get("invoice")+","+this.invoiceInformation.get("vatTinNumber")+")
				preparedStmt =  this.dbConnection.prepareStatement(query);
				preparedStmt.setString(1, this.paymentInfo.getString("payMode"));
				preparedStmt.setString(2, this.paymentInfo.getString("transactionId"));
				preparedStmt.setString(3, this.paymentInfo.getString("remark"));
				preparedStmt.setFloat(6, this.paymentInfo.getInt("amount"));
				preparedStmt.setString(4, this.invoiceInformation.get("invoice"));
				preparedStmt.setString(5, this.invoiceInformation.get("vatTinNumber"));
				
				
			}
		return preparedStmt;
	}

}
