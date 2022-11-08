package com.bridgelabz.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.bridgelabz.model.EmailDetails;

@Component
public class JavaEmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

//	public String sendSimpleMail(EmailDetails details) {

//		// Try block to check for exceptions
//		try {
//
//			// Creating a simple mail message
//			SimpleMailMessage mailMessage = new SimpleMailMessage();
//
//			// Setting up necessary details
//			mailMessage.setFrom(sender);
//			mailMessage.setTo(details.getRecipient());
//			mailMessage.setText(details.getMsgBody());
//			mailMessage.setSubject(details.getSubject());
//
//			// Sending the mail
//			javaMailSender.send(mailMessage);
//			return "Mail Sent Successfully...";
//		}
//
//		// Catch block to handle the exceptions
//		catch (Exception e) {
//			return "Error while Sending Mail";
//		}
//	}

	public String sendSimpleMail(String email, String token, String subject) {

		try {

			SimpleMailMessage mailMessage = new SimpleMailMessage();

			mailMessage.setFrom(sender);
			mailMessage.setTo(email);
			mailMessage.setText(token);
			mailMessage.setSubject(subject);

			javaMailSender.send(mailMessage);
			return "Mail Sent Successfully...";
		}

		catch (Exception e) {
			return "Error while Sending Mail";
		}
	}

}
