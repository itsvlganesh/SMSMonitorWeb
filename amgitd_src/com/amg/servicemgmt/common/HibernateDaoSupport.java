package com.amg.servicemgmt.common;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateDaoSupport {

	  @Autowired
	   private SessionFactory sessionFactory;
	  
	    public Session getSession() {
	        return sessionFactory.getCurrentSession();
	    }
	    
	    
	    @Autowired
		   private SessionFactory sessionFactoryShrms;
		  
		    public Session getSessionShrms() {
		        return sessionFactoryShrms.getCurrentSession();
		    }
	    
}
