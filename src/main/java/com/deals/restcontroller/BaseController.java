package com.deals.restcontroller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deals.model.City;
import com.deals.model.PublicUserPlan;
import com.deals.model.State;
import com.deals.model.Taluka;
import com.deals.model.Village;
import com.deals.repository.CityRepository;
import com.deals.repository.CountryRepository;
import com.deals.repository.PublicUserPlanRepository;
import com.deals.repository.StateRepository;
import com.deals.repository.TalukaRepository;
import com.deals.repository.UserDetailRepository;
import com.deals.repository.VillageRepository;
import com.deals.util.App;
import com.deals.util.Secret;
import com.deals.util.Status;
import com.deals.vo.BasePlaceModel;
import com.deals.vo.PlaceNameResponseVo;

@RestController
@RequestMapping(value="/rest/base/")
public class BaseController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static Status status =  new Status();
	List<BasePlaceModel> basePlaces = null;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private UserDetailRepository userDetailRepository;
	
	@Autowired
	private TalukaRepository talukaRepository;
	
	@Autowired
	private VillageRepository villageRepository;
	
	@Autowired
	private PublicUserPlanRepository userPlanRepository;
	
	
	@RequestMapping(value="/findAllCountry", method = RequestMethod.GET)
	public Status findAllCountry(){
		return App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, countryRepository.findAll());
	}
	
	@RequestMapping(value="/findAllStateByCountry/{countryId}", method = RequestMethod.GET)
	public Status findAllStateByCountry(@PathVariable Long countryId){
		List<State> states = stateRepository.findIdAndNameByCountryId(countryId);
		basePlaces = new ArrayList<>();
		for (State state : states) {
			BasePlaceModel basePlaceModel = new BasePlaceModel();
			basePlaceModel.setId(state.getId());
			basePlaceModel.setName(state.getName());
			basePlaces.add(basePlaceModel);
		}
		return App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, basePlaces);
	}
	
	@RequestMapping(value="/findAllCitiesByState/{stateId}", method = RequestMethod.GET)
	public Status findAllCitiesByState(@PathVariable Long stateId){
		List<City> cities = cityRepository.findAllByStateId(stateId);
		basePlaces = new ArrayList<>();
		for (City city : cities) {
			BasePlaceModel basePlaceModel = new BasePlaceModel();
			basePlaceModel.setId(city.getId());
			basePlaceModel.setName(city.getName());
			basePlaces.add(basePlaceModel);
		}
		return App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, basePlaces);
	}
	
	@RequestMapping(value="/findAllTalukasByCityId/{cityId}", method = RequestMethod.GET)
	public Status findAllTalukasByCityId(@PathVariable Long cityId){
		List<Taluka> talukas = talukaRepository.findAllByCityId(cityId);
		basePlaces = new ArrayList<>();
		for (Taluka taluka : talukas) {
			BasePlaceModel basePlaceModel = new BasePlaceModel();
			basePlaceModel.setId(taluka.getId());
			basePlaceModel.setName(taluka.getName());
			basePlaces.add(basePlaceModel);	
		}
		return status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, basePlaces);
	}
	
	@RequestMapping(value="/findAllVillagesByTalukaId/{talukaId}", method = RequestMethod.GET)
	public Status findAllVillagesByTalukaId(@PathVariable Long talukaId){
		List<Village> villages = villageRepository.findAllByTalukaId(talukaId);
		basePlaces = new ArrayList<>();
		for (Village village : villages) {
			BasePlaceModel basePlaceModel = new BasePlaceModel();
			basePlaceModel.setId(village.getId());
			basePlaceModel.setName(village.getName());
			basePlaces.add(basePlaceModel);
		}
		return status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, basePlaces);
	}
	
	@RequestMapping(value="/findAllPlaceNames/{cityId}", method = RequestMethod.GET)
	public Status findAllPlaceNames(@PathVariable Long cityId){
		List<String> placeNames = userDetailRepository.findAllPlaceNameByVillageTalukaCityId(cityId);
		PlaceNameResponseVo nameResponseVo = new PlaceNameResponseVo();
		nameResponseVo.setCityId(cityId);
		nameResponseVo.setPlaceNames(placeNames);
		log.info("PlaceNameResponseVos ::: "+nameResponseVo);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, nameResponseVo);
		return status;
	}
	
	@RequestMapping(value="/secret", method = RequestMethod.GET)
	public Status secret(HttpServletRequest req){
		String type = req.getParameter("type");
		String strUserId = req.getParameter("userId");
		Long userId = strUserId != null ? Long.parseLong(strUserId) : new Long(0);
		Object response = null;
		
		if(type != null && type.toLowerCase().equals("getsecret")){
			PublicUserPlan userPlan = userPlanRepository.findByUserId(userId);
			if(userPlan!=null && userPlan.getQrCode()!=null){
				response = userPlan.getQrCode();
			}else{
				response = "Error in getting QR code. Try again later";
			}	
		}else if(type != null && type.toLowerCase().equals("encrypt")){
			String key = req.getParameter("key");
			response = new Secret().encrypt(key);
		}else if(type != null && type.toLowerCase().equals("decrypt")){
			String key = req.getParameter("key");
			response = new Secret().decrypt(key);
		}
		
		return App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, response);
	}
	

}
