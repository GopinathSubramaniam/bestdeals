package com.deals.util;

public class Core {

	public static void main(String[] args){
		String val = "1, 2, 3";
		System.out.println();
		String[] vals = val.split(",");
		for (String string : vals) {
			System.out.println(string.trim());
		}
	}
}
