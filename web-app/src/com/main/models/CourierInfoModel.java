// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CourierInfoModel.java

package com.main.models;


public class CourierInfoModel
{

    public CourierInfoModel()
    {
    }

    public String getCourierName()
    {
        return courierName;
    }

    public void setCourierName(String courierName)
    {
        this.courierName = courierName;
    }

    public String getCourierPhone()
    {
        return courierPhone;
    }

    public void setCourierPhone(String courierPhone)
    {
        this.courierPhone = courierPhone;
    }

    public String getCourierDocumentNo()
    {
        return courierDocumentNo;
    }

    public void setCourierDocumentNo(String courierDocumentNo)
    {
        this.courierDocumentNo = courierDocumentNo;
    }

    private String courierName;
    private String courierPhone;
    private String courierDocumentNo;
}
