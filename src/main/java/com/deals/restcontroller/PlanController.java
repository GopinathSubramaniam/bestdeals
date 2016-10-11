package com.deals.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deals.model.Plan;
import com.deals.service.PlanService;
import com.deals.util.Status;

@RestController
@RequestMapping("/rest/plan")
public class PlanController {

	@Autowired
	private PlanService planService;
	
	@RequestMapping(value="/", method= RequestMethod.POST, produces={"application/json"})
	public Status create(@RequestBody Plan plan){
		return planService.create(plan);
	}
	
	@RequestMapping(value="/", method= RequestMethod.GET)
	public Status findAll(){
		return planService.findAll();
	}
	
	@RequestMapping(value="/{id}", method= RequestMethod.GET)
	public Status findOne(@PathVariable Long id){
		return planService.findOne(id);
	}
	
	@RequestMapping(value="/", method= RequestMethod.PUT, produces={"application/json"})
	public Status update(@RequestBody Plan plan){
		return planService.update(plan);
	}
	
	@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
	public Status delete(@PathVariable Long id){
		return planService.delete(id);
	}
	
	@RequestMapping(value="/{userId}/{planId}", method= RequestMethod.GET)
	public Status assignPlanToUser(@PathVariable Long userId, @PathVariable Long planId){
		return planService.assignPlanToUser(userId, planId);
	}
	
}
