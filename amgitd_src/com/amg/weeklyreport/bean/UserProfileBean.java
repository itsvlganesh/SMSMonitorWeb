package com.amg.weeklyreport.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.servicemgmt.common.Util;
import com.amg.servicemgmt.util.CypherSecurityImpl;
import com.amg.servicemgmt.util.iCypherSecurity;
import com.amg.weeklyreport.model.Userdept;
import com.amg.weeklyreport.model.Usergroup;
import com.amg.weeklyreport.model.Userprofile;
import com.amg.weeklyreport.model.Userrole;
import com.amg.weeklyreport.service.WeeklyReportService;

@Component(value = "userProfBean")
@Scope("session")
public class UserProfileBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserProfileBean() {
		// TODO Auto-generated constructor stub
	}

	private BigDecimal empNo;
	private String userName;
	private String pass = "";
	private String confPassword = "";
	private String text;
	private Integer roleId;
	private Integer deptId;
	private Integer groupId;
	private String status;
	private List<Userdept> userDeptList;
	private List<Usergroup> userGroupList;
	private List<Userrole> userRoleList;
	private Map<Integer, String> userDeptListM = null;
	private Map<Integer, String> userGroupListM = null;
	private Map<Integer, String> userRoleListM = null;

	@Autowired
	WeeklyReportService iWeeklyReportService;

	iCypherSecurity iCS = new CypherSecurityImpl();

	public void showList() {

		userRoleList = iWeeklyReportService.getRoles();
		userRoleListM = new HashMap<Integer, String>();
		for (Object obj : userRoleList) {
			Userrole userrole = new Userrole();
			userrole = (Userrole) obj;
			userRoleListM.put(userrole.getRoleID(), userrole.getRoleName());
		}

		userDeptList = iWeeklyReportService.getDepts();
		userDeptListM = new HashMap<Integer, String>();
		for (Object obj : userDeptList) {
			Userdept userdept = new Userdept();
			userdept = (Userdept) obj;
			userDeptListM.put(userdept.getDeptID(), userdept.getDeptName());
		}

		userGroupList = iWeeklyReportService.getGroups();
		userGroupListM = new HashMap<Integer, String>();
		for (Object obj : userGroupList) {
			Usergroup usergroup = new Usergroup();
			usergroup = (Usergroup) obj;
			userGroupListM
					.put(usergroup.getGroupID(), usergroup.getGroupName());
		}
	}

	public void checkAvailablity() {
		Userprofile usr = iWeeklyReportService.geUserByEmpno(this.getEmpNo());
		String empNo = (String) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("empNo");
		if (usr != null) {

			if (empNo != null) {
				this.setDeptId(usr.getDeptID());
				this.setGroupId(usr.getGroupID());
				this.setRoleId(usr.getRoleID());
				this.setPass(usr.getPassword());
				this.setUserName(usr.getUserName());
				this.setStatus(usr.getStatus());
			} else if(empNo == null || empNo.isEmpty())
				this.setEmpNo(null);

				Util.showMessage("User ID alreasy exist",
						"User ID alreasy exist, goto login page");
				
			
		} else{
			this.setDeptId(null);
			this.setGroupId(null);
			this.setRoleId(null);
			this.setPass("");
			this.setConfPassword("");
			this.setUserName("");
			this.setStatus("");
			Util.showMessage("Valid User ID", "Proceed with the registration");
		}
	}

	public void matchPassword2() {
		System.out.println(this.getPass());
	}

	public void matchPassword() {
		pass = this.getPass();
		confPassword = this.getConfPassword();
		System.out.println(pass + " conf:" + confPassword);
		if (!this.getPass().equals(this.getConfPassword())) {
			Util.showMessage("Passwords not matching",
					"Password & confirm password are not matching");
			this.setPass("");
			this.setConfPassword("");
		} else {

			System.out.println("Passwords matching");
			//Util.showMessage("Passwords matching",					"Password & confirm password are  matching");

		}

	}

	public void clear() {
		this.setEmpNo(null);
		this.setUserName("");
		this.setConfPassword("");
		this.setPass("");
		this.setRoleId(null);
		this.setDeptId(null);
		this.setGroupId(null);

	}

	public String createNew() {

		return "/weeklyreport/userReg.xhtml?faces-redirect=true";
	}

	public void saveUser() {
		System.out.println("Save User");
		try {
			// matchPassword();
			/*
			 * Userrole usrRole =
			 * iWeeklyReportService.getRoldById(this.getRoleId()); Userdept
			 * usrDept = iWeeklyReportService.getDeptById(this.getDeptId());
			 * Usergroup usrGroup =
			 * iWeeklyReportService.getGroupById(this.getGroupId());
			 */

			// store user
			Userprofile userProfile = new Userprofile();
			userProfile.setEmpID(this.getEmpNo());
			userProfile.setUserName(this.getUserName());
			String s = iCS.encodePassword(this.getPass().trim(), this
					.getEmpNo().toString());
			userProfile.setPassword(s);
			userProfile.setRoleID(this.getRoleId());
			userProfile.setDeptID(this.getDeptId());
			userProfile.setGroupID(this.getGroupId());
			userProfile.setCreated(new Date());
			userProfile.setStatus("D");

			iWeeklyReportService.saveUser(userProfile);

			Util.showMessage("Success",
					"User Registered Successfully:" + this.getUserName()
							+ " Please contact admin to activate your account");
			clear();
		} catch (Exception e) {
			System.out.println("reached exception reference - bean");
			Util.showMessage("Failed", "failed to Save the data.");
			e.printStackTrace();
		}
	}

	public void updatePass() {
		System.out.println("data:" + this.getText());
		if (this.getPass().equals(this.getConfPassword())) {
			String empNo = (String) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("empNo");
			Userprofile usr = iWeeklyReportService
					.geUserByEmpno(new BigDecimal(empNo));
			if (usr != null) {
				// but here update the status
				String s = iCS.encodePassword(this.getPass(),
						String.valueOf(usr.getEmpID()));
				System.out.println("Edit user:" + s);
				usr.setPassword(s);
				usr.setModified(new Date());
				usr.setStatus("A");
				try {
					iWeeklyReportService.updateUser(usr);
					Util.showMessage("Modified Successfully",
							"Password Updated Successfully");
				} catch (Exception e) {
					Util.showMessage("Failed", "Failed to Update Password");
					e.printStackTrace();
				}
			} else
				Util.showMessage("Error", "Something went wrong");
		} else
			Util.showMessage("Passwords not matching",
					"Password & confirm password are not matching");
	}

	public void updateProfile() {
		System.out.println("data:" + this.getText());
		if (this.getPass().equals(this.getConfPassword())) {
			Userprofile usr = iWeeklyReportService
					.geUserByEmpno(this.getEmpNo());
			if (usr != null) {
				// but here update the status
				String s = iCS.encodePassword(this.getPass(),
						String.valueOf(usr.getEmpID()));
				System.out.println("Edit user:" + s);
				usr.setDeptID(this.getDeptId());
				usr.setGroupID(this.getGroupId());
				usr.setRoleID(this.getRoleId());
				usr.setPassword(s);
				usr.setModified(new Date());
				usr.setStatus(this.getStatus());
				try {
					iWeeklyReportService.updateUser(usr);
					Util.showMessage("Modified Successfully", "Profile Updated Successfully");
					clear();
				} catch (Exception e) {
					Util.showMessage("Failed", "Failed to Update Profile");
					e.printStackTrace();
				}
			} else
				Util.showMessage("Error", "Something went wrong");
		} else
			Util.showMessage("Passwords not matching",
					"Password & confirm password are not matching");
	}

	public BigDecimal getEmpNo() {
		return empNo;
	}

	public void setEmpNo(BigDecimal empNo) {
		this.empNo = empNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getConfPassword() {
		return confPassword;
	}

	public void setConfPassword(String confPassword) {
		this.confPassword = confPassword;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Map<Integer, String> getUserDeptListM() {
		return userDeptListM;
	}

	public void setUserDeptListM(Map<Integer, String> userDeptListM) {
		this.userDeptListM = userDeptListM;
	}

	public Map<Integer, String> getUserGroupListM() {
		return userGroupListM;
	}

	public void setUserGroupListM(Map<Integer, String> userGroupListM) {
		this.userGroupListM = userGroupListM;
	}

	public Map<Integer, String> getUserRoleListM() {
		return userRoleListM;
	}

	public void setUserRoleListM(Map<Integer, String> userRoleListM) {
		this.userRoleListM = userRoleListM;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
