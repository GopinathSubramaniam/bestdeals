package com.deals.vo;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@ToString
@Setter
@Getter
public class QRCodeResponse {

	private Object qr;
	private Object details;
	private boolean isvalidQR; 
}
