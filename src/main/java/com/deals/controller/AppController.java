package com.deals.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deals.service.SalesmanService;
import com.deals.service.UserService;
import com.deals.util.App;
import com.deals.util.Status;

@Controller
public class AppController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SalesmanService salesmanService;
	
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
	
	@RequestMapping(value="/salesman")
	public String salespeople(Model model){
		Status status = salesmanService.findAllSalesMan();
		model.addAttribute("title", "Sales List");
		model.addAttribute("popupTitle", "Creaet New SalesMan");
		model.addAttribute("salesmans", status.getData());
		model.addAttribute("tab", App.SALE);
		return "salesman";
	}
	
	@RequestMapping(value="/user")
	public String user(Model model){
		Status status = userService.findAll();
		model.addAttribute("users", status.getData());
		model.addAttribute("title", "Users List");
		model.addAttribute("popupTitle", "Creaet New User");
		model.addAttribute("tab", App.USER);
		model.addAttribute("addNewBtnText", "Add New User");
		return "user";
	}
	
}
