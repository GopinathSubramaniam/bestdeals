package com.deals.model;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Setter
@Getter
public class EmailDetail {

	private String receiverMail;
	private String receiverName;
	private String message;
	private String subject;
	
}
