package com.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.OTP;

public interface OTPRepository extends JpaRepository<OTP, Long>{
	
	public OTP findByUserId(Long id);
	
}
