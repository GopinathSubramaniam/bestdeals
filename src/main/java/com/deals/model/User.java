package com.deals.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrePersist;

import com.deals.enums.AuthType;
import com.deals.enums.LoginState;
import com.deals.enums.UserType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@Entity
public class User extends BaseEntity{
	
	private String name;
	private String mobile;
	
	@Column(unique=true)
	private String email;
	
	private String password;
	private String token;
	
	@Enumerated(EnumType.STRING)
	private UserType userType;
	
	@Enumerated(EnumType.STRING)
	private AuthType authType;
	
	@Enumerated(EnumType.STRING)
	private LoginState loginState;
	
	@PrePersist
	public void prePersist(){
		String uuid = UUID.randomUUID().toString();
		this.token = uuid;
	}
}
