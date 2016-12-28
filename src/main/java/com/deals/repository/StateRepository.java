package com.deals.repository;

import com.deals.model.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Long>{

//	@Query("select s.id, s.name from State s where s.country.id=?")
	List<State> findIdAndNameByCountryId(Long id);
	
	List<State> findAllIdAndNameByCountryId(Long id);
}
