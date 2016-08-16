package com.deals.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.model.Category;
import com.deals.repository.CategoryRepository;
import com.deals.util.App;
import com.deals.util.Status;

@Service
public class CategoryService {
	
	private static Status status = new Status();
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Status create(Category category){
		if(category !=null){
			category = categoryRepository.saveAndFlush(category);
			status = App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, category);
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, null);
		}
		return status;
	}
	
	public Status findAll(){
		List<Category> categories = categoryRepository.findAll();
		return status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, categories);
	}
	
	public Status findAllByLoginId(Long loginId){
		List<Category> categories = categoryRepository.findAllByUserId(loginId);
		return status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, categories);
	}
	
}
