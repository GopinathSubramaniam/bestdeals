package com.deals.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

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
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<SubCategory> subCategories;
	
}
