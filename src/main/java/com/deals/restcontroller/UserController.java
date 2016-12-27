package com.deals.restcontroller;

import java.util.List;

import com.deals.enums.PlanType;
import com.deals.service.PlanService;
import com.deals.util.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/rest/user")
public class UserController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDetailService userDetailService;

	@Autowired
	PlanService planService;
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
	public Status register(@RequestBody RegisterVo registerVo, HttpServletRequest request){

		Status status = new Status();
		User user = userService.findUserByMobile(registerVo.getMobile());
		if(user == null){
			user= new User();
			user.setName(registerVo.getName());
			user.setEmail(registerVo.getEmail());
			user.setMobile(registerVo.getMobile());
			user.setAuthType(AuthType.OK);
			user.setPassword(registerVo.getPassword());
			user.setUserType(registerVo.getUserType());
			user.setPlan(planService.getByPlanType(PlanType.FREE));

			Object userId = request.getSession().getAttribute("userId");
			if ( userId != null ) {
				user.setCreatedBy(userService.findOne((Long)userId).getMobile());
			}
			status = userService.createOnlyUser(user);
			user = (User)status.getData();
			
			userPlanService.create(user);

			UserDetail userDetail = new UserDetail();
			if(status.getStatusCode() != "500" && registerVo.getShopName() != null && registerVo.getAddress1() != null){
				userDetail.setAddress1(registerVo.getAddress1());
				userDetail.setDescription(registerVo.getDescription());
				userDetail.setPhoneNumbers(registerVo.getPhoneNumbers());
				userDetail.setPlaceName(registerVo.getPlaceName());
				userDetail.setShopName(registerVo.getShopName());
				userDetail.setVillage(new Village(registerVo.getVillage()));
			}
			userDetail.setUser(user);
			userDetail.setLikes(new Long(0));
			userDetail.setViews(new Long(0));
			userDetail.setLatitude(registerVo.getLatitude() == 0.0d ? 18.5204303d : registerVo.getLatitude());
			userDetail.setLongitude(registerVo.getLongitude() == 0.0d ? 73.8567437d : registerVo.getLatitude());

			userDetailService.create(userDetail);
		}else{
			status.setMessage(App.MSG_USER_EXISTS);
			status.setStatusCode(App.CODE_FAIL);
			status.setStatusMsg(App.MSG_FAIL);
		}

		/*status = userService.createOnlyUser(user);
		if (status.getStatusCode() != App.CODE_OK || status.getData() == null) {
			return status;
		}
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
			userDetail.setPhoneNumbers(registerVo.getPhoneNumbers());
			userDetail.setPlaceName(registerVo.getPlaceName());
			userDetail.setShopName(registerVo.getShopName());
		}
		userDetail.setDescription(registerVo.getDescription());
		userDetail.setVillage(new Village(registerVo.getVillage()));
		userDetail.setUser(user);
		userDetail.setLikes(new Long(0));
		userDetail.setViews(new Long(0));

		userDetailService.create(userDetail);*/
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
	public Status findAllByUserType(@PathVariable UserType userType, @RequestParam(defaultValue = "0", required = false) int page){
		Pageable pageable = new PageRequest(page, 20);
		return userService.findAllByUserType(userType, pageable);
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
	public Status findUsersByUserType(@PathVariable UserType userType, @RequestParam(defaultValue = "0", required = false) int page){
		return userService.findAllByUserType(userType, new PageRequest(page, 20));
	}
	
	/* We doesn't provide this functionality by mail, we must use SMS gateway
	@RequestMapping(value="/forgotPassword/{emailAddress}", method=RequestMethod.GET)
	public Status forgotPassword(@PathVariable("emailAddress") String emailAddress){
		System.out.println("Email Address ::::: "+emailAddress);
		return userService.forgotPassword(emailAddress);
	}*/
	
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

	@RequestMapping(value="/updateToSimplePlan", method=RequestMethod.GET)
	public Status updateUserPlanToSimple( @RequestParam String userId, HttpServletRequest request){
		Object val = request.getSession().getAttribute("userId");
		if(val != null){
			long franchiseUserId = Long.parseLong(val.toString());
			if (userId != null && !userId.isEmpty()){
				try {
					PublicUserPlan publicUserPlan = userPlanService.updateUserPlanToSimple(userService.findOne(franchiseUserId), Long.parseLong(userId));
					return App.getResponse(App.CODE_OK, App.STATUS_UPDATE, App.MSG_UPDATE, publicUserPlan);
				} catch (Exception e) {
					e.printStackTrace();
					return App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, e.getMessage(), null);
				}
			}
		}
		return App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_USER_NOT_AUTH, null);
	}

	@RequestMapping(value="/buyPlan", method=RequestMethod.GET)
	public Status buyPlan( @RequestParam String userId,@RequestParam String planId, HttpServletRequest request){
		Object val = request.getSession().getAttribute("userId");
		if(val != null){
			long merchantId = Long.parseLong(val.toString());
			if (userId != null && !userId.isEmpty()){
				try {
					PublicUserPlan publicUserPlan = userPlanService.updateUserPlanToSimple(userService.findOne(merchantId), Long.parseLong(userId));
					return App.getResponse(App.CODE_OK, App.STATUS_UPDATE, App.MSG_UPDATE, publicUserPlan);
				} catch (Exception e) {
					e.printStackTrace();
					return App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, e.getMessage(), null);
				}
			}
		}
		return App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_USER_NOT_AUTH, null);
	}
}
