package com.deals.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.enums.AuthType;
import com.deals.enums.EmailType;
import com.deals.enums.UserType;
import com.deals.model.EMail;
import com.deals.model.EmailDetail;
import com.deals.model.OTP;
import com.deals.model.User;
import com.deals.model.UserDetail;
import com.deals.repository.OTPRepository;
import com.deals.repository.UserDetailRepository;
import com.deals.repository.UserRepository;
import com.deals.util.App;
import com.deals.util.Status;


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
	
	public Status create(User user){
		Object obj = null;
		
		if(user != null){
			// User creation START
			user.setAuthType(AuthType.PENDING);
			user = userRepository.saveAndFlush(user);
			// User creation END
			
			// Creating OTP START
			String oneTimePassword = App.generateKey(6);
			OTP otp = new OTP();
			otp.setOtpCode(oneTimePassword);
			otp.setUser(user);
			otpRepository.saveAndFlush(otp);
			// Creating OTP END
			
			// Sending OPT to users EmailId START
			EMail eMail = new EMail();
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
			}
			// Sending OPT to users EmailId END
			status = App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, obj);
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, obj);
		}
		return status;
	}
	
	public Status getUser(Long id){
		if(id != null && id !=0){
			User user = userRepository.findOne(id);
			status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, user);
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, null);
		}
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
			
			user = userRepository.saveAndFlush(existingUser);
			status = App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, user);
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

	public Status verifyOTP(String codeOTP, Long userId){
		OTP otpObj = otpRepository.findByUserId(userId);
		if(otpObj !=null && otpObj.getOtpCode().equals(codeOTP)){
			otpObj.setOtpCode(codeOTP);
			otpObj.getUser().setAuthType(AuthType.OK);
			otpRepository.saveAndFlush(otpObj);
			status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, App.MSG_OK);
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, App.MSG_INVALID_KEY);
		}
		return status;
	}
	
	public Status findUsersByUserType(UserType userType){
		List<User> users = userRepository.findAllByUserType(userType);
		return App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, users);
		
	}
	
	public Status findAll(){
		List<User> users = userRepository.findAll();
		Status status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, users);
		return status;
	}
	
}
