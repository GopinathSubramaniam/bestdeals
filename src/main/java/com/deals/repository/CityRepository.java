package com.deals.repository;

import com.deals.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long>{

	List<City> findAllByStateId(Long id);
}
