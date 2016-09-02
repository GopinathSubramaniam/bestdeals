package com.deals.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deals.enums.Page;
import com.deals.model.User;
import com.deals.service.AppService;
import com.deals.service.CompanyService;
import com.deals.service.LoginService;
import com.deals.service.PlanService;
import com.deals.service.SalesmanService;
import com.deals.service.UserService;
import com.deals.util.App;
import com.deals.util.Status;

@Controller
public class AppController {

	@Autowired
	private AppService appService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SalesmanService salesmanService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private PlanService planService;
	
	@RequestMapping(value="/")
	public String login(Model model){
		model.addAttribute("message", "Login in BestDeals");
		return "login";
	}

	@RequestMapping(value="/login")
	public String dologin(Model model, User user, HttpServletRequest req){
		user = (User)loginService.login(user).getData();
		if(user != null){
			model.addAttribute("message", "Welcome to BestDeals!!!");
			model.addAttribute("username", user.getName());
			return "greetings";
		}else{
			model.addAttribute("message", "Login in BestDeals");
			model.addAttribute("errorMsg", "Invalid username and password");
			return "login";
		}
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest req){
		HttpSession session = req.getSession();
		if(session != null){
			session.invalidate();
		}
		return "login";
	}
	
	@RequestMapping(value="/home")
	public String home(Model model){
		model.addAttribute("tab", "DASHBOARD");
		model.addAttribute("message", "Welcome to BestDeals API Section!!!");
		model.addAttribute("details", appService.getAdminDetail());
		return "greetings";
	}
	
	@RequestMapping(value="/salesman")
	public String salespeople(Model model){
		
		model.addAttribute("title", "Sales List");
		model.addAttribute("popupTitle", "Creaet New SalesMan");
		model.addAttribute("salesmans", salesmanService.findAllSalesMan().getData());
		model.addAttribute("companies", companyService.findAll().getData());
		model.addAttribute("salesManagers", salesmanService.findAllSalesManagerByCompanyId(new Long(1)).getData());
		
		model.addAttribute("tab", Page.SALE.toString());
		return "salesman";
	}
	
	@RequestMapping(value="/salesManager")
	public String salesManager(Model model){
		
		model.addAttribute("title", "SalesManager List");
		model.addAttribute("popupTitle", "Creaet New SalesManager");
		model.addAttribute("companies", companyService.findAll().getData());
		model.addAttribute("salesManagers", salesmanService.findAllSalesManager().getData());
		model.addAttribute("tab", Page.SALES_MANAGER.toString());
		return "salesmanager";
	}
	
	@RequestMapping(value="/user")
	public String user(Model model){
		List<String> userTypes = App.getUserTypes();
		Status status = userService.findAll();
		
		model.addAttribute("users", status.getData());
		model.addAttribute("title", "Users List");
		model.addAttribute("popupTitle", "Create New User");
		model.addAttribute("tab", Page.USER.toString());
		model.addAttribute("addNewBtnText", "Add New User");
		model.addAttribute("userTypes", userTypes);
		
		return "user";
	}
	
	@RequestMapping(value="/plan")
	public String plan(Model model){
		model.addAttribute("tab", Page.PLAN.toString());
		model.addAttribute("title", "Plan List");
		model.addAttribute("addNewBtnText", "Add New Plan");
		model.addAttribute("popupTitle", "Create New Plan");
		model.addAttribute("plans", planService.findAll().getData());
		model.addAttribute("planTypes", App.getPlanTypes());
		model.addAttribute("companies", companyService.findAll().getData());
		
		return "plan";
	}
	
}
