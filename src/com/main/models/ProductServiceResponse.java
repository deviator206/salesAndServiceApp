package com.main.models;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceResponse  extends MainResponse{
	private int counter =0;
	private List<String> createdProductList = new ArrayList();
	

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
	
	public List<String> getCreatedProductList() {
		// TODO Auto-generated method stub
		return this.createdProductList;
	}
	

	public void setProductCreatedList(List<String> productCreatedList) {
		this.createdProductList =productCreatedList;
		
	}

	

}
