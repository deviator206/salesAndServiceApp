// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SearchProductServiceImpl.java

package com.main.impl;

import com.main.models.SerachProductServiceResponse;
import com.main.models.SingleProductModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.main.impl:
//            CreateProductServiceImpl

public class SearchProductServiceImpl extends CreateProductServiceImpl
{

    public SearchProductServiceImpl()
    {
        finalColumn = "";
        queryText = "";
    }

    public SerachProductServiceResponse getSearchResponse()
    {
        return serachProductServiceResponse;
    }

    public void setSearchOn(String queryOnColumn)
    {
        if(queryOnColumn.equals("NAME"))
            finalColumn = COL_BRAND_NAME;
        else
        if(queryOnColumn.equals("MODEL"))
            finalColumn = COL_BRAND_MODEL;
        else
        if(queryOnColumn.equals("SN"))
            finalColumn = COL_SN;
    }

    public void setSearchText(String queryText)
    {
        this.queryText = queryText;
    }

    public void executeSearch()
    {
        getConnection();
        serachProductServiceResponse = new SerachProductServiceResponse();
        try
        {
            serachProductServiceResponse.setStatus(false);
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery((new StringBuilder()).append("select * from ").append(PRODUCT_TABLE).append(" where ").append(finalColumn).append(" LIKE '%").append(queryText).append("' OR ").append(finalColumn).append(" LIKE '").append(queryText).append("%' OR ").append(finalColumn).append(" LIKE '%").append(queryText).append("%'   ").toString());
            List singleProductModelList = new ArrayList();
            SingleProductModel singleProductModel;
            for(; rs.next(); singleProductModelList.add(singleProductModel))
            {
                serachProductServiceResponse.setStatus(true);
                singleProductModel = new SingleProductModel();
                singleProductModel.setId(rs.getInt(1));
                singleProductModel.setName(rs.getString(2));
                singleProductModel.setModel(rs.getString(3));
                singleProductModel.setSn(rs.getString(4));
                singleProductModel.setPrice(rs.getInt(5));
                singleProductModel.setTaxType(rs.getString(6));
            }

            serachProductServiceResponse.setSingleProductModelList(singleProductModelList);
            dbConnection.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    private SerachProductServiceResponse serachProductServiceResponse;
    private String finalColumn;
    private String queryText;
}
