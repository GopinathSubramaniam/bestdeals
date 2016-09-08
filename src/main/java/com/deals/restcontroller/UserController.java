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
	
	@RequestMapping(value="/sendOTP/{mobNumber}", method=RequestMethod.GET)
	public Status sendOTP(@PathVariable String mobNumber){
		return userService.sendOTP(mobNumber);
	}
	
	@RequestMapping(value="/verifyOTP/{mobileNumber}/{oneTimePassword}", method=RequestMethod.GET)
	public Status verifyOTP(@PathVariable String mobileNumber, @PathVariable String oneTimePassword){
		return userService.verifyOTP(mobileNumber, oneTimePassword);
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST, produces={"application/json"})
	public Status create(@RequestBody User user){
		return userService.create(user);
	}
	
	@RequestMapping(value="/", method=RequestMethod.PUT, produces={"application/json"})
	public Status update(@RequestBody User user){
		return userService.update(user);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public Status delete(@PathVariable Long id){
		return userService.delete(id);
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public Status findAll(){
		return userService.findAll();
	}
	
	@RequestMapping(value="/findAllByUserType/{userType}", method=RequestMethod.GET)
	public Status findAllByUserType(@PathVariable UserType userType){
		return userService.findAllByType(userType);
	}
	
	@RequestMapping(value="/saveUserDetail", method=RequestMethod.POST, produces={"application/json"})
	public Status updateUserDetail(@RequestBody UserDetail userDetail){
		return userService.saveUserDetail(userDetail);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Status get(@PathVariable Long id){
		Status status = userService.findUser(id);
		System.out.println("getUser Status :::: "+status);
		return status;
	}
	
	/*@RequestMapping(value="/verify/{codeOTP}/{loginId}", method=RequestMethod.GET)
	public Status verifyOTP(@PathVariable String codeOTP, @PathVariable Long loginId){
		return userService.verifyOTP(codeOTP, loginId);
	}*/
	
	@RequestMapping(value="/findByUserType/{userType}", method=RequestMethod.GET)
	public Status findUsersByUserType(@PathVariable UserType userType){
		return userService.findUsersByUserType(userType);
	}
	
}
