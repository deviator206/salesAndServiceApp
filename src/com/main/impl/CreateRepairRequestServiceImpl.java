package com.main.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.main.models.RepairServiceResponse;
import com.main.models.SalesServiceResponse;

public class CreateRepairRequestServiceImpl extends ServiceBase {
	/*
	 * 
	 * create table SERVICE_INFO_TABLE ( id int(30) not null auto_increment,
	 * customerId int not null ,
	 * 
	 * productName varchar(60), productModel varchar(60), productSN varchar(60),
	 * 
	 * userId int not null ,
	 * 
	 * accessoryList varchar(60), problemList varchar(60), isCourier boolean
	 * default false, courierName varchar(30), courierPhone varchar(30),
	 * techComment varchar(120), shopUserComment varchar(120), customerComment
	 * varchar(120), serviceStatus varchar(20), primary key (id), FOREIGN
	 * KEY(customerId) REFERENCES SERVICE_CUSTOMER_TABLE(id), FOREIGN
	 * KEY(userId) REFERENCES validEmporiumUser(empId) )
	 * 
	 * 
	 * 
	 */

	public String SERVICE_INFO_TABLE = "SERVICE_INFO_TABLE";
	public String customerId = "customerId";
	public String productName = "productName";
	public String productModel = "productModel";
	public String productSN = "productSN";
	public String userId = "userId";
	public String accessoryList = "accessoryList";
	public String problemList = "problemList";
	public String isCourier = "isCourier";
	public String courierName = "courierName";
	public String courierPhone = "courierPhone";
	public String techComment = "techComment";
	public String shopUserComment = "shopUserComment";
	public String customerComment = "customerComment";
	public String serviceStatus = "serviceStatus";
	public String courierDocumentNo = "courierDocumentNo";
	public String service_order_date = "service_order_date";
	public String service_completion_date = "service_completion_date";
	public String service_order_number = "service_order_number";
	
	public String estimated_delivery_cost = "estimated_delivery_cost";
	public String estimated_delivery_date = "estimated_delivery_date";

	public String actual_service_cost = "actual_service_cost";
	public String tentative_quoted_cost = "tentative_quoted_cost";
	public String tentative_service_completion_date = "tentative_service_completion_date";
	public String vatTinNumber = "vatTinNumber";
	public String advancedPayment = "advancedPayment";
	
	public String taxName = "taxName";
	public String taxRate = "taxRate";
	public String taxValue = "taxValue";

	public JSONObject customerInfo;
	public JSONObject courierInfo;
	public JSONObject tecnicianInfo;
	public JSONArray productInfo; // contains tax & taxtype & tax rate & tax
									// value
	public String probList;
	public String accList;
	public String shopUserCom;
	public String customerCom;
	public JSONObject userInfo;
	public Timestamp serviceCreationDate;
	public Timestamp serviceCompletionDate;
	public Timestamp tentaiveServiceCompletionDate;
	public String tentaiveServiceCost;
	public String finalServiceCost;
	public JSONObject taxInfo;
	public String serviceInvoiceNumber;
	public JSONObject paymentInfo;
	//public String advancedPayment;
	public String tentative_quoted_costInfo;
	
	public RepairServiceResponse repairServiceResponse;
	
	public String getTentative_service_completion_dateInfo() {
		return tentative_service_completion_dateInfo;
	}

	public void setTentative_service_completion_dateInfo(String tentative_service_completion_dateInfo) {
		this.tentative_service_completion_dateInfo = tentative_service_completion_dateInfo;
	}

	public void setService_order_dateInfo(String service_order_dateInfo) {
		this.service_order_dateInfo = service_order_dateInfo;
	}
	public String tentative_service_completion_dateInfo;
	public String service_order_dateInfo;
	private JSONObject estimatedObject;

	public void setTentative_quoted_costInfo(String tentative_quoted_costInfo) {
		this.tentative_quoted_costInfo = tentative_quoted_costInfo;
	}

	public void execute() throws InternalError, JSONException, SQLException {
		this.getConnection();
		int customerValidID;
		repairServiceResponse = new RepairServiceResponse();
		repairServiceResponse.setStatus(false);

		if (this.userInfo.getString("id") == null || this.userInfo.getString("id").equals("")) {
			throw new InternalError("SHOP USER INFORMATION NOT PROVIDED");
		}
		// createCustomer if not created
		if (customerInfo.getString("id") == null || customerInfo.getString("id").equals("")) {
			// create customer
			CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
			customerServiceImpl.setUserName(customerInfo.getString("name").toUpperCase());
			customerServiceImpl.setUserAddress(customerInfo.getString("address").toUpperCase());
			customerServiceImpl.setUserPhone(customerInfo.getString("phone"));
			customerServiceImpl.setUserAlternatePhone(customerInfo.getString("alternateNo"));
			customerServiceImpl.setUserEmail(customerInfo.getString("email"));
			customerServiceImpl.execute();
			customerValidID = customerServiceImpl.getCustomerCreationResponse().getId();
		} else {
			customerValidID = Integer.parseInt(customerInfo.getString("id"));
		}

		GenerateInvoiceOnlyImpl generateInvoiceOnlyImpl = new GenerateInvoiceOnlyImpl();
		generateInvoiceOnlyImpl.SALES_INVOICE_TABLE = "REPAIR_INVOICE_TABLE";
		HashMap<String, String> invoiceInformation = generateInvoiceOnlyImpl.getNewInvoice();

		String sql = "insert into " + this.SERVICE_INFO_TABLE + " (" + this.customerId + ", " + this.productName + ", "	+ this.productModel + "," + this.productSN + "," + this.accessoryList + "," + this.problemList + ","+ this.shopUserComment + "," + this.customerComment + "," + this.serviceStatus + ","+ this.tentative_quoted_cost + "," + this.tentative_service_completion_date + ","+ this.service_order_date + ","+ this.service_order_number + "," + this.vatTinNumber + ","	+ this.isCourier + "," + this.courierName + "," + this.courierPhone + "," + this.courierDocumentNo + ","+ this.userId + "," + this.advancedPayment + "," + this.estimated_delivery_cost+ "," + this.estimated_delivery_date+") "+"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
		PreparedStatement ps = this.dbConnection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		//this.dbConnection.setAutoCommit(false);
		int count = 0;
		int paymentInserted= 0;
		for (int productLoop = 0; productLoop < this.productInfo.length(); productLoop++) {
			JSONObject singleProduct = (JSONObject) this.productInfo.get(productLoop);
			ps.setInt(1, customerValidID);
			ps.setString(2, singleProduct.getString("name").toUpperCase());
			ps.setString(3, singleProduct.getString("model").toUpperCase());
			ps.setString(4, singleProduct.getString("sn"));
			ps.setString(5, this.accList);
			ps.setString(6, this.probList);
			ps.setString(7, this.shopUserCom);
			ps.setString(8, this.customerCom);
			ps.setString(9, "NS");
			ps.setString(10, this.tentative_quoted_costInfo);
			ps.setTimestamp(11, (this.tentative_service_completion_dateInfo != null) ?Timestamp.valueOf(this.tentative_service_completion_dateInfo):null);
			ps.setTimestamp(12, (this.service_order_dateInfo != null) ?Timestamp.valueOf(this.service_order_dateInfo):null);
			ps.setString(13, invoiceInformation.get("invoice"));
			ps.setString(14, invoiceInformation.get("vatTinNumber"));
			ps.setBoolean(15, this.courierInfo.getBoolean("isCourier"));
			ps.setString(16, this.courierInfo.getString("courierName"));
			ps.setString(17, this.courierInfo.getString("courierPhone"));
			ps.setString(18, this.courierInfo.getString("courierDocumentNo"));
			ps.setString(19, this.userInfo.getString("id"));
			int totalAdvancedPaid=0;
			int partialAmount = 0; 
			if (!this.paymentInfo.getJSONObject(this.paymentInfo.getString("paymentType")).getString("amount").equals("")) {
				partialAmount = Integer.parseInt(this.paymentInfo.getJSONObject(this.paymentInfo.getString("paymentType")).getString("amount"));
			}
			
			totalAdvancedPaid = partialAmount;
			if (this.paymentInfo.has("additional_cash")) {
				String additionalCash = this.paymentInfo.getString("additional_cash");
				
				if (additionalCash != null) {
					partialAmount = Integer.parseInt(this.paymentInfo.getJSONObject(this.paymentInfo.getString("paymentType")).getString("amount"));
					totalAdvancedPaid = partialAmount + Integer.parseInt(additionalCash);
				} 
			}
			
			ps.setString(20,  new Integer(totalAdvancedPaid).toString());
			if (this.estimatedObject.has("cost")) {
				ps.setString(21, this.estimatedObject.getString("cost"));
			} else {
				ps.setString(21, "NA");
			}
			
			if (this.estimatedObject.has("date")) {
				ps.setString(22, this.estimatedObject.getString("date"));
			}else {
				ps.setString(22, "NA");
			}
						
			count  = ps.executeUpdate();
 			break;
		}
		
		
		paymentInserted = this.insertPaymentInfo( invoiceInformation);
		

		if (count > 0 && paymentInserted > 0) {
			repairServiceResponse.setStatus(true);
			repairServiceResponse.setRepairReceiptId(invoiceInformation.get("invoice"));
			repairServiceResponse.setVatTinNumber(invoiceInformation.get("vatTinNumber"));
		}
		
		this.dbConnection.close();

	}

	private int insertPaymentInfo(HashMap<String, String> invoiceInformation) throws JSONException {
		SalesServiceResponse salesServiceResponse = new SalesServiceResponse();
		salesServiceResponse.setStatus(false);
		PaymentDetailsImpl paymentDetailsImpl = new PaymentDetailsImpl();
		paymentDetailsImpl.setInvoiceInfo(invoiceInformation);
		JSONArray paymentDetailsImplInput = new JSONArray();
		JSONObject newPaymentInfoInput = new JSONObject();
		String paymentType = this.paymentInfo.getString("paymentType");
		JSONObject singlePaymentInfo = this.paymentInfo.getJSONObject(paymentType);
		
	    Iterator paymentKeys = singlePaymentInfo.keys();
	    while(paymentKeys.hasNext()) {
	    	String key = (String)paymentKeys.next();
	        // loop to get the dynamic key
	        Object value = (Object)singlePaymentInfo.get(key);
	        if(value instanceof Integer) {
	        	newPaymentInfoInput.put(key,(int)value);
	        } else if(value instanceof String) {
	        	newPaymentInfoInput.put(key,(String)value);
	        } 
	        
	    }
	    newPaymentInfoInput.put("type", paymentType);
		int intAddtionalCash=0;
	    if (this.paymentInfo.has("additional_cash")) {
	    	String additionalCash = this.paymentInfo.getString("additional_cash");
			if (additionalCash != null && !additionalCash.isEmpty()) {
				intAddtionalCash = Integer.parseInt(additionalCash);
			}
	    }
		newPaymentInfoInput.put("additional_cash",intAddtionalCash);
		paymentDetailsImplInput.put(newPaymentInfoInput);
		paymentDetailsImpl.setPaymentInfo(paymentDetailsImplInput);
		salesServiceResponse = paymentDetailsImpl.updatePaymentDetails();
		return (salesServiceResponse.getStatus() ? 1 : 0);
	}

	public void setCustomerInfo(JSONObject jsonObject) {
		this.customerInfo = jsonObject;

	}

	public void setProductInfo(JSONArray jsonArray) {
		this.productInfo = jsonArray;

	}

	public void setCourierInfo(JSONObject jsonObject) {
		this.courierInfo = jsonObject;

	}

	public void setTechnicianInfo(JSONObject jsonObject) {
		this.tecnicianInfo = jsonObject;

	}

	public void setUserInfo(JSONObject jsonObject2) {
		this.userInfo = jsonObject2;

	}

	public void setPaymentInfo(JSONObject jsonObject2) {
		this.paymentInfo = jsonObject2;

	}

	public void setProblemListInfo(String str) {
		// TODO Auto-generated method stub
		this.probList = str;
		
	}

	public void setAccessoryListInfo(String str) {
		// TODO Auto-generated method stub
		this.accList = str;
	}
	
	public void setShopUserComment(String str) {
		// TODO Auto-generated method stub
		this.shopUserCom = str;
	}
	public void setCustomerComment(String str) {
		// TODO Auto-generated method stub
		this.customerCom= str;
	}

	public RepairServiceResponse getResponse() {
		return this.repairServiceResponse;
	}

	public void setServiceEstimation(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		this.estimatedObject = jsonObject;
		
	}
}
