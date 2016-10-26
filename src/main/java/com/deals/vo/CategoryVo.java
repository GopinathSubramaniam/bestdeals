package com.deals.vo;

import com.deals.model.Category;
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

	public CategoryVo() {}

	public CategoryVo (long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
}
