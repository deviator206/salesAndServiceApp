// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SerachProductServiceResponse.java

package com.main.models;

import java.util.List;

// Referenced classes of package com.main.models:
//            MainResponse

public class SerachProductServiceResponse extends MainResponse
{

    public SerachProductServiceResponse()
    {
    }

    public List getSingleProductModelList()
    {
        return singleProductModelList;
    }

    public void setSingleProductModelList(List singleProductModelList)
    {
        this.singleProductModelList = singleProductModelList;
    }

    private List singleProductModelList;
}
