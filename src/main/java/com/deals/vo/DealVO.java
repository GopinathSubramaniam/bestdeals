package com.deals.vo;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Getter
@Setter
public class DealVO {

	private Long id;
	private String name;
	private String imgUrl;
	private String type;
	private String description;
	private String stateName;
	private String cityName;
	private String placeName;
	
	private UserVO user;
}
