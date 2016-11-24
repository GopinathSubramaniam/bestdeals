package com.deals.repository;

import com.deals.enums.PlanType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long>{
    Plan findByPlanType(PlanType planType);
}
