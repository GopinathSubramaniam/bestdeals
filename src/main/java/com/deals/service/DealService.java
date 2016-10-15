package com.deals.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.enums.PlanType;
import com.deals.model.Deal;
import com.deals.model.UserDetail;
import com.deals.repository.DealRepository;
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
	
	public Status findAllBySubCat(Long subCatId){
		List<Deal> deals = dealRepository.findAllBySubCategoryId(subCatId);
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
