// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CustomerSearchResponse.java

package com.main.models;

import java.util.List;

// Referenced classes of package com.main.models:
//            MainResponse

public class CustomerSearchResponse extends MainResponse
{

    public CustomerSearchResponse()
    {
    }

    public List getCustomerServiceResponseList()
    {
        return customerServiceResponseList;
    }

    public void setCustomerServiceResponseList(List customerServiceResponseList)
    {
        this.customerServiceResponseList = customerServiceResponseList;
    }

    private List customerServiceResponseList;
}
