package com.deals.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.Salesman;

public interface SalesmanRepository extends JpaRepository<Salesman, Long>{

	public List<Salesman> findAllBySalesManagerId(Long id);
}
