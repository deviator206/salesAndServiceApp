package com.main.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class GenerateInvoiceOnlyImpl extends ServiceBase {

	public String SALES_INVOICE_TABLE = "SALES_INVOICE_TABLE";
	public String COL_DEFAULT = "defaultValue";
	public String COL_INVOICE_FINAL = "actualInvoiceId";
	public String COL_VAT = "vat_tin_number";

	public HashMap<String, String> getNewInvoice() {
		HashMap<String, String> returnedHash = new HashMap<>();
		String invoiceStr = "";
		String vatTinNumber = "";
		Statement stmt;
		this.getConnection();
		try {

			stmt = this.dbConnection.createStatement();
			int counter = 0;
			String actualInvoiceId = "";
			ResultSet rs = stmt.executeQuery("select count(*) from " + this.SALES_INVOICE_TABLE);
			while (rs.next()) {
				counter = rs.getInt(1);
			}

			System.out.println(counter);
			// increment counter
			counter++;

			ResultSet rs1 = stmt.executeQuery(
					"select " + this.COL_DEFAULT + "," + this.COL_VAT + " from " + this.SALES_INVOICE_TABLE);
			while (rs1.next()) {
				actualInvoiceId = rs1.getString(1);
				vatTinNumber = rs1.getString(2);
				break;
			}
			StringBuilder sb = new StringBuilder(String.valueOf(actualInvoiceId));
			sb.append(counter);

			invoiceStr = sb.toString();

			if (!invoiceStr.isEmpty()) {
				String query = " insert into " + this.SALES_INVOICE_TABLE + " (" + this.COL_INVOICE_FINAL
						+ ") values (?)";
				PreparedStatement preparedStmt = this.dbConnection.prepareStatement(query);
				preparedStmt.setString(1, invoiceStr);
				int count = preparedStmt.executeUpdate();
				this.dbConnection.close();
				if (count > 0) {
					returnedHash.put("invoice", invoiceStr);
					returnedHash.put("vatTinNumber", vatTinNumber);
					return returnedHash;
				}
			} else {
				this.dbConnection.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public HashMap<String, String> deleteInvoice(String invoiceStr) {
		this.getConnection();
		try {
			String query = " delete from " + this.SALES_INVOICE_TABLE + " where " + this.COL_INVOICE_FINAL
					+ " = ?";
			PreparedStatement preparedStmt = this.dbConnection.prepareStatement(query);
			preparedStmt.setString(1, invoiceStr);
			int count = preparedStmt.executeUpdate();
			this.dbConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
