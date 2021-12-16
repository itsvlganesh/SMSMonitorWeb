package com.amg.servicemgmt.common;

import java.math.BigDecimal;

import javax.faces.context.FacesContext;

/*******************************************************************************************************************
 * File : SessionStateManage.java
 * 
 * Project : AlmullaExchange
 * 
 * Package : com.amg.exchange.util
 * 
 * Created : Date : 22-Apr-2014 3:46:42 pm By : Justin Vincent Revision:
 * 
 * Last Change: Date : 2014-04-01 By : Justin Vincent Revision:
 * 
 * Description: TODO
 ********************************************************************************************************************/
public class SessionStateManage {

	public String getSessionValue(String sessionObjectName) {
		if (isExists(sessionObjectName)) {
			return FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().get(sessionObjectName).toString();
		} else {
			return null;
		}
	}

	public void setSessionValue(String sessionObjectName,
			String sessionObjectValue) {

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(sessionObjectName, sessionObjectValue);
	}

	public boolean isExists(String sessionObjectName) {

		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().containsKey(sessionObjectName);
	}

	public BigDecimal getLevel() {

		StringBuffer sbf = new StringBuffer();

		sbf.append(isExists("applicationId") ? "1" : "0");
		sbf.append(isExists("companyId") ? "1" : "0");
		sbf.append(isExists("countryId") ? "1" : "0");
		sbf.append(isExists("pageId") ? "1" : "0");

		return new BigDecimal(Integer.parseInt(sbf.toString(), 2));
	}

	public BigDecimal getApplicationId() {

		return new BigDecimal(
				isExists("applicationId") ? getSessionValue("applicationId")
						: "0");
	}

	public BigDecimal getCompanyId() {

		return new BigDecimal(
				isExists("companyId") ? getSessionValue("companyId")
						: "0");
	}

	public BigDecimal getCountryId() {

		return new BigDecimal(
				isExists("countryId") ? getSessionValue("countryId")
						: "0");
	}

	public BigDecimal getPageId() {

		return new BigDecimal(
				isExists("pageId") ? getSessionValue("pageId")
						: "0");
	}

}
