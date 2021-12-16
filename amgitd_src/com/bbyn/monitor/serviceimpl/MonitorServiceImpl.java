package com.bbyn.monitor.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.bbyn.monitor.dao.MonitorDao;
import com.bbyn.monitor.model.SMSMonitor;
import com.bbyn.monitor.service.MonitorService;

@Service("monitorService")
public class MonitorServiceImpl<T> implements MonitorService<T> {

	
	@Autowired(required = true)
	private MonitorDao<T> iMonitorDao;

	@Transactional
	@Override
	public SMSMonitor getSMS(String networkName) {
		// TODO Auto-generated method stub
		return iMonitorDao.getSMS(networkName);
	}

	@Transactional
	@Override
	public List<SMSMonitor> getSMSs() {
		// TODO Auto-generated method stub
		return iMonitorDao.getSMSs();
	}

	@Transactional
	@Override
	public List<SMSMonitor> getSMSs(String serviceProvider) {
		// TODO Auto-generated method stub
		return iMonitorDao.getSMSs(serviceProvider);
	}

	
}
