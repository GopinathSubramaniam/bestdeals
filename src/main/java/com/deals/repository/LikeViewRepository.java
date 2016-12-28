package com.deals.repository;

import com.deals.model.LikeView;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface LikeViewRepository extends JpaRepository<LikeView, Long>{

	
	LikeView findByUserIdAndMerchantIdAndType(Long userId, Long merchantId, String type);
	public void deleteAllByUserId(Long id);
}
