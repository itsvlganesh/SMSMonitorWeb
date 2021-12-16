package com.amg.servicemgmt.common;

import java.util.Date;
import org.springframework.scheduling.annotation.Scheduled;
 
public class DemoServiceBasicUsageFixedDelay
{
	@Scheduled(fixedDelay = 5000)
    public void demoServiceMethod()
    {
        System.out.println("Method executed at every 5 seconds. Current time is :: "+ new Date());
    }
}