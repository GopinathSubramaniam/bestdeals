package com.deals.model;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@ToString
@Setter
@Getter
@Entity
public class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private String description;
	private String imgUrl;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<SubCategory> subCategories;
	
}
