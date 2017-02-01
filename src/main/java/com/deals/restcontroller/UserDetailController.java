package com.deals.restcontroller;

import com.deals.model.UserDetail;
import com.deals.service.UserDetailService;
import com.deals.service.UserService;
import com.deals.util.App;
import com.deals.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
		if(userDetail != null){
			userDetail = userDetailService.update(userDetail);
			return App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, userDetail);
		}
		return App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, userDetail);
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
		return userDetailService.findLikeCityAndPlaceName(cityName);
	}
	
}
