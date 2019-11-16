// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CreateRepairRequestServiceImpl.java

package com.main.impl;

import com.main.models.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import org.codehaus.jettison.json.*;

// Referenced classes of package com.main.impl:
//            ServiceBase, CustomerServiceImpl, GenerateInvoiceOnlyImpl, PaymentDetailsImpl

public class CreateRepairRequestServiceImpl extends ServiceBase
{

    public CreateRepairRequestServiceImpl()
    {
        SERVICE_INFO_TABLE = "SERVICE_INFO_TABLE";
        customerId = "customerId";
        productName = "productName";
        productModel = "productModel";
        productSN = "productSN";
        userId = "userId";
        accessoryList = "accessoryList";
        problemList = "problemList";
        isCourier = "isCourier";
        courierName = "courierName";
        courierPhone = "courierPhone";
        techComment = "techComment";
        shopUserComment = "shopUserComment";
        customerComment = "customerComment";
        serviceStatus = "serviceStatus";
        courierDocumentNo = "courierDocumentNo";
        service_order_date = "service_order_date";
        service_completion_date = "service_completion_date";
        service_order_number = "service_order_number";
        estimated_delivery_cost = "estimated_delivery_cost";
        estimated_delivery_date = "estimated_delivery_date";
        actual_service_cost = "actual_service_cost";
        tentative_quoted_cost = "tentative_quoted_cost";
        tentative_service_completion_date = "tentative_service_completion_date";
        vatTinNumber = "vatTinNumber";
        advancedPayment = "advancedPayment";
        taxName = "taxName";
        taxRate = "taxRate";
        taxValue = "taxValue";
    }

    public String getTentative_service_completion_dateInfo()
    {
        return tentative_service_completion_dateInfo;
    }

    public void setTentative_service_completion_dateInfo(String tentative_service_completion_dateInfo)
    {
        this.tentative_service_completion_dateInfo = tentative_service_completion_dateInfo;
    }

    public void setService_order_dateInfo(String service_order_dateInfo)
    {
        this.service_order_dateInfo = service_order_dateInfo;
    }

    public void setTentative_quoted_costInfo(String tentative_quoted_costInfo)
    {
        this.tentative_quoted_costInfo = tentative_quoted_costInfo;
    }

    public void execute()
        throws InternalError, JSONException, SQLException
    {
        getConnection();
        repairServiceResponse = new RepairServiceResponse();
        repairServiceResponse.setStatus(false);
        if(userInfo.getString("id") == null || userInfo.getString("id").equals(""))
            throw new InternalError("SHOP USER INFORMATION NOT PROVIDED");
        int customerValidID;
        if(customerInfo.getString("id") == null || customerInfo.getString("id").equals(""))
        {
            CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
            customerServiceImpl.setUserName(customerInfo.getString("name").toUpperCase());
            customerServiceImpl.setUserAddress(customerInfo.getString("address").toUpperCase());
            customerServiceImpl.setUserPhone(customerInfo.getString("phone"));
            customerServiceImpl.setUserAlternatePhone(customerInfo.getString("alternateNo"));
            customerServiceImpl.setUserEmail(customerInfo.getString("email"));
            customerServiceImpl.execute();
            customerValidID = customerServiceImpl.getCustomerCreationResponse().getId();
        } else
        {
            customerValidID = Integer.parseInt(customerInfo.getString("id"));
        }
        GenerateInvoiceOnlyImpl generateInvoiceOnlyImpl = new GenerateInvoiceOnlyImpl();
        generateInvoiceOnlyImpl.SALES_INVOICE_TABLE = "REPAIR_INVOICE_TABLE";
        HashMap invoiceInformation = generateInvoiceOnlyImpl.getNewInvoice();
        String sql = (new StringBuilder()).append("insert into ").append(SERVICE_INFO_TABLE).append(" (").append(customerId).append(", ").append(productName).append(", ").append(productModel).append(",").append(productSN).append(",").append(accessoryList).append(",").append(problemList).append(",").append(shopUserComment).append(",").append(customerComment).append(",").append(serviceStatus).append(",").append(tentative_quoted_cost).append(",").append(tentative_service_completion_date).append(",").append(service_order_date).append(",").append(service_order_number).append(",").append(vatTinNumber).append(",").append(isCourier).append(",").append(courierName).append(",").append(courierPhone).append(",").append(courierDocumentNo).append(",").append(userId).append(",").append(advancedPayment).append(",").append(estimated_delivery_cost).append(",").append(estimated_delivery_date).append(") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?,?,?)").toString();
        PreparedStatement ps = dbConnection.prepareStatement(sql, 1);
        int count = 0;
        int paymentInserted = 0;
        int productLoop = 0;
        if(productLoop < productInfo.length())
        {
            JSONObject singleProduct = (JSONObject)productInfo.get(productLoop);
            ps.setInt(1, customerValidID);
            ps.setString(2, singleProduct.getString("name").toUpperCase());
            ps.setString(3, singleProduct.getString("model").toUpperCase());
            ps.setString(4, singleProduct.getString("sn"));
            ps.setString(5, accList);
            ps.setString(6, probList);
            ps.setString(7, shopUserCom);
            ps.setString(8, customerCom);
            ps.setString(9, "NS");
            ps.setString(10, tentative_quoted_costInfo);
            ps.setTimestamp(11, tentative_service_completion_dateInfo == null ? null : Timestamp.valueOf(tentative_service_completion_dateInfo));
            ps.setTimestamp(12, service_order_dateInfo == null ? null : Timestamp.valueOf(service_order_dateInfo));
            ps.setString(13, (String)invoiceInformation.get("invoice"));
            ps.setString(14, (String)invoiceInformation.get("vatTinNumber"));
            ps.setBoolean(15, courierInfo.getBoolean("isCourier"));
            ps.setString(16, courierInfo.getString("courierName"));
            ps.setString(17, courierInfo.getString("courierPhone"));
            ps.setString(18, courierInfo.getString("courierDocumentNo"));
            ps.setString(19, userInfo.getString("id"));
            int totalAdvancedPaid = 0;
            int partialAmount = Integer.parseInt(paymentInfo.getJSONObject(paymentInfo.getString("paymentType")).getString("amount"));
            totalAdvancedPaid = partialAmount;
            if(paymentInfo.has("additional_cash"))
            {
                String additionalCash = paymentInfo.getString("additional_cash");
                if(additionalCash != null)
                {
                    partialAmount = Integer.parseInt(paymentInfo.getJSONObject(paymentInfo.getString("paymentType")).getString("amount"));
                    totalAdvancedPaid = partialAmount + Integer.parseInt(additionalCash);
                }
            }
            ps.setString(20, (new Integer(totalAdvancedPaid)).toString());
            if(estimatedObject.has("cost"))
                ps.setString(21, estimatedObject.getString("cost"));
            else
                ps.setString(21, "NA");
            if(estimatedObject.has("date"))
                ps.setString(22, estimatedObject.getString("date"));
            else
                ps.setString(22, "NA");
            count = ps.executeUpdate();
        }
        paymentInserted = insertPaymentInfo(invoiceInformation);
        if(count > 0 && paymentInserted > 0)
        {
            repairServiceResponse.setStatus(true);
            repairServiceResponse.setRepairReceiptId((String)invoiceInformation.get("invoice"));
            repairServiceResponse.setVatTinNumber((String)invoiceInformation.get("vatTinNumber"));
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
        String paymentType = paymentInfo.getString("paymentType");
        JSONObject singlePaymentInfo = paymentInfo.getJSONObject(paymentType);
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
        int intAddtionalCash = 0;
        if(paymentInfo.has("additional_cash"))
        {
            String additionalCash = paymentInfo.getString("additional_cash");
            if(additionalCash != null && !additionalCash.isEmpty())
                intAddtionalCash = Integer.parseInt(additionalCash);
        }
        newPaymentInfoInput.put("additional_cash", intAddtionalCash);
        paymentDetailsImplInput.put(newPaymentInfoInput);
        paymentDetailsImpl.setPaymentInfo(paymentDetailsImplInput);
        salesServiceResponse = paymentDetailsImpl.updatePaymentDetails();
        return salesServiceResponse.getStatus() ? 1 : 0;
    }

    public void setCustomerInfo(JSONObject jsonObject)
    {
        customerInfo = jsonObject;
    }

    public void setProductInfo(JSONArray jsonArray)
    {
        productInfo = jsonArray;
    }

    public void setCourierInfo(JSONObject jsonObject)
    {
        courierInfo = jsonObject;
    }

    public void setTechnicianInfo(JSONObject jsonObject)
    {
        tecnicianInfo = jsonObject;
    }

    public void setUserInfo(JSONObject jsonObject2)
    {
        userInfo = jsonObject2;
    }

    public void setPaymentInfo(JSONObject jsonObject2)
    {
        paymentInfo = jsonObject2;
    }

    public void setProblemListInfo(String str)
    {
        probList = str;
    }

    public void setAccessoryListInfo(String str)
    {
        accList = str;
    }

    public void setShopUserComment(String str)
    {
        shopUserCom = str;
    }

    public void setCustomerComment(String str)
    {
        customerCom = str;
    }

    public RepairServiceResponse getResponse()
    {
        return repairServiceResponse;
    }

    public void setServiceEstimation(JSONObject jsonObject)
    {
        estimatedObject = jsonObject;
    }

    public String SERVICE_INFO_TABLE;
    public String customerId;
    public String productName;
    public String productModel;
    public String productSN;
    public String userId;
    public String accessoryList;
    public String problemList;
    public String isCourier;
    public String courierName;
    public String courierPhone;
    public String techComment;
    public String shopUserComment;
    public String customerComment;
    public String serviceStatus;
    public String courierDocumentNo;
    public String service_order_date;
    public String service_completion_date;
    public String service_order_number;
    public String estimated_delivery_cost;
    public String estimated_delivery_date;
    public String actual_service_cost;
    public String tentative_quoted_cost;
    public String tentative_service_completion_date;
    public String vatTinNumber;
    public String advancedPayment;
    public String taxName;
    public String taxRate;
    public String taxValue;
    public JSONObject customerInfo;
    public JSONObject courierInfo;
    public JSONObject tecnicianInfo;
    public JSONArray productInfo;
    public String probList;
    public String accList;
    public String shopUserCom;
    public String customerCom;
    public JSONObject userInfo;
    public Timestamp serviceCreationDate;
    public Timestamp serviceCompletionDate;
    public Timestamp tentaiveServiceCompletionDate;
    public String tentaiveServiceCost;
    public String finalServiceCost;
    public JSONObject taxInfo;
    public String serviceInvoiceNumber;
    public JSONObject paymentInfo;
    public String tentative_quoted_costInfo;
    public RepairServiceResponse repairServiceResponse;
    public String tentative_service_completion_dateInfo;
    public String service_order_dateInfo;
    private JSONObject estimatedObject;
}
