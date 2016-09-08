package com.deals.service;

import java.util.List;

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

	private Status status = new Status();

	@Autowired
	private SalesmanRepository salesmanRepository;
	
	@Autowired
	private SalesManagerRepository salesManagerRepository;

	public Status create(Salesman salesman) {
		if (salesman != null) {
			salesman = salesmanRepository.saveAndFlush(salesman);
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
			salesManager = salesManagerRepository.saveAndFlush(salesManager);
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
	
	public Status findAllSalesMan(){
		List<Salesman> salesmans = salesmanRepository.findAll();
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, salesmans);
		return status;
	}
	
}
