package com.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long>{

	UserDetail findByUserId(Long id); 
}
