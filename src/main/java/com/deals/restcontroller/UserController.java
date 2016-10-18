package com.deals.restcontroller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deals.enums.AuthType;
import com.deals.enums.UserType;
import com.deals.model.PublicUserPlan;
import com.deals.model.User;
import com.deals.model.UserDetail;
import com.deals.model.Village;
import com.deals.repository.VillageRepository;
import com.deals.service.PublicUserPlanService;
import com.deals.service.UserDetailService;
import com.deals.service.UserService;
import com.deals.util.Status;
import com.deals.vo.RegisterVo;

@RestController
@RequestMapping("/rest/user")
public class UserController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private VillageRepository villageRepository;
	
	@Autowired
	private PublicUserPlanService userPlanService;
	
	@RequestMapping(value="/sendOTP/{mobNumber}", method=RequestMethod.GET)
	public Status sendOTP(@PathVariable String mobNumber){
		return userService.sendOTP(mobNumber);
	}
	
	@RequestMapping(value="/verifyOTP/{mobileNumber}/{oneTimePassword}", method=RequestMethod.GET)
	public Status verifyOTP(@PathVariable String mobileNumber, @PathVariable String oneTimePassword){
		return userService.verifyOTP(mobileNumber, oneTimePassword);
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST, produces={"application/json"})
	public Status register(@RequestBody RegisterVo registerVo){
		User user = new User();
		user.setName(registerVo.getName());
		user.setEmail(registerVo.getEmail());
		user.setMobile(registerVo.getMobile());
		user.setAuthType(AuthType.OK);
		user.setPassword(registerVo.getPassword());
		user.setUserType(registerVo.getUserType());
		
		Status status = userService.createOnlyUser(user);
		user = (User)status.getData();
		
		if(user.getUserType().equals(UserType.PUBLIC)){
			log.info("Public User::: Create QR code and plan");
			userPlanService.create(user);
		}
		UserDetail userDetail = new UserDetail();
		if(status.getStatusCode() != "500" && registerVo.getShopName() != null && registerVo.getAddress1() != null){
			userDetail.setAddress1(registerVo.getAddress1());
			userDetail.setLatitude(registerVo.getLatitude());
			userDetail.setLongitude(registerVo.getLongitude());
			userDetail.setDescription(registerVo.getDescription());
			userDetail.setLikes(new Long(0));
			userDetail.setViews(new Long(0));
			userDetail.setPhoneNumbers(registerVo.getPhoneNumbers());
			userDetail.setPlaceName(registerVo.getPlaceName());
			userDetail.setShopName(registerVo.getShopName());
			userDetail.setUser(user);
			userDetail.setVillage(new Village(registerVo.getVillage()));
		}else{
			userDetail.setLikes(new Long(0));
			userDetail.setViews(new Long(0));
		}
		userDetailService.create(userDetail);
		return status;
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
	
	@RequestMapping(value="/findByUserType/{userType}", method=RequestMethod.GET)
	public Status findUsersByUserType(@PathVariable UserType userType){
		return userService.findUsersByUserType(userType);
	}
	
	@RequestMapping(value="/forgotPassword/{emailAddress}", method=RequestMethod.GET)
	public Status forgotPassword(@PathVariable("emailAddress") String emailAddress){
		System.out.println("Email Address ::::: "+emailAddress);
		return userService.forgotPassword(emailAddress);
	}
	
	@RequestMapping(value="/likeOrView/{userId}/{merchantId}/{type}", method=RequestMethod.GET)
	public Status like(@PathVariable Long userId, @PathVariable Long merchantId, @PathVariable String type){
		return userService.likeOrView(userId, merchantId, type);
	}
	
	@RequestMapping(value="/getPublicUserPlans", method=RequestMethod.GET)
	public List<PublicUserPlan> getPublicUserPlans(){
		return userPlanService.getPublicUserPlans();
	}
	
	@RequestMapping(value="/findPublicPlanById/{id}", method=RequestMethod.GET)
	public Status findPublicPlanByUserId(@PathVariable Long id){
		return userPlanService.getPublicUserPlan(id);
	}
	
	@RequestMapping(value="/updatePublicPlan", method=RequestMethod.PUT)
	public Status updatePublicPlan(@RequestBody PublicUserPlan publicUserPlan){
		return userPlanService.updatePublicUserPlan(publicUserPlan);
	}
	
}
