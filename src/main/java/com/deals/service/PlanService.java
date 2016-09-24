package com.deals.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.model.Plan;
import com.deals.model.User;
import com.deals.repository.PlanRepository;
import com.deals.repository.UserRepository;
import com.deals.util.App;
import com.deals.util.Status;

@Service
public class PlanService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static Status status = new Status();
	
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	public Status create(Plan plan){
		if(plan !=null){
			plan = planRepository.saveAndFlush(plan);
			status = App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, plan);
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, null);
		}
		return status;
	}
	
	public Status findAll(){
		List<Plan> plans = planRepository.findAll();
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, plans);
		return status;
	}
	
	public Status findOne(Long id){
		log.info("FindPlan PlanId ::: "+id);
		Plan plan = null;
		if(id !=null){
			plan = planRepository.findOne(id);
		}
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, plan);
		return status;
	}
	
	public Status update(Plan plan){
		Plan existingPlan = planRepository.findOne(plan.getId());
		existingPlan.setName(plan.getName());
		existingPlan.setAmount(plan.getAmount());
		existingPlan.setDescription(plan.getDescription());
		existingPlan.setPlanType(plan.getPlanType());
		
		plan = planRepository.saveAndFlush(existingPlan);
		status = App.getResponse(App.CODE_OK, App.STATUS_UPDATE, App.MSG_UPDATE, plan);
		return status;
	}
	
	public Status delete(Long id){
		planRepository.delete(id);
		status = App.getResponse(App.CODE_OK, App.STATUS_DELETE, App.MSG_DELETE, null);
		return status;
	}
	
	public Status assignPlanToUser(Long userId, Long planId){
		Plan plan = planRepository.findOne(planId);
		User user = userRepository.findOne(userId);
		user.setPlan(plan);
		user = userRepository.saveAndFlush(user);
		status = App.getResponse(App.CODE_OK, App.STATUS_UPDATE, App.MSG_UPDATE, user);
		return status;
	}
	
}
