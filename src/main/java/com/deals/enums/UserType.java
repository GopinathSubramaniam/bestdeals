package com.deals.enums;

public enum UserType {

	ADMIN,
	SALES_MAN,
	FRANCHISE,
	PUBLIC,
	MERCHANT;

	public UserType getUserType(String userType) {
		if (UserType.FRANCHISE.name().equals(userType))
			return FRANCHISE;
		if (UserType.ADMIN.name().equals(userType))
			return ADMIN;
		if (UserType.SALES_MAN.name().equals(userType))
			return SALES_MAN;
		if (UserType.PUBLIC.name().equals(userType))
			return PUBLIC;
		if (UserType.MERCHANT.name().equals(userType))
			return MERCHANT;
		return null;
	}
}
