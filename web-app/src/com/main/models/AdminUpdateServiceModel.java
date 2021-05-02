package com.main.models;

public class AdminUpdateServiceModel {
    private String operationType;

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActualInvoiceId() {
        return actualInvoiceId;
    }

    public void setActualInvoiceId(String actualInvoiceId) {
        this.actualInvoiceId = actualInvoiceId;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getVatTinNumber() {
        return vatTinNumber;
    }

    public void setVatTinNumber(String vatTinNumber) {
        this.vatTinNumber = vatTinNumber;
    }

    private String id;
    private String actualInvoiceId;
    private String defaultValue;
    private String vatTinNumber;
}
