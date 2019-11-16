// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CustomerInfo.java

package com.main.models.sales;


public class CustomerInfo
{

    public CustomerInfo()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getAlternateNo()
    {
        return alternateNo;
    }

    public void setAlternateNo(String alternateNo)
    {
        this.alternateNo = alternateNo;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    private String name;
    private String address;
    private String phone;
    private String alternateNo;
    private String email;
}
