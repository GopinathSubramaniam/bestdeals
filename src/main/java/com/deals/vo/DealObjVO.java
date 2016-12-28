package com.deals.vo;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@ToString
public class DealObjVO {

	private MultipartFile file;
	private String category;
	private String subCategory;
	private String state;
	private String city;
	private String placeName;
	private String name;
	private String description;
	private String contact;
	
	
}
