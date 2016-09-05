package com.deals.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.enums.PlanType;
import com.deals.enums.Priority;
import com.deals.model.Deal;
import com.deals.repository.DealRepository;
import com.deals.util.App;
import com.deals.util.Status;

@Service
public class DealService {
	private static Status status =  new Status();
	private static Integer ADVERTISTMENT_MAX_COUNT = 15;
	
	@Autowired
	private DealRepository dealRepository;
	
	public Status findAllByUserId(Long userId){
		List<Deal> deals = dealRepository.findAllByUserId(userId);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, deals);
		return status;
	}
	
	public Status findAll(){
		List<Deal> highDeals = dealRepository.findAllByPriorityAndUserPlanPlanType(Priority.HIGH, PlanType.GOLD);
		System.out.println("High Deals :::: "+highDeals);
		
		if(highDeals.size() < ADVERTISTMENT_MAX_COUNT){
			List<Deal> mediumDeals = dealRepository.findAllByPriorityAndUserPlanPlanType(Priority.MEDIUM, PlanType.PLATINUM);
			System.out.println("Medium Deals :::: "+mediumDeals);
			for (Deal deal : mediumDeals) {
				if(highDeals.size() < ADVERTISTMENT_MAX_COUNT){
					highDeals.add(deal);
				}else{
					break;
				}
			}
		}
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, highDeals);
		return status;
	}
	
}
