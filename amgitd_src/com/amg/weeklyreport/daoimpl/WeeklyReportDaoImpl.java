package com.amg.weeklyreport.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.amg.servicemgmt.common.HibernateDaoSupport;
import com.amg.weeklyreport.dao.WeeklyReportDao;
import com.amg.weeklyreport.model.Meeting;
import com.amg.weeklyreport.model.Userdept;
import com.amg.weeklyreport.model.Usergroup;
import com.amg.weeklyreport.model.Userprofile;
import com.amg.weeklyreport.model.Userrole;
import com.amg.weeklyreport.model.Weeklyreport;
import com.amg.weeklyreport.model.WeeklyreportArchive;

@Repository
public class WeeklyReportDaoImpl<T> extends HibernateDaoSupport implements
		WeeklyReportDao {

	@Override
	public Userprofile geUserByEmpno(BigDecimal empID) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Userprofile.class,
				"Userprofile");
		criteria.add(Restrictions.eq("empID", empID));
		// criteria.add(Restrictions.eq("status", "A"));
		return (Userprofile) criteria.uniqueResult();
	}

	@Override
	public Userrole getRoldById(Integer roleId) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Userrole.class,
				"Userrole");
		criteria.add(Restrictions.eq("roleID", roleId));
		return (Userrole) criteria.uniqueResult();
	}

	@Override
	public List<Userrole> getRoles() {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Userrole.class,
				"Userrole");
		criteria.add(Restrictions.eq("status", "A"));
		return criteria.list();
	}

	@Override
	public Userdept getDeptById(Integer deptId) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Userdept.class,
				"Userdept");
		criteria.add(Restrictions.eq("deptID", deptId));
		return (Userdept) criteria.uniqueResult();
	}

	@Override
	public List<Userdept> getDepts() {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Userdept.class,
				"Userdept");
		criteria.add(Restrictions.eq("status", "A"));
		return criteria.list();
	}

	@Override
	public Usergroup getGroupById(Integer groupId) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Usergroup.class,
				"Usergroup");
		criteria.add(Restrictions.eq("groupID", groupId));
		return (Usergroup) criteria.uniqueResult();
	}

	@Override
	public List<Usergroup> getGroups() {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Usergroup.class,
				"Usergroup");
		criteria.add(Restrictions.eq("status", "A"));
		return criteria.list();
	}

	@Override
	public void saveUser(Userprofile userprofile) {
		// TODO Auto-generated method stub
		getSession().save(userprofile);
	}

	@Override
	public void updateUser(Userprofile userprofile) {
		// TODO Auto-generated method stub
		getSession().merge(userprofile);
	}

	@Override
	public Weeklyreport getWeeklyReport(Integer deptId, Integer meetID,
			String status) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Weeklyreport.class,
				"Weeklyreport");
		criteria.add(Restrictions.eq("deptID", deptId));
		criteria.add(Restrictions.eq("meetID", meetID));
		criteria.add(Restrictions.eq("status", status));
		return (Weeklyreport) criteria.uniqueResult();
	}

	@Override
	public void saveReport(Weeklyreport weeklyreport) {
		// TODO Auto-generated method stub
		getSession().save(weeklyreport);
	}

	@Override
	public void saveReport(WeeklyreportArchive weeklyreport) {
		// TODO Auto-generated method stub
		getSession().save(weeklyreport);
	}

	@Override
	public void updateReport(Weeklyreport weeklyreport, String stauts) {
		// TODO Auto-generated method stub
		weeklyreport.setStatus(stauts);
		getSession().merge(weeklyreport);
	}

	@Override
	public void deleteReport(Weeklyreport weeklyreport) {
		// TODO Auto-generated method stub
		getSession().delete(weeklyreport);
	}

	@Override
	public Meeting getMeetingById(Integer meetID) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Meeting.class,
				"Meeting");
		criteria.add(Restrictions.eq("meetID", meetID));
		return (Meeting) criteria.uniqueResult();
	}

	@Override
	public List<Meeting> getMeetings() {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Meeting.class,
				"Meeting");
		criteria.add(Restrictions.eq("status", "A"));
		return criteria.list();
	}

	@Override
	public List<Weeklyreport> getWeeklyReport(Integer meetID) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(Weeklyreport.class,
				"weeklyreport");
		Criterion rest1 = Restrictions.eq("status", "A");
		Criterion rest2 = Restrictions.eq("status", "C");
		criteria.add(Restrictions.eq("meetID", meetID));
		criteria.add(Restrictions.or(rest1, rest2));
		return criteria.list();
	}

	@Override
	public List<WeeklyreportArchive> getWeeklyReportArch(Integer deptId,
			Integer meetID, String status, Date fromDate, Date toDate) {
		Criteria criteria = getSession().createCriteria(
				WeeklyreportArchive.class, "weeklyreport_archive");
		criteria.add(Restrictions.eq("status", "D"));
		criteria.add(Restrictions.eq("meetID", meetID));
		criteria.add(Restrictions.eq("deptID", deptId));
		if (fromDate != null && toDate != null)
			criteria.add(Restrictions.between("created", fromDate, toDate));
		else if (fromDate != null && toDate == null)
			criteria.add(Restrictions.between("created", fromDate, new Date()));
		return criteria.list();
	}

	@Override
	public void saveMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		getSession().save(meeting);
	}

	@Override
	public void updateMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		getSession().merge(meeting);
	}

	@Override
	public void saveUserdept(Userdept userdept) {
		// TODO Auto-generated method stub
		getSession().save(userdept);
	}

	@Override
	public void updateUserdept(Userdept userdept) {
		// TODO Auto-generated method stub
		getSession().merge(userdept);
	}

	@Override
	public void saveUserrole(Userrole userrole) {
		// TODO Auto-generated method stub
		getSession().save(userrole);
	}

	@Override
	public void updateUserrole(Userrole userrole) {
		// TODO Auto-generated method stub
		getSession().merge(userrole);
	}

}
