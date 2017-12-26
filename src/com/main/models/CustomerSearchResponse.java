package com.main.models;

import java.util.List;

public class CustomerSearchResponse extends MainResponse{
	private List<CustomerServiceResponse> customerServiceResponseList;

	public List<CustomerServiceResponse> getCustomerServiceResponseList() {
		return customerServiceResponseList;
	}

	public void setCustomerServiceResponseList(List<CustomerServiceResponse> customerServiceResponseList) {
		this.customerServiceResponseList = customerServiceResponseList;
	}
	
	

}
