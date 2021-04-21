// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProductInfo.java

package com.main.models.sales;


public class ProductInfo
{

    public ProductInfo()
    {
    }

    public String getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(String orderDate)
    {
        this.orderDate = orderDate;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getSn()
    {
        return sn;
    }

    public void setSn(String sn)
    {
        this.sn = sn;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        this.quantity = quantity;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public String getTaxType()
    {
        return taxType;
    }

    public void setTaxType(String taxType)
    {
        this.taxType = taxType;
    }

    public String getTaxValue()
    {
        return taxValue;
    }

    public void setTaxValue(String taxValue)
    {
        this.taxValue = taxValue;
    }

    public String getTaxAmmount()
    {
        return taxAmmount;
    }

    public void setTaxAmmount(String taxAmmount)
    {
        this.taxAmmount = taxAmmount;
    }

    public String getGrandTotal()
    {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal)
    {
        this.grandTotal = grandTotal;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTaxRate()
    {
        return taxRate;
    }

    public void setTaxRate(String taxRate)
    {
        this.taxRate = taxRate;
    }

    private String orderDate;
    private String name;
    private String model;
    private String sn;
    private String quantity;
    private String price;
    private String totalPrice;
    private String taxType;
    private String taxValue;
    private String taxAmmount;
    private String grandTotal;
    private String id;
    private String taxRate;
}
