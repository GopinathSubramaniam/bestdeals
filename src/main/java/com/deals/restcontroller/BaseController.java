package com.deals.restcontroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.deals.enums.PlanType;
import com.deals.model.*;
import com.deals.service.PublicUserPlanService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.deals.repository.CityRepository;
import com.deals.repository.CountryRepository;
import com.deals.repository.PublicUserPlanRepository;
import com.deals.repository.StateRepository;
import com.deals.repository.TalukaRepository;
import com.deals.repository.UserDetailRepository;
import com.deals.repository.UserRepository;
import com.deals.repository.VillageRepository;
import com.deals.util.App;
import com.deals.util.Secret;
import com.deals.util.Status;
import com.deals.vo.BasePlaceModel;
import com.deals.vo.PlaceNameResponseVo;
import com.deals.vo.PublicPlanResponse;
import com.deals.vo.QRCodeResponse;

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
	
//	@Autowired private PublicUserPlanRepository userPlanRepository;
	@Autowired PublicUserPlanService publicUserPlanService;

	@Autowired
	public UserRepository userRepository;
	
	
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
		QRCodeResponse qrRes = new QRCodeResponse();
		Long userId = strUserId != null ? Long.parseLong(strUserId) : new Long(0);
		User user= userRepository.findOne(userId);
		Plan plan = user.getPlan();

		if (plan == null || plan.getPlanType() == PlanType.FREE)
			return App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, "Please buy plan first", qrRes);

		if(type != null && type.toLowerCase().equals("getsecret")){
			PublicUserPlan userPlan = publicUserPlanService.findByUserId(userId);
			if (userPlan == null)
				return App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, "User plan not found", qrRes);
			JSONObject rule = new JSONObject(plan.getRules());
			boolean checkValidity = rule.getBoolean("check_validity");
			if(userPlan.getQrCode()!=null && (checkValidity ? userPlan.getEndDate().getTime() > new Date().getTime() : true )){
				qrRes.setQr(userPlan.getQrCode());
				PublicPlanResponse planRes = new PublicPlanResponse();
				planRes.setId(userPlan.getId());
				planRes.setAmount(userPlan.getAmount());
				planRes.setDescription(userPlan.getDescription());
				planRes.setPercentage(userPlan.getPercentage());
				planRes.setPlanType(userPlan.getPlanType());
				planRes.setStartDate(userPlan.getStartDate());
				planRes.setEndDate(userPlan.getEndDate());
				planRes.setValidityInMonths(userPlan.getValidityInMonths());
				qrRes.setDetails(planRes);
			}else{
				return App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, "QR code not found or expired", qrRes);
			}	
		}else if(type != null && type.toLowerCase().equals("encrypt")){
			String key = req.getParameter("key");
			qrRes.setQr(new Secret().encrypt(key));
		}else if(type != null && type.toLowerCase().equals("decrypt")){
			String key = req.getParameter("key");
			qrRes.setQr(new Secret().decrypt(key));
		}else if(type != null && type.toLowerCase().equals("verify")){
			String key = req.getParameter("key");
			PublicUserPlan userPlan = publicUserPlanService.findByQrCodeEncryptedQrCode(key);
			if (userPlan == null)
				return App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, "User plan not found", qrRes);
			if(userPlan.getQrCode() != null
					&& userPlan.getEndDate().getTime() > new Date().getTime()
					&& key.equals(userPlan.getQrCode().getEncryptedQrCode())){
				qrRes.setIsvalidQR(true);
				qrRes.setQr(userPlan.getQrCode().getNormalQrCode());
				PublicPlanResponse planRes = new PublicPlanResponse();
				planRes.setId(userPlan.getId());
				planRes.setAmount(userPlan.getAmount());
				planRes.setDescription(userPlan.getDescription());
				planRes.setPercentage(userPlan.getPercentage());
				planRes.setPlanType(userPlan.getPlanType());
				planRes.setStartDate(userPlan.getStartDate());
				planRes.setEndDate(userPlan.getEndDate());
				planRes.setValidityInMonths(userPlan.getValidityInMonths());
				qrRes.setDetails(planRes);
			}else{
				qrRes.setIsvalidQR(false);
				qrRes.setDetails(false);
				return App.getResponse(App.CODE_OK, App.STATUS_FAIL, "QR code not matched or expired", qrRes);
			}
		}
		
		return App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, qrRes);
	}
	

}
