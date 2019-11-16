// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GetSalesHistoryImpl.java

package com.main.impl;

import com.main.models.SalesHistoryResponse;
import com.main.models.sales.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jettison.json.JSONException;

// Referenced classes of package com.main.impl:
//            ServiceBase

public class GetSalesHistoryImpl extends ServiceBase
{

    public GetSalesHistoryImpl()
    {
        queryText = "";
        queryOnColumn = "";
        byType = "";
        startFrom = "";
        startTo = "";
        salesHistoryResponse = new SalesHistoryResponse();
    }

    public void setQueryText(String queryText)
    {
        this.queryText = queryText;
    }

    public void setQueryOnColumn(String queryOnColumn)
    {
        this.queryOnColumn = queryOnColumn;
    }

    public void setByType(String byType)
    {
        this.byType = byType;
    }

    public void setStartFrom(String startFrom)
    {
        this.startFrom = startFrom;
    }

    public void setStartTo(String startTo)
    {
        this.startTo = startTo;
    }

    public void execute()
        throws SQLException, JSONException
    {
        getConnection();
        Statement stmt = dbConnection.createStatement();
        String query = "";
        if(!byType.isEmpty() && byType.equalsIgnoreCase("BY_QUERY"))
        {
            String s = queryOnColumn.toUpperCase();
            byte byte0 = -1;
            switch(s.hashCode())
            {
            case 1001977420: 
                if(s.equals("CUSTOMER_NAME"))
                    byte0 = 0;
                break;

            case 998586797: 
                if(s.equals("CUSTOMER_PHONE"))
                    byte0 = 1;
                break;

            case -1897977971: 
                if(s.equals("INVOICE_ID"))
                    byte0 = 2;
                break;
            }
            switch(byte0)
            {
            case 0: // '\0'
                query = (new StringBuilder()).append("select * from emp_customer_table t1 inner join sales_order_table t2 on t1.id = t2.customer_id inner join emp_product_table t3 on t2.product_id = t3.id inner join payment_details_table t4 on t2.invoice_id = t4.invoice_id  where t1.name like'%").append(queryText).append("%' order by t2.sale_id DESC ").toString();
                break;

            case 1: // '\001'
                query = (new StringBuilder()).append("select * from emp_customer_table t1 inner join sales_order_table t2 on t1.id = t2.customer_id inner join emp_product_table t3 on t2.product_id = t3.id inner join payment_details_table t4 on t2.invoice_id = t4.invoice_id  where  t1.phone like'%").append(queryText).append("%' or t1.alternate_number like '%").append(queryText).append("%' order by t2.sale_id DESC ").toString();
                break;

            case 2: // '\002'
                query = (new StringBuilder()).append("select * from emp_customer_table t1 inner join sales_order_table t2 on t1.id = t2.customer_id inner join emp_product_table t3 on t2.product_id = t3.id inner join payment_details_table t4 on t2.invoice_id = t4.invoice_id  where   t2.invoice_id like'%").append(queryText).append("%' order by t2.sale_id DESC ").toString();
                break;
            }
        } else
        if(!byType.isEmpty())
            if(!byType.equalsIgnoreCase("BY_DATE"));
        ResultSet resultSet = stmt.executeQuery(query);
        List finalResponseList = new ArrayList();
        SingleSaleInfo finalResponse;
        for(; resultSet.next(); finalResponseList.add(finalResponse))
        {
            finalResponse = new SingleSaleInfo();
            CustomerInfo customerInfo = new CustomerInfo();
            customerInfo.setName(resultSet.getString("name"));
            customerInfo.setAddress(resultSet.getString("address"));
            customerInfo.setPhone(resultSet.getString("phone"));
            customerInfo.setAlternateNo(resultSet.getString("alternate_number"));
            customerInfo.setEmail(resultSet.getString("email_id"));
            finalResponse.setCustomerInfo(customerInfo);
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setAmount(resultSet.getString("amount"));
            if(resultSet.getString("cardNetwork") != null && resultSet.getString("cardNo") != null)
            {
                paymentInfo.setType("card");
                paymentInfo.setCardNetwork(resultSet.getString("cardNetwork"));
                paymentInfo.setCardNumber(resultSet.getString("cardNo"));
                paymentInfo.setBankName(resultSet.getString("final_bankName"));
                paymentInfo.setCardBank(resultSet.getString("final_bankName"));
                paymentInfo.setExpDate(resultSet.getString("cardExpiryDate"));
                if(resultSet.getString("cash") != null)
                    paymentInfo.setAdditional_cash(resultSet.getString("cash"));
            } else
            if(resultSet.getString("cheqNo") != null || resultSet.getString("cheqDate") != null)
            {
                paymentInfo.setType("cheq");
                paymentInfo.setCheqDate(resultSet.getString("cheqDate"));
                paymentInfo.setCheqNo(resultSet.getString("cheqNo"));
                paymentInfo.setBankName(resultSet.getString("bankName"));
                if(resultSet.getString("cash") != null)
                    paymentInfo.setAdditional_cash(resultSet.getString("cash"));
            } else
            if(resultSet.getString("onlinePaymentMode") != null || resultSet.getString("onlineTransactionId") != null)
            {
                paymentInfo.setType("online");
                paymentInfo.setPayMode(resultSet.getString("onlinePaymentMode"));
                paymentInfo.setTransactionId(resultSet.getString("onlineTransactionId"));
                paymentInfo.setRemark(resultSet.getString("onlineRemark"));
                if(resultSet.getString("cash") != null)
                    paymentInfo.setAdditional_cash(resultSet.getString("cash"));
            } else
            {
                paymentInfo.setType("cash");
            }
            finalResponse.setPaymentInfo(paymentInfo);
            ProductInfo productInfo = new ProductInfo();
            productInfo.setName(resultSet.getString("brandName"));
            productInfo.setModel(resultSet.getString("brandModel"));
            productInfo.setSn(resultSet.getString("serialNumber"));
            productInfo.setQuantity(resultSet.getString("product_qty"));
            productInfo.setPrice(resultSet.getString("product_unit_price"));
            productInfo.setTaxAmmount(resultSet.getString("tax_amount"));
            productInfo.setTaxType(resultSet.getString("tax_type"));
            productInfo.setTaxValue(resultSet.getString("tax_value"));
            productInfo.setGrandTotal(resultSet.getString("total_amount"));
            productInfo.setTaxRate(resultSet.getString("tax_rate"));
            productInfo.setOrderDate(resultSet.getString("order_date"));
            List productInfoList = new ArrayList();
            productInfoList.add(productInfo);
            finalResponse.setProductInfo(productInfoList);
            finalResponse.setInvoiceId(resultSet.getString("invoice_id"));
            finalResponse.setInvoiceTin(resultSet.getString("invoice_tin"));
        }

        dbConnection.close();
        salesHistoryResponse.setSalesList(finalResponseList);
    }

    public SalesHistoryResponse getSearchResult()
    {
        return salesHistoryResponse;
    }

    private String queryText;
    private String queryOnColumn;
    private String byType;
    private String startFrom;
    private String startTo;
    private SalesHistoryResponse salesHistoryResponse;
}
