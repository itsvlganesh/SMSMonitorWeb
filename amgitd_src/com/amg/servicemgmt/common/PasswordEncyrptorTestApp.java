package com.amg.servicemgmt.common;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.amg.weeklyreport.dao.WeeklyReportDao;
import com.amg.weeklyreport.service.WeeklyReportService;


public class PasswordEncyrptorTestApp {

	

	public static void main(String[] args) {

	//	logger.info("Starting HibernateTestApp...");

		ApplicationContext context = new ClassPathXmlApplicationContext("WEB-INF/applicationContext.xml");

WeeklyReportService genericDao = context.getBean(WeeklyReportService.class);
		
		List<?> dataList2 = genericDao.getDepts();
		
		System.out.println("Got the data: {}"+ dataList2);

		((AbstractApplicationContext) context).close();

	}

}
