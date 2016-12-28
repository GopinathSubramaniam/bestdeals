package com.deals.vo;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ToString
@Getter
@Setter
public class PlaceNameResponseVo {

	private List<String> placeNames;
	private Long cityId;
	
}
