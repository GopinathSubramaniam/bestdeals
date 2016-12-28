package com.deals.model;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@ToString
@Setter
@Getter
@Entity
public class SubCategory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private String description;
	private Integer stepId;
	
	@ManyToOne
	private Category category;
	
	public SubCategory(){}
	
	public SubCategory(Long id){
		super();
		this.id = id;
	}
	

}
