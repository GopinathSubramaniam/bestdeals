package com.deals.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Status {

	private String statusCode;
	private String statusMsg;
	private String message;
	private Object data;
}
