// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EstimationInfoModel.java

package com.main.models;


public class EstimationInfoModel
{

    public EstimationInfoModel()
    {
        cost = "";
        date = "";
    }

    public String getCost()
    {
        return cost;
    }

    public void setCost(String cost)
    {
        this.cost = cost;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    private String cost;
    private String date;
}
