package com.deals.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deals.model.City;
import com.deals.repository.CityRepository;
import com.deals.repository.UserDetailRepository;
import com.deals.util.App;
import com.deals.util.Status;
import com.deals.vo.PlaceNameResponseVo;

@RestController
@RequestMapping(value="/rest/base/")
public class BaseController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static Status status =  new Status();
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private UserDetailRepository userDetailRepository;
	
	@RequestMapping(value="/findAllCityByState/{stateId}", method = RequestMethod.GET)
	public Status findAllCityByState(@PathVariable Long stateId){
		List<City> cities = cityRepository.findAllByStateId(stateId);
		log.info("Cities ::: "+cities);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, cities);
		return status;
	}
	
	@RequestMapping(value="/findAllCities", method = RequestMethod.GET)
	public Status findAllCities(){
		List<City> cities = cityRepository.findAll();
		log.info("Cities ::: "+cities);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, cities);
		return status;
	}
	
	@RequestMapping(value="/findAllPlaceNames/{cityId}", method = RequestMethod.GET)
	public Status findAllPlaceNames(@PathVariable Long cityId){
		List<String> placeNames = userDetailRepository.findAllPlaceNameByCityId(cityId);
		PlaceNameResponseVo nameResponseVo = new PlaceNameResponseVo();
		nameResponseVo.setCityId(cityId);
		nameResponseVo.setPlaceNames(placeNames);
		log.info("PlaceNameResponseVos ::: "+nameResponseVo);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, nameResponseVo);
		return status;
	}
	
	
	
	
	

}
