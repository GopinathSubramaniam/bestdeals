package com.deals.repository;

import com.deals.enums.UserType;
import com.deals.model.User;
import com.deals.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;

@Transactional
public interface UserDetailRepository extends JpaRepository<UserDetail, Long>{

	List<UserDetail> findAllByUserId(Long id);
	List<UserDetail> findByUserUserType(UserType userType);
	List<UserDetail> findByUserCreatedBy(String createdBy);
	UserDetail findByUserId(Long id);
	
	UserDetail findByVillageNameLike(String cityName);
	
	@Query("select shopName from UserDetail where village.taluka.city.id = :id")
	List<String> findAllShopNameByVillageTalukaCityId(@Param("id") Long id);

	@Query(nativeQuery = true,
			value = "SELECT z.user_id FROM user_detail AS z" +
					"  JOIN ( SELECT  :latpoint  AS latpoint,  :longpoint AS longpoint," +
					"                :radius AS radius,      111.045 AS distance_unit" +
					"    ) AS p ON 1=1" +
					"  WHERE z.latitude" +
					"     BETWEEN p.latpoint  - (p.radius / p.distance_unit)" +
					"         AND p.latpoint  + (p.radius / p.distance_unit)" +
					"    AND z.longitude" +
					"     BETWEEN p.longpoint - (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))" +
					"         AND p.longpoint + (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))"
					 + "  GROUP BY z.user_id"
					// + "  ORDER BY distance_in_km" + "LIMIT 15"
					)
	List<BigInteger> findNearByUserIdsByLatLongInRadius(@Param("latpoint") double latpoint,
														@Param("longpoint") double longpoint,
														@Param("radius") double radius
//												  ,Pageable pageable
	);
	/*
	@Query(nativeQuery = true,
			value = "SELECT z.* FROM user_detail AS z\n" +
					"  JOIN (   *//* these are the query parameters *//*\n" +
					"        SELECT  :latpoint  AS latpoint,  :longpoint AS longpoint,\n" +
					"                :radius AS radius,      111.045 AS distance_unit\n" +
					"    ) AS p ON 1=1\n" +
					"  WHERE z.latitude\n" +
					"     BETWEEN p.latpoint  - (p.radius / p.distance_unit)\n" +
					"         AND p.latpoint  + (p.radius / p.distance_unit)\n" +
					"    AND z.longitude\n" +
					"     BETWEEN p.longpoint - (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))\n" +
					"         AND p.longpoint + (p.radius / (p.distance_unit * COS(RADIANS(p.latpoint))))\n" +
					"  ORDER BY distance_in_km\n" +
					"LIMIT 15")*/
	@Query("from UserDetail ")
	List<UserDetail> findNearByUserDetailsByLatLongInRange(@Param("latpoint") double latpoint,
												 @Param("longpoint") double longpoint, @Param("radius") double radius);

	public void deleteByUser(User user);
	
}
