package com.deals.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.deals.enums.PlanType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
public class Plan  extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private String name;
	private Double amount;
	private String description;
	private String rules;
	
	@Enumerated(EnumType.STRING)
	private PlanType planType;
	
}
