package com.deals.util;

import java.util.Random;

//import com.way2sms.SMS;

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
	
	
	private static Status status = new Status();
	
	public static Status getResponse(String statusCode, String statusMsg, String message,  Object data ){
		status.setStatusCode(statusCode);
		status.setStatusMsg(statusMsg);
		status.setMessage(message);
		status.setData(data);
		return status;
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
