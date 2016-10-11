package com.deals.service;

import java.util.Date;

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
		publicUserPlan.setPlanType(PlanType.TRIAL);
		publicUserPlan.setDescription("You are on six month trial period");
		publicUserPlan.setValidatyInMonths(6);
		publicUserPlan.setPercentage(new Double(20));
		publicUserPlan.setStartDate(new Date());
		
		Date endDate = new Date();
		int month = endDate.getMonth()+publicUserPlan.getValidatyInMonths();
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
	
	
}
