// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RepairServiceResponse.java

package com.main.models;


// Referenced classes of package com.main.models:
//            MainResponse

public class RepairServiceResponse extends MainResponse
{

    public RepairServiceResponse()
    {
    }

    public void setCreatedProductList(int recordsAffected[])
    {
        recordsCreated = recordsAffected;
    }

    public void setRepairReceiptId(String string)
    {
        repairReceiptId = string;
    }

    public String getRepairReceiptId()
    {
        return repairReceiptId;
    }

    public void setVatTinNumber(String string)
    {
        vatTinNumber = string;
    }

    public String getVatTinNumber()
    {
        return vatTinNumber;
    }

    private int recordsCreated[];
    private String repairReceiptId;
    private String vatTinNumber;
}
