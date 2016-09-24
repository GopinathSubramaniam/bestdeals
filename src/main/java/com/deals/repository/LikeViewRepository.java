package com.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.LikeView;

public interface LikeViewRepository extends JpaRepository<LikeView, Long>{

	
	LikeView findByUserIdAndMerchantIdAndType(Long userId, Long merchantId, String type);
}
