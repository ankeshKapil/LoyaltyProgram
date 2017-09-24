package com.cosmos.LoyaltyProgram.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.ValidationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cosmos.LoyaltyProgram.controller.TransactionController;
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
	Logger logger=Logger.getLogger(TransactionController.class);
	@Autowired
	SendSms sendSMS;

	@Autowired
    DriverRepository driverRepository;
	
	@Transactional
	@Override
	public Transaction createTransaction(Transaction transaction) {
		// TODO Auto-generated method stub
		transaction.setTrxTime(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
		Driver driver = driverService.getDriverByCardNumber(transaction.getDriver().getCardNumber());
		calculateLoyaltyPointsAndVolume(transaction, driver);
		driver.setLastFuelingTime(transaction.getTrxTime());
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
		datatableCollection.setData(transactionRepository.findFirst100ByOrderByTrxTimeDesc());
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
		
		driverRepository.save(driver);
		transactionRepository.delete(id);

	}
	
	public DatatableCollection<Transaction> searchTransaction(Long cardnumber,String fromDate,String toDate){
		DatatableCollection<Transaction> datatableCollection = new DatatableCollection<>();
		DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("d MMMM, yyyy");
		LocalDateTime startDate = LocalDateTime.now();
		LocalDateTime endDate = LocalDateTime.now();
		if(StringUtils.isEmpty(fromDate)){
			LocalDate ld = LocalDate.ofEpochDay(0);
			startDate = LocalDateTime.of(ld, LocalTime.MIDNIGHT);
		}
		else{
			LocalDate ld = LocalDate.parse(fromDate, DATEFORMATTER);
		    startDate = LocalDateTime.of(ld, LocalTime.MIDNIGHT);
		}
		if(StringUtils.isEmpty(toDate)){
			LocalDate ld = LocalDate.now();
			endDate = LocalDateTime.of(ld, LocalTime.MAX);
		}
		else{
			LocalDate ld = LocalDate.parse(toDate, DATEFORMATTER);
		    endDate = LocalDateTime.of(ld, LocalTime.MAX);
		}
		if(endDate.isBefore(startDate)){
			throw new ValidationException("Invalid date range");
		}
		if(cardnumber!=null){

			logger.info("search for transactions with card number "+cardnumber+" start time "+startDate+" end time "+endDate);
			datatableCollection.setData(transactionRepository.findByDriverCardNumberAndTrxTimeBetweenOrderByTrxTimeDesc(cardnumber, startDate, endDate));
		}
	
		else{
			datatableCollection.setData(transactionRepository.findByTrxTimeBetweenOrderByTrxTimeDesc(startDate, endDate));
		}
		return datatableCollection;
	};


}
