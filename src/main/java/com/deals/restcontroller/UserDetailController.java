package com.deals.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deals.model.UserDetail;
import com.deals.service.UserDetailService;
import com.deals.util.Status;

@RestController
@RequestMapping("/rest/userDetail")
public class UserDetailController {

	
	@Autowired
	private UserDetailService userDetailService;
	
	@RequestMapping(value="/", method=RequestMethod.POST, produces={"application/json"})
	public Status create(UserDetail userDetail){
		return userDetailService.create(userDetail);
	}
	
	@RequestMapping(value="/findAllByUserId/{userId}", method=RequestMethod.GET)
	public Status findAllByUserId(@PathVariable Long userId){
		return userDetailService.findAllByUserId(userId);
	}
	
	@RequestMapping(value="/findLikeCityAndPlaceName/{cityName}/{placeName}", method=RequestMethod.GET)
	public Status findLikeCityAndPlaceName( @PathVariable String cityName, @PathVariable String placeName){
		return userDetailService.findLikeCityAndPlaceName(cityName, placeName);
	}
	
}
