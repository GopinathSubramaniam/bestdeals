package com.deals.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@ToString
@Setter
@Getter
@Entity
public class QRCode {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@JsonIgnore
	private String normalQrCode;
	private String encryptedQrCode;
	
}
