package com.amg.weeklyreport.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.servicemgmt.common.Util;
import com.amg.weeklyreport.model.Userprofile;
import com.amg.weeklyreport.model.Userrole;
import com.amg.weeklyreport.service.WeeklyReportService;

@Component(value = "facesBeanMenu")
@Scope("session")
public class FacesBeanReq<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FacesBeanReq() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	WeeklyReportService iWeeklyReportService;
	private String roleName;
	private boolean master;
	private String name;

	/**
	 * Service Interface
	 */

	public void menu() {
		String user = LoginBean.chkForValidation();
		System.out.println("faces bean menu user:" + user);
		Userprofile userr = iWeeklyReportService.geUserByEmpno(new BigDecimal(
				user));
		if(userr!=null)
		this.setName(userr.getUserName());
		Userrole usrRole = iWeeklyReportService.getRoldById(userr.getRoleID());
		System.out.println("role name:" + usrRole.getRoleName());
		this.setRoleName(usrRole.getRoleName());

		System.out.println("print rolename:" + this.getRoleName());

		if (this.getRoleName().equalsIgnoreCase("Admin")
				|| this.getRoleName().equalsIgnoreCase("Master")) {
			this.setMaster(true);
		} else {
			this.setMaster(false);
		}

	}

	public String logout() {
		System.out.println("logout called");
//		WeeklyReportBean weekBean = new WeeklyReportBean<T>();
//		weekBean.setApprove(false);
//		weekBean.setMeetID(null);
//
//		weekBean.setSave(false);
//		weekBean.setUpdate(false);
//		weekBean.setShowReport(false);
//		weekBean.setText(null);
//
//		weekBean.setUserrole(null);
//		weekBean.setUsr(null);
//		weekBean.setWeeklyReport(null);
//		weekBean.setMeetList(null);
//		weekBean.setMeetListM(null);
//		weekBean.setWeeklyReportByMeetID(null);
		
		UserProfileBean<T> usrB=new UserProfileBean<T>();
		usrB.setEmpNo(null);
		usrB.setUserName(null);
		usrB.setPass(null);
		usrB.setRoleId(null);
		usrB.setDeptId(null);
		usrB.setGroupId(null);
		usrB.setStatus(null);

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.remove("empNo");
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		return "/login/login.xhtml?faces-redirect=true";
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
