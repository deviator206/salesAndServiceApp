// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GetRepairRequestStatusImpl.java

package com.main.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.main.models.CommentsInfoModel;
import com.main.models.CourierInfoModel;
import com.main.models.CustomerServiceResponse;
import com.main.models.EstimationInfoModel;
import com.main.models.PaymentInfoModel;
import com.main.models.PaymentSingleFinalModel;
import com.main.models.PaymentSingleModel;
import com.main.models.ProductInfoModel;
import com.main.models.RepairRequestResponse;
import com.main.models.SearchRepairServiceResponse;
import com.main.models.UserInfo;

import org.codehaus.jettison.json.JSONException;

// Referenced classes of package com.main.impl:
//            CreateRepairRequestServiceImpl

public class GetRepairRequestStatusImpl extends CreateRepairRequestServiceImpl
{

    public GetRepairRequestStatusImpl()
    {
        queryText = "";
        queryOnColumn = "";
        queryByType = "";
        queryStartFrom = "";
        queryStartTo = "";
        mainResponse = new SearchRepairServiceResponse();
        serviceOrderStatusInput = "";
        totalIncome = 0;
        onlyAdvanceIncome = 0;
        serviceMode = false;
        queryOnColumn = "";
    }

    public void setQueryText(String queryText)
    {
        this.queryText = queryText;
    }

    public void setQueryOnColumn(String queryOnColumn)
    {
        this.queryOnColumn = queryOnColumn;
    }

    public SearchRepairServiceResponse getSearchResult()
    {
        return mainResponse;
    }

    private String getColumnNameString(int customerId)
    {
        String query = "";
        if(queryOnColumn.equalsIgnoreCase("SERVICE_ID"))
            query = (new StringBuilder()).append(" service_order_number LIKE '%").append(queryText).append("%'").toString();
        else
        if(queryOnColumn.equalsIgnoreCase("SERIAL_NUMBER"))
            query = (new StringBuilder()).append(" productSN LIKE '%").append(queryText).append("%'").toString();
        else
        if(queryOnColumn.equalsIgnoreCase("PRODUCT_NAME"))
            query = (new StringBuilder()).append(" productName LIKE '%").append(queryText).append("%'").toString();
        else
        if(queryOnColumn.equalsIgnoreCase("CUSTOMER_PHONE") || queryOnColumn.equalsIgnoreCase("CUSTOMER_NAME"))
            query = (new StringBuilder()).append(" customerId  = ").append(customerId).toString();
        return query;
    }

    private String getCustomerInfoString()
    {
        String query = "";
        if(queryOnColumn.equalsIgnoreCase("CUSTOMER_PHONE"))
            query = (new StringBuilder()).append(" phone LIKE '%").append(queryText).append("%'").toString();
        else
        if(queryOnColumn.equalsIgnoreCase("CUSTOMER_NAME"))
            query = (new StringBuilder()).append(" name LIKE '%").append(queryText).append("%'").toString();
        return query;
    }

    private String createQuery(CustomerServiceResponse customerInfo, int customerId)
    {
        String query = "";
        if(queryByType != null && queryByType.equalsIgnoreCase("BY_QUERY"))
        {
            if((queryText.equalsIgnoreCase("*") || queryText.equalsIgnoreCase("")) && customerId == 0)
            {
                query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append(" ORDER BY id DESC").toString();
                if(serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty())
                    query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append(" where serviceStatus='").append(serviceOrderStatusInput).append("' ORDER BY id DESC ").toString();
            } else
            {
                query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append(" where ").append(getColumnNameString(customerId)).append(" ORDER BY id DESC").toString();
                if(serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty())
                    query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append(" where ").append(getColumnNameString(customerId)).append(" AND  serviceStatus='").append(serviceOrderStatusInput).append("' ORDER BY id DESC").toString();
            }
        } else
        if(queryByType != null && queryByType.equalsIgnoreCase("BY_DATE"))
            query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append(" where service_order_date  BETWEEN '").append(Timestamp.valueOf(queryStartFrom)).append("' AND  '").append(Timestamp.valueOf(queryStartTo)).append("' ORDER BY id DESC").toString();
        else
        if(queryByType == null)
        {
            queryByType = "";
            if((queryText.equalsIgnoreCase("*") || queryText.equalsIgnoreCase("")) && customerId == 0)
            {
                query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append(" ORDER BY id DESC").toString();
                if(serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty())
                    query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append(" where serviceStatus='").append(serviceOrderStatusInput).append("' ORDER BY id DESC ").toString();
            } else
            {
                query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append(" where ").append(getColumnNameString(customerId)).append(" ORDER BY id DESC").toString();
                if(serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty())
                    query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append(" where ").append(getColumnNameString(customerId)).append(" AND  serviceStatus='").append(serviceOrderStatusInput).append("' ORDER BY id DESC").toString();
            }
        } else
        if(queryByType != null)
        {
            queryByType = "";
            if((queryText.equalsIgnoreCase("*") || queryText.equalsIgnoreCase("")) && customerId == 0)
            {
                query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append(" ORDER BY id DESC").toString();
                if(serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty())
                    query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append(" where serviceStatus='").append(serviceOrderStatusInput).append("' ORDER BY id DESC ").toString();
            } else
            {
                query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append(" where ").append(getColumnNameString(customerId)).append(" ORDER BY id DESC").toString();
                if(serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty())
                    query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append(" where ").append(getColumnNameString(customerId)).append(" AND  serviceStatus='").append(serviceOrderStatusInput).append("' ORDER BY id DESC").toString();
            }
        }
        return query;
    }

    public void triggerSearch(CustomerServiceResponse customerInfo, int customerId)
        throws SQLException
    {
        Statement stmt = dbConnection.createStatement();
        String query = "";
        if(serviceMode)
            query = createQueryForReport(customerInfo, customerId);
        else
            query = createQuery(customerInfo, customerId);
        for(ResultSet rs = stmt.executeQuery(query); rs.next(); mainResponse.setStatus(true))
        {
            RepairRequestResponse repairServiceResponse = new RepairRequestResponse();
            if(customerId != 0)
                repairServiceResponse.setCustomerInfo(customerInfo);
            else
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
            CourierInfoModel outwardCourierInfo = new CourierInfoModel();
            outwardCourierInfo.setCourierPhone(rs.getString(43));
            outwardCourierInfo.setCourierName(rs.getString(42));
            outwardCourierInfo.setCourierDocumentNo(rs.getString(44));
            repairServiceResponse.setOutwardCourierInfo(outwardCourierInfo);
            EstimationInfoModel estimation = new EstimationInfoModel();
            if(rs.getString("estimated_delivery_cost") != null)
                estimation.setCost(rs.getString("estimated_delivery_cost"));
            if(rs.getString("estimated_delivery_date") != null)
                estimation.setDate(rs.getString("estimated_delivery_date"));
            repairServiceResponse.setEstimation(estimation);
            CommentsInfoModel commentInfo = new CommentsInfoModel();
            commentInfo.setTech(rs.getString(12));
            commentInfo.setShopkeeper(rs.getString(13));
            commentInfo.setCustomer(rs.getString(14));
            if(rs.getString(40) != null)
                commentInfo.setC_comment((new StringBuilder()).append(rs.getString(39)).append(" : ").append(rs.getString(40)).toString());
            if(rs.getString(35) != null)
                commentInfo.setPp_comment((new StringBuilder()).append(rs.getString(38)).append(" : ").append(rs.getString(35)).toString());
            if(rs.getString(33) != null)
                commentInfo.setCa_comment((new StringBuilder()).append(rs.getString(37)).append(" : ").append(rs.getString(33)).toString());
            if(rs.getString(31) != null)
                commentInfo.setTh_comment((new StringBuilder()).append(rs.getString(36)).append(" : ").append(rs.getString(31)).toString());
            repairServiceResponse.setCommentsInfo(commentInfo);
            repairServiceResponse.setServiceStatus(rs.getString(15));
            repairServiceResponse.setServiceDate(rs.getString(17));
            repairServiceResponse.setDeliveredToCustomerDate(rs.getString(18));
            repairServiceResponse.setServiceNumber(rs.getString(22));
            repairServiceResponse.setVatTinNumber(rs.getString(23));
            repairServiceResponse.setAdvancePayment(rs.getString(27));
            PaymentInfoModel paymentInfoModel = new PaymentInfoModel();
            paymentInfoModel.setAdvancePayment(rs.getString(27));
            if(rs.getString(28) != null && !rs.getString(27).isEmpty())
            {
                totalIncome = totalIncome + rs.getInt(28);
                paymentInfoModel.setFinalAmount(rs.getString(28));
            } else
            if(rs.getString(27) != null && !rs.getString(27).isEmpty())
                onlyAdvanceIncome = onlyAdvanceIncome + Integer.parseInt(rs.getString(27));
            PaymentSingleModel paymentSingleModel = new PaymentSingleModel();
            PaymentSingleFinalModel paymentSingleFinalModel = new PaymentSingleFinalModel();
            if(serviceMode)
            {
                paymentSingleModel.setCash(rs.getString(48));
                paymentSingleModel.setCheqNo(rs.getString(49));
                paymentSingleModel.setBankName(rs.getString(50));
                paymentSingleModel.setIfscCode(rs.getString(51));
                paymentSingleModel.setCheqDate(rs.getString(52));
                paymentSingleModel.setAccountNo(rs.getString(53));
                paymentSingleModel.setCardNo(rs.getString(54));
                paymentSingleModel.setInvoice_id(rs.getString(55));
                paymentSingleModel.setInvoice_tin(rs.getString(56));
                paymentSingleModel.setAmount(rs.getString(57));
                paymentSingleModel.setOnlinePaymentMode(rs.getString(58));
                paymentSingleModel.setOnlineRemark(rs.getString(59));
                paymentSingleModel.setCardExpiryDate(rs.getString(60));
                paymentSingleModel.setCardNetwork(rs.getString(61));
                paymentSingleModel.setCardBank(rs.getString(62));
                paymentSingleFinalModel.setFinal_cash(rs.getString(64));
                paymentSingleFinalModel.setFinal_cheqNo(rs.getString(65));
                paymentSingleFinalModel.setFinal_cheqDate(rs.getString(66));
                paymentSingleFinalModel.setFinal_bankName(rs.getString(67));
                paymentSingleFinalModel.setFinal_cardNo(rs.getString(68));
                paymentSingleFinalModel.setFinal_cardNetwork(rs.getString(69));
                paymentSingleFinalModel.setFinal_onlinePaymentMode(rs.getString(70));
                paymentSingleFinalModel.setFinal_onlineTransactionId(rs.getString(71));
                paymentSingleFinalModel.setFinal_onlineRemark(rs.getString(72));
                paymentSingleFinalModel.setFinal_amount(rs.getString(73));
            }
            repairServiceResponse.setPaymentSingleModel(paymentSingleModel);
            repairServiceResponse.setPaymentSingleFinalModel(paymentSingleFinalModel);
            repairServiceResponse.setPaymentInfo(paymentInfoModel);
            repairServiceResponse.setMetadata(rs.getString("metadata"));
            responseSearchResult.add(repairServiceResponse);
        }

    }

    private String getBasicJoinQuery()
    {
        return " LEFT JOIN PAYMENT_DETAILS_TABLE ON SERVICE_INFO_TABLE.service_order_number = PAYMENT_DETAILS_TABLE.invoice_id";
    }

    private String getJoinBasedOrderByQuery()
    {
        return " ORDER BY SERVICE_INFO_TABLE.id DESC";
    }

    private String createQueryForReport(CustomerServiceResponse customerInfo, int customerId)
    {
        String query = "";
        if(queryByType != null && queryByType.equalsIgnoreCase("BY_QUERY"))
        {
            if((queryText.equalsIgnoreCase("*") || queryText.equalsIgnoreCase("")) && customerId == 0)
            {
                query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append("").append(getBasicJoinQuery()).append("").append(getJoinBasedOrderByQuery()).toString();
                if(serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty())
                    query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append("").append(getBasicJoinQuery()).append(" where serviceStatus='").append(serviceOrderStatusInput).append("").append(getJoinBasedOrderByQuery()).toString();
            } else
            {
                query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append("").append(getBasicJoinQuery()).append(" where ").append(getColumnNameString(customerId)).append("").append(getJoinBasedOrderByQuery()).toString();
                if(serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty())
                    query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append("").append(getBasicJoinQuery()).append(" where ").append(getColumnNameString(customerId)).append(" AND  serviceStatus='").append(serviceOrderStatusInput).append("' ").append(getJoinBasedOrderByQuery()).toString();
            }
        } else
        if(queryByType != null && queryByType.equalsIgnoreCase("BY_DATE"))
            query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append("").append(getBasicJoinQuery()).append(" where serviceStatus='").append(serviceOrderStatusInput).append("' AND service_order_date  BETWEEN '").append(Timestamp.valueOf(queryStartFrom)).append("' AND  '").append(Timestamp.valueOf(queryStartTo)).append("' ").append(getJoinBasedOrderByQuery()).toString();
        else
        if(queryByType == null)
        {
            queryByType = "";
            if((queryText.equalsIgnoreCase("*") || queryText.equalsIgnoreCase("")) && customerId == 0)
            {
                query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append("").append(getBasicJoinQuery()).append("").append(getJoinBasedOrderByQuery()).toString();
                if(serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty())
                    query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append("").append(getBasicJoinQuery()).append(" where serviceStatus='").append(serviceOrderStatusInput).append("'").append(getJoinBasedOrderByQuery()).toString();
            } else
            {
                query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append("").append(getBasicJoinQuery()).append(" where ").append(getColumnNameString(customerId)).append("").append(getJoinBasedOrderByQuery()).toString();
                if(serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty())
                    query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append("").append(getBasicJoinQuery()).append(" where ").append(getColumnNameString(customerId)).append(" AND  serviceStatus='").append(serviceOrderStatusInput).append("' ").append(getJoinBasedOrderByQuery()).toString();
            }
        } else
        if(queryByType != null)
        {
            queryByType = "";
            if((queryText.equalsIgnoreCase("*") || queryText.equalsIgnoreCase("")) && customerId == 0)
            {
                query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append("").append(getBasicJoinQuery()).append("").append(getJoinBasedOrderByQuery()).toString();
                if(serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty())
                    query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append("").append(getBasicJoinQuery()).append(" where serviceStatus='").append(serviceOrderStatusInput).append("'").append(getJoinBasedOrderByQuery()).toString();
            } else
            {
                query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append("").append(getBasicJoinQuery()).append(" where ").append(getColumnNameString(customerId)).append(" ").append(getJoinBasedOrderByQuery()).toString();
                if(serviceOrderStatusInput != null && !serviceOrderStatusInput.isEmpty())
                    query = (new StringBuilder()).append("select * from ").append(SERVICE_INFO_TABLE).append("").append(getBasicJoinQuery()).append(" where ").append(getColumnNameString(customerId)).append(" AND  serviceStatus='").append(serviceOrderStatusInput).append("' ").append(getJoinBasedOrderByQuery()).toString();
            }
        }
        return query;
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
        throws SQLException, JSONException
    {
        getConnection();
        responseSearchResult = new ArrayList();
        int customerId = 0;
        totalIncome = 0;
        onlyAdvanceIncome = 0;
        if(queryOnColumn.equalsIgnoreCase("CUSTOMER_PHONE") || queryOnColumn.equalsIgnoreCase("CUSTOMER_NAME") && !queryText.equalsIgnoreCase(""))
        {
            Statement stmt1 = dbConnection.createStatement();
            String query1 = (new StringBuilder()).append("select * from EMP_CUSTOMER_TABLE where ").append(getCustomerInfoString()).toString();
            CustomerServiceResponse customerInfo;
            for(ResultSet rs1 = stmt1.executeQuery(query1); rs1.next(); triggerSearch(customerInfo, customerId))
            {
                customerInfo = new CustomerServiceResponse();
                customerId = rs1.getInt(1);
                customerInfo.setId(customerId);
                customerInfo.setName(rs1.getString("name"));
                customerInfo.setAddress(rs1.getString("address"));
                customerInfo.setPhone(rs1.getString("phone"));
                customerInfo.setAlternateNo(rs1.getString("alternate_number"));
                customerInfo.setEmail(rs1.getString("email_id"));
                System.out.println((new StringBuilder()).append(" Looping for ").append(customerId).append(" : ###").append(customerInfo.getName()).toString());
            }

        } else
        {
            triggerSearch(null, 0);
        }
        mainResponse.setSearchResults(responseSearchResult);
        mainResponse.setFinalIncome(totalIncome);
        mainResponse.setOnlyAdvanceIncome(onlyAdvanceIncome);
        dbConnection.close();
    }

    public void setStatus(String status)
    {
        serviceOrderStatusInput = status;
    }

    public void setOrderStatusForSearch(String s)
    {
    }

    public void setByType(String byType)
    {
        queryByType = byType;
    }

    public void setStartTo(String startTo)
    {
        queryStartTo = startTo;
    }

    public void setStartFrom(String startFrom)
    {
        queryStartFrom = startFrom;
    }

    public void setServiceMode()
    {
        serviceMode = true;
    }

    private String queryText;
    private String queryOnColumn;
    private String queryByType;
    private String queryStartFrom;
    private String queryStartTo;
    SearchRepairServiceResponse mainResponse;
    public List responseSearchResult;
    private String serviceOrderStatusInput;
    private int totalIncome;
    private int onlyAdvanceIncome;
    private boolean serviceMode;

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    private String metadata;
}
