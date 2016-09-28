package com.deals.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.Salesman;

@Transactional
public interface SalesmanRepository extends JpaRepository<Salesman, Long>{

	public List<Salesman> findAllBySalesManagerId(Long id);
	
	public void deleteBySalesManagerId(Long id);
}
