package com.deals.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deals.model.Village;

@Transactional
public interface VillageRepository extends JpaRepository<Village, Long>{

	@Query("select v.id, v.name from Village v where v.taluka.id=?")
	public List<Village> findAllByTalukaId(Long id);
}
