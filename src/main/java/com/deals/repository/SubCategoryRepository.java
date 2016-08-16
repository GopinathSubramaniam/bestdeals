package com.deals.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long>{

	List<SubCategory> findAllByCategoryId(Long id);
	
}
