// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CustomerServiceImpl.java

package com.main.impl;

import com.main.models.CustomerServiceResponse;
import java.io.PrintStream;
import java.sql.*;

// Referenced classes of package com.main.impl:
//            ServiceBase

public class CustomerServiceImpl extends ServiceBase
{

    public CustomerServiceImpl()
    {
        CUSTOMER_TABLE = "EMP_CUSTOMER_TABLE";
        COL_USER_NAME = "name";
        COL_USER_ADDRESS = "address";
        COL_USER_PHONE = "phone";
        COL_USER_ID = "id";
    }

    public void setUserName(String str)
    {
        userName = str;
    }

    public void setUserAddress(String str)
    {
        userAddress = str;
    }

    public void setUserPhone(String str)
    {
        userPhone = str;
    }

    public void setUserID(int str)
    {
        userID = str;
    }

    public void execute()
    {
        getConnection();
        customerServiceResponse = new CustomerServiceResponse();
        try
        {
            customerServiceResponse.setStatus(false);
            String query = (new StringBuilder()).append(" insert into ").append(CUSTOMER_TABLE).append(" (").append(COL_USER_NAME).append(", ").append(COL_USER_ADDRESS).append(",").append(COL_USER_PHONE).append(") values (?, ?, ?)").toString();
            PreparedStatement preparedStmt = dbConnection.prepareStatement(query, 1);
            preparedStmt.setString(1, userName);
            preparedStmt.setString(2, userAddress);
            preparedStmt.setString(3, userPhone);
            int count = preparedStmt.executeUpdate();
            if(count > 0)
                customerServiceResponse.setStatus(true);
            customerServiceResponse.setName(userName);
            customerServiceResponse.setAddress(userAddress);
            customerServiceResponse.setPhone(userPhone);
            ResultSet rs = preparedStmt.getGeneratedKeys();
            if(rs != null && rs.next())
                customerServiceResponse.setId(rs.getInt(1));
            System.out.println(count);
            dbConnection.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public CustomerServiceResponse getCustomerCreationResponse()
    {
        return customerServiceResponse;
    }

    public void executeUpdateCustomer()
    {
        getConnection();
        customerServiceResponse = new CustomerServiceResponse();
        try
        {
            customerServiceResponse.setStatus(false);
            String query = (new StringBuilder()).append(" update  ").append(CUSTOMER_TABLE).append(" set ").append(COL_USER_NAME).append(" =? ,").append(COL_USER_ADDRESS).append("=?, ").append(COL_USER_PHONE).append("=? , alternate_number = ? , email_id= ? where ").append(COL_USER_ID).append(" = ?").toString();
            PreparedStatement preparedStmt = dbConnection.prepareStatement(query, 1);
            preparedStmt.setString(1, userName);
            preparedStmt.setString(2, userAddress);
            preparedStmt.setString(3, userPhone);
            preparedStmt.setString(4, userAlternatePhone);
            preparedStmt.setString(5, userEmail);
            preparedStmt.setInt(6, userID);
            int count = preparedStmt.executeUpdate();
            if(count > 0)
                customerServiceResponse.setStatus(true);
            customerServiceResponse.setName(userName);
            customerServiceResponse.setAddress(userAddress);
            customerServiceResponse.setPhone(userPhone);
            customerServiceResponse.setAlternateNo(userAlternatePhone);
            customerServiceResponse.setEmail(userEmail);
            ResultSet rs = preparedStmt.getGeneratedKeys();
            if(rs != null && rs.next())
                customerServiceResponse.setId(rs.getInt(1));
            System.out.println(count);
            dbConnection.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public String getUserAlternatePhone()
    {
        return userAlternatePhone;
    }

    public void setUserAlternatePhone(String userAlternatePhone)
    {
        this.userAlternatePhone = userAlternatePhone;
    }

    public String getUserEmail()
    {
        return userEmail;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    private CustomerServiceResponse customerServiceResponse;
    private int userID;
    private String userName;
    private String userAddress;
    private String userPhone;
    private String userAlternatePhone;
    private String userEmail;
    public String CUSTOMER_TABLE;
    public String COL_USER_NAME;
    private String COL_USER_ADDRESS;
    private String COL_USER_PHONE;
    private String COL_USER_ID;
}
