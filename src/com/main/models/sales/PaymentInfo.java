// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PaymentInfo.java

package com.main.models.sales;


public class PaymentInfo
{

    public PaymentInfo()
    {
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getCardNetwork()
    {
        return cardNetwork;
    }

    public void setCardNetwork(String cardNetwork)
    {
        this.cardNetwork = cardNetwork;
    }

    public String getCardNumber()
    {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getCardBank()
    {
        return cardBank;
    }

    public void setCardBank(String cardBank)
    {
        this.cardBank = cardBank;
    }

    public String getExpDate()
    {
        return expDate;
    }

    public void setExpDate(String expDate)
    {
        this.expDate = expDate;
    }

    public String getAdditional_cash()
    {
        return additional_cash;
    }

    public void setAdditional_cash(String additional_cash)
    {
        this.additional_cash = additional_cash;
    }

    public String getCheqDate()
    {
        return cheqDate;
    }

    public void setCheqDate(String cheqDate)
    {
        this.cheqDate = cheqDate;
    }

    public String getCheqNo()
    {
        return cheqNo;
    }

    public void setCheqNo(String cheqNo)
    {
        this.cheqNo = cheqNo;
    }

    public String getPayMode()
    {
        return payMode;
    }

    public void setPayMode(String payMode)
    {
        this.payMode = payMode;
    }

    public String getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(String transactionId)
    {
        this.transactionId = transactionId;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    private String amount;
    private String type;
    private String cardNetwork;
    private String cardNumber;
    private String bankName;
    private String cardBank;
    private String expDate;
    private String additional_cash;
    private String cheqDate;
    private String cheqNo;
    private String payMode;
    private String transactionId;
    private String remark;
}
