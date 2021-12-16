package com.bbyn.monitor.service;

import java.util.List;

import com.bbyn.monitor.model.SMSMonitor;

public interface MonitorService<T> {

	public SMSMonitor getSMS(String networkName);
	public List<SMSMonitor> getSMSs();
	public List<SMSMonitor> getSMSs(String serviceProvider);
}
