package com.deals.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.SalesManager;

@Transactional
public interface SalesManagerRepository extends JpaRepository<SalesManager, Long>{
	public List<SalesManager> findAllByUserId(Long id);
	public void deleteByUserId(Long id);
}
