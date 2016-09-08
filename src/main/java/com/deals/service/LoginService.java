package com.deals.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.enums.AuthType;
import com.deals.enums.LoginState;
import com.deals.model.User;
import com.deals.repository.UserRepository;
import com.deals.util.App;
import com.deals.util.Status;

@Service
public class LoginService {
	private static Status status =  new Status();
	
	@Autowired
	private UserRepository userRepository;
	
	public Status login(User user){
		if(user != null){
			User existUser = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
			System.out.println("User :::: "+user);
			System.out.println("ExistUser Type :::: "+existUser.getUserType());
			System.out.println("User Type :::: "+user.getUserType());
			
			if(existUser!=null && user.getUserType().equals(existUser.getUserType())){
				if(!existUser.getAuthType().equals(AuthType.OK)){
					status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_USER_NOT_AUTH, null);
				}else{
					existUser.setLoginState(LoginState.ONLINE);
					userRepository.saveAndFlush(existUser);
					status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, existUser);
				}
			}else{
				status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_LOGIN_ERROR, null);
			}
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_LOGIN_ERROR, null);
		}
		return status;
	}
	
	
	public Status mobileLogin(User user){
		if(user != null ){
			User existUser = userRepository.findByMobile(user.getMobile());
			if(existUser != null){
				status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, existUser);
				if(!(existUser.getPassword().equals(user.getPassword()))){
					status.setStatusCode(App.CODE_FAIL);
					status.setStatusMsg(App.STATUS_FAIL);
					status.setMessage(App.MSG_USER_INCORRECT_PASSWORD);
					status.setData(null);
				}else if(!existUser.getAuthType().equals(AuthType.OK)){
					status.setStatusCode(App.CODE_FAIL);
					status.setStatusMsg(App.STATUS_FAIL);
					status.setMessage(App.MSG_AUTH_DENINED);
					status.setData(null);
				}
			}else{
				status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_USER_NOT_REGISTERED, user);
			}
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_LOGIN_ERROR, user);
		}
		return status;
	}
	
	public Status logout(Long id){
		if(id != null && id != 0){
			User user = userRepository.findOne(id);
			if(user!=null){
				user.setLoginState(LoginState.OFFLINE);
				userRepository.saveAndFlush(user);
//				user.setPassword(null);
				status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, user);
			}else{
				status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_LOGIN_ERROR, user);
			}
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_LOGIN_ERROR, null);
		}
		return status;
	}
}
