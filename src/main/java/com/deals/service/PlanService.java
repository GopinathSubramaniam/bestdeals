package com.deals.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.model.Plan;
import com.deals.repository.PlanRepository;
import com.deals.util.App;
import com.deals.util.Status;

@Service
public class PlanService {

	private static Status status = new Status();
	
	@Autowired
	private PlanRepository planRepository;
	
	
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
	
	public Status update(Plan plan){
		plan = planRepository.saveAndFlush(plan);
		status = App.getResponse(App.CODE_OK, App.STATUS_UPDATE, App.MSG_UPDATE, plan);
		return status;
	}
	
	public Status delete(Long id){
		planRepository.delete(id);
		status = App.getResponse(App.CODE_OK, App.STATUS_DELETE, App.MSG_DELETE, null);
		return status;
	}
	
}
