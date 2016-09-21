package com.deals.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Date createdDate;
	private Date changedDate;
	private String createdBy;
	private String changedBy;
	
	
	@PreUpdate
	public void update(){
		this.changedDate = new Date();
	}
	
	@PrePersist
	public void create(){
		this.createdDate = new Date();
		this.changedDate = new Date();
	}
	
}
