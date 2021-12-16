package com.amg.weeklyreport.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the meetings database table.
 * 
 */
@Entity
@Table(name="meetings")
public class Meeting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int meetID;

	private int meetCycle;

	@Temporal(TemporalType.TIMESTAMP)
	private Date meetDate;

	private String meetName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;

	private String status;

	public Meeting() {
	}

	public int getMeetID() {
		return this.meetID;
	}

	public void setMeetID(int meetID) {
		this.meetID = meetID;
	}

	public int getMeetCycle() {
		return this.meetCycle;
	}

	public void setMeetCycle(int meetCycle) {
		this.meetCycle = meetCycle;
	}

	public Date getMeetDate() {
		return this.meetDate;
	}

	public void setMeetDate(Date meetDate) {
		this.meetDate = meetDate;
	}

	public String getMeetName() {
		return this.meetName;
	}

	public void setMeetName(String meetName) {
		this.meetName = meetName;
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