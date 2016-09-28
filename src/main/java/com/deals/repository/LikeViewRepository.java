package com.deals.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.LikeView;

@Transactional
public interface LikeViewRepository extends JpaRepository<LikeView, Long>{

	
	LikeView findByUserIdAndMerchantIdAndType(Long userId, Long merchantId, String type);
	public void deleteAllByUserId(Long id);
}
