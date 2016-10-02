package com.deals.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
public class Village {
	
	@Id
	private Long id;
	private String name;
	
	@ManyToOne
	private Taluka taluka;
	
	public Village(){}
	public Village(Long id){
		super();
		this.id = id;
	}
	
}
