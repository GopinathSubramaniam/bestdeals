package com.deals.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.model.Company;
import com.deals.repository.CompanyRepository;
import com.deals.util.App;
import com.deals.util.Status;

@Service
public class CompanyService {

	private static Status status = new Status();
	
	@Autowired
	private CompanyRepository companyRepository;
	
	public Status create(Company company){
		if(company != null){
			company = companyRepository.saveAndFlush(company);
			status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, company);
		}else{
			status = App.getResponse(App.CODE_FAIL, App.STATUS_FAIL, App.MSG_FAIL, null);
		}
		return status;
	}
	
	public Status findAll(){
		List<Company> companies = companyRepository.findAll();
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.MSG_OK, companies);
		return status;
	}
	
}
