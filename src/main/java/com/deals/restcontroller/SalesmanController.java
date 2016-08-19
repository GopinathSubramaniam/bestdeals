package com.deals.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deals.model.Salesman;
import com.deals.service.SalesmanService;
import com.deals.util.Status;

@RestController
@RequestMapping("/salesman")
public class SalesmanController {

	@Autowired
	private SalesmanService salesmanService;
	
	@RequestMapping(value="/", method=RequestMethod.POST, produces={"application/json"})
	public Status create(@RequestBody Salesman salesman){
		return salesmanService.create(salesman);
	}
	
	@RequestMapping(value="/findAllByManagerId/{managerId}", method=RequestMethod.POST, produces={"application/json"})
	public Status findAllByManagerId(@PathVariable Long managerId){
		return salesmanService.findAllBySalesManager(managerId);
	}
	
}
