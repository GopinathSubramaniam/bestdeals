package com.deals.repository;

import com.deals.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long>{

	@Query("SELECT c.id FROM SubCategory c WHERE " +
			"LOWER(c.name) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
	public List<Long> findIdByNameLike(@Param("searchTerm") String name);

	@Query("SELECT c.id FROM SubCategory c WHERE " +
			"LOWER(c.category.name) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
	public List<Long> findIdByCategoryNameLike(@Param("searchTerm") String name);

	List<SubCategory> findAllByCategoryId(Long id);
	
}
