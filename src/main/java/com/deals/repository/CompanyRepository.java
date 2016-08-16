package com.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

}
