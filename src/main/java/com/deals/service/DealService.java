package com.deals.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deals.model.Deal;
import com.deals.repository.DealRepository;
import com.deals.util.App;
import com.deals.util.Status;

@Service
public class DealService {
	private static Status status =  new Status();
	
	@Autowired
	private DealRepository dealRepository;
	
	public Status findAllByUserId(Long userId){
		List<Deal> deals = dealRepository.findAllByUserId(userId);
		status = App.getResponse(App.CODE_OK, App.STATUS_OK, App.STATUS_OK, deals);
		return status;
	}
	
}
