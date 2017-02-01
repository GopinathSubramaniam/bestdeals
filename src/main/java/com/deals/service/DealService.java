package com.deals.service;

import com.deals.enums.PlanType;
import com.deals.model.Deal;
import com.deals.model.SubCategory;
import com.deals.model.User;
import com.deals.model.UserDetail;
import com.deals.repository.CategoryRepository;
import com.deals.repository.DealRepository;
import com.deals.repository.SubCategoryRepository;
import com.deals.util.App;
import com.deals.util.Status;
import com.deals.vo.DealVO;
import com.deals.vo.ImageVo;
import com.deals.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
public class DealService {
	private static Status status =  new Status();

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private DealRepository dealRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private SubCategoryRepository subCategoryRepository;
	@Autowired
	private UserDetailService userDetailService;
	
	public Status create(Deal deal){
		deal = dealRepository.saveAndFlush(deal);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, deal);
		return status;
	}
	
	public Status findOne(Long id){
		Deal deal = dealRepository.findOne(id);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, deal);
		return status;
	}
	
	public Status findAllByUserId(Long userId){
		List<Deal> deals = dealRepository.findAllByUserId(userId);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, deals);
		return status;
	}

	public List<DealVO> filter(
			String categoryName,
			String subcategoryName,
			String cityNme,
			String talukaName,
			String description,
			String dealName,
			String shopname			){
		// Query for a List of objects.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		/*CriteriaQuery<Deal> cq = cb.createQuery(Deal.class);
		Root<Deal> dealRoot = cq.from(Deal.class);
		Root<SubCategory> sc = cq.from(SubCategory.class);
		Root<City> cityRoot = cq.from(City.class);
		Root<Category> categoryRoot = cq.from(Category.class);
		Root<UserDetail> userDetailRoot = cq.from(UserDetail.class);
		Join subCategoryJ = dealRoot.join("subCategory", JoinType.LEFT).on(cb.equal(dealRoot.get("subCategory").get("id"), sc.get("id")));
		Join cityJ = dealRoot.join("city", JoinType.LEFT).on(cb.equal(dealRoot.get("city").get("id"), cityRoot.get("id")));

		Join categoryJ = subCategoryJ.join("category", JoinType.LEFT).on(cb.equal(sc.get("category").get("id"), categoryRoot.get("id")));
		Join userDetailJ = dealRoot.join("userDetail", JoinType.LEFT).on(cb.equal(dealRoot.get("user").get("id"), userDetailRoot.get("user").get("id")));
		List<Predicate> predicates = new ArrayList<>();
		if (subcategoryName != null && !subcategoryName.isEmpty()) {
			predicates.add(cb.equal(subCategoryJ.get("name"), subcategoryName));
		}
		if (categoryName != null && !categoryName.isEmpty()) {
			predicates.add(cb.equal(categoryJ.get("name"), categoryName));
		}
		if (cityNme != null && !cityNme.isEmpty()) {
			predicates.add(cb.equal(cityJ.get("name"), cityNme));
		}
		if (talukaName != null && !talukaName.isEmpty()) {
			predicates.add(cb.equal(dealRoot.get("talukaName"), talukaName));
		}
		if (description != null && !description.isEmpty()) {
			predicates.add(cb.equal(dealRoot.get("description"), description));
		}
		if (dealName != null && !dealName.isEmpty()) {
			predicates.add(cb.equal(dealRoot.get("dealName"), dealName));
		}
		if (shopname != null && !shopname.isEmpty()) {
			predicates.add(cb.equal(userDetailJ.get("shopName"), shopname));
		}

		if (predicates.size() > 0) {
			cq.where((Predicate[]) predicates.toArray());
			Query query = em.createQuery(cq);
			platinumDeals = query.getResultList();
		}*/
		boolean iscategoryName = false;
		boolean issubcategoryName = false;
		boolean iscityNme = false;
		boolean isTalukaName = false;
		boolean isdescription = false;
		boolean isdealName = false;
		boolean isshopname = false;
		String mQuery = "select" +
				"        deal0_.*" +
				"    from" +
				"        deal deal0_ " +
				"    left outer join" +
				"        sub_category subcategor1_ " +
				"            on deal0_.sub_category_id=subcategor1_.id " +
				"    left outer join" +
				"        category category2_ " +
				"            on subcategor1_.category_id=category2_.id " +
				"    left outer join" +
				"        city city3_ " +
				"            on deal0_.city_id=city3_.id " +
				"    left outer join" +
				"        user_detail userdetail4_ " +
				"            on deal0_.user_id=userdetail4_.user_id ";
		StringBuilder queryCondition = new StringBuilder();
		if (subcategoryName != null && !subcategoryName.isEmpty()) {
			issubcategoryName = true;
			if (queryCondition.length() < 1 ) queryCondition.append(" where ");
			queryCondition.append(" subcategor1_.name = :subcategoryName ");
		}
		if (categoryName != null && !categoryName.isEmpty()) {
			iscategoryName = true;
			if (queryCondition.length() < 1 ) queryCondition.append(" where ");
			else
				queryCondition.append(" and ");
			queryCondition.append(" category2_.name = :categoryName ");
		}
		if (cityNme != null && !cityNme.isEmpty()) {
			iscityNme = true;
			if (queryCondition.length() < 1 ) queryCondition.append(" where ");
			else
				queryCondition.append(" and ");
			queryCondition.append(" city3_.name = :cityNme ");
		}
		if (talukaName != null && !talukaName.isEmpty()) {
			isTalukaName = true;
			if (queryCondition.length() < 1 ) queryCondition.append(" where ");
			else
				queryCondition.append(" and ");
			queryCondition.append(" deal0_.user_id in (select ud.user_id from user_detail as ud where " +
					//"ud.city_id = (select id from city where city.name = '') OR " +
					" ud.village_id in " +
					"(select v.id from village v where v.taluka_id in (select t.id from taluka t where t.name = :talukaName))) ");
		}
		if (description != null && !description.isEmpty()) {
			isdescription = true;
			if (queryCondition.length() < 1 ) queryCondition.append(" where ");
			else
				queryCondition.append(" and ");
			queryCondition.append(" deal0_.description = :description ");
		}
		if (dealName != null && !dealName.isEmpty()) {
			isdealName = true;
			if (queryCondition.length() < 1 ) queryCondition.append(" where ");
			else
				queryCondition.append(" and ");
			queryCondition.append(" deal0_.name = :dealName ");
		}
		if (shopname != null && !shopname.isEmpty()) {
			isshopname = true;
			if (queryCondition.length() < 1 ) queryCondition.append(" where ");
			else
				queryCondition.append(" and ");
			queryCondition.append(" userdetail4_.shop_name = :shopname ");
		}
		queryCondition.append("	group by deal0_.user_id " );
		queryCondition.append("	order by deal0_.id DESC" );
		Query nativeQuery = em.createNativeQuery( mQuery + queryCondition.toString(), Deal.class);
		if (iscategoryName)
			nativeQuery.setParameter("categoryName", categoryName);
		if (issubcategoryName)
			nativeQuery.setParameter("subcategoryName", subcategoryName);
		if (iscityNme)
			nativeQuery.setParameter("cityNme", cityNme);
		if (isTalukaName)
			nativeQuery.setParameter("talukaName", talukaName);
		if (isdescription)
			nativeQuery.setParameter("description", description);
		if (isdealName)
			nativeQuery.setParameter("dealName", dealName);
		if (isshopname)
			nativeQuery.setParameter("shopname", shopname);
		List<Deal> platinumDeals = nativeQuery.setMaxResults(50).getResultList();
		return mergeDealVOs(platinumDeals);
	}
	
	public Status searchGlobal(String searchKey, int max){
		List<Deal> platinumDeals = Collections.emptyList();
		if (searchKey != null && !searchKey.isEmpty())
			platinumDeals = dealRepository.findByAny(searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, max);

/*		platinumDeals = dealRepository.findAllBySubCategoryCategoryNameOrSubCategoryNameOrCityNameOrPlaceNameOrDescriptionOrNameOrUserNameOrUserMobileOrUserPlanNameOrUserEmailAndUserPlanPlanType(searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey,PlanType.PLATINUM);
		List<Deal> goldDeals = new ArrayList<>();
		if(platinumDeals.size() == 0 ){
			goldDeals = dealRepository.findAllBySubCategoryCategoryNameOrSubCategoryNameOrCityNameOrPlaceNameOrDescriptionOrNameOrUserNameOrUserMobileOrUserPlanNameOrUserEmailAndUserPlanPlanType(searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, PlanType.GOLD);
			platinumDeals = goldDeals;
		}
		List<Deal> silverDeals = new ArrayList<>();
		if(platinumDeals.size() == 0 && goldDeals.size() == 0){
			silverDeals = dealRepository.findAllBySubCategoryCategoryNameOrSubCategoryNameOrCityNameOrPlaceNameOrDescriptionOrNameOrUserNameOrUserMobileOrUserPlanNameOrUserEmailAndUserPlanPlanType(searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, PlanType.SILVER);
			platinumDeals = silverDeals;
		}*/
		List<DealVO> dealVOs = mergeDealVOs(platinumDeals);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, dealVOs);
		return status;
	}

	public List<DealVO> searchOr(String categoryName, String subCatName, String cityName, String placeName,
								 double latPoint, double lngPoint, int distance, Pageable pageable){
		List<Deal> deals = null;
		if (!subCatName.isEmpty()) {
			List<Long> subCategoryIds = subCategoryRepository.findIdByNameLike(subCatName);
//			deals = dealRepository.findBySubCategoryIdIn(subCategoryIds);
			// using default image
//			deals = dealRepository.findDefaultDealsBySubCategoryIdInAndOnePerUser(subCategoryIds);
			// any random deal image
			if (subCategoryIds != null && subCategoryIds.size() > 0)
				deals = dealRepository.findBySubCategoryIdInAndOnePerUser(subCategoryIds);
		} else if (!categoryName.isEmpty()) {
//			List<Long> categoryIds= categoryRepository.findIdByNameLike(categoryName);
			List<Long> subCategoryIds = subCategoryRepository.findIdByCategoryNameLike(categoryName);
			//deals = dealRepository.findBySubCategoryIdIn(subCategoryIds);
			if (subCategoryIds != null && subCategoryIds.size() > 0)
				deals = dealRepository.findBySubCategoryIdInOnePerUser(subCategoryIds);
		} else if (latPoint > -90 && latPoint < 90 && lngPoint > -180 && lngPoint < 180 && distance > 0 ) {
			List<BigInteger> userIds = userDetailService.findNearByUserIdsByLatLongInRange(latPoint,lngPoint,distance);
			List<Long> users = new ArrayList<>(userIds.size());
			for (Iterator<BigInteger> itr1 = userIds.iterator(); itr1.hasNext();) {
				BigInteger bi = itr1.next();
				if (bi != null) users.add(bi.longValue() );
			}
			if (users.size() > 0 )
				deals = dealRepository.findByUserIdInGroupByUser(users);
		} else if (!cityName.isEmpty()) {
			deals = dealRepository.findByCityName(cityName);
		} else if (!placeName.isEmpty()) {
//			deals = dealRepository.findByPlaceName(placeName);
		}
		List<DealVO> dealVOs = null;
		if (deals != null && deals.size() > 0) {
			dealVOs = new ArrayList<>();
			for (Deal deal : deals) {
				deal.getUser();
				List<UserDetail> userDetails = userDetailService.findByUser(deal.getUser());
				UserDetail userDetail = userDetails.size() > 0 ? userDetails.get(0) : null;
				List<Deal> userDeals = dealRepository.findAllByUserId(deal.getUser().getId());
				dealVOs.add(new DealVO(deal, App.setUserVo(deal.getUser(), userDetail, null, userDeals)));
			}
		} else {
			dealVOs = Collections.emptyList();
		}
		return dealVOs;
	}

	public Status findDealsForDashboard(String categoryName, String subCatName, String cityName, String placeName){
		List<Deal> platinumDeals = new ArrayList<>();
		List<Deal> goldDeals = new ArrayList<>();
		List<Deal> silverDeals = new ArrayList<>();
		
		platinumDeals = dealRepository.findAllBySubCategoryCategoryNameAndSubCategoryNameOrCityNameAndUserPlanPlanType(categoryName, subCatName, cityName, PlanType.PLATINUM);
		
		if(platinumDeals.size() == 0 ){
			goldDeals = dealRepository.findAllBySubCategoryCategoryNameAndSubCategoryNameOrCityNameAndUserPlanPlanType(categoryName, subCatName, cityName, PlanType.GOLD);
			platinumDeals = goldDeals;
		}
		
		if(platinumDeals.size() == 0 && goldDeals.size() == 0){
			silverDeals = dealRepository.findAllBySubCategoryCategoryNameAndSubCategoryNameOrCityNameAndUserPlanPlanType(categoryName, subCatName, cityName, PlanType.SILVER);
			platinumDeals = silverDeals;
		}
		List<DealVO> dealVOs = mergeDealVOs(platinumDeals);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, dealVOs);
		return status;
	
	}
	
	public Status findDealsForDashboard(int page, int max){
		List<Deal> platinumDeals = new ArrayList<>();
		List<Deal> goldDeals = new ArrayList<>();
		List<Deal> silverDeals = new ArrayList<>();
		
		platinumDeals = dealRepository.findAllByUserPlanPlanTypeAndIsDefaultOrderByChangedDateDesc(PlanType.PLATINUM, true, new PageRequest(page, max));
		
		if(platinumDeals.size() < max ){
			goldDeals = dealRepository.findAllByUserPlanPlanTypeAndIsDefaultOrderByChangedDateDesc(PlanType.GOLD, true, new PageRequest(page, max - platinumDeals.size()));
			if ( platinumDeals.size() == 0 )
				platinumDeals = goldDeals;
			else if ( goldDeals.size() > 0 )
				platinumDeals.addAll(goldDeals);
		}
		
		if ( platinumDeals.size() < max ){
			silverDeals = dealRepository.findAllByUserPlanPlanTypeAndIsDefaultOrderByChangedDateDesc(PlanType.SILVER, true, new PageRequest(page, max - platinumDeals.size() ));
			if ( platinumDeals.size() == 0 )
				platinumDeals = silverDeals;
			else if ( silverDeals.size() > 0 )
				platinumDeals.addAll(silverDeals);
		}
		List<DealVO> dealVOs = mergeDealVOs(platinumDeals);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, dealVOs);
		return status;
	}

	public Status findNearByDealsForDashboard(int page, int max, double latPoint, double lngPoint, double radius){
		List<Deal> platinumDeals = Collections.emptyList();

		List<BigInteger> userIds = userDetailService.findNearByUserIdsByLatLongInRange(latPoint,lngPoint, radius);
		if (userIds == null || userIds.size() == 0)
			return App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, userIds);

		List<User> userList = new ArrayList<>(userIds.size());
		for (int i = 0; i < userIds.size(); i++) {
			userList.add(new User(userIds.get(i).longValue()));
		}
		platinumDeals = dealRepository.findByUserInAndUserPlanPlanTypeAndIsDefaultOrderByChangedDateDesc( userList, PlanType.PLATINUM, true, new PageRequest(page, max));

		if(platinumDeals.size() < max ){
			List<Deal> goldDeals = dealRepository.findByUserInAndUserPlanPlanTypeAndIsDefaultOrderByChangedDateDesc( userList, PlanType.GOLD, true, new PageRequest(page, max - platinumDeals.size()));
			if ( platinumDeals.size() == 0 )
				platinumDeals = goldDeals;
			else if ( goldDeals.size() > 0 )
				platinumDeals.addAll(goldDeals);
		}

		if ( platinumDeals.size() < max ){
			List<Deal> silverDeals = dealRepository.findByUserInAndUserPlanPlanTypeAndIsDefaultOrderByChangedDateDesc( userList, PlanType.SILVER, true, new PageRequest(page, max - platinumDeals.size() ));
			if ( platinumDeals.size() == 0 )
				platinumDeals = silverDeals;
			else if ( silverDeals.size() > 0 )
				platinumDeals.addAll(silverDeals);
		}
		if (platinumDeals != null && platinumDeals.size() > 0) {
			List<DealVO> dealVOs = mergeDealVOs(platinumDeals);
			status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, dealVOs);
		} else {
			status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, null);
		}
		return status;
	}

	public Status findAllBySubCat(Long subCatId, boolean isDefault){
		List<Deal> deals = dealRepository.findAllBySubCategoryIdAndIsDefault(subCatId, isDefault);
		if (deals == null || deals.size() < 1) {
			SubCategory sc = new SubCategory();
			sc.setId(subCatId);
			deals = dealRepository.findAllBySubCategoryIdGroupByUser(sc);
		}
		List<DealVO> dealVOs = mergeDealVOs(deals);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, dealVOs);
		return status;
	}
	
	private List<DealVO> mergeDealVOs(List<Deal> deals){
		List<DealVO> dealVOs = new ArrayList<>();
		for (Deal deal : deals) {
			DealVO dealVO = App.setDealVO(deal);
			List<UserDetail> userDetails = (List<UserDetail>) userDetailService.findAllByUserId(deal.getUser().getId()).getData();
			UserDetail userDetail = userDetails.size() > 0 ? userDetails.get(0) : null;
			UserVO user = App.setUserVo(deal.getUser(), userDetail , null, null);
			dealVO.setUser(user);
			
			List<Deal> imgUrls = dealRepository.findImageUrlAndDescriptionByUserId(user.getId());
			for (Deal dealObj : imgUrls) {
				ImageVo imageVo = new ImageVo();
				imageVo.setImgUrl(dealObj.getImgUrl());
				imageVo.setDescription(dealObj.getDescription());
				user.getImageUrls().add(imageVo);
			}
			dealVOs.add(dealVO);
		}
		return dealVOs;
	} 
	
	public Status delete(Long id){
		dealRepository.delete(id);
		return App.getResponse(App.CODE_OK, App.STATUS_DELETE, App.STATUS_DELETE, null);
	}
	
	public Status selectDefault(Long id, boolean isDefault, Long userId){
		Deal deal = dealRepository.findByUserIdAndIsDefault(userId, true);
		if(deal != null){
			deal.setDefault(false);
			dealRepository.saveAndFlush(deal);
		}
		
		deal = dealRepository.findOne(id);
		if(deal != null){
			deal.setDefault(isDefault);
			dealRepository.saveAndFlush(deal);
			return App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_UPDATE, deal);
		}else{
			return App.getResponse(App.CODE_OK, App.STATUS_FAIL, App.STATUS_FAIL, deal);
		}
	}
	
}
