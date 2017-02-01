package com.deals.repository;

import com.deals.enums.PlanType;
import com.deals.enums.Priority;
import com.deals.model.City;
import com.deals.model.Deal;
import com.deals.model.SubCategory;
import com.deals.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Transactional
public interface DealRepository extends JpaRepository<Deal, Long>{
	/*
	@Query("SELECT t FROM Todo t WHERE " +
		"LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
		"LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
	*/
	@Query(nativeQuery = true, value = "select" +
			"        deal0_.*" +
			/*"        deal0_.id as id1_4_," +
			"        deal0_.changed_by as changed_2_4_," +
			"        deal0_.changed_date as changed_3_4_," +
			"        deal0_.created_by as created_4_4_," +
			"        deal0_.created_date as created_5_4_," +
			"        deal0_.city_id as city_id14_4_," +
			"        deal0_.contact as contact6_4_," +
			"        deal0_.description as descript7_4_," +
			"        deal0_.img_url as img_url8_4_," +
			"        deal0_.is_default as is_defau9_4_," +
			"        deal0_.name as name10_4_," +
			"        deal0_.priority as priorit12_4_," +
			"        deal0_.sub_category_id as sub_cat15_4_," +
			"        deal0_.type as type13_4_," +
			"        deal0_.user_id as user_id16_4_ " +*/
			"    from" +
			"        deal deal0_ " +
			"    left outer join" +
			"        sub_category subcategor1_ " +
			"            on deal0_.sub_category_id=subcategor1_.id " +
			"    left outer join" +
			"        category category2_ " +
			"            on subcategor1_.category_id=category2_.id " +
			"    left outer join" +
			"        user_detail userdetail4_ " +
			"            on deal0_.user_id=userdetail4_.user_id " +
			"    left outer join" +
			"        city city3_ " +
			"            on deal0_.city_id=city3_.id " +
			"    where " +
			"        category2_.name LIKE LOWER(CONCAT('%',:categoryName, '%')) " +
			"        or subcategor1_.name LIKE LOWER(CONCAT('%',:subcategoryName, '%')) " +
			"        or city3_.name LIKE LOWER(CONCAT('%',:cityNme, '%')) " +
			"        or deal0_.description LIKE LOWER(CONCAT('%',:description, '%'))  " +
			"        or deal0_.name LIKE LOWER(CONCAT('%',:dealName, '%'))  " +
			"        or userdetail4_.shop_name LIKE LOWER(CONCAT('%',:shopname, '%'))  " +

			" 		or deal0_.user_id in (select ud.user_id from user_detail as ud where " +
			//"ud.city_id = (select id from city where city.name = '') OR " +
			" ud.village_id in (select v.id from village v where v.taluka_id in (select t.id from taluka t where t.name = :talukaName))) " +

			"	group by deal0_.user_id" +
			"	order by deal0_.id DESC" +
			"	LIMIT :limit"
			)
	public List<Deal> findByAny(@Param(value = "categoryName") 		String categoryName,
								@Param(value = "subcategoryName") 	String subcategoryName,
								@Param(value = "cityNme") 			String cityNme,
								@Param(value = "talukaName")			String talukaName,
								@Param(value = "description") 		String description,
								@Param(value = "dealName") 			String dealName,
								@Param(value = "shopname") 			String shopname,
								@Param(value = "limit") 			int limit
	);

	public Deal findByUserIdAndIsDefault(Long id, boolean isDefault);

	public List<Deal> findAllByUserId(Long id);

	@Query( "select d from Deal d where d.user.id in :ids group by d.user order by d.id desc" )
	public List<Deal> findByUserIdInGroupByUser(@Param("ids") List<Long> userIdList);

	public List<Deal> findByUserIn(Collection<User> user);

	public List<Deal> findAllByPriorityAndUserPlanPlanType(Priority priority, PlanType planType);

	public List<Deal> findAllByUserPlanPlanTypeAndIsDefault(PlanType planType, boolean isDefault);


	public List<Deal> findAllByUserPlanPlanTypeAndIsDefaultOrderByChangedDateDesc(PlanType planType, boolean isDefault, Pageable pageable);

	public List<Deal> findByUserInAndUserPlanPlanTypeAndIsDefaultOrderByChangedDateDesc(Collection<User> users, PlanType planType, boolean isDefault, Pageable pageable);

	public List<Deal> findAllBySubCategoryId(Long id);
	public List<Deal> findAllBySubCategoryIdAndIsDefault(Long id, boolean isDefault);

	@Query(value = "select d from Deal d where d.subCategory = :id group by d.user")
	public List<Deal> findAllBySubCategoryIdGroupByUser(@Param("id") SubCategory subCategory);

	public Long countBySubCategoryId(Long id);

//	@Query( "select d from Deal d where d.subCategory in :ids" )
	public Long countBySubCategoryIdIn(@Param("ids") List<Long> subCategoryIdList);

	@Query( "select d from Deal d where d.subCategory.id in :ids" )
	public List<Deal> findBySubCategoryIdIn(@Param("ids") List<Long> subCategoryIdList);

	@Query( "select d from Deal d where d.subCategory.id in :ids group by d.user order by d.id desc" )
	public List<Deal> findBySubCategoryIdInOnePerUser(@Param("ids") List<Long> subCategoryIdList);

	@Query( "select d from Deal d where d.isDefault = true and d.subCategory.id in :ids " )
	public List<Deal> findDefaultDealsBySubCategoryIdIn(@Param("ids") List<Long> subCategoryIdList);

	@Query( "select d from Deal d where d.isDefault = true and d.subCategory.id in :ids group by d.user" )
	public List<Deal> findDefaultDealsBySubCategoryIdInAndOnePerUser(@Param("ids") List<Long> subCategoryIdList);

	@Query( "select d from Deal d where d.subCategory.id in :ids group by d.user order by d.id desc" )
	public List<Deal> findBySubCategoryIdInAndOnePerUser(@Param("ids") List<Long> subCategoryIdList);

	@Query("SELECT d FROM Deal d WHERE " +
			"LOWER(d.city.name) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
	public List<Deal> findByCityName(@Param("searchTerm") String cityName);

//	@Query("select CONCAT(img_url,'?caption=',description) as imgUrl from Deal d where d.user.id = ?")
	public List<Deal> findImageUrlAndDescriptionByUserId(Long id);
	
	public Integer countByUserId(Long id);
	public Long deleteByUserId(Long id);
	public List<Deal> findAllBySubCategoryCategoryNameOrSubCategoryNameOrCityNameOrDescriptionOrNameOrUserNameOrUserMobileOrUserPlanNameOrUserEmailAndUserPlanPlanType(String categoryName, String subCatName, String cityName,String description, String name, String userName, String mobile, String planName, String email, PlanType planType);
	public List<Deal> findAllBySubCategoryCategoryNameAndSubCategoryNameOrCityNameAndUserPlanPlanType(String categoryName, String subCatName, String cityName, PlanType planType);

}
