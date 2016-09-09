package com.deals.vo;

import org.springframework.web.multipart.MultipartFile;

import com.deals.model.Deal;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ToString
public class DealObjVO {

	private MultipartFile file;
	private Deal deal;
	
	
}
