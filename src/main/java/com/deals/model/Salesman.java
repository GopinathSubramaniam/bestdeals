package com.deals.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
public class Salesman extends BaseEntity{

	
	private String name;
	private String email;
	private String mobile;
	private String password;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Company company;
	
	@ManyToOne
	private SalesManager salesManager;
	
}
