package com.deals.repository;

import com.deals.model.Village;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface VillageRepository extends JpaRepository<Village, Long>{

	public List<Village> findAllByTalukaId(Long id);
}
