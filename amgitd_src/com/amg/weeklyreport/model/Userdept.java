package com.amg.weeklyreport.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the userdept database table.
 * 
 */
@Entity
@Table(name = "Userdept")
public class Userdept implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int deptID;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private String deptName;
	
	private String emailIds;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	private String status;

	public Userdept() {
	}

	public int getDeptID() {
		return this.deptID;
	}

	public void setDeptID(int deptID) {
		this.deptID = deptID;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmailIds() {
		return emailIds;
	}

	public void setEmailIds(String emailIds) {
		this.emailIds = emailIds;
	}
	
	

}