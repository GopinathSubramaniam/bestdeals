package com.deals.repository;

import com.deals.model.PublicUserPlan;
import com.deals.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface PublicUserPlanRepository extends JpaRepository<PublicUserPlan, Long>{

	PublicUserPlan findByUserId(Long id);
	PublicUserPlan findByQrCodeEncryptedQrCode(String encryptedQrCode);
	void deleteByUser(User user);
}
