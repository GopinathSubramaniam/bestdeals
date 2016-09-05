package com.deals.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.enums.PlanType;
import com.deals.enums.Priority;
import com.deals.model.Deal;

public interface DealRepository extends JpaRepository<Deal, Long>{

	public List<Deal> findAllByUserId(Long id);
	public List<Deal> findAllByPriorityAndUserPlanPlanType(Priority priority, PlanType planType);
	
}
