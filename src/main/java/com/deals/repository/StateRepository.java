package com.deals.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deals.model.State;

public interface StateRepository extends JpaRepository<State, Long>{

	@Query("select s.id, s.name from State s where s.country.id=?")
	List<State> findAllByCountryId(Long id);
}
