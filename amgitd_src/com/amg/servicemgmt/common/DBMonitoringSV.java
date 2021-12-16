package com.amg.servicemgmt.common;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.io.BufferedReader;

import java.io.ByteArrayOutputStream;

import java.io.File;

import java.io.FileFilter;

import java.io.IOException;

import java.io.InputStream;

import java.io.InputStreamReader;

import java.nio.charset.Charset;

import java.sql.CallableStatement;

import java.sql.Connection;

import java.sql.SQLException;

import java.sql.Statement;

import java.text.DateFormat;

import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.time.LocalDate;

import java.util.Arrays;

import java.util.Base64;

import java.util.Calendar;

import java.util.Date;

import java.util.HashMap;

import java.util.LinkedHashMap;

import java.util.Map;

import java.util.Properties;

import java.util.Set;

import java.util.logging.FileHandler;

import java.util.logging.Logger;

import java.util.logging.SimpleFormatter;

import javax.crypto.Cipher;

import javax.crypto.SecretKey;

import javax.crypto.SecretKeyFactory;

import javax.crypto.spec.IvParameterSpec;

import javax.crypto.spec.PBEKeySpec;

import javax.crypto.spec.SecretKeySpec;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

public class DBMonitoringSV {

	public static Logger logger = Logger.getLogger("DRMonitoringSV");

	public static FileHandler fh;

	public static Connection connObj;

	public static String DBCONN = "jdbc:mysql://localhost:3306/SRS";

	public static String DBPass = null;

	public static String DBUser = null;

	public static Map<String, String> STCM, STCMIN = null;

	public static Map<String, String> ZAINM, ZAINMIN = null;

	public static Map<String, String> OOREDOOM, OOREDOOMIN = null;

	public static void main(String[] argv) {

		try {

			// System.out.println(Charset.defaultCharset().name());

			// This block configure the logger with handler and formatter

			String dte = new SimpleDateFormat("dd_MM_yyyy").format(new Date());

			fh = new FileHandler("C:/OBO/MyLogFile" + dte + ".log", true);

			logger.addHandler(fh);

			SimpleFormatter formatter = new SimpleFormatter();

			fh.setFormatter(formatter);

		} catch (SecurityException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		fh.close();

		dbConnection2("Network");

	}

	public static void dbConnection2(String source) {

		try {

			DBUser = "root";

			DBPass = "vasa@123";

			if (source.equalsIgnoreCase("Network")) {

				STCM = new LinkedHashMap<String, String>();

				ZAINM = new LinkedHashMap<String, String>();

				OOREDOOM = new LinkedHashMap<String, String>();

				STCMIN = new LinkedHashMap<String, String>();

				ZAINMIN = new LinkedHashMap<String, String>();

				OOREDOOMIN = new LinkedHashMap<String, String>();

				logger.info("ON DR");

				getDbConnection(DBCONN, DBUser, DBPass, STCM, STCMIN, "STC");
				getDbConnection(DBCONN, DBUser, DBPass, ZAINM, ZAINMIN, "ZAIN");
				getDbConnection(DBCONN, DBUser, DBPass, OOREDOOM, OOREDOOMIN, "OOREDOO");


				// classic way, loop a Map

				for (Map.Entry<String, String> entry : STCM.entrySet()) {

					System.out.println("STCM Key : " + entry.getKey() + " Value : " + entry.getValue());
					

				}
				
				System.out.println("STCM Contains:"+STCM.get("OTP"));

				for (Map.Entry<String, String> entry : STCMIN.entrySet()) {

					System.out.println("STCMIN Key : " + entry.getKey() + " Value : " + entry.getValue());

				}

				for (Map.Entry<String, String> entry : ZAINM.entrySet()) {

					System.out.println("ZAINM Key : " + entry.getKey() + " Value : " + entry.getValue());

				}
				
				for (Map.Entry<String, String> entry : ZAINMIN.entrySet()) {

					System.out.println("ZAINMIN Key : " + entry.getKey() + " Value : " + entry.getValue());

				}

				for (Map.Entry<String, String> entry : OOREDOOM.entrySet()) {

					System.out.println("OOREDOOM Key : " + entry.getKey() + " Value : " + entry.getValue());

				}
				
				for (Map.Entry<String, String> entry : OOREDOOMIN.entrySet()) {

					System.out.println("OOREDOOMIN Key : " + entry.getKey() + " Value : " + entry.getValue());

				}
			}

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

	}

	public static Date getDateStr() {

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DATE, -1);

		Date date = cal.getTime();

		return date;

	}

	public static void getDbConnection(String URL, String user, String pass, Map<String, String> listM,
			Map<String, String> listMIN, String source) {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

//                                    String password = JaspytPasswordEncryptor.getDecryptedString("12345678", "PBEWITHSHA256AND256BITAES-CBC-BC",
//
//                                                            pass);

			connObj = DriverManager.getConnection(URL, user, pass);

			if (connObj != null) {

				logger.info("-----DB connection established-----");

				try {

					// Create a CallableStatement to execute the GetAllNetworkucts()

					// procedure.

					CallableStatement stmt = null;

					// stmt = connObj.prepareCall("{call proc_smsMonitor("+source+") }");
					stmt = connObj.prepareCall("{call proc_smsMonitor(?) }");
					stmt.setString(1, source);

					ResultSet rs = stmt.executeQuery();

					rs = stmt.getResultSet();

					while (rs.next()) {

						System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"

								+ rs.getString(4));

						if (rs.getString(1) != null) {

							logger.info("DB exe was successful");

							DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date date1 = formatter.parse(rs.getString(3));
							Date date2 = formatter.parse(rs.getString(4));
							long diffMin = diff(date1, date2);
							String color = "";
							if (diffMin < 5)
								color = "GREEN";
							else if (diffMin < 10)
								color = "YELLOW";
							else if (diffMin >= 10)
								color = "RED";
							
							System.out.println("rs.getString(1).toUpperCase():"+rs.getString(1).toUpperCase().replaceAll("\\s+","")+"    color:"+color);
							listM.put(rs.getString(1).toUpperCase().replaceAll("\\s+",""), color);

							listMIN.put(rs.getString(1).toUpperCase().replaceAll("\\s+",""), String.valueOf(diffMin));

						} else

							logger.info("API Failed!");

					}

					rs.close();

				} catch (SQLException e) {

					e.printStackTrace();

				}

			}

		} catch (Exception sqlException) {

			sqlException.printStackTrace();

		} finally {

			try {

				connObj.close();

				logger.info("-----DB connection closed-----");

			} catch (SQLException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}

		}

	}

	public static long diff(Date d1, Date RcvdDate) {

		Date d2 = new Date();

		// Get msec from each, and subtract.
		long diff = d2.getTime() - d1.getTime();
		long diffSeconds = diff / 1000;
		long diffMinutes = diff / (60 * 1000);
		long diffHours = diff / (60 * 60 * 1000);
		System.out.println("Time in seconds: " + diffSeconds + " seconds.");
		System.out.println("Time in minutes: " + diffMinutes + " minutes.");
		System.out.println("Time in hours: " + diffHours + " hours.");

		return diffMinutes;

	}

	public static void getDbConnection2(String URL, String user, String pass, Map<String, String> listM,
			Map<String, String> listMIN, String source) {

		try {

			Class.forName("com.");

			String password = JaspytPasswordEncryptor.getDecryptedString("12345678", "PBEWITHSHA256AND256BITAES-CBC-BC",

					pass);

			connObj = DriverManager.getConnection(URL, user, password);

			if (connObj != null) {

				logger.info("-----DB connection established-----");

				try {

					// Create a CallableStatement to execute the GetAllNetworkucts()

					// procedure.

					CallableStatement stmt = null;

					if (source.equalsIgnoreCase("Network"))

						stmt = connObj.prepareCall("{call n_Network()}");

					else if (source.equalsIgnoreCase("dr"))

						stmt = connObj.prepareCall("{call bb_i_DR()}");

					ResultSet rs = stmt.executeQuery();

					rs = stmt.getResultSet();

					while (rs.next()) {

						System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3) + "\t"

								+ rs.getString(4));

						if (rs.getString(1) != null) {

							logger.info("DB exe was successful");

							listM.put(rs.getString(3).toUpperCase(), rs.getString(4).toUpperCase());

							listMIN.put(rs.getString(3).toUpperCase(), rs.getString(2));

						} else

							logger.info("API Failed!");

					}

					rs.close();

				} catch (SQLException e) {

					e.printStackTrace();

				}

			}

		} catch (Exception sqlException) {

			sqlException.printStackTrace();

		} finally {

			try {

				connObj.close();

				logger.info("-----DB connection closed-----");

			} catch (SQLException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}

		}

	}

}
