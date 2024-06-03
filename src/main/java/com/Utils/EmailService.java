package com.Utils;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService {
	
	private final JavaMailSender javaMailSender;
	
	
	public void sendEmail(String to,String subject,String body) {
		
		 try {
	            MimeMessage message = javaMailSender.createMimeMessage();
	            MimeMessageHelper helper = new MimeMessageHelper(message,true);
	            helper.setTo(to);
	            helper.setSubject(subject);
	            helper.setText(body,true);
	            javaMailSender.send(message);
	        } catch (MessagingException e) {
	            e.printStackTrace();
	            e.getMessage();
	            throw new RuntimeException(e);
	        }
	}
}
