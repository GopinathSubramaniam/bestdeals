package com.deals.model;

import com.deals.enums.AuthType;
import com.deals.enums.LoginState;
import com.deals.enums.UserType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@ToString
@Setter
@Getter
@Entity
public class User extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	@Column(unique=true, nullable = false)
	private String mobile;
	
	private String email;
	
	private String password;
	private String token;
	
	@Enumerated(EnumType.STRING)
	private UserType userType;
	
	@Enumerated(EnumType.STRING)
	private AuthType authType;
	
	@Enumerated(EnumType.STRING)
	private LoginState loginState;
	
	@ManyToOne
	private Plan plan;
	
	@PrePersist
	public void prePersist(){
		String uuid = UUID.randomUUID().toString();
		this.token = uuid;
	}
	
	public User(){}
	
	public User(Long id){
		super();
		this.setId(id);
	}
	
	
}
