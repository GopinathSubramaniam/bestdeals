package com.deals.repository;

import com.deals.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    @Query("SELECT c.id FROM Category c WHERE " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    public Iterable<Long> findIdByNameLike(@Param("searchTerm") String name);

    public Iterable<Category> findByNameLike(String name);
}
