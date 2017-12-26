package com.main.models;

public class CustomerServiceResponse  extends MainResponse{
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * @return the alternateNo
	 */
	public String getAlternateNo() {
		return alternateNo;
	}
	/**
	 * @param alternateNo the alternateNo to set
	 */
	public void setAlternateNo(String alternateNo) {
		this.alternateNo = alternateNo;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	private int id;
	private String name;
	private String address;
	private String phone;
	private String alternateNo;
	private String email;

}
