package com.deals.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.City;

public interface CityRepository extends JpaRepository<City, Long>{

	List<City> findAllByStateId(Long id);
}
