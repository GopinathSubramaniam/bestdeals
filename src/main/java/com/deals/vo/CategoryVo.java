package com.deals.vo;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Getter
@Setter
public class CategoryVo {

	private Long id;
	private String name;
	private String description;
	private String imgUrl;

	public CategoryVo() {}

	public CategoryVo (long id, String name, String description, String imgUrl) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.imgUrl = imgUrl;
	}
}
