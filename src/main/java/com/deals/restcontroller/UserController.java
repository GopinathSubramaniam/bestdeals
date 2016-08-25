package com.deals.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deals.enums.UserType;
import com.deals.model.User;
import com.deals.model.UserDetail;
import com.deals.service.UserService;
import com.deals.util.Status;

@RestController
@RequestMapping("/rest/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/", method=RequestMethod.POST, produces={"application/json"})
	public Status create(@RequestBody User user){
		return userService.create(user);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public Status delete(@PathVariable Long id){
		return userService.delete(id);
	}
	
	@RequestMapping(value="/saveUserDetail", method=RequestMethod.POST, produces={"application/json"})
	public Status updateUserDetail(@RequestBody UserDetail userDetail){
		return userService.saveUserDetail(userDetail);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Status get(@PathVariable Long id){
		Status status = userService.getUser(id);
		System.out.println("getUser Status :::: "+status);
		return status;
	}
	
	@RequestMapping(value="/verify/{codeOTP}/{loginId}", method=RequestMethod.GET)
	public Status verifyOTP(@PathVariable String codeOTP, @PathVariable Long loginId){
		return userService.verifyOTP(codeOTP, loginId);
	}
	
	@RequestMapping(value="/findByUserType/{userType}", method=RequestMethod.GET)
	public Status findUsersByUserType(@PathVariable UserType userType){
		return userService.findUsersByUserType(userType);
	}
	
}
