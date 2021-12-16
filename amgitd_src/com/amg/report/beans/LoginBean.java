//package com.amg.report.beans;
//
//import java.util.ArrayList;
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//import javax.faces.context.FacesContext;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.Date;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.text.SimpleDateFormat;
//
//import javax.faces.application.FacesMessage;
//import javax.faces.bean.SessionScoped;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.amg.report.common.DBUtils;
//import com.amg.report.common.DateUtil;
//import com.amg.report.model.*;
//import com.amg.report.service.IService;
//
//@Component(value = "loginBean")
//@SessionScoped
//public class LoginBean<T> {
//
//	// Login Page Credentials
//	private String userName;
//	private String password;
//	private String hostName;
//
//	String dbfileName = "\\\\192.168.100.176\\ami\\repout\\DAYEND_REPORTS\\CBK_Secret_Summary_Report_5_25112014060035.pdf";
//
//	// Date chosen by the User to view respective Report (For the first login,
//	// System date is used)
//	String reportDate = DateUtil.todaysDateWithDDMMYY();
//
//	private String fileDest;
//
//	// System date that is passed
//	Date date = new Date();
//
//	List<String> filenames = new ArrayList<String>();
//
//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String getHostName() {
//		return hostName;
//	}
//
//	public void setHostName(String hostName) {
//		this.hostName = hostName;
//	}
//
//	public List<VReportAppnsModel> getvReportList() {
//		return vReportList;
//	}
//
//	public void setvReportList(List<VReportAppnsModel> vReportList) {
//		this.vReportList = vReportList;
//	}
//
//	public VReportAppnsModel getVreportModel() {
//		return vreportModel;
//	}
//
//	public void setVreportModel(VReportAppnsModel vreportModel) {
//		this.vreportModel = vreportModel;
//	}
//
//	public List<ITVDayendReportModel> getDayEnd() {
//		return dayEnd;
//	}
//
//	public void setDayEnd(List<ITVDayendReportModel> dayEnd) {
//		this.dayEnd = dayEnd;
//	}
//
//	public List<VDayendReportBean> getDayEndReportList() {
//		return dayEndReportList;
//	}
//
//	public void setDayEndReportList(List<VDayendReportBean> dayEndReportList) {
//		this.dayEndReportList = dayEndReportList;
//	}
//
//	public String getReportDate() {
//		return reportDate;
//	}
//
//	public void setReportDate(String reportDate) {
//		this.reportDate = reportDate;
//	}
//
//	public Date getDate() {
//		return date;
//	}
//
//	public void setDate(Date date) {
//		this.date = date;
//	}
//
//	@Autowired
//	IService<T> iService;
//
//	VReportAppnsModel vreportModel = null;
//	List<VReportAppnsModel> vReportList = null;
//	List<ITVDayendReportModel> dayEnd = null;
//
//	List<VDayendReportBean> dayEndReportList = null;
//
//	// Login Method that processes user credentials to pass/reject user to the report page
//	public String loginAction() throws IOException {
//		
//		
////		vreportModel = iService.getAppnModel(this.getHostName());
//
//		if (vreportModel != null) {
//			try {
//
//				/*
//				 * Date date = Format.parse(reportDate);
//				 * System.out.println(date);
//				 * System.out.println(formatter.format(date));
//				 */
//
//				// this.setReportDate();
//
//				/*
//				 * System.out.println("reportDate :" + reportDate +
//				 * "\t Host Name :" + vreportModel.getConnectionStr());
//				 */
//
//				dayEndReportList = this.getDayendReportList(getUserName()
//						.trim(), getPassword().trim(), vreportModel
//						.getConnectionStr().trim(), vreportModel.getAppnId(),
//						reportDate);
//
//				this.setDayEndReportList(dayEndReportList);
//
//				System.out.println(" dayEndReportList -->"
//						+ dayEndReportList.size());
//
//				String userc = getUserName().toUpperCase();
//				String passc = getPassword().toUpperCase();
//				String hostc = getHostName().toUpperCase();
//				Connection con;
//				con = DBUtils.getConnection(userc, passc, hostc);
//				System.out.println(con);
//				if (con != null) {
//					return "success";
//				} else {
//
//					FacesContext.getCurrentInstance()
//							.addMessage(
//									null,
//									new FacesMessage(
//											FacesMessage.SEVERITY_ERROR,
//											"Invalid Username/Password",
//											"Logon Denied"));
//					return "failure";
//				}
//
//				// Connection conn = DBUtils.getConnection(getUserName().trim(),
//				// getPassword().trim(), getHostName().trim());
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				System.out.println("Message :" + e.getMessage());
//				return "failure";
//			}
//
//		} else {
//			FacesContext.getCurrentInstance().addMessage(
//					null,
//					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!",
//							"Check Hostname"));
//			System.out.println("NO record found");
//			return null;
//		}
//
//	}
//
//	public List<VDayendReportBean> getDayendReportList(String user,
//			String pass, String host, String appid, String date) {
//
//		String userc = user.toUpperCase();
//		String passc = pass.toUpperCase();
//		String hostc = host.toUpperCase();
//
//		List<VDayendReportBean> dayEndReportList = new ArrayList<VDayendReportBean>();
//		Connection conn = null;
//		Statement stmt = null;
//		ResultSet rs = null;
//		String query = null;
//		try {
//			conn = DBUtils.getConnection(userc, passc, hostc);
//			stmt = conn.createStatement();
//
//			query = " SELECT rownum,CNTRYCD ,USER_APPN_ID,DOCDAT,ACYYMM,REPORT_NO,REPORT_DESC,REPORT_FILENAME,"
//					+ " ORAUSER,REMARKS,VERNO,LOGIN_APPN_ID,SRVIPADDR,REPORT_DEST FROM IT_V_DAYEND_REP where orauser='"
//					+ userc
//					+ "'"
//					+ " and user_appn_id='"
//					+ appid
//					+ "' and to_date(docdat,'dd/MM/yy')=to_date('"
//					+ date
//					+ "','dd/MM/yy')";
//
//			rs = stmt.executeQuery(query);
//			while (rs.next()) {
//				VDayendReportBean vreportBean = new VDayendReportBean();
//				System.out.println("REPORT_NO-->" + rs.getString("REPORT_NO"));
//
//				System.out.println("REPORT_FILENAME-->" + fileDest);
//
//				vreportBean.setRowNumber(rs.getString("rownum"));
//				vreportBean.setReport_no(rs.getString("REPORT_NO"));
//				vreportBean.setReport_desc(rs.getString("REPORT_DESC"));
//				vreportBean.setRemarks(rs.getString("REMARKS"));
//				vreportBean.setVerno(rs.getInt("VERNO"));
//				vreportBean.setReport_filename(rs.getString("REPORT_FILENAME"));
//				vreportBean.setReportDestination(rs.getString("REPORT_DEST"));
//				dayEndReportList.add(vreportBean);
//				fileDest = rs.getString("REPORT_DEST");
//
//				// List to store all PDF Filenames
//
//				filenames.add(fileDest);
//				for (int i = 0; i < filenames.size(); i++) {
//					// System.out.println("\nFILENAMES OF PDF IN THE LIST ARE 1 -  "
//					// + filenames.get(i));
//
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			String msg = e.toString();
//			if (msg.startsWith("ORA-01017")) {
//				FacesContext.getCurrentInstance().addMessage(
//						null,
//						new FacesMessage(FacesMessage.SEVERITY_ERROR,
//								"Logon Denied", "Check Username/Password"));
//
//				return null;
//			}
//
//		}
//
//		System.out.println("dayEndReportList :" + dayEndReportList.size());
//
//		return dayEndReportList;
//
//	}
//
//	// Logout Method that invalidates the entered credentials and renders them
//	// null and sets Date back to Current Date
//	public String logout() {
//		this.setUserName("");
//		this.setPassword("");
//		this.setHostName("");
//		this.setDate(new Date());
//		FacesContext.getCurrentInstance().getExternalContext()
//				.invalidateSession();
//		return "/report/login.xhtml?faces-redirect=true";
//
//	}
//
//	// Method that passes control to the same Report page when "Home" is clicked
//	public String home() {
//		return "home";
//
//	}
//
//	// Search Method that fetches Report details when the user chooses preferred
//	// date
//	public String search() {
//
//		try { // User Entered Date
//			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
//
//			String tDate = dateFormat.format(getDate());
//
//			System.out.println("date----date:" + tDate);
//
//			System.out.println("User selected date :" + getDate()
//					+ "\t Host Name :" + vreportModel.getConnectionStr());
//
//			dayEndReportList = this.getDayendReportList(getUserName().trim(),
//					getPassword().trim(), vreportModel.getConnectionStr()
//							.trim(), vreportModel.getAppnId(), tDate);
//
//			this.setDayEndReportList(dayEndReportList);
//
//			System.out.println(" dayEndReportList -->"
//					+ dayEndReportList.size());
//
//			for (Iterator<String> i = getFilenames().iterator(); i.hasNext();) {
//				String item = i.next();
//				System.out.println("Search method Iterator --" + item);
//
//			}
//			return "success";
//		} catch (Exception e) {
//			e.printStackTrace();
//
//			System.out.println("Message :" + e.getMessage());
//			return "failure";
//		}
//	}
//
//	public void downLoad() throws IOException {
//
//		for (int i = 0; i < getFilenames().size(); i++) {
//
//			System.out.println("\nFILENAMES OF PDF IN THE LIST ARE  "
//					+ getFilenames().get(i));
//
//			// List<String> pdfFilenames = new ArrayList<String>(filenames);
//
//			//
//			// System.out.println( + pdfFilenames.get(i));
//
//			final int DEFAULT_BUFFER_SIZE = 10240;
//
//			// String pdf1="C:\\arafordum.pdf";
//			// String pdf1
//			// ="\\\\192.168.100.176\\ami-repout\\DAYEND_REPORTS\\Collection_Followup_Employee_wise_1_17112014081741.pdf";
//
//			FacesContext context = FacesContext.getCurrentInstance();
//			HttpServletResponse response = (HttpServletResponse) context
//					.getExternalContext().getResponse();
//
//			// String pdfpath = "";
//			// for (int i = 0; i < getFilenames().size(); i++) {
//			//
//			// System.out.println("\nFILENAMES OF PDF IN THE LIST ARE  "
//			// + getFilenames().get(i));
//
//			// pdfpath = filenames.get(5);
//
//			// System.out.println("downLoad method filenames --> " + item);
//			// System.out.println("downLoad method filenames size--> " +
//			// getFilenames().size());
//			File file = new File(getFilenames().get(i));
//
//			// System.out.println("downLoad method - pdf filename -> "
//			// + filenames.get(i));
//
//			if (!file.exists()) {
//				response.sendError(HttpServletResponse.SC_NOT_FOUND);
//				return;
//			}
//			response.reset();
//			response.setBufferSize(DEFAULT_BUFFER_SIZE);
//			response.setContentType("application/pdf");
//			response.setHeader("Content-Length", String.valueOf(file.length()));
//			response.setHeader("Content-Disposition", "attachment;filename=\""
//					+ file.getName() + "\"");
//
//			System.out.println("downLoad Method file.getName -> "
//					+ file.getName());
//
//			BufferedInputStream input = null;
//			BufferedOutputStream output = null;
//			try {
//				input = new BufferedInputStream(new FileInputStream(file),
//						DEFAULT_BUFFER_SIZE);
//				output = new BufferedOutputStream(response.getOutputStream(),
//						DEFAULT_BUFFER_SIZE);
//				byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
//				int length;
//				while ((length = input.read(buffer)) > 0) {
//					output.write(buffer, 0, length);
//				}
//			} finally {
//				input.close();
//				output.close();
//
//			}
//			context.responseComplete();
//		}
//	}
//
//	public List<String> getFilenames() {
//		return filenames;
//	}
//
//	public void setFilenames(List<String> filenames) {
//		this.filenames = filenames;
//	}
//
//}
