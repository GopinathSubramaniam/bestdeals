package com.deals.otp;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.deals.util.App;

@Service
public class SendOTPClient {
    
	/*private static String otpReceived;
    private static String countryCode;
    private static String mobNumber;
    private static InputStreamReader r=new InputStreamReader(System.in);
    private static BufferedReader br=new BufferedReader(r);*/

    /*public static void main(String gg[]){
        try {
            System.out.println("Enter country code: ");
            countryCode = br.readLine();
            System.out.println("Enter mobile number: ");
            mobNumber = br.readLine();

            System.out.println("Auto verify? ('Y' / 'N'): ");
            String choice = br.readLine();
            if(!("Y".equalsIgnoreCase(choice)) && !("N".equalsIgnoreCase(choice))){
                System.out.println("Invalid choice.");
                return;
            }
            if("Y".equalsIgnoreCase(choice)){
                sendOTPAutoVerification();
            }
            if("N".equalsIgnoreCase(choice)){
                sendOTPManualVerification();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public JSONObject sendOTP(String countryCode, String mobNumber){
    	SendOTPServer sendOTPServer = new SendOTPServer();
        return sendOTPServer.generateOTP(countryCode, mobNumber);
    }
    
    public boolean verifyOTP(String mobileNumber, String oneTimePassword){
    	SendOTPServer sendOTPServer = new SendOTPServer();
        return sendOTPServer.verifyOTP(App.COUNTRY_CODE_IN, mobileNumber, oneTimePassword);
    }
    
    /*private static void sendOTPManualVerification(){
        SendOTPServer sendOTPServer = new SendOTPServer();
        sendOTPServer.generateOTP(countryCode,mobNumber);

        System.out.println("Enter received otp: ");
        try {
            otpReceived = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sendOTPServer.verifyOTP(otpReceived);
    }
*/
    /*private static void sendOTPAutoVerification(){
        SendOTPAutoVerification sendOTPServer = new SendOTPAutoVerification();
        sendOTPServer.generateOTP(countryCode,mobNumber);

        System.out.println("Enter received otp: ");
        try {
            otpReceived = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sendOTPServer.verifyOTP(countryCode,mobNumber,otpReceived);
    }*/
}
