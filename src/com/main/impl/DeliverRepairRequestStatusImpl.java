package com.main.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.main.models.CommentsInfoModel;
import com.main.models.CourierInfoModel;
import com.main.models.CustomerServiceResponse;
import com.main.models.MainResponse;
import com.main.models.PaymentInfoModel;
import com.main.models.ProductInfoModel;
import com.main.models.RepairRequestResponse;
import com.main.models.RepairServiceResponse;
import com.main.models.SearchRepairServiceResponse;
import com.main.models.UserInfo;

public class DeliverRepairRequestStatusImpl extends ServiceBase {

	public String getServiceID() {
		return serviceID;
	}

	public void setServiceID(String serviceID) {
		this.serviceID = serviceID.replaceAll(":", "/");
	}

	public String getServiceItemList() {
		return serviceItemList;
	}

	public void setServiceItemList(String serviceItemList) {
		this.serviceItemList = serviceItemList;
	}

	private String serviceID;
	private String serviceItemList;
	private  List<RepairRequestResponse> responseSearchResult;
	private SearchRepairServiceResponse mainResponse;
	private JSONObject finalPaymentInfo;
	private RepairServiceResponse finalPaymentDone;
	private String finalServiceNumber;
	
	private String outwardCName="";
	private String outwardCPhn="";
	private String outwardCDocNo="";
	private int outwardCBoolean= 0 ;
	private String finalDeliveryDate;


	public CustomerServiceResponse returnValidCustomerInfo(int customerId) throws SQLException{
		CustomerServiceResponse customerInfo = new  CustomerServiceResponse();
		Statement stmt1;
		stmt1 = this.dbConnection.createStatement();
		String query1 = "select * from EMP_CUSTOMER_TABLE where id ="+customerId ;
		ResultSet rs1 = stmt1.executeQuery(query1);
		while (rs1.next()) {
			customerId = rs1.getInt(1);
			customerInfo.setId(customerId);
			customerInfo.setName(rs1.getString(2));
			customerInfo.setAddress(rs1.getString(3));
			customerInfo.setPhone( rs1.getString(4));
		}
		return customerInfo;
	}
	
	public void execute() throws SQLException {
		this.getConnection();
		Statement stmt1;
		stmt1 = this.dbConnection.createStatement();
		this.responseSearchResult = new ArrayList<>();
		this.mainResponse = new SearchRepairServiceResponse();
		
		
		String query1 = "select * from SERVICE_INFO_TABLE where  service_order_number = '"+this.serviceID+"' AND "+this.getServiceOrderActionsList();
		ResultSet rs = stmt1.executeQuery(query1);
		while (rs.next()) {
			RepairRequestResponse repairServiceResponse = new RepairRequestResponse();
			//customer 
			repairServiceResponse.setCustomerInfo(this.returnValidCustomerInfo(rs.getInt(2)));
			
			List<ProductInfoModel> productInfoTempList = new ArrayList<>();
			ProductInfoModel productInfo = new ProductInfoModel();
			productInfo.setId(rs.getInt(1));
			productInfo.setName(rs.getString(3));
			productInfo.setModel(rs.getString(4));
			productInfo.setSn(rs.getString(5));
			productInfo.setServiceStatus(rs.getString(15));
			productInfo.setService_order_date(rs.getString(17));
			productInfoTempList.add(productInfo);
			repairServiceResponse.setProductInfo(productInfoTempList);
			
			UserInfo userInfo = new UserInfo();
			userInfo.setId(rs.getInt(6));
			repairServiceResponse.setUserInfo(userInfo);
			
			repairServiceResponse.setAccessoryList(rs.getString(7));
			repairServiceResponse.setProblemList(rs.getString(8));
			
			
			CourierInfoModel courierInfo = new CourierInfoModel();
			courierInfo.setCourierPhone(rs.getString(11));
			courierInfo.setCourierName(rs.getString(10));
			courierInfo.setCourierDocumentNo(rs.getString(16));
			repairServiceResponse.setCourierInfo(courierInfo);;
			
			
			CommentsInfoModel commentInfo = new CommentsInfoModel();
			commentInfo.setTech(rs.getString(12));
			commentInfo.setShopkeeper(rs.getString(13));
			commentInfo.setCustomer(rs.getString(14));
			repairServiceResponse.setCommentsInfo(commentInfo);
			
			repairServiceResponse.setServiceStatus(rs.getString(15));
			repairServiceResponse.setServiceDate(rs.getString(17));
			repairServiceResponse.setServiceNumber(rs.getString(22));
			repairServiceResponse.setVatTinNumber(rs.getString(23));
			repairServiceResponse.setAdvancePayment(rs.getString(27));
			
			PaymentInfoModel paymentInfoModel = new PaymentInfoModel();
			paymentInfoModel.setAdvancePayment(rs.getString(27));
			repairServiceResponse.setPaymentInfo(paymentInfoModel);
			responseSearchResult.add(repairServiceResponse);
			mainResponse.setStatus(true);
		}
		
		mainResponse.setSearchResults(this.responseSearchResult);
		this.dbConnection.close();
	}

	private String getServiceOrderActionsList() {
		String query = "";
		String[] orderActions = this.serviceItemList.split(",");
		
		for(int i=0;i<orderActions.length;i++){
			if (i > 0) {
				query = query.concat(" OR ");
			}
			query = query.concat(" id =  "+orderActions[i]);
		}
		return query;
	}

	public SearchRepairServiceResponse getSearchResult() {
		// TODO Auto-generated method stub
		return this.mainResponse;
	}

	public void setFinalPayment(JSONObject jsonObject) {
		this.finalPaymentInfo = jsonObject;
		
	}

	public void executeFinalPayment() throws SQLException, JSONException {
		this.getConnection();
		Statement stmt1;
		String sql ="update SERVICE_INFO_TABLE SET finalPayment = ?, serviceStatus = ? , isOutwardCourier =?, outwardCourierDocumentNo = ?,  outwardCourierName = ?, outwardCourierPhone = ? , service_completion_date = ? where service_order_number = '"+this.finalServiceNumber+"' ";
		PreparedStatement ps = this.dbConnection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, this.finalPaymentInfo.getJSONObject(this.finalPaymentInfo.getString("paymentType")).getString("amount"));
		ps.setString(2, "DTC");
		ps.setInt(3, outwardCBoolean);
		ps.setString(4, outwardCDocNo);
		ps.setString(5, outwardCName);
		ps.setString(6,outwardCPhn);
		ps.setString(7,finalDeliveryDate);
		int count  = ps.executeUpdate();
		finalPaymentDone = new RepairServiceResponse();
		if (count > 0) {
			finalPaymentDone.setStatus(true);
			JSONObject invoiceInformation = this.fetchInvoiceInformation();
			finalPaymentDone.setRepairReceiptId(invoiceInformation.getString("invoice"));
			finalPaymentDone.setVatTinNumber(invoiceInformation.getString("vatTinNumber"));
		}
		this.dbConnection.close();
	}

	private JSONObject fetchInvoiceInformation() throws SQLException, JSONException {
		Statement stmt;
		stmt = this.dbConnection.createStatement();
		String query = "";
		query = "select * from SERVICE_INFO_TABLE where service_order_number = '" + this.finalServiceNumber+"'";
		ResultSet rs = stmt.executeQuery(query);
		JSONObject invoiceInfo = new JSONObject();
		while (rs.next()) {
			invoiceInfo.put("invoice",rs.getString(22));
			invoiceInfo.put("vatTinNumber", rs.getString(26));
			break;
		}
		return invoiceInfo;
	}

	public RepairServiceResponse getInvoiceResult() {
		// TODO Auto-generated method stub
		return this.finalPaymentDone;
	}

	public void setServiceNumber(String string) {
		this.finalServiceNumber = string;
		
	}

	public void setOutwardCourierInfo(JSONObject jsonObject) throws JSONException {
		// TODO Auto-generated method stub
		if(jsonObject.getBoolean("isCourier")) {
			outwardCName=jsonObject.getString("courierName");
			outwardCPhn=jsonObject.getString("courierPhone");
			outwardCDocNo=jsonObject.getString("courierDocumentNo");
			outwardCBoolean= 1 ;
		}
		
	}

	public void setFinalDeliveryDate(String finalDate) {
		// TODO Auto-generated method stub
		this.finalDeliveryDate = finalDate;
	}

	
	

}
