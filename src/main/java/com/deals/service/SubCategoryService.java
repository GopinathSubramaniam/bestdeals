package com.deals.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.model.SubCategory;
import com.deals.repository.DealRepository;
import com.deals.repository.SubCategoryRepository;
import com.deals.util.App;
import com.deals.util.Status;
import com.deals.vo.SubCategoryVo;

@Service
public class SubCategoryService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static Status status = new Status();
	
	@Autowired
	private SubCategoryRepository subCategoryRepository;
	
	@Autowired
	private DealRepository dealRepository;
	
	public Status create(SubCategory subCategory){
		if(subCategory != null){
			subCategory = subCategoryRepository.saveAndFlush(subCategory);
			status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, subCategory);
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, subCategory);
		}
		return status;
	}
	
	public Status findByCategoryId(Long catId){
		List<SubCategory> subCategories = subCategoryRepository.findAllByCategoryId(catId);
		List<SubCategoryVo> subCategorieVos = new ArrayList<SubCategoryVo>();
		log.info("SubCategories ::::: "+subCategories);
		for (SubCategory subCategory : subCategories) {
			if(dealRepository.findAllBySubCategoryId(subCategory.getId()).size() > 0){
				SubCategoryVo subCategoryVo = new SubCategoryVo();
				subCategoryVo.setId(subCategory.getId());
				subCategoryVo.setName(subCategory.getName());
				subCategoryVo.setDescription(subCategory.getDescription());
				subCategorieVos.add(subCategoryVo);	
			}
		}
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, subCategorieVos);
		return status;
	}
	
	public Status findOne(Long subCatId){
		SubCategory subCategory = subCategoryRepository.findOne(subCatId);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, subCategory);
		return status;
	}
	
	public Status findAll(){
		return App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, subCategoryRepository.findAll());
	}
	
	public Status delete(Long subcatId){
		subCategoryRepository.delete(subcatId);
		return App.getResponse(App.CODE_OK, App.STATUS_DELETE, App.MSG_DELETE, null);
	}
	
}
