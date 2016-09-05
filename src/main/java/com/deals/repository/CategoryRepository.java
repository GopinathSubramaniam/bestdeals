package com.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
