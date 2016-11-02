package com.deals.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.enums.PlanType;
import com.deals.model.Deal;
import com.deals.model.SubCategory;
import com.deals.model.UserDetail;
import com.deals.repository.CategoryRepository;
import com.deals.repository.DealRepository;
import com.deals.repository.SubCategoryRepository;
import com.deals.util.App;
import com.deals.util.Status;
import com.deals.vo.DealVO;
import com.deals.vo.ImageVo;
import com.deals.vo.UserVO;

@Service
public class DealService {
	private static Status status =  new Status();
	
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
	
	public Status searchGlobal(String searchKey){
		List<Deal> platinumDeals = new ArrayList<>();
		List<Deal> goldDeals = new ArrayList<>();
		List<Deal> silverDeals = new ArrayList<>();
		
		platinumDeals = dealRepository.findAllBySubCategoryCategoryNameOrSubCategoryNameOrCityNameOrPlaceNameOrDescriptionOrNameOrUserNameOrUserMobileOrUserPlanNameOrUserEmailAndUserPlanPlanType(searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey,PlanType.PLATINUM);
		
		if(platinumDeals.size() == 0 ){
			goldDeals = dealRepository.findAllBySubCategoryCategoryNameOrSubCategoryNameOrCityNameOrPlaceNameOrDescriptionOrNameOrUserNameOrUserMobileOrUserPlanNameOrUserEmailAndUserPlanPlanType(searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, PlanType.GOLD);
			platinumDeals = goldDeals;
		}
		
		if(platinumDeals.size() == 0 && goldDeals.size() == 0){
			silverDeals = dealRepository.findAllBySubCategoryCategoryNameOrSubCategoryNameOrCityNameOrPlaceNameOrDescriptionOrNameOrUserNameOrUserMobileOrUserPlanNameOrUserEmailAndUserPlanPlanType(searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, searchKey, PlanType.SILVER);
			platinumDeals = silverDeals;
		}
		List<DealVO> dealVOs = mergeDealVOs(platinumDeals);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, dealVOs);
		return status;
	}

	public List<DealVO> searchOr(String categoryName, String subCatName, String cityName, String placeName){
		List<Deal> deals = null;
		if (!categoryName.isEmpty()) {
//			List<Long> categoryIds= categoryRepository.findIdByNameLike(categoryName);
			List<Long> subCategoryIds = subCategoryRepository.findIdByCategoryNameLike(categoryName);
			deals = dealRepository.findBySubCategoryIdIn(subCategoryIds);
		} else if (!subCatName.isEmpty()) {
			List<Long> subCategoryIds = subCategoryRepository.findIdByNameLike(subCatName);
//			deals = dealRepository.findBySubCategoryIdIn(subCategoryIds);
			deals = dealRepository.findDefaultDealsBySubCategoryIdInAndOnePerUser(subCategoryIds);
		} else if (!cityName.isEmpty()) {
			deals = dealRepository.findByCityName(cityName);
		} else if (!placeName.isEmpty()) {
			deals = dealRepository.findByPlaceName(placeName);
		}
		List<DealVO> dealVOs = null;
		if (deals != null && deals.size() > 0) {
			dealVOs = new ArrayList<>();
			for (Deal deal : deals) {
				deal.getUser();
				List<UserDetail> userDetails = userDetailService.findByUser(deal.getUser());
				UserDetail userDetail = userDetails.size() > 0 ? userDetails.get(0) : null;
				dealVOs.add(new DealVO(deal, new UserVO(deal.getUser(), userDetail, null)));
			}
		}
		return dealVOs;
	}
	public Status findAll(String categoryName, String subCatName, String cityName, String placeName){

		List<Deal> platinumDeals = new ArrayList<>();
		List<Deal> goldDeals = new ArrayList<>();
		List<Deal> silverDeals = new ArrayList<>();
		
		platinumDeals = dealRepository.findAllBySubCategoryCategoryNameAndSubCategoryNameOrCityNameOrPlaceNameAndUserPlanPlanType(categoryName, subCatName, cityName, placeName, PlanType.PLATINUM);
		
		if(platinumDeals.size() == 0 ){
			goldDeals = dealRepository.findAllBySubCategoryCategoryNameAndSubCategoryNameOrCityNameOrPlaceNameAndUserPlanPlanType(categoryName, subCatName, cityName, placeName, PlanType.GOLD);
			platinumDeals = goldDeals;
		}
		
		if(platinumDeals.size() == 0 && goldDeals.size() == 0){
			silverDeals = dealRepository.findAllBySubCategoryCategoryNameAndSubCategoryNameOrCityNameOrPlaceNameAndUserPlanPlanType(categoryName, subCatName, cityName, placeName, PlanType.SILVER);
			platinumDeals = silverDeals;
		}
		List<DealVO> dealVOs = mergeDealVOs(platinumDeals);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, dealVOs);
		return status;
	
	}
	
	public Status findAll(){
		List<Deal> platinumDeals = new ArrayList<>();
		List<Deal> goldDeals = new ArrayList<>();
		List<Deal> silverDeals = new ArrayList<>();
		
		platinumDeals = dealRepository.findAllByUserPlanPlanTypeAndIsDefault(PlanType.PLATINUM, true);
		
		if(platinumDeals.size() == 0 ){
			goldDeals = dealRepository.findAllByUserPlanPlanTypeAndIsDefault(PlanType.GOLD, true);
			platinumDeals = goldDeals;
		}
		
		if(platinumDeals.size() == 0 && goldDeals.size() == 0){
			silverDeals = dealRepository.findAllByUserPlanPlanTypeAndIsDefault(PlanType.SILVER, true);
			platinumDeals = silverDeals;
		}
		List<DealVO> dealVOs = mergeDealVOs(platinumDeals);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, dealVOs);
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
			UserVO user = App.setUserVo(deal.getUser(), userDetail , null);
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
