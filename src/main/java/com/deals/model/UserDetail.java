package com.deals.model;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
	@Column(precision=10, scale=8)
	private double latitude;
	@Column(precision=11, scale=8)
	private double longitude;
	private String description;
	private Long likes;
	private Long views;
	private String phoneNumbers;
	private String placeName;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Village village;
	
}
