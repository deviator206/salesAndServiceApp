// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RepairRequestResponse.java

package com.main.models;

import java.util.List;

// Referenced classes of package com.main.models:
//            MainResponse, PaymentSingleFinalModel, CommentsInfoModel, PaymentSingleModel, 
//            CourierInfoModel, CustomerServiceResponse, PaymentInfoModel, EstimationInfoModel, 
//            UserInfo

public class RepairRequestResponse extends MainResponse
{

    public RepairRequestResponse()
    {
    }

    public String getAdvancePayment()
    {
        return advancePayment;
    }

    public void setAdvancePayment(String advancePayment)
    {
        this.advancePayment = advancePayment;
    }

    public String getVatTinNumber()
    {
        return vatTinNumber;
    }

    public void setVatTinNumber(String vatTinNumber)
    {
        this.vatTinNumber = vatTinNumber;
    }

    public String getAccessoryList()
    {
        return accessoryList;
    }

    public void setAccessoryList(String accessoryList)
    {
        this.accessoryList = accessoryList;
    }

    public String getProblemList()
    {
        return problemList;
    }

    public void setProblemList(String problemList)
    {
        this.problemList = problemList;
    }

    public String getServiceNumber()
    {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber)
    {
        this.serviceNumber = serviceNumber;
    }

    public String getServiceStatus()
    {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus)
    {
        this.serviceStatus = serviceStatus;
    }

    public String getServiceDate()
    {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate)
    {
        this.serviceDate = serviceDate;
    }

    public CommentsInfoModel getCommentsInfo()
    {
        return commentsInfo;
    }

    public void setCommentsInfo(CommentsInfoModel commentInfo)
    {
        commentsInfo = commentInfo;
    }

    public CourierInfoModel getCourierInfo()
    {
        return courierInfo;
    }

    public void setCourierInfo(CourierInfoModel courierInfo)
    {
        this.courierInfo = courierInfo;
    }

    public CustomerServiceResponse getCustomerInfo()
    {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerServiceResponse customerInfo)
    {
        this.customerInfo = customerInfo;
    }

    public PaymentInfoModel getPaymentInfo()
    {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfoModel paymentInfo)
    {
        this.paymentInfo = paymentInfo;
    }

    public List getProductInfo()
    {
        return productInfo;
    }

    public void setProductInfo(List productInfo)
    {
        this.productInfo = productInfo;
    }

    public UserInfo getUserInfo()
    {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo)
    {
        this.userInfo = userInfo;
    }

    public String getTotalIncome()
    {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome)
    {
        this.totalIncome = totalIncome;
    }

    public CourierInfoModel getOutwardCourierInfo()
    {
        return outwardCourierInfo;
    }

    public void setOutwardCourierInfo(CourierInfoModel outwardCourierInfo)
    {
        this.outwardCourierInfo = outwardCourierInfo;
    }

    public String getDeliveredToCustomerDate()
    {
        return deliveredToCustomerDate;
    }

    public void setDeliveredToCustomerDate(String deliveredToCustomerDate)
    {
        this.deliveredToCustomerDate = deliveredToCustomerDate;
    }

    public PaymentSingleModel getPaymentSingleModel()
    {
        return paymentSingleModel;
    }

    public void setPaymentSingleModel(PaymentSingleModel paymentSingleModel)
    {
        this.paymentSingleModel = paymentSingleModel;
    }

    public PaymentSingleFinalModel getPaymentSingleFinalModel()
    {
        return paymentSingleFinalModel;
    }

    public void setPaymentSingleFinalModel(PaymentSingleFinalModel paymentSingleFinalModel)
    {
        this.paymentSingleFinalModel = paymentSingleFinalModel;
    }

    public EstimationInfoModel getEstimation()
    {
        return estimation;
    }

    public void setEstimation(EstimationInfoModel estimation)
    {
        this.estimation = estimation;
    }

    private String accessoryList;
    private String vatTinNumber;
    private String advancePayment;
    private PaymentSingleFinalModel paymentSingleFinalModel;
    private String problemList;
    private String serviceNumber;
    private String serviceStatus;
    private String serviceDate;
    private String deliveredToCustomerDate;
    private String totalIncome;
    private CommentsInfoModel commentsInfo;
    private PaymentSingleModel paymentSingleModel;
    private CourierInfoModel courierInfo;
    private CourierInfoModel outwardCourierInfo;
    private CustomerServiceResponse customerInfo;
    private PaymentInfoModel paymentInfo;
    private EstimationInfoModel estimation;
    private List productInfo;
    private UserInfo userInfo;

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    private String metadata;
}
