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
	public Status create(@RequestParam("file") MultipartFile file, @RequestParam("deal") String stringDeal){
		JSONObject jsonDeal = new JSONObject(stringDeal);
		System.out.println("File ::: "+file.getOriginalFilename());
		System.out.println("File ::: "+jsonDeal.getString("name"));
		String fileUrl = appService.copyFile(file);
		
		User user = new User();
		user.setId(Long.parseLong(jsonDeal.getString("user")));
		
		City city = new City();
		city.setId(Long.parseLong(jsonDeal.getString("city")));
		
		SubCategory subCat = new SubCategory();
		subCat.setId(Long.parseLong(jsonDeal.getString("subCategory")));
		
		Deal deal = new Deal();
		deal.setName(jsonDeal.getString("name"));
		deal.setDescription(jsonDeal.getString("name"));
		deal.setImgUrl(fileUrl);
		deal.setCity(city);
		deal.setContact(jsonDeal.getString("contact"));
		deal.setPlaceName(jsonDeal.getString("placeName"));
		deal.setType(DealType.ADVERTISTMENT);
		deal.setUser(user);
		
		return dealService.create(deal);
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
	
	
}
