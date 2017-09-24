package com.cosmos.LoyaltyProgram.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

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
		driver.setUpdatedOn(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
		if(StringUtils.isEmpty(driver.getId())){
			driver.setCreatedOn(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
			if(driverRepository.getDriverByCardNumber(driver.getCardNumber())!=null)
				throw new ValidationException("A Driver with this Loyalty card Number Already exists in database");
			if(driverRepository.getDriverByPhoneNumber(driver.getPhoneNumber())!=null)
				throw new ValidationException("A Driver with this Loyalty card Number Already exists in database");
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
	
	public DatatableCollection<Driver> getDriverByLastFuelingTimeBefore(){
		DatatableCollection<Driver> collection=new DatatableCollection<>();
		collection.setData(driverRepository.getDriverByLastFuelingTimeBefore(LocalDateTime.of(LocalDate.now().minusDays(15), LocalTime.MIN)));
		return collection;
	}
	  

}
