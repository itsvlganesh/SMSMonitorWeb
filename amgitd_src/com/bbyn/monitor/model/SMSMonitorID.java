package com.bbyn.monitor.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class SMSMonitorID<T> implements Serializable {

	
	@Column(name = "msgSentTime")
	@Temporal(TemporalType.TIMESTAMP)
	Date msgSentTime;
	
	@Column(name = "serviceProvider")
	String serviceProvider;
	
	@Column(name = "mobileNum")
	String mobileNum;
	
	@Column(name = "queue")
	String queue;

	public Date getMsgSentTime() {
		return msgSentTime;
	}

	public void setMsgSentTime(Date msgSentTime) {
		this.msgSentTime = msgSentTime;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}
	
	
	
	
}
