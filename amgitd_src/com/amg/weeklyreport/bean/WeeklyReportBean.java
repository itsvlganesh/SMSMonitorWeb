package com.amg.weeklyreport.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.PrimeFaces;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IWebContext;


import com.amg.servicemgmt.common.Util;
import com.amg.weeklyreport.model.Meeting;
import com.amg.weeklyreport.model.Userdept;
import com.amg.weeklyreport.model.Usergroup;
import com.amg.weeklyreport.model.Userprofile;
import com.amg.weeklyreport.model.Userrole;
import com.amg.weeklyreport.model.Weeklyreport;
import com.amg.weeklyreport.model.WeeklyreportArchive;
import com.amg.weeklyreport.service.WeeklyReportService;

@Component(value = "weeklyReportBean")
@Scope("session")
public class WeeklyReportBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String text;
	private String today;

	private Integer meetID;
	private List<Meeting> meetList;
	private Map<Integer, String> meetListM = null;

	private Boolean showReport;
	private Boolean save;
	private Boolean approve;
	private Boolean update;

	private Meeting meeting;
	private Weeklyreport weeklyReport;
	private List<Weeklyreport> weeklyReportByMeetID;

	private Userprofile usr;
	private Userrole userrole;

	private String meetName;
	private int number1;
	private Date date1;

	private Integer deptID;
	private String deptName;
	private String emailId;
	private List<Userdept> deptList;

	private Integer deptSID;
	private Map<Integer, String> userDeptListM = null;

	private Date fromDate, toDate;
	private List<WeeklyreportArchive> weeklyReportArchive;

	@Autowired
	WeeklyReportService<T> iWeeklyReportService;

	public WeeklyReportBean() {
		// TODO Auto-generated constructor stub
		Date now = new Date();
		SimpleDateFormat dat = new SimpleDateFormat("dd-MM-yyyy");

		this.setToday(dat.format(now));

		// this.setText("<html><div class='container'><div class='vertical-center'><body align='justify' style='border:1px solid #000000;align:center'><strong><ol><li><span style='font-family: Arial, Verdana; font-size: x-large;'>Weekly Report# 1 - Swift</span></li><li><span style='font-family: Arial, Verdana; font-size: x-large;'>Weekly Report# 2 - PaymentSafe</span></li><li><span style='font-family: Arial, Verdana; font-size: x-large;'>Weekly Report# 3 - Automation</span></li><li><span style='font-family: Arial, Verdana; font-size: x-large;'>Weekly Report# 4 - Automation1</span></li><li><span style='font-family: Arial, Verdana; font-size: x-large;'>Weekly Report# 5 - IMAL</span></li></ol>");

	}

	public void showList() {
		// this.setMeetID(null);
		System.out.println("Show List");
		meetList = iWeeklyReportService.getMeetings();
		meetListM = new HashMap<Integer, String>();
		for (Object obj : meetList) {
			Meeting meeting = new Meeting();
			meeting = (Meeting) obj;
			meetListM.put(meeting.getMeetID(), meeting.getMeetName());
		}

		deptList = iWeeklyReportService.getDepts();
	}

	public void showListReport() {
		System.out.println("Show List Report");
		meetList = iWeeklyReportService.getMeetings();
		meetListM = new HashMap<Integer, String>();
		for (Object obj : meetList) {
			Meeting meeting = new Meeting();
			meeting = (Meeting) obj;
			meetListM.put(meeting.getMeetID(), meeting.getMeetName());
		}

		deptList = iWeeklyReportService.getDepts();

		userDeptListM = new HashMap<Integer, String>();
		for (Object obj : deptList) {
			Userdept userdept = new Userdept();
			userdept = (Userdept) obj;
			userDeptListM.put(userdept.getDeptID(), userdept.getDeptName());
		}
	}

	public String formatData(String data) {
		return "<html><div class='container'><div class='vertical-center'><body>"
				+ data + "</body></div></div></html>";
	}

	public void findMeetView() {
		System.out.println("Find Meet View Id:" + this.getMeetID());
		weeklyReportByMeetID = iWeeklyReportService.getWeeklyReport(this
				.getMeetID());
	}

	public String getMeetName(Integer meetID) {
		String meetName = "";
		try {
			meetName = iWeeklyReportService.getMeetingById(meetID)
					.getMeetName();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return meetName;
	}

	public String getDeptName(Integer deptID) {
		String deptName = "";
		try {
			deptName = iWeeklyReportService.getDeptById(deptID).getDeptName();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return deptName;
	}

	public void findMeet(String show) {
		System.out.println("Find Meet Id:" + this.getMeetID());
		String empNo = (String) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("empNo");

		System.out.println("empNo#:" + empNo);
		usr = iWeeklyReportService.geUserByEmpno(new BigDecimal(empNo));
		userrole = iWeeklyReportService.getRoldById(usr.getRoleID());

		if (usr != null) {
			try {
				System.out.println("Condition1 Userrole!=null:"
						+ userrole.getRoleName());
				System.out.println("Meeting ID:" + this.getMeetID());
				weeklyReport = iWeeklyReportService.getWeeklyReport(
						usr.getDeptID(), this.getMeetID(), "C");
				if (weeklyReport != null) {
					System.out.println("Condition2 Weeklyreport#1 Status:C");
					userrole = iWeeklyReportService
							.getRoldById(usr.getRoleID());

					this.setShowReport(true);
					this.setText(weeklyReport.getReportdata());
					this.setSave(false);
					this.setUpdate(true);
					if (userrole.getRoleName().contains("Appr"))
						this.setApprove(true);
					else
						this.setApprove(false);
					if (show.equalsIgnoreCase("Yes"))
						Util.showMessage("Report Found",
								"An opened report found for the selected meeting.");

				} else {
					System.out.println("Condition3 Weeklyreport#2 Status:A");
					weeklyReport = iWeeklyReportService.getWeeklyReport(
							usr.getDeptID(), this.getMeetID(), "A");
					if (weeklyReport != null) {

						this.setShowReport(true);
						this.setText(weeklyReport.getReportdata());
						this.setSave(false);
						this.setUpdate(true);
						if (userrole.getRoleName().contains("Appr"))
							this.setApprove(true);
						else
							this.setApprove(false);

						Util.showMessage("Report Found",
								"An approved report found for the selected meeting.");
					} else {
						this.setText("");
						this.setShowReport(true);
						this.setSave(true);
						this.setUpdate(false);
						this.setApprove(false);

						Util.showMessage("No Report Found",
								"No report found for the selected meeting.");
					}
				}
			} catch (Exception e) {
				Util.showMessage("Failed", "Failed to Update Password");
				e.printStackTrace();
			}
		} else
			Util.showMessage("Error", "Something went wrong");

	}

	public void clear() {
		this.setMeetID(null);
		this.setText("");
		this.setMeetName("");
		this.setNumber1(0);
		this.setDate1(null);
		this.setDeptName("");
		this.setEmailId("");
		this.setDeptID(null);
		this.setFromDate(null);
		this.setToDate(null);
		this.setDeptSID(null);

	}

	public void createReport() {
		System.out.println("Create Report -data:" + this.getText());

		try {
			Weeklyreport wReport = new Weeklyreport();
			wReport.setCreatedBY(usr.getEmpID());
			wReport.setReportdata(this.getText());
			wReport.setDeptID(usr.getDeptID());
			wReport.setCreated(new Date());
			wReport.setMeetID(this.getMeetID());
			wReport.setStatus("C");
			iWeeklyReportService.saveReport(wReport);
			Util.showMessage("Report Created Successfully", "Success");
			findMeet("No");
		} catch (Exception e) {
			System.out.println("exception:" + e.getMessage());
			Util.showMessage("Failed to insert", "Report not saved.");
		}
	}

	public void searchReport() {
		if(this.getDeptSID() != null ){
			System.out.println("Dept:"+this.getDeptSID());
			weeklyReportArchive = iWeeklyReportService.getWeeklyReportArch(
					this.getDeptSID(), this.getMeetID(), "D",
					this.getFromDate(), this.getToDate());
			
		}else{
		String user = (String) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("empNo");
		if (user != null) {
			Userprofile userr = iWeeklyReportService
					.geUserByEmpno(new BigDecimal(user));
			if (userr != null) {
				weeklyReportArchive = iWeeklyReportService.getWeeklyReportArch(
						userr.getDeptID(), this.getMeetID(), "D",
						this.getFromDate(), this.getToDate());
			}

		}
		}
	}

	public void updateReport() {
		System.out.println("Update Report -data:" + this.getText());
		try {
			weeklyReport.setCreatedBY(usr.getEmpID());
			weeklyReport.setReportdata(this.getText());
			weeklyReport.setModified(new Date());
			// weeklyReport.setMeetID(this.getMeetID());
			// weeklyReport.setStatus("A");
			iWeeklyReportService.updateReport(weeklyReport, "A");
			Util.showMessage("Report Updated Successfully", "Success");
		} catch (Exception e) {
			System.out.println("exception:" + e.getMessage());
			Util.showMessage("Failed to update", "Report not updated.");
		}
	}

	public void updateReport(Integer deptId, Integer meetID, String status) {
		System.out.println("close Report -data dept:" + deptId + " meet:"
				+ meetID + " status:" + status);
		try {
			weeklyReport = iWeeklyReportService.getWeeklyReport(deptId, meetID,
					status);
			if (weeklyReport != null) {
				System.out.println("close report if not null");
				weeklyReport.setModified(new Date());
				WeeklyreportArchive weekArchive = new WeeklyreportArchive();
				weekArchive.setReportdata(weeklyReport.getReportdata());
				weekArchive.setCreatedBY(weeklyReport.getCreatedBY());
				weekArchive.setApproverID(weeklyReport.getApproverID());
				weekArchive.setCreated(weeklyReport.getCreated());
				weekArchive.setModified(weeklyReport.getModified());
				weekArchive.setStatus("D");
				weekArchive.setDeptID(weeklyReport.getDeptID());
				weekArchive.setMeetID(weeklyReport.getMeetID());
				weekArchive.setReportID(weeklyReport.getReportID());
				iWeeklyReportService.saveReport(weekArchive);
				iWeeklyReportService.deleteReport(weeklyReport);
				findMeetView();
				Util.showMessage("Report Updated Successfully", "Success");
			} else
				Util.showMessage("Report failed to save", "Failed");
		} catch (Exception e) {
			System.out.println("exception:" + e.getMessage());
			Util.showMessage("Failed to update", "Report not updated.");
		}
	}

	public void approveReport() {
		System.out.println("Create Report -data:" + this.getText());
		try {
			weeklyReport.setApproverID(usr.getEmpID());
			weeklyReport.setReportdata(this.getText());
			weeklyReport.setModified(new Date());
			// weeklyReport.setMeetID(this.getMeetID());
			iWeeklyReportService.updateReport(weeklyReport, "A");
			Util.showMessage("Report Approved Successfully", "Success");
		} catch (Exception e) {
			System.out.println("exception:" + e.getMessage());
			Util.showMessage("Failed to approve", "Report not approved.");
		}
	}

	public void saveMeet() {
		System.out.println("saveMeet -data:" + this.getMeetName());

		try {
			Meeting meeting = new Meeting();
			meeting.setMeetName(this.getMeetName());
			meeting.setMeetCycle(this.getNumber1());
			meeting.setMeetDate(this.getDate1());
			meeting.setStatus("A");
			iWeeklyReportService.saveMeeting(meeting);
			Util.showMessage("Saved Successfully", "Success");
		} catch (Exception e) {
			System.out.println("exception:" + e.getMessage());
			Util.showMessage("Failed to insert", "Meet not saved.");
		}
	}

	public void saveDept() {
		System.out.println("Save Userdept -data:" + this.getDeptName());

		try {
			Userdept userdept = new Userdept();
			userdept.setDeptName(this.getDeptName());
			userdept.setCreated(new Date());
			userdept.setEmailIds(this.getEmailId());
			userdept.setStatus("A");
			iWeeklyReportService.saveUserdept(userdept);
			Util.showMessage("Saved Successfully", "Success");
		} catch (Exception e) {
			System.out.println("exception:" + e.getMessage());
			Util.showMessage("Failed to insert", "Dept/Team not saved.");
		}
	}

	public void onRowEdit(RowEditEvent event) {

		Userdept dept = (Userdept) event.getObject();
		dept.setModified(new Date());
		dept.setStatus("A");
		try {
			iWeeklyReportService.updateUserdept(dept);
			Util.showMessage("Updated Successfully", "Success");
		} catch (Exception e) {
			Util.showMessage("Update Failed", "Failed to Update Record");
			e.printStackTrace();
		}
	}

	public void onRowEdit2(RowEditEvent event) {

		try {
			Meeting meet = (Meeting) event.getObject();
			meet.setModified(new Date());
			meet.setStatus("A");
			iWeeklyReportService.updateMeeting(meet);
			Util.showMessage("Updated Successfully", "Success");
		} catch (Exception e) {
			Util.showMessage("Update Failed", "Failed to Update Record");
			e.printStackTrace();
		}
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		// RequestContext.getCurrentInstance().showMessageInDialog(msg);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getToday() {
		return today;
	}

	public void setToday(String today) {
		this.today = today;
	}

	public Integer getMeetID() {
		return meetID;
	}

	public void setMeetID(Integer meetID) {
		this.meetID = meetID;
	}

	public Map<Integer, String> getMeetListM() {
		return meetListM;
	}

	public void setMeetListM(Map<Integer, String> meetListM) {
		this.meetListM = meetListM;
	}

	public Boolean getShowReport() {
		return showReport;
	}

	public void setShowReport(Boolean showReport) {
		this.showReport = showReport;
	}

	public Boolean getSave() {
		return save;
	}

	public void setSave(Boolean save) {
		this.save = save;
	}

	public Boolean getApprove() {
		return approve;
	}

	public void setApprove(Boolean approve) {
		this.approve = approve;
	}

	public Boolean getUpdate() {
		return update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}

	public List<Weeklyreport> getWeeklyReportByMeetID() {
		return weeklyReportByMeetID;
	}

	public void setWeeklyReportByMeetID(List<Weeklyreport> weeklyReportByMeetID) {
		this.weeklyReportByMeetID = weeklyReportByMeetID;
	}

	public List<Meeting> getMeetList() {
		return meetList;
	}

	public void setMeetList(List<Meeting> meetList) {
		this.meetList = meetList;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public Weeklyreport getWeeklyReport() {
		return weeklyReport;
	}

	public void setWeeklyReport(Weeklyreport weeklyReport) {
		this.weeklyReport = weeklyReport;
	}

	public Userprofile getUsr() {
		return usr;
	}

	public void setUsr(Userprofile usr) {
		this.usr = usr;
	}

	public Userrole getUserrole() {
		return userrole;
	}

	public void setUserrole(Userrole userrole) {
		this.userrole = userrole;
	}

	public String getMeetName() {
		return meetName;
	}

	public void setMeetName(String meetName) {
		this.meetName = meetName;
	}

	public int getNumber1() {
		return number1;
	}

	public void setNumber1(int number1) {
		this.number1 = number1;
	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public List<Userdept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Userdept> deptList) {
		this.deptList = deptList;
	}

	public Integer getDeptID() {
		return deptID;
	}

	public void setDeptID(Integer deptID) {
		this.deptID = deptID;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<WeeklyreportArchive> getWeeklyReportArchive() {
		return weeklyReportArchive;
	}

	public void setWeeklyReportArchive(
			List<WeeklyreportArchive> weeklyReportArchive) {
		this.weeklyReportArchive = weeklyReportArchive;
	}

	public Integer getDeptSID() {
		return deptSID;
	}

	public void setDeptSID(Integer deptSID) {
		this.deptSID = deptSID;
	}


	public Map<Integer, String> getUserDeptListM() {
		return userDeptListM;
	}

	public void setUserDeptListM(Map<Integer, String> userDeptListM) {
		this.userDeptListM = userDeptListM;
	}

	
	
}
