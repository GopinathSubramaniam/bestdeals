package com.deals.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Setter
@Getter
@Entity
public class OTP extends BaseEntity{
	
	private String otpCode;
	
	@ManyToOne
	private User user;

}