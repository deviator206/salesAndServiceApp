package com.main.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.main.models.UpdateRepairServiceResponse;

public class UpdateRepairRequestStatusImpl extends ServiceBase {
	private JSONArray updatedProductList;
	private UpdateRepairServiceResponse updateRepairServiceResponse;

	public void execute() throws SQLException, JSONException {
		this.getConnection();

		int counter = 0;
		updateRepairServiceResponse = new UpdateRepairServiceResponse();

		for (int i = 0; i < updatedProductList.length(); i++) {
			String sql = "";
			JSONObject jo = updatedProductList.getJSONObject(i);
			PreparedStatement ps;

			if (jo.getString("currentStatus").equalsIgnoreCase("C")) {
				sql = "update  SERVICE_INFO_TABLE set serviceStatus = ?,   tech_completed_comment = ? , completed_time = ? where service_order_number = ? AND id = ?";
			} else if (jo.getString("currentStatus").equalsIgnoreCase("PP")) {
				sql = "update  SERVICE_INFO_TABLE set serviceStatus = ? ,   part_pending_comment = ?, part_pending_time = ? where  service_order_number = ? AND id = ?";

			} else if (jo.getString("currentStatus").equalsIgnoreCase("TH")) {
				sql = "update  SERVICE_INFO_TABLE set  serviceStatus = ? ,   technician_handle_comment = ?, technician_handle_time = ? where  service_order_number = ? AND id = ?";
			} else if (jo.getString("currentStatus").equalsIgnoreCase("CA")) {
				sql = "update  SERVICE_INFO_TABLE set serviceStatus = ?,   customer_approval_comment = ?, customer_approval_time = ? where  service_order_number = ? AND id = ?";

			} else if (jo.getString("currentStatus").equalsIgnoreCase("NS")) {
				sql = "update  SERVICE_INFO_TABLE set serviceStatus = ?,   techComment = ?, customer_approval_time = ? where  service_order_number = ? AND id = ?";
			}

			ps = this.dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, jo.getString("status"));
			ps.setString(2, jo.getString("techComment"));

			java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
			ps.setTimestamp(3, date);

			ps.setString(4, jo.getString("serviceNumber"));
			ps.setString(5, jo.getString("itemId"));

			counter = ps.executeUpdate();
			if (counter > 0) {
				updateRepairServiceResponse.setStatus(true);
			}
			break;
		}
	}

	public UpdateRepairServiceResponse getSearchResult() {
		return this.updateRepairServiceResponse;
	}

	public void setUpdatedProductList(JSONArray jsonObject) {
		this.updatedProductList = jsonObject;

	}

}
