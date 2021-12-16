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

import org.springframework.stereotype.Component;

import com.bbyn.monitor.model.SMSMonitor;
import com.bbyn.monitor.service.MonitorService;
import com.bbyn.monitor.serviceimpl.MonitorServiceImpl;

import javassist.bytecode.stackmap.BasicBlock.Catch;

@Component(value = "drMonBeanTestBK")

@Scope("session")

public class DRMonBeanTestBK<T> implements Serializable {

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

	private static Map<String, String> DUNLIP1M, DUNLIP1MIN = null;

	private static Map<String, String> DUNLIPDRM, DUNLIPDRMIN = null;

	private static Map<String, String> DUNLIPROM, DUNLIPROMIN = null;

	private static Map<String, String> DUNLIPRODRM, DUNLIPRODRMIN = null;

	private static Map<String, String> DUNLIPIQM, DUNLIPIQMIN = null;

	List<SMSMonitor> getSMSs = null;
	SMSMonitor smsM = null;

	@Autowired
	MonitorService<T> monitorService;

	@PostConstruct

	public void init() {

		targetLive.add("DUNLIPDR");

		targetLive.add("DUNLIPRO");

		targetLive.add("DUNLIPRODR");

		targetLive.add("DUNLIPIQ");

		targetDR.add("DUNLIP1");

		targetDR.add("DUNLIPRO");

		targetDR.add("DUNLIPRODR");

		targetDR.add("DUNLIPIQ");

		dbs.add("IMALDB_PRD");

		dbs.add("BACKOFFICEDB");

		dbs.add("BB_CREDIT");

		dbs.add("NG_DB");

		status.add("Zero Latency or No Alert");

		status.add("Moderate Latency or Proactive");

		status.add("Latency or Action Required");

		DUNLIP1M = new LinkedHashMap<String, String>();
		DUNLIPDRM = new LinkedHashMap<String, String>();
		DUNLIPROM = new LinkedHashMap<String, String>();
		DUNLIPRODRM = new LinkedHashMap<String, String>();
		DUNLIPIQM = new LinkedHashMap<String, String>();

		DUNLIP1MIN = new LinkedHashMap<String, String>();
		DUNLIPDRMIN = new LinkedHashMap<String, String>();
		DUNLIPROMIN = new LinkedHashMap<String, String>();
		DUNLIPRODRMIN = new LinkedHashMap<String, String>();
		DUNLIPIQMIN = new LinkedHashMap<String, String>();

		DUNLIP1M.put("IMALDB_PRD", "GREEN");
		DUNLIP1M.put("BACKOFFICEDB", "GREEN");
		DUNLIP1M.put("BB_CREDIT", "GREEN");
		DUNLIP1M.put("NG_DB", "GREEN");

		DUNLIPDRM.put("IMALDB_PRD", "GREEN");
		DUNLIPDRM.put("BACKOFFICEDB", "GREEN");
		DUNLIPDRM.put("BB_CREDIT", "GREEN");
		DUNLIPDRM.put("NG_DB", "GREEN");

		DUNLIPROM.put("IMALDB_PRD", "GREEN");
		DUNLIPROM.put("BACKOFFICEDB", "GREEN");
		DUNLIPROM.put("BB_CREDIT", "GREEN");
		DUNLIPROM.put("NG_DB", "GREEN");

		DUNLIPRODRM.put("IMALDB_PRD", "GREEN");
		DUNLIPRODRM.put("BACKOFFICEDB", "GREEN");
		DUNLIPRODRM.put("BB_CREDIT", "GREEN");
		DUNLIPRODRM.put("NG_DB", "GREEN");

		DUNLIPIQM.put("IMALDB_PRD", "GREEN");
		DUNLIPIQM.put("BACKOFFICEDB", "GREEN");
		DUNLIPIQM.put("BB_CREDIT", "GREEN");
		DUNLIPIQM.put("NG_DB", "GREEN");

		DUNLIP1MIN.put("IMALDB_PRD", "10");
		DUNLIP1MIN.put("BACKOFFICEDB", "0");
		DUNLIP1MIN.put("BB_CREDIT", "0");
		DUNLIP1MIN.put("NG_DB", "0");

		DUNLIPDRMIN.put("IMALDB_PRD", "10");
		DUNLIPDRMIN.put("BACKOFFICEDB", "0");
		DUNLIPDRMIN.put("BB_CREDIT", "0");
		DUNLIPDRMIN.put("NG_DB", "0");

		DUNLIPROMIN.put("IMALDB_PRD", "10");
		DUNLIPROMIN.put("BACKOFFICEDB", "0");
		DUNLIPROMIN.put("BB_CREDIT", "0");
		DUNLIPROMIN.put("NG_DB", "0");

		DUNLIPRODRMIN.put("IMALDB_PRD", "0");
		DUNLIPRODRMIN.put("BACKOFFICEDB", "0");
		DUNLIPRODRMIN.put("BB_CREDIT", "0");
		DUNLIPRODRMIN.put("NG_DB", "0");

		DUNLIPIQMIN.put("IMALDB_PRD", "0");
		DUNLIPIQMIN.put("BACKOFFICEDB", "0");
		DUNLIPIQMIN.put("BB_CREDIT", "0");
		DUNLIPIQMIN.put("NG_DB", "0");

	}

	public void showList() {

		// userID = Util.chkForValidation();

		getSMSs = monitorService.getSMSs();
		for (SMSMonitor s : getSMSs)
			System.out.println("SMSMonitor::::::::::::::::" + s.getSmsMonitorID().getMobileNum());
	}

	public void stop() {

		System.out.println("Stop:" + source);

		System.out.println("Duration:" + duration);

	}

	public void start() {

		System.out.println("Start:" + source);

		System.out.println("Duration:" + duration);

		try {

			this.setGetDate(getDateTime());

		} catch (ParseException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		getView("prod");

	}

	public void getView(String envName) {

		selection = new DefaultOrganigramNode(null, "Ridvan Agar", null);

		rootNode = new DefaultOrganigramNode("root", envName, null);

		rootNode.setCollapsible(false);

		rootNode.setDroppable(true);

		// OrganigramNode targetEnv = addDivision(rootNode, envName);

		OrganigramNode statuss = null;

		try {

			if (source.equalsIgnoreCase("prod")) {

				for (String target : targetLive) {

					OrganigramNode targetServer = addDivision(rootNode, target);

					System.out.println("Target:" + target);

					for (String stat : status) {

						System.out.println("Status:" + status);

//                                              if(!target.equalsIgnoreCase("DUNLIPro") && (stat.contains("No Alert") || stat.contains("Action Required")) ) {

						statuss = addDivision(targetServer, stat);

						for (String db : dbs) {

							System.out.println("db:" + db);

							if (target.equalsIgnoreCase("DUNLIPDR") && DUNLIPDRM != null) {

								System.out.println(

										"TARGET:" + target + "  DUNLIPDRM.get(db):" + DUNLIPDRM.get(db) + "  stat:"
												+ stat);

								String min = DUNLIPDRMIN.get(db);

								min = "Latency:" + min + " mins";

								if (DUNLIPDRM.get(db).equalsIgnoreCase("GREEN") && stat.contains("No Alert"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (DUNLIPDRM.get(db).equalsIgnoreCase("YELLOW") && stat.contains("Proactive"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (DUNLIPDRM.get(db).equalsIgnoreCase("RED") && stat.contains("Action Required"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

							} else if (target.equalsIgnoreCase("DUNLIPRO") && DUNLIPROM != null) {

								System.out.println(

										"TARGET:" + target + "  DUNLIPROM.get(db):" + DUNLIPROM.get(db) + "  stat:"
												+ stat);

								String min = DUNLIPROMIN.get(db);

								min = "Latency:" + min + " mins";

								if (DUNLIPROM.get(db).equalsIgnoreCase("GREEN") && stat.contains("No Alert"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (DUNLIPROM.get(db).equalsIgnoreCase("YELLOW") && stat.contains("Proactive"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (DUNLIPROM.get(db).equalsIgnoreCase("RED") && stat.contains("Action Required"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

							} else if (target.equalsIgnoreCase("DUNLIPRODR") && DUNLIPRODRM != null) {

								System.out.println("TARGET:" + target + "  DUNLIPRODR.get(db):" + DUNLIPRODRM.get(db)

										+ "  stat:" + stat);

								String min = DUNLIPRODRMIN.get(db);

								min = "Latency:" + min + " mins";

								if (DUNLIPRODRM.get(db).equalsIgnoreCase("GREEN") && stat.contains("No Alert"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (DUNLIPRODRM.get(db).equalsIgnoreCase("YELLOW") && stat.contains("Proactive"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (DUNLIPRODRM.get(db).equalsIgnoreCase("RED") && stat.contains("Action Required"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

							} else if (target.equalsIgnoreCase("DUNLIPIQ") && DUNLIPIQM != null) {

								System.out.println(

										"TARGET:" + target + "  DUNLIPIQM.get(db):" + DUNLIPIQM.get(db) + "  stat:"
												+ stat);

								String min = DUNLIPIQMIN.get(db);

								min = "Latency:" + min + " mins";

								if (DUNLIPIQM.get(db).equalsIgnoreCase("GREEN") && stat.contains("No Alert"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (DUNLIPIQM.get(db).equalsIgnoreCase("YELLOW") && stat.contains("Proactive"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (DUNLIPIQM.get(db).equalsIgnoreCase("RED") && stat.contains("Action Required"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

							}

						} // end of db loop

						// required to add no db icon

						if (!DUNLIPDRM.containsValue("RED") && target.equalsIgnoreCase("DUNLIPDR")

								&& stat.contains("Action Required"))

							addDivision(statuss, "NO DB AVAILABLE");

						else if (!DUNLIPDRM.containsValue("YELLOW") && target.equalsIgnoreCase("DUNLIPDR")

								&& stat.contains("Proactive"))

							addDivision(statuss, "NO DB AVAILABLE");

						else if (!DUNLIPDRM.containsValue("GREEN") && target.equalsIgnoreCase("DUNLIPDR")

								&& stat.contains("No Alert"))

							addDivision(statuss, "NO DB AVAILABLE");

						if (!DUNLIPROM.containsValue("RED") && target.equalsIgnoreCase("DUNLIPRO")

								&& stat.contains("Action Required"))

							addDivision(statuss, "NO DB AVAILABLE");

						else if (!DUNLIPROM.containsValue("YELLOW") && target.equalsIgnoreCase("DUNLIPRO")

								&& stat.contains("Proactive"))

							addDivision(statuss, "NO DB AVAILABLE");

						else if (!DUNLIPROM.containsValue("GREEN") && target.equalsIgnoreCase("DUNLIPRO")

								&& stat.contains("No Alert"))

							addDivision(statuss, "NO DB AVAILABLE");

						if (!DUNLIPRODRM.containsValue("RED") && target.equalsIgnoreCase("DUNLIPRODR")

								&& stat.contains("Action Required"))

							addDivision(statuss, "NO DB AVAILABLE");

						else if (!DUNLIPRODRM.containsValue("YELLOW") && target.equalsIgnoreCase("DUNLIPRODR")

								&& stat.contains("Proactive"))

							addDivision(statuss, "NO DB AVAILABLE");

						else if (!DUNLIPRODRM.containsValue("GREEN") && target.equalsIgnoreCase("DUNLIPRODR")

								&& stat.contains("No Alert"))

							addDivision(statuss, "NO DB AVAILABLE");

						if (!DUNLIPIQM.containsValue("RED") && target.equalsIgnoreCase("DUNLIPIQ")

								&& stat.contains("Action Required"))

							addDivision(statuss, "NO DB AVAILABLE");

						else if (!DUNLIPIQM.containsValue("YELLOW") && target.equalsIgnoreCase("DUNLIPIQ")

								&& stat.contains("Proactive"))

							addDivision(statuss, "NO DB AVAILABLE");

						else if (!DUNLIPIQM.containsValue("GREEN") && target.equalsIgnoreCase("DUNLIPIQ")

								&& stat.contains("No Alert"))

							addDivision(statuss, "NO DB AVAILABLE");

					}

				}

			} else {

				for (String target : targetDR) {

					OrganigramNode targetServer = addDivision(rootNode, target);

					System.out.println("Target:" + target);

					for (String stat : status) {

						System.out.println("Status:" + status);

//                                              if(!target.equalsIgnoreCase("DUNLIPro") && (stat.contains("No Alert") || stat.contains("Action Required")) ) {

						statuss = addDivision(targetServer, stat);

						for (String db : dbs) {

							System.out.println("db:" + db);

							if (target.equalsIgnoreCase("DUNLIP1") && DUNLIP1M != null) {

								System.out.println(

										"TARGET:" + target + "  DUNLIP1M.get(db):" + DUNLIP1M.get(db) + "  stat:"
												+ stat);

								String min = DUNLIP1MIN.get(db);

								min = "Latency:" + min + " mins";

								if (DUNLIP1M.get(db).equalsIgnoreCase("green") && stat.contains("No Alert"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (DUNLIP1M.get(db).equalsIgnoreCase("yellow") && stat.contains("Proactive"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (DUNLIP1M.get(db).equalsIgnoreCase("red") && stat.contains("Action Required"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

							} else if (target.equalsIgnoreCase("DUNLIPRO") && DUNLIPROM != null) {

								System.out.println(

										"TARGET:" + target + "  DUNLIPROM.get(db):" + DUNLIPROM.get(db) + "  stat:"
												+ stat);

								String min = DUNLIPROMIN.get(db);

								min = "Latency:" + min + " mins";

								if (DUNLIPROM.get(db).equalsIgnoreCase("green") && stat.contains("No Alert"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (DUNLIPROM.get(db).equalsIgnoreCase("yellow") && stat.contains("Proactive"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (DUNLIPROM.get(db).equalsIgnoreCase("red") && stat.contains("Action Required"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

							} else if (target.equalsIgnoreCase("DUNLIPRODR") && DUNLIPRODRM != null) {

								System.out.println("TARGET:" + target + "  DUNLIPRODR.get(db):" + DUNLIPRODRM.get(db)

										+ "  stat:" + stat);

								String min = DUNLIPRODRMIN.get(db);

								min = "Latency:" + min + " mins";

								if (DUNLIPRODRM.get(db).equalsIgnoreCase("green") && stat.contains("No Alert"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (DUNLIPRODRM.get(db).equalsIgnoreCase("yellow") && stat.contains("Proactive"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (DUNLIPRODRM.get(db).equalsIgnoreCase("red") && stat.contains("Action Required"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

							} else if (target.equalsIgnoreCase("DUNLIPIQ") && DUNLIPIQM != null) {

								System.out.println(

										"TARGET:" + target + "  DUNLIPIQM.get(db):" + DUNLIPIQM.get(db) + "  stat:"
												+ stat);

								String min = DUNLIPIQMIN.get(db);

								min = "Latency:" + min + " mins";

								if (DUNLIPIQM.get(db).equalsIgnoreCase("green") && stat.contains("No Alert"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (DUNLIPIQM.get(db).equalsIgnoreCase("yellow") && stat.contains("Proactive"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

								if (DUNLIPIQM.get(db).equalsIgnoreCase("red") && stat.contains("Action Required"))

									addDivision2(statuss, db + System.lineSeparator() + min, stat);

							}

						} // end of db loop

						// required to add no db icon

						if (!DUNLIP1M.containsValue("RED") && target.equalsIgnoreCase("DUNLIP1")

								&& stat.contains("Action Required"))

							addDivision(statuss, "NO DB AVAILABLE");

						else if (!DUNLIP1M.containsValue("YELLOW") && target.equalsIgnoreCase("DUNLIP1")

								&& stat.contains("Proactive"))

							addDivision(statuss, "NO DB AVAILABLE");

						else if (!DUNLIP1M.containsValue("GREEN") && target.equalsIgnoreCase("DUNLIP1")

								&& stat.contains("No Alert"))

							addDivision(statuss, "NO DB AVAILABLE");

						if (!DUNLIPROM.containsValue("RED") && target.equalsIgnoreCase("DUNLIPRO")

								&& stat.contains("Action Required"))

							addDivision(statuss, "NO DB AVAILABLE");

						else if (!DUNLIPROM.containsValue("YELLOW") && target.equalsIgnoreCase("DUNLIPRO")

								&& stat.contains("Proactive"))

							addDivision(statuss, "NO DB AVAILABLE");

						else if (!DUNLIPROM.containsValue("GREEN") && target.equalsIgnoreCase("DUNLIPRO")

								&& stat.contains("No Alert"))

							addDivision(statuss, "NO DB AVAILABLE");

						if (!DUNLIPRODRM.containsValue("RED") && target.equalsIgnoreCase("DUNLIPRODR")

								&& stat.contains("Action Required"))

							addDivision(statuss, "NO DB AVAILABLE");

						else if (!DUNLIPRODRM.containsValue("YELLOW") && target.equalsIgnoreCase("DUNLIPRODR")

								&& stat.contains("Proactive"))

							addDivision(statuss, "NO DB AVAILABLE");

						else if (!DUNLIPRODRM.containsValue("GREEN") && target.equalsIgnoreCase("DUNLIPRODR")

								&& stat.contains("No Alert"))

							addDivision(statuss, "NO DB AVAILABLE");

						if (!DUNLIPIQM.containsValue("RED") && target.equalsIgnoreCase("DUNLIPIQ")

								&& stat.contains("Action Required"))

							addDivision(statuss, "NO DB AVAILABLE");

						else if (!DUNLIPIQM.containsValue("YELLOW") && target.equalsIgnoreCase("DUNLIPIQ")

								&& stat.contains("Proactive"))

							addDivision(statuss, "NO DB AVAILABLE");

						else if (!DUNLIPIQM.containsValue("GREEN") && target.equalsIgnoreCase("DUNLIPIQ")

								&& stat.contains("No Alert"))

							addDivision(statuss, "NO DB AVAILABLE");

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

		if (source.equalsIgnoreCase("prod")) {

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
