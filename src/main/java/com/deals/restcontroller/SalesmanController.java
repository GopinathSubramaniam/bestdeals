package com.deals.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deals.model.SalesManager;
import com.deals.model.Salesman;
import com.deals.service.SalesmanService;
import com.deals.util.Status;

@RestController
@RequestMapping("/rest/salesman")
public class SalesmanController {

	@Autowired
	private SalesmanService salesmanService;
	
	@RequestMapping(value="/", method=RequestMethod.POST, produces={"application/json"})
	public Status create(@RequestBody Salesman salesman){
		return salesmanService.create(salesman);
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT, produces={"application/json"})
	public Status update(@RequestBody Salesman salesman){
		return salesmanService.create(salesman);
	}
	
	@RequestMapping(value="/{salesmanId}", method=RequestMethod.GET)
	public Status findById(@PathVariable Long salesmanId){
		return salesmanService.findSalesManById(salesmanId);
	}
	
	@RequestMapping(value="/findAllSalesManager", method=RequestMethod.GET)
	public Status findAllSalesManager(){
		return salesmanService.findAllSalesManager();
	}
	
	@RequestMapping(value="/{salesmanId}", method=RequestMethod.DELETE)
	public Status delete(@PathVariable Long salesmanId){
		return salesmanService.deleteSalesManById(salesmanId);
	}
	
	@RequestMapping(value="/findAllByManagerId/{managerId}", method=RequestMethod.GET)
	public Status findAllByManagerId(@PathVariable Long managerId){
		return salesmanService.findAllBySalesManager(managerId);
	}
	
	@RequestMapping(value="/createSalesManager", method=RequestMethod.POST, produces={"application/json"})
	public Status createSalesManager(@RequestBody SalesManager salesManager){
		return salesmanService.createSalesManager(salesManager);
	}
	
}
