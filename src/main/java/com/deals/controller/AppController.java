package com.deals.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

	
	@RequestMapping(value="/")
	public String login(Model model){
		model.addAttribute("message", "Login in BestDeals");
		return "login";
	}
	
	@RequestMapping(value="/home")
	public String home(Model model){
		model.addAttribute("message", "Welcome to BestDeals API Section!!!");
		return "greetings";
	}
}
