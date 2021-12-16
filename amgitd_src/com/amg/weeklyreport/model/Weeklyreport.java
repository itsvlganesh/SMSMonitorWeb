package com.amg.weeklyreport.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the weeklyreport database table.
 * 
 */
@Entity
@Table(name="weeklyreport")
public class Weeklyreport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int reportID;

	
	private int deptID;
	
	
	private String status;
	
	
	private int meetID;

	private BigDecimal createdBY;

	private BigDecimal approverID;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	private String reportdata;

	public Weeklyreport() {
	}

	
	public BigDecimal getApproverID() {
		return this.approverID;
	}

	public void setApproverID(BigDecimal approverID) {
		this.approverID = approverID;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getReportdata() {
		return this.reportdata;
	}

	public void setReportdata(String reportdata) {
		this.reportdata = reportdata;
	}


	

	public int getDeptID() {
		return deptID;
	}


	public void setDeptID(int deptID) {
		this.deptID = deptID;
	}


	public BigDecimal getCreatedBY() {
		return createdBY;
	}


	public void setCreatedBY(BigDecimal createdBY) {
		this.createdBY = createdBY;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public int getMeetID() {
		return meetID;
	}


	public void setMeetID(int meetID) {
		this.meetID = meetID;
	}

	public int getReportID() {
		return reportID;
	}


	public void setReportID(int reportID) {
		this.reportID = reportID;
	}

	
}