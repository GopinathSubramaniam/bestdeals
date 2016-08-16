package com.deals.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.Country;

public interface CountryRepository extends JpaRepository<Country, Long>{

}
