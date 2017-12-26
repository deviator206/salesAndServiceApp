package com.main.models;

public class PaymentInfoModel {
	
private String advancePayment;
private String finalAmount;

public String getAdvancePayment() {
	return advancePayment;
}

public void setAdvancePayment(String advancePayment) {
	this.advancePayment = advancePayment;
}

/**
 * @return the finalAmount
 */
public String getFinalAmount() {
	return finalAmount;
}

/**
 * @param finalAmount the finalAmount to set
 */
public void setFinalAmount(String finalAmount) {
	this.finalAmount = finalAmount;
} 
}
