package com.deals.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.Taluka;

@Transactional
public interface TalukaRepository extends JpaRepository<Taluka, Long>{

	public List<Taluka> findAllByCityId(Long id);
}
