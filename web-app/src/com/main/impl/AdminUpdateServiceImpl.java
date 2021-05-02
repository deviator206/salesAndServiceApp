package com.main.impl;

import com.main.models.AdminUpdateServiceModel;
import org.codehaus.jettison.json.JSONException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminUpdateServiceImpl extends  ServiceBase{


    public AdminUpdateServiceModel execute(AdminUpdateServiceModel adminUpdateServiceModel)
            throws SQLException, JSONException{
        getConnection();
        String sql = "";
        int counter = 0;
        PreparedStatement ps;
        String fetchSQL = "SELECT  * FROM repair_invoice_table limit 1";
        Statement stmt;
        ResultSet rs;
        int firstRecord = 0 ;
        AdminUpdateServiceModel adminUpdateServiceModelResponse = new AdminUpdateServiceModel();

        switch (adminUpdateServiceModel.getOperationType()){
            case "_UPDATE_INVOICE_ID_DEFAULT_VALUE":
                stmt = this.dbConnection.createStatement();
                rs = stmt.executeQuery(fetchSQL);
                while (rs.next()) {
                    firstRecord = rs.getInt(1);
                }
                if(firstRecord != 0) {
                    sql = "update  repair_invoice_table set defaultValue = ? where id=?";
                    ps = dbConnection.prepareStatement(sql, 1);
                    ps.setString(1, adminUpdateServiceModel.getDefaultValue());
                    ps.setInt(2,firstRecord);
                    counter = ps.executeUpdate();
                    if(counter>0);
                    {
                        stmt = this.dbConnection.createStatement();
                        rs = stmt.executeQuery(fetchSQL);
                        while (rs.next()) {
                            firstRecord = rs.getInt(1);
                            adminUpdateServiceModelResponse.setId(String.valueOf(firstRecord));
                            adminUpdateServiceModelResponse.setActualInvoiceId(rs.getString("actualInvoiceId"));
                            adminUpdateServiceModelResponse.setDefaultValue(rs.getString("defaultValue"));
                            adminUpdateServiceModelResponse.setVatTinNumber(rs.getString("vat_tin_number"));
                        }
                    }
                }
                break;
        }
        return adminUpdateServiceModelResponse;

    }
}
