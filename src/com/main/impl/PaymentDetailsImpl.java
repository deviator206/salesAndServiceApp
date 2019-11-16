// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PaymentDetailsImpl.java

package com.main.impl;

import com.main.models.SalesServiceResponse;
import java.io.PrintStream;
import java.sql.*;
import java.util.HashMap;
import org.codehaus.jettison.json.*;

// Referenced classes of package com.main.impl:
//            ServiceBase

public class PaymentDetailsImpl extends ServiceBase
{

    public PaymentDetailsImpl()
    {
        COL_CASH = "cash";
        COL_INVOICE = "invoice_id";
        COL_TIN = "invoice_tin";
        PAYMENT_TABLE = "PAYMENT_DETAILS_TABLE";
        COL_CARD_NO = "cardNo";
        COL_BANK_NAME = "bankName";
        COL_CHEQ_NO = "cheqNo";
        COL_CHEQ_DATE = "cheqDate";
        COL_AMOUNT = "amount";
    }

    public void setPaymentInfo(JSONArray jsonObject)
    {
        paymentInfoList = jsonObject;
    }

    public void setInvoiceInfo(HashMap invoiceInformation2)
    {
        invoiceInformation = invoiceInformation2;
    }

    public SalesServiceResponse updateFinalPaymentDetails()
    {
        SalesServiceResponse ps = new SalesServiceResponse();
        boolean success = false;
        int count = 0;
        getConnection();
        try
        {
            for(int i = 0; i < paymentInfoList.length(); i++)
            {
                paymentInfo = paymentInfoList.getJSONObject(i);
                PreparedStatement preparedStmt = getQueryBasedOnFinalDeliveryInput();
                int UpdatedCount = preparedStmt.executeUpdate();
                if(UpdatedCount > 0)
                    count++;
            }

            int len = paymentInfoList.length();
            if(count == len)
                success = true;
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
        ps.setStatus(success);
        ps.setCounter(count);
        return ps;
    }

    private PreparedStatement getQueryBasedOnFinalDeliveryInput()
        throws JSONException, SQLException
    {
        String query = "";
        String invoiceID = (String)invoiceInformation.get("invoice");
        int additionalCash = 0;
        if(paymentInfo.has("additional_cash"))
            additionalCash = paymentInfo.getInt("additional_cash");
        PreparedStatement preparedStmt = null;
        if(paymentInfo.getString("type").equalsIgnoreCase("CASH"))
        {
            query = "UPDATE payment_details_table SET final_cash  = ? WHERE invoice_id = ?  ";
            preparedStmt = dbConnection.prepareStatement(query);
            preparedStmt.setFloat(1, paymentInfo.getInt("amount"));
            preparedStmt.setString(2, invoiceID);
        } else
        if(paymentInfo.getString("type").equalsIgnoreCase("CARD"))
        {
            query = "update payment_details_table  SET final_amount  = ?, final_cardNo = ?, final_cardNetwork = ? , final_cash = ? WHERE invoice_id = ? ";
            preparedStmt = dbConnection.prepareStatement(query);
            preparedStmt.setFloat(1, paymentInfo.getInt("amount"));
            preparedStmt.setString(2, paymentInfo.getString("cardNumber"));
            preparedStmt.setString(3, paymentInfo.getString("cardNetwork"));
            preparedStmt.setFloat(4, additionalCash);
            preparedStmt.setString(5, (String)invoiceInformation.get("invoice"));
        } else
        if(paymentInfo.getString("type").equalsIgnoreCase("CHEQ"))
        {
            query = "update payment_details_table  SET final_amount  = ?, final_cheqNo = ?, final_cheqDate = ? , final_bankName = ? , final_cash = ? WHERE invoice_id = ? ";
            preparedStmt = dbConnection.prepareStatement(query);
            preparedStmt.setFloat(1, paymentInfo.getInt("amount"));
            preparedStmt.setString(2, paymentInfo.getString("cheqNo"));
            preparedStmt.setString(3, paymentInfo.getString("cheqDate"));
            preparedStmt.setString(4, paymentInfo.getString("bankName"));
            preparedStmt.setFloat(5, additionalCash);
            preparedStmt.setString(6, (String)invoiceInformation.get("invoice"));
        } else
        if(paymentInfo.getString("type").equalsIgnoreCase("ONLINE"))
        {
            query = "update payment_details_table  SET final_amount  = ?, final_onlinePaymentMode = ?, final_onlineTransactionId = ? , final_onlineRemark = ? , final_cash = ? WHERE invoice_id = ? ";
            preparedStmt = dbConnection.prepareStatement(query);
            preparedStmt.setFloat(1, paymentInfo.getInt("amount"));
            preparedStmt.setString(2, paymentInfo.getString("payMode"));
            preparedStmt.setString(3, paymentInfo.getString("transactionId"));
            preparedStmt.setString(4, paymentInfo.getString("remark"));
            preparedStmt.setFloat(5, additionalCash);
            preparedStmt.setString(6, (String)invoiceInformation.get("invoice"));
        }
        return preparedStmt;
    }

    public SalesServiceResponse updatePaymentDetails()
    {
        SalesServiceResponse ps = new SalesServiceResponse();
        boolean success = false;
        int count = 0;
        getConnection();
        try
        {
            for(int i = 0; i < paymentInfoList.length(); i++)
            {
                paymentInfo = paymentInfoList.getJSONObject(i);
                PreparedStatement preparedStmt = getQueryBasedOnInput();
                int UpdatedCount = preparedStmt.executeUpdate();
                if(UpdatedCount > 0)
                    count++;
            }

            System.out.println("COMPARING");
            System.out.print(count);
            System.out.print("-------");
            System.out.print(paymentInfoList.length());
            int len = paymentInfoList.length();
            if(count == len)
                success = true;
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
        ps.setStatus(success);
        ps.setCounter(count);
        return ps;
    }

    private PreparedStatement getQueryBasedOnInput()
        throws JSONException, SQLException
    {
        String query = "";
        PreparedStatement preparedStmt = null;
        if(paymentInfo.getString("type").equalsIgnoreCase("CASH"))
        {
            query = (new StringBuilder()).append("insert into ").append(PAYMENT_TABLE).append("(").append(COL_INVOICE).append(",").append(COL_TIN).append(",").append(COL_AMOUNT).append(" , cash) values (?,?,?, ?)").toString();
            preparedStmt = dbConnection.prepareStatement(query);
            preparedStmt.setFloat(4, paymentInfo.getInt("additional_cash"));
            preparedStmt.setFloat(3, paymentInfo.getInt("amount"));
            preparedStmt.setString(1, (String)invoiceInformation.get("invoice"));
            preparedStmt.setString(2, (String)invoiceInformation.get("vatTinNumber"));
        } else
        if(paymentInfo.getString("type").equalsIgnoreCase("CARD"))
        {
            query = (new StringBuilder()).append("insert into ").append(PAYMENT_TABLE).append("(").append(COL_CARD_NO).append(",cardNetwork ,").append(COL_INVOICE).append(",").append(COL_TIN).append(",").append(COL_AMOUNT).append(" , cash) values (?,?,?,?,?,?)").toString();
            preparedStmt = dbConnection.prepareStatement(query);
            preparedStmt.setString(1, paymentInfo.getString("cardNumber"));
            preparedStmt.setString(2, paymentInfo.getString("cardNetwork"));
            preparedStmt.setFloat(5, paymentInfo.getInt("amount"));
            preparedStmt.setString(3, (String)invoiceInformation.get("invoice"));
            preparedStmt.setString(4, (String)invoiceInformation.get("vatTinNumber"));
            preparedStmt.setFloat(6, paymentInfo.getInt("additional_cash"));
        } else
        if(paymentInfo.getString("type").equalsIgnoreCase("CHEQ"))
        {
            query = (new StringBuilder()).append("insert into ").append(PAYMENT_TABLE).append("(").append(COL_CHEQ_NO).append(",").append(COL_CHEQ_DATE).append(",").append(COL_BANK_NAME).append(",").append(COL_INVOICE).append(",").append(COL_TIN).append(",").append(COL_AMOUNT).append(" , cash) values (?,?,?,?,?,?,?)").toString();
            preparedStmt = dbConnection.prepareStatement(query);
            preparedStmt.setString(1, paymentInfo.getString("cheqNo"));
            preparedStmt.setString(2, paymentInfo.getString("cheqDate"));
            preparedStmt.setString(3, paymentInfo.getString("bankName"));
            preparedStmt.setFloat(6, paymentInfo.getInt("amount"));
            preparedStmt.setString(4, (String)invoiceInformation.get("invoice"));
            preparedStmt.setString(5, (String)invoiceInformation.get("vatTinNumber"));
            preparedStmt.setFloat(7, paymentInfo.getInt("additional_cash"));
        } else
        if(paymentInfo.getString("type").equalsIgnoreCase("ONLINE"))
        {
            query = (new StringBuilder()).append("insert into ").append(PAYMENT_TABLE).append("(onlinePaymentMode,onlineTransactionId,onlineRemark,").append(COL_INVOICE).append(",").append(COL_TIN).append(",").append(COL_AMOUNT).append(", cash) values (?,?,?,?,?,?, ?)").toString();
            preparedStmt = dbConnection.prepareStatement(query);
            preparedStmt.setString(1, paymentInfo.getString("payMode"));
            preparedStmt.setString(2, paymentInfo.getString("transactionId"));
            preparedStmt.setString(3, paymentInfo.getString("remark"));
            preparedStmt.setFloat(6, paymentInfo.getInt("amount"));
            preparedStmt.setString(4, (String)invoiceInformation.get("invoice"));
            preparedStmt.setString(5, (String)invoiceInformation.get("vatTinNumber"));
            preparedStmt.setFloat(7, paymentInfo.getInt("additional_cash"));
        }
        return preparedStmt;
    }

    private JSONObject paymentInfo;
    private JSONArray paymentInfoList;
    private HashMap invoiceInformation;
    private String COL_CASH;
    private String COL_INVOICE;
    private String COL_TIN;
    private String PAYMENT_TABLE;
    private String COL_CARD_NO;
    private String COL_BANK_NAME;
    private String COL_CHEQ_NO;
    private String COL_CHEQ_DATE;
    private String COL_AMOUNT;
}
