package com.deals.repository;

import com.deals.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OTPRepository extends JpaRepository<OTP, Long>{
	
	public OTP findByUserId(Long id);
	public void removeByUserId(Long id);
	
}
