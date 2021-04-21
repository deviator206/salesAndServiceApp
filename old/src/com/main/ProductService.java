package com.main;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.main.impl.CreateProductServiceImpl;
import com.main.impl.SearchProductServiceImpl;
import com.main.models.ProductServiceResponse;
import com.main.models.SerachProductServiceResponse;

@Path("/product/")
public class ProductService {
	
	@Path("create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ProductServiceResponse createProductEntry(JSONObject productInfo) throws JSONException{
		JSONArray productList =  (JSONArray) productInfo.get("productList");
		CreateProductServiceImpl createProductServiceImpl =  new CreateProductServiceImpl();
		createProductServiceImpl.setProductList(productList);
		createProductServiceImpl.executeCreation();
		return createProductServiceImpl.getResponse();
	}
	
	
	
	@Path("search-product")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SerachProductServiceResponse searchProduct(@QueryParam("text") String queryText,@QueryParam("type") String queryOnColumn ) throws JSONException{
		SearchProductServiceImpl serachProductServiceImpl =  new SearchProductServiceImpl();
		serachProductServiceImpl.setSearchOn(queryOnColumn);
		serachProductServiceImpl.setSearchText(queryText);
		serachProductServiceImpl.executeSearch();
		return serachProductServiceImpl.getSearchResponse();
	}
}
