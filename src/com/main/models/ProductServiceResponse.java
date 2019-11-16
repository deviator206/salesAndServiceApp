// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProductServiceResponse.java

package com.main.models;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.main.models:
//            MainResponse

public class ProductServiceResponse extends MainResponse
{

    public ProductServiceResponse()
    {
        counter = 0;
        createdProductList = new ArrayList();
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

    public List getCreatedProductList()
    {
        return createdProductList;
    }

    public void setProductCreatedList(List productCreatedList)
    {
        createdProductList = productCreatedList;
    }

    private int counter;
    private List createdProductList;
}
