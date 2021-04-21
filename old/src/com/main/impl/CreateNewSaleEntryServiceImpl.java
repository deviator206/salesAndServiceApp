// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CreateNewSaleEntryServiceImpl.java

package com.main.impl;

import java.io.PrintStream;
import java.sql.*;
import java.util.List;

// Referenced classes of package com.main.impl:
//            ServiceBase

public class CreateNewSaleEntryServiceImpl extends ServiceBase
{

    public CreateNewSaleEntryServiceImpl()
    {
        executionForList = false;
        invoiceId = "";
        vatTin = "";
        productId = "";
        SALE_TABLE = "SALES_ORDER_TABLE";
        invoice_id = "invoice_id";
        customer_id = "customer_id";
        product_id = "product_id";
        product_unit_price = "product_unit_price";
        product_qty = "product_qty";
        product_price_with_qty = "product_price_with_qty";
        tax_type = "tax_type";
        tax_value = "tax_value";
        tax_rate = "tax_rate";
        order_date = "order_date";
        tax_amount = "tax_amount";
        total_amount = "total_amount";
    }

    public void setCustomerId(int id)
    {
        customerId = Integer.toString(id);
    }

    public void setProductList(List newList)
    {
        this.newList = newList;
    }

    public void isProductList(boolean b)
    {
        executionForList = b;
    }

    public void setInvoiceInformation(String str, String str2)
    {
        vatTin = str2;
        invoiceId = str;
    }

    public void execute()
    {
        if(!executionForList);
    }

    public void setProductId(String string)
    {
        productId = string;
    }

    public void setGrandTotal(float str1)
    {
        grandTotal = str1;
    }

    public void setTaxInformation(String taxType, float taxValue, float taxAmmount, String taxRate)
    {
        this.taxType = taxType;
        this.taxValue = taxValue;
        this.taxAmmount = taxAmmount;
        this.taxRate = taxRate;
    }

    public void setQuantityInfo(float quantity, float price, float totalPrice)
    {
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public void executeExistingProduct()
    {
        getConnection();
        String query = (new StringBuilder()).append(" insert into ").append(SALE_TABLE).append(" (").append(invoice_id).append(", ").append(customer_id).append(",").append(product_id).append(",").append(product_unit_price).append(",").append(product_qty).append(",").append(product_price_with_qty).append(",").append(tax_type).append(",").append(tax_rate).append(",").append(order_date).append(",").append(tax_amount).append(",").append(total_amount).append(",").append(tax_value).append(")  values (?, ?, ?,?,?,?,?,?,?,?,?,?)").toString();
        try
        {
            PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
            preparedStmt.setString(1, invoiceId);
            preparedStmt.setString(2, customerId);
            preparedStmt.setString(3, productId);
            preparedStmt.setFloat(4, price);
            preparedStmt.setFloat(5, quantity);
            preparedStmt.setFloat(6, totalPrice);
            preparedStmt.setString(7, taxType);
            preparedStmt.setString(8, taxRate);
            preparedStmt.setTimestamp(9, orderDate);
            preparedStmt.setFloat(10, taxAmmount);
            preparedStmt.setFloat(11, grandTotal);
            preparedStmt.setFloat(12, taxValue);
            int count = preparedStmt.executeUpdate();
            if(count > 0)
                System.out.println(" VALUE INSERTED");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void setOrderDate(Timestamp long1)
    {
        orderDate = long1;
    }

    private String customerId;
    private List newList;
    private boolean executionForList;
    private String invoiceId;
    private String vatTin;
    private String productId;
    private float grandTotal;
    private String taxType;
    private float taxValue;
    private float taxAmmount;
    private String taxRate;
    private float quantity;
    private float price;
    private float totalPrice;
    private String SALE_TABLE;
    private String invoice_id;
    private String customer_id;
    private String product_id;
    private String product_unit_price;
    private String product_qty;
    private String product_price_with_qty;
    private String tax_type;
    private String tax_value;
    private String tax_rate;
    private String order_date;
    private String tax_amount;
    private String total_amount;
    private Timestamp orderDate;
}
