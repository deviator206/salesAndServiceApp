package com.main.models.sales;

import java.util.ArrayList;
import java.util.List;

public class SingleSaleInfo {
	private String invoiceId;
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getInvoiceTin() {
		return invoiceTin;
	}
	public void setInvoiceTin(String invoiceTin) {
		this.invoiceTin = invoiceTin;
	}
	private String invoiceTin;
	private CustomerInfo customerInfo = new CustomerInfo();
	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}
	public void setCustomerInfo(CustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}
	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
	public List<ProductInfo> getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(List<ProductInfo> productInfo) {
		this.productInfo = productInfo;
	}
	private PaymentInfo paymentInfo =new PaymentInfo();
	private List<ProductInfo> productInfo = new ArrayList<>();
	

}
