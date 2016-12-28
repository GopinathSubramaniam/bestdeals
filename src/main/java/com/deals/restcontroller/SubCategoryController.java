package com.deals.restcontroller;

import com.deals.model.SubCategory;
import com.deals.service.SubCategoryService;
import com.deals.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/subcat")
public class SubCategoryController {

	@Autowired
	private SubCategoryService subCategoryService;
	
	@RequestMapping(value = "/", method=RequestMethod.POST, produces={"application/json"})	
	public Status create(@RequestBody SubCategory subCategory){
		return subCategoryService.create(subCategory);
	}
	
	@RequestMapping(value = "/{catId}", method=RequestMethod.GET)	
	public Status findByCategoryId(@PathVariable Long catId){
		return subCategoryService.findByCategoryId(catId);
	}
	
	@RequestMapping(value = "/findOne/{subcatId}", method=RequestMethod.GET)	
	public Status findOne(@PathVariable Long subcatId){
		return subCategoryService.findOne(subcatId);
	}
	
	@RequestMapping(value = "/{subcatId}", method=RequestMethod.DELETE)	
	public Status delete(@PathVariable Long subcatId){
		return subCategoryService.delete(subcatId);
	}
	
	@RequestMapping(value = "/findAllByCategoryId/{catId}", method=RequestMethod.GET)	
	public Status findAllByCategoryId(@PathVariable Long catId){
		return subCategoryService.findAllByCategoryId(catId);
	}
	
	
	
}
