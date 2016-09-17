package com.deals.restcontroller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.deals.enums.DealType;
import com.deals.enums.Priority;
import com.deals.model.City;
import com.deals.model.Deal;
import com.deals.model.SubCategory;
import com.deals.model.User;
import com.deals.service.AppService;
import com.deals.service.DealService;
import com.deals.util.Status;

@RestController
@RequestMapping("/rest/deal")
public class DealController {

	@Autowired
	private DealService dealService;
	
	@Autowired
	private AppService appService;
	
	@RequestMapping(value="/", method= RequestMethod.POST, consumes = "multipart/form-data", produces = { "application/json", "application/xml" })
	public Status create(@RequestParam("file") MultipartFile file, @RequestParam("deal") String stringDeal) throws Exception{
		JSONObject jsonDeal = new JSONObject(stringDeal);
		String fileUrl = "";
		if(!(file.getOriginalFilename().contains("null.txt"))){
			fileUrl = appService.copyFile(file);
		}
		
		User user = new User();
		user.setId(Long.parseLong(jsonDeal.getString("user")));
		
		City city = new City();
		city.setId(Long.parseLong(jsonDeal.getString("city")));
		
		SubCategory subCat = new SubCategory();
		subCat.setId(Long.parseLong(jsonDeal.getString("subCategory")));
		
		String dealId = jsonDeal.getString("id");
		
		Deal deal = new Deal();
		if(dealId != null){
			deal = (Deal)dealService.findOne(Long.parseLong(dealId)).getData();
		}
		deal.setName(jsonDeal.getString("name"));
		deal.setDescription(jsonDeal.getString("name"));
		deal.setCity(city);
		deal.setContact(jsonDeal.getString("contact"));
		deal.setPlaceName(jsonDeal.getString("placeName"));
		deal.setType(DealType.ADVERTISEMENT);
		deal.setPriority(Priority.HIGH);
		deal.setUser(user);
		deal.setSubCategory(subCat);
		if(fileUrl != "") deal.setImgUrl(fileUrl);
		
		return dealService.create(deal);
	}
	
	@RequestMapping(value="/findOne/{id}", method= RequestMethod.GET)
	public Status findOne(@PathVariable Long id){
		return dealService.findOne(id);
	} 
	
	@RequestMapping(value="/{userId}", method= RequestMethod.GET)
	public Status findAllByUserId(@PathVariable Long userId){
		return dealService.findAllByUserId(userId);
	} 
	
	@RequestMapping(value="/", method= RequestMethod.GET)
	public Status findAll(){
		return dealService.findAll();
	} 
	
	@RequestMapping(value="/findAllBySubCat/{subCatId}", method= RequestMethod.GET)
	public Status findAllBySubCat(@PathVariable Long subCatId){
		return dealService.findAllBySubCat(subCatId);
	} 
	
	@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
	public Status delete(@PathVariable Long id){
		return dealService.delete(id);
	} 
	
	
}
