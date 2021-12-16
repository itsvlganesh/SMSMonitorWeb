package com.amg.servicemgmt.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailer {

	public static String getApplicationUri() {
		try {
			FacesContext ctxt = FacesContext.getCurrentInstance();
			ExternalContext ext = ctxt.getExternalContext();
			URI uri = new URI(ext.getRequestScheme(), null,
					ext.getRequestServerName(), ext.getRequestServerPort(),
					ext.getRequestContextPath(), null, null);
			return uri.toASCIIString();
		} catch (URISyntaxException e) {
			throw new FacesException(e);
		}
	}

	public static void sendEmailIni(String user, String reqNumber, String empName,
			String dept, String reqType, String systemIp, String systemName,String userRemarks, String subject,
			InternetAddress[] address, InternetAddress[] addressCC) {

		// Sender's email ID needs to be mentioned
		String from = "service.management@almullagroup.com";
		String url = getApplicationUri();
		// Get system properties
		Properties properties = createConfiguration();

		// Get the default Session object.
		SmtpAuthenticator authentication = new SmtpAuthenticator();

		Session session = Session
				.getDefaultInstance(properties, authentication);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipients(Message.RecipientType.TO, address);
			// Set To: header field of the header.
			System.out.println("dsaddsadasdsadasdasdasd:" + addressCC.length);
			if (addressCC.length != 4)
				message.addRecipients(Message.RecipientType.CC, addressCC);

			// Set Subject: header field
			message.setSubject(subject);

			// String data = "vasa";
			// Send the actual HTML message, as big as you like
			message.setContent(
					"<!DOCTYPE html>\n" + "<html>\n" + "<body>\n"
							+ "<h2>Service Request #"
							+ reqNumber
							+ " is created with the following details:</h2>\n"
							+ "<h2>"
							+ subject
							+ "</h2>\n"
							+ "<table border=\"1\" style=\"width:100%\">\n"
							+ " \n"
							+ " <tr>\n"
							+ " <td>Requset Sr.Number</td>\n"
							+ "     <td>"
							+ reqNumber
							+ "</td>\n"
							+ "  </tr>\n"
							+ "  <tr>\n"
							+ "    <td>Employee Number</td>\n"
							+ "    <td>"
							+ user
							+ "</td>	\n"
							+ "  </tr>\n"
							+ "  <tr>\n"
							+ "    <td>Employee Name</td>\n"
							+ "    <td>"
							+ empName
							+ "</td>\n"
							+ "  </tr>\n"
							+ "  <tr>\n"
							+ "    <td>Department</td>\n"
							+ "    <td>"
							+ dept
							+ "</td>\n"
							+ "  </tr>\n"
							+ "  <tr>\n"
							+ "    <td>Request Type</td>\n"
							+ "    <td>"
							+ reqType
							+ "</td>\n"
							+ "  </tr>\n"
							+ " <tr>\n"
							+ " <td>System IP</td>\n"
							+ "     <td>"
							+ systemIp
							+ "</td>\n"
							+ "  </tr>\n"
							+ " <tr>\n"
							+ " <td>Windows Username</td>\n"
							+ "     <td>"
							+ systemName
							+ "</td>\n"
							+ "  </tr>\n"
							+ "  <tr>\n"
							+ "    <td>User Remarks</td>\n"
							+ "    <td>"
							+ userRemarks
							+ "</td>\n"
							+ "  </tr>\n"						
							+ "</table>\n"
							+ "<br/>\n"
							+ "<b> click the below link to view the details using Chrome</b>\n"
							+ "<br/>\n"
							+"<a href="+url+">Service Management</a>\n"
							+ "<br/>\n"
							+ "<br/>\n"
							+ "\n"
							+ "<b>Thanks & Regrads</b><br/>\n"
							+ "Almulla Group, ITD\n"
							+ "<br/>\n"
							+ "FTZ,Kuwait.\n" + "</body>\n" + "</html>",
					"text/html");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	
	
	public static void sendEmail(String user, String reqNumber, String empName,
			String dept, String reqType, String systemIp, String systemName,String userRemarks,String approverRemarks,String providerRemarks, String subject,
			InternetAddress[] address, InternetAddress[] addressCC) {

		// Sender's email ID needs to be mentioned
		String from = "service.management@almullagroup.com";
		String url = getApplicationUri();
		// Get system properties
		Properties properties = createConfiguration();

		// Get the default Session object.
		SmtpAuthenticator authentication = new SmtpAuthenticator();

		Session session = Session
				.getDefaultInstance(properties, authentication);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipients(Message.RecipientType.TO, address);
			// Set To: header field of the header.
			System.out.println("dsaddsadasdsadasdasdasd:" + addressCC.length);
			if (addressCC.length != 4)
				message.addRecipients(Message.RecipientType.CC, addressCC);

			// Set Subject: header field
			message.setSubject(subject);

			// String data = "vasa";
			// Send the actual HTML message, as big as you like
			message.setContent(
					"<!DOCTYPE html>\n" + "<html>\n" + "<body>\n"
							+ "<h2>Service Request #"
							+ reqNumber
							+ " is created with the following details:</h2>\n"
							+ "<h2>"
							+ subject
							+ "</h2>\n"
							+ "<table border=\"1\" style=\"width:100%\">\n"
							+ " \n"
							+ " <tr>\n"
							+ " <td>Requset Sr.Number</td>\n"
							+ "     <td>"
							+ reqNumber
							+ "</td>\n"
							+ "  </tr>\n"
							+ "  <tr>\n"
							+ "    <td>Employee Number</td>\n"
							+ "    <td>"
							+ user
							+ "</td>	\n"
							+ "  </tr>\n"
							+ "  <tr>\n"
							+ "    <td>Employee Name</td>\n"
							+ "    <td>"
							+ empName
							+ "</td>\n"
							+ "  </tr>\n"
							+ "  <tr>\n"
							+ "    <td>Department</td>\n"
							+ "    <td>"
							+ dept
							+ "</td>\n"
							+ "  </tr>\n"
							+ "  <tr>\n"
							+ "    <td>Request Type</td>\n"
							+ "    <td>"
							+ reqType
							+ "</td>\n"
							+ "  </tr>\n"
							+ " <tr>\n"
							+ " <td>System IP</td>\n"
							+ "     <td>"
							+ systemIp
							+ "</td>\n"
							+ "  </tr>\n"
							+ " <tr>\n"
							+ " <td>Windows Username</td>\n"
							+ "     <td>"
							+ systemName
							+ "</td>\n"
							+ "  </tr>\n"
							+ "  <tr>\n"
							+ "    <td>User Remarks</td>\n"
							+ "    <td>"
							+ userRemarks
							+ "</td>\n"
							+ "  </tr>\n"
							+ "  <tr>\n"
							+ "    <td>Approver Remarks</td>\n"
							+ "    <td>"
							+ approverRemarks
							+ "</td>\n"
							+ "  </tr>\n"
							+ "  <tr>\n"
							+ "    <td>Provider Remarks</td>\n"
							+ "    <td>"
							+ providerRemarks
							+ "</td>\n"
							+ "  </tr>\n"
							+ "</table>\n"
							+ "<br/>\n"
							+ "<b> click the below link to view the details using Chrome</b>\n"
							+ "<br/>\n"
							+"<a href="+url+">Service Management</a>\n"
							+ "<br/>\n"
							+ "<br/>\n"
							+ "\n"
							+ "<b>Thanks & Regrads</b><br/>\n"
							+ "Almulla Group, ITD\n"
							+ "<br/>\n"
							+ "FTZ,Kuwait.\n" + "</body>\n" + "</html>",
					"text/html");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	public static void sendEmail(String user, String reqNumber, String empName,
			String dept, String reqType,String userRemarks,String approverRemarks,String providerRemarks, String subject,
			InternetAddress[] address) {
		// TODO code application logic here
		// Recipient's email ID needs to be mentioned.
		// String to = "vlganesh@almullagroup.com";

		// Sender's email ID needs to be mentioned
		String from = "service.management@almullagroup.com";

		// Get system properties
		Properties properties = createConfiguration();

		// Get the default Session object.
		SmtpAuthenticator authentication = new SmtpAuthenticator();

		Session session = Session
				.getDefaultInstance(properties, authentication);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipients(Message.RecipientType.TO, address);

			// Set Subject: header field
			message.setSubject(subject);

			// String data = "vasa";
			// Send the actual HTML message, as big as you like
			message.setContent("<!DOCTYPE html>\n" + "<html>\n" + "<body>\n"
					+ "<h2>Service Request #"
					+ reqNumber
					+ " is created with the following details:</h2>\n"
					+ "<h2>"
					+ subject
					+ "</h2>\n"
					+ "<table border=\"1\" style=\"width:100%\">\n"
					+ " \n"
					+ " <tr>\n"
					+ " <td>Requset Sr.Number</td>\n"
					+ "     <td>"
					+ reqNumber
					+ "</td>\n"
					+ "  </tr>\n"
					+ "  <tr>\n"
					+ "    <td>Employee Number</td>\n"
					+ "    <td>"
					+ user
					+ "</td>	\n"
					+ "  </tr>\n"
					+ "  <tr>\n"
					+ "    <td>Employee Name</td>\n"
					+ "    <td>"
					+ empName
					+ "</td>\n"
					+ "  </tr>\n"
					+ "  <tr>\n"
					+ "    <td>Department</td>\n"
					+ "    <td>"
					+ dept
					+ "</td>\n"
					+ "  </tr>\n"
					+ "  <tr>\n"
					+ "    <td>Request Type</td>\n"
					+ "    <td>"
					+ reqType
					+ "</td>\n"
					+ "  </tr>\n"
							
					+ "  <tr>\n"
					+ "    <td>User Remarks</td>\n"
					+ "    <td>"
					+ userRemarks
					+ "</td>\n"
					+ "  </tr>\n"
					+ "  <tr>\n"
					+ "    <td>Approver Remarks</td>\n"
					+ "    <td>"
					+ approverRemarks
					+ "</td>\n"
					+ "  </tr>\n"
					+ "  <tr>\n"
					+ "    <td>Provider Remarks</td>\n"
					+ "    <td>"
					+ providerRemarks
					+ "</td>\n"
					+ "  </tr>\n"
					+ "</table>\n"
					+ "<br/>\n"
					+ "<br/>\n"
					+ "\n"
					+ "<b>Thanks & Regrads</b><br/>\n"
					+ "Almulla Group, ITD\n"
					+ "<br/>\n"
					+ "FTZ,Kuwait.\n"
					+ "</body>\n" + "</html>", "text/html");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
	
	public static void sendEmailIni(String user, String reqNumber, String empName,
			String dept, String reqType,String userRemarks, String subject,
			InternetAddress[] address) {
		// TODO code application logic here
		// Recipient's email ID needs to be mentioned.
		// String to = "vlganesh@almullagroup.com";

		// Sender's email ID needs to be mentioned
		String from = "service.management@almullagroup.com";

		// Get system properties
		Properties properties = createConfiguration();

		// Get the default Session object.
		SmtpAuthenticator authentication = new SmtpAuthenticator();

		Session session = Session
				.getDefaultInstance(properties, authentication);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipients(Message.RecipientType.TO, address);

			// Set Subject: header field
			message.setSubject(subject);

			// String data = "vasa";
			// Send the actual HTML message, as big as you like
			message.setContent("<!DOCTYPE html>\n" + "<html>\n" + "<body>\n"
					+ "<h2>Service Request #"
					+ reqNumber
					+ " is created with the following details:</h2>\n"
					+ "<h2>"
					+ subject
					+ "</h2>\n"
					+ "<table border=\"1\" style=\"width:100%\">\n"
					+ " \n"
					+ " <tr>\n"
					+ " <td>Requset Sr.Number</td>\n"
					+ "     <td>"
					+ reqNumber
					+ "</td>\n"
					+ "  </tr>\n"
					+ "  <tr>\n"
					+ "    <td>Employee Number</td>\n"
					+ "    <td>"
					+ user
					+ "</td>	\n"
					+ "  </tr>\n"
					+ "  <tr>\n"
					+ "    <td>Employee Name</td>\n"
					+ "    <td>"
					+ empName
					+ "</td>\n"
					+ "  </tr>\n"
					+ "  <tr>\n"
					+ "    <td>Department</td>\n"
					+ "    <td>"
					+ dept
					+ "</td>\n"
					+ "  </tr>\n"
					+ "  <tr>\n"
					+ "    <td>Request Type</td>\n"
					+ "    <td>"
					+ reqType
					+ "</td>\n"
					+ "  </tr>\n"
							
					+ "  <tr>\n"
					+ "    <td>User Remarks</td>\n"
					+ "    <td>"
					+ userRemarks
					+ "</td>\n"
					+ "  </tr>\n"				
					+ "</table>\n"
					+ "<br/>\n"
					+ "<br/>\n"
					+ "\n"
					+ "<b>Thanks & Regrads</b><br/>\n"
					+ "Almulla Group, ITD\n"
					+ "<br/>\n"
					+ "FTZ,Kuwait.\n"
					+ "</body>\n" + "</html>", "text/html");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	private static Properties createConfiguration() {
		return new Properties() {
			{
				put("mail.smtp.host", "mail.almullagroup.com");
				put("mail.smtp.port", "25");
				put("mail.smtp.auth", "true");
				put("mail.smtp.starttls.enable", "false");
			}
		};
	}

	private static class SmtpAuthenticator extends Authenticator {

		private final String username = "service.management";
		private final String password = "tne0115M";

		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}

}
