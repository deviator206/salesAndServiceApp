// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UpdateRepairRequestStatusImpl.java

package com.main.impl;

import com.main.models.UpdateRepairServiceResponse;
import java.sql.*;
import java.util.Date;
import org.codehaus.jettison.json.*;

// Referenced classes of package com.main.impl:
//            ServiceBase

public class UpdateRepairRequestStatusImpl extends ServiceBase
{

    public UpdateRepairRequestStatusImpl()
    {
    }

    public void execute()
        throws SQLException, JSONException
    {
        getConnection();
        int counter = 0;
        updateRepairServiceResponse = new UpdateRepairServiceResponse();
        int i = 0;
        if(i < updatedProductList.length())
        {
            String sql = "";
            JSONObject jo = updatedProductList.getJSONObject(i);
            if(jo.getString("currentStatus").equalsIgnoreCase("C"))
                sql = "update  SERVICE_INFO_TABLE set serviceStatus = ?,   tech_completed_comment = ? , completed_time = ? where service_order_number = ? AND id = ?";
            else
            if(jo.getString("currentStatus").equalsIgnoreCase("PP"))
                sql = "update  SERVICE_INFO_TABLE set serviceStatus = ? ,   part_pending_comment = ?, part_pending_time = ? where  service_order_number = ? AND id = ?";
            else
            if(jo.getString("currentStatus").equalsIgnoreCase("TH"))
                sql = "update  SERVICE_INFO_TABLE set  serviceStatus = ? ,   technician_handle_comment = ?, technician_handle_time = ? where  service_order_number = ? AND id = ?";
            else
            if(jo.getString("currentStatus").equalsIgnoreCase("CA"))
                sql = "update  SERVICE_INFO_TABLE set serviceStatus = ?,   customer_approval_comment = ?, customer_approval_time = ? where  service_order_number = ? AND id = ?";
            else
            if(jo.getString("currentStatus").equalsIgnoreCase("NS"))
                sql = "update  SERVICE_INFO_TABLE set serviceStatus = ?,   techComment = ?, customer_approval_time = ? where  service_order_number = ? AND id = ?";
            PreparedStatement ps = dbConnection.prepareStatement(sql, 1);
            ps.setString(1, jo.getString("status"));
            ps.setString(2, jo.getString("techComment"));
            Timestamp date = new Timestamp((new Date()).getTime());
            ps.setTimestamp(3, date);
            ps.setString(4, jo.getString("serviceNumber"));
            ps.setString(5, jo.getString("itemId"));
            counter = ps.executeUpdate();
            if(counter > 0)
                updateRepairServiceResponse.setStatus(true);
        }
    }

    public UpdateRepairServiceResponse getSearchResult()
    {
        return updateRepairServiceResponse;
    }

    public void setUpdatedProductList(JSONArray jsonObject)
    {
        updatedProductList = jsonObject;
    }

    private JSONArray updatedProductList;
    private UpdateRepairServiceResponse updateRepairServiceResponse;
}
