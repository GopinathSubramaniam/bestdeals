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
			user = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
			System.out.println("User >>> "+user);
			if(user!=null){
				if(!user.getAuthType().equals(AuthType.OK)){
					status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_USER_NOT_AUTH, null);
				}else{
					user.setLoginState(LoginState.ONLINE);
					userRepository.saveAndFlush(user);
					user.setPassword(null);
					status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, user);
				}
			}else{
				status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_LOGIN_ERROR, null);
			}
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_LOGIN_ERROR, null);
		}
		return status;
	}
	
	public Status logout(User user){
		if(user != null){
			user = userRepository.findByEmail(user.getEmail());
			if(user!=null){
				user.setLoginState(LoginState.OFFLINE);
				userRepository.saveAndFlush(user);
				user.setPassword(null);
				status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, user);
			}else{
				status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_LOGIN_ERROR, user);
			}
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_LOGIN_ERROR, user);
		}
		return status;
	}
}
