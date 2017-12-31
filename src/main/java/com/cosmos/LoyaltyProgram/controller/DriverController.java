package com.cosmos.LoyaltyProgram.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import com.cosmos.LoyaltyProgram.model.Driver;
import com.cosmos.LoyaltyProgram.model.Prize;
import com.cosmos.LoyaltyProgram.service.DriverService;

@Controller
public class DriverController {
	
	Logger logger=Logger.getLogger(this.getClass());
	
	@Autowired
	DriverService driverService;
	
	@RequestMapping(value="/admin/driversview",method=RequestMethod.GET)
	public ModelAndView drivers(ModelAndView modelAndView){
		
		modelAndView.setViewName("admin/drivers");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/drivers",method=RequestMethod.POST)
	@ResponseBody
	public Driver saveDrivers(@RequestBody Driver driver){
		logger.info("Request to save driver"+driver.toString());	
		return driverService.saveDriver(driver);
	}
	
	@RequestMapping(value="/admin/drivers",method=RequestMethod.GET)
	@ResponseBody
	public DatatableCollection<Driver> findAll(){
		
		return driverService.findAll();
	}
	
	
	@RequestMapping(value="/admin/drivers/{id}",method=RequestMethod.DELETE)
	@ResponseBody
	public void deleteDriver(@PathVariable("id") Long id){
		driverService.deleteDriver(id);
		
	}
	@RequestMapping(value="/admin/getCardNumbers",method=RequestMethod.GET)
	public @ResponseBody List<Long> getCardNumbers(){
		return driverService.getCardNumbers();
	}
	
	@RequestMapping(value="/admin/getPrizes",method=RequestMethod.GET)
	public @ResponseBody Set<Prize> getPrizes(@RequestParam("searchCardNumber")Long cardNumber){
		Driver driver=driverService.getDriverByCardNumber(cardNumber);
		Set<Prize> prizes=driver.getScheme().getPrizes();
		prizes=prizes.stream().filter(prize->prize.getTargetFuel()<=driver.getLoyaltyPoints()).sorted().collect(Collectors.toSet());
		return prizes;
	}
	
	
	@RequestMapping(value="/admin/dormantdrivers",method=RequestMethod.GET)
	@ResponseBody
	public DatatableCollection<Driver> dormantDrivers(){
		
		return driverService.getDriverByLastFuelingTimeBefore();
	}

}
