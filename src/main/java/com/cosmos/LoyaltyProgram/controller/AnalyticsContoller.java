package com.cosmos.LoyaltyProgram.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cosmos.LoyaltyProgram.model.Driver;
import com.cosmos.LoyaltyProgram.sms.service.SendSms;

@Controller
public class AnalyticsContoller {
	
	
	@Autowired
	SendSms sendSms;

	@RequestMapping(value="/admin/analyticsview",method=RequestMethod.GET)
	public ModelAndView drivers(ModelAndView modelAndView){
		
		modelAndView.setViewName("admin/analytics");
		
		return modelAndView;
	}
	
	
	@RequestMapping(value="/admin/notifydormantdrivers",method=RequestMethod.POST)
	public @ResponseBody String notifyDromantDrivers(@RequestBody List<Driver> drivers){
		
		sendSms.sendNotifyDormantDriver(drivers);
		
		return "Successfully sent Messages";
	}
}
