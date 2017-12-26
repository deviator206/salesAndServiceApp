package com.main.models;

import java.util.HashMap;
import java.util.List;

public class SalesServiceResponse extends MainResponse{
	
	private int counter =0;
	private String invoiceId ="";
	private String vatTinNumber ="";
	private CustomerServiceResponse customerServiceResponse;
	private List<String> newProductServiceResponse;
	
	
	
	public List<String> getProductServiceResponse() {
		return newProductServiceResponse;
	}


	public void setProductServiceResponse(List<String> list) {
		this.newProductServiceResponse = list;
	}


	public CustomerServiceResponse getCustomerServiceResponse() {
		return customerServiceResponse;
	}


	public void setCustomerServiceResponse(CustomerServiceResponse customerServiceResponse) {
		this.customerServiceResponse = customerServiceResponse;
	}


	public int getCounter() {
		return counter;
	}


	public String getVatTinNumber() {
		return vatTinNumber;
	}


	public void setVatTinNumber(String vatTinNumber) {
		this.vatTinNumber = vatTinNumber;
	}


	public void setInvoiceId(String invoiceInfo) {
		this.invoiceId = invoiceInfo;
	}
	public String getInvoiceId() {
		return this.invoiceId ;
	}

	
	
	
	public void setCounter(int i) {
		this.counter = i;
	}
	
	public void incrementCounter() {
		this.counter++;
	}

	public int getCounterValue() {
		// TODO Auto-generated method stub
		return this.counter;
	}

	public void setInvoiceInfo(HashMap<String, String> invoiceInformation) {
			invoiceId = invoiceInformation.get("invoice");
			vatTinNumber = invoiceInformation.get("vatTinNumber");
	}
	
}
