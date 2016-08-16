package com.deals.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.deals.enums.DealType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
public class Deal  extends BaseEntity{

	private String name;
	
	@Enumerated(EnumType.STRING)
	private DealType type;
	
	private String description;
	
	@ManyToOne
	private Company company;
	
}
