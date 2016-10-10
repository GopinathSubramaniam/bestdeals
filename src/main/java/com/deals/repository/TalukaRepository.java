package com.deals.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deals.model.Taluka;

@Transactional
public interface TalukaRepository extends JpaRepository<Taluka, Long>{

	@Query("select t.id, t.name from Taluka t where t.city.id=?")
	public List<Taluka> findAllByCityId(Long id);
}
