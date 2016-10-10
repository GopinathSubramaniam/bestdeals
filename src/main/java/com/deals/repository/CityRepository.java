package com.deals.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deals.model.City;

public interface CityRepository extends JpaRepository<City, Long>{

	@Query("select c.id, c.name from City c where c.state.id=?")
	List<City> findAllByStateId(Long id);
}
