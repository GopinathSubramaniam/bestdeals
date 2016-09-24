package com.deals.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.model.SalesManager;
import com.deals.model.Salesman;
import com.deals.repository.SalesManagerRepository;
import com.deals.repository.SalesmanRepository;
import com.deals.util.App;
import com.deals.util.Status;


@Service
public class SalesmanService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private Status status = new Status();

	@Autowired
	private SalesmanRepository salesmanRepository;
	
	@Autowired
	private SalesManagerRepository salesManagerRepository;

	public Status create(Salesman salesman) {
		if (salesman != null) {
			Salesman existingSalesManObj = null;
			if(salesman.getId() != null && salesman.getId() > 0){
				existingSalesManObj = salesmanRepository.findOne(salesman.getId());
				existingSalesManObj.setEmail(salesman.getEmail());
				existingSalesManObj.setMobile(salesman.getMobile());
				existingSalesManObj.setName(salesman.getName());
				existingSalesManObj.setPassword(salesman.getPassword());
			}
			salesman = salesmanRepository.saveAndFlush(existingSalesManObj);
			status = App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, salesman);
		} else {
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, null);
		}
		return status;
	}
	
	public Status findAllBySalesManager(Long managerId) {
		List<Salesman> salesmans = salesmanRepository.findAllBySalesManagerId(managerId);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, salesmans);
		return status;
	}

	public Status findAllSalesManager(){
		return App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, salesManagerRepository.findAll());
	}
	
	
	public Status findAllSalesManagerByUserId(Long userId) {
		List<SalesManager> salesmans = salesManagerRepository.findAllByUserId(userId);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, salesmans);
		return status;
	}
	
	public Status createSalesManager(SalesManager salesManager){
		if(salesManager != null){
			SalesManager salesManagerObj = null;
			if(salesManager.getId() != null && salesManager.getId() > 0){
				salesManagerObj = salesManagerRepository.findOne(salesManager.getId());
				salesManagerObj.setEmail(salesManager.getEmail());
				salesManagerObj.setMobile(salesManager.getMobile());
				salesManagerObj.setName(salesManager.getName());
				salesManagerObj.setPassword(salesManager.getPassword());
			}
			salesManager = salesManagerRepository.saveAndFlush(salesManagerObj);
			status = App.getResponse(App.CODE_OK, App.STATUS_CREATE, App.MSG_CREATE, salesManager);
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, salesManager);
		}
		return status;
	}
	
	public Status findSalesManagerById(Long salesManagerId){
		SalesManager salesManager = salesManagerRepository.findOne(salesManagerId);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, salesManager);
		return status;
	}
	
	public Status findSalesManById(Long salesmanId){
		Salesman salesman = salesmanRepository.findOne(salesmanId);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, salesman);
		return status;
	}
	
	public Status deleteSalesManById(Long salesmanId){
		Salesman salesman = salesmanRepository.findOne(salesmanId);
		salesmanRepository.delete(salesman);
		status = App.getResponse(App.CODE_OK, App.STATUS_DELETE, App.MSG_DELETE, salesman);
		return status;
	}
	
	public Status deleteSalesManagerById(Long salesmanagerId){
		SalesManager salesManager = salesManagerRepository.findOne(salesmanagerId);
		salesManagerRepository.delete(salesManager);
		status = App.getResponse(App.CODE_OK, App.STATUS_DELETE, App.MSG_DELETE, salesManager);
		return status;
	}
	
	public Status findAllSalesMan(){
		List<Salesman> salesmans = salesmanRepository.findAll();
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, salesmans);
		return status;
	}
	
}
