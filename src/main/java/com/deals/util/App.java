package com.deals.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.deals.enums.EmailType;
import com.deals.model.Deal;
import com.deals.model.EMail;
import com.deals.model.EmailDetail;
import com.deals.model.User;
import com.deals.model.UserDetail;
import com.deals.vo.DealVO;
import com.deals.vo.UserVO;

//import com.way2sms.SMS;
@Service
public class App {
	
	public static String CODE_OK = "200";
	public static String CODE_FAIL = "500";
	public static String CODE_CANT_ACCESS = "403";
	
	public static String STATUS_OK = "OK";
	public static String STATUS_CREATE = "CREATED";
	public static String STATUS_UPDATE = "UPDATED";
	public static String STATUS_DELETE = "DELETED";
	public static String STATUS_FAIL = "FAILED";
	
	public static String MSG_OK = "OK";
	public static String MSG_CREATE = "Created Successfully";
	public static String MSG_UPDATE = "Updated Successfully";
	public static String MSG_DELETE = "Deleted Successfully";
	public static String MSG_FAIL = "Request Failed";
	public static String MSG_CONN_ERROR = "Server connection error. Please try again later";
	public static String MSG_LOGIN_ERROR = "Invalid Email Or Passsword";
	
	public static String MSG_AUTH_DENINED = "Autnentication Denied";
	public static String MSG_MAIL_SENT = "Mail sent successfully";
	public static String MSG_INVALID_KEY = "Invaid Key";
	
	public static String MSG_OTP_SUBJECT = "OTP from BestDeals";
	public static String MSG_USE_OTP_LOGIN = "Use this OTP for Login : ";
	public static String MSG_MAIL_SUBJECT = "Welcome to BestDeals";
	public static String MSG_MAIL_BODY = "Welcome to BestDeals. Enjoy your deals with us";
	
	public static String MSG_USER_NOT_AUTH = "User not authenticated";
	public static String MSG_USER_NOT_REGISTERED = "Mobile number is not registered";
	public static String MSG_USER_INCORRECT_PASSWORD = "Invalid password";
	public static String MSG_USER_EXISTS = "User already exists";
	public static String MSG_USER_INVALID_EMAIL = "Invalid Email";
	
	public static String COUNTRY_CODE_IN = "91";
	
	private static Status status = new Status();
	
	public static HttpSession setUserInSession(HttpServletRequest req, User user){
		HttpSession httpSession = req.getSession();
		httpSession.setAttribute("userId", user.getId());
		httpSession.setAttribute("username", user.getName());
		httpSession.setAttribute("loginState", user.getLoginState());
		
		return httpSession;
	}
	
	public static List<String> getPlanTypes(){
		List<String> planTypes = new ArrayList<String>();
		planTypes.add("FREE");
		planTypes.add("SILVER");
		planTypes.add("GOLD");
		planTypes.add("PLATINUM");
		return planTypes;
	}
	
	public static List<String> getUserTypes(){
		List<String> userTypes = new ArrayList<String>();
		userTypes.add("ADMIN");
		userTypes.add("FRANCHISE");
		userTypes.add("PUBLIC");
		userTypes.add("MERCHANT");
		return userTypes;
	}
	
	public static EMail setEmailObject(String templateName, EmailDetail emailDetail){
		EMail eMail = new EMail();
		eMail.setEmailType(EmailType.PASSWORD);
		eMail.setSenderEmail("gomyshoppy@gmail.com");
		eMail.setSenderName("GoMyShoppy");
		eMail.setTemplateName(templateName);
		eMail.setEmailDetail(emailDetail);
		return eMail;
	}
	
	public static Status getResponse(String statusCode, String statusMsg, String message,  Object data ){
		status.setStatusCode(statusCode);
		status.setStatusMsg(statusMsg);
		status.setMessage(message);
		status.setData(data);
		return status;
	}
	
	public static UserVO setUserVo(User user, UserDetail userDetail, List<Deal> deals){
		UserVO userVO = new UserVO();
		
		if(user != null){
			userVO.setId(user.getId());
			userVO.setName(user.getName());
			userVO.setMobile(user.getMobile());
			userVO.setEmail(user.getEmail());
			userVO.setUserType(user.getUserType());
			userVO.setPassword(user.getPassword());
			userVO.setCreatedDate(user.getCreatedDate().toString());
			if(user.getPlan() != null){
				userVO.setPlanId(user.getPlan().getId());
			}
		}
		if(userDetail !=null){
			userVO.setUserDetailId(userDetail.getId());
			userVO.setAddress1(userDetail.getAddress1());
			userVO.setAddress2(userDetail.getAddress2());
			userVO.setAddress3(userDetail.getAddress3());
			userVO.setLatitude(userDetail.getLatitude());
			userVO.setLongitude(userDetail.getLongitude());
			userVO.setLikes(userDetail.getLikes());
			userVO.setViews(userDetail.getViews());
			userVO.setTimings(userDetail.getTimings());
			userVO.setShopName(userDetail.getShopName());
			userVO.setPlaceName(userDetail.getPlaceName());
			userVO.setCityName(userDetail.getCity().getName());
			userVO.setStateName(userDetail.getCity().getState().getName());
			
			List<String> phoneNumbers = new ArrayList<>();
			if(userDetail.getPhoneNumbers() != null){
				String[] numbers = userDetail.getPhoneNumbers().split(",");
				for (String number : numbers) {
					phoneNumbers.add(number);
				}
				userVO.setPhoneNumbers(phoneNumbers);
			}
		}
		List<String> imgUrls = new ArrayList<String>();
		if(deals != null ){
			for (Deal deal : deals) {
				System.out.println("Deal ::: "+deal);
				System.out.println("Deal Image Url ::: "+deal.getImgUrl());
				imgUrls.add(deal.getImgUrl());
			}
		}
		userVO.setImageUrls(imgUrls);
		userVO.setDeals(deals);
		
		return userVO;
	}
	
	public static DealVO setDealVO(Deal deal) {
		DealVO dealVO = new DealVO();
		dealVO.setId(deal.getId());
		dealVO.setName(deal.getName());
		dealVO.setType(deal.getType().toString());
		dealVO.setDescription(deal.getDescription());
		dealVO.setCityName(deal.getCity().getName());
		dealVO.setStateName(deal.getCity().getState().getName());
		dealVO.setPlaceName(deal.getPlaceName());
		dealVO.setUser(setUserVo(deal.getUser(), null, null));
		dealVO.setImgUrl(deal.getImgUrl());
		return dealVO;
	}
	
	
	public static String randomSixDigitNum(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = length; i > 0; i -= 12) {
			int n = Math.min(12, Math.abs(i));
			sb.append(Long.toString(Math.round(Math.random() * Math.pow(36, n)), 36), n, '0');
		}
		return sb.toString();
	}
	
	public static String generateKey(int length) {
		String alphabet = new String("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"); // 9
		int n = alphabet.length(); // 10

		String result = new String();
		Random r = new Random(); // 11

		for (int i = 0; i < length; i++) // 12
			result = result + alphabet.charAt(r.nextInt(n)); // 13

		return result;
	}
	
	public static String sendSMS(){
//		SMS smsClient = new SMS();
//		Way2sms way2sms = new Way2sms();
//		way2sms.loginWay2SMS("9637752262", "eTHICALhACKER");
//		way2sms.sendSMS("8087466506", "Hi How are you?", "", "9637752262", "eTHICALhACKER");
//		smsClient.send("9637752262", "eTHICALhACKER", "8087466506", "Hi How are you?", "");
		return "OK";
		
	}
	
	/*public static void main(String[] args){
		String response = sendSMS();
		System.out.println("Response::: "+response);
	}
	*/
	
}
