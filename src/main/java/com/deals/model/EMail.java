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
}
