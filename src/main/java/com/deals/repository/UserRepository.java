package com.deals.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.enums.UserType;
import com.deals.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByEmailAndPassword(String email, String password);
	User findByToken(String token);
	User findByEmail(String email);
	List<User> findAllByUserType(UserType userType);
}