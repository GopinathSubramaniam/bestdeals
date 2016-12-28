package com.deals.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

public class Secret {
	 Cipher cipher;
	 byte[] salt = {
		        (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
		        (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
		    };
	 int iterationCount = 19;
	 public static String secretKey = "gomyshoppy";
	 private KeySpec keySpec;
	 private SecretKey key;
	 private AlgorithmParameterSpec paramSpec;
	 
	 public Secret(){
		try {
			this.keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
			this.key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
			this.paramSpec = new PBEParameterSpec(salt, iterationCount);
			this.cipher = Cipher.getInstance(key.getAlgorithm());
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	
	public String encrypt(String plainText){
		String encStr = null;
        try {
			cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			String charSet="UTF-8";       
			byte[] in = plainText.getBytes(charSet);
			byte[] out = cipher.doFinal(in);
			encStr=new sun.misc.BASE64Encoder().encode(out);
		} catch (Exception e) {
			e.printStackTrace();
		}      
        return encStr;
    }
	
	public String decrypt(String encryptedText){
		String plainStr = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
			String charSet = "UTF-8";
			byte[] enc = new sun.misc.BASE64Decoder().decodeBuffer(encryptedText);
			byte[] utf8 = cipher.doFinal(enc);
			plainStr = new String(utf8, charSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
       return plainStr;
   }
	
	
}
