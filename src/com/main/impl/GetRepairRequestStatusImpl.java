package com.main.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONException;

import com.main.models.CommentsInfoModel;
import com.main.models.CourierInfoModel;
import com.main.models.CustomerServiceResponse;
import com.main.models.EstimationInfoModel;
import com.main.models.PaymentInfoModel;
import com.main.models.PaymentSingleFinalModel;
import com.main.models.PaymentSingleModel;
import com.main.models.ProductInfoListModel;
import com.main.models.ProductInfoModel;
import com.main.models.RepairRequestResponse;
import com.main.models.SearchRepairServiceResponse;
import com.main.models.UserInfo;

public class GetRepairRequestStatusImpl extends CreateRepairRequestServiceImpl {
	private String queryText="";
	private String queryOnColumn="";
	private String queryByType="";
	private String queryStartFrom="";
	private String queryStartTo="";
	SearchRepairServiceResponse mainResponse = new SearchRepairServiceResponse();
	public List<RepairRequestResponse> responseSearchResult;
	private String serviceOrderStatusInput="";
	private int totalIncome = 0;
	private int onlyAdvanceIncome= 0;
	private boolean serviceMode = false;
	private boolean manipulatePayment = false;

	public boolean isManipulatePayment() {
		return manipulatePayment;
	}

	public void setManipulatePayment(boolean manipulatePayment) {
		this.manipulatePayment = manipulatePayment;
	}

	public GetRepairRequestStatusImpl() {
		this.queryOnColumn = "";
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;

	}

	public void setQueryOnColumn(String queryOnColumn) {
		this.queryOnColumn = queryOnColumn;

	}

	public SearchRepairServiceResponse getSearchResult() {
		return mainResponse;
	}

	private String getColumnNameString(int customerId) {
		String query = "";
		if (queryOnColumn.equalsIgnoreCase("SERVICE_ID")) {
			query = " service_order_number LIKE '%" + this.queryText + "%'";

		} else if (queryOnColumn.equalsIgnoreCase("SERIAL_NUMBER")) {
			query = " productSN LIKE '%" + this.queryText + "%'";

		} else if (queryOnColumn.equalsIgnoreCase("PRODUCT_NAME")) {
			query = " productName LIKE '%" + this.queryText + "%'";

		} else if (queryOnColumn.equalsIgnoreCase("CUSTOMER_PHONE") || queryOnColumn.equalsIgnoreCase("CUSTOMER_NAME")) {
			query = " customerId  = " + customerId ;
		}
		return query;

	}

	private String getCustomerInfoString() {
		String query = "";
		if (queryOnColumn.equalsIgnoreCase("CUSTOMER_PHONE")) {
			query = " phone LIKE '%" + this.queryText + "%'";

		} else if (queryOnColumn.equalsIgnoreCase("CUSTOMER_NAME")) {
			query = " name LIKE '%" + this.queryText + "%'";
		}
		return query;

	}

	private String createQuery(CustomerServiceResponse customerInfo, int customerId){
		String query = "";
		if (this.queryByType != null && this.queryByType.equalsIgnoreCase("BY_QUERY")) {
			
			
			if ((this.queryText.equalsIgnoreCase("*") || this.queryText.equalsIgnoreCase("")) && customerId == 0) {
				query = "select * from " + this.SERVICE_INFO_TABLE +" ORDER BY id DESC";
				if (serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty()){
					query = "select * from " + this.SERVICE_INFO_TABLE +" where serviceStatus='"+serviceOrderStatusInput+"' ORDER BY id DESC ";
				}
			}
			else {
				query = "select * from " + this.SERVICE_INFO_TABLE + " where " + this.getColumnNameString(customerId)+" ORDER BY id DESC";
				if (serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty()){
					query = "select * from " + this.SERVICE_INFO_TABLE + " where " + this.getColumnNameString(customerId)+" AND  serviceStatus='"+serviceOrderStatusInput+"' ORDER BY id DESC";
				}
			}
		} else if (this.queryByType != null && this.queryByType.equalsIgnoreCase("BY_DATE")){
			query = "select * from " + this.SERVICE_INFO_TABLE +" where service_order_date  BETWEEN '"+Timestamp.valueOf(this.queryStartFrom)+"' AND  '"+Timestamp.valueOf(this.queryStartTo)+"' ORDER BY id DESC";
		}else if (this.queryByType == null) {
			this.queryByType = "";
			if ((this.queryText.equalsIgnoreCase("*") || this.queryText.equalsIgnoreCase("")) && customerId == 0) {
				query = "select * from " + this.SERVICE_INFO_TABLE +" ORDER BY id DESC";
				if (serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty()){
					query = "select * from " + this.SERVICE_INFO_TABLE +" where serviceStatus='"+serviceOrderStatusInput+"' ORDER BY id DESC ";
				}
			}
			else {
				query = "select * from " + this.SERVICE_INFO_TABLE + " where " + this.getColumnNameString(customerId)+" ORDER BY id DESC";
				if (serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty()){
					query = "select * from " + this.SERVICE_INFO_TABLE + " where " + this.getColumnNameString(customerId)+" AND  serviceStatus='"+serviceOrderStatusInput+"' ORDER BY id DESC";
				}
			}
			
		}else if (this.queryByType != null) {
			this.queryByType = "";
			if ((this.queryText.equalsIgnoreCase("*") || this.queryText.equalsIgnoreCase("")) && customerId == 0) {
				query = "select * from " + this.SERVICE_INFO_TABLE +" ORDER BY id DESC";
				if (serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty()){
					query = "select * from " + this.SERVICE_INFO_TABLE +" where serviceStatus='"+serviceOrderStatusInput+"' ORDER BY id DESC ";
				}
			}
			else {
				query = "select * from " + this.SERVICE_INFO_TABLE + " where " + this.getColumnNameString(customerId)+" ORDER BY id DESC";
				if (serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty()){
					query = "select * from " + this.SERVICE_INFO_TABLE + " where " + this.getColumnNameString(customerId)+" AND  serviceStatus='"+serviceOrderStatusInput+"' ORDER BY id DESC";
				}
			}
			
		}
		return query;
	}
	
	public void triggerSearch(CustomerServiceResponse customerInfo, int customerId) throws SQLException{
		// System.out.println(" Looping for Tigger Search............");
		Statement stmt;
		stmt = this.dbConnection.createStatement();
		
		String query = "";
		if(this.serviceMode) {
			query = this.createQueryForReport(customerInfo, customerId);
		} else {
			query = this.createQuery(customerInfo, customerId);
		}
		
		
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			
			RepairRequestResponse repairServiceResponse = new RepairRequestResponse();
			//customer 
			if (customerId != 0) {
				repairServiceResponse.setCustomerInfo(customerInfo);
			} else {
				repairServiceResponse.setCustomerInfo(this.returnValidCustomerInfo(rs.getInt(2)));
			}
			
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
			repairServiceResponse.setCourierInfo(courierInfo);
			
			
			CourierInfoModel outwardCourierInfo = new CourierInfoModel();
			outwardCourierInfo.setCourierPhone(rs.getString(43));
			outwardCourierInfo.setCourierName(rs.getString(42));
			outwardCourierInfo.setCourierDocumentNo(rs.getString(44));
			repairServiceResponse.setOutwardCourierInfo(outwardCourierInfo);
			
			EstimationInfoModel estimation = new EstimationInfoModel();
			if( rs.getString("estimated_delivery_cost") != null) 
			{
				estimation.setCost( rs.getString("estimated_delivery_cost"));
			}
			if( rs.getString("estimated_delivery_date") != null) 
			{
				estimation.setDate( rs.getString("estimated_delivery_date"));
			}
			repairServiceResponse.setEstimation(estimation);
			
			
			CommentsInfoModel commentInfo = new CommentsInfoModel();
			commentInfo.setTech(rs.getString(12));
			commentInfo.setShopkeeper(rs.getString(13));
			commentInfo.setCustomer(rs.getString(14));
			if (rs.getString(40) != null){
				commentInfo.setC_comment(rs.getString(39) +" : " +rs.getString(40));
			}
			
			if (rs.getString(35) != null){
				commentInfo.setPp_comment(rs.getString(38) + " : "+rs.getString(35));
			}
			
			if (rs.getString(33) != null){
				commentInfo.setCa_comment(rs.getString(37) + " : "+rs.getString(33));
			}
			
			if (rs.getString(31) != null){
				commentInfo.setTh_comment(rs.getString(36) + " : "+rs.getString(31));
			}
			
			repairServiceResponse.setCommentsInfo(commentInfo);
			
			repairServiceResponse.setServiceStatus(rs.getString(15));
			repairServiceResponse.setServiceDate(rs.getString(17));
			repairServiceResponse.setDeliveredToCustomerDate(rs.getString(18));
			repairServiceResponse.setServiceNumber(rs.getString(22));
			repairServiceResponse.setVatTinNumber(rs.getString(23));
			repairServiceResponse.setAdvancePayment(rs.getString(27));
			
			PaymentInfoModel paymentInfoModel = new PaymentInfoModel();
			paymentInfoModel.setAdvancePayment(rs.getString(27));
			if (rs.getString(28) != null && !rs.getString(27).isEmpty()){
				totalIncome = totalIncome + rs.getInt(28); 
				paymentInfoModel.setFinalAmount(rs.getString(28));
				 
			} else if(rs.getString(27) != null && !rs.getString(27).isEmpty()) {
				onlyAdvanceIncome = onlyAdvanceIncome +Integer.parseInt(rs.getString(27));
			}
				
			PaymentSingleModel paymentSingleModel = new PaymentSingleModel();
			PaymentSingleFinalModel paymentSingleFinalModel = new PaymentSingleFinalModel();
			if(this.serviceMode) {
				paymentSingleModel.setCash(rs.getString(48));
				paymentSingleModel.setCheqNo(rs.getString(49));
				paymentSingleModel.setBankName(rs.getString(50));
				paymentSingleModel.setIfscCode(rs.getString(51));
				paymentSingleModel.setCheqDate(rs.getString(52));
				paymentSingleModel.setAccountNo(rs.getString(53));
				paymentSingleModel.setCardNo(rs.getString(54));
				paymentSingleModel.setInvoice_id(rs.getString(55));
				paymentSingleModel.setInvoice_tin(rs.getString(56));
				paymentSingleModel.setAmount(rs.getString(57));
				paymentSingleModel.setOnlinePaymentMode(rs.getString(58));
				paymentSingleModel.setOnlineRemark(rs.getString(59));
				paymentSingleModel.setCardExpiryDate(rs.getString(60));
				paymentSingleModel.setCardNetwork(rs.getString(61));
				paymentSingleModel.setCardBank(rs.getString(62));	
				
				paymentSingleFinalModel.setFinal_cash(rs.getString(64));
				paymentSingleFinalModel.setFinal_cheqNo(rs.getString(65));
				paymentSingleFinalModel.setFinal_cheqDate(rs.getString(66));
				paymentSingleFinalModel.setFinal_bankName(rs.getString(67));
				paymentSingleFinalModel.setFinal_cardNo(rs.getString(68));
				paymentSingleFinalModel.setFinal_cardNetwork(rs.getString(69));
				paymentSingleFinalModel.setFinal_onlinePaymentMode(rs.getString(70));
				paymentSingleFinalModel.setFinal_onlineTransactionId(rs.getString(71));
				paymentSingleFinalModel.setFinal_onlineRemark(rs.getString(72));
				paymentSingleFinalModel.setFinal_amount(rs.getString(73));
				
			}
			
			if(this.manipulatePayment) {
				// indicates that status is being invoked for 
				// re-printing the information - VIEW should understand only singlePaymentInfo
			}
			
			repairServiceResponse.setPaymentSingleModel(paymentSingleModel);
			repairServiceResponse.setPaymentSingleFinalModel(paymentSingleFinalModel);
			
			repairServiceResponse.setPaymentInfo(paymentInfoModel);
			responseSearchResult.add(repairServiceResponse);
			mainResponse.setStatus(true);

		}
		
	}
	
	private String getBasicJoinQuery(){
		return " LEFT JOIN PAYMENT_DETAILS_TABLE ON SERVICE_INFO_TABLE.service_order_number = PAYMENT_DETAILS_TABLE.invoice_id";
	}
	
	private String getJoinBasedOrderByQuery(){
		return " ORDER BY SERVICE_INFO_TABLE.id DESC";
	}
	
	private String createQueryForReport(CustomerServiceResponse customerInfo, int customerId) {
		String query = "";
		if (this.queryByType != null && this.queryByType.equalsIgnoreCase("BY_QUERY")) {
			
			
			if ((this.queryText.equalsIgnoreCase("*") || this.queryText.equalsIgnoreCase("")) && customerId == 0) {
				query = "select * from " + this.SERVICE_INFO_TABLE +""+this.getBasicJoinQuery()+""+this.getJoinBasedOrderByQuery();
				if (serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty()){
					query = "select * from " + this.SERVICE_INFO_TABLE +""+this.getBasicJoinQuery()+" where serviceStatus='"+serviceOrderStatusInput+""+this.getJoinBasedOrderByQuery();
				}
			}
			else {
				query = "select * from " + this.SERVICE_INFO_TABLE +""+this.getBasicJoinQuery()+ " where " + this.getColumnNameString(customerId)+""+this.getJoinBasedOrderByQuery();
				if (serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty()){
					query = "select * from " + this.SERVICE_INFO_TABLE +""+this.getBasicJoinQuery()+ " where " + this.getColumnNameString(customerId)+" AND  serviceStatus='"+serviceOrderStatusInput+"' "+this.getJoinBasedOrderByQuery();
				}
			}
		} else if (this.queryByType != null && this.queryByType.equalsIgnoreCase("BY_DATE")){
			query = "select * from " + this.SERVICE_INFO_TABLE +""+this.getBasicJoinQuery()+" where serviceStatus='"+serviceOrderStatusInput+"' AND service_order_date  BETWEEN '"+Timestamp.valueOf(this.queryStartFrom)+"' AND  '"+Timestamp.valueOf(this.queryStartTo)+"' "+this.getJoinBasedOrderByQuery();
		}else if (this.queryByType == null) {
			this.queryByType = "";
			if ((this.queryText.equalsIgnoreCase("*") || this.queryText.equalsIgnoreCase("")) && customerId == 0) {
				query = "select * from " + this.SERVICE_INFO_TABLE +""+this.getBasicJoinQuery()+""+this.getJoinBasedOrderByQuery();
				if (serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty()){
					query = "select * from " + this.SERVICE_INFO_TABLE +""+this.getBasicJoinQuery()+" where serviceStatus='"+serviceOrderStatusInput+"'"+this.getJoinBasedOrderByQuery();
				}
			}
			else {
				query = "select * from " + this.SERVICE_INFO_TABLE +""+this.getBasicJoinQuery()+ " where " + this.getColumnNameString(customerId)+""+this.getJoinBasedOrderByQuery();
				if (serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty()){
					query = "select * from " + this.SERVICE_INFO_TABLE +""+this.getBasicJoinQuery()+ " where " + this.getColumnNameString(customerId)+" AND  serviceStatus='"+serviceOrderStatusInput+"' "+this.getJoinBasedOrderByQuery();
				}
			}
			
		}else if (this.queryByType != null) {
			this.queryByType = "";
			if ((this.queryText.equalsIgnoreCase("*") || this.queryText.equalsIgnoreCase("")) && customerId == 0) {
				query = "select * from " + this.SERVICE_INFO_TABLE +""+this.getBasicJoinQuery()+""+this.getJoinBasedOrderByQuery();
				if (serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty()){
					query = "select * from " + this.SERVICE_INFO_TABLE +""+this.getBasicJoinQuery()+" where serviceStatus='"+serviceOrderStatusInput+"'"+this.getJoinBasedOrderByQuery();
				}
			}
			else {
				query = "select * from " + this.SERVICE_INFO_TABLE +""+this.getBasicJoinQuery()+ " where " + this.getColumnNameString(customerId)+" "+this.getJoinBasedOrderByQuery();
				if (serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty()){
					query = "select * from " + this.SERVICE_INFO_TABLE +""+this.getBasicJoinQuery()+  " where " + this.getColumnNameString(customerId)+" AND  serviceStatus='"+serviceOrderStatusInput+"' "+this.getJoinBasedOrderByQuery();
				}
			}
			
		}
		return query;
	}

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
	public void execute() throws SQLException, JSONException {

		this.getConnection();
		
		responseSearchResult = new ArrayList<>();
		int customerId = 0;
		totalIncome = 0;
		onlyAdvanceIncome = 0;
		
		if (queryOnColumn.equalsIgnoreCase("CUSTOMER_PHONE") || queryOnColumn.equalsIgnoreCase("CUSTOMER_NAME") && !this.queryText.equalsIgnoreCase("") ) {
			Statement stmt1;
			stmt1 = this.dbConnection.createStatement();
			String query1 = "select * from EMP_CUSTOMER_TABLE where " + this.getCustomerInfoString();
			ResultSet rs1 = stmt1.executeQuery(query1);
			
			while (rs1.next()) {
				CustomerServiceResponse customerInfo = new  CustomerServiceResponse();
				customerId = rs1.getInt(1);
				customerInfo.setId(customerId);
				customerInfo.setName(rs1.getString(2));
				customerInfo.setAddress(rs1.getString(3));
				customerInfo.setPhone( rs1.getString(4));
				this.triggerSearch(customerInfo,customerId);
			}
		} else {
			this.triggerSearch(null,0);
		}
		
		mainResponse.setSearchResults(this.responseSearchResult);
		mainResponse.setFinalIncome(totalIncome);
		mainResponse.setOnlyAdvanceIncome(onlyAdvanceIncome);
		this.dbConnection.close();
		
	}

	public void setStatus(String status) {
		this.serviceOrderStatusInput = status;
	}

	public void setOrderStatusForSearch(String orderStatus) {
		// TODO Auto-generated method stub
		
	}

	public void setByType(String byType) {
		// TODO Auto-generated method stub
		queryByType =byType;
	}

	public void setStartTo(String startTo) {
		// TODO Auto-generated method stub
		this.queryStartTo = startTo; 
	}

	public void setStartFrom(String startFrom) {
		// TODO Auto-generated method stub
		queryStartFrom = startFrom;
	}

	public void setServiceMode() {
		// TODO Auto-generated method stub
		this.serviceMode = true;
		
	}

	
}
