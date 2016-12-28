package com.deals.service;

import com.deals.enums.PlanType;
import com.deals.model.Plan;
import com.deals.model.PublicUserPlan;
import com.deals.model.QRCode;
import com.deals.model.User;
import com.deals.repository.PublicUserPlanRepository;
import com.deals.repository.QRCodeRepository;
import com.deals.util.App;
import com.deals.util.Secret;
import com.deals.util.Status;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PublicUserPlanService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static Status status = new Status();
	@Autowired PlanService planService;
	@Autowired UserService userService;
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
		publicUserPlan.setPercentage(0d);
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

	public PublicUserPlan updatePlanForUser(User user, Plan plan) {
		PublicUserPlan publicUserPlan = findByUserId(user.getId());
		if (publicUserPlan == null)
			publicUserPlan = (PublicUserPlan) create(user).getData();

//		renewal with previous balance based on topup from payment service. And its not plan amount.
		//publicUserPlan.setAmount();

		publicUserPlan.setDescription(plan.getDescription());
		publicUserPlan.setPercentage(0d);
		publicUserPlan.setPlanType(plan.getPlanType());
		publicUserPlan.setStartDate(new Date());

		JSONObject rule = new JSONObject(plan.getRules());
		int validMonths = rule.getInt("validity_months");
		publicUserPlan.setEndDate(addMonths(publicUserPlan.getStartDate(), validMonths));

		return save(publicUserPlan);
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

	public PublicUserPlan updateUserPlanToSimple(User merchant, long publicUserId) throws Exception {
		PublicUserPlan merchantUserPlan = publicUserPlanRepository.findByUserId(merchant.getId());
		if (merchant.getId() != null && merchantUserPlan == null) {
			merchantUserPlan = (PublicUserPlan) create(merchant).getData();
//			throw new Exception(App.MSG_NO_BALANCE_DETAILS);
		}
		Plan simplePlan = planService.getByPlanType(PlanType.SIMPLE);
		double beforeAmount = merchantUserPlan.getAmount();
		if (beforeAmount < simplePlan.getAmount() ) {
			throw new Exception(App.MSG_INSUFFICIENT_BAL);
		}
		User publicUser = userService.findOne(publicUserId);
		if (publicUser == null)
			throw new Exception(App.MSG_USER_NOT_FOUND);
		publicUser.setPlan(simplePlan);
		userService.update(publicUser);

		merchantUserPlan.setAmount(beforeAmount - simplePlan.getAmount());
		merchantUserPlan.setChangedBy(merchant.getMobile());
		publicUserPlanRepository.saveAndFlush(merchantUserPlan);

		//TODO maintain transaction/Order history

		return merchantUserPlan;
	}

	public PublicUserPlan findByUserId(Long userId) {
		return publicUserPlanRepository.findByUserId(userId);
	}
	public PublicUserPlan findByQrCodeEncryptedQrCode(String encryptedQrCode) {
		return publicUserPlanRepository.findByQrCodeEncryptedQrCode(encryptedQrCode);
	}

	public void deleteByUser(User user) {
		publicUserPlanRepository.deleteByUser(user);
	}

	public PublicUserPlan save(PublicUserPlan publicUserPlan) {
		return publicUserPlanRepository.saveAndFlush(publicUserPlan);
	}

	public static Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); //minus number would decrement the days
		return cal.getTime();
	}

	public static Date addMonths(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months); //minus number would decrement the months
		return cal.getTime();
	}
}
