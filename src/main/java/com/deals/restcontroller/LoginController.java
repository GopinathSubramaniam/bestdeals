package com.deals.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deals.model.User;
import com.deals.service.LoginService;
import com.deals.util.Status;

@RestController
@RequestMapping("/rest/login")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value="/", method= RequestMethod.POST, produces={"application/json"})
	public Status login(@RequestBody User user){
		return loginService.login(user);
	}
	
	@RequestMapping(value="/out", method= RequestMethod.POST, produces={"application/json"})
	public Status logout(@RequestBody User user){
		return loginService.logout(user);
	}
	
}
