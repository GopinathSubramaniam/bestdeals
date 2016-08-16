package com.deals.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deals.model.City;
import com.deals.model.Country;
import com.deals.model.State;
import com.deals.repository.CityRepository;
import com.deals.repository.CountryRepository;
import com.deals.repository.StateRepository;
import com.deals.util.App;
import com.deals.util.Status;

@RestController
@RequestMapping("/rest/app/")
public class MainController {

	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	private static Status status = new Status();
	
	@RequestMapping(value="/findAllCountry", method=RequestMethod.GET)
	public Status findAllCountry(){
		List<Country> countries = countryRepository.findAll();
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, countries);
		return status;
	}

	@RequestMapping(value="/findAllState/{countryId}", method=RequestMethod.GET)
	public Status findAllState(@PathVariable Long countryId){
		List<State> states = stateRepository.findAllByCountryId(countryId);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, states);
		return status;
	}

	@RequestMapping(value="/findAllCity/{stateId}", method=RequestMethod.GET)
	public Status findAllCity(@PathVariable Long stateId){
		List<City> cities = cityRepository.findAllByStateId(stateId);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, cities);
		return status;
	}
	
}
