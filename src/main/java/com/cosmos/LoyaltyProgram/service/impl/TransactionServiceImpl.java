package com.cosmos.LoyaltyProgram.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cosmos.LoyaltyProgram.model.DatatableCollection;
import com.cosmos.LoyaltyProgram.model.Driver;
import com.cosmos.LoyaltyProgram.model.Transaction;
import com.cosmos.LoyaltyProgram.repository.DriverRepository;
import com.cosmos.LoyaltyProgram.repository.TransactionRepository;
import com.cosmos.LoyaltyProgram.service.DriverService;
import com.cosmos.LoyaltyProgram.service.TransactionService;
import com.cosmos.LoyaltyProgram.sms.service.SendSms;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	DriverService driverService;
	
	@Autowired
	SendSms sendSMS;
	@Transactional
	@Override
	public Transaction createTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		transaction.setTrxTime(LocalDateTime.now());
		Driver driver = driverService.getDriverByCardNumber(transaction.getDriver().getCardNumber());
		calculateLoyaltyPointsAndVolume(transaction, driver);
		transaction.setDriver(driver);
		sendSMS.sendTransactionSuccessSMStoDriver(transaction);
		return transactionRepository.save(transaction);
	}

	private void calculateLoyaltyPointsAndVolume(Transaction transaction, Driver driver) {
		long loyaltyPoints=driver.getLoyaltyPoints();
		BigDecimal totalVolume=driver.getTotalFuelVolume();
		if(transaction.isRedeem()){

			if(driver.getLoyaltyPoints()< transaction.getFuelVolume().longValue()){
				throw new ValidationException("Driver does not have this much loyalty points");
			}
			loyaltyPoints=loyaltyPoints-transaction.getFuelVolume().longValue();
			
			
		}
		else{
			loyaltyPoints=loyaltyPoints+transaction.getFuelVolume().longValue();
			totalVolume=totalVolume.add(transaction.getFuelVolume());
		}
		
		driver.setLoyaltyPoints(loyaltyPoints);
		driver.setTotalFuelVolume(totalVolume);
	}

	@Override
	public DatatableCollection<Transaction> findAll() {
		// TODO Auto-generated method stub
		DatatableCollection<Transaction> datatableCollection = new DatatableCollection<>();
		datatableCollection.setData(transactionRepository.findAll());
		return datatableCollection;
	}

	@Override
	public void deleteTransaction(Long id) {
		// TODO Auto-generated method stub
		Transaction transaction = transactionRepository.findOne(id);
		Driver driver = driverService.getDriverByCardNumber(transaction.getDriver().getCardNumber());
		if(transaction.isRedeem()){
			driver.setLoyaltyPoints(driver.getLoyaltyPoints()+transaction.getFuelVolume().longValue());
		}
		else{
			driver.setLoyaltyPoints(driver.getLoyaltyPoints()-transaction.getFuelVolume().longValue());
			if(driver.getLoyaltyPoints()-transaction.getFuelVolume().longValue()<0){
				driver.setLoyaltyPoints(0);
			}
			driver.setTotalFuelVolume(driver.getTotalFuelVolume().subtract(transaction.getFuelVolume()));
		}
		
		driverService.saveDriver(driver);
		transactionRepository.delete(id);

	}

}
