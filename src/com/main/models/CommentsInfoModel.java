package com.main.models;

public class CommentsInfoModel {

	public String getTech() {
		return tech;
	}
	public void setTech(String tech) {
		this.tech = tech;
	}
	public String getShopkeeper() {
		return shopkeeper;
	}
	public void setShopkeeper(String shopkeeper) {
		this.shopkeeper = shopkeeper;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getC_comment() {
		return c_comment;
	}
	public void setC_comment(String c_comment) {
		this.c_comment = c_comment;
	}
	/**
	 * @return the ca_comment
	 */
	public String getCa_comment() {
		return ca_comment;
	}
	/**
	 * @param ca_comment the ca_comment to set
	 */
	public void setCa_comment(String ca_comment) {
		this.ca_comment = ca_comment;
	}
	/**
	 * @return the pp_comment
	 */
	public String getPp_comment() {
		return pp_comment;
	}
	/**
	 * @param pp_comment the pp_comment to set
	 */
	public void setPp_comment(String pp_comment) {
		this.pp_comment = pp_comment;
	}
	/**
	 * @return the th_comment
	 */
	public String getTh_comment() {
		return th_comment;
	}
	/**
	 * @param th_comment the th_comment to set
	 */
	public void setTh_comment(String th_comment) {
		this.th_comment = th_comment;
	}
	private String tech;
	private String shopkeeper;
	private String customer;
	
	private String c_comment;
	private String ca_comment;
	private String pp_comment;
	private String th_comment;
	
}
