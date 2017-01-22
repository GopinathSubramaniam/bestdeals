package com.deals.repository;

import com.deals.enums.UserType;
import com.deals.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByEmailAndPassword(String email, String password);
	User findByMobileAndPassword(String mobile, String password);
	User findByToken(String token);
	User findByEmail(String email);
	User findByMobile(String mobile);
	User findByMobileOrEmail(String mobile, String email);
	List<User> findAllByUserType(UserType userType, Pageable pageable);
	List<User> findByUserType(UserType userType);
	List<User> findByCreatedBy(String createdBy);

	@Query( "from User u where u.createdBy = :createdBy AND u.createdDate between :fromDate AND :toDate")
	List<User> findByCreatedByBetweenDates(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("createdBy") String createdBy);

	User findByPlanId(Long id);
}
