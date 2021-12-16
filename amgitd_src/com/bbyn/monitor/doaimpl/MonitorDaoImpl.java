package com.bbyn.monitor.doaimpl;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.amg.servicemgmt.common.HibernateDaoSupport;
import com.amg.weeklyreport.model.Userrole;
import com.bbyn.monitor.dao.MonitorDao;
import com.bbyn.monitor.model.SMSMonitor;

@Repository
public class MonitorDaoImpl<T> extends HibernateDaoSupport implements MonitorDao<T> {

	@Override
	public SMSMonitor getSMS(String networkName) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(SMSMonitor.class,
				"smsmonitor");
		criteria.add(Restrictions.eq("serviceProvider", networkName));
		return (SMSMonitor) criteria.uniqueResult();
	}

	@Override
	public List<SMSMonitor> getSMSs() {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(SMSMonitor.class,
				"smsmonitor");
		criteria.add(Restrictions.eq("status", "A"));
		criteria.addOrder(Order.asc("smsMonitorID.msgSentTime"));
		return criteria.list();
	}

	@Override
	public List<SMSMonitor> getSMSs(String serviceProvider) {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(SMSMonitor.class,
				"smsmonitor");
		criteria.add(Restrictions.eq("status", "A"));
		criteria.add(Restrictions.eq("smsMonitorID.serviceProvider", serviceProvider));
		criteria.addOrder(Order.desc("smsMonitorID.msgSentTime"));
		//criteria.setMaxResults(4);
		return criteria.list();
	}
}
