package com.deals.vo;

import com.deals.model.Deal;
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

	public DealVO() {}
	public DealVO(Deal deal, UserVO userVO) {
		this.id = deal.getId();
		this.name = deal.getName();
		this.imgUrl = deal.getImgUrl();
		this.type = deal.getType().name();
		this.description = deal.getDescription();
//		this.stateName = deal
		this.cityName = deal.getCity() == null ? "" : deal.getCity().getName();
		this.placeName = deal.getPlaceName();
		this.user = userVO;
	}
}
