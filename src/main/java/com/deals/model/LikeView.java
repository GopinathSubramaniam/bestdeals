package com.deals.model;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Setter
@Getter
@ToString
@Entity
public class LikeView {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private Long merchantId;
	private Long userId;
	private String type;
	
	public LikeView(){}
	
	public LikeView(Long id) {
		super();
		this.id = id;
	}
	
	public LikeView(Long merchantId, Long userId) {
		super();
		this.merchantId = merchantId;
		this.userId = userId;
	}
	
	
}
