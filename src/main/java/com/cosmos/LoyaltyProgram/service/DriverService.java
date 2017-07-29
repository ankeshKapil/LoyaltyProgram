package com.cosmos.LoyaltyProgram.service;

import java.util.List;

import com.cosmos.LoyaltyProgram.model.DatatableCollection;
import com.cosmos.LoyaltyProgram.model.Driver;

public interface DriverService {
	
	Driver saveDriver(Driver driver);
	
    DatatableCollection<Driver> findAll();

	void deleteDriver(Long id);

	public List<Long> getCardNumbers();
	
	public Driver getDriverByCardNumber(Long cardNumber);
	
	public DatatableCollection<Driver> getDriverByLastFuelingTimeBefore();

}
