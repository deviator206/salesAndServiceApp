package com.main.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.main.models.SerachProductServiceResponse;
import com.main.models.SingleProductModel;

public class SearchProductServiceImpl extends CreateProductServiceImpl {

	private SerachProductServiceResponse serachProductServiceResponse;
	private String finalColumn = "";
	private String queryText = "";

	public SerachProductServiceResponse getSearchResponse() {
		return serachProductServiceResponse;
	}

	public void setSearchOn(String queryOnColumn) {
		if (queryOnColumn.equals("NAME")) {
			this.finalColumn = this.COL_BRAND_NAME;

		} else if (queryOnColumn.equals("MODEL")) {
			this.finalColumn = this.COL_BRAND_MODEL;
		} else if (queryOnColumn.equals("SN")) {
			this.finalColumn = this.COL_SN;
		}

	}

	public void setSearchText(String queryText) {
		this.queryText = queryText;

	}

	public void executeSearch() {
		this.getConnection();
		Statement stmt;
		serachProductServiceResponse = new SerachProductServiceResponse();
		try {
			
			serachProductServiceResponse.setStatus(false);
			stmt = this.dbConnection.createStatement();
			ResultSet rs=stmt.executeQuery("select * from "+this.PRODUCT_TABLE+" where "+this.finalColumn+" LIKE '%"+this.queryText+"' OR "+this.finalColumn+" LIKE '"+this.queryText+"%' OR "+this.finalColumn+" LIKE '%"+this.queryText+"%'   ");
			List<SingleProductModel> singleProductModelList = new ArrayList<>();
			while(rs.next()) {
				serachProductServiceResponse.setStatus(true);
				SingleProductModel singleProductModel = new SingleProductModel();
				singleProductModel.setId(rs.getInt(1));
				singleProductModel.setName(rs.getString(2));
				singleProductModel.setModel(rs.getString(3));
				singleProductModel.setSn(rs.getString(4));
				singleProductModel.setPrice(rs.getInt(5));
				singleProductModel.setTaxType(rs.getString(6));
				singleProductModelList.add(singleProductModel);
			}
			serachProductServiceResponse.setSingleProductModelList(singleProductModelList);
			this.dbConnection.close();  
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

	}

}
