package com.deals.restcontroller;

import javax.servlet.http.HttpServletRequest;

import com.deals.util.App;
import com.deals.vo.DealVO;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import java.util.List;

@RestController
@RequestMapping("/rest/deal")
public class DealController {

	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DealService dealService;
	
	@Autowired
	private AppService appService;

	@RequestMapping(value="/", method= RequestMethod.GET)
	public Status findAll(){
		return dealService.findDealsForDashboard(0,20);
	}

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
	
	@RequestMapping(value="/searchGlobal", method= RequestMethod.GET)
	public Status searchglobal(HttpServletRequest req){
		String type = req.getParameter("type");
		if(type != null && type.toLowerCase().equals("global")){
			log.info("Global Search");
			String searchKey = req.getParameter("key");
			return dealService.searchGlobal(searchKey);
		}else{
			log.info("Advanced Search");
			String categoryName = req.getParameter("cname");
			String subCatName = req.getParameter("scname");
			String cityName = req.getParameter("cityName");
			String placeName = req.getParameter("placeName");
			return dealService.findDealsForDashboard(categoryName, subCatName, cityName, placeName);
		}
	}

	@RequestMapping(value="/search", method= RequestMethod.GET)
	public Status searchDeal(HttpServletRequest req){
		String type = req.getParameter("type");
		if(type != null && type.toLowerCase().equals("global")){
			log.info("Global Search");
			String searchKey = req.getParameter("key");
			return dealService.searchGlobal(searchKey);
		}else{
			double latPoint = 1000.00d;
			double lngPoint = 1000.00d;
			int radius = 5;
			log.info("Advanced Search");
			String categoryName = req.getParameter("cname");
			if (categoryName == null) categoryName = "";
			String subCatName = req.getParameter("scname");
			if (subCatName == null) subCatName = "";
			String cityName = req.getParameter("cityName");
			if (cityName == null) cityName = "";
			String placeName = req.getParameter("placeName");
			if (placeName == null) placeName = "";

			try {
				String latitude = req.getParameter("latPoint");
				String lngitude = req.getParameter("lngPoint");
				String distance = req.getParameter("distance");
				if (latitude!= null && !latitude.isEmpty()) latPoint = Double.parseDouble(latitude);
				if (lngitude!= null && !lngitude.isEmpty()) lngPoint = Double.parseDouble(lngitude);
				if (distance!= null && !distance.isEmpty()) radius = Integer.parseInt(distance);
			} catch (Exception e){}

			Pageable pageable = new PageRequest(0, 5);
			List<DealVO> dealVOs = dealService.searchOr(categoryName, subCatName, cityName, placeName,
				latPoint, lngPoint, radius, pageable);
			return App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, dealVOs);
		}
	}

	@RequestMapping(value="/findAllBySubCat/{subCatId}", method= RequestMethod.GET)
	public Status findAllBySubCatAndUniqueMerchant(@PathVariable Long subCatId){
		return dealService.findAllBySubCat(subCatId, true);
	} 
	
	@RequestMapping(value="/{id}", method= RequestMethod.DELETE)
	public Status delete(@PathVariable Long id){
		return dealService.delete(id);
	} 
	

	@RequestMapping(value="/selectDefault/{id}/{isDefault}/{userId}", method= RequestMethod.GET)
	public Status selectDefault(@PathVariable Long id, @PathVariable boolean isDefault, @PathVariable Long userId){
		return dealService.selectDefault(id, isDefault, userId);
	} 
	
}
