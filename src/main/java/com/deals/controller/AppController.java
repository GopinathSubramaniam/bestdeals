package com.deals.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.deals.enums.DealType;
import com.deals.enums.Page;
import com.deals.enums.PlanType;
import com.deals.enums.Priority;
import com.deals.enums.UserType;
import com.deals.model.Category;
import com.deals.model.City;
import com.deals.model.Deal;
import com.deals.model.Plan;
import com.deals.model.State;
import com.deals.model.SubCategory;
import com.deals.model.Taluka;
import com.deals.model.User;
import com.deals.model.UserDetail;
import com.deals.model.Village;
import com.deals.repository.CategoryRepository;
import com.deals.repository.CityRepository;
import com.deals.repository.DealRepository;
import com.deals.repository.StateRepository;
import com.deals.repository.SubCategoryRepository;
import com.deals.repository.TalukaRepository;
import com.deals.repository.VillageRepository;
import com.deals.service.AppService;
import com.deals.service.CategoryService;
import com.deals.service.CompanyService;
import com.deals.service.DealService;
import com.deals.service.LoginService;
import com.deals.service.PlanService;
import com.deals.service.SalesmanService;
import com.deals.service.SubCategoryService;
import com.deals.service.UserDetailService;
import com.deals.service.UserService;
import com.deals.util.App;
import com.deals.util.Status;
import com.deals.vo.RegisterVo;
import com.deals.vo.UserVO;

@Controller
public class AppController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDetailService userDetailService;
	
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
	
	@Autowired
	private DealRepository dealRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private SubCategoryRepository subCategoryRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private TalukaRepository talukaRepository;

	@Autowired
	private VillageRepository villageRepository;
	
	
	@RequestMapping(value="/login")
	public String dologin(Model model, User user){
		user = (User)loginService.login(user).getData();
		String page = "u-login";
		
		if(user != null){
			boolean isAdmin = user.getUserType().name().equals(UserType.ADMIN);
			session.setAttribute("userId", user.getId());
			session.setAttribute("username", user.getName());
			log.info("Logged In Plan ::: "+user.getPlan());
			if(user.getPlan() != null){
				session.setAttribute("planType", user.getPlan().getPlanType());
			}
			model.addAttribute("message", "Welcome to BestDeals!!!");
			model.addAttribute("username", user.getName());
			if(!isAdmin) page = "redirect:greetings";
		}else{
			model.addAttribute("message", "Login in BestDeals");
			model.addAttribute("errorMsg", "Invalid username and password");
		}
		return page;
	}
	
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest req){
		if(session != null){
			session.invalidate();
		}
		return "login";
	}
	
	@RequestMapping(value="/out")
	public String out(){
		if(session != null){
			session.invalidate();
		}
		return "u-login";
	}
	
	
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
		log.info("Session UserType ::::: "+session.getAttribute("planType"));
		model.addAttribute("message", "Welcome to BestDeals !!!");
		model.addAttribute("userName", getSessionVal("username"));
		return "u-greetings";
	}

	@RequestMapping(value="/profile")
	public String profile(Model model){
		boolean displayAdvertisement = false;
		boolean displayMap = false;
		
		List<Plan> plans = (List<Plan>)planService.findAll().getData();
		Long userId = (Long) getSessionVal("userId");
		UserVO userVO = (UserVO)userService.findUser(userId).getData();
		Plan plan = (Plan)planService.findOne(userVO.getPlanId()).getData();
		
		if(plan != null){
			displayAdvertisement = !(plan.getPlanType().equals(PlanType.FREE));
			displayMap = plan.getPlanType().equals(PlanType.PLATINUM);
			log.info("plan.getPlanType() ::::: "+plan.getPlanType());
			log.info("DisplayMap ::::: "+displayMap);
		}
		model.addAttribute("tab", Page.USERPROFILE.toString());
		model.addAttribute("user", userVO);
		model.addAttribute("plan", plan);
		model.addAttribute("plans", plans);
		model.addAttribute("displayAdvertisement", displayAdvertisement);
		model.addAttribute("userName", getSessionVal("username"));
		model.addAttribute("displayMap", displayMap);
		
		return "u-profile";
	}

	@RequestMapping("/advertisement")
	public String advertisement(HttpServletRequest req, Model model){
		String isError = req.getParameter("error");
		if(isError != null && isError.equals("0")){
			model.addAttribute("message", "Please update your plan to create advertisement");
		}else if(isError != null && isError.equals("1")){
			model.addAttribute("message", "You limit exceed");
		}
		model = getAdvertisementModel(model);
		model.addAttribute("userName", getSessionVal("username"));
		return "u-advertisement";
	}
	
	@RequestMapping("/updatePlan")
	public String updatePlan(HttpServletRequest req, Model model){
		Long userId = (Long) getSessionVal("userId");
		Long planId = Long.parseLong(req.getParameter("pid"));
		log.info("UserId = "+userId);
		log.info("PlanId = "+planId);
		planService.assignPlanToUser(userId, planId);
		return "redirect:userplan";
	}
	
	@RequestMapping("/error")
	public String error(){
		return "error";
	}
	
	/**
	 * User Plan Page
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/userplan")
	public String uplan(Model model){
		Long userId = (Long) getSessionVal("userId");
		UserVO userVO = (UserVO)userService.findUser(userId).getData();
		List<Plan> plans = (List<Plan>)planService.findAll().getData();
		log.info("Plan id ::: "+userVO.getPlanId());
		log.info("Plans ::: "+plans.size());
		Plan plan = (Plan)planService.findOne(userVO.getPlanId()).getData();
		
		model.addAttribute("tab", Page.USERPLAN.toString());
		model.addAttribute("user", userVO);
		model.addAttribute("plan", plan);
		model.addAttribute("plans", plans);
		model.addAttribute("userName", getSessionVal("username"));
		
		return "u-plan";
	}

	@RequestMapping(value="/registerPage")
	public String registerPage(Model model){
		List<City> cities = null;
		List<Taluka> talukas = null;
		List<Village> villages = null;
		List<State> states = stateRepository.findAll();
		
		if(states.get(0) != null)
			cities = cityRepository.findAllByStateId(states.get(0).getId());
		if(cities.get(0) != null)
			talukas = talukaRepository.findAllByCityId(cities.get(0).getId());
		if(talukas.get(0) != null )
			villages = villageRepository.findAllByTalukaId(talukas.get(0).getId());
		
		model.addAttribute("states", states);
		model.addAttribute("cities", cities);
		model.addAttribute("talukas", talukas);
		model.addAttribute("villages", villages);
		
		model.addAttribute("message", null);
		return "u-register";
	}
	
	@RequestMapping(value="/register")
	public String register(HttpServletRequest req, Model model, RegisterVo register){
		User user = new User();
		user.setUserType(register.getUserType());
		user.setName(register.getName());
		user.setEmail(register.getEmail());
		user.setMobile(register.getMobile());
		user.setPassword(register.getPassword());
		user = (User) userService.create(user).getData();
		
		UserDetail userDetail = new UserDetail();
		userDetail.setAddress1(register.getAddress1());
		userDetail.setDescription(register.getDescription());
		userDetail.setLikes(new Long(0));
		userDetail.setViews(new Long(0));
		userDetail.setShopName(register.getShopName());
		userDetail.setUser(user);
		userDetail.setVillage(new Village(register.getVillage()));
		userDetail = (UserDetail)userDetailService.create(userDetail).getData();
		
		model.addAttribute("states", stateRepository.findAll());
		if(userDetail.getId() > 0){
			model.addAttribute("message", "You have registered successfully.");
			model.addAttribute("messageClass", "has-success");
			model.addAttribute("icon", "fa fa-check");
		}else{
			model.addAttribute("message", "Registeration failed.");
			model.addAttribute("messageClass", "has-error");
			model.addAttribute("icon", "fa fa-times-circle-o");
		}
		return "u-register";
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
	
	@RequestMapping(value="/home")
	public String home(Model model){
		model.addAttribute("tab", Page.DASHBOARD.toString());
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
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("subcategories", subCategoryRepository.findAll());
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
	
	@RequestMapping(value="/deleteDeal")
	public String deleteDeal(Model model, HttpServletRequest req){
		Long dealId = req.getParameter("id") != null ? Long.parseLong(req.getParameter("id")) : 0;
		dealService.delete(dealId);
		return "redirect:advertisement";
	}
	
	@RequestMapping(value="/createDeal")
	public String createDeal(HttpServletRequest req, HttpServletResponse res, Model model){
		
		String page = "redirect:advertisement";
		
		try {
			Long userId = (Long) getSessionVal("userId");
			User user = userService.findOne(userId);
			JSONObject rule = null;
			boolean isAllowedToCreateDeal = false;
			
			if(user != null && user.getPlan() != null && user.getPlan().getAmount() > 0){
				rule = new JSONObject(user.getPlan().getRules()); 
				if(rule.getInt("max_adv_count") > 0 && rule.getInt("max_adv_count") > dealRepository.countByUserId(userId)){
					isAllowedToCreateDeal = true;
				}
			}
			
			if(isAllowedToCreateDeal){
				Deal deal = new Deal();
				String id = req.getParameter("id");
				Long subCatId = Long.parseLong(req.getParameter("subCategory"));
				Long cityId = Long.parseLong(req.getParameter("city"));
				String contact = req.getParameter("contact");
				String description = req.getParameter("description");
				String name = req.getParameter("name");
				String placeName = req.getParameter("placeName");
				
				Part part = req.getPart("file");
				log.info("Part File :::: "+part.getSize());
				log.info("Id ::: "+id);
				if(id != null && !(id.isEmpty())){
					deal = (Deal)dealService.findOne(Long.parseLong(id)).getData();
				}
				
				if(part != null && part.getSize() > 0){
					String uploadedImgUrl = appService.copyFileInputstream(part, res);
					log.info("File Url ::: "+uploadedImgUrl);
					// Delete old image
					/*if(deal.getImgUrl() != null && deal.getImgUrl() != ""){
						log.info("Delete Image ::: "+deal.getImgUrl());
						appService.deleteFileFromServer(deal.getImgUrl());
					}*/
					
					deal.setImgUrl(uploadedImgUrl);
				}
				
				deal.setCity(new City(cityId));
				deal.setContact(contact);
				deal.setDescription(description);
				deal.setName(name);
				deal.setPlaceName(placeName);
				deal.setPriority(Priority.HIGH);
				deal.setSubCategory(new SubCategory(subCatId));
				deal.setType(DealType.ADVERTISEMENT);
				deal.setUser(new User(userId));
				
				dealService.create(deal);
			}else{
				page = user.getPlan() == null ? page+"?error=0" : page+"?error=1";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return page;
	}
	
	
	private Model getAdvertisementModel(Model model){
		Long userId = (Long) getSessionVal("userId");
		List<Deal> deals = (List<Deal>)dealService.findAllByUserId(userId).getData();
		List<Category> categories = (List<Category>)categoryService.findAll().getData();
		List<State> states = stateRepository.findAll();
		model.addAttribute("tab", Page.ADVERTISEMENT.toString());
		model.addAttribute("deals", deals);
		model.addAttribute("states", states);
		model.addAttribute("categories", categories);
		return model;
	}
	
	public Object getSessionVal(String key){
		Object val = session.getAttribute(key);
		if(val != null){
			String  strVal = val.toString();
			val = strVal;
			if(key.equals("userId")){
				val = Long.parseLong(strVal);
			}
		}
		return val;
	}
	
	
}
