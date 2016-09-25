package com.deals.vo;

import java.util.List;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Getter
@Setter
public class PlaceNameResponseVo {

	private List<String> placeNames;
	private Long cityId;
	
}
