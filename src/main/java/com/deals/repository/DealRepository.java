package com.deals.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.Deal;

public interface DealRepository extends JpaRepository<Deal, Long>{

	public List<Deal> findAllByUserId(Long id);
	
}
