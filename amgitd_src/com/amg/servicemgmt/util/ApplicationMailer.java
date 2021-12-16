package com.amg.servicemgmt.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("mailService")
public class ApplicationMailer {

	@Autowired(required = true)
	private JavaMailSender mailSender2;
	
	

    @Autowired
    private SimpleMailMessage preConfiguredMessage;
    
    /**
     * This method will send a pre-configured message
     * */
    public void sendPreConfiguredMail(String message)
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
        mailMessage.setText(message);
        mailSender2.send(mailMessage);
    }
 
	public JavaMailSender getMailSender() {
		return mailSender2;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender2 = mailSender;
	}

	public void sendRegistrationMail(String to, String subject, String username) {
		
		try{
			final MimeMessage mimeMessage = mailSender2.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo(to);
			message.setSubject(subject);
			
			
			message.setText(getRegistrationContent().replace("${0}", username), true);
			mailSender2.send(mimeMessage);
		}catch(Exception e){
			System.out.println("Mail not sent to user :: "+e);
		}
	}
	
	public void sendTokenMail(String to, String subject, String customerId, String tokenNumber ) {
		
		try{
			final MimeMessage mimeMessage = mailSender2.createMimeMessage();
			final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
			message.setTo(to);
			message.setSubject(subject);
			
			
			message.setText((getTokenGenerationContent().replace("${0}", customerId)).replace("${1}", tokenNumber), true);
			mailSender2.send(mimeMessage);
		}catch(Exception e){
			System.out.println("Mail not sent to user :: "+e);
		}
	}
	
	public void sendMail(String to, String subject, String content ) {
		
		try{
		final MimeMessage mimeMessage = mailSender2.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
		message.setTo(to);
		message.setSubject(subject);
		
		message.setText(content);
		
		mailSender2.send(mimeMessage);
		}catch(Exception e){
			System.out.println("Mail not sent to user :: "+e);
		}
	}
	
	private String getRegistrationContent(){
		
		StringBuffer content = new StringBuffer();
		
		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
		.append("<title>Al Mulla Exchange</title>")
		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"left\" valign=\"middle\" bgcolor=\"#00a53d\"><img src=\"https://applications2.almullagroup.com/onlineremit/faces/images/prettyLogo.png\" width=\"264\" height=\"89\" /></td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:#00a53d; padding:5px;\">Al Mulla Exchange</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear <strong style=\"color:#000000;\">Customer</strong> ,<br/><br/>")
		.append("")
		.append("")
		.append("Greetings from Almulla Exchange!<br/><br/>")
		.append("")
		.append("You received this email because you have successfully created online account with us .<br/><br/>")
		.append("")
		.append("<strong style=\"color:#333;\">Your user name :</strong> <strong style=\"color:#257b45;\">${0}</strong><br/><br/>")
		.append("")
		.append("Please <a href=\"http://www.almullagroup.com/contact.aspx\" target=\"_blank\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">contact us</a> should you have any questions or need further assistance.<br/><br/>")
		.append("")
		.append("Thanks for register with us!</td>")
		.append("      </tr>")
		.append("    </table></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#d7d7d7\" style=\" border-top:2px solid #018a34; font-family:Arial, Helvetica, sans-serif; font-size:14px;  color:#000; padding:10px;\"><a href=\"http://www.almullagroup.com/finance/exchange.aspx\" target=\"_blank\" style=\"outline:none; color:#000000; text-decoration:none;\">www.almullaexchange.com</a></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</body>")
		.append("</html>");
		
		
		return content.toString();
	}
	 
	private String getTokenGenerationContent(){
		
		StringBuffer content = new StringBuffer();
		
		content.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">")
		.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">")
		.append("<head>")
		.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />")
		.append("<title>Al Mulla Exchange</title>")
		.append("</head>")
		.append("<body bgcolor=\"#ededed\" style=\"padding:0; margin:0; text-align:center;\">")
		.append("<table width=\"700\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"left\" valign=\"middle\" bgcolor=\"#00a53d\"><img src=\"https://applications2.almullagroup.com/onlineremit/faces/images/prettyLogo.png\" width=\"264\" height=\"89\" /></td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#FFFFFF\" style=\"padding:15px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" style=\"border:1px solid #dcdcdc; padding:2px;\"><table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">")
		.append("      <tr>")
		.append("        <td align=\"center\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:24px; color:#FFF; border-bottom:2px solid #f18300; background-color:#00a53d; padding:5px;\">Al Mulla Exchange</td>")
		.append("      </tr>")
		.append("      <tr>")
		.append("        <td align=\"left\" valign=\"middle\" style=\"font-family:Arial, Helvetica, sans-serif; font-size:15px; line-height:20px; color:#666; padding:30px;\">Dear <strong style=\"color:#000000;\">Customer</strong> ,<br/><br/>")
		.append("")
		.append("")
		.append("Greetings from Almulla Exchange!<br/><br/>")
		.append("")
		.append("You received this email because you have successfully create your account with us Please approach nearest branch to complete your proceeding.<br/><br/>")
		.append("")
		.append("<strong style=\"color:#333;\">Your customer id No:</strong> <strong style=\"color:#257b45;\">${0}</strong><br/><br/>")
		.append("<strong style=\"color:#333;\">Your Token No.</strong> <strong style=\"color:#257b45;\">${1}</strong><br/><br/>")
		.append("")
		.append("Please <a href=\"http://www.almullagroup.com/contact.aspx\" target=\"_blank\" style=\"outline:none; color:#f18300; font-weight:bold; text-decoration:none;\">contact us</a> should you have any questions or need further assistance.<br/><br/>")
		.append("")
		.append("Thanks for register with us!</td>")
		.append("      </tr>")
		.append("    </table></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</td>")
		.append("  </tr>")
		.append("  <tr>")
		.append("    <td align=\"center\" valign=\"middle\" bgcolor=\"#d7d7d7\" style=\" border-top:2px solid #018a34; font-family:Arial, Helvetica, sans-serif; font-size:14px;  color:#000; padding:10px;\"><a href=\"http://www.almullagroup.com/finance/exchange.aspx\" target=\"_blank\" style=\"outline:none; color:#000000; text-decoration:none;\">www.almullaexchange.com</a></td>")
		.append("  </tr>")
		.append("</table>")
		.append("</body>")
		.append("</html>");
		
		return content.toString();
	}
}