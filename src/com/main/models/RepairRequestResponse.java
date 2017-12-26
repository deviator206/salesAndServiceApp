package com.main.models;

import java.util.List;

public class RepairRequestResponse extends MainResponse {

	private String accessoryList;
	private String vatTinNumber;
	private String advancePayment;

	public String getAdvancePayment() {
		return advancePayment;
	}

	public void setAdvancePayment(String advancePayment) {
		this.advancePayment = advancePayment;
	}

	public String getVatTinNumber() {
		return vatTinNumber;
	}

	public void setVatTinNumber(String vatTinNumber) {
		this.vatTinNumber = vatTinNumber;
	}

	public String getAccessoryList() {
		return accessoryList;
	}

	public void setAccessoryList(String accessoryList) {
		this.accessoryList = accessoryList;
	}

	public String getProblemList() {
		return problemList;
	}

	public void setProblemList(String problemList) {
		this.problemList = problemList;
	}

	public String getServiceNumber() {
		return serviceNumber;
	}

	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getServiceDate() {
		return serviceDate;
	}

	public void setServiceDate(String serviceDate) {
		this.serviceDate = serviceDate;
	}

	public CommentsInfoModel getCommentsInfo() {
		return commentsInfo;
	}

	public void setCommentsInfo(CommentsInfoModel commentInfo) {
		this.commentsInfo = commentInfo;
	}

	public CourierInfoModel getCourierInfo() {
		return courierInfo;
	}

	public void setCourierInfo(CourierInfoModel courierInfo) {
		this.courierInfo = courierInfo;
	}

	public CustomerServiceResponse getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(CustomerServiceResponse customerInfo) {
		this.customerInfo = customerInfo;
	}

	public PaymentInfoModel getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PaymentInfoModel paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public List<ProductInfoModel> getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(List<ProductInfoModel> productInfo) {
		this.productInfo = productInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * @return the totalIncome
	 */
	public String getTotalIncome() {
		return totalIncome;
	}

	/**
	 * @param totalIncome the totalIncome to set
	 */
	public void setTotalIncome(String totalIncome) {
		this.totalIncome = totalIncome;
	}

	/**
	 * @return the outwardCourierInfo
	 */
	public CourierInfoModel getOutwardCourierInfo() {
		return outwardCourierInfo;
	}

	/**
	 * @param outwardCourierInfo the outwardCourierInfo to set
	 */
	public void setOutwardCourierInfo(CourierInfoModel outwardCourierInfo) {
		this.outwardCourierInfo = outwardCourierInfo;
	}

	private String problemList;
	private String serviceNumber;
	private String serviceStatus;
	private String serviceDate;
	private String totalIncome;
	
	private CommentsInfoModel commentsInfo;
	private CourierInfoModel courierInfo;
	private CourierInfoModel outwardCourierInfo;
	private CustomerServiceResponse customerInfo;
	private PaymentInfoModel paymentInfo;
	private List<ProductInfoModel> productInfo;
	private UserInfo userInfo;

}
