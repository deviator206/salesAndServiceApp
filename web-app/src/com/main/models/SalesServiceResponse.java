// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SalesServiceResponse.java

package com.main.models;

import java.util.HashMap;
import java.util.List;

// Referenced classes of package com.main.models:
//            MainResponse, CustomerServiceResponse

public class SalesServiceResponse extends MainResponse
{

    public SalesServiceResponse()
    {
        counter = 0;
        invoiceId = "";
        vatTinNumber = "";
    }

    public List getProductServiceResponse()
    {
        return newProductServiceResponse;
    }

    public void setProductServiceResponse(List list)
    {
        newProductServiceResponse = list;
    }

    public CustomerServiceResponse getCustomerServiceResponse()
    {
        return customerServiceResponse;
    }

    public void setCustomerServiceResponse(CustomerServiceResponse customerServiceResponse)
    {
        this.customerServiceResponse = customerServiceResponse;
    }

    public int getCounter()
    {
        return counter;
    }

    public String getVatTinNumber()
    {
        return vatTinNumber;
    }

    public void setVatTinNumber(String vatTinNumber)
    {
        this.vatTinNumber = vatTinNumber;
    }

    public void setInvoiceId(String invoiceInfo)
    {
        invoiceId = invoiceInfo;
    }

    public String getInvoiceId()
    {
        return invoiceId;
    }

    public void setCounter(int i)
    {
        counter = i;
    }

    public void incrementCounter()
    {
        counter++;
    }

    public int getCounterValue()
    {
        return counter;
    }

    public void setInvoiceInfo(HashMap invoiceInformation)
    {
        invoiceId = (String)invoiceInformation.get("invoice");
        vatTinNumber = (String)invoiceInformation.get("vatTinNumber");
    }

    private int counter;
    private String invoiceId;
    private String vatTinNumber;
    private CustomerServiceResponse customerServiceResponse;
    private List newProductServiceResponse;
}
