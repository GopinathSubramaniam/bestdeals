package com.deals.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.deals.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import com.deals.enums.PlanType;
import com.deals.enums.Priority;
import com.deals.model.Deal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Transactional
public interface DealRepository extends JpaRepository<Deal, Long>{

	public Deal findByUserIdAndIsDefault(Long id, boolean isDefault);
	
	public List<Deal> findAllByUserId(Long id);
	public List<Deal> findAllByPriorityAndUserPlanPlanType(Priority priority, PlanType planType);
	public List<Deal> findAllByUserPlanPlanTypeAndIsDefault(PlanType planType, boolean isDefault);
	public List<Deal> findAllBySubCategoryId(Long id);
	public List<Deal> findAllBySubCategoryIdAndIsDefault(Long id, boolean isDefault);

	@Query(value = "select d from Deal d where d.subCategory = :id group by d.user")
	public List<Deal> findAllBySubCategoryIdGroupByUser(@Param("id") SubCategory subCategory);

	public Long countBySubCategoryId(Long id);

//	@Query( "select d from Deal d where subCategory in :ids" )
	public Long countBySubCategoryIdIn(@Param("ids") List<Long> subCategoryIdList);

//	@Query("select CONCAT(img_url,'?caption=',description) as imgUrl from Deal d where d.user.id = ?")
	public List<Deal> findImageUrlAndDescriptionByUserId(Long id);
	
	public Integer countByUserId(Long id);
//	public Long removeAllByUserId(Long id);
	public Long deleteByUserId(Long id);
	public List<Deal> findAllBySubCategoryCategoryNameOrSubCategoryNameOrCityNameOrPlaceNameOrDescriptionOrNameOrUserNameOrUserMobileOrUserPlanNameOrUserEmailAndUserPlanPlanType(String categoryName, String subCatName, String cityName, String placeName, String description, String name, String userName, String mobile, String planName, String email, PlanType planType);
	public List<Deal> findAllBySubCategoryCategoryNameAndSubCategoryNameOrCityNameOrPlaceNameAndUserPlanPlanType(String categoryName, String subCatName, String cityName, String placeName, PlanType planType);
	
	
}
