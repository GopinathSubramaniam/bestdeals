package com.deals.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.deals.enums.DealType;
import com.deals.enums.Priority;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
public class Deal  extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String name;
	private String imgUrl;
	private String description;
	
	@Enumerated(EnumType.STRING)
	private DealType type;
	
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	@ManyToOne
	private SubCategory subCategory;
	
	@ManyToOne
	private City city;
	private String placeName; //Village Name
	
	@ManyToOne
	private User user;
	
}
