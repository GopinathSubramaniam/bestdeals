package com.deals.service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.model.EMail;
import com.deals.util.App;
import com.deals.util.Status;

import it.ozimov.springboot.templating.mail.model.Email;
import it.ozimov.springboot.templating.mail.model.impl.EmailImpl;
import it.ozimov.springboot.templating.mail.service.EmailService;
import it.ozimov.springboot.templating.mail.service.exception.CannotSendEmailException;

@Service
public class MailService {

	private static Status status = new Status();
	
	@Autowired
	private EmailService emailService;
	
	
	public Status sendMail(EMail eMail){
		System.out.println("Started");
		try {
			List<InternetAddress> toAddresses = Lists.newArrayList();   
			toAddresses.add(new InternetAddress(eMail.getEmailDetail().getReceiverMail(), eMail.getEmailDetail().getReceiverName()));
			InternetAddress fromAddress = new InternetAddress(eMail.getSenderEmail(), eMail.getSenderName());
			final Email email = EmailImpl.builder().from(fromAddress).to(toAddresses).subject(eMail.getEmailDetail().getSubject()).body("").encoding(Charset.forName("UTF-8")).build();
			final Map<String, Object> modelObject = new HashMap<>();
	        modelObject.put("message", eMail.getEmailDetail().getMessage());
	                
	        emailService.send(email, eMail.getTemplateName(), modelObject);
			System.out.println("Done");
			status = App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_MAIL_SENT, null);
		} catch (UnsupportedEncodingException e) {
			status = App.getResponse(App.CODE_FAIL, App.STATUS_CREATE, e.getMessage(), null);
		} catch (CannotSendEmailException e) {
			status = App.getResponse(App.CODE_FAIL, App.STATUS_CREATE, e.getMessage(), null);
			e.printStackTrace();
		}
		
		return status;
	}
	
}