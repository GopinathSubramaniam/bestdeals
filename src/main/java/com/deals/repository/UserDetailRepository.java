package com.deals.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.UserDetail;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long>{

	List<UserDetail> findAllByUserId(Long id); 
	UserDetail findByUserId(Long id); 
	
	UserDetail findByPlaceNameLikeAndCityNameLike(String placeName, String cityName);
}
