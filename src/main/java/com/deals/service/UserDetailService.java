package com.deals.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.model.UserDetail;
import com.deals.repository.UserDetailRepository;
import com.deals.util.App;
import com.deals.util.Status;

@Service
public class UserDetailService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static Status status = new Status();
	
	@Autowired
	private UserDetailRepository userDetailRepository;
	
	public Status create(UserDetail userDetail){
		System.out.println("Create UserDetails :::: "+userDetail);
		if(userDetail != null){
			userDetail = userDetailRepository.saveAndFlush(userDetail);
			status = App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, userDetail);
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, userDetail);
		}
		return status;
	}
	
	public Status update(UserDetail userDetail){
		if(userDetail != null){
			UserDetail existsUserDetail = null;
			if(userDetail.getId() != null){
				existsUserDetail = userDetailRepository.findOne(userDetail.getId());
			}else{
				existsUserDetail = new UserDetail();
			}
			if(userDetail.getPhoneNumbers() != null){
				String phoneNumbers = "";
				String[] numbers = userDetail.getPhoneNumbers().split(",");
				for (String number : numbers) {
					phoneNumbers += number;
				}
				existsUserDetail.setPhoneNumbers(phoneNumbers);
			}
			
			if( userDetail.getAddress1() != null ){
				existsUserDetail.setAddress1(userDetail.getAddress1());
			} 
			
			if( userDetail.getAddress2() != null ){
				existsUserDetail.setAddress2(userDetail.getAddress2());
			}
			if( userDetail.getAddress3() != null ){
				existsUserDetail.setAddress3(userDetail.getAddress3());
			}
			if( userDetail.getDescription() != null ){
				existsUserDetail.setDescription(userDetail.getDescription());
			}
			
			existsUserDetail.setUser(userDetail.getUser());
			userDetailRepository.saveAndFlush(existsUserDetail);
			status = App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, existsUserDetail);
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
		UserDetail userDetail = userDetailRepository.findByPlaceNameLikeAndVillageNameLike(placeName, cityName);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, userDetail);
		return status;
	}
	
}
