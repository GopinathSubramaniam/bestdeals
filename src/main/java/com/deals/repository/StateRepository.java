package com.deals.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.State;

public interface StateRepository extends JpaRepository<State, Long>{

	List<State> findAllByCountryId(Long id);
}
