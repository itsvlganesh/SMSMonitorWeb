package com.bbyn.monitor.dao;

import java.util.*;
import com.bbyn.monitor.model.SMSMonitor;


public interface MonitorDao<T> {
	
	public SMSMonitor getSMS(String networkName);
	public List<SMSMonitor> getSMSs();
	public List<SMSMonitor> getSMSs(String serviceProvider);


}