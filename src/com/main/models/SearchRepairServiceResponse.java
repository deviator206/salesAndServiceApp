package com.main.models;

import java.util.ArrayList;
import java.util.List;

public class SearchRepairServiceResponse extends MainResponse {
	
	public List<RepairRequestResponse> searchResults = new ArrayList<>();
	private int finalIncome;

	public List<RepairRequestResponse> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(List<RepairRequestResponse> responseSearchResult) {
		this.searchResults = responseSearchResult;
	}

	/**
	 * @return the finalIncome
	 */
	public int getFinalIncome() {
		return finalIncome;
	}

	/**
	 * @param totalIncome the finalIncome to set
	 */
	public void setFinalIncome(int totalIncome) {
		this.finalIncome = totalIncome;
	}

}
