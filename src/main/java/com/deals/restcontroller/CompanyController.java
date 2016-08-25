package com.deals.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deals.model.Company;
import com.deals.service.CompanyService;
import com.deals.util.Status;

@RestController
@RequestMapping("/rest/company")
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public Status create(@RequestBody Company company){
		return companyService.create(company);
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public Status findAll(){
		return companyService.findAll();
	}
	
}
