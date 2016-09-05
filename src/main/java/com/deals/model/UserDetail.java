package com.deals.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Getter
@Setter
@Entity
public class UserDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String shopName;
	private String address1;
	private String address2;
	private String address3;
	private String latitude;
	private String longitude;
	private String timings;
	private Integer likes;
	private Integer views;
	private String phoneNumbers;
	private String placeName;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private City city;
	
	@ManyToOne
	private State state;
	
	@ManyToOne
	private Country country;

}
