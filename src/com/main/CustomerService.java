package com.main;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.main.impl.CustomerSearchImpl;
import com.main.impl.CustomerServiceImpl;
import com.main.models.CustomerSearchResponse;
import com.main.models.CustomerServiceResponse;

@Path("/customer/")
public class CustomerService {

	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerServiceResponse createCustomer(JSONObject customerInfo) throws JSONException{
		CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
		customerServiceImpl.setUserName(customerInfo.getString("customerName"));
		customerServiceImpl.setUserAddress(customerInfo.getString("customerAddress"));
		customerServiceImpl.setUserPhone(customerInfo.getString("customerPhone"));
		customerServiceImpl.execute();
		return customerServiceImpl.getCustomerCreationResponse();
	}
	
	
	@Path("serach-customer")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerSearchResponse searchCustomer(@QueryParam("text") String queryText) throws JSONException{
		CustomerSearchImpl customerSearchImpl = new CustomerSearchImpl();
		customerSearchImpl.setQueryText(queryText);
		customerSearchImpl.execute();
		return customerSearchImpl.getCustomerSearchResponse();
	}
	
	
	/*
	 * testing creation of customer
	public static void main(String[] args) {
		CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
		customerServiceImpl.setUserName("chenchunkkas");
		customerServiceImpl.setUserAddress("KP raoad Lulla nagar ear rahauntnagar");
		customerServiceImpl.setUserPhone("8976542398");
		customerServiceImpl.execute();
	}*/
	
	/*
	public static void main(String[] args) {
		
	CustomerSearchImpl customerSearchImpl = new CustomerSearchImpl();
		customerSearchImpl.setQueryText("n");
		customerSearchImpl.execute();
	}*/
	
}
