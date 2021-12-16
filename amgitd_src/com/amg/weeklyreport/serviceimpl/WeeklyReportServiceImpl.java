package com.amg.weeklyreport.serviceimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.weeklyreport.dao.WeeklyReportDao;
import com.amg.weeklyreport.model.Meeting;
import com.amg.weeklyreport.model.Userdept;
import com.amg.weeklyreport.model.Usergroup;
import com.amg.weeklyreport.model.Userprofile;
import com.amg.weeklyreport.model.Userrole;
import com.amg.weeklyreport.model.Weeklyreport;
import com.amg.weeklyreport.model.WeeklyreportArchive;
import com.amg.weeklyreport.service.WeeklyReportService;



@Service("weeklyReportService")
public class WeeklyReportServiceImpl<T> implements WeeklyReportService {

	/**
	 * Incorporate dao interface
	 */
	@Autowired(required = true)
	private WeeklyReportDao weeklyReportDao;



	@Transactional
	@Override
	public Userprofile geUserByEmpno(BigDecimal empID) {
		// TODO Auto-generated method stub
		return weeklyReportDao.geUserByEmpno(empID);
	}

	@Transactional
	@Override
	public Userrole getRoldById(Integer roleId) {
		// TODO Auto-generated method stub
		return weeklyReportDao.getRoldById(roleId);
	}
	
	@Transactional
	@Override
	public List<Userrole> getRoles() {
		// TODO Auto-generated method stub
		return weeklyReportDao.getRoles();
	}

	@Transactional
	@Override
	public Userdept getDeptById(Integer deptId) {
		// TODO Auto-generated method stub
		return weeklyReportDao.getDeptById(deptId);
	}

	@Transactional
	@Override
	public List<Userdept> getDepts() {
		// TODO Auto-generated method stub
		return weeklyReportDao.getDepts();
	}

	@Transactional
	@Override
	public Usergroup getGroupById(Integer groupId) {
		// TODO Auto-generated method stub
		return weeklyReportDao.getGroupById(groupId);
	}

	@Transactional
	@Override
	public List<Usergroup> getGroups() {
		// TODO Auto-generated method stub
		return weeklyReportDao.getGroups();
	}

	@Transactional
	@Override
	public void saveUser(Userprofile userprofile) {
		// TODO Auto-generated method stub
		weeklyReportDao.saveUser(userprofile);
	}

	@Transactional
	@Override
	public void updateUser(Userprofile userprofile) {
		// TODO Auto-generated method stub
		weeklyReportDao.updateUser(userprofile);
	}

	@Transactional
	@Override
	public Weeklyreport getWeeklyReport(Integer deptId,Integer meetID, String status) {
		// TODO Auto-generated method stub
		return weeklyReportDao.getWeeklyReport(deptId,meetID, status);
	}

	@Transactional
	@Override
	public void saveReport(Weeklyreport weeklyreport) {
		// TODO Auto-generated method stub
		weeklyReportDao.saveReport(weeklyreport);
	}
	
	@Transactional
	@Override
	public void saveReport(WeeklyreportArchive weeklyreport) {
		// TODO Auto-generated method stub
		weeklyReportDao.saveReport(weeklyreport);
	}

	@Transactional
	@Override
	public void updateReport(Weeklyreport weeklyreport,String stauts) {
		// TODO Auto-generated method stub
		weeklyReportDao.updateReport(weeklyreport,stauts);
	}
	
	@Transactional
	@Override
	public void deleteReport(Weeklyreport weeklyreport) {
		// TODO Auto-generated method stub
		weeklyReportDao.deleteReport(weeklyreport);
	}

	@Transactional
	@Override
	public Meeting getMeetingById(Integer meetID) {
		// TODO Auto-generated method stub
		return weeklyReportDao.getMeetingById(meetID);
	}

	@Transactional
	@Override
	public List<Meeting> getMeetings() {
		// TODO Auto-generated method stub
		return weeklyReportDao.getMeetings();
	}

	@Transactional
	@Override
	public List<Weeklyreport> getWeeklyReport(Integer meetID) {
		// TODO Auto-generated method stub
		return weeklyReportDao.getWeeklyReport(meetID);
	}

	@Transactional
	@Override
	public void saveMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		weeklyReportDao.saveMeeting(meeting);
	}

	@Transactional
	@Override
	public void updateMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		weeklyReportDao.updateMeeting(meeting);
	}

	@Transactional
	@Override
	public void saveUserdept(Userdept userdept) {
		// TODO Auto-generated method stub
		weeklyReportDao.saveUserdept(userdept);
	}

	@Transactional
	@Override
	public void updateUserdept(Userdept userdept) {
		// TODO Auto-generated method stub
		weeklyReportDao.updateUserdept(userdept);
	}

	@Transactional
	@Override
	public void saveUserrole(Userrole userrole) {
		// TODO Auto-generated method stub
		weeklyReportDao.saveUserrole(userrole);
	}

	@Transactional
	@Override
	public void updateUserrole(Userrole userrole) {
		// TODO Auto-generated method stub
		weeklyReportDao.updateUserrole(userrole);
	}

	@Transactional
	@Override
	public List<WeeklyreportArchive> getWeeklyReportArch(Integer deptId, Integer meetID,
			String status, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
		return weeklyReportDao.getWeeklyReportArch(deptId, meetID, status, fromDate, toDate);
	}

	

}
