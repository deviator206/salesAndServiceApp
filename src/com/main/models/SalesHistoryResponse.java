// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SalesHistoryResponse.java

package com.main.models;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.main.models:
//            MainResponse

public class SalesHistoryResponse extends MainResponse
{

    public SalesHistoryResponse()
    {
        salesList = new ArrayList();
    }

    public List getSalesList()
    {
        return salesList;
    }

    public void setSalesList(List salesList)
    {
        this.salesList = salesList;
    }

    private List salesList;
}
