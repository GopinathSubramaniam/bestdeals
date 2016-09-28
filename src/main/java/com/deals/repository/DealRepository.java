package com.deals.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deals.enums.PlanType;
import com.deals.enums.Priority;
import com.deals.model.Deal;

@Transactional
public interface DealRepository extends JpaRepository<Deal, Long>{

	public List<Deal> findAllByUserId(Long id);
	public List<Deal> findAllByPriorityAndUserPlanPlanType(Priority priority, PlanType planType);
	public List<Deal> findAllByUserPlanPlanType(PlanType planType);
	public List<Deal> findAllBySubCategoryId(Long id);
	
	@Query("select imgUrl from Deal d where d.user.id = ?")
	public List<String> findImgUrlByUserId(Long id);
	
	public Integer countByUserId(Long id);
//	public Long removeAllByUserId(Long id);
	public Long deleteByUserId(Long id);
	public List<Deal> findAllBySubCategoryCategoryNameOrSubCategoryNameOrCityNameOrPlaceNameAndUserPlanPlanType(String categoryName, String subCatName, String cityName, String placeName, PlanType planType);
	public List<Deal> findAllBySubCategoryCategoryNameAndSubCategoryNameOrCityNameOrPlaceNameAndUserPlanPlanType(String categoryName, String subCatName, String cityName, String placeName, PlanType planType);
	
	
}
