package com.deals.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.enums.PlanType;
import com.deals.model.PublicUserPlan;
import com.deals.model.QRCode;
import com.deals.model.User;
import com.deals.repository.PublicUserPlanRepository;
import com.deals.repository.QRCodeRepository;
import com.deals.util.App;
import com.deals.util.Secret;
import com.deals.util.Status;

@Service
public class PublicUserPlanService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static Status status = new Status();
	
	@Autowired
	private PublicUserPlanRepository publicUserPlanRepository;
	
	@Autowired
	private QRCodeRepository qrCodeRepository;
	
	
	public Status create(User user){
		
		String encryptedKey =  new Secret().encrypt(user.getMobile());
		
		QRCode qrCode = new QRCode();
		qrCode.setNormalQrCode(Secret.secretKey+user.getMobile());
		qrCode.setEncryptedQrCode(encryptedKey);
		
		qrCode = qrCodeRepository.saveAndFlush(qrCode);
		
		PublicUserPlan publicUserPlan = new PublicUserPlan();
		publicUserPlan.setPlanType(PlanType.FREE);
		publicUserPlan.setDescription("You are on six month trial period");
		publicUserPlan.setValidityInMonths(6);
		publicUserPlan.setPercentage(new Double(20));
		publicUserPlan.setStartDate(new Date());
		
		Date endDate = new Date();
		int month = endDate.getMonth()+publicUserPlan.getValidityInMonths();
		endDate.setMonth(month);
		publicUserPlan.setEndDate(endDate);
		
		publicUserPlan.setUser(user);
		publicUserPlan.setQrCode(qrCode);
		
		publicUserPlan = publicUserPlanRepository.saveAndFlush(publicUserPlan);
		return App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, publicUserPlan);
	}
	
	public Status createQRCode(QRCode qrCode){
		return App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, qrCodeRepository.saveAndFlush(qrCode));
	}
	
	
	public List<PublicUserPlan> getPublicUserPlans(){
		return publicUserPlanRepository.findAll();
	}
	
	public Status getPublicUserPlan(Long userId){
		PublicUserPlan publicUserPlan = publicUserPlanRepository.findOne(userId);
		return App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, publicUserPlan);
	}
	
	public Status updatePublicUserPlan(PublicUserPlan userPlan){
		log.info("Update Public User Plan ::: "+userPlan);
		PublicUserPlan publicUserPlan = publicUserPlanRepository.findOne(userPlan.getId());
		publicUserPlan.setAmount(userPlan.getAmount());
		publicUserPlan.setDescription(userPlan.getDescription());
		publicUserPlan.setPercentage(userPlan.getPercentage());
		publicUserPlan.setPlanType(userPlan.getPlanType());
		publicUserPlan.setStartDate(userPlan.getStartDate());
		publicUserPlan.setEndDate(userPlan.getEndDate());
		publicUserPlanRepository.saveAndFlush(publicUserPlan);
		return App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, publicUserPlan);
	}
	public PublicUserPlan findByUserId(Long userId) {
		return publicUserPlanRepository.findByUserId(userId);
	}
	public PublicUserPlan findByQrCodeEncryptedQrCode(String encryptedQrCode) {
		return publicUserPlanRepository.findByQrCodeEncryptedQrCode(encryptedQrCode);
	}
}
