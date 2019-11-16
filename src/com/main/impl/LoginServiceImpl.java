// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LoginServiceImpl.java

package com.main.impl;

import com.main.models.LoginResponse;
import java.io.PrintStream;
import java.sql.*;

// Referenced classes of package com.main.impl:
//            ServiceBase

public class LoginServiceImpl extends ServiceBase
{

    public LoginServiceImpl()
    {
        LOGIN_TABLE = "validemporiumuser";
        COL_USER_NAME = "empName";
        COL_USER_PASSWORD = "empPassword";
    }

    public void setUserName(String str)
    {
        userName = str;
    }

    public void setUserPassword(String str)
    {
        userPassword = str;
    }

    public void execute()
    {
        getConnection();
        loginResponse = new LoginResponse();
        try
        {
            loginResponse.setStatus(false);
            Statement stmt = dbConnection.createStatement();
            for(ResultSet rs = stmt.executeQuery((new StringBuilder()).append("select * from ").append(LOGIN_TABLE).append(" where ").append(COL_USER_NAME).append("='").append(userName).append("' AND ").append(COL_USER_PASSWORD).append("='").append(userPassword).append("'").toString()); rs.next(); System.out.println((new StringBuilder()).append(rs.getInt(1)).append("  ").append(rs.getString(2)).append("  ").append(rs.getString(3)).toString()))
            {
                loginResponse.setStatus(true);
                loginResponse.setId(rs.getInt(1));
                loginResponse.setUserName(rs.getString(2));
                loginResponse.setRole(rs.getString(4));
            }

            dbConnection.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public LoginResponse getLoginResponse()
    {
        return loginResponse;
    }

    private String userName;
    private String userPassword;
    private String LOGIN_TABLE;
    private String COL_USER_NAME;
    private String COL_USER_PASSWORD;
    private LoginResponse loginResponse;
}
