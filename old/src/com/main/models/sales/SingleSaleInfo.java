// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SingleSaleInfo.java

package com.main.models.sales;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.main.models.sales:
//            CustomerInfo, PaymentInfo

public class SingleSaleInfo
{

    public SingleSaleInfo()
    {
        customerInfo = new CustomerInfo();
        paymentInfo = new PaymentInfo();
        productInfo = new ArrayList();
    }

    public String getInvoiceId()
    {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId)
    {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceTin()
    {
        return invoiceTin;
    }

    public void setInvoiceTin(String invoiceTin)
    {
        this.invoiceTin = invoiceTin;
    }

    public CustomerInfo getCustomerInfo()
    {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo)
    {
        this.customerInfo = customerInfo;
    }

    public PaymentInfo getPaymentInfo()
    {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo)
    {
        this.paymentInfo = paymentInfo;
    }

    public List getProductInfo()
    {
        return productInfo;
    }

    public void setProductInfo(List productInfo)
    {
        this.productInfo = productInfo;
    }

    private String invoiceId;
    private String invoiceTin;
    private CustomerInfo customerInfo;
    private PaymentInfo paymentInfo;
    private List productInfo;
}
