package com.main.models;

public class ProductInfoModel {

	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public String getService_order_date() {
		return service_order_date;
	}
	public void setService_order_date(String service_order_date) {
		this.service_order_date = service_order_date;
	}
	private String name;
	private String model;
	private String sn;
	private String serviceStatus;
	private String service_order_date;
	
	
	
}
