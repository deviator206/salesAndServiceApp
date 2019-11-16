// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SearchRepairServiceResponse.java

package com.main.models;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.main.models:
//            MainResponse

public class SearchRepairServiceResponse extends MainResponse
{

    public SearchRepairServiceResponse()
    {
        searchResults = new ArrayList();
    }

    public List getSearchResults()
    {
        return searchResults;
    }

    public void setSearchResults(List responseSearchResult)
    {
        searchResults = responseSearchResult;
    }

    public int getFinalIncome()
    {
        return finalIncome;
    }

    public void setFinalIncome(int totalIncome)
    {
        finalIncome = totalIncome;
    }

    public void setOnlyAdvanceIncome(int onlyAdvanceIncome)
    {
        this.onlyAdvanceIncome = onlyAdvanceIncome;
    }

    public List searchResults;
    private int finalIncome;
    private int onlyAdvanceIncome;
}
