package com.amg.weeklyreport.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.weeklyreport.model.Meeting;
import com.amg.weeklyreport.model.Userdept;
import com.amg.weeklyreport.model.Usergroup;
import com.amg.weeklyreport.model.Userprofile;
import com.amg.weeklyreport.model.Userrole;
import com.amg.weeklyreport.model.Weeklyreport;
import com.amg.weeklyreport.model.WeeklyreportArchive;

public interface WeeklyReportService<T> {

	public void saveUser(Userprofile userprofile);
	public void updateUser(Userprofile userprofile);
	public Userprofile geUserByEmpno(BigDecimal empID);
	public Userrole getRoldById(Integer roleId);
	public List<Userrole> getRoles();
	public Userdept getDeptById(Integer deptId);
	public List<Userdept> getDepts();
	public Usergroup getGroupById(Integer groupId);
	public List<Usergroup> getGroups();
    public Weeklyreport getWeeklyReport(Integer deptId,Integer meetID,String status);
    public List<Weeklyreport> getWeeklyReport(Integer meetID);
    public List<WeeklyreportArchive> getWeeklyReportArch(Integer deptId,Integer meetID,String status, Date fromDate, Date toDate);
	public void saveReport(Weeklyreport weeklyreport);
	public void saveReport(WeeklyreportArchive weeklyreport);
	public void updateReport(Weeklyreport weeklyreport,String stauts);
	public void deleteReport(Weeklyreport weeklyreport);
	public Meeting getMeetingById(Integer meetID);
	public List<Meeting> getMeetings();
	public void saveMeeting(Meeting meeting);
	public void updateMeeting(Meeting meeting);
	public void saveUserdept(Userdept userdept);
	public void updateUserdept(Userdept userdept);
	public void saveUserrole(Userrole userrole);
	public void updateUserrole(Userrole userrole);
	
}
