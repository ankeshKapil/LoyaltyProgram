package com.cosmos.LoyaltyProgram.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cosmos.LoyaltyProgram.model.DatatableCollection;
import com.cosmos.LoyaltyProgram.model.Transaction;
import com.cosmos.LoyaltyProgram.service.TransactionService;

@Controller
public class TransactionController {

	@Autowired
	TransactionService transactionService;
	
	Logger logger=Logger.getLogger(TransactionController.class);

	@RequestMapping(value = "/admin/transactionview", method = RequestMethod.GET)
	public ModelAndView getTransactionView(ModelAndView modelAndView) {
		modelAndView.setViewName("admin/transaction");

		return modelAndView;
	}

	@RequestMapping(value = "/admin/transaction", method = RequestMethod.POST)
	@ResponseBody
	public Transaction add(@RequestBody @Valid Transaction transaction) {
		transaction = transactionService.createTransaction(transaction);

		return transaction;
	}


	@RequestMapping(value = "/admin/transaction/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteTransaction(@PathVariable("id") Long id) {
		transactionService.deleteTransaction(id);

	}

	@RequestMapping(value = "/admin/transaction", method = RequestMethod.GET)
	@ResponseBody
	public DatatableCollection<Transaction> searchTransaction(@RequestParam("searchCardNumber") Long cardNumber,
			@RequestParam("startDate") String fromDate, @RequestParam("endDate") String toDate) {
		
		return transactionService.searchTransaction(cardNumber, fromDate, toDate);

	}

}
