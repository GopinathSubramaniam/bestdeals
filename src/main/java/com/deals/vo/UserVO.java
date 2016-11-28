package com.deals.vo;

import java.util.ArrayList;
import java.util.List;

import com.deals.enums.UserType;
import com.deals.model.Deal;

import com.deals.model.PublicUserPlan;
import com.deals.model.User;
import com.deals.model.UserDetail;
import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Setter
@Getter
public class UserVO {

	private Long id;
	private String createdDate;
	private Long userDetailId;
	private String name;
	private UserType userType;
	private String password;
	private String email;
	private String mobile;
	private String address1;
	private String address2;
	private String address3;
	private List<String> phoneNumbers = new ArrayList<String>();
	private String timings;
	private Long likes;
	private Long views;
	private double latitude;
	private double longitude;
	private List<Deal> deals;
	private List<ImageVo> imageUrls;
	
	private String placeName; //Village Name
	private String shopName; //Shop Name
	private String cityName;
	private String stateName;
	private String description;
	private String qrCode;
	private String encryptedQrCode;
	
	private Long planId;
	private String planName;
	private String planExpiryDate;
	private String planDescription;

	public UserVO() {
	}
	/*public UserVO(User user, UserDetail userDetail, PublicUserPlan publicUserPlan, List<Deal> deals) {
		this.setId(user.getId());
		this.setMobile(user.getMobile());
		this.setEmail(user.getEmail());
		this.setName(user.getName());
		this.setUserType(user.getUserType());

		if(userDetail != null){
			this.setAddress1(userDetail.getAddress1());
			this.setAddress2(userDetail.getAddress2());
			this.setAddress3(userDetail.getAddress3());
			if (userDetail.getVillage() != null)
				if (userDetail.getVillage().getTaluka() != null)
					if (userDetail.getVillage().getTaluka().getCity() != null)
						this.setCityName(userDetail.getVillage().getTaluka().getCity().getName());
			this.setLatitude(userDetail.getLatitude());
			this.setLongitude(userDetail.getLongitude());
			this.phoneNumbers.add(userDetail.getPhoneNumbers());
			this.setShopName(userDetail.getShopName());
//			this.setStateName(userDetail.getVillage().getTaluka().getCity().getState().getName());
			this.setTimings(userDetail.getDescription());
			this.setLikes(userDetail.getLikes());
			this.setViews(userDetail.getViews());
		}

		if(user.getPlan() != null){
			this.setPlanId(user.getPlan().getId());
			this.setPlanName(user.getPlan().getName());
			this.setPlanDescription(user.getPlan().getDescription());
		}

		if(publicUserPlan != null){
			this.setQrCode(publicUserPlan.getQrCode().getNormalQrCode());
			this.setEncryptedQrCode(publicUserPlan.getQrCode().getEncryptedQrCode());
			this.setPlanId(publicUserPlan.getId());
			this.setPlanName(publicUserPlan.getPlanType().toString());
			this.setPlanDescription(publicUserPlan.getDescription());
			this.setPlanExpiryDate(publicUserPlan.getEndDate().toString());
		}

		if (deals != null && deals.size() > 0) {
			imageUrls = new ArrayList<>();
			for (Deal deal: deals) {
				ImageVo iVo = new ImageVo();
				iVo.setImgUrl(deal.getImgUrl());
				iVo.setDescription(deal.getDescription());
				imageUrls.add(iVo);
			}
		}
	}*/
}
