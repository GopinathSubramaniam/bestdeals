package com.deals.vo;

import com.deals.enums.PlanType;
import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@ToString
@Getter
@Setter
public class PublicPlanResponse {
	private Long id;
	private PlanType planType;
	private String description;
	private Double percentage;
	private Double amount = 0.00;
	private Integer validityInMonths = 6;
	private Date startDate;
	private Date endDate;	
}
