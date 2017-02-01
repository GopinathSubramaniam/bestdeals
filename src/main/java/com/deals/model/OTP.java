package com.deals.model;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@ToString
@Setter
@Getter
//@Entity
public class OTP extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String otpCode;
	
	@ManyToOne
	private User user;

}
