package com.cosmos.LoyaltyProgram.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cosmos.LoyaltyProgram.model.DatatableCollection;
import com.cosmos.LoyaltyProgram.model.Driver;
import com.cosmos.LoyaltyProgram.repository.DriverRepository;
import com.cosmos.LoyaltyProgram.service.DriverService;
@Service("driverService")
public class DriverServiceImpl implements DriverService {
	Logger logger=Logger.getLogger(this.getClass());
	@Autowired
	DriverRepository driverRepository;
	
	@Override
	public Driver saveDriver(Driver driver) {
		driver.setUpdatedOn(LocalDateTime.now());
		if(StringUtils.isEmpty(driver.getId())){
			driver.setCreatedOn(LocalDateTime.now());
		}
		return driverRepository.save(driver);
	}

	@Override
	public void deleteDriver(Long id) {
		 driverRepository.delete(id);;
		
	}

	@Override
	public DatatableCollection<Driver> findAll() {
		DatatableCollection<Driver> collection=new DatatableCollection<>();
		collection.setData(driverRepository.findAll());
		return collection;
	}
	
	public List<Long> getCardNumbers(){
		return driverRepository.findAll().stream().map(Driver::getCardNumber).collect(Collectors.toList());
	}
	
	public Driver getDriverByCardNumber(Long cardNumber){
		return driverRepository.getDriverByCardNumber(cardNumber);
	}

}
