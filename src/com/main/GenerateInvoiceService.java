package com.main;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.main.impl.CreateNewSaleEntryServiceImpl;
import com.main.impl.CreateProductServiceImpl;
import com.main.impl.CustomerServiceImpl;
import com.main.impl.GenerateInvoiceOnlyImpl;
import com.main.impl.PaymentDetailsImpl;
import com.main.models.SalesServiceResponse;

@Path("/invoice/")
public class GenerateInvoiceService {
	
	@Path("sales")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SalesServiceResponse createSalesInvoice(JSONObject orderInfo) throws JSONException{
		GenerateInvoiceOnlyImpl generateInvoiceOnlyImpl = new GenerateInvoiceOnlyImpl();
		HashMap<String, String> invoiceInformation = generateInvoiceOnlyImpl.getNewInvoice();
		SalesServiceResponse salesServiceResponse = new SalesServiceResponse();
		salesServiceResponse.setStatus(false);
		if (invoiceInformation.size() > 0){
			System.out.println(invoiceInformation);
			PaymentDetailsImpl paymentDetailsImpl = new PaymentDetailsImpl();
			paymentDetailsImpl.setInvoiceInfo(invoiceInformation);
			
			paymentDetailsImpl.setPaymentInfo(orderInfo.getJSONArray("paymentInfo"));
			salesServiceResponse = paymentDetailsImpl.updatePaymentDetails();
			if (salesServiceResponse.getStatus()){
				
				JSONObject customerInformation = orderInfo.getJSONObject("customerInfo");
				int customerValidID ;
				if (customerInformation.getString("id") != null && !customerInformation.getString("id").equals("")){
					
					//CUSTOMER EXIST
					// add it to table 
					//preseve this customer Id to use it for later case
					customerValidID = Integer.parseInt(customerInformation.getString("id"));
					
					CustomerServiceImpl customerServiceUpdateImpl = new CustomerServiceImpl();
					customerServiceUpdateImpl.setUserName(customerInformation.getString("name"));
					customerServiceUpdateImpl.setUserAddress(customerInformation.getString("address"));
					customerServiceUpdateImpl.setUserPhone(customerInformation.getString("phone"));
					customerServiceUpdateImpl.setUserID(customerValidID);
					customerServiceUpdateImpl.executeUpdateCustomer();
					
				} else {
					// NEW CUSTOMER
					CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
					customerServiceImpl.setUserName(customerInformation.getString("name"));
					customerServiceImpl.setUserAddress(customerInformation.getString("address"));
					customerServiceImpl.setUserPhone(customerInformation.getString("phone"));
					customerServiceImpl.execute();
					customerValidID=  customerServiceImpl.getCustomerCreationResponse().getId();
				}
				
				
				JSONArray productList = orderInfo.getJSONArray("productInfo");
				for (int j =0;j<productList.length();j++){
					JSONObject productInfo = productList.getJSONObject(j);
					CreateNewSaleEntryServiceImpl createNewSaleEntryServiceImpl =  new CreateNewSaleEntryServiceImpl();
					if (productInfo.getString("id") != null && !productInfo.getString("id").equals("")){
						createNewSaleEntryServiceImpl.setProductId(productInfo.getString("id"));
					}else {
						JSONArray newProductList = new JSONArray();
						productInfo.put("isNew", true);
						newProductList.put(productInfo);
						CreateProductServiceImpl createProductServiceImpl =  new CreateProductServiceImpl();
						createProductServiceImpl.setProductList(newProductList);
						createProductServiceImpl.executeCreation();
						
						createNewSaleEntryServiceImpl.setProductId(createProductServiceImpl.getResponse().getCreatedProductList().get(0));
					}
					createNewSaleEntryServiceImpl.setCustomerId(customerValidID);
					createNewSaleEntryServiceImpl.setOrderDate(Timestamp.valueOf(productInfo.getString("orderDate")));
					createNewSaleEntryServiceImpl.setInvoiceInformation(invoiceInformation.get("invoice"),invoiceInformation.get("vatTinNumber"));
					createNewSaleEntryServiceImpl.setQuantityInfo(productInfo.getLong("quantity"),productInfo.getLong("price"),productInfo.getLong("totalPrice"));
					createNewSaleEntryServiceImpl.setTaxInformation(productInfo.getString("taxType"),productInfo.getLong("taxValue"),productInfo.getLong("taxAmmount"),productInfo.getString("taxRate"));
					createNewSaleEntryServiceImpl.setGrandTotal(productInfo.getLong("grandTotal"));
					createNewSaleEntryServiceImpl.executeExistingProduct();
				}
				
				
				/*
				 * 
				 *
				JSONArray newProductList = new JSONArray();
				List<String> oldProductIdList = new ArrayList();
				for (int j =0;j<productList.length();j++){
					JSONObject productInfo = productList.getJSONObject(j);
					if (productInfo.getString("id") != null && !productInfo.getString("id").equals("")){
						// existing product
						oldProductIdList.add(productInfo.getString("id"));
					}else {
						//new product
						productInfo.put("isNew", true);
						newProductList.put(productInfo);
					}
				}
				
				List<String> newList = new ArrayList<String>(oldProductIdList);
				
				
				if(newProductList.length() > 0) {
					//create ProductList for new products
					CreateProductServiceImpl createProductServiceImpl =  new CreateProductServiceImpl();
					createProductServiceImpl.setProductList(newProductList);
					createProductServiceImpl.executeCreation();
					salesServiceResponse.setProductServiceResponse(createProductServiceImpl.getResponse().getCreatedProductList());
					newList.addAll(salesServiceResponse.getProductServiceResponse());
				}
				
				*/
				
				
				
				salesServiceResponse.setInvoiceId(invoiceInformation.get("invoice"));
				salesServiceResponse.setVatTinNumber(invoiceInformation.get("vatTinNumber"));
			
				
				
				
				
			}
		}
		return salesServiceResponse;
	}
	
	
	public static void main(String[] args) {
		String tx = "2017-05-25 04:38:25";
		Timestamp ts = Timestamp.valueOf(tx);
		System.out.println(ts);
		
			}
	
}
