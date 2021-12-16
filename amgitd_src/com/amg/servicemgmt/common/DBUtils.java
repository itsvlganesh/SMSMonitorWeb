package com.amg.servicemgmt.common;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;

public class DBUtils {
	public DBUtils() {
	}

	private static Connection con = null;

	// Some constants
	public static Connection getConnection(String user, String pass, String host)
			throws ClassNotFoundException, SQLException,
			InstantiationException, IllegalAccessException {
		try {
			String url = "jdbc:oracle:thin:@" + host.trim();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("Connection Successful");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Message :" + e.getMessage());

		}
		return con;

	}

	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	public static void main(String[] args) throws Exception {
		// Connection conn =
		// getConnection("trefmdba1","trefmdba5","192.168.100.155:1542:trefm");

		// Connection conn =
		// getConnection("HASEEB1","trefmdba5","192.168.100.155:1542:trefm");

	}

}
