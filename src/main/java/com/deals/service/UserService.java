package com.deals.service;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.enums.AuthType;
import com.deals.enums.UserType;
import com.deals.model.Deal;
import com.deals.model.EMail;
import com.deals.model.EmailDetail;
import com.deals.model.LikeView;
import com.deals.model.SalesManager;
import com.deals.model.User;
import com.deals.model.UserDetail;
import com.deals.otp.SendOTPClient;
import com.deals.repository.DealRepository;
import com.deals.repository.LikeViewRepository;
import com.deals.repository.SalesManagerRepository;
import com.deals.repository.SalesmanRepository;
import com.deals.repository.UserDetailRepository;
import com.deals.repository.UserRepository;
import com.deals.util.App;
import com.deals.util.Status;
import com.deals.vo.UserVO;


@Service
public class UserService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static Status status =  null;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserDetailRepository userDetailRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private DealRepository dealRepository;
	
	@Autowired
	private SendOTPClient sendOTPClient;
	
	@Autowired
	private SalesManagerRepository salesmanagerRepository;
	
	@Autowired
	private SalesmanRepository salesmanRepository;
	
	@Autowired
	private LikeViewRepository likeViewRepository;
	
	@Autowired
	private PublicUserPlanService userPlanService;
	
	
	public User userExists(String mobile){
		return userRepository.findByMobile(mobile);
	}
	
	public Status sendOTP(String mobNumber){
		
		User user = userRepository.findByMobile(mobNumber);
		if(user == null){
			JSONObject oneTimePasswordJson = sendOTPClient.sendOTP(App.COUNTRY_CODE_IN, mobNumber);
			status = App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, oneTimePasswordJson.toString());
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_USER_EXISTS, mobNumber);
		}
		
		return status;
	}
	
	public Status verifyOTP(String mobileNumber, String oneTimePassword){
		log.info("OneTimePassword ::: "+oneTimePassword);
		boolean b = sendOTPClient.verifyOTP(mobileNumber, oneTimePassword);
		status = App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, b);
		return status;
	}
	
	public Status createOnlyUser(User user){
		User userExist = null;
		if(user != null && user.getMobile() != null){
			userExist = userRepository.findByMobile(user.getMobile());
			log.info("User Exists :::: "+userExist);
			if(userExist != null){
				log.info("EXISTS ::::");
				status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_USER_EXISTS, null);
			}else{
				log.info("NOT EXISTS ::::");
				user.setAuthType(AuthType.OK);
				user = userRepository.saveAndFlush(user);
				status = App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, user);
			}
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, user);
		}
		return status;
	}
	
	public Status create(User user){
		User userExist = null;
		if(user != null && user.getMobile() != null){
			userExist = userRepository.findByMobile(user.getMobile());
			log.info("User Exists :::: "+userExist);
			if(userExist != null){
				log.info("EXISTS :::: ");
				status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_USER_EXISTS, null);
			}else{
				log.info("NOT EXISTS :::: ");
				user.setAuthType(AuthType.OK);
				user = userRepository.saveAndFlush(user);
				
				userPlanService.create(user);
				
				UserDetail userDetail = new UserDetail();
				userDetail.setLikes(new Long(0));
				userDetail.setViews(new Long(0));
				userDetail.setUser(user);
				userDetailRepository.saveAndFlush(userDetail);
				
				status = App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, user);
			}
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, user);
		}
		return status;
	}
	
	public User findOne(Long id){
		return userRepository.findOne(id);
	}
	
	public Status findUser(Long id){
		if(id != null && id !=0){
			User user = userRepository.findOne(id);
			List<Deal> deals = dealRepository.findAllByUserId(user.getId());
			List<UserDetail > userDetails = userDetailRepository.findAllByUserId(user.getId());
			log.info("User Details ::: "+userDetails);
			log.info("User ::: "+user);
			UserVO userVO = App.setUserVo(user, userDetails.size() > 0 ? userDetails.get(0): null, deals);
			log.info("UserDetailsId :: "+userVO.getUserDetailId());
			status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, userVO);
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, null);
		}
		return status; 
	}
	
	public Status findAllByType(UserType userType){
		status = App.getResponse(App.CODE_OK, App.STATUS_UPDATE, App.MSG_UPDATE, userRepository.findAllByUserType(userType));
		return status;
	}
	
	public Status saveUserDetail(UserDetail userDetail){
		if(userDetail != null){
			userDetail = userDetailRepository.saveAndFlush(userDetail);
			status = App.getResponse(App.CODE_OK, App.STATUS_UPDATE, App.MSG_UPDATE, userDetail);
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, null);
		}
		return status; 
	}
	
	public Status update(User user){
		if(user != null){
			User existingUser = userRepository.getOne(user.getId());
			if(user.getChangedBy() != null) existingUser.setChangedBy(user.getChangedBy());
			if(user.getUserType() != null) existingUser.setUserType(user.getUserType());
			if(user.getPassword() != null) existingUser.setPassword(user.getPassword());
			
			existingUser.setName(user.getName());
			existingUser.setEmail(user.getEmail());
			existingUser.setMobile(user.getMobile());
			if(user.getPlan() != null){
				existingUser.setPlan(user.getPlan());
			}
			
			user = userRepository.saveAndFlush(existingUser);
			status = App.getResponse(App.CODE_OK, App.STATUS_UPDATE, App.MSG_UPDATE, user);
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, user);
		}
		return status;
	}
	
	public Status delete(Long id){
		if(id != null && id !=0){
			User user = userRepository.findOne(id);
			log.info("Delete User :: "+user);
			if(user != null){
				dealRepository.deleteByUserId(user.getId());
				likeViewRepository.deleteAllByUserId(id);
				List<SalesManager> salesManagers = salesmanagerRepository.findAllByUserId(user.getId());
				for (SalesManager salesManager : salesManagers) {
					salesmanRepository.deleteBySalesManagerId(salesManager.getId());
					salesmanagerRepository.delete(salesManager.getId());
				}
				userDetailRepository.deleteByUserId(user.getId());
				userRepository.delete(user);
			}
			status = App.getResponse(App.CODE_OK, App.STATUS_DELETE, App.MSG_DELETE, id);
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, id);
		}
		return status;
	}
	
	public Status findUsersByUserType(UserType userType){
		List<User> users = userRepository.findAllByUserType(userType);
		return App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, users);
		
	}
	
	public Status findAll(){
		List<User> users = userRepository.findAll();
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, users);
		return status;
	}
	
	public Status findAllFranchise(){
		List<User> users = userRepository.findAllByUserType(UserType.FRANCHISE);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, users);
		return status;
	}
	
	public Status forgotPassword(String email){
		log.info("Email :::: "+email);
		if(!email.contains(".com")){
			email = email+".com";
		}
		
		User user = userRepository.findByEmail(email);
		if(user != null ){
			EmailDetail emailDetail = new EmailDetail();
			emailDetail.setSubject("Password Request");
			emailDetail.setReceiverName(user.getName());
			emailDetail.setReceiverMail(user.getEmail());
			emailDetail.setMessage("Your password is "+user.getPassword());
			EMail eMail = App.setEmailObject("welcome.html",emailDetail);
			mailService.sendMail(eMail);
			status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_MAIL_SENT, eMail.getEmailDetail());
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_USER_INVALID_EMAIL, null);
		}
		return status;
	}
	
	public Status likeOrView(Long userId, Long merchantId, String type){
		type = type.equalsIgnoreCase("like") ? "LIKE" : "VIEW";
		LikeView likeView = likeViewRepository.findByUserIdAndMerchantIdAndType(userId, merchantId, type);
		if(likeView == null){
			UserDetail merchantDetail = userDetailRepository.findByUserId(merchantId);
			if(type.equals("LIKE")){
				merchantDetail.setLikes(merchantDetail.getLikes()+1);
			}else{
				merchantDetail.setViews(merchantDetail.getViews()+1);
			}
			userDetailRepository.saveAndFlush(merchantDetail);
			
			likeView = new LikeView();
			likeView.setUserId(userId);
			likeView.setMerchantId(merchantId);	
			
			likeView.setType(type);
			likeViewRepository.saveAndFlush(likeView);
			return App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, App.MSG_OK);
		}else{
			return App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_USER_ALREADY_VIEWED, App.MSG_OK);
		}
		
	}
	
}
