package com.deals.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deals.model.Category;
import com.deals.service.CategoryService;
import com.deals.util.Status;

@RestController
@RequestMapping("/rest/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value="/", method= RequestMethod.POST, produces={"application/json"})
	public Status create(@RequestBody Category category){
		return categoryService.create(category);
	}
	
	@RequestMapping(value="/", method= RequestMethod.GET)
	public Status findAll(){
		return categoryService.findAll();
	}
	
	@RequestMapping(value="/findAllCategory", method= RequestMethod.GET)
	public Status findAllCategory(){
		return categoryService.findAllCategory();
	}
	
	@RequestMapping(value="/{id}", method= RequestMethod.GET)
	public Status findById(@PathVariable Long id){
		return categoryService.findOne(id);
	}
	
	@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
	public Status delete(@PathVariable Long id){
		return categoryService.delete(id);
	}
	
}
