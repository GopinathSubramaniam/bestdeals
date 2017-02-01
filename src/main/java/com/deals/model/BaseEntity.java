package com.deals.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@ToString
@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {
	private static final Logger log = LoggerFactory.getLogger(BaseEntity.class);
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
		if (log.isDebugEnabled())
			log.debug(this.getClass().getName() + " Updated");
	}
	
	@PrePersist
	public void create(){
		this.createdDate = new Date();
		this.changedDate = new Date();
	}
	
}
