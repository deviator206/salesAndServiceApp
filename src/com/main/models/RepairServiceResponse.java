package com.main.models;

public class RepairServiceResponse extends MainResponse{

	private int[] recordsCreated;
	private String repairReceiptId;
	private String vatTinNumber;

	public void setCreatedProductList(int[] recordsAffected) {
		this.recordsCreated = recordsAffected;
		
	}

	public void setRepairReceiptId(String string) {
		this.repairReceiptId = string;
		
	}
	
	public String getRepairReceiptId() {
		return this.repairReceiptId;
		
	}

	public void setVatTinNumber(String string) {
		this.vatTinNumber = string;
		
	}
	
	public String getVatTinNumber() {
		return this.vatTinNumber;
		
	}

}
