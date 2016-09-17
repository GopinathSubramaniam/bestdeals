package com.deals.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.model.UserDetail;
import com.deals.repository.UserDetailRepository;
import com.deals.util.App;
import com.deals.util.Status;

@Service
public class UserDetailService {

	private static Status status = new Status();
	
	@Autowired
	private UserDetailRepository userDetailRepository;
	
	public Status create(UserDetail userDetail){
		if(userDetail != null){
			userDetail = userDetailRepository.saveAndFlush(userDetail);
			status = App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, userDetail);
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, userDetail);
		}
		return status;
	}
	
	public Status findAllByUserId(Long userId){
		List<UserDetail> userDetail = userDetailRepository.findAllByUserId(userId);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, userDetail);
		return status;
	}
	
	public Status findLikeCityAndPlaceName(String cityName, String placeName){
		UserDetail userDetail = userDetailRepository.findByPlaceNameLikeAndCityNameLike(placeName, cityName);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, userDetail);
		return status;
	}
	
}
