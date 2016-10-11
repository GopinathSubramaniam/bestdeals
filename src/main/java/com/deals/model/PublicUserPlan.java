package com.deals.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
	private Integer validatyInMonths;
	private Date startDate;
	private Date endDate;	
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private QRCode qrCode;

}
