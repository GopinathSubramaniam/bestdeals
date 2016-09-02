package com.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long>{

}
