package com.deals.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.deals.enums.PlanType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
public class Plan  extends BaseEntity{

	private String name;
	private Double amount;
	private String description;
	
	@Enumerated(EnumType.STRING)
	private PlanType planType;
	
	@ManyToOne
	private Company company;
	
	
}
