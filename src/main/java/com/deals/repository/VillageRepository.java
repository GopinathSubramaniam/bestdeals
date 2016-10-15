package com.deals.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.model.Village;

@Transactional
public interface VillageRepository extends JpaRepository<Village, Long>{

	public List<Village> findAllByTalukaId(Long id);
}
