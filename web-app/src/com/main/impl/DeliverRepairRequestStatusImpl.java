// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DeliverRepairRequestStatusImpl.java

package com.main.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.main.models.CommentsInfoModel;
import com.main.models.CourierInfoModel;
import com.main.models.CustomerServiceResponse;
import com.main.models.PaymentInfoModel;
import com.main.models.ProductInfoModel;
import com.main.models.RepairRequestResponse;
import com.main.models.RepairServiceResponse;
import com.main.models.SalesServiceResponse;
import com.main.models.SearchRepairServiceResponse;
import com.main.models.UserInfo;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

// Referenced classes of package com.main.impl:
//            ServiceBase, PaymentDetailsImpl

public class DeliverRepairRequestStatusImpl extends ServiceBase
{

    public DeliverRepairRequestStatusImpl()
    {
        outwardCName = "";
        outwardCPhn = "";
        outwardCDocNo = "";
        outwardCBoolean = 0;
    }

    public String getServiceID()
    {
        return serviceID;
    }

    public void setServiceID(String serviceID)
    {
        this.serviceID = serviceID.replaceAll(":", "/");
    }

    public String getServiceItemList()
    {
        return serviceItemList;
    }

    public void setServiceItemList(String serviceItemList)
    {
        this.serviceItemList = serviceItemList;
    }

    public CustomerServiceResponse returnValidCustomerInfo(int customerId)
        throws SQLException
    {
        CustomerServiceResponse customerInfo = new CustomerServiceResponse();
        Statement stmt1 = dbConnection.createStatement();
        String query1 = (new StringBuilder()).append("select * from EMP_CUSTOMER_TABLE where id =").append(customerId).toString();
        for(ResultSet rs1 = stmt1.executeQuery(query1); rs1.next(); customerInfo.setPhone(rs1.getString(4)))
        {
            customerId = rs1.getInt(1);
            customerInfo.setId(customerId);
            customerInfo.setName(rs1.getString("name"));
            customerInfo.setAddress(rs1.getString("address"));
            customerInfo.setPhone(rs1.getString("phone"));
            customerInfo.setAlternateNo(rs1.getString("alternate_number"));
            customerInfo.setEmail(rs1.getString("email_id"));
        }

        return customerInfo;
    }

    public void execute()
        throws SQLException
    {
        getConnection();
        Statement stmt1 = dbConnection.createStatement();
        responseSearchResult = new ArrayList();
        mainResponse = new SearchRepairServiceResponse();
        String query1 = (new StringBuilder()).append("select * from SERVICE_INFO_TABLE where  service_order_number = '").append(serviceID).append("' AND ").append(getServiceOrderActionsList()).toString();
        for(ResultSet rs = stmt1.executeQuery(query1); rs.next(); mainResponse.setStatus(true))
        {
            RepairRequestResponse repairServiceResponse = new RepairRequestResponse();
            repairServiceResponse.setCustomerInfo(returnValidCustomerInfo(rs.getInt(2)));
            List productInfoTempList = new ArrayList();
            ProductInfoModel productInfo = new ProductInfoModel();
            productInfo.setId(rs.getInt(1));
            productInfo.setName(rs.getString(3));
            productInfo.setModel(rs.getString(4));
            productInfo.setSn(rs.getString(5));
            productInfo.setServiceStatus(rs.getString(15));
            productInfo.setService_order_date(rs.getString(17));
            productInfoTempList.add(productInfo);
            repairServiceResponse.setProductInfo(productInfoTempList);
            UserInfo userInfo = new UserInfo();
            userInfo.setId(rs.getInt(6));
            repairServiceResponse.setUserInfo(userInfo);
            repairServiceResponse.setAccessoryList(rs.getString(7));
            repairServiceResponse.setProblemList(rs.getString(8));
            CourierInfoModel courierInfo = new CourierInfoModel();
            courierInfo.setCourierPhone(rs.getString(11));
            courierInfo.setCourierName(rs.getString(10));
            courierInfo.setCourierDocumentNo(rs.getString(16));
            repairServiceResponse.setCourierInfo(courierInfo);
            CommentsInfoModel commentInfo = new CommentsInfoModel();
            commentInfo.setTech(rs.getString(12));
            commentInfo.setShopkeeper(rs.getString(13));
            commentInfo.setCustomer(rs.getString(14));
            repairServiceResponse.setCommentsInfo(commentInfo);
            repairServiceResponse.setServiceStatus(rs.getString(15));
            repairServiceResponse.setServiceDate(rs.getString(17));
            repairServiceResponse.setServiceNumber(rs.getString(22));
            repairServiceResponse.setVatTinNumber(rs.getString(23));
            repairServiceResponse.setAdvancePayment(rs.getString(27));
            PaymentInfoModel paymentInfoModel = new PaymentInfoModel();
            paymentInfoModel.setAdvancePayment(rs.getString(27));
            repairServiceResponse.setPaymentInfo(paymentInfoModel);
            responseSearchResult.add(repairServiceResponse);
        }

        mainResponse.setSearchResults(responseSearchResult);
        dbConnection.close();
    }

    private String getServiceOrderActionsList()
    {
        String query = "";
        String orderActions[] = serviceItemList.split(",");
        for(int i = 0; i < orderActions.length; i++)
        {
            if(i > 0)
                query = query.concat(" OR ");
            query = query.concat((new StringBuilder()).append(" id =  ").append(orderActions[i]).toString());
        }

        return query;
    }

    public SearchRepairServiceResponse getSearchResult()
    {
        return mainResponse;
    }

    public void setFinalPayment(JSONObject jsonObject)
    {
        finalPaymentInfo = jsonObject;
    }

    public void executeFinalPayment()
        throws SQLException, JSONException
    {
        getConnection();
        String sql = (new StringBuilder()).append("update SERVICE_INFO_TABLE SET " +
                "finalPayment = ?, " +
                "serviceStatus = ? , " +
                "isOutwardCourier =?, " +
                "outwardCourierDocumentNo = ?,  " +
                "outwardCourierName = ?, " +
                "outwardCourierPhone = ? , " +
                "service_completion_date = ? ," +
                "metadata = ? " +
                "where service_order_number = '").append(finalServiceNumber).append("' ").toString();
        PreparedStatement ps = dbConnection.prepareStatement(sql, 1);
        ps.setString(1, finalPaymentInfo.getJSONObject(finalPaymentInfo.getString("paymentType")).getString("amount"));
        ps.setString(2, "DTC");
        ps.setInt(3, outwardCBoolean);
        ps.setString(4, outwardCDocNo);
        ps.setString(5, outwardCName);
        ps.setString(6, outwardCPhn);
        ps.setString(7, finalDeliveryDate);
        ps.setString(8,metadata);
        int count = ps.executeUpdate();
        HashMap returnedHash = new HashMap();
        JSONObject invoiceInformation = fetchInvoiceInformation();
        returnedHash.put("invoice", invoiceInformation.getString("invoice"));
        returnedHash.put("vatTinNumber", invoiceInformation.getString("vatTinNumber"));
        int paymentInserted = insertPaymentInfo(returnedHash);
        finalPaymentDone = new RepairServiceResponse();
        if(count > 0)
        {
            finalPaymentDone.setStatus(true);
            finalPaymentDone.setRepairReceiptId(invoiceInformation.getString("invoice"));
            finalPaymentDone.setVatTinNumber(invoiceInformation.getString("vatTinNumber"));
        }
        dbConnection.close();
    }

    private int insertPaymentInfo(HashMap invoiceInformation)
        throws JSONException
    {
        SalesServiceResponse salesServiceResponse = new SalesServiceResponse();
        salesServiceResponse.setStatus(false);
        PaymentDetailsImpl paymentDetailsImpl = new PaymentDetailsImpl();
        paymentDetailsImpl.setInvoiceInfo(invoiceInformation);
        JSONArray paymentDetailsImplInput = new JSONArray();
        JSONObject newPaymentInfoInput = new JSONObject();
        String paymentType = finalPaymentInfo.getString("paymentType");
        JSONObject singlePaymentInfo = finalPaymentInfo.getJSONObject(paymentType);
        Iterator paymentKeys = singlePaymentInfo.keys();
        do
        {
            if(!paymentKeys.hasNext())
                break;
            String key = (String)paymentKeys.next();
            Object value = singlePaymentInfo.get(key);
            if(value instanceof Integer)
                newPaymentInfoInput.put(key, ((Integer)value).intValue());
            else
            if(value instanceof String)
                newPaymentInfoInput.put(key, (String)value);
        } while(true);
        newPaymentInfoInput.put("type", paymentType);
        if(finalPaymentInfo.has("additional_cash"))
            newPaymentInfoInput.put("additional_cash", finalPaymentInfo.getInt("additional_cash"));
        paymentDetailsImplInput.put(newPaymentInfoInput);
        paymentDetailsImpl.setPaymentInfo(paymentDetailsImplInput);
        salesServiceResponse = paymentDetailsImpl.updateFinalPaymentDetails();
        return salesServiceResponse.getStatus() ? 1 : 0;
    }

    private JSONObject fetchInvoiceInformation()
        throws SQLException, JSONException
    {
        Statement stmt = dbConnection.createStatement();
        String query = "";
        query = (new StringBuilder()).append("select * from SERVICE_INFO_TABLE where service_order_number = '").append(finalServiceNumber).append("'").toString();
        ResultSet rs = stmt.executeQuery(query);
        JSONObject invoiceInfo = new JSONObject();
        if(rs.next())
        {
            invoiceInfo.put("invoice", rs.getString(22));
            invoiceInfo.put("vatTinNumber", rs.getString(26));
        }
        return invoiceInfo;
    }

    public RepairServiceResponse getInvoiceResult()
    {
        return finalPaymentDone;
    }

    public void setServiceNumber(String string)
    {
        finalServiceNumber = string;
    }

    public void setOutwardCourierInfo(JSONObject jsonObject)
        throws JSONException
    {
        if(jsonObject.getBoolean("isCourier"))
        {
            outwardCName = jsonObject.getString("courierName");
            outwardCPhn = jsonObject.getString("courierPhone");
            outwardCDocNo = jsonObject.getString("courierDocumentNo");
            outwardCBoolean = 1;
        }
    }

    public void setFinalDeliveryDate(String finalDate)
    {
        finalDeliveryDate = finalDate;
    }

    private String serviceID;
    private String serviceItemList;
    private List responseSearchResult;
    private SearchRepairServiceResponse mainResponse;
    private JSONObject finalPaymentInfo;
    private RepairServiceResponse finalPaymentDone;
    private String finalServiceNumber;
    private String outwardCName;
    private String outwardCPhn;
    private String outwardCDocNo;
    private int outwardCBoolean;
    private String finalDeliveryDate;

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    private String metadata;
}
