package com.deals.model;

import com.deals.enums.PlanType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@ToString
@Setter
@Getter
@Entity
public class PublicUserPlan extends BaseEntity {
	
	@Enumerated(EnumType.STRING)
	private PlanType planType;
	private String description;
	private Double percentage;
	
	@Column(columnDefinition="Decimal(10,2) default '0.00'")
	private Double amount = 0.00;
	private Integer validityInMonths = 0;
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	private Date endDate;	
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private QRCode qrCode;

}
