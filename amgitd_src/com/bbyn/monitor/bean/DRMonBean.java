package com.bbyn.monitor.bean;

import java.io.Serializable;

import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;

import java.util.LinkedHashMap;

import java.util.List;

import java.util.Map;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.primefaces.model.DefaultOrganigramNode;

import org.primefaces.model.DefaultTreeNode;

import org.primefaces.model.OrganigramNode;

import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amg.servicemgmt.common.DBMonitoringSV;
import com.amg.servicemgmt.common.Util;
import com.bbyn.monitor.model.SMSMonitor;
import com.bbyn.monitor.service.MonitorService;
import com.bbyn.monitor.serviceimpl.MonitorServiceImpl;

import javassist.bytecode.stackmap.BasicBlock.Catch;

@Component(value = "drMonBean")

@Scope("session")

public class DRMonBean<T> implements Serializable {

	private String userID;

	private String getDate;

	private String source;

	private Integer duration;

	private OrganigramNode rootNode;

	private OrganigramNode selection;

	private boolean zoom = false;

	private String style = "width: 1600px";

	private int leafNodeConnectorHeight = 0;

	private boolean autoScrollToSelection = false;

	private List<String> targetLive = new ArrayList<String>();

	private List<String> targetDR = new ArrayList<String>();

	private List<String> dbs = new ArrayList<String>();

	private List<String> status = new ArrayList<String>();

	private String employeeName;

	private static Map<String, String> STCM, STCMIN = null;

	private static Map<String, String> ZAINM, ZAINMIN = null;

	private static Map<String, String> OOREDOOM, OOREDOOMIN = null;

	List<SMSMonitor> getSMSs = null;
	List<SMSMonitor> getSMSSTC = null;
	SMSMonitor smsM = null;

	@Autowired
	MonitorService<T> monitorService;

	@PostConstruct

	public void init() {

		targetLive.add("STC");
		targetLive.add("ZAIN");
		targetLive.add("OOREDOO");

		dbs.add("SALARY");
		dbs.add("TRANSACTION");
		dbs.add("MARKETING");
		dbs.add("OTP");

		status.add("Zero Latency or No Alert (0to5Mins)");
		status.add("Moderate Latency or Proactive (5to10Mins)");
		status.add("Latency or Action Required (>10Mins)");

//		STCM = new LinkedHashMap<String, String>();
//		ZAINM = new LinkedHashMap<String, String>();
//		OOREDOOM = new LinkedHashMap<String, String>();
//
//		STCMIN = new LinkedHashMap<String, String>();
//		ZAINMIN = new LinkedHashMap<String, String>();
//		OOREDOOMIN = new LinkedHashMap<String, String>();
//
//		STCM.put("SALARY", "RED");
//		STCM.put("TRANSACTION", "RED");
//		STCM.put("MARKETING", "RED");
//		STCM.put("OTP", "RED");
//
//		ZAINM.put("SALARY", "RED");
//		ZAINM.put("TRANSACTION", "RED");
//		ZAINM.put("MARKETING", "RED");
//		ZAINM.put("OTP", "RED");
//
//		OOREDOOM.put("SALARY", "RED");
//		OOREDOOM.put("TRANSACTION", "RED");
//		OOREDOOM.put("MARKETING", "RED");
//		OOREDOOM.put("OTP", "RED");
//
//		STCMIN.put("SALARY", "x");
//		STCMIN.put("TRANSACTION", "x");
//		STCMIN.put("MARKETING", "x");
//		STCMIN.put("OTP", "x");
//
//		ZAINMIN.put("SALARY", "x");
//		ZAINMIN.put("TRANSACTION", "x");
//		ZAINMIN.put("MARKETING", "x");
//		ZAINMIN.put("OTP", "x");
//
//		OOREDOOMIN.put("SALARY", "x");
//		OOREDOOMIN.put("TRANSACTION", "x");
//		OOREDOOMIN.put("MARKETING", "x");
//		OOREDOOMIN.put("OTP", "x");

	}

	public void showList() {

		 userID = Util.chkForValidation();

	}

	public long diff(Date d1) {

		Date d2 = new Date();

		// Get msec from each, and subtract.
		long diff = d2.getTime() - d1.getTime();
		long diffSeconds = diff / 1000;
		long diffMinutes = diff / (60 * 1000);
		long diffHours = diff / (60 * 60 * 1000);
		System.out.println("Time in seconds: " + diffSeconds + " seconds.");
		System.out.println("Time in minutes: " + diffMinutes + " minutes.");
		System.out.println("Time in hours: " + diffHours + " hours.");

		return diffMinutes;

	}

	public void stop() {

		System.out.println("Stop:" + source);

		System.out.println("Duration:" + duration);
		
		STCM = null;
		ZAINM = null;
		OOREDOOM = null;
		STCMIN = null;
		ZAINMIN = null;
		OOREDOOMIN = null;
		System.gc();
		
		
		

	}
	
//	@Scheduled(fixedDelay = 30000)
//    public void demoServiceMethod()
//    {
//        System.out.println("Method executed at every 5 seconds. Current time is :: "+ new Date());
//        
//		DBMonitoringSV.dbConnection2("Network");
//		
//		STCM = DBMonitoringSV.STCM;
//		ZAINM = DBMonitoringSV.ZAINM;
//		OOREDOOM = DBMonitoringSV.OOREDOOM;
//		STCMIN = DBMonitoringSV.STCMIN;
//		ZAINMIN = DBMonitoringSV.ZAINMIN;
//		OOREDOOMIN = DBMonitoringSV.OOREDOOMIN;
//
//		getView("Network");
//
//    }

	public void start() {

		System.out.println("Start:" + source);

		System.out.println("Duration:" + duration);

		try {

			this.setGetDate(getDateTime());

			DBMonitoringSV.dbConnection2("Network");
			
			STCM = null;
			ZAINM = null;
			OOREDOOM = null;
			STCMIN = null;
			ZAINMIN = null;
			OOREDOOMIN = null;
			
			STCM = DBMonitoringSV.STCM;
			ZAINM = DBMonitoringSV.ZAINM;
			OOREDOOM = DBMonitoringSV.OOREDOOM;
			STCMIN = DBMonitoringSV.STCMIN;
			ZAINMIN = DBMonitoringSV.ZAINMIN;
			OOREDOOMIN = DBMonitoringSV.OOREDOOMIN;
			
			for (Map.Entry<String, String> entry : STCM.entrySet()) {

				System.out.println("STCM Key : " + entry.getKey() + " Value : " + entry.getValue());
				

			}

		} catch (ParseException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} catch (Exception ee) {
			ee.printStackTrace();
		}

		getView("Network");

	}

	public void getView(String envName) {

		selection = new DefaultOrganigramNode(null, "Ridvan Agar", null);

		rootNode = new DefaultOrganigramNode("root", envName, null);

		rootNode.setCollapsible(false);

		rootNode.setDroppable(true);

		// OrganigramNode targetEnv = addDivision(rootNode, envName);

		OrganigramNode statuss = null;

		try {

			if (envName.equalsIgnoreCase("Network")) {

				for (String target : targetLive) {

					OrganigramNode targetServer = addDivision(rootNode, target);

					System.out.println("Target:" + target);

					for (String stat : status) {

						System.out.println("Status:" + status);

//                                              if(!target.equalsIgnoreCase("DUNLIPro") && (stat.contains("No Alert") || stat.contains("Action Required")) ) {

						statuss = addDivision(targetServer, stat);

						for (String db : dbs) {

							System.out.println("db:" + db);

							if (target.equalsIgnoreCase("STC") && STCM != null) {

								System.out.println(

										"TARGET:" + target + "  STCM.get(db):" + STCM.get(db) + "  stat:" + stat);

								System.out.println("STCM Array Size:" + STCM.size());

								String min = STCMIN.get(db);

								min = "Latency:" + min + " mins";

								for (int i = 0; i < STCM.size(); i++)
									System.out.println("STCM Loop::::" + STCM.get(i));

								if (STCM.get(db).equalsIgnoreCase("GREEN") && stat.contains("No Alert"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (STCM.get(db).equalsIgnoreCase("YELLOW") && stat.contains("Proactive"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (STCM.get(db).equalsIgnoreCase("RED") && stat.contains("Action Required"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

							} else if (target.equalsIgnoreCase("ZAIN") && ZAINM != null) {

								System.out.println(

										"TARGET:" + target + "  ZAINM.get(db):" + ZAINM.get(db) + "  stat:" + stat);

								String min = ZAINMIN.get(db);

								min = "Latency:" + min + " mins";

								if (ZAINM.get(db).equalsIgnoreCase("GREEN") && stat.contains("No Alert"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (ZAINM.get(db).equalsIgnoreCase("YELLOW") && stat.contains("Proactive"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (ZAINM.get(db).equalsIgnoreCase("RED") && stat.contains("Action Required"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

							} else if (target.equalsIgnoreCase("OOREDOO") && OOREDOOM != null) {

								System.out.println("TARGET:" + target + "  OOREDOOM.get(db):" + OOREDOOM.get(db)

										+ "  stat:" + stat);

								String min = OOREDOOMIN.get(db);

								min = "Latency:" + min + " mins";

								if (OOREDOOM.get(db).equalsIgnoreCase("GREEN") && stat.contains("No Alert"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (OOREDOOM.get(db).equalsIgnoreCase("YELLOW") && stat.contains("Proactive"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (OOREDOOM.get(db).equalsIgnoreCase("RED") && stat.contains("Action Required"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

							}
						} // end of db loop

						// required to add NO QUEUE icon

						if (!STCM.containsValue("RED") && target.equalsIgnoreCase("STC")

								&& stat.contains("Action Required"))

							addDivision(statuss, "NO QUEUE AVAILABLE");

						else if (!STCM.containsValue("YELLOW") && target.equalsIgnoreCase("STC")

								&& stat.contains("Proactive"))

							addDivision(statuss, "NO QUEUE AVAILABLE");

						else if (!STCM.containsValue("GREEN") && target.equalsIgnoreCase("STC")

								&& stat.contains("No Alert"))

							addDivision(statuss, "NO QUEUE AVAILABLE");

						if (!ZAINM.containsValue("RED") && target.equalsIgnoreCase("ZAIN")

								&& stat.contains("Action Required"))

							addDivision(statuss, "NO QUEUE AVAILABLE");

						else if (!ZAINM.containsValue("YELLOW") && target.equalsIgnoreCase("ZAIN")

								&& stat.contains("Proactive"))

							addDivision(statuss, "NO QUEUE AVAILABLE");

						else if (!ZAINM.containsValue("GREEN") && target.equalsIgnoreCase("ZAIN")

								&& stat.contains("No Alert"))

							addDivision(statuss, "NO QUEUE AVAILABLE");

						if (!OOREDOOM.containsValue("RED") && target.equalsIgnoreCase("OOREDOO")

								&& stat.contains("Action Required"))

							addDivision(statuss, "NO QUEUE AVAILABLE");

						else if (!OOREDOOM.containsValue("YELLOW") && target.equalsIgnoreCase("OOREDOO")

								&& stat.contains("Proactive"))

							addDivision(statuss, "NO QUEUE AVAILABLE");

						else if (!OOREDOOM.containsValue("GREEN") && target.equalsIgnoreCase("OOREDOO")

								&& stat.contains("No Alert"))

							addDivision(statuss, "NO QUEUE AVAILABLE");

					}

				}

			}

		} catch (NullPointerException e) {

			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Null pointer at get view");

		}

	}

	public String sptStr(String str) {

		String[] parts = str.split("_");

		String part1 = parts[0]; // 004

		String part2 = parts[1]; // 034556

		return part2;

	}

	public void getViewBK(String envName) {

		selection = new DefaultOrganigramNode(null, "Ridvan Agar", null);

		rootNode = new DefaultOrganigramNode("root", envName, null);

		rootNode.setCollapsible(false);

		rootNode.setDroppable(true);

		// OrganigramNode targetEnv = addDivision(rootNode, envName);

		if (source.equalsIgnoreCase("Network")) {

			for (String target : targetLive) {

				OrganigramNode targetServer = addDivision(rootNode, target);

				System.out.println("Target:" + target);

				for (String stat : status) {

					System.out.println("Status:" + status);

//                                              if(!target.equalsIgnoreCase("DUNLIPro") && (stat.contains("No Alert") || stat.contains("Action Required")) ) {

					OrganigramNode statuss = addDivision(targetServer, stat);

					for (String db : dbs) {

						System.out.println("db:" + db);

						addDivision2(statuss, db, stat);

					}

					// }

				}

			}

		} else {

			for (String target : targetDR) {

				OrganigramNode targetServer = addDivision(rootNode, target);

				System.out.println("Target:" + target);

				for (String stat : status) {

					System.out.println("Status:" + status);

//                                              if(!target.equalsIgnoreCase("DUNLIPro") && (stat.contains("No Alert") || stat.contains("Action Required")) ) {

					OrganigramNode statuss = addDivision(targetServer, stat);

					for (String db : dbs) {

						System.out.println("db:" + db);

						addDivision2(statuss, db, stat);

					}

					// }

				}

			}

		}

	}

	private String getDateTime() throws ParseException {

		// Date date = new SimpleDateFormat("MM/dd/yy HH:mm").parse(inputDate);

		// Date date = new SimpleDateFormat("dd/MM/yy HH:mm").parse(inputDate);

		SimpleDateFormat ss = new SimpleDateFormat("dd/MMM/yyyy HH:mm");

		ss.setTimeZone(TimeZone.getTimeZone("GMT+3"));

		return ss.format(new Date());

	}

	protected OrganigramNode addDivision2(OrganigramNode parent, String status, String name) {

		if (name.contains("No Alert"))

			name = "database-noalter";

		else if (name.contains("Proactive"))

			name = "database-proactive";

		else if (name.contains("Action Required"))

			name = "database-action";

		System.out.println("name:" + name);

		OrganigramNode statusNode = new DefaultOrganigramNode(name, status, parent);

		statusNode.setDroppable(true);

		statusNode.setDraggable(true);

		statusNode.setSelectable(true);

		return statusNode;

	}

	protected OrganigramNode addDivision(OrganigramNode parent, String status, String... databases) {

		OrganigramNode statusNode = new DefaultOrganigramNode("status", status, parent);

		statusNode.setDroppable(true);

		statusNode.setDraggable(true);

		statusNode.setSelectable(true);

		if (databases != null && status.contains("No Alert")) {

			for (String database : databases) {

				OrganigramNode databaseNode = new DefaultOrganigramNode("database-noalter", database, parent);

				databaseNode.setDraggable(true);

				databaseNode.setSelectable(true);

			}

		} else if (databases != null && status.contains("Proactive")) {

			for (String database : databases) {

				OrganigramNode databaseNode = new DefaultOrganigramNode("database-proactive", database, parent);

				databaseNode.setDraggable(true);

				databaseNode.setSelectable(true);

			}

		} else if (databases != null && status.contains("Action Required")) {

			for (String database : databases) {

				OrganigramNode databaseNode = new DefaultOrganigramNode("database-action", database, parent);

				databaseNode.setDraggable(true);

				databaseNode.setSelectable(true);

			}

		}

		return statusNode;

	}

	public OrganigramNode getRootNode() {

		return rootNode;

	}

	public void setRootNode(OrganigramNode rootNode) {

		this.rootNode = rootNode;

	}

	public OrganigramNode getSelection() {

		return selection;

	}

	public void setSelection(OrganigramNode selection) {

		this.selection = selection;

	}

	public boolean isZoom() {

		return zoom;

	}

	public void setZoom(boolean zoom) {

		this.zoom = zoom;

	}

	public String getEmployeeName() {

		return employeeName;

	}

	public void setEmployeeName(String employeeName) {

		this.employeeName = employeeName;

	}

	public String getStyle() {

		return style;

	}

	public void setStyle(String style) {

		this.style = style;

	}

	public int getLeafNodeConnectorHeight() {

		return leafNodeConnectorHeight;

	}

	public void setLeafNodeConnectorHeight(int leafNodeConnectorHeight) {

		this.leafNodeConnectorHeight = leafNodeConnectorHeight;

	}

	public boolean isAutoScrollToSelection() {

		return autoScrollToSelection;

	}

	public void setAutoScrollToSelection(boolean autoScrollToSelection) {

		this.autoScrollToSelection = autoScrollToSelection;

	}

	public String getUserID() {

		return userID;

	}

	public void setUserID(String userID) {

		this.userID = userID;

	}

	public String getSource() {

		return source;

	}

	public void setSource(String source) {

		this.source = source;

	}

	public Integer getDuration() {

		return duration;

	}

	public void setDuration(Integer duration) {

		this.duration = duration;

	}

	public String getGetDate() {

		return getDate;

	}

	public void setGetDate(String getDate) {

		this.getDate = getDate;

	}

}
