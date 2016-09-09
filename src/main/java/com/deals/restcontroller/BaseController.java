package com.deals.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deals.model.City;
import com.deals.repository.CityRepository;
import com.deals.repository.StateRepository;
import com.deals.util.App;
import com.deals.util.Status;

@RestController
@RequestMapping(value="/rest/base/")
public class BaseController {
	
	private static Status status =  new Status();
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@RequestMapping(value="/findAllCityByState/{stateId}", method = RequestMethod.GET)
	public Status findAllCityByState(@PathVariable Long stateId){
		List<City> cities = cityRepository.findAllByStateId(stateId);
		System.out.println("Cities ::: "+cities);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, cities);
		return status;
	}
	
	
	

}
