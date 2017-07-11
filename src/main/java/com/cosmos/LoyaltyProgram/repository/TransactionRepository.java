package com.cosmos.LoyaltyProgram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cosmos.LoyaltyProgram.model.Transaction;

@Repository("transactionRepository")
public interface TransactionRepository  extends JpaRepository<Transaction, Long>{

}
