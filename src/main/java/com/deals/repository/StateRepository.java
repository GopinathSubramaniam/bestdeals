package com.deals.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.State;

public interface StateRepository extends JpaRepository<State, Long>{

//	@Query("select s.id, s.name from State s where s.country.id=?")
	List<State> findIdAndNameByCountryId(Long id);
	
	List<State> findAllIdAndNameByCountryId(Long id);
}
