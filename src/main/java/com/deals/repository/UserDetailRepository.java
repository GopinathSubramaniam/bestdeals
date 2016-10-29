package com.deals.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.deals.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deals.model.UserDetail;

@Transactional
public interface UserDetailRepository extends JpaRepository<UserDetail, Long>{

	List<UserDetail> findAllByUserId(Long id);
	List<UserDetail> findAllByUser(User user);
	UserDetail findByUserId(Long id); 
	
	UserDetail findByPlaceNameLikeAndVillageNameLike(String placeName, String cityName);
	
	@Query("select placeName from UserDetail where village.taluka.city.id=?")
	List<String> findAllPlaceNameByVillageTalukaCityId(Long id);
	
	public void deleteByUser(User user);
	
}
