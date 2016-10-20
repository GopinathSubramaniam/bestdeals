package com.deals.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.model.Category;
import com.deals.model.SubCategory;
import com.deals.repository.CategoryRepository;
import com.deals.repository.DealRepository;
import com.deals.repository.SubCategoryRepository;
import com.deals.util.App;
import com.deals.util.Status;
import com.deals.vo.CategoryVo;

@Service
public class CategoryService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static Status status = new Status();
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private SubCategoryRepository subCategoryRepository;
	
	@Autowired
	private DealRepository dealRepository;
	
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
		List<CategoryVo> filteredCategories = new ArrayList<CategoryVo>();
		
		for (Category category : categories) {
			List<SubCategory> subCategories = subCategoryRepository.findAllByCategoryId(category.getId());
			for (SubCategory subCategory : subCategories) {
				Long dealCount = dealRepository.countBySubCategoryId(subCategory.getId());
				log.info("Deal Count :::: "+dealCount);
				if(dealCount > 0){
					CategoryVo categoryVo = new CategoryVo();
					categoryVo.setId(category.getId());
					categoryVo.setName(category.getName());
					categoryVo.setDescription(category.getDescription());
					filteredCategories.add(categoryVo);
				}
			}
		}
		
		return status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, filteredCategories);
	}
	
	public Status findAllCategory(){
		List<Category> categories = categoryRepository.findAll();
		List<CategoryVo> filteredCategories = new ArrayList<CategoryVo>();
		for (Category category : categories) {
			CategoryVo categoryVo = new CategoryVo();
			categoryVo.setId(category.getId());
			categoryVo.setName(category.getName());
			categoryVo.setDescription(category.getDescription());
			filteredCategories.add(categoryVo);
		}
		return status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, filteredCategories);
	}
	
	public Status delete(Long id){
		categoryRepository.delete(id);
		return status = App.getResponse(App.CODE_OK, App.STATUS_DELETE, App.MSG_DELETE, null);
	}
	
	public Status findOne(Long id){
		return status = App.getResponse(App.CODE_OK, App.MSG_OK, App.MSG_OK, categoryRepository.findOne(id));
	}
	
}
