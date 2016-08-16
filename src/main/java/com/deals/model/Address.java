package com.deals.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
public class Address {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String address1;
	private String address2;
	private String address3;
	
	@ManyToOne
	private City city;
	
	@ManyToOne
	private State state;
	
	@ManyToOne
	private Country country;
	
}
