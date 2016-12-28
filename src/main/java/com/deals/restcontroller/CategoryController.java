package com.deals.restcontroller;

import com.deals.model.Category;
import com.deals.service.AppService;
import com.deals.service.CategoryService;
import com.deals.util.Status;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/rest/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private AppService appService;

	@RequestMapping(value="/", method= RequestMethod.POST, produces={"application/json"})
	public Status create(@RequestParam("category") String categoryJson,
						 @RequestParam("imageFile") MultipartFile categoryImagePart){
		String fileUrl = "";
		JSONObject jsonDeal = new JSONObject(categoryJson);
		if(!(categoryImagePart.getOriginalFilename().contains("null.txt"))){
			fileUrl = appService.copyFile(categoryImagePart);
		} else
			fileUrl = jsonDeal.getString("imgUrl");
		Category category = new Category();
		if (jsonDeal.getString("id").isEmpty())
			category.setId(0l);
		else
			category.setId(Long.parseLong(jsonDeal.getString("id")));
		category.setName(jsonDeal.getString("name"));
		category.setDescription(jsonDeal.getString("description"));
		category.setImgUrl(fileUrl);
		return categoryService.create(category);
	}
	
	@RequestMapping(value="/", method= RequestMethod.GET)
	public Status findAll(){
		return categoryService.findAllHavingSubCategoryAndDeals();
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
