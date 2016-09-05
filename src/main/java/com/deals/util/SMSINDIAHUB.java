/*package com.deals.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SMSINDIAHUB {
	public static String retval = "";

	public static String SMSSender(String user, String password, String msisdn, String msg, String sid, String fl) {
		String rsp = "";

		try {
			// Construct The Post Data
			String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
			data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
			data += "&" + URLEncoder.encode("msisdn", "UTF-8") + "=" + URLEncoder.encode(msisdn, "UTF-8");
			data += "&" + URLEncoder.encode("msg", "UTF-8") + "=" + URLEncoder.encode(msg, "UTF-8");
			data += "&" + URLEncoder.encode("sid", "UTF-8") + "=" + URLEncoder.encode(sid, "UTF-8");
			data += "&" + URLEncoder.encode("fl", "UTF-8") + "=" + URLEncoder.encode(fl, "UTF-8");

			// Push the HTTP Request
			URL url = new URL("http://cloud.smsindiahub.in/vendorsms/pushsms.aspx");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Read The Response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				// Process line...
				retval += line;
			}
			wr.close();
			rd.close();

			System.out.println(retval);
			rsp = retval;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rsp; 
	}
	
	public static void main(String[] args) {
		String response = SMSSender("shahidmcm", "!33D!nF0tech@", "9637752262", "Hi How are you?", "WEBSMS", "0");
//		String response = SMSSender("shahidmcm", "!33D!nF0tech@", "9637752262", "Hi How are you?", "ZEPFND", "0");

		System.out.println(response);
	} 

	
}
*/