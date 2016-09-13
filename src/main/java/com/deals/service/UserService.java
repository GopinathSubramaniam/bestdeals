package com.deals.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.enums.AuthType;
import com.deals.enums.UserType;
import com.deals.model.Deal;
import com.deals.model.EMail;
import com.deals.model.EmailDetail;
import com.deals.model.OTP;
import com.deals.model.User;
import com.deals.model.UserDetail;
import com.deals.otp.SendOTPClient;
import com.deals.repository.DealRepository;
import com.deals.repository.OTPRepository;
import com.deals.repository.UserDetailRepository;
import com.deals.repository.UserRepository;
import com.deals.util.App;
import com.deals.util.Status;
import com.deals.vo.UserVO;


@Service
public class UserService {
	
	private static Status status =  new Status();
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserDetailRepository userDetailRepository;
	
	@Autowired
	private OTPRepository otpRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private DealRepository dealRepository;
	
	@Autowired
	private SendOTPClient sendOTPClient;
	
	
	public Status sendOTP(String mobNumber){
		JSONObject oneTimePasswordJson = sendOTPClient.sendOTP(App.COUNTRY_CODE_IN, mobNumber);
		status = App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, oneTimePasswordJson.toString());
		return status;
	}
	
	public Status verifyOTP(String mobileNumber, String oneTimePassword){
		System.out.println("OneTimePassword ::: "+oneTimePassword);
		boolean b = sendOTPClient.verifyOTP(mobileNumber, oneTimePassword);
		status = App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, b);
		return status;
	}
	
	
	public Status create(User user){
//		Object obj = null;
		User userExist = null;
		if(user != null){
			if(user.getMobile() != null){
				userExist = userRepository.findByMobileOrEmail(user.getMobile(), user.getEmail());
			}
			
			if(userExist != null && userExist.getId() != null){
				status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_USER_EXISTS, null);
			}else{
				// User creation START
				user.setAuthType(AuthType.OK);
				user = userRepository.saveAndFlush(user);
				// User creation END
				
				// Creating OTP START
				/*String oneTimePassword = sendOTPClient.sendOTP(App.COUNTRY_CODE_IN, user.getMobile());//App.generateKey(6);
				OTP otp = new OTP();
				otp.setOtpCode(oneTimePassword);
				otp.setUser(user);
				otpRepository.saveAndFlush(otp);*/
				// Creating OTP END
				
				// Sending OPT to users EmailId START
				/*EMail eMail = new EMail();
				eMail.setTemplateName("welcome.html");
				EmailDetail emailDetail = new EmailDetail();
				emailDetail.setReceiverMail(user.getEmail());
				emailDetail.setReceiverName(user.getName());
				emailDetail.setSubject("OTP From BestDeals");
				emailDetail.setMessage("Use this OPT for login : "+oneTimePassword);
				
				eMail.setEmailDetail(emailDetail);
				eMail.setEmailType(EmailType.OTP);
				Status mailStatus = mailService.sendMail(eMail);
				if(mailStatus.getStatusCode().equals(App.CODE_FAIL)){
					obj = mailStatus;
				}else{
					user.setPassword(null);
					obj = user;
				}	*/
				status = App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, user);
			}
			// Sending OPT to users EmailId END
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, user);
		}
		return status;
	}
	
	public Status findUser(Long id){
		if(id != null && id !=0){
			User user = userRepository.findOne(id);
			List<Deal> deals = dealRepository.findAllByUserId(user.getId());
			List<UserDetail > userDetails = userDetailRepository.findAllByUserId(user.getId());
			System.out.println("User Details ::: "+userDetails);
			System.out.println("User ::: "+user);
			UserVO userVO = App.setUserVo(user, userDetails.size() > 0 ? userDetails.get(0): null, deals);
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
			existingUser.setChangedBy(user.getChangedBy());
			existingUser.setName(user.getName());
			existingUser.setPassword(user.getPassword());
			existingUser.setEmail(user.getEmail());
			existingUser.setUserType(user.getUserType());
			existingUser.setMobile(user.getMobile());
			
			user = userRepository.saveAndFlush(existingUser);
			status = App.getResponse(App.CODE_OK, App.STATUS_UPDATE, App.MSG_UPDATE, user);
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, user);
		}
		return status;
	}
	
	public Status delete(Long id){
		if(id != null && id !=0){
			OTP otp = otpRepository.findByUserId(id);
			if(otp!=null){
				otpRepository.delete(otp);
			}
			
			User user = userRepository.findOne(id);
			if(user != null){
				userRepository.delete(user);
			}
			status = App.getResponse(App.CODE_OK, App.STATUS_DELETE, App.MSG_DELETE, id);
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, id);
		}
		return status;
	}

	/*public Status verifyOTP(String codeOTP, Long userId){
		OTP otpObj = otpRepository.findByUserId(userId);
		if(sendOTPClient.verifyOTP(codeOTP) && otpObj.getOtpCode().equals(codeOTP)){
			
			otpObj.setOtpCode(codeOTP);
			otpObj.getUser().setAuthType(AuthType.OK);
			otpObj.getUser().setPassword(codeOTP);
			otpRepository.saveAndFlush(otpObj);
			
			User user = userRepository.findOne(userId);
			user.setAuthType(AuthType.OK);
			user.setPassword(codeOTP);
			userRepository.saveAndFlush(user);
			
			status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, otpObj.getUser());
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, App.MSG_INVALID_KEY);
		}
		return status;
	}*/
	
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
		System.out.println("Email :::: "+email);
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
	
}
