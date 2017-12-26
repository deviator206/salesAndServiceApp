package com.main.models;

import java.util.List;

public class SerachProductServiceResponse extends MainResponse {
	private List<SingleProductModel> singleProductModelList ;

	public List<SingleProductModel> getSingleProductModelList() {
		return singleProductModelList;
	}

	public void setSingleProductModelList(List<SingleProductModel> singleProductModelList) {
		this.singleProductModelList = singleProductModelList;
	}

}
