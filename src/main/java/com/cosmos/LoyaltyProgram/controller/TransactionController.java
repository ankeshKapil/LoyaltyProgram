package com.cosmos.LoyaltyProgram.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cosmos.LoyaltyProgram.model.DatatableCollection;
import com.cosmos.LoyaltyProgram.model.Scheme;
import com.cosmos.LoyaltyProgram.model.Transaction;
import com.cosmos.LoyaltyProgram.service.TransactionService;
import com.cosmos.LoyaltyProgram.sms.service.SendSms;

@Controller
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;
	
	
	
	@RequestMapping(value="/admin/transactionview",method=RequestMethod.GET)
	public ModelAndView getTransactionView(ModelAndView modelAndView){
		modelAndView.setViewName("admin/transaction");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/transaction",method=RequestMethod.POST)
	@ResponseBody
	public Transaction add(@RequestBody  @Valid Transaction transaction){
		transaction=transactionService.createTransaction(transaction);
		
		return transaction;
	}
	
	@RequestMapping(value="/admin/transaction",method=RequestMethod.GET)
	@ResponseBody
	public DatatableCollection<Transaction> findAll(){
		
		return transactionService.findAll();
	}
	
	@RequestMapping(value="/admin/transaction/{id}",method=RequestMethod.DELETE)
	@ResponseBody
	public void deleteTransaction(@PathVariable("id") Long id){
		transactionService.deleteTransaction(id);
		
	}
	
	

	

}
