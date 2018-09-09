package com.main.models;

import java.util.ArrayList;
import java.util.List;

import com.main.models.sales.SingleSaleInfo;

public class SalesHistoryResponse  extends MainResponse {
	
	private List<SingleSaleInfo> salesList = new  ArrayList<>();

	public List<SingleSaleInfo> getSalesList() {
		return salesList;
	}

	public void setSalesList(List<SingleSaleInfo> salesList) {
		this.salesList = salesList;
	}
	
	

}
