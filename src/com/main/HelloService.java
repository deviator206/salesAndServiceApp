package com.main;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.main.impl.LoginServiceImpl;
import com.main.models.UserInfo;

//Sets the path to base URL + /hello
@Path("/hello")
public class HelloService {
	
	/*
	 * PLAIN STRING RETURN
	 */
	@GET
	@Path("/getstring")
	@Produces(MediaType.TEXT_PLAIN)
	 public String sayPlainTextHello() {
		//LoginServiceImpl lo = new LoginServiceImpl();
		//lo.getConnection();
	    return "Hello Jersey";
	  }
	
	/*
	 * PLAIN XML RETURN
	 */
	@GET
	@Path("/getxml")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	 public UserInfo sayPlainXMLHello() {
	    UserInfo userInfo = new UserInfo();
	    userInfo.setId(1);
	    userInfo.setName("SANDEEP");
	    return userInfo;
	  }
	
	@GET
	@Path("/getjson")
	@Produces({MediaType.APPLICATION_JSON })
	 public UserInfo sayPlainJSONHello() {
	    UserInfo userInfo = new UserInfo();
	    userInfo.setId(1);
	    userInfo.setName("SANDEEP");
	    return userInfo;
	  }
	
	
	@POST
	@Path("/jsoninput")
	@Produces({MediaType.APPLICATION_JSON })
	@Consumes({MediaType.APPLICATION_JSON })
	 public UserInfo getPlainJSONHello(JSONObject js) throws JSONException {
	    UserInfo userInfo = new UserInfo();
	    userInfo.setId(1);
	    userInfo.setName(js.getString("name"));
	    return userInfo;
	  }
	
	


}
