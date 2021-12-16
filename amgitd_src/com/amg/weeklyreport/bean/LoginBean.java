package com.amg.weeklyreport.bean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.panel.Panel;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.amg.servicemgmt.common.Util;
import com.amg.servicemgmt.util.CypherSecurityImpl;
import com.amg.servicemgmt.util.iCypherSecurity;
import com.amg.weeklyreport.model.Userprofile;
import com.amg.weeklyreport.model.Userrole;
import com.amg.weeklyreport.service.WeeklyReportService;

@Component(value = "loginBean")
@SessionScoped
public class LoginBean<T> {

	private BigDecimal empNo;
	private String password;
	


	@Autowired
	WeeklyReportService iWeeklyReportService;

	iCypherSecurity iCS = new CypherSecurityImpl();

	// Logout Method that invalidates the entered credentials and renders them
	// null when "Logout" is clicked
	public String logout() {

		this.setEmpNo(null);
		this.setPassword("");
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("empNo", null);
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		return "/login/login.xhtml?faces-redirect=true";

	}

	// Method that passes control to the Welcome page when "Home" is clicked
	public String home() {
		return "/common/welcome.xhtml?faces-redirect=true";

	}

	public String checkAvailablity() {
		try {
			if (this.getEmpNo() != null) {
				System.out.println("Check Availability1");
				Userprofile usr = iWeeklyReportService.geUserByEmpno(this
						.getEmpNo());
				
				iCypherSecurity iCS = new CypherSecurityImpl();
				String s = iCS.encodePassword(this.getPassword().trim(), this
						.getEmpNo().toString());
				if (usr != null && usr.getPassword().equals(s)
						&& usr.getStatus().equals("A")) {
					System.out.println("Check Availability2");
					FacesContext.getCurrentInstance().getExternalContext()
							.getSessionMap()
							.put("empNo", this.getEmpNo().toString());
					return "/common/welcome.xhtml?faces-redirect=true";
				} else if (usr != null && !usr.getPassword().equals(s)) {
					// if empno is empty or not found
					System.out.println("Check Availability3");
					Util.showMessage("Invalid user/employee number", "failed");
					return "/login/login.xhtml?faces-redirect=true";
				} else if (usr != null && usr.getStatus().equals("D")) {
					// if empno is empty or not found
					System.out.println("Check Availability4");
					Util.showMessage("Failure",
							"Your account is not active, please contact admin.");
					return "/login/login.xhtml?faces-redirect=true";
				} else
					Util.showMessage("Failure", "User Not Found");
			}

		} catch (Exception e) {
			Util.showMessage("Invalid user/employee number", "failed");

		}
		return "/login/login.xhtml?faces-redirect=true";
	}

	public static String chkForValidation() {
		String user = (String) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("empNo");

		if (user == null) {
			System.out.println("username1 check validation:" + user);
			FacesContext.getCurrentInstance().getExternalContext()
					.invalidateSession();
			FacesContext fc = FacesContext.getCurrentInstance();
			NavigationHandler nh = fc.getApplication().getNavigationHandler();
			nh.handleNavigation(fc, null,
					"/login/login.xhtml?faces-redirect=true");
			return user;

		}
		return user;

	}

	public boolean chkRole() {
		String user = chkForValidation();
		if (user != null) {
			System.out.println("chk role:" + user);
			Userprofile usr = iWeeklyReportService
					.geUserByEmpno(new BigDecimal(user));

			Userrole usrRole = iWeeklyReportService
					.getRoldById(usr.getRoleID());

			if (!usrRole.getRoleName().equalsIgnoreCase("USER"))
				return true;
			else
				return false;
		}
		return false;
	}

	public BigDecimal getEmpNo() {
		return empNo;
	}

	public void setEmpNo(BigDecimal empNo) {
		this.empNo = empNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

	
}
