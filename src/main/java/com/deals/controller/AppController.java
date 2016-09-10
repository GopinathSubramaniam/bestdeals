package com.deals.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deals.enums.Page;
import com.deals.enums.PlanType;
import com.deals.model.Category;
import com.deals.model.Deal;
import com.deals.model.Plan;
import com.deals.model.State;
import com.deals.model.User;
import com.deals.repository.StateRepository;
import com.deals.service.AppService;
import com.deals.service.CategoryService;
import com.deals.service.CompanyService;
import com.deals.service.DealService;
import com.deals.service.LoginService;
import com.deals.service.PlanService;
import com.deals.service.SalesmanService;
import com.deals.service.SubCategoryService;
import com.deals.service.UserService;
import com.deals.util.App;
import com.deals.util.Status;
import com.deals.vo.UserVO;

@Controller
public class AppController {
	
	private HttpSession session = null;
	
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
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private SubCategoryService subcategoryService;
	
	@Autowired
	private DealService dealService;
	
	@Autowired
	private StateRepository stateRepository;

	/*
	 * Client Routing START 
	 * 
	 */
	@RequestMapping(value="/")
	public String loginForCustomer(Model model){
		model.addAttribute("message", "Login in BestDeals");
		return "u-login";
	}
	
	@RequestMapping(value="/greetings")
	public String userLandingPage(Model model){
		model.addAttribute("message", "Welcome to BestDeals !!!");
		return "u-greetings";
	}

	@RequestMapping(value="/profile")
	public String profile(Model model, HttpServletRequest req){
		session = req.getSession();
		boolean displayAdvertisement = false;
		Long userId = Long.parseLong(req.getParameter("userId").toString());
		UserVO userVO = (UserVO)userService.findUser(userId).getData();
		Plan plan = (Plan)planService.findOne(userVO.getPlanId()).getData();
		List<Deal> deals = (List<Deal>)dealService.findAllByUserId(userId).getData();
		List<Category> categories = (List<Category>)categoryService.findAll().getData();
		List<State> states = stateRepository.findAll();
		
		System.out.println("Deals ::: "+deals);
		if(plan != null){
			displayAdvertisement = plan.getPlanType().equals(PlanType.FREE) ? false : true;
		}
		model.addAttribute("user", userVO);
		model.addAttribute("deals", deals);
		model.addAttribute("plan", plan);
		model.addAttribute("categories", categories);
		model.addAttribute("states", states);
		model.addAttribute("displayAdvertisement", displayAdvertisement);
		return "u-profile";
	}


	/*
	 * Client Routing END 
	 * 
	 */	
	
	@RequestMapping(value="/admin")
	public String login(Model model){
		model.addAttribute("message", "Login in BestDeals");
		return "login";
	}
	
	@RequestMapping(value="/login")
	public String dologin(Model model, User user, HttpServletRequest req){
		user = (User)loginService.login(user).getData();
		if(user != null){
			session = req.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("username", user.getName());
			
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
		model.addAttribute("salesManagers", salesmanService.findAllSalesManager().getData());
		
		model.addAttribute("tab", Page.SALE.toString());
		return "salesman";
	}
	
	@RequestMapping(value="/salesManager")
	public String salesManager(Model model){
		
		model.addAttribute("title", "SalesManager List");
		model.addAttribute("popupTitle", "Creaet New SalesManager");
		model.addAttribute("salesManagers", salesmanService.findAllSalesManager().getData());
		model.addAttribute("tab", Page.SALES_MANAGER.toString());
		return "salesmanager";
	}
	
	@RequestMapping(value="/user")
	public String user(Model model){
		List<String> userTypes = App.getUserTypes();
		Status status = userService.findAll();
		
		model.addAttribute("users", status.getData());
		model.addAttribute("plans", planService.findAll().getData());
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
		
		return "plan";
	}
	
	@RequestMapping(value="/category")
	public String category(Model model){
		model.addAttribute("tab", Page.CATEGORY.toString());
		model.addAttribute("title", "Category And SubCategory List");
		model.addAttribute("addNewBtnText", "Add New Category");
		model.addAttribute("popupTitle", "Create New Category");
		model.addAttribute("categories", categoryService.findAll().getData());
		model.addAttribute("subcategories", subcategoryService.findAll().getData());
		return "category";
	}
	
	@RequestMapping(value="/subcategory")
	public String subcategory(Model model){
		model.addAttribute("tab", Page.SUB_CATEGORY.toString());
		model.addAttribute("title", "Sub Category List");
		model.addAttribute("addNewBtnText", "Add New SubCategory");
		model.addAttribute("popupTitle", "Create New SubCategory");
		model.addAttribute("subcategories", subcategoryService.findAll().getData());
		return "category";
	}
}
