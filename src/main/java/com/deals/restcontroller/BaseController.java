package com.deals.restcontroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deals.model.PublicUserPlan;
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
import com.deals.vo.PlaceNameResponseVo;

@RestController
@RequestMapping(value="/rest/base/")
public class BaseController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static Status status =  new Status();
	
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
		return App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, stateRepository.findAllByCountryId(countryId));
	}
	
	@RequestMapping(value="/findAllCitiesByState/{stateId}", method = RequestMethod.GET)
	public Status findAllCitiesByState(@PathVariable Long stateId){
		return App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, cityRepository.findAllByStateId(stateId));
	}
	
	@RequestMapping(value="/findAllTalukasByCityId/{cityId}", method = RequestMethod.GET)
	public Status findAllTalukasByCityId(@PathVariable Long cityId){
		return status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, talukaRepository.findAllByCityId(cityId));
	}
	
	@RequestMapping(value="/findAllVillagesByTalukaId/{talukaId}", method = RequestMethod.GET)
	public Status findAllVillagesByTalukaId(@PathVariable Long talukaId){
		return status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, villageRepository.findAllByTalukaId(talukaId));
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
