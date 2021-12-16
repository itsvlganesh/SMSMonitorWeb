package com.amg.weeklyreport.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the userprofile database table.
 * 
 */
@Entity
@Table(name = "Userprofile")
public class Userprofile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private BigDecimal empID;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private int deptID;

	private int groupID;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	private String password;

	private int roleID;

	private String status;

	private String userName;

	public Userprofile() {
	}

	public BigDecimal getEmpID() {
		return this.empID;
	}

	public void setEmpID(BigDecimal empID) {
		this.empID = empID;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getDeptID() {
		return this.deptID;
	}

	public void setDeptID(int deptID) {
		this.deptID = deptID;
	}

	public int getGroupID() {
		return this.groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public Date getModified() {
		return this.modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleID() {
		return this.roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}