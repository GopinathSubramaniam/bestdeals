package com.deals.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.deals.enums.PlanType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
public class PublicUserPlan {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private PlanType planType;
	private String description;
	private Double percentage;
	
	@Column(columnDefinition="Decimal(10,2) default '0.00'")
	private Double amount = 0.00;
	private Integer validityInMonths = 6;
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	private Date endDate;	
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private QRCode qrCode;

}
