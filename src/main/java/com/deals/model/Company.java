package com.deals.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.deals.enums.CompanyType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
public class Company extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String mobile;
	private String phone;
	private String email;
	private String address1;
	private String address2;
	
	@Enumerated(EnumType.STRING)
	private CompanyType companyType;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private City city;
	
	@ManyToOne
	private State state;
	
	@ManyToOne
	private Country country;
	
	
}
