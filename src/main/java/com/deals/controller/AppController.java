package com.deals.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.deals.enums.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import static org.springframework.data.domain.Sort.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.deals.enums.UserType.*;
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
import com.deals.repository.UserRepository;
import com.deals.repository.VillageRepository;
import com.deals.service.AppService;
import com.deals.service.CategoryService;
import com.deals.service.DealService;
import com.deals.service.LoginService;
import com.deals.service.PlanService;
import com.deals.service.PublicUserPlanService;
import com.deals.service.SalesmanService;
import com.deals.service.SubCategoryService;
import com.deals.service.UserDetailService;
import com.deals.service.UserService;
import com.deals.util.App;
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
	private UserRepository userRepository;
	@Autowired
	private UserDetailService userDetailService;
	@Autowired
	private SalesmanService salesmanService;
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
	@Autowired
	private PublicUserPlanService publicUserPlanservice;

	@RequestMapping(value="/", method = RequestMethod.GET)
	public String landingPage(Model model){
		String page = "u-login";
		Object objval = getSessionVal("userId");
		if (objval != null) {
			Long userId = (Long) objval;
			User user = userService.findOne(userId);
			if(user != null) {
				model.addAttribute("userName", user.getName());
				model.addAttribute("userId", user.getId());
				model.addAttribute("userType", user.getUserType().name());
				model.addAttribute("tab", Page.DASHBOARD.toString());
//			page = isAdmin ? "forward:home" : "redirect:greetings";
				switch (user.getUserType()) {
					case ADMIN:
						page = "greetings";
						model.addAttribute("message", "Welcome to BestDeals API Section!!!");
						model.addAttribute("details", appService.getAdminDetail());
						break;
					case FRANCHISE:
						Pageable pageable = new PageRequest(0, 20, new Sort(
								new Order(Direction.DESC, "id")	//,new Order(Direction.DESC, "salary")
						));
						model.addAttribute("publicUsers", userService.findAllPublic(pageable));
						model.addAttribute("plans", planService.findAll());
						model.addAttribute("message", "Welcome to BestDeals franchise!!!");
						page = "franchise/home";
						break;
					case MERCHANT:
						model.addAttribute("message", "Welcome to BestDeals merchants!!!");
						page = "u-greetings";
						break;
					case SALES_MAN:
						model.addAttribute("message", "Welcome to BestDeals salesman!!!");
						page = "salesman/home";
						break;
					case PUBLIC:
						page = "u-greetings";
						model.addAttribute("message", "Welcome to BestDeals users!!!");
						break;
				}
			} else
				model.addAttribute("message", "Login in BestDeals");
		}
		return page;
	}

	@RequestMapping(value="/", method = RequestMethod.POST)
	public String dologin(Model model, User user){
		user = (User)loginService.login(user).getData();
		String page = "u-login";
		int maxAdvCount = 0;
		if(user != null){
			session.setAttribute("userId", user.getId());
			session.setAttribute("username", user.getName());
			session.setAttribute("usertype", user.getUserType().name());
			log.info("Logged In Plan ::: "+user.getPlan());
			if(user.getPlan() != null){
				session.setAttribute("planType", user.getPlan().getPlanType());
				JSONObject rule = new JSONObject(user.getPlan().getRules());
				maxAdvCount = rule.getInt("max_adv_count");
				session.setAttribute("maxAdvCount", maxAdvCount);
			}
			page = "redirect:/";
		}else{
			model.addAttribute("message", "Login in BestDeals");
			model.addAttribute("errorMsg", "Invalid username and password");
		}
		return page;
	}

	@RequestMapping(value="/admin")
	public String login(Model model){
		model.addAttribute("message", "Login in BestDeals");
		return "login";
	}

	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest req){
		if(session != null){
			session.invalidate();
		}
		return "redirect:/";
	}

	/*
	 * Client Routing START
	 *
	 */

	@RequestMapping(value="/profile")
	public String profile(Model model){
		boolean displayAdvertisement = false;
		boolean displayMap = false;

		model.addAttribute("userName", getSessionVal("username"));
		model.addAttribute("userId", getSessionVal("userId"));
		model.addAttribute("userType", getSessionVal("usertype"));

		List<Plan> plans = (List<Plan>)planService.findAll().getData();
		Long userId = (Long) getSessionVal("userId");
		UserVO userVO = (UserVO)userService.findUser(userId).getData();
		log.info("UserVO getPhoneNumbers :::: "+userVO.getPhoneNumbers());
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
		model.addAttribute("displayMap", displayMap);
		log.info("User Timings :::: "+userVO.getTimings());
		return "u-profile";
	}

	@RequestMapping("/advertisement")
	public String advertisement(HttpServletRequest req, Model model){

		model.addAttribute("userName", getSessionVal("username"));
		model.addAttribute("userId", getSessionVal("userId"));
		model.addAttribute("userType", getSessionVal("usertype"));

		model.addAttribute("tab", Page.ADVERTISEMENT.toString());
		List<State> states = stateRepository.findAll();
		model.addAttribute("states", states);
		List<Category> categories = (List<Category>)categoryService.findAllCategory().getData();
		model.addAttribute("categories", categories);

		String isError = req.getParameter("error");
		if(isError != null && isError.equals("0")){
			model.addAttribute("message", "Please update your plan to create advertisement");
		}else if(isError != null && isError.equals("1")){
			model.addAttribute("message", "You limit exceed");
		} else {
			User user = userService.findOne((long) getSessionVal("userId"));
			if (user.getPlan() != null) {
				JSONObject rule = new JSONObject(user.getPlan().getRules());
				int maxAdvCount = rule.getInt("max_adv_count");
				model.addAttribute("maxAdvCount", maxAdvCount);
				model = getAdvertisementModel(model, maxAdvCount);
			} else {
				model = getAdvertisementModel(model, 0);
			}
		}
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

		model.addAttribute("userName", getSessionVal("username"));
		model.addAttribute("userId", getSessionVal("userId"));
		model.addAttribute("userType", getSessionVal("usertype"));

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

		return "u-plan";
	}

	@RequestMapping(value="/registerPage")
	public String registerPage(Model model){
		List<City> cities = new ArrayList<City>();
		List<Taluka> talukas = new ArrayList<Taluka>();
		List<Village> villages = new ArrayList<Village>();
		List<State> states = stateRepository.findAll();

		if(states.size() > 0){
			model.addAttribute("states", states);
			cities = cityRepository.findAllByStateId(states.get(0).getId());
		}
		if(cities.size() > 0){
			model.addAttribute("cities", cities);
			talukas = talukaRepository.findAllByCityId(cities.get(0).getId());
		}
		if(talukas.size() > 0){
			model.addAttribute("talukas", talukas);
			villages = villageRepository.findAllByTalukaId(talukas.get(0).getId());
		}

		if(villages.size() > 0){
			model.addAttribute("villages", villages);
		}

		model.addAttribute("message", null);
		return "u-register";
	}
/*
	@RequestMapping(value="/register")
	public String register(HttpServletRequest req, Model model, RegisterVo register){
		log.info("Mobile ::: "+register.getMobile());
		User user = userRepository.findByMobile(register.getMobile());
		log.info("User :::: "+user);
		if(user == null ){
			log.info("User not exists:::: ");
			user = new User();
			user.setUserType(register.getUserType());
			user.setName(register.getName());
			user.setEmail(register.getEmail());
			user.setMobile(register.getMobile());
			user.setPassword(register.getPassword());
			user.setPlan(planService.getByPlanType(PlanType.FREE));
			user = (User) userService.create(user).getData();

			UserDetail userDetail = new UserDetail();
			userDetail.setAddress1(register.getAddress1());
			userDetail.setDescription(register.getDescription());
			userDetail.setLikes(new Long(0));
			userDetail.setViews(new Long(0));
			userDetail.setShopName(register.getShopName());
			userDetail.setUser(user);
			userDetail.setVillage(new Village(register.getVillage()));
			userDetail.setLatitude(register.getLatitude());
			userDetail.setLongitude(register.getLongitude());

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
		}else{
			log.info("User :::: "+App.MSG_USER_EXISTS);
			model.addAttribute("message", App.MSG_USER_EXISTS);
			model.addAttribute("messageClass", "has-error");
			model.addAttribute("icon", "fa fa-times-circle-o");
		}
		List<City> cities = new ArrayList<City>();
		List<Taluka> talukas = new ArrayList<Taluka>();
		List<Village> villages = new ArrayList<Village>();
		List<State> states = stateRepository.findAll();

		if(states.size() > 0){
			model.addAttribute("states", states);
			cities = cityRepository.findAllByStateId(states.get(0).getId());
		}
		if(cities.size() > 0){
			model.addAttribute("cities", cities);
			talukas = talukaRepository.findAllByCityId(cities.get(0).getId());
		}
		if(talukas.size() > 0){
			model.addAttribute("talukas", talukas);
			villages = villageRepository.findAllByTalukaId(talukas.get(0).getId());
		}

		if(villages.size() > 0){
			model.addAttribute("villages", villages);
		}

		return "u-register";
	}*/

	/*
	 * Client Routing END
	 *
	 */

	@RequestMapping(value="/salesman")
	public String salespeople(Model model){

		model.addAttribute("userName", getSessionVal("username"));
		model.addAttribute("userId", getSessionVal("userId"));
		model.addAttribute("userType", getSessionVal("usertype"));

		model.addAttribute("title", "Sales List");
		model.addAttribute("popupTitle", "Creaet New SalesMan");
		model.addAttribute("salesmans", salesmanService.findAllSalesMan().getData());
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
/*

	@RequestMapping(value="/user")
	public String user(Model model){
		if (getSessionVal("usertype") != UserType.ADMIN) {
			model.addAttribute("message", "Not authorized admin pages.");
			return "/";
		}
		model.addAttribute("userName", getSessionVal("username"));
		model.addAttribute("userId", getSessionVal("userId"));
		model.addAttribute("userType", getSessionVal("usertype"));

		List<City> cities = new ArrayList<City>();
		List<Taluka> talukas = new ArrayList<Taluka>();
		List<Village> villages = new ArrayList<Village>();
		List<State> states = stateRepository.findAll();

		if(states.size() > 0){
			model.addAttribute("states", states);
			cities = cityRepository.findAllByStateId(states.get(0).getId());
		}
		if(cities.size() > 0){
			model.addAttribute("cities", cities);
			talukas = talukaRepository.findAllByCityId(cities.get(0).getId());
		}
		if(talukas.size() > 0){
			model.addAttribute("talukas", talukas);
			villages = villageRepository.findAllByTalukaId(talukas.get(0).getId());
		}

		if(villages.size() > 0){
			model.addAttribute("villages", villages);
		}

		model.addAttribute("message", null);


		List<String> userTypes = App.getUserTypes();
		Pageable pageable = new PageRequest(0,20);
		List<User> merchants = userService.findAllMerchant(pageable);
		List<User> franchises = userService.findAllFranchise(pageable);
		List<User> publicUsers = userService.findAllPublic(pageable);

		model.addAttribute("merchants", merchants);
		model.addAttribute("franchises", franchises);
		model.addAttribute("publicUsers", publicUsers);

		model.addAttribute("plans", planService.findAll().getData());
		model.addAttribute("title", "Users List");
		model.addAttribute("popupTitle", "Create New User");
		model.addAttribute("tab", Page.USER.toString());
		model.addAttribute("addNewBtnText", "Add New User");
		model.addAttribute("userTypes", userTypes);

		return "user";
	}
*/

//	@RequestMapping(value="/user/{userType}")
	@RequestMapping(value="/user")
	public String userPage(//@PathVariable UserType userType,
						   Model model,
						   @RequestParam( name = "userType", required = true) UserType userType,
						   @RequestParam(defaultValue = "0", name = "page", required = false) int page,
						   @RequestParam(defaultValue = "20", name = "size", required = false) int size) {
		if (!UserType.ADMIN.name().equals(getSessionVal("usertype"))) {
			model.addAttribute("message", "Not authorized admin pages.");
			return "greetings";
		}
		model.addAttribute("userName", getSessionVal("username"));
		model.addAttribute("userId", getSessionVal("userId"));
		model.addAttribute("userType", getSessionVal("usertype"));

		model.addAttribute("pageUserType", userType);

		List<City> cities = new ArrayList<City>();
		List<Taluka> talukas = new ArrayList<Taluka>();
		List<Village> villages = new ArrayList<Village>();
		List<State> states = stateRepository.findAll();

		if (states.size() > 0) {
			model.addAttribute("states", states);
			cities = cityRepository.findAllByStateId(states.get(0).getId());
		}
		if (cities.size() > 0) {
			model.addAttribute("cities", cities);
			talukas = talukaRepository.findAllByCityId(cities.get(0).getId());
		}
		if (talukas.size() > 0) {
			model.addAttribute("talukas", talukas);
			villages = villageRepository.findAllByTalukaId(talukas.get(0).getId());
		}

		if (villages.size() > 0) {
			model.addAttribute("villages", villages);
		}

		List<String> userTypes = App.getUserTypes();
		Pageable pageable = new PageRequest(page, size);

		List<User> users = null;
		if (userType == UserType.MERCHANT) {
			users = userService.findAllMerchant(pageable);
		}
		if (userType == UserType.FRANCHISE) {
			users = userService.findAllFranchise(pageable);
		}
		if (userType == UserType.PUBLIC) {
			users = userService.findAllPublic(pageable);
		}
		if (users == null || users.size() == 0){
			model.addAttribute("message", "No users found");
		} else {
			model.addAttribute("users", users);
			model.addAttribute("next", page + 1);
		}
		if (page > 0)
			model.addAttribute("prev", page - 1);

		model.addAttribute("plans", planService.findAll().getData());
		model.addAttribute("title", userType +" Users List");
		model.addAttribute("popupTitle", "Create New User");
		model.addAttribute("tab", userType);
		model.addAttribute("addNewBtnText", "Add New User");
		model.addAttribute("userTypes", userTypes);

		return "userList";
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

		model.addAttribute("userName", getSessionVal("username"));
		model.addAttribute("userId", getSessionVal("userId"));
		model.addAttribute("userType", getSessionVal("usertype"));

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

	@RequestMapping(value="/publicUserPlan")
	public String publicUserPlan(Model model){

		model.addAttribute("userName", getSessionVal("username"));
		model.addAttribute("userId", getSessionVal("userId"));
		model.addAttribute("userType", getSessionVal("usertype"));

		model.addAttribute("tab", Page.PUBLIC_USER_PLAN.toString());
		model.addAttribute("title", "Public User Plan List");
		model.addAttribute("publicUserPlans", publicUserPlanservice.getPublicUserPlans());
		return "publicuserplan";
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
		int dealCount = 0;
		int maxAdvCount = 0;

		try {
			Long userId = (Long) getSessionVal("userId");
			User user = userService.findOne(userId);
			List<UserDetail> userDetails =(List<UserDetail>)userDetailService.findAllByUserId(userId).getData();
			UserDetail userDetail = userDetails.get(0);

			JSONObject rule = null;
			boolean isAllowedToCreateDeal = false;

			if(user != null && user.getPlan() != null && user.getPlan().getAmount() > 0){
				rule = new JSONObject(user.getPlan().getRules());
				maxAdvCount = rule.getInt("max_adv_count");
				if(maxAdvCount > 0 && maxAdvCount > dealRepository.countByUserId(userId)){
					isAllowedToCreateDeal = true;
				}
			}

			if(isAllowedToCreateDeal){
				String id = req.getParameter("id");
				String subCat = req.getParameter("subCategory");
				Long subCatId = subCat.isEmpty() ? 0 : Long.parseLong(subCat);
				if (subCatId == 0) {
					model.addAttribute("message", "Please select Subcategory");
					return page;
				}
				String name = req.getParameter("name");

				if(id != null && !(id.isEmpty())){
					Deal deal = (Deal)dealService.findOne(Long.parseLong(id)).getData();
					Part part = req.getPart("file");
					if(part.getSize() > 0){
						String uploadedImgUrl = appService.copyFileInputstream(part, res);
						log.info("File Url ::: "+uploadedImgUrl);
						deal.setImgUrl(uploadedImgUrl);
					}
					deal.setDescription(req.getParameter("description"));
					deal.setSubCategory(new SubCategory(subCatId));
					dealService.create(deal);
				}else{
					for (int i = 1; i <= maxAdvCount; i++) {
						Part part = req.getPart("file_"+i);
						Long fileSize = part != null ? part.getSize() : 0;
						if(dealCount <= maxAdvCount && fileSize > 0){
							Deal deal = new Deal();
							dealCount++;
							log.info("Part File :::: "+fileSize);
							log.info("Id ::: "+id);

							String uploadedImgUrl = appService.copyFileInputstream(part, res);
							log.info("File Url ::: "+uploadedImgUrl);
							deal.setImgUrl(uploadedImgUrl);

							String description = req.getParameter("description_"+i);
							deal.setDescription(description);
							deal.setPriority(Priority.LOW);
							deal.setSubCategory(new SubCategory(subCatId));
							deal.setType(DealType.ADVERTISEMENT);
							deal.setUser(new User(userId));
							if(userDetail.getVillage() != null){
								deal.setCity(userDetail.getVillage().getTaluka().getCity());
							}

							dealService.create(deal);
						}
					}
				}



				/*for (Part part : parts) {
//					Part part = req.getPart("file");
					Deal deal = new Deal();
					if(dealCount <= maxAdvCount){
						dealCount++;
						log.info("Part File :::: "+part.getSize());
						log.info("Id ::: "+id);
						if(id != null && !(id.isEmpty())){
							deal = (Deal)dealService.findOne(Long.parseLong(id)).getData();
						}

						if(part != null && part.getSize() > 0){
							String uploadedImgUrl = appService.copyFileInputstream(part, res);
							log.info("File Url ::: "+uploadedImgUrl);
							deal.setImgUrl(uploadedImgUrl);
						}

						deal.setDescription(description);
						deal.setName(name);
						deal.setPriority(Priority.LOW);
						deal.setSubCategory(new SubCategory(subCatId));
						deal.setType(DealType.ADVERTISEMENT);
						deal.setUser(new User(userId));

						dealService.create(deal);
					}
				}*/
			}else{
				page = user.getPlan() == null ? page+"?error=0" : page+"?error=1";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}


	private Model getAdvertisementModel(Model model, int maxAdvCount){
		Long userId = (Long) getSessionVal("userId");
		List<Deal> deals = (List<Deal>)dealService.findAllByUserId(userId).getData();
		if (deals == null )
			deals = Collections.emptyList();
		model.addAttribute("deals", deals);

		if(maxAdvCount > 0){
			int availableAdvCount = maxAdvCount - deals.size();
			int count = (availableAdvCount <= 0) ? 0 : (availableAdvCount);
			log.info("Adv Count Left :::: "+count);
			if(count == 0){
				model.addAttribute("message", "Your is limit exceed. Your can create only "+session.getAttribute("maxAdvCount")+" advertisement.");
			}
		}else{
			model.addAttribute("maxAdvCount", 0);
			model.addAttribute("message", "Buy plan to post advertisement!!!. Go to plan section and purchase.");
		}

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
