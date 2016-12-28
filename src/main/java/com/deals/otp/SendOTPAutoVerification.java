package com.deals.otp;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;


/*
* This code is based on jersey-client library.
* For gradle based project use compile 'com.sun.jersey:jersey-client:1.18.4'
* You can also download the jar and add it to you project.
* */
public class SendOTPAutoVerification {

    private static String otp;

    //Base URL
    public static String baseUrl = "https://sendotp.msg91.com/api";
    //public static String baseUrl = "http://54.169.180.68:8080/SendOTP-API";

    // Your application key
    public static String applicationKey = "mtBPgYy6ibP4ZthwhT4172obHwe3oiOFC5Aq71ennU8eDNK2MojbvG0avMbyvkIrkPGYYKPNlAuh3cIviZvWbvpa0EEIrGG07gOuMaHbRTRfQPwSHp4HqzlabTtSiYydp3FA7TH4Gy9e7ilNf5VXBw==";
    /*
    * This function is used to send OTP message on mobile number
    * */
    public static String generateOTP(String countryCode, String mobileNumber){
    	String output = null;
        try {
            Client client = Client.create();
            String Url  = baseUrl+"/generateOTP";
            WebResource webResource = client.resource(Url);

            HashMap<String, String> requestBodyMap = new HashMap();
            requestBodyMap.put("countryCode",countryCode);
            requestBodyMap.put("mobileNumber",mobileNumber);
            JSONObject requestBodyJsonObject = new JSONObject(requestBodyMap);
            String input = requestBodyJsonObject.toString();

            ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
                    .header("application-Key", applicationKey)
                    .post(ClientResponse.class, input);
            output = response.getEntity(String.class);
            System.out.println("Request: "+output);
            //fetch your oneTimePassword and save it to session or db
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonobj = new JSONObject(output);
        String code = "";
        if(jsonobj.getJSONObject("response") != null){
        	code = jsonobj.getJSONObject("response").getString("code");
        }
        return code;
    }

    /*
    * This function is used to send OTP message on mobile number
    * */
    public static void verifyOTP(String countryCode, String mobileNumber, String oneTimePassword){
        try {
            //fetch your oneTimePassword from session or db
            //and compare it with the OTP sent from clien
            Client client = Client.create();
            String Url  = baseUrl+"/verifyOTP";
            WebResource webResource = client.resource(Url);

            HashMap<String, String> requestBodyMap = new HashMap();
            requestBodyMap.put("countryCode",countryCode);
            requestBodyMap.put("mobileNumber",mobileNumber);
            requestBodyMap.put("oneTimePassword",oneTimePassword);
            JSONObject requestBodyJsonObject = new JSONObject(requestBodyMap);
            String input = requestBodyJsonObject.toString();

            ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
                    .header("application-Key", applicationKey)
                    .post(ClientResponse.class, input);
            String output = response.getEntity(String.class);
            System.out.println("Response: "+output);
            JSONObject jsonObj = new JSONObject(output);
            JSONObject responseJson = jsonObj.getJSONObject("response");
            String status = responseJson.getString("code");

            if("NUMBER_VERIFIED_SUCCESSFULLY".equalsIgnoreCase(status)){
                System.out.println("Verified");
            }
            else{
                System.out.println("Invalid code");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
