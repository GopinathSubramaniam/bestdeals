package com.deals.util;

import org.json.JSONObject;

public class Core {

	public static void main(String[] args){
		String val = "{\"status\":\"success\",\"response\":{\"code\":\"OTP_SENT_SUCCESSFULLY\",\"oneTimePassword\":\"45334\"}}";
        JSONObject jsonobj = new JSONObject(val);
        String code = jsonobj.getJSONObject("response").getString("code");
        System.out.println("val ::: "+  code);
	}
}
