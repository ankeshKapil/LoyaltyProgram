package com.cosmos.LoyaltyProgram.service;

import com.cosmos.LoyaltyProgram.model.DatatableCollection;
import com.cosmos.LoyaltyProgram.model.Transaction;

public interface TransactionService {

	Transaction createTransaction(Transaction transaction);

	DatatableCollection<Transaction> findAll();

	void deleteTransaction(Long id);

}
