package com.bbyn.monitor.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "smsmonitor")
public class SMSMonitor {

	@Column(name = "msgBody")
	String msgBody;

	@Column(name = "status")
	String status;

	@EmbeddedId
	private SMSMonitorID smsMonitorID;

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SMSMonitorID getSmsMonitorID() {
		return smsMonitorID;
	}

	public void setSmsMonitorID(SMSMonitorID smsMonitorID) {
		this.smsMonitorID = smsMonitorID;
	}

}
