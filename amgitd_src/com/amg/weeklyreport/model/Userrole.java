package com.amg.weeklyreport.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the userrole database table.
 * 
 */
@Entity
@Table(name = "Userrole")
public class Userrole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int roleID;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	private String roleName;

	private String status;

	public Userrole() {
	}

	public int getRoleID() {
		return this.roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
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

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}