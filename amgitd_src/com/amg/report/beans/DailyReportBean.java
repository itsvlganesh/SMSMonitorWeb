package com.amg.report.beans;

import java.util.Date;

import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.panel.Panel;
import org.springframework.stereotype.Component;

import oracle.sql.BLOB;


@Component(value = "drBean")
@ViewScoped
public class DailyReportBean {

	private Date reportDate;
	private Date fromTime;
	private Date toTime;
	private String category;
	private String workPlace;
	private String chargeType;
	private String jobDesc;

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Date getFromTime() {
		return fromTime;
	}

	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}

	public Date getToTime() {
		return toTime;
	}

	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getWorkPlace() {
		return workPlace;
	}

	public void setWorkPlace(String workPlace) {
		this.workPlace = workPlace;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	
	private boolean renderPanel;

    public void showPanel() {
        this.renderPanel = true;
    }

    public void hidePanel() {
        this.renderPanel = false;
    }
	
	// public void addPanel(ActionEvent event) {
	// UIComponent component = FacesContext.getCurrentInstance().getViewRoot()
	// .findComponent("dailyreport");
	// if (component != null) {
	// Panel p = new Panel();
	// p.setClosable(true);
	// p.setHeader("Test");
	// p.setVisible(true);
	// component.getChildren().add(p);
	// }
	// }
}
