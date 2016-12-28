package com.deals.repository;

import com.deals.model.Taluka;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface TalukaRepository extends JpaRepository<Taluka, Long>{

	public List<Taluka> findAllByCityId(Long id);
}
