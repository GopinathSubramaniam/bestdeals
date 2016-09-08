package com.deals.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.model.SubCategory;
import com.deals.repository.SubCategoryRepository;
import com.deals.util.App;
import com.deals.util.Status;

@Service
public class SubCategoryService {
	
	private static Status status = new Status();
	
	@Autowired
	private SubCategoryRepository subCategoryRepository;
	
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
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, subCategories);
		return status;
	}
	
	public Status findAll(){
		return App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, subCategoryRepository.findAll());
	}
	
}
