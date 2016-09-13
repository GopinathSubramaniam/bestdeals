package com.deals.model;

import com.deals.enums.EmailType;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Setter
@Getter
public class EMail {

	private String senderEmail;
	private String senderName;
	private EmailDetail emailDetail;
	private String templateName;
	private EmailType emailType;

	public EMail(){}
	public EMail(String senderEmail, String senderName, EmailDetail emailDetail, String templateName,
			EmailType emailType) {
		super();
		this.senderEmail = senderEmail;
		this.senderName = senderName;
		this.emailDetail = emailDetail;
		this.templateName = templateName;
		this.emailType = emailType;
	}
	
}
