package com.deals.vo;

import java.util.ArrayList;
import java.util.List;

import com.deals.enums.UserType;
import com.deals.model.Deal;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Setter
@Getter
public class UserVO {

	private Long id;
	private String createdDate;
	private Long userDetailId;
	private String name;
	private UserType userType;
	private String password;
	private String email;
	private String mobile;
	private String address1;
	private String address2;
	private String address3;
	private List<String> phoneNumbers = new ArrayList<String>();
	private String timings;
	private Long likes;
	private Long views;
	private String latitude;
	private String longitude;
	private List<Deal> deals;
	private List<ImageVo> imageUrls;
	
	private String placeName; //Village Name
	private String shopName; //Shop Name
	private String cityName;
	private String stateName;
	private String description;
	private String qrCode;
	private String encryptedQrCode;
	
	private Long planId;
	private String planName;
	private String planExpiryDate;
	private String planDescription;
	
	
}
