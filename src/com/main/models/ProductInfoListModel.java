// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ProductInfoListModel.java

package com.main.models;

import java.util.List;

public class ProductInfoListModel
{

    public ProductInfoListModel()
    {
    }

    public List getProductInfoList()
    {
        return productInfoList;
    }

    public void setProductInfoList(List productInfoList)
    {
        this.productInfoList = productInfoList;
    }

    private List productInfoList;
}
