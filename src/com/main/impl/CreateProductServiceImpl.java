// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CreateProductServiceImpl.java

package com.main.impl;

import com.main.models.ProductServiceResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jettison.json.*;

// Referenced classes of package com.main.impl:
//            ServiceBase

public class CreateProductServiceImpl extends ServiceBase
{

    public CreateProductServiceImpl()
    {
        PRODUCT_TABLE = "EMP_PRODUCT_TABLE";
        COL_BRAND_NAME = "brandName";
        COL_BRAND_MODEL = "brandModel";
        COL_SN = "serialNumber";
        COL_PRICE = "price";
        COL_TAXT_TYPE = "tax_type";
    }

    public void setBrandName(String s)
    {
    }

    public void setProductList(JSONArray productList)
    {
        this.productList = productList;
    }

    public void executeCreation()
    {
        getConnection();
        productServiceResponse = new ProductServiceResponse();
        try
        {
            productServiceResponse.setStatus(false);
            productServiceResponse.setCounter(0);
            List productCreatedList = new ArrayList();
            for(int i = 0; i < productList.length(); i++)
            {
                JSONObject singleProduct = (JSONObject)productList.get(i);
                if(singleProduct.getString("isNew").toLowerCase() != "true")
                    continue;
                String query = (new StringBuilder()).append(" insert into ").append(PRODUCT_TABLE).append(" (").append(COL_BRAND_NAME).append(", ").append(COL_BRAND_MODEL).append(",").append(COL_SN).append(",").append(COL_PRICE).append(",").append(COL_TAXT_TYPE).append(") values (?, ?, ?,?,?)").toString();
                PreparedStatement preparedStmt = dbConnection.prepareStatement(query, 1);
                preparedStmt.setString(1, singleProduct.getString("name"));
                preparedStmt.setString(2, singleProduct.getString("model"));
                preparedStmt.setString(3, singleProduct.getString("sn"));
                preparedStmt.setInt(4, singleProduct.getInt("price"));
                preparedStmt.setString(5, singleProduct.getString("taxType"));
                int count = preparedStmt.executeUpdate();
                if(count > 0)
                {
                    productServiceResponse.setStatus(true);
                    productServiceResponse.incrementCounter();
                }
                for(ResultSet rs = preparedStmt.getGeneratedKeys(); rs != null && rs.next(); productCreatedList.add(Integer.toString(rs.getInt(1))));
            }

            productServiceResponse.setProductCreatedList(productCreatedList);
            dbConnection.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void createNewProductAndUpdateMap()
    {
    }

    public ProductServiceResponse getResponse()
    {
        return productServiceResponse;
    }

    private JSONArray productList;
    private ProductServiceResponse productServiceResponse;
    public String PRODUCT_TABLE;
    public String COL_BRAND_NAME;
    public String COL_BRAND_MODEL;
    public String COL_SN;
    private String COL_PRICE;
    private String COL_TAXT_TYPE;
}
