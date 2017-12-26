package com.main;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.main.impl.LoginServiceImpl;
import com.main.models.LoginResponse;

@Path("/login")
public class LoginService {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LoginResponse authenticateUser(JSONObject userInformation) throws JSONException{
		LoginServiceImpl loginServiceImpl = new LoginServiceImpl();
		loginServiceImpl.setUserName(userInformation.getString("userName"));
		loginServiceImpl.setUserPassword(userInformation.getString("password"));
		loginServiceImpl.execute();
		return loginServiceImpl.getLoginResponse();
		
	}
}
