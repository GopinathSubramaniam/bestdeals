package com.deals.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.model.AdminDetail;
import com.deals.repository.PlanRepository;
import com.deals.repository.SalesManagerRepository;
import com.deals.repository.SalesmanRepository;
import com.deals.repository.UserRepository;

@Service
public class AppService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SalesmanRepository salesmanRepository;
	
	@Autowired
	private SalesManagerRepository salesManagerRepository;
	
	@Autowired
	private PlanRepository planRepository;
	
	
	public List<AdminDetail> getAdminDetail(){
		List<AdminDetail> adminDetails = new ArrayList<>();
		AdminDetail adminDetail = new AdminDetail();
		adminDetail.setName("Users");
		adminDetail.setCount(userRepository.count());
		adminDetail.setIconName("fa fa-users");
		adminDetail.setColorName("bg-aqua");
		adminDetail.setLandingPath("user");
		adminDetails.add(adminDetail);
		
		adminDetail = new AdminDetail();
		adminDetail.setName("Sales Mans");
		adminDetail.setCount(salesmanRepository.count());
		adminDetail.setIconName("fa fa-users");
		adminDetail.setColorName("bg-green");
		adminDetail.setLandingPath("salesman");
		adminDetails.add(adminDetail);
		
		
		adminDetail = new AdminDetail();
		adminDetail.setName("Sales Managers");
		adminDetail.setCount(salesManagerRepository.count());
		adminDetail.setIconName("fa fa-users");
		adminDetail.setColorName("bg-yellow");
		adminDetail.setLandingPath("salesManager");
		adminDetails.add(adminDetail);
		
		adminDetail = new AdminDetail();
		adminDetail.setName("Plans");
		adminDetail.setCount(planRepository.count());
		adminDetail.setIconName("fa fa-archive");
		adminDetail.setColorName("bg-red");
		adminDetail.setLandingPath("plan");
		adminDetails.add(adminDetail);
		
		return adminDetails;
		
	}
	
}
