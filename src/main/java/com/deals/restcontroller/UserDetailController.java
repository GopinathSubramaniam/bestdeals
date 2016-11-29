package com.deals.restcontroller;

import com.deals.service.UserService;
import com.deals.util.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	private UserService userService;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@RequestMapping(value="/", method=RequestMethod.POST, produces={"application/json"})
	public Status create(@RequestBody UserDetail userDetail){
		return userDetailService.create(userDetail);
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT, produces={"application/json"})
	public Status update(@RequestBody UserDetail userDetail){
		if ( userDetail != null && userDetail.getUser() != null && userDetail.getUser().getId() != 0) {
			userService.update(userDetail.getUser());
		}
		return userDetailService.update(userDetail);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Status findById(@PathVariable Long id){
		return App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, userDetailService.findOne(id));
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
