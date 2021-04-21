// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServiceBase.java

package com.main.impl;

import com.main.util.PropertyFileReader;
import java.sql.*;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class ServiceBase
{

    public ServiceBase()
    {
        propertyFileReader = new PropertyFileReader();
    }

    public void getConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection((new StringBuilder()).append("jdbc:mysql://").append(propertyFileReader.propertyInfo.getString("host")).append(":").append(propertyFileReader.propertyInfo.getString("port")).append("/").append(propertyFileReader.propertyInfo.getString("db")).append("").toString(), propertyFileReader.propertyInfo.getString("dbuser"), propertyFileReader.propertyInfo.getString("dbpassword"));
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    private PropertyFileReader propertyFileReader;
    public Connection dbConnection;
}
