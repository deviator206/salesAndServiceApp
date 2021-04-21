// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PaymentInfoModel.java

package com.main.models;


public class PaymentInfoModel
{

    public PaymentInfoModel()
    {
    }

    public String getAdvancePayment()
    {
        return advancePayment;
    }

    public void setAdvancePayment(String advancePayment)
    {
        this.advancePayment = advancePayment;
    }

    public String getFinalAmount()
    {
        return finalAmount;
    }

    public void setFinalAmount(String finalAmount)
    {
        this.finalAmount = finalAmount;
    }

    private String advancePayment;
    private String finalAmount;
}
