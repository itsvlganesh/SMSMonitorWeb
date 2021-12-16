package com.amg.servicemgmt.common;

import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

public class Util {

	public Util() {
		// TODO Auto-generated constructor stub
	}

	public static void showMessage(String message, String message2){
		FacesContext context = FacesContext.getCurrentInstance();
	    
	    context.addMessage(null, new FacesMessage(message,message2));
	    context.getExternalContext().getFlash().setKeepMessages(true);
	    //context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
	}
	
	
	public static void showMessageFlash(String message, String message2){
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
	    context.getExternalContext().getFlash().setKeepMessages(true);
	    
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
	
	public String logout() {

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put("empNo", null);
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		return "/login/login.xhtml?faces-redirect=true";

	}

}
