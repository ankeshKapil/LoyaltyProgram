package com.cosmos.LoyaltyProgram.repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cosmos.LoyaltyProgram.model.Transaction;

@Repository("transactionRepository")
public interface TransactionRepository  extends JpaRepository<Transaction, Long>{

	List<Transaction> findFirst100ByOrderByTrxTimeDesc();
	
	List<Transaction> findByDriverCardNumberAndTrxTimeBetweenOrderByTrxTimeDesc(Long cardnumber,LocalDateTime startDate,LocalDateTime endDate);
	
	List<Transaction> findByDriverCardNumberOrderByTrxTimeDesc(Long cardnumber);
	
	List<Transaction> findByTrxTimeBetweenOrderByTrxTimeDesc(LocalDateTime startDate,LocalDateTime endDate);


}
