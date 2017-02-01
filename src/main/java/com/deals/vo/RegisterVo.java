package com.deals.vo;

import com.deals.enums.UserType;
import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Getter
@Setter
public class RegisterVo {

	private UserType userType;
	private String name;
	private String mobile;
	private String email;
	private String password;
	private String description; // Show discription
	private double latitude;
	private double longitude;
	
	private String shopName;
	private String address1;
	private String address2;
	private String address3;
	private String phoneNumbers;
	private Long city;
	private Long taluka;
	private Long village;

}
