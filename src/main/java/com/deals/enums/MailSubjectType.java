package com.deals.enums;

public enum MailSubjectType {
	
	MailSubjectType("WELCOME", "Welcome to BestDeals !!!!");
	
	
	private final String mailType;
	private final String subject;
	
	
//	MailSubjectType("WELCOME", "");
	
	private MailSubjectType(String mailType, String subject){
		this.mailType = mailType;
		this.subject = subject;
	}
	
	
	
	public String getSubject(){
		return subject;
	}
	
	public String getMailType(){
		return mailType;
	}
	
	
	
}
