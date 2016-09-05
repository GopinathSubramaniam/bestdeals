package com.deals.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deals.service.DealService;
import com.deals.util.Status;

@RestController
@RequestMapping("/rest/deal")
public class DealController {

	@Autowired
	private DealService dealService;
	
	@RequestMapping(value="/{userId}", method= RequestMethod.GET)
	public Status findAllByUserId(@PathVariable Long userId){
		return dealService.findAllByUserId(userId);
	} 
	
	@RequestMapping(value="/", method= RequestMethod.GET)
	public Status findAll(){
		return dealService.findAll();
	} 
	
	@RequestMapping(value="/findAllBySubCat/{subCatId}", method= RequestMethod.GET)
	public Status findAllBySubCat(@PathVariable Long subCatId){
		return dealService.findAllBySubCat(subCatId);
	} 
}
