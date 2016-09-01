package com.deals.model;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ToString
public class AdminDetail {

	private String name;
	private Long count;
	private String iconName;
	private String colorName;
	private String landingPath;
	
}
