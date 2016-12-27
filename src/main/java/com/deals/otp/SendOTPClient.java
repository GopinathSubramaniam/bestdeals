package com.deals.otp;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.deals.util.App;

@Service
public class SendOTPClient {
    SendOTPServer sendOTPServer = new SendOTPServer();

    public JSONObject sendOTP(String countryCode, String mobNumber){
        return sendOTPServer.generateOTP(countryCode, mobNumber);
    }
    
    public boolean verifyOTP(String mobileNumber, String oneTimePassword){
        return sendOTPServer.verifyOTP(App.COUNTRY_CODE_IN, mobileNumber, oneTimePassword);
    }
    
}
