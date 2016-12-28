package com.deals.restcontroller;

//@RestController
//@RequestMapping("/rest/mail")
public class MailController {
/*
	@Autowired
	private MailService mailService;
	
	@Value("${sender.mail}")
	private String fromAddress;
	
	@Value("${sender.name}")
	private String senderName;
	
	@Value("${mail.template}")
	private String templateName;
	
	
	@RequestMapping(value="/send", method=RequestMethod.POST, produces={"application/json"})
	public Status sendMail(@RequestBody EMail eMail){
		eMail.setSenderEmail(fromAddress);
		eMail.setSenderName(senderName);
		eMail.setTemplateName(templateName);
		
		String message = "";
		String subject = "";
		if(eMail.getEmailType().equals(EmailType.OTP)){
			message = App.MSG_USE_OTP_LOGIN+App.generateKey(6);	
			subject = App.MSG_OTP_SUBJECT;
		}
		if(eMail.getEmailType().equals(EmailType.WELCOME)){
			message = App.MSG_MAIL_BODY;
			subject = App.MSG_MAIL_SUBJECT;
		}
		eMail.getEmailDetail().setMessage(message);
		eMail.getEmailDetail().setSubject(subject);
		
		return mailService.sendMail(eMail);
	}*/
}
