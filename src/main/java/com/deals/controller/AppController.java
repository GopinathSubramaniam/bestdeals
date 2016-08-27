package com.deals.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deals.model.User;
import com.deals.service.CompanyService;
import com.deals.service.LoginService;
import com.deals.service.SalesmanService;
import com.deals.service.UserService;
import com.deals.util.App;
import com.deals.util.Status;

@Controller
public class AppController {

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SalesmanService salesmanService;
	
	@Autowired
	private CompanyService companyService;
	
	@RequestMapping(value="/")
	public String login(Model model){
		model.addAttribute("message", "Login in BestDeals");
		return "login";
	}
	

	@RequestMapping(value="/login")
	public String dologin(Model model, User user, HttpServletRequest req){
		user = (User)loginService.login(user).getData();
		if(user != null){
			HttpSession session = req.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("username", user.getName());
			session.setAttribute("loginState", user.getLoginState());
			
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
	public String home(Model model, HttpServletRequest req){
		
		HttpSession session = req.getSession();
		model.addAttribute("message", "Welcome to BestDeals API Section!!!");
		model.addAttribute("username", session.getAttribute("username"));
		
		return "greetings";
	}
	
	@RequestMapping(value="/salesman")
	public String salespeople(Model model, HttpServletRequest req){
		
		HttpSession session = req.getSession();
		model.addAttribute("title", "Sales List");
		model.addAttribute("popupTitle", "Creaet New SalesMan");
		model.addAttribute("salesmans", salesmanService.findAllSalesMan().getData());
		model.addAttribute("companies", companyService.findAll().getData());
		model.addAttribute("salesManagers", salesmanService.findAllSalesManagerByCompanyId(new Long(1)).getData());
		model.addAttribute("username", session.getAttribute("username"));
		
		model.addAttribute("tab", App.SALE);
		return "salesman";
	}
	
	@RequestMapping(value="/salesManager")
	public String salesManager(Model model, HttpServletRequest req){
		
		model.addAttribute("title", "SalesManager List");
		model.addAttribute("popupTitle", "Creaet New SalesManager");
		model.addAttribute("companies", companyService.findAll().getData());
		model.addAttribute("salesManagers", salesmanService.findAllSalesManagerByCompanyId(new Long(1)).getData());
		
		model.addAttribute("tab", App.SALE);
		return "salesman";
	}
	
	@RequestMapping(value="/user")
	public String user(Model model, HttpServletRequest req){
		List<String> userTypes = App.getUserTypes();
		HttpSession session = req.getSession();
		Status status = userService.findAll();
		
		model.addAttribute("users", status.getData());
		model.addAttribute("title", "Users List");
		model.addAttribute("popupTitle", "Create New User");
		model.addAttribute("tab", App.USER);
		model.addAttribute("addNewBtnText", "Add New User");
		model.addAttribute("userTypes", userTypes);
		
		model.addAttribute("username", session.getAttribute("username"));
		return "user";
	}
	
}
