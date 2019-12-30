// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CustomerSearchImpl.java

package com.main.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.main.models.CustomerSearchResponse;
import com.main.models.CustomerServiceResponse;

// Referenced classes of package com.main.impl:
//            CustomerServiceImpl

public class CustomerSearchImpl extends CustomerServiceImpl
{

    public CustomerSearchImpl()
    {
    }

    public String getQueryText()
    {
        return queryText;
    }

    public void setQueryText(String queryText)
    {
        this.queryText = queryText;
    }

    public void execute()
    {
        getConnection();
        customerSearchResponse = new CustomerSearchResponse();
        try
        {
            customerSearchResponse.setStatus(false);
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery((new StringBuilder()).append("select * from ").append(CUSTOMER_TABLE).append(" where ").append(COL_USER_NAME).append(" LIKE '%").append(queryText).append("' OR ").append(COL_USER_NAME).append(" LIKE '").append(queryText).append("%' OR ").append(COL_USER_NAME).append(" LIKE '%").append(queryText).append("%'   ").toString());
            List customerServiceResponses = new ArrayList();
            CustomerServiceResponse customerServiceResponse;
            for(; rs.next(); customerServiceResponses.add(customerServiceResponse))
            {
                customerSearchResponse.setStatus(true);
                customerServiceResponse = new CustomerServiceResponse();
                customerServiceResponse.setId(rs.getInt(1));
                customerServiceResponse.setName(rs.getString(2));
                customerServiceResponse.setAddress(rs.getString(3));
                customerServiceResponse.setPhone(rs.getString(4));
                customerServiceResponse.setAlternateNo(rs.getString(5));
            }

            customerSearchResponse.setCustomerServiceResponseList(customerServiceResponses);
            dbConnection.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public CustomerSearchResponse getCustomerSearchResponse()
    {
        return customerSearchResponse;
    }

    private String queryText;
    private CustomerSearchResponse customerSearchResponse;
}
