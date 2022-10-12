package in.ashokit.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
	
      @Autowired
      JavaMailSender mailSender;
      
      
   public boolean sendEmail(String to, String body ) {
    	  boolean isMailSent = false;
    	  MimeMessage mimeMessage = mailSender.createMimeMessage();
    	  MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
    	  try {
			helper.setTo(to);
			helper.setSubject("Mail from user management");
	    	helper.setText(body);
	    	mailSender.send(mimeMessage);
	    	isMailSent = true;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	  
    	  return isMailSent;	  
      }
   }	


