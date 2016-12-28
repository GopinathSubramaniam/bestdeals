package com.deals.restcontroller;

import com.deals.model.User;
import com.deals.service.LoginService;
import com.deals.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/login")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value={"/mobileLogin/","/mobileLogin"}, method= RequestMethod.POST, produces={"application/json"})
	public Status mobileLogin(@RequestBody User user){
		return loginService.mobileLogin(user);
	}

	@RequestMapping(value="/out/{userId}", method= RequestMethod.GET)
	public Status logout(@PathVariable Long userId){
		return loginService.logout(userId);
	}
	
}
