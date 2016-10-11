/*package com.deals.util;

import javax.crypto.Cipher;

public class Core {
	
	 Cipher ecipher;
	 Cipher dcipher;
	 byte[] salt = {
	        (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
	        (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
	    };
	 int iterationCount = 19;
	
	public static void main(String[] args){
		String val ="80874665as062";
		String eKey = new Secret().encrypt(val);
		System.out.println("Key ::: "+val);
		System.out.println("E Key ::: "+eKey);
		String dKey = new Secret().decrypt(eKey);
		System.out.println("D Key ::: "+dKey);
	}
	
	

}
*/