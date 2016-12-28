package com.deals.repository;

import com.deals.enums.PlanType;
import com.deals.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long>{
    Plan findByPlanType(PlanType planType);
}
