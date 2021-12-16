package com.amg.weeklyreport.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the usergroup database table.
 * 
 */
@Entity
@Table(name = "Usergroup")
public class Usergroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int groupID;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private String groupName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	private String status;

	public Usergroup() {
	}

	public int getGroupID() {
		return this.groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

}